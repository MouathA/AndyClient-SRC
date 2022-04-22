package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.io.*;

public abstract class Normalizer2
{
    public static Normalizer2 getNFCInstance() {
        return Norm2AllModes.getNFCInstance().comp;
    }
    
    public static Normalizer2 getNFDInstance() {
        return Norm2AllModes.getNFCInstance().decomp;
    }
    
    public static Normalizer2 getNFKCInstance() {
        return Norm2AllModes.getNFKCInstance().comp;
    }
    
    public static Normalizer2 getNFKDInstance() {
        return Norm2AllModes.getNFKCInstance().decomp;
    }
    
    public static Normalizer2 getNFKCCasefoldInstance() {
        return Norm2AllModes.getNFKC_CFInstance().comp;
    }
    
    public static Normalizer2 getInstance(final InputStream inputStream, final String s, final Mode mode) {
        final Norm2AllModes instance = Norm2AllModes.getInstance(inputStream, s);
        switch (mode) {
            case COMPOSE: {
                return instance.comp;
            }
            case DECOMPOSE: {
                return instance.decomp;
            }
            case FCD: {
                return instance.fcd;
            }
            case COMPOSE_CONTIGUOUS: {
                return instance.fcc;
            }
            default: {
                return null;
            }
        }
    }
    
    public String normalize(final CharSequence charSequence) {
        if (!(charSequence instanceof String)) {
            return this.normalize(charSequence, new StringBuilder(charSequence.length())).toString();
        }
        final int spanQuickCheckYes = this.spanQuickCheckYes(charSequence);
        if (spanQuickCheckYes == charSequence.length()) {
            return (String)charSequence;
        }
        return this.normalizeSecondAndAppend(new StringBuilder(charSequence.length()).append(charSequence, 0, spanQuickCheckYes), charSequence.subSequence(spanQuickCheckYes, charSequence.length())).toString();
    }
    
    public abstract StringBuilder normalize(final CharSequence p0, final StringBuilder p1);
    
    public abstract Appendable normalize(final CharSequence p0, final Appendable p1);
    
    public abstract StringBuilder normalizeSecondAndAppend(final StringBuilder p0, final CharSequence p1);
    
    public abstract StringBuilder append(final StringBuilder p0, final CharSequence p1);
    
    public abstract String getDecomposition(final int p0);
    
    public String getRawDecomposition(final int n) {
        return null;
    }
    
    public int composePair(final int n, final int n2) {
        return -1;
    }
    
    public int getCombiningClass(final int n) {
        return 0;
    }
    
    public abstract boolean isNormalized(final CharSequence p0);
    
    public abstract Normalizer.QuickCheckResult quickCheck(final CharSequence p0);
    
    public abstract int spanQuickCheckYes(final CharSequence p0);
    
    public abstract boolean hasBoundaryBefore(final int p0);
    
    public abstract boolean hasBoundaryAfter(final int p0);
    
    public abstract boolean isInert(final int p0);
    
    @Deprecated
    protected Normalizer2() {
    }
    
    public enum Mode
    {
        COMPOSE("COMPOSE", 0), 
        DECOMPOSE("DECOMPOSE", 1), 
        FCD("FCD", 2), 
        COMPOSE_CONTIGUOUS("COMPOSE_CONTIGUOUS", 3);
        
        private static final Mode[] $VALUES;
        
        private Mode(final String s, final int n) {
        }
        
        static {
            $VALUES = new Mode[] { Mode.COMPOSE, Mode.DECOMPOSE, Mode.FCD, Mode.COMPOSE_CONTIGUOUS };
        }
    }
}
