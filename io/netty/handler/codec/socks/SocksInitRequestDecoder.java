package io.netty.handler.codec.socks;

import io.netty.handler.codec.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.channel.*;

public class SocksInitRequestDecoder extends ReplayingDecoder
{
    private static final String name = "SOCKS_INIT_REQUEST_DECODER";
    private final List authSchemes;
    private SocksProtocolVersion version;
    private byte authSchemeNum;
    private SocksRequest msg;
    
    @Deprecated
    public static String getName() {
        return "SOCKS_INIT_REQUEST_DECODER";
    }
    
    public SocksInitRequestDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
        this.authSchemes = new ArrayList();
        this.msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        switch ((State)this.state()) {
            case CHECK_PROTOCOL_VERSION: {
                this.version = SocksProtocolVersion.valueOf(byteBuf.readByte());
                if (this.version != SocksProtocolVersion.SOCKS5) {
                    break;
                }
                this.checkpoint(State.READ_AUTH_SCHEMES);
            }
            case READ_AUTH_SCHEMES: {
                this.authSchemes.clear();
                this.authSchemeNum = byteBuf.readByte();
                while (0 < this.authSchemeNum) {
                    this.authSchemes.add(SocksAuthScheme.valueOf(byteBuf.readByte()));
                    int n = 0;
                    ++n;
                }
                this.msg = new SocksInitRequest(this.authSchemes);
                break;
            }
        }
        channelHandlerContext.pipeline().remove(this);
        list.add(this.msg);
    }
    
    enum State
    {
        CHECK_PROTOCOL_VERSION("CHECK_PROTOCOL_VERSION", 0), 
        READ_AUTH_SCHEMES("READ_AUTH_SCHEMES", 1);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.CHECK_PROTOCOL_VERSION, State.READ_AUTH_SCHEMES };
        }
    }
}
