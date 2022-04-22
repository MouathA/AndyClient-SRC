package org.apache.commons.lang3.concurrent;

import java.util.concurrent.*;
import java.util.*;

public class MultiBackgroundInitializer extends BackgroundInitializer
{
    private final Map childInitializers;
    
    public MultiBackgroundInitializer() {
        this.childInitializers = new HashMap();
    }
    
    public MultiBackgroundInitializer(final ExecutorService executorService) {
        super(executorService);
        this.childInitializers = new HashMap();
    }
    
    public void addInitializer(final String s, final BackgroundInitializer backgroundInitializer) {
        if (s == null) {
            throw new IllegalArgumentException("Name of child initializer must not be null!");
        }
        if (backgroundInitializer == null) {
            throw new IllegalArgumentException("Child initializer must not be null!");
        }
        // monitorenter(this)
        if (this.isStarted()) {
            throw new IllegalStateException("addInitializer() must not be called after start()!");
        }
        this.childInitializers.put(s, backgroundInitializer);
    }
    // monitorexit(this)
    
    @Override
    protected int getTaskCount() {
        final Iterator<BackgroundInitializer> iterator = this.childInitializers.values().iterator();
        while (iterator.hasNext()) {
            final int n = 1 + iterator.next().getTaskCount();
        }
        return 1;
    }
    
    @Override
    protected MultiBackgroundInitializerResults initialize() throws Exception {
        // monitorenter(this)
        final HashMap<Object, BackgroundInitializer> hashMap = new HashMap<Object, BackgroundInitializer>(this.childInitializers);
        // monitorexit(this)
        final ExecutorService activeExecutor = this.getActiveExecutor();
        for (final BackgroundInitializer backgroundInitializer : hashMap.values()) {
            if (backgroundInitializer.getExternalExecutor() == null) {
                backgroundInitializer.setExternalExecutor(activeExecutor);
            }
            backgroundInitializer.start();
        }
        final HashMap<Object, Object> hashMap2 = new HashMap<Object, Object>();
        final HashMap hashMap3 = new HashMap();
        for (final Map.Entry<Object, BackgroundInitializer> entry : hashMap.entrySet()) {
            hashMap2.put(entry.getKey(), entry.getValue().get());
        }
        return new MultiBackgroundInitializerResults(hashMap, hashMap2, hashMap3, null);
    }
    
    @Override
    protected Object initialize() throws Exception {
        return this.initialize();
    }
    
    public static class MultiBackgroundInitializerResults
    {
        private final Map initializers;
        private final Map resultObjects;
        private final Map exceptions;
        
        private MultiBackgroundInitializerResults(final Map initializers, final Map resultObjects, final Map exceptions) {
            this.initializers = initializers;
            this.resultObjects = resultObjects;
            this.exceptions = exceptions;
        }
        
        public BackgroundInitializer getInitializer(final String s) {
            return this.checkName(s);
        }
        
        public Object getResultObject(final String s) {
            this.checkName(s);
            return this.resultObjects.get(s);
        }
        
        public boolean isException(final String s) {
            this.checkName(s);
            return this.exceptions.containsKey(s);
        }
        
        public ConcurrentException getException(final String s) {
            this.checkName(s);
            return this.exceptions.get(s);
        }
        
        public Set initializerNames() {
            return Collections.unmodifiableSet(this.initializers.keySet());
        }
        
        public boolean isSuccessful() {
            return this.exceptions.isEmpty();
        }
        
        private BackgroundInitializer checkName(final String s) {
            final BackgroundInitializer backgroundInitializer = this.initializers.get(s);
            if (backgroundInitializer == null) {
                throw new NoSuchElementException("No child initializer with name " + s);
            }
            return backgroundInitializer;
        }
        
        MultiBackgroundInitializerResults(final Map map, final Map map2, final Map map3, final MultiBackgroundInitializer$1 object) {
            this(map, map2, map3);
        }
    }
}
