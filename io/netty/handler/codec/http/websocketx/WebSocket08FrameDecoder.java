package io.netty.handler.codec.http.websocketx;

import java.util.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import io.netty.handler.codec.*;
import io.netty.util.internal.logging.*;

public class WebSocket08FrameDecoder extends ReplayingDecoder implements WebSocketFrameDecoder
{
    private static final InternalLogger logger;
    private static final byte OPCODE_CONT = 0;
    private static final byte OPCODE_TEXT = 1;
    private static final byte OPCODE_BINARY = 2;
    private static final byte OPCODE_CLOSE = 8;
    private static final byte OPCODE_PING = 9;
    private static final byte OPCODE_PONG = 10;
    private int fragmentedFramesCount;
    private final long maxFramePayloadLength;
    private boolean frameFinalFlag;
    private int frameRsv;
    private int frameOpcode;
    private long framePayloadLength;
    private ByteBuf framePayload;
    private int framePayloadBytesRead;
    private byte[] maskingKey;
    private ByteBuf payloadBuffer;
    private final boolean allowExtensions;
    private final boolean maskedPayload;
    private boolean receivedClosingHandshake;
    private Utf8Validator utf8Validator;
    
    public WebSocket08FrameDecoder(final boolean maskedPayload, final boolean allowExtensions, final int n) {
        super(State.FRAME_START);
        this.maskedPayload = maskedPayload;
        this.allowExtensions = allowExtensions;
        this.maxFramePayloadLength = n;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (this.receivedClosingHandshake) {
            byteBuf.skipBytes(this.actualReadableBytes());
            return;
        }
        switch ((State)this.state()) {
            case FRAME_START: {
                this.framePayloadBytesRead = 0;
                this.framePayloadLength = -1L;
                this.framePayload = null;
                this.payloadBuffer = null;
                final byte byte1 = byteBuf.readByte();
                this.frameFinalFlag = ((byte1 & 0x80) != 0x0);
                this.frameRsv = (byte1 & 0x70) >> 4;
                this.frameOpcode = (byte1 & 0xF);
                if (WebSocket08FrameDecoder.logger.isDebugEnabled()) {
                    WebSocket08FrameDecoder.logger.debug("Decoding WebSocket Frame opCode={}", (Object)this.frameOpcode);
                }
                final byte byte2 = byteBuf.readByte();
                final boolean b = (byte2 & 0x80) != 0x0;
                final int n = byte2 & 0x7F;
                if (this.frameRsv != 0 && !this.allowExtensions) {
                    this.protocolViolation(channelHandlerContext, "RSV != 0 and no extension negotiated, RSV:" + this.frameRsv);
                    return;
                }
                if (this.maskedPayload && !b) {
                    this.protocolViolation(channelHandlerContext, "unmasked client to server frame");
                    return;
                }
                if (this.frameOpcode > 7) {
                    if (!this.frameFinalFlag) {
                        this.protocolViolation(channelHandlerContext, "fragmented control frame");
                        return;
                    }
                    if (n > 125) {
                        this.protocolViolation(channelHandlerContext, "control frame with payload length > 125 octets");
                        return;
                    }
                    if (this.frameOpcode != 8 && this.frameOpcode != 9 && this.frameOpcode != 10) {
                        this.protocolViolation(channelHandlerContext, "control frame using reserved opcode " + this.frameOpcode);
                        return;
                    }
                    if (this.frameOpcode == 8 && n == 1) {
                        this.protocolViolation(channelHandlerContext, "received close control frame with payload len 1");
                        return;
                    }
                }
                else {
                    if (this.frameOpcode != 0 && this.frameOpcode != 1 && this.frameOpcode != 2) {
                        this.protocolViolation(channelHandlerContext, "data frame using reserved opcode " + this.frameOpcode);
                        return;
                    }
                    if (this.fragmentedFramesCount == 0 && this.frameOpcode == 0) {
                        this.protocolViolation(channelHandlerContext, "received continuation data frame outside fragmented message");
                        return;
                    }
                    if (this.fragmentedFramesCount != 0 && this.frameOpcode != 0 && this.frameOpcode != 9) {
                        this.protocolViolation(channelHandlerContext, "received non-continuation data frame while inside fragmented message");
                        return;
                    }
                }
                if (n == 126) {
                    this.framePayloadLength = byteBuf.readUnsignedShort();
                    if (this.framePayloadLength < 126L) {
                        this.protocolViolation(channelHandlerContext, "invalid data frame length (not using minimal length encoding)");
                        return;
                    }
                }
                else if (n == 127) {
                    this.framePayloadLength = byteBuf.readLong();
                    if (this.framePayloadLength < 65536L) {
                        this.protocolViolation(channelHandlerContext, "invalid data frame length (not using minimal length encoding)");
                        return;
                    }
                }
                else {
                    this.framePayloadLength = n;
                }
                if (this.framePayloadLength > this.maxFramePayloadLength) {
                    this.protocolViolation(channelHandlerContext, "Max frame length of " + this.maxFramePayloadLength + " has been exceeded.");
                    return;
                }
                if (WebSocket08FrameDecoder.logger.isDebugEnabled()) {
                    WebSocket08FrameDecoder.logger.debug("Decoding WebSocket Frame length={}", (Object)this.framePayloadLength);
                }
                this.checkpoint(State.MASKING_KEY);
            }
            case MASKING_KEY: {
                if (this.maskedPayload) {
                    if (this.maskingKey == null) {
                        this.maskingKey = new byte[4];
                    }
                    byteBuf.readBytes(this.maskingKey);
                }
                this.checkpoint(State.PAYLOAD);
            }
            case PAYLOAD: {
                final int actualReadableBytes = this.actualReadableBytes();
                final long n2 = this.framePayloadBytesRead + actualReadableBytes;
                if (n2 == this.framePayloadLength) {
                    (this.payloadBuffer = channelHandlerContext.alloc().buffer(actualReadableBytes)).writeBytes(byteBuf, actualReadableBytes);
                }
                else {
                    if (n2 < this.framePayloadLength) {
                        if (this.framePayload == null) {
                            this.framePayload = channelHandlerContext.alloc().buffer(toFrameLength(this.framePayloadLength));
                        }
                        this.framePayload.writeBytes(byteBuf, actualReadableBytes);
                        this.framePayloadBytesRead += actualReadableBytes;
                        return;
                    }
                    if (n2 > this.framePayloadLength) {
                        if (this.framePayload == null) {
                            this.framePayload = channelHandlerContext.alloc().buffer(toFrameLength(this.framePayloadLength));
                        }
                        this.framePayload.writeBytes(byteBuf, toFrameLength(this.framePayloadLength - this.framePayloadBytesRead));
                    }
                }
                this.checkpoint(State.FRAME_START);
                if (this.framePayload == null) {
                    this.framePayload = this.payloadBuffer;
                    this.payloadBuffer = null;
                }
                else if (this.payloadBuffer != null) {
                    this.framePayload.writeBytes(this.payloadBuffer);
                    this.payloadBuffer.release();
                    this.payloadBuffer = null;
                }
                if (this.maskedPayload) {
                    this.unmask(this.framePayload);
                }
                if (this.frameOpcode == 9) {
                    list.add(new PingWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
                    this.framePayload = null;
                    return;
                }
                if (this.frameOpcode == 10) {
                    list.add(new PongWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
                    this.framePayload = null;
                    return;
                }
                if (this.frameOpcode == 8) {
                    this.checkCloseFrameBody(channelHandlerContext, this.framePayload);
                    this.receivedClosingHandshake = true;
                    list.add(new CloseWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
                    this.framePayload = null;
                    return;
                }
                if (this.frameFinalFlag) {
                    if (this.frameOpcode != 9) {
                        this.fragmentedFramesCount = 0;
                        if (this.frameOpcode == 1 || (this.utf8Validator != null && this.utf8Validator.isChecking())) {
                            this.checkUTF8String(channelHandlerContext, this.framePayload);
                            this.utf8Validator.finish();
                        }
                    }
                }
                else {
                    if (this.fragmentedFramesCount == 0) {
                        if (this.frameOpcode == 1) {
                            this.checkUTF8String(channelHandlerContext, this.framePayload);
                        }
                    }
                    else if (this.utf8Validator != null && this.utf8Validator.isChecking()) {
                        this.checkUTF8String(channelHandlerContext, this.framePayload);
                    }
                    ++this.fragmentedFramesCount;
                }
                if (this.frameOpcode == 1) {
                    list.add(new TextWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
                    this.framePayload = null;
                    return;
                }
                if (this.frameOpcode == 2) {
                    list.add(new BinaryWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
                    this.framePayload = null;
                    return;
                }
                if (this.frameOpcode == 0) {
                    list.add(new ContinuationWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
                    this.framePayload = null;
                    return;
                }
                throw new UnsupportedOperationException("Cannot decode web socket frame with opcode: " + this.frameOpcode);
            }
            case CORRUPT: {
                byteBuf.readByte();
            }
            default: {
                throw new Error("Shouldn't reach here.");
            }
        }
    }
    
    private void unmask(final ByteBuf byteBuf) {
        for (int i = byteBuf.readerIndex(); i < byteBuf.writerIndex(); ++i) {
            byteBuf.setByte(i, byteBuf.getByte(i) ^ this.maskingKey[i % 4]);
        }
    }
    
    private void protocolViolation(final ChannelHandlerContext channelHandlerContext, final String s) {
        this.protocolViolation(channelHandlerContext, new CorruptedFrameException(s));
    }
    
    private void protocolViolation(final ChannelHandlerContext channelHandlerContext, final CorruptedFrameException ex) {
        this.checkpoint(State.CORRUPT);
        if (channelHandlerContext.channel().isActive()) {
            channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
        }
        throw ex;
    }
    
    private static int toFrameLength(final long n) {
        if (n > 2147483647L) {
            throw new TooLongFrameException("Length:" + n);
        }
        return (int)n;
    }
    
    private void checkUTF8String(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) {
        if (this.utf8Validator == null) {
            this.utf8Validator = new Utf8Validator();
        }
        this.utf8Validator.check(byteBuf);
    }
    
    protected void checkCloseFrameBody(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) {
        if (byteBuf == null || !byteBuf.isReadable()) {
            return;
        }
        if (byteBuf.readableBytes() == 1) {
            this.protocolViolation(channelHandlerContext, "Invalid close frame body");
        }
        final int readerIndex = byteBuf.readerIndex();
        byteBuf.readerIndex(0);
        final short short1 = byteBuf.readShort();
        if ((short1 >= 0 && short1 <= 999) || (short1 >= 1004 && short1 <= 1006) || (short1 >= 1012 && short1 <= 2999)) {
            this.protocolViolation(channelHandlerContext, "Invalid close frame getStatus code: " + short1);
        }
        if (byteBuf.isReadable()) {
            new Utf8Validator().check(byteBuf);
        }
        byteBuf.readerIndex(readerIndex);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelInactive(channelHandlerContext);
        if (this.framePayload != null) {
            this.framePayload.release();
        }
        if (this.payloadBuffer != null) {
            this.payloadBuffer.release();
        }
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(WebSocket08FrameDecoder.class);
    }
    
    enum State
    {
        FRAME_START("FRAME_START", 0), 
        MASKING_KEY("MASKING_KEY", 1), 
        PAYLOAD("PAYLOAD", 2), 
        CORRUPT("CORRUPT", 3);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.FRAME_START, State.MASKING_KEY, State.PAYLOAD, State.CORRUPT };
        }
    }
}
