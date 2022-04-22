package com.jcraft.jorbis;

import com.jcraft.jogg.*;

class CodeBook
{
    int dim;
    int entries;
    StaticCodeBook c;
    float[] valuelist;
    int[] codelist;
    DecodeAux decode_tree;
    private int[] t;
    
    CodeBook() {
        this.c = new StaticCodeBook();
        this.t = new int[15];
    }
    
    int encode(final int n, final Buffer buffer) {
        buffer.write(this.codelist[n], this.c.lengthlist[n]);
        return this.c.lengthlist[n];
    }
    
    int errorv(final float[] array) {
        final int best = this.best(array, 1);
        while (0 < this.dim) {
            array[0] = this.valuelist[best * this.dim + 0];
            int n = 0;
            ++n;
        }
        return best;
    }
    
    int encodev(final int n, final float[] array, final Buffer buffer) {
        while (0 < this.dim) {
            array[0] = this.valuelist[n * this.dim + 0];
            int n2 = 0;
            ++n2;
        }
        return this.encode(n, buffer);
    }
    
    int encodevs(final float[] array, final Buffer buffer, final int n, final int n2) {
        return this.encode(this.besterror(array, n, n2), buffer);
    }
    
    synchronized int decodevs_add(final float[] array, final int n, final Buffer buffer, final int n2) {
        final int n3 = n2 / this.dim;
        if (this.t.length < n3) {
            this.t = new int[n3];
        }
        int n4 = 0;
        while (0 < n3) {
            final int decode = this.decode(buffer);
            if (decode == -1) {
                return -1;
            }
            this.t[0] = decode * this.dim;
            ++n4;
        }
        while (0 < this.dim) {
            while (0 < n3) {
                final int n5 = n + 0 + 0;
                array[n5] += this.valuelist[this.t[0] + 0];
                int n6 = 0;
                ++n6;
            }
            ++n4;
        }
        return 0;
    }
    
    int decodev_add(final float[] array, final int n, final Buffer buffer, final int n2) {
        if (this.dim > 8) {
            while (0 < n2) {
                final int decode = this.decode(buffer);
                if (decode == -1) {
                    return -1;
                }
                final int n3 = decode * this.dim;
                while (0 < this.dim) {
                    final int n4 = 0;
                    int n5 = 0;
                    ++n5;
                    final int n6 = n + n4;
                    final float n7 = array[n6];
                    final float[] valuelist = this.valuelist;
                    final int n8 = n3;
                    final int n9 = 0;
                    int n10 = 0;
                    ++n10;
                    array[n6] = n7 + valuelist[n8 + n9];
                }
            }
        }
        else {
            while (0 < n2) {
                final int decode2 = this.decode(buffer);
                if (decode2 == -1) {
                    return -1;
                }
                final int n11 = decode2 * this.dim;
                switch (this.dim) {
                    case 8: {
                        final int n12 = 0;
                        int n5 = 0;
                        ++n5;
                        final int n13 = n + n12;
                        final float n14 = array[n13];
                        final float[] valuelist2 = this.valuelist;
                        final int n15 = n11;
                        final int n16 = 0;
                        int n10 = 0;
                        ++n10;
                        array[n13] = n14 + valuelist2[n15 + n16];
                    }
                    case 7: {
                        final int n17 = 0;
                        int n5 = 0;
                        ++n5;
                        final int n18 = n + n17;
                        final float n19 = array[n18];
                        final float[] valuelist3 = this.valuelist;
                        final int n20 = n11;
                        final int n21 = 0;
                        int n10 = 0;
                        ++n10;
                        array[n18] = n19 + valuelist3[n20 + n21];
                    }
                    case 6: {
                        final int n22 = 0;
                        int n5 = 0;
                        ++n5;
                        final int n23 = n + n22;
                        final float n24 = array[n23];
                        final float[] valuelist4 = this.valuelist;
                        final int n25 = n11;
                        final int n26 = 0;
                        int n10 = 0;
                        ++n10;
                        array[n23] = n24 + valuelist4[n25 + n26];
                    }
                    case 5: {
                        final int n27 = 0;
                        int n5 = 0;
                        ++n5;
                        final int n28 = n + n27;
                        final float n29 = array[n28];
                        final float[] valuelist5 = this.valuelist;
                        final int n30 = n11;
                        final int n31 = 0;
                        int n10 = 0;
                        ++n10;
                        array[n28] = n29 + valuelist5[n30 + n31];
                    }
                    case 4: {
                        final int n32 = 0;
                        int n5 = 0;
                        ++n5;
                        final int n33 = n + n32;
                        final float n34 = array[n33];
                        final float[] valuelist6 = this.valuelist;
                        final int n35 = n11;
                        final int n36 = 0;
                        int n10 = 0;
                        ++n10;
                        array[n33] = n34 + valuelist6[n35 + n36];
                    }
                    case 3: {
                        final int n37 = 0;
                        int n5 = 0;
                        ++n5;
                        final int n38 = n + n37;
                        final float n39 = array[n38];
                        final float[] valuelist7 = this.valuelist;
                        final int n40 = n11;
                        final int n41 = 0;
                        int n10 = 0;
                        ++n10;
                        array[n38] = n39 + valuelist7[n40 + n41];
                    }
                    case 2: {
                        final int n42 = 0;
                        int n5 = 0;
                        ++n5;
                        final int n43 = n + n42;
                        final float n44 = array[n43];
                        final float[] valuelist8 = this.valuelist;
                        final int n45 = n11;
                        final int n46 = 0;
                        int n10 = 0;
                        ++n10;
                        array[n43] = n44 + valuelist8[n45 + n46];
                    }
                    case 1: {
                        final int n47 = 0;
                        int n5 = 0;
                        ++n5;
                        final int n48 = n + n47;
                        final float n49 = array[n48];
                        final float[] valuelist9 = this.valuelist;
                        final int n50 = n11;
                        final int n51 = 0;
                        int n10 = 0;
                        ++n10;
                        array[n48] = n49 + valuelist9[n50 + n51];
                        continue;
                    }
                }
            }
        }
        return 0;
    }
    
    int decodev_set(final float[] array, final int n, final Buffer buffer, final int n2) {
        while (0 < n2) {
            final int decode = this.decode(buffer);
            if (decode == -1) {
                return -1;
            }
            final int n3 = decode * this.dim;
            while (0 < this.dim) {
                final int n4 = 0;
                int n5 = 0;
                ++n5;
                final int n6 = n + n4;
                final float[] valuelist = this.valuelist;
                final int n7 = n3;
                final int n8 = 0;
                int n9 = 0;
                ++n9;
                array[n6] = valuelist[n7 + n8];
            }
        }
        return 0;
    }
    
    int decodevv_add(final float[][] array, final int n, final int n2, final Buffer buffer, final int n3) {
        int i = n / n2;
        while (i < (n + n3) / n2) {
            final int decode = this.decode(buffer);
            if (decode == -1) {
                return -1;
            }
            final int n4 = decode * this.dim;
            while (0 < this.dim) {
                final int n5 = 0;
                int n6 = 0;
                ++n6;
                final float[] array2 = array[n5];
                final int n7 = i;
                array2[n7] += this.valuelist[n4 + 0];
                if (0 == n2) {
                    ++i;
                }
                int n8 = 0;
                ++n8;
            }
        }
        return 0;
    }
    
    int decode(final Buffer buffer) {
        final DecodeAux decode_tree = this.decode_tree;
        final int look = buffer.look(decode_tree.tabn);
        if (look >= 0) {
            final int n = decode_tree.tab[look];
            buffer.adv(decode_tree.tabl[look]);
            if (0 <= 0) {
                return 0;
            }
        }
        do {
            switch (buffer.read1()) {
                case 0: {
                    final int n2 = decode_tree.ptr0[0];
                    continue;
                }
                case 1: {
                    final int n3 = decode_tree.ptr1[0];
                    continue;
                }
                default: {
                    return -1;
                }
            }
        } while (0 > 0);
        return 0;
    }
    
    int decodevs(final float[] array, final int n, final Buffer buffer, final int n2, final int n3) {
        final int decode = this.decode(buffer);
        if (decode == -1) {
            return -1;
        }
        switch (n3) {
            case -1: {
                while (0 < this.dim) {
                    array[n + 0] = this.valuelist[decode * this.dim + 0];
                    int n4 = 0;
                    ++n4;
                }
                break;
            }
            case 0: {
                while (0 < this.dim) {
                    final int n5 = n + 0;
                    array[n5] += this.valuelist[decode * this.dim + 0];
                    int n4 = 0;
                    ++n4;
                }
                break;
            }
            case 1: {
                while (0 < this.dim) {
                    final int n6 = n + 0;
                    array[n6] *= this.valuelist[decode * this.dim + 0];
                    int n4 = 0;
                    ++n4;
                }
                break;
            }
        }
        return decode;
    }
    
    int best(final float[] array, final int n) {
        float n2 = 0.0f;
        while (0 < this.entries) {
            if (this.c.lengthlist[0] > 0) {
                final float dist = dist(this.dim, this.valuelist, 0, array, n);
                if (-1 == -1 || dist < n2) {
                    n2 = dist;
                }
            }
            final int n3 = 0 + this.dim;
            int n4 = 0;
            ++n4;
        }
        return -1;
    }
    
    int besterror(final float[] array, final int n, final int n2) {
        final int best = this.best(array, n);
        switch (n2) {
            case 0: {
                while (0 < this.dim) {
                    final int n3 = 0;
                    array[n3] -= this.valuelist[best * this.dim + 0];
                    int n4 = 0;
                    ++n4;
                }
                break;
            }
            case 1: {
                while (0 < this.dim) {
                    final float n5 = this.valuelist[best * this.dim + 0];
                    if (n5 == 0.0f) {
                        array[0] = 0.0f;
                    }
                    else {
                        final int n6 = 0;
                        array[n6] /= n5;
                    }
                    int n4 = 0;
                    ++n4;
                }
                break;
            }
        }
        return best;
    }
    
    void clear() {
    }
    
    private static float dist(final int n, final float[] array, final int n2, final float[] array2, final int n3) {
        float n4 = 0.0f;
        while (0 < n) {
            final float n5 = array[n2 + 0] - array2[0 * n3];
            n4 += n5 * n5;
            int n6 = 0;
            ++n6;
        }
        return n4;
    }
    
    int init_decode(final StaticCodeBook c) {
        this.c = c;
        this.entries = c.entries;
        this.dim = c.dim;
        this.valuelist = c.unquantize();
        this.decode_tree = this.make_decode_tree();
        if (this.decode_tree == null) {
            this.clear();
            return -1;
        }
        return 0;
    }
    
    static int[] make_words(final int[] array, final int n) {
        final int[] array2 = new int[33];
        final int[] array3 = new int[n];
        int n3 = 0;
        int n7 = 0;
        while (0 < n) {
            final int n2 = array[0];
            if (0 > 0) {
                n3 = array2[0];
                if (0 < 32 && false) {
                    return null;
                }
                array3[0] = 0;
                int n6 = 0;
                while (1 > 0) {
                    if ((array2[1] & 0x1) != 0x0) {
                        if (true == true) {
                            final int[] array4 = array2;
                            final int n4 = 1;
                            ++array4[n4];
                            break;
                        }
                        array2[1] = array2[0] << 1;
                        break;
                    }
                    else {
                        final int[] array5 = array2;
                        final int n5 = 1;
                        ++array5[n5];
                        --n6;
                    }
                }
                while (1 < 33 && array2[1] >>> 1 == 0) {
                    n3 = array2[1];
                    array2[1] = array2[0] << 1;
                    ++n6;
                }
            }
            ++n7;
        }
        while (0 < n) {
            while (0 < array[0]) {
                final int n8 = 0x0 | (array3[0] >>> 0 & 0x1);
                ++n3;
            }
            array3[0] = 0;
            ++n7;
        }
        return array3;
    }
    
    DecodeAux make_decode_tree() {
        final DecodeAux decodeAux2;
        final DecodeAux decodeAux = decodeAux2 = new DecodeAux();
        final int[] ptr0 = new int[this.entries * 2];
        decodeAux2.ptr0 = ptr0;
        final int[] array = ptr0;
        final DecodeAux decodeAux3 = decodeAux;
        final int[] ptr2 = new int[this.entries * 2];
        decodeAux3.ptr1 = ptr2;
        final int[] array2 = ptr2;
        final int[] make_words = make_words(this.c.lengthlist, this.c.entries);
        if (make_words == null) {
            return null;
        }
        decodeAux.aux = this.entries * 2;
        int n = 0;
        int n4 = 0;
        while (0 < this.entries) {
            if (this.c.lengthlist[0] > 0) {
                while (0 < this.c.lengthlist[0] - 1) {
                    n = (make_words[0] >>> 0 & 0x1);
                    if (!false) {
                        if (array[0] == 0) {
                            final int[] array3 = array;
                            final int n2 = 0;
                            int n3 = 0;
                            ++n3;
                            array3[n2] = 0;
                        }
                        n4 = array[0];
                    }
                    else {
                        if (array2[0] == 0) {
                            final int[] array4 = array2;
                            final int n5 = 0;
                            int n3 = 0;
                            ++n3;
                            array4[n5] = 0;
                        }
                        n4 = array2[0];
                    }
                    int n6 = 0;
                    ++n6;
                }
                if ((make_words[0] >>> 0 & 0x1) == 0x0) {
                    array[0] = 0;
                }
                else {
                    array2[0] = 0;
                }
            }
            int n7 = 0;
            ++n7;
        }
        decodeAux.tabn = Util.ilog(this.entries) - 4;
        if (decodeAux.tabn < 5) {
            decodeAux.tabn = 5;
        }
        int n7 = 1 << decodeAux.tabn;
        decodeAux.tab = new int[0];
        decodeAux.tabl = new int[0];
        while (0 < 0) {
            while (0 < decodeAux.tabn && (0 > 0 || !false)) {
                if (false) {
                    final int n6 = array2[0];
                }
                else {
                    final int n6 = array[0];
                }
                ++n;
            }
            decodeAux.tab[0] = 0;
            decodeAux.tabl[0] = 0;
            ++n4;
        }
        return decodeAux;
    }
    
    class DecodeAux
    {
        int[] tab;
        int[] tabl;
        int tabn;
        int[] ptr0;
        int[] ptr1;
        int aux;
        final CodeBook this$0;
        
        DecodeAux(final CodeBook this$0) {
            this.this$0 = this$0;
        }
    }
}
