package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;

final class BinaryTagWriterImpl implements BinaryTagIO.Writer
{
    static final BinaryTagIO.Writer INSTANCE;
    
    @Override
    public void write(@NotNull final CompoundBinaryTag tag, @NotNull final Path path, final BinaryTagIO.Compression compression) throws IOException {
        final OutputStream outputStream = Files.newOutputStream(path, new OpenOption[0]);
        this.write(tag, outputStream, compression);
        if (outputStream != null) {
            outputStream.close();
        }
    }
    
    @Override
    public void write(@NotNull final CompoundBinaryTag tag, @NotNull final OutputStream output, final BinaryTagIO.Compression compression) throws IOException {
        final DataOutputStream output2 = new DataOutputStream(new BufferedOutputStream(compression.compress(IOStreamUtil.closeShield(output))));
        this.write(tag, (DataOutput)output2);
        output2.close();
    }
    
    @Override
    public void write(@NotNull final CompoundBinaryTag tag, @NotNull final DataOutput output) throws IOException {
        output.writeByte(BinaryTagTypes.COMPOUND.id());
        output.writeUTF("");
        BinaryTagTypes.COMPOUND.write(tag, output);
    }
    
    @Override
    public void writeNamed(final Map.Entry tag, @NotNull final Path path, final BinaryTagIO.Compression compression) throws IOException {
        final OutputStream outputStream = Files.newOutputStream(path, new OpenOption[0]);
        this.writeNamed(tag, outputStream, compression);
        if (outputStream != null) {
            outputStream.close();
        }
    }
    
    @Override
    public void writeNamed(final Map.Entry tag, @NotNull final OutputStream output, final BinaryTagIO.Compression compression) throws IOException {
        final DataOutputStream output2 = new DataOutputStream(new BufferedOutputStream(compression.compress(IOStreamUtil.closeShield(output))));
        this.writeNamed(tag, (DataOutput)output2);
        output2.close();
    }
    
    @Override
    public void writeNamed(final Map.Entry tag, @NotNull final DataOutput output) throws IOException {
        output.writeByte(BinaryTagTypes.COMPOUND.id());
        output.writeUTF(tag.getKey());
        BinaryTagTypes.COMPOUND.write((BinaryTag)tag.getValue(), output);
    }
    
    static {
        INSTANCE = new BinaryTagWriterImpl();
    }
}
