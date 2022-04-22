package io.netty.util;

import java.util.concurrent.atomic.*;
import io.netty.util.concurrent.*;
import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;
import java.util.*;
import java.lang.ref.*;

public abstract class Recycler
{
    private static final InternalLogger logger;
    private static final AtomicInteger ID_GENERATOR;
    private static final int OWN_THREAD_ID;
    private static final int DEFAULT_MAX_CAPACITY;
    private static final int INITIAL_CAPACITY;
    private final int maxCapacity;
    private final FastThreadLocal threadLocal;
    private static final FastThreadLocal DELAYED_RECYCLED;
    
    protected Recycler() {
        this(Recycler.DEFAULT_MAX_CAPACITY);
    }
    
    protected Recycler(final int n) {
        this.threadLocal = new FastThreadLocal() {
            final Recycler this$0;
            
            @Override
            protected Stack initialValue() {
                return new Stack(this.this$0, Thread.currentThread(), Recycler.access$000(this.this$0));
            }
            
            @Override
            protected Object initialValue() throws Exception {
                return this.initialValue();
            }
        };
        this.maxCapacity = Math.max(0, n);
    }
    
    public final Object get() {
        final Stack stack = (Stack)this.threadLocal.get();
        DefaultHandle defaultHandle = stack.pop();
        if (defaultHandle == null) {
            defaultHandle = stack.newHandle();
            DefaultHandle.access$102(defaultHandle, this.newObject(defaultHandle));
        }
        return DefaultHandle.access$100(defaultHandle);
    }
    
    public final boolean recycle(final Object o, final Handle handle) {
        final DefaultHandle defaultHandle = (DefaultHandle)handle;
        if (DefaultHandle.access$200(defaultHandle).parent != this) {
            return false;
        }
        if (o != DefaultHandle.access$100(defaultHandle)) {
            throw new IllegalArgumentException("o does not belong to handle");
        }
        defaultHandle.recycle();
        return true;
    }
    
    protected abstract Object newObject(final Handle p0);
    
    static int access$000(final Recycler recycler) {
        return recycler.maxCapacity;
    }
    
    static FastThreadLocal access$300() {
        return Recycler.DELAYED_RECYCLED;
    }
    
    static AtomicInteger access$400() {
        return Recycler.ID_GENERATOR;
    }
    
    static int access$1400() {
        return Recycler.INITIAL_CAPACITY;
    }
    
    static int access$1700() {
        return Recycler.OWN_THREAD_ID;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(Recycler.class);
        ID_GENERATOR = new AtomicInteger(Integer.MIN_VALUE);
        OWN_THREAD_ID = Recycler.ID_GENERATOR.getAndIncrement();
        SystemPropertyUtil.getInt("io.netty.recycler.maxCapacity.default", 0);
        if (262144 <= 0) {}
        DEFAULT_MAX_CAPACITY = 262144;
        if (Recycler.logger.isDebugEnabled()) {
            Recycler.logger.debug("-Dio.netty.recycler.maxCapacity.default: {}", (Object)Recycler.DEFAULT_MAX_CAPACITY);
        }
        INITIAL_CAPACITY = Math.min(Recycler.DEFAULT_MAX_CAPACITY, 256);
        DELAYED_RECYCLED = new FastThreadLocal() {
            @Override
            protected Map initialValue() {
                return new WeakHashMap();
            }
            
            @Override
            protected Object initialValue() throws Exception {
                return this.initialValue();
            }
        };
    }
    
    static final class Stack
    {
        final Recycler parent;
        final Thread thread;
        private DefaultHandle[] elements;
        private final int maxCapacity;
        private int size;
        private WeakOrderQueue head;
        private WeakOrderQueue cursor;
        private WeakOrderQueue prev;
        
        Stack(final Recycler parent, final Thread thread, final int maxCapacity) {
            this.parent = parent;
            this.thread = thread;
            this.maxCapacity = maxCapacity;
            this.elements = new DefaultHandle[Recycler.access$1400()];
        }
        
        DefaultHandle pop() {
            int size = this.size;
            if (size == 0) {
                if (this != 0) {
                    return null;
                }
                size = this.size;
            }
            --size;
            final DefaultHandle defaultHandle = this.elements[size];
            if (DefaultHandle.access$700(defaultHandle) != DefaultHandle.access$1300(defaultHandle)) {
                throw new IllegalStateException("recycled multiple times");
            }
            DefaultHandle.access$1302(defaultHandle, 0);
            DefaultHandle.access$702(defaultHandle, 0);
            this.size = size;
            return defaultHandle;
        }
        
        void push(final DefaultHandle defaultHandle) {
            if ((DefaultHandle.access$1300(defaultHandle) | DefaultHandle.access$700(defaultHandle)) != 0x0) {
                throw new IllegalStateException("recycled already");
            }
            DefaultHandle.access$1302(defaultHandle, DefaultHandle.access$702(defaultHandle, Recycler.access$1700()));
            final int size = this.size;
            if (size == this.elements.length) {
                if (size == this.maxCapacity) {
                    return;
                }
                this.elements = Arrays.copyOf(this.elements, size << 1);
            }
            this.elements[size] = defaultHandle;
            this.size = size + 1;
        }
        
        DefaultHandle newHandle() {
            return new DefaultHandle(this);
        }
        
        static WeakOrderQueue access$600(final Stack stack) {
            return stack.head;
        }
        
        static WeakOrderQueue access$602(final Stack stack, final WeakOrderQueue head) {
            return stack.head = head;
        }
        
        static int access$1100(final Stack stack) {
            return stack.size;
        }
        
        static DefaultHandle[] access$1200(final Stack stack) {
            return stack.elements;
        }
        
        static DefaultHandle[] access$1202(final Stack stack, final DefaultHandle[] elements) {
            return stack.elements = elements;
        }
        
        static int access$1102(final Stack stack, final int size) {
            return stack.size = size;
        }
    }
    
    static final class DefaultHandle implements Handle
    {
        private int lastRecycledId;
        private int recycleId;
        private Stack stack;
        private Object value;
        
        DefaultHandle(final Stack stack) {
            this.stack = stack;
        }
        
        public void recycle() {
            final Thread currentThread = Thread.currentThread();
            if (currentThread == this.stack.thread) {
                this.stack.push(this);
                return;
            }
            final Map map = (Map)Recycler.access$300().get();
            WeakOrderQueue weakOrderQueue = map.get(this.stack);
            if (weakOrderQueue == null) {
                map.put(this.stack, weakOrderQueue = new WeakOrderQueue(this.stack, currentThread));
            }
            weakOrderQueue.add(this);
        }
        
        static Object access$102(final DefaultHandle defaultHandle, final Object value) {
            return defaultHandle.value = value;
        }
        
        static Object access$100(final DefaultHandle defaultHandle) {
            return defaultHandle.value;
        }
        
        static Stack access$200(final DefaultHandle defaultHandle) {
            return defaultHandle.stack;
        }
        
        static int access$702(final DefaultHandle defaultHandle, final int lastRecycledId) {
            return defaultHandle.lastRecycledId = lastRecycledId;
        }
        
        static Stack access$202(final DefaultHandle defaultHandle, final Stack stack) {
            return defaultHandle.stack = stack;
        }
        
        static int access$1300(final DefaultHandle defaultHandle) {
            return defaultHandle.recycleId;
        }
        
        static int access$1302(final DefaultHandle defaultHandle, final int recycleId) {
            return defaultHandle.recycleId = recycleId;
        }
        
        static int access$700(final DefaultHandle defaultHandle) {
            return defaultHandle.lastRecycledId;
        }
    }
    
    private static final class WeakOrderQueue
    {
        private static final int LINK_CAPACITY = 16;
        private Link head;
        private Link tail;
        private WeakOrderQueue next;
        private final WeakReference owner;
        private final int id;
        
        WeakOrderQueue(final Stack stack, final Thread thread) {
            this.id = Recycler.access$400().getAndIncrement();
            final Link link = new Link(null);
            this.tail = link;
            this.head = link;
            this.owner = new WeakReference((T)thread);
            // monitorenter(stack)
            this.next = Stack.access$600(stack);
            Stack.access$602(stack, this);
        }
        // monitorexit(stack)
        
        void add(final DefaultHandle defaultHandle) {
            DefaultHandle.access$702(defaultHandle, this.id);
            Link tail = this.tail;
            int n;
            if ((n = tail.get()) == 16) {
                tail = (this.tail = Link.access$802(tail, new Link(null)));
                n = tail.get();
            }
            DefaultHandle.access$202(Link.access$900(tail)[n] = defaultHandle, null);
            tail.lazySet(n + 1);
        }
        
        boolean hasFinalData() {
            return Link.access$1000(this.tail) != this.tail.get();
        }
        
        boolean transfer(final Stack stack) {
            Link head = this.head;
            if (head == null) {
                return false;
            }
            if (Link.access$1000(head) == 16) {
                if (Link.access$800(head) == null) {
                    return false;
                }
                head = (this.head = Link.access$800(head));
            }
            int i = Link.access$1000(head);
            final int value = head.get();
            if (i == value) {
                return false;
            }
            final int n = value - i;
            if (Stack.access$1100(stack) + n > Stack.access$1200(stack).length) {
                Stack.access$1202(stack, Arrays.copyOf(Stack.access$1200(stack), (Stack.access$1100(stack) + n) * 2));
            }
            final DefaultHandle[] access$900 = Link.access$900(head);
            final DefaultHandle[] access$901 = Stack.access$1200(stack);
            int access$902 = Stack.access$1100(stack);
            while (i < value) {
                final DefaultHandle defaultHandle = access$900[i];
                if (DefaultHandle.access$1300(defaultHandle) == 0) {
                    DefaultHandle.access$1302(defaultHandle, DefaultHandle.access$700(defaultHandle));
                }
                else if (DefaultHandle.access$1300(defaultHandle) != DefaultHandle.access$700(defaultHandle)) {
                    throw new IllegalStateException("recycled already");
                }
                DefaultHandle.access$202(defaultHandle, stack);
                access$901[access$902++] = defaultHandle;
                access$900[i++] = null;
            }
            Stack.access$1102(stack, access$902);
            if (value == 16 && Link.access$800(head) != null) {
                this.head = Link.access$800(head);
            }
            Link.access$1002(head, value);
            return true;
        }
        
        static WeakOrderQueue access$1500(final WeakOrderQueue weakOrderQueue) {
            return weakOrderQueue.next;
        }
        
        static WeakReference access$1600(final WeakOrderQueue weakOrderQueue) {
            return weakOrderQueue.owner;
        }
        
        static WeakOrderQueue access$1502(final WeakOrderQueue weakOrderQueue, final WeakOrderQueue next) {
            return weakOrderQueue.next = next;
        }
        
        private static final class Link extends AtomicInteger
        {
            private final DefaultHandle[] elements;
            private int readIndex;
            private Link next;
            
            private Link() {
                this.elements = new DefaultHandle[16];
            }
            
            Link(final Recycler$1 fastThreadLocal) {
                this();
            }
            
            static Link access$802(final Link link, final Link next) {
                return link.next = next;
            }
            
            static DefaultHandle[] access$900(final Link link) {
                return link.elements;
            }
            
            static int access$1000(final Link link) {
                return link.readIndex;
            }
            
            static Link access$800(final Link link) {
                return link.next;
            }
            
            static int access$1002(final Link link, final int readIndex) {
                return link.readIndex = readIndex;
            }
        }
    }
    
    public interface Handle
    {
    }
}
