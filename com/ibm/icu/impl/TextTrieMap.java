package com.ibm.icu.impl;

import java.util.*;
import com.ibm.icu.lang.*;

public class TextTrieMap
{
    private Node _root;
    boolean _ignoreCase;
    
    public TextTrieMap(final boolean ignoreCase) {
        this._root = new Node(null);
        this._ignoreCase = ignoreCase;
    }
    
    public TextTrieMap put(final CharSequence charSequence, final Object o) {
        this._root.add(new CharIterator(charSequence, 0, this._ignoreCase), o);
        return this;
    }
    
    public Iterator get(final String s) {
        return this.get(s, 0);
    }
    
    public Iterator get(final CharSequence charSequence, final int n) {
        return this.get(charSequence, n, null);
    }
    
    public Iterator get(final CharSequence charSequence, final int n, final int[] array) {
        final LongestMatchHandler longestMatchHandler = new LongestMatchHandler(null);
        this.find(charSequence, n, longestMatchHandler);
        if (array != null && array.length > 0) {
            array[0] = longestMatchHandler.getMatchLength();
        }
        return longestMatchHandler.getMatches();
    }
    
    public void find(final CharSequence charSequence, final ResultHandler resultHandler) {
        this.find(charSequence, 0, resultHandler);
    }
    
    public void find(final CharSequence charSequence, final int n, final ResultHandler resultHandler) {
        this.find(this._root, new CharIterator(charSequence, n, this._ignoreCase), resultHandler);
    }
    
    private synchronized void find(final Node node, final CharIterator charIterator, final ResultHandler resultHandler) {
        final Iterator values = node.values();
        if (values != null && !resultHandler.handlePrefixMatch(charIterator.processedLength(), values)) {
            return;
        }
        final Node match = node.findMatch(charIterator);
        if (match != null) {
            this.find(match, charIterator, resultHandler);
        }
    }
    
    private static char[] toCharArray(final CharSequence charSequence) {
        final char[] array = new char[charSequence.length()];
        while (0 < array.length) {
            array[0] = charSequence.charAt(0);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    private static char[] subArray(final char[] array, final int n) {
        if (n == 0) {
            return array;
        }
        final char[] array2 = new char[array.length - n];
        System.arraycopy(array, n, array2, 0, array2.length);
        return array2;
    }
    
    private static char[] subArray(final char[] array, final int n, final int n2) {
        if (n == 0 && n2 == array.length) {
            return array;
        }
        final char[] array2 = new char[n2 - n];
        System.arraycopy(array, n, array2, 0, n2 - n);
        return array2;
    }
    
    static char[] access$200(final CharSequence charSequence) {
        return toCharArray(charSequence);
    }
    
    static char[] access$300(final char[] array, final int n) {
        return subArray(array, n);
    }
    
    static char[] access$400(final char[] array, final int n, final int n2) {
        return subArray(array, n, n2);
    }
    
    private class Node
    {
        private char[] _text;
        private List _values;
        private List _children;
        final TextTrieMap this$0;
        
        private Node(final TextTrieMap this$0) {
            this.this$0 = this$0;
        }
        
        private Node(final TextTrieMap this$0, final char[] text, final List values, final List children) {
            this.this$0 = this$0;
            this._text = text;
            this._values = values;
            this._children = children;
        }
        
        public Iterator values() {
            if (this._values == null) {
                return null;
            }
            return this._values.iterator();
        }
        
        public void add(final CharIterator charIterator, final Object o) {
            final StringBuilder sb = new StringBuilder();
            while (charIterator.hasNext()) {
                sb.append(charIterator.next());
            }
            this.add(TextTrieMap.access$200(sb), 0, o);
        }
        
        public Node findMatch(final CharIterator charIterator) {
            if (this._children == null) {
                return null;
            }
            if (!charIterator.hasNext()) {
                return null;
            }
            Node node = null;
            final Character next = charIterator.next();
            for (final Node node2 : this._children) {
                if (next < node2._text[0]) {
                    break;
                }
                if (next != node2._text[0]) {
                    continue;
                }
                if (node2.matchFollowing(charIterator)) {
                    node = node2;
                    break;
                }
                break;
            }
            return node;
        }
        
        private void add(final char[] array, final int n, final Object o) {
            if (array.length == n) {
                this._values = this.addValue(this._values, o);
                return;
            }
            if (this._children == null) {
                (this._children = new LinkedList()).add(this.this$0.new Node(TextTrieMap.access$300(array, n), this.addValue(null, o), null));
                return;
            }
            final ListIterator<Node> listIterator = this._children.listIterator();
            while (listIterator.hasNext()) {
                final Node node = listIterator.next();
                if (array[n] < node._text[0]) {
                    listIterator.previous();
                    break;
                }
                if (array[n] == node._text[0]) {
                    final int lenMatches = node.lenMatches(array, n);
                    if (lenMatches == node._text.length) {
                        node.add(array, n + lenMatches, o);
                    }
                    else {
                        node.split(lenMatches);
                        node.add(array, n + lenMatches, o);
                    }
                    return;
                }
            }
            listIterator.add(this.this$0.new Node(TextTrieMap.access$300(array, n), this.addValue(null, o), null));
        }
        
        private boolean matchFollowing(final CharIterator charIterator) {
            while (1 < this._text.length) {
                if (!charIterator.hasNext()) {
                    break;
                }
                if (charIterator.next() != this._text[1]) {
                    break;
                }
                int n = 0;
                ++n;
            }
            return false;
        }
        
        private int lenMatches(final char[] array, final int n) {
            final int n2 = array.length - n;
            while (0 < ((this._text.length < n2) ? this._text.length : n2) && this._text[0] == array[n + 0]) {
                int n3 = 0;
                ++n3;
            }
            return 0;
        }
        
        private void split(final int n) {
            final char[] access$300 = TextTrieMap.access$300(this._text, n);
            this._text = TextTrieMap.access$400(this._text, 0, n);
            final Node node = this.this$0.new Node(access$300, this._values, this._children);
            this._values = null;
            (this._children = new LinkedList()).add(node);
        }
        
        private List addValue(List list, final Object o) {
            if (list == null) {
                list = new LinkedList<Object>();
            }
            list.add(o);
            return list;
        }
        
        Node(final TextTrieMap textTrieMap, final TextTrieMap$1 object) {
            this(textTrieMap);
        }
    }
    
    public static class CharIterator implements Iterator
    {
        private boolean _ignoreCase;
        private CharSequence _text;
        private int _nextIdx;
        private int _startIdx;
        private Character _remainingChar;
        
        CharIterator(final CharSequence text, final int n, final boolean ignoreCase) {
            this._text = text;
            this._startIdx = n;
            this._nextIdx = n;
            this._ignoreCase = ignoreCase;
        }
        
        public boolean hasNext() {
            return this._nextIdx != this._text.length() || this._remainingChar != null;
        }
        
        public Character next() {
            if (this._nextIdx == this._text.length() && this._remainingChar == null) {
                return null;
            }
            Character c;
            if (this._remainingChar != null) {
                c = this._remainingChar;
                this._remainingChar = null;
            }
            else if (this._ignoreCase) {
                final int foldCase = UCharacter.foldCase(Character.codePointAt(this._text, this._nextIdx), true);
                this._nextIdx += Character.charCount(foldCase);
                final char[] chars = Character.toChars(foldCase);
                c = chars[0];
                if (chars.length == 2) {
                    this._remainingChar = chars[1];
                }
            }
            else {
                c = this._text.charAt(this._nextIdx);
                ++this._nextIdx;
            }
            return c;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("remove() not supproted");
        }
        
        public int nextIndex() {
            return this._nextIdx;
        }
        
        public int processedLength() {
            if (this._remainingChar != null) {
                throw new IllegalStateException("In the middle of surrogate pair");
            }
            return this._nextIdx - this._startIdx;
        }
        
        public Object next() {
            return this.next();
        }
    }
    
    private static class LongestMatchHandler implements ResultHandler
    {
        private Iterator matches;
        private int length;
        
        private LongestMatchHandler() {
            this.matches = null;
            this.length = 0;
        }
        
        public boolean handlePrefixMatch(final int length, final Iterator matches) {
            if (length > this.length) {
                this.length = length;
                this.matches = matches;
            }
            return true;
        }
        
        public Iterator getMatches() {
            return this.matches;
        }
        
        public int getMatchLength() {
            return this.length;
        }
        
        LongestMatchHandler(final TextTrieMap$1 object) {
            this();
        }
    }
    
    public interface ResultHandler
    {
        boolean handlePrefixMatch(final int p0, final Iterator p1);
    }
}
