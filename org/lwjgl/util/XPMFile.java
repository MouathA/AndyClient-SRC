package org.lwjgl.util;

import java.util.*;
import java.io.*;

public class XPMFile
{
    private byte[] bytes;
    private static final int WIDTH = 0;
    private static final int HEIGHT = 1;
    private static final int NUMBER_OF_COLORS = 2;
    private static final int CHARACTERS_PER_PIXEL = 3;
    private static int[] format;
    
    private XPMFile() {
    }
    
    public static XPMFile load(final String s) throws IOException {
        return load(new FileInputStream(new File(s)));
    }
    
    public static XPMFile load(final InputStream inputStream) {
        final XPMFile xpmFile = new XPMFile();
        xpmFile.readImage(inputStream);
        return xpmFile;
    }
    
    public int getHeight() {
        return XPMFile.format[1];
    }
    
    public int getWidth() {
        return XPMFile.format[0];
    }
    
    public byte[] getBytes() {
        return this.bytes;
    }
    
    private void readImage(final InputStream inputStream) {
        try {
            final LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(inputStream));
            final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
            XPMFile.format = parseFormat(nextLineOfInterest(lineNumberReader));
            for (int i = 0; i < XPMFile.format[2]; ++i) {
                final Object[] color = parseColor(nextLineOfInterest(lineNumberReader));
                hashMap.put((String)color[0], (Integer)color[1]);
            }
            this.bytes = new byte[XPMFile.format[0] * XPMFile.format[1] * 4];
            for (int j = 0; j < XPMFile.format[1]; ++j) {
                this.parseImageLine(nextLineOfInterest(lineNumberReader), XPMFile.format, hashMap, j);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Unable to parse XPM File");
        }
    }
    
    private static String nextLineOfInterest(final LineNumberReader lineNumberReader) throws IOException {
        String line;
        do {
            line = lineNumberReader.readLine();
        } while (!line.startsWith("\""));
        return line.substring(1, line.lastIndexOf(34));
    }
    
    private static int[] parseFormat(final String s) {
        final StringTokenizer stringTokenizer = new StringTokenizer(s);
        return new int[] { Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken()) };
    }
    
    private static Object[] parseColor(final String s) {
        return new Object[] { s.substring(0, XPMFile.format[3]), Integer.parseInt(s.substring(XPMFile.format[3] + 4), 16) };
    }
    
    private void parseImageLine(final String s, final int[] array, final HashMap hashMap, final int n) {
        final int n2 = n * 4 * array[0];
        for (int i = 0; i < array[0]; ++i) {
            final int intValue = hashMap.get(s.substring(i * array[3], i * array[3] + array[3]));
            this.bytes[n2 + i * 4] = (byte)((intValue & 0xFF0000) >> 16);
            this.bytes[n2 + (i * 4 + 1)] = (byte)((intValue & 0xFF00) >> 8);
            this.bytes[n2 + (i * 4 + 2)] = (byte)((intValue & 0xFF) >> 0);
            this.bytes[n2 + (i * 4 + 3)] = -1;
        }
    }
    
    public static void main(final String[] array) {
        if (array.length != 1) {
            System.out.println("usage:\nXPMFile <file>");
        }
        try {
            final String string = array[0].substring(0, array[0].indexOf(".")) + ".raw";
            final XPMFile load = load(array[0]);
            final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(string)));
            bufferedOutputStream.write(load.getBytes());
            bufferedOutputStream.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    static {
        XPMFile.format = new int[4];
    }
}
