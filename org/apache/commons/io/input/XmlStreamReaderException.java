package org.apache.commons.io.input;

import java.io.*;

public class XmlStreamReaderException extends IOException
{
    private static final long serialVersionUID = 1L;
    private final String bomEncoding;
    private final String xmlGuessEncoding;
    private final String xmlEncoding;
    private final String contentTypeMime;
    private final String contentTypeEncoding;
    
    public XmlStreamReaderException(final String s, final String s2, final String s3, final String s4) {
        this(s, null, null, s2, s3, s4);
    }
    
    public XmlStreamReaderException(final String s, final String contentTypeMime, final String contentTypeEncoding, final String bomEncoding, final String xmlGuessEncoding, final String xmlEncoding) {
        super(s);
        this.contentTypeMime = contentTypeMime;
        this.contentTypeEncoding = contentTypeEncoding;
        this.bomEncoding = bomEncoding;
        this.xmlGuessEncoding = xmlGuessEncoding;
        this.xmlEncoding = xmlEncoding;
    }
    
    public String getBomEncoding() {
        return this.bomEncoding;
    }
    
    public String getXmlGuessEncoding() {
        return this.xmlGuessEncoding;
    }
    
    public String getXmlEncoding() {
        return this.xmlEncoding;
    }
    
    public String getContentTypeMime() {
        return this.contentTypeMime;
    }
    
    public String getContentTypeEncoding() {
        return this.contentTypeEncoding;
    }
}
