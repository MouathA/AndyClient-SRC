package com.jcraft.jorbis;

import com.jcraft.jogg.*;

class StaticCodeBook
{
    int dim;
    int entries;
    int[] lengthlist;
    int maptype;
    int q_min;
    int q_delta;
    int q_quant;
    int q_sequencep;
    int[] quantlist;
    static final int VQ_FEXP = 10;
    static final int VQ_FMAN = 21;
    static final int VQ_FEXP_BIAS = 768;
    
    int pack(final Buffer buffer) {
        buffer.write(5653314, 24);
        buffer.write(this.dim, 16);
        buffer.write(this.entries, 24);
        int n = 0;
        while (0 < this.entries && this.lengthlist[0] >= this.lengthlist[-1]) {
            ++n;
        }
        if (0 == this.entries) {}
        if (true) {
            buffer.write(1, 1);
            buffer.write(this.lengthlist[0] - 1, 5);
            while (0 < this.entries) {
                final int n2 = this.lengthlist[0];
                final int n3 = this.lengthlist[-1];
                if (n2 > n3) {
                    for (int i = n3; i < n2; ++i) {
                        buffer.write(0, Util.ilog(this.entries - 0));
                    }
                }
                ++n;
            }
            buffer.write(0, Util.ilog(this.entries - 0));
        }
        else {
            buffer.write(0, 1);
            while (0 < this.entries && this.lengthlist[0] != 0) {
                ++n;
            }
            if (0 == this.entries) {
                buffer.write(0, 1);
                while (0 < this.entries) {
                    buffer.write(this.lengthlist[0] - 1, 5);
                    ++n;
                }
            }
            else {
                buffer.write(1, 1);
                while (0 < this.entries) {
                    if (this.lengthlist[0] == 0) {
                        buffer.write(0, 1);
                    }
                    else {
                        buffer.write(1, 1);
                        buffer.write(this.lengthlist[0] - 1, 5);
                    }
                    ++n;
                }
            }
        }
        buffer.write(this.maptype, 4);
        switch (this.maptype) {
            case 0: {
                break;
            }
            case 1:
            case 2: {
                if (this.quantlist == null) {
                    return -1;
                }
                buffer.write(this.q_min, 32);
                buffer.write(this.q_delta, 32);
                buffer.write(this.q_quant - 1, 4);
                buffer.write(this.q_sequencep, 1);
                switch (this.maptype) {
                    case 1: {
                        this.maptype1_quantvals();
                        break;
                    }
                    case 2: {
                        final int n4 = this.entries * this.dim;
                        break;
                    }
                }
                while (0 < 0) {
                    buffer.write(Math.abs(this.quantlist[0]), this.q_quant);
                    ++n;
                }
                break;
            }
            default: {
                return -1;
            }
        }
        return 0;
    }
    
    int unpack(final Buffer buffer) {
        if (buffer.read(24) != 5653314) {
            this.clear();
            return -1;
        }
        this.dim = buffer.read(16);
        this.entries = buffer.read(24);
        if (this.entries == -1) {
            this.clear();
            return -1;
        }
        int n = 0;
        switch (buffer.read(1)) {
            case 0: {
                this.lengthlist = new int[this.entries];
                if (buffer.read(1) != 0) {
                    while (0 < this.entries) {
                        if (buffer.read(1) != 0) {
                            buffer.read(5);
                            if (0 == -1) {
                                this.clear();
                                return -1;
                            }
                            this.lengthlist[0] = 1;
                        }
                        else {
                            this.lengthlist[0] = 0;
                        }
                        ++n;
                    }
                    break;
                }
                while (0 < this.entries) {
                    buffer.read(5);
                    if (0 == -1) {
                        this.clear();
                        return -1;
                    }
                    this.lengthlist[0] = 1;
                    ++n;
                }
                break;
            }
            case 1: {
                int n2 = buffer.read(5) + 1;
                this.lengthlist = new int[this.entries];
                while (0 < this.entries) {
                    final int read = buffer.read(Util.ilog(this.entries - 0));
                    if (read == -1) {
                        this.clear();
                        return -1;
                    }
                    while (0 < read) {
                        this.lengthlist[0] = 0;
                        int n3 = 0;
                        ++n3;
                        ++n;
                    }
                    ++n2;
                }
                break;
            }
            default: {
                return -1;
            }
        }
        switch (this.maptype = buffer.read(4)) {
            case 0: {
                break;
            }
            case 1:
            case 2: {
                this.q_min = buffer.read(32);
                this.q_delta = buffer.read(32);
                this.q_quant = buffer.read(4) + 1;
                this.q_sequencep = buffer.read(1);
                switch (this.maptype) {
                    case 1: {
                        this.maptype1_quantvals();
                        break;
                    }
                    case 2: {
                        final int n4 = this.entries * this.dim;
                        break;
                    }
                }
                this.quantlist = new int[0];
                while (0 < 0) {
                    this.quantlist[0] = buffer.read(this.q_quant);
                    ++n;
                }
                if (this.quantlist[-1] == -1) {
                    this.clear();
                    return -1;
                }
                break;
            }
            default: {
                this.clear();
                return -1;
            }
        }
        return 0;
    }
    
    private int maptype1_quantvals() {
        int n = (int)Math.floor(Math.pow(this.entries, 1.0 / this.dim));
        while (true) {
            if (0 < this.dim) {
                int n2 = 0;
                ++n2;
            }
            else {
                if (1 <= this.entries && 1 > this.entries) {
                    break;
                }
                if (1 > this.entries) {
                    --n;
                }
                else {
                    ++n;
                }
            }
        }
        return n;
    }
    
    void clear() {
    }
    
    float[] unquantize() {
        if (this.maptype == 1 || this.maptype == 2) {
            final float float32_unpack = float32_unpack(this.q_min);
            final float float32_unpack2 = float32_unpack(this.q_delta);
            final float[] array = new float[this.entries * this.dim];
            switch (this.maptype) {
                case 1: {
                    final int maptype1_quantvals = this.maptype1_quantvals();
                    while (0 < this.entries) {
                        float n = 0.0f;
                        while (0 < this.dim) {
                            final float n2 = Math.abs((float)this.quantlist[0 % maptype1_quantvals]) * float32_unpack2 + float32_unpack + n;
                            if (this.q_sequencep != 0) {
                                n = n2;
                            }
                            array[0 * this.dim + 0] = n2;
                            final int n3 = 0 * maptype1_quantvals;
                            int n4 = 0;
                            ++n4;
                        }
                        int n5 = 0;
                        ++n5;
                    }
                    break;
                }
                case 2: {
                    while (0 < this.entries) {
                        float n6 = 0.0f;
                        while (0 < this.dim) {
                            final float n7 = Math.abs((float)this.quantlist[0 * this.dim + 0]) * float32_unpack2 + float32_unpack + n6;
                            if (this.q_sequencep != 0) {
                                n6 = n7;
                            }
                            array[0 * this.dim + 0] = n7;
                            int n3 = 0;
                            ++n3;
                        }
                        int n5 = 0;
                        ++n5;
                    }
                    break;
                }
            }
            return array;
        }
        return null;
    }
    
    static long float32_pack(float n) {
        if (n < 0.0f) {
            n = -n;
        }
        final int n2 = (int)Math.floor(Math.log(n) / Math.log(2.0));
        return Integer.MIN_VALUE | n2 + 768 << 21 | (int)Math.rint(Math.pow(n, 20 - n2));
    }
    
    static float float32_unpack(final int n) {
        float n2 = (float)(n & 0x1FFFFF);
        final float n3 = (float)((n & 0x7FE00000) >>> 21);
        if ((n & Integer.MIN_VALUE) != 0x0) {
            n2 = -n2;
        }
        return ldexp(n2, (int)n3 - 20 - 768);
    }
    
    static float ldexp(final float n, final int n2) {
        return (float)(n * Math.pow(2.0, n2));
    }
}
