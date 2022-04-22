package com.jcraft.jorbis;

import com.jcraft.jogg.*;

class Residue0 extends FuncResidue
{
    private static int[][][] _01inverse_partword;
    static int[][] _2inverse_partword;
    
    @Override
    void pack(final Object o, final Buffer buffer) {
        final InfoResidue0 infoResidue0 = (InfoResidue0)o;
        buffer.write(infoResidue0.begin, 24);
        buffer.write(infoResidue0.end, 24);
        buffer.write(infoResidue0.grouping - 1, 24);
        buffer.write(infoResidue0.partitions - 1, 6);
        buffer.write(infoResidue0.groupbook, 8);
        while (0 < infoResidue0.partitions) {
            final int n = infoResidue0.secondstages[0];
            if (Util.ilog(n) > 3) {
                buffer.write(n, 3);
                buffer.write(1, 1);
                buffer.write(n >>> 3, 5);
            }
            else {
                buffer.write(n, 4);
            }
            final int n2 = 0 + Util.icount(n);
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    Object unpack(final Info info, final Buffer buffer) {
        final InfoResidue0 infoResidue0 = new InfoResidue0();
        infoResidue0.begin = buffer.read(24);
        infoResidue0.end = buffer.read(24);
        infoResidue0.grouping = buffer.read(24) + 1;
        infoResidue0.partitions = buffer.read(6) + 1;
        infoResidue0.groupbook = buffer.read(8);
        while (0 < infoResidue0.partitions) {
            int read = buffer.read(3);
            if (buffer.read(1) != 0) {
                read |= buffer.read(5) << 3;
            }
            infoResidue0.secondstages[0] = read;
            final int n = 0 + Util.icount(read);
            int n2 = 0;
            ++n2;
        }
        if (infoResidue0.groupbook >= info.books) {
            this.free_info(infoResidue0);
            return null;
        }
        return infoResidue0;
    }
    
    @Override
    Object look(final DspState dspState, final InfoMode infoMode, final Object o) {
        final InfoResidue0 info = (InfoResidue0)o;
        final LookResidue0 lookResidue0 = new LookResidue0();
        lookResidue0.info = info;
        lookResidue0.map = infoMode.mapping;
        lookResidue0.parts = info.partitions;
        lookResidue0.fullbooks = dspState.fullbooks;
        lookResidue0.phrasebook = dspState.fullbooks[info.groupbook];
        final int dim = lookResidue0.phrasebook.dim;
        lookResidue0.partbooks = new int[lookResidue0.parts][];
        int n2 = 0;
        int n3 = 0;
        while (0 < lookResidue0.parts) {
            final int n = info.secondstages[0];
            final int ilog = Util.ilog(0);
            if (ilog != 0) {
                if (ilog > 0) {}
                lookResidue0.partbooks[0] = new int[ilog];
                while (0 < ilog) {
                    ++n2;
                }
            }
            ++n3;
        }
        lookResidue0.partvals = (int)Math.rint(Math.pow(lookResidue0.parts, dim));
        lookResidue0.stages = 0;
        lookResidue0.decodemap = new int[lookResidue0.partvals][];
        while (0 < lookResidue0.partvals) {
            int n4 = lookResidue0.partvals / lookResidue0.parts;
            lookResidue0.decodemap[0] = new int[dim];
            while (0 < dim) {
                final int n5 = 0 / n4;
                n4 /= lookResidue0.parts;
                lookResidue0.decodemap[0][0] = n5;
                ++n2;
            }
            ++n3;
        }
        return lookResidue0;
    }
    
    @Override
    void free_info(final Object o) {
    }
    
    @Override
    void free_look(final Object o) {
    }
    
    static synchronized int _01inverse(final Block block, final Object o, final float[][] array, final int n, final int n2) {
        final LookResidue0 lookResidue0 = (LookResidue0)o;
        final InfoResidue0 info = lookResidue0.info;
        final int grouping = info.grouping;
        final int dim = lookResidue0.phrasebook.dim;
        final int n3 = (info.end - info.begin) / grouping;
        final int n4 = (n3 + dim - 1) / dim;
        if (Residue0._01inverse_partword.length < n) {
            Residue0._01inverse_partword = new int[n][][];
        }
        int n5 = 0;
        while (0 < n) {
            if (Residue0._01inverse_partword[0] == null || Residue0._01inverse_partword[0].length < n4) {
                Residue0._01inverse_partword[0] = new int[n4][];
            }
            ++n5;
        }
        while (0 < lookResidue0.stages) {
            while (0 < n3) {
                while (0 < n) {
                    final int decode = lookResidue0.phrasebook.decode(block.opb);
                    if (decode == -1) {
                        return 0;
                    }
                    Residue0._01inverse_partword[0][0] = lookResidue0.decodemap[decode];
                    if (Residue0._01inverse_partword[0][0] == null) {
                        return 0;
                    }
                    ++n5;
                }
                while (0 < dim && 0 < n3) {
                    while (0 < n) {
                        final int n6 = info.begin + 0 * grouping;
                        final int n7 = Residue0._01inverse_partword[0][0][0];
                        if ((info.secondstages[n7] & 0x1) != 0x0) {
                            final CodeBook codeBook = lookResidue0.fullbooks[lookResidue0.partbooks[n7][0]];
                            if (codeBook != null) {
                                if (n2 == 0) {
                                    if (codeBook.decodevs_add(array[0], n6, block.opb, grouping) == -1) {
                                        return 0;
                                    }
                                }
                                else if (n2 == 1 && codeBook.decodev_add(array[0], n6, block.opb, grouping) == -1) {
                                    return 0;
                                }
                            }
                        }
                        ++n5;
                    }
                    int n8 = 0;
                    ++n8;
                    int n9 = 0;
                    ++n9;
                }
                int n10 = 0;
                ++n10;
            }
            int n11 = 0;
            ++n11;
        }
        return 0;
    }
    
    static synchronized int _2inverse(final Block block, final Object o, final float[][] array, final int n) {
        final LookResidue0 lookResidue0 = (LookResidue0)o;
        final InfoResidue0 info = lookResidue0.info;
        final int grouping = info.grouping;
        final int dim = lookResidue0.phrasebook.dim;
        final int n2 = (info.end - info.begin) / grouping;
        final int n3 = (n2 + dim - 1) / dim;
        if (Residue0._2inverse_partword == null || Residue0._2inverse_partword.length < n3) {
            Residue0._2inverse_partword = new int[n3][];
        }
        while (0 < lookResidue0.stages) {
            while (0 < n2) {
                final int decode = lookResidue0.phrasebook.decode(block.opb);
                if (decode == -1) {
                    return 0;
                }
                Residue0._2inverse_partword[0] = lookResidue0.decodemap[decode];
                if (Residue0._2inverse_partword[0] == null) {
                    return 0;
                }
                while (0 < dim && 0 < n2) {
                    final int n4 = info.begin + 0 * grouping;
                    final int n5 = Residue0._2inverse_partword[0][0];
                    if ((info.secondstages[n5] & 0x1) != 0x0) {
                        final CodeBook codeBook = lookResidue0.fullbooks[lookResidue0.partbooks[n5][0]];
                        if (codeBook != null && codeBook.decodevv_add(array, n4, n, block.opb, grouping) == -1) {
                            return 0;
                        }
                    }
                    int n6 = 0;
                    ++n6;
                    int n7 = 0;
                    ++n7;
                }
                int n8 = 0;
                ++n8;
            }
            int n9 = 0;
            ++n9;
        }
        return 0;
    }
    
    @Override
    int inverse(final Block block, final Object o, final float[][] array, final int[] array2, final int n) {
        while (0 < n) {
            if (array2[0] != 0) {
                final int n2 = 0;
                int n3 = 0;
                ++n3;
                array[n2] = array[0];
            }
            int n4 = 0;
            ++n4;
        }
        return 0;
    }
    
    static {
        Residue0._01inverse_partword = new int[2][][];
        Residue0._2inverse_partword = null;
    }
    
    class InfoResidue0
    {
        int begin;
        int end;
        int grouping;
        int partitions;
        int groupbook;
        int[] secondstages;
        int[] booklist;
        float[] entmax;
        float[] ampmax;
        int[] subgrp;
        int[] blimit;
        final Residue0 this$0;
        
        InfoResidue0(final Residue0 this$0) {
            this.this$0 = this$0;
            this.secondstages = new int[64];
            this.booklist = new int[256];
            this.entmax = new float[64];
            this.ampmax = new float[64];
            this.subgrp = new int[64];
            this.blimit = new int[64];
        }
    }
    
    class LookResidue0
    {
        InfoResidue0 info;
        int map;
        int parts;
        int stages;
        CodeBook[] fullbooks;
        CodeBook phrasebook;
        int[][] partbooks;
        int partvals;
        int[][] decodemap;
        int postbits;
        int phrasebits;
        int frames;
        final Residue0 this$0;
        
        LookResidue0(final Residue0 this$0) {
            this.this$0 = this$0;
        }
    }
}
