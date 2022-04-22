package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import java.nio.charset.*;
import org.apache.http.protocol.*;
import org.apache.http.auth.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.message.*;
import java.security.*;

@NotThreadSafe
public class DigestScheme extends RFC2617Scheme
{
    private static final char[] HEXADECIMAL;
    private boolean complete;
    private static final int QOP_UNKNOWN = -1;
    private static final int QOP_MISSING = 0;
    private static final int QOP_AUTH_INT = 1;
    private static final int QOP_AUTH = 2;
    private String lastNonce;
    private long nounceCount;
    private String cnonce;
    private String a1;
    private String a2;
    
    public DigestScheme(final Charset charset) {
        super(charset);
        this.complete = false;
    }
    
    @Deprecated
    public DigestScheme(final ChallengeState challengeState) {
        super(challengeState);
    }
    
    public DigestScheme() {
        this(Consts.ASCII);
    }
    
    @Override
    public void processChallenge(final Header header) throws MalformedChallengeException {
        super.processChallenge(header);
        this.complete = true;
    }
    
    public boolean isComplete() {
        return !"true".equalsIgnoreCase(this.getParameter("stale")) && this.complete;
    }
    
    public String getSchemeName() {
        return "digest";
    }
    
    public boolean isConnectionBased() {
        return false;
    }
    
    public void overrideParamter(final String s, final String s2) {
        this.getParameters().put(s, s2);
    }
    
    @Deprecated
    public Header authenticate(final Credentials credentials, final HttpRequest httpRequest) throws AuthenticationException {
        return this.authenticate(credentials, httpRequest, new BasicHttpContext());
    }
    
    @Override
    public Header authenticate(final Credentials credentials, final HttpRequest httpRequest, final HttpContext httpContext) throws AuthenticationException {
        Args.notNull(credentials, "Credentials");
        Args.notNull(httpRequest, "HTTP request");
        if (this.getParameter("realm") == null) {
            throw new AuthenticationException("missing realm in challenge");
        }
        if (this.getParameter("nonce") == null) {
            throw new AuthenticationException("missing nonce in challenge");
        }
        this.getParameters().put("methodname", httpRequest.getRequestLine().getMethod());
        this.getParameters().put("uri", httpRequest.getRequestLine().getUri());
        if (this.getParameter("charset") == null) {
            this.getParameters().put("charset", this.getCredentialsCharset(httpRequest));
        }
        return this.createDigestHeader(credentials, httpRequest);
    }
    
    private static MessageDigest createMessageDigest(final String s) throws UnsupportedDigestAlgorithmException {
        return MessageDigest.getInstance(s);
    }
    
    private Header createDigestHeader(final Credentials credentials, final HttpRequest httpRequest) throws AuthenticationException {
        final String parameter = this.getParameter("uri");
        final String parameter2 = this.getParameter("realm");
        final String parameter3 = this.getParameter("nonce");
        final String parameter4 = this.getParameter("opaque");
        final String parameter5 = this.getParameter("methodname");
        String parameter6 = this.getParameter("algorithm");
        if (parameter6 == null) {
            parameter6 = "MD5";
        }
        final HashSet<String> set = new HashSet<String>(8);
        final String parameter7 = this.getParameter("qop");
        if (parameter7 != null) {
            final StringTokenizer stringTokenizer = new StringTokenizer(parameter7, ",");
            while (stringTokenizer.hasMoreTokens()) {
                set.add(stringTokenizer.nextToken().trim().toLowerCase(Locale.US));
            }
            if (!(httpRequest instanceof HttpEntityEnclosingRequest) || !set.contains("auth-int")) {
                if (set.contains("auth")) {}
            }
        }
        String parameter8 = this.getParameter("charset");
        if (parameter8 == null) {
            parameter8 = "ISO-8859-1";
        }
        String s = parameter6;
        if (s.equalsIgnoreCase("MD5-sess")) {
            s = "MD5";
        }
        final MessageDigest messageDigest = createMessageDigest(s);
        final String name = credentials.getUserPrincipal().getName();
        final String password = credentials.getPassword();
        if (parameter3.equals(this.lastNonce)) {
            ++this.nounceCount;
        }
        else {
            this.nounceCount = 1L;
            this.cnonce = null;
            this.lastNonce = parameter3;
        }
        final StringBuilder sb = new StringBuilder(256);
        final Formatter formatter = new Formatter(sb, Locale.US);
        formatter.format("%08x", this.nounceCount);
        formatter.close();
        final String string = sb.toString();
        if (this.cnonce == null) {
            this.cnonce = createCnonce();
        }
        this.a1 = null;
        this.a2 = null;
        if (parameter6.equalsIgnoreCase("MD5-sess")) {
            sb.setLength(0);
            sb.append(name).append(':').append(parameter2).append(':').append(password);
            final String encode = encode(messageDigest.digest(EncodingUtils.getBytes(sb.toString(), parameter8)));
            sb.setLength(0);
            sb.append(encode).append(':').append(parameter3).append(':').append(this.cnonce);
            this.a1 = sb.toString();
        }
        else {
            sb.setLength(0);
            sb.append(name).append(':').append(parameter2).append(':').append(password);
            this.a1 = sb.toString();
        }
        final String encode2 = encode(messageDigest.digest(EncodingUtils.getBytes(this.a1, parameter8)));
        this.a2 = parameter5 + ':' + parameter;
        final String encode3 = encode(messageDigest.digest(EncodingUtils.getBytes(this.a2, parameter8)));
        sb.setLength(0);
        sb.append(encode2).append(':').append(parameter3).append(':').append(string).append(':').append(this.cnonce).append(':').append("auth").append(':').append(encode3);
        final String encode4 = encode(messageDigest.digest(EncodingUtils.getAsciiBytes(sb.toString())));
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(128);
        if (this.isProxy()) {
            charArrayBuffer.append("Proxy-Authorization");
        }
        else {
            charArrayBuffer.append("Authorization");
        }
        charArrayBuffer.append(": Digest ");
        final ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(20);
        list.add(new BasicNameValuePair("username", name));
        list.add(new BasicNameValuePair("realm", parameter2));
        list.add(new BasicNameValuePair("nonce", parameter3));
        list.add(new BasicNameValuePair("uri", parameter));
        list.add(new BasicNameValuePair("response", encode4));
        list.add(new BasicNameValuePair("algorithm", parameter6));
        if (parameter4 != null) {
            list.add(new BasicNameValuePair("opaque", parameter4));
        }
        while (0 < list.size()) {
            final BasicNameValuePair basicNameValuePair = list.get(0);
            final String name2 = basicNameValuePair.getName();
            BasicHeaderValueFormatter.INSTANCE.formatNameValuePair(charArrayBuffer, basicNameValuePair, !"nc".equals(name2) && !"qop".equals(name2) && !"algorithm".equals(name2));
            int n = 0;
            ++n;
        }
        return new BufferedHeader(charArrayBuffer);
    }
    
    String getCnonce() {
        return this.cnonce;
    }
    
    String getA1() {
        return this.a1;
    }
    
    String getA2() {
        return this.a2;
    }
    
    static String encode(final byte[] array) {
        final int length = array.length;
        final char[] array2 = new char[length * 2];
        while (0 < length) {
            final int n = array[0] & 0xF;
            array2[0] = DigestScheme.HEXADECIMAL[(array[0] & 0xF0) >> 4];
            array2[1] = DigestScheme.HEXADECIMAL[n];
            int n2 = 0;
            ++n2;
        }
        return new String(array2);
    }
    
    public static String createCnonce() {
        final SecureRandom secureRandom = new SecureRandom();
        final byte[] array = new byte[8];
        secureRandom.nextBytes(array);
        return encode(array);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DIGEST [complete=").append(this.complete).append(", nonce=").append(this.lastNonce).append(", nc=").append(this.nounceCount).append("]");
        return sb.toString();
    }
    
    static {
        HEXADECIMAL = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    }
}
