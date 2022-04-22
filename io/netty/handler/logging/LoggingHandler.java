package io.netty.handler.logging;

import io.netty.util.internal.logging.*;
import java.net.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import io.netty.util.internal.*;

@ChannelHandler.Sharable
public class LoggingHandler extends ChannelDuplexHandler
{
    private static final LogLevel DEFAULT_LEVEL;
    private static final String NEWLINE;
    private static final String[] HEXPADDING;
    private static final String[] BYTEPADDING;
    private static final char[] BYTE2CHAR;
    protected final InternalLogger logger;
    protected final InternalLogLevel internalLevel;
    private final LogLevel level;
    
    public LoggingHandler() {
        this(LoggingHandler.DEFAULT_LEVEL);
    }
    
    public LoggingHandler(final LogLevel level) {
        if (level == null) {
            throw new NullPointerException("level");
        }
        this.logger = InternalLoggerFactory.getInstance(this.getClass());
        this.level = level;
        this.internalLevel = level.toInternalLevel();
    }
    
    public LoggingHandler(final Class clazz) {
        this(clazz, LoggingHandler.DEFAULT_LEVEL);
    }
    
    public LoggingHandler(final Class clazz, final LogLevel level) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        if (level == null) {
            throw new NullPointerException("level");
        }
        this.logger = InternalLoggerFactory.getInstance(clazz);
        this.level = level;
        this.internalLevel = level.toInternalLevel();
    }
    
    public LoggingHandler(final String s) {
        this(s, LoggingHandler.DEFAULT_LEVEL);
    }
    
    public LoggingHandler(final String s, final LogLevel level) {
        if (s == null) {
            throw new NullPointerException("name");
        }
        if (level == null) {
            throw new NullPointerException("level");
        }
        this.logger = InternalLoggerFactory.getInstance(s);
        this.level = level;
        this.internalLevel = level.toInternalLevel();
    }
    
    public LogLevel level() {
        return this.level;
    }
    
    protected String format(final ChannelHandlerContext channelHandlerContext, final String s) {
        final String string = channelHandlerContext.channel().toString();
        final StringBuilder sb = new StringBuilder(string.length() + s.length() + 1);
        sb.append(string);
        sb.append(' ');
        sb.append(s);
        return sb.toString();
    }
    
    @Override
    public void channelRegistered(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "REGISTERED"));
        }
        super.channelRegistered(channelHandlerContext);
    }
    
    @Override
    public void channelUnregistered(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "UNREGISTERED"));
        }
        super.channelUnregistered(channelHandlerContext);
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "ACTIVE"));
        }
        super.channelActive(channelHandlerContext);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "INACTIVE"));
        }
        super.channelInactive(channelHandlerContext);
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "EXCEPTION: " + t), t);
        }
        super.exceptionCaught(channelHandlerContext, t);
    }
    
    @Override
    public void userEventTriggered(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "USER_EVENT: " + o));
        }
        super.userEventTriggered(channelHandlerContext, o);
    }
    
    @Override
    public void bind(final ChannelHandlerContext channelHandlerContext, final SocketAddress socketAddress, final ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "BIND(" + socketAddress + ')'));
        }
        super.bind(channelHandlerContext, socketAddress, channelPromise);
    }
    
    @Override
    public void connect(final ChannelHandlerContext channelHandlerContext, final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "CONNECT(" + socketAddress + ", " + socketAddress2 + ')'));
        }
        super.connect(channelHandlerContext, socketAddress, socketAddress2, channelPromise);
    }
    
    @Override
    public void disconnect(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "DISCONNECT()"));
        }
        super.disconnect(channelHandlerContext, channelPromise);
    }
    
    @Override
    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "CLOSE()"));
        }
        super.close(channelHandlerContext, channelPromise);
    }
    
    @Override
    public void deregister(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "DEREGISTER()"));
        }
        super.deregister(channelHandlerContext, channelPromise);
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        this.logMessage(channelHandlerContext, "RECEIVED", o);
        channelHandlerContext.fireChannelRead(o);
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        this.logMessage(channelHandlerContext, "WRITE", o);
        channelHandlerContext.write(o, channelPromise);
    }
    
    @Override
    public void flush(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "FLUSH"));
        }
        channelHandlerContext.flush();
    }
    
    private void logMessage(final ChannelHandlerContext channelHandlerContext, final String s, final Object o) {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, this.formatMessage(s, o)));
        }
    }
    
    protected String formatMessage(final String s, final Object o) {
        if (o instanceof ByteBuf) {
            return this.formatByteBuf(s, (ByteBuf)o);
        }
        if (o instanceof ByteBufHolder) {
            return this.formatByteBufHolder(s, (ByteBufHolder)o);
        }
        return this.formatNonByteBuf(s, o);
    }
    
    protected String formatByteBuf(final String s, final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        final StringBuilder sb = new StringBuilder((readableBytes / 16 + ((readableBytes % 15 != 0) ? 1 : 0) + 4) * 80 + s.length() + 16);
        sb.append(s).append('(').append(readableBytes).append('B').append(')');
        sb.append(LoggingHandler.NEWLINE + "         +-------------------------------------------------+" + LoggingHandler.NEWLINE + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |" + LoggingHandler.NEWLINE + "+--------+-------------------------------------------------+----------------+");
        final int readerIndex = byteBuf.readerIndex();
        int writerIndex;
        int i;
        for (writerIndex = byteBuf.writerIndex(), i = readerIndex; i < writerIndex; ++i) {
            final int n = i - readerIndex;
            final int n2 = n & 0xF;
            if (n2 == 0) {
                sb.append(LoggingHandler.NEWLINE);
                sb.append(Long.toHexString(((long)n & 0xFFFFFFFFL) | 0x100000000L));
                sb.setCharAt(sb.length() - 9, '|');
                sb.append('|');
            }
            sb.append(LoggingHandler.BYTE2HEX[byteBuf.getUnsignedByte(i)]);
            if (n2 == 15) {
                sb.append(" |");
                for (int j = i - 15; j <= i; ++j) {
                    sb.append(LoggingHandler.BYTE2CHAR[byteBuf.getUnsignedByte(j)]);
                }
                sb.append('|');
            }
        }
        if ((i - readerIndex & 0xF) != 0x0) {
            final int n3 = readableBytes & 0xF;
            sb.append(LoggingHandler.HEXPADDING[n3]);
            sb.append(" |");
            for (int k = i - n3; k < i; ++k) {
                sb.append(LoggingHandler.BYTE2CHAR[byteBuf.getUnsignedByte(k)]);
            }
            sb.append(LoggingHandler.BYTEPADDING[n3]);
            sb.append('|');
        }
        sb.append(LoggingHandler.NEWLINE + "+--------+-------------------------------------------------+----------------+");
        return sb.toString();
    }
    
    protected String formatNonByteBuf(final String s, final Object o) {
        return s + ": " + o;
    }
    
    protected String formatByteBufHolder(final String s, final ByteBufHolder byteBufHolder) {
        return this.formatByteBuf(s, byteBufHolder.content());
    }
    
    static {
        DEFAULT_LEVEL = LogLevel.DEBUG;
        NEWLINE = StringUtil.NEWLINE;
        LoggingHandler.BYTE2HEX = new String[256];
        HEXPADDING = new String[16];
        BYTEPADDING = new String[16];
        BYTE2CHAR = new char[256];
        int n = 0;
        while (0 < LoggingHandler.BYTE2HEX.length) {
            LoggingHandler.BYTE2HEX[0] = ' ' + StringUtil.byteToHexStringPadded(0);
            ++n;
        }
        int n3 = 0;
        while (0 < LoggingHandler.HEXPADDING.length) {
            final int n2 = LoggingHandler.HEXPADDING.length - 0;
            final StringBuilder sb = new StringBuilder(n2 * 3);
            while (0 < n2) {
                sb.append("   ");
                ++n3;
            }
            LoggingHandler.HEXPADDING[0] = sb.toString();
            ++n;
        }
        while (0 < LoggingHandler.BYTEPADDING.length) {
            final int n4 = LoggingHandler.BYTEPADDING.length - 0;
            final StringBuilder sb2 = new StringBuilder(n4);
            while (0 < n4) {
                sb2.append(' ');
                ++n3;
            }
            LoggingHandler.BYTEPADDING[0] = sb2.toString();
            ++n;
        }
        while (0 < LoggingHandler.BYTE2CHAR.length) {
            LoggingHandler.BYTE2CHAR[0] = '.';
            ++n;
        }
    }
}
