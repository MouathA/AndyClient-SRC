package com.viaversion.viaversion.libs.opennbt;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.zip.*;
import java.nio.charset.*;
import java.io.*;

public class NBTIO
{
    public static CompoundTag readFile(final String s) throws IOException {
        return readFile(new File(s));
    }
    
    public static CompoundTag readFile(final File file) throws IOException {
        return readFile(file, true, false);
    }
    
    public static CompoundTag readFile(final String s, final boolean b, final boolean b2) throws IOException {
        return readFile(new File(s), b, b2);
    }
    
    public static CompoundTag readFile(final File file, final boolean b, final boolean b2) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        if (b) {
            inputStream = new GZIPInputStream(inputStream);
        }
        final CompoundTag tag = readTag(inputStream, b2);
        if (!(tag instanceof CompoundTag)) {
            throw new IOException("Root tag is not a CompoundTag!");
        }
        return tag;
    }
    
    public static void writeFile(final CompoundTag compoundTag, final String s) throws IOException {
        writeFile(compoundTag, new File(s));
    }
    
    public static void writeFile(final CompoundTag compoundTag, final File file) throws IOException {
        writeFile(compoundTag, file, true, false);
    }
    
    public static void writeFile(final CompoundTag compoundTag, final String s, final boolean b, final boolean b2) throws IOException {
        writeFile(compoundTag, new File(s), b, b2);
    }
    
    public static void writeFile(final CompoundTag compoundTag, final File file, final boolean b, final boolean b2) throws IOException {
        if (!file.exists()) {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        if (b) {
            outputStream = new GZIPOutputStream(outputStream);
        }
        writeTag(outputStream, compoundTag, b2);
        outputStream.close();
    }
    
    public static CompoundTag readTag(final InputStream inputStream) throws IOException {
        return readTag(inputStream, false);
    }
    
    public static CompoundTag readTag(final InputStream inputStream, final boolean b) throws IOException {
        return readTag(b ? new LittleEndianDataInputStream(inputStream, null) : new DataInputStream(inputStream));
    }
    
    public static CompoundTag readTag(final DataInput dataInput) throws IOException {
        final byte byte1 = dataInput.readByte();
        if (byte1 != 10) {
            throw new IOException(String.format("Expected root tag to be a CompoundTag, was %s", byte1));
        }
        dataInput.skipBytes(dataInput.readUnsignedShort());
        final CompoundTag compoundTag = new CompoundTag();
        compoundTag.read(dataInput);
        return compoundTag;
    }
    
    public static void writeTag(final OutputStream outputStream, final CompoundTag compoundTag) throws IOException {
        writeTag(outputStream, compoundTag, false);
    }
    
    public static void writeTag(final OutputStream outputStream, final CompoundTag compoundTag, final boolean b) throws IOException {
        writeTag(b ? new LittleEndianDataOutputStream(outputStream, null) : new DataOutputStream(outputStream), compoundTag);
    }
    
    public static void writeTag(final DataOutput dataOutput, final CompoundTag compoundTag) throws IOException {
        dataOutput.writeByte(10);
        dataOutput.writeUTF("");
        compoundTag.write(dataOutput);
    }
    
    private static final class LittleEndianDataOutputStream extends FilterOutputStream implements DataOutput
    {
        private LittleEndianDataOutputStream(final OutputStream outputStream) {
            super(outputStream);
        }
        
        @Override
        public synchronized void write(final int n) throws IOException {
            this.out.write(n);
        }
        
        @Override
        public synchronized void write(final byte[] array, final int n, final int n2) throws IOException {
            this.out.write(array, n, n2);
        }
        
        @Override
        public void flush() throws IOException {
            this.out.flush();
        }
        
        @Override
        public void writeBoolean(final boolean b) throws IOException {
            this.out.write(b ? 1 : 0);
        }
        
        @Override
        public void writeByte(final int n) throws IOException {
            this.out.write(n);
        }
        
        @Override
        public void writeShort(final int n) throws IOException {
            this.out.write(n & 0xFF);
            this.out.write(n >>> 8 & 0xFF);
        }
        
        @Override
        public void writeChar(final int n) throws IOException {
            this.out.write(n & 0xFF);
            this.out.write(n >>> 8 & 0xFF);
        }
        
        @Override
        public void writeInt(final int n) throws IOException {
            this.out.write(n & 0xFF);
            this.out.write(n >>> 8 & 0xFF);
            this.out.write(n >>> 16 & 0xFF);
            this.out.write(n >>> 24 & 0xFF);
        }
        
        @Override
        public void writeLong(final long n) throws IOException {
            this.out.write((int)(n & 0xFFL));
            this.out.write((int)(n >>> 8 & 0xFFL));
            this.out.write((int)(n >>> 16 & 0xFFL));
            this.out.write((int)(n >>> 24 & 0xFFL));
            this.out.write((int)(n >>> 32 & 0xFFL));
            this.out.write((int)(n >>> 40 & 0xFFL));
            this.out.write((int)(n >>> 48 & 0xFFL));
            this.out.write((int)(n >>> 56 & 0xFFL));
        }
        
        @Override
        public void writeFloat(final float n) throws IOException {
            this.writeInt(Float.floatToIntBits(n));
        }
        
        @Override
        public void writeDouble(final double n) throws IOException {
            this.writeLong(Double.doubleToLongBits(n));
        }
        
        @Override
        public void writeBytes(final String s) throws IOException {
            while (0 < s.length()) {
                this.out.write((byte)s.charAt(0));
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public void writeChars(final String s) throws IOException {
            while (0 < s.length()) {
                final char char1 = s.charAt(0);
                this.out.write(char1 & '\u00ff');
                this.out.write(char1 >>> 8 & 0xFF);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public void writeUTF(final String s) throws IOException {
            final byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
            this.writeShort(bytes.length);
            this.write(bytes);
        }
        
        LittleEndianDataOutputStream(final OutputStream outputStream, final NBTIO$1 object) {
            this(outputStream);
        }
    }
    
    private static final class LittleEndianDataInputStream extends FilterInputStream implements DataInput
    {
        private LittleEndianDataInputStream(final InputStream inputStream) {
            super(inputStream);
        }
        
        @Override
        public int read(final byte[] array) throws IOException {
            return this.in.read(array, 0, array.length);
        }
        
        @Override
        public int read(final byte[] array, final int n, final int n2) throws IOException {
            return this.in.read(array, n, n2);
        }
        
        @Override
        public void readFully(final byte[] array) throws IOException {
            this.readFully(array, 0, array.length);
        }
        
        @Override
        public void readFully(final byte[] array, final int n, final int n2) throws IOException {
            if (n2 < 0) {
                throw new IndexOutOfBoundsException();
            }
            while (0 < n2) {
                if (this.in.read(array, n + 0, n2 - 0) < 0) {
                    throw new EOFException();
                }
            }
        }
        
        @Override
        public int skipBytes(final int n) throws IOException {
            int n2;
            while (0 < n && (n2 = (int)this.in.skip(n - 0)) > 0) {}
            return 0;
        }
        
        @Override
        public boolean readBoolean() throws IOException {
            final int read = this.in.read();
            if (read < 0) {
                throw new EOFException();
            }
            return read != 0;
        }
        
        @Override
        public byte readByte() throws IOException {
            final int read = this.in.read();
            if (read < 0) {
                throw new EOFException();
            }
            return (byte)read;
        }
        
        @Override
        public int readUnsignedByte() throws IOException {
            final int read = this.in.read();
            if (read < 0) {
                throw new EOFException();
            }
            return read;
        }
        
        @Override
        public short readShort() throws IOException {
            final int read = this.in.read();
            final int read2 = this.in.read();
            if ((read | read2) < 0) {
                throw new EOFException();
            }
            return (short)(read | read2 << 8);
        }
        
        @Override
        public int readUnsignedShort() throws IOException {
            final int read = this.in.read();
            final int read2 = this.in.read();
            if ((read | read2) < 0) {
                throw new EOFException();
            }
            return read | read2 << 8;
        }
        
        @Override
        public char readChar() throws IOException {
            final int read = this.in.read();
            final int read2 = this.in.read();
            if ((read | read2) < 0) {
                throw new EOFException();
            }
            return (char)(read | read2 << 8);
        }
        
        @Override
        public int readInt() throws IOException {
            final int read = this.in.read();
            final int read2 = this.in.read();
            final int read3 = this.in.read();
            final int read4 = this.in.read();
            if ((read | read2 | read3 | read4) < 0) {
                throw new EOFException();
            }
            return read | read2 << 8 | read3 << 16 | read4 << 24;
        }
        
        @Override
        public long readLong() throws IOException {
            final long n = this.in.read();
            final long n2 = this.in.read();
            final long n3 = this.in.read();
            final long n4 = this.in.read();
            final long n5 = this.in.read();
            final long n6 = this.in.read();
            final long n7 = this.in.read();
            final long n8 = this.in.read();
            if ((n | n2 | n3 | n4 | n5 | n6 | n7 | n8) < 0L) {
                throw new EOFException();
            }
            return n | n2 << 8 | n3 << 16 | n4 << 24 | n5 << 32 | n6 << 40 | n7 << 48 | n8 << 56;
        }
        
        @Override
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(this.readInt());
        }
        
        @Override
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(this.readLong());
        }
        
        @Override
        public String readLine() throws IOException {
            throw new UnsupportedOperationException("Use readUTF.");
        }
        
        @Override
        public String readUTF() throws IOException {
            final byte[] array = new byte[this.readUnsignedShort()];
            this.readFully(array);
            return new String(array, StandardCharsets.UTF_8);
        }
        
        LittleEndianDataInputStream(final InputStream inputStream, final NBTIO$1 object) {
            this(inputStream);
        }
    }
}
