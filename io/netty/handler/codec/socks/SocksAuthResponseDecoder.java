package io.netty.handler.codec.socks;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import java.util.*;
import io.netty.channel.*;

public class SocksAuthResponseDecoder extends ReplayingDecoder
{
    private static final String name = "SOCKS_AUTH_RESPONSE_DECODER";
    private SocksSubnegotiationVersion version;
    private SocksAuthStatus authStatus;
    private SocksResponse msg;
    
    @Deprecated
    public static String getName() {
        return "SOCKS_AUTH_RESPONSE_DECODER";
    }
    
    public SocksAuthResponseDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
        this.msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        switch ((State)this.state()) {
            case CHECK_PROTOCOL_VERSION: {
                this.version = SocksSubnegotiationVersion.valueOf(byteBuf.readByte());
                if (this.version != SocksSubnegotiationVersion.AUTH_PASSWORD) {
                    break;
                }
                this.checkpoint(State.READ_AUTH_RESPONSE);
            }
            case READ_AUTH_RESPONSE: {
                this.authStatus = SocksAuthStatus.valueOf(byteBuf.readByte());
                this.msg = new SocksAuthResponse(this.authStatus);
                break;
            }
        }
        channelHandlerContext.pipeline().remove(this);
        list.add(this.msg);
    }
    
    enum State
    {
        CHECK_PROTOCOL_VERSION("CHECK_PROTOCOL_VERSION", 0), 
        READ_AUTH_RESPONSE("READ_AUTH_RESPONSE", 1);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.CHECK_PROTOCOL_VERSION, State.READ_AUTH_RESPONSE };
        }
    }
}
