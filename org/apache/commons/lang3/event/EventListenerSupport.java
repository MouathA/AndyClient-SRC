package org.apache.commons.lang3.event;

import org.apache.commons.lang3.*;
import java.util.concurrent.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;

public class EventListenerSupport implements Serializable
{
    private static final long serialVersionUID = 3593265990380473632L;
    private List listeners;
    private transient Object proxy;
    private transient Object[] prototypeArray;
    
    public static EventListenerSupport create(final Class clazz) {
        return new EventListenerSupport(clazz);
    }
    
    public EventListenerSupport(final Class clazz) {
        this(clazz, Thread.currentThread().getContextClassLoader());
    }
    
    public EventListenerSupport(final Class clazz, final ClassLoader classLoader) {
        this();
        Validate.notNull(clazz, "Listener interface cannot be null.", new Object[0]);
        Validate.notNull(classLoader, "ClassLoader cannot be null.", new Object[0]);
        Validate.isTrue(clazz.isInterface(), "Class {0} is not an interface", clazz.getName());
        this.initializeTransientFields(clazz, classLoader);
    }
    
    private EventListenerSupport() {
        this.listeners = new CopyOnWriteArrayList();
    }
    
    public Object fire() {
        return this.proxy;
    }
    
    public void addListener(final Object o) {
        Validate.notNull(o, "Listener object cannot be null.", new Object[0]);
        this.listeners.add(o);
    }
    
    int getListenerCount() {
        return this.listeners.size();
    }
    
    public void removeListener(final Object o) {
        Validate.notNull(o, "Listener object cannot be null.", new Object[0]);
        this.listeners.remove(o);
    }
    
    public Object[] getListeners() {
        return this.listeners.toArray(this.prototypeArray);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final ArrayList<Object> list = new ArrayList<Object>();
        final ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new ByteArrayOutputStream());
        for (final Object next : this.listeners) {
            objectOutputStream2.writeObject(next);
            list.add(next);
        }
        objectOutputStream.writeObject(list.toArray(this.prototypeArray));
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final Object[] array = (Object[])objectInputStream.readObject();
        this.listeners = new CopyOnWriteArrayList(array);
        this.initializeTransientFields(array.getClass().getComponentType(), Thread.currentThread().getContextClassLoader());
    }
    
    private void initializeTransientFields(final Class clazz, final ClassLoader classLoader) {
        this.prototypeArray = (Object[])Array.newInstance(clazz, 0);
        this.createProxy(clazz, classLoader);
    }
    
    private void createProxy(final Class clazz, final ClassLoader classLoader) {
        this.proxy = clazz.cast(Proxy.newProxyInstance(classLoader, new Class[] { clazz }, this.createInvocationHandler()));
    }
    
    protected InvocationHandler createInvocationHandler() {
        return new ProxyInvocationHandler();
    }
    
    static List access$000(final EventListenerSupport eventListenerSupport) {
        return eventListenerSupport.listeners;
    }
    
    protected class ProxyInvocationHandler implements InvocationHandler
    {
        final EventListenerSupport this$0;
        
        protected ProxyInvocationHandler(final EventListenerSupport this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object invoke(final Object o, final Method method, final Object[] array) throws Throwable {
            final Iterator<Object> iterator = EventListenerSupport.access$000(this.this$0).iterator();
            while (iterator.hasNext()) {
                method.invoke(iterator.next(), array);
            }
            return null;
        }
    }
}
