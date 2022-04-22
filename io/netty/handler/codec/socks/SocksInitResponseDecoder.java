package io.netty.handler.codec.socks;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import java.util.*;
import io.netty.channel.*;

public class SocksInitResponseDecoder extends ReplayingDecoder
{
    private static final String name = "SOCKS_INIT_RESPONSE_DECODER";
    private SocksProtocolVersion version;
    private SocksAuthScheme authScheme;
    private SocksResponse msg;
    
    @Deprecated
    public static String getName() {
        return "SOCKS_INIT_RESPONSE_DECODER";
    }
    
    public SocksInitResponseDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
        this.msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        switch ((State)this.state()) {
            case CHECK_PROTOCOL_VERSION: {
                this.version = SocksProtocolVersion.valueOf(byteBuf.readByte());
                if (this.version != SocksProtocolVersion.SOCKS5) {
                    break;
                }
                this.checkpoint(State.READ_PREFFERED_AUTH_TYPE);
            }
            case READ_PREFFERED_AUTH_TYPE: {
                this.authScheme = SocksAuthScheme.valueOf(byteBuf.readByte());
                this.msg = new SocksInitResponse(this.authScheme);
                break;
            }
        }
        channelHandlerContext.pipeline().remove(this);
        list.add(this.msg);
    }
    
    enum State
    {
        CHECK_PROTOCOL_VERSION("CHECK_PROTOCOL_VERSION", 0), 
        READ_PREFFERED_AUTH_TYPE("READ_PREFFERED_AUTH_TYPE", 1);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.CHECK_PROTOCOL_VERSION, State.READ_PREFFERED_AUTH_TYPE };
        }
    }
}
