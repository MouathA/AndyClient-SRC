package org.apache.http.conn.ssl;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import java.security.cert.*;
import java.io.*;
import javax.net.ssl.*;
import java.util.*;
import org.apache.http.conn.util.*;
import java.net.*;

@Immutable
public abstract class AbstractVerifier implements X509HostnameVerifier
{
    private final Log log;
    
    public AbstractVerifier() {
        this.log = LogFactory.getLog(this.getClass());
    }
    
    public final void verify(final String s, final SSLSocket sslSocket) throws IOException {
        if (s == null) {
            throw new NullPointerException("host to verify is null");
        }
        SSLSession sslSession = sslSocket.getSession();
        if (sslSession == null) {
            sslSocket.getInputStream().available();
            sslSession = sslSocket.getSession();
            if (sslSession == null) {
                sslSocket.startHandshake();
                sslSession = sslSocket.getSession();
            }
        }
        this.verify(s, (X509Certificate)sslSession.getPeerCertificates()[0]);
    }
    
    public final boolean verify(final String s, final SSLSession sslSession) {
        this.verify(s, (X509Certificate)sslSession.getPeerCertificates()[0]);
        return true;
    }
    
    public final void verify(final String s, final X509Certificate x509Certificate) throws SSLException {
        this.verify(s, getCNs(x509Certificate), getSubjectAlts(x509Certificate, s));
    }
    
    public final void verify(final String s, final String[] array, final String[] array2, final boolean b) throws SSLException {
        final LinkedList<String> list = new LinkedList<String>();
        if (array != null && array.length > 0 && array[0] != null) {
            list.add(array[0]);
        }
        if (array2 != null) {
            while (0 < array2.length) {
                final String s2 = array2[0];
                if (s2 != null) {
                    list.add(s2);
                }
                int n = 0;
                ++n;
            }
        }
        if (list.isEmpty()) {
            throw new SSLException("Certificate for <" + s + "> doesn't contain CN or DNS subjectAlt");
        }
        final StringBuilder sb = new StringBuilder();
        final String normaliseIPv6Address = this.normaliseIPv6Address(s.trim().toLowerCase(Locale.US));
        final Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            final String lowerCase = iterator.next().toLowerCase(Locale.US);
            sb.append(" <");
            sb.append(lowerCase);
            sb.append('>');
            if (iterator.hasNext()) {
                sb.append(" OR");
            }
            final String[] split = lowerCase.split("\\.");
            if (split.length >= 3 && split[0].endsWith("*") && this.validCountryWildcard(lowerCase) && !isIPAddress(s)) {
                final String s3 = split[0];
                if (s3.length() > 1) {
                    final String substring = s3.substring(0, s3.length() - 1);
                    final String substring2 = lowerCase.substring(s3.length());
                    final String substring3 = normaliseIPv6Address.substring(substring.length());
                    final int n = (normaliseIPv6Address.startsWith(substring) && substring3.endsWith(substring2)) ? 1 : 0;
                }
                else {
                    final int n = normaliseIPv6Address.endsWith(lowerCase.substring(1)) ? 1 : 0;
                }
                if (false && b) {
                    final int n = (countDots(normaliseIPv6Address) == countDots(lowerCase)) ? 1 : 0;
                }
            }
            else {
                final int n = normaliseIPv6Address.equals(this.normaliseIPv6Address(lowerCase)) ? 1 : 0;
            }
            if (false) {
                break;
            }
        }
        if (!false) {
            throw new SSLException("hostname in certificate didn't match: <" + s + "> !=" + (Object)sb);
        }
    }
    
    @Deprecated
    public static boolean acceptableCountryWildcard(final String s) {
        final String[] split = s.split("\\.");
        return split.length != 3 || split[2].length() != 2 || Arrays.binarySearch(AbstractVerifier.BAD_COUNTRY_2LDS, split[1]) < 0;
    }
    
    boolean validCountryWildcard(final String s) {
        final String[] split = s.split("\\.");
        return split.length != 3 || split[2].length() != 2 || Arrays.binarySearch(AbstractVerifier.BAD_COUNTRY_2LDS, split[1]) < 0;
    }
    
    public static String[] getCNs(final X509Certificate x509Certificate) {
        final LinkedList<String> list = new LinkedList<String>();
        final StringTokenizer stringTokenizer = new StringTokenizer(x509Certificate.getSubjectX500Principal().toString(), ",+");
        while (stringTokenizer.hasMoreTokens()) {
            final String trim = stringTokenizer.nextToken().trim();
            if (trim.length() > 3 && trim.substring(0, 3).equalsIgnoreCase("CN=")) {
                list.add(trim.substring(3));
            }
        }
        if (!list.isEmpty()) {
            final String[] array = new String[list.size()];
            list.toArray(array);
            return array;
        }
        return null;
    }
    
    private static String[] getSubjectAlts(final X509Certificate x509Certificate, final String s) {
        if (isIPAddress(s)) {}
        final LinkedList<String> list = new LinkedList<String>();
        final Collection<List<?>> subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
        if (subjectAlternativeNames != null) {
            for (final List<Integer> list2 : subjectAlternativeNames) {
                if (list2.get(0) == 2) {
                    list.add((String)list2.get(1));
                }
            }
        }
        if (!list.isEmpty()) {
            final String[] array = new String[list.size()];
            list.toArray(array);
            return array;
        }
        return null;
    }
    
    public static String[] getDNSSubjectAlts(final X509Certificate x509Certificate) {
        return getSubjectAlts(x509Certificate, null);
    }
    
    public static int countDots(final String s) {
        while (0 < s.length()) {
            if (s.charAt(0) == '.') {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    private static boolean isIPAddress(final String s) {
        return s != null && (InetAddressUtils.isIPv4Address(s) || InetAddressUtils.isIPv6Address(s));
    }
    
    private String normaliseIPv6Address(final String s) {
        if (s == null || !InetAddressUtils.isIPv6Address(s)) {
            return s;
        }
        return InetAddress.getByName(s).getHostAddress();
    }
    
    static {
        Arrays.sort(AbstractVerifier.BAD_COUNTRY_2LDS = new String[] { "ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", "lg", "ne", "net", "or", "org" });
    }
}
