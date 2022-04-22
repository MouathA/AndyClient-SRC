package io.netty.handler.ssl;

import java.util.*;
import io.netty.util.*;
import io.netty.buffer.*;
import io.netty.handler.codec.base64.*;
import java.security.cert.*;
import java.util.regex.*;
import java.security.*;
import java.io.*;
import io.netty.util.internal.logging.*;

final class PemReader
{
    private static final InternalLogger logger;
    private static final Pattern CERT_PATTERN;
    private static final Pattern KEY_PATTERN;
    
    static ByteBuf[] readCertificates(final File file) throws CertificateException {
        final String content = readContent(file);
        final ArrayList<ByteBuf> list = new ArrayList<ByteBuf>();
        final Matcher matcher = PemReader.CERT_PATTERN.matcher(content);
        while (matcher.find(0)) {
            final ByteBuf copiedBuffer = Unpooled.copiedBuffer(matcher.group(1), CharsetUtil.US_ASCII);
            final ByteBuf decode = Base64.decode(copiedBuffer);
            copiedBuffer.release();
            list.add(decode);
            matcher.end();
        }
        if (list.isEmpty()) {
            throw new CertificateException("found no certificates: " + file);
        }
        return list.toArray(new ByteBuf[list.size()]);
    }
    
    static ByteBuf readPrivateKey(final File file) throws KeyException {
        final Matcher matcher = PemReader.KEY_PATTERN.matcher(readContent(file));
        if (!matcher.find()) {
            throw new KeyException("found no private key: " + file);
        }
        final ByteBuf copiedBuffer = Unpooled.copiedBuffer(matcher.group(1), CharsetUtil.US_ASCII);
        final ByteBuf decode = Base64.decode(copiedBuffer);
        copiedBuffer.release();
        return decode;
    }
    
    private static String readContent(final File file) throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(file);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final byte[] array = new byte[8192];
        while (true) {
            final int read = fileInputStream.read(array);
            if (read < 0) {
                break;
            }
            byteArrayOutputStream.write(array, 0, read);
        }
        final String string = byteArrayOutputStream.toString(CharsetUtil.US_ASCII.name());
        safeClose(fileInputStream);
        safeClose(byteArrayOutputStream);
        return string;
    }
    
    private static void safeClose(final InputStream inputStream) {
        inputStream.close();
    }
    
    private static void safeClose(final OutputStream outputStream) {
        outputStream.close();
    }
    
    private PemReader() {
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(PemReader.class);
        CERT_PATTERN = Pattern.compile("-+BEGIN\\s+.*CERTIFICATE[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+.*CERTIFICATE[^-]*-+", 2);
        KEY_PATTERN = Pattern.compile("-+BEGIN\\s+.*PRIVATE\\s+KEY[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+.*PRIVATE\\s+KEY[^-]*-+", 2);
    }
}
