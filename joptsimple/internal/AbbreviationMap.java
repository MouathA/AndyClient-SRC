package joptsimple.internal;

import java.util.*;

public class AbbreviationMap
{
    private String key;
    private Object value;
    private final Map children;
    private int keysBeyond;
    
    public AbbreviationMap() {
        this.children = new TreeMap();
    }
    
    public boolean contains(final String s) {
        return this.get(s) != null;
    }
    
    public Object get(final String s) {
        final char[] chars = charsOf(s);
        AbbreviationMap abbreviationMap = this;
        final char[] array = chars;
        while (0 < array.length) {
            abbreviationMap = (AbbreviationMap)abbreviationMap.children.get(array[0]);
            if (abbreviationMap == null) {
                return null;
            }
            int n = 0;
            ++n;
        }
        return abbreviationMap.value;
    }
    
    public void put(final String s, final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (s.length() == 0) {
            throw new IllegalArgumentException();
        }
        final char[] chars = charsOf(s);
        this.add(chars, o, 0, chars.length);
    }
    
    public void putAll(final Iterable iterable, final Object o) {
        final Iterator<String> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            this.put(iterator.next(), o);
        }
    }
    
    private boolean add(final char[] array, final Object value, final int n, final int n2) {
        if (n == n2) {
            this.value = value;
            final boolean b = this.key != null;
            this.key = new String(array);
            return !b;
        }
        final char c = array[n];
        AbbreviationMap abbreviationMap = this.children.get(c);
        if (abbreviationMap == null) {
            abbreviationMap = new AbbreviationMap();
            this.children.put(c, abbreviationMap);
        }
        final boolean add = abbreviationMap.add(array, value, n + 1, n2);
        if (add) {
            ++this.keysBeyond;
        }
        if (this.key == null) {
            this.value = ((this.keysBeyond > 1) ? null : value);
        }
        return add;
    }
    
    public void remove(final String s) {
        if (s.length() == 0) {
            throw new IllegalArgumentException();
        }
        final char[] chars = charsOf(s);
        this.remove(chars, 0, chars.length);
    }
    
    private void setValueToThatOfOnlyChild() {
        this.value = this.children.entrySet().iterator().next().getValue().value;
    }
    
    private boolean removeAtEndOfKey() {
        if (this.key == null) {
            return false;
        }
        this.key = null;
        if (this.keysBeyond == 1) {
            this.setValueToThatOfOnlyChild();
        }
        else {
            this.value = null;
        }
        return true;
    }
    
    public Map toJavaUtilMap() {
        final TreeMap treeMap = new TreeMap();
        this.addToMappings(treeMap);
        return treeMap;
    }
    
    private void addToMappings(final Map map) {
        if (this.key != null) {
            map.put(this.key, this.value);
        }
        final Iterator<AbbreviationMap> iterator = this.children.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().addToMappings(map);
        }
    }
    
    private static char[] charsOf(final String s) {
        final char[] array = new char[s.length()];
        s.getChars(0, s.length(), array, 0);
        return array;
    }
}
