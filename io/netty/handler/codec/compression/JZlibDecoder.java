package io.netty.handler.codec.compression;

import com.jcraft.jzlib.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;

public class JZlibDecoder extends ZlibDecoder
{
    private final Inflater z;
    private byte[] dictionary;
    private boolean finished;
    
    public JZlibDecoder() {
        this(ZlibWrapper.ZLIB);
    }
    
    public JZlibDecoder(final ZlibWrapper zlibWrapper) {
        this.z = new Inflater();
        if (zlibWrapper == null) {
            throw new NullPointerException("wrapper");
        }
        final int init = this.z.init(ZlibUtil.convertWrapperType(zlibWrapper));
        if (init != 0) {
            ZlibUtil.fail(this.z, "initialization failure", init);
        }
    }
    
    public JZlibDecoder(final byte[] dictionary) {
        this.z = new Inflater();
        if (dictionary == null) {
            throw new NullPointerException("dictionary");
        }
        this.dictionary = dictionary;
        final int inflateInit = this.z.inflateInit(JZlib.W_ZLIB);
        if (inflateInit != 0) {
            ZlibUtil.fail(this.z, "initialization failure", inflateInit);
        }
    }
    
    @Override
    public boolean isClosed() {
        return this.finished;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (this.finished) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        if (!byteBuf.isReadable()) {
            return;
        }
        final int readableBytes = byteBuf.readableBytes();
        this.z.avail_in = readableBytes;
        if (byteBuf.hasArray()) {
            this.z.next_in = byteBuf.array();
            this.z.next_in_index = byteBuf.arrayOffset() + byteBuf.readerIndex();
        }
        else {
            final byte[] next_in = new byte[readableBytes];
            byteBuf.getBytes(byteBuf.readerIndex(), next_in);
            this.z.next_in = next_in;
            this.z.next_in_index = 0;
        }
        final int next_in_index = this.z.next_in_index;
        final int avail_out = readableBytes << 1;
        final ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer(avail_out);
    Label_0392:
        while (true) {
            heapBuffer.ensureWritable(this.z.avail_out = avail_out);
            this.z.next_out = heapBuffer.array();
            this.z.next_out_index = heapBuffer.arrayOffset() + heapBuffer.writerIndex();
            final int next_out_index = this.z.next_out_index;
            final int inflate = this.z.inflate(2);
            final int n = this.z.next_out_index - next_out_index;
            if (n > 0) {
                heapBuffer.writerIndex(heapBuffer.writerIndex() + n);
            }
            switch (inflate) {
                case 2: {
                    if (this.dictionary == null) {
                        ZlibUtil.fail(this.z, "decompression failure", inflate);
                        continue;
                    }
                    final int inflateSetDictionary = this.z.inflateSetDictionary(this.dictionary, this.dictionary.length);
                    if (inflateSetDictionary != 0) {
                        ZlibUtil.fail(this.z, "failed to set the dictionary", inflateSetDictionary);
                        continue;
                    }
                    continue;
                }
                case 1: {
                    this.finished = true;
                    this.z.inflateEnd();
                    break Label_0392;
                }
                case 0: {
                    continue;
                }
                case -5: {
                    if (this.z.avail_in <= 0) {
                        break Label_0392;
                    }
                    continue;
                }
                default: {
                    ZlibUtil.fail(this.z, "decompression failure", inflate);
                    continue;
                }
            }
        }
        byteBuf.skipBytes(this.z.next_in_index - next_in_index);
        if (heapBuffer.isReadable()) {
            list.add(heapBuffer);
        }
        else {
            heapBuffer.release();
        }
        this.z.next_in = null;
        this.z.next_out = null;
    }
}
