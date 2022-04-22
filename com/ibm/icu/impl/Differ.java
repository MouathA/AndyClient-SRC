package com.ibm.icu.impl;

public final class Differ
{
    private int STACKSIZE;
    private int EQUALSIZE;
    private Object[] a;
    private Object[] b;
    private Object last;
    private Object next;
    private int aCount;
    private int bCount;
    private int aLine;
    private int bLine;
    private int maxSame;
    private int aTop;
    private int bTop;
    
    public Differ(final int stacksize, final int equalsize) {
        this.last = null;
        this.next = null;
        this.aCount = 0;
        this.bCount = 0;
        this.aLine = 1;
        this.bLine = 1;
        this.maxSame = 0;
        this.aTop = 0;
        this.bTop = 0;
        this.STACKSIZE = stacksize;
        this.EQUALSIZE = equalsize;
        this.a = new Object[stacksize + equalsize];
        this.b = new Object[stacksize + equalsize];
    }
    
    public void add(final Object o, final Object o2) {
        this.addA(o);
        this.addB(o2);
    }
    
    public void addA(final Object o) {
        this.flush();
        this.a[this.aCount++] = o;
    }
    
    public void addB(final Object o) {
        this.flush();
        this.b[this.bCount++] = o;
    }
    
    public int getALine(final int n) {
        return this.aLine + this.maxSame + n;
    }
    
    public Object getA(final int n) {
        if (n < 0) {
            return this.last;
        }
        if (n > this.aTop - this.maxSame) {
            return this.next;
        }
        return this.a[n];
    }
    
    public int getACount() {
        return this.aTop - this.maxSame;
    }
    
    public int getBCount() {
        return this.bTop - this.maxSame;
    }
    
    public int getBLine(final int n) {
        return this.bLine + this.maxSame + n;
    }
    
    public Object getB(final int n) {
        if (n < 0) {
            return this.last;
        }
        if (n > this.bTop - this.maxSame) {
            return this.next;
        }
        return this.b[n];
    }
    
    public void checkMatch(final boolean b) {
        int n = this.aCount;
        if (n > this.bCount) {
            n = this.bCount;
        }
        while (0 < n && this.a[0].equals(this.b[0])) {
            int n2 = 0;
            ++n2;
        }
        this.maxSame = 0;
        final int maxSame = this.maxSame;
        this.bTop = maxSame;
        this.aTop = maxSame;
        if (this.maxSame > 0) {
            this.last = this.a[this.maxSame - 1];
        }
        this.next = null;
        if (b) {
            this.aTop = this.aCount;
            this.bTop = this.bCount;
            this.next = null;
            return;
        }
        if (this.aCount - this.maxSame < this.EQUALSIZE || this.bCount - this.maxSame < this.EQUALSIZE) {
            return;
        }
        final int find = this.find(this.a, this.aCount - this.EQUALSIZE, this.aCount, this.b, this.maxSame, this.bCount);
        if (find != -1) {
            this.aTop = this.aCount - this.EQUALSIZE;
            this.bTop = find;
            this.next = this.a[this.aTop];
            return;
        }
        final int find2 = this.find(this.b, this.bCount - this.EQUALSIZE, this.bCount, this.a, this.maxSame, this.aCount);
        if (find2 != -1) {
            this.bTop = this.bCount - this.EQUALSIZE;
            this.aTop = find2;
            this.next = this.b[this.bTop];
            return;
        }
        if (this.aCount >= this.STACKSIZE || this.bCount >= this.STACKSIZE) {
            this.aCount = (this.aCount + this.maxSame) / 2;
            this.bCount = (this.bCount + this.maxSame) / 2;
            this.next = null;
        }
    }
    
    public int find(final Object[] array, final int n, final int n2, final Object[] array2, final int n3, final int n4) {
        final int n5 = n2 - n;
        final int n6 = n4 - n5;
        int i = n3;
    Label_0016:
        while (i <= n6) {
            while (0 < n5) {
                if (!array2[i + 0].equals(array[n + 0])) {
                    ++i;
                    continue Label_0016;
                }
                int n7 = 0;
                ++n7;
            }
            return i;
        }
        return -1;
    }
    
    private void flush() {
        if (this.aTop != 0) {
            final int aCount = this.aCount - this.aTop;
            System.arraycopy(this.a, this.aTop, this.a, 0, aCount);
            this.aCount = aCount;
            this.aLine += this.aTop;
            this.aTop = 0;
        }
        if (this.bTop != 0) {
            final int bCount = this.bCount - this.bTop;
            System.arraycopy(this.b, this.bTop, this.b, 0, bCount);
            this.bCount = bCount;
            this.bLine += this.bTop;
            this.bTop = 0;
        }
    }
}
