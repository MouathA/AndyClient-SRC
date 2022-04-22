package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.util.*;

public final class PaletteType1_18 extends Type
{
    private final int globalPaletteBits;
    private final PaletteType type;
    
    public PaletteType1_18(final PaletteType type, final int globalPaletteBits) {
        super(DataPalette.class);
        this.globalPaletteBits = globalPaletteBits;
        this.type = type;
    }
    
    @Override
    public DataPalette read(final ByteBuf byteBuf) throws Exception {
        final byte byte1;
        int globalPaletteBits = byte1 = byteBuf.readByte();
        if (globalPaletteBits > this.type.highestBitsPerValue()) {
            globalPaletteBits = this.globalPaletteBits;
        }
        if (globalPaletteBits == 0) {
            final DataPaletteImpl dataPaletteImpl = new DataPaletteImpl(this.type.size(), 1);
            dataPaletteImpl.addId(Type.VAR_INT.readPrimitive(byteBuf));
            Type.VAR_INT.readPrimitive(byteBuf);
            return dataPaletteImpl;
        }
        DataPaletteImpl dataPaletteImpl2;
        if (globalPaletteBits != this.globalPaletteBits) {
            final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
            dataPaletteImpl2 = new DataPaletteImpl(this.type.size(), primitive);
            while (0 < primitive) {
                dataPaletteImpl2.addId(Type.VAR_INT.readPrimitive(byteBuf));
                int n = 0;
                ++n;
            }
        }
        else {
            dataPaletteImpl2 = new DataPaletteImpl(this.type.size());
        }
        final long[] array = new long[Type.VAR_INT.readPrimitive(byteBuf)];
        if (array.length > 0) {
            final int n = (char)(64 / globalPaletteBits);
            final int n2 = (this.type.size() + 0 - 1) / 0;
            if (array.length != n2) {
                throw new IllegalStateException("Palette data length (" + array.length + ") does not match expected length (" + n2 + ")! bitsPerValue=" + globalPaletteBits + ", originalBitsPerValue=" + byte1);
            }
            while (0 < array.length) {
                array[0] = byteBuf.readLong();
                int n3 = 0;
                ++n3;
            }
            CompactArrayUtil.iterateCompactArrayWithPadding(globalPaletteBits, this.type.size(), array, (globalPaletteBits == this.globalPaletteBits) ? dataPaletteImpl2::setIdAt : dataPaletteImpl2::setPaletteIndexAt);
        }
        return dataPaletteImpl2;
    }
    
    public void write(final ByteBuf byteBuf, final DataPalette dataPalette) throws Exception {
        if (dataPalette.size() == 1) {
            byteBuf.writeByte(0);
            Type.VAR_INT.writePrimitive(byteBuf, dataPalette.idByIndex(0));
            Type.VAR_INT.writePrimitive(byteBuf, 0);
            return;
        }
        int n = Math.max((this.type == PaletteType.BLOCKS) ? 4 : 1, MathUtil.ceilLog2(dataPalette.size()));
        if (n > this.type.highestBitsPerValue()) {
            n = this.globalPaletteBits;
        }
        byteBuf.writeByte(n);
        if (n != this.globalPaletteBits) {
            Type.VAR_INT.writePrimitive(byteBuf, dataPalette.size());
            while (0 < dataPalette.size()) {
                Type.VAR_INT.writePrimitive(byteBuf, dataPalette.idByIndex(0));
                int n2 = 0;
                ++n2;
            }
        }
        final long[] compactArrayWithPadding = CompactArrayUtil.createCompactArrayWithPadding(n, this.type.size(), (n == this.globalPaletteBits) ? dataPalette::idAt : dataPalette::paletteIndexAt);
        Type.VAR_INT.writePrimitive(byteBuf, compactArrayWithPadding.length);
        final long[] array = compactArrayWithPadding;
        while (0 < array.length) {
            byteBuf.writeLong(array[0]);
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (DataPalette)o);
    }
}
