package Mood.AndyConnection;

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

public class UUIDChecker
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
    private static final String[] lIIIIIlIIlIIllIl;
    private static String[] lIIIIIlIIlIlIlIl;
    
    static {
        llIIIIIIIllIIIII();
        llIIIIIIIlIlllll();
        HEADER_ACCEPT_CHARSET = UUIDChecker.lIIIIIlIIlIIllIl[0];
        HEADER_CACHE_CONTROL = UUIDChecker.lIIIIIlIIlIIllIl[1];
        HEADER_ACCEPT_ENCODING = UUIDChecker.lIIIIIlIIlIIllIl[2];
        HEADER_PROXY_AUTHORIZATION = UUIDChecker.lIIIIIlIIlIIllIl[3];
        METHOD_DELETE = UUIDChecker.lIIIIIlIIlIIllIl[4];
        METHOD_OPTIONS = UUIDChecker.lIIIIIlIIlIIllIl[5];
        HEADER_EXPIRES = UUIDChecker.lIIIIIlIIlIIllIl[6];
        HEADER_CONTENT_LENGTH = UUIDChecker.lIIIIIlIIlIIllIl[7];
        CRLF = UUIDChecker.lIIIIIlIIlIIllIl[8];
        HEADER_CONTENT_ENCODING = UUIDChecker.lIIIIIlIIlIIllIl[9];
        HEADER_SERVER = UUIDChecker.lIIIIIlIIlIIllIl[10];
        METHOD_PUT = UUIDChecker.lIIIIIlIIlIIllIl[11];
        HEADER_LAST_MODIFIED = UUIDChecker.lIIIIIlIIlIIllIl[12];
        HEADER_IF_NONE_MATCH = UUIDChecker.lIIIIIlIIlIIllIl[13];
        METHOD_PATCH = UUIDChecker.lIIIIIlIIlIIllIl[14];
        METHOD_GET = UUIDChecker.lIIIIIlIIlIIllIl[15];
        BOUNDARY = UUIDChecker.lIIIIIlIIlIIllIl[16];
        HEADER_CONTENT_TYPE = UUIDChecker.lIIIIIlIIlIIllIl[17];
        HEADER_DATE = UUIDChecker.lIIIIIlIIlIIllIl[18];
        HEADER_LOCATION = UUIDChecker.lIIIIIlIIlIIllIl[19];
        CONTENT_TYPE_MULTIPART = UUIDChecker.lIIIIIlIIlIIllIl[20];
        PARAM_CHARSET = UUIDChecker.lIIIIIlIIlIIllIl[21];
        HEADER_ETAG = UUIDChecker.lIIIIIlIIlIIllIl[22];
        CHARSET_UTF8 = UUIDChecker.lIIIIIlIIlIIllIl[23];
        HEADER_AUTHORIZATION = UUIDChecker.lIIIIIlIIlIIllIl[24];
        HEADER_REFERER = UUIDChecker.lIIIIIlIIlIIllIl[25];
        METHOD_POST = UUIDChecker.lIIIIIlIIlIIllIl[26];
        CONTENT_TYPE_FORM = UUIDChecker.lIIIIIlIIlIIllIl[27];
        ENCODING_GZIP = UUIDChecker.lIIIIIlIIlIIllIl[28];
        METHOD_HEAD = UUIDChecker.lIIIIIlIIlIIllIl[29];
        HEADER_ACCEPT = UUIDChecker.lIIIIIlIIlIIllIl[30];
        METHOD_TRACE = UUIDChecker.lIIIIIlIIlIIllIl[31];
        HEADER_USER_AGENT = UUIDChecker.lIIIIIlIIlIIllIl[32];
        CONTENT_TYPE_JSON = UUIDChecker.lIIIIIlIIlIIllIl[33];
        EMPTY_STRINGS = new String[0];
        UUIDChecker.CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
    }
    
    private static String getValidCharset(final String s) {
        if (s != null && s.length() > 0) {
            return s;
        }
        return UUIDChecker.lIIIIIlIIlIIllIl[34];
    }
    
    private static SSLSocketFactory getTrustedFactory() throws HttpRequestException {
        if (UUIDChecker.TRUSTED_FACTORY == null) {
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
                final SSLContext instance = SSLContext.getInstance(UUIDChecker.lIIIIIlIIlIIllIl[35]);
                instance.init(null, array, new SecureRandom());
                UUIDChecker.TRUSTED_FACTORY = instance.getSocketFactory();
            }
            catch (GeneralSecurityException ex2) {
                final IOException ex = new IOException(UUIDChecker.lIIIIIlIIlIIllIl[36]);
                ex.initCause(ex2);
                throw new HttpRequestException(ex);
            }
        }
        return UUIDChecker.TRUSTED_FACTORY;
    }
    
    private static HostnameVerifier getTrustedVerifier() {
        if (UUIDChecker.TRUSTED_VERIFIER == null) {
            UUIDChecker.TRUSTED_VERIFIER = new HostnameVerifier() {
                @Override
                public boolean verify(final String s, final SSLSession sslSession) {
                    return true;
                }
            };
        }
        return UUIDChecker.TRUSTED_VERIFIER;
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
                sb.append(UUIDChecker.lIIIIIlIIlIIllIl[37]);
                final Object next = iterator.next();
                if (next != null) {
                    sb.append(next);
                }
                if (iterator.hasNext()) {
                    sb.append(UUIDChecker.lIIIIIlIIlIIllIl[38]);
                }
            }
        }
        else {
            sb.append(o);
            sb.append(UUIDChecker.lIIIIIlIIlIIllIl[39]);
            if (arrayToList != null) {
                sb.append(arrayToList);
            }
        }
        return sb;
    }
    
    public static void setConnectionFactory(final ConnectionFactory connection_FACTORY) {
        if (connection_FACTORY == null) {
            UUIDChecker.CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
        }
        else {
            UUIDChecker.CONNECTION_FACTORY = connection_FACTORY;
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
                s2 = String.valueOf(s2.substring(0, index + 1)) + s2.substring(index + 1).replace(UUIDChecker.lIIIIIlIIlIIllIl[40], UUIDChecker.lIIIIIlIIlIIllIl[41]);
            }
            return s2;
        }
        catch (URISyntaxException ex3) {
            final IOException ex2 = new IOException(UUIDChecker.lIIIIIlIIlIIllIl[42]);
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
            throw new IllegalArgumentException(UUIDChecker.lIIIIIlIIlIIllIl[43]);
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
    
    public static UUIDChecker get(final CharSequence charSequence) throws HttpRequestException {
        return new UUIDChecker(charSequence, UUIDChecker.lIIIIIlIIlIIllIl[44]);
    }
    
    public static UUIDChecker get(final URL url) throws HttpRequestException {
        return new UUIDChecker(url, UUIDChecker.lIIIIIlIIlIIllIl[45]);
    }
    
    public static UUIDChecker get(final CharSequence charSequence, final Map map, final boolean b) {
        final String append = append(charSequence, map);
        return get(b ? encode(append) : append);
    }
    
    public static UUIDChecker get(final CharSequence charSequence, final boolean b, final Object... array) {
        final String append = append(charSequence, array);
        return get(b ? encode(append) : append);
    }
    
    public static UUIDChecker post(final CharSequence charSequence) throws HttpRequestException {
        return new UUIDChecker(charSequence, UUIDChecker.lIIIIIlIIlIIllIl[46]);
    }
    
    public static UUIDChecker post(final URL url) throws HttpRequestException {
        return new UUIDChecker(url, UUIDChecker.lIIIIIlIIlIIllIl[47]);
    }
    
    public static UUIDChecker post(final CharSequence charSequence, final Map map, final boolean b) {
        final String append = append(charSequence, map);
        return post(b ? encode(append) : append);
    }
    
    public static UUIDChecker post(final CharSequence charSequence, final boolean b, final Object... array) {
        final String append = append(charSequence, array);
        return post(b ? encode(append) : append);
    }
    
    public static UUIDChecker patch(final CharSequence charSequence) throws HttpRequestException {
        return new UUIDChecker(charSequence, UUIDChecker.lIIIIIlIIlIIllIl[48]);
    }
    
    public static UUIDChecker put(final CharSequence charSequence) throws HttpRequestException {
        return new UUIDChecker(charSequence, UUIDChecker.lIIIIIlIIlIIllIl[49]);
    }
    
    public static UUIDChecker put(final URL url) throws HttpRequestException {
        return new UUIDChecker(url, UUIDChecker.lIIIIIlIIlIIllIl[50]);
    }
    
    public static UUIDChecker put(final CharSequence charSequence, final Map map, final boolean b) {
        final String append = append(charSequence, map);
        return put(b ? encode(append) : append);
    }
    
    public static UUIDChecker put(final CharSequence charSequence, final boolean b, final Object... array) {
        final String append = append(charSequence, array);
        return put(b ? encode(append) : append);
    }
    
    public static UUIDChecker delete(final CharSequence charSequence) throws HttpRequestException {
        return new UUIDChecker(charSequence, UUIDChecker.lIIIIIlIIlIIllIl[51]);
    }
    
    public static UUIDChecker delete(final URL url) throws HttpRequestException {
        return new UUIDChecker(url, UUIDChecker.lIIIIIlIIlIIllIl[52]);
    }
    
    public static UUIDChecker delete(final CharSequence charSequence, final Map map, final boolean b) {
        final String append = append(charSequence, map);
        return delete(b ? encode(append) : append);
    }
    
    public static UUIDChecker delete(final CharSequence charSequence, final boolean b, final Object... array) {
        final String append = append(charSequence, array);
        return delete(b ? encode(append) : append);
    }
    
    public static UUIDChecker head(final CharSequence charSequence) throws HttpRequestException {
        return new UUIDChecker(charSequence, UUIDChecker.lIIIIIlIIlIIllIl[53]);
    }
    
    public static UUIDChecker head(final URL url) throws HttpRequestException {
        return new UUIDChecker(url, UUIDChecker.lIIIIIlIIlIIllIl[54]);
    }
    
    public static UUIDChecker head(final CharSequence charSequence, final Map map, final boolean b) {
        final String append = append(charSequence, map);
        return head(b ? encode(append) : append);
    }
    
    public static UUIDChecker head(final CharSequence charSequence, final boolean b, final Object... array) {
        final String append = append(charSequence, array);
        return head(b ? encode(append) : append);
    }
    
    public static UUIDChecker options(final CharSequence charSequence) throws HttpRequestException {
        return new UUIDChecker(charSequence, UUIDChecker.lIIIIIlIIlIIllIl[55]);
    }
    
    public static UUIDChecker options(final URL url) throws HttpRequestException {
        return new UUIDChecker(url, UUIDChecker.lIIIIIlIIlIIllIl[56]);
    }
    
    public static UUIDChecker trace(final CharSequence charSequence) throws HttpRequestException {
        return new UUIDChecker(charSequence, UUIDChecker.lIIIIIlIIlIIllIl[57]);
    }
    
    public static UUIDChecker trace(final URL url) throws HttpRequestException {
        return new UUIDChecker(url, UUIDChecker.lIIIIIlIIlIIllIl[58]);
    }
    
    public static void keepAlive(final boolean b) {
        setProperty(UUIDChecker.lIIIIIlIIlIIllIl[59], Boolean.toString(b));
    }
    
    public static void maxConnections(final int n) {
        setProperty(UUIDChecker.lIIIIIlIIlIIllIl[60], Integer.toString(n));
    }
    
    public static void proxyHost(final String s) {
        setProperty(UUIDChecker.lIIIIIlIIlIIllIl[61], s);
        setProperty(UUIDChecker.lIIIIIlIIlIIllIl[62], s);
    }
    
    public static void proxyPort(final int n) {
        final String string = Integer.toString(n);
        setProperty(UUIDChecker.lIIIIIlIIlIIllIl[63], string);
        setProperty(UUIDChecker.lIIIIIlIIlIIllIl[64], string);
    }
    
    public static void nonProxyHosts(final String... array) {
        if (array != null && array.length > 0) {
            final StringBuilder sb = new StringBuilder();
            final int n = array.length - 1;
            for (int i = 0; i < n; ++i) {
                sb.append(array[i]).append('|');
            }
            sb.append(array[n]);
            setProperty(UUIDChecker.lIIIIIlIIlIIllIl[65], sb.toString());
        }
        else {
            setProperty(UUIDChecker.lIIIIIlIIlIIllIl[66], null);
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
    
    public UUIDChecker(final CharSequence charSequence, final String requestMethod) throws HttpRequestException {
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
    
    public UUIDChecker(final URL url, final String requestMethod) throws HttpRequestException {
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
                httpURLConnection = UUIDChecker.CONNECTION_FACTORY.create(this.url, this.createProxy());
            }
            else {
                httpURLConnection = UUIDChecker.CONNECTION_FACTORY.create(this.url);
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
    
    public UUIDChecker ignoreCloseExceptions(final boolean ignoreCloseExceptions) {
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
    
    public UUIDChecker code(final AtomicInteger atomicInteger) throws HttpRequestException {
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
    
    public UUIDChecker disconnect() {
        this.getConnection().disconnect();
        return this;
    }
    
    public UUIDChecker chunk(final int chunkedStreamingMode) {
        this.getConnection().setChunkedStreamingMode(chunkedStreamingMode);
        return this;
    }
    
    public UUIDChecker bufferSize(final int bufferSize) {
        if (bufferSize < 1) {
            throw new IllegalArgumentException(UUIDChecker.lIIIIIlIIlIIllIl[67]);
        }
        this.bufferSize = bufferSize;
        return this;
    }
    
    public int bufferSize() {
        return this.bufferSize;
    }
    
    public UUIDChecker uncompress(final boolean uncompress) {
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
    
    public UUIDChecker body(final AtomicReference atomicReference) throws HttpRequestException {
        atomicReference.set(this.body());
        return this;
    }
    
    public UUIDChecker body(final AtomicReference atomicReference, final String s) throws HttpRequestException {
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
        if (!this.uncompress || !UUIDChecker.lIIIIIlIIlIIllIl[68].equals(this.contentEncoding())) {
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
    
    public UUIDChecker receive(final File file) throws HttpRequestException {
        BufferedOutputStream bufferedOutputStream;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file), this.bufferSize);
        }
        catch (FileNotFoundException ex) {
            throw new HttpRequestException(ex);
        }
        return (UUIDChecker)new CloseOperation((Closeable)bufferedOutputStream, this.ignoreCloseExceptions, (OutputStream)bufferedOutputStream) {
            final UUIDChecker this$0;
            private final OutputStream val$output;
            
            @Override
            protected UUIDChecker run() throws HttpRequestException, IOException {
                return this.this$0.receive(this.val$output);
            }
            
            @Override
            protected Object run() throws HttpRequestException, IOException {
                return this.run();
            }
        }.call();
    }
    
    public UUIDChecker receive(final OutputStream outputStream) throws HttpRequestException {
        try {
            return this.copy(this.buffer(), outputStream);
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
    }
    
    public UUIDChecker receive(final PrintStream printStream) throws HttpRequestException {
        return this.receive((OutputStream)printStream);
    }
    
    public UUIDChecker receive(final Appendable appendable) throws HttpRequestException {
        final BufferedReader bufferedReader = this.bufferedReader();
        return (UUIDChecker)new CloseOperation((Closeable)bufferedReader, this.ignoreCloseExceptions, bufferedReader, appendable) {
            final UUIDChecker this$0;
            private final BufferedReader val$reader;
            private final Appendable val$appendable;
            
            public UUIDChecker run() throws IOException {
                final CharBuffer allocate = CharBuffer.allocate(UUIDChecker.access$1(this.this$0));
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
    
    public UUIDChecker receive(final Writer writer) throws HttpRequestException {
        final BufferedReader bufferedReader = this.bufferedReader();
        return (UUIDChecker)new CloseOperation((Closeable)bufferedReader, this.ignoreCloseExceptions, bufferedReader, writer) {
            final UUIDChecker this$0;
            private final BufferedReader val$reader;
            private final Writer val$writer;
            
            public UUIDChecker run() throws IOException {
                return this.this$0.copy(this.val$reader, this.val$writer);
            }
            
            public Object run() throws HttpRequestException, IOException {
                return this.run();
            }
        }.call();
    }
    
    public UUIDChecker readTimeout(final int readTimeout) {
        this.getConnection().setReadTimeout(readTimeout);
        return this;
    }
    
    public UUIDChecker connectTimeout(final int connectTimeout) {
        this.getConnection().setConnectTimeout(connectTimeout);
        return this;
    }
    
    public UUIDChecker header(final String s, final String s2) {
        this.getConnection().setRequestProperty(s, s2);
        return this;
    }
    
    public UUIDChecker header(final String s, final Number n) {
        return this.header(s, (n != null) ? n.toString() : null);
    }
    
    public UUIDChecker headers(final Map map) {
        if (!map.isEmpty()) {
            final Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                this.header(iterator.next());
            }
        }
        return this;
    }
    
    public UUIDChecker header(final Map.Entry entry) {
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
            return UUIDChecker.EMPTY_STRINGS;
        }
        final List list = headers.get(s);
        if (list != null && !list.isEmpty()) {
            return (String[])list.toArray(new String[list.size()]);
        }
        return UUIDChecker.EMPTY_STRINGS;
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
        return this.parameter(UUIDChecker.lIIIIIlIIlIIllIl[69], UUIDChecker.lIIIIIlIIlIIllIl[70]);
    }
    
    public UUIDChecker userAgent(final String s) {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[71], s);
    }
    
    public UUIDChecker referer(final String s) {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[72], s);
    }
    
    public UUIDChecker useCaches(final boolean useCaches) {
        this.getConnection().setUseCaches(useCaches);
        return this;
    }
    
    public UUIDChecker acceptEncoding(final String s) {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[73], s);
    }
    
    public UUIDChecker acceptGzipEncoding() {
        return this.acceptEncoding(UUIDChecker.lIIIIIlIIlIIllIl[74]);
    }
    
    public UUIDChecker acceptCharset(final String s) {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[75], s);
    }
    
    public String contentEncoding() {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[76]);
    }
    
    public String server() {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[77]);
    }
    
    public long date() {
        return this.dateHeader(UUIDChecker.lIIIIIlIIlIIllIl[78]);
    }
    
    public String cacheControl() {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[79]);
    }
    
    public String eTag() {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[80]);
    }
    
    public long expires() {
        return this.dateHeader(UUIDChecker.lIIIIIlIIlIIllIl[81]);
    }
    
    public long lastModified() {
        return this.dateHeader(UUIDChecker.lIIIIIlIIlIIllIl[82]);
    }
    
    public String location() {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[83]);
    }
    
    public UUIDChecker authorization(final String s) {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[84], s);
    }
    
    public UUIDChecker proxyAuthorization(final String s) {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[85], s);
    }
    
    public UUIDChecker basic(final String s, final String s2) {
        return this.authorization(UUIDChecker.lIIIIIlIIlIIllIl[86] + Base64.encode(String.valueOf(s) + ':' + s2));
    }
    
    public UUIDChecker proxyBasic(final String s, final String s2) {
        return this.proxyAuthorization(UUIDChecker.lIIIIIlIIlIIllIl[87] + Base64.encode(String.valueOf(s) + ':' + s2));
    }
    
    public UUIDChecker ifModifiedSince(final long ifModifiedSince) {
        this.getConnection().setIfModifiedSince(ifModifiedSince);
        return this;
    }
    
    public UUIDChecker ifNoneMatch(final String s) {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[88], s);
    }
    
    public UUIDChecker contentType(final String s) {
        return this.contentType(s, null);
    }
    
    public UUIDChecker contentType(final String s, final String s2) {
        if (s2 != null && s2.length() > 0) {
            final String s3 = UUIDChecker.lIIIIIlIIlIIllIl[89];
            return this.header(UUIDChecker.lIIIIIlIIlIIllIl[90], String.valueOf(s) + UUIDChecker.lIIIIIlIIlIIllIl[91] + s2);
        }
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[92], s);
    }
    
    public String contentType() {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[93]);
    }
    
    public int contentLength() {
        return this.intHeader(UUIDChecker.lIIIIIlIIlIIllIl[94]);
    }
    
    public UUIDChecker contentLength(final String s) {
        return this.contentLength(Integer.parseInt(s));
    }
    
    public UUIDChecker contentLength(final int fixedLengthStreamingMode) {
        this.getConnection().setFixedLengthStreamingMode(fixedLengthStreamingMode);
        return this;
    }
    
    public UUIDChecker accept(final String s) {
        return this.header(UUIDChecker.lIIIIIlIIlIIllIl[95], s);
    }
    
    public UUIDChecker acceptJson() {
        return this.accept(UUIDChecker.lIIIIIlIIlIIllIl[96]);
    }
    
    protected UUIDChecker copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        return (UUIDChecker)new CloseOperation((Closeable)inputStream, this.ignoreCloseExceptions, inputStream, outputStream) {
            final UUIDChecker this$0;
            private final InputStream val$input;
            private final OutputStream val$output;
            
            public UUIDChecker run() throws IOException {
                final byte[] array = new byte[UUIDChecker.access$1(this.this$0)];
                int read;
                while ((read = this.val$input.read(array)) != -1) {
                    this.val$output.write(array, 0, read);
                    final UUIDChecker this$0 = this.this$0;
                    UUIDChecker.access$3(this$0, UUIDChecker.access$2(this$0) + read);
                    UUIDChecker.access$4(this.this$0).onUpload(UUIDChecker.access$2(this.this$0), UUIDChecker.access$5(this.this$0));
                }
                return this.this$0;
            }
            
            public Object run() throws HttpRequestException, IOException {
                return this.run();
            }
        }.call();
    }
    
    protected UUIDChecker copy(final Reader reader, final Writer writer) throws IOException {
        return (UUIDChecker)new CloseOperation((Closeable)reader, this.ignoreCloseExceptions, reader, writer) {
            final UUIDChecker this$0;
            private final Reader val$input;
            private final Writer val$output;
            
            public UUIDChecker run() throws IOException {
                final char[] array = new char[UUIDChecker.access$1(this.this$0)];
                int read;
                while ((read = this.val$input.read(array)) != -1) {
                    this.val$output.write(array, 0, read);
                    final UUIDChecker this$0 = this.this$0;
                    UUIDChecker.access$3(this$0, UUIDChecker.access$2(this$0) + read);
                    UUIDChecker.access$4(this.this$0).onUpload(UUIDChecker.access$2(this.this$0), -1L);
                }
                return this.this$0;
            }
            
            public Object run() throws HttpRequestException, IOException {
                return this.run();
            }
        }.call();
    }
    
    public UUIDChecker progress(final UploadProgress progress) {
        if (progress == null) {
            this.progress = UploadProgress.DEFAULT;
        }
        else {
            this.progress = progress;
        }
        return this;
    }
    
    private UUIDChecker incrementTotalSize(final long n) {
        if (this.totalSize == -1L) {
            this.totalSize = 0L;
        }
        this.totalSize += n;
        return this;
    }
    
    protected UUIDChecker closeOutput() throws IOException {
        this.progress(null);
        if (this.output == null) {
            return this;
        }
        if (this.multipart) {
            this.output.write(UUIDChecker.lIIIIIlIIlIIllIl[97]);
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
    
    protected UUIDChecker closeOutputQuietly() throws HttpRequestException {
        try {
            return this.closeOutput();
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
    }
    
    protected UUIDChecker openOutput() throws IOException {
        if (this.output != null) {
            return this;
        }
        this.getConnection().setDoOutput(true);
        this.output = new RequestOutputStream(this.getConnection().getOutputStream(), this.getParam(this.getConnection().getRequestProperty(UUIDChecker.lIIIIIlIIlIIllIl[98]), UUIDChecker.lIIIIIlIIlIIllIl[99]), this.bufferSize);
        return this;
    }
    
    protected UUIDChecker startPart() throws IOException {
        if (!this.multipart) {
            this.multipart = true;
            this.contentType(UUIDChecker.lIIIIIlIIlIIllIl[100]).openOutput();
            this.output.write(UUIDChecker.lIIIIIlIIlIIllIl[101]);
        }
        else {
            this.output.write(UUIDChecker.lIIIIIlIIlIIllIl[102]);
        }
        return this;
    }
    
    protected UUIDChecker writePartHeader(final String s, final String s2) throws IOException {
        return this.writePartHeader(s, s2, null);
    }
    
    protected UUIDChecker writePartHeader(final String s, final String s2, final String s3) throws IOException {
        final StringBuilder sb = new StringBuilder();
        sb.append(UUIDChecker.lIIIIIlIIlIIllIl[103]).append(s);
        if (s2 != null) {
            sb.append(UUIDChecker.lIIIIIlIIlIIllIl[104]).append(s2);
        }
        sb.append('\"');
        this.partHeader(UUIDChecker.lIIIIIlIIlIIllIl[105], sb.toString());
        if (s3 != null) {
            this.partHeader(UUIDChecker.lIIIIIlIIlIIllIl[106], s3);
        }
        return this.send(UUIDChecker.lIIIIIlIIlIIllIl[107]);
    }
    
    public UUIDChecker part(final String s, final String s2) {
        return this.part(s, null, s2);
    }
    
    public UUIDChecker part(final String s, final String s2, final String s3) throws HttpRequestException {
        return this.part(s, s2, null, s3);
    }
    
    public UUIDChecker part(final String s, final String s2, final String s3, final String s4) throws HttpRequestException {
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
    
    public UUIDChecker part(final String s, final Number n) throws HttpRequestException {
        return this.part(s, null, n);
    }
    
    public UUIDChecker part(final String s, final String s2, final Number n) throws HttpRequestException {
        return this.part(s, s2, (n != null) ? n.toString() : null);
    }
    
    public UUIDChecker part(final String s, final File file) throws HttpRequestException {
        return this.part(s, null, file);
    }
    
    public UUIDChecker part(final String s, final String s2, final File file) throws HttpRequestException {
        return this.part(s, s2, null, file);
    }
    
    public UUIDChecker part(final String s, final String s2, final String s3, final File file) throws HttpRequestException {
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
    
    public UUIDChecker part(final String s, final InputStream inputStream) throws HttpRequestException {
        return this.part(s, null, null, inputStream);
    }
    
    public UUIDChecker part(final String s, final String s2, final String s3, final InputStream inputStream) throws HttpRequestException {
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
    
    public UUIDChecker partHeader(final String s, final String s2) throws HttpRequestException {
        return this.send(s).send(UUIDChecker.lIIIIIlIIlIIllIl[108]).send(s2).send(UUIDChecker.lIIIIIlIIlIIllIl[109]);
    }
    
    public UUIDChecker send(final File file) throws HttpRequestException {
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
    
    public UUIDChecker send(final byte[] array) throws HttpRequestException {
        if (array != null) {
            this.incrementTotalSize(array.length);
        }
        return this.send(new ByteArrayInputStream(array));
    }
    
    public UUIDChecker send(final InputStream inputStream) throws HttpRequestException {
        try {
            this.openOutput();
            this.copy(inputStream, this.output);
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
        return this;
    }
    
    public UUIDChecker send(final Reader reader) throws HttpRequestException {
        try {
            this.openOutput();
        }
        catch (IOException ex) {
            throw new HttpRequestException(ex);
        }
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.output, RequestOutputStream.access$0(this.output).charset());
        return (UUIDChecker)new FlushOperation((Flushable)outputStreamWriter, reader, (Writer)outputStreamWriter) {
            final UUIDChecker this$0;
            private final Reader val$input;
            private final Writer val$writer;
            
            @Override
            protected UUIDChecker run() throws IOException {
                return this.this$0.copy(this.val$input, this.val$writer);
            }
            
            @Override
            protected Object run() throws HttpRequestException, IOException {
                return this.run();
            }
        }.call();
    }
    
    public UUIDChecker send(final CharSequence charSequence) throws HttpRequestException {
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
    
    public UUIDChecker form(final Map map) throws HttpRequestException {
        return this.form(map, UUIDChecker.lIIIIIlIIlIIllIl[110]);
    }
    
    public UUIDChecker form(final Map.Entry entry) throws HttpRequestException {
        return this.form(entry, UUIDChecker.lIIIIIlIIlIIllIl[111]);
    }
    
    public UUIDChecker form(final Map.Entry entry, final String s) throws HttpRequestException {
        return this.form(entry.getKey(), entry.getValue(), s);
    }
    
    public UUIDChecker form(final Object o, final Object o2) throws HttpRequestException {
        return this.form(o, o2, UUIDChecker.lIIIIIlIIlIIllIl[112]);
    }
    
    public UUIDChecker form(final Object o, final Object o2, String validCharset) throws HttpRequestException {
        final boolean b = !this.form;
        if (b) {
            this.contentType(UUIDChecker.lIIIIIlIIlIIllIl[113], validCharset);
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
    
    public UUIDChecker form(final Map map, final String s) throws HttpRequestException {
        if (!map.isEmpty()) {
            final Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                this.form(iterator.next(), s);
            }
        }
        return this;
    }
    
    public UUIDChecker trustAllCerts() throws HttpRequestException {
        final HttpURLConnection connection = this.getConnection();
        if (connection instanceof HttpsURLConnection) {
            ((HttpsURLConnection)connection).setSSLSocketFactory(getTrustedFactory());
        }
        return this;
    }
    
    public UUIDChecker trustAllHosts() {
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
    
    public UUIDChecker useProxy(final String httpProxyHost, final int httpProxyPort) {
        if (this.connection != null) {
            throw new IllegalStateException(UUIDChecker.lIIIIIlIIlIIllIl[114]);
        }
        this.httpProxyHost = httpProxyHost;
        this.httpProxyPort = httpProxyPort;
        return this;
    }
    
    public UUIDChecker followRedirects(final boolean instanceFollowRedirects) {
        this.getConnection().setInstanceFollowRedirects(instanceFollowRedirects);
        return this;
    }
    
    static String access$0(final String s) {
        return getValidCharset(s);
    }
    
    static int access$1(final UUIDChecker uuidChecker) {
        return uuidChecker.bufferSize;
    }
    
    static long access$2(final UUIDChecker uuidChecker) {
        return uuidChecker.totalWritten;
    }
    
    static void access$3(final UUIDChecker uuidChecker, final long totalWritten) {
        uuidChecker.totalWritten = totalWritten;
    }
    
    static UploadProgress access$4(final UUIDChecker uuidChecker) {
        return uuidChecker.progress;
    }
    
    static long access$5(final UUIDChecker uuidChecker) {
        return uuidChecker.totalSize;
    }
    
    private static void llIIIIIIIlIlllll() {
        (lIIIIIlIIlIIllIl = new String[115])[0] = llIIIIIIIlIlIIll(UUIDChecker.lIIIIIlIIlIlIlIl[0], UUIDChecker.lIIIIIlIIlIlIlIl[1]);
        UUIDChecker.lIIIIIlIIlIIllIl[1] = llIIIIIIIlIlIlII(UUIDChecker.lIIIIIlIIlIlIlIl[2], UUIDChecker.lIIIIIlIIlIlIlIl[3]);
        UUIDChecker.lIIIIIlIIlIIllIl[2] = llIIIIIIIlIlIlIl(UUIDChecker.lIIIIIlIIlIlIlIl[4], UUIDChecker.lIIIIIlIIlIlIlIl[5]);
        UUIDChecker.lIIIIIlIIlIIllIl[3] = llIIIIIIIlIlIllI(UUIDChecker.lIIIIIlIIlIlIlIl[6], UUIDChecker.lIIIIIlIIlIlIlIl[7]);
        UUIDChecker.lIIIIIlIIlIIllIl[4] = llIIIIIIIlIlIlII(UUIDChecker.lIIIIIlIIlIlIlIl[8], UUIDChecker.lIIIIIlIIlIlIlIl[9]);
        UUIDChecker.lIIIIIlIIlIIllIl[5] = llIIIIIIIlIlIlII(UUIDChecker.lIIIIIlIIlIlIlIl[10], UUIDChecker.lIIIIIlIIlIlIlIl[11]);
        UUIDChecker.lIIIIIlIIlIIllIl[6] = llIIIIIIIlIlIlII(UUIDChecker.lIIIIIlIIlIlIlIl[12], UUIDChecker.lIIIIIlIIlIlIlIl[13]);
        UUIDChecker.lIIIIIlIIlIIllIl[7] = llIIIIIIIlIlIlIl(UUIDChecker.lIIIIIlIIlIlIlIl[14], UUIDChecker.lIIIIIlIIlIlIlIl[15]);
        UUIDChecker.lIIIIIlIIlIIllIl[8] = llIIIIIIIlIlIIll(UUIDChecker.lIIIIIlIIlIlIlIl[16], UUIDChecker.lIIIIIlIIlIlIlIl[17]);
        UUIDChecker.lIIIIIlIIlIIllIl[9] = llIIIIIIIlIlIlIl(UUIDChecker.lIIIIIlIIlIlIlIl[18], UUIDChecker.lIIIIIlIIlIlIlIl[19]);
        UUIDChecker.lIIIIIlIIlIIllIl[10] = llIIIIIIIlIlIllI(UUIDChecker.lIIIIIlIIlIlIlIl[20], UUIDChecker.lIIIIIlIIlIlIlIl[21]);
        UUIDChecker.lIIIIIlIIlIIllIl[11] = llIIIIIIIlIlIIll(UUIDChecker.lIIIIIlIIlIlIlIl[22], UUIDChecker.lIIIIIlIIlIlIlIl[23]);
        UUIDChecker.lIIIIIlIIlIIllIl[12] = llIIIIIIIlIlIlII(UUIDChecker.lIIIIIlIIlIlIlIl[24], UUIDChecker.lIIIIIlIIlIlIlIl[25]);
        UUIDChecker.lIIIIIlIIlIIllIl[13] = llIIIIIIIlIlIlIl(UUIDChecker.lIIIIIlIIlIlIlIl[26], UUIDChecker.lIIIIIlIIlIlIlIl[27]);
        UUIDChecker.lIIIIIlIIlIIllIl[14] = llIIIIIIIlIlIlIl(UUIDChecker.lIIIIIlIIlIlIlIl[28], UUIDChecker.lIIIIIlIIlIlIlIl[29]);
        UUIDChecker.lIIIIIlIIlIIllIl[15] = llIIIIIIIlIlIllI(UUIDChecker.lIIIIIlIIlIlIlIl[30], UUIDChecker.lIIIIIlIIlIlIlIl[31]);
        UUIDChecker.lIIIIIlIIlIIllIl[16] = llIIIIIIIlIlIllI(UUIDChecker.lIIIIIlIIlIlIlIl[32], UUIDChecker.lIIIIIlIIlIlIlIl[33]);
        UUIDChecker.lIIIIIlIIlIIllIl[17] = llIIIIIIIlIlIlIl(UUIDChecker.lIIIIIlIIlIlIlIl[34], "tfXJE");
        UUIDChecker.lIIIIIlIIlIIllIl[18] = llIIIIIIIlIlIlII("TBc/FbrIe9w=", "qgFaZ");
        UUIDChecker.lIIIIIlIIlIIllIl[19] = llIIIIIIIlIlIlII("jJV+joTUvcpYjrYAHmZoWA==", "oXKMW");
        UUIDChecker.lIIIIIlIIlIIllIl[20] = llIIIIIIIlIlIllI("qtx/8VvsuuQIZ3eFtK2QmpfvNF50Hgls+cbZLXBlz/mEJeo4YRAl4YFmU+9yskSndxVdaeD6my0OCdbA+okQEQ==", "ikDWN");
        UUIDChecker.lIIIIIlIIlIIllIl[21] = llIIIIIIIlIlIIll("Prfq0Lb5c+0=", "OofqQ");
        UUIDChecker.lIIIIIlIIlIIllIl[22] = llIIIIIIIlIlIllI("UtFWiIT83nhOJawgbeBifg==", "btCOm");
        UUIDChecker.lIIIIIlIIlIIllIl[23] = llIIIIIIIlIlIlII("bcx9bG10wQo=", "TKrYU");
        UUIDChecker.lIIIIIlIIlIIllIl[24] = llIIIIIIIlIlIlII("OJgaXfFTIW52DeaqmgdjqA==", "qPerY");
        UUIDChecker.lIIIIIlIIlIIllIl[25] = llIIIIIIIlIlIlIl("KiIOJDwdNQ==", "xGhAN");
        UUIDChecker.lIIIIIlIIlIIllIl[26] = llIIIIIIIlIlIlIl("Jx0WFg==", "wREBK");
        UUIDChecker.lIIIIIlIIlIIllIl[27] = llIIIIIIIlIlIllI("01Mokg6YbTF9Zx3OH8lhMIMGR4sNsZpvYoRVAn/MSPyphY4TYMQMW5jjJ9IAcKQx", "azPHh");
        UUIDChecker.lIIIIIlIIlIIllIl[28] = llIIIIIIIlIlIlII("W+bSbGfLaUE=", "vccHX");
        UUIDChecker.lIIIIIlIIlIIllIl[29] = llIIIIIIIlIlIlII("C+iFdLvxKSg=", "Qnffl");
        UUIDChecker.lIIIIIlIIlIIllIl[30] = llIIIIIIIlIlIllI("VzPJfdADrVJXatWBXKvYbA==", "SVPCh");
        UUIDChecker.lIIIIIlIIlIIllIl[31] = llIIIIIIIlIlIlIl("HzczJT0=", "Kerfx");
        UUIDChecker.lIIIIIlIIlIIllIl[32] = llIIIIIIIlIlIlII("0KFA3x/BddW7cKsfDg7F1g==", "AbGUO");
        UUIDChecker.lIIIIIlIIlIIllIl[33] = llIIIIIIIlIlIlIl("BQIIAR4HEwwEGApdEh4YCg==", "drxmw");
        UUIDChecker.lIIIIIlIIlIIllIl[34] = llIIIIIIIlIlIlIl("HxsCWn0=", "JODwE");
        UUIDChecker.lIIIIIlIIlIIllIl[35] = llIIIIIIIlIlIlIl("Ay05", "Wajwy");
        UUIDChecker.lIIIIIlIIlIIllIl[36] = llIIIIIIIlIlIlIl("OgwsJzEAHTZyJhEKKiI3AAYhciAGByk7JBwbJjwkSTocHmMKBiEmJhEd", "iiORC");
        UUIDChecker.lIIIIIlIIlIIllIl[37] = llIIIIIIIlIlIlII("ZVrt29l++FY=", "XCkiI");
        UUIDChecker.lIIIIIlIIlIIllIl[38] = llIIIIIIIlIlIIll("y72mMxLJWlY=", "HfGoq");
        UUIDChecker.lIIIIIlIIlIIllIl[39] = llIIIIIIIlIlIlIl("VA==", "iKPXu");
        UUIDChecker.lIIIIIlIIlIIllIl[40] = llIIIIIIIlIlIIll("8rqDHwcTiyw=", "StqPZ");
        UUIDChecker.lIIIIIlIIlIIllIl[41] = llIIIIIIIlIlIllI("P4M2vt54BdggOa3Pd/nMrA==", "DKNKq");
        UUIDChecker.lIIIIIlIIlIIllIl[42] = llIIIIIIIlIlIIll("4L5rc6i0rpxns7iB8pFMNTEDohZOiA0z", "xEOJv");
        UUIDChecker.lIIIIIlIIlIIllIl[43] = llIIIIIIIlIlIllI("v6BunoINFfHGnosbLuFj2Zj2EgHNFoL5wRXy5kpbovTvdRaKtqmJKbiE7D4WR1L2bZYkfOkj0Lr7g6gXpsXXuA==", "oJhjH");
        UUIDChecker.lIIIIIlIIlIIllIl[44] = llIIIIIIIlIlIllI("ieY+OOrZgUGUQq6SilZFhQ==", "rmweL");
        UUIDChecker.lIIIIIlIIlIIllIl[45] = llIIIIIIIlIlIIll("kJBXQEDr00o=", "FtqIR");
        UUIDChecker.lIIIIIlIIlIIllIl[46] = llIIIIIIIlIlIlIl("Mis0Fw==", "bdgCZ");
        UUIDChecker.lIIIIIlIIlIIllIl[47] = llIIIIIIIlIlIllI("pCXnnH4NFyh4acwW47i5jA==", "sfIoa");
        UUIDChecker.lIIIIIlIIlIIllIl[48] = llIIIIIIIlIlIllI("5KH4uLeoJk1E3ruxaAPaAw==", "AOoKj");
        UUIDChecker.lIIIIIlIIlIIllIl[49] = llIIIIIIIlIlIIll("8Y1On3cfj/I=", "rldnL");
        UUIDChecker.lIIIIIlIIlIIllIl[50] = llIIIIIIIlIlIlIl("Px0Y", "oHLOI");
        UUIDChecker.lIIIIIlIIlIIllIl[51] = llIIIIIIIlIlIlIl("CRw9JDsI", "MYqao");
        UUIDChecker.lIIIIIlIIlIIllIl[52] = llIIIIIIIlIlIIll("XgMeSTxtRa0=", "RvhoU");
        UUIDChecker.lIIIIIlIIlIIllIl[53] = llIIIIIIIlIlIlIl("Pw8HCg==", "wJFNK");
        UUIDChecker.lIIIIIlIIlIIllIl[54] = llIIIIIIIlIlIlIl("MjQOIw==", "zqOgK");
        UUIDChecker.lIIIIIlIIlIIllIl[55] = llIIIIIIIlIlIlII("JyP6ATbsSOA=", "pBGdv");
        UUIDChecker.lIIIIIlIIlIIllIl[56] = llIIIIIIIlIlIllI("2TPufiW/igc081w9+6lFEA==", "JmdVg");
        UUIDChecker.lIIIIIlIIlIIllIl[57] = llIIIIIIIlIlIlII("00bflMuK0NY=", "KmQiC");
        UUIDChecker.lIIIIIlIIlIIllIl[58] = llIIIIIIIlIlIlII("bAoSwlPpE00=", "reitn");
        UUIDChecker.lIIIIIlIIlIIllIl[59] = llIIIIIIIlIlIlIl("CjYgBkoJJzEGJQ4rIhM=", "bBTvd");
        UUIDChecker.lIIIIIlIIlIIllIl[60] = llIIIIIIIlIlIlII("yUzV2pl4VyiGqSHSSrWHhzGiB/CT9ntD", "PwCnF");
        UUIDChecker.lIIIIIlIIlIIllIl[61] = llIIIIIIIlIlIllI("f4bEaL9zO4WZab+LggHrmQ==", "HntdU");
        UUIDChecker.lIIIIIlIIlIIllIl[62] = llIIIIIIIlIlIllI("c8GVFzNXpRcTdibJvv271A==", "WXDlS");
        UUIDChecker.lIIIIIlIIlIIllIl[63] = llIIIIIIIlIlIlII("B9fX2Zu58Cl7d19M6LaMNQ==", "pkdTy");
        UUIDChecker.lIIIIIlIIlIIllIl[64] = llIIIIIIIlIlIlIl("Ijo6JjVkPjw5PjMeISQy", "JNNVF");
        UUIDChecker.lIIIIIlIIlIIllIl[65] = llIIIIIIIlIlIlIl("ChAbPmEMCwEePQ0cFgYgERAc", "bdoNO");
        UUIDChecker.lIIIIIlIIlIIllIl[66] = llIIIIIIIlIlIllI("Lgqptl7U4as8VpxdxdzwTT5kJnKTSsBHQf+HpdsYpcU=", "AhaYN");
        UUIDChecker.lIIIIIlIIlIIllIl[67] = llIIIIIIIlIlIllI("jlYvivxcsHIziuwIRwtkFdvFborPtfxf+VkzACGkjQ0=", "MIwvi");
        UUIDChecker.lIIIIIlIIlIIllIl[68] = llIIIIIIIlIlIlII("hAtVz4Q3Y2U=", "hfRHE");
        UUIDChecker.lIIIIIlIIlIIllIl[69] = llIIIIIIIlIlIlIl("JisIDQgLMEstFBUh", "eDfym");
        UUIDChecker.lIIIIIlIIlIIllIl[70] = llIIIIIIIlIlIlIl("BiQ3PzsAOA==", "eLVMH");
        UUIDChecker.lIIIIIlIIlIIllIl[71] = llIIIIIIIlIlIlII("eN/3/MweBv8996gkqMJdGA==", "EkZuB");
        UUIDChecker.lIIIIIlIIlIIllIl[72] = llIIIIIIIlIlIlII("MqAxlCsd2to=", "pVSaI");
        UUIDChecker.lIIIIIlIIlIIllIl[73] = llIIIIIIIlIlIlIl("LQcTFQIYSTUeEQMAGR4V", "ldppr");
        UUIDChecker.lIIIIIlIIlIIllIl[74] = llIIIIIIIlIlIlIl("Ey8PCA==", "tUfxl");
        UUIDChecker.lIIIIIlIIlIIllIl[75] = llIIIIIIIlIlIIll("j+zJXQ9VV7hdgwQFLE8jRQ==", "Blhby");
        UUIDChecker.lIIIIIlIIlIIllIl[76] = llIIIIIIIlIlIllI("tBWSYu7bDsVk2lnDqv0deS5MyS8pgaac+yFgkVbYBm4=", "KzoWR");
        UUIDChecker.lIIIIIlIIlIIllIl[77] = llIIIIIIIlIlIlII("wsRR9vhN7yU=", "nLaKf");
        UUIDChecker.lIIIIIlIIlIIllIl[78] = llIIIIIIIlIlIlIl("JTc8Pw==", "aVHZe");
        UUIDChecker.lIIIIIlIIlIIllIl[79] = llIIIIIIIlIlIlIl("MyUaEA9dBxYWHgIrFQ==", "pDyxj");
        UUIDChecker.lIIIIIlIIlIIllIl[80] = llIIIIIIIlIlIllI("ViPeYnj7cepy6KB3Kz7oIw==", "lOHSe");
        UUIDChecker.lIIIIIlIIlIIllIl[81] = llIIIIIIIlIlIlII("zsl0mfNnmS4=", "wiBeH");
        UUIDChecker.lIIIIIlIIlIIllIl[82] = llIIIIIIIlIlIlII("Wgf102HeaLUJ1zc+qkBB+g==", "wDBnc");
        UUIDChecker.lIIIIIlIIlIIllIl[83] = llIIIIIIIlIlIlII("k1w5xa1HpjG1KYEP+wB6JQ==", "LtRYm");
        UUIDChecker.lIIIIIlIIlIIllIl[84] = llIIIIIIIlIlIlIl("DTsMLSc+JwIkPCUhFg==", "LNxEH");
        UUIDChecker.lIIIIIlIIlIIllIl[85] = llIIIIIIIlIlIlII("NwiFQSDSKXnxxKvVg8vLYYEfuvmO8GGQ", "GohGN");
        UUIDChecker.lIIIIIlIIlIIllIl[86] = llIIIIIIIlIlIlII("JolzRFbJXMA=", "ikgoI");
        UUIDChecker.lIIIIIlIIlIIllIl[87] = llIIIIIIIlIlIIll("THX5ZlG1PWM=", "ZiSdx");
        UUIDChecker.lIIIIIlIIlIIllIl[88] = llIIIIIIIlIlIllI("SkSMF4Y0IvnjUaWF8K5V0w==", "ufMKl");
        UUIDChecker.lIIIIIlIIlIIllIl[89] = llIIIIIIIlIlIIll("YcI7EKubWoamOzJnyF6KzA==", "OWTPv");
        UUIDChecker.lIIIIIlIIlIIllIl[90] = llIIIIIIIlIlIIll("B5ysGs8bNokiVlBrAuQHVw==", "tNbfl");
        UUIDChecker.lIIIIIlIIlIIllIl[91] = llIIIIIIIlIlIIll("YupRf8CMKvzEutfSWNDyXg==", "PRLIY");
        UUIDChecker.lIIIIIlIIlIIllIl[92] = llIIIIIIIlIlIllI("927byltlQsFJRqdMsZjzoQ==", "dVPjV");
        UUIDChecker.lIIIIIlIIlIIllIl[93] = llIIIIIIIlIlIIll("PCcvZAW/SItnU/Xj9/Ec7A==", "XPFnW");
        UUIDChecker.lIIIIIlIIlIIllIl[94] = llIIIIIIIlIlIlIl("MhUXMycfDlQLJx8dDS8=", "qzyGB");
        UUIDChecker.lIIIIIlIIlIIllIl[95] = llIIIIIIIlIlIIll("HEqLs8s3+vg=", "fYnrQ");
        UUIDChecker.lIIIIIlIIlIIllIl[96] = llIIIIIIIlIlIlIl("MzkbFiIxKB8TJDxmAQkkPA==", "RIkzK");
        UUIDChecker.lIIIIIlIIlIIllIl[97] = llIIIIIIIlIlIlIl("aE5/WlxVJz0ZGAAqJkcOCjE8Ew0XPWJHQUhJWA==", "eDRwl");
        UUIDChecker.lIIIIIlIIlIIllIl[98] = llIIIIIIIlIlIIll("kCHAt+M39hTabcL78FgulQ==", "qvYuY");
        UUIDChecker.lIIIIIlIIlIIllIl[99] = llIIIIIIIlIlIlIl("ISYLOTwnOg==", "BNjKO");
        UUIDChecker.lIIIIIlIIlIIllIl[100] = llIIIIIIIlIlIllI("5jeQiOT2qV8NHfOr31+ndLqEHKleiIJwaWJfEAkn1NgsmABTp2booNnjbxWQIVF+iAPmdOzrikSIHX3nOG0EsA==", "RzLCu");
        UUIDChecker.lIIIIIlIIlIIllIl[101] = llIIIIIIIlIlIIll("KfP9LWaYMikfrRPmyVdWgU4Vk6/P15AQb+k00HA7iLc=", "Exusg");
        UUIDChecker.lIIIIIlIIlIIllIl[102] = llIIIIIIIlIlIIll("HJX9RlU6zdB5Ynpi6/WXcD7bTfTxp0yB3ycfcmY90pw=", "OYbNf");
        UUIDChecker.lIIIIIlIIlIIllIl[103] = llIIIIIIIlIlIlIl("BCoRL0AGJBcjVkIrAi8IX2c=", "bEcBm");
        UUIDChecker.lIIIIIlIIlIIllIl[104] = llIIIIIIIlIlIllI("oqIvLbupo9g/uSU5q4jrUA==", "pPJyT");
        UUIDChecker.lIIIIIlIIlIIllIl[105] = llIIIIIIIlIlIIll("jYaUJobCXc222cuq6Q+ryUDJTRHG1Lb3", "YKIzB");
        UUIDChecker.lIIIIIlIIlIIllIl[106] = llIIIIIIIlIlIlII("oMOWG/3Y8qJyhE/uH2WjfA==", "XTpHW");
        UUIDChecker.lIIIIIlIIlIIllIl[107] = llIIIIIIIlIlIlIl("bEg=", "aBcRC");
        UUIDChecker.lIIIIIlIIlIIllIl[108] = llIIIIIIIlIlIIll("1rc/47GHc04=", "PIkjn");
        UUIDChecker.lIIIIIlIIlIIllIl[109] = llIIIIIIIlIlIIll("3T8Mbo1Ouyk=", "ZXnCG");
        UUIDChecker.lIIIIIlIIlIIllIl[110] = llIIIIIIIlIlIllI("amO6zkzXQXvBqAJls3yD9g==", "KfYez");
        UUIDChecker.lIIIIIlIIlIIllIl[111] = llIIIIIIIlIlIllI("g6Wdp+O6zLwnVz6/0oaunQ==", "JTyYP");
        UUIDChecker.lIIIIIlIIlIIllIl[112] = llIIIIIIIlIlIllI("JTgmxsfKAXFVaWbV7o7P5A==", "YFTmC");
        UUIDChecker.lIIIIIlIIlIIllIl[113] = llIIIIIIIlIlIlII("s66ntSpaj4/AmK1tMzRB7biJk//WvDnk6zAN6GjmDx6goQSCgTp+zg==", "pPQEG");
        UUIDChecker.lIIIIIlIIlIIllIl[114] = llIIIIIIIlIlIIll("Qy9MMBO1O7TUcKUqlEFt1gxNa2X0sf0XtVwuP2x9fc02m9LTZt6F9Xvzy1brJ9bbnlOYsxwIGu57NQ5Ld0Un4KLLczD5CIAKdeJU2/HAySoPKEVTrz7ik7IwzBRNhIptf8LeOFw+E+azOTrMekzkjg==", "FIAvE");
        UUIDChecker.lIIIIIlIIlIlIlIl = null;
    }
    
    private static void llIIIIIIIllIIIII() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        UUIDChecker.lIIIIIlIIlIlIlIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String llIIIIIIIlIlIlIl(String s, final String s2) {
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
    
    private static String llIIIIIIIlIlIIll(final String s, final String s2) {
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
    
    private static String llIIIIIIIlIlIlII(final String s, final String s2) {
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
    
    private static String llIIIIIIIlIlIllI(final String s, final String s2) {
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
            this.encoder = Charset.forName(UUIDChecker.access$0(s)).newEncoder();
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
