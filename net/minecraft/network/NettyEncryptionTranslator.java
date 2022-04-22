package net.minecraft.network;

import io.netty.buffer.*;
import io.netty.channel.*;
import javax.crypto.*;

public class NettyEncryptionTranslator
{
    private final Cipher cipher;
    private byte[] field_150505_b;
    private byte[] field_150506_c;
    private static final String __OBFID;
    
    protected NettyEncryptionTranslator(final Cipher cipher) {
        this.field_150505_b = new byte[0];
        this.field_150506_c = new byte[0];
        this.cipher = cipher;
    }
    
    private byte[] func_150502_a(final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        if (this.field_150505_b.length < readableBytes) {
            this.field_150505_b = new byte[readableBytes];
        }
        byteBuf.readBytes(this.field_150505_b, 0, readableBytes);
        return this.field_150505_b;
    }
    
    protected ByteBuf decipher(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws ShortBufferException {
        final int readableBytes = byteBuf.readableBytes();
        final byte[] func_150502_a = this.func_150502_a(byteBuf);
        final ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer(this.cipher.getOutputSize(readableBytes));
        heapBuffer.writerIndex(this.cipher.update(func_150502_a, 0, readableBytes, heapBuffer.array(), heapBuffer.arrayOffset()));
        return heapBuffer;
    }
    
    protected void cipher(final ByteBuf byteBuf, final ByteBuf byteBuf2) throws ShortBufferException {
        final int readableBytes = byteBuf.readableBytes();
        final byte[] func_150502_a = this.func_150502_a(byteBuf);
        final int outputSize = this.cipher.getOutputSize(readableBytes);
        if (this.field_150506_c.length < outputSize) {
            this.field_150506_c = new byte[outputSize];
        }
        byteBuf2.writeBytes(this.field_150506_c, 0, this.cipher.update(func_150502_a, 0, readableBytes, this.field_150506_c));
    }
    
    static {
        __OBFID = "CL_00001237";
    }
}
