package com.viaversion.viaversion.libs.gson.internal;

import java.io.*;
import java.util.*;

public final class LinkedHashTreeMap extends AbstractMap implements Serializable
{
    private static final Comparator NATURAL_ORDER;
    Comparator comparator;
    Node[] table;
    final Node header;
    int size;
    int modCount;
    int threshold;
    private EntrySet entrySet;
    private KeySet keySet;
    static final boolean $assertionsDisabled;
    
    public LinkedHashTreeMap() {
        this(LinkedHashTreeMap.NATURAL_ORDER);
    }
    
    public LinkedHashTreeMap(final Comparator comparator) {
        this.size = 0;
        this.modCount = 0;
        this.comparator = ((comparator != null) ? comparator : LinkedHashTreeMap.NATURAL_ORDER);
        this.header = new Node();
        this.table = new Node[16];
        this.threshold = this.table.length / 2 + this.table.length / 4;
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
        Arrays.fill(this.table, null);
        this.size = 0;
        ++this.modCount;
        final Node header = this.header;
        Node next2;
        for (Node next = header.next; next != header; next = next2) {
            next2 = next.next;
            final Node node = next;
            final Node node2 = next;
            final Node node3 = null;
            node2.prev = node3;
            node.next = node3;
        }
        final Node node4 = header;
        final Node node5 = header;
        final Node node6 = header;
        node5.prev = node6;
        node4.next = node6;
    }
    
    @Override
    public Object remove(final Object o) {
        final Node removeInternalByKey = this.removeInternalByKey(o);
        return (removeInternalByKey != null) ? removeInternalByKey.value : null;
    }
    
    Node find(final Object o, final boolean b) {
        final Comparator comparator = this.comparator;
        final Node[] table = this.table;
        final int secondaryHash = secondaryHash(o.hashCode());
        final int n = secondaryHash & table.length - 1;
        final Node node = table[n];
        if (node != null) {
            final Comparable comparable = (comparator == LinkedHashTreeMap.NATURAL_ORDER) ? ((Comparable)o) : null;
            final int n2 = (comparable != null) ? comparable.compareTo(node.key) : comparator.compare(o, node.key);
            return node;
        }
        if (!b) {
            return null;
        }
        final Node header = this.header;
        Node right;
        if (node == null) {
            if (comparator == LinkedHashTreeMap.NATURAL_ORDER && !(o instanceof Comparable)) {
                throw new ClassCastException(o.getClass().getName() + " is not Comparable");
            }
            right = new Node(node, o, secondaryHash, header, header.prev);
            table[n] = right;
        }
        else {
            right = new Node(node, o, secondaryHash, header, header.prev);
            node.right = right;
            this.rebalance(node, true);
        }
        if (this.size++ > this.threshold) {
            this.doubleCapacity();
        }
        ++this.modCount;
        return right;
    }
    
    Node findByObject(final Object o) {
        return (o != null) ? this.find(o, false) : null;
    }
    
    Node findByEntry(final Map.Entry p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
        //     7: invokevirtual   com/viaversion/viaversion/libs/gson/internal/LinkedHashTreeMap.findByObject:(Ljava/lang/Object;)Lcom/viaversion/viaversion/libs/gson/internal/LinkedHashTreeMap$Node;
        //    10: astore_2       
        //    11: aload_2        
        //    12: ifnull          33
        //    15: aload_0        
        //    16: aload_2        
        //    17: getfield        com/viaversion/viaversion/libs/gson/internal/LinkedHashTreeMap$Node.value:Ljava/lang/Object;
        //    20: aload_1        
        //    21: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
        //    26: if_acmpeq       33
        //    29: iconst_1       
        //    30: goto            34
        //    33: iconst_0       
        //    34: istore_3       
        //    35: iload_3        
        //    36: ifeq            43
        //    39: aload_2        
        //    40: goto            44
        //    43: aconst_null    
        //    44: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0033 (coming from #0026).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static int secondaryHash(int n) {
        n ^= (n >>> 20 ^ n >>> 12);
        return n ^ n >>> 7 ^ n >>> 4;
    }
    
    void removeInternal(final Node node, final boolean b) {
        if (b) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            final Node node2 = null;
            node.prev = node2;
            node.next = node2;
        }
        final Node left = node.left;
        final Node right = node.right;
        final Node parent = node.parent;
        if (left != null && right != null) {
            final Node node3 = (left.height > right.height) ? left.last() : right.first();
            this.removeInternal(node3, false);
            final Node left2 = node.left;
            if (left2 != null) {
                final int height = left2.height;
                node3.left = left2;
                left2.parent = node3;
                node.left = null;
            }
            final Node right2 = node.right;
            if (right2 != null) {
                final int height2 = right2.height;
                node3.right = right2;
                right2.parent = node3;
                node.right = null;
            }
            node3.height = Math.max(0, 0) + 1;
            this.replaceInParent(node, node3);
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
    
    private void replaceInParent(final Node node, final Node node2) {
        final Node parent = node.parent;
        node.parent = null;
        if (node2 != null) {
            node2.parent = parent;
        }
        if (parent != null) {
            if (parent.left == node) {
                parent.left = node2;
            }
            else {
                assert parent.right == node;
                parent.right = node2;
            }
        }
        else {
            this.table[node.hash & this.table.length - 1] = node2;
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
    
    private void doubleCapacity() {
        this.table = doubleCapacity(this.table);
        this.threshold = this.table.length / 2 + this.table.length / 4;
    }
    
    static Node[] doubleCapacity(final Node[] array) {
        final int length = array.length;
        final Node[] array2 = new Node[length * 2];
        final AvlIterator avlIterator = new AvlIterator();
        final AvlBuilder avlBuilder = new AvlBuilder();
        final AvlBuilder avlBuilder2 = new AvlBuilder();
        while (0 < length) {
            final Node node = array[0];
            if (node != null) {
                avlIterator.reset(node);
                Node next;
                while ((next = avlIterator.next()) != null) {
                    if ((next.hash & length) == 0x0) {
                        int n = 0;
                        ++n;
                    }
                    else {
                        int n2 = 0;
                        ++n2;
                    }
                }
                avlBuilder.reset(0);
                avlBuilder2.reset(0);
                avlIterator.reset(node);
                Node next2;
                while ((next2 = avlIterator.next()) != null) {
                    if ((next2.hash & length) == 0x0) {
                        avlBuilder.add(next2);
                    }
                    else {
                        avlBuilder2.add(next2);
                    }
                }
                array2[0 + length] = (array2[0] = null);
            }
            int n3 = 0;
            ++n3;
        }
        return array2;
    }
    
    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }
    
    static {
        $assertionsDisabled = !LinkedHashTreeMap.class.desiredAssertionStatus();
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
        final LinkedHashTreeMap this$0;
        
        KeySet(final LinkedHashTreeMap this$0) {
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
        final int hash;
        Object value;
        int height;
        
        Node() {
            this.key = null;
            this.hash = -1;
            this.prev = this;
            this.next = this;
        }
        
        Node(final Node parent, final Object key, final int hash, final Node next, final Node prev) {
            this.parent = parent;
            this.key = key;
            this.hash = hash;
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
        final LinkedHashTreeMap this$0;
        
        LinkedTreeMapIterator(final LinkedHashTreeMap this$0) {
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
    
    final class EntrySet extends AbstractSet
    {
        final LinkedHashTreeMap this$0;
        
        EntrySet(final LinkedHashTreeMap this$0) {
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
    
    static final class AvlBuilder
    {
        private Node stack;
        private int leavesToSkip;
        private int leavesSkipped;
        private int size;
        
        void reset(final int n) {
            this.leavesToSkip = Integer.highestOneBit(n) * 2 - 1 - n;
            this.size = 0;
            this.leavesSkipped = 0;
            this.stack = null;
        }
        
        void add(final Node stack) {
            final Node left = null;
            stack.right = left;
            stack.parent = left;
            stack.left = left;
            stack.height = 1;
            if (this.leavesToSkip > 0 && (this.size & 0x1) == 0x0) {
                ++this.size;
                --this.leavesToSkip;
                ++this.leavesSkipped;
            }
            stack.parent = this.stack;
            this.stack = stack;
            ++this.size;
            if (this.leavesToSkip > 0 && (this.size & 0x1) == 0x0) {
                ++this.size;
                --this.leavesToSkip;
                ++this.leavesSkipped;
            }
            while ((this.size & 0x3) == 0x3) {
                if (this.leavesSkipped == 0) {
                    final Node stack2 = this.stack;
                    final Node parent = stack2.parent;
                    final Node parent2 = parent.parent;
                    parent.parent = parent2.parent;
                    this.stack = parent;
                    parent.left = parent2;
                    parent.right = stack2;
                    parent.height = stack2.height + 1;
                    parent2.parent = parent;
                    stack2.parent = parent;
                }
                else if (this.leavesSkipped == 1) {
                    final Node stack3 = this.stack;
                    final Node parent3 = stack3.parent;
                    this.stack = parent3;
                    parent3.right = stack3;
                    parent3.height = stack3.height + 1;
                    stack3.parent = parent3;
                    this.leavesSkipped = 0;
                }
                else {
                    if (this.leavesSkipped != 2) {
                        continue;
                    }
                    this.leavesSkipped = 0;
                }
            }
        }
        
        Node root() {
            final Node stack = this.stack;
            if (stack.parent != null) {
                throw new IllegalStateException();
            }
            return stack;
        }
    }
    
    static class AvlIterator
    {
        private Node stackTop;
        
        void reset(final Node node) {
            Node node2 = null;
            for (Node left = node; left != null; left = left.left) {
                left.parent = node2;
                node2 = left;
            }
            this.stackTop = node2;
        }
        
        public Node next() {
            final Node stackTop = this.stackTop;
            if (stackTop == null) {
                return null;
            }
            final Node node = stackTop;
            Node parent = node.parent;
            node.parent = null;
            for (Node node2 = node.right; node2 != null; node2 = node2.left) {
                node2.parent = parent;
                parent = node2;
            }
            this.stackTop = parent;
            return node;
        }
    }
}
