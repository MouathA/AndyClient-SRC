package org.yaml.snakeyaml.util;

import java.util.*;

public class ArrayUtils
{
    private ArrayUtils() {
    }
    
    public static List toUnmodifiableList(final Object[] array) {
        return (array.length == 0) ? Collections.emptyList() : new UnmodifiableArrayList(array);
    }
    
    public static List toUnmodifiableCompositeList(final Object[] array, final Object[] array2) {
        List list;
        if (array.length == 0) {
            list = toUnmodifiableList(array2);
        }
        else if (array2.length == 0) {
            list = toUnmodifiableList(array);
        }
        else {
            list = new CompositeUnmodifiableArrayList(array, array2);
        }
        return list;
    }
    
    private static class CompositeUnmodifiableArrayList extends AbstractList
    {
        private final Object[] array1;
        private final Object[] array2;
        
        CompositeUnmodifiableArrayList(final Object[] array1, final Object[] array2) {
            this.array1 = array1;
            this.array2 = array2;
        }
        
        @Override
        public Object get(final int n) {
            Object o;
            if (n < this.array1.length) {
                o = this.array1[n];
            }
            else {
                if (n - this.array1.length >= this.array2.length) {
                    throw new IndexOutOfBoundsException("Index: " + n + ", Size: " + this.size());
                }
                o = this.array2[n - this.array1.length];
            }
            return o;
        }
        
        @Override
        public int size() {
            return this.array1.length + this.array2.length;
        }
    }
    
    private static class UnmodifiableArrayList extends AbstractList
    {
        private final Object[] array;
        
        UnmodifiableArrayList(final Object[] array) {
            this.array = array;
        }
        
        @Override
        public Object get(final int n) {
            if (n >= this.array.length) {
                throw new IndexOutOfBoundsException("Index: " + n + ", Size: " + this.size());
            }
            return this.array[n];
        }
        
        @Override
        public int size() {
            return this.array.length;
        }
    }
}
