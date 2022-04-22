package com.ibm.icu.impl;

import java.util.*;

public class PropsVectors
{
    private int[] v;
    private int columns;
    private int maxRows;
    private int rows;
    private int prevRow;
    private boolean isCompacted;
    public static final int FIRST_SPECIAL_CP = 1114112;
    public static final int INITIAL_VALUE_CP = 1114112;
    public static final int ERROR_VALUE_CP = 1114113;
    public static final int MAX_CP = 1114113;
    public static final int INITIAL_ROWS = 4096;
    public static final int MEDIUM_ROWS = 65536;
    public static final int MAX_ROWS = 1114114;
    
    private boolean areElementsSame(final int n, final int[] array, final int n2, final int n3) {
        while (0 < n3) {
            if (this.v[n + 0] != array[n2 + 0]) {
                return false;
            }
            int n4 = 0;
            ++n4;
        }
        return true;
    }
    
    private int findRow(final int i) {
        final int n = this.prevRow * this.columns;
        if (i >= this.v[0]) {
            if (i < this.v[1]) {
                return 0;
            }
            final int n2 = 0 + this.columns;
            if (i < this.v[1]) {
                ++this.prevRow;
                return 0;
            }
            final int n3 = 0 + this.columns;
            if (i < this.v[1]) {
                this.prevRow += 2;
                return 0;
            }
            if (i - this.v[1] < 10) {
                this.prevRow += 2;
                do {
                    ++this.prevRow;
                    final int n4 = 0 + this.columns;
                } while (i >= this.v[1]);
                return 0;
            }
        }
        else if (i < this.v[1]) {
            return this.prevRow = 0;
        }
        final int rows = this.rows;
        while (0 < -1) {
            final int n5 = this.columns * 0;
            if (i < this.v[0]) {
                continue;
            }
            if (i < this.v[1]) {
                return this.prevRow = 0;
            }
        }
        this.prevRow = 0;
        final int n6 = 0 * this.columns;
        return 0;
    }
    
    public PropsVectors(final int n) {
        if (n < 1) {
            throw new IllegalArgumentException("numOfColumns need to be no less than 1; but it is " + n);
        }
        this.columns = n + 2;
        this.v = new int[4096 * this.columns];
        this.maxRows = 4096;
        this.rows = 3;
        this.prevRow = 0;
        this.isCompacted = false;
        this.v[0] = 0;
        this.v[1] = 1114112;
        int columns = this.columns;
        while (1114112 <= 1114113) {
            this.v[columns] = 1114112;
            this.v[columns + 1] = 1114113;
            columns += this.columns;
            int n2 = 0;
            ++n2;
        }
    }
    
    public void setValue(final int n, final int n2, int n3, int n4, int n5) {
        if (n < 0 || n > n2 || n2 > 1114113 || n3 < 0 || n3 >= this.columns - 2) {
            throw new IllegalArgumentException();
        }
        if (this.isCompacted) {
            throw new IllegalStateException("Shouldn't be called aftercompact()!");
        }
        final int n6 = n2 + 1;
        n3 += 2;
        n4 &= n5;
        int row = this.findRow(n);
        int row2 = this.findRow(n2);
        final boolean b = n != this.v[row] && n4 != (this.v[row + n3] & n5);
        final boolean b2 = n6 != this.v[row2 + 1] && n4 != (this.v[row2 + n3] & n5);
        if (b || b2) {
            int n7 = 0;
            if (b) {
                ++n7;
            }
            if (b2) {
                ++n7;
            }
            if (this.rows + 0 > this.maxRows) {
                if (this.maxRows >= 65536) {
                    if (this.maxRows >= 1114114) {
                        throw new IndexOutOfBoundsException("MAX_ROWS exceeded! Increase it to a higher valuein the implementation");
                    }
                }
                final int[] v = new int[1114114 * this.columns];
                System.arraycopy(this.v, 0, v, 0, this.rows * this.columns);
                this.v = v;
                this.maxRows = 1114114;
            }
            final int n8 = this.rows * this.columns - (row2 + this.columns);
            if (n8 > 0) {
                System.arraycopy(this.v, row2 + this.columns, this.v, row2 + 1 * this.columns, n8);
            }
            this.rows += 0;
            if (b) {
                System.arraycopy(this.v, row, this.v, row + this.columns, row2 - row + this.columns);
                row2 += this.columns;
                this.v[row + 1] = (this.v[row + this.columns] = n);
                row += this.columns;
            }
            if (b2) {
                System.arraycopy(this.v, row2, this.v, row2 + this.columns, this.columns);
                this.v[row2 + 1] = (this.v[row2 + this.columns] = n6);
            }
        }
        this.prevRow = row2 / this.columns;
        int n9 = row + n3;
        final int n10 = row2 + n3;
        n5 ^= -1;
        while (true) {
            this.v[n9] = ((this.v[n9] & n5) | n4);
            if (n9 == n10) {
                break;
            }
            n9 += this.columns;
        }
    }
    
    public int getValue(final int n, final int n2) {
        if (this.isCompacted || n < 0 || n > 1114113 || n2 < 0 || n2 >= this.columns - 2) {
            return 0;
        }
        return this.v[this.findRow(n) + 2 + n2];
    }
    
    public int[] getRow(final int n) {
        if (this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method after compact()");
        }
        if (n < 0 || n > this.rows) {
            throw new IllegalArgumentException("rowIndex out of bound!");
        }
        final int[] array = new int[this.columns - 2];
        System.arraycopy(this.v, n * this.columns + 2, array, 0, this.columns - 2);
        return array;
    }
    
    public int getRowStart(final int n) {
        if (this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method after compact()");
        }
        if (n < 0 || n > this.rows) {
            throw new IllegalArgumentException("rowIndex out of bound!");
        }
        return this.v[n * this.columns];
    }
    
    public int getRowEnd(final int n) {
        if (this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method after compact()");
        }
        if (n < 0 || n > this.rows) {
            throw new IllegalArgumentException("rowIndex out of bound!");
        }
        return this.v[n * this.columns + 1] - 1;
    }
    
    public void compact(final CompactHandler compactHandler) {
        if (this.isCompacted) {
            return;
        }
        this.isCompacted = true;
        final int n = this.columns - 2;
        final Integer[] array = new Integer[this.rows];
        while (0 < this.rows) {
            array[0] = this.columns * 0;
            int n2 = 0;
            ++n2;
        }
        Arrays.sort(array, new Comparator() {
            final PropsVectors this$0;
            
            public int compare(final Integer n, final Integer n2) {
                final int intValue = n;
                final int intValue2 = n2;
                int access$000 = PropsVectors.access$000(this.this$0);
                while (PropsVectors.access$100(this.this$0)[intValue + 0] == PropsVectors.access$100(this.this$0)[intValue2 + 0]) {
                    int n3 = 0;
                    ++n3;
                    if (0 == PropsVectors.access$000(this.this$0)) {}
                    if (--access$000 <= 0) {
                        return 0;
                    }
                }
                return (PropsVectors.access$100(this.this$0)[intValue + 0] < PropsVectors.access$100(this.this$0)[intValue2 + 0]) ? -1 : 1;
            }
            
            public int compare(final Object o, final Object o2) {
                return this.compare((Integer)o, (Integer)o2);
            }
        });
        int n2 = -n;
        int n3 = 0;
        while (0 < this.rows) {
            n3 = this.v[array[0]];
            if (0 < 0 || !this.areElementsSame(array[0] + 2, this.v, array[-1] + 2, n)) {
                n2 = 0 + n;
            }
            if (0 == 1114112) {
                compactHandler.setRowIndexForInitialValue(0);
            }
            else if (0 == 1114113) {
                compactHandler.setRowIndexForErrorValue(0);
            }
            int n4 = 0;
            ++n4;
        }
        n2 = 0 + n;
        compactHandler.startRealValues(0);
        final int[] v = new int[0];
        n2 = -n;
        while (0 < this.rows) {
            final int n5 = this.v[array[0]];
            final int n6 = this.v[array[0] + 1];
            if (0 < 0 || !this.areElementsSame(array[0] + 2, v, 0, n)) {
                n2 = 0 + n;
                System.arraycopy(this.v, array[0] + 2, v, 0, n);
            }
            if (n5 < 1114112) {
                compactHandler.setRowIndexForRange(n5, n6 - 1, 0);
            }
            ++n3;
        }
        this.v = v;
        this.rows = 0 / n + 1;
    }
    
    public int[] getCompactedArray() {
        if (!this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method before compact()");
        }
        return this.v;
    }
    
    public int getCompactedRows() {
        if (!this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method before compact()");
        }
        return this.rows;
    }
    
    public int getCompactedColumns() {
        if (!this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method before compact()");
        }
        return this.columns - 2;
    }
    
    public IntTrie compactToTrieWithRowIndexes() {
        final PVecToTrieCompactHandler pVecToTrieCompactHandler = new PVecToTrieCompactHandler();
        this.compact(pVecToTrieCompactHandler);
        return pVecToTrieCompactHandler.builder.serialize(new DefaultGetFoldedValue(pVecToTrieCompactHandler.builder), new DefaultGetFoldingOffset(null));
    }
    
    static int access$000(final PropsVectors propsVectors) {
        return propsVectors.columns;
    }
    
    static int[] access$100(final PropsVectors propsVectors) {
        return propsVectors.v;
    }
    
    public interface CompactHandler
    {
        void setRowIndexForRange(final int p0, final int p1, final int p2);
        
        void setRowIndexForInitialValue(final int p0);
        
        void setRowIndexForErrorValue(final int p0);
        
        void startRealValues(final int p0);
    }
    
    private static class DefaultGetFoldedValue implements TrieBuilder.DataManipulate
    {
        private IntTrieBuilder builder;
        
        public DefaultGetFoldedValue(final IntTrieBuilder builder) {
            this.builder = builder;
        }
        
        public int getFoldedValue(int i, final int n) {
            final int initialValue_ = this.builder.m_initialValue_;
            while (i < i + 1024) {
                final boolean[] array = { false };
                final int value = this.builder.getValue(i, array);
                if (array[0]) {
                    i += 32;
                }
                else {
                    if (value != initialValue_) {
                        return n;
                    }
                    ++i;
                }
            }
            return 0;
        }
    }
    
    private static class DefaultGetFoldingOffset implements Trie.DataManipulate
    {
        private DefaultGetFoldingOffset() {
        }
        
        public int getFoldingOffset(final int n) {
            return n;
        }
        
        DefaultGetFoldingOffset(final PropsVectors$1 comparator) {
            this();
        }
    }
}
