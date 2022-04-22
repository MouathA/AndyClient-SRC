package io.netty.handler.codec.socks;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import java.util.*;
import io.netty.util.*;
import io.netty.channel.*;

public class SocksAuthRequestDecoder extends ReplayingDecoder
{
    private static final String name = "SOCKS_AUTH_REQUEST_DECODER";
    private SocksSubnegotiationVersion version;
    private int fieldLength;
    private String username;
    private String password;
    private SocksRequest msg;
    
    @Deprecated
    public static String getName() {
        return "SOCKS_AUTH_REQUEST_DECODER";
    }
    
    public SocksAuthRequestDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
        this.msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        switch ((State)this.state()) {
            case CHECK_PROTOCOL_VERSION: {
                this.version = SocksSubnegotiationVersion.valueOf(byteBuf.readByte());
                if (this.version != SocksSubnegotiationVersion.AUTH_PASSWORD) {
                    break;
                }
                this.checkpoint(State.READ_USERNAME);
            }
            case READ_USERNAME: {
                this.fieldLength = byteBuf.readByte();
                this.username = byteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
                this.checkpoint(State.READ_PASSWORD);
            }
            case READ_PASSWORD: {
                this.fieldLength = byteBuf.readByte();
                this.password = byteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
                this.msg = new SocksAuthRequest(this.username, this.password);
                break;
            }
        }
        channelHandlerContext.pipeline().remove(this);
        list.add(this.msg);
    }
    
    enum State
    {
        CHECK_PROTOCOL_VERSION("CHECK_PROTOCOL_VERSION", 0), 
        READ_USERNAME("READ_USERNAME", 1), 
        READ_PASSWORD("READ_PASSWORD", 2);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.CHECK_PROTOCOL_VERSION, State.READ_USERNAME, State.READ_PASSWORD };
        }
    }
}
