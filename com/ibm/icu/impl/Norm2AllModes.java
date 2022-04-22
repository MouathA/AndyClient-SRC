package com.ibm.icu.impl;

import java.io.*;
import com.ibm.icu.text.*;

public final class Norm2AllModes
{
    public final Normalizer2Impl impl;
    public final ComposeNormalizer2 comp;
    public final DecomposeNormalizer2 decomp;
    public final FCDNormalizer2 fcd;
    public final ComposeNormalizer2 fcc;
    private static CacheBase cache;
    public static final NoopNormalizer2 NOOP_NORMALIZER2;
    
    private Norm2AllModes(final Normalizer2Impl impl) {
        this.impl = impl;
        this.comp = new ComposeNormalizer2(impl, false);
        this.decomp = new DecomposeNormalizer2(impl);
        this.fcd = new FCDNormalizer2(impl);
        this.fcc = new ComposeNormalizer2(impl, true);
    }
    
    private static Norm2AllModes getInstanceFromSingleton(final Norm2AllModesSingleton norm2AllModesSingleton) {
        if (Norm2AllModesSingleton.access$000(norm2AllModesSingleton) != null) {
            throw Norm2AllModesSingleton.access$000(norm2AllModesSingleton);
        }
        return Norm2AllModesSingleton.access$100(norm2AllModesSingleton);
    }
    
    public static Norm2AllModes getNFCInstance() {
        return getInstanceFromSingleton(NFCSingleton.access$200());
    }
    
    public static Norm2AllModes getNFKCInstance() {
        return getInstanceFromSingleton(NFKCSingleton.access$300());
    }
    
    public static Norm2AllModes getNFKC_CFInstance() {
        return getInstanceFromSingleton(NFKC_CFSingleton.access$400());
    }
    
    public static Normalizer2WithImpl getN2WithImpl(final int n) {
        switch (n) {
            case 0: {
                return getNFCInstance().decomp;
            }
            case 1: {
                return getNFKCInstance().decomp;
            }
            case 2: {
                return getNFCInstance().comp;
            }
            case 3: {
                return getNFKCInstance().comp;
            }
            default: {
                return null;
            }
        }
    }
    
    public static Norm2AllModes getInstance(final InputStream inputStream, final String s) {
        if (inputStream == null) {
            Norm2AllModesSingleton norm2AllModesSingleton;
            if (s.equals("nfc")) {
                norm2AllModesSingleton = NFCSingleton.access$200();
            }
            else if (s.equals("nfkc")) {
                norm2AllModesSingleton = NFKCSingleton.access$300();
            }
            else if (s.equals("nfkc_cf")) {
                norm2AllModesSingleton = NFKC_CFSingleton.access$400();
            }
            else {
                norm2AllModesSingleton = null;
            }
            if (norm2AllModesSingleton != null) {
                if (Norm2AllModesSingleton.access$000(norm2AllModesSingleton) != null) {
                    throw Norm2AllModesSingleton.access$000(norm2AllModesSingleton);
                }
                return Norm2AllModesSingleton.access$100(norm2AllModesSingleton);
            }
        }
        return (Norm2AllModes)Norm2AllModes.cache.getInstance(s, inputStream);
    }
    
    public static Normalizer2 getFCDNormalizer2() {
        return getNFCInstance().fcd;
    }
    
    Norm2AllModes(final Normalizer2Impl normalizer2Impl, final Norm2AllModes$1 softCache) {
        this(normalizer2Impl);
    }
    
    static {
        Norm2AllModes.cache = new SoftCache() {
            protected Norm2AllModes createInstance(final String s, final InputStream inputStream) {
                Normalizer2Impl normalizer2Impl;
                if (inputStream == null) {
                    normalizer2Impl = new Normalizer2Impl().load("data/icudt51b/" + s + ".nrm");
                }
                else {
                    normalizer2Impl = new Normalizer2Impl().load(inputStream);
                }
                return new Norm2AllModes(normalizer2Impl, null);
            }
            
            @Override
            protected Object createInstance(final Object o, final Object o2) {
                return this.createInstance((String)o, (InputStream)o2);
            }
        };
        NOOP_NORMALIZER2 = new NoopNormalizer2();
    }
    
    private static final class NFKC_CFSingleton
    {
        private static final Norm2AllModesSingleton INSTANCE;
        
        static Norm2AllModesSingleton access$400() {
            return NFKC_CFSingleton.INSTANCE;
        }
        
        static {
            INSTANCE = new Norm2AllModesSingleton("nfkc_cf", null);
        }
    }
    
    private static final class Norm2AllModesSingleton
    {
        private Norm2AllModes allModes;
        private RuntimeException exception;
        
        private Norm2AllModesSingleton(final String s) {
            this.allModes = new Norm2AllModes(new Normalizer2Impl().load("data/icudt51b/" + s + ".nrm"), null);
        }
        
        static RuntimeException access$000(final Norm2AllModesSingleton norm2AllModesSingleton) {
            return norm2AllModesSingleton.exception;
        }
        
        static Norm2AllModes access$100(final Norm2AllModesSingleton norm2AllModesSingleton) {
            return norm2AllModesSingleton.allModes;
        }
        
        Norm2AllModesSingleton(final String s, final Norm2AllModes$1 softCache) {
            this(s);
        }
    }
    
    private static final class NFKCSingleton
    {
        private static final Norm2AllModesSingleton INSTANCE;
        
        static Norm2AllModesSingleton access$300() {
            return NFKCSingleton.INSTANCE;
        }
        
        static {
            INSTANCE = new Norm2AllModesSingleton("nfkc", null);
        }
    }
    
    private static final class NFCSingleton
    {
        private static final Norm2AllModesSingleton INSTANCE;
        
        static Norm2AllModesSingleton access$200() {
            return NFCSingleton.INSTANCE;
        }
        
        static {
            INSTANCE = new Norm2AllModesSingleton("nfc", null);
        }
    }
    
    public static final class FCDNormalizer2 extends Normalizer2WithImpl
    {
        public FCDNormalizer2(final Normalizer2Impl normalizer2Impl) {
            super(normalizer2Impl);
        }
        
        @Override
        protected void normalize(final CharSequence charSequence, final Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.makeFCD(charSequence, 0, charSequence.length(), reorderingBuffer);
        }
        
        @Override
        protected void normalizeAndAppend(final CharSequence charSequence, final boolean b, final Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.makeFCDAndAppend(charSequence, b, reorderingBuffer);
        }
        
        @Override
        public int spanQuickCheckYes(final CharSequence charSequence) {
            return this.impl.makeFCD(charSequence, 0, charSequence.length(), null);
        }
        
        @Override
        public int getQuickCheck(final int n) {
            return this.impl.isDecompYes(this.impl.getNorm16(n)) ? 1 : 0;
        }
        
        @Override
        public boolean hasBoundaryBefore(final int n) {
            return this.impl.hasFCDBoundaryBefore(n);
        }
        
        @Override
        public boolean hasBoundaryAfter(final int n) {
            return this.impl.hasFCDBoundaryAfter(n);
        }
        
        @Override
        public boolean isInert(final int n) {
            return this.impl.isFCDInert(n);
        }
    }
    
    public abstract static class Normalizer2WithImpl extends Normalizer2
    {
        public final Normalizer2Impl impl;
        
        public Normalizer2WithImpl(final Normalizer2Impl impl) {
            this.impl = impl;
        }
        
        @Override
        public StringBuilder normalize(final CharSequence charSequence, final StringBuilder sb) {
            if (sb == charSequence) {
                throw new IllegalArgumentException();
            }
            sb.setLength(0);
            this.normalize(charSequence, new Normalizer2Impl.ReorderingBuffer(this.impl, sb, charSequence.length()));
            return sb;
        }
        
        @Override
        public Appendable normalize(final CharSequence charSequence, final Appendable appendable) {
            if (appendable == charSequence) {
                throw new IllegalArgumentException();
            }
            final Normalizer2Impl.ReorderingBuffer reorderingBuffer = new Normalizer2Impl.ReorderingBuffer(this.impl, appendable, charSequence.length());
            this.normalize(charSequence, reorderingBuffer);
            reorderingBuffer.flush();
            return appendable;
        }
        
        protected abstract void normalize(final CharSequence p0, final Normalizer2Impl.ReorderingBuffer p1);
        
        @Override
        public StringBuilder normalizeSecondAndAppend(final StringBuilder sb, final CharSequence charSequence) {
            return this.normalizeSecondAndAppend(sb, charSequence, true);
        }
        
        @Override
        public StringBuilder append(final StringBuilder sb, final CharSequence charSequence) {
            return this.normalizeSecondAndAppend(sb, charSequence, false);
        }
        
        public StringBuilder normalizeSecondAndAppend(final StringBuilder sb, final CharSequence charSequence, final boolean b) {
            if (sb == charSequence) {
                throw new IllegalArgumentException();
            }
            this.normalizeAndAppend(charSequence, b, new Normalizer2Impl.ReorderingBuffer(this.impl, sb, sb.length() + charSequence.length()));
            return sb;
        }
        
        protected abstract void normalizeAndAppend(final CharSequence p0, final boolean p1, final Normalizer2Impl.ReorderingBuffer p2);
        
        @Override
        public String getDecomposition(final int n) {
            return this.impl.getDecomposition(n);
        }
        
        @Override
        public String getRawDecomposition(final int n) {
            return this.impl.getRawDecomposition(n);
        }
        
        @Override
        public int composePair(final int n, final int n2) {
            return this.impl.composePair(n, n2);
        }
        
        @Override
        public int getCombiningClass(final int n) {
            return this.impl.getCC(this.impl.getNorm16(n));
        }
        
        @Override
        public boolean isNormalized(final CharSequence charSequence) {
            return charSequence.length() == this.spanQuickCheckYes(charSequence);
        }
        
        @Override
        public Normalizer.QuickCheckResult quickCheck(final CharSequence charSequence) {
            return this.isNormalized(charSequence) ? Normalizer.YES : Normalizer.NO;
        }
        
        public int getQuickCheck(final int n) {
            return 1;
        }
    }
    
    public static final class ComposeNormalizer2 extends Normalizer2WithImpl
    {
        private final boolean onlyContiguous;
        
        public ComposeNormalizer2(final Normalizer2Impl normalizer2Impl, final boolean onlyContiguous) {
            super(normalizer2Impl);
            this.onlyContiguous = onlyContiguous;
        }
        
        @Override
        protected void normalize(final CharSequence charSequence, final Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.compose(charSequence, 0, charSequence.length(), this.onlyContiguous, true, reorderingBuffer);
        }
        
        @Override
        protected void normalizeAndAppend(final CharSequence charSequence, final boolean b, final Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.composeAndAppend(charSequence, b, this.onlyContiguous, reorderingBuffer);
        }
        
        @Override
        public boolean isNormalized(final CharSequence charSequence) {
            return this.impl.compose(charSequence, 0, charSequence.length(), this.onlyContiguous, false, new Normalizer2Impl.ReorderingBuffer(this.impl, new StringBuilder(), 5));
        }
        
        @Override
        public Normalizer.QuickCheckResult quickCheck(final CharSequence charSequence) {
            final int composeQuickCheck = this.impl.composeQuickCheck(charSequence, 0, charSequence.length(), this.onlyContiguous, false);
            if ((composeQuickCheck & 0x1) != 0x0) {
                return Normalizer.MAYBE;
            }
            if (composeQuickCheck >>> 1 == charSequence.length()) {
                return Normalizer.YES;
            }
            return Normalizer.NO;
        }
        
        @Override
        public int spanQuickCheckYes(final CharSequence charSequence) {
            return this.impl.composeQuickCheck(charSequence, 0, charSequence.length(), this.onlyContiguous, true) >>> 1;
        }
        
        @Override
        public int getQuickCheck(final int n) {
            return this.impl.getCompQuickCheck(this.impl.getNorm16(n));
        }
        
        @Override
        public boolean hasBoundaryBefore(final int n) {
            return this.impl.hasCompBoundaryBefore(n);
        }
        
        @Override
        public boolean hasBoundaryAfter(final int n) {
            return this.impl.hasCompBoundaryAfter(n, this.onlyContiguous, false);
        }
        
        @Override
        public boolean isInert(final int n) {
            return this.impl.hasCompBoundaryAfter(n, this.onlyContiguous, true);
        }
    }
    
    public static final class DecomposeNormalizer2 extends Normalizer2WithImpl
    {
        public DecomposeNormalizer2(final Normalizer2Impl normalizer2Impl) {
            super(normalizer2Impl);
        }
        
        @Override
        protected void normalize(final CharSequence charSequence, final Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.decompose(charSequence, 0, charSequence.length(), reorderingBuffer);
        }
        
        @Override
        protected void normalizeAndAppend(final CharSequence charSequence, final boolean b, final Normalizer2Impl.ReorderingBuffer reorderingBuffer) {
            this.impl.decomposeAndAppend(charSequence, b, reorderingBuffer);
        }
        
        @Override
        public int spanQuickCheckYes(final CharSequence charSequence) {
            return this.impl.decompose(charSequence, 0, charSequence.length(), null);
        }
        
        @Override
        public int getQuickCheck(final int n) {
            return this.impl.isDecompYes(this.impl.getNorm16(n)) ? 1 : 0;
        }
        
        @Override
        public boolean hasBoundaryBefore(final int n) {
            return this.impl.hasDecompBoundary(n, true);
        }
        
        @Override
        public boolean hasBoundaryAfter(final int n) {
            return this.impl.hasDecompBoundary(n, false);
        }
        
        @Override
        public boolean isInert(final int n) {
            return this.impl.isDecompInert(n);
        }
    }
    
    public static final class NoopNormalizer2 extends Normalizer2
    {
        @Override
        public StringBuilder normalize(final CharSequence charSequence, final StringBuilder sb) {
            if (sb != charSequence) {
                sb.setLength(0);
                return sb.append(charSequence);
            }
            throw new IllegalArgumentException();
        }
        
        @Override
        public Appendable normalize(final CharSequence charSequence, final Appendable appendable) {
            if (appendable != charSequence) {
                return appendable.append(charSequence);
            }
            throw new IllegalArgumentException();
        }
        
        @Override
        public StringBuilder normalizeSecondAndAppend(final StringBuilder sb, final CharSequence charSequence) {
            if (sb != charSequence) {
                return sb.append(charSequence);
            }
            throw new IllegalArgumentException();
        }
        
        @Override
        public StringBuilder append(final StringBuilder sb, final CharSequence charSequence) {
            if (sb != charSequence) {
                return sb.append(charSequence);
            }
            throw new IllegalArgumentException();
        }
        
        @Override
        public String getDecomposition(final int n) {
            return null;
        }
        
        @Override
        public boolean isNormalized(final CharSequence charSequence) {
            return true;
        }
        
        @Override
        public Normalizer.QuickCheckResult quickCheck(final CharSequence charSequence) {
            return Normalizer.YES;
        }
        
        @Override
        public int spanQuickCheckYes(final CharSequence charSequence) {
            return charSequence.length();
        }
        
        @Override
        public boolean hasBoundaryBefore(final int n) {
            return true;
        }
        
        @Override
        public boolean hasBoundaryAfter(final int n) {
            return true;
        }
        
        @Override
        public boolean isInert(final int n) {
            return true;
        }
    }
}
