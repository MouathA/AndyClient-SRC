package com.jcraft.jorbis;

import com.jcraft.jogg.*;

public class Comment
{
    private static byte[] _vorbis;
    private static byte[] _vendor;
    private static final int OV_EIMPL = -130;
    public byte[][] user_comments;
    public int[] comment_lengths;
    public int comments;
    public byte[] vendor;
    
    public void init() {
        this.user_comments = null;
        this.comments = 0;
        this.vendor = null;
    }
    
    public void add(final String s) {
        this.add(s.getBytes());
    }
    
    private void add(final byte[] array) {
        final byte[][] user_comments = new byte[this.comments + 2][];
        if (this.user_comments != null) {
            System.arraycopy(this.user_comments, 0, user_comments, 0, this.comments);
        }
        this.user_comments = user_comments;
        final int[] comment_lengths = new int[this.comments + 2];
        if (this.comment_lengths != null) {
            System.arraycopy(this.comment_lengths, 0, comment_lengths, 0, this.comments);
        }
        this.comment_lengths = comment_lengths;
        final byte[] array2 = new byte[array.length + 1];
        System.arraycopy(array, 0, array2, 0, array.length);
        this.user_comments[this.comments] = array2;
        this.comment_lengths[this.comments] = array.length;
        ++this.comments;
        this.user_comments[this.comments] = null;
    }
    
    public void add_tag(final String s, String s2) {
        if (s2 == null) {
            s2 = "";
        }
        this.add(s + "=" + s2);
    }
    
    static boolean tagcompare(final byte[] array, final byte[] array2, final int n) {
        while (0 < n) {
            byte b = array[0];
            byte b2 = array2[0];
            if (90 >= b && b >= 65) {
                b = (byte)(b - 65 + 97);
            }
            if (90 >= b2 && b2 >= 65) {
                b2 = (byte)(b2 - 65 + 97);
            }
            if (b != b2) {
                return false;
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    public String query(final String s) {
        return this.query(s, 0);
    }
    
    public String query(final String s, final int n) {
        final int query = this.query(s.getBytes(), n);
        if (query == -1) {
            return null;
        }
        final byte[] array = this.user_comments[query];
        while (0 < this.comment_lengths[query]) {
            if (array[0] == 61) {
                return new String(array, 1, this.comment_lengths[query] - 1);
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    private int query(final byte[] array, final int n) {
        final int n2 = array.length + 1;
        final byte[] array2 = new byte[n2];
        System.arraycopy(array, 0, array2, 0, array.length);
        array2[array.length] = 61;
        while (0 < this.comments) {
            if (tagcompare(this.user_comments[0], array2, n2)) {
                if (n == 0) {
                    return 0;
                }
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        return -1;
    }
    
    int unpack(final Buffer buffer) {
        final int read = buffer.read(32);
        if (read < 0) {
            this.clear();
            return -1;
        }
        buffer.read(this.vendor = new byte[read + 1], read);
        this.comments = buffer.read(32);
        if (this.comments < 0) {
            this.clear();
            return -1;
        }
        this.user_comments = new byte[this.comments + 1][];
        this.comment_lengths = new int[this.comments + 1];
        while (0 < this.comments) {
            final int read2 = buffer.read(32);
            if (read2 < 0) {
                this.clear();
                return -1;
            }
            this.comment_lengths[0] = read2;
            buffer.read(this.user_comments[0] = new byte[read2 + 1], read2);
            int n = 0;
            ++n;
        }
        if (buffer.read(1) != 1) {
            this.clear();
            return -1;
        }
        return 0;
    }
    
    int pack(final Buffer buffer) {
        buffer.write(3, 8);
        buffer.write(Comment._vorbis);
        buffer.write(Comment._vendor.length, 32);
        buffer.write(Comment._vendor);
        buffer.write(this.comments, 32);
        if (this.comments != 0) {
            while (0 < this.comments) {
                if (this.user_comments[0] != null) {
                    buffer.write(this.comment_lengths[0], 32);
                    buffer.write(this.user_comments[0]);
                }
                else {
                    buffer.write(0, 32);
                }
                int n = 0;
                ++n;
            }
        }
        buffer.write(1, 1);
        return 0;
    }
    
    public int header_out(final Packet packet) {
        final Buffer buffer = new Buffer();
        buffer.writeinit();
        if (this.pack(buffer) != 0) {
            return -130;
        }
        packet.packet_base = new byte[buffer.bytes()];
        packet.packet = 0;
        packet.bytes = buffer.bytes();
        System.arraycopy(buffer.buffer(), 0, packet.packet_base, 0, packet.bytes);
        packet.b_o_s = 0;
        packet.e_o_s = 0;
        packet.granulepos = 0L;
        return 0;
    }
    
    void clear() {
        while (0 < this.comments) {
            this.user_comments[0] = null;
            int n = 0;
            ++n;
        }
        this.user_comments = null;
        this.vendor = null;
    }
    
    public String getVendor() {
        return new String(this.vendor, 0, this.vendor.length - 1);
    }
    
    public String getComment(final int n) {
        if (this.comments <= n) {
            return null;
        }
        return new String(this.user_comments[n], 0, this.user_comments[n].length - 1);
    }
    
    @Override
    public String toString() {
        String s = "Vendor: " + new String(this.vendor, 0, this.vendor.length - 1);
        while (0 < this.comments) {
            s = s + "\nComment: " + new String(this.user_comments[0], 0, this.user_comments[0].length - 1);
            int n = 0;
            ++n;
        }
        return s + "\n";
    }
    
    static {
        Comment._vorbis = "vorbis".getBytes();
        Comment._vendor = "Xiphophorus libVorbis I 20000508".getBytes();
    }
}
