package com.ibm.icu.text;

public final class UnicodeCompressor implements SCSU
{
    private static boolean[] sSingleTagTable;
    private static boolean[] sUnicodeTagTable;
    private int fCurrentWindow;
    private int[] fOffsets;
    private int fMode;
    private int[] fIndexCount;
    private int[] fTimeStamps;
    private int fTimeStamp;
    
    public UnicodeCompressor() {
        this.fCurrentWindow = 0;
        this.fOffsets = new int[8];
        this.fMode = 0;
        this.fIndexCount = new int[256];
        this.fTimeStamps = new int[8];
        this.fTimeStamp = 0;
        this.reset();
    }
    
    public static byte[] compress(final String s) {
        return compress(s.toCharArray(), 0, s.length());
    }
    
    public static byte[] compress(final char[] array, final int n, final int n2) {
        final UnicodeCompressor unicodeCompressor = new UnicodeCompressor();
        final int max = Math.max(4, 3 * (n2 - n) + 1);
        final byte[] array2 = new byte[max];
        final int compress = unicodeCompressor.compress(array, n, n2, null, array2, 0, max);
        final byte[] array3 = new byte[compress];
        System.arraycopy(array2, 0, array3, 0, compress);
        return array3;
    }
    
    public int compress(final char[] array, final int n, final int n2, final int[] array2, final byte[] array3, final int n3, final int n4) {
        int n5 = n3;
        int n6 = n;
        if (array3.length < 4 || n4 - n3 < 4) {
            throw new IllegalArgumentException("byteBuffer.length < 4");
        }
    Label_1431:
        while (n6 < n2 && n5 < n4) {
            switch (this.fMode) {
                case 0: {
                    while (n6 < n2 && n5 < n4) {
                        final char c = array[n6++];
                        if (n6 < n2) {
                            final char c2 = array[n6];
                        }
                        if (-1 < 128) {
                            if (UnicodeCompressor.sSingleTagTable[0]) {
                                if (n5 + 1 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                array3[n5++] = 1;
                            }
                            array3[n5++] = 0;
                        }
                        else if (this.inDynamicWindow(-1, this.fCurrentWindow)) {
                            array3[n5++] = (byte)(-1 - this.fOffsets[this.fCurrentWindow] + 128);
                        }
                        else if (!isCompressible(-1)) {
                            if (-1 != -1 && isCompressible(-1)) {
                                if (n5 + 2 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                array3[n5++] = 14;
                                array3[n5++] = (byte)16777215;
                                array3[n5++] = (byte)255;
                            }
                            else {
                                if (n5 + 3 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                array3[n5++] = 15;
                                if (UnicodeCompressor.sUnicodeTagTable[0]) {
                                    array3[n5++] = -16;
                                }
                                array3[n5++] = 0;
                                array3[n5++] = 0;
                                this.fMode = 1;
                                break;
                            }
                        }
                        else if (this.findDynamicWindow(-1) != -1) {
                            if (n6 + 1 < n2) {
                                final char c3 = array[n6 + 1];
                            }
                            if (this.inDynamicWindow(-1, 0) && this.inDynamicWindow(-1, 0)) {
                                if (n5 + 1 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                array3[n5++] = 16;
                                array3[n5++] = (byte)(-1 - this.fOffsets[0] + 128);
                                this.fTimeStamps[0] = ++this.fTimeStamp;
                                this.fCurrentWindow = 0;
                            }
                            else {
                                if (n5 + 1 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                array3[n5++] = 1;
                                array3[n5++] = (byte)(-1 - this.fOffsets[0] + 128);
                            }
                        }
                        else if (findStaticWindow(-1) != -1 && !inStaticWindow(-1, 0)) {
                            if (n5 + 1 >= n4) {
                                --n6;
                                break Label_1431;
                            }
                            array3[n5++] = 1;
                            array3[n5++] = (byte)(-1 - UnicodeCompressor.sOffsets[0]);
                        }
                        else {
                            makeIndex(-1);
                            final int[] fIndexCount = this.fIndexCount;
                            final int n7 = -1;
                            ++fIndexCount[n7];
                            if (n6 + 1 < n2) {
                                final char c4 = array[n6 + 1];
                            }
                            if (this.fIndexCount[-1] > 1 || (-1 == makeIndex(-1) && -1 == makeIndex(-1))) {
                                if (n5 + 2 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                this.getLRDefinedWindow();
                                array3[n5++] = 24;
                                array3[n5++] = -1;
                                array3[n5++] = (byte)(-1 - UnicodeCompressor.sOffsetTable[-1] + 128);
                                this.fOffsets[0] = UnicodeCompressor.sOffsetTable[-1];
                                this.fCurrentWindow = 0;
                                this.fTimeStamps[0] = ++this.fTimeStamp;
                            }
                            else {
                                if (n5 + 3 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                array3[n5++] = 15;
                                if (UnicodeCompressor.sUnicodeTagTable[0]) {
                                    array3[n5++] = -16;
                                }
                                array3[n5++] = 0;
                                array3[n5++] = 0;
                                this.fMode = 1;
                                break;
                            }
                        }
                    }
                    continue;
                }
                case 1: {
                    while (n6 < n2 && n5 < n4) {
                        final char c5 = array[n6++];
                        if (n6 < n2) {
                            final char c6 = array[n6];
                        }
                        if (!isCompressible(-1) || (-1 != -1 && !isCompressible(-1))) {
                            if (n5 + 2 >= n4) {
                                --n6;
                                break Label_1431;
                            }
                            if (UnicodeCompressor.sUnicodeTagTable[0]) {
                                array3[n5++] = -16;
                            }
                            array3[n5++] = 0;
                            array3[n5++] = 0;
                        }
                        else if (-1 < 128) {
                            if (-1 != -1 && -1 < 128 && !UnicodeCompressor.sSingleTagTable[0]) {
                                if (n5 + 1 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                final int fCurrentWindow = this.fCurrentWindow;
                                array3[n5++] = (byte)224;
                                array3[n5++] = 0;
                                this.fTimeStamps[0] = ++this.fTimeStamp;
                                this.fMode = 0;
                                break;
                            }
                            else {
                                if (n5 + 1 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                array3[n5++] = 0;
                                array3[n5++] = 0;
                            }
                        }
                        else if (this.findDynamicWindow(-1) != -1) {
                            if (this.inDynamicWindow(-1, 0)) {
                                if (n5 + 1 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                array3[n5++] = (byte)224;
                                array3[n5++] = (byte)(-1 - this.fOffsets[0] + 128);
                                this.fTimeStamps[0] = ++this.fTimeStamp;
                                this.fCurrentWindow = 0;
                                this.fMode = 0;
                                break;
                            }
                            else {
                                if (n5 + 2 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                if (UnicodeCompressor.sUnicodeTagTable[0]) {
                                    array3[n5++] = -16;
                                }
                                array3[n5++] = 0;
                                array3[n5++] = 0;
                            }
                        }
                        else {
                            makeIndex(-1);
                            final int[] fIndexCount2 = this.fIndexCount;
                            final int n8 = -1;
                            ++fIndexCount2[n8];
                            if (n6 + 1 < n2) {
                                final char c7 = array[n6 + 1];
                            }
                            if (this.fIndexCount[-1] > 1 || (-1 == makeIndex(-1) && -1 == makeIndex(-1))) {
                                if (n5 + 2 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                this.getLRDefinedWindow();
                                array3[n5++] = (byte)232;
                                array3[n5++] = -1;
                                array3[n5++] = (byte)(-1 - UnicodeCompressor.sOffsetTable[-1] + 128);
                                this.fOffsets[0] = UnicodeCompressor.sOffsetTable[-1];
                                this.fCurrentWindow = 0;
                                this.fTimeStamps[0] = ++this.fTimeStamp;
                                this.fMode = 0;
                                break;
                            }
                            else {
                                if (n5 + 2 >= n4) {
                                    --n6;
                                    break Label_1431;
                                }
                                if (UnicodeCompressor.sUnicodeTagTable[0]) {
                                    array3[n5++] = -16;
                                }
                                array3[n5++] = 0;
                                array3[n5++] = 0;
                            }
                        }
                    }
                    continue;
                }
            }
        }
        if (array2 != null) {
            array2[0] = n6 - n;
        }
        return n5 - n3;
    }
    
    public void reset() {
        this.fOffsets[0] = 128;
        this.fOffsets[1] = 192;
        this.fOffsets[2] = 1024;
        this.fOffsets[3] = 1536;
        this.fOffsets[4] = 2304;
        this.fOffsets[5] = 12352;
        this.fOffsets[6] = 12448;
        this.fOffsets[7] = 65280;
        int n = 0;
        while (0 < 8) {
            this.fTimeStamps[0] = 0;
            ++n;
        }
        while (0 <= 255) {
            this.fIndexCount[0] = 0;
            ++n;
        }
        this.fTimeStamp = 0;
        this.fCurrentWindow = 0;
        this.fMode = 0;
    }
    
    private static int makeIndex(final int n) {
        if (n >= 192 && n < 320) {
            return 249;
        }
        if (n >= 592 && n < 720) {
            return 250;
        }
        if (n >= 880 && n < 1008) {
            return 251;
        }
        if (n >= 1328 && n < 1424) {
            return 252;
        }
        if (n >= 12352 && n < 12448) {
            return 253;
        }
        if (n >= 12448 && n < 12576) {
            return 254;
        }
        if (n >= 65376 && n < 65439) {
            return 255;
        }
        if (n >= 128 && n < 13312) {
            return n / 128 & 0xFF;
        }
        if (n >= 57344 && n <= 65535) {
            return (n - 44032) / 128 & 0xFF;
        }
        return 0;
    }
    
    private boolean inDynamicWindow(final int n, final int n2) {
        return n >= this.fOffsets[n2] && n < this.fOffsets[n2] + 128;
    }
    
    private static boolean inStaticWindow(final int n, final int n2) {
        return n >= UnicodeCompressor.sOffsets[n2] && n < UnicodeCompressor.sOffsets[n2] + 128;
    }
    
    private static boolean isCompressible(final int n) {
        return n < 13312 || n >= 57344;
    }
    
    private int findDynamicWindow(final int n) {
        while (7 >= 0) {
            if (this.inDynamicWindow(n, 7)) {
                final int[] fTimeStamps = this.fTimeStamps;
                final int n2 = 7;
                ++fTimeStamps[n2];
                return 7;
            }
            int n3 = 0;
            --n3;
        }
        return -1;
    }
    
    private static int findStaticWindow(final int n) {
        while (7 >= 0) {
            if (inStaticWindow(n, 7)) {
                return 7;
            }
            int n2 = 0;
            --n2;
        }
        return -1;
    }
    
    private int getLRDefinedWindow() {
        while (7 >= 0) {
            if (this.fTimeStamps[7] < Integer.MAX_VALUE) {
                final int n = this.fTimeStamps[7];
            }
            int n2 = 0;
            --n2;
        }
        return -1;
    }
    
    static {
        UnicodeCompressor.sSingleTagTable = new boolean[] { false, true, true, true, true, true, true, true, true, false, false, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false };
        UnicodeCompressor.sUnicodeTagTable = new boolean[] { false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false };
    }
}
