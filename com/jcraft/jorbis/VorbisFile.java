package com.jcraft.jorbis;

import com.jcraft.jogg.*;
import java.io.*;

public class VorbisFile
{
    static final int CHUNKSIZE = 8500;
    static final int SEEK_SET = 0;
    static final int SEEK_CUR = 1;
    static final int SEEK_END = 2;
    static final int OV_FALSE = -1;
    static final int OV_EOF = -2;
    static final int OV_HOLE = -3;
    static final int OV_EREAD = -128;
    static final int OV_EFAULT = -129;
    static final int OV_EIMPL = -130;
    static final int OV_EINVAL = -131;
    static final int OV_ENOTVORBIS = -132;
    static final int OV_EBADHEADER = -133;
    static final int OV_EVERSION = -134;
    static final int OV_ENOTAUDIO = -135;
    static final int OV_EBADPACKET = -136;
    static final int OV_EBADLINK = -137;
    static final int OV_ENOSEEK = -138;
    InputStream datasource;
    boolean seekable;
    long offset;
    long end;
    SyncState oy;
    int links;
    long[] offsets;
    long[] dataoffsets;
    int[] serialnos;
    long[] pcmlengths;
    Info[] vi;
    Comment[] vc;
    long pcm_offset;
    boolean decode_ready;
    int current_serialno;
    int current_link;
    float bittrack;
    float samptrack;
    StreamState os;
    DspState vd;
    Block vb;
    
    public VorbisFile(final String s) throws JOrbisException {
        this.seekable = false;
        this.oy = new SyncState();
        this.decode_ready = false;
        this.os = new StreamState();
        this.vd = new DspState();
        this.vb = new Block(this.vd);
        final SeekableInputStream seekableInputStream = new SeekableInputStream(s);
        if (this.open(seekableInputStream, null, 0) == -1) {
            throw new JOrbisException("VorbisFile: open return -1");
        }
        if (seekableInputStream != null) {
            seekableInputStream.close();
        }
    }
    
    public VorbisFile(final InputStream inputStream, final byte[] array, final int n) throws JOrbisException {
        this.seekable = false;
        this.oy = new SyncState();
        this.decode_ready = false;
        this.os = new StreamState();
        this.vd = new DspState();
        this.vb = new Block(this.vd);
        this.open(inputStream, array, n);
        -1;
    }
    
    private int get_data() {
        this.datasource.read(this.oy.data, this.oy.buffer(8500), 8500);
        this.oy.wrote(0);
        if (0 == -1) {}
        return 0;
    }
    
    private void seek_helper(final long offset) {
        fseek(this.datasource, offset, 0);
        this.offset = offset;
        this.oy.reset();
    }
    
    private int get_next_page(final Page page, long n) {
        if (n > 0L) {
            n += this.offset;
        }
        while (n <= 0L || this.offset < n) {
            final int pageseek = this.oy.pageseek(page);
            if (pageseek < 0) {
                this.offset -= pageseek;
            }
            else {
                if (pageseek != 0) {
                    final int n2 = (int)this.offset;
                    this.offset += pageseek;
                    return n2;
                }
                if (n == 0L) {
                    return -1;
                }
                final int get_data = this.get_data();
                if (get_data == 0) {
                    return -2;
                }
                if (get_data < 0) {
                    return -128;
                }
                continue;
            }
        }
        return -1;
    }
    
    private int get_prev_page(final Page page) throws JOrbisException {
        long offset = this.offset;
        while (-1 == -1) {
            offset -= 8500L;
            if (offset < 0L) {
                offset = 0L;
            }
            this.seek_helper(offset);
            while (this.offset < offset + 8500L) {
                final int get_next_page = this.get_next_page(page, offset + 8500L - this.offset);
                if (get_next_page == -128) {
                    return -128;
                }
                if (get_next_page >= 0) {
                    continue;
                }
                if (-1 == -1) {
                    throw new JOrbisException();
                }
                break;
            }
        }
        this.seek_helper(-1);
        if (this.get_next_page(page, 8500L) < 0) {
            return -129;
        }
        return -1;
    }
    
    int bisect_forward_serialno(final long n, long n2, final long n3, final int n4, final int n5) {
        long n6 = n3;
        long n7 = n3;
        final Page page = new Page();
        while (n2 < n6) {
            long n8;
            if (n6 - n2 < 8500L) {
                n8 = n2;
            }
            else {
                n8 = (n2 + n6) / 2L;
            }
            this.seek_helper(n8);
            final int get_next_page = this.get_next_page(page, -1L);
            if (get_next_page == -128) {
                return -128;
            }
            if (get_next_page < 0 || page.serialno() != n4) {
                n6 = n8;
                if (get_next_page < 0) {
                    continue;
                }
                n7 = get_next_page;
            }
            else {
                n2 = get_next_page + page.header_len + page.body_len;
            }
        }
        this.seek_helper(n7);
        final int get_next_page2 = this.get_next_page(page, -1L);
        if (get_next_page2 == -128) {
            return -128;
        }
        if (n2 >= n3 || get_next_page2 == -1) {
            this.links = n5 + 1;
            (this.offsets = new long[n5 + 2])[n5 + 1] = n2;
        }
        else if (this.bisect_forward_serialno(n7, this.offset, n3, page.serialno(), n5 + 1) == -128) {
            return -128;
        }
        this.offsets[n5] = n;
        return 0;
    }
    
    int fetch_headers(final Info info, final Comment comment, final int[] array, Page page) {
        final Page page2 = new Page();
        final Packet packet = new Packet();
        if (page == null) {
            final int get_next_page = this.get_next_page(page2, 8500L);
            if (get_next_page == -128) {
                return -128;
            }
            if (get_next_page < 0) {
                return -132;
            }
            page = page2;
        }
        if (array != null) {
            array[0] = page.serialno();
        }
        this.os.init(page.serialno());
        info.init();
        comment.init();
        while (0 < 3) {
            this.os.pagein(page);
            while (0 < 3) {
                final int packetout = this.os.packetout(packet);
                if (packetout == 0) {
                    break;
                }
                if (packetout == -1) {
                    info.clear();
                    comment.clear();
                    this.os.clear();
                    return -1;
                }
                if (info.synthesis_headerin(comment, packet) != 0) {
                    info.clear();
                    comment.clear();
                    this.os.clear();
                    return -1;
                }
                int n = 0;
                ++n;
            }
            if (0 < 3 && this.get_next_page(page, 1L) < 0) {
                info.clear();
                comment.clear();
                this.os.clear();
                return -1;
            }
        }
        return 0;
    }
    
    void prefetch_all_headers(final Info info, final Comment comment, final int n) throws JOrbisException {
        final Page page = new Page();
        this.vi = new Info[this.links];
        this.vc = new Comment[this.links];
        this.dataoffsets = new long[this.links];
        this.pcmlengths = new long[this.links];
        this.serialnos = new int[this.links];
    Label_0061:
        while (0 < this.links) {
            if (info != null && comment != null && !false) {
                this.vi[0] = info;
                this.vc[0] = comment;
                this.dataoffsets[0] = n;
            }
            else {
                this.seek_helper(this.offsets[0]);
                this.vi[0] = new Info();
                this.vc[0] = new Comment();
                if (this.fetch_headers(this.vi[0], this.vc[0], null, null) == -1) {
                    this.dataoffsets[0] = -1L;
                }
                else {
                    this.dataoffsets[0] = this.offset;
                    this.os.clear();
                }
            }
            this.seek_helper(this.offsets[1]);
            while (true) {
                while (this.get_prev_page(page) != -1) {
                    if (page.granulepos() != -1L) {
                        this.serialnos[0] = page.serialno();
                        this.pcmlengths[0] = page.granulepos();
                        int n2 = 0;
                        ++n2;
                        continue Label_0061;
                    }
                }
                this.vi[0].clear();
                this.vc[0].clear();
                continue;
            }
        }
    }
    
    private int make_decode_ready() {
        if (this.decode_ready) {
            System.exit(1);
        }
        this.vd.synthesis_init(this.vi[0]);
        this.vb.init(this.vd);
        this.decode_ready = true;
        return 0;
    }
    
    int open_seekable() throws JOrbisException {
        final Info info = new Info();
        final Comment comment = new Comment();
        final Page page = new Page();
        final int[] array = { 0 };
        final int fetch_headers = this.fetch_headers(info, comment, array, null);
        final int n = array[0];
        final int n2 = (int)this.offset;
        this.os.clear();
        if (fetch_headers == -1) {
            return -1;
        }
        if (fetch_headers < 0) {
            return fetch_headers;
        }
        this.seekable = true;
        fseek(this.datasource, 0L, 2);
        this.offset = ftell(this.datasource);
        final long offset = this.offset;
        final long n3 = this.get_prev_page(page);
        if (page.serialno() != n) {
            if (this.bisect_forward_serialno(0L, 0L, n3 + 1L, n, 0) < 0) {
                this.clear();
                return -128;
            }
        }
        else if (this.bisect_forward_serialno(0L, n3, n3 + 1L, n, 0) < 0) {
            this.clear();
            return -128;
        }
        this.prefetch_all_headers(info, comment, n2);
        return 0;
    }
    
    int open_nonseekable() {
        this.links = 1;
        (this.vi = new Info[this.links])[0] = new Info();
        (this.vc = new Comment[this.links])[0] = new Comment();
        final int[] array = { 0 };
        if (this.fetch_headers(this.vi[0], this.vc[0], array, null) == -1) {
            return -1;
        }
        this.current_serialno = array[0];
        this.make_decode_ready();
        return 0;
    }
    
    void decode_clear() {
        this.os.clear();
        this.vd.clear();
        this.vb.clear();
        this.decode_ready = false;
        this.bittrack = 0.0f;
        this.samptrack = 0.0f;
    }
    
    int process_packet(final int p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: invokespecial   com/jcraft/jogg/Page.<init>:()V
        //     7: astore_2       
        //     8: aload_0        
        //     9: getfield        com/jcraft/jorbis/VorbisFile.decode_ready:Z
        //    12: ifeq            209
        //    15: new             Lcom/jcraft/jogg/Packet;
        //    18: dup            
        //    19: invokespecial   com/jcraft/jogg/Packet.<init>:()V
        //    22: astore_3       
        //    23: aload_0        
        //    24: getfield        com/jcraft/jorbis/VorbisFile.os:Lcom/jcraft/jogg/StreamState;
        //    27: aload_3        
        //    28: invokevirtual   com/jcraft/jogg/StreamState.packetout:(Lcom/jcraft/jogg/Packet;)I
        //    31: istore          4
        //    33: iload           4
        //    35: ifle            209
        //    38: aload_3        
        //    39: getfield        com/jcraft/jogg/Packet.granulepos:J
        //    42: lstore          5
        //    44: aload_0        
        //    45: getfield        com/jcraft/jorbis/VorbisFile.vb:Lcom/jcraft/jorbis/Block;
        //    48: aload_3        
        //    49: invokevirtual   com/jcraft/jorbis/Block.synthesis:(Lcom/jcraft/jogg/Packet;)I
        //    52: ifne            209
        //    55: aload_0        
        //    56: getfield        com/jcraft/jorbis/VorbisFile.vd:Lcom/jcraft/jorbis/DspState;
        //    59: aconst_null    
        //    60: checkcast       [[[F
        //    63: aconst_null    
        //    64: invokevirtual   com/jcraft/jorbis/DspState.synthesis_pcmout:([[[F[I)I
        //    67: istore          7
        //    69: aload_0        
        //    70: getfield        com/jcraft/jorbis/VorbisFile.vd:Lcom/jcraft/jorbis/DspState;
        //    73: aload_0        
        //    74: getfield        com/jcraft/jorbis/VorbisFile.vb:Lcom/jcraft/jorbis/Block;
        //    77: invokevirtual   com/jcraft/jorbis/DspState.synthesis_blockin:(Lcom/jcraft/jorbis/Block;)I
        //    80: pop            
        //    81: aload_0        
        //    82: dup            
        //    83: getfield        com/jcraft/jorbis/VorbisFile.samptrack:F
        //    86: aload_0        
        //    87: getfield        com/jcraft/jorbis/VorbisFile.vd:Lcom/jcraft/jorbis/DspState;
        //    90: aconst_null    
        //    91: checkcast       [[[F
        //    94: aconst_null    
        //    95: invokevirtual   com/jcraft/jorbis/DspState.synthesis_pcmout:([[[F[I)I
        //    98: iload           7
        //   100: isub           
        //   101: i2f            
        //   102: fadd           
        //   103: putfield        com/jcraft/jorbis/VorbisFile.samptrack:F
        //   106: aload_0        
        //   107: dup            
        //   108: getfield        com/jcraft/jorbis/VorbisFile.bittrack:F
        //   111: aload_3        
        //   112: getfield        com/jcraft/jogg/Packet.bytes:I
        //   115: bipush          8
        //   117: imul           
        //   118: i2f            
        //   119: fadd           
        //   120: putfield        com/jcraft/jorbis/VorbisFile.bittrack:F
        //   123: lload           5
        //   125: ldc2_w          -1
        //   128: lcmp           
        //   129: ifeq            207
        //   132: aload_3        
        //   133: getfield        com/jcraft/jogg/Packet.e_o_s:I
        //   136: ifne            207
        //   139: aload_0        
        //   140: getfield        com/jcraft/jorbis/VorbisFile.seekable:Z
        //   143: ifeq            153
        //   146: aload_0        
        //   147: getfield        com/jcraft/jorbis/VorbisFile.current_link:I
        //   150: goto            154
        //   153: iconst_0       
        //   154: istore          7
        //   156: aload_0        
        //   157: getfield        com/jcraft/jorbis/VorbisFile.vd:Lcom/jcraft/jorbis/DspState;
        //   160: aconst_null    
        //   161: checkcast       [[[F
        //   164: aconst_null    
        //   165: invokevirtual   com/jcraft/jorbis/DspState.synthesis_pcmout:([[[F[I)I
        //   168: istore          8
        //   170: lload           5
        //   172: iload           8
        //   174: i2l            
        //   175: lsub           
        //   176: lstore          5
        //   178: iconst_0       
        //   179: iload           7
        //   181: if_icmpge       201
        //   184: lload           5
        //   186: aload_0        
        //   187: getfield        com/jcraft/jorbis/VorbisFile.pcmlengths:[J
        //   190: iconst_0       
        //   191: laload         
        //   192: ladd           
        //   193: lstore          5
        //   195: iinc            9, 1
        //   198: goto            178
        //   201: aload_0        
        //   202: lload           5
        //   204: putfield        com/jcraft/jorbis/VorbisFile.pcm_offset:J
        //   207: iconst_1       
        //   208: ireturn        
        //   209: iload_1        
        //   210: ifne            215
        //   213: iconst_0       
        //   214: ireturn        
        //   215: aload_0        
        //   216: aload_2        
        //   217: ldc2_w          -1
        //   220: invokespecial   com/jcraft/jorbis/VorbisFile.get_next_page:(Lcom/jcraft/jogg/Page;J)I
        //   223: ifge            228
        //   226: iconst_0       
        //   227: ireturn        
        //   228: aload_0        
        //   229: dup            
        //   230: getfield        com/jcraft/jorbis/VorbisFile.bittrack:F
        //   233: aload_2        
        //   234: getfield        com/jcraft/jogg/Page.header_len:I
        //   237: bipush          8
        //   239: imul           
        //   240: i2f            
        //   241: fadd           
        //   242: putfield        com/jcraft/jorbis/VorbisFile.bittrack:F
        //   245: aload_0        
        //   246: getfield        com/jcraft/jorbis/VorbisFile.decode_ready:Z
        //   249: ifeq            267
        //   252: aload_0        
        //   253: getfield        com/jcraft/jorbis/VorbisFile.current_serialno:I
        //   256: aload_2        
        //   257: invokevirtual   com/jcraft/jogg/Page.serialno:()I
        //   260: if_icmpeq       267
        //   263: aload_0        
        //   264: invokevirtual   com/jcraft/jorbis/VorbisFile.decode_clear:()V
        //   267: aload_0        
        //   268: getfield        com/jcraft/jorbis/VorbisFile.decode_ready:Z
        //   271: ifne            413
        //   274: aload_0        
        //   275: getfield        com/jcraft/jorbis/VorbisFile.seekable:Z
        //   278: ifeq            356
        //   281: aload_0        
        //   282: aload_2        
        //   283: invokevirtual   com/jcraft/jogg/Page.serialno:()I
        //   286: putfield        com/jcraft/jorbis/VorbisFile.current_serialno:I
        //   289: iconst_0       
        //   290: aload_0        
        //   291: getfield        com/jcraft/jorbis/VorbisFile.links:I
        //   294: if_icmpge       319
        //   297: aload_0        
        //   298: getfield        com/jcraft/jorbis/VorbisFile.serialnos:[I
        //   301: iconst_0       
        //   302: iaload         
        //   303: aload_0        
        //   304: getfield        com/jcraft/jorbis/VorbisFile.current_serialno:I
        //   307: if_icmpne       313
        //   310: goto            319
        //   313: iinc            3, 1
        //   316: goto            289
        //   319: iconst_0       
        //   320: aload_0        
        //   321: getfield        com/jcraft/jorbis/VorbisFile.links:I
        //   324: if_icmpne       329
        //   327: iconst_m1      
        //   328: ireturn        
        //   329: aload_0        
        //   330: iconst_0       
        //   331: putfield        com/jcraft/jorbis/VorbisFile.current_link:I
        //   334: aload_0        
        //   335: getfield        com/jcraft/jorbis/VorbisFile.os:Lcom/jcraft/jogg/StreamState;
        //   338: aload_0        
        //   339: getfield        com/jcraft/jorbis/VorbisFile.current_serialno:I
        //   342: invokevirtual   com/jcraft/jogg/StreamState.init:(I)V
        //   345: aload_0        
        //   346: getfield        com/jcraft/jorbis/VorbisFile.os:Lcom/jcraft/jogg/StreamState;
        //   349: invokevirtual   com/jcraft/jogg/StreamState.reset:()I
        //   352: pop            
        //   353: goto            408
        //   356: iconst_1       
        //   357: newarray        I
        //   359: astore          4
        //   361: aload_0        
        //   362: aload_0        
        //   363: getfield        com/jcraft/jorbis/VorbisFile.vi:[Lcom/jcraft/jorbis/Info;
        //   366: iconst_0       
        //   367: aaload         
        //   368: aload_0        
        //   369: getfield        com/jcraft/jorbis/VorbisFile.vc:[Lcom/jcraft/jorbis/Comment;
        //   372: iconst_0       
        //   373: aaload         
        //   374: aload           4
        //   376: aload_2        
        //   377: invokevirtual   com/jcraft/jorbis/VorbisFile.fetch_headers:(Lcom/jcraft/jorbis/Info;Lcom/jcraft/jorbis/Comment;[ILcom/jcraft/jogg/Page;)I
        //   380: istore          5
        //   382: aload_0        
        //   383: aload           4
        //   385: iconst_0       
        //   386: iaload         
        //   387: putfield        com/jcraft/jorbis/VorbisFile.current_serialno:I
        //   390: iload           5
        //   392: ifeq            398
        //   395: iload           5
        //   397: ireturn        
        //   398: aload_0        
        //   399: dup            
        //   400: getfield        com/jcraft/jorbis/VorbisFile.current_link:I
        //   403: iconst_1       
        //   404: iadd           
        //   405: putfield        com/jcraft/jorbis/VorbisFile.current_link:I
        //   408: aload_0        
        //   409: invokespecial   com/jcraft/jorbis/VorbisFile.make_decode_ready:()I
        //   412: pop            
        //   413: aload_0        
        //   414: getfield        com/jcraft/jorbis/VorbisFile.os:Lcom/jcraft/jogg/StreamState;
        //   417: aload_2        
        //   418: invokevirtual   com/jcraft/jogg/StreamState.pagein:(Lcom/jcraft/jogg/Page;)I
        //   421: pop            
        //   422: goto            8
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    int clear() {
        this.vb.clear();
        this.vd.clear();
        this.os.clear();
        if (this.vi != null && this.links != 0) {
            while (0 < this.links) {
                this.vi[0].clear();
                this.vc[0].clear();
                int n = 0;
                ++n;
            }
            this.vi = null;
            this.vc = null;
        }
        if (this.dataoffsets != null) {
            this.dataoffsets = null;
        }
        if (this.pcmlengths != null) {
            this.pcmlengths = null;
        }
        if (this.serialnos != null) {
            this.serialnos = null;
        }
        if (this.offsets != null) {
            this.offsets = null;
        }
        this.oy.clear();
        return 0;
    }
    
    static int fseek(final InputStream inputStream, final long n, final int n2) {
        if (inputStream instanceof SeekableInputStream) {
            final SeekableInputStream seekableInputStream = (SeekableInputStream)inputStream;
            if (n2 == 0) {
                seekableInputStream.seek(n);
            }
            else if (n2 == 2) {
                seekableInputStream.seek(seekableInputStream.getLength() - n);
            }
            return 0;
        }
        if (n2 == 0) {
            inputStream.reset();
        }
        inputStream.skip(n);
        return 0;
    }
    
    static long ftell(final InputStream inputStream) {
        if (inputStream instanceof SeekableInputStream) {
            return ((SeekableInputStream)inputStream).tell();
        }
        return 0L;
    }
    
    int open(final InputStream inputStream, final byte[] array, final int n) throws JOrbisException {
        return this.open_callbacks(inputStream, array, n);
    }
    
    int open_callbacks(final InputStream datasource, final byte[] array, final int n) throws JOrbisException {
        this.datasource = datasource;
        this.oy.init();
        if (array != null) {
            System.arraycopy(array, 0, this.oy.data, this.oy.buffer(n), n);
            this.oy.wrote(n);
        }
        int n2;
        if (datasource instanceof SeekableInputStream) {
            n2 = this.open_seekable();
        }
        else {
            n2 = this.open_nonseekable();
        }
        if (n2 != 0) {
            this.datasource = null;
            this.clear();
        }
        return n2;
    }
    
    public int streams() {
        return this.links;
    }
    
    public boolean seekable() {
        return this.seekable;
    }
    
    public int bitrate(final int n) {
        if (n >= this.links) {
            return -1;
        }
        if (!this.seekable && n != 0) {
            return this.bitrate(0);
        }
        if (n < 0) {
            long n2 = 0L;
            while (0 < this.links) {
                n2 += (this.offsets[1] - this.dataoffsets[0]) * 8L;
                int n3 = 0;
                ++n3;
            }
            return (int)Math.rint(n2 / this.time_total(-1));
        }
        if (this.seekable) {
            return (int)Math.rint((this.offsets[n + 1] - this.dataoffsets[n]) * 8L / this.time_total(n));
        }
        if (this.vi[n].bitrate_nominal > 0) {
            return this.vi[n].bitrate_nominal;
        }
        if (this.vi[n].bitrate_upper <= 0) {
            return -1;
        }
        if (this.vi[n].bitrate_lower > 0) {
            return (this.vi[n].bitrate_upper + this.vi[n].bitrate_lower) / 2;
        }
        return this.vi[n].bitrate_upper;
    }
    
    public int bitrate_instant() {
        final int n = this.seekable ? this.current_link : 0;
        if (this.samptrack == 0.0f) {
            return -1;
        }
        final int n2 = (int)(this.bittrack / this.samptrack * this.vi[n].rate + 0.5);
        this.bittrack = 0.0f;
        this.samptrack = 0.0f;
        return n2;
    }
    
    public int serialnumber(final int n) {
        if (n >= this.links) {
            return -1;
        }
        if (!this.seekable && n >= 0) {
            return this.serialnumber(-1);
        }
        if (n < 0) {
            return this.current_serialno;
        }
        return this.serialnos[n];
    }
    
    public long raw_total(final int n) {
        if (!this.seekable || n >= this.links) {
            return -1L;
        }
        if (n < 0) {
            long n2 = 0L;
            while (0 < this.links) {
                n2 += this.raw_total(0);
                int n3 = 0;
                ++n3;
            }
            return n2;
        }
        return this.offsets[n + 1] - this.offsets[n];
    }
    
    public long pcm_total(final int n) {
        if (!this.seekable || n >= this.links) {
            return -1L;
        }
        if (n < 0) {
            long n2 = 0L;
            while (0 < this.links) {
                n2 += this.pcm_total(0);
                int n3 = 0;
                ++n3;
            }
            return n2;
        }
        return this.pcmlengths[n];
    }
    
    public float time_total(final int n) {
        if (!this.seekable || n >= this.links) {
            return -1.0f;
        }
        if (n < 0) {
            float n2 = 0.0f;
            while (0 < this.links) {
                n2 += this.time_total(0);
                int n3 = 0;
                ++n3;
            }
            return n2;
        }
        return this.pcmlengths[n] / (float)this.vi[n].rate;
    }
    
    public int raw_seek(final int n) {
        if (!this.seekable) {
            return -1;
        }
        if (n < 0 || n > this.offsets[this.links]) {
            this.pcm_offset = -1L;
            this.decode_clear();
            return -1;
        }
        this.pcm_offset = -1L;
        this.decode_clear();
        this.seek_helper(n);
        switch (this.process_packet(1)) {
            case 0: {
                this.pcm_offset = this.pcm_total(-1);
                return 0;
            }
            case -1: {
                this.pcm_offset = -1L;
                this.decode_clear();
                return -1;
            }
            default: {
                while (true) {
                    switch (this.process_packet(0)) {
                        case 0: {
                            return 0;
                        }
                        case -1: {
                            this.pcm_offset = -1L;
                            this.decode_clear();
                            return -1;
                        }
                        default: {
                            continue;
                        }
                    }
                }
                break;
            }
        }
    }
    
    public int pcm_seek(final long n) {
        long pcm_total = this.pcm_total(-1);
        if (!this.seekable) {
            return -1;
        }
        if (n < 0L || n > pcm_total) {
            this.pcm_offset = -1L;
            this.decode_clear();
            return -1;
        }
        int n2 = this.links - 1;
        while (-1 >= 0) {
            pcm_total -= this.pcmlengths[-1];
            if (n >= pcm_total) {
                break;
            }
            --n2;
        }
        final long n3 = n - pcm_total;
        long n4 = this.offsets[0];
        long offset = this.offsets[-1];
        int n5 = (int)offset;
        final Page page = new Page();
        while (offset < n4) {
            long n6;
            if (n4 - offset < 8500L) {
                n6 = offset;
            }
            else {
                n6 = (n4 + offset) / 2L;
            }
            this.seek_helper(n6);
            final int get_next_page = this.get_next_page(page, n4 - n6);
            if (get_next_page == -1) {
                n4 = n6;
            }
            else if (page.granulepos() < n3) {
                n5 = get_next_page;
                offset = this.offset;
            }
            else {
                n4 = n6;
            }
        }
        if (this.raw_seek(n5) != 0) {
            this.pcm_offset = -1L;
            this.decode_clear();
            return -1;
        }
        if (this.pcm_offset >= n) {
            this.pcm_offset = -1L;
            this.decode_clear();
            return -1;
        }
        if (n > this.pcm_total(-1)) {
            this.pcm_offset = -1L;
            this.decode_clear();
            return -1;
        }
        while (this.pcm_offset < n) {
            final int n7 = (int)(n - this.pcm_offset);
            int synthesis_pcmout = this.vd.synthesis_pcmout(new float[1][][], new int[this.getInfo(-1).channels]);
            if (synthesis_pcmout > n7) {
                synthesis_pcmout = n7;
            }
            this.vd.synthesis_read(synthesis_pcmout);
            this.pcm_offset += synthesis_pcmout;
            if (synthesis_pcmout < n7 && this.process_packet(1) == 0) {
                this.pcm_offset = this.pcm_total(-1);
            }
        }
        return 0;
    }
    
    int time_seek(final float n) {
        long pcm_total = this.pcm_total(-1);
        float time_total = this.time_total(-1);
        if (!this.seekable) {
            return -1;
        }
        if (n < 0.0f || n > time_total) {
            this.pcm_offset = -1L;
            this.decode_clear();
            return -1;
        }
        int n2 = this.links - 1;
        while (-1 >= 0) {
            pcm_total -= this.pcmlengths[-1];
            time_total -= this.time_total(-1);
            if (n >= time_total) {
                break;
            }
            --n2;
        }
        return this.pcm_seek((long)(pcm_total + (n - time_total) * this.vi[-1].rate));
    }
    
    public long raw_tell() {
        return this.offset;
    }
    
    public long pcm_tell() {
        return this.pcm_offset;
    }
    
    public float time_tell() {
        long pcm_total = 0L;
        float time_total = 0.0f;
        if (this.seekable) {
            pcm_total = this.pcm_total(-1);
            time_total = this.time_total(-1);
            int n = this.links - 1;
            while (-1 >= 0) {
                pcm_total -= this.pcmlengths[-1];
                time_total -= this.time_total(-1);
                if (this.pcm_offset >= pcm_total) {
                    break;
                }
                --n;
            }
        }
        return time_total + (this.pcm_offset - pcm_total) / (float)this.vi[-1].rate;
    }
    
    public Info getInfo(final int n) {
        if (this.seekable) {
            if (n < 0) {
                if (this.decode_ready) {
                    return this.vi[this.current_link];
                }
                return null;
            }
            else {
                if (n >= this.links) {
                    return null;
                }
                return this.vi[n];
            }
        }
        else {
            if (this.decode_ready) {
                return this.vi[0];
            }
            return null;
        }
    }
    
    public Comment getComment(final int n) {
        if (this.seekable) {
            if (n < 0) {
                if (this.decode_ready) {
                    return this.vc[this.current_link];
                }
                return null;
            }
            else {
                if (n >= this.links) {
                    return null;
                }
                return this.vc[n];
            }
        }
        else {
            if (this.decode_ready) {
                return this.vc[0];
            }
            return null;
        }
    }
    
    int host_is_big_endian() {
        return 1;
    }
    
    int read(final byte[] array, final int n, final int n2, final int n3, final int n4, final int[] array2) {
        final int host_is_big_endian = this.host_is_big_endian();
        while (true) {
            if (this.decode_ready) {
                final float[][][] array3 = { null };
                final int[] array4 = new int[this.getInfo(-1).channels];
                int synthesis_pcmout = this.vd.synthesis_pcmout(array3, array4);
                final float[][] array5 = array3[0];
                if (synthesis_pcmout != 0) {
                    final int channels = this.getInfo(-1).channels;
                    final int n5 = n3 * channels;
                    if (synthesis_pcmout > n / n5) {
                        synthesis_pcmout = n / n5;
                    }
                    if (n3 == 1) {
                        final int n6 = (n4 != 0) ? 0 : 128;
                        while (0 < synthesis_pcmout) {
                            while (0 < channels) {
                                final int n7 = (int)(array5[0][array4[0] + 0] * 128.0 + 0.5);
                                if (-32768 <= 127) {
                                    if (-32768 < -128) {}
                                }
                                final int n8 = 0;
                                int n9 = 0;
                                ++n9;
                                array[n8] = (byte)(-32768 + n6);
                                int n10 = 0;
                                ++n10;
                            }
                            int n11 = 0;
                            ++n11;
                        }
                    }
                    else {
                        final int n12 = (n4 != 0) ? 0 : 32768;
                        if (host_is_big_endian == n2) {
                            if (n4 != 0) {
                                while (0 < channels) {
                                    final int n10 = array4[0];
                                    while (0 < synthesis_pcmout) {
                                        final int n13 = (int)(array5[0][0] * 32768.0 + 0.5);
                                        if (-32768 <= 32767) {
                                            if (-32768 < -32768) {}
                                        }
                                        array[0] = (byte)16777088;
                                        array[1] = (byte)(-32768);
                                        int n14 = 0;
                                        ++n14;
                                    }
                                    int n11 = 0;
                                    ++n11;
                                }
                            }
                            else {
                                while (0 < channels) {
                                    final float[] array6 = array5[0];
                                    while (0 < synthesis_pcmout) {
                                        final int n15 = (int)(array6[0] * 32768.0 + 0.5);
                                        if (-32768 <= 32767) {
                                            if (-32768 < -32768) {}
                                        }
                                        array[0] = (byte)(-32768 + n12 >>> 8);
                                        array[1] = (byte)(-32768 + n12);
                                        int n14 = 0;
                                        ++n14;
                                    }
                                    int n11 = 0;
                                    ++n11;
                                }
                            }
                        }
                        else if (n2 != 0) {
                            while (0 < synthesis_pcmout) {
                                while (0 < channels) {
                                    final int n16 = (int)(array5[0][0] * 32768.0 + 0.5);
                                    if (-32768 <= 32767) {
                                        if (-32768 < -32768) {}
                                    }
                                    final int n17 = 0;
                                    int n9 = 0;
                                    ++n9;
                                    array[n17] = (byte)16777088;
                                    final int n18 = 0;
                                    ++n9;
                                    array[n18] = (byte)(-32768);
                                    int n10 = 0;
                                    ++n10;
                                }
                                int n11 = 0;
                                ++n11;
                            }
                        }
                        else {
                            while (0 < synthesis_pcmout) {
                                while (0 < channels) {
                                    final int n19 = (int)(array5[0][0] * 32768.0 + 0.5);
                                    if (-32768 <= 32767) {
                                        if (-32768 < -32768) {}
                                    }
                                    final int n20 = 0;
                                    int n9 = 0;
                                    ++n9;
                                    array[n20] = (byte)(-32768);
                                    final int n21 = 0;
                                    ++n9;
                                    array[n21] = (byte)16777088;
                                    int n10 = 0;
                                    ++n10;
                                }
                                int n11 = 0;
                                ++n11;
                            }
                        }
                    }
                    this.vd.synthesis_read(synthesis_pcmout);
                    this.pcm_offset += synthesis_pcmout;
                    if (array2 != null) {
                        array2[0] = this.current_link;
                    }
                    return synthesis_pcmout * n5;
                }
            }
            switch (this.process_packet(1)) {
                case 0: {
                    return 0;
                }
                case -1: {
                    return -1;
                }
                default: {
                    continue;
                }
            }
        }
    }
    
    public Info[] getInfo() {
        return this.vi;
    }
    
    public Comment[] getComment() {
        return this.vc;
    }
    
    public void close() throws IOException {
        this.datasource.close();
    }
    
    class SeekableInputStream extends InputStream
    {
        RandomAccessFile raf;
        final String mode = "r";
        final VorbisFile this$0;
        
        SeekableInputStream(final VorbisFile this$0, final String s) throws IOException {
            this.this$0 = this$0;
            this.raf = null;
            this.raf = new RandomAccessFile(s, "r");
        }
        
        @Override
        public int read() throws IOException {
            return this.raf.read();
        }
        
        @Override
        public int read(final byte[] array) throws IOException {
            return this.raf.read(array);
        }
        
        @Override
        public int read(final byte[] array, final int n, final int n2) throws IOException {
            return this.raf.read(array, n, n2);
        }
        
        @Override
        public long skip(final long n) throws IOException {
            return this.raf.skipBytes((int)n);
        }
        
        public long getLength() throws IOException {
            return this.raf.length();
        }
        
        public long tell() throws IOException {
            return this.raf.getFilePointer();
        }
        
        @Override
        public int available() throws IOException {
            return (this.raf.length() != this.raf.getFilePointer()) ? 1 : 0;
        }
        
        @Override
        public void close() throws IOException {
            this.raf.close();
        }
        
        @Override
        public synchronized void mark(final int n) {
        }
        
        @Override
        public synchronized void reset() throws IOException {
        }
        
        @Override
        public boolean markSupported() {
            return false;
        }
        
        public void seek(final long n) throws IOException {
            this.raf.seek(n);
        }
    }
}
