package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.io.*;
import java.util.*;

public final class BinaryTagTypes
{
    public static final BinaryTagType END;
    public static final BinaryTagType BYTE;
    public static final BinaryTagType SHORT;
    public static final BinaryTagType INT;
    public static final BinaryTagType LONG;
    public static final BinaryTagType FLOAT;
    public static final BinaryTagType DOUBLE;
    public static final BinaryTagType BYTE_ARRAY;
    public static final BinaryTagType STRING;
    public static final BinaryTagType LIST;
    public static final BinaryTagType COMPOUND;
    public static final BinaryTagType INT_ARRAY;
    public static final BinaryTagType LONG_ARRAY;
    
    private BinaryTagTypes() {
    }
    
    private static void lambda$static$24(final LongArrayBinaryTag tag, final DataOutput dataOutput) throws IOException {
        final long[] value = LongArrayBinaryTagImpl.value(tag);
        final int length = value.length;
        dataOutput.writeInt(length);
        while (0 < length) {
            dataOutput.writeLong(value[0]);
            int n = 0;
            ++n;
        }
    }
    
    private static LongArrayBinaryTag lambda$static$23(final DataInput input) throws IOException {
        final int int1 = input.readInt();
        final BinaryTagScope enter = TrackingDataInput.enter(input, int1 * 8L);
        final long[] value = new long[int1];
        while (0 < int1) {
            value[0] = input.readLong();
            int n = 0;
            ++n;
        }
        final LongArrayBinaryTag of = LongArrayBinaryTag.of(value);
        if (enter != null) {
            enter.close();
        }
        return of;
    }
    
    private static void lambda$static$22(final IntArrayBinaryTag tag, final DataOutput dataOutput) throws IOException {
        final int[] value = IntArrayBinaryTagImpl.value(tag);
        final int length = value.length;
        dataOutput.writeInt(length);
        while (0 < length) {
            dataOutput.writeInt(value[0]);
            int n = 0;
            ++n;
        }
    }
    
    private static IntArrayBinaryTag lambda$static$21(final DataInput input) throws IOException {
        final int int1 = input.readInt();
        final BinaryTagScope enter = TrackingDataInput.enter(input, int1 * 4L);
        final int[] value = new int[int1];
        while (0 < int1) {
            value[0] = input.readInt();
            int n = 0;
            ++n;
        }
        final IntArrayBinaryTag of = IntArrayBinaryTag.of(value);
        if (enter != null) {
            enter.close();
        }
        return of;
    }
    
    private static void lambda$static$20(final CompoundBinaryTag compoundBinaryTag, final DataOutput output) throws IOException {
        for (final Map.Entry<K, BinaryTag> entry : compoundBinaryTag) {
            final BinaryTag tag = entry.getValue();
            if (tag != null) {
                final BinaryTagType type = tag.type();
                output.writeByte(type.id());
                if (type == BinaryTagTypes.END) {
                    continue;
                }
                output.writeUTF((String)entry.getKey());
                BinaryTagType.write(type, tag, output);
            }
        }
        output.writeByte(BinaryTagTypes.END.id());
    }
    
    private static CompoundBinaryTag lambda$static$19(final DataInput dataInput) throws IOException {
        final BinaryTagScope enter = TrackingDataInput.enter(dataInput);
        final HashMap<String, BinaryTag> tags = new HashMap<String, BinaryTag>();
        BinaryTagType of;
        while ((of = BinaryTagType.of(dataInput.readByte())) != BinaryTagTypes.END) {
            tags.put(dataInput.readUTF(), of.read(dataInput));
        }
        final CompoundBinaryTagImpl compoundBinaryTagImpl = new CompoundBinaryTagImpl(tags);
        if (enter != null) {
            enter.close();
        }
        return compoundBinaryTagImpl;
    }
    
    private static void lambda$static$18(final ListBinaryTag listBinaryTag, final DataOutput output) throws IOException {
        output.writeByte(listBinaryTag.elementType().id());
        output.writeInt(listBinaryTag.size());
        for (final BinaryTag tag : listBinaryTag) {
            BinaryTagType.write(tag.type(), tag, output);
        }
    }
    
    private static ListBinaryTag lambda$static$17(final DataInput dataInput) throws IOException {
        final BinaryTagType of = BinaryTagType.of(dataInput.readByte());
        final int int1 = dataInput.readInt();
        final BinaryTagScope enter = TrackingDataInput.enter(dataInput, int1 * 8L);
        final ArrayList tags = new ArrayList<BinaryTag>(int1);
        while (0 < int1) {
            tags.add(of.read(dataInput));
            int n = 0;
            ++n;
        }
        final ListBinaryTag of2 = ListBinaryTag.of(of, tags);
        if (enter != null) {
            enter.close();
        }
        return of2;
    }
    
    private static void lambda$static$16(final StringBinaryTag stringBinaryTag, final DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(stringBinaryTag.value());
    }
    
    private static StringBinaryTag lambda$static$15(final DataInput dataInput) throws IOException {
        return StringBinaryTag.of(dataInput.readUTF());
    }
    
    private static void lambda$static$14(final ByteArrayBinaryTag tag, final DataOutput dataOutput) throws IOException {
        final byte[] value = ByteArrayBinaryTagImpl.value(tag);
        dataOutput.writeInt(value.length);
        dataOutput.write(value);
    }
    
    private static ByteArrayBinaryTag lambda$static$13(final DataInput input) throws IOException {
        final int int1 = input.readInt();
        final BinaryTagScope enter = TrackingDataInput.enter(input, int1);
        final byte[] value = new byte[int1];
        input.readFully(value);
        final ByteArrayBinaryTag of = ByteArrayBinaryTag.of(value);
        if (enter != null) {
            enter.close();
        }
        return of;
    }
    
    private static void lambda$static$12(final DoubleBinaryTag doubleBinaryTag, final DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(doubleBinaryTag.value());
    }
    
    private static DoubleBinaryTag lambda$static$11(final DataInput dataInput) throws IOException {
        return DoubleBinaryTag.of(dataInput.readDouble());
    }
    
    private static void lambda$static$10(final FloatBinaryTag floatBinaryTag, final DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(floatBinaryTag.value());
    }
    
    private static FloatBinaryTag lambda$static$9(final DataInput dataInput) throws IOException {
        return FloatBinaryTag.of(dataInput.readFloat());
    }
    
    private static void lambda$static$8(final LongBinaryTag longBinaryTag, final DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(longBinaryTag.value());
    }
    
    private static LongBinaryTag lambda$static$7(final DataInput dataInput) throws IOException {
        return LongBinaryTag.of(dataInput.readLong());
    }
    
    private static void lambda$static$6(final IntBinaryTag intBinaryTag, final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(intBinaryTag.value());
    }
    
    private static IntBinaryTag lambda$static$5(final DataInput dataInput) throws IOException {
        return IntBinaryTag.of(dataInput.readInt());
    }
    
    private static void lambda$static$4(final ShortBinaryTag shortBinaryTag, final DataOutput dataOutput) throws IOException {
        dataOutput.writeShort(shortBinaryTag.value());
    }
    
    private static ShortBinaryTag lambda$static$3(final DataInput dataInput) throws IOException {
        return ShortBinaryTag.of(dataInput.readShort());
    }
    
    private static void lambda$static$2(final ByteBinaryTag byteBinaryTag, final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(byteBinaryTag.value());
    }
    
    private static ByteBinaryTag lambda$static$1(final DataInput dataInput) throws IOException {
        return ByteBinaryTag.of(dataInput.readByte());
    }
    
    private static EndBinaryTag lambda$static$0(final DataInput dataInput) throws IOException {
        return EndBinaryTag.get();
    }
    
    static {
        END = BinaryTagType.register(EndBinaryTag.class, (byte)0, BinaryTagTypes::lambda$static$0, null);
        BYTE = BinaryTagType.registerNumeric(ByteBinaryTag.class, (byte)1, BinaryTagTypes::lambda$static$1, BinaryTagTypes::lambda$static$2);
        SHORT = BinaryTagType.registerNumeric(ShortBinaryTag.class, (byte)2, BinaryTagTypes::lambda$static$3, BinaryTagTypes::lambda$static$4);
        INT = BinaryTagType.registerNumeric(IntBinaryTag.class, (byte)3, BinaryTagTypes::lambda$static$5, BinaryTagTypes::lambda$static$6);
        LONG = BinaryTagType.registerNumeric(LongBinaryTag.class, (byte)4, BinaryTagTypes::lambda$static$7, BinaryTagTypes::lambda$static$8);
        FLOAT = BinaryTagType.registerNumeric(FloatBinaryTag.class, (byte)5, BinaryTagTypes::lambda$static$9, BinaryTagTypes::lambda$static$10);
        DOUBLE = BinaryTagType.registerNumeric(DoubleBinaryTag.class, (byte)6, BinaryTagTypes::lambda$static$11, BinaryTagTypes::lambda$static$12);
        BYTE_ARRAY = BinaryTagType.register(ByteArrayBinaryTag.class, (byte)7, BinaryTagTypes::lambda$static$13, BinaryTagTypes::lambda$static$14);
        STRING = BinaryTagType.register(StringBinaryTag.class, (byte)8, BinaryTagTypes::lambda$static$15, BinaryTagTypes::lambda$static$16);
        LIST = BinaryTagType.register(ListBinaryTag.class, (byte)9, BinaryTagTypes::lambda$static$17, BinaryTagTypes::lambda$static$18);
        COMPOUND = BinaryTagType.register(CompoundBinaryTag.class, (byte)10, BinaryTagTypes::lambda$static$19, BinaryTagTypes::lambda$static$20);
        INT_ARRAY = BinaryTagType.register(IntArrayBinaryTag.class, (byte)11, BinaryTagTypes::lambda$static$21, BinaryTagTypes::lambda$static$22);
        LONG_ARRAY = BinaryTagType.register(LongArrayBinaryTag.class, (byte)12, BinaryTagTypes::lambda$static$23, BinaryTagTypes::lambda$static$24);
    }
}
