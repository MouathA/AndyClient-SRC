package org.apache.commons.io;

import java.nio.channels.*;
import java.nio.charset.*;
import java.net.*;
import org.apache.commons.io.output.*;
import java.util.*;
import java.io.*;

public class IOUtils
{
    private static final int EOF = -1;
    public static final char DIR_SEPARATOR_UNIX = '/';
    public static final char DIR_SEPARATOR_WINDOWS = '\\';
    public static final char DIR_SEPARATOR;
    public static final String LINE_SEPARATOR_UNIX = "\n";
    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    public static final String LINE_SEPARATOR;
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int SKIP_BUFFER_SIZE = 2048;
    private static char[] SKIP_CHAR_BUFFER;
    private static byte[] SKIP_BYTE_BUFFER;
    
    public static void close(final URLConnection urlConnection) {
        if (urlConnection instanceof HttpURLConnection) {
            ((HttpURLConnection)urlConnection).disconnect();
        }
    }
    
    public static void closeQuietly(final Reader reader) {
        closeQuietly((Closeable)reader);
    }
    
    public static void closeQuietly(final Writer writer) {
        closeQuietly((Closeable)writer);
    }
    
    public static void closeQuietly(final InputStream inputStream) {
        closeQuietly((Closeable)inputStream);
    }
    
    public static void closeQuietly(final OutputStream outputStream) {
        closeQuietly((Closeable)outputStream);
    }
    
    public static void closeQuietly(final Closeable closeable) {
        if (closeable != null) {
            closeable.close();
        }
    }
    
    public static void closeQuietly(final Socket socket) {
        if (socket != null) {
            socket.close();
        }
    }
    
    public static void closeQuietly(final Selector selector) {
        if (selector != null) {
            selector.close();
        }
    }
    
    public static void closeQuietly(final ServerSocket serverSocket) {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
    
    public static InputStream toBufferedInputStream(final InputStream inputStream) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(inputStream);
    }
    
    public static BufferedReader toBufferedReader(final Reader reader) {
        return (BufferedReader)((reader instanceof BufferedReader) ? reader : new BufferedReader(reader));
    }
    
    public static byte[] toByteArray(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    
    public static byte[] toByteArray(final InputStream inputStream, final long n) throws IOException {
        if (n > 2147483647L) {
            throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + n);
        }
        return toByteArray(inputStream, (int)n);
    }
    
    public static byte[] toByteArray(final InputStream inputStream, final int n) throws IOException {
        if (n < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + n);
        }
        if (n == 0) {
            return new byte[0];
        }
        final byte[] array = new byte[n];
        int read;
        while (0 < n && (read = inputStream.read(array, 0, n - 0)) != -1) {}
        if (0 != n) {
            throw new IOException("Unexpected readed size. current: " + 0 + ", excepted: " + n);
        }
        return array;
    }
    
    public static byte[] toByteArray(final Reader reader) throws IOException {
        return toByteArray(reader, Charset.defaultCharset());
    }
    
    public static byte[] toByteArray(final Reader reader, final Charset charset) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(reader, byteArrayOutputStream, charset);
        return byteArrayOutputStream.toByteArray();
    }
    
    public static byte[] toByteArray(final Reader reader, final String s) throws IOException {
        return toByteArray(reader, Charsets.toCharset(s));
    }
    
    @Deprecated
    public static byte[] toByteArray(final String s) throws IOException {
        return s.getBytes();
    }
    
    public static byte[] toByteArray(final URI uri) throws IOException {
        return toByteArray(uri.toURL());
    }
    
    public static byte[] toByteArray(final URL url) throws IOException {
        final URLConnection openConnection = url.openConnection();
        final byte[] byteArray = toByteArray(openConnection);
        close(openConnection);
        return byteArray;
    }
    
    public static byte[] toByteArray(final URLConnection urlConnection) throws IOException {
        final InputStream inputStream = urlConnection.getInputStream();
        final byte[] byteArray = toByteArray(inputStream);
        inputStream.close();
        return byteArray;
    }
    
    public static char[] toCharArray(final InputStream inputStream) throws IOException {
        return toCharArray(inputStream, Charset.defaultCharset());
    }
    
    public static char[] toCharArray(final InputStream inputStream, final Charset charset) throws IOException {
        final CharArrayWriter charArrayWriter = new CharArrayWriter();
        copy(inputStream, charArrayWriter, charset);
        return charArrayWriter.toCharArray();
    }
    
    public static char[] toCharArray(final InputStream inputStream, final String s) throws IOException {
        return toCharArray(inputStream, Charsets.toCharset(s));
    }
    
    public static char[] toCharArray(final Reader reader) throws IOException {
        final CharArrayWriter charArrayWriter = new CharArrayWriter();
        copy(reader, charArrayWriter);
        return charArrayWriter.toCharArray();
    }
    
    public static String toString(final InputStream inputStream) throws IOException {
        return toString(inputStream, Charset.defaultCharset());
    }
    
    public static String toString(final InputStream inputStream, final Charset charset) throws IOException {
        final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        copy(inputStream, stringBuilderWriter, charset);
        return stringBuilderWriter.toString();
    }
    
    public static String toString(final InputStream inputStream, final String s) throws IOException {
        return toString(inputStream, Charsets.toCharset(s));
    }
    
    public static String toString(final Reader reader) throws IOException {
        final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        copy(reader, stringBuilderWriter);
        return stringBuilderWriter.toString();
    }
    
    public static String toString(final URI uri) throws IOException {
        return toString(uri, Charset.defaultCharset());
    }
    
    public static String toString(final URI uri, final Charset charset) throws IOException {
        return toString(uri.toURL(), Charsets.toCharset(charset));
    }
    
    public static String toString(final URI uri, final String s) throws IOException {
        return toString(uri, Charsets.toCharset(s));
    }
    
    public static String toString(final URL url) throws IOException {
        return toString(url, Charset.defaultCharset());
    }
    
    public static String toString(final URL url, final Charset charset) throws IOException {
        final InputStream openStream = url.openStream();
        final String string = toString(openStream, charset);
        openStream.close();
        return string;
    }
    
    public static String toString(final URL url, final String s) throws IOException {
        return toString(url, Charsets.toCharset(s));
    }
    
    @Deprecated
    public static String toString(final byte[] array) throws IOException {
        return new String(array);
    }
    
    public static String toString(final byte[] array, final String s) throws IOException {
        return new String(array, Charsets.toCharset(s));
    }
    
    public static List readLines(final InputStream inputStream) throws IOException {
        return readLines(inputStream, Charset.defaultCharset());
    }
    
    public static List readLines(final InputStream inputStream, final Charset charset) throws IOException {
        return readLines(new InputStreamReader(inputStream, Charsets.toCharset(charset)));
    }
    
    public static List readLines(final InputStream inputStream, final String s) throws IOException {
        return readLines(inputStream, Charsets.toCharset(s));
    }
    
    public static List readLines(final Reader reader) throws IOException {
        final BufferedReader bufferedReader = toBufferedReader(reader);
        final ArrayList<String> list = new ArrayList<String>();
        for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
            list.add(s);
        }
        return list;
    }
    
    public static LineIterator lineIterator(final Reader reader) {
        return new LineIterator(reader);
    }
    
    public static LineIterator lineIterator(final InputStream inputStream, final Charset charset) throws IOException {
        return new LineIterator(new InputStreamReader(inputStream, Charsets.toCharset(charset)));
    }
    
    public static LineIterator lineIterator(final InputStream inputStream, final String s) throws IOException {
        return lineIterator(inputStream, Charsets.toCharset(s));
    }
    
    public static InputStream toInputStream(final CharSequence charSequence) {
        return toInputStream(charSequence, Charset.defaultCharset());
    }
    
    public static InputStream toInputStream(final CharSequence charSequence, final Charset charset) {
        return toInputStream(charSequence.toString(), charset);
    }
    
    public static InputStream toInputStream(final CharSequence charSequence, final String s) throws IOException {
        return toInputStream(charSequence, Charsets.toCharset(s));
    }
    
    public static InputStream toInputStream(final String s) {
        return toInputStream(s, Charset.defaultCharset());
    }
    
    public static InputStream toInputStream(final String s, final Charset charset) {
        return new ByteArrayInputStream(s.getBytes(Charsets.toCharset(charset)));
    }
    
    public static InputStream toInputStream(final String s, final String s2) throws IOException {
        return new ByteArrayInputStream(s.getBytes(Charsets.toCharset(s2)));
    }
    
    public static void write(final byte[] array, final OutputStream outputStream) throws IOException {
        if (array != null) {
            outputStream.write(array);
        }
    }
    
    public static void write(final byte[] array, final Writer writer) throws IOException {
        write(array, writer, Charset.defaultCharset());
    }
    
    public static void write(final byte[] array, final Writer writer, final Charset charset) throws IOException {
        if (array != null) {
            writer.write(new String(array, Charsets.toCharset(charset)));
        }
    }
    
    public static void write(final byte[] array, final Writer writer, final String s) throws IOException {
        write(array, writer, Charsets.toCharset(s));
    }
    
    public static void write(final char[] array, final Writer writer) throws IOException {
        if (array != null) {
            writer.write(array);
        }
    }
    
    public static void write(final char[] array, final OutputStream outputStream) throws IOException {
        write(array, outputStream, Charset.defaultCharset());
    }
    
    public static void write(final char[] array, final OutputStream outputStream, final Charset charset) throws IOException {
        if (array != null) {
            outputStream.write(new String(array).getBytes(Charsets.toCharset(charset)));
        }
    }
    
    public static void write(final char[] array, final OutputStream outputStream, final String s) throws IOException {
        write(array, outputStream, Charsets.toCharset(s));
    }
    
    public static void write(final CharSequence charSequence, final Writer writer) throws IOException {
        if (charSequence != null) {
            write(charSequence.toString(), writer);
        }
    }
    
    public static void write(final CharSequence charSequence, final OutputStream outputStream) throws IOException {
        write(charSequence, outputStream, Charset.defaultCharset());
    }
    
    public static void write(final CharSequence charSequence, final OutputStream outputStream, final Charset charset) throws IOException {
        if (charSequence != null) {
            write(charSequence.toString(), outputStream, charset);
        }
    }
    
    public static void write(final CharSequence charSequence, final OutputStream outputStream, final String s) throws IOException {
        write(charSequence, outputStream, Charsets.toCharset(s));
    }
    
    public static void write(final String s, final Writer writer) throws IOException {
        if (s != null) {
            writer.write(s);
        }
    }
    
    public static void write(final String s, final OutputStream outputStream) throws IOException {
        write(s, outputStream, Charset.defaultCharset());
    }
    
    public static void write(final String s, final OutputStream outputStream, final Charset charset) throws IOException {
        if (s != null) {
            outputStream.write(s.getBytes(Charsets.toCharset(charset)));
        }
    }
    
    public static void write(final String s, final OutputStream outputStream, final String s2) throws IOException {
        write(s, outputStream, Charsets.toCharset(s2));
    }
    
    @Deprecated
    public static void write(final StringBuffer sb, final Writer writer) throws IOException {
        if (sb != null) {
            writer.write(sb.toString());
        }
    }
    
    @Deprecated
    public static void write(final StringBuffer sb, final OutputStream outputStream) throws IOException {
        write(sb, outputStream, null);
    }
    
    @Deprecated
    public static void write(final StringBuffer sb, final OutputStream outputStream, final String s) throws IOException {
        if (sb != null) {
            outputStream.write(sb.toString().getBytes(Charsets.toCharset(s)));
        }
    }
    
    public static void writeLines(final Collection collection, final String s, final OutputStream outputStream) throws IOException {
        writeLines(collection, s, outputStream, Charset.defaultCharset());
    }
    
    public static void writeLines(final Collection collection, String line_SEPARATOR, final OutputStream outputStream, final Charset charset) throws IOException {
        if (collection == null) {
            return;
        }
        if (line_SEPARATOR == null) {
            line_SEPARATOR = IOUtils.LINE_SEPARATOR;
        }
        final Charset charset2 = Charsets.toCharset(charset);
        for (final Object next : collection) {
            if (next != null) {
                outputStream.write(next.toString().getBytes(charset2));
            }
            outputStream.write(line_SEPARATOR.getBytes(charset2));
        }
    }
    
    public static void writeLines(final Collection collection, final String s, final OutputStream outputStream, final String s2) throws IOException {
        writeLines(collection, s, outputStream, Charsets.toCharset(s2));
    }
    
    public static void writeLines(final Collection collection, String line_SEPARATOR, final Writer writer) throws IOException {
        if (collection == null) {
            return;
        }
        if (line_SEPARATOR == null) {
            line_SEPARATOR = IOUtils.LINE_SEPARATOR;
        }
        for (final Object next : collection) {
            if (next != null) {
                writer.write(next.toString());
            }
            writer.write(line_SEPARATOR);
        }
    }
    
    public static int copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        final long copyLarge = copyLarge(inputStream, outputStream);
        if (copyLarge > 2147483647L) {
            return -1;
        }
        return (int)copyLarge;
    }
    
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        return copyLarge(inputStream, outputStream, new byte[4096]);
    }
    
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream, final byte[] array) throws IOException {
        long n = 0L;
        while (-1 != inputStream.read(array)) {
            outputStream.write(array, 0, 0);
            n += 0;
        }
        return n;
    }
    
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream, final long n, final long n2) throws IOException {
        return copyLarge(inputStream, outputStream, n, n2, new byte[4096]);
    }
    
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream, final long n, final long n2, final byte[] array) throws IOException {
        if (n > 0L) {
            skipFully(inputStream, n);
        }
        if (n2 == 0L) {
            return 0L;
        }
        int length;
        final int n3 = length = array.length;
        if (n2 > 0L && n2 < n3) {
            length = (int)n2;
        }
        long n4;
        int read;
        for (n4 = 0L; length > 0 && -1 != (read = inputStream.read(array, 0, length)); length = (int)Math.min(n2 - n4, n3)) {
            outputStream.write(array, 0, read);
            n4 += read;
            if (n2 > 0L) {}
        }
        return n4;
    }
    
    public static void copy(final InputStream inputStream, final Writer writer) throws IOException {
        copy(inputStream, writer, Charset.defaultCharset());
    }
    
    public static void copy(final InputStream inputStream, final Writer writer, final Charset charset) throws IOException {
        copy(new InputStreamReader(inputStream, Charsets.toCharset(charset)), writer);
    }
    
    public static void copy(final InputStream inputStream, final Writer writer, final String s) throws IOException {
        copy(inputStream, writer, Charsets.toCharset(s));
    }
    
    public static int copy(final Reader reader, final Writer writer) throws IOException {
        final long copyLarge = copyLarge(reader, writer);
        if (copyLarge > 2147483647L) {
            return -1;
        }
        return (int)copyLarge;
    }
    
    public static long copyLarge(final Reader reader, final Writer writer) throws IOException {
        return copyLarge(reader, writer, new char[4096]);
    }
    
    public static long copyLarge(final Reader reader, final Writer writer, final char[] array) throws IOException {
        long n = 0L;
        while (-1 != reader.read(array)) {
            writer.write(array, 0, 0);
            n += 0;
        }
        return n;
    }
    
    public static long copyLarge(final Reader reader, final Writer writer, final long n, final long n2) throws IOException {
        return copyLarge(reader, writer, n, n2, new char[4096]);
    }
    
    public static long copyLarge(final Reader reader, final Writer writer, final long n, final long n2, final char[] array) throws IOException {
        if (n > 0L) {
            skipFully(reader, n);
        }
        if (n2 == 0L) {
            return 0L;
        }
        int length = array.length;
        if (n2 > 0L && n2 < array.length) {
            length = (int)n2;
        }
        long n3;
        int read;
        for (n3 = 0L; length > 0 && -1 != (read = reader.read(array, 0, length)); length = (int)Math.min(n2 - n3, array.length)) {
            writer.write(array, 0, read);
            n3 += read;
            if (n2 > 0L) {}
        }
        return n3;
    }
    
    public static void copy(final Reader reader, final OutputStream outputStream) throws IOException {
        copy(reader, outputStream, Charset.defaultCharset());
    }
    
    public static void copy(final Reader reader, final OutputStream outputStream, final Charset charset) throws IOException {
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charsets.toCharset(charset));
        copy(reader, outputStreamWriter);
        outputStreamWriter.flush();
    }
    
    public static void copy(final Reader reader, final OutputStream outputStream, final String s) throws IOException {
        copy(reader, outputStream, Charsets.toCharset(s));
    }
    
    public static boolean contentEquals(InputStream inputStream, InputStream inputStream2) throws IOException {
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        if (!(inputStream2 instanceof BufferedInputStream)) {
            inputStream2 = new BufferedInputStream(inputStream2);
        }
        for (int n = inputStream.read(); -1 != n; n = inputStream.read()) {
            if (n != inputStream2.read()) {
                return false;
            }
        }
        return inputStream2.read() == -1;
    }
    
    public static boolean contentEquals(final Reader reader, final Reader reader2) throws IOException {
        final BufferedReader bufferedReader = toBufferedReader(reader);
        final BufferedReader bufferedReader2 = toBufferedReader(reader2);
        for (int n = bufferedReader.read(); -1 != n; n = bufferedReader.read()) {
            if (n != bufferedReader2.read()) {
                return false;
            }
        }
        return bufferedReader2.read() == -1;
    }
    
    public static boolean contentEqualsIgnoreEOL(final Reader reader, final Reader reader2) throws IOException {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        String s;
        String s2;
        for (bufferedReader = toBufferedReader(reader), bufferedReader2 = toBufferedReader(reader2), s = bufferedReader.readLine(), s2 = bufferedReader2.readLine(); s != null && s2 != null && s.equals(s2); s = bufferedReader.readLine(), s2 = bufferedReader2.readLine()) {}
        return (s == null) ? (s2 == null) : s.equals(s2);
    }
    
    public static long skip(final InputStream inputStream, final long n) throws IOException {
        if (n < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + n);
        }
        if (IOUtils.SKIP_BYTE_BUFFER == null) {
            IOUtils.SKIP_BYTE_BUFFER = new byte[2048];
        }
        long n2;
        long n3;
        for (n2 = n; n2 > 0L; n2 -= n3) {
            n3 = inputStream.read(IOUtils.SKIP_BYTE_BUFFER, 0, (int)Math.min(n2, 2048L));
            if (n3 < 0L) {
                break;
            }
        }
        return n - n2;
    }
    
    public static long skip(final Reader reader, final long n) throws IOException {
        if (n < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + n);
        }
        if (IOUtils.SKIP_CHAR_BUFFER == null) {
            IOUtils.SKIP_CHAR_BUFFER = new char[2048];
        }
        long n2;
        long n3;
        for (n2 = n; n2 > 0L; n2 -= n3) {
            n3 = reader.read(IOUtils.SKIP_CHAR_BUFFER, 0, (int)Math.min(n2, 2048L));
            if (n3 < 0L) {
                break;
            }
        }
        return n - n2;
    }
    
    public static void skipFully(final InputStream inputStream, final long n) throws IOException {
        if (n < 0L) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + n);
        }
        final long skip = skip(inputStream, n);
        if (skip != n) {
            throw new EOFException("Bytes to skip: " + n + " actual: " + skip);
        }
    }
    
    public static void skipFully(final Reader reader, final long n) throws IOException {
        final long skip = skip(reader, n);
        if (skip != n) {
            throw new EOFException("Chars to skip: " + n + " actual: " + skip);
        }
    }
    
    public static int read(final Reader reader, final char[] array, final int n, final int n2) throws IOException {
        if (n2 < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + n2);
        }
        int i;
        int read;
        for (i = n2; i > 0; i -= read) {
            read = reader.read(array, n + (n2 - i), i);
            if (-1 == read) {
                break;
            }
        }
        return n2 - i;
    }
    
    public static int read(final Reader reader, final char[] array) throws IOException {
        return read(reader, array, 0, array.length);
    }
    
    public static int read(final InputStream inputStream, final byte[] array, final int n, final int n2) throws IOException {
        if (n2 < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + n2);
        }
        int i;
        int read;
        for (i = n2; i > 0; i -= read) {
            read = inputStream.read(array, n + (n2 - i), i);
            if (-1 == read) {
                break;
            }
        }
        return n2 - i;
    }
    
    public static int read(final InputStream inputStream, final byte[] array) throws IOException {
        return read(inputStream, array, 0, array.length);
    }
    
    public static void readFully(final Reader reader, final char[] array, final int n, final int n2) throws IOException {
        final int read = read(reader, array, n, n2);
        if (read != n2) {
            throw new EOFException("Length to read: " + n2 + " actual: " + read);
        }
    }
    
    public static void readFully(final Reader reader, final char[] array) throws IOException {
        readFully(reader, array, 0, array.length);
    }
    
    public static void readFully(final InputStream inputStream, final byte[] array, final int n, final int n2) throws IOException {
        final int read = read(inputStream, array, n, n2);
        if (read != n2) {
            throw new EOFException("Length to read: " + n2 + " actual: " + read);
        }
    }
    
    public static void readFully(final InputStream inputStream, final byte[] array) throws IOException {
        readFully(inputStream, array, 0, array.length);
    }
    
    static {
        DIR_SEPARATOR = File.separatorChar;
        final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter(4);
        final PrintWriter printWriter = new PrintWriter(stringBuilderWriter);
        printWriter.println();
        LINE_SEPARATOR = stringBuilderWriter.toString();
        printWriter.close();
    }
}
