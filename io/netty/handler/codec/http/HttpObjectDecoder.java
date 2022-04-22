package io.netty.handler.codec.http;

import io.netty.util.internal.*;
import io.netty.channel.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.handler.codec.*;

public abstract class HttpObjectDecoder extends ReplayingDecoder
{
    private final int maxInitialLineLength;
    private final int maxHeaderSize;
    private final int maxChunkSize;
    private final boolean chunkedSupported;
    protected final boolean validateHeaders;
    private final AppendableCharSequence seq;
    private final HeaderParser headerParser;
    private final LineParser lineParser;
    private HttpMessage message;
    private long chunkSize;
    private int headerSize;
    private long contentLength;
    static final boolean $assertionsDisabled;
    
    protected HttpObjectDecoder() {
        this(4096, 8192, 8192, true);
    }
    
    protected HttpObjectDecoder(final int n, final int n2, final int n3, final boolean b) {
        this(n, n2, n3, b, true);
    }
    
    protected HttpObjectDecoder(final int maxInitialLineLength, final int maxHeaderSize, final int maxChunkSize, final boolean chunkedSupported, final boolean validateHeaders) {
        super(State.SKIP_CONTROL_CHARS);
        this.seq = new AppendableCharSequence(128);
        this.headerParser = new HeaderParser(this.seq);
        this.lineParser = new LineParser(this.seq);
        this.contentLength = Long.MIN_VALUE;
        if (maxInitialLineLength <= 0) {
            throw new IllegalArgumentException("maxInitialLineLength must be a positive integer: " + maxInitialLineLength);
        }
        if (maxHeaderSize <= 0) {
            throw new IllegalArgumentException("maxHeaderSize must be a positive integer: " + maxHeaderSize);
        }
        if (maxChunkSize <= 0) {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + maxChunkSize);
        }
        this.maxInitialLineLength = maxInitialLineLength;
        this.maxHeaderSize = maxHeaderSize;
        this.maxChunkSize = maxChunkSize;
        this.chunkedSupported = chunkedSupported;
        this.validateHeaders = validateHeaders;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        Label_0768: {
            switch ((State)this.state()) {
                case SKIP_CONTROL_CHARS: {
                    skipControlCharacters(byteBuf);
                    this.checkpoint(State.READ_INITIAL);
                    this.checkpoint();
                }
                case READ_INITIAL: {
                    final String[] splitInitialLine = splitInitialLine(this.lineParser.parse(byteBuf));
                    if (splitInitialLine.length < 3) {
                        this.checkpoint(State.SKIP_CONTROL_CHARS);
                        return;
                    }
                    this.message = this.createMessage(splitInitialLine);
                    this.checkpoint(State.READ_HEADER);
                }
                case READ_HEADER: {
                    final State headers = this.readHeaders(byteBuf);
                    this.checkpoint(headers);
                    if (headers == State.READ_CHUNK_SIZE) {
                        if (!this.chunkedSupported) {
                            throw new IllegalArgumentException("Chunked messages not supported");
                        }
                        list.add(this.message);
                        return;
                    }
                    else {
                        if (headers == State.SKIP_CONTROL_CHARS) {
                            list.add(this.message);
                            list.add(LastHttpContent.EMPTY_LAST_CONTENT);
                            this.reset();
                            return;
                        }
                        final long contentLength = this.contentLength();
                        if (contentLength == 0L || (contentLength == -1L && this.isDecodingRequest())) {
                            list.add(this.message);
                            list.add(LastHttpContent.EMPTY_LAST_CONTENT);
                            this.reset();
                            return;
                        }
                        assert headers == State.READ_VARIABLE_LENGTH_CONTENT;
                        list.add(this.message);
                        if (headers == State.READ_FIXED_LENGTH_CONTENT) {
                            this.chunkSize = contentLength;
                        }
                        return;
                    }
                    break;
                }
                case READ_VARIABLE_LENGTH_CONTENT: {
                    final int min = Math.min(this.actualReadableBytes(), this.maxChunkSize);
                    if (min > 0) {
                        final ByteBuf bytes = ByteBufUtil.readBytes(channelHandlerContext.alloc(), byteBuf, min);
                        if (byteBuf.isReadable()) {
                            list.add(new DefaultHttpContent(bytes));
                        }
                        else {
                            list.add(new DefaultLastHttpContent(bytes, this.validateHeaders));
                            this.reset();
                        }
                    }
                    else if (!byteBuf.isReadable()) {
                        list.add(LastHttpContent.EMPTY_LAST_CONTENT);
                        this.reset();
                    }
                }
                case READ_FIXED_LENGTH_CONTENT: {
                    final int actualReadableBytes = this.actualReadableBytes();
                    if (actualReadableBytes == 0) {
                        return;
                    }
                    int min2 = Math.min(actualReadableBytes, this.maxChunkSize);
                    if (min2 > this.chunkSize) {
                        min2 = (int)this.chunkSize;
                    }
                    final ByteBuf bytes2 = ByteBufUtil.readBytes(channelHandlerContext.alloc(), byteBuf, min2);
                    this.chunkSize -= min2;
                    if (this.chunkSize == 0L) {
                        list.add(new DefaultLastHttpContent(bytes2, this.validateHeaders));
                        this.reset();
                    }
                    else {
                        list.add(new DefaultHttpContent(bytes2));
                    }
                }
                case READ_CHUNK_SIZE: {
                    final int chunkSize = getChunkSize(this.lineParser.parse(byteBuf).toString());
                    this.chunkSize = chunkSize;
                    if (chunkSize == 0) {
                        this.checkpoint(State.READ_CHUNK_FOOTER);
                        return;
                    }
                    this.checkpoint(State.READ_CHUNKED_CONTENT);
                }
                case READ_CHUNKED_CONTENT: {
                    assert this.chunkSize <= 2147483647L;
                    final int min3 = Math.min((int)this.chunkSize, this.maxChunkSize);
                    final DefaultHttpContent defaultHttpContent = new DefaultHttpContent(ByteBufUtil.readBytes(channelHandlerContext.alloc(), byteBuf, min3));
                    this.chunkSize -= min3;
                    list.add(defaultHttpContent);
                    if (this.chunkSize == 0L) {
                        this.checkpoint(State.READ_CHUNK_DELIMITER);
                        break Label_0768;
                    }
                }
                case READ_CHUNK_DELIMITER: {
                    while (true) {
                        final byte byte1 = byteBuf.readByte();
                        if (byte1 == 13) {
                            if (byteBuf.readByte() == 10) {
                                this.checkpoint(State.READ_CHUNK_SIZE);
                                return;
                            }
                            continue;
                        }
                        else {
                            if (byte1 == 10) {
                                this.checkpoint(State.READ_CHUNK_SIZE);
                                return;
                            }
                            this.checkpoint();
                        }
                    }
                    break;
                }
                case READ_CHUNK_FOOTER: {
                    list.add(this.readTrailingHeaders(byteBuf));
                    this.reset();
                }
                case BAD_MESSAGE: {
                    byteBuf.skipBytes(this.actualReadableBytes());
                    break;
                }
                case UPGRADED: {
                    if (this.actualReadableBytes() > 0) {
                        list.add(byteBuf.readBytes(this.actualReadableBytes()));
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    @Override
    protected void decodeLast(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        this.decode(channelHandlerContext, byteBuf, list);
        if (this.message != null) {
            if (!this.isDecodingRequest()) {
                final boolean b = this.contentLength() > 0L;
            }
            this.reset();
            if (!true) {
                list.add(LastHttpContent.EMPTY_LAST_CONTENT);
            }
        }
    }
    
    protected boolean isContentAlwaysEmpty(final HttpMessage httpMessage) {
        if (httpMessage instanceof HttpResponse) {
            final HttpResponse httpResponse = (HttpResponse)httpMessage;
            final int code = httpResponse.getStatus().code();
            if (code >= 100 && code < 200) {
                return code != 101 || httpResponse.headers().contains("Sec-WebSocket-Accept");
            }
            switch (code) {
                case 204:
                case 205:
                case 304: {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void reset() {
        final HttpMessage message = this.message;
        this.message = null;
        this.contentLength = Long.MIN_VALUE;
        if (!this.isDecodingRequest()) {
            final HttpResponse httpResponse = (HttpResponse)message;
            if (httpResponse != null && httpResponse.getStatus().code() == 101) {
                this.checkpoint(State.UPGRADED);
                return;
            }
        }
        this.checkpoint(State.SKIP_CONTROL_CHARS);
    }
    
    private HttpMessage invalidMessage(final Exception ex) {
        this.checkpoint(State.BAD_MESSAGE);
        if (this.message != null) {
            this.message.setDecoderResult(DecoderResult.failure(ex));
        }
        else {
            (this.message = this.createInvalidMessage()).setDecoderResult(DecoderResult.failure(ex));
        }
        final HttpMessage message = this.message;
        this.message = null;
        return message;
    }
    
    private HttpContent invalidChunk(final Exception ex) {
        this.checkpoint(State.BAD_MESSAGE);
        final DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
        defaultLastHttpContent.setDecoderResult(DecoderResult.failure(ex));
        this.message = null;
        return defaultLastHttpContent;
    }
    
    private static void skipControlCharacters(final ByteBuf byteBuf) {
        char c;
        do {
            c = (char)byteBuf.readUnsignedByte();
        } while (Character.isISOControl(c) || Character.isWhitespace(c));
        byteBuf.readerIndex(byteBuf.readerIndex() - 1);
    }
    
    private State readHeaders(final ByteBuf byteBuf) {
        this.headerSize = 0;
        final HttpMessage message = this.message;
        final HttpHeaders headers = message.headers();
        AppendableCharSequence appendableCharSequence = this.headerParser.parse(byteBuf);
        String s = null;
        String string = null;
        if (appendableCharSequence.length() > 0) {
            headers.clear();
            do {
                final char char1 = appendableCharSequence.charAt(0);
                if (s != null && (char1 == ' ' || char1 == '\t')) {
                    string = string + ' ' + appendableCharSequence.toString().trim();
                }
                else {
                    if (s != null) {
                        headers.add(s, string);
                    }
                    final String[] splitHeader = splitHeader(appendableCharSequence);
                    s = splitHeader[0];
                    string = splitHeader[1];
                }
                appendableCharSequence = this.headerParser.parse(byteBuf);
            } while (appendableCharSequence.length() > 0);
            if (s != null) {
                headers.add(s, string);
            }
        }
        State state;
        if (this.isContentAlwaysEmpty(message)) {
            HttpHeaders.removeTransferEncodingChunked(message);
            state = State.SKIP_CONTROL_CHARS;
        }
        else if (HttpHeaders.isTransferEncodingChunked(message)) {
            state = State.READ_CHUNK_SIZE;
        }
        else if (this.contentLength() >= 0L) {
            state = State.READ_FIXED_LENGTH_CONTENT;
        }
        else {
            state = State.READ_VARIABLE_LENGTH_CONTENT;
        }
        return state;
    }
    
    private long contentLength() {
        if (this.contentLength == Long.MIN_VALUE) {
            this.contentLength = HttpHeaders.getContentLength(this.message, -1L);
        }
        return this.contentLength;
    }
    
    private LastHttpContent readTrailingHeaders(final ByteBuf byteBuf) {
        this.headerSize = 0;
        AppendableCharSequence appendableCharSequence = this.headerParser.parse(byteBuf);
        String s = null;
        if (appendableCharSequence.length() > 0) {
            final DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER, this.validateHeaders);
            do {
                final char char1 = appendableCharSequence.charAt(0);
                if (s != null && (char1 == ' ' || char1 == '\t')) {
                    final List all = defaultLastHttpContent.trailingHeaders().getAll(s);
                    if (!all.isEmpty()) {
                        final int n = all.size() - 1;
                        all.set(n, all.get(n) + appendableCharSequence.toString().trim());
                    }
                }
                else {
                    final String[] splitHeader = splitHeader(appendableCharSequence);
                    final String s2 = splitHeader[0];
                    if (!HttpHeaders.equalsIgnoreCase(s2, "Content-Length") && !HttpHeaders.equalsIgnoreCase(s2, "Transfer-Encoding") && !HttpHeaders.equalsIgnoreCase(s2, "Trailer")) {
                        defaultLastHttpContent.trailingHeaders().add(s2, splitHeader[1]);
                    }
                    s = s2;
                }
                appendableCharSequence = this.headerParser.parse(byteBuf);
            } while (appendableCharSequence.length() > 0);
            return defaultLastHttpContent;
        }
        return LastHttpContent.EMPTY_LAST_CONTENT;
    }
    
    protected abstract boolean isDecodingRequest();
    
    protected abstract HttpMessage createMessage(final String[] p0) throws Exception;
    
    protected abstract HttpMessage createInvalidMessage();
    
    private static int getChunkSize(String s) {
        s = s.trim();
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 == ';' || Character.isWhitespace(char1) || Character.isISOControl(char1)) {
                s = s.substring(0, 0);
                break;
            }
            int n = 0;
            ++n;
        }
        return Integer.parseInt(s, 16);
    }
    
    private static String[] splitInitialLine(final AppendableCharSequence appendableCharSequence) {
        final int nonWhitespace = findNonWhitespace(appendableCharSequence, 0);
        final int whitespace = findWhitespace(appendableCharSequence, nonWhitespace);
        final int nonWhitespace2 = findNonWhitespace(appendableCharSequence, whitespace);
        final int whitespace2 = findWhitespace(appendableCharSequence, nonWhitespace2);
        final int nonWhitespace3 = findNonWhitespace(appendableCharSequence, whitespace2);
        final int endOfString = findEndOfString(appendableCharSequence);
        return new String[] { appendableCharSequence.substring(nonWhitespace, whitespace), appendableCharSequence.substring(nonWhitespace2, whitespace2), (nonWhitespace3 < endOfString) ? appendableCharSequence.substring(nonWhitespace3, endOfString) : "" };
    }
    
    private static String[] splitHeader(final AppendableCharSequence appendableCharSequence) {
        int length;
        int i;
        int n;
        for (length = appendableCharSequence.length(), n = (i = findNonWhitespace(appendableCharSequence, 0)); i < length; ++i) {
            final char char1 = appendableCharSequence.charAt(i);
            if (char1 == ':') {
                break;
            }
            if (Character.isWhitespace(char1)) {
                break;
            }
        }
        int j;
        for (j = i; j < length; ++j) {
            if (appendableCharSequence.charAt(j) == ':') {
                ++j;
                break;
            }
        }
        final int nonWhitespace = findNonWhitespace(appendableCharSequence, j);
        if (nonWhitespace == length) {
            return new String[] { appendableCharSequence.substring(n, i), "" };
        }
        return new String[] { appendableCharSequence.substring(n, i), appendableCharSequence.substring(nonWhitespace, findEndOfString(appendableCharSequence)) };
    }
    
    private static int findNonWhitespace(final CharSequence charSequence, final int n) {
        int n2;
        for (n2 = n; n2 < charSequence.length() && Character.isWhitespace(charSequence.charAt(n2)); ++n2) {}
        return n2;
    }
    
    private static int findWhitespace(final CharSequence charSequence, final int n) {
        int n2;
        for (n2 = n; n2 < charSequence.length() && !Character.isWhitespace(charSequence.charAt(n2)); ++n2) {}
        return n2;
    }
    
    private static int findEndOfString(final CharSequence charSequence) {
        int length;
        for (length = charSequence.length(); length > 0 && Character.isWhitespace(charSequence.charAt(length - 1)); --length) {}
        return length;
    }
    
    static int access$002(final HttpObjectDecoder httpObjectDecoder, final int headerSize) {
        return httpObjectDecoder.headerSize = headerSize;
    }
    
    static int access$008(final HttpObjectDecoder httpObjectDecoder) {
        return httpObjectDecoder.headerSize++;
    }
    
    static int access$000(final HttpObjectDecoder httpObjectDecoder) {
        return httpObjectDecoder.headerSize;
    }
    
    static int access$100(final HttpObjectDecoder httpObjectDecoder) {
        return httpObjectDecoder.maxHeaderSize;
    }
    
    static int access$200(final HttpObjectDecoder httpObjectDecoder) {
        return httpObjectDecoder.maxInitialLineLength;
    }
    
    static {
        $assertionsDisabled = !HttpObjectDecoder.class.desiredAssertionStatus();
    }
    
    enum State
    {
        SKIP_CONTROL_CHARS("SKIP_CONTROL_CHARS", 0), 
        READ_INITIAL("READ_INITIAL", 1), 
        READ_HEADER("READ_HEADER", 2), 
        READ_VARIABLE_LENGTH_CONTENT("READ_VARIABLE_LENGTH_CONTENT", 3), 
        READ_FIXED_LENGTH_CONTENT("READ_FIXED_LENGTH_CONTENT", 4), 
        READ_CHUNK_SIZE("READ_CHUNK_SIZE", 5), 
        READ_CHUNKED_CONTENT("READ_CHUNKED_CONTENT", 6), 
        READ_CHUNK_DELIMITER("READ_CHUNK_DELIMITER", 7), 
        READ_CHUNK_FOOTER("READ_CHUNK_FOOTER", 8), 
        BAD_MESSAGE("BAD_MESSAGE", 9), 
        UPGRADED("UPGRADED", 10);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.SKIP_CONTROL_CHARS, State.READ_INITIAL, State.READ_HEADER, State.READ_VARIABLE_LENGTH_CONTENT, State.READ_FIXED_LENGTH_CONTENT, State.READ_CHUNK_SIZE, State.READ_CHUNKED_CONTENT, State.READ_CHUNK_DELIMITER, State.READ_CHUNK_FOOTER, State.BAD_MESSAGE, State.UPGRADED };
        }
    }
    
    private final class LineParser implements ByteBufProcessor
    {
        private final AppendableCharSequence seq;
        private int size;
        final HttpObjectDecoder this$0;
        
        LineParser(final HttpObjectDecoder this$0, final AppendableCharSequence seq) {
            this.this$0 = this$0;
            this.seq = seq;
        }
        
        public AppendableCharSequence parse(final ByteBuf byteBuf) {
            this.seq.reset();
            this.size = 0;
            byteBuf.readerIndex(byteBuf.forEachByte(this) + 1);
            return this.seq;
        }
        
        @Override
        public boolean process(final byte b) throws Exception {
            final char c = (char)b;
            if (c == '\r') {
                return true;
            }
            if (c == '\n') {
                return false;
            }
            if (this.size >= HttpObjectDecoder.access$200(this.this$0)) {
                throw new TooLongFrameException("An HTTP line is larger than " + HttpObjectDecoder.access$200(this.this$0) + " bytes.");
            }
            ++this.size;
            this.seq.append(c);
            return true;
        }
    }
    
    private final class HeaderParser implements ByteBufProcessor
    {
        private final AppendableCharSequence seq;
        final HttpObjectDecoder this$0;
        
        HeaderParser(final HttpObjectDecoder this$0, final AppendableCharSequence seq) {
            this.this$0 = this$0;
            this.seq = seq;
        }
        
        public AppendableCharSequence parse(final ByteBuf byteBuf) {
            this.seq.reset();
            HttpObjectDecoder.access$002(this.this$0, 0);
            byteBuf.readerIndex(byteBuf.forEachByte(this) + 1);
            return this.seq;
        }
        
        @Override
        public boolean process(final byte b) throws Exception {
            final char c = (char)b;
            HttpObjectDecoder.access$008(this.this$0);
            if (c == '\r') {
                return true;
            }
            if (c == '\n') {
                return false;
            }
            if (HttpObjectDecoder.access$000(this.this$0) >= HttpObjectDecoder.access$100(this.this$0)) {
                throw new TooLongFrameException("HTTP header is larger than " + HttpObjectDecoder.access$100(this.this$0) + " bytes.");
            }
            this.seq.append(c);
            return true;
        }
    }
}
