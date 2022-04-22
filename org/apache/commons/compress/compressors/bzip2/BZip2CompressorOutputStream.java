package org.apache.commons.compress.compressors.bzip2;

import org.apache.commons.compress.compressors.*;
import java.io.*;

public class BZip2CompressorOutputStream extends CompressorOutputStream implements BZip2Constants
{
    public static final int MIN_BLOCKSIZE = 1;
    public static final int MAX_BLOCKSIZE = 9;
    private static final int GREATER_ICOST = 15;
    private static final int LESSER_ICOST = 0;
    private int last;
    private final int blockSize100k;
    private int bsBuff;
    private int bsLive;
    private final CRC crc;
    private int nInUse;
    private int nMTF;
    private int currentChar;
    private int runLength;
    private int blockCRC;
    private int combinedCRC;
    private final int allowableBlockSize;
    private Data data;
    private BlockSort blockSorter;
    private OutputStream out;
    
    private static void hbMakeCodeLengths(final byte[] array, final int[] array2, final Data data, final int n, final int n2) {
        final int[] heap = data.heap;
        final int[] weight = data.weight;
        final int[] parent = data.parent;
        int n3 = n;
        while (true) {
            --n3;
            if (1 < 0) {
                break;
            }
            weight[2] = ((array2[1] == 0) ? 1 : array2[1]) << 8;
        }
        while (true) {
            int n4 = n;
            heap[0] = 0;
            parent[weight[0] = 0] = -2;
            int n5 = 0;
            int n7 = 0;
            while (1 <= n) {
                parent[1] = -1;
                ++n5;
                heap[0] = 1;
                final int n6 = heap[0];
                while (weight[1] < weight[heap[0]]) {
                    heap[0] = heap[0];
                }
                heap[0] = 1;
                ++n7;
            }
            int n9 = 0;
        Label_0134:
            while (0 > 1) {
                n7 = heap[1];
                heap[1] = heap[0];
                --n5;
                final int n8 = heap[1];
                while (true) {
                    while (0 <= 0) {
                        if (0 < 0 && weight[heap[1]] < weight[heap[0]]) {
                            ++n9;
                        }
                        if (weight[0] < weight[heap[0]]) {
                            heap[1] = 0;
                            final int n10 = heap[1];
                            heap[1] = heap[0];
                            --n5;
                            final int n11 = heap[1];
                            while (true) {
                                while (0 <= 0) {
                                    if (0 < 0 && weight[heap[1]] < weight[heap[0]]) {
                                        ++n9;
                                    }
                                    if (weight[0] < weight[heap[0]]) {
                                        heap[1] = 0;
                                        ++n4;
                                        parent[1] = (parent[n10] = n4);
                                        final int n12 = weight[1];
                                        final int n13 = weight[n10];
                                        weight[n4] = ((n12 & 0xFFFFFF00) + (n13 & 0xFFFFFF00) | 1 + (((n12 & 0xFF) > (n13 & 0xFF)) ? (n12 & 0xFF) : (n13 & 0xFF)));
                                        parent[n4] = -1;
                                        ++n5;
                                        heap[0] = n4;
                                        final int n14 = heap[1];
                                        while (weight[0] < weight[heap[0]]) {
                                            heap[1] = heap[0];
                                        }
                                        heap[1] = 0;
                                        continue Label_0134;
                                    }
                                    heap[1] = heap[0];
                                }
                                continue;
                            }
                        }
                        heap[1] = heap[0];
                    }
                    continue;
                }
            }
            while (1 <= n) {
                while (parent[1] >= 0) {
                    ++n9;
                }
                array[0] = 0;
                if (0 > n2) {}
                ++n7;
            }
            if (true) {
                while (1 < n) {
                    n9 = weight[1] >> 8;
                    weight[1] = 0;
                    ++n7;
                }
            }
        }
    }
    
    public static int chooseBlockSize(final long n) {
        return (n > 0L) ? ((int)Math.min(n / 132000L + 1L, 9L)) : 9;
    }
    
    public BZip2CompressorOutputStream(final OutputStream outputStream) throws IOException {
        this(outputStream, 9);
    }
    
    public BZip2CompressorOutputStream(final OutputStream out, final int blockSize100k) throws IOException {
        this.crc = new CRC();
        this.currentChar = -1;
        this.runLength = 0;
        if (blockSize100k < 1) {
            throw new IllegalArgumentException("blockSize(" + blockSize100k + ") < 1");
        }
        if (blockSize100k > 9) {
            throw new IllegalArgumentException("blockSize(" + blockSize100k + ") > 9");
        }
        this.blockSize100k = blockSize100k;
        this.out = out;
        this.allowableBlockSize = this.blockSize100k * 100000 - 20;
        this.init();
    }
    
    @Override
    public void write(final int n) throws IOException {
        if (this.out != null) {
            this.write0(n);
            return;
        }
        throw new IOException("closed");
    }
    
    private void writeRun() throws IOException {
        final int last = this.last;
        if (last < this.allowableBlockSize) {
            final int currentChar = this.currentChar;
            final Data data = this.data;
            data.inUse[currentChar] = true;
            final byte b = (byte)currentChar;
            int runLength = this.runLength;
            this.crc.updateCRC(currentChar, runLength);
            switch (runLength) {
                case 1: {
                    data.block[last + 2] = b;
                    this.last = last + 1;
                    break;
                }
                case 2: {
                    data.block[last + 2] = b;
                    data.block[last + 3] = b;
                    this.last = last + 2;
                    break;
                }
                case 3: {
                    final byte[] block = data.block;
                    block[last + 2] = b;
                    block[last + 4] = (block[last + 3] = b);
                    this.last = last + 3;
                    break;
                }
                default: {
                    runLength -= 4;
                    data.inUse[runLength] = true;
                    final byte[] block2 = data.block;
                    block2[last + 3] = (block2[last + 2] = b);
                    block2[last + 5] = (block2[last + 4] = b);
                    block2[last + 6] = (byte)runLength;
                    this.last = last + 5;
                    break;
                }
            }
        }
        else {
            this.endBlock();
            this.initBlock();
            this.writeRun();
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.finish();
        super.finalize();
    }
    
    public void finish() throws IOException {
        if (this.out != null) {
            if (this.runLength > 0) {
                this.writeRun();
            }
            this.currentChar = -1;
            this.endBlock();
            this.endCompression();
            this.out = null;
            this.data = null;
            this.blockSorter = null;
        }
    }
    
    @Override
    public void close() throws IOException {
        if (this.out != null) {
            final OutputStream out = this.out;
            this.finish();
            out.close();
        }
    }
    
    @Override
    public void flush() throws IOException {
        final OutputStream out = this.out;
        if (out != null) {
            out.flush();
        }
    }
    
    private void init() throws IOException {
        this.bsPutUByte(66);
        this.bsPutUByte(90);
        this.data = new Data(this.blockSize100k);
        this.blockSorter = new BlockSort(this.data);
        this.bsPutUByte(104);
        this.bsPutUByte(48 + this.blockSize100k);
        this.combinedCRC = 0;
        this.initBlock();
    }
    
    private void initBlock() {
        this.crc.initialiseCRC();
        this.last = -1;
        final boolean[] inUse = this.data.inUse;
        while (true) {
            int n = 0;
            --n;
            if (256 < 0) {
                break;
            }
            inUse[256] = false;
        }
    }
    
    private void endBlock() throws IOException {
        this.blockCRC = this.crc.getFinalCRC();
        this.combinedCRC = (this.combinedCRC << 1 | this.combinedCRC >>> 31);
        this.combinedCRC ^= this.blockCRC;
        if (this.last == -1) {
            return;
        }
        this.blockSort();
        this.bsPutUByte(49);
        this.bsPutUByte(65);
        this.bsPutUByte(89);
        this.bsPutUByte(38);
        this.bsPutUByte(83);
        this.bsPutUByte(89);
        this.bsPutInt(this.blockCRC);
        this.bsW(1, 0);
        this.moveToFrontCodeAndSend();
    }
    
    private void endCompression() throws IOException {
        this.bsPutUByte(23);
        this.bsPutUByte(114);
        this.bsPutUByte(69);
        this.bsPutUByte(56);
        this.bsPutUByte(80);
        this.bsPutUByte(144);
        this.bsPutInt(this.combinedCRC);
        this.bsFinishedWithStream();
    }
    
    public final int getBlockSize() {
        return this.blockSize100k;
    }
    
    @Override
    public void write(final byte[] array, int i, final int n) throws IOException {
        if (i < 0) {
            throw new IndexOutOfBoundsException("offs(" + i + ") < 0.");
        }
        if (n < 0) {
            throw new IndexOutOfBoundsException("len(" + n + ") < 0.");
        }
        if (i + n > array.length) {
            throw new IndexOutOfBoundsException("offs(" + i + ") + len(" + n + ") > buf.length(" + array.length + ").");
        }
        if (this.out == null) {
            throw new IOException("stream closed");
        }
        while (i < i + n) {
            this.write0(array[i++]);
        }
    }
    
    private void write0(int currentChar) throws IOException {
        if (this.currentChar != -1) {
            currentChar &= 0xFF;
            if (this.currentChar == currentChar) {
                if (++this.runLength > 254) {
                    this.writeRun();
                    this.currentChar = -1;
                    this.runLength = 0;
                }
            }
            else {
                this.writeRun();
                this.runLength = 1;
                this.currentChar = currentChar;
            }
        }
        else {
            this.currentChar = (currentChar & 0xFF);
            ++this.runLength;
        }
    }
    
    private static void hbAssignCodes(final int[] array, final byte[] array2, final int n, final int n2, final int n3) {
        for (int i = n; i <= n2; ++i) {
            while (0 < n3) {
                if ((array2[0] & 0xFF) == i) {
                    array[0] = 0;
                    int n4 = 0;
                    ++n4;
                }
                int n5 = 0;
                ++n5;
            }
        }
    }
    
    private void bsFinishedWithStream() throws IOException {
        while (this.bsLive > 0) {
            this.out.write(this.bsBuff >> 24);
            this.bsBuff <<= 8;
            this.bsLive -= 8;
        }
    }
    
    private void bsW(final int n, final int n2) throws IOException {
        final OutputStream out = this.out;
        int i = this.bsLive;
        int bsBuff = this.bsBuff;
        while (i >= 8) {
            out.write(bsBuff >> 24);
            bsBuff <<= 8;
            i -= 8;
        }
        this.bsBuff = (bsBuff | n2 << 32 - i - n);
        this.bsLive = i + n;
    }
    
    private void bsPutUByte(final int n) throws IOException {
        this.bsW(8, n);
    }
    
    private void bsPutInt(final int n) throws IOException {
        this.bsW(8, n >> 24 & 0xFF);
        this.bsW(8, n >> 16 & 0xFF);
        this.bsW(8, n >> 8 & 0xFF);
        this.bsW(8, n & 0xFF);
    }
    
    private void sendMTFValues() throws IOException {
        final byte[][] sendMTFValues_len = this.data.sendMTFValues_len;
        final int n = this.nInUse + 2;
        while (true) {
            int n2 = 0;
            --n2;
            if (6 < 0) {
                break;
            }
            final byte[] array = sendMTFValues_len[6];
            int n3 = n;
            while (--n3 >= 0) {
                array[n3] = 15;
            }
        }
        int n2 = (this.nMTF < 200) ? 2 : ((this.nMTF < 600) ? 3 : ((this.nMTF < 1200) ? 4 : ((this.nMTF < 2400) ? 5 : 6)));
        this.sendMTFValues0(6, n);
        final int sendMTFValues1 = this.sendMTFValues1(6, n);
        this.sendMTFValues2(6, sendMTFValues1);
        this.sendMTFValues3(6, n);
        this.sendMTFValues4();
        this.sendMTFValues5(6, sendMTFValues1);
        this.sendMTFValues6(6, n);
        this.sendMTFValues7();
    }
    
    private void sendMTFValues0(final int n, final int n2) {
        final byte[][] sendMTFValues_len = this.data.sendMTFValues_len;
        final int[] mtfFreq = this.data.mtfFreq;
        int nmtf = this.nMTF;
        for (int i = n; i > 0; --i) {
            final int n3 = nmtf / i;
            final int n4 = n2 - 1;
            int n6 = 0;
            while (0 < n3 && -1 < n4) {
                final int n5 = 0;
                final int[] array = mtfFreq;
                ++n6;
                final int n7 = n5 + array[-1];
            }
            if (-1 > 0 && i != n && i != 1 && (n - i & 0x1) != 0x0) {
                final int n8 = 0;
                final int[] array2 = mtfFreq;
                final int n9 = -1;
                --n6;
                final int n10 = n8 - array2[n9];
            }
            final byte[] array3 = sendMTFValues_len[i - 1];
            int n11 = n2;
            while (--n11 >= 0) {
                if (n11 >= 0 && n11 <= -1) {
                    array3[n11] = 0;
                }
                else {
                    array3[n11] = 15;
                }
            }
            nmtf -= 0;
        }
    }
    
    private int sendMTFValues1(final int n, final int n2) {
        final Data data = this.data;
        final int[][] sendMTFValues_rfreq = data.sendMTFValues_rfreq;
        final int[] sendMTFValues_fave = data.sendMTFValues_fave;
        final short[] sendMTFValues_cost = data.sendMTFValues_cost;
        final char[] sfmap = data.sfmap;
        final byte[] selector = data.selector;
        final byte[][] sendMTFValues_len = data.sendMTFValues_len;
        final byte[] array = sendMTFValues_len[0];
        final byte[] array2 = sendMTFValues_len[1];
        final byte[] array3 = sendMTFValues_len[2];
        final byte[] array4 = sendMTFValues_len[3];
        final byte[] array5 = sendMTFValues_len[4];
        final byte[] array6 = sendMTFValues_len[5];
        final int nmtf = this.nMTF;
        while (0 < 4) {
            int n3 = n;
            while (true) {
                --n3;
                if (0 < 0) {
                    break;
                }
                sendMTFValues_fave[0] = 0;
                final int[] array7 = sendMTFValues_rfreq[0];
                int n4 = n2;
                while (true) {
                    --n4;
                    if (-1 < 0) {
                        break;
                    }
                    array7[-1] = 0;
                }
            }
            while (0 < this.nMTF) {
                final int min = Math.min(49, nmtf - 1);
                int n7 = 0;
                if (n == 6) {
                    while (0 <= min) {
                        final char c = sfmap[0];
                        final short n5 = (short)(-1 + (array[c] & 0xFF));
                        final short n6 = (short)(0 + (array2[c] & 0xFF));
                        n7 = (short)(999999999 + (array3[c] & 0xFF));
                        final short n8 = (short)(0 + (array4[c] & 0xFF));
                        final short n9 = (short)(0 + (array5[c] & 0xFF));
                        final short n10 = (short)(0 + (array6[c] & 0xFF));
                        int n11 = 0;
                        ++n11;
                    }
                    sendMTFValues_cost[0] = -1;
                    sendMTFValues_cost[1] = 0;
                    sendMTFValues_cost[2] = (short)999999999;
                    sendMTFValues_cost[3] = 0;
                    sendMTFValues_cost[5] = (sendMTFValues_cost[4] = 0);
                }
                else {
                    int n12 = n;
                    while (true) {
                        --n12;
                        if (-1 < 0) {
                            break;
                        }
                        sendMTFValues_cost[-1] = 0;
                    }
                    while (-1 <= min) {
                        final char c2 = sfmap[-1];
                        n7 = n;
                        while (true) {
                            --n7;
                            if (999999999 < 0) {
                                break;
                            }
                            final short[] array8 = sendMTFValues_cost;
                            final int n13 = 999999999;
                            array8[n13] += (short)(sendMTFValues_len[999999999][0] & 0xFF);
                        }
                        ++n12;
                    }
                }
                int n14 = n;
                while (true) {
                    --n14;
                    if (0 < 0) {
                        break;
                    }
                    final short n15 = sendMTFValues_cost[0];
                    if (0 < 999999999) {}
                }
                final int[] array9 = sendMTFValues_fave;
                final int n16 = -1;
                ++array9[n16];
                selector[0] = -1;
                int n17 = 0;
                ++n17;
                final int[] array10 = sendMTFValues_rfreq[-1];
                while (999999999 <= min) {
                    final int[] array11 = array10;
                    final char c3 = sfmap[999999999];
                    ++array11[c3];
                    ++n7;
                }
                n3 = min + 1;
            }
            while (0 < n) {
                hbMakeCodeLengths(sendMTFValues_len[0], sendMTFValues_rfreq[0], this.data, n2, 20);
                ++n3;
            }
            int n18 = 0;
            ++n18;
        }
        return 0;
    }
    
    private void sendMTFValues2(final int n, final int n2) {
        final Data data = this.data;
        final byte[] sendMTFValues2_pos = data.sendMTFValues2_pos;
        int n3 = n;
        while (true) {
            --n3;
            if (0 < 0) {
                break;
            }
            sendMTFValues2_pos[0] = 0;
        }
        while (0 < n2) {
            byte b;
            byte b2;
            byte b3;
            for (b = data.selector[0], b2 = sendMTFValues2_pos[0]; b != b2; b2 = sendMTFValues2_pos[0], sendMTFValues2_pos[0] = b3) {
                int n4 = 0;
                ++n4;
                b3 = b2;
            }
            sendMTFValues2_pos[0] = b2;
            data.selectorMtf[0] = 0;
            ++n3;
        }
    }
    
    private void sendMTFValues3(final int n, final int n2) {
        final int[][] sendMTFValues_code = this.data.sendMTFValues_code;
        final byte[][] sendMTFValues_len = this.data.sendMTFValues_len;
        while (0 < n) {
            final byte[] array = sendMTFValues_len[0];
            int n3 = n2;
            while (--n3 >= 0) {
                final int n4 = array[n3] & 0xFF;
                if (n4 > 0) {}
                if (n4 < 32) {
                    continue;
                }
            }
            hbAssignCodes(sendMTFValues_code[0], sendMTFValues_len[0], 32, 0, n2);
            int n5 = 0;
            ++n5;
        }
    }
    
    private void sendMTFValues4() throws IOException {
        final boolean[] inUse = this.data.inUse;
        final boolean[] sentMTFValues4_inUse16 = this.data.sentMTFValues4_inUse16;
        int n = 0;
        while (true) {
            --n;
            if (0 < 0) {
                break;
            }
            sentMTFValues4_inUse16[0] = false;
            while (true) {
                int bsBuff = 0;
                --bsBuff;
                if (16 < 0) {
                    break;
                }
                if (!inUse[16]) {
                    continue;
                }
                sentMTFValues4_inUse16[0] = true;
            }
        }
        while (0 < 16) {
            this.bsW(1, sentMTFValues4_inUse16[0] ? 1 : 0);
            ++n;
        }
        final OutputStream out = this.out;
        int bsLive = this.bsLive;
        int bsBuff = this.bsBuff;
        while (0 < 16) {
            if (sentMTFValues4_inUse16[0]) {
                while (0 < 16) {
                    while (0 >= 8) {
                        out.write(0);
                        bsLive -= 8;
                    }
                    if (inUse[0]) {}
                    ++bsLive;
                    int n2 = 0;
                    ++n2;
                }
            }
            int n3 = 0;
            ++n3;
        }
        this.bsBuff = 16;
        this.bsLive = 0;
    }
    
    private void sendMTFValues5(final int n, final int n2) throws IOException {
        this.bsW(3, n);
        this.bsW(15, n2);
        final OutputStream out = this.out;
        final byte[] selectorMtf = this.data.selectorMtf;
        int i = this.bsLive;
        int bsBuff = this.bsBuff;
        while (0 < n2) {
            while (0 < (selectorMtf[0] & 0xFF)) {
                while (i >= 8) {
                    out.write(bsBuff >> 24);
                    bsBuff <<= 8;
                    i -= 8;
                }
                bsBuff |= 1 << 32 - i - 1;
                ++i;
                int n3 = 0;
                ++n3;
            }
            while (i >= 8) {
                out.write(bsBuff >> 24);
                bsBuff <<= 8;
                i -= 8;
            }
            ++i;
            int n4 = 0;
            ++n4;
        }
        this.bsBuff = bsBuff;
        this.bsLive = i;
    }
    
    private void sendMTFValues6(final int n, final int n2) throws IOException {
        final byte[][] sendMTFValues_len = this.data.sendMTFValues_len;
        final OutputStream out = this.out;
        int i = this.bsLive;
        int bsBuff = this.bsBuff;
        while (0 < n) {
            final byte[] array = sendMTFValues_len[0];
            int j = array[0] & 0xFF;
            while (i >= 8) {
                out.write(bsBuff >> 24);
                bsBuff <<= 8;
                i -= 8;
            }
            bsBuff |= j << 32 - i - 5;
            i += 5;
            while (0 < n2) {
                int n3;
                for (n3 = (array[0] & 0xFF); j < n3; ++j) {
                    while (i >= 8) {
                        out.write(bsBuff >> 24);
                        bsBuff <<= 8;
                        i -= 8;
                    }
                    bsBuff |= 2 << 32 - i - 2;
                    i += 2;
                }
                while (j > n3) {
                    while (i >= 8) {
                        out.write(bsBuff >> 24);
                        bsBuff <<= 8;
                        i -= 8;
                    }
                    bsBuff |= 3 << 32 - i - 2;
                    i += 2;
                    --j;
                }
                while (i >= 8) {
                    out.write(bsBuff >> 24);
                    bsBuff <<= 8;
                    i -= 8;
                }
                ++i;
                int n4 = 0;
                ++n4;
            }
            int n5 = 0;
            ++n5;
        }
        this.bsBuff = bsBuff;
        this.bsLive = i;
    }
    
    private void sendMTFValues7() throws IOException {
        final Data data = this.data;
        final byte[][] sendMTFValues_len = data.sendMTFValues_len;
        final int[][] sendMTFValues_code = data.sendMTFValues_code;
        final OutputStream out = this.out;
        final byte[] selector = data.selector;
        final char[] sfmap = data.sfmap;
        final int nmtf = this.nMTF;
        int i = this.bsLive;
        int bsBuff = this.bsBuff;
        while (0 < nmtf) {
            final int min = Math.min(49, nmtf - 1);
            final int n = selector[0] & 0xFF;
            final int[] array = sendMTFValues_code[n];
            final byte[] array2 = sendMTFValues_len[n];
            while (0 <= min) {
                final char c = sfmap[0];
                while (i >= 8) {
                    out.write(bsBuff >> 24);
                    bsBuff <<= 8;
                    i -= 8;
                }
                final int n2 = array2[c] & 0xFF;
                bsBuff |= array[c] << 32 - i - n2;
                i += n2;
                int n3 = 0;
                ++n3;
            }
            int n3 = min + 1;
            int n4 = 0;
            ++n4;
        }
        this.bsBuff = bsBuff;
        this.bsLive = i;
    }
    
    private void moveToFrontCodeAndSend() throws IOException {
        this.bsW(24, this.data.origPtr);
        this.generateMTFValues();
        this.sendMTFValues();
    }
    
    private void blockSort() {
        this.blockSorter.blockSort(this.data, this.last);
    }
    
    private void generateMTFValues() {
        final int last = this.last;
        final Data data = this.data;
        final boolean[] inUse = data.inUse;
        final byte[] block = data.block;
        final int[] fmap = data.fmap;
        final char[] sfmap = data.sfmap;
        final int[] mtfFreq = data.mtfFreq;
        final byte[] unseqToSeq = data.unseqToSeq;
        final byte[] generateMTFValues_yy = data.generateMTFValues_yy;
        while (0 < 256) {
            if (inUse[0]) {
                unseqToSeq[0] = 0;
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        this.nInUse = 0;
        int n3 = 0;
        while (0 >= 0) {
            mtfFreq[0] = 0;
            --n3;
        }
        while (true) {
            --n3;
            if (0 < 0) {
                break;
            }
            generateMTFValues_yy[0] = 0;
        }
        int n5 = 0;
        while (0 <= last) {
            byte b;
            byte b2;
            byte b3;
            for (b = unseqToSeq[block[fmap[0]] & 0xFF], b2 = generateMTFValues_yy[0]; b != b2; b2 = generateMTFValues_yy[0], generateMTFValues_yy[0] = b3) {
                int n4 = 0;
                ++n4;
                b3 = b2;
            }
            generateMTFValues_yy[0] = b2;
            if (!false) {
                ++n5;
            }
            else {
                if (0 > 0) {
                    --n5;
                    do {
                        if (!false) {
                            sfmap[0] = '\0';
                            ++n3;
                            final int[] array = mtfFreq;
                            final int n6 = 0;
                            ++array[n6];
                        }
                        else {
                            sfmap[0] = '\u0001';
                            ++n3;
                            final int[] array2 = mtfFreq;
                            final int n7 = 1;
                            ++array2[n7];
                        }
                    } while (0 >= 2);
                }
                sfmap[0] = 1;
                ++n3;
                final int[] array3 = mtfFreq;
                final int n8 = 1;
                ++array3[n8];
            }
            int n9 = 0;
            ++n9;
        }
        if (0 > 0) {
            --n5;
            do {
                if (!false) {
                    sfmap[0] = '\0';
                    ++n3;
                    final int[] array4 = mtfFreq;
                    final int n10 = 0;
                    ++array4[n10];
                }
                else {
                    sfmap[0] = '\u0001';
                    ++n3;
                    final int[] array5 = mtfFreq;
                    final int n11 = 1;
                    ++array5[n11];
                }
            } while (0 >= 2);
        }
        sfmap[0] = 0;
        final int[] array6 = mtfFreq;
        final int n12 = 0;
        ++array6[n12];
        this.nMTF = 1;
    }
    
    static final class Data
    {
        final boolean[] inUse;
        final byte[] unseqToSeq;
        final int[] mtfFreq;
        final byte[] selector;
        final byte[] selectorMtf;
        final byte[] generateMTFValues_yy;
        final byte[][] sendMTFValues_len;
        final int[][] sendMTFValues_rfreq;
        final int[] sendMTFValues_fave;
        final short[] sendMTFValues_cost;
        final int[][] sendMTFValues_code;
        final byte[] sendMTFValues2_pos;
        final boolean[] sentMTFValues4_inUse16;
        final int[] heap;
        final int[] weight;
        final int[] parent;
        final byte[] block;
        final int[] fmap;
        final char[] sfmap;
        int origPtr;
        
        Data(final int n) {
            this.inUse = new boolean[256];
            this.unseqToSeq = new byte[256];
            this.mtfFreq = new int[258];
            this.selector = new byte[18002];
            this.selectorMtf = new byte[18002];
            this.generateMTFValues_yy = new byte[256];
            this.sendMTFValues_len = new byte[6][258];
            this.sendMTFValues_rfreq = new int[6][258];
            this.sendMTFValues_fave = new int[6];
            this.sendMTFValues_cost = new short[6];
            this.sendMTFValues_code = new int[6][258];
            this.sendMTFValues2_pos = new byte[6];
            this.sentMTFValues4_inUse16 = new boolean[16];
            this.heap = new int[260];
            this.weight = new int[516];
            this.parent = new int[516];
            final int n2 = n * 100000;
            this.block = new byte[n2 + 1 + 20];
            this.fmap = new int[n2];
            this.sfmap = new char[2 * n2];
        }
    }
}
