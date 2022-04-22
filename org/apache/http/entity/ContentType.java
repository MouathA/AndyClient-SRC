package org.apache.http.entity;

import java.io.*;
import org.apache.http.annotation.*;
import java.nio.charset.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.http.message.*;
import org.apache.http.*;

@Immutable
public final class ContentType implements Serializable
{
    private static final long serialVersionUID = -7768694718232371896L;
    public static final ContentType APPLICATION_ATOM_XML;
    public static final ContentType APPLICATION_FORM_URLENCODED;
    public static final ContentType APPLICATION_JSON;
    public static final ContentType APPLICATION_OCTET_STREAM;
    public static final ContentType APPLICATION_SVG_XML;
    public static final ContentType APPLICATION_XHTML_XML;
    public static final ContentType APPLICATION_XML;
    public static final ContentType MULTIPART_FORM_DATA;
    public static final ContentType TEXT_HTML;
    public static final ContentType TEXT_PLAIN;
    public static final ContentType TEXT_XML;
    public static final ContentType WILDCARD;
    public static final ContentType DEFAULT_TEXT;
    public static final ContentType DEFAULT_BINARY;
    private final String mimeType;
    private final Charset charset;
    private final NameValuePair[] params;
    
    ContentType(final String mimeType, final Charset charset) {
        this.mimeType = mimeType;
        this.charset = charset;
        this.params = null;
    }
    
    ContentType(final String mimeType, final NameValuePair[] params) throws UnsupportedCharsetException {
        this.mimeType = mimeType;
        this.params = params;
        final String parameter = this.getParameter("charset");
        this.charset = (TextUtils.isBlank(parameter) ? null : Charset.forName(parameter));
    }
    
    public String getMimeType() {
        return this.mimeType;
    }
    
    public Charset getCharset() {
        return this.charset;
    }
    
    public String getParameter(final String s) {
        Args.notEmpty(s, "Parameter name");
        if (this.params == null) {
            return null;
        }
        final NameValuePair[] params = this.params;
        while (0 < params.length) {
            final NameValuePair nameValuePair = params[0];
            if (nameValuePair.getName().equalsIgnoreCase(s)) {
                return nameValuePair.getValue();
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    @Override
    public String toString() {
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        charArrayBuffer.append(this.mimeType);
        if (this.params != null) {
            charArrayBuffer.append("; ");
            BasicHeaderValueFormatter.INSTANCE.formatParameters(charArrayBuffer, this.params, false);
        }
        else if (this.charset != null) {
            charArrayBuffer.append("; charset=");
            charArrayBuffer.append(this.charset.name());
        }
        return charArrayBuffer.toString();
    }
    
    private static boolean valid(final String s) {
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 == '\"' || char1 == ',' || char1 == ';') {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static ContentType create(final String s, final Charset charset) {
        final String lowerCase = ((String)Args.notBlank(s, "MIME type")).toLowerCase(Locale.US);
        Args.check(valid(lowerCase), "MIME type may not contain reserved characters");
        return new ContentType(lowerCase, charset);
    }
    
    public static ContentType create(final String s) {
        return new ContentType(s, (Charset)null);
    }
    
    public static ContentType create(final String s, final String s2) throws UnsupportedCharsetException {
        return create(s, TextUtils.isBlank(s2) ? null : Charset.forName(s2));
    }
    
    private static ContentType create(final HeaderElement headerElement) {
        final String name = headerElement.getName();
        final NameValuePair[] parameters = headerElement.getParameters();
        return new ContentType(name, (NameValuePair[])((parameters != null && parameters.length > 0) ? parameters : null));
    }
    
    public static ContentType parse(final String s) throws ParseException, UnsupportedCharsetException {
        Args.notNull(s, "Content type");
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(s.length());
        charArrayBuffer.append(s);
        final HeaderElement[] elements = BasicHeaderValueParser.INSTANCE.parseElements(charArrayBuffer, new ParserCursor(0, s.length()));
        if (elements.length > 0) {
            return create(elements[0]);
        }
        throw new ParseException("Invalid content type: " + s);
    }
    
    public static ContentType get(final HttpEntity httpEntity) throws ParseException, UnsupportedCharsetException {
        if (httpEntity == null) {
            return null;
        }
        final Header contentType = httpEntity.getContentType();
        if (contentType != null) {
            final HeaderElement[] elements = contentType.getElements();
            if (elements.length > 0) {
                return create(elements[0]);
            }
        }
        return null;
    }
    
    public static ContentType getOrDefault(final HttpEntity httpEntity) throws ParseException, UnsupportedCharsetException {
        final ContentType value = get(httpEntity);
        return (value != null) ? value : ContentType.DEFAULT_TEXT;
    }
    
    public ContentType withCharset(final Charset charset) {
        return create(this.getMimeType(), charset);
    }
    
    public ContentType withCharset(final String s) {
        return create(this.getMimeType(), s);
    }
    
    static {
        APPLICATION_ATOM_XML = create("application/atom+xml", Consts.ISO_8859_1);
        APPLICATION_FORM_URLENCODED = create("application/x-www-form-urlencoded", Consts.ISO_8859_1);
        APPLICATION_JSON = create("application/json", Consts.UTF_8);
        APPLICATION_OCTET_STREAM = create("application/octet-stream", (Charset)null);
        APPLICATION_SVG_XML = create("application/svg+xml", Consts.ISO_8859_1);
        APPLICATION_XHTML_XML = create("application/xhtml+xml", Consts.ISO_8859_1);
        APPLICATION_XML = create("application/xml", Consts.ISO_8859_1);
        MULTIPART_FORM_DATA = create("multipart/form-data", Consts.ISO_8859_1);
        TEXT_HTML = create("text/html", Consts.ISO_8859_1);
        TEXT_PLAIN = create("text/plain", Consts.ISO_8859_1);
        TEXT_XML = create("text/xml", Consts.ISO_8859_1);
        WILDCARD = create("*/*", (Charset)null);
        DEFAULT_TEXT = ContentType.TEXT_PLAIN;
        DEFAULT_BINARY = ContentType.APPLICATION_OCTET_STREAM;
    }
}
