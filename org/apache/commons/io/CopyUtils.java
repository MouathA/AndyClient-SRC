package org.apache.commons.io;

import java.io.*;

@Deprecated
public class CopyUtils
{
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    
    public static void copy(final byte[] array, final OutputStream outputStream) throws IOException {
        outputStream.write(array);
    }
    
    public static void copy(final byte[] array, final Writer writer) throws IOException {
        copy(new ByteArrayInputStream(array), writer);
    }
    
    public static void copy(final byte[] array, final Writer writer, final String s) throws IOException {
        copy(new ByteArrayInputStream(array), writer, s);
    }
    
    public static int copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        final byte[] array = new byte[4096];
        while (-1 != inputStream.read(array)) {
            outputStream.write(array, 0, 0);
        }
        return 0;
    }
    
    public static int copy(final Reader reader, final Writer writer) throws IOException {
        final char[] array = new char[4096];
        while (-1 != reader.read(array)) {
            writer.write(array, 0, 0);
        }
        return 0;
    }
    
    public static void copy(final InputStream inputStream, final Writer writer) throws IOException {
        copy(new InputStreamReader(inputStream), writer);
    }
    
    public static void copy(final InputStream inputStream, final Writer writer, final String s) throws IOException {
        copy(new InputStreamReader(inputStream, s), writer);
    }
    
    public static void copy(final Reader reader, final OutputStream outputStream) throws IOException {
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        copy(reader, outputStreamWriter);
        outputStreamWriter.flush();
    }
    
    public static void copy(final String s, final OutputStream outputStream) throws IOException {
        final StringReader stringReader = new StringReader(s);
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        copy(stringReader, outputStreamWriter);
        outputStreamWriter.flush();
    }
    
    public static void copy(final String s, final Writer writer) throws IOException {
        writer.write(s);
    }
}
