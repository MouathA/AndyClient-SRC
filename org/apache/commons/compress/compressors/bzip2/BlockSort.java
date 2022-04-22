package org.apache.commons.compress.compressors.bzip2;

import java.util.*;

class BlockSort
{
    private static final int QSORT_STACK_SIZE = 1000;
    private static final int FALLBACK_QSORT_STACK_SIZE = 100;
    private static final int STACK_SIZE = 1000;
    private int workDone;
    private int workLimit;
    private boolean firstAttempt;
    private final int[] stack_ll;
    private final int[] stack_hh;
    private final int[] stack_dd;
    private final int[] mainSort_runningOrder;
    private final int[] mainSort_copy;
    private final boolean[] mainSort_bigDone;
    private final int[] ftab;
    private final char[] quadrant;
    private static final int FALLBACK_QSORT_SMALL_THRESH = 10;
    private int[] eclass;
    private static final int[] INCS;
    private static final int SMALL_THRESH = 20;
    private static final int DEPTH_THRESH = 10;
    private static final int WORK_FACTOR = 30;
    private static final int SETMASK = 2097152;
    private static final int CLEARMASK = -2097153;
    
    BlockSort(final BZip2CompressorOutputStream.Data data) {
        this.stack_ll = new int[1000];
        this.stack_hh = new int[1000];
        this.stack_dd = new int[1000];
        this.mainSort_runningOrder = new int[256];
        this.mainSort_copy = new int[256];
        this.mainSort_bigDone = new boolean[256];
        this.ftab = new int[65537];
        this.quadrant = data.sfmap;
    }
    
    void blockSort(final BZip2CompressorOutputStream.Data data, final int n) {
        this.workLimit = 30 * n;
        this.workDone = 0;
        this.firstAttempt = true;
        if (n + 1 < 10000) {
            this.fallbackSort(data, n);
        }
        else {
            this.mainSort(data, n);
            if (this.firstAttempt && this.workDone > this.workLimit) {
                this.fallbackSort(data, n);
            }
        }
        final int[] fmap = data.fmap;
        data.origPtr = -1;
        for (int i = 0; i <= n; ++i) {
            if (fmap[i] == 0) {
                data.origPtr = i;
                break;
            }
        }
    }
    
    final void fallbackSort(final BZip2CompressorOutputStream.Data data, final int n) {
        data.block[0] = data.block[n + 1];
        this.fallbackSort(data.fmap, data.block, n + 1);
        for (int i = 0; i < n + 1; ++i) {
            final int[] fmap = data.fmap;
            final int n2 = i;
            --fmap[n2];
        }
        for (int j = 0; j < n + 1; ++j) {
            if (data.fmap[j] == -1) {
                data.fmap[j] = n;
                break;
            }
        }
    }
    
    private void fallbackSimpleSort(final int[] array, final int[] array2, final int n, final int n2) {
        if (n == n2) {
            return;
        }
        if (n2 - n > 3) {
            for (int i = n2 - 4; i >= n; --i) {
                final int n3 = array[i];
                int n4;
                int n5;
                for (n4 = array2[n3], n5 = i + 4; n5 <= n2 && n4 > array2[array[n5]]; n5 += 4) {
                    array[n5 - 4] = array[n5];
                }
                array[n5 - 4] = n3;
            }
        }
        for (int j = n2 - 1; j >= n; --j) {
            final int n6 = array[j];
            int n7;
            int n8;
            for (n7 = array2[n6], n8 = j + 1; n8 <= n2 && n7 > array2[array[n8]]; ++n8) {
                array[n8 - 1] = array[n8];
            }
            array[n8 - 1] = n6;
        }
    }
    
    private void fswap(final int[] array, final int n, final int n2) {
        final int n3 = array[n];
        array[n] = array[n2];
        array[n2] = n3;
    }
    
    private void fvswap(final int[] array, int n, int n2, int i) {
        while (i > 0) {
            this.fswap(array, n, n2);
            ++n;
            ++n2;
            --i;
        }
    }
    
    private int fmin(final int n, final int n2) {
        return (n < n2) ? n : n2;
    }
    
    private void fpush(final int n, final int n2, final int n3) {
        this.stack_ll[n] = n2;
        this.stack_hh[n] = n3;
    }
    
    private int[] fpop(final int n) {
        return new int[] { this.stack_ll[n], this.stack_hh[n] };
    }
    
    private void fallbackQSort3(final int[] array, final int[] array2, final int n, final int n2) {
        long n3 = 0L;
        int i = 0;
        this.fpush(i++, n, n2);
        while (i > 0) {
            final int[] fpop = this.fpop(--i);
            final int n4 = fpop[0];
            final int n5 = fpop[1];
            if (n5 - n4 < 10) {
                this.fallbackSimpleSort(array, array2, n4, n5);
            }
            else {
                n3 = (n3 * 7621L + 1L) % 32768L;
                final long n6 = n3 % 3L;
                long n7;
                if (n6 == 0L) {
                    n7 = array2[array[n4]];
                }
                else if (n6 == 1L) {
                    n7 = array2[array[n4 + n5 >>> 1]];
                }
                else {
                    n7 = array2[array[n5]];
                }
                int j;
                int n8 = j = n4;
                int n10;
                int n9 = n10 = n5;
            Label_0275_Outer:
                while (true) {
                    if (j <= n10) {
                        final int n11 = array2[array[j]] - (int)n7;
                        if (n11 == 0) {
                            this.fswap(array, j, n8);
                            ++n8;
                            ++j;
                            continue Label_0275_Outer;
                        }
                        if (n11 <= 0) {
                            ++j;
                            continue Label_0275_Outer;
                        }
                    }
                    while (true) {
                        while (j <= n10) {
                            final int n12 = array2[array[n10]] - (int)n7;
                            if (n12 == 0) {
                                this.fswap(array, n10, n9);
                                --n9;
                                --n10;
                            }
                            else if (n12 < 0) {
                                if (j > n10) {
                                    break Label_0275_Outer;
                                }
                                this.fswap(array, j, n10);
                                ++j;
                                --n10;
                                continue Label_0275_Outer;
                            }
                            else {
                                --n10;
                            }
                        }
                        continue;
                    }
                }
                if (n9 < n8) {
                    continue;
                }
                final int fmin = this.fmin(n8 - n4, j - n8);
                this.fvswap(array, n4, j - fmin, fmin);
                final int fmin2 = this.fmin(n5 - n9, n9 - n10);
                this.fvswap(array, n10 + 1, n5 - fmin2 + 1, fmin2);
                final int n13 = n4 + j - n8 - 1;
                final int n14 = n5 - (n9 - n10) + 1;
                if (n13 - n4 > n5 - n14) {
                    this.fpush(i++, n4, n13);
                    this.fpush(i++, n14, n5);
                }
                else {
                    this.fpush(i++, n14, n5);
                    this.fpush(i++, n4, n13);
                }
            }
        }
    }
    
    private int[] getEclass() {
        return (this.eclass == null) ? (this.eclass = new int[this.quadrant.length / 2]) : this.eclass;
    }
    
    final void fallbackSort(final int[] array, final byte[] array2, final int n) {
        final int[] array3 = new int[257];
        final int[] eclass = this.getEclass();
        for (int i = 0; i < n; ++i) {
            eclass[i] = 0;
        }
        for (int j = 0; j < n; ++j) {
            final int[] array4 = array3;
            final int n2 = array2[j] & 0xFF;
            ++array4[n2];
        }
        for (int k = 1; k < 257; ++k) {
            final int[] array5 = array3;
            final int n3 = k;
            array5[n3] += array3[k - 1];
        }
        for (int l = 0; l < n; ++l) {
            final int n4 = array2[l] & 0xFF;
            array[--array3[n4]] = l;
        }
        final BitSet set = new BitSet(64 + n);
        for (int n5 = 0; n5 < 256; ++n5) {
            set.set(array3[n5]);
        }
        for (int n6 = 0; n6 < 32; ++n6) {
            set.set(n + 2 * n6);
            set.clear(n + 2 * n6 + 1);
        }
        int n7 = 1;
        int n8;
        do {
            int n9 = 0;
            for (int n10 = 0; n10 < n; ++n10) {
                if (set.get(n10)) {
                    n9 = n10;
                }
                int n11 = array[n10] - n7;
                if (n11 < 0) {
                    n11 += n;
                }
                eclass[n11] = n9;
            }
            n8 = 0;
            int n12 = -1;
            while (true) {
                final int nextClearBit = set.nextClearBit(n12 + 1);
                final int n13 = nextClearBit - 1;
                if (n13 >= n) {
                    break;
                }
                n12 = set.nextSetBit(nextClearBit + 1) - 1;
                if (n12 >= n) {
                    break;
                }
                if (n12 <= n13) {
                    continue;
                }
                n8 += n12 - n13 + 1;
                this.fallbackQSort3(array, eclass, n13, n12);
                int n14 = -1;
                for (int n15 = n13; n15 <= n12; ++n15) {
                    final int n16 = eclass[array[n15]];
                    if (n14 != n16) {
                        set.set(n15);
                        n14 = n16;
                    }
                }
            }
            n7 *= 2;
        } while (n7 <= n && n8 != 0);
    }
    
    private boolean mainSimpleSort(final BZip2CompressorOutputStream.Data data, final int n, final int n2, final int n3, final int n4) {
        final int n5 = n2 - n + 1;
        if (n5 < 2) {
            return this.firstAttempt && this.workDone > this.workLimit;
        }
        int n6;
        for (n6 = 0; BlockSort.INCS[n6] < n5; ++n6) {}
        final int[] fmap = data.fmap;
        final char[] quadrant = this.quadrant;
        final byte[] block = data.block;
        final int n7 = n4 + 1;
        final boolean firstAttempt = this.firstAttempt;
        final int workLimit = this.workLimit;
        int workDone = this.workDone;
    Label_0905:
        while (--n6 >= 0) {
            final int n8 = BlockSort.INCS[n6];
            final int n9 = n + n8 - 1;
            int i = n + n8;
            while (i <= n2) {
                for (int n10 = 3; i <= n2 && --n10 >= 0; ++i) {
                    final int n11 = fmap[i];
                    final int n12 = n11 + n3;
                    int n13 = i;
                    int n14 = 0;
                    int n15 = 0;
                Label_0176:
                    while (true) {
                        if (n14 != 0) {
                            fmap[n13] = n15;
                            if ((n13 -= n8) <= n9) {
                                break;
                            }
                        }
                        else {
                            n14 = 1;
                        }
                        n15 = fmap[n13 - n8];
                        int n16 = n15 + n3;
                        int n17 = n12;
                        if (block[n16 + 1] == block[n17 + 1]) {
                            if (block[n16 + 2] == block[n17 + 2]) {
                                if (block[n16 + 3] == block[n17 + 3]) {
                                    if (block[n16 + 4] == block[n17 + 4]) {
                                        if (block[n16 + 5] == block[n17 + 5]) {
                                            final byte[] array = block;
                                            n16 += 6;
                                            final byte b = array[n16];
                                            final byte[] array2 = block;
                                            n17 += 6;
                                            if (b == array2[n17]) {
                                                int j = n4;
                                                while (j > 0) {
                                                    j -= 4;
                                                    if (block[n16 + 1] == block[n17 + 1]) {
                                                        if (quadrant[n16] == quadrant[n17]) {
                                                            if (block[n16 + 2] == block[n17 + 2]) {
                                                                if (quadrant[n16 + 1] == quadrant[n17 + 1]) {
                                                                    if (block[n16 + 3] == block[n17 + 3]) {
                                                                        if (quadrant[n16 + 2] == quadrant[n17 + 2]) {
                                                                            if (block[n16 + 4] == block[n17 + 4]) {
                                                                                if (quadrant[n16 + 3] == quadrant[n17 + 3]) {
                                                                                    n16 += 4;
                                                                                    if (n16 >= n7) {
                                                                                        n16 -= n7;
                                                                                    }
                                                                                    n17 += 4;
                                                                                    if (n17 >= n7) {
                                                                                        n17 -= n7;
                                                                                    }
                                                                                    ++workDone;
                                                                                }
                                                                                else {
                                                                                    if (quadrant[n16 + 3] > quadrant[n17 + 3]) {
                                                                                        continue Label_0176;
                                                                                    }
                                                                                    break;
                                                                                }
                                                                            }
                                                                            else {
                                                                                if ((block[n16 + 4] & 0xFF) > (block[n17 + 4] & 0xFF)) {
                                                                                    continue Label_0176;
                                                                                }
                                                                                break;
                                                                            }
                                                                        }
                                                                        else {
                                                                            if (quadrant[n16 + 2] > quadrant[n17 + 2]) {
                                                                                continue Label_0176;
                                                                            }
                                                                            break;
                                                                        }
                                                                    }
                                                                    else {
                                                                        if ((block[n16 + 3] & 0xFF) > (block[n17 + 3] & 0xFF)) {
                                                                            continue Label_0176;
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                                else {
                                                                    if (quadrant[n16 + 1] > quadrant[n17 + 1]) {
                                                                        continue Label_0176;
                                                                    }
                                                                    break;
                                                                }
                                                            }
                                                            else {
                                                                if ((block[n16 + 2] & 0xFF) > (block[n17 + 2] & 0xFF)) {
                                                                    continue Label_0176;
                                                                }
                                                                break;
                                                            }
                                                        }
                                                        else {
                                                            if (quadrant[n16] > quadrant[n17]) {
                                                                continue Label_0176;
                                                            }
                                                            break;
                                                        }
                                                    }
                                                    else {
                                                        if ((block[n16 + 1] & 0xFF) > (block[n17 + 1] & 0xFF)) {
                                                            continue Label_0176;
                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            }
                                            if ((block[n16] & 0xFF) > (block[n17] & 0xFF)) {
                                                continue;
                                            }
                                            break;
                                        }
                                        else {
                                            if ((block[n16 + 5] & 0xFF) > (block[n17 + 5] & 0xFF)) {
                                                continue;
                                            }
                                            break;
                                        }
                                    }
                                    else {
                                        if ((block[n16 + 4] & 0xFF) > (block[n17 + 4] & 0xFF)) {
                                            continue;
                                        }
                                        break;
                                    }
                                }
                                else {
                                    if ((block[n16 + 3] & 0xFF) > (block[n17 + 3] & 0xFF)) {
                                        continue;
                                    }
                                    break;
                                }
                            }
                            else {
                                if ((block[n16 + 2] & 0xFF) > (block[n17 + 2] & 0xFF)) {
                                    continue;
                                }
                                break;
                            }
                        }
                        else {
                            if ((block[n16 + 1] & 0xFF) > (block[n17 + 1] & 0xFF)) {
                                continue;
                            }
                            break;
                        }
                    }
                    fmap[n13] = n11;
                }
                if (firstAttempt && i <= n2 && workDone > workLimit) {
                    break Label_0905;
                }
            }
        }
        this.workDone = workDone;
        return firstAttempt && workDone > workLimit;
    }
    
    private static void vswap(final int[] array, int i, int n, int n2) {
        int n3;
        for (n2 += i; i < n2; array[i++] = array[n], array[n++] = n3) {
            n3 = array[i];
        }
    }
    
    private static byte med3(final byte b, final byte b2, final byte b3) {
        return (b < b2) ? ((b2 < b3) ? b2 : ((b < b3) ? b3 : b)) : ((b2 > b3) ? b2 : ((b > b3) ? b3 : b));
    }
    
    private void mainQSort3(final BZip2CompressorOutputStream.Data data, final int n, final int n2, final int n3, final int n4) {
        final int[] stack_ll = this.stack_ll;
        final int[] stack_hh = this.stack_hh;
        final int[] stack_dd = this.stack_dd;
        final int[] fmap = data.fmap;
        final byte[] block = data.block;
        stack_ll[0] = n;
        stack_hh[0] = n2;
        stack_dd[0] = n3;
        int n5 = 1;
        while (--n5 >= 0) {
            final int n6 = stack_ll[n5];
            final int n7 = stack_hh[n5];
            final int n8 = stack_dd[n5];
            if (n7 - n6 < 20 || n8 > 10) {
                if (this.mainSimpleSort(data, n6, n7, n8, n4)) {
                    return;
                }
                continue;
            }
            else {
                final int n9 = n8 + 1;
                final int n10 = med3(block[fmap[n6] + n9], block[fmap[n7] + n9], block[fmap[n6 + n7 >>> 1] + n9]) & 0xFF;
                int i = n6;
                int n11 = n7;
                int n12 = n6;
                int n13 = n7;
                while (true) {
                    Label_0257: {
                        if (i <= n11) {
                            final int n14 = (block[fmap[i] + n9] & 0xFF) - n10;
                            if (n14 == 0) {
                                final int n15 = fmap[i];
                                fmap[i++] = fmap[n12];
                                fmap[n12++] = n15;
                            }
                            else {
                                if (n14 >= 0) {
                                    break Label_0257;
                                }
                                ++i;
                            }
                            continue;
                        }
                    }
                    while (i <= n11) {
                        final int n16 = (block[fmap[n11] + n9] & 0xFF) - n10;
                        if (n16 == 0) {
                            final int n17 = fmap[n11];
                            fmap[n11--] = fmap[n13];
                            fmap[n13--] = n17;
                        }
                        else {
                            if (n16 <= 0) {
                                break;
                            }
                            --n11;
                        }
                    }
                    if (i > n11) {
                        break;
                    }
                    final int n18 = fmap[i];
                    fmap[i++] = fmap[n11];
                    fmap[n11--] = n18;
                }
                if (n13 < n12) {
                    stack_ll[n5] = n6;
                    stack_hh[n5] = n7;
                    stack_dd[n5] = n9;
                    ++n5;
                }
                else {
                    final int n19 = (n12 - n6 < i - n12) ? (n12 - n6) : (i - n12);
                    vswap(fmap, n6, i - n19, n19);
                    final int n20 = (n7 - n13 < n13 - n11) ? (n7 - n13) : (n13 - n11);
                    vswap(fmap, i, n7 - n20 + 1, n20);
                    final int n21 = n6 + i - n12 - 1;
                    final int n22 = n7 - (n13 - n11) + 1;
                    stack_ll[n5] = n6;
                    stack_hh[n5] = n21;
                    stack_dd[n5] = n8;
                    ++n5;
                    stack_ll[n5] = n21 + 1;
                    stack_hh[n5] = n22 - 1;
                    stack_dd[n5] = n9;
                    ++n5;
                    stack_ll[n5] = n22;
                    stack_hh[n5] = n7;
                    stack_dd[n5] = n8;
                    ++n5;
                }
            }
        }
    }
    
    final void mainSort(final BZip2CompressorOutputStream.Data data, final int n) {
        final int[] mainSort_runningOrder = this.mainSort_runningOrder;
        final int[] mainSort_copy = this.mainSort_copy;
        final boolean[] mainSort_bigDone = this.mainSort_bigDone;
        final int[] ftab = this.ftab;
        final byte[] block = data.block;
        final int[] fmap = data.fmap;
        final char[] quadrant = this.quadrant;
        final int workLimit = this.workLimit;
        final boolean firstAttempt = this.firstAttempt;
        int n2 = 65537;
        while (--n2 >= 0) {
            ftab[n2] = 0;
        }
        for (int i = 0; i < 20; ++i) {
            block[n + i + 2] = block[i % (n + 1) + 1];
        }
        int n3 = n + 20 + 1;
        while (--n3 >= 0) {
            quadrant[n3] = '\0';
        }
        block[0] = block[n + 1];
        int n4 = block[0] & 0xFF;
        for (int j = 0; j <= n; ++j) {
            final int n5 = block[j + 1] & 0xFF;
            final int[] array = ftab;
            final int n6 = (n4 << 8) + n5;
            ++array[n6];
            n4 = n5;
        }
        for (int k = 1; k <= 65536; ++k) {
            final int[] array2 = ftab;
            final int n7 = k;
            array2[n7] += ftab[k - 1];
        }
        int n8 = block[1] & 0xFF;
        for (int l = 0; l < n; ++l) {
            final int n9 = block[l + 2] & 0xFF;
            final int[] array3 = fmap;
            final int[] array4 = ftab;
            final int n10 = (n8 << 8) + n9;
            array3[--array4[n10]] = l;
            n8 = n9;
        }
        final int[] array5 = fmap;
        final int[] array6 = ftab;
        final int n11 = ((block[n + 1] & 0xFF) << 8) + (block[1] & 0xFF);
        array5[--array6[n11]] = n;
        int n12 = 256;
        while (--n12 >= 0) {
            mainSort_bigDone[n12] = false;
            mainSort_runningOrder[n12] = n12;
        }
        int n13 = 364;
        while (n13 != 1) {
            int n14;
            for (n13 = (n14 = n13 / 3); n14 <= 255; ++n14) {
                final int n15 = mainSort_runningOrder[n14];
                final int n16 = ftab[n15 + 1 << 8] - ftab[n15 << 8];
                final int n17 = n13 - 1;
                int n18 = n14;
                for (int n19 = mainSort_runningOrder[n18 - n13]; ftab[n19 + 1 << 8] - ftab[n19 << 8] > n16; n19 = mainSort_runningOrder[n18 - n13]) {
                    mainSort_runningOrder[n18] = n19;
                    n18 -= n13;
                    if (n18 <= n17) {
                        break;
                    }
                }
                mainSort_runningOrder[n18] = n15;
            }
        }
        for (int n20 = 0; n20 <= 255; ++n20) {
            final int n21 = mainSort_runningOrder[n20];
            for (int n22 = 0; n22 <= 255; ++n22) {
                final int n23 = (n21 << 8) + n22;
                final int n24 = ftab[n23];
                if ((n24 & 0x200000) != 0x200000) {
                    final int n25 = n24 & 0xFFDFFFFF;
                    final int n26 = (ftab[n23 + 1] & 0xFFDFFFFF) - 1;
                    if (n26 > n25) {
                        this.mainQSort3(data, n25, n26, 2, n);
                        if (firstAttempt && this.workDone > workLimit) {
                            return;
                        }
                    }
                    ftab[n23] = (n24 | 0x200000);
                }
            }
            for (int n27 = 0; n27 <= 255; ++n27) {
                mainSort_copy[n27] = (ftab[(n27 << 8) + n21] & 0xFFDFFFFF);
            }
            for (int n28 = ftab[n21 << 8] & 0xFFDFFFFF; n28 < (ftab[n21 + 1 << 8] & 0xFFDFFFFF); ++n28) {
                final int n29 = fmap[n28];
                final int n30 = block[n29] & 0xFF;
                if (!mainSort_bigDone[n30]) {
                    fmap[mainSort_copy[n30]] = ((n29 == 0) ? n : (n29 - 1));
                    final int[] array7 = mainSort_copy;
                    final int n31 = n30;
                    ++array7[n31];
                }
            }
            int n32 = 256;
            while (--n32 >= 0) {
                final int[] array8 = ftab;
                final int n33 = (n32 << 8) + n21;
                array8[n33] |= 0x200000;
            }
            mainSort_bigDone[n21] = true;
            if (n20 < 255) {
                final int n34 = ftab[n21 << 8] & 0xFFDFFFFF;
                int n35;
                int n36;
                for (n35 = (ftab[n21 + 1 << 8] & 0xFFDFFFFF) - n34, n36 = 0; n35 >> n36 > 65534; ++n36) {}
                for (int n37 = 0; n37 < n35; ++n37) {
                    final int n38 = fmap[n34 + n37];
                    final char c = (char)(n37 >> n36);
                    quadrant[n38] = c;
                    if (n38 < 20) {
                        quadrant[n38 + n + 1] = c;
                    }
                }
            }
        }
    }
    
    static {
        INCS = new int[] { 1, 4, 13, 40, 121, 364, 1093, 3280, 9841, 29524, 88573, 265720, 797161, 2391484 };
    }
}
