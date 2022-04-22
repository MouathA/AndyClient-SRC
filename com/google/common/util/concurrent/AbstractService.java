package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.*;
import com.google.common.base.*;
import java.util.concurrent.*;
import javax.annotation.concurrent.*;
import javax.annotation.*;

@Beta
public abstract class AbstractService implements Service
{
    private static final ListenerCallQueue.Callback STARTING_CALLBACK;
    private static final ListenerCallQueue.Callback RUNNING_CALLBACK;
    private static final ListenerCallQueue.Callback STOPPING_FROM_STARTING_CALLBACK;
    private static final ListenerCallQueue.Callback STOPPING_FROM_RUNNING_CALLBACK;
    private static final ListenerCallQueue.Callback TERMINATED_FROM_NEW_CALLBACK;
    private static final ListenerCallQueue.Callback TERMINATED_FROM_RUNNING_CALLBACK;
    private static final ListenerCallQueue.Callback TERMINATED_FROM_STOPPING_CALLBACK;
    private final Monitor monitor;
    private final Monitor.Guard isStartable;
    private final Monitor.Guard isStoppable;
    private final Monitor.Guard hasReachedRunning;
    private final Monitor.Guard isStopped;
    @GuardedBy("monitor")
    private final List listeners;
    @GuardedBy("monitor")
    private StateSnapshot snapshot;
    
    private static ListenerCallQueue.Callback terminatedCallback(final State state) {
        return new ListenerCallQueue.Callback("terminated({from = " + state + "})", state) {
            final State val$from;
            
            void call(final Listener listener) {
                listener.terminated(this.val$from);
            }
            
            @Override
            void call(final Object o) {
                this.call((Listener)o);
            }
        };
    }
    
    private static ListenerCallQueue.Callback stoppingCallback(final State state) {
        return new ListenerCallQueue.Callback("stopping({from = " + state + "})", state) {
            final State val$from;
            
            void call(final Listener listener) {
                listener.stopping(this.val$from);
            }
            
            @Override
            void call(final Object o) {
                this.call((Listener)o);
            }
        };
    }
    
    protected AbstractService() {
        this.monitor = new Monitor();
        this.isStartable = new Monitor.Guard(this.monitor) {
            final AbstractService this$0;
            
            @Override
            public boolean isSatisfied() {
                return this.this$0.state() == State.NEW;
            }
        };
        this.isStoppable = new Monitor.Guard(this.monitor) {
            final AbstractService this$0;
            
            @Override
            public boolean isSatisfied() {
                return this.this$0.state().compareTo(State.RUNNING) <= 0;
            }
        };
        this.hasReachedRunning = new Monitor.Guard(this.monitor) {
            final AbstractService this$0;
            
            @Override
            public boolean isSatisfied() {
                return this.this$0.state().compareTo(State.RUNNING) >= 0;
            }
        };
        this.isStopped = new Monitor.Guard(this.monitor) {
            final AbstractService this$0;
            
            @Override
            public boolean isSatisfied() {
                return this.this$0.state().isTerminal();
            }
        };
        this.listeners = Collections.synchronizedList(new ArrayList<Object>());
        this.snapshot = new StateSnapshot(State.NEW);
    }
    
    protected abstract void doStart();
    
    protected abstract void doStop();
    
    @Override
    public final Service startAsync() {
        if (this.monitor.enterIf(this.isStartable)) {
            this.snapshot = new StateSnapshot(State.STARTING);
            this.starting();
            this.doStart();
            this.monitor.leave();
            this.executeListeners();
            return this;
        }
        throw new IllegalStateException("Service " + this + " has already been started");
    }
    
    @Override
    public final Service stopAsync() {
        if (this.monitor.enterIf(this.isStoppable)) {
            final State state = this.state();
            switch (state) {
                case NEW: {
                    this.snapshot = new StateSnapshot(State.TERMINATED);
                    this.terminated(State.NEW);
                    break;
                }
                case STARTING: {
                    this.snapshot = new StateSnapshot(State.STARTING, true, null);
                    this.stopping(State.STARTING);
                    break;
                }
                case RUNNING: {
                    this.snapshot = new StateSnapshot(State.STOPPING);
                    this.stopping(State.RUNNING);
                    this.doStop();
                    break;
                }
                case STOPPING:
                case TERMINATED:
                case FAILED: {
                    throw new AssertionError((Object)("isStoppable is incorrectly implemented, saw: " + state));
                }
                default: {
                    throw new AssertionError((Object)("Unexpected state: " + state));
                }
            }
            this.monitor.leave();
            this.executeListeners();
        }
        return this;
    }
    
    @Override
    public final void awaitRunning() {
        this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);
        this.checkCurrentState(State.RUNNING);
        this.monitor.leave();
    }
    
    @Override
    public final void awaitRunning(final long n, final TimeUnit timeUnit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, n, timeUnit)) {
            this.checkCurrentState(State.RUNNING);
            this.monitor.leave();
            return;
        }
        throw new TimeoutException("Timed out waiting for " + this + " to reach the RUNNING state. " + "Current state: " + this.state());
    }
    
    @Override
    public final void awaitTerminated() {
        this.monitor.enterWhenUninterruptibly(this.isStopped);
        this.checkCurrentState(State.TERMINATED);
        this.monitor.leave();
    }
    
    @Override
    public final void awaitTerminated(final long n, final TimeUnit timeUnit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.isStopped, n, timeUnit)) {
            this.checkCurrentState(State.TERMINATED);
            this.monitor.leave();
            return;
        }
        throw new TimeoutException("Timed out waiting for " + this + " to reach a terminal state. " + "Current state: " + this.state());
    }
    
    @GuardedBy("monitor")
    private void checkCurrentState(final State state) {
        final State state2 = this.state();
        if (state2 == state) {
            return;
        }
        if (state2 == State.FAILED) {
            throw new IllegalStateException("Expected the service to be " + state + ", but the service has FAILED", this.failureCause());
        }
        throw new IllegalStateException("Expected the service to be " + state + ", but was " + state2);
    }
    
    protected final void notifyStarted() {
        this.monitor.enter();
        if (this.snapshot.state != State.STARTING) {
            final IllegalStateException ex = new IllegalStateException("Cannot notifyStarted() when the service is " + this.snapshot.state);
            this.notifyFailed(ex);
            throw ex;
        }
        if (this.snapshot.shutdownWhenStartupFinishes) {
            this.snapshot = new StateSnapshot(State.STOPPING);
            this.doStop();
        }
        else {
            this.snapshot = new StateSnapshot(State.RUNNING);
            this.running();
        }
        this.monitor.leave();
        this.executeListeners();
    }
    
    protected final void notifyStopped() {
        this.monitor.enter();
        final State state = this.snapshot.state;
        if (state != State.STOPPING && state != State.RUNNING) {
            final IllegalStateException ex = new IllegalStateException("Cannot notifyStopped() when the service is " + state);
            this.notifyFailed(ex);
            throw ex;
        }
        this.snapshot = new StateSnapshot(State.TERMINATED);
        this.terminated(state);
        this.monitor.leave();
        this.executeListeners();
    }
    
    protected final void notifyFailed(final Throwable t) {
        Preconditions.checkNotNull(t);
        this.monitor.enter();
        final State state = this.state();
        switch (state) {
            case NEW:
            case TERMINATED: {
                throw new IllegalStateException("Failed while in state:" + state, t);
            }
            case STARTING:
            case RUNNING:
            case STOPPING: {
                this.snapshot = new StateSnapshot(State.FAILED, false, t);
                this.failed(state, t);
                break;
            }
            case FAILED: {
                break;
            }
            default: {
                throw new AssertionError((Object)("Unexpected state: " + state));
            }
        }
        this.monitor.leave();
        this.executeListeners();
    }
    
    @Override
    public final boolean isRunning() {
        return this.state() == State.RUNNING;
    }
    
    @Override
    public final State state() {
        return this.snapshot.externalState();
    }
    
    @Override
    public final Throwable failureCause() {
        return this.snapshot.failureCause();
    }
    
    @Override
    public final void addListener(final Listener listener, final Executor executor) {
        Preconditions.checkNotNull(listener, (Object)"listener");
        Preconditions.checkNotNull(executor, (Object)"executor");
        this.monitor.enter();
        if (!this.state().isTerminal()) {
            this.listeners.add(new ListenerCallQueue(listener, executor));
        }
        this.monitor.leave();
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [" + this.state() + "]";
    }
    
    private void executeListeners() {
        if (!this.monitor.isOccupiedByCurrentThread()) {
            while (0 < this.listeners.size()) {
                this.listeners.get(0).execute();
                int n = 0;
                ++n;
            }
        }
    }
    
    @GuardedBy("monitor")
    private void starting() {
        AbstractService.STARTING_CALLBACK.enqueueOn(this.listeners);
    }
    
    @GuardedBy("monitor")
    private void running() {
        AbstractService.RUNNING_CALLBACK.enqueueOn(this.listeners);
    }
    
    @GuardedBy("monitor")
    private void stopping(final State state) {
        if (state == State.STARTING) {
            AbstractService.STOPPING_FROM_STARTING_CALLBACK.enqueueOn(this.listeners);
        }
        else {
            if (state != State.RUNNING) {
                throw new AssertionError();
            }
            AbstractService.STOPPING_FROM_RUNNING_CALLBACK.enqueueOn(this.listeners);
        }
    }
    
    @GuardedBy("monitor")
    private void terminated(final State state) {
        switch (state) {
            case NEW: {
                AbstractService.TERMINATED_FROM_NEW_CALLBACK.enqueueOn(this.listeners);
                break;
            }
            case RUNNING: {
                AbstractService.TERMINATED_FROM_RUNNING_CALLBACK.enqueueOn(this.listeners);
                break;
            }
            case STOPPING: {
                AbstractService.TERMINATED_FROM_STOPPING_CALLBACK.enqueueOn(this.listeners);
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    @GuardedBy("monitor")
    private void failed(final State state, final Throwable t) {
        new ListenerCallQueue.Callback("failed({from = " + state + ", cause = " + t + "})", state, t) {
            final State val$from;
            final Throwable val$cause;
            final AbstractService this$0;
            
            void call(final Listener listener) {
                listener.failed(this.val$from, this.val$cause);
            }
            
            @Override
            void call(final Object o) {
                this.call((Listener)o);
            }
        }.enqueueOn(this.listeners);
    }
    
    static {
        STARTING_CALLBACK = new ListenerCallQueue.Callback("starting()") {
            void call(final Listener listener) {
                listener.starting();
            }
            
            @Override
            void call(final Object o) {
                this.call((Listener)o);
            }
        };
        RUNNING_CALLBACK = new ListenerCallQueue.Callback("running()") {
            void call(final Listener listener) {
                listener.running();
            }
            
            @Override
            void call(final Object o) {
                this.call((Listener)o);
            }
        };
        STOPPING_FROM_STARTING_CALLBACK = stoppingCallback(State.STARTING);
        STOPPING_FROM_RUNNING_CALLBACK = stoppingCallback(State.RUNNING);
        TERMINATED_FROM_NEW_CALLBACK = terminatedCallback(State.NEW);
        TERMINATED_FROM_RUNNING_CALLBACK = terminatedCallback(State.RUNNING);
        TERMINATED_FROM_STOPPING_CALLBACK = terminatedCallback(State.STOPPING);
    }
    
    @Immutable
    private static final class StateSnapshot
    {
        final State state;
        final boolean shutdownWhenStartupFinishes;
        @Nullable
        final Throwable failure;
        
        StateSnapshot(final State state) {
            this(state, false, null);
        }
        
        StateSnapshot(final State state, final boolean shutdownWhenStartupFinishes, @Nullable final Throwable failure) {
            Preconditions.checkArgument(!shutdownWhenStartupFinishes || state == State.STARTING, "shudownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", state);
            Preconditions.checkArgument(!(failure != null ^ state == State.FAILED), "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", state, failure);
            this.state = state;
            this.shutdownWhenStartupFinishes = shutdownWhenStartupFinishes;
            this.failure = failure;
        }
        
        State externalState() {
            if (this.shutdownWhenStartupFinishes && this.state == State.STARTING) {
                return State.STOPPING;
            }
            return this.state;
        }
        
        Throwable failureCause() {
            Preconditions.checkState(this.state == State.FAILED, "failureCause() is only valid if the service has failed, service is %s", this.state);
            return this.failure;
        }
    }
}
