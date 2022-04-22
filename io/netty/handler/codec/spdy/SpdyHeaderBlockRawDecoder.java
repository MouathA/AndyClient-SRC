package io.netty.handler.codec.spdy;

import io.netty.buffer.*;

public class SpdyHeaderBlockRawDecoder extends SpdyHeaderBlockDecoder
{
    private static final int LENGTH_FIELD_SIZE = 4;
    private final int maxHeaderSize;
    private State state;
    private ByteBuf cumulation;
    private int headerSize;
    private int numHeaders;
    private int length;
    private String name;
    
    public SpdyHeaderBlockRawDecoder(final SpdyVersion spdyVersion, final int maxHeaderSize) {
        if (spdyVersion == null) {
            throw new NullPointerException("spdyVersion");
        }
        this.maxHeaderSize = maxHeaderSize;
        this.state = State.READ_NUM_HEADERS;
    }
    
    private static int readLengthField(final ByteBuf byteBuf) {
        final int signedInt = SpdyCodecUtil.getSignedInt(byteBuf, byteBuf.readerIndex());
        byteBuf.skipBytes(4);
        return signedInt;
    }
    
    @Override
    void decode(final ByteBuf byteBuf, final SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        if (byteBuf == null) {
            throw new NullPointerException("headerBlock");
        }
        if (spdyHeadersFrame == null) {
            throw new NullPointerException("frame");
        }
        if (this.cumulation == null) {
            this.decodeHeaderBlock(byteBuf, spdyHeadersFrame);
            if (byteBuf.isReadable()) {
                (this.cumulation = byteBuf.alloc().buffer(byteBuf.readableBytes())).writeBytes(byteBuf);
            }
        }
        else {
            this.cumulation.writeBytes(byteBuf);
            this.decodeHeaderBlock(this.cumulation, spdyHeadersFrame);
            if (this.cumulation.isReadable()) {
                this.cumulation.discardReadBytes();
            }
            else {
                this.releaseBuffer();
            }
        }
    }
    
    protected void decodeHeaderBlock(final ByteBuf byteBuf, final SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        while (byteBuf.isReadable()) {
            switch (this.state) {
                case READ_NUM_HEADERS: {
                    if (byteBuf.readableBytes() < 4) {
                        return;
                    }
                    this.numHeaders = readLengthField(byteBuf);
                    if (this.numHeaders < 0) {
                        this.state = State.ERROR;
                        spdyHeadersFrame.setInvalid();
                        continue;
                    }
                    if (this.numHeaders == 0) {
                        this.state = State.END_HEADER_BLOCK;
                        continue;
                    }
                    this.state = State.READ_NAME_LENGTH;
                    continue;
                }
                case READ_NAME_LENGTH: {
                    if (byteBuf.readableBytes() < 4) {
                        return;
                    }
                    this.length = readLengthField(byteBuf);
                    if (this.length <= 0) {
                        this.state = State.ERROR;
                        spdyHeadersFrame.setInvalid();
                        continue;
                    }
                    if (this.length > this.maxHeaderSize || this.headerSize > this.maxHeaderSize - this.length) {
                        this.headerSize = this.maxHeaderSize + 1;
                        this.state = State.SKIP_NAME;
                        spdyHeadersFrame.setTruncated();
                        continue;
                    }
                    this.headerSize += this.length;
                    this.state = State.READ_NAME;
                    continue;
                }
                case READ_NAME: {
                    if (byteBuf.readableBytes() < this.length) {
                        return;
                    }
                    final byte[] array = new byte[this.length];
                    byteBuf.readBytes(array);
                    this.name = new String(array, "UTF-8");
                    if (spdyHeadersFrame.headers().contains(this.name)) {
                        this.state = State.ERROR;
                        spdyHeadersFrame.setInvalid();
                        continue;
                    }
                    this.state = State.READ_VALUE_LENGTH;
                    continue;
                }
                case SKIP_NAME: {
                    final int min = Math.min(byteBuf.readableBytes(), this.length);
                    byteBuf.skipBytes(min);
                    this.length -= min;
                    if (this.length == 0) {
                        this.state = State.READ_VALUE_LENGTH;
                        continue;
                    }
                    continue;
                }
                case READ_VALUE_LENGTH: {
                    if (byteBuf.readableBytes() < 4) {
                        return;
                    }
                    this.length = readLengthField(byteBuf);
                    if (this.length < 0) {
                        this.state = State.ERROR;
                        spdyHeadersFrame.setInvalid();
                        continue;
                    }
                    if (this.length == 0) {
                        if (!spdyHeadersFrame.isTruncated()) {
                            spdyHeadersFrame.headers().add(this.name, "");
                        }
                        this.name = null;
                        if (--this.numHeaders == 0) {
                            this.state = State.END_HEADER_BLOCK;
                            continue;
                        }
                        this.state = State.READ_NAME_LENGTH;
                        continue;
                    }
                    else {
                        if (this.length > this.maxHeaderSize || this.headerSize > this.maxHeaderSize - this.length) {
                            this.headerSize = this.maxHeaderSize + 1;
                            this.name = null;
                            this.state = State.SKIP_VALUE;
                            spdyHeadersFrame.setTruncated();
                            continue;
                        }
                        this.headerSize += this.length;
                        this.state = State.READ_VALUE;
                        continue;
                    }
                    break;
                }
                case READ_VALUE: {
                    if (byteBuf.readableBytes() < this.length) {
                        return;
                    }
                    final byte[] array2 = new byte[this.length];
                    byteBuf.readBytes(array2);
                    if (array2[0] == 0) {
                        this.state = State.ERROR;
                        spdyHeadersFrame.setInvalid();
                        continue;
                    }
                    while (0 < this.length) {
                        int n = 0;
                        while (0 < array2.length && array2[0] != 0) {
                            ++n;
                        }
                        if (0 < array2.length && (1 == array2.length || array2[1] == 0)) {
                            this.state = State.ERROR;
                            spdyHeadersFrame.setInvalid();
                            break;
                        }
                        spdyHeadersFrame.headers().add(this.name, new String(array2, 0, 0, "UTF-8"));
                        ++n;
                    }
                    this.name = null;
                    if (this.state == State.ERROR) {
                        continue;
                    }
                    if (--this.numHeaders == 0) {
                        this.state = State.END_HEADER_BLOCK;
                        continue;
                    }
                    this.state = State.READ_NAME_LENGTH;
                    continue;
                }
                case SKIP_VALUE: {
                    final int min2 = Math.min(byteBuf.readableBytes(), this.length);
                    byteBuf.skipBytes(min2);
                    this.length -= min2;
                    if (this.length != 0) {
                        continue;
                    }
                    if (--this.numHeaders == 0) {
                        this.state = State.END_HEADER_BLOCK;
                        continue;
                    }
                    this.state = State.READ_NAME_LENGTH;
                    continue;
                }
                case END_HEADER_BLOCK: {
                    this.state = State.ERROR;
                    spdyHeadersFrame.setInvalid();
                    continue;
                }
                case ERROR: {
                    byteBuf.skipBytes(byteBuf.readableBytes());
                }
                default: {
                    throw new Error("Shouldn't reach here.");
                }
            }
        }
    }
    
    @Override
    void endHeaderBlock(final SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        if (this.state != State.END_HEADER_BLOCK) {
            spdyHeadersFrame.setInvalid();
        }
        this.releaseBuffer();
        this.headerSize = 0;
        this.name = null;
        this.state = State.READ_NUM_HEADERS;
    }
    
    @Override
    void end() {
        this.releaseBuffer();
    }
    
    private void releaseBuffer() {
        if (this.cumulation != null) {
            this.cumulation.release();
            this.cumulation = null;
        }
    }
    
    private enum State
    {
        READ_NUM_HEADERS("READ_NUM_HEADERS", 0), 
        READ_NAME_LENGTH("READ_NAME_LENGTH", 1), 
        READ_NAME("READ_NAME", 2), 
        SKIP_NAME("SKIP_NAME", 3), 
        READ_VALUE_LENGTH("READ_VALUE_LENGTH", 4), 
        READ_VALUE("READ_VALUE", 5), 
        SKIP_VALUE("SKIP_VALUE", 6), 
        END_HEADER_BLOCK("END_HEADER_BLOCK", 7), 
        ERROR("ERROR", 8);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.READ_NUM_HEADERS, State.READ_NAME_LENGTH, State.READ_NAME, State.SKIP_NAME, State.READ_VALUE_LENGTH, State.READ_VALUE, State.SKIP_VALUE, State.END_HEADER_BLOCK, State.ERROR };
        }
    }
}
