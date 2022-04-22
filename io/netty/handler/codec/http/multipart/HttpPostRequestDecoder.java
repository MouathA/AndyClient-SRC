package io.netty.handler.codec.http.multipart;

import java.nio.charset.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.util.internal.*;
import java.io.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.*;

public class HttpPostRequestDecoder
{
    private static final int DEFAULT_DISCARD_THRESHOLD = 10485760;
    private final HttpDataFactory factory;
    private final HttpRequest request;
    private final Charset charset;
    private boolean bodyToDecode;
    private boolean isLastChunk;
    private final List bodyListHttpData;
    private final Map bodyMapHttpData;
    private ByteBuf undecodedChunk;
    private boolean isMultipart;
    private int bodyListHttpDataRank;
    private String multipartDataBoundary;
    private String multipartMixedBoundary;
    private MultiPartStatus currentStatus;
    private Map currentFieldAttributes;
    private FileUpload currentFileUpload;
    private Attribute currentAttribute;
    private boolean destroyed;
    private int discardThreshold;
    
    public HttpPostRequestDecoder(final HttpRequest httpRequest) throws ErrorDataDecoderException, IncompatibleDataDecoderException {
        this(new DefaultHttpDataFactory(16384L), httpRequest, HttpConstants.DEFAULT_CHARSET);
    }
    
    public HttpPostRequestDecoder(final HttpDataFactory httpDataFactory, final HttpRequest httpRequest) throws ErrorDataDecoderException, IncompatibleDataDecoderException {
        this(httpDataFactory, httpRequest, HttpConstants.DEFAULT_CHARSET);
    }
    
    public HttpPostRequestDecoder(final HttpDataFactory factory, final HttpRequest request, final Charset charset) throws ErrorDataDecoderException, IncompatibleDataDecoderException {
        this.bodyListHttpData = new ArrayList();
        this.bodyMapHttpData = new TreeMap(CaseIgnoringComparator.INSTANCE);
        this.currentStatus = MultiPartStatus.NOTSTARTED;
        this.discardThreshold = 10485760;
        if (factory == null) {
            throw new NullPointerException("factory");
        }
        if (request == null) {
            throw new NullPointerException("request");
        }
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.request = request;
        final HttpMethod method = request.getMethod();
        if (method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT) || method.equals(HttpMethod.PATCH)) {
            this.bodyToDecode = true;
        }
        this.charset = charset;
        this.factory = factory;
        final String value = this.request.headers().get("Content-Type");
        if (value != null) {
            this.checkMultipart(value);
        }
        else {
            this.isMultipart = false;
        }
        if (!this.bodyToDecode) {
            throw new IncompatibleDataDecoderException("No Body to decode");
        }
        if (request instanceof HttpContent) {
            this.offer((HttpContent)request);
        }
        else {
            this.undecodedChunk = Unpooled.buffer();
            this.parseBody();
        }
    }
    
    private void checkMultipart(final String s) throws ErrorDataDecoderException {
        final String[] splitHeaderContentType = splitHeaderContentType(s);
        if (splitHeaderContentType[0].toLowerCase().startsWith("multipart/form-data") && splitHeaderContentType[1].toLowerCase().startsWith("boundary")) {
            final String[] split = StringUtil.split(splitHeaderContentType[1], '=');
            if (split.length != 2) {
                throw new ErrorDataDecoderException("Needs a boundary value");
            }
            if (split[1].charAt(0) == '\"') {
                final String trim = split[1].trim();
                final int n = trim.length() - 1;
                if (trim.charAt(n) == '\"') {
                    split[1] = trim.substring(1, n);
                }
            }
            this.multipartDataBoundary = "--" + split[1];
            this.isMultipart = true;
            this.currentStatus = MultiPartStatus.HEADERDELIMITER;
        }
        else {
            this.isMultipart = false;
        }
    }
    
    private void checkDestroyed() {
        if (this.destroyed) {
            throw new IllegalStateException(HttpPostRequestDecoder.class.getSimpleName() + " was destroyed already");
        }
    }
    
    public boolean isMultipart() {
        this.checkDestroyed();
        return this.isMultipart;
    }
    
    public void setDiscardThreshold(final int discardThreshold) {
        if (discardThreshold < 0) {
            throw new IllegalArgumentException("discardThreshold must be >= 0");
        }
        this.discardThreshold = discardThreshold;
    }
    
    public int getDiscardThreshold() {
        return this.discardThreshold;
    }
    
    public List getBodyHttpDatas() throws NotEnoughDataDecoderException {
        this.checkDestroyed();
        if (!this.isLastChunk) {
            throw new NotEnoughDataDecoderException();
        }
        return this.bodyListHttpData;
    }
    
    public List getBodyHttpDatas(final String s) throws NotEnoughDataDecoderException {
        this.checkDestroyed();
        if (!this.isLastChunk) {
            throw new NotEnoughDataDecoderException();
        }
        return this.bodyMapHttpData.get(s);
    }
    
    public InterfaceHttpData getBodyHttpData(final String s) throws NotEnoughDataDecoderException {
        this.checkDestroyed();
        if (!this.isLastChunk) {
            throw new NotEnoughDataDecoderException();
        }
        final List<InterfaceHttpData> list = this.bodyMapHttpData.get(s);
        if (list != null) {
            return list.get(0);
        }
        return null;
    }
    
    public HttpPostRequestDecoder offer(final HttpContent httpContent) throws ErrorDataDecoderException {
        this.checkDestroyed();
        final ByteBuf content = httpContent.content();
        if (this.undecodedChunk == null) {
            this.undecodedChunk = content.copy();
        }
        else {
            this.undecodedChunk.writeBytes(content);
        }
        if (httpContent instanceof LastHttpContent) {
            this.isLastChunk = true;
        }
        this.parseBody();
        if (this.undecodedChunk != null && this.undecodedChunk.writerIndex() > this.discardThreshold) {
            this.undecodedChunk.discardReadBytes();
        }
        return this;
    }
    
    public InterfaceHttpData next() throws EndOfDataDecoderException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   io/netty/handler/codec/http/multipart/HttpPostRequestDecoder.checkDestroyed:()V
        //     4: aload_0        
        //     5: if_acmpne       32
        //     8: aload_0        
        //     9: getfield        io/netty/handler/codec/http/multipart/HttpPostRequestDecoder.bodyListHttpData:Ljava/util/List;
        //    12: aload_0        
        //    13: dup            
        //    14: getfield        io/netty/handler/codec/http/multipart/HttpPostRequestDecoder.bodyListHttpDataRank:I
        //    17: dup_x1         
        //    18: iconst_1       
        //    19: iadd           
        //    20: putfield        io/netty/handler/codec/http/multipart/HttpPostRequestDecoder.bodyListHttpDataRank:I
        //    23: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //    28: checkcast       Lio/netty/handler/codec/http/multipart/InterfaceHttpData;
        //    31: areturn        
        //    32: aconst_null    
        //    33: areturn        
        //    Exceptions:
        //  throws io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void parseBody() throws ErrorDataDecoderException {
        if (this.currentStatus == MultiPartStatus.PREEPILOGUE || this.currentStatus == MultiPartStatus.EPILOGUE) {
            if (this.isLastChunk) {
                this.currentStatus = MultiPartStatus.EPILOGUE;
            }
            return;
        }
        if (this.isMultipart) {
            this.parseBodyMultipart();
        }
        else {
            this.parseBodyAttributes();
        }
    }
    
    protected void addHttpData(final InterfaceHttpData interfaceHttpData) {
        if (interfaceHttpData == null) {
            return;
        }
        List<InterfaceHttpData> list = this.bodyMapHttpData.get(interfaceHttpData.getName());
        if (list == null) {
            list = new ArrayList<InterfaceHttpData>(1);
            this.bodyMapHttpData.put(interfaceHttpData.getName(), list);
        }
        list.add(interfaceHttpData);
        this.bodyListHttpData.add(interfaceHttpData);
    }
    
    private void parseBodyAttributesStandard() throws ErrorDataDecoderException {
        final int readerIndex;
        final int n = readerIndex = this.undecodedChunk.readerIndex();
        if (this.currentStatus == MultiPartStatus.NOTSTARTED) {
            this.currentStatus = MultiPartStatus.DISPOSITION;
        }
        if (this.undecodedChunk.isReadable()) {}
        if (this.isLastChunk && this.currentAttribute != null) {
            final int n2 = readerIndex;
            if (n2 > n) {
                this.setFinalBuffer(this.undecodedChunk.copy(n, n2 - n));
            }
            else if (!this.currentAttribute.isCompleted()) {
                this.setFinalBuffer(Unpooled.EMPTY_BUFFER);
            }
            final int n3 = readerIndex;
            this.currentStatus = MultiPartStatus.EPILOGUE;
            this.undecodedChunk.readerIndex(n3);
            return;
        }
        this.undecodedChunk.readerIndex(n);
    }
    
    private void parseBodyAttributes() throws ErrorDataDecoderException {
        final HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
        int readerIndex;
        int n = readerIndex = this.undecodedChunk.readerIndex();
        if (this.currentStatus == MultiPartStatus.NOTSTARTED) {
            this.currentStatus = MultiPartStatus.DISPOSITION;
        }
    Label_0499:
        while (seekAheadOptimize.pos < seekAheadOptimize.limit) {
            final char c = (char)(seekAheadOptimize.bytes[seekAheadOptimize.pos++] & 0xFF);
            ++readerIndex;
            switch (this.currentStatus) {
                case DISPOSITION: {
                    if (c == '=') {
                        this.currentStatus = MultiPartStatus.FIELD;
                        this.currentAttribute = this.factory.createAttribute(this.request, decodeAttribute(this.undecodedChunk.toString(n, readerIndex - 1 - n, this.charset), this.charset));
                        n = readerIndex;
                        continue;
                    }
                    if (c == '&') {
                        this.currentStatus = MultiPartStatus.DISPOSITION;
                        (this.currentAttribute = this.factory.createAttribute(this.request, decodeAttribute(this.undecodedChunk.toString(n, readerIndex - 1 - n, this.charset), this.charset))).setValue("");
                        this.addHttpData(this.currentAttribute);
                        this.currentAttribute = null;
                        n = readerIndex;
                        continue;
                    }
                    continue;
                }
                case FIELD: {
                    if (c == '&') {
                        this.currentStatus = MultiPartStatus.DISPOSITION;
                        this.setFinalBuffer(this.undecodedChunk.copy(n, readerIndex - 1 - n));
                        n = readerIndex;
                        continue;
                    }
                    if (c == '\r') {
                        if (seekAheadOptimize.pos < seekAheadOptimize.limit) {
                            final char c2 = (char)(seekAheadOptimize.bytes[seekAheadOptimize.pos++] & 0xFF);
                            ++readerIndex;
                            if (c2 == '\n') {
                                this.currentStatus = MultiPartStatus.PREEPILOGUE;
                                final int n2 = readerIndex - 2;
                                seekAheadOptimize.setReadPosition(0);
                                this.setFinalBuffer(this.undecodedChunk.copy(n, n2 - n));
                                n = readerIndex;
                                break Label_0499;
                            }
                            seekAheadOptimize.setReadPosition(0);
                            throw new ErrorDataDecoderException("Bad end of line");
                        }
                        else {
                            if (seekAheadOptimize.limit > 0) {
                                --readerIndex;
                                continue;
                            }
                            continue;
                        }
                    }
                    else {
                        if (c == '\n') {
                            this.currentStatus = MultiPartStatus.PREEPILOGUE;
                            final int n3 = readerIndex - 1;
                            seekAheadOptimize.setReadPosition(0);
                            this.setFinalBuffer(this.undecodedChunk.copy(n, n3 - n));
                            n = readerIndex;
                            break Label_0499;
                        }
                        continue;
                    }
                    break;
                }
                default: {
                    seekAheadOptimize.setReadPosition(0);
                    break Label_0499;
                }
            }
        }
        if (this.isLastChunk && this.currentAttribute != null) {
            final int n4 = readerIndex;
            if (n4 > n) {
                this.setFinalBuffer(this.undecodedChunk.copy(n, n4 - n));
            }
            else if (!this.currentAttribute.isCompleted()) {
                this.setFinalBuffer(Unpooled.EMPTY_BUFFER);
            }
            final int n5 = readerIndex;
            this.currentStatus = MultiPartStatus.EPILOGUE;
            this.undecodedChunk.readerIndex(n5);
            return;
        }
        this.undecodedChunk.readerIndex(n);
    }
    
    private void setFinalBuffer(final ByteBuf byteBuf) throws ErrorDataDecoderException, IOException {
        this.currentAttribute.addContent(byteBuf, true);
        this.currentAttribute.setValue(decodeAttribute(this.currentAttribute.getByteBuf().toString(this.charset), this.charset));
        this.addHttpData(this.currentAttribute);
        this.currentAttribute = null;
    }
    
    private static String decodeAttribute(final String s, final Charset charset) throws ErrorDataDecoderException {
        return QueryStringDecoder.decodeComponent(s, charset);
    }
    
    private void parseBodyMultipart() throws ErrorDataDecoderException {
        if (this.undecodedChunk == null || this.undecodedChunk.readableBytes() == 0) {
            return;
        }
        for (InterfaceHttpData interfaceHttpData = this.decodeMultipart(this.currentStatus); interfaceHttpData != null; interfaceHttpData = this.decodeMultipart(this.currentStatus)) {
            this.addHttpData(interfaceHttpData);
            if (this.currentStatus == MultiPartStatus.PREEPILOGUE) {
                break;
            }
            if (this.currentStatus == MultiPartStatus.EPILOGUE) {
                break;
            }
        }
    }
    
    private InterfaceHttpData decodeMultipart(final MultiPartStatus multiPartStatus) throws ErrorDataDecoderException {
        switch (multiPartStatus) {
            case NOTSTARTED: {
                throw new ErrorDataDecoderException("Should not be called with the current getStatus");
            }
            case PREAMBLE: {
                throw new ErrorDataDecoderException("Should not be called with the current getStatus");
            }
            case HEADERDELIMITER: {
                return this.findMultipartDelimiter(this.multipartDataBoundary, MultiPartStatus.DISPOSITION, MultiPartStatus.PREEPILOGUE);
            }
            case DISPOSITION: {
                return this.findMultipartDisposition();
            }
            case FIELD: {
                Charset forName = null;
                final Attribute attribute = this.currentFieldAttributes.get("charset");
                if (attribute != null) {
                    forName = Charset.forName(attribute.getValue());
                }
                final Attribute attribute2 = this.currentFieldAttributes.get("name");
                if (this.currentAttribute == null) {
                    this.currentAttribute = this.factory.createAttribute(this.request, cleanString(attribute2.getValue()));
                    if (forName != null) {
                        this.currentAttribute.setCharset(forName);
                    }
                }
                this.loadFieldMultipart(this.multipartDataBoundary);
                final Attribute currentAttribute = this.currentAttribute;
                this.currentAttribute = null;
                this.currentFieldAttributes = null;
                this.currentStatus = MultiPartStatus.HEADERDELIMITER;
                return currentAttribute;
            }
            case FILEUPLOAD: {
                return this.getFileUpload(this.multipartDataBoundary);
            }
            case MIXEDDELIMITER: {
                return this.findMultipartDelimiter(this.multipartMixedBoundary, MultiPartStatus.MIXEDDISPOSITION, MultiPartStatus.HEADERDELIMITER);
            }
            case MIXEDDISPOSITION: {
                return this.findMultipartDisposition();
            }
            case MIXEDFILEUPLOAD: {
                return this.getFileUpload(this.multipartMixedBoundary);
            }
            case PREEPILOGUE: {
                return null;
            }
            case EPILOGUE: {
                return null;
            }
            default: {
                throw new ErrorDataDecoderException("Shouldn't reach here.");
            }
        }
    }
    
    void skipControlCharacters() throws NotEnoughDataDecoderException {
        final HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
        while (seekAheadOptimize.pos < seekAheadOptimize.limit) {
            final char c = (char)(seekAheadOptimize.bytes[seekAheadOptimize.pos++] & 0xFF);
            if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
                seekAheadOptimize.setReadPosition(1);
                return;
            }
        }
        throw new NotEnoughDataDecoderException("Access out of bounds");
    }
    
    void skipControlCharactersStandard() {
        char c;
        do {
            c = (char)this.undecodedChunk.readUnsignedByte();
        } while (Character.isISOControl(c) || Character.isWhitespace(c));
        this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
    }
    
    private InterfaceHttpData findMultipartDelimiter(final String s, final MultiPartStatus currentStatus, final MultiPartStatus currentStatus2) throws ErrorDataDecoderException {
        final int readerIndex = this.undecodedChunk.readerIndex();
        this.skipControlCharacters();
        this.skipOneLine();
        final String delimiter = this.readDelimiter(s);
        if (delimiter.equals(s)) {
            this.currentStatus = currentStatus;
            return this.decodeMultipart(currentStatus);
        }
        if (!delimiter.equals(s + "--")) {
            this.undecodedChunk.readerIndex(readerIndex);
            throw new ErrorDataDecoderException("No Multipart delimiter found");
        }
        this.currentStatus = currentStatus2;
        if (this.currentStatus == MultiPartStatus.HEADERDELIMITER) {
            this.currentFieldAttributes = null;
            return this.decodeMultipart(MultiPartStatus.HEADERDELIMITER);
        }
        return null;
    }
    
    private InterfaceHttpData findMultipartDisposition() throws ErrorDataDecoderException {
        this.undecodedChunk.readerIndex();
        if (this.currentStatus == MultiPartStatus.DISPOSITION) {
            this.currentFieldAttributes = new TreeMap(CaseIgnoringComparator.INSTANCE);
        }
        while (this == 0) {
            this.skipControlCharacters();
            final String line = this.readLine();
            final String[] splitMultipartHeader = splitMultipartHeader(line);
            if (splitMultipartHeader[0].equalsIgnoreCase("Content-Disposition")) {
                if (this.currentStatus == MultiPartStatus.DISPOSITION) {
                    final int equalsIgnoreCase = splitMultipartHeader[1].equalsIgnoreCase("form-data") ? 1 : 0;
                }
                else {
                    final int equalsIgnoreCase = (splitMultipartHeader[1].equalsIgnoreCase("attachment") || splitMultipartHeader[1].equalsIgnoreCase("file")) ? 1 : 0;
                }
                while (2 < splitMultipartHeader.length) {
                    final String[] split = StringUtil.split(splitMultipartHeader[2], '=');
                    final String cleanString = cleanString(split[0]);
                    final String s = split[1];
                    String s2;
                    if ("filename".equals(cleanString)) {
                        s2 = s.substring(1, s.length() - 1);
                    }
                    else {
                        s2 = cleanString(s);
                    }
                    final Attribute attribute = this.factory.createAttribute(this.request, cleanString, s2);
                    this.currentFieldAttributes.put(attribute.getName(), attribute);
                    int n = 0;
                    ++n;
                }
            }
            else if (splitMultipartHeader[0].equalsIgnoreCase("Content-Transfer-Encoding")) {
                this.currentFieldAttributes.put("Content-Transfer-Encoding", this.factory.createAttribute(this.request, "Content-Transfer-Encoding", cleanString(splitMultipartHeader[1])));
            }
            else if (splitMultipartHeader[0].equalsIgnoreCase("Content-Length")) {
                this.currentFieldAttributes.put("Content-Length", this.factory.createAttribute(this.request, "Content-Length", cleanString(splitMultipartHeader[1])));
            }
            else {
                if (!splitMultipartHeader[0].equalsIgnoreCase("Content-Type")) {
                    throw new ErrorDataDecoderException("Unknown Params: " + line);
                }
                if (splitMultipartHeader[1].equalsIgnoreCase("multipart/mixed")) {
                    if (this.currentStatus == MultiPartStatus.DISPOSITION) {
                        this.multipartMixedBoundary = "--" + StringUtil.split(splitMultipartHeader[2], '=')[1];
                        this.currentStatus = MultiPartStatus.MIXEDDELIMITER;
                        return this.decodeMultipart(MultiPartStatus.MIXEDDELIMITER);
                    }
                    throw new ErrorDataDecoderException("Mixed Multipart found in a previous Mixed Multipart");
                }
                else {
                    while (1 < splitMultipartHeader.length) {
                        if (splitMultipartHeader[1].toLowerCase().startsWith("charset")) {
                            this.currentFieldAttributes.put("charset", this.factory.createAttribute(this.request, "charset", cleanString(StringUtil.split(splitMultipartHeader[1], '=')[1])));
                        }
                        else {
                            final Attribute attribute2 = this.factory.createAttribute(this.request, cleanString(splitMultipartHeader[0]), splitMultipartHeader[1]);
                            this.currentFieldAttributes.put(attribute2.getName(), attribute2);
                        }
                        int equalsIgnoreCase = 0;
                        ++equalsIgnoreCase;
                    }
                }
            }
        }
        final Attribute attribute3 = this.currentFieldAttributes.get("filename");
        if (this.currentStatus == MultiPartStatus.DISPOSITION) {
            if (attribute3 != null) {
                this.currentStatus = MultiPartStatus.FILEUPLOAD;
                return this.decodeMultipart(MultiPartStatus.FILEUPLOAD);
            }
            this.currentStatus = MultiPartStatus.FIELD;
            return this.decodeMultipart(MultiPartStatus.FIELD);
        }
        else {
            if (attribute3 != null) {
                this.currentStatus = MultiPartStatus.MIXEDFILEUPLOAD;
                return this.decodeMultipart(MultiPartStatus.MIXEDFILEUPLOAD);
            }
            throw new ErrorDataDecoderException("Filename not found");
        }
    }
    
    protected InterfaceHttpData getFileUpload(final String s) throws ErrorDataDecoderException {
        final Attribute attribute = this.currentFieldAttributes.get("Content-Transfer-Encoding");
        Charset charset = this.charset;
        HttpPostBodyUtil.TransferEncodingMechanism transferEncodingMechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT7;
        if (attribute != null) {
            final String lowerCase = attribute.getValue().toLowerCase();
            if (lowerCase.equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT7.value())) {
                charset = HttpPostBodyUtil.US_ASCII;
            }
            else if (lowerCase.equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT8.value())) {
                charset = HttpPostBodyUtil.ISO_8859_1;
                transferEncodingMechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT8;
            }
            else {
                if (!lowerCase.equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) {
                    throw new ErrorDataDecoderException("TransferEncoding Unknown: " + lowerCase);
                }
                transferEncodingMechanism = HttpPostBodyUtil.TransferEncodingMechanism.BINARY;
            }
        }
        final Attribute attribute2 = this.currentFieldAttributes.get("charset");
        if (attribute2 != null) {
            charset = Charset.forName(attribute2.getValue());
        }
        if (this.currentFileUpload == null) {
            final Attribute attribute3 = this.currentFieldAttributes.get("filename");
            final Attribute attribute4 = this.currentFieldAttributes.get("name");
            final Attribute attribute5 = this.currentFieldAttributes.get("Content-Type");
            if (attribute5 == null) {
                throw new ErrorDataDecoderException("Content-Type is absent but required");
            }
            final Attribute attribute6 = this.currentFieldAttributes.get("Content-Length");
            this.currentFileUpload = this.factory.createFileUpload(this.request, cleanString(attribute4.getValue()), cleanString(attribute3.getValue()), attribute5.getValue(), transferEncodingMechanism.value(), charset, (attribute6 != null) ? Long.parseLong(attribute6.getValue()) : 0L);
        }
        this.readFileUploadByteMultipart(s);
        if (this.currentFileUpload.isCompleted()) {
            if (this.currentStatus == MultiPartStatus.FILEUPLOAD) {
                this.currentStatus = MultiPartStatus.HEADERDELIMITER;
                this.currentFieldAttributes = null;
            }
            else {
                this.currentStatus = MultiPartStatus.MIXEDDELIMITER;
                this.cleanMixedAttributes();
            }
            final FileUpload currentFileUpload = this.currentFileUpload;
            this.currentFileUpload = null;
            return currentFileUpload;
        }
        return null;
    }
    
    public void destroy() {
        this.checkDestroyed();
        this.cleanFiles();
        this.destroyed = true;
        if (this.undecodedChunk != null && this.undecodedChunk.refCnt() > 0) {
            this.undecodedChunk.release();
            this.undecodedChunk = null;
        }
        for (int i = this.bodyListHttpDataRank; i < this.bodyListHttpData.size(); ++i) {
            ((InterfaceHttpData)this.bodyListHttpData.get(i)).release();
        }
    }
    
    public void cleanFiles() {
        this.checkDestroyed();
        this.factory.cleanRequestHttpDatas(this.request);
    }
    
    public void removeHttpDataFromClean(final InterfaceHttpData interfaceHttpData) {
        this.checkDestroyed();
        this.factory.removeHttpDataFromClean(this.request, interfaceHttpData);
    }
    
    private void cleanMixedAttributes() {
        this.currentFieldAttributes.remove("charset");
        this.currentFieldAttributes.remove("Content-Length");
        this.currentFieldAttributes.remove("Content-Transfer-Encoding");
        this.currentFieldAttributes.remove("Content-Type");
        this.currentFieldAttributes.remove("filename");
    }
    
    private String readLineStandard() throws NotEnoughDataDecoderException {
        final int readerIndex = this.undecodedChunk.readerIndex();
        final ByteBuf buffer = Unpooled.buffer(64);
        while (this.undecodedChunk.isReadable()) {
            final byte byte1 = this.undecodedChunk.readByte();
            if (byte1 == 13) {
                if (this.undecodedChunk.getByte(this.undecodedChunk.readerIndex()) == 10) {
                    this.undecodedChunk.skipBytes(1);
                    return buffer.toString(this.charset);
                }
                buffer.writeByte(13);
            }
            else {
                if (byte1 == 10) {
                    return buffer.toString(this.charset);
                }
                buffer.writeByte(byte1);
            }
        }
        this.undecodedChunk.readerIndex(readerIndex);
        throw new NotEnoughDataDecoderException();
    }
    
    private String readLine() throws NotEnoughDataDecoderException {
        final HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
        final int readerIndex = this.undecodedChunk.readerIndex();
        final ByteBuf buffer = Unpooled.buffer(64);
        while (seekAheadOptimize.pos < seekAheadOptimize.limit) {
            final byte b = seekAheadOptimize.bytes[seekAheadOptimize.pos++];
            if (b == 13) {
                if (seekAheadOptimize.pos < seekAheadOptimize.limit) {
                    if (seekAheadOptimize.bytes[seekAheadOptimize.pos++] == 10) {
                        seekAheadOptimize.setReadPosition(0);
                        return buffer.toString(this.charset);
                    }
                    final HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize2 = seekAheadOptimize;
                    --seekAheadOptimize2.pos;
                    buffer.writeByte(13);
                }
                else {
                    buffer.writeByte(b);
                }
            }
            else {
                if (b == 10) {
                    seekAheadOptimize.setReadPosition(0);
                    return buffer.toString(this.charset);
                }
                buffer.writeByte(b);
            }
        }
        this.undecodedChunk.readerIndex(readerIndex);
        throw new NotEnoughDataDecoderException();
    }
    
    private String readDelimiterStandard(final String s) throws NotEnoughDataDecoderException {
        final int readerIndex = this.undecodedChunk.readerIndex();
        final StringBuilder sb = new StringBuilder(64);
        final int length = s.length();
        while (this.undecodedChunk.isReadable() && 0 < length) {
            final byte byte1 = this.undecodedChunk.readByte();
            if (byte1 != s.charAt(0)) {
                this.undecodedChunk.readerIndex(readerIndex);
                throw new NotEnoughDataDecoderException();
            }
            int n = 0;
            ++n;
            sb.append((char)byte1);
        }
        if (this.undecodedChunk.isReadable()) {
            final byte byte2 = this.undecodedChunk.readByte();
            if (byte2 == 13) {
                if (this.undecodedChunk.readByte() == 10) {
                    return sb.toString();
                }
                this.undecodedChunk.readerIndex(readerIndex);
                throw new NotEnoughDataDecoderException();
            }
            else {
                if (byte2 == 10) {
                    return sb.toString();
                }
                if (byte2 == 45) {
                    sb.append('-');
                    if (this.undecodedChunk.readByte() == 45) {
                        sb.append('-');
                        if (!this.undecodedChunk.isReadable()) {
                            return sb.toString();
                        }
                        final byte byte3 = this.undecodedChunk.readByte();
                        if (byte3 == 13) {
                            if (this.undecodedChunk.readByte() == 10) {
                                return sb.toString();
                            }
                            this.undecodedChunk.readerIndex(readerIndex);
                            throw new NotEnoughDataDecoderException();
                        }
                        else {
                            if (byte3 == 10) {
                                return sb.toString();
                            }
                            this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
                            return sb.toString();
                        }
                    }
                }
            }
        }
        this.undecodedChunk.readerIndex(readerIndex);
        throw new NotEnoughDataDecoderException();
    }
    
    private String readDelimiter(final String s) throws NotEnoughDataDecoderException {
        final HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
        final int readerIndex = this.undecodedChunk.readerIndex();
        final int length = s.length();
        final StringBuilder sb = new StringBuilder(64);
        while (seekAheadOptimize.pos < seekAheadOptimize.limit && 0 < length) {
            final byte b = seekAheadOptimize.bytes[seekAheadOptimize.pos++];
            if (b != s.charAt(0)) {
                this.undecodedChunk.readerIndex(readerIndex);
                throw new NotEnoughDataDecoderException();
            }
            int n = 0;
            ++n;
            sb.append((char)b);
        }
        if (seekAheadOptimize.pos < seekAheadOptimize.limit) {
            final byte b2 = seekAheadOptimize.bytes[seekAheadOptimize.pos++];
            if (b2 == 13) {
                if (seekAheadOptimize.pos >= seekAheadOptimize.limit) {
                    this.undecodedChunk.readerIndex(readerIndex);
                    throw new NotEnoughDataDecoderException();
                }
                if (seekAheadOptimize.bytes[seekAheadOptimize.pos++] == 10) {
                    seekAheadOptimize.setReadPosition(0);
                    return sb.toString();
                }
                this.undecodedChunk.readerIndex(readerIndex);
                throw new NotEnoughDataDecoderException();
            }
            else {
                if (b2 == 10) {
                    seekAheadOptimize.setReadPosition(0);
                    return sb.toString();
                }
                if (b2 == 45) {
                    sb.append('-');
                    if (seekAheadOptimize.pos < seekAheadOptimize.limit && seekAheadOptimize.bytes[seekAheadOptimize.pos++] == 45) {
                        sb.append('-');
                        if (seekAheadOptimize.pos >= seekAheadOptimize.limit) {
                            seekAheadOptimize.setReadPosition(0);
                            return sb.toString();
                        }
                        final byte b3 = seekAheadOptimize.bytes[seekAheadOptimize.pos++];
                        if (b3 == 13) {
                            if (seekAheadOptimize.pos >= seekAheadOptimize.limit) {
                                this.undecodedChunk.readerIndex(readerIndex);
                                throw new NotEnoughDataDecoderException();
                            }
                            if (seekAheadOptimize.bytes[seekAheadOptimize.pos++] == 10) {
                                seekAheadOptimize.setReadPosition(0);
                                return sb.toString();
                            }
                            this.undecodedChunk.readerIndex(readerIndex);
                            throw new NotEnoughDataDecoderException();
                        }
                        else {
                            if (b3 == 10) {
                                seekAheadOptimize.setReadPosition(0);
                                return sb.toString();
                            }
                            seekAheadOptimize.setReadPosition(1);
                            return sb.toString();
                        }
                    }
                }
            }
        }
        this.undecodedChunk.readerIndex(readerIndex);
        throw new NotEnoughDataDecoderException();
    }
    
    private void readFileUploadByteMultipartStandard(final String s) throws NotEnoughDataDecoderException, ErrorDataDecoderException {
        final int readerIndex = this.undecodedChunk.readerIndex();
        int n = this.undecodedChunk.readerIndex();
        while (this.undecodedChunk.isReadable()) {
            final byte byte1 = this.undecodedChunk.readByte();
            if (byte1 == s.codePointAt(0)) {
                int n2 = 0;
                ++n2;
                if (s.length() == 0) {
                    break;
                }
                continue;
            }
            else if (byte1 == 13) {
                if (!this.undecodedChunk.isReadable()) {
                    continue;
                }
                if (this.undecodedChunk.readByte() == 10) {
                    n = this.undecodedChunk.readerIndex() - 2;
                }
                else {
                    n = this.undecodedChunk.readerIndex() - 1;
                    this.undecodedChunk.readerIndex(n);
                }
            }
            else if (byte1 == 10) {
                n = this.undecodedChunk.readerIndex() - 1;
            }
            else {
                n = this.undecodedChunk.readerIndex();
            }
        }
        this.currentFileUpload.addContent(this.undecodedChunk.copy(readerIndex, n - readerIndex), true);
        this.undecodedChunk.readerIndex(n);
    }
    
    private void readFileUploadByteMultipart(final String s) throws NotEnoughDataDecoderException, ErrorDataDecoderException {
        final HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
        final int readerIndex = this.undecodedChunk.readerIndex();
        int n = seekAheadOptimize.pos;
        while (seekAheadOptimize.pos < seekAheadOptimize.limit) {
            final byte b = seekAheadOptimize.bytes[seekAheadOptimize.pos++];
            if (b == s.codePointAt(0)) {
                int n2 = 0;
                ++n2;
                if (s.length() == 0) {
                    break;
                }
                continue;
            }
            else if (b == 13) {
                if (seekAheadOptimize.pos >= seekAheadOptimize.limit) {
                    continue;
                }
                if (seekAheadOptimize.bytes[seekAheadOptimize.pos++] == 10) {
                    n = seekAheadOptimize.pos - 2;
                }
                else {
                    final HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize2 = seekAheadOptimize;
                    --seekAheadOptimize2.pos;
                    n = seekAheadOptimize.pos;
                }
            }
            else if (b == 10) {
                n = seekAheadOptimize.pos - 1;
            }
            else {
                n = seekAheadOptimize.pos;
            }
        }
        final int readPosition = seekAheadOptimize.getReadPosition(n);
        this.currentFileUpload.addContent(this.undecodedChunk.copy(readerIndex, readPosition - readerIndex), true);
        this.undecodedChunk.readerIndex(readPosition);
    }
    
    private void loadFieldMultipartStandard(final String s) throws NotEnoughDataDecoderException, ErrorDataDecoderException {
        final int readerIndex = this.undecodedChunk.readerIndex();
        int n = this.undecodedChunk.readerIndex();
        while (this.undecodedChunk.isReadable()) {
            final byte byte1 = this.undecodedChunk.readByte();
            if (byte1 == s.codePointAt(0)) {
                int n2 = 0;
                ++n2;
                if (s.length() == 0) {
                    break;
                }
                continue;
            }
            else if (byte1 == 13) {
                if (this.undecodedChunk.isReadable()) {
                    if (this.undecodedChunk.readByte() == 10) {
                        n = this.undecodedChunk.readerIndex() - 2;
                    }
                    else {
                        n = this.undecodedChunk.readerIndex() - 1;
                        this.undecodedChunk.readerIndex(n);
                    }
                }
                else {
                    n = this.undecodedChunk.readerIndex() - 1;
                }
            }
            else if (byte1 == 10) {
                n = this.undecodedChunk.readerIndex() - 1;
            }
            else {
                n = this.undecodedChunk.readerIndex();
            }
        }
        this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, n - readerIndex), true);
        this.undecodedChunk.readerIndex(n);
    }
    
    private void loadFieldMultipart(final String s) throws NotEnoughDataDecoderException, ErrorDataDecoderException {
        final HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
        final int readerIndex = this.undecodedChunk.readerIndex();
        int n = seekAheadOptimize.pos;
        while (seekAheadOptimize.pos < seekAheadOptimize.limit) {
            final byte b = seekAheadOptimize.bytes[seekAheadOptimize.pos++];
            if (b == s.codePointAt(0)) {
                int n2 = 0;
                ++n2;
                if (s.length() == 0) {
                    break;
                }
                continue;
            }
            else if (b == 13) {
                if (seekAheadOptimize.pos >= seekAheadOptimize.limit) {
                    continue;
                }
                if (seekAheadOptimize.bytes[seekAheadOptimize.pos++] == 10) {
                    n = seekAheadOptimize.pos - 2;
                }
                else {
                    final HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize2 = seekAheadOptimize;
                    --seekAheadOptimize2.pos;
                    n = seekAheadOptimize.pos;
                }
            }
            else if (b == 10) {
                n = seekAheadOptimize.pos - 1;
            }
            else {
                n = seekAheadOptimize.pos;
            }
        }
        final int readPosition = seekAheadOptimize.getReadPosition(n);
        this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, readPosition - readerIndex), true);
        this.undecodedChunk.readerIndex(readPosition);
    }
    
    private static String cleanString(final String s) {
        final StringBuilder sb = new StringBuilder(s.length());
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 == ':') {
                sb.append(32);
            }
            else if (char1 == ',') {
                sb.append(32);
            }
            else if (char1 == '=') {
                sb.append(32);
            }
            else if (char1 == ';') {
                sb.append(32);
            }
            else if (char1 == '\t') {
                sb.append(32);
            }
            else if (char1 != '\"') {
                sb.append(char1);
            }
            int n = 0;
            ++n;
        }
        return sb.toString().trim();
    }
    
    private static String[] splitHeaderContentType(final String s) {
        final int nonWhitespace = HttpPostBodyUtil.findNonWhitespace(s, 0);
        int index = s.indexOf(59);
        if (index == -1) {
            return new String[] { s, "" };
        }
        if (s.charAt(index - 1) == ' ') {
            --index;
        }
        return new String[] { s.substring(nonWhitespace, index), s.substring(HttpPostBodyUtil.findNonWhitespace(s, index + 1), HttpPostBodyUtil.findEndOfString(s)) };
    }
    
    private static String[] splitMultipartHeader(final String s) {
        final ArrayList<String> list = new ArrayList<String>(1);
        int i;
        int n;
        for (n = (i = HttpPostBodyUtil.findNonWhitespace(s, 0)); i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 == ':') {
                break;
            }
            if (Character.isWhitespace(char1)) {
                break;
            }
        }
        int j;
        for (j = i; j < s.length(); ++j) {
            if (s.charAt(j) == ':') {
                ++j;
                break;
            }
        }
        final int nonWhitespace = HttpPostBodyUtil.findNonWhitespace(s, j);
        final int endOfString = HttpPostBodyUtil.findEndOfString(s);
        list.add(s.substring(n, i));
        final String substring = s.substring(nonWhitespace, endOfString);
        String[] array;
        if (substring.indexOf(59) >= 0) {
            array = StringUtil.split(substring, ';');
        }
        else {
            array = StringUtil.split(substring, ',');
        }
        int length = array.length;
        final String[] array2 = new String[list.size()];
        while (0 < list.size()) {
            array2[0] = list.get(0);
            ++length;
        }
        return array2;
    }
    
    public static class IncompatibleDataDecoderException extends DecoderException
    {
        private static final long serialVersionUID = -953268047926250267L;
        
        public IncompatibleDataDecoderException() {
        }
        
        public IncompatibleDataDecoderException(final String s) {
            super(s);
        }
        
        public IncompatibleDataDecoderException(final Throwable t) {
            super(t);
        }
        
        public IncompatibleDataDecoderException(final String s, final Throwable t) {
            super(s, t);
        }
    }
    
    public static class ErrorDataDecoderException extends DecoderException
    {
        private static final long serialVersionUID = 5020247425493164465L;
        
        public ErrorDataDecoderException() {
        }
        
        public ErrorDataDecoderException(final String s) {
            super(s);
        }
        
        public ErrorDataDecoderException(final Throwable t) {
            super(t);
        }
        
        public ErrorDataDecoderException(final String s, final Throwable t) {
            super(s, t);
        }
    }
    
    public static class NotEnoughDataDecoderException extends DecoderException
    {
        private static final long serialVersionUID = -7846841864603865638L;
        
        public NotEnoughDataDecoderException() {
        }
        
        public NotEnoughDataDecoderException(final String s) {
            super(s);
        }
        
        public NotEnoughDataDecoderException(final Throwable t) {
            super(t);
        }
        
        public NotEnoughDataDecoderException(final String s, final Throwable t) {
            super(s, t);
        }
    }
    
    private enum MultiPartStatus
    {
        NOTSTARTED("NOTSTARTED", 0), 
        PREAMBLE("PREAMBLE", 1), 
        HEADERDELIMITER("HEADERDELIMITER", 2), 
        DISPOSITION("DISPOSITION", 3), 
        FIELD("FIELD", 4), 
        FILEUPLOAD("FILEUPLOAD", 5), 
        MIXEDPREAMBLE("MIXEDPREAMBLE", 6), 
        MIXEDDELIMITER("MIXEDDELIMITER", 7), 
        MIXEDDISPOSITION("MIXEDDISPOSITION", 8), 
        MIXEDFILEUPLOAD("MIXEDFILEUPLOAD", 9), 
        MIXEDCLOSEDELIMITER("MIXEDCLOSEDELIMITER", 10), 
        CLOSEDELIMITER("CLOSEDELIMITER", 11), 
        PREEPILOGUE("PREEPILOGUE", 12), 
        EPILOGUE("EPILOGUE", 13);
        
        private static final MultiPartStatus[] $VALUES;
        
        private MultiPartStatus(final String s, final int n) {
        }
        
        static {
            $VALUES = new MultiPartStatus[] { MultiPartStatus.NOTSTARTED, MultiPartStatus.PREAMBLE, MultiPartStatus.HEADERDELIMITER, MultiPartStatus.DISPOSITION, MultiPartStatus.FIELD, MultiPartStatus.FILEUPLOAD, MultiPartStatus.MIXEDPREAMBLE, MultiPartStatus.MIXEDDELIMITER, MultiPartStatus.MIXEDDISPOSITION, MultiPartStatus.MIXEDFILEUPLOAD, MultiPartStatus.MIXEDCLOSEDELIMITER, MultiPartStatus.CLOSEDELIMITER, MultiPartStatus.PREEPILOGUE, MultiPartStatus.EPILOGUE };
        }
    }
    
    public static class EndOfDataDecoderException extends DecoderException
    {
        private static final long serialVersionUID = 1336267941020800769L;
    }
}
