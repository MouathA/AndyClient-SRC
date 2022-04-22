package org.apache.commons.io.input;

import org.apache.commons.io.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class XmlStreamReader extends Reader
{
    private static final int BUFFER_SIZE = 4096;
    private static final String UTF_8 = "UTF-8";
    private static final String US_ASCII = "US-ASCII";
    private static final String UTF_16BE = "UTF-16BE";
    private static final String UTF_16LE = "UTF-16LE";
    private static final String UTF_32BE = "UTF-32BE";
    private static final String UTF_32LE = "UTF-32LE";
    private static final String UTF_16 = "UTF-16";
    private static final String UTF_32 = "UTF-32";
    private static final String EBCDIC = "CP1047";
    private static final ByteOrderMark[] BOMS;
    private static final ByteOrderMark[] XML_GUESS_BYTES;
    private final Reader reader;
    private final String encoding;
    private final String defaultEncoding;
    private static final Pattern CHARSET_PATTERN;
    public static final Pattern ENCODING_PATTERN;
    private static final String RAW_EX_1 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch";
    private static final String RAW_EX_2 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] unknown BOM";
    private static final String HTTP_EX_1 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL";
    private static final String HTTP_EX_2 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch";
    private static final String HTTP_EX_3 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], Invalid MIME";
    
    public String getDefaultEncoding() {
        return this.defaultEncoding;
    }
    
    public XmlStreamReader(final File file) throws IOException {
        this(new FileInputStream(file));
    }
    
    public XmlStreamReader(final InputStream inputStream) throws IOException {
        this(inputStream, true);
    }
    
    public XmlStreamReader(final InputStream inputStream, final boolean b) throws IOException {
        this(inputStream, b, null);
    }
    
    public XmlStreamReader(final InputStream inputStream, final boolean b, final String defaultEncoding) throws IOException {
        this.defaultEncoding = defaultEncoding;
        final BOMInputStream bomInputStream = new BOMInputStream(new BufferedInputStream(inputStream, 4096), false, XmlStreamReader.BOMS);
        final BOMInputStream bomInputStream2 = new BOMInputStream(bomInputStream, true, XmlStreamReader.XML_GUESS_BYTES);
        this.encoding = this.doRawStream(bomInputStream, bomInputStream2, b);
        this.reader = new InputStreamReader(bomInputStream2, this.encoding);
    }
    
    public XmlStreamReader(final URL url) throws IOException {
        this(url.openConnection(), null);
    }
    
    public XmlStreamReader(final URLConnection urlConnection, final String defaultEncoding) throws IOException {
        this.defaultEncoding = defaultEncoding;
        final String contentType = urlConnection.getContentType();
        final BOMInputStream bomInputStream = new BOMInputStream(new BufferedInputStream(urlConnection.getInputStream(), 4096), false, XmlStreamReader.BOMS);
        final BOMInputStream bomInputStream2 = new BOMInputStream(bomInputStream, true, XmlStreamReader.XML_GUESS_BYTES);
        if (urlConnection instanceof HttpURLConnection || contentType != null) {
            this.encoding = this.doHttpStream(bomInputStream, bomInputStream2, contentType, true);
        }
        else {
            this.encoding = this.doRawStream(bomInputStream, bomInputStream2, true);
        }
        this.reader = new InputStreamReader(bomInputStream2, this.encoding);
    }
    
    public XmlStreamReader(final InputStream inputStream, final String s) throws IOException {
        this(inputStream, s, true);
    }
    
    public XmlStreamReader(final InputStream inputStream, final String s, final boolean b, final String defaultEncoding) throws IOException {
        this.defaultEncoding = defaultEncoding;
        final BOMInputStream bomInputStream = new BOMInputStream(new BufferedInputStream(inputStream, 4096), false, XmlStreamReader.BOMS);
        final BOMInputStream bomInputStream2 = new BOMInputStream(bomInputStream, true, XmlStreamReader.XML_GUESS_BYTES);
        this.encoding = this.doHttpStream(bomInputStream, bomInputStream2, s, b);
        this.reader = new InputStreamReader(bomInputStream2, this.encoding);
    }
    
    public XmlStreamReader(final InputStream inputStream, final String s, final boolean b) throws IOException {
        this(inputStream, s, b, null);
    }
    
    public String getEncoding() {
        return this.encoding;
    }
    
    @Override
    public int read(final char[] array, final int n, final int n2) throws IOException {
        return this.reader.read(array, n, n2);
    }
    
    @Override
    public void close() throws IOException {
        this.reader.close();
    }
    
    private String doRawStream(final BOMInputStream bomInputStream, final BOMInputStream bomInputStream2, final boolean b) throws IOException {
        final String bomCharsetName = bomInputStream.getBOMCharsetName();
        final String bomCharsetName2 = bomInputStream2.getBOMCharsetName();
        return this.calculateRawEncoding(bomCharsetName, bomCharsetName2, getXmlProlog(bomInputStream2, bomCharsetName2));
    }
    
    private String doHttpStream(final BOMInputStream bomInputStream, final BOMInputStream bomInputStream2, final String s, final boolean b) throws IOException {
        final String bomCharsetName = bomInputStream.getBOMCharsetName();
        final String bomCharsetName2 = bomInputStream2.getBOMCharsetName();
        return this.calculateHttpEncoding(s, bomCharsetName, bomCharsetName2, getXmlProlog(bomInputStream2, bomCharsetName2), b);
    }
    
    private String doLenientDetection(String s, final XmlStreamReaderException ex) throws IOException {
        if (s != null && s.startsWith("text/html")) {
            s = s.substring(9);
            s = "text/xml" + s;
            return this.calculateHttpEncoding(s, ex.getBomEncoding(), ex.getXmlGuessEncoding(), ex.getXmlEncoding(), true);
        }
        String s2 = ex.getXmlEncoding();
        if (s2 == null) {
            s2 = ex.getContentTypeEncoding();
        }
        if (s2 == null) {
            s2 = ((this.defaultEncoding == null) ? "UTF-8" : this.defaultEncoding);
        }
        return s2;
    }
    
    String calculateRawEncoding(final String s, final String s2, final String s3) throws IOException {
        if (s == null) {
            if (s2 == null || s3 == null) {
                return (this.defaultEncoding == null) ? "UTF-8" : this.defaultEncoding;
            }
            if (s3.equals("UTF-16") && (s2.equals("UTF-16BE") || s2.equals("UTF-16LE"))) {
                return s2;
            }
            return s3;
        }
        else if (s.equals("UTF-8")) {
            if (s2 != null && !s2.equals("UTF-8")) {
                throw new XmlStreamReaderException(MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", s, s2, s3), s, s2, s3);
            }
            if (s3 != null && !s3.equals("UTF-8")) {
                throw new XmlStreamReaderException(MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", s, s2, s3), s, s2, s3);
            }
            return s;
        }
        else if (s.equals("UTF-16BE") || s.equals("UTF-16LE")) {
            if (s2 != null && !s2.equals(s)) {
                throw new XmlStreamReaderException(MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", s, s2, s3), s, s2, s3);
            }
            if (s3 != null && !s3.equals("UTF-16") && !s3.equals(s)) {
                throw new XmlStreamReaderException(MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", s, s2, s3), s, s2, s3);
            }
            return s;
        }
        else {
            if (!s.equals("UTF-32BE") && !s.equals("UTF-32LE")) {
                throw new XmlStreamReaderException(MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] unknown BOM", s, s2, s3), s, s2, s3);
            }
            if (s2 != null && !s2.equals(s)) {
                throw new XmlStreamReaderException(MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", s, s2, s3), s, s2, s3);
            }
            if (s3 != null && !s3.equals("UTF-32") && !s3.equals(s)) {
                throw new XmlStreamReaderException(MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", s, s2, s3), s, s2, s3);
            }
            return s;
        }
    }
    
    String calculateHttpEncoding(final String s, final String s2, final String s3, final String s4, final boolean b) throws IOException {
        if (b && s4 != null) {
            return s4;
        }
        final String contentTypeMime = getContentTypeMime(s);
        final String contentTypeEncoding = getContentTypeEncoding(s);
        final boolean appXml = isAppXml(contentTypeMime);
        final boolean textXml = isTextXml(contentTypeMime);
        if (!appXml && !textXml) {
            throw new XmlStreamReaderException(MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], Invalid MIME", contentTypeMime, contentTypeEncoding, s2, s3, s4), contentTypeMime, contentTypeEncoding, s2, s3, s4);
        }
        if (contentTypeEncoding == null) {
            if (appXml) {
                return this.calculateRawEncoding(s2, s3, s4);
            }
            return (this.defaultEncoding == null) ? "US-ASCII" : this.defaultEncoding;
        }
        else if (contentTypeEncoding.equals("UTF-16BE") || contentTypeEncoding.equals("UTF-16LE")) {
            if (s2 != null) {
                throw new XmlStreamReaderException(MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL", contentTypeMime, contentTypeEncoding, s2, s3, s4), contentTypeMime, contentTypeEncoding, s2, s3, s4);
            }
            return contentTypeEncoding;
        }
        else if (contentTypeEncoding.equals("UTF-16")) {
            if (s2 != null && s2.startsWith("UTF-16")) {
                return s2;
            }
            throw new XmlStreamReaderException(MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch", contentTypeMime, contentTypeEncoding, s2, s3, s4), contentTypeMime, contentTypeEncoding, s2, s3, s4);
        }
        else if (contentTypeEncoding.equals("UTF-32BE") || contentTypeEncoding.equals("UTF-32LE")) {
            if (s2 != null) {
                throw new XmlStreamReaderException(MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL", contentTypeMime, contentTypeEncoding, s2, s3, s4), contentTypeMime, contentTypeEncoding, s2, s3, s4);
            }
            return contentTypeEncoding;
        }
        else {
            if (!contentTypeEncoding.equals("UTF-32")) {
                return contentTypeEncoding;
            }
            if (s2 != null && s2.startsWith("UTF-32")) {
                return s2;
            }
            throw new XmlStreamReaderException(MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch", contentTypeMime, contentTypeEncoding, s2, s3, s4), contentTypeMime, contentTypeEncoding, s2, s3, s4);
        }
    }
    
    static String getContentTypeMime(final String s) {
        String trim = null;
        if (s != null) {
            final int index = s.indexOf(";");
            String substring;
            if (index >= 0) {
                substring = s.substring(0, index);
            }
            else {
                substring = s;
            }
            trim = substring.trim();
        }
        return trim;
    }
    
    static String getContentTypeEncoding(final String s) {
        String s2 = null;
        if (s != null) {
            final int index = s.indexOf(";");
            if (index > -1) {
                final Matcher matcher = XmlStreamReader.CHARSET_PATTERN.matcher(s.substring(index + 1));
                final String s3 = matcher.find() ? matcher.group(1) : null;
                s2 = ((s3 != null) ? s3.toUpperCase(Locale.US) : null);
            }
        }
        return s2;
    }
    
    private static String getXmlProlog(final InputStream inputStream, final String s) throws IOException {
        final String s2 = null;
        if (s == null) {
            return s2;
        }
        final byte[] array = new byte[4096];
        inputStream.mark(4096);
        int i = inputStream.read(array, 0, 4096);
        while (i != -1) {
            i = inputStream.read(array, 0, 4096);
            new String(array, 0, 0, s).indexOf(62);
        }
        if (i == -1) {
            throw new IOException("Unexpected end of XML stream");
        }
        throw new IOException("XML prolog or ROOT element not found on first " + 0 + " bytes");
    }
    
    static boolean isAppXml(final String s) {
        return s != null && (s.equals("application/xml") || s.equals("application/xml-dtd") || s.equals("application/xml-external-parsed-entity") || (s.startsWith("application/") && s.endsWith("+xml")));
    }
    
    static boolean isTextXml(final String s) {
        return s != null && (s.equals("text/xml") || s.equals("text/xml-external-parsed-entity") || (s.startsWith("text/") && s.endsWith("+xml")));
    }
    
    static {
        BOMS = new ByteOrderMark[] { ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32LE };
        XML_GUESS_BYTES = new ByteOrderMark[] { new ByteOrderMark("UTF-8", new int[] { 60, 63, 120, 109 }), new ByteOrderMark("UTF-16BE", new int[] { 0, 60, 0, 63 }), new ByteOrderMark("UTF-16LE", new int[] { 60, 0, 63, 0 }), new ByteOrderMark("UTF-32BE", new int[] { 0, 0, 0, 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109 }), new ByteOrderMark("UTF-32LE", new int[] { 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109, 0, 0, 0 }), new ByteOrderMark("CP1047", new int[] { 76, 111, 167, 148 }) };
        CHARSET_PATTERN = Pattern.compile("charset=[\"']?([.[^; \"']]*)[\"']?");
        ENCODING_PATTERN = Pattern.compile("<\\?xml.*encoding[\\s]*=[\\s]*((?:\".[^\"]*\")|(?:'.[^']*'))", 8);
    }
}
