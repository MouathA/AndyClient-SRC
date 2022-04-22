package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.logging.*;
import java.lang.ref.*;
import java.util.concurrent.*;
import javax.annotation.concurrent.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;

@Beta
public final class ServiceManager
{
    private static final Logger logger;
    private static final ListenerCallQueue.Callback HEALTHY_CALLBACK;
    private static final ListenerCallQueue.Callback STOPPED_CALLBACK;
    private final ServiceManagerState state;
    private final ImmutableList services;
    
    public ServiceManager(final Iterable iterable) {
        ImmutableList services = ImmutableList.copyOf(iterable);
        if (services.isEmpty()) {
            ServiceManager.logger.log(Level.WARNING, "ServiceManager configured with no services.  Is your application configured properly?", new EmptyServiceManagerWarning((ServiceManager$1)null));
            services = ImmutableList.of(new NoOpService(null));
        }
        this.state = new ServiceManagerState(services);
        this.services = services;
        final WeakReference weakReference = new WeakReference((T)this.state);
        final ListeningExecutorService sameThreadExecutor = MoreExecutors.sameThreadExecutor();
        for (final Service service : services) {
            service.addListener(new ServiceListener(service, weakReference), sameThreadExecutor);
            Preconditions.checkArgument(service.state() == Service.State.NEW, "Can only manage NEW services, %s", service);
        }
        this.state.markReady();
    }
    
    public void addListener(final Listener listener, final Executor executor) {
        this.state.addListener(listener, executor);
    }
    
    public void addListener(final Listener listener) {
        this.state.addListener(listener, MoreExecutors.sameThreadExecutor());
    }
    
    public ServiceManager startAsync() {
        for (final Service service : this.services) {
            final Service.State state = service.state();
            Preconditions.checkState(state == Service.State.NEW, "Service %s is %s, cannot start it.", service, state);
        }
        final Iterator iterator2 = this.services.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().startAsync();
        }
        return this;
    }
    
    public void awaitHealthy() {
        this.state.awaitHealthy();
    }
    
    public void awaitHealthy(final long n, final TimeUnit timeUnit) throws TimeoutException {
        this.state.awaitHealthy(n, timeUnit);
    }
    
    public ServiceManager stopAsync() {
        final Iterator iterator = this.services.iterator();
        while (iterator.hasNext()) {
            iterator.next().stopAsync();
        }
        return this;
    }
    
    public void awaitStopped() {
        this.state.awaitStopped();
    }
    
    public void awaitStopped(final long n, final TimeUnit timeUnit) throws TimeoutException {
        this.state.awaitStopped(n, timeUnit);
    }
    
    public boolean isHealthy() {
        final Iterator iterator = this.services.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isRunning()) {
                return false;
            }
        }
        return true;
    }
    
    public ImmutableMultimap servicesByState() {
        return this.state.servicesByState();
    }
    
    public ImmutableMap startupTimes() {
        return this.state.startupTimes();
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(ServiceManager.class).add("services", Collections2.filter(this.services, Predicates.not(Predicates.instanceOf(NoOpService.class)))).toString();
    }
    
    static Logger access$200() {
        return ServiceManager.logger;
    }
    
    static ListenerCallQueue.Callback access$300() {
        return ServiceManager.STOPPED_CALLBACK;
    }
    
    static ListenerCallQueue.Callback access$400() {
        return ServiceManager.HEALTHY_CALLBACK;
    }
    
    static {
        logger = Logger.getLogger(ServiceManager.class.getName());
        HEALTHY_CALLBACK = new ListenerCallQueue.Callback("healthy()") {
            void call(final Listener listener) {
                listener.healthy();
            }
            
            @Override
            void call(final Object o) {
                this.call((Listener)o);
            }
        };
        STOPPED_CALLBACK = new ListenerCallQueue.Callback("stopped()") {
            void call(final Listener listener) {
                listener.stopped();
            }
            
            @Override
            void call(final Object o) {
                this.call((Listener)o);
            }
        };
    }
    
    private static final class EmptyServiceManagerWarning extends Throwable
    {
        private EmptyServiceManagerWarning() {
        }
        
        EmptyServiceManagerWarning(final ServiceManager$1 callback) {
            this();
        }
    }
    
    private static final class NoOpService extends AbstractService
    {
        private NoOpService() {
        }
        
        @Override
        protected void doStart() {
            this.notifyStarted();
        }
        
        @Override
        protected void doStop() {
            this.notifyStopped();
        }
        
        NoOpService(final ServiceManager$1 callback) {
            this();
        }
    }
    
    private static final class ServiceListener extends Service.Listener
    {
        final Service service;
        final WeakReference state;
        
        ServiceListener(final Service service, final WeakReference state) {
            this.service = service;
            this.state = state;
        }
        
        @Override
        public void starting() {
            final ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState != null) {
                serviceManagerState.transitionService(this.service, Service.State.NEW, Service.State.STARTING);
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.access$200().log(Level.FINE, "Starting {0}.", this.service);
                }
            }
        }
        
        @Override
        public void running() {
            final ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState != null) {
                serviceManagerState.transitionService(this.service, Service.State.STARTING, Service.State.RUNNING);
            }
        }
        
        @Override
        public void stopping(final Service.State state) {
            final ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState != null) {
                serviceManagerState.transitionService(this.service, state, Service.State.STOPPING);
            }
        }
        
        @Override
        public void terminated(final Service.State state) {
            final ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState != null) {
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.access$200().log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[] { this.service, state });
                }
                serviceManagerState.transitionService(this.service, state, Service.State.TERMINATED);
            }
        }
        
        @Override
        public void failed(final Service.State state, final Throwable t) {
            final ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState != null) {
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.access$200().log(Level.SEVERE, "Service " + this.service + " has failed in the " + state + " state.", t);
                }
                serviceManagerState.transitionService(this.service, state, Service.State.FAILED);
            }
        }
    }
    
    private static final class ServiceManagerState
    {
        final Monitor monitor;
        @GuardedBy("monitor")
        final SetMultimap servicesByState;
        @GuardedBy("monitor")
        final Multiset states;
        @GuardedBy("monitor")
        final Map startupTimers;
        @GuardedBy("monitor")
        boolean ready;
        @GuardedBy("monitor")
        boolean transitioned;
        final int numberOfServices;
        final Monitor.Guard awaitHealthGuard;
        final Monitor.Guard stoppedGuard;
        @GuardedBy("monitor")
        final List listeners;
        
        ServiceManagerState(final ImmutableCollection collection) {
            this.monitor = new Monitor();
            this.servicesByState = Multimaps.newSetMultimap(new EnumMap(Service.State.class), new Supplier() {
                final ServiceManagerState this$0;
                
                @Override
                public Set get() {
                    return Sets.newLinkedHashSet();
                }
                
                @Override
                public Object get() {
                    return this.get();
                }
            });
            this.states = this.servicesByState.keys();
            this.startupTimers = Maps.newIdentityHashMap();
            this.awaitHealthGuard = new Monitor.Guard(this.monitor) {
                final ServiceManagerState this$0;
                
                @Override
                public boolean isSatisfied() {
                    return this.this$0.states.count(Service.State.RUNNING) == this.this$0.numberOfServices || this.this$0.states.contains(Service.State.STOPPING) || this.this$0.states.contains(Service.State.TERMINATED) || this.this$0.states.contains(Service.State.FAILED);
                }
            };
            this.stoppedGuard = new Monitor.Guard(this.monitor) {
                final ServiceManagerState this$0;
                
                @Override
                public boolean isSatisfied() {
                    return this.this$0.states.count(Service.State.TERMINATED) + this.this$0.states.count(Service.State.FAILED) == this.this$0.numberOfServices;
                }
            };
            this.listeners = Collections.synchronizedList(new ArrayList<Object>());
            this.numberOfServices = collection.size();
            this.servicesByState.putAll(Service.State.NEW, collection);
            final Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                this.startupTimers.put(iterator.next(), Stopwatch.createUnstarted());
            }
        }
        
        void markReady() {
            this.monitor.enter();
            if (!this.transitioned) {
                this.ready = true;
                this.monitor.leave();
                return;
            }
            final ArrayList arrayList = Lists.newArrayList();
            for (final Service service : this.servicesByState().values()) {
                if (service.state() != Service.State.NEW) {
                    arrayList.add(service);
                }
            }
            throw new IllegalArgumentException("Services started transitioning asynchronously before the ServiceManager was constructed: " + arrayList);
        }
        
        void addListener(final Listener listener, final Executor executor) {
            Preconditions.checkNotNull(listener, (Object)"listener");
            Preconditions.checkNotNull(executor, (Object)"executor");
            this.monitor.enter();
            if (!this.stoppedGuard.isSatisfied()) {
                this.listeners.add(new ListenerCallQueue(listener, executor));
            }
            this.monitor.leave();
        }
        
        void awaitHealthy() {
            this.monitor.enterWhenUninterruptibly(this.awaitHealthGuard);
            this.checkHealthy();
            this.monitor.leave();
        }
        
        void awaitHealthy(final long n, final TimeUnit timeUnit) throws TimeoutException {
            this.monitor.enter();
            if (!this.monitor.waitForUninterruptibly(this.awaitHealthGuard, n, timeUnit)) {
                throw new TimeoutException("Timeout waiting for the services to become healthy. The following services have not started: " + Multimaps.filterKeys(this.servicesByState, Predicates.in(ImmutableSet.of(Service.State.NEW, Service.State.STARTING))));
            }
            this.checkHealthy();
            this.monitor.leave();
        }
        
        void awaitStopped() {
            this.monitor.enterWhenUninterruptibly(this.stoppedGuard);
            this.monitor.leave();
        }
        
        void awaitStopped(final long n, final TimeUnit timeUnit) throws TimeoutException {
            this.monitor.enter();
            if (!this.monitor.waitForUninterruptibly(this.stoppedGuard, n, timeUnit)) {
                throw new TimeoutException("Timeout waiting for the services to stop. The following services have not stopped: " + Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.in(ImmutableSet.of(Service.State.TERMINATED, Service.State.FAILED)))));
            }
            this.monitor.leave();
        }
        
        ImmutableMultimap servicesByState() {
            final ImmutableSetMultimap.Builder builder = ImmutableSetMultimap.builder();
            this.monitor.enter();
            for (final Map.Entry<Object, V> entry : this.servicesByState.entries()) {
                if (!(entry.getValue() instanceof NoOpService)) {
                    builder.put(entry.getKey(), (Object)entry.getValue());
                }
            }
            this.monitor.leave();
            return builder.build();
        }
        
        ImmutableMap startupTimes() {
            this.monitor.enter();
            final ArrayList arrayListWithCapacity = Lists.newArrayListWithCapacity(this.states.size() - this.states.count(Service.State.NEW) + this.states.count(Service.State.STARTING));
            for (final Map.Entry<Service, V> entry : this.startupTimers.entrySet()) {
                final Service service = entry.getKey();
                final Stopwatch stopwatch = (Stopwatch)entry.getValue();
                if (!stopwatch.isRunning() && !this.servicesByState.containsEntry(Service.State.NEW, service) && !(service instanceof NoOpService)) {
                    arrayListWithCapacity.add(Maps.immutableEntry(service, stopwatch.elapsed(TimeUnit.MILLISECONDS)));
                }
            }
            this.monitor.leave();
            Collections.sort((List<Object>)arrayListWithCapacity, Ordering.natural().onResultOf(new Function() {
                final ServiceManagerState this$0;
                
                public Long apply(final Map.Entry entry) {
                    return entry.getValue();
                }
                
                @Override
                public Object apply(final Object o) {
                    return this.apply((Map.Entry)o);
                }
            }));
            final ImmutableMap.Builder builder = ImmutableMap.builder();
            final Iterator<Map.Entry> iterator2 = arrayListWithCapacity.iterator();
            while (iterator2.hasNext()) {
                builder.put(iterator2.next());
            }
            return builder.build();
        }
        
        void transitionService(final Service service, final Service.State state, final Service.State state2) {
            Preconditions.checkNotNull(service);
            Preconditions.checkArgument(state != state2);
            this.monitor.enter();
            this.transitioned = true;
            if (!this.ready) {
                this.monitor.leave();
                this.executeListeners();
                return;
            }
            Preconditions.checkState(this.servicesByState.remove(state, service), "Service %s not at the expected location in the state map %s", service, state);
            Preconditions.checkState(this.servicesByState.put(state2, service), "Service %s in the state map unexpectedly at %s", service, state2);
            final Stopwatch stopwatch = this.startupTimers.get(service);
            if (state == Service.State.NEW) {
                stopwatch.start();
            }
            if (state2.compareTo(Service.State.RUNNING) >= 0 && stopwatch.isRunning()) {
                stopwatch.stop();
                if (!(service instanceof NoOpService)) {
                    ServiceManager.access$200().log(Level.FINE, "Started {0} in {1}.", new Object[] { service, stopwatch });
                }
            }
            if (state2 == Service.State.FAILED) {
                this.fireFailedListeners(service);
            }
            if (this.states.count(Service.State.RUNNING) == this.numberOfServices) {
                this.fireHealthyListeners();
            }
            else if (this.states.count(Service.State.TERMINATED) + this.states.count(Service.State.FAILED) == this.numberOfServices) {
                this.fireStoppedListeners();
            }
            this.monitor.leave();
            this.executeListeners();
        }
        
        @GuardedBy("monitor")
        void fireStoppedListeners() {
            ServiceManager.access$300().enqueueOn(this.listeners);
        }
        
        @GuardedBy("monitor")
        void fireHealthyListeners() {
            ServiceManager.access$400().enqueueOn(this.listeners);
        }
        
        @GuardedBy("monitor")
        void fireFailedListeners(final Service service) {
            new ListenerCallQueue.Callback("failed({service=" + service + "})", service) {
                final Service val$service;
                final ServiceManagerState this$0;
                
                void call(final Listener listener) {
                    listener.failure(this.val$service);
                }
                
                @Override
                void call(final Object o) {
                    this.call((Listener)o);
                }
            }.enqueueOn(this.listeners);
        }
        
        void executeListeners() {
            Preconditions.checkState(!this.monitor.isOccupiedByCurrentThread(), (Object)"It is incorrect to execute listeners with the monitor held.");
            while (0 < this.listeners.size()) {
                this.listeners.get(0).execute();
                int n = 0;
                ++n;
            }
        }
        
        @GuardedBy("monitor")
        void checkHealthy() {
            if (this.states.count(Service.State.RUNNING) != this.numberOfServices) {
                throw new IllegalStateException("Expected to be healthy after starting. The following services are not running: " + Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.equalTo(Service.State.RUNNING))));
            }
        }
    }
    
    @Beta
    public abstract static class Listener
    {
        public void healthy() {
        }
        
        public void stopped() {
        }
        
        public void failure(final Service service) {
        }
    }
}
