package com.viaversion.viaversion.api.minecraft.nbt;

import java.nio.file.*;
import java.io.*;
import java.util.zip.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public final class BinaryTagIO
{
    private BinaryTagIO() {
    }
    
    public static CompoundTag readPath(final Path path) throws IOException {
        return readInputStream(Files.newInputStream(path, new OpenOption[0]));
    }
    
    public static CompoundTag readInputStream(final InputStream inputStream) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        final Object o = null;
        final CompoundTag dataInput = readDataInput(dataInputStream);
        if (dataInputStream != null) {
            if (o != null) {
                dataInputStream.close();
            }
            else {
                dataInputStream.close();
            }
        }
        return dataInput;
    }
    
    public static CompoundTag readCompressedPath(final Path path) throws IOException {
        return readCompressedInputStream(Files.newInputStream(path, new OpenOption[0]));
    }
    
    public static CompoundTag readCompressedInputStream(final InputStream inputStream) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)));
        final Object o = null;
        final CompoundTag dataInput = readDataInput(dataInputStream);
        if (dataInputStream != null) {
            if (o != null) {
                dataInputStream.close();
            }
            else {
                dataInputStream.close();
            }
        }
        return dataInput;
    }
    
    public static CompoundTag readDataInput(final DataInput dataInput) throws IOException {
        final byte byte1 = dataInput.readByte();
        if (byte1 != 10) {
            throw new IOException(String.format("Expected root tag to be a CompoundTag, was %s", byte1));
        }
        dataInput.skipBytes(dataInput.readUnsignedShort());
        final CompoundTag compoundTag = new CompoundTag();
        compoundTag.read(dataInput);
        return compoundTag;
    }
    
    public static void writePath(final CompoundTag compoundTag, final Path path) throws IOException {
        writeOutputStream(compoundTag, Files.newOutputStream(path, new OpenOption[0]));
    }
    
    public static void writeOutputStream(final CompoundTag compoundTag, final OutputStream outputStream) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        final Object o = null;
        writeDataOutput(compoundTag, dataOutputStream);
        if (dataOutputStream != null) {
            if (o != null) {
                dataOutputStream.close();
            }
            else {
                dataOutputStream.close();
            }
        }
    }
    
    public static void writeCompressedPath(final CompoundTag compoundTag, final Path path) throws IOException {
        writeCompressedOutputStream(compoundTag, Files.newOutputStream(path, new OpenOption[0]));
    }
    
    public static void writeCompressedOutputStream(final CompoundTag compoundTag, final OutputStream outputStream) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(outputStream));
        final Object o = null;
        writeDataOutput(compoundTag, dataOutputStream);
        if (dataOutputStream != null) {
            if (o != null) {
                dataOutputStream.close();
            }
            else {
                dataOutputStream.close();
            }
        }
    }
    
    public static void writeDataOutput(final CompoundTag compoundTag, final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(10);
        dataOutput.writeUTF("");
        compoundTag.write(dataOutput);
    }
    
    public static CompoundTag readString(final String s) throws IOException {
        final CharBuffer charBuffer = new CharBuffer(s);
        final CompoundTag compound = new TagStringReader(charBuffer).compound();
        if (charBuffer.skipWhitespace().hasMore()) {
            throw new IOException("Document had trailing content after first CompoundTag");
        }
        return compound;
    }
    
    public static String writeString(final CompoundTag compoundTag) throws IOException {
        final StringBuilder sb = new StringBuilder();
        final TagStringWriter tagStringWriter = new TagStringWriter(sb);
        final Object o = null;
        tagStringWriter.writeTag(compoundTag);
        if (tagStringWriter != null) {
            if (o != null) {
                tagStringWriter.close();
            }
            else {
                tagStringWriter.close();
            }
        }
        return sb.toString();
    }
}
