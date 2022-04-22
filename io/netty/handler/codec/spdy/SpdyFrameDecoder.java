package io.netty.handler.codec.spdy;

import io.netty.buffer.*;

public class SpdyFrameDecoder
{
    private final int spdyVersion;
    private final int maxChunkSize;
    private final SpdyFrameDecoderDelegate delegate;
    private State state;
    private byte flags;
    private int length;
    private int streamId;
    private int numSettings;
    
    public SpdyFrameDecoder(final SpdyVersion spdyVersion, final SpdyFrameDecoderDelegate spdyFrameDecoderDelegate) {
        this(spdyVersion, spdyFrameDecoderDelegate, 8192);
    }
    
    public SpdyFrameDecoder(final SpdyVersion spdyVersion, final SpdyFrameDecoderDelegate delegate, final int maxChunkSize) {
        if (spdyVersion == null) {
            throw new NullPointerException("spdyVersion");
        }
        if (delegate == null) {
            throw new NullPointerException("delegate");
        }
        if (maxChunkSize <= 0) {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + maxChunkSize);
        }
        this.spdyVersion = spdyVersion.getVersion();
        this.delegate = delegate;
        this.maxChunkSize = maxChunkSize;
        this.state = State.READ_COMMON_HEADER;
    }
    
    public void decode(final ByteBuf byteBuf) {
        while (true) {
            switch (this.state) {
                case READ_COMMON_HEADER: {
                    if (byteBuf.readableBytes() < 8) {
                        return;
                    }
                    final int readerIndex = byteBuf.readerIndex();
                    final int n = readerIndex + 4;
                    final int n2 = readerIndex + 5;
                    byteBuf.skipBytes(8);
                    int spdyVersion;
                    if ((byteBuf.getByte(readerIndex) & 0x80) != 0x0) {
                        spdyVersion = (SpdyCodecUtil.getUnsignedShort(byteBuf, readerIndex) & 0x7FFF);
                        SpdyCodecUtil.getUnsignedShort(byteBuf, readerIndex + 2);
                        this.streamId = 0;
                    }
                    else {
                        spdyVersion = this.spdyVersion;
                        this.streamId = SpdyCodecUtil.getUnsignedInt(byteBuf, readerIndex);
                    }
                    this.flags = byteBuf.getByte(n);
                    this.length = SpdyCodecUtil.getUnsignedMedium(byteBuf, n2);
                    if (spdyVersion != this.spdyVersion) {
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid SPDY Version");
                        continue;
                    }
                    if (!isValidFrameHeader(this.streamId, 0, this.flags, this.length)) {
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid Frame Error");
                        continue;
                    }
                    this.state = getNextState(0, this.length);
                    continue;
                }
                case READ_DATA_FRAME: {
                    if (this.length == 0) {
                        this.state = State.READ_COMMON_HEADER;
                        this.delegate.readDataFrame(this.streamId, hasFlag(this.flags, (byte)1), Unpooled.buffer(0));
                        continue;
                    }
                    final int min = Math.min(this.maxChunkSize, this.length);
                    if (byteBuf.readableBytes() < min) {
                        return;
                    }
                    final ByteBuf buffer = byteBuf.alloc().buffer(min);
                    buffer.writeBytes(byteBuf, min);
                    this.length -= min;
                    if (this.length == 0) {
                        this.state = State.READ_COMMON_HEADER;
                    }
                    this.delegate.readDataFrame(this.streamId, this.length == 0 && hasFlag(this.flags, (byte)1), buffer);
                    continue;
                }
                case READ_SYN_STREAM_FRAME: {
                    if (byteBuf.readableBytes() < 10) {
                        return;
                    }
                    final int readerIndex2 = byteBuf.readerIndex();
                    this.streamId = SpdyCodecUtil.getUnsignedInt(byteBuf, readerIndex2);
                    final int unsignedInt = SpdyCodecUtil.getUnsignedInt(byteBuf, readerIndex2 + 4);
                    final byte b = (byte)(byteBuf.getByte(readerIndex2 + 8) >> 5 & 0x7);
                    final boolean hasFlag = hasFlag(this.flags, (byte)1);
                    final boolean hasFlag2 = hasFlag(this.flags, (byte)2);
                    byteBuf.skipBytes(10);
                    this.length -= 10;
                    if (this.streamId == 0) {
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid SYN_STREAM Frame");
                        continue;
                    }
                    this.state = State.READ_HEADER_BLOCK;
                    this.delegate.readSynStreamFrame(this.streamId, unsignedInt, b, hasFlag, hasFlag2);
                    continue;
                }
                case READ_SYN_REPLY_FRAME: {
                    if (byteBuf.readableBytes() < 4) {
                        return;
                    }
                    this.streamId = SpdyCodecUtil.getUnsignedInt(byteBuf, byteBuf.readerIndex());
                    final boolean hasFlag3 = hasFlag(this.flags, (byte)1);
                    byteBuf.skipBytes(4);
                    this.length -= 4;
                    if (this.streamId == 0) {
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid SYN_REPLY Frame");
                        continue;
                    }
                    this.state = State.READ_HEADER_BLOCK;
                    this.delegate.readSynReplyFrame(this.streamId, hasFlag3);
                    continue;
                }
                case READ_RST_STREAM_FRAME: {
                    if (byteBuf.readableBytes() < 8) {
                        return;
                    }
                    this.streamId = SpdyCodecUtil.getUnsignedInt(byteBuf, byteBuf.readerIndex());
                    final int signedInt = SpdyCodecUtil.getSignedInt(byteBuf, byteBuf.readerIndex() + 4);
                    byteBuf.skipBytes(8);
                    if (this.streamId == 0 || signedInt == 0) {
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid RST_STREAM Frame");
                        continue;
                    }
                    this.state = State.READ_COMMON_HEADER;
                    this.delegate.readRstStreamFrame(this.streamId, signedInt);
                    continue;
                }
                case READ_SETTINGS_FRAME: {
                    if (byteBuf.readableBytes() < 4) {
                        return;
                    }
                    final boolean hasFlag4 = hasFlag(this.flags, (byte)1);
                    this.numSettings = SpdyCodecUtil.getUnsignedInt(byteBuf, byteBuf.readerIndex());
                    byteBuf.skipBytes(4);
                    this.length -= 4;
                    if ((this.length & 0x7) != 0x0 || this.length >> 3 != this.numSettings) {
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid SETTINGS Frame");
                        continue;
                    }
                    this.state = State.READ_SETTING;
                    this.delegate.readSettingsFrame(hasFlag4);
                    continue;
                }
                case READ_SETTING: {
                    if (this.numSettings == 0) {
                        this.state = State.READ_COMMON_HEADER;
                        this.delegate.readSettingsEnd();
                        continue;
                    }
                    if (byteBuf.readableBytes() < 8) {
                        return;
                    }
                    final byte byte1 = byteBuf.getByte(byteBuf.readerIndex());
                    final int unsignedMedium = SpdyCodecUtil.getUnsignedMedium(byteBuf, byteBuf.readerIndex() + 1);
                    final int signedInt2 = SpdyCodecUtil.getSignedInt(byteBuf, byteBuf.readerIndex() + 4);
                    final boolean hasFlag5 = hasFlag(byte1, (byte)1);
                    final boolean hasFlag6 = hasFlag(byte1, (byte)2);
                    byteBuf.skipBytes(8);
                    --this.numSettings;
                    this.delegate.readSetting(unsignedMedium, signedInt2, hasFlag5, hasFlag6);
                    continue;
                }
                case READ_PING_FRAME: {
                    if (byteBuf.readableBytes() < 4) {
                        return;
                    }
                    final int signedInt3 = SpdyCodecUtil.getSignedInt(byteBuf, byteBuf.readerIndex());
                    byteBuf.skipBytes(4);
                    this.state = State.READ_COMMON_HEADER;
                    this.delegate.readPingFrame(signedInt3);
                    continue;
                }
                case READ_GOAWAY_FRAME: {
                    if (byteBuf.readableBytes() < 8) {
                        return;
                    }
                    final int unsignedInt2 = SpdyCodecUtil.getUnsignedInt(byteBuf, byteBuf.readerIndex());
                    final int signedInt4 = SpdyCodecUtil.getSignedInt(byteBuf, byteBuf.readerIndex() + 4);
                    byteBuf.skipBytes(8);
                    this.state = State.READ_COMMON_HEADER;
                    this.delegate.readGoAwayFrame(unsignedInt2, signedInt4);
                    continue;
                }
                case READ_HEADERS_FRAME: {
                    if (byteBuf.readableBytes() < 4) {
                        return;
                    }
                    this.streamId = SpdyCodecUtil.getUnsignedInt(byteBuf, byteBuf.readerIndex());
                    final boolean hasFlag7 = hasFlag(this.flags, (byte)1);
                    byteBuf.skipBytes(4);
                    this.length -= 4;
                    if (this.streamId == 0) {
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid HEADERS Frame");
                        continue;
                    }
                    this.state = State.READ_HEADER_BLOCK;
                    this.delegate.readHeadersFrame(this.streamId, hasFlag7);
                    continue;
                }
                case READ_WINDOW_UPDATE_FRAME: {
                    if (byteBuf.readableBytes() < 8) {
                        return;
                    }
                    this.streamId = SpdyCodecUtil.getUnsignedInt(byteBuf, byteBuf.readerIndex());
                    final int unsignedInt3 = SpdyCodecUtil.getUnsignedInt(byteBuf, byteBuf.readerIndex() + 4);
                    byteBuf.skipBytes(8);
                    if (unsignedInt3 == 0) {
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid WINDOW_UPDATE Frame");
                        continue;
                    }
                    this.state = State.READ_COMMON_HEADER;
                    this.delegate.readWindowUpdateFrame(this.streamId, unsignedInt3);
                    continue;
                }
                case READ_HEADER_BLOCK: {
                    if (this.length == 0) {
                        this.state = State.READ_COMMON_HEADER;
                        this.delegate.readHeaderBlockEnd();
                        continue;
                    }
                    if (!byteBuf.isReadable()) {
                        return;
                    }
                    final int min2 = Math.min(byteBuf.readableBytes(), this.length);
                    final ByteBuf buffer2 = byteBuf.alloc().buffer(min2);
                    buffer2.writeBytes(byteBuf, min2);
                    this.length -= min2;
                    this.delegate.readHeaderBlock(buffer2);
                    continue;
                }
                case DISCARD_FRAME: {
                    final int min3 = Math.min(byteBuf.readableBytes(), this.length);
                    byteBuf.skipBytes(min3);
                    this.length -= min3;
                    if (this.length == 0) {
                        this.state = State.READ_COMMON_HEADER;
                        continue;
                    }
                }
                case FRAME_ERROR: {
                    byteBuf.skipBytes(byteBuf.readableBytes());
                }
                default: {
                    throw new Error("Shouldn't reach here.");
                }
            }
        }
    }
    
    private static boolean hasFlag(final byte b, final byte b2) {
        return (b & b2) != 0x0;
    }
    
    private static State getNextState(final int n, final int n2) {
        switch (n) {
            case 0: {
                return State.READ_DATA_FRAME;
            }
            case 1: {
                return State.READ_SYN_STREAM_FRAME;
            }
            case 2: {
                return State.READ_SYN_REPLY_FRAME;
            }
            case 3: {
                return State.READ_RST_STREAM_FRAME;
            }
            case 4: {
                return State.READ_SETTINGS_FRAME;
            }
            case 6: {
                return State.READ_PING_FRAME;
            }
            case 7: {
                return State.READ_GOAWAY_FRAME;
            }
            case 8: {
                return State.READ_HEADERS_FRAME;
            }
            case 9: {
                return State.READ_WINDOW_UPDATE_FRAME;
            }
            default: {
                if (n2 != 0) {
                    return State.DISCARD_FRAME;
                }
                return State.READ_COMMON_HEADER;
            }
        }
    }
    
    private static boolean isValidFrameHeader(final int n, final int n2, final byte b, final int n3) {
        switch (n2) {
            case 0: {
                return n != 0;
            }
            case 1: {
                return n3 >= 10;
            }
            case 2: {
                return n3 >= 4;
            }
            case 3: {
                return b == 0 && n3 == 8;
            }
            case 4: {
                return n3 >= 4;
            }
            case 6: {
                return n3 == 4;
            }
            case 7: {
                return n3 == 8;
            }
            case 8: {
                return n3 >= 4;
            }
            case 9: {
                return n3 == 8;
            }
            default: {
                return true;
            }
        }
    }
    
    private enum State
    {
        READ_COMMON_HEADER("READ_COMMON_HEADER", 0), 
        READ_DATA_FRAME("READ_DATA_FRAME", 1), 
        READ_SYN_STREAM_FRAME("READ_SYN_STREAM_FRAME", 2), 
        READ_SYN_REPLY_FRAME("READ_SYN_REPLY_FRAME", 3), 
        READ_RST_STREAM_FRAME("READ_RST_STREAM_FRAME", 4), 
        READ_SETTINGS_FRAME("READ_SETTINGS_FRAME", 5), 
        READ_SETTING("READ_SETTING", 6), 
        READ_PING_FRAME("READ_PING_FRAME", 7), 
        READ_GOAWAY_FRAME("READ_GOAWAY_FRAME", 8), 
        READ_HEADERS_FRAME("READ_HEADERS_FRAME", 9), 
        READ_WINDOW_UPDATE_FRAME("READ_WINDOW_UPDATE_FRAME", 10), 
        READ_HEADER_BLOCK("READ_HEADER_BLOCK", 11), 
        DISCARD_FRAME("DISCARD_FRAME", 12), 
        FRAME_ERROR("FRAME_ERROR", 13);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.READ_COMMON_HEADER, State.READ_DATA_FRAME, State.READ_SYN_STREAM_FRAME, State.READ_SYN_REPLY_FRAME, State.READ_RST_STREAM_FRAME, State.READ_SETTINGS_FRAME, State.READ_SETTING, State.READ_PING_FRAME, State.READ_GOAWAY_FRAME, State.READ_HEADERS_FRAME, State.READ_WINDOW_UPDATE_FRAME, State.READ_HEADER_BLOCK, State.DISCARD_FRAME, State.FRAME_ERROR };
        }
    }
}
