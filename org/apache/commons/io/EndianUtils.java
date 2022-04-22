package org.apache.commons.io;

import java.io.*;

public class EndianUtils
{
    public static short swapShort(final short n) {
        return (short)(((n >> 0 & 0xFF) << 8) + ((n >> 8 & 0xFF) << 0));
    }
    
    public static int swapInteger(final int n) {
        return ((n >> 0 & 0xFF) << 24) + ((n >> 8 & 0xFF) << 16) + ((n >> 16 & 0xFF) << 8) + ((n >> 24 & 0xFF) << 0);
    }
    
    public static long swapLong(final long n) {
        return ((n >> 0 & 0xFFL) << 56) + ((n >> 8 & 0xFFL) << 48) + ((n >> 16 & 0xFFL) << 40) + ((n >> 24 & 0xFFL) << 32) + ((n >> 32 & 0xFFL) << 24) + ((n >> 40 & 0xFFL) << 16) + ((n >> 48 & 0xFFL) << 8) + ((n >> 56 & 0xFFL) << 0);
    }
    
    public static float swapFloat(final float n) {
        return Float.intBitsToFloat(swapInteger(Float.floatToIntBits(n)));
    }
    
    public static double swapDouble(final double n) {
        return Double.longBitsToDouble(swapLong(Double.doubleToLongBits(n)));
    }
    
    public static void writeSwappedShort(final byte[] array, final int n, final short n2) {
        array[n + 0] = (byte)(n2 >> 0 & 0xFF);
        array[n + 1] = (byte)(n2 >> 8 & 0xFF);
    }
    
    public static short readSwappedShort(final byte[] array, final int n) {
        return (short)(((array[n + 0] & 0xFF) << 0) + ((array[n + 1] & 0xFF) << 8));
    }
    
    public static int readSwappedUnsignedShort(final byte[] array, final int n) {
        return ((array[n + 0] & 0xFF) << 0) + ((array[n + 1] & 0xFF) << 8);
    }
    
    public static void writeSwappedInteger(final byte[] array, final int n, final int n2) {
        array[n + 0] = (byte)(n2 >> 0 & 0xFF);
        array[n + 1] = (byte)(n2 >> 8 & 0xFF);
        array[n + 2] = (byte)(n2 >> 16 & 0xFF);
        array[n + 3] = (byte)(n2 >> 24 & 0xFF);
    }
    
    public static int readSwappedInteger(final byte[] array, final int n) {
        return ((array[n + 0] & 0xFF) << 0) + ((array[n + 1] & 0xFF) << 8) + ((array[n + 2] & 0xFF) << 16) + ((array[n + 3] & 0xFF) << 24);
    }
    
    public static long readSwappedUnsignedInteger(final byte[] array, final int n) {
        return ((long)(array[n + 3] & 0xFF) << 24) + (0xFFFFFFFFL & (long)(((array[n + 0] & 0xFF) << 0) + ((array[n + 1] & 0xFF) << 8) + ((array[n + 2] & 0xFF) << 16)));
    }
    
    public static void writeSwappedLong(final byte[] array, final int n, final long n2) {
        array[n + 0] = (byte)(n2 >> 0 & 0xFFL);
        array[n + 1] = (byte)(n2 >> 8 & 0xFFL);
        array[n + 2] = (byte)(n2 >> 16 & 0xFFL);
        array[n + 3] = (byte)(n2 >> 24 & 0xFFL);
        array[n + 4] = (byte)(n2 >> 32 & 0xFFL);
        array[n + 5] = (byte)(n2 >> 40 & 0xFFL);
        array[n + 6] = (byte)(n2 >> 48 & 0xFFL);
        array[n + 7] = (byte)(n2 >> 56 & 0xFFL);
    }
    
    public static long readSwappedLong(final byte[] array, final int n) {
        return ((long)(((array[n + 4] & 0xFF) << 0) + ((array[n + 5] & 0xFF) << 8) + ((array[n + 6] & 0xFF) << 16) + ((array[n + 7] & 0xFF) << 24)) << 32) + (0xFFFFFFFFL & (long)(((array[n + 0] & 0xFF) << 0) + ((array[n + 1] & 0xFF) << 8) + ((array[n + 2] & 0xFF) << 16) + ((array[n + 3] & 0xFF) << 24)));
    }
    
    public static void writeSwappedFloat(final byte[] array, final int n, final float n2) {
        writeSwappedInteger(array, n, Float.floatToIntBits(n2));
    }
    
    public static float readSwappedFloat(final byte[] array, final int n) {
        return Float.intBitsToFloat(readSwappedInteger(array, n));
    }
    
    public static void writeSwappedDouble(final byte[] array, final int n, final double n2) {
        writeSwappedLong(array, n, Double.doubleToLongBits(n2));
    }
    
    public static double readSwappedDouble(final byte[] array, final int n) {
        return Double.longBitsToDouble(readSwappedLong(array, n));
    }
    
    public static void writeSwappedShort(final OutputStream outputStream, final short n) throws IOException {
        outputStream.write((byte)(n >> 0 & 0xFF));
        outputStream.write((byte)(n >> 8 & 0xFF));
    }
    
    public static short readSwappedShort(final InputStream inputStream) throws IOException {
        return (short)(((read(inputStream) & 0xFF) << 0) + ((read(inputStream) & 0xFF) << 8));
    }
    
    public static int readSwappedUnsignedShort(final InputStream inputStream) throws IOException {
        return ((read(inputStream) & 0xFF) << 0) + ((read(inputStream) & 0xFF) << 8);
    }
    
    public static void writeSwappedInteger(final OutputStream outputStream, final int n) throws IOException {
        outputStream.write((byte)(n >> 0 & 0xFF));
        outputStream.write((byte)(n >> 8 & 0xFF));
        outputStream.write((byte)(n >> 16 & 0xFF));
        outputStream.write((byte)(n >> 24 & 0xFF));
    }
    
    public static int readSwappedInteger(final InputStream inputStream) throws IOException {
        return ((read(inputStream) & 0xFF) << 0) + ((read(inputStream) & 0xFF) << 8) + ((read(inputStream) & 0xFF) << 16) + ((read(inputStream) & 0xFF) << 24);
    }
    
    public static long readSwappedUnsignedInteger(final InputStream inputStream) throws IOException {
        return ((long)(read(inputStream) & 0xFF) << 24) + (0xFFFFFFFFL & (long)(((read(inputStream) & 0xFF) << 0) + ((read(inputStream) & 0xFF) << 8) + ((read(inputStream) & 0xFF) << 16)));
    }
    
    public static void writeSwappedLong(final OutputStream outputStream, final long n) throws IOException {
        outputStream.write((byte)(n >> 0 & 0xFFL));
        outputStream.write((byte)(n >> 8 & 0xFFL));
        outputStream.write((byte)(n >> 16 & 0xFFL));
        outputStream.write((byte)(n >> 24 & 0xFFL));
        outputStream.write((byte)(n >> 32 & 0xFFL));
        outputStream.write((byte)(n >> 40 & 0xFFL));
        outputStream.write((byte)(n >> 48 & 0xFFL));
        outputStream.write((byte)(n >> 56 & 0xFFL));
    }
    
    public static long readSwappedLong(final InputStream inputStream) throws IOException {
        final byte[] array = new byte[8];
        while (0 < 8) {
            array[0] = (byte)read(inputStream);
            int n = 0;
            ++n;
        }
        return readSwappedLong(array, 0);
    }
    
    public static void writeSwappedFloat(final OutputStream outputStream, final float n) throws IOException {
        writeSwappedInteger(outputStream, Float.floatToIntBits(n));
    }
    
    public static float readSwappedFloat(final InputStream inputStream) throws IOException {
        return Float.intBitsToFloat(readSwappedInteger(inputStream));
    }
    
    public static void writeSwappedDouble(final OutputStream outputStream, final double n) throws IOException {
        writeSwappedLong(outputStream, Double.doubleToLongBits(n));
    }
    
    public static double readSwappedDouble(final InputStream inputStream) throws IOException {
        return Double.longBitsToDouble(readSwappedLong(inputStream));
    }
    
    private static int read(final InputStream inputStream) throws IOException {
        final int read = inputStream.read();
        if (-1 == read) {
            throw new EOFException("Unexpected EOF reached");
        }
        return read;
    }
}
