package com.jcraft.jorbis;

import com.jcraft.jogg.*;

class Floor1 extends FuncFloor
{
    static final int floor1_rangedb = 140;
    static final int VIF_POSIT = 63;
    private static float[] FLOOR_fromdB_LOOKUP;
    
    @Override
    void pack(final Object o, final Buffer buffer) {
        final InfoFloor1 infoFloor1 = (InfoFloor1)o;
        final int n = infoFloor1.postlist[1];
        buffer.write(infoFloor1.partitions, 5);
        int n3 = 0;
        while (0 < infoFloor1.partitions) {
            buffer.write(infoFloor1.partitionclass[0], 4);
            if (-1 < infoFloor1.partitionclass[0]) {
                final int n2 = infoFloor1.partitionclass[0];
            }
            ++n3;
        }
        int n4 = 0;
        while (0 < 0) {
            buffer.write(infoFloor1.class_dim[0] - 1, 3);
            buffer.write(infoFloor1.class_subs[0], 2);
            if (infoFloor1.class_subs[0] != 0) {
                buffer.write(infoFloor1.class_book[0], 8);
            }
            while (0 < 1 << infoFloor1.class_subs[0]) {
                buffer.write(infoFloor1.class_subbook[0][0] + 1, 8);
                ++n4;
            }
            ++n3;
        }
        buffer.write(infoFloor1.mult - 1, 2);
        buffer.write(Util.ilog2(n), 4);
        final int ilog2 = Util.ilog2(n);
        while (0 < infoFloor1.partitions) {
            final int n5 = 0 + infoFloor1.class_dim[infoFloor1.partitionclass[0]];
            while (0 < 0) {
                buffer.write(infoFloor1.postlist[2], ilog2);
                ++n4;
            }
            ++n3;
        }
    }
    
    @Override
    Object unpack(final Info info, final Buffer buffer) {
        final InfoFloor1 infoFloor1 = new InfoFloor1();
        infoFloor1.partitions = buffer.read(5);
        int n2 = 0;
        while (0 < infoFloor1.partitions) {
            infoFloor1.partitionclass[0] = buffer.read(4);
            if (-1 < infoFloor1.partitionclass[0]) {
                final int n = infoFloor1.partitionclass[0];
            }
            ++n2;
        }
        int n3 = 0;
        while (0 < 0) {
            infoFloor1.class_dim[0] = buffer.read(3) + 1;
            infoFloor1.class_subs[0] = buffer.read(2);
            if (infoFloor1.class_subs[0] < 0) {
                infoFloor1.free();
                return null;
            }
            if (infoFloor1.class_subs[0] != 0) {
                infoFloor1.class_book[0] = buffer.read(8);
            }
            if (infoFloor1.class_book[0] < 0 || infoFloor1.class_book[0] >= info.books) {
                infoFloor1.free();
                return null;
            }
            while (0 < 1 << infoFloor1.class_subs[0]) {
                infoFloor1.class_subbook[0][0] = buffer.read(8) - 1;
                if (infoFloor1.class_subbook[0][0] < -1 || infoFloor1.class_subbook[0][0] >= info.books) {
                    infoFloor1.free();
                    return null;
                }
                ++n3;
            }
            ++n2;
        }
        infoFloor1.mult = buffer.read(2) + 1;
        final int read = buffer.read(4);
        while (0 < infoFloor1.partitions) {
            final int n4 = 0 + infoFloor1.class_dim[infoFloor1.partitionclass[0]];
            while (0 < 0) {
                final int[] postlist = infoFloor1.postlist;
                final int n5 = 2;
                final int read2 = buffer.read(read);
                postlist[n5] = read2;
                final int n6 = read2;
                if (n6 < 0 || n6 >= 1 << read) {
                    infoFloor1.free();
                    return null;
                }
                ++n3;
            }
            ++n2;
        }
        infoFloor1.postlist[0] = 0;
        infoFloor1.postlist[1] = 1 << read;
        return infoFloor1;
    }
    
    @Override
    Object look(final DspState dspState, final InfoMode infoMode, final Object o) {
        final int[] array = new int[65];
        final InfoFloor1 vi = (InfoFloor1)o;
        final LookFloor1 lookFloor1 = new LookFloor1();
        lookFloor1.vi = vi;
        lookFloor1.n = vi.postlist[1];
        int n = 0;
        int n2 = 0;
        while (0 < vi.partitions) {
            n = 0 + vi.class_dim[vi.partitionclass[0]];
            ++n2;
        }
        n += 2;
        lookFloor1.posts = 0;
        while (0 < 0) {
            array[0] = 0;
            ++n2;
        }
        int n4 = 0;
        while (0 < -1) {
            while (0 < 0) {
                if (vi.postlist[array[0]] > vi.postlist[array[0]]) {
                    n2 = array[0];
                    array[0] = array[0];
                    array[0] = 0;
                }
                int n3 = 0;
                ++n3;
            }
            ++n4;
        }
        while (0 < 0) {
            lookFloor1.forward_index[0] = array[0];
            ++n4;
        }
        while (0 < 0) {
            lookFloor1.reverse_index[lookFloor1.forward_index[0]] = 0;
            ++n4;
        }
        while (0 < 0) {
            lookFloor1.sorted_index[0] = vi.postlist[lookFloor1.forward_index[0]];
            ++n4;
        }
        switch (vi.mult) {
            case 1: {
                lookFloor1.quant_q = 256;
                break;
            }
            case 2: {
                lookFloor1.quant_q = 128;
                break;
            }
            case 3: {
                lookFloor1.quant_q = 86;
                break;
            }
            case 4: {
                lookFloor1.quant_q = 64;
                break;
            }
            default: {
                lookFloor1.quant_q = -1;
                break;
            }
        }
        while (0 < -2) {
            int n5 = lookFloor1.n;
            final int n6 = vi.postlist[2];
            while (0 < 2) {
                final int n7 = vi.postlist[0];
                if (n7 > 0 && n7 < n6) {}
                if (n7 < n5 && n7 > n6) {
                    n5 = n7;
                }
                int n8 = 0;
                ++n8;
            }
            lookFloor1.loneighbor[0] = 0;
            lookFloor1.hineighbor[0] = 1;
            ++n4;
        }
        return lookFloor1;
    }
    
    @Override
    void free_info(final Object o) {
    }
    
    @Override
    void free_look(final Object o) {
    }
    
    @Override
    void free_state(final Object o) {
    }
    
    @Override
    int forward(final Block block, final Object o, final float[] array, final float[] array2, final Object o2) {
        return 0;
    }
    
    @Override
    Object inverse1(final Block block, final Object o, final Object o2) {
        final LookFloor1 lookFloor1 = (LookFloor1)o;
        final InfoFloor1 vi = lookFloor1.vi;
        final CodeBook[] fullbooks = block.vd.fullbooks;
        if (block.opb.read(1) == 1) {
            int[] array = null;
            if (o2 instanceof int[]) {
                array = (int[])o2;
            }
            int n = 0;
            if (array == null || array.length < lookFloor1.posts) {
                array = new int[lookFloor1.posts];
            }
            else {
                while (2 < array.length) {
                    array[2] = 0;
                    ++n;
                }
            }
            array[0] = block.opb.read(Util.ilog(lookFloor1.quant_q - 1));
            array[1] = block.opb.read(Util.ilog(lookFloor1.quant_q - 1));
            while (2 < vi.partitions) {
                final int n2 = vi.partitionclass[2];
                final int n3 = vi.class_dim[n2];
                final int n4 = vi.class_subs[n2];
                final int n5 = 1 << n4;
                if (n4 != 0) {
                    fullbooks[vi.class_book[n2]].decode(block.opb);
                    if (0 == -1) {
                        return null;
                    }
                }
                while (0 < 2) {
                    final int n6 = vi.class_subbook[n2][0x0 & n5 - 1];
                    if (n6 >= 0) {
                        if ((array[2] = fullbooks[n6].decode(block.opb)) == -1) {
                            return null;
                        }
                    }
                    else {
                        array[2] = 0;
                    }
                    int n7 = 0;
                    ++n7;
                }
                ++n;
            }
            while (2 < lookFloor1.posts) {
                render_point(vi.postlist[lookFloor1.loneighbor[0]], vi.postlist[lookFloor1.hineighbor[0]], array[lookFloor1.loneighbor[0]], array[lookFloor1.hineighbor[0]], vi.postlist[2]);
                final int n8 = lookFloor1.quant_q - 2;
                final int n9 = ((n8 < 2) ? n8 : 2) << 1;
                final int n10 = array[2];
                if (n10 != 0) {
                    int n11;
                    if (n10 >= n9) {
                        if (n8 > 2) {
                            n11 = n10 - 2;
                        }
                        else {
                            n11 = -1 - (n10 - n8);
                        }
                    }
                    else if ((n10 & 0x1) != 0x0) {
                        n11 = -(n10 + 1 >>> 1);
                    }
                    else {
                        n11 = n10 >> 1;
                    }
                    array[2] = n11 + 2;
                    final int[] array2 = array;
                    final int n12 = lookFloor1.loneighbor[0];
                    array2[n12] &= 0x7FFF;
                    final int[] array3 = array;
                    final int n13 = lookFloor1.hineighbor[0];
                    array3[n13] &= 0x7FFF;
                }
                else {
                    array[2] = 32770;
                }
                ++n;
            }
            return array;
        }
        return null;
    }
    
    private static int render_point(final int n, final int n2, int n3, int n4, final int n5) {
        n3 &= 0x7FFF;
        n4 &= 0x7FFF;
        final int n6 = n4 - n3;
        final int n7 = Math.abs(n6) * (n5 - n) / (n2 - n);
        if (n6 < 0) {
            return n3 - n7;
        }
        return n3 + n7;
    }
    
    @Override
    int inverse2(final Block block, final Object o, final Object o2, final float[] array) {
        final LookFloor1 lookFloor1 = (LookFloor1)o;
        final InfoFloor1 vi = lookFloor1.vi;
        final int n = block.vd.vi.blocksizes[block.mode] / 2;
        if (o2 != null) {
            final int[] array2 = (int[])o2;
            int n2 = array2[0] * vi.mult;
            int n7 = 0;
            while (1 < lookFloor1.posts) {
                final int n3 = lookFloor1.forward_index[1];
                final int n4 = array2[n3] & 0x7FFF;
                if (n4 == array2[n3]) {
                    final int n5 = n4 * vi.mult;
                    final int n6 = vi.postlist[n3];
                    render_line(0, 0, n2, n5, array);
                    n2 = n5;
                }
                ++n7;
            }
            while (1 < n) {
                final int n8 = 1;
                array[n8] *= array[0];
                ++n7;
            }
            return 1;
        }
        while (0 < n) {
            array[0] = 0.0f;
            int n9 = 0;
            ++n9;
        }
        return 0;
    }
    
    private static void render_line(final int n, final int n2, final int n3, final int n4, final float[] array) {
        final int n5 = n4 - n3;
        final int n6 = n2 - n;
        final int abs = Math.abs(n5);
        final int n7 = n5 / n6;
        final int n8 = (n5 < 0) ? (n7 - 1) : (n7 + 1);
        int n9 = n;
        int n10 = n3;
        final int n11 = abs - Math.abs(n7 * n6);
        final int n12 = n9;
        array[n12] *= Floor1.FLOOR_fromdB_LOOKUP[n10];
        while (++n9 < n2) {
            if (0 >= n6) {
                n10 += n8;
            }
            else {
                n10 += n7;
            }
            final int n13 = n9;
            array[n13] *= Floor1.FLOOR_fromdB_LOOKUP[n10];
        }
    }
    
    static {
        Floor1.FLOOR_fromdB_LOOKUP = new float[] { 1.0649863E-7f, 1.1341951E-7f, 1.2079015E-7f, 1.2863978E-7f, 1.369995E-7f, 1.459025E-7f, 1.5538409E-7f, 1.6548181E-7f, 1.7623574E-7f, 1.8768856E-7f, 1.998856E-7f, 2.128753E-7f, 2.2670913E-7f, 2.4144197E-7f, 2.5713223E-7f, 2.7384212E-7f, 2.9163792E-7f, 3.1059022E-7f, 3.307741E-7f, 3.5226967E-7f, 3.7516213E-7f, 3.995423E-7f, 4.255068E-7f, 4.5315863E-7f, 4.8260745E-7f, 5.1397E-7f, 5.4737063E-7f, 5.829419E-7f, 6.208247E-7f, 6.611694E-7f, 7.041359E-7f, 7.4989464E-7f, 7.98627E-7f, 8.505263E-7f, 9.057983E-7f, 9.646621E-7f, 1.0273513E-6f, 1.0941144E-6f, 1.1652161E-6f, 1.2409384E-6f, 1.3215816E-6f, 1.4074654E-6f, 1.4989305E-6f, 1.5963394E-6f, 1.7000785E-6f, 1.8105592E-6f, 1.9282195E-6f, 2.053526E-6f, 2.1869757E-6f, 2.3290977E-6f, 2.4804558E-6f, 2.6416496E-6f, 2.813319E-6f, 2.9961443E-6f, 3.1908505E-6f, 3.39821E-6f, 3.619045E-6f, 3.8542307E-6f, 4.1047006E-6f, 4.371447E-6f, 4.6555283E-6f, 4.958071E-6f, 5.280274E-6f, 5.623416E-6f, 5.988857E-6f, 6.3780467E-6f, 6.7925284E-6f, 7.2339453E-6f, 7.704048E-6f, 8.2047E-6f, 8.737888E-6f, 9.305725E-6f, 9.910464E-6f, 1.0554501E-5f, 1.1240392E-5f, 1.1970856E-5f, 1.2748789E-5f, 1.3577278E-5f, 1.4459606E-5f, 1.5399271E-5f, 1.6400005E-5f, 1.7465769E-5f, 1.8600793E-5f, 1.9809577E-5f, 2.1096914E-5f, 2.2467912E-5f, 2.3928002E-5f, 2.5482977E-5f, 2.7139005E-5f, 2.890265E-5f, 3.078091E-5f, 3.2781227E-5f, 3.4911533E-5f, 3.718028E-5f, 3.9596467E-5f, 4.2169668E-5f, 4.491009E-5f, 4.7828602E-5f, 5.0936775E-5f, 5.424693E-5f, 5.7772202E-5f, 6.152657E-5f, 6.552491E-5f, 6.9783084E-5f, 7.4317984E-5f, 7.914758E-5f, 8.429104E-5f, 8.976875E-5f, 9.560242E-5f, 1.0181521E-4f, 1.0843174E-4f, 1.1547824E-4f, 1.2298267E-4f, 1.3097477E-4f, 1.3948625E-4f, 1.4855085E-4f, 1.5820454E-4f, 1.6848555E-4f, 1.7943469E-4f, 1.9109536E-4f, 2.0351382E-4f, 2.167393E-4f, 2.3082423E-4f, 2.4582449E-4f, 2.6179955E-4f, 2.7881275E-4f, 2.9693157E-4f, 3.1622787E-4f, 3.3677815E-4f, 3.5866388E-4f, 3.8197188E-4f, 4.0679457E-4f, 4.3323037E-4f, 4.613841E-4f, 4.913675E-4f, 5.2329927E-4f, 5.573062E-4f, 5.935231E-4f, 6.320936E-4f, 6.731706E-4f, 7.16917E-4f, 7.635063E-4f, 8.1312325E-4f, 8.6596457E-4f, 9.2223985E-4f, 9.821722E-4f, 0.0010459992f, 0.0011139743f, 0.0011863665f, 0.0012634633f, 0.0013455702f, 0.0014330129f, 0.0015261382f, 0.0016253153f, 0.0017309374f, 0.0018434235f, 0.0019632196f, 0.0020908006f, 0.0022266726f, 0.0023713743f, 0.0025254795f, 0.0026895993f, 0.0028643848f, 0.0030505287f, 0.003248769f, 0.0034598925f, 0.0036847359f, 0.0039241905f, 0.0041792067f, 0.004450795f, 0.004740033f, 0.005048067f, 0.0053761187f, 0.005725489f, 0.0060975635f, 0.0064938175f, 0.0069158226f, 0.0073652514f, 0.007843887f, 0.008353627f, 0.008896492f, 0.009474637f, 0.010090352f, 0.01074608f, 0.011444421f, 0.012188144f, 0.012980198f, 0.013823725f, 0.014722068f, 0.015678791f, 0.016697686f, 0.017782796f, 0.018938422f, 0.020169148f, 0.021479854f, 0.022875736f, 0.02436233f, 0.025945531f, 0.027631618f, 0.029427277f, 0.031339627f, 0.03337625f, 0.035545226f, 0.037855156f, 0.0403152f, 0.042935107f, 0.045725275f, 0.048696756f, 0.05186135f, 0.05523159f, 0.05882085f, 0.062643364f, 0.06671428f, 0.07104975f, 0.075666964f, 0.08058423f, 0.08582105f, 0.09139818f, 0.097337745f, 0.1036633f, 0.11039993f, 0.11757434f, 0.12521498f, 0.13335215f, 0.14201812f, 0.15124726f, 0.16107617f, 0.1715438f, 0.18269168f, 0.19456401f, 0.20720787f, 0.22067343f, 0.23501402f, 0.25028655f, 0.26655158f, 0.28387362f, 0.3023213f, 0.32196787f, 0.34289113f, 0.36517414f, 0.3889052f, 0.41417846f, 0.44109413f, 0.4697589f, 0.50028646f, 0.53279793f, 0.5674221f, 0.6042964f, 0.64356697f, 0.6853896f, 0.72993004f, 0.777365f, 0.8278826f, 0.88168305f, 0.9389798f, 1.0f };
    }
    
    class EchstateFloor1
    {
        int[] codewords;
        float[] curve;
        long frameno;
        long codes;
        final Floor1 this$0;
        
        EchstateFloor1(final Floor1 this$0) {
            this.this$0 = this$0;
        }
    }
    
    class Lsfit_acc
    {
        long x0;
        long x1;
        long xa;
        long ya;
        long x2a;
        long y2a;
        long xya;
        long n;
        long an;
        long un;
        long edgey0;
        long edgey1;
        final Floor1 this$0;
        
        Lsfit_acc(final Floor1 this$0) {
            this.this$0 = this$0;
        }
    }
    
    class LookFloor1
    {
        static final int VIF_POSIT = 63;
        int[] sorted_index;
        int[] forward_index;
        int[] reverse_index;
        int[] hineighbor;
        int[] loneighbor;
        int posts;
        int n;
        int quant_q;
        InfoFloor1 vi;
        int phrasebits;
        int postbits;
        int frames;
        final Floor1 this$0;
        
        LookFloor1(final Floor1 this$0) {
            this.this$0 = this$0;
            this.sorted_index = new int[65];
            this.forward_index = new int[65];
            this.reverse_index = new int[65];
            this.hineighbor = new int[63];
            this.loneighbor = new int[63];
        }
        
        void free() {
            this.sorted_index = null;
            this.forward_index = null;
            this.reverse_index = null;
            this.hineighbor = null;
            this.loneighbor = null;
        }
    }
    
    class InfoFloor1
    {
        static final int VIF_POSIT = 63;
        static final int VIF_CLASS = 16;
        static final int VIF_PARTS = 31;
        int partitions;
        int[] partitionclass;
        int[] class_dim;
        int[] class_subs;
        int[] class_book;
        int[][] class_subbook;
        int mult;
        int[] postlist;
        float maxover;
        float maxunder;
        float maxerr;
        int twofitminsize;
        int twofitminused;
        int twofitweight;
        float twofitatten;
        int unusedminsize;
        int unusedmin_n;
        int n;
        final Floor1 this$0;
        
        InfoFloor1(final Floor1 this$0) {
            this.this$0 = this$0;
            this.partitionclass = new int[31];
            this.class_dim = new int[16];
            this.class_subs = new int[16];
            this.class_book = new int[16];
            this.class_subbook = new int[16][];
            this.postlist = new int[65];
            while (0 < this.class_subbook.length) {
                this.class_subbook[0] = new int[8];
                int n = 0;
                ++n;
            }
        }
        
        void free() {
            this.partitionclass = null;
            this.class_dim = null;
            this.class_subs = null;
            this.class_book = null;
            this.class_subbook = null;
            this.postlist = null;
        }
        
        Object copy_info() {
            final InfoFloor1 infoFloor1 = this.this$0.new InfoFloor1();
            infoFloor1.partitions = this.partitions;
            System.arraycopy(this.partitionclass, 0, infoFloor1.partitionclass, 0, 31);
            System.arraycopy(this.class_dim, 0, infoFloor1.class_dim, 0, 16);
            System.arraycopy(this.class_subs, 0, infoFloor1.class_subs, 0, 16);
            System.arraycopy(this.class_book, 0, infoFloor1.class_book, 0, 16);
            while (true) {
                System.arraycopy(this.class_subbook[0], 0, infoFloor1.class_subbook[0], 0, 8);
                int n = 0;
                ++n;
            }
        }
    }
}
