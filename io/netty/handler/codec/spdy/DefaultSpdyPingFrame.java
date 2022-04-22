package io.netty.handler.codec.spdy;

import io.netty.util.internal.*;

public class DefaultSpdyPingFrame implements SpdyPingFrame
{
    private int id;
    
    public DefaultSpdyPingFrame(final int id) {
        this.setId(id);
    }
    
    @Override
    public int id() {
        return this.id;
    }
    
    @Override
    public SpdyPingFrame setId(final int id) {
        this.id = id;
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append(StringUtil.NEWLINE);
        sb.append("--> ID = ");
        sb.append(this.id());
        return sb.toString();
    }
}
