package com.ibm.icu.impl;

import java.util.*;

public abstract class ICUNotifier
{
    private final Object notifyLock;
    private NotifyThread notifyThread;
    private List listeners;
    
    public ICUNotifier() {
        this.notifyLock = new Object();
    }
    
    public void addListener(final EventListener eventListener) {
        if (eventListener == null) {
            throw new NullPointerException();
        }
        if (this.acceptsListener(eventListener)) {
            // monitorenter(notifyLock = this.notifyLock)
            if (this.listeners == null) {
                this.listeners = new ArrayList();
            }
            else {
                final Iterator<EventListener> iterator = this.listeners.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() == eventListener) {
                        // monitorexit(notifyLock)
                        return;
                    }
                }
            }
            this.listeners.add(eventListener);
            // monitorexit(notifyLock)
            return;
        }
        throw new IllegalStateException("Listener invalid for this notifier.");
    }
    
    public void removeListener(final EventListener eventListener) {
        if (eventListener == null) {
            throw new NullPointerException();
        }
        // monitorenter(notifyLock = this.notifyLock)
        if (this.listeners != null) {
            final Iterator iterator = this.listeners.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() == eventListener) {
                    iterator.remove();
                    if (this.listeners.size() == 0) {
                        this.listeners = null;
                    }
                    // monitorexit(notifyLock)
                    return;
                }
            }
        }
    }
    // monitorexit(notifyLock)
    
    public void notifyChanged() {
        if (this.listeners != null) {
            // monitorenter(notifyLock = this.notifyLock)
            if (this.listeners != null) {
                if (this.notifyThread == null) {
                    (this.notifyThread = new NotifyThread(this)).setDaemon(true);
                    this.notifyThread.start();
                }
                this.notifyThread.queue(this.listeners.toArray(new EventListener[this.listeners.size()]));
            }
        }
        // monitorexit(notifyLock)
    }
    
    protected abstract boolean acceptsListener(final EventListener p0);
    
    protected abstract void notifyListener(final EventListener p0);
    
    private static class NotifyThread extends Thread
    {
        private final ICUNotifier notifier;
        private final List queue;
        
        NotifyThread(final ICUNotifier notifier) {
            this.queue = new ArrayList();
            this.notifier = notifier;
        }
        
        public void queue(final EventListener[] array) {
            // monitorenter(this)
            this.queue.add(array);
            this.notify();
        }
        // monitorexit(this)
        
        @Override
        public void run() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: dup            
            //     2: astore_2       
            //     3: monitorenter   
            //     4: aload_0        
            //     5: getfield        com/ibm/icu/impl/ICUNotifier$NotifyThread.queue:Ljava/util/List;
            //     8: invokeinterface java/util/List.isEmpty:()Z
            //    13: ifeq            23
            //    16: aload_0        
            //    17: invokevirtual   java/lang/Object.wait:()V
            //    20: goto            4
            //    23: aload_0        
            //    24: getfield        com/ibm/icu/impl/ICUNotifier$NotifyThread.queue:Ljava/util/List;
            //    27: iconst_0       
            //    28: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
            //    33: checkcast       [Ljava/util/EventListener;
            //    36: astore_1       
            //    37: aload_2        
            //    38: monitorexit    
            //    39: goto            47
            //    42: astore_3       
            //    43: aload_2        
            //    44: monitorexit    
            //    45: aload_3        
            //    46: athrow         
            //    47: iconst_0       
            //    48: aload_1        
            //    49: arraylength    
            //    50: if_icmpge       69
            //    53: aload_0        
            //    54: getfield        com/ibm/icu/impl/ICUNotifier$NotifyThread.notifier:Lcom/ibm/icu/impl/ICUNotifier;
            //    57: aload_1        
            //    58: iconst_0       
            //    59: aaload         
            //    60: invokevirtual   com/ibm/icu/impl/ICUNotifier.notifyListener:(Ljava/util/EventListener;)V
            //    63: iinc            2, 1
            //    66: goto            47
            //    69: goto            0
            //    72: astore_2       
            //    73: goto            0
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
    }
}
