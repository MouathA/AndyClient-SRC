package com.jcraft.jorbis;

import com.jcraft.jogg.*;

public class Info
{
    private static final int OV_EBADPACKET = -136;
    private static final int OV_ENOTAUDIO = -135;
    private static byte[] _vorbis;
    private static final int VI_TIMEB = 1;
    private static final int VI_FLOORB = 2;
    private static final int VI_RESB = 3;
    private static final int VI_MAPB = 1;
    private static final int VI_WINDOWB = 1;
    public int version;
    public int channels;
    public int rate;
    int bitrate_upper;
    int bitrate_nominal;
    int bitrate_lower;
    int[] blocksizes;
    int modes;
    int maps;
    int times;
    int floors;
    int residues;
    int books;
    int psys;
    InfoMode[] mode_param;
    int[] map_type;
    Object[] map_param;
    int[] time_type;
    Object[] time_param;
    int[] floor_type;
    Object[] floor_param;
    int[] residue_type;
    Object[] residue_param;
    StaticCodeBook[] book_param;
    PsyInfo[] psy_param;
    int envelopesa;
    float preecho_thresh;
    float preecho_clamp;
    
    public Info() {
        this.blocksizes = new int[2];
        this.mode_param = null;
        this.map_type = null;
        this.map_param = null;
        this.time_type = null;
        this.time_param = null;
        this.floor_type = null;
        this.floor_param = null;
        this.residue_type = null;
        this.residue_param = null;
        this.book_param = null;
        this.psy_param = new PsyInfo[64];
    }
    
    public void init() {
        this.rate = 0;
    }
    
    public void clear() {
        int n = 0;
        while (0 < this.modes) {
            this.mode_param[0] = null;
            ++n;
        }
        this.mode_param = null;
        while (0 < this.maps) {
            FuncMapping.mapping_P[this.map_type[0]].free_info(this.map_param[0]);
            ++n;
        }
        this.map_param = null;
        while (0 < this.times) {
            FuncTime.time_P[this.time_type[0]].free_info(this.time_param[0]);
            ++n;
        }
        this.time_param = null;
        while (0 < this.floors) {
            FuncFloor.floor_P[this.floor_type[0]].free_info(this.floor_param[0]);
            ++n;
        }
        this.floor_param = null;
        while (0 < this.residues) {
            FuncResidue.residue_P[this.residue_type[0]].free_info(this.residue_param[0]);
            ++n;
        }
        this.residue_param = null;
        while (0 < this.books) {
            if (this.book_param[0] != null) {
                this.book_param[0].clear();
                this.book_param[0] = null;
            }
            ++n;
        }
        this.book_param = null;
        while (0 < this.psys) {
            this.psy_param[0].free();
            ++n;
        }
    }
    
    int unpack_info(final Buffer buffer) {
        this.version = buffer.read(32);
        if (this.version != 0) {
            return -1;
        }
        this.channels = buffer.read(8);
        this.rate = buffer.read(32);
        this.bitrate_upper = buffer.read(32);
        this.bitrate_nominal = buffer.read(32);
        this.bitrate_lower = buffer.read(32);
        this.blocksizes[0] = 1 << buffer.read(4);
        this.blocksizes[1] = 1 << buffer.read(4);
        if (this.rate < 1 || this.channels < 1 || this.blocksizes[0] < 8 || this.blocksizes[1] < this.blocksizes[0] || buffer.read(1) != 1) {
            this.clear();
            return -1;
        }
        return 0;
    }
    
    int unpack_books(final Buffer buffer) {
        this.books = buffer.read(8) + 1;
        if (this.book_param == null || this.book_param.length != this.books) {
            this.book_param = new StaticCodeBook[this.books];
        }
        int n = 0;
        while (0 < this.books) {
            this.book_param[0] = new StaticCodeBook();
            if (this.book_param[0].unpack(buffer) != 0) {
                this.clear();
                return -1;
            }
            ++n;
        }
        this.times = buffer.read(6) + 1;
        if (this.time_type == null || this.time_type.length != this.times) {
            this.time_type = new int[this.times];
        }
        if (this.time_param == null || this.time_param.length != this.times) {
            this.time_param = new Object[this.times];
        }
        while (0 < this.times) {
            this.time_type[0] = buffer.read(16);
            if (this.time_type[0] < 0 || this.time_type[0] >= 1) {
                this.clear();
                return -1;
            }
            this.time_param[0] = FuncTime.time_P[this.time_type[0]].unpack(this, buffer);
            if (this.time_param[0] == null) {
                this.clear();
                return -1;
            }
            ++n;
        }
        this.floors = buffer.read(6) + 1;
        if (this.floor_type == null || this.floor_type.length != this.floors) {
            this.floor_type = new int[this.floors];
        }
        if (this.floor_param == null || this.floor_param.length != this.floors) {
            this.floor_param = new Object[this.floors];
        }
        while (0 < this.floors) {
            this.floor_type[0] = buffer.read(16);
            if (this.floor_type[0] < 0 || this.floor_type[0] >= 2) {
                this.clear();
                return -1;
            }
            this.floor_param[0] = FuncFloor.floor_P[this.floor_type[0]].unpack(this, buffer);
            if (this.floor_param[0] == null) {
                this.clear();
                return -1;
            }
            ++n;
        }
        this.residues = buffer.read(6) + 1;
        if (this.residue_type == null || this.residue_type.length != this.residues) {
            this.residue_type = new int[this.residues];
        }
        if (this.residue_param == null || this.residue_param.length != this.residues) {
            this.residue_param = new Object[this.residues];
        }
        while (0 < this.residues) {
            this.residue_type[0] = buffer.read(16);
            if (this.residue_type[0] < 0 || this.residue_type[0] >= 3) {
                this.clear();
                return -1;
            }
            this.residue_param[0] = FuncResidue.residue_P[this.residue_type[0]].unpack(this, buffer);
            if (this.residue_param[0] == null) {
                this.clear();
                return -1;
            }
            ++n;
        }
        this.maps = buffer.read(6) + 1;
        if (this.map_type == null || this.map_type.length != this.maps) {
            this.map_type = new int[this.maps];
        }
        if (this.map_param == null || this.map_param.length != this.maps) {
            this.map_param = new Object[this.maps];
        }
        while (0 < this.maps) {
            this.map_type[0] = buffer.read(16);
            if (this.map_type[0] < 0 || this.map_type[0] >= 1) {
                this.clear();
                return -1;
            }
            this.map_param[0] = FuncMapping.mapping_P[this.map_type[0]].unpack(this, buffer);
            if (this.map_param[0] == null) {
                this.clear();
                return -1;
            }
            ++n;
        }
        this.modes = buffer.read(6) + 1;
        if (this.mode_param == null || this.mode_param.length != this.modes) {
            this.mode_param = new InfoMode[this.modes];
        }
        while (0 < this.modes) {
            this.mode_param[0] = new InfoMode();
            this.mode_param[0].blockflag = buffer.read(1);
            this.mode_param[0].windowtype = buffer.read(16);
            this.mode_param[0].transformtype = buffer.read(16);
            this.mode_param[0].mapping = buffer.read(8);
            if (this.mode_param[0].windowtype >= 1 || this.mode_param[0].transformtype >= 1 || this.mode_param[0].mapping >= this.maps) {
                this.clear();
                return -1;
            }
            ++n;
        }
        if (buffer.read(1) != 1) {
            this.clear();
            return -1;
        }
        return 0;
    }
    
    public int synthesis_headerin(final Comment comment, final Packet packet) {
        final Buffer buffer = new Buffer();
        if (packet != null) {
            buffer.readinit(packet.packet_base, packet.packet, packet.bytes);
            final byte[] array = new byte[6];
            final int read = buffer.read(8);
            buffer.read(array, 6);
            if (array[0] != 118 || array[1] != 111 || array[2] != 114 || array[3] != 98 || array[4] != 105 || array[5] != 115) {
                return -1;
            }
            switch (read) {
                case 1: {
                    if (packet.b_o_s == 0) {
                        return -1;
                    }
                    if (this.rate != 0) {
                        return -1;
                    }
                    return this.unpack_info(buffer);
                }
                case 3: {
                    if (this.rate == 0) {
                        return -1;
                    }
                    return comment.unpack(buffer);
                }
                case 5: {
                    if (this.rate == 0 || comment.vendor == null) {
                        return -1;
                    }
                    return this.unpack_books(buffer);
                }
            }
        }
        return -1;
    }
    
    int pack_info(final Buffer buffer) {
        buffer.write(1, 8);
        buffer.write(Info._vorbis);
        buffer.write(0, 32);
        buffer.write(this.channels, 8);
        buffer.write(this.rate, 32);
        buffer.write(this.bitrate_upper, 32);
        buffer.write(this.bitrate_nominal, 32);
        buffer.write(this.bitrate_lower, 32);
        buffer.write(Util.ilog2(this.blocksizes[0]), 4);
        buffer.write(Util.ilog2(this.blocksizes[1]), 4);
        buffer.write(1, 1);
        return 0;
    }
    
    int pack_books(final Buffer buffer) {
        buffer.write(5, 8);
        buffer.write(Info._vorbis);
        buffer.write(this.books - 1, 8);
        int n = 0;
        while (0 < this.books) {
            if (this.book_param[0].pack(buffer) != 0) {
                return -1;
            }
            ++n;
        }
        buffer.write(this.times - 1, 6);
        while (0 < this.times) {
            buffer.write(this.time_type[0], 16);
            FuncTime.time_P[this.time_type[0]].pack(this.time_param[0], buffer);
            ++n;
        }
        buffer.write(this.floors - 1, 6);
        while (0 < this.floors) {
            buffer.write(this.floor_type[0], 16);
            FuncFloor.floor_P[this.floor_type[0]].pack(this.floor_param[0], buffer);
            ++n;
        }
        buffer.write(this.residues - 1, 6);
        while (0 < this.residues) {
            buffer.write(this.residue_type[0], 16);
            FuncResidue.residue_P[this.residue_type[0]].pack(this.residue_param[0], buffer);
            ++n;
        }
        buffer.write(this.maps - 1, 6);
        while (0 < this.maps) {
            buffer.write(this.map_type[0], 16);
            FuncMapping.mapping_P[this.map_type[0]].pack(this, this.map_param[0], buffer);
            ++n;
        }
        buffer.write(this.modes - 1, 6);
        while (0 < this.modes) {
            buffer.write(this.mode_param[0].blockflag, 1);
            buffer.write(this.mode_param[0].windowtype, 16);
            buffer.write(this.mode_param[0].transformtype, 16);
            buffer.write(this.mode_param[0].mapping, 8);
            ++n;
        }
        buffer.write(1, 1);
        return 0;
    }
    
    public int blocksize(final Packet packet) {
        final Buffer buffer = new Buffer();
        buffer.readinit(packet.packet_base, packet.packet, packet.bytes);
        if (buffer.read(1) != 0) {
            return -135;
        }
        for (int i = this.modes; i > 1; i >>>= 1) {
            int n = 0;
            ++n;
        }
        final int read = buffer.read(0);
        if (read == -1) {
            return -136;
        }
        return this.blocksizes[this.mode_param[read].blockflag];
    }
    
    @Override
    public String toString() {
        return "version:" + new Integer(this.version) + ", channels:" + new Integer(this.channels) + ", rate:" + new Integer(this.rate) + ", bitrate:" + new Integer(this.bitrate_upper) + "," + new Integer(this.bitrate_nominal) + "," + new Integer(this.bitrate_lower);
    }
    
    static {
        Info._vorbis = "vorbis".getBytes();
    }
}
