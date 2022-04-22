package net.minecraft.util;

import java.security.cert.*;
import java.util.concurrent.atomic.*;
import java.util.zip.*;
import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.util.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.concurrent.*;
import java.nio.charset.*;
import java.nio.*;

public class DamageEffectFX
{
    public static final String CHARSET_UTF8;
    public static final String CONTENT_TYPE_FORM;
    public static final String CONTENT_TYPE_JSON;
    public static final String ENCODING_GZIP;
    public static final String HEADER_ACCEPT;
    public static final String HEADER_ACCEPT_CHARSET;
    public static final String HEADER_ACCEPT_ENCODING;
    public static final String HEADER_AUTHORIZATION;
    public static final String HEADER_CACHE_CONTROL;
    public static final String HEADER_CONTENT_ENCODING;
    public static final String HEADER_CONTENT_LENGTH;
    public static final String HEADER_CONTENT_TYPE;
    public static final String HEADER_DATE;
    public static final String HEADER_ETAG;
    public static final String HEADER_EXPIRES;
    public static final String HEADER_IF_NONE_MATCH;
    public static final String HEADER_LAST_MODIFIED;
    public static final String HEADER_LOCATION;
    public static final String HEADER_PROXY_AUTHORIZATION;
    public static final String HEADER_REFERER;
    public static final String HEADER_SERVER;
    public static final String HEADER_USER_AGENT;
    public static final String METHOD_DELETE;
    public static final String METHOD_GET;
    public static final String METHOD_HEAD;
    public static final String METHOD_OPTIONS;
    public static final String METHOD_POST;
    public static final String METHOD_PATCH;
    public static final String METHOD_PUT;
    public static final String METHOD_TRACE;
    public static final String PARAM_CHARSET;
    private static final String BOUNDARY;
    private static final String CONTENT_TYPE_MULTIPART;
    private static final String CRLF;
    private static final String[] EMPTY_STRINGS;
    private static SSLSocketFactory TRUSTED_FACTORY;
    private static HostnameVerifier TRUSTED_VERIFIER;
    private static ConnectionFactory CONNECTION_FACTORY;
    private HttpURLConnection connection;
    private final URL url;
    private final String requestMethod;
    private RequestOutputStream output;
    private boolean multipart;
    private boolean form;
    private boolean ignoreCloseExceptions;
    private boolean uncompress;
    private int bufferSize;
    private long totalSize;
    private long totalWritten;
    private String httpProxyHost;
    private int httpProxyPort;
    private UploadProgress progress;
    private static final String[] llIIIIllllllIlI;
    private static String[] llIIIlIIIIIlIlI;
    
    static {
        lIIlIIIIIlIlIlII();
        lIIlIIIIIlIlIIll();
        METHOD_PATCH = DamageEffectFX.llIIIIllllllIlI[0];
        HEADER_CONTENT_ENCODING = DamageEffectFX.llIIIIllllllIlI[1];
        HEADER_LOCATION = DamageEffectFX.llIIIIllllllIlI[2];
        HEADER_CACHE_CONTROL = DamageEffectFX.llIIIIllllllIlI[3];
        CONTENT_TYPE_FORM = DamageEffectFX.llIIIIllllllIlI[4];
        ENCODING_GZIP = DamageEffectFX.llIIIIllllllIlI[5];
        METHOD_TRACE = DamageEffectFX.llIIIIllllllIlI[6];
        METHOD_DELETE = DamageEffectFX.llIIIIllllllIlI[7];
        METHOD_GET = DamageEffectFX.llIIIIllllllIlI[8];
        HEADER_CONTENT_TYPE = DamageEffectFX.llIIIIllllllIlI[9];
        METHOD_PUT = DamageEffectFX.llIIIIllllllIlI[10];
        HEADER_DATE = DamageEffectFX.llIIIIllllllIlI[11];
        HEADER_AUTHORIZATION = DamageEffectFX.llIIIIllllllIlI[12];
        METHOD_POST = DamageEffectFX.llIIIIllllllIlI[13];
        HEADER_EXPIRES = DamageEffectFX.llIIIIllllllIlI[14];
        HEADER_ETAG = DamageEffectFX.llIIIIllllllIlI[15];
        HEADER_LAST_MODIFIED = DamageEffectFX.llIIIIllllllIlI[16];
        CRLF = DamageEffectFX.llIIIIllllllIlI[17];
        HEADER_USER_AGENT = DamageEffectFX.llIIIIllllllIlI[18];
        CONTENT_TYPE_MULTIPART = DamageEffectFX.llIIIIllllllIlI[19];
        PARAM_CHARSET = DamageEffectFX.llIIIIllllllIlI[20];
        HEADER_ACCEPT_ENCODING = DamageEffectFX.llIIIIllllllIlI[21];
        METHOD_HEAD = DamageEffectFX.llIIIIllllllIlI[22];
        BOUNDARY = DamageEffectFX.llIIIIllllllIlI[23];
        HEADER_ACCEPT = DamageEffectFX.llIIIIllllllIlI[24];
        CONTENT_TYPE_JSON = DamageEffectFX.llIIIIllllllIlI[25];
        HEADER_CONTENT_LENGTH = DamageEffectFX.llIIIIllllllIlI[26];
        HEADER_SERVER = DamageEffectFX.llIIIIllllllIlI[27];
        HEADER_ACCEPT_CHARSET = DamageEffectFX.llIIIIllllllIlI[28];
        HEADER_REFERER = DamageEffectFX.llIIIIllllllIlI[29];
        HEADER_IF_NONE_MATCH = DamageEffectFX.llIIIIllllllIlI[30];
        HEADER_PROXY_AUTHORIZATION = DamageEffectFX.llIIIIllllllIlI[31];
        CHARSET_UTF8 = DamageEffectFX.llIIIIllllllIlI[32];
        METHOD_OPTIONS = DamageEffectFX.llIIIIllllllIlI[33];
        EMPTY_STRINGS = new String[0];
        DamageEffectFX.CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
    }
    
    private static String getValidCharset(final String s) {
        if (s != null && s.length() > 0) {
            return s;
        }
        return DamageEffectFX.llIIIIllllllIlI[34];
    }
    
    private static SSLSocketFactory getTrustedFactory() throws HttpRequestException {
        if (DamageEffectFX.TRUSTED_FACTORY == null) {
            final TrustManager[] array = { new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    
                    @Override
                    public void checkClientTrusted(final X509Certificate[] array, final String s) {
                    }
                    
                    @Override
                    public void checkServerTrusted(final X509Certificate[] array, final String s) {
                    }
                } };
            try {
                final SSLContext instance = SSLContext.getInstance(DamageEffectFX.llIIIIllllllIlI[35]);
                instance.init(null, array, new SecureRandom());
                DamageEffectFX.TRUSTED_FACTORY = instance.getSocketFactory();
            }
            catch (GeneralSecurityException ex2) {
                final IOException ex = new IOException(DamageEffectFX.llIIIIllllllIlI[36]);
                ex.initCause(ex2);
                throw new HttpRequestException(ex);
            }
        }
        return DamageEffectFX.TRUSTED_FACTORY;
    }
    
    private static HostnameVerifier getTrustedVerifier() {
        if (DamageEffectFX.TRUSTED_VERIFIER == null) {
            DamageEffectFX.TRUSTED_VERIFIER = new HostnameVerifier() {
                @Override
                public boolean verify(final String s, final SSLSession sslSession) {
                    return true;
                }
            };
        }
        return DamageEffectFX.TRUSTED_VERIFIER;
    }
    
    private static StringBuilder addPathSeparator(final String s, final StringBuilder sb) {
        if (s.indexOf(58) + 2 == s.lastIndexOf(47)) {
            sb.append('/');
        }
        return sb;
    }
    
    private static StringBuilder addParamPrefix(final String s, final StringBuilder sb) {
        final int index = s.indexOf(63);
        final int n = sb.length() - 1;
        if (index == -1) {
            sb.append('?');
        }
        else if (index < n && s.charAt(n) != '&') {
            sb.append('&');
        }
        return sb;
    }
    
    private static StringBuilder addParam(final Object o, Object arrayToList, final StringBuilder sb) {
        if (arrayToList != null && arrayToList.getClass().isArray()) {
            arrayToList = arrayToList(arrayToList);
        }
        if (arrayToList instanceof Iterable) {
            final Iterator<Object> iterator = (Iterator<Object>)((Iterable)arrayToList).iterator();
            while (iterator.hasNext()) {
                sb.append(o);
                sb.append(DamageEffectFX.llIIIIllllllIlI[37]);
                final Object next = iterator.next();
                if (next != null) {
                    sb.append(next);
                }
                if (iterator.hasNext()) {
                    sb.append(DamageEffectFX.llIIIIllllllIlI[38]);
                }
            }
        }
        else {
            sb.append(o);
            sb.append(DamageEffectFX.llIIIIllllllIlI[39]);
            if (arrayToList != null) {
                sb.append(arrayToList);
            }
        }
        return sb;
    }
    
    public static void setConnectionFactory(final ConnectionFactory connection_FACTORY) {
        if (connection_FACTORY == null) {
            DamageEffectFX.CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
        }
        else {
            DamageEffectFX.CONNECTION_FACTORY = connection_FACTORY;
        }
    }
    
    private static List arrayToList(final Object o) {
        if (o instanceof Object[]) {
            return Arrays.asList((Object[])o);
        }
        final ArrayList<Integer> list = new ArrayList<Integer>();
        if (o instanceof int[]) {
            int[] array;
            for (int length = (array = (int[])o).length, i = 0; i < length; ++i) {
                list.add(array[i]);
            }
        }
        else if (o instanceof boolean[]) {
            boolean[] array2;
            for (int length2 = (array2 = (boolean[])o).length, j = 0; j < length2; ++j) {
                list.add((Integer)(Object)Boolean.valueOf(array2[j]));
            }
        }
        else if (o instanceof long[]) {
            long[] array3;
            for (int length3 = (array3 = (long[])o).length, k = 0; k < length3; ++k) {
                list.add((Integer)(Object)Long.valueOf(array3[k]));
            }
        }
        else if (o instanceof float[]) {
            float[] array4;
            for (int length4 = (array4 = (float[])o).length, l = 0; l < length4; ++l) {
                list.add((Integer)(Object)Float.valueOf(array4[l]));
            }
        }
        else if (o instanceof double[]) {
            double[] array5;
            for (int length5 = (array5 = (double[])o).length, n = 0; n < length5; ++n) {
                list.add((Integer)(Object)Double.valueOf(array5[n]));
            }
        }
        else if (o instanceof short[]) {
            short[] array6;
            for (int length6 = (array6 = (short[])o).length, n2 = 0; n2 < length6; ++n2) {
                list.add(Integer.valueOf(Short.valueOf(array6[n2])));
            }
        }
        else if (o instanceof byte[]) {
            byte[] array7;
            for (int length7 = (array7 = (byte[])o).length, n3 = 0; n3 < length7; ++n3) {
                list.add(Integer.valueOf(Byte.valueOf(array7[n3])));
            }
        }
        else if (o instanceof char[]) {
            char[] array8;
            for (int length8 = (array8 = (char[])o).length, n4 = 0; n4 < length8; ++n4) {
                list.add(Integer.valueOf(Character.valueOf(array8[n4])));
            }
        }
        return list;
    }
    
    public static String encode(final CharSequence charSequence) throws HttpRequestException {
        URL url;
        try {
            url = new URL(charSequence.toString());
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
        String s = url.getHost();
        final int port = url.getPort();
        if (port != -1) {
            s = String.valueOf(s) + ':' + Integer.toString(port);
        }
        try {
            String s2 = new URI(url.getProtocol(), s, url.getPath(), url.getQuery(), null).toASCIIString();
            final int index = s2.indexOf(63);
            if (index > 0 && index + 1 < s2.length()) {
                s2 = String.valueOf(s2.substring(0, index + 1)) + s2.substring(index + 1).replace(DamageEffectFX.llIIIIllllllIlI[40], DamageEffectFX.llIIIIllllllIlI[41]);
            }
            return s2;
        }
        catch (URISyntaxException ex3) {
            final IOException ex2 = new IOException(DamageEffectFX.llIIIIllllllIlI[42]);
            ex2.initCause(ex3);
            throw new HttpRequestException(ex2);
        }
    }
    
    public static String append(final CharSequence charSequence, final Map map) {
        final String string = charSequence.toString();
        if (map == null || map.isEmpty()) {
            return string;
        }
        final StringBuilder sb = new StringBuilder(string);
        addPathSeparator(string, sb);
        addParamPrefix(string, sb);
        final Iterator<Map.Entry<Object, V>> iterator = map.entrySet().iterator();
        final Map.Entry<Object, V> entry = iterator.next();
        addParam(entry.getKey().toString(), entry.getValue(), sb);
        while (iterator.hasNext()) {
            sb.append('&');
            final Map.Entry<Object, V> entry2 = iterator.next();
            addParam(entry2.getKey().toString(), entry2.getValue(), sb);
        }
        return sb.toString();
    }
    
    public static String append(final CharSequence charSequence, final Object... array) {
        final String string = charSequence.toString();
        if (array == null || array.length == 0) {
            return string;
        }
        if (array.length % 2 != 0) {
            throw new IllegalArgumentException(DamageEffectFX.llIIIIllllllIlI[43]);
        }
        final StringBuilder sb = new StringBuilder(string);
        addPathSeparator(string, sb);
        addParamPrefix(string, sb);
        addParam(array[0], array[1], sb);
        for (int i = 2; i < array.length; i += 2) {
            sb.append('&');
            addParam(array[i], array[i + 1], sb);
        }
        return sb.toString();
    }
    
    public static DamageEffectFX get(final CharSequence charSequence) throws HttpRequestException {
        return new DamageEffectFX(charSequence, DamageEffectFX.llIIIIllllllIlI[44]);
    }
    
    public static DamageEffectFX get(final URL url) throws HttpRequestException {
        return new DamageEffectFX(url, DamageEffectFX.llIIIIllllllIlI[45]);
    }
    
    public static DamageEffectFX get(final CharSequence charSequence, final Map map, final boolean b) {
        final String append = append(charSequence, map);
        return get(b ? encode(append) : append);
    }
    
    public static DamageEffectFX get(final CharSequence charSequence, final boolean b, final Object... array) {
        final String append = append(charSequence, array);
        return get(b ? encode(append) : append);
    }
    
    public static DamageEffectFX post(final CharSequence charSequence) throws HttpRequestException {
        return new DamageEffectFX(charSequence, DamageEffectFX.llIIIIllllllIlI[46]);
    }
    
    public static DamageEffectFX post(final URL url) throws HttpRequestException {
        return new DamageEffectFX(url, DamageEffectFX.llIIIIllllllIlI[47]);
    }
    
    public static DamageEffectFX post(final CharSequence charSequence, final Map map, final boolean b) {
        final String append = append(charSequence, map);
        return post(b ? encode(append) : append);
    }
    
    public static DamageEffectFX post(final CharSequence charSequence, final boolean b, final Object... array) {
        final String append = append(charSequence, array);
        return post(b ? encode(append) : append);
    }
    
    public static DamageEffectFX patch(final CharSequence charSequence) throws HttpRequestException {
        return new DamageEffectFX(charSequence, DamageEffectFX.llIIIIllllllIlI[48]);
    }
    
    public static DamageEffectFX put(final CharSequence charSequence) throws HttpRequestException {
        return new DamageEffectFX(charSequence, DamageEffectFX.llIIIIllllllIlI[49]);
    }
    
    public static DamageEffectFX put(final URL url) throws HttpRequestException {
        return new DamageEffectFX(url, DamageEffectFX.llIIIIllllllIlI[50]);
    }
    
    public static DamageEffectFX put(final CharSequence charSequence, final Map map, final boolean b) {
        final String append = append(charSequence, map);
        return put(b ? encode(append) : append);
    }
    
    public static DamageEffectFX put(final CharSequence charSequence, final boolean b, final Object... array) {
        final String append = append(charSequence, array);
        return put(b ? encode(append) : append);
    }
    
    public static DamageEffectFX delete(final CharSequence charSequence) throws HttpRequestException {
        return new DamageEffectFX(charSequence, DamageEffectFX.llIIIIllllllIlI[51]);
    }
    
    public static DamageEffectFX delete(final URL url) throws HttpRequestException {
        return new DamageEffectFX(url, DamageEffectFX.llIIIIllllllIlI[52]);
    }
    
    public static DamageEffectFX delete(final CharSequence charSequence, final Map map, final boolean b) {
        final String append = append(charSequence, map);
        return delete(b ? encode(append) : append);
    }
    
    public static DamageEffectFX delete(final CharSequence charSequence, final boolean b, final Object... array) {
        final String append = append(charSequence, array);
        return delete(b ? encode(append) : append);
    }
    
    public static DamageEffectFX head(final CharSequence charSequence) throws HttpRequestException {
        return new DamageEffectFX(charSequence, DamageEffectFX.llIIIIllllllIlI[53]);
    }
    
    public static DamageEffectFX head(final URL url) throws HttpRequestException {
        return new DamageEffectFX(url, DamageEffectFX.llIIIIllllllIlI[54]);
    }
    
    public static DamageEffectFX head(final CharSequence charSequence, final Map map, final boolean b) {
        final String append = append(charSequence, map);
        return head(b ? encode(append) : append);
    }
    
    public static DamageEffectFX head(final CharSequence charSequence, final boolean b, final Object... array) {
        final String append = append(charSequence, array);
        return head(b ? encode(append) : append);
    }
    
    public static DamageEffectFX options(final CharSequence charSequence) throws HttpRequestException {
        return new DamageEffectFX(charSequence, DamageEffectFX.llIIIIllllllIlI[55]);
    }
    
    public static DamageEffectFX options(final URL url) throws HttpRequestException {
        return new DamageEffectFX(url, DamageEffectFX.llIIIIllllllIlI[56]);
    }
    
    public static DamageEffectFX trace(final CharSequence charSequence) throws HttpRequestException {
        return new DamageEffectFX(charSequence, DamageEffectFX.llIIIIllllllIlI[57]);
    }
    
    public static DamageEffectFX trace(final URL url) throws HttpRequestException {
        return new DamageEffectFX(url, DamageEffectFX.llIIIIllllllIlI[58]);
    }
    
    public static void keepAlive(final boolean b) {
        setProperty(DamageEffectFX.llIIIIllllllIlI[59], Boolean.toString(b));
    }
    
    public static void maxConnections(final int n) {
        setProperty(DamageEffectFX.llIIIIllllllIlI[60], Integer.toString(n));
    }
    
    public static void proxyHost(final String s) {
        setProperty(DamageEffectFX.llIIIIllllllIlI[61], s);
        setProperty(DamageEffectFX.llIIIIllllllIlI[62], s);
    }
    
    public static void proxyPort(final int n) {
        final String string = Integer.toString(n);
        setProperty(DamageEffectFX.llIIIIllllllIlI[63], string);
        setProperty(DamageEffectFX.llIIIIllllllIlI[64], string);
    }
    
    public static void nonProxyHosts(final String... array) {
        if (array != null && array.length > 0) {
            final StringBuilder sb = new StringBuilder();
            final int n = array.length - 1;
            for (int i = 0; i < n; ++i) {
                sb.append(array[i]).append('|');
            }
            sb.append(array[n]);
            setProperty(DamageEffectFX.llIIIIllllllIlI[65], sb.toString());
        }
        else {
            setProperty(DamageEffectFX.llIIIIllllllIlI[66], null);
        }
    }
    
    private static String setProperty(final String s, final String s2) {
        PrivilegedAction privilegedAction;
        if (s2 != null) {
            privilegedAction = new PrivilegedAction(s2) {
                private final String val$name;
                private final String val$value;
                
                @Override
                public String run() {
                    return System.setProperty(this.val$name, this.val$value);
                }
                
                @Override
                public Object run() {
                    return this.run();
                }
            };
        }
        else {
            privilegedAction = new PrivilegedAction() {
                private final String val$name;
                
                @Override
                public String run() {
                    return System.clearProperty(this.val$name);
                }
                
                @Override
                public Object run() {
                    return this.run();
                }
            };
        }
        return AccessController.doPrivileged((PrivilegedAction<String>)privilegedAction);
    }
    
    public DamageEffectFX(final CharSequence charSequence, final String requestMethod) throws HttpRequestException {
        this.connection = null;
        this.ignoreCloseExceptions = true;
        this.uncompress = false;
        this.bufferSize = 8192;
        this.totalSize = -1L;
        this.totalWritten = 0L;
        this.progress = UploadProgress.DEFAULT;
        try {
            this.url = new URL(charSequence.toString());
        }
        catch (MalformedURLException ex) {
            throw new HttpRequestException(ex);
        }
        this.requestMethod = requestMethod;
    }
    
    public DamageEffectFX(final URL url, final String requestMethod) throws HttpRequestException {
        this.connection = null;
        this.ignoreCloseExceptions = true;
        this.uncompress = false;
        this.bufferSize = 8192;
        this.totalSize = -1L;
        this.totalWritten = 0L;
        this.progress = UploadProgress.DEFAULT;
        this.url = url;
        this.requestMethod = requestMethod;
    }
    
    private Proxy createProxy() {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.httpProxyHost, this.httpProxyPort));
    }
    
    private HttpURLConnection createConnection() {
        try {
            HttpURLConnection httpURLConnection;
            if (this.httpProxyHost != null) {
                httpURLConnection = DamageEffectFX.CONNECTION_FACTORY.create(this.url, this.createProxy());
            }
            else {
                httpURLConnection = DamageEffectFX.CONNECTION_FACTORY.create(this.url);
            }
            httpURLConnection.setRequestMethod(this.requestMethod);
            return httpURLConnection;
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.method()) + ' ' + this.url();
    }
    
    public HttpURLConnection getConnection() {
        if (this.connection == null) {
            this.connection = this.createConnection();
        }
        return this.connection;
    }
    
    public DamageEffectFX ignoreCloseExceptions(final boolean ignoreCloseExceptions) {
        this.ignoreCloseExceptions = ignoreCloseExceptions;
        return this;
    }
    
    public boolean ignoreCloseExceptions() {
        return this.ignoreCloseExceptions;
    }
    
    public int code() throws HttpRequestException {
        try {
            this.closeOutput();
            return this.getConnection().getResponseCode();
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
    }
    
    public DamageEffectFX code(final AtomicInteger atomicInteger) throws HttpRequestException {
        atomicInteger.set(this.code());
        return this;
    }
    
    public boolean ok() throws HttpRequestException {
        return 200 == this.code();
    }
    
    public boolean created() throws HttpRequestException {
        return 201 == this.code();
    }
    
    public boolean noContent() throws HttpRequestException {
        return 204 == this.code();
    }
    
    public boolean serverError() throws HttpRequestException {
        return 500 == this.code();
    }
    
    public boolean badRequest() throws HttpRequestException {
        return 400 == this.code();
    }
    
    public boolean notFound() throws HttpRequestException {
        return 404 == this.code();
    }
    
    public boolean notModified() throws HttpRequestException {
        return 304 == this.code();
    }
    
    public String message() throws HttpRequestException {
        try {
            this.closeOutput();
            return this.getConnection().getResponseMessage();
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
    }
    
    public DamageEffectFX disconnect() {
        this.getConnection().disconnect();
        return this;
    }
    
    public DamageEffectFX chunk(final int chunkedStreamingMode) {
        this.getConnection().setChunkedStreamingMode(chunkedStreamingMode);
        return this;
    }
    
    public DamageEffectFX bufferSize(final int bufferSize) {
        if (bufferSize < 1) {
            throw new IllegalArgumentException(DamageEffectFX.llIIIIllllllIlI[67]);
        }
        this.bufferSize = bufferSize;
        return this;
    }
    
    public int bufferSize() {
        return this.bufferSize;
    }
    
    public DamageEffectFX uncompress(final boolean uncompress) {
        this.uncompress = uncompress;
        return this;
    }
    
    protected ByteArrayOutputStream byteStream() {
        final int contentLength = this.contentLength();
        if (contentLength > 0) {
            return new ByteArrayOutputStream(contentLength);
        }
        return new ByteArrayOutputStream();
    }
    
    public String body(final String s) throws HttpRequestException {
        final ByteArrayOutputStream byteStream = this.byteStream();
        try {
            this.copy(this.buffer(), byteStream);
            return byteStream.toString(getValidCharset(s));
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
    }
    
    public String body() throws HttpRequestException {
        return this.body(this.charset());
    }
    
    public DamageEffectFX body(final AtomicReference atomicReference) throws HttpRequestException {
        atomicReference.set(this.body());
        return this;
    }
    
    public DamageEffectFX body(final AtomicReference atomicReference, final String s) throws HttpRequestException {
        atomicReference.set(this.body(s));
        return this;
    }
    
    public boolean isBodyEmpty() throws HttpRequestException {
        return this.contentLength() == 0;
    }
    
    public byte[] bytes() throws HttpRequestException {
        final ByteArrayOutputStream byteStream = this.byteStream();
        try {
            this.copy(this.buffer(), byteStream);
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
        return byteStream.toByteArray();
    }
    
    public BufferedInputStream buffer() throws HttpRequestException {
        return new BufferedInputStream(this.stream(), this.bufferSize);
    }
    
    public InputStream stream() throws HttpRequestException {
        InputStream inputStream = null;
        Label_0082: {
            if (this.code() < 400) {
                try {
                    inputStream = this.getConnection().getInputStream();
                    break Label_0082;
                }
                catch (IOException ex) {
                    throw new HttpRequestException(ex);
                }
            }
            inputStream = this.getConnection().getErrorStream();
            if (inputStream == null) {
                try {
                    inputStream = this.getConnection().getInputStream();
                }
                catch (IOException ex2) {
                    if (this.contentLength() > 0) {
                        throw new HttpRequestException(ex2);
                    }
                    inputStream = new ByteArrayInputStream(new byte[0]);
                }
            }
        }
        if (!this.uncompress || !DamageEffectFX.llIIIIllllllIlI[68].equals(this.contentEncoding())) {
            return inputStream;
        }
        try {
            return new GZIPInputStream(inputStream);
        }
        catch (IOException ex3) {
            throw new HttpRequestException(ex3);
        }
    }
    
    public InputStreamReader reader(final String s) throws HttpRequestException {
        try {
            return new InputStreamReader(this.stream(), getValidCharset(s));
        }
        catch (UnsupportedEncodingException ex) {
            throw new HttpRequestException(ex);
        }
    }
    
    public InputStreamReader reader() throws HttpRequestException {
        return this.reader(this.charset());
    }
    
    public BufferedReader bufferedReader(final String s) throws HttpRequestException {
        return new BufferedReader(this.reader(s), this.bufferSize);
    }
    
    public BufferedReader bufferedReader() throws HttpRequestException {
        return this.bufferedReader(this.charset());
    }
    
    public DamageEffectFX receive(final File file) throws HttpRequestException {
        BufferedOutputStream bufferedOutputStream;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file), this.bufferSize);
        }
        catch (FileNotFoundException ex) {
            throw new HttpRequestException(ex);
        }
        return (DamageEffectFX)new CloseOperation((Closeable)bufferedOutputStream, this.ignoreCloseExceptions, (OutputStream)bufferedOutputStream) {
            final DamageEffectFX this$0;
            private final OutputStream val$output;
            
            @Override
            protected DamageEffectFX run() throws HttpRequestException, IOException {
                return this.this$0.receive(this.val$output);
            }
            
            @Override
            protected Object run() throws HttpRequestException, IOException {
                return this.run();
            }
        }.call();
    }
    
    public DamageEffectFX receive(final OutputStream outputStream) throws HttpRequestException {
        try {
            return this.copy(this.buffer(), outputStream);
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
    }
    
    public DamageEffectFX receive(final PrintStream printStream) throws HttpRequestException {
        return this.receive((OutputStream)printStream);
    }
    
    public DamageEffectFX receive(final Appendable appendable) throws HttpRequestException {
        final BufferedReader bufferedReader = this.bufferedReader();
        return (DamageEffectFX)new CloseOperation((Closeable)bufferedReader, this.ignoreCloseExceptions, bufferedReader, appendable) {
            final DamageEffectFX this$0;
            private final BufferedReader val$reader;
            private final Appendable val$appendable;
            
            public DamageEffectFX run() throws IOException {
                final CharBuffer allocate = CharBuffer.allocate(DamageEffectFX.access$1(this.this$0));
                int read;
                while ((read = this.val$reader.read(allocate)) != -1) {
                    allocate.rewind();
                    this.val$appendable.append(allocate, 0, read);
                    allocate.rewind();
                }
                return this.this$0;
            }
            
            public Object run() throws HttpRequestException, IOException {
                return this.run();
            }
        }.call();
    }
    
    public DamageEffectFX receive(final Writer writer) throws HttpRequestException {
        final BufferedReader bufferedReader = this.bufferedReader();
        return (DamageEffectFX)new CloseOperation((Closeable)bufferedReader, this.ignoreCloseExceptions, bufferedReader, writer) {
            final DamageEffectFX this$0;
            private final BufferedReader val$reader;
            private final Writer val$writer;
            
            public DamageEffectFX run() throws IOException {
                return this.this$0.copy(this.val$reader, this.val$writer);
            }
            
            public Object run() throws HttpRequestException, IOException {
                return this.run();
            }
        }.call();
    }
    
    public DamageEffectFX readTimeout(final int readTimeout) {
        this.getConnection().setReadTimeout(readTimeout);
        return this;
    }
    
    public DamageEffectFX connectTimeout(final int connectTimeout) {
        this.getConnection().setConnectTimeout(connectTimeout);
        return this;
    }
    
    public DamageEffectFX header(final String s, final String s2) {
        this.getConnection().setRequestProperty(s, s2);
        return this;
    }
    
    public DamageEffectFX header(final String s, final Number n) {
        return this.header(s, (n != null) ? n.toString() : null);
    }
    
    public DamageEffectFX headers(final Map map) {
        if (!map.isEmpty()) {
            final Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                this.header(iterator.next());
            }
        }
        return this;
    }
    
    public DamageEffectFX header(final Map.Entry entry) {
        return this.header(entry.getKey(), (String)entry.getValue());
    }
    
    public String header(final String s) throws HttpRequestException {
        this.closeOutputQuietly();
        return this.getConnection().getHeaderField(s);
    }
    
    public Map headers() throws HttpRequestException {
        this.closeOutputQuietly();
        return this.getConnection().getHeaderFields();
    }
    
    public long dateHeader(final String s) throws HttpRequestException {
        return this.dateHeader(s, -1L);
    }
    
    public long dateHeader(final String s, final long n) throws HttpRequestException {
        this.closeOutputQuietly();
        return this.getConnection().getHeaderFieldDate(s, n);
    }
    
    public int intHeader(final String s) throws HttpRequestException {
        return this.intHeader(s, -1);
    }
    
    public int intHeader(final String s, final int n) throws HttpRequestException {
        this.closeOutputQuietly();
        return this.getConnection().getHeaderFieldInt(s, n);
    }
    
    public String[] headers(final String s) {
        final Map headers = this.headers();
        if (headers == null || headers.isEmpty()) {
            return DamageEffectFX.EMPTY_STRINGS;
        }
        final List list = headers.get(s);
        if (list != null && !list.isEmpty()) {
            return (String[])list.toArray(new String[list.size()]);
        }
        return DamageEffectFX.EMPTY_STRINGS;
    }
    
    public String parameter(final String s, final String s2) {
        return this.getParam(this.header(s), s2);
    }
    
    public Map parameters(final String s) {
        return this.getParams(this.header(s));
    }
    
    protected Map getParams(final String s) {
        if (s == null || s.length() == 0) {
            return Collections.emptyMap();
        }
        final int length = s.length();
        int i = s.indexOf(59) + 1;
        if (i == 0 || i == length) {
            return Collections.emptyMap();
        }
        int n = s.indexOf(59, i);
        if (n == -1) {
            n = length;
        }
        final LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        while (i < n) {
            final int index = s.indexOf(61, i);
            if (index != -1 && index < n) {
                final String trim = s.substring(i, index).trim();
                if (trim.length() > 0) {
                    final String trim2 = s.substring(index + 1, n).trim();
                    final int length2 = trim2.length();
                    if (length2 != 0) {
                        if (length2 > 2 && '\"' == trim2.charAt(0) && '\"' == trim2.charAt(length2 - 1)) {
                            linkedHashMap.put(trim, trim2.substring(1, length2 - 1));
                        }
                        else {
                            linkedHashMap.put(trim, trim2);
                        }
                    }
                }
            }
            i = n + 1;
            n = s.indexOf(59, i);
            if (n == -1) {
                n = length;
            }
        }
        return linkedHashMap;
    }
    
    protected String getParam(final String s, final String s2) {
        if (s == null || s.length() == 0) {
            return null;
        }
        final int length = s.length();
        int i = s.indexOf(59) + 1;
        if (i == 0 || i == length) {
            return null;
        }
        int n = s.indexOf(59, i);
        if (n == -1) {
            n = length;
        }
        while (i < n) {
            final int index = s.indexOf(61, i);
            if (index != -1 && index < n && s2.equals(s.substring(i, index).trim())) {
                final String trim = s.substring(index + 1, n).trim();
                final int length2 = trim.length();
                if (length2 != 0) {
                    if (length2 > 2 && '\"' == trim.charAt(0) && '\"' == trim.charAt(length2 - 1)) {
                        return trim.substring(1, length2 - 1);
                    }
                    return trim;
                }
            }
            i = n + 1;
            n = s.indexOf(59, i);
            if (n == -1) {
                n = length;
            }
        }
        return null;
    }
    
    public String charset() {
        return this.parameter(DamageEffectFX.llIIIIllllllIlI[69], DamageEffectFX.llIIIIllllllIlI[70]);
    }
    
    public DamageEffectFX userAgent(final String s) {
        return this.header(DamageEffectFX.llIIIIllllllIlI[71], s);
    }
    
    public DamageEffectFX referer(final String s) {
        return this.header(DamageEffectFX.llIIIIllllllIlI[72], s);
    }
    
    public DamageEffectFX useCaches(final boolean useCaches) {
        this.getConnection().setUseCaches(useCaches);
        return this;
    }
    
    public DamageEffectFX acceptEncoding(final String s) {
        return this.header(DamageEffectFX.llIIIIllllllIlI[73], s);
    }
    
    public DamageEffectFX acceptGzipEncoding() {
        return this.acceptEncoding(DamageEffectFX.llIIIIllllllIlI[74]);
    }
    
    public DamageEffectFX acceptCharset(final String s) {
        return this.header(DamageEffectFX.llIIIIllllllIlI[75], s);
    }
    
    public String contentEncoding() {
        return this.header(DamageEffectFX.llIIIIllllllIlI[76]);
    }
    
    public String server() {
        return this.header(DamageEffectFX.llIIIIllllllIlI[77]);
    }
    
    public long date() {
        return this.dateHeader(DamageEffectFX.llIIIIllllllIlI[78]);
    }
    
    public String cacheControl() {
        return this.header(DamageEffectFX.llIIIIllllllIlI[79]);
    }
    
    public String eTag() {
        return this.header(DamageEffectFX.llIIIIllllllIlI[80]);
    }
    
    public long expires() {
        return this.dateHeader(DamageEffectFX.llIIIIllllllIlI[81]);
    }
    
    public long lastModified() {
        return this.dateHeader(DamageEffectFX.llIIIIllllllIlI[82]);
    }
    
    public String location() {
        return this.header(DamageEffectFX.llIIIIllllllIlI[83]);
    }
    
    public DamageEffectFX authorization(final String s) {
        return this.header(DamageEffectFX.llIIIIllllllIlI[84], s);
    }
    
    public DamageEffectFX proxyAuthorization(final String s) {
        return this.header(DamageEffectFX.llIIIIllllllIlI[85], s);
    }
    
    public DamageEffectFX basic(final String s, final String s2) {
        return this.authorization(DamageEffectFX.llIIIIllllllIlI[86] + Base64.encode(String.valueOf(s) + ':' + s2));
    }
    
    public DamageEffectFX proxyBasic(final String s, final String s2) {
        return this.proxyAuthorization(DamageEffectFX.llIIIIllllllIlI[87] + Base64.encode(String.valueOf(s) + ':' + s2));
    }
    
    public DamageEffectFX ifModifiedSince(final long ifModifiedSince) {
        this.getConnection().setIfModifiedSince(ifModifiedSince);
        return this;
    }
    
    public DamageEffectFX ifNoneMatch(final String s) {
        return this.header(DamageEffectFX.llIIIIllllllIlI[88], s);
    }
    
    public DamageEffectFX contentType(final String s) {
        return this.contentType(s, null);
    }
    
    public DamageEffectFX contentType(final String s, final String s2) {
        if (s2 != null && s2.length() > 0) {
            final String s3 = DamageEffectFX.llIIIIllllllIlI[89];
            return this.header(DamageEffectFX.llIIIIllllllIlI[90], String.valueOf(s) + DamageEffectFX.llIIIIllllllIlI[91] + s2);
        }
        return this.header(DamageEffectFX.llIIIIllllllIlI[92], s);
    }
    
    public String contentType() {
        return this.header(DamageEffectFX.llIIIIllllllIlI[93]);
    }
    
    public int contentLength() {
        return this.intHeader(DamageEffectFX.llIIIIllllllIlI[94]);
    }
    
    public DamageEffectFX contentLength(final String s) {
        return this.contentLength(Integer.parseInt(s));
    }
    
    public DamageEffectFX contentLength(final int fixedLengthStreamingMode) {
        this.getConnection().setFixedLengthStreamingMode(fixedLengthStreamingMode);
        return this;
    }
    
    public DamageEffectFX accept(final String s) {
        return this.header(DamageEffectFX.llIIIIllllllIlI[95], s);
    }
    
    public DamageEffectFX acceptJson() {
        return this.accept(DamageEffectFX.llIIIIllllllIlI[96]);
    }
    
    protected DamageEffectFX copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        return (DamageEffectFX)new CloseOperation((Closeable)inputStream, this.ignoreCloseExceptions, inputStream, outputStream) {
            final DamageEffectFX this$0;
            private final InputStream val$input;
            private final OutputStream val$output;
            
            public DamageEffectFX run() throws IOException {
                final byte[] array = new byte[DamageEffectFX.access$1(this.this$0)];
                int read;
                while ((read = this.val$input.read(array)) != -1) {
                    this.val$output.write(array, 0, read);
                    final DamageEffectFX this$0 = this.this$0;
                    DamageEffectFX.access$3(this$0, DamageEffectFX.access$2(this$0) + read);
                    DamageEffectFX.access$4(this.this$0).onUpload(DamageEffectFX.access$2(this.this$0), DamageEffectFX.access$5(this.this$0));
                }
                return this.this$0;
            }
            
            public Object run() throws HttpRequestException, IOException {
                return this.run();
            }
        }.call();
    }
    
    protected DamageEffectFX copy(final Reader reader, final Writer writer) throws IOException {
        return (DamageEffectFX)new CloseOperation((Closeable)reader, this.ignoreCloseExceptions, reader, writer) {
            final DamageEffectFX this$0;
            private final Reader val$input;
            private final Writer val$output;
            
            public DamageEffectFX run() throws IOException {
                final char[] array = new char[DamageEffectFX.access$1(this.this$0)];
                int read;
                while ((read = this.val$input.read(array)) != -1) {
                    this.val$output.write(array, 0, read);
                    final DamageEffectFX this$0 = this.this$0;
                    DamageEffectFX.access$3(this$0, DamageEffectFX.access$2(this$0) + read);
                    DamageEffectFX.access$4(this.this$0).onUpload(DamageEffectFX.access$2(this.this$0), -1L);
                }
                return this.this$0;
            }
            
            public Object run() throws HttpRequestException, IOException {
                return this.run();
            }
        }.call();
    }
    
    public DamageEffectFX progress(final UploadProgress progress) {
        if (progress == null) {
            this.progress = UploadProgress.DEFAULT;
        }
        else {
            this.progress = progress;
        }
        return this;
    }
    
    private DamageEffectFX incrementTotalSize(final long n) {
        if (this.totalSize == -1L) {
            this.totalSize = 0L;
        }
        this.totalSize += n;
        return this;
    }
    
    protected DamageEffectFX closeOutput() throws IOException {
        this.progress(null);
        if (this.output == null) {
            return this;
        }
        if (this.multipart) {
            this.output.write(DamageEffectFX.llIIIIllllllIlI[97]);
        }
        if (this.ignoreCloseExceptions) {
            try {
                this.output.close();
            }
            catch (IOException ex) {}
        }
        else {
            this.output.close();
        }
        this.output = null;
        return this;
    }
    
    protected DamageEffectFX closeOutputQuietly() throws HttpRequestException {
        try {
            return this.closeOutput();
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
    }
    
    protected DamageEffectFX openOutput() throws IOException {
        if (this.output != null) {
            return this;
        }
        this.getConnection().setDoOutput(true);
        this.output = new RequestOutputStream(this.getConnection().getOutputStream(), this.getParam(this.getConnection().getRequestProperty(DamageEffectFX.llIIIIllllllIlI[98]), DamageEffectFX.llIIIIllllllIlI[99]), this.bufferSize);
        return this;
    }
    
    protected DamageEffectFX startPart() throws IOException {
        if (!this.multipart) {
            this.multipart = true;
            this.contentType(DamageEffectFX.llIIIIllllllIlI[100]).openOutput();
            this.output.write(DamageEffectFX.llIIIIllllllIlI[101]);
        }
        else {
            this.output.write(DamageEffectFX.llIIIIllllllIlI[102]);
        }
        return this;
    }
    
    protected DamageEffectFX writePartHeader(final String s, final String s2) throws IOException {
        return this.writePartHeader(s, s2, null);
    }
    
    protected DamageEffectFX writePartHeader(final String s, final String s2, final String s3) throws IOException {
        final StringBuilder sb = new StringBuilder();
        sb.append(DamageEffectFX.llIIIIllllllIlI[103]).append(s);
        if (s2 != null) {
            sb.append(DamageEffectFX.llIIIIllllllIlI[104]).append(s2);
        }
        sb.append('\"');
        this.partHeader(DamageEffectFX.llIIIIllllllIlI[105], sb.toString());
        if (s3 != null) {
            this.partHeader(DamageEffectFX.llIIIIllllllIlI[106], s3);
        }
        return this.send(DamageEffectFX.llIIIIllllllIlI[107]);
    }
    
    public DamageEffectFX part(final String s, final String s2) {
        return this.part(s, null, s2);
    }
    
    public DamageEffectFX part(final String s, final String s2, final String s3) throws HttpRequestException {
        return this.part(s, s2, null, s3);
    }
    
    public DamageEffectFX part(final String s, final String s2, final String s3, final String s4) throws HttpRequestException {
        try {
            this.startPart();
            this.writePartHeader(s, s2, s3);
            this.output.write(s4);
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
        return this;
    }
    
    public DamageEffectFX part(final String s, final Number n) throws HttpRequestException {
        return this.part(s, null, n);
    }
    
    public DamageEffectFX part(final String s, final String s2, final Number n) throws HttpRequestException {
        return this.part(s, s2, (n != null) ? n.toString() : null);
    }
    
    public DamageEffectFX part(final String s, final File file) throws HttpRequestException {
        return this.part(s, null, file);
    }
    
    public DamageEffectFX part(final String s, final String s2, final File file) throws HttpRequestException {
        return this.part(s, s2, null, file);
    }
    
    public DamageEffectFX part(final String s, final String s2, final String s3, final File file) throws HttpRequestException {
        BufferedInputStream bufferedInputStream;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            this.incrementTotalSize(file.length());
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
        return this.part(s, s2, s3, bufferedInputStream);
    }
    
    public DamageEffectFX part(final String s, final InputStream inputStream) throws HttpRequestException {
        return this.part(s, null, null, inputStream);
    }
    
    public DamageEffectFX part(final String s, final String s2, final String s3, final InputStream inputStream) throws HttpRequestException {
        try {
            this.startPart();
            this.writePartHeader(s, s2, s3);
            this.copy(inputStream, this.output);
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
        return this;
    }
    
    public DamageEffectFX partHeader(final String s, final String s2) throws HttpRequestException {
        return this.send(s).send(DamageEffectFX.llIIIIllllllIlI[108]).send(s2).send(DamageEffectFX.llIIIIllllllIlI[109]);
    }
    
    public DamageEffectFX send(final File file) throws HttpRequestException {
        BufferedInputStream bufferedInputStream;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            this.incrementTotalSize(file.length());
        }
        catch (FileNotFoundException ex) {
            throw new HttpRequestException(ex);
        }
        return this.send(bufferedInputStream);
    }
    
    public DamageEffectFX send(final byte[] array) throws HttpRequestException {
        if (array != null) {
            this.incrementTotalSize(array.length);
        }
        return this.send(new ByteArrayInputStream(array));
    }
    
    public DamageEffectFX send(final InputStream inputStream) throws HttpRequestException {
        try {
            this.openOutput();
            this.copy(inputStream, this.output);
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
        return this;
    }
    
    public DamageEffectFX send(final Reader reader) throws HttpRequestException {
        try {
            this.openOutput();
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.output, RequestOutputStream.access$0(this.output).charset());
        return (DamageEffectFX)new FlushOperation((Flushable)outputStreamWriter, reader, (Writer)outputStreamWriter) {
            final DamageEffectFX this$0;
            private final Reader val$input;
            private final Writer val$writer;
            
            @Override
            protected DamageEffectFX run() throws IOException {
                return this.this$0.copy(this.val$input, this.val$writer);
            }
            
            @Override
            protected Object run() throws HttpRequestException, IOException {
                return this.run();
            }
        }.call();
    }
    
    public DamageEffectFX send(final CharSequence charSequence) throws HttpRequestException {
        try {
            this.openOutput();
            this.output.write(charSequence.toString());
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
        return this;
    }
    
    public OutputStreamWriter writer() throws HttpRequestException {
        try {
            this.openOutput();
            return new OutputStreamWriter(this.output, RequestOutputStream.access$0(this.output).charset());
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
    }
    
    public DamageEffectFX form(final Map map) throws HttpRequestException {
        return this.form(map, DamageEffectFX.llIIIIllllllIlI[110]);
    }
    
    public DamageEffectFX form(final Map.Entry entry) throws HttpRequestException {
        return this.form(entry, DamageEffectFX.llIIIIllllllIlI[111]);
    }
    
    public DamageEffectFX form(final Map.Entry entry, final String s) throws HttpRequestException {
        return this.form(entry.getKey(), entry.getValue(), s);
    }
    
    public DamageEffectFX form(final Object o, final Object o2) throws HttpRequestException {
        return this.form(o, o2, DamageEffectFX.llIIIIllllllIlI[112]);
    }
    
    public DamageEffectFX form(final Object o, final Object o2, String validCharset) throws HttpRequestException {
        final boolean b = !this.form;
        if (b) {
            this.contentType(DamageEffectFX.llIIIIllllllIlI[113], validCharset);
            this.form = true;
        }
        validCharset = getValidCharset(validCharset);
        try {
            this.openOutput();
            if (!b) {
                this.output.write(38);
            }
            this.output.write(URLEncoder.encode(o.toString(), validCharset));
            this.output.write(61);
            if (o2 != null) {
                this.output.write(URLEncoder.encode(o2.toString(), validCharset));
            }
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
        return this;
    }
    
    public DamageEffectFX form(final Map map, final String s) throws HttpRequestException {
        if (!map.isEmpty()) {
            final Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                this.form(iterator.next(), s);
            }
        }
        return this;
    }
    
    public DamageEffectFX trustAllCerts() throws HttpRequestException {
        final HttpURLConnection connection = this.getConnection();
        if (connection instanceof HttpsURLConnection) {
            ((HttpsURLConnection)connection).setSSLSocketFactory(getTrustedFactory());
        }
        return this;
    }
    
    public DamageEffectFX trustAllHosts() {
        final HttpURLConnection connection = this.getConnection();
        if (connection instanceof HttpsURLConnection) {
            ((HttpsURLConnection)connection).setHostnameVerifier(getTrustedVerifier());
        }
        return this;
    }
    
    public URL url() {
        return this.getConnection().getURL();
    }
    
    public String method() {
        return this.getConnection().getRequestMethod();
    }
    
    public DamageEffectFX useProxy(final String httpProxyHost, final int httpProxyPort) {
        if (this.connection != null) {
            throw new IllegalStateException(DamageEffectFX.llIIIIllllllIlI[114]);
        }
        this.httpProxyHost = httpProxyHost;
        this.httpProxyPort = httpProxyPort;
        return this;
    }
    
    public DamageEffectFX followRedirects(final boolean instanceFollowRedirects) {
        this.getConnection().setInstanceFollowRedirects(instanceFollowRedirects);
        return this;
    }
    
    static String access$0(final String s) {
        return getValidCharset(s);
    }
    
    static int access$1(final DamageEffectFX damageEffectFX) {
        return damageEffectFX.bufferSize;
    }
    
    static long access$2(final DamageEffectFX damageEffectFX) {
        return damageEffectFX.totalWritten;
    }
    
    static void access$3(final DamageEffectFX damageEffectFX, final long totalWritten) {
        damageEffectFX.totalWritten = totalWritten;
    }
    
    static UploadProgress access$4(final DamageEffectFX damageEffectFX) {
        return damageEffectFX.progress;
    }
    
    static long access$5(final DamageEffectFX damageEffectFX) {
        return damageEffectFX.totalSize;
    }
    
    private static void lIIlIIIIIlIlIIll() {
        (llIIIIllllllIlI = new String[115])[0] = lIIlIIIIIlIIIIlI(DamageEffectFX.llIIIlIIIIIlIlI[0], DamageEffectFX.llIIIlIIIIIlIlI[1]);
        DamageEffectFX.llIIIIllllllIlI[1] = lIIlIIIIIlIIIllI(DamageEffectFX.llIIIlIIIIIlIlI[2], DamageEffectFX.llIIIlIIIIIlIlI[3]);
        DamageEffectFX.llIIIIllllllIlI[2] = lIIlIIIIIlIIIIlI(DamageEffectFX.llIIIlIIIIIlIlI[4], DamageEffectFX.llIIIlIIIIIlIlI[5]);
        DamageEffectFX.llIIIIllllllIlI[3] = lIIlIIIIIlIIIIlI(DamageEffectFX.llIIIlIIIIIlIlI[6], DamageEffectFX.llIIIlIIIIIlIlI[7]);
        DamageEffectFX.llIIIIllllllIlI[4] = lIIlIIIIIlIIlIII(DamageEffectFX.llIIIlIIIIIlIlI[8], DamageEffectFX.llIIIlIIIIIlIlI[9]);
        DamageEffectFX.llIIIIllllllIlI[5] = lIIlIIIIIlIIlIIl(DamageEffectFX.llIIIlIIIIIlIlI[10], DamageEffectFX.llIIIlIIIIIlIlI[11]);
        DamageEffectFX.llIIIIllllllIlI[6] = lIIlIIIIIlIIlIIl(DamageEffectFX.llIIIlIIIIIlIlI[12], DamageEffectFX.llIIIlIIIIIlIlI[13]);
        DamageEffectFX.llIIIIllllllIlI[7] = lIIlIIIIIlIIIllI(DamageEffectFX.llIIIlIIIIIlIlI[14], DamageEffectFX.llIIIlIIIIIlIlI[15]);
        DamageEffectFX.llIIIIllllllIlI[8] = lIIlIIIIIlIIIIlI(DamageEffectFX.llIIIlIIIIIlIlI[16], DamageEffectFX.llIIIlIIIIIlIlI[17]);
        DamageEffectFX.llIIIIllllllIlI[9] = lIIlIIIIIlIIlIIl(DamageEffectFX.llIIIlIIIIIlIlI[18], DamageEffectFX.llIIIlIIIIIlIlI[19]);
        DamageEffectFX.llIIIIllllllIlI[10] = lIIlIIIIIlIIlIIl(DamageEffectFX.llIIIlIIIIIlIlI[20], DamageEffectFX.llIIIlIIIIIlIlI[21]);
        DamageEffectFX.llIIIIllllllIlI[11] = lIIlIIIIIlIIIllI(DamageEffectFX.llIIIlIIIIIlIlI[22], DamageEffectFX.llIIIlIIIIIlIlI[23]);
        DamageEffectFX.llIIIIllllllIlI[12] = lIIlIIIIIlIIlIII(DamageEffectFX.llIIIlIIIIIlIlI[24], DamageEffectFX.llIIIlIIIIIlIlI[25]);
        DamageEffectFX.llIIIIllllllIlI[13] = lIIlIIIIIlIIlIIl(DamageEffectFX.llIIIlIIIIIlIlI[26], DamageEffectFX.llIIIlIIIIIlIlI[27]);
        DamageEffectFX.llIIIIllllllIlI[14] = lIIlIIIIIlIIlIIl(DamageEffectFX.llIIIlIIIIIlIlI[28], DamageEffectFX.llIIIlIIIIIlIlI[29]);
        DamageEffectFX.llIIIIllllllIlI[15] = lIIlIIIIIlIIIllI(DamageEffectFX.llIIIlIIIIIlIlI[30], DamageEffectFX.llIIIlIIIIIlIlI[31]);
        DamageEffectFX.llIIIIllllllIlI[16] = lIIlIIIIIlIIlIII(DamageEffectFX.llIIIlIIIIIlIlI[32], DamageEffectFX.llIIIlIIIIIlIlI[33]);
        DamageEffectFX.llIIIIllllllIlI[17] = lIIlIIIIIlIIlIIl(DamageEffectFX.llIIIlIIIIIlIlI[34], DamageEffectFX.llIIIlIIIIIlIlI[35]);
        DamageEffectFX.llIIIIllllllIlI[18] = lIIlIIIIIlIIlIII("yuFXjfXgiC7Ey0boLT71zA==", "guVzm");
        DamageEffectFX.llIIIIllllllIlI[19] = lIIlIIIIIlIIIIlI("9u5TxLhMz/5MwBb9K2Aqj7kwU9BFPMuhxFL58B65PAR96Nso17SXYA+39BOdLnVIxVOGOTED+qo=", "SZdgx");
        DamageEffectFX.llIIIIllllllIlI[20] = lIIlIIIIIlIIlIIl("MCsnPSQ2Nw==", "SCFOW");
        DamageEffectFX.llIIIIllllllIlI[21] = lIIlIIIIIlIIIIlI("eQBhZqEIz65YjR3D/PgX/Q==", "sooSX");
        DamageEffectFX.llIIIIllllllIlI[22] = lIIlIIIIIlIIlIII("yP9ef/+KXyQ=", "YOLIr");
        DamageEffectFX.llIIIIllllllIlI[23] = lIIlIIIIIlIIIIlI("A0oNZTlJ6vBtMWt3UhTD/2VpTbOpJIUR", "fviZU");
        DamageEffectFX.llIIIIllllllIlI[24] = lIIlIIIIIlIIIIlI("ecHHxpFqW7Y=", "pdLaU");
        DamageEffectFX.llIIIIllllllIlI[25] = lIIlIIIIIlIIIIlI("XDC3LEY6G0qu8TMOOKUHsNypT0uxqMis", "shglK");
        DamageEffectFX.llIIIIllllllIlI[26] = lIIlIIIIIlIIIllI("j3I2aucXKyxNfuiOCwfCOA==", "AUpqb");
        DamageEffectFX.llIIIIllllllIlI[27] = lIIlIIIIIlIIIllI("F9UUT79fx/OJrV6JJRUZtA==", "rKFEQ");
        DamageEffectFX.llIIIIllllllIlI[28] = lIIlIIIIIlIIIIlI("u5xjfxgYezfRGusCV7EK8g==", "HgRtW");
        DamageEffectFX.llIIIIllllllIlI[29] = lIIlIIIIIlIIlIIl("MAgkLycHHw==", "bmBJU");
        DamageEffectFX.llIIIIllllllIlI[30] = lIIlIIIIIlIIlIIl("ICNBFCkHIEEXJx0mBA==", "iElZF");
        DamageEffectFX.llIIIIllllllIlI[31] = lIIlIIIIIlIIIIlI("7MWcgPB9j1L2Zubfte7XQIEp3dgbqUol", "LbKRr");
        DamageEffectFX.llIIIIllllllIlI[32] = lIIlIIIIIlIIIllI("Jw6/FwPIHMj5rUYsTwFMZg==", "WTZSm");
        DamageEffectFX.llIIIIllllllIlI[33] = lIIlIIIIIlIIlIIl("LgU+JwEvBg==", "aUjnN");
        DamageEffectFX.llIIIIllllllIlI[34] = lIIlIIIIIlIIlIIl("IBU2R0A=", "uApjx");
        DamageEffectFX.llIIIIllllllIlI[35] = lIIlIIIIIlIIIllI("xBDR/XWSvUvsGKP+HXWFAw==", "xUeTb");
        DamageEffectFX.llIIIIllllllIlI[36] = lIIlIIIIIlIIlIII("aB/R/WNegaVunUYOUc9qb4wOGSCbUHF24FYO5szWSbGovdTfKjkt4TlQvCT3/G8O", "FYIfo");
        DamageEffectFX.llIIIIllllllIlI[37] = lIIlIIIIIlIIlIIl("Pj5M", "ecqWQ");
        DamageEffectFX.llIIIIllllllIlI[38] = lIIlIIIIIlIIlIIl("Rw==", "aCuCw");
        DamageEffectFX.llIIIIllllllIlI[39] = lIIlIIIIIlIIIllI("28mdo6mXvCrg7nAgwn8GOw==", "zLQqj");
        DamageEffectFX.llIIIIllllllIlI[40] = lIIlIIIIIlIIlIII("9Uzgk4L+ySg=", "ZIbvZ");
        DamageEffectFX.llIIIIllllllIlI[41] = lIIlIIIIIlIIIllI("RYxDKOIppZf1uux76+F3hQ==", "NQQgt");
        DamageEffectFX.llIIIIllllllIlI[42] = lIIlIIIIIlIIlIII("zVdXyLBMPIq1zKxUsDFSIEdMQSoZ60BF", "UzpkF");
        DamageEffectFX.llIIIIllllllIlI[43] = lIIlIIIIIlIIIllI("yoiNE+4XVIU2GFXb2jquHoDn3+yPue0Nr/Fqg9tKeA8Xtlz9CDu9e9je7RRAZKEJT9PWdb0VJcJW1nJpgDDeRA==", "JylwL");
        DamageEffectFX.llIIIIllllllIlI[44] = lIIlIIIIIlIIlIIl("Egc+", "UBjev");
        DamageEffectFX.llIIIIllllllIlI[45] = lIIlIIIIIlIIIllI("G4wu6vkSxMhHjnAE8y/VWA==", "PRqJj");
        DamageEffectFX.llIIIIllllllIlI[46] = lIIlIIIIIlIIIIlI("aHwbouYCJuI=", "DVtEj");
        DamageEffectFX.llIIIIllllllIlI[47] = lIIlIIIIIlIIlIII("PxNMQ+ugDT4=", "vfsdd");
        DamageEffectFX.llIIIIllllllIlI[48] = lIIlIIIIIlIIIllI("KKH9nCwaTvapMu+YLxpzjw==", "LehDn");
        DamageEffectFX.llIIIIllllllIlI[49] = lIIlIIIIIlIIIllI("vREP25TA/qTpCXIUhCRD2A==", "fqoMK");
        DamageEffectFX.llIIIIllllllIlI[50] = lIIlIIIIIlIIlIIl("AR4+", "QKjPG");
        DamageEffectFX.llIIIIllllllIlI[51] = lIIlIIIIIlIIlIIl("JRQYAiQk", "aQTGp");
        DamageEffectFX.llIIIIllllllIlI[52] = lIIlIIIIIlIIIIlI("WYdS4YUJslA=", "UHaMR");
        DamageEffectFX.llIIIIllllllIlI[53] = lIIlIIIIIlIIlIIl("Lg8gDA==", "fJaHW");
        DamageEffectFX.llIIIIllllllIlI[54] = lIIlIIIIIlIIlIIl("MRM2EA==", "yVwTp");
        DamageEffectFX.llIIIIllllllIlI[55] = lIIlIIIIIlIIIllI("TAcEf/tOI6Z3a7CURYOmcQ==", "pHmbR");
        DamageEffectFX.llIIIIllllllIlI[56] = lIIlIIIIIlIIIIlI("X+1p6z8G6Vo=", "BYZvp");
        DamageEffectFX.llIIIIllllllIlI[57] = lIIlIIIIIlIIIllI("kX7iUEydnlmCc5tDC13b7Q==", "Cyuqi");
        DamageEffectFX.llIIIIllllllIlI[58] = lIIlIIIIIlIIlIII("RaQAlwvIwsI=", "eKAJq");
        DamageEffectFX.llIIIIllllllIlI[59] = lIIlIIIIIlIIlIIl("HB4mMkIfDzcyLRgDJCc=", "tjRBl");
        DamageEffectFX.llIIIIllllllIlI[60] = lIIlIIIIIlIIlIII("gNhx8LwP+WYbtFSHbLiWy0YpmRsDibOu", "sdCTn");
        DamageEffectFX.llIIIIllllllIlI[61] = lIIlIIIIIlIIlIII("LHkmnLEw7qvuvhxNR1r/3Q==", "QFXLb");
        DamageEffectFX.llIIIIllllllIlI[62] = lIIlIIIIIlIIlIIl("Bz8+HzxBOzgANxYDJRw7", "oKJoO");
        DamageEffectFX.llIIIIllllllIlI[63] = lIIlIIIIIlIIlIIl("Lg4hFnY2CDoeIRYVJxI=", "FzUfX");
        DamageEffectFX.llIIIIllllllIlI[64] = lIIlIIIIIlIIIIlI("be2UhyjhM0kt15eJ8/vV1w==", "vEDLd");
        DamageEffectFX.llIIIIllllllIlI[65] = lIIlIIIIIlIIIIlI("a2ygd1J4pu37sDaIrVa0XdiUXrQFZepi", "nlfrn");
        DamageEffectFX.llIIIIllllllIlI[66] = lIIlIIIIIlIIlIIl("OQQXBkY/Hw0mGj4IGj4HIgQQ", "Qpcvh");
        DamageEffectFX.llIIIIllllllIlI[67] = lIIlIIIIIlIIIllI("iSDAmeDOz07QOJTH/Bdm9cjW1tHLHRfh9U1xe2VJ+kw=", "IQHQn");
        DamageEffectFX.llIIIIllllllIlI[68] = lIIlIIIIIlIIIllI("xXlH5WoOJMUagrvTE3fYzQ==", "dYmad");
        DamageEffectFX.llIIIIllllllIlI[69] = lIIlIIIIIlIIlIII("3aoR+n95k0jT9434TKmaiw==", "CQikW");
        DamageEffectFX.llIIIIllllllIlI[70] = lIIlIIIIIlIIlIIl("ByUrKxIBOQ==", "dMJYa");
        DamageEffectFX.llIIIIllllllIlI[71] = lIIlIIIIIlIIlIII("3A0g+UkadP8hbP5W9CiWjQ==", "mmHme");
        DamageEffectFX.llIIIIllllllIlI[72] = lIIlIIIIIlIIlIIl("JRElDiQSBg==", "wtCkV");
        DamageEffectFX.llIIIIllllllIlI[73] = lIIlIIIIIlIIIllI("jwHGQyUwWUYihO4v9Aiehg==", "frDMi");
        DamageEffectFX.llIIIIllllllIlI[74] = lIIlIIIIIlIIIllI("LRvVcpvhhebSRNa0WOem+A==", "PbHXr");
        DamageEffectFX.llIIIIllllllIlI[75] = lIIlIIIIIlIIIllI("4xZNzRSONoTffT77ZAZ41g==", "qMViM");
        DamageEffectFX.llIIIIllllllIlI[76] = lIIlIIIIIlIIlIIl("JiUJOygLPkoKIwYlAyYjAg==", "eJgOM");
        DamageEffectFX.llIIIIllllllIlI[77] = lIIlIIIIIlIIIllI("bPo7HZtLWTItgZXHcfmPxg==", "DSUgg");
        DamageEffectFX.llIIIIllllllIlI[78] = lIIlIIIIIlIIIIlI("n27Ecp6Rr+o=", "ljtvm");
        DamageEffectFX.llIIIIllllllIlI[79] = lIIlIIIIIlIIIllI("AoBLGK17p/C2Ymm/gHc4qA==", "voWos");
        DamageEffectFX.llIIIIllllllIlI[80] = lIIlIIIIIlIIlIII("+LoD8poLpfA=", "VJNEW");
        DamageEffectFX.llIIIIllllllIlI[81] = lIIlIIIIIlIIIIlI("zbZtn7ISFf4=", "qoxSy");
        DamageEffectFX.llIIIIllllllIlI[82] = lIIlIIIIIlIIIIlI("nzcsOKPGiEx1rNbK483NFA==", "RJjQU");
        DamageEffectFX.llIIIIllllllIlI[83] = lIIlIIIIIlIIlIII("S65v8tbSUvreuZe/cD6rWQ==", "cyMDx");
        DamageEffectFX.llIIIIllllllIlI[84] = lIIlIIIIIlIIlIII("zifWiEwGT/JQE8Xo6aeThQ==", "jqSWJ");
        DamageEffectFX.llIIIIllllllIlI[85] = lIIlIIIIIlIIIIlI("HYX9Y5p4L+Hpalw12hmnacPVNdQwtExn", "oQaES");
        DamageEffectFX.llIIIIllllllIlI[86] = lIIlIIIIIlIIIllI("rtWBVD6nstNb9qTMOJdBeg==", "kACVj");
        DamageEffectFX.llIIIIllllllIlI[87] = lIIlIIIIIlIIIIlI("3CaBhPKyDFY=", "WZEWM");
        DamageEffectFX.llIIIIllllllIlI[88] = lIIlIIIIIlIIIllI("j7AN2gpGsA79kYaFsKaBJg==", "HwwvM");
        DamageEffectFX.llIIIIllllllIlI[89] = lIIlIIIIIlIIIIlI("EPYFvfmPY9boUbjtb9AiGQ==", "HRrRs");
        DamageEffectFX.llIIIIllllllIlI[90] = lIIlIIIIIlIIlIIl("Lgs5BBYDEHokCh0B", "mdWps");
        DamageEffectFX.llIIIIllllllIlI[91] = lIIlIIIIIlIIlIII("9k2PwetkrpejIidE3d5JNw==", "hNRmA");
        DamageEffectFX.llIIIIllllllIlI[92] = lIIlIIIIIlIIlIII("lyQRhReclOLbWgecysrOpg==", "PIraw");
        DamageEffectFX.llIIIIllllllIlI[93] = lIIlIIIIIlIIIIlI("8rK6KPTqrgzL7xUx5+gvCg==", "uZBbN");
        DamageEffectFX.llIIIIllllllIlI[94] = lIIlIIIIIlIIIllI("PcEWG6i0Qqm6YcGlYcl8cQ==", "dxyzm");
        DamageEffectFX.llIIIIllllllIlI[95] = lIIlIIIIIlIIIllI("dzaBlJ50GSaxEKf09+DV6g==", "JbntR");
        DamageEffectFX.llIIIIllllllIlI[96] = lIIlIIIIIlIIlIII("EAmNNaGH7yLor3BOqLhRoJAI6c7HWOtg", "PVYiJ");
        DamageEffectFX.llIIIIllllllIlI[97] = lIIlIIIIIlIIIIlI("5sK0KHW/5n4FvzU/D4VVTpXnZH+azzxRuDlep9hkYqs=", "XaQnU");
        DamageEffectFX.llIIIIllllllIlI[98] = lIIlIIIIIlIIIIlI("HNUgHIz7/HAAjL0kgpHVow==", "HVaet");
        DamageEffectFX.llIIIIllllllIlI[99] = lIIlIIIIIlIIlIII("cmwvx4fIQFo=", "aFTAV");
        DamageEffectFX.llIIIIllllllIlI[100] = lIIlIIIIIlIIIllI("cl0NkslT88KUzS7czqmrurZvRbfQVCt8aOMjyR9xL7Bm2IAVZ3d8Eqibr+uwiFDhdh33Ik/wyCgz99h9FP7YNw==", "CoKYh");
        DamageEffectFX.llIIIIllllllIlI[101] = lIIlIIIIIlIIIllI("bA4lghxSPzVdgWmJOXDGKfZO4cPjODnaBEWBb1xe1ls=", "Sajip");
        DamageEffectFX.llIIIIllllllIlI[102] = lIIlIIIIIlIIIIlI("cssvrwjT4VOmWNPkFSfWtWEoUt6h6T9g1bu8cLCyk24=", "UOKAi");
        DamageEffectFX.llIIIIllllllIlI[103] = lIIlIIIIIlIIIIlI("IdtEewYolZTLfdjkE373W38bRvdyyHq9", "mRKFO");
        DamageEffectFX.llIIIIllllllIlI[104] = lIIlIIIIIlIIIIlI("50JkS/CpzAzqCYPnVmpNcg==", "bfWae");
        DamageEffectFX.llIIIIllllllIlI[105] = lIIlIIIIIlIIIllI("VjgN3XGbrlMvAqpa4cJnyLwWcBs8Jbc+OuS6Z6tuUN0=", "AlmWZ");
        DamageEffectFX.llIIIIllllllIlI[106] = lIIlIIIIIlIIIIlI("1Smk3lL8efgSnrqwND2IWQ==", "BlSyg");
        DamageEffectFX.llIIIIllllllIlI[107] = lIIlIIIIIlIIlIII("mzegN01g9ew=", "khZaf");
        DamageEffectFX.llIIIIllllllIlI[108] = lIIlIIIIIlIIIllI("OyV7EbDNRVybdIrnhU7PgA==", "JVynY");
        DamageEffectFX.llIIIIllllllIlI[109] = lIIlIIIIIlIIlIII("VakmcSj6Eqw=", "snAVb");
        DamageEffectFX.llIIIIllllllIlI[110] = lIIlIIIIIlIIlIIl("MxEnaE4=", "fEaEv");
        DamageEffectFX.llIIIIllllllIlI[111] = lIIlIIIIIlIIlIIl("DyYCfm8=", "ZrDSW");
        DamageEffectFX.llIIIIllllllIlI[112] = lIIlIIIIIlIIlIII("PW6hdr05zO0=", "ZBQig");
        DamageEffectFX.llIIIIllllllIlI[113] = lIIlIIIIIlIIIllI("jSYtBKFoQNs3WLiqMbj6Wa7La+6zo8L4eXedfdRou9/37kXmQxBwyWkAOgU5ohZb", "BgjeM");
        DamageEffectFX.llIIIIllllllIlI[114] = lIIlIIIIIlIIIIlI("AuIjGACQSrr98PS2OjSwctS1kNv+Z81l7P1eF2BwNkPZq4vYqfxCJ2eczuWzxzp6d1RdZ4S76WwrX/pbt+4rhvP+Ep2bj11w9Z4pjUjOy0ky6AxrHJBABUTLbZaYEac8PWksFBO1JEESmVSyEtOKjw==", "RrZlu");
        DamageEffectFX.llIIIlIIIIIlIlI = null;
    }
    
    private static void lIIlIIIIIlIlIlII() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        DamageEffectFX.llIIIlIIIIIlIlI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIIlIIIIIlIIlIIl(String s, final String s2) {
        s = new String(java.util.Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
    
    private static String lIIlIIIIIlIIIllI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(java.util.Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIlIIIIIlIIIIlI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(java.util.Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIlIIIIIlIIlIII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(java.util.Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static class Base64
    {
        private static final byte EQUALS_SIGN;
        private static final String PREFERRED_ENCODING;
        private static final byte[] _STANDARD_ALPHABET;
        
        static {
            EQUALS_SIGN = 61;
            PREFERRED_ENCODING = "US-ASCII";
            _STANDARD_ALPHABET = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
        }
        
        private Base64() {
        }
        
        private static byte[] encode3to4(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) {
            final byte[] standard_ALPHABET = Base64._STANDARD_ALPHABET;
            final int n4 = ((n2 > 0) ? (array[n] << 24 >>> 8) : 0) | ((n2 > 1) ? (array[n + 1] << 24 >>> 16) : 0) | ((n2 > 2) ? (array[n + 2] << 24 >>> 24) : 0);
            switch (n2) {
                case 3: {
                    array2[n3] = standard_ALPHABET[n4 >>> 18];
                    array2[n3 + 1] = standard_ALPHABET[n4 >>> 12 & 0x3F];
                    array2[n3 + 2] = standard_ALPHABET[n4 >>> 6 & 0x3F];
                    array2[n3 + 3] = standard_ALPHABET[n4 & 0x3F];
                    return array2;
                }
                case 2: {
                    array2[n3] = standard_ALPHABET[n4 >>> 18];
                    array2[n3 + 1] = standard_ALPHABET[n4 >>> 12 & 0x3F];
                    array2[n3 + 2] = standard_ALPHABET[n4 >>> 6 & 0x3F];
                    array2[n3 + 3] = 61;
                    return array2;
                }
                case 1: {
                    array2[n3] = standard_ALPHABET[n4 >>> 18];
                    array2[n3 + 1] = standard_ALPHABET[n4 >>> 12 & 0x3F];
                    array2[n3 + 3] = (array2[n3 + 2] = 61);
                    return array2;
                }
                default: {
                    return array2;
                }
            }
        }
        
        public static String encode(final String s) {
            return encodeBytes(s.getBytes("US-ASCII"));
        }
        
        public static String encodeBytes(final byte[] array) {
            return encodeBytes(array, 0, array.length);
        }
        
        public static String encodeBytes(final byte[] array, final int n, final int n2) {
            return new String(encodeBytesToBytes(array, n, n2), "US-ASCII");
        }
        
        public static byte[] encodeBytesToBytes(final byte[] array, final int n, final int n2) {
            if (array == null) {
                throw new NullPointerException("Cannot serialize a null array.");
            }
            if (n < 0) {
                throw new IllegalArgumentException("Cannot have negative offset: " + n);
            }
            if (n2 < 0) {
                throw new IllegalArgumentException("Cannot have length offset: " + n2);
            }
            if (n + n2 > array.length) {
                throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", n, n2, array.length));
            }
            final byte[] array2 = new byte[n2 / 3 * 4 + ((n2 % 3 > 0) ? 4 : 0)];
            int n4 = 0;
            while (0 < n2 - 2) {
                encode3to4(array, 0 + n, 3, array2, 0);
                final int n3;
                n3 += 3;
                n4 += 4;
            }
            if (0 < n2) {
                encode3to4(array, 0 + n, n2 - 0, array2, 0);
                n4 += 4;
            }
            if (0 <= array2.length - 1) {
                final byte[] array3 = new byte[0];
                System.arraycopy(array2, 0, array3, 0, 0);
                return array3;
            }
            return array2;
        }
    }
    
    protected abstract static class CloseOperation extends Operation
    {
        private final Closeable closeable;
        private final boolean ignoreCloseExceptions;
        
        protected CloseOperation(final Closeable closeable, final boolean ignoreCloseExceptions) {
            this.closeable = closeable;
            this.ignoreCloseExceptions = ignoreCloseExceptions;
        }
        
        @Override
        protected void done() throws IOException {
            if (this.closeable instanceof Flushable) {
                ((Flushable)this.closeable).flush();
            }
            if (this.ignoreCloseExceptions) {
                this.closeable.close();
            }
            else {
                this.closeable.close();
            }
        }
    }
    
    protected abstract static class Operation implements Callable
    {
        protected abstract Object run() throws HttpRequestException, IOException;
        
        protected abstract void done() throws IOException;
        
        @Override
        public Object call() throws HttpRequestException {
            final Object run = this.run();
            this.done();
            return run;
        }
    }
    
    public static class HttpRequestException extends RuntimeException
    {
        private static final long serialVersionUID = -1170466989781746231L;
        
        public HttpRequestException(final IOException ex) {
            super(ex);
        }
        
        @Override
        public IOException getCause() {
            return (IOException)super.getCause();
        }
        
        @Override
        public Throwable getCause() {
            return this.getCause();
        }
    }
    
    public interface ConnectionFactory
    {
        public static final ConnectionFactory DEFAULT = new ConnectionFactory() {
            @Override
            public HttpURLConnection create(final URL url) throws IOException {
                return (HttpURLConnection)url.openConnection();
            }
            
            @Override
            public HttpURLConnection create(final URL url, final Proxy proxy) throws IOException {
                return (HttpURLConnection)url.openConnection(proxy);
            }
        };
        
        HttpURLConnection create(final URL p0) throws IOException;
        
        HttpURLConnection create(final URL p0, final Proxy p1) throws IOException;
    }
    
    protected abstract static class FlushOperation extends Operation
    {
        private final Flushable flushable;
        
        protected FlushOperation(final Flushable flushable) {
            this.flushable = flushable;
        }
        
        @Override
        protected void done() throws IOException {
            this.flushable.flush();
        }
    }
    
    public static class RequestOutputStream extends BufferedOutputStream
    {
        private final CharsetEncoder encoder;
        
        public RequestOutputStream(final OutputStream outputStream, final String s, final int n) {
            super(outputStream, n);
            this.encoder = Charset.forName(DamageEffectFX.access$0(s)).newEncoder();
        }
        
        public RequestOutputStream write(final String s) throws IOException {
            final ByteBuffer encode = this.encoder.encode(CharBuffer.wrap(s));
            super.write(encode.array(), 0, encode.limit());
            return this;
        }
        
        static CharsetEncoder access$0(final RequestOutputStream requestOutputStream) {
            return requestOutputStream.encoder;
        }
    }
    
    public interface UploadProgress
    {
        public static final UploadProgress DEFAULT = new UploadProgress() {
            @Override
            public void onUpload(final long n, final long n2) {
            }
        };
        
        void onUpload(final long p0, final long p1);
    }
}
