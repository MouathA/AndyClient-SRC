package io.netty.util;

import io.netty.util.internal.*;
import java.util.concurrent.atomic.*;

public class DefaultAttributeMap implements AttributeMap
{
    private static final AtomicReferenceFieldUpdater updater;
    private static final int BUCKET_SIZE = 4;
    private static final int MASK = 3;
    private AtomicReferenceArray attributes;
    
    @Override
    public Attribute attr(final AttributeKey attributeKey) {
        if (attributeKey == null) {
            throw new NullPointerException("key");
        }
        AtomicReferenceArray<DefaultAttribute> atomicReferenceArray = (AtomicReferenceArray<DefaultAttribute>)this.attributes;
        if (atomicReferenceArray == null) {
            atomicReferenceArray = new AtomicReferenceArray<DefaultAttribute>(4);
            if (!DefaultAttributeMap.updater.compareAndSet(this, null, atomicReferenceArray)) {
                atomicReferenceArray = (AtomicReferenceArray<DefaultAttribute>)this.attributes;
            }
        }
        final int index = index(attributeKey);
        DefaultAttribute defaultAttribute = atomicReferenceArray.get(index);
        if (defaultAttribute == null) {
            final DefaultAttribute defaultAttribute2 = new DefaultAttribute(attributeKey);
            if (atomicReferenceArray.compareAndSet(index, null, defaultAttribute2)) {
                return defaultAttribute2;
            }
            defaultAttribute = atomicReferenceArray.get(index);
        }
        // monitorenter(defaultAttribute3 = defaultAttribute)
        DefaultAttribute defaultAttribute4;
        DefaultAttribute access$200;
        for (defaultAttribute4 = defaultAttribute; DefaultAttribute.access$000(defaultAttribute4) || DefaultAttribute.access$100(defaultAttribute4) != attributeKey; defaultAttribute4 = access$200) {
            access$200 = DefaultAttribute.access$200(defaultAttribute4);
            if (access$200 == null) {
                final DefaultAttribute defaultAttribute5 = new DefaultAttribute(defaultAttribute, attributeKey);
                DefaultAttribute.access$202(defaultAttribute4, defaultAttribute5);
                DefaultAttribute.access$302(defaultAttribute5, defaultAttribute4);
                // monitorexit(defaultAttribute3)
                return defaultAttribute5;
            }
        }
        // monitorexit(defaultAttribute3)
        return defaultAttribute4;
    }
    
    private static int index(final AttributeKey attributeKey) {
        return attributeKey.id() & 0x3;
    }
    
    static {
        AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray> updater2 = (AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray>)PlatformDependent.newAtomicReferenceFieldUpdater(DefaultAttributeMap.class, "attributes");
        if (updater2 == null) {
            updater2 = AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, AtomicReferenceArray.class, "attributes");
        }
        updater = updater2;
    }
    
    private static final class DefaultAttribute extends AtomicReference implements Attribute
    {
        private static final long serialVersionUID = -2661411462200283011L;
        private final DefaultAttribute head;
        private final AttributeKey key;
        private DefaultAttribute prev;
        private DefaultAttribute next;
        private boolean removed;
        
        DefaultAttribute(final DefaultAttribute head, final AttributeKey key) {
            this.head = head;
            this.key = key;
        }
        
        DefaultAttribute(final AttributeKey key) {
            this.head = this;
            this.key = key;
        }
        
        @Override
        public AttributeKey key() {
            return this.key;
        }
        
        @Override
        public Object setIfAbsent(final Object o) {
            while (!this.compareAndSet(null, o)) {
                final Object value = this.get();
                if (value != null) {
                    return value;
                }
            }
            return null;
        }
        
        @Override
        public Object getAndRemove() {
            this.removed = true;
            final Object andSet = this.getAndSet(null);
            this.remove0();
            return andSet;
        }
        
        @Override
        public void remove() {
            this.removed = true;
            this.set(null);
            this.remove0();
        }
        
        private void remove0() {
            // monitorenter(head = this.head)
            if (this.prev != null) {
                this.prev.next = this.next;
                if (this.next != null) {
                    this.next.prev = this.prev;
                }
            }
        }
        // monitorexit(head)
        
        static boolean access$000(final DefaultAttribute defaultAttribute) {
            return defaultAttribute.removed;
        }
        
        static AttributeKey access$100(final DefaultAttribute defaultAttribute) {
            return defaultAttribute.key;
        }
        
        static DefaultAttribute access$200(final DefaultAttribute defaultAttribute) {
            return defaultAttribute.next;
        }
        
        static DefaultAttribute access$202(final DefaultAttribute defaultAttribute, final DefaultAttribute next) {
            return defaultAttribute.next = next;
        }
        
        static DefaultAttribute access$302(final DefaultAttribute defaultAttribute, final DefaultAttribute prev) {
            return defaultAttribute.prev = prev;
        }
    }
}
