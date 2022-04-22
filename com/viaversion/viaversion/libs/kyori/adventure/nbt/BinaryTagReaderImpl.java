package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;

final class BinaryTagReaderImpl implements BinaryTagIO.Reader
{
    private final long maxBytes;
    static final BinaryTagIO.Reader UNLIMITED;
    static final BinaryTagIO.Reader DEFAULT_LIMIT;
    
    BinaryTagReaderImpl(final long maxBytes) {
        this.maxBytes = maxBytes;
    }
    
    @NotNull
    @Override
    public CompoundBinaryTag read(@NotNull final Path path, final BinaryTagIO.Compression compression) throws IOException {
        final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        final CompoundBinaryTag read = this.read(inputStream, compression);
        if (inputStream != null) {
            inputStream.close();
        }
        return read;
    }
    
    @NotNull
    @Override
    public CompoundBinaryTag read(@NotNull final InputStream input, final BinaryTagIO.Compression compression) throws IOException {
        final DataInputStream input2 = new DataInputStream(new BufferedInputStream(compression.decompress(IOStreamUtil.closeShield(input))));
        final CompoundBinaryTag read = this.read((DataInput)input2);
        input2.close();
        return read;
    }
    
    @NotNull
    @Override
    public CompoundBinaryTag read(@NotNull DataInput var_1_13) throws IOException {
        if (!(var_1_13 instanceof TrackingDataInput)) {
            var_1_13 = new TrackingDataInput(var_1_13, this.maxBytes);
        }
        requireCompound(BinaryTagType.of(var_1_13.readByte()));
        var_1_13.skipBytes(var_1_13.readUnsignedShort());
        return (CompoundBinaryTag)BinaryTagTypes.COMPOUND.read(var_1_13);
    }
    
    @Override
    public Map.Entry readNamed(@NotNull final Path path, final BinaryTagIO.Compression compression) throws IOException {
        final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        final Map.Entry named = this.readNamed(inputStream, compression);
        if (inputStream != null) {
            inputStream.close();
        }
        return named;
    }
    
    @Override
    public Map.Entry readNamed(@NotNull final InputStream input, final BinaryTagIO.Compression compression) throws IOException {
        final DataInputStream input2 = new DataInputStream(new BufferedInputStream(compression.decompress(IOStreamUtil.closeShield(input))));
        final Map.Entry named = this.readNamed((DataInput)input2);
        input2.close();
        return named;
    }
    
    @Override
    public Map.Entry readNamed(@NotNull final DataInput input) throws IOException {
        requireCompound(BinaryTagType.of(input.readByte()));
        return new AbstractMap.SimpleImmutableEntry(input.readUTF(), BinaryTagTypes.COMPOUND.read(input));
    }
    
    private static void requireCompound(final BinaryTagType type) throws IOException {
        if (type != BinaryTagTypes.COMPOUND) {
            throw new IOException(String.format("Expected root tag to be a %s, was %s", BinaryTagTypes.COMPOUND, type));
        }
    }
    
    static {
        UNLIMITED = new BinaryTagReaderImpl(-1L);
        DEFAULT_LIMIT = new BinaryTagReaderImpl(131082L);
    }
}
