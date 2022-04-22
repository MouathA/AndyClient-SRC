package io.netty.handler.codec.http.multipart;

import io.netty.handler.stream.*;
import java.nio.charset.*;
import io.netty.util.internal.*;
import java.io.*;
import java.net.*;
import java.util.regex.*;
import io.netty.channel.*;
import java.util.*;
import io.netty.handler.codec.http.*;
import io.netty.buffer.*;
import io.netty.util.*;
import io.netty.handler.codec.*;

public class HttpPostRequestEncoder implements ChunkedInput
{
    private static final Map percentEncodings;
    private final HttpDataFactory factory;
    private final HttpRequest request;
    private final Charset charset;
    private boolean isChunked;
    private final List bodyListDatas;
    final List multipartHttpDatas;
    private final boolean isMultipart;
    String multipartDataBoundary;
    String multipartMixedBoundary;
    private boolean headerFinalized;
    private final EncoderMode encoderMode;
    private boolean isLastChunk;
    private boolean isLastChunkSent;
    private FileUpload currentFileUpload;
    private boolean duringMixedMode;
    private long globalBodySize;
    private ListIterator iterator;
    private ByteBuf currentBuffer;
    private InterfaceHttpData currentData;
    private boolean isKey;
    
    public HttpPostRequestEncoder(final HttpRequest httpRequest, final boolean b) throws ErrorDataEncoderException {
        this(new DefaultHttpDataFactory(16384L), httpRequest, b, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
    }
    
    public HttpPostRequestEncoder(final HttpDataFactory httpDataFactory, final HttpRequest httpRequest, final boolean b) throws ErrorDataEncoderException {
        this(httpDataFactory, httpRequest, b, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
    }
    
    public HttpPostRequestEncoder(final HttpDataFactory factory, final HttpRequest request, final boolean isMultipart, final Charset charset, final EncoderMode encoderMode) throws ErrorDataEncoderException {
        this.isKey = true;
        if (factory == null) {
            throw new NullPointerException("factory");
        }
        if (request == null) {
            throw new NullPointerException("request");
        }
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        if (request.getMethod() != HttpMethod.POST) {
            throw new ErrorDataEncoderException("Cannot create a Encoder if not a POST");
        }
        this.request = request;
        this.charset = charset;
        this.factory = factory;
        this.bodyListDatas = new ArrayList();
        this.isLastChunk = false;
        this.isLastChunkSent = false;
        this.isMultipart = isMultipart;
        this.multipartHttpDatas = new ArrayList();
        this.encoderMode = encoderMode;
        if (this.isMultipart) {
            this.initDataMultipart();
        }
    }
    
    public void cleanFiles() {
        this.factory.cleanRequestHttpDatas(this.request);
    }
    
    public boolean isMultipart() {
        return this.isMultipart;
    }
    
    private void initDataMultipart() {
        this.multipartDataBoundary = getNewMultipartDelimiter();
    }
    
    private void initMixedMultipart() {
        this.multipartMixedBoundary = getNewMultipartDelimiter();
    }
    
    private static String getNewMultipartDelimiter() {
        return Long.toHexString(ThreadLocalRandom.current().nextLong()).toLowerCase();
    }
    
    public List getBodyListAttributes() {
        return this.bodyListDatas;
    }
    
    public void setBodyHttpDatas(final List list) throws ErrorDataEncoderException {
        if (list == null) {
            throw new NullPointerException("datas");
        }
        this.globalBodySize = 0L;
        this.bodyListDatas.clear();
        this.currentFileUpload = null;
        this.duringMixedMode = false;
        this.multipartHttpDatas.clear();
        final Iterator<InterfaceHttpData> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.addBodyHttpData(iterator.next());
        }
    }
    
    public void addBodyAttribute(final String s, final String s2) throws ErrorDataEncoderException {
        if (s == null) {
            throw new NullPointerException("name");
        }
        String s3;
        if ((s3 = s2) == null) {
            s3 = "";
        }
        this.addBodyHttpData(this.factory.createAttribute(this.request, s, s3));
    }
    
    public void addBodyFileUpload(final String s, final File content, final String s2, final boolean b) throws ErrorDataEncoderException {
        if (s == null) {
            throw new NullPointerException("name");
        }
        if (content == null) {
            throw new NullPointerException("file");
        }
        String s3 = s2;
        String value = null;
        if (s2 == null) {
            if (b) {
                s3 = "text/plain";
            }
            else {
                s3 = "application/octet-stream";
            }
        }
        if (!b) {
            value = HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value();
        }
        final FileUpload fileUpload = this.factory.createFileUpload(this.request, s, content.getName(), s3, value, null, content.length());
        fileUpload.setContent(content);
        this.addBodyHttpData(fileUpload);
    }
    
    public void addBodyFileUploads(final String s, final File[] array, final String[] array2, final boolean[] array3) throws ErrorDataEncoderException {
        if (array.length != array2.length && array.length != array3.length) {
            throw new NullPointerException("Different array length");
        }
        while (0 < array.length) {
            this.addBodyFileUpload(s, array[0], array2[0], array3[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void addBodyHttpData(final InterfaceHttpData interfaceHttpData) throws ErrorDataEncoderException {
        if (this.headerFinalized) {
            throw new ErrorDataEncoderException("Cannot add value once finalized");
        }
        if (interfaceHttpData == null) {
            throw new NullPointerException("data");
        }
        this.bodyListDatas.add(interfaceHttpData);
        if (!this.isMultipart) {
            if (interfaceHttpData instanceof Attribute) {
                final Attribute attribute = (Attribute)interfaceHttpData;
                final Attribute attribute2 = this.factory.createAttribute(this.request, this.encodeAttribute(attribute.getName(), this.charset), this.encodeAttribute(attribute.getValue(), this.charset));
                this.multipartHttpDatas.add(attribute2);
                this.globalBodySize += attribute2.getName().length() + 1 + attribute2.length() + 1L;
            }
            else if (interfaceHttpData instanceof FileUpload) {
                final FileUpload fileUpload = (FileUpload)interfaceHttpData;
                final Attribute attribute3 = this.factory.createAttribute(this.request, this.encodeAttribute(fileUpload.getName(), this.charset), this.encodeAttribute(fileUpload.getFilename(), this.charset));
                this.multipartHttpDatas.add(attribute3);
                this.globalBodySize += attribute3.getName().length() + 1 + attribute3.length() + 1L;
            }
            return;
        }
        if (interfaceHttpData instanceof Attribute) {
            if (this.duringMixedMode) {
                final InternalAttribute internalAttribute = new InternalAttribute(this.charset);
                internalAttribute.addValue("\r\n--" + this.multipartMixedBoundary + "--");
                this.multipartHttpDatas.add(internalAttribute);
                this.multipartMixedBoundary = null;
                this.currentFileUpload = null;
                this.duringMixedMode = false;
            }
            final InternalAttribute internalAttribute2 = new InternalAttribute(this.charset);
            if (!this.multipartHttpDatas.isEmpty()) {
                internalAttribute2.addValue("\r\n");
            }
            internalAttribute2.addValue("--" + this.multipartDataBoundary + "\r\n");
            final Attribute attribute4 = (Attribute)interfaceHttpData;
            internalAttribute2.addValue("Content-Disposition: form-data; name=\"" + attribute4.getName() + "\"\r\n");
            final Charset charset = attribute4.getCharset();
            if (charset != null) {
                internalAttribute2.addValue("Content-Type: text/plain; charset=" + charset + "\r\n");
            }
            internalAttribute2.addValue("\r\n");
            this.multipartHttpDatas.add(internalAttribute2);
            this.multipartHttpDatas.add(interfaceHttpData);
            this.globalBodySize += attribute4.length() + internalAttribute2.size();
        }
        else if (interfaceHttpData instanceof FileUpload) {
            final FileUpload fileUpload2 = (FileUpload)interfaceHttpData;
            InternalAttribute internalAttribute3 = new InternalAttribute(this.charset);
            if (!this.multipartHttpDatas.isEmpty()) {
                internalAttribute3.addValue("\r\n");
            }
            if (this.duringMixedMode) {
                if (this.currentFileUpload == null || !this.currentFileUpload.getName().equals(fileUpload2.getName())) {
                    internalAttribute3.addValue("--" + this.multipartMixedBoundary + "--");
                    this.multipartHttpDatas.add(internalAttribute3);
                    this.multipartMixedBoundary = null;
                    internalAttribute3 = new InternalAttribute(this.charset);
                    internalAttribute3.addValue("\r\n");
                    this.currentFileUpload = fileUpload2;
                    this.duringMixedMode = false;
                }
            }
            else if (this.currentFileUpload != null && this.currentFileUpload.getName().equals(fileUpload2.getName())) {
                this.initMixedMultipart();
                final InternalAttribute internalAttribute4 = this.multipartHttpDatas.get(this.multipartHttpDatas.size() - 2);
                this.globalBodySize -= internalAttribute4.size();
                final StringBuilder sb = new StringBuilder(139 + this.multipartDataBoundary.length() + this.multipartMixedBoundary.length() * 2 + fileUpload2.getFilename().length() + fileUpload2.getName().length());
                sb.append("--");
                sb.append(this.multipartDataBoundary);
                sb.append("\r\n");
                sb.append("Content-Disposition");
                sb.append(": ");
                sb.append("form-data");
                sb.append("; ");
                sb.append("name");
                sb.append("=\"");
                sb.append(fileUpload2.getName());
                sb.append("\"\r\n");
                sb.append("Content-Type");
                sb.append(": ");
                sb.append("multipart/mixed");
                sb.append("; ");
                sb.append("boundary");
                sb.append('=');
                sb.append(this.multipartMixedBoundary);
                sb.append("\r\n\r\n");
                sb.append("--");
                sb.append(this.multipartMixedBoundary);
                sb.append("\r\n");
                sb.append("Content-Disposition");
                sb.append(": ");
                sb.append("attachment");
                sb.append("; ");
                sb.append("filename");
                sb.append("=\"");
                sb.append(fileUpload2.getFilename());
                sb.append("\"\r\n");
                internalAttribute4.setValue(sb.toString(), 1);
                internalAttribute4.setValue("", 2);
                this.globalBodySize += internalAttribute4.size();
                this.duringMixedMode = true;
            }
            else {
                this.currentFileUpload = fileUpload2;
                this.duringMixedMode = false;
            }
            internalAttribute3.addValue("--" + this.multipartDataBoundary + "\r\n");
            internalAttribute3.addValue("Content-Disposition: form-data; name=\"" + fileUpload2.getName() + "\"; " + "filename" + "=\"" + fileUpload2.getFilename() + "\"\r\n");
            internalAttribute3.addValue("Content-Type: " + fileUpload2.getContentType());
            final String contentTransferEncoding = fileUpload2.getContentTransferEncoding();
            if (contentTransferEncoding != null && contentTransferEncoding.equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) {
                internalAttribute3.addValue("\r\nContent-Transfer-Encoding: " + HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value() + "\r\n\r\n");
            }
            else if (fileUpload2.getCharset() != null) {
                internalAttribute3.addValue("; charset=" + fileUpload2.getCharset() + "\r\n\r\n");
            }
            else {
                internalAttribute3.addValue("\r\n\r\n");
            }
            this.multipartHttpDatas.add(internalAttribute3);
            this.multipartHttpDatas.add(interfaceHttpData);
            this.globalBodySize += fileUpload2.length() + internalAttribute3.size();
        }
    }
    
    public HttpRequest finalizeRequest() throws ErrorDataEncoderException {
        if (this.headerFinalized) {
            throw new ErrorDataEncoderException("Header already encoded");
        }
        if (this.isMultipart) {
            final InternalAttribute internalAttribute = new InternalAttribute(this.charset);
            if (this.duringMixedMode) {
                internalAttribute.addValue("\r\n--" + this.multipartMixedBoundary + "--");
            }
            internalAttribute.addValue("\r\n--" + this.multipartDataBoundary + "--\r\n");
            this.multipartHttpDatas.add(internalAttribute);
            this.multipartMixedBoundary = null;
            this.currentFileUpload = null;
            this.duringMixedMode = false;
            this.globalBodySize += internalAttribute.size();
        }
        this.headerFinalized = true;
        final HttpHeaders headers = this.request.headers();
        final List all = headers.getAll("Content-Type");
        final List all2 = headers.getAll("Transfer-Encoding");
        if (all != null) {
            headers.remove("Content-Type");
            for (final String s : all) {
                final String lowerCase = s.toLowerCase();
                if (!lowerCase.startsWith("multipart/form-data")) {
                    if (lowerCase.startsWith("application/x-www-form-urlencoded")) {
                        continue;
                    }
                    headers.add("Content-Type", s);
                }
            }
        }
        if (this.isMultipart) {
            headers.add("Content-Type", "multipart/form-data; boundary=" + this.multipartDataBoundary);
        }
        else {
            headers.add("Content-Type", "application/x-www-form-urlencoded");
        }
        long globalBodySize = this.globalBodySize;
        if (this.isMultipart) {
            this.iterator = this.multipartHttpDatas.listIterator();
        }
        else {
            --globalBodySize;
            this.iterator = this.multipartHttpDatas.listIterator();
        }
        headers.set("Content-Length", String.valueOf(globalBodySize));
        if (globalBodySize > 8096L || this.isMultipart) {
            this.isChunked = true;
            if (all2 != null) {
                headers.remove("Transfer-Encoding");
                for (final String s2 : all2) {
                    if (s2.equalsIgnoreCase("chunked")) {
                        continue;
                    }
                    headers.add("Transfer-Encoding", s2);
                }
            }
            HttpHeaders.setTransferEncodingChunked(this.request);
            return new WrappedHttpRequest(this.request);
        }
        final HttpContent nextChunk = this.nextChunk();
        if (this.request instanceof FullHttpRequest) {
            final FullHttpRequest fullHttpRequest = (FullHttpRequest)this.request;
            final ByteBuf content = nextChunk.content();
            if (fullHttpRequest.content() != content) {
                fullHttpRequest.content().clear().writeBytes(content);
                content.release();
            }
            return fullHttpRequest;
        }
        return new WrappedFullHttpRequest(this.request, nextChunk, null);
    }
    
    public boolean isChunked() {
        return this.isChunked;
    }
    
    private String encodeAttribute(final String s, final Charset charset) throws ErrorDataEncoderException {
        if (s == null) {
            return "";
        }
        String s2 = URLEncoder.encode(s, charset.name());
        if (this.encoderMode == EncoderMode.RFC3986) {
            for (final Map.Entry<K, String> entry : HttpPostRequestEncoder.percentEncodings.entrySet()) {
                s2 = ((Pattern)entry.getKey()).matcher(s2).replaceAll(entry.getValue());
            }
        }
        return s2;
    }
    
    private ByteBuf fillByteBuf() {
        if (this.currentBuffer.readableBytes() > 8096) {
            final ByteBuf slice = this.currentBuffer.slice(this.currentBuffer.readerIndex(), 8096);
            this.currentBuffer.skipBytes(8096);
            return slice;
        }
        final ByteBuf currentBuffer = this.currentBuffer;
        this.currentBuffer = null;
        return currentBuffer;
    }
    
    private HttpContent encodeNextChunkMultipart(final int n) throws ErrorDataEncoderException {
        if (this.currentData == null) {
            return null;
        }
        ByteBuf currentBuffer;
        if (this.currentData instanceof InternalAttribute) {
            currentBuffer = ((InternalAttribute)this.currentData).toByteBuf();
            this.currentData = null;
        }
        else {
            if (this.currentData instanceof Attribute) {
                currentBuffer = ((Attribute)this.currentData).getChunk(n);
            }
            else {
                currentBuffer = ((HttpData)this.currentData).getChunk(n);
            }
            if (currentBuffer.capacity() == 0) {
                this.currentData = null;
                return null;
            }
        }
        if (this.currentBuffer == null) {
            this.currentBuffer = currentBuffer;
        }
        else {
            this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, currentBuffer);
        }
        if (this.currentBuffer.readableBytes() < 8096) {
            this.currentData = null;
            return null;
        }
        return new DefaultHttpContent(this.fillByteBuf());
    }
    
    private HttpContent encodeNextChunkUrlEncoded(final int n) throws ErrorDataEncoderException {
        if (this.currentData == null) {
            return null;
        }
        int n2 = n;
        if (this.isKey) {
            final ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(this.currentData.getName().getBytes());
            this.isKey = false;
            if (this.currentBuffer == null) {
                this.currentBuffer = Unpooled.wrappedBuffer(wrappedBuffer, Unpooled.wrappedBuffer("=".getBytes()));
                n2 -= wrappedBuffer.readableBytes() + 1;
            }
            else {
                this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, wrappedBuffer, Unpooled.wrappedBuffer("=".getBytes()));
                n2 -= wrappedBuffer.readableBytes() + 1;
            }
            if (this.currentBuffer.readableBytes() >= 8096) {
                return new DefaultHttpContent(this.fillByteBuf());
            }
        }
        final ByteBuf chunk = ((HttpData)this.currentData).getChunk(n2);
        ByteBuf currentBuffer = null;
        if (chunk.readableBytes() < n2) {
            this.isKey = true;
            currentBuffer = (this.iterator.hasNext() ? Unpooled.wrappedBuffer("&".getBytes()) : null);
        }
        if (chunk.capacity() == 0) {
            this.currentData = null;
            if (this.currentBuffer == null) {
                this.currentBuffer = currentBuffer;
            }
            else if (currentBuffer != null) {
                this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, currentBuffer);
            }
            if (this.currentBuffer.readableBytes() >= 8096) {
                return new DefaultHttpContent(this.fillByteBuf());
            }
            return null;
        }
        else {
            if (this.currentBuffer == null) {
                if (currentBuffer != null) {
                    this.currentBuffer = Unpooled.wrappedBuffer(chunk, currentBuffer);
                }
                else {
                    this.currentBuffer = chunk;
                }
            }
            else if (currentBuffer != null) {
                this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, chunk, currentBuffer);
            }
            else {
                this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, chunk);
            }
            if (this.currentBuffer.readableBytes() < 8096) {
                this.currentData = null;
                this.isKey = true;
                return null;
            }
            return new DefaultHttpContent(this.fillByteBuf());
        }
    }
    
    @Override
    public void close() throws Exception {
    }
    
    @Override
    public HttpContent readChunk(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.isLastChunkSent) {
            return null;
        }
        return this.nextChunk();
    }
    
    private HttpContent nextChunk() throws ErrorDataEncoderException {
        if (this.isLastChunk) {
            this.isLastChunkSent = true;
            return LastHttpContent.EMPTY_LAST_CONTENT;
        }
        if (this.currentBuffer != null) {
            final int n = 8096 - this.currentBuffer.readableBytes();
        }
        if (this.currentData != null) {
            if (this.isMultipart) {
                final HttpContent encodeNextChunkMultipart = this.encodeNextChunkMultipart(8096);
                if (encodeNextChunkMultipart != null) {
                    return encodeNextChunkMultipart;
                }
            }
            else {
                final HttpContent encodeNextChunkUrlEncoded = this.encodeNextChunkUrlEncoded(8096);
                if (encodeNextChunkUrlEncoded != null) {
                    return encodeNextChunkUrlEncoded;
                }
            }
            final int n2 = 8096 - this.currentBuffer.readableBytes();
        }
        if (!this.iterator.hasNext()) {
            this.isLastChunk = true;
            final ByteBuf currentBuffer = this.currentBuffer;
            this.currentBuffer = null;
            return new DefaultHttpContent(currentBuffer);
        }
        while (this.iterator.hasNext()) {
            this.currentData = this.iterator.next();
            HttpContent httpContent;
            if (this.isMultipart) {
                httpContent = this.encodeNextChunkMultipart(8096);
            }
            else {
                httpContent = this.encodeNextChunkUrlEncoded(8096);
            }
            if (httpContent != null) {
                return httpContent;
            }
            final int n3 = 8096 - this.currentBuffer.readableBytes();
        }
        this.isLastChunk = true;
        if (this.currentBuffer == null) {
            this.isLastChunkSent = true;
            return LastHttpContent.EMPTY_LAST_CONTENT;
        }
        final ByteBuf currentBuffer2 = this.currentBuffer;
        this.currentBuffer = null;
        return new DefaultHttpContent(currentBuffer2);
    }
    
    @Override
    public boolean isEndOfInput() throws Exception {
        return this.isLastChunkSent;
    }
    
    @Override
    public Object readChunk(final ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.readChunk(channelHandlerContext);
    }
    
    static {
        (percentEncodings = new HashMap()).put(Pattern.compile("\\*"), "%2A");
        HttpPostRequestEncoder.percentEncodings.put(Pattern.compile("\\+"), "%20");
        HttpPostRequestEncoder.percentEncodings.put(Pattern.compile("%7E"), "~");
    }
    
    private static final class WrappedFullHttpRequest extends WrappedHttpRequest implements FullHttpRequest
    {
        private final HttpContent content;
        
        private WrappedFullHttpRequest(final HttpRequest httpRequest, final HttpContent content) {
            super(httpRequest);
            this.content = content;
        }
        
        @Override
        public FullHttpRequest setProtocolVersion(final HttpVersion protocolVersion) {
            super.setProtocolVersion(protocolVersion);
            return this;
        }
        
        @Override
        public FullHttpRequest setMethod(final HttpMethod method) {
            super.setMethod(method);
            return this;
        }
        
        @Override
        public FullHttpRequest setUri(final String uri) {
            super.setUri(uri);
            return this;
        }
        
        @Override
        public FullHttpRequest copy() {
            final DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(this.getProtocolVersion(), this.getMethod(), this.getUri(), this.content().copy());
            defaultFullHttpRequest.headers().set(this.headers());
            defaultFullHttpRequest.trailingHeaders().set(this.trailingHeaders());
            return defaultFullHttpRequest;
        }
        
        @Override
        public FullHttpRequest duplicate() {
            final DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(this.getProtocolVersion(), this.getMethod(), this.getUri(), this.content().duplicate());
            defaultFullHttpRequest.headers().set(this.headers());
            defaultFullHttpRequest.trailingHeaders().set(this.trailingHeaders());
            return defaultFullHttpRequest;
        }
        
        @Override
        public FullHttpRequest retain(final int n) {
            this.content.retain(n);
            return this;
        }
        
        @Override
        public FullHttpRequest retain() {
            this.content.retain();
            return this;
        }
        
        @Override
        public ByteBuf content() {
            return this.content.content();
        }
        
        @Override
        public HttpHeaders trailingHeaders() {
            if (this.content instanceof LastHttpContent) {
                return ((LastHttpContent)this.content).trailingHeaders();
            }
            return HttpHeaders.EMPTY_HEADERS;
        }
        
        @Override
        public int refCnt() {
            return this.content.refCnt();
        }
        
        @Override
        public boolean release() {
            return this.content.release();
        }
        
        @Override
        public boolean release(final int n) {
            return this.content.release(n);
        }
        
        @Override
        public HttpRequest setUri(final String uri) {
            return this.setUri(uri);
        }
        
        @Override
        public HttpRequest setMethod(final HttpMethod method) {
            return this.setMethod(method);
        }
        
        @Override
        public HttpRequest setProtocolVersion(final HttpVersion protocolVersion) {
            return this.setProtocolVersion(protocolVersion);
        }
        
        @Override
        public HttpMessage setProtocolVersion(final HttpVersion protocolVersion) {
            return this.setProtocolVersion(protocolVersion);
        }
        
        @Override
        public FullHttpMessage retain() {
            return this.retain();
        }
        
        @Override
        public FullHttpMessage retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public FullHttpMessage copy() {
            return this.copy();
        }
        
        @Override
        public LastHttpContent retain() {
            return this.retain();
        }
        
        @Override
        public LastHttpContent retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public LastHttpContent copy() {
            return this.copy();
        }
        
        @Override
        public HttpContent retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public HttpContent retain() {
            return this.retain();
        }
        
        @Override
        public HttpContent duplicate() {
            return this.duplicate();
        }
        
        @Override
        public HttpContent copy() {
            return this.copy();
        }
        
        @Override
        public ByteBufHolder retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public ByteBufHolder retain() {
            return this.retain();
        }
        
        @Override
        public ByteBufHolder duplicate() {
            return this.duplicate();
        }
        
        @Override
        public ByteBufHolder copy() {
            return this.copy();
        }
        
        @Override
        public ReferenceCounted retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public ReferenceCounted retain() {
            return this.retain();
        }
        
        WrappedFullHttpRequest(final HttpRequest httpRequest, final HttpContent httpContent, final HttpPostRequestEncoder$1 object) {
            this(httpRequest, httpContent);
        }
    }
    
    private static class WrappedHttpRequest implements HttpRequest
    {
        private final HttpRequest request;
        
        WrappedHttpRequest(final HttpRequest request) {
            this.request = request;
        }
        
        @Override
        public HttpRequest setProtocolVersion(final HttpVersion protocolVersion) {
            this.request.setProtocolVersion(protocolVersion);
            return this;
        }
        
        @Override
        public HttpRequest setMethod(final HttpMethod method) {
            this.request.setMethod(method);
            return this;
        }
        
        @Override
        public HttpRequest setUri(final String uri) {
            this.request.setUri(uri);
            return this;
        }
        
        @Override
        public HttpMethod getMethod() {
            return this.request.getMethod();
        }
        
        @Override
        public String getUri() {
            return this.request.getUri();
        }
        
        @Override
        public HttpVersion getProtocolVersion() {
            return this.request.getProtocolVersion();
        }
        
        @Override
        public HttpHeaders headers() {
            return this.request.headers();
        }
        
        @Override
        public DecoderResult getDecoderResult() {
            return this.request.getDecoderResult();
        }
        
        @Override
        public void setDecoderResult(final DecoderResult decoderResult) {
            this.request.setDecoderResult(decoderResult);
        }
        
        @Override
        public HttpMessage setProtocolVersion(final HttpVersion protocolVersion) {
            return this.setProtocolVersion(protocolVersion);
        }
    }
    
    public static class ErrorDataEncoderException extends Exception
    {
        private static final long serialVersionUID = 5020247425493164465L;
        
        public ErrorDataEncoderException() {
        }
        
        public ErrorDataEncoderException(final String s) {
            super(s);
        }
        
        public ErrorDataEncoderException(final Throwable t) {
            super(t);
        }
        
        public ErrorDataEncoderException(final String s, final Throwable t) {
            super(s, t);
        }
    }
    
    public enum EncoderMode
    {
        RFC1738("RFC1738", 0), 
        RFC3986("RFC3986", 1);
        
        private static final EncoderMode[] $VALUES;
        
        private EncoderMode(final String s, final int n) {
        }
        
        static {
            $VALUES = new EncoderMode[] { EncoderMode.RFC1738, EncoderMode.RFC3986 };
        }
    }
}
