package org.apache.http.client.utils;

import org.apache.http.annotation.*;
import java.net.*;
import org.apache.http.entity.*;
import org.apache.http.protocol.*;
import java.nio.charset.*;
import java.io.*;
import org.apache.http.util.*;
import org.apache.http.message.*;
import org.apache.http.*;
import java.util.*;
import java.nio.*;

@Immutable
public class URLEncodedUtils
{
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final char QP_SEP_A = '&';
    private static final char QP_SEP_S = ';';
    private static final String NAME_VALUE_SEPARATOR = "=";
    private static final char[] QP_SEPS;
    private static final String QP_SEP_PATTERN;
    private static final BitSet UNRESERVED;
    private static final BitSet PUNCT;
    private static final BitSet USERINFO;
    private static final BitSet PATHSAFE;
    private static final BitSet URIC;
    private static final BitSet RESERVED;
    private static final BitSet URLENCODER;
    private static final int RADIX = 16;
    
    public static List parse(final URI uri, final String s) {
        final String rawQuery = uri.getRawQuery();
        if (rawQuery != null && rawQuery.length() > 0) {
            final ArrayList list = new ArrayList();
            parse(list, new Scanner(rawQuery), URLEncodedUtils.QP_SEP_PATTERN, s);
            return list;
        }
        return Collections.emptyList();
    }
    
    public static List parse(final HttpEntity httpEntity) throws IOException {
        final ContentType value = ContentType.get(httpEntity);
        if (value != null && value.getMimeType().equalsIgnoreCase("application/x-www-form-urlencoded")) {
            final String string = EntityUtils.toString(httpEntity, Consts.ASCII);
            if (string != null && string.length() > 0) {
                Charset charset = value.getCharset();
                if (charset == null) {
                    charset = HTTP.DEF_CONTENT_CHARSET;
                }
                return parse(string, charset, URLEncodedUtils.QP_SEPS);
            }
        }
        return Collections.emptyList();
    }
    
    public static boolean isEncoded(final HttpEntity httpEntity) {
        final Header contentType = httpEntity.getContentType();
        if (contentType != null) {
            final HeaderElement[] elements = contentType.getElements();
            if (elements.length > 0) {
                return elements[0].getName().equalsIgnoreCase("application/x-www-form-urlencoded");
            }
        }
        return false;
    }
    
    public static void parse(final List list, final Scanner scanner, final String s) {
        parse(list, scanner, URLEncodedUtils.QP_SEP_PATTERN, s);
    }
    
    public static void parse(final List list, final Scanner scanner, final String s, final String s2) {
        scanner.useDelimiter(s);
        while (scanner.hasNext()) {
            String decodeFormFields = null;
            final String next = scanner.next();
            final int index = next.indexOf("=");
            String s3;
            if (index != -1) {
                s3 = decodeFormFields(next.substring(0, index).trim(), s2);
                decodeFormFields = decodeFormFields(next.substring(index + 1).trim(), s2);
            }
            else {
                s3 = decodeFormFields(next.trim(), s2);
            }
            list.add(new BasicNameValuePair(s3, decodeFormFields));
        }
    }
    
    public static List parse(final String s, final Charset charset) {
        return parse(s, charset, URLEncodedUtils.QP_SEPS);
    }
    
    public static List parse(final String s, final Charset charset, final char... array) {
        if (s == null) {
            return Collections.emptyList();
        }
        final BasicHeaderValueParser instance = BasicHeaderValueParser.INSTANCE;
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(s.length());
        charArrayBuffer.append(s);
        final ParserCursor parserCursor = new ParserCursor(0, charArrayBuffer.length());
        final ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        while (!parserCursor.atEnd()) {
            final NameValuePair nameValuePair = instance.parseNameValuePair(charArrayBuffer, parserCursor, array);
            if (nameValuePair.getName().length() > 0) {
                list.add(new BasicNameValuePair(decodeFormFields(nameValuePair.getName(), charset), decodeFormFields(nameValuePair.getValue(), charset)));
            }
        }
        return list;
    }
    
    public static String format(final List list, final String s) {
        return format(list, '&', s);
    }
    
    public static String format(final List list, final char c, final String s) {
        final StringBuilder sb = new StringBuilder();
        for (final NameValuePair nameValuePair : list) {
            final String encodeFormFields = encodeFormFields(nameValuePair.getName(), s);
            final String encodeFormFields2 = encodeFormFields(nameValuePair.getValue(), s);
            if (sb.length() > 0) {
                sb.append(c);
            }
            sb.append(encodeFormFields);
            if (encodeFormFields2 != null) {
                sb.append("=");
                sb.append(encodeFormFields2);
            }
        }
        return sb.toString();
    }
    
    public static String format(final Iterable iterable, final Charset charset) {
        return format(iterable, '&', charset);
    }
    
    public static String format(final Iterable iterable, final char c, final Charset charset) {
        final StringBuilder sb = new StringBuilder();
        for (final NameValuePair nameValuePair : iterable) {
            final String encodeFormFields = encodeFormFields(nameValuePair.getName(), charset);
            final String encodeFormFields2 = encodeFormFields(nameValuePair.getValue(), charset);
            if (sb.length() > 0) {
                sb.append(c);
            }
            sb.append(encodeFormFields);
            if (encodeFormFields2 != null) {
                sb.append("=");
                sb.append(encodeFormFields2);
            }
        }
        return sb.toString();
    }
    
    private static String urlEncode(final String s, final Charset charset, final BitSet set, final boolean b) {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        final ByteBuffer encode = charset.encode(s);
        while (encode.hasRemaining()) {
            final int n = encode.get() & 0xFF;
            if (set.get(n)) {
                sb.append((char)n);
            }
            else if (b && n == 32) {
                sb.append('+');
            }
            else {
                sb.append("%");
                final char upperCase = Character.toUpperCase(Character.forDigit(n >> 4 & 0xF, 16));
                final char upperCase2 = Character.toUpperCase(Character.forDigit(n & 0xF, 16));
                sb.append(upperCase);
                sb.append(upperCase2);
            }
        }
        return sb.toString();
    }
    
    private static String urlDecode(final String s, final Charset charset, final boolean b) {
        if (s == null) {
            return null;
        }
        final ByteBuffer allocate = ByteBuffer.allocate(s.length());
        final CharBuffer wrap = CharBuffer.wrap(s);
        while (wrap.hasRemaining()) {
            final char value = wrap.get();
            if (value == '%' && wrap.remaining() >= 2) {
                final char value2 = wrap.get();
                final char value3 = wrap.get();
                final int digit = Character.digit(value2, 16);
                final int digit2 = Character.digit(value3, 16);
                if (digit != -1 && digit2 != -1) {
                    allocate.put((byte)((digit << 4) + digit2));
                }
                else {
                    allocate.put((byte)37);
                    allocate.put((byte)value2);
                    allocate.put((byte)value3);
                }
            }
            else if (b && value == '+') {
                allocate.put((byte)32);
            }
            else {
                allocate.put((byte)value);
            }
        }
        allocate.flip();
        return charset.decode(allocate).toString();
    }
    
    private static String decodeFormFields(final String s, final String s2) {
        if (s == null) {
            return null;
        }
        return urlDecode(s, (s2 != null) ? Charset.forName(s2) : Consts.UTF_8, true);
    }
    
    private static String decodeFormFields(final String s, final Charset charset) {
        if (s == null) {
            return null;
        }
        return urlDecode(s, (charset != null) ? charset : Consts.UTF_8, true);
    }
    
    private static String encodeFormFields(final String s, final String s2) {
        if (s == null) {
            return null;
        }
        return urlEncode(s, (s2 != null) ? Charset.forName(s2) : Consts.UTF_8, URLEncodedUtils.URLENCODER, true);
    }
    
    private static String encodeFormFields(final String s, final Charset charset) {
        if (s == null) {
            return null;
        }
        return urlEncode(s, (charset != null) ? charset : Consts.UTF_8, URLEncodedUtils.URLENCODER, true);
    }
    
    static String encUserInfo(final String s, final Charset charset) {
        return urlEncode(s, charset, URLEncodedUtils.USERINFO, false);
    }
    
    static String encUric(final String s, final Charset charset) {
        return urlEncode(s, charset, URLEncodedUtils.URIC, false);
    }
    
    static String encPath(final String s, final Charset charset) {
        return urlEncode(s, charset, URLEncodedUtils.PATHSAFE, false);
    }
    
    static {
        QP_SEPS = new char[] { '&', ';' };
        QP_SEP_PATTERN = "[" + new String(URLEncodedUtils.QP_SEPS) + "]";
        UNRESERVED = new BitSet(256);
        PUNCT = new BitSet(256);
        USERINFO = new BitSet(256);
        PATHSAFE = new BitSet(256);
        URIC = new BitSet(256);
        RESERVED = new BitSet(256);
        URLENCODER = new BitSet(256);
        while (true) {
            URLEncodedUtils.UNRESERVED.set(48);
            int n = 0;
            ++n;
        }
    }
}
