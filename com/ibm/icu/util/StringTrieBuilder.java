package com.ibm.icu.util;

import java.util.*;

public abstract class StringTrieBuilder
{
    private State state;
    @Deprecated
    protected StringBuilder strings;
    private Node root;
    private HashMap nodes;
    private ValueNode lookupFinalValueNode;
    static final boolean $assertionsDisabled;
    
    @Deprecated
    protected StringTrieBuilder() {
        this.state = State.ADDING;
        this.strings = new StringBuilder();
        this.nodes = new HashMap();
        this.lookupFinalValueNode = new ValueNode();
    }
    
    @Deprecated
    protected void addImpl(final CharSequence charSequence, final int n) {
        if (this.state != State.ADDING) {
            throw new IllegalStateException("Cannot add (string, value) pairs after build().");
        }
        if (charSequence.length() > 65535) {
            throw new IndexOutOfBoundsException("The maximum string length is 0xffff.");
        }
        if (this.root == null) {
            this.root = this.createSuffixNode(charSequence, 0, n);
        }
        else {
            this.root = this.root.add(this, charSequence, 0, n);
        }
    }
    
    @Deprecated
    protected final void buildImpl(final Option option) {
        switch (this.state) {
            case ADDING: {
                if (this.root == null) {
                    throw new IndexOutOfBoundsException("No (string, value) pairs were added.");
                }
                if (option == Option.FAST) {
                    this.state = State.BUILDING_FAST;
                    break;
                }
                this.state = State.BUILDING_SMALL;
                break;
            }
            case BUILDING_FAST:
            case BUILDING_SMALL: {
                throw new IllegalStateException("Builder failed and must be clear()ed.");
            }
            case BUILT: {
                return;
            }
        }
        (this.root = this.root.register(this)).markRightEdgesFirst(-1);
        this.root.write(this);
        this.state = State.BUILT;
    }
    
    @Deprecated
    protected void clearImpl() {
        this.strings.setLength(0);
        this.nodes.clear();
        this.root = null;
        this.state = State.ADDING;
    }
    
    private final Node registerNode(final Node node) {
        if (this.state == State.BUILDING_FAST) {
            return node;
        }
        final Node node2 = this.nodes.get(node);
        if (node2 != null) {
            return node2;
        }
        final Node node3 = this.nodes.put(node, node);
        assert node3 == null;
        return node;
    }
    
    private final ValueNode registerFinalValue(final int n) {
        ValueNode.access$000(this.lookupFinalValueNode, n);
        final Node node = this.nodes.get(this.lookupFinalValueNode);
        if (node != null) {
            return (ValueNode)node;
        }
        final ValueNode valueNode = new ValueNode(n);
        final ValueNode valueNode2 = this.nodes.put(valueNode, valueNode);
        assert valueNode2 == null;
        return valueNode;
    }
    
    private ValueNode createSuffixNode(final CharSequence charSequence, final int n, final int n2) {
        ValueNode registerFinalValue = this.registerFinalValue(n2);
        if (n < charSequence.length()) {
            final int length = this.strings.length();
            this.strings.append(charSequence, n, charSequence.length());
            registerFinalValue = new LinearMatchNode(this.strings, length, charSequence.length() - n, registerFinalValue);
        }
        return registerFinalValue;
    }
    
    @Deprecated
    protected abstract boolean matchNodesCanHaveValues();
    
    @Deprecated
    protected abstract int getMaxBranchLinearSubNodeLength();
    
    @Deprecated
    protected abstract int getMinLinearMatch();
    
    @Deprecated
    protected abstract int getMaxLinearMatchLength();
    
    @Deprecated
    protected abstract int write(final int p0);
    
    @Deprecated
    protected abstract int write(final int p0, final int p1);
    
    @Deprecated
    protected abstract int writeValueAndFinal(final int p0, final boolean p1);
    
    @Deprecated
    protected abstract int writeValueAndType(final boolean p0, final int p1, final int p2);
    
    @Deprecated
    protected abstract int writeDeltaTo(final int p0);
    
    static ValueNode access$100(final StringTrieBuilder stringTrieBuilder, final CharSequence charSequence, final int n, final int n2) {
        return stringTrieBuilder.createSuffixNode(charSequence, n, n2);
    }
    
    static Node access$200(final StringTrieBuilder stringTrieBuilder, final Node node) {
        return stringTrieBuilder.registerNode(node);
    }
    
    static {
        $assertionsDisabled = !StringTrieBuilder.class.desiredAssertionStatus();
    }
    
    private enum State
    {
        ADDING("ADDING", 0), 
        BUILDING_FAST("BUILDING_FAST", 1), 
        BUILDING_SMALL("BUILDING_SMALL", 2), 
        BUILT("BUILT", 3);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.ADDING, State.BUILDING_FAST, State.BUILDING_SMALL, State.BUILT };
        }
    }
    
    private static final class BranchHeadNode extends ValueNode
    {
        private int length;
        private Node next;
        
        public BranchHeadNode(final int length, final Node next) {
            this.length = length;
            this.next = next;
        }
        
        @Override
        public int hashCode() {
            return (248302782 + this.length) * 37 + this.next.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!super.equals(o)) {
                return false;
            }
            final BranchHeadNode branchHeadNode = (BranchHeadNode)o;
            return this.length == branchHeadNode.length && this.next == branchHeadNode.next;
        }
        
        @Override
        public int markRightEdgesFirst(int n) {
            if (this.offset == 0) {
                n = (this.offset = this.next.markRightEdgesFirst(n));
            }
            return n;
        }
        
        @Override
        public void write(final StringTrieBuilder stringTrieBuilder) {
            this.next.write(stringTrieBuilder);
            if (this.length <= stringTrieBuilder.getMinLinearMatch()) {
                this.offset = stringTrieBuilder.writeValueAndType(this.hasValue, this.value, this.length - 1);
            }
            else {
                stringTrieBuilder.write(this.length - 1);
                this.offset = stringTrieBuilder.writeValueAndType(this.hasValue, this.value, 0);
            }
        }
    }
    
    private abstract static class Node
    {
        protected int offset;
        
        public Node() {
            this.offset = 0;
        }
        
        @Override
        public abstract int hashCode();
        
        @Override
        public boolean equals(final Object o) {
            return this == o || this.getClass() == o.getClass();
        }
        
        public Node add(final StringTrieBuilder stringTrieBuilder, final CharSequence charSequence, final int n, final int n2) {
            return this;
        }
        
        public Node register(final StringTrieBuilder stringTrieBuilder) {
            return this;
        }
        
        public int markRightEdgesFirst(final int offset) {
            if (this.offset == 0) {
                this.offset = offset;
            }
            return offset;
        }
        
        public abstract void write(final StringTrieBuilder p0);
        
        public final void writeUnlessInsideRightEdge(final int n, final int n2, final StringTrieBuilder stringTrieBuilder) {
            if (this.offset < 0 && (this.offset < n2 || n < this.offset)) {
                this.write(stringTrieBuilder);
            }
        }
        
        public final int getOffset() {
            return this.offset;
        }
    }
    
    private static class ValueNode extends Node
    {
        protected boolean hasValue;
        protected int value;
        static final boolean $assertionsDisabled;
        
        public ValueNode() {
        }
        
        public ValueNode(final int value) {
            this.hasValue = true;
            this.value = value;
        }
        
        public final void setValue(final int value) {
            assert !this.hasValue;
            this.hasValue = true;
            this.value = value;
        }
        
        private void setFinalValue(final int value) {
            this.hasValue = true;
            this.value = value;
        }
        
        @Override
        public int hashCode() {
            if (this.hasValue) {
                final int n = 41383797 + this.value;
            }
            return 1118481;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!super.equals(o)) {
                return false;
            }
            final ValueNode valueNode = (ValueNode)o;
            return this.hasValue == valueNode.hasValue && (!this.hasValue || this.value == valueNode.value);
        }
        
        @Override
        public Node add(final StringTrieBuilder stringTrieBuilder, final CharSequence charSequence, final int n, final int n2) {
            if (n == charSequence.length()) {
                throw new IllegalArgumentException("Duplicate string.");
            }
            final ValueNode access$100 = StringTrieBuilder.access$100(stringTrieBuilder, charSequence, n, n2);
            access$100.setValue(this.value);
            return access$100;
        }
        
        @Override
        public void write(final StringTrieBuilder stringTrieBuilder) {
            this.offset = stringTrieBuilder.writeValueAndFinal(this.value, true);
        }
        
        static void access$000(final ValueNode valueNode, final int finalValue) {
            valueNode.setFinalValue(finalValue);
        }
        
        static {
            $assertionsDisabled = !StringTrieBuilder.class.desiredAssertionStatus();
        }
    }
    
    private static final class SplitBranchNode extends BranchNode
    {
        private char unit;
        private Node lessThan;
        private Node greaterOrEqual;
        static final boolean $assertionsDisabled;
        
        public SplitBranchNode(final char unit, final Node lessThan, final Node greaterOrEqual) {
            this.hash = ((206918985 + unit) * 37 + lessThan.hashCode()) * 37 + greaterOrEqual.hashCode();
            this.unit = unit;
            this.lessThan = lessThan;
            this.greaterOrEqual = greaterOrEqual;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!super.equals(o)) {
                return false;
            }
            final SplitBranchNode splitBranchNode = (SplitBranchNode)o;
            return this.unit == splitBranchNode.unit && this.lessThan == splitBranchNode.lessThan && this.greaterOrEqual == splitBranchNode.greaterOrEqual;
        }
        
        @Override
        public int hashCode() {
            return super.hashCode();
        }
        
        @Override
        public int markRightEdgesFirst(int markRightEdgesFirst) {
            if (this.offset == 0) {
                this.firstEdgeNumber = markRightEdgesFirst;
                markRightEdgesFirst = this.greaterOrEqual.markRightEdgesFirst(markRightEdgesFirst);
                markRightEdgesFirst = (this.offset = this.lessThan.markRightEdgesFirst(markRightEdgesFirst - 1));
            }
            return markRightEdgesFirst;
        }
        
        @Override
        public void write(final StringTrieBuilder stringTrieBuilder) {
            this.lessThan.writeUnlessInsideRightEdge(this.firstEdgeNumber, this.greaterOrEqual.getOffset(), stringTrieBuilder);
            this.greaterOrEqual.write(stringTrieBuilder);
            assert this.lessThan.getOffset() > 0;
            stringTrieBuilder.writeDeltaTo(this.lessThan.getOffset());
            this.offset = stringTrieBuilder.write(this.unit);
        }
        
        static {
            $assertionsDisabled = !StringTrieBuilder.class.desiredAssertionStatus();
        }
    }
    
    private abstract static class BranchNode extends Node
    {
        protected int hash;
        protected int firstEdgeNumber;
        
        public BranchNode() {
        }
        
        @Override
        public int hashCode() {
            return this.hash;
        }
    }
    
    private static final class ListBranchNode extends BranchNode
    {
        private Node[] equal;
        private int length;
        private int[] values;
        private char[] units;
        static final boolean $assertionsDisabled;
        
        public ListBranchNode(final int n) {
            this.hash = 165535188 + n;
            this.equal = new Node[n];
            this.values = new int[n];
            this.units = new char[n];
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!super.equals(o)) {
                return false;
            }
            final ListBranchNode listBranchNode = (ListBranchNode)o;
            while (0 < this.length) {
                if (this.units[0] != listBranchNode.units[0] || this.values[0] != listBranchNode.values[0] || this.equal[0] != listBranchNode.equal[0]) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        
        @Override
        public int hashCode() {
            return super.hashCode();
        }
        
        @Override
        public int markRightEdgesFirst(int markRightEdgesFirst) {
            if (this.offset == 0) {
                this.firstEdgeNumber = markRightEdgesFirst;
                int i = this.length;
                do {
                    final Node node = this.equal[--i];
                    if (node != null) {
                        markRightEdgesFirst = node.markRightEdgesFirst(markRightEdgesFirst - 1);
                    }
                } while (i > 0);
                this.offset = markRightEdgesFirst;
            }
            return markRightEdgesFirst;
        }
        
        @Override
        public void write(final StringTrieBuilder stringTrieBuilder) {
            int i = this.length - 1;
            final Node node = this.equal[i];
            final int n = (node == null) ? this.firstEdgeNumber : node.getOffset();
            do {
                --i;
                if (this.equal[i] != null) {
                    this.equal[i].writeUnlessInsideRightEdge(this.firstEdgeNumber, n, stringTrieBuilder);
                }
            } while (i > 0);
            int n2 = this.length - 1;
            if (node == null) {
                stringTrieBuilder.writeValueAndFinal(this.values[n2], true);
            }
            else {
                node.write(stringTrieBuilder);
            }
            this.offset = stringTrieBuilder.write(this.units[n2]);
            while (--n2 >= 0) {
                int n3;
                if (this.equal[n2] == null) {
                    n3 = this.values[n2];
                }
                else {
                    assert this.equal[n2].getOffset() > 0;
                    n3 = this.offset - this.equal[n2].getOffset();
                }
                stringTrieBuilder.writeValueAndFinal(n3, false);
                this.offset = stringTrieBuilder.write(this.units[n2]);
            }
        }
        
        public void add(final int n, final int n2) {
            this.units[this.length] = (char)n;
            this.equal[this.length] = null;
            this.values[this.length] = n2;
            ++this.length;
            this.hash = (this.hash * 37 + n) * 37 + n2;
        }
        
        public void add(final int n, final Node node) {
            this.units[this.length] = (char)n;
            this.equal[this.length] = node;
            this.values[this.length] = 0;
            ++this.length;
            this.hash = (this.hash * 37 + n) * 37 + node.hashCode();
        }
        
        static {
            $assertionsDisabled = !StringTrieBuilder.class.desiredAssertionStatus();
        }
    }
    
    private static final class DynamicBranchNode extends ValueNode
    {
        private StringBuilder chars;
        private ArrayList equal;
        
        public DynamicBranchNode() {
            this.chars = new StringBuilder();
            this.equal = new ArrayList();
        }
        
        public void add(final char c, final Node node) {
            final int find = this.find(c);
            this.chars.insert(find, c);
            this.equal.add(find, node);
        }
        
        @Override
        public Node add(final StringTrieBuilder stringTrieBuilder, final CharSequence charSequence, int n, final int value) {
            if (n != charSequence.length()) {
                final char char1 = charSequence.charAt(n++);
                final int find = this.find(char1);
                if (find < this.chars.length() && char1 == this.chars.charAt(find)) {
                    this.equal.set(find, ((Node)this.equal.get(find)).add(stringTrieBuilder, charSequence, n, value));
                }
                else {
                    this.chars.insert(find, char1);
                    this.equal.add(find, StringTrieBuilder.access$100(stringTrieBuilder, charSequence, n, value));
                }
                return this;
            }
            if (this.hasValue) {
                throw new IllegalArgumentException("Duplicate string.");
            }
            this.setValue(value);
            return this;
        }
        
        @Override
        public Node register(final StringTrieBuilder stringTrieBuilder) {
            ValueNode valueNode;
            final BranchHeadNode branchHeadNode = (BranchHeadNode)(valueNode = new BranchHeadNode(this.chars.length(), this.register(stringTrieBuilder, 0, this.chars.length())));
            if (this.hasValue) {
                if (stringTrieBuilder.matchNodesCanHaveValues()) {
                    branchHeadNode.setValue(this.value);
                }
                else {
                    valueNode = new IntermediateValueNode(this.value, StringTrieBuilder.access$200(stringTrieBuilder, branchHeadNode));
                }
            }
            return StringTrieBuilder.access$200(stringTrieBuilder, valueNode);
        }
        
        private Node register(final StringTrieBuilder stringTrieBuilder, int n, final int n2) {
            final int n3 = n2 - n;
            if (n3 > stringTrieBuilder.getMaxBranchLinearSubNodeLength()) {
                final int n4 = n + n3 / 2;
                return StringTrieBuilder.access$200(stringTrieBuilder, new SplitBranchNode(this.chars.charAt(n4), this.register(stringTrieBuilder, n, n4), this.register(stringTrieBuilder, n4, n2)));
            }
            final ListBranchNode listBranchNode = new ListBranchNode(n3);
            do {
                final char char1 = this.chars.charAt(n);
                final Node node = this.equal.get(n);
                if (((ValueNode)node).getClass() == ValueNode.class) {
                    listBranchNode.add(char1, ((ValueNode)node).value);
                }
                else {
                    listBranchNode.add(char1, node.register(stringTrieBuilder));
                }
            } while (++n < n2);
            return StringTrieBuilder.access$200(stringTrieBuilder, listBranchNode);
        }
        
        private int find(final char c) {
            int length = this.chars.length();
            while (0 < length) {
                final int n = (0 + length) / 2;
                final char char1 = this.chars.charAt(n);
                if (c < char1) {
                    length = n;
                }
                else {
                    if (c == char1) {
                        return n;
                    }
                    continue;
                }
            }
            return 0;
        }
    }
    
    private static final class IntermediateValueNode extends ValueNode
    {
        private Node next;
        
        public IntermediateValueNode(final int value, final Node next) {
            this.next = next;
            this.setValue(value);
        }
        
        @Override
        public int hashCode() {
            return (82767594 + this.value) * 37 + this.next.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (super.equals(o) && this.next == ((IntermediateValueNode)o).next);
        }
        
        @Override
        public int markRightEdgesFirst(int n) {
            if (this.offset == 0) {
                n = (this.offset = this.next.markRightEdgesFirst(n));
            }
            return n;
        }
        
        @Override
        public void write(final StringTrieBuilder stringTrieBuilder) {
            this.next.write(stringTrieBuilder);
            this.offset = stringTrieBuilder.writeValueAndFinal(this.value, false);
        }
    }
    
    private static final class LinearMatchNode extends ValueNode
    {
        private CharSequence strings;
        private int stringOffset;
        private int length;
        private Node next;
        private int hash;
        
        public LinearMatchNode(final CharSequence strings, final int stringOffset, final int length, final Node next) {
            this.strings = strings;
            this.stringOffset = stringOffset;
            this.length = length;
            this.next = next;
        }
        
        @Override
        public int hashCode() {
            return this.hash;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!super.equals(o)) {
                return false;
            }
            final LinearMatchNode linearMatchNode = (LinearMatchNode)o;
            if (this.length != linearMatchNode.length || this.next != linearMatchNode.next) {
                return false;
            }
            for (int i = this.stringOffset, stringOffset = linearMatchNode.stringOffset; i < this.stringOffset + this.length; ++i, ++stringOffset) {
                if (this.strings.charAt(i) != this.strings.charAt(stringOffset)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public Node add(final StringTrieBuilder stringTrieBuilder, final CharSequence charSequence, int n, final int n2) {
            if (n != charSequence.length()) {
                for (int n3 = this.stringOffset + this.length, i = this.stringOffset; i < n3; ++i, ++n) {
                    if (n == charSequence.length()) {
                        final int length = i - this.stringOffset;
                        final LinearMatchNode next = new LinearMatchNode(this.strings, i, this.length - length, this.next);
                        next.setValue(n2);
                        this.length = length;
                        this.next = next;
                        return this;
                    }
                    final char char1 = this.strings.charAt(i);
                    final char char2 = charSequence.charAt(n);
                    if (char1 != char2) {
                        final DynamicBranchNode dynamicBranchNode = new DynamicBranchNode();
                        Node next2;
                        ValueNode valueNode;
                        if (i == this.stringOffset) {
                            if (this.hasValue) {
                                dynamicBranchNode.setValue(this.value);
                                this.value = 0;
                                this.hasValue = false;
                            }
                            ++this.stringOffset;
                            --this.length;
                            next2 = ((this.length > 0) ? this : this.next);
                            valueNode = dynamicBranchNode;
                        }
                        else if (i == n3 - 1) {
                            --this.length;
                            next2 = this.next;
                            this.next = dynamicBranchNode;
                            valueNode = this;
                        }
                        else {
                            final int length2 = i - this.stringOffset;
                            ++i;
                            next2 = new LinearMatchNode(this.strings, i, this.length - (length2 + 1), this.next);
                            this.length = length2;
                            this.next = dynamicBranchNode;
                            valueNode = this;
                        }
                        final ValueNode access$100 = StringTrieBuilder.access$100(stringTrieBuilder, charSequence, n + 1, n2);
                        dynamicBranchNode.add(char1, next2);
                        dynamicBranchNode.add(char2, access$100);
                        return valueNode;
                    }
                }
                this.next = this.next.add(stringTrieBuilder, charSequence, n, n2);
                return this;
            }
            if (this.hasValue) {
                throw new IllegalArgumentException("Duplicate string.");
            }
            this.setValue(n2);
            return this;
        }
        
        @Override
        public Node register(final StringTrieBuilder stringTrieBuilder) {
            this.next = this.next.register(stringTrieBuilder);
            final int maxLinearMatchLength = stringTrieBuilder.getMaxLinearMatchLength();
            while (this.length > maxLinearMatchLength) {
                final int n = this.stringOffset + this.length - maxLinearMatchLength;
                this.length -= maxLinearMatchLength;
                final LinearMatchNode linearMatchNode = new LinearMatchNode(this.strings, n, maxLinearMatchLength, this.next);
                linearMatchNode.setHashCode();
                this.next = StringTrieBuilder.access$200(stringTrieBuilder, linearMatchNode);
            }
            ValueNode valueNode;
            if (this.hasValue && !stringTrieBuilder.matchNodesCanHaveValues()) {
                final int value = this.value;
                this.value = 0;
                this.hasValue = false;
                this.setHashCode();
                valueNode = new IntermediateValueNode(value, StringTrieBuilder.access$200(stringTrieBuilder, this));
            }
            else {
                this.setHashCode();
                valueNode = this;
            }
            return StringTrieBuilder.access$200(stringTrieBuilder, valueNode);
        }
        
        @Override
        public int markRightEdgesFirst(int n) {
            if (this.offset == 0) {
                n = (this.offset = this.next.markRightEdgesFirst(n));
            }
            return n;
        }
        
        @Override
        public void write(final StringTrieBuilder stringTrieBuilder) {
            this.next.write(stringTrieBuilder);
            stringTrieBuilder.write(this.stringOffset, this.length);
            this.offset = stringTrieBuilder.writeValueAndType(this.hasValue, this.value, stringTrieBuilder.getMinLinearMatch() + this.length - 1);
        }
        
        private void setHashCode() {
            this.hash = (124151391 + this.length) * 37 + this.next.hashCode();
            if (this.hasValue) {
                this.hash = this.hash * 37 + this.value;
            }
            for (int i = this.stringOffset; i < this.stringOffset + this.length; ++i) {
                this.hash = this.hash * 37 + this.strings.charAt(i);
            }
        }
    }
    
    public enum Option
    {
        FAST("FAST", 0), 
        SMALL("SMALL", 1);
        
        private static final Option[] $VALUES;
        
        private Option(final String s, final int n) {
        }
        
        static {
            $VALUES = new Option[] { Option.FAST, Option.SMALL };
        }
    }
}
