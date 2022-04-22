package io.netty.handler.codec.socks;

import io.netty.buffer.*;
import java.util.*;

public final class SocksInitRequest extends SocksRequest
{
    private final List authSchemes;
    
    public SocksInitRequest(final List authSchemes) {
        super(SocksRequestType.INIT);
        if (authSchemes == null) {
            throw new NullPointerException("authSchemes");
        }
        this.authSchemes = authSchemes;
    }
    
    public List authSchemes() {
        return Collections.unmodifiableList((List<?>)this.authSchemes);
    }
    
    @Override
    public void encodeAsByteBuf(final ByteBuf byteBuf) {
        byteBuf.writeByte(this.protocolVersion().byteValue());
        byteBuf.writeByte(this.authSchemes.size());
        final Iterator<SocksAuthScheme> iterator = this.authSchemes.iterator();
        while (iterator.hasNext()) {
            byteBuf.writeByte(iterator.next().byteValue());
        }
    }
}
