package com.jcraft.jorbis;

import com.jcraft.jogg.*;

public class Block
{
    float[][] pcm;
    Buffer opb;
    int lW;
    int W;
    int nW;
    int pcmend;
    int mode;
    int eofflag;
    long granulepos;
    long sequence;
    DspState vd;
    int glue_bits;
    int time_bits;
    int floor_bits;
    int res_bits;
    
    public Block(final DspState vd) {
        this.pcm = new float[0][];
        this.opb = new Buffer();
        this.vd = vd;
        if (vd.analysisp != 0) {
            this.opb.writeinit();
        }
    }
    
    public void init(final DspState vd) {
        this.vd = vd;
    }
    
    public int clear() {
        if (this.vd != null && this.vd.analysisp != 0) {
            this.opb.writeclear();
        }
        return 0;
    }
    
    public int synthesis(final Packet packet) {
        final Info vi = this.vd.vi;
        this.opb.readinit(packet.packet_base, packet.packet, packet.bytes);
        if (this.opb.read(1) != 0) {
            return -1;
        }
        final int read = this.opb.read(this.vd.modebits);
        if (read == -1) {
            return -1;
        }
        this.mode = read;
        this.W = vi.mode_param[this.mode].blockflag;
        if (this.W != 0) {
            this.lW = this.opb.read(1);
            this.nW = this.opb.read(1);
            if (this.nW == -1) {
                return -1;
            }
        }
        else {
            this.lW = 0;
            this.nW = 0;
        }
        this.granulepos = packet.granulepos;
        this.sequence = packet.packetno - 3L;
        this.eofflag = packet.e_o_s;
        this.pcmend = vi.blocksizes[this.W];
        if (this.pcm.length < vi.channels) {
            this.pcm = new float[vi.channels][];
        }
        while (0 < vi.channels) {
            if (this.pcm[0] == null || this.pcm[0].length < this.pcmend) {
                this.pcm[0] = new float[this.pcmend];
            }
            else {
                while (0 < this.pcmend) {
                    this.pcm[0][0] = 0.0f;
                    int n = 0;
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
        int n2 = vi.map_type[vi.mode_param[this.mode].mapping];
        return FuncMapping.mapping_P[0].inverse(this, this.vd.mode[this.mode]);
    }
}
