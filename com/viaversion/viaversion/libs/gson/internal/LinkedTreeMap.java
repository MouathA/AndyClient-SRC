package com.viaversion.viaversion.libs.gson.internal;

import java.io.*;
import java.util.*;

public final class LinkedTreeMap extends AbstractMap implements Serializable
{
    private static final Comparator NATURAL_ORDER;
    Comparator comparator;
    Node root;
    int size;
    int modCount;
    final Node header;
    private EntrySet entrySet;
    private KeySet keySet;
    static final boolean $assertionsDisabled;
    
    public LinkedTreeMap() {
        this(LinkedTreeMap.NATURAL_ORDER);
    }
    
    public LinkedTreeMap(final Comparator comparator) {
        this.size = 0;
        this.modCount = 0;
        this.header = new Node();
        this.comparator = ((comparator != null) ? comparator : LinkedTreeMap.NATURAL_ORDER);
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public Object get(final Object o) {
        final Node byObject = this.findByObject(o);
        return (byObject != null) ? byObject.value : null;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.findByObject(o) != null;
    }
    
    @Override
    public Object put(final Object o, final Object value) {
        if (o == null) {
            throw new NullPointerException("key == null");
        }
        final Node find = this.find(o, true);
        final Object value2 = find.value;
        find.value = value;
        return value2;
    }
    
    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
        ++this.modCount;
        final Node header;
        final Node node2;
        final Node node = node2 = (header = this.header);
        node.prev = node2;
        header.next = node2;
    }
    
    @Override
    public Object remove(final Object o) {
        final Node removeInternalByKey = this.removeInternalByKey(o);
        return (removeInternalByKey != null) ? removeInternalByKey.value : null;
    }
    
    Node find(final Object o, final boolean b) {
        final Comparator comparator = this.comparator;
        Node root = this.root;
        if (root != null) {
            final Comparable comparable = (comparator == LinkedTreeMap.NATURAL_ORDER) ? ((Comparable)o) : null;
            while (true) {
                final int n = (comparable != null) ? comparable.compareTo(root.key) : comparator.compare(o, root.key);
                if (!false) {
                    return root;
                }
                final Node node = (0 < 0) ? root.left : root.right;
                if (node == null) {
                    break;
                }
                root = node;
            }
        }
        if (!b) {
            return null;
        }
        final Node header = this.header;
        Node right;
        if (root == null) {
            if (comparator == LinkedTreeMap.NATURAL_ORDER && !(o instanceof Comparable)) {
                throw new ClassCastException(o.getClass().getName() + " is not Comparable");
            }
            right = new Node(root, o, header, header.prev);
            this.root = right;
        }
        else {
            right = new Node(root, o, header, header.prev);
            if (0 < 0) {
                root.left = right;
            }
            else {
                root.right = right;
            }
            this.rebalance(root, true);
        }
        ++this.size;
        ++this.modCount;
        return right;
    }
    
    Node findByObject(final Object o) {
        return (o != null) ? this.find(o, false) : null;
    }
    
    Node findByEntry(final Map.Entry entry) {
        final Node byObject = this.findByObject(entry.getKey());
        return (byObject != null && this.equal(byObject.value, entry.getValue())) ? byObject : null;
    }
    
    private boolean equal(final Object o, final Object o2) {
        return o == o2 || (o != null && o.equals(o2));
    }
    
    void removeInternal(final Node node, final boolean b) {
        if (b) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        final Node left = node.left;
        final Node right = node.right;
        final Node parent = node.parent;
        if (left != null && right != null) {
            final Node node2 = (left.height > right.height) ? left.last() : right.first();
            this.removeInternal(node2, false);
            final Node left2 = node.left;
            if (left2 != null) {
                final int height = left2.height;
                node2.left = left2;
                left2.parent = node2;
                node.left = null;
            }
            final Node right2 = node.right;
            if (right2 != null) {
                final int height2 = right2.height;
                node2.right = right2;
                right2.parent = node2;
                node.right = null;
            }
            node2.height = Math.max(0, 0) + 1;
            this.replaceInParent(node, node2);
            return;
        }
        if (left != null) {
            this.replaceInParent(node, left);
            node.left = null;
        }
        else if (right != null) {
            this.replaceInParent(node, right);
            node.right = null;
        }
        else {
            this.replaceInParent(node, null);
        }
        this.rebalance(parent, false);
        --this.size;
        ++this.modCount;
    }
    
    Node removeInternalByKey(final Object o) {
        final Node byObject = this.findByObject(o);
        if (byObject != null) {
            this.removeInternal(byObject, true);
        }
        return byObject;
    }
    
    private void replaceInParent(final Node node, final Node root) {
        final Node parent = node.parent;
        node.parent = null;
        if (root != null) {
            root.parent = parent;
        }
        if (parent != null) {
            if (parent.left == node) {
                parent.left = root;
            }
            else {
                assert parent.right == node;
                parent.right = root;
            }
        }
        else {
            this.root = root;
        }
    }
    
    private void rebalance(final Node node, final boolean b) {
        for (Node parent = node; parent != null; parent = parent.parent) {
            final Node left = parent.left;
            final Node right = parent.right;
            final int n = (left != null) ? left.height : 0;
            final int n2 = (right != null) ? right.height : 0;
            final int n3 = n - n2;
            if (n3 == -2) {
                final Node left2 = right.left;
                final Node right2 = right.right;
                final int n4 = ((left2 != null) ? left2.height : 0) - ((right2 != null) ? right2.height : 0);
                if (n4 == -1 || (n4 == 0 && !b)) {
                    this.rotateLeft(parent);
                }
                else {
                    assert n4 == 1;
                    this.rotateRight(right);
                    this.rotateLeft(parent);
                }
                if (b) {
                    break;
                }
            }
            else if (n3 == 2) {
                final Node left3 = left.left;
                final Node right3 = left.right;
                final int n5 = ((left3 != null) ? left3.height : 0) - ((right3 != null) ? right3.height : 0);
                if (n5 == 1 || (n5 == 0 && !b)) {
                    this.rotateRight(parent);
                }
                else {
                    assert n5 == -1;
                    this.rotateLeft(left);
                    this.rotateRight(parent);
                }
                if (b) {
                    break;
                }
            }
            else if (n3 == 0) {
                parent.height = n + 1;
                if (b) {
                    break;
                }
            }
            else {
                assert n3 == 1;
                parent.height = Math.max(n, n2) + 1;
                if (!b) {
                    break;
                }
            }
        }
    }
    
    private void rotateLeft(final Node node) {
        final Node left = node.left;
        final Node right = node.right;
        final Node left2 = right.left;
        final Node right2 = right.right;
        node.right = left2;
        if (left2 != null) {
            left2.parent = node;
        }
        this.replaceInParent(node, right);
        right.left = node;
        node.parent = right;
        node.height = Math.max((left != null) ? left.height : 0, (left2 != null) ? left2.height : 0) + 1;
        right.height = Math.max(node.height, (right2 != null) ? right2.height : 0) + 1;
    }
    
    private void rotateRight(final Node node) {
        final Node left = node.left;
        final Node right = node.right;
        final Node left2 = left.left;
        final Node right2 = left.right;
        node.left = right2;
        if (right2 != null) {
            right2.parent = node;
        }
        this.replaceInParent(node, left);
        left.right = node;
        node.parent = left;
        node.height = Math.max((right != null) ? right.height : 0, (right2 != null) ? right2.height : 0) + 1;
        left.height = Math.max(node.height, (left2 != null) ? left2.height : 0) + 1;
    }
    
    @Override
    public Set entrySet() {
        final EntrySet entrySet = this.entrySet;
        return (entrySet != null) ? entrySet : (this.entrySet = new EntrySet());
    }
    
    @Override
    public Set keySet() {
        final KeySet keySet = this.keySet;
        return (keySet != null) ? keySet : (this.keySet = new KeySet());
    }
    
    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }
    
    static {
        $assertionsDisabled = !LinkedTreeMap.class.desiredAssertionStatus();
        NATURAL_ORDER = new Comparator() {
            public int compare(final Comparable comparable, final Comparable comparable2) {
                return comparable.compareTo(comparable2);
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((Comparable)o, (Comparable)o2);
            }
        };
    }
    
    final class KeySet extends AbstractSet
    {
        final LinkedTreeMap this$0;
        
        KeySet(final LinkedTreeMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public int size() {
            return this.this$0.size;
        }
        
        @Override
        public Iterator iterator() {
            return new LinkedTreeMapIterator() {
                final KeySet this$1;
                
                @Override
                public Object next() {
                    return this.nextNode().key;
                }
            };
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.this$0.containsKey(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            return this.this$0.removeInternalByKey(o) != null;
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
    }
    
    static final class Node implements Map.Entry
    {
        Node parent;
        Node left;
        Node right;
        Node next;
        Node prev;
        final Object key;
        Object value;
        int height;
        
        Node() {
            this.key = null;
            this.prev = this;
            this.next = this;
        }
        
        Node(final Node parent, final Object key, final Node next, final Node prev) {
            this.parent = parent;
            this.key = key;
            this.height = 1;
            this.next = next;
            this.prev = prev;
            prev.next = this;
            next.prev = this;
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
        public Object setValue(final Object value) {
            final Object value2 = this.value;
            this.value = value;
            return value2;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry entry = (Map.Entry)o;
                if (this.key == null) {
                    if (entry.getKey() != null) {
                        return false;
                    }
                }
                else if (!this.key.equals(entry.getKey())) {
                    return false;
                }
                if ((this.value != null) ? this.value.equals(entry.getValue()) : (entry.getValue() == null)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value.hashCode());
        }
        
        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
        
        public Node first() {
            Node node = this;
            for (Node node2 = node.left; node2 != null; node2 = node.left) {
                node = node2;
            }
            return node;
        }
        
        public Node last() {
            Node node = this;
            for (Node node2 = node.right; node2 != null; node2 = node.right) {
                node = node2;
            }
            return node;
        }
    }
    
    private abstract class LinkedTreeMapIterator implements Iterator
    {
        Node next;
        Node lastReturned;
        int expectedModCount;
        final LinkedTreeMap this$0;
        
        LinkedTreeMapIterator(final LinkedTreeMap this$0) {
            this.this$0 = this$0;
            this.next = this.this$0.header.next;
            this.lastReturned = null;
            this.expectedModCount = this.this$0.modCount;
        }
        
        @Override
        public final boolean hasNext() {
            return this.next != this.this$0.header;
        }
        
        final Node nextNode() {
            final Node next = this.next;
            if (next == this.this$0.header) {
                throw new NoSuchElementException();
            }
            if (this.this$0.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            this.next = next.next;
            return this.lastReturned = next;
        }
        
        @Override
        public final void remove() {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            this.this$0.removeInternal(this.lastReturned, true);
            this.lastReturned = null;
            this.expectedModCount = this.this$0.modCount;
        }
    }
    
    class EntrySet extends AbstractSet
    {
        final LinkedTreeMap this$0;
        
        EntrySet(final LinkedTreeMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public int size() {
            return this.this$0.size;
        }
        
        @Override
        public Iterator iterator() {
            return new LinkedTreeMapIterator() {
                final EntrySet this$1;
                
                @Override
                public Map.Entry next() {
                    return this.nextNode();
                }
                
                @Override
                public Object next() {
                    return this.next();
                }
            };
        }
        
        @Override
        public boolean contains(final Object o) {
            return o instanceof Map.Entry && this.this$0.findByEntry((Map.Entry)o) != null;
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Node byEntry = this.this$0.findByEntry((Map.Entry)o);
            if (byEntry == null) {
                return false;
            }
            this.this$0.removeInternal(byEntry, true);
            return true;
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
    }
}
