package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.io.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
public class LinkedListMultimap extends AbstractMultimap implements ListMultimap, Serializable
{
    private transient Node head;
    private transient Node tail;
    private transient Map keyToKeyList;
    private transient int size;
    private transient int modCount;
    @GwtIncompatible("java serialization not supported")
    private static final long serialVersionUID = 0L;
    
    public static LinkedListMultimap create() {
        return new LinkedListMultimap();
    }
    
    public static LinkedListMultimap create(final int n) {
        return new LinkedListMultimap(n);
    }
    
    public static LinkedListMultimap create(final Multimap multimap) {
        return new LinkedListMultimap(multimap);
    }
    
    LinkedListMultimap() {
        this.keyToKeyList = Maps.newHashMap();
    }
    
    private LinkedListMultimap(final int n) {
        this.keyToKeyList = new HashMap(n);
    }
    
    private LinkedListMultimap(final Multimap multimap) {
        this(multimap.keySet().size());
        this.putAll(multimap);
    }
    
    private Node addNode(@Nullable final Object o, @Nullable final Object o2, @Nullable final Node node) {
        final Node node2 = new Node(o, o2);
        if (this.head == null) {
            final Node node3 = node2;
            this.tail = node3;
            this.head = node3;
            this.keyToKeyList.put(o, new KeyList(node2));
            ++this.modCount;
        }
        else if (node == null) {
            this.tail.next = node2;
            node2.previous = this.tail;
            this.tail = node2;
            final KeyList list = this.keyToKeyList.get(o);
            if (list == null) {
                this.keyToKeyList.put(o, new KeyList(node2));
                ++this.modCount;
            }
            else {
                final KeyList list2 = list;
                ++list2.count;
                final Node tail = list.tail;
                tail.nextSibling = node2;
                node2.previousSibling = tail;
                list.tail = node2;
            }
        }
        else {
            final KeyList list3 = this.keyToKeyList.get(o);
            ++list3.count;
            node2.previous = node.previous;
            node2.previousSibling = node.previousSibling;
            node2.next = node;
            node2.nextSibling = node;
            if (node.previousSibling == null) {
                this.keyToKeyList.get(o).head = node2;
            }
            else {
                node.previousSibling.nextSibling = node2;
            }
            if (node.previous == null) {
                this.head = node2;
            }
            else {
                node.previous.next = node2;
            }
            node.previous = node2;
            node.previousSibling = node2;
        }
        ++this.size;
        return node2;
    }
    
    private void removeNode(final Node node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        }
        else {
            this.head = node.next;
        }
        if (node.next != null) {
            node.next.previous = node.previous;
        }
        else {
            this.tail = node.previous;
        }
        if (node.previousSibling == null && node.nextSibling == null) {
            this.keyToKeyList.remove(node.key).count = 0;
            ++this.modCount;
        }
        else {
            final KeyList list2;
            final KeyList list = list2 = this.keyToKeyList.get(node.key);
            --list2.count;
            if (node.previousSibling == null) {
                list.head = node.nextSibling;
            }
            else {
                node.previousSibling.nextSibling = node.nextSibling;
            }
            if (node.nextSibling == null) {
                list.tail = node.previousSibling;
            }
            else {
                node.nextSibling.previousSibling = node.previousSibling;
            }
        }
        --this.size;
    }
    
    private void removeAllNodes(@Nullable final Object o) {
        Iterators.clear(new ValueForKeyIterator(o));
    }
    
    private static void checkElement(@Nullable final Object o) {
        if (o == null) {
            throw new NoSuchElementException();
        }
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean isEmpty() {
        return this.head == null;
    }
    
    @Override
    public boolean containsKey(@Nullable final Object o) {
        return this.keyToKeyList.containsKey(o);
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        return this.values().contains(o);
    }
    
    @Override
    public boolean put(@Nullable final Object o, @Nullable final Object o2) {
        this.addNode(o, o2, null);
        return true;
    }
    
    @Override
    public List replaceValues(@Nullable final Object o, final Iterable iterable) {
        final List copy = this.getCopy(o);
        final ValueForKeyIterator valueForKeyIterator = new ValueForKeyIterator(o);
        final Iterator<Object> iterator = iterable.iterator();
        while (valueForKeyIterator.hasNext() && iterator.hasNext()) {
            valueForKeyIterator.next();
            valueForKeyIterator.set(iterator.next());
        }
        while (valueForKeyIterator.hasNext()) {
            valueForKeyIterator.next();
            valueForKeyIterator.remove();
        }
        while (iterator.hasNext()) {
            valueForKeyIterator.add(iterator.next());
        }
        return copy;
    }
    
    private List getCopy(@Nullable final Object o) {
        return Collections.unmodifiableList((List<?>)Lists.newArrayList(new ValueForKeyIterator(o)));
    }
    
    @Override
    public List removeAll(@Nullable final Object o) {
        final List copy = this.getCopy(o);
        this.removeAllNodes(o);
        return copy;
    }
    
    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.keyToKeyList.clear();
        this.size = 0;
        ++this.modCount;
    }
    
    @Override
    public List get(@Nullable final Object o) {
        return new AbstractSequentialList(o) {
            final Object val$key;
            final LinkedListMultimap this$0;
            
            @Override
            public int size() {
                final KeyList list = LinkedListMultimap.access$600(this.this$0).get(this.val$key);
                return (list == null) ? 0 : list.count;
            }
            
            @Override
            public ListIterator listIterator(final int n) {
                return this.this$0.new ValueForKeyIterator(this.val$key, n);
            }
        };
    }
    
    @Override
    Set createKeySet() {
        return new Sets.ImprovedAbstractSet() {
            final LinkedListMultimap this$0;
            
            @Override
            public int size() {
                return LinkedListMultimap.access$600(this.this$0).size();
            }
            
            @Override
            public Iterator iterator() {
                return this.this$0.new DistinctKeyIterator(null);
            }
            
            @Override
            public boolean contains(final Object o) {
                return this.this$0.containsKey(o);
            }
            
            @Override
            public boolean remove(final Object o) {
                return !this.this$0.removeAll(o).isEmpty();
            }
        };
    }
    
    @Override
    public List values() {
        return (List)super.values();
    }
    
    @Override
    List createValues() {
        return new AbstractSequentialList() {
            final LinkedListMultimap this$0;
            
            @Override
            public int size() {
                return LinkedListMultimap.access$900(this.this$0);
            }
            
            @Override
            public ListIterator listIterator(final int n) {
                final NodeIterator nodeIterator = this.this$0.new NodeIterator(n);
                return new TransformedListIterator((ListIterator)nodeIterator, nodeIterator) {
                    final NodeIterator val$nodeItr;
                    final LinkedListMultimap$3 this$1;
                    
                    Object transform(final Map.Entry entry) {
                        return entry.getValue();
                    }
                    
                    @Override
                    public void set(final Object value) {
                        this.val$nodeItr.setValue(value);
                    }
                    
                    @Override
                    Object transform(final Object o) {
                        return this.transform((Map.Entry)o);
                    }
                };
            }
        };
    }
    
    @Override
    public List entries() {
        return (List)super.entries();
    }
    
    @Override
    List createEntries() {
        return new AbstractSequentialList() {
            final LinkedListMultimap this$0;
            
            @Override
            public int size() {
                return LinkedListMultimap.access$900(this.this$0);
            }
            
            @Override
            public ListIterator listIterator(final int n) {
                return this.this$0.new NodeIterator(n);
            }
        };
    }
    
    @Override
    Iterator entryIterator() {
        throw new AssertionError((Object)"should never be called");
    }
    
    @Override
    Map createAsMap() {
        return new Multimaps.AsMap(this);
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size());
        for (final Map.Entry<Object, V> entry : this.entries()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.keyToKeyList = Maps.newLinkedHashMap();
        while (0 < objectInputStream.readInt()) {
            this.put(objectInputStream.readObject(), objectInputStream.readObject());
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public Map asMap() {
        return super.asMap();
    }
    
    @Override
    Collection createValues() {
        return this.createValues();
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    public Multiset keys() {
        return super.keys();
    }
    
    @Override
    public Set keySet() {
        return super.keySet();
    }
    
    @Override
    Collection createEntries() {
        return this.createEntries();
    }
    
    @Override
    public Collection entries() {
        return this.entries();
    }
    
    @Override
    public Collection replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    public boolean putAll(final Multimap multimap) {
        return super.putAll(multimap);
    }
    
    @Override
    public boolean putAll(final Object o, final Iterable iterable) {
        return super.putAll(o, iterable);
    }
    
    @Override
    public boolean remove(final Object o, final Object o2) {
        return super.remove(o, o2);
    }
    
    @Override
    public boolean containsEntry(final Object o, final Object o2) {
        return super.containsEntry(o, o2);
    }
    
    @Override
    public Collection get(final Object o) {
        return this.get(o);
    }
    
    @Override
    public Collection removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    static int access$000(final LinkedListMultimap linkedListMultimap) {
        return linkedListMultimap.modCount;
    }
    
    static Node access$100(final LinkedListMultimap linkedListMultimap) {
        return linkedListMultimap.tail;
    }
    
    static Node access$200(final LinkedListMultimap linkedListMultimap) {
        return linkedListMultimap.head;
    }
    
    static void access$300(final Object o) {
        checkElement(o);
    }
    
    static void access$400(final LinkedListMultimap linkedListMultimap, final Node node) {
        linkedListMultimap.removeNode(node);
    }
    
    static void access$500(final LinkedListMultimap linkedListMultimap, final Object o) {
        linkedListMultimap.removeAllNodes(o);
    }
    
    static Map access$600(final LinkedListMultimap linkedListMultimap) {
        return linkedListMultimap.keyToKeyList;
    }
    
    static Node access$700(final LinkedListMultimap linkedListMultimap, final Object o, final Object o2, final Node node) {
        return linkedListMultimap.addNode(o, o2, node);
    }
    
    static int access$900(final LinkedListMultimap linkedListMultimap) {
        return linkedListMultimap.size;
    }
    
    private class ValueForKeyIterator implements ListIterator
    {
        final Object key;
        int nextIndex;
        Node next;
        Node current;
        Node previous;
        final LinkedListMultimap this$0;
        
        ValueForKeyIterator(@Nullable final LinkedListMultimap this$0, final Object key) {
            this.this$0 = this$0;
            this.key = key;
            final KeyList list = LinkedListMultimap.access$600(this$0).get(key);
            this.next = ((list == null) ? null : list.head);
        }
        
        public ValueForKeyIterator(@Nullable final LinkedListMultimap this$0, final Object key, int n) {
            this.this$0 = this$0;
            final KeyList list = LinkedListMultimap.access$600(this$0).get(key);
            final int nextIndex = (list == null) ? 0 : list.count;
            Preconditions.checkPositionIndex(n, nextIndex);
            if (n >= nextIndex / 2) {
                this.previous = ((list == null) ? null : list.tail);
                this.nextIndex = nextIndex;
                while (n++ < nextIndex) {
                    this.previous();
                }
            }
            else {
                this.next = ((list == null) ? null : list.head);
                while (n-- > 0) {
                    this.next();
                }
            }
            this.key = key;
            this.current = null;
        }
        
        @Override
        public boolean hasNext() {
            return this.next != null;
        }
        
        @Override
        public Object next() {
            LinkedListMultimap.access$300(this.next);
            final Node next = this.next;
            this.current = next;
            this.previous = next;
            this.next = this.next.nextSibling;
            ++this.nextIndex;
            return this.current.value;
        }
        
        @Override
        public boolean hasPrevious() {
            return this.previous != null;
        }
        
        @Override
        public Object previous() {
            LinkedListMultimap.access$300(this.previous);
            final Node previous = this.previous;
            this.current = previous;
            this.next = previous;
            this.previous = this.previous.previousSibling;
            --this.nextIndex;
            return this.current.value;
        }
        
        @Override
        public int nextIndex() {
            return this.nextIndex;
        }
        
        @Override
        public int previousIndex() {
            return this.nextIndex - 1;
        }
        
        @Override
        public void remove() {
            CollectPreconditions.checkRemove(this.current != null);
            if (this.current != this.next) {
                this.previous = this.current.previousSibling;
                --this.nextIndex;
            }
            else {
                this.next = this.current.nextSibling;
            }
            LinkedListMultimap.access$400(this.this$0, this.current);
            this.current = null;
        }
        
        @Override
        public void set(final Object value) {
            Preconditions.checkState(this.current != null);
            this.current.value = value;
        }
        
        @Override
        public void add(final Object o) {
            this.previous = LinkedListMultimap.access$700(this.this$0, this.key, o, this.next);
            ++this.nextIndex;
            this.current = null;
        }
    }
    
    private static final class Node extends AbstractMapEntry
    {
        final Object key;
        Object value;
        Node next;
        Node previous;
        Node nextSibling;
        Node previousSibling;
        
        Node(@Nullable final Object key, @Nullable final Object value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public Object getKey() {
            return this.key;
        }
        
        @Override
        public Object getValue() {
            return this.value;
        }
        
        @Override
        public Object setValue(@Nullable final Object value) {
            final Object value2 = this.value;
            this.value = value;
            return value2;
        }
    }
    
    private static class KeyList
    {
        Node head;
        Node tail;
        int count;
        
        KeyList(final Node node) {
            this.head = node;
            this.tail = node;
            node.previousSibling = null;
            node.nextSibling = null;
            this.count = 1;
        }
    }
    
    private class DistinctKeyIterator implements Iterator
    {
        final Set seenKeys;
        Node next;
        Node current;
        int expectedModCount;
        final LinkedListMultimap this$0;
        
        private DistinctKeyIterator(final LinkedListMultimap this$0) {
            this.this$0 = this$0;
            this.seenKeys = Sets.newHashSetWithExpectedSize(this.this$0.keySet().size());
            this.next = LinkedListMultimap.access$200(this.this$0);
            this.expectedModCount = LinkedListMultimap.access$000(this.this$0);
        }
        
        private void checkForConcurrentModification() {
            if (LinkedListMultimap.access$000(this.this$0) != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
        
        @Override
        public boolean hasNext() {
            this.checkForConcurrentModification();
            return this.next != null;
        }
        
        @Override
        public Object next() {
            this.checkForConcurrentModification();
            LinkedListMultimap.access$300(this.next);
            this.current = this.next;
            this.seenKeys.add(this.current.key);
            do {
                this.next = this.next.next;
            } while (this.next != null && !this.seenKeys.add(this.next.key));
            return this.current.key;
        }
        
        @Override
        public void remove() {
            this.checkForConcurrentModification();
            CollectPreconditions.checkRemove(this.current != null);
            LinkedListMultimap.access$500(this.this$0, this.current.key);
            this.current = null;
            this.expectedModCount = LinkedListMultimap.access$000(this.this$0);
        }
        
        DistinctKeyIterator(final LinkedListMultimap linkedListMultimap, final LinkedListMultimap$1 abstractSequentialList) {
            this(linkedListMultimap);
        }
    }
    
    private class NodeIterator implements ListIterator
    {
        int nextIndex;
        Node next;
        Node current;
        Node previous;
        int expectedModCount;
        final LinkedListMultimap this$0;
        
        NodeIterator(final LinkedListMultimap this$0, int n) {
            this.this$0 = this$0;
            this.expectedModCount = LinkedListMultimap.access$000(this.this$0);
            final int size = this$0.size();
            Preconditions.checkPositionIndex(n, size);
            if (n >= size / 2) {
                this.previous = LinkedListMultimap.access$100(this$0);
                this.nextIndex = size;
                while (n++ < size) {
                    this.previous();
                }
            }
            else {
                this.next = LinkedListMultimap.access$200(this$0);
                while (n-- > 0) {
                    this.next();
                }
            }
            this.current = null;
        }
        
        private void checkForConcurrentModification() {
            if (LinkedListMultimap.access$000(this.this$0) != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
        
        @Override
        public boolean hasNext() {
            this.checkForConcurrentModification();
            return this.next != null;
        }
        
        @Override
        public Node next() {
            this.checkForConcurrentModification();
            LinkedListMultimap.access$300(this.next);
            final Node next = this.next;
            this.current = next;
            this.previous = next;
            this.next = this.next.next;
            ++this.nextIndex;
            return this.current;
        }
        
        @Override
        public void remove() {
            this.checkForConcurrentModification();
            CollectPreconditions.checkRemove(this.current != null);
            if (this.current != this.next) {
                this.previous = this.current.previous;
                --this.nextIndex;
            }
            else {
                this.next = this.current.next;
            }
            LinkedListMultimap.access$400(this.this$0, this.current);
            this.current = null;
            this.expectedModCount = LinkedListMultimap.access$000(this.this$0);
        }
        
        @Override
        public boolean hasPrevious() {
            this.checkForConcurrentModification();
            return this.previous != null;
        }
        
        @Override
        public Node previous() {
            this.checkForConcurrentModification();
            LinkedListMultimap.access$300(this.previous);
            final Node previous = this.previous;
            this.current = previous;
            this.next = previous;
            this.previous = this.previous.previous;
            --this.nextIndex;
            return this.current;
        }
        
        @Override
        public int nextIndex() {
            return this.nextIndex;
        }
        
        @Override
        public int previousIndex() {
            return this.nextIndex - 1;
        }
        
        public void set(final Map.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        public void add(final Map.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        void setValue(final Object value) {
            Preconditions.checkState(this.current != null);
            this.current.value = value;
        }
        
        @Override
        public void add(final Object o) {
            this.add((Map.Entry)o);
        }
        
        @Override
        public void set(final Object o) {
            this.set((Map.Entry)o);
        }
        
        @Override
        public Object previous() {
            return this.previous();
        }
        
        @Override
        public Object next() {
            return this.next();
        }
    }
}
