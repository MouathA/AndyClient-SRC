package org.apache.commons.compress.archivers.sevenz;

import java.util.*;

class Folder
{
    Coder[] coders;
    long totalInputStreams;
    long totalOutputStreams;
    BindPair[] bindPairs;
    long[] packedStreams;
    long[] unpackSizes;
    boolean hasCrc;
    long crc;
    int numUnpackSubStreams;
    
    Iterable getOrderedCoders() {
        final LinkedList<Coder> list = new LinkedList<Coder>();
        int bindPairForOutStream;
        for (int i = (int)this.packedStreams[0]; i != -1; i = ((bindPairForOutStream != -1) ? ((int)this.bindPairs[bindPairForOutStream].inIndex) : -1)) {
            list.addLast(this.coders[i]);
            bindPairForOutStream = this.findBindPairForOutStream(i);
        }
        return list;
    }
    
    int findBindPairForInStream(final int n) {
        while (0 < this.bindPairs.length) {
            if (this.bindPairs[0].inIndex == n) {
                return 0;
            }
            int n2 = 0;
            ++n2;
        }
        return -1;
    }
    
    int findBindPairForOutStream(final int n) {
        while (0 < this.bindPairs.length) {
            if (this.bindPairs[0].outIndex == n) {
                return 0;
            }
            int n2 = 0;
            ++n2;
        }
        return -1;
    }
    
    long getUnpackSize() {
        if (this.totalOutputStreams == 0L) {
            return 0L;
        }
        for (int i = (int)this.totalOutputStreams - 1; i >= 0; --i) {
            if (this.findBindPairForOutStream(i) < 0) {
                return this.unpackSizes[i];
            }
        }
        return 0L;
    }
}
