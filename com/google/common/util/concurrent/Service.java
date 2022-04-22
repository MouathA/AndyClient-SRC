package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.concurrent.*;

@Beta
public interface Service
{
    Service startAsync();
    
    boolean isRunning();
    
    State state();
    
    Service stopAsync();
    
    void awaitRunning();
    
    void awaitRunning(final long p0, final TimeUnit p1) throws TimeoutException;
    
    void awaitTerminated();
    
    void awaitTerminated(final long p0, final TimeUnit p1) throws TimeoutException;
    
    Throwable failureCause();
    
    void addListener(final Listener p0, final Executor p1);
    
    @Beta
    public abstract static class Listener
    {
        public void starting() {
        }
        
        public void running() {
        }
        
        public void stopping(final State state) {
        }
        
        public void terminated(final State state) {
        }
        
        public void failed(final State state, final Throwable t) {
        }
    }
    
    @Beta
    public enum State
    {
        NEW {
            @Override
            boolean isTerminal() {
                return false;
            }
        }, 
        STARTING {
            @Override
            boolean isTerminal() {
                return false;
            }
        }, 
        RUNNING {
            @Override
            boolean isTerminal() {
                return false;
            }
        }, 
        STOPPING {
            @Override
            boolean isTerminal() {
                return false;
            }
        }, 
        TERMINATED {
            @Override
            boolean isTerminal() {
                return true;
            }
        }, 
        FAILED {
            @Override
            boolean isTerminal() {
                return true;
            }
        };
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        abstract boolean isTerminal();
        
        State(final String s, final int n, final Service$1 object) {
            this(s, n);
        }
        
        static {
            $VALUES = new State[] { State.NEW, State.STARTING, State.RUNNING, State.STOPPING, State.TERMINATED, State.FAILED };
        }
    }
}
