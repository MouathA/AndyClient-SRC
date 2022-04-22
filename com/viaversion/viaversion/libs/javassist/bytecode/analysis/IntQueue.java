package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import java.util.*;

class IntQueue
{
    private Entry head;
    private Entry tail;
    
    void add(final int n) {
        final Entry entry = new Entry(n, null);
        if (this.tail != null) {
            Entry.access$102(this.tail, entry);
        }
        this.tail = entry;
        if (this.head == null) {
            this.head = entry;
        }
    }
    
    boolean isEmpty() {
        return this.head == null;
    }
    
    int take() {
        if (this.head == null) {
            throw new NoSuchElementException();
        }
        final int access$200 = Entry.access$200(this.head);
        this.head = Entry.access$100(this.head);
        if (this.head == null) {
            this.tail = null;
        }
        return access$200;
    }
    
    private static class Entry
    {
        private Entry next;
        private int value;
        
        private Entry(final int value) {
            this.value = value;
        }
        
        Entry(final int n, final IntQueue$1 object) {
            this(n);
        }
        
        static Entry access$102(final Entry entry, final Entry next) {
            return entry.next = next;
        }
        
        static int access$200(final Entry entry) {
            return entry.value;
        }
        
        static Entry access$100(final Entry entry) {
            return entry.next;
        }
    }
}
