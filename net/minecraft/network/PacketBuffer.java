package net.minecraft.network;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import com.google.common.base.*;
import io.netty.handler.codec.*;
import java.nio.*;
import java.io.*;
import java.nio.channels.*;
import io.netty.buffer.*;
import java.nio.charset.*;
import io.netty.util.*;

public class PacketBuffer extends ByteBuf
{
    private final ByteBuf buf;
    private static final String __OBFID;
    
    public PacketBuffer(final ByteBuf buf) {
        this.buf = buf;
    }
    
    public static int getVarIntSize(final int n) {
        while (1 < 5) {
            if ((n & 0xFFFFFF80) == 0x0) {
                return 1;
            }
            int n2 = 0;
            ++n2;
        }
        return 5;
    }
    
    public void writeByteArray(final byte[] array) {
        this.writeVarIntToBuffer(array.length);
        this.writeBytes(array);
    }
    
    public byte[] readByteArray() {
        final byte[] array = new byte[this.readVarIntFromBuffer()];
        this.readBytes(array);
        return array;
    }
    
    public BlockPos readBlockPos() {
        return BlockPos.fromLong(this.readLong());
    }
    
    public void writeBlockPos(final BlockPos blockPos) {
        this.writeLong(blockPos.toLong());
    }
    
    public IChatComponent readChatComponent() {
        return IChatComponent.Serializer.jsonToComponent(this.readStringFromBuffer(32767));
    }
    
    public void writeChatComponent(final IChatComponent chatComponent) {
        this.writeString(IChatComponent.Serializer.componentToJson(chatComponent));
    }
    
    public Enum readEnumValue(final Class clazz) {
        return ((Enum[])clazz.getEnumConstants())[this.readVarIntFromBuffer()];
    }
    
    public void writeEnumValue(final Enum enum1) {
        this.writeVarIntToBuffer(enum1.ordinal());
    }
    
    public int readVarIntFromBuffer() {
        byte byte1;
        do {
            byte1 = this.readByte();
            int n = 0;
            ++n;
            if (0 > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((byte1 & 0x80) == 0x80);
        return 0;
    }
    
    public long readVarLong() {
        long n = 0L;
        byte byte1;
        do {
            byte1 = this.readByte();
            final long n2 = n;
            final long n3 = byte1 & 0x7F;
            final int n4 = 0;
            int n5 = 0;
            ++n5;
            n = (n2 | n3 << n4 * 7);
            if (0 > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while ((byte1 & 0x80) == 0x80);
        return n;
    }
    
    public void writeUuid(final UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }
    
    public UUID readUuid() {
        return new UUID(this.readLong(), this.readLong());
    }
    
    public void writeVarIntToBuffer(int n) {
        while ((n & 0xFFFFFF80) != 0x0) {
            this.writeByte((n & 0x7F) | 0x80);
            n >>>= 7;
        }
        this.writeByte(n);
    }
    
    public void writeVarLong(long n) {
        while ((n & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
            this.writeByte((int)(n & 0x7FL) | 0x80);
            n >>>= 7;
        }
        this.writeByte((int)n);
    }
    
    public void writeNBTTagCompoundToBuffer(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound == null) {
            this.writeByte(0);
        }
        else {
            CompressedStreamTools.write(nbtTagCompound, new ByteBufOutputStream(this));
        }
    }
    
    public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
        final int readerIndex = this.readerIndex();
        if (this.readByte() == 0) {
            return null;
        }
        this.readerIndex(readerIndex);
        return CompressedStreamTools.func_152456_a(new ByteBufInputStream(this), new NBTSizeTracker(2097152L));
    }
    
    public void writeItemStackToBuffer(final ItemStack itemStack) {
        if (itemStack == null) {
            this.writeShort(-1);
        }
        else {
            this.writeShort(Item.getIdFromItem(itemStack.getItem()));
            this.writeByte(itemStack.stackSize);
            this.writeShort(itemStack.getMetadata());
            NBTTagCompound tagCompound = null;
            if (itemStack.getItem().isDamageable() || itemStack.getItem().getShareTag()) {
                tagCompound = itemStack.getTagCompound();
            }
            this.writeNBTTagCompoundToBuffer(tagCompound);
        }
    }
    
    public ItemStack readItemStackFromBuffer() throws IOException {
        ItemStack itemStack = null;
        final short short1 = this.readShort();
        if (short1 >= 0) {
            itemStack = new ItemStack(Item.getItemById(short1), this.readByte(), this.readShort());
            itemStack.setTagCompound(this.readNBTTagCompoundFromBuffer());
        }
        return itemStack;
    }
    
    public String readStringFromBuffer(final int n) {
        final int varIntFromBuffer = this.readVarIntFromBuffer();
        if (varIntFromBuffer > n * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + varIntFromBuffer + " > " + n * 4 + ")");
        }
        if (varIntFromBuffer < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        final String s = new String(this.readBytes(varIntFromBuffer).array(), Charsets.UTF_8);
        if (s.length() > n) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + varIntFromBuffer + " > " + n + ")");
        }
        return s;
    }
    
    public PacketBuffer writeString(final String s) {
        final byte[] bytes = s.getBytes(Charsets.UTF_8);
        if (bytes.length > 32767) {
            throw new EncoderException("String too big (was " + s.length() + " bytes encoded, max " + 32767 + ")");
        }
        this.writeVarIntToBuffer(bytes.length);
        this.writeBytes(bytes);
        return this;
    }
    
    @Override
    public int capacity() {
        return this.buf.capacity();
    }
    
    @Override
    public ByteBuf capacity(final int n) {
        return this.buf.capacity(n);
    }
    
    @Override
    public int maxCapacity() {
        return this.buf.maxCapacity();
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.buf.alloc();
    }
    
    @Override
    public ByteOrder order() {
        return this.buf.order();
    }
    
    @Override
    public ByteBuf order(final ByteOrder byteOrder) {
        return this.buf.order(byteOrder);
    }
    
    @Override
    public ByteBuf unwrap() {
        return this.buf.unwrap();
    }
    
    @Override
    public boolean isDirect() {
        return this.buf.isDirect();
    }
    
    @Override
    public int readerIndex() {
        return this.buf.readerIndex();
    }
    
    @Override
    public ByteBuf readerIndex(final int n) {
        return this.buf.readerIndex(n);
    }
    
    @Override
    public int writerIndex() {
        return this.buf.writerIndex();
    }
    
    @Override
    public ByteBuf writerIndex(final int n) {
        return this.buf.writerIndex(n);
    }
    
    @Override
    public ByteBuf setIndex(final int n, final int n2) {
        return this.buf.setIndex(n, n2);
    }
    
    @Override
    public int readableBytes() {
        return this.buf.readableBytes();
    }
    
    @Override
    public int writableBytes() {
        return this.buf.writableBytes();
    }
    
    @Override
    public int maxWritableBytes() {
        return this.buf.maxWritableBytes();
    }
    
    @Override
    public boolean isReadable() {
        return this.buf.isReadable();
    }
    
    @Override
    public boolean isReadable(final int n) {
        return this.buf.isReadable(n);
    }
    
    @Override
    public boolean isWritable() {
        return this.buf.isWritable();
    }
    
    @Override
    public boolean isWritable(final int n) {
        return this.buf.isWritable(n);
    }
    
    @Override
    public ByteBuf clear() {
        return this.buf.clear();
    }
    
    @Override
    public ByteBuf markReaderIndex() {
        return this.buf.markReaderIndex();
    }
    
    @Override
    public ByteBuf resetReaderIndex() {
        return this.buf.resetReaderIndex();
    }
    
    @Override
    public ByteBuf markWriterIndex() {
        return this.buf.markWriterIndex();
    }
    
    @Override
    public ByteBuf resetWriterIndex() {
        return this.buf.resetWriterIndex();
    }
    
    @Override
    public ByteBuf discardReadBytes() {
        return this.buf.discardReadBytes();
    }
    
    @Override
    public ByteBuf discardSomeReadBytes() {
        return this.buf.discardSomeReadBytes();
    }
    
    @Override
    public ByteBuf ensureWritable(final int n) {
        return this.buf.ensureWritable(n);
    }
    
    @Override
    public int ensureWritable(final int n, final boolean b) {
        return this.buf.ensureWritable(n, b);
    }
    
    @Override
    public boolean getBoolean(final int n) {
        return this.buf.getBoolean(n);
    }
    
    @Override
    public byte getByte(final int n) {
        return this.buf.getByte(n);
    }
    
    @Override
    public short getUnsignedByte(final int n) {
        return this.buf.getUnsignedByte(n);
    }
    
    @Override
    public short getShort(final int n) {
        return this.buf.getShort(n);
    }
    
    @Override
    public int getUnsignedShort(final int n) {
        return this.buf.getUnsignedShort(n);
    }
    
    @Override
    public int getMedium(final int n) {
        return this.buf.getMedium(n);
    }
    
    @Override
    public int getUnsignedMedium(final int n) {
        return this.buf.getUnsignedMedium(n);
    }
    
    @Override
    public int getInt(final int n) {
        return this.buf.getInt(n);
    }
    
    @Override
    public long getUnsignedInt(final int n) {
        return this.buf.getUnsignedInt(n);
    }
    
    @Override
    public long getLong(final int n) {
        return this.buf.getLong(n);
    }
    
    @Override
    public char getChar(final int n) {
        return this.buf.getChar(n);
    }
    
    @Override
    public float getFloat(final int n) {
        return this.buf.getFloat(n);
    }
    
    @Override
    public double getDouble(final int n) {
        return this.buf.getDouble(n);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
        return this.buf.getBytes(n, byteBuf);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return this.buf.getBytes(n, byteBuf, n2);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        return this.buf.getBytes(n, byteBuf, n2, n3);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array) {
        return this.buf.getBytes(n, array);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        return this.buf.getBytes(n, array, n2, n3);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        return this.buf.getBytes(n, byteBuffer);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        return this.buf.getBytes(n, outputStream, n2);
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        return this.buf.getBytes(n, gatheringByteChannel, n2);
    }
    
    @Override
    public ByteBuf setBoolean(final int n, final boolean b) {
        return this.buf.setBoolean(n, b);
    }
    
    @Override
    public ByteBuf setByte(final int n, final int n2) {
        return this.buf.setByte(n, n2);
    }
    
    @Override
    public ByteBuf setShort(final int n, final int n2) {
        return this.buf.setShort(n, n2);
    }
    
    @Override
    public ByteBuf setMedium(final int n, final int n2) {
        return this.buf.setMedium(n, n2);
    }
    
    @Override
    public ByteBuf setInt(final int n, final int n2) {
        return this.buf.setInt(n, n2);
    }
    
    @Override
    public ByteBuf setLong(final int n, final long n2) {
        return this.buf.setLong(n, n2);
    }
    
    @Override
    public ByteBuf setChar(final int n, final int n2) {
        return this.buf.setChar(n, n2);
    }
    
    @Override
    public ByteBuf setFloat(final int n, final float n2) {
        return this.buf.setFloat(n, n2);
    }
    
    @Override
    public ByteBuf setDouble(final int n, final double n2) {
        return this.buf.setDouble(n, n2);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
        return this.buf.setBytes(n, byteBuf);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return this.buf.setBytes(n, byteBuf, n2);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        return this.buf.setBytes(n, byteBuf, n2, n3);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array) {
        return this.buf.setBytes(n, array);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        return this.buf.setBytes(n, array, n2, n3);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        return this.buf.setBytes(n, byteBuffer);
    }
    
    @Override
    public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
        return this.buf.setBytes(n, inputStream, n2);
    }
    
    @Override
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
        return this.buf.setBytes(n, scatteringByteChannel, n2);
    }
    
    @Override
    public ByteBuf setZero(final int n, final int n2) {
        return this.buf.setZero(n, n2);
    }
    
    @Override
    public boolean readBoolean() {
        return this.buf.readBoolean();
    }
    
    @Override
    public byte readByte() {
        return this.buf.readByte();
    }
    
    @Override
    public short readUnsignedByte() {
        return this.buf.readUnsignedByte();
    }
    
    @Override
    public short readShort() {
        return this.buf.readShort();
    }
    
    @Override
    public int readUnsignedShort() {
        return this.buf.readUnsignedShort();
    }
    
    @Override
    public int readMedium() {
        return this.buf.readMedium();
    }
    
    @Override
    public int readUnsignedMedium() {
        return this.buf.readUnsignedMedium();
    }
    
    @Override
    public int readInt() {
        return this.buf.readInt();
    }
    
    @Override
    public long readUnsignedInt() {
        return this.buf.readUnsignedInt();
    }
    
    @Override
    public long readLong() {
        return this.buf.readLong();
    }
    
    @Override
    public char readChar() {
        return this.buf.readChar();
    }
    
    @Override
    public float readFloat() {
        return this.buf.readFloat();
    }
    
    @Override
    public double readDouble() {
        return this.buf.readDouble();
    }
    
    @Override
    public ByteBuf readBytes(final int n) {
        return this.buf.readBytes(n);
    }
    
    @Override
    public ByteBuf readSlice(final int n) {
        return this.buf.readSlice(n);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf) {
        return this.buf.readBytes(byteBuf);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
        return this.buf.readBytes(byteBuf, n);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return this.buf.readBytes(byteBuf, n, n2);
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array) {
        return this.buf.readBytes(array);
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
        return this.buf.readBytes(array, n, n2);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuffer byteBuffer) {
        return this.buf.readBytes(byteBuffer);
    }
    
    @Override
    public ByteBuf readBytes(final OutputStream outputStream, final int n) throws IOException {
        return this.buf.readBytes(outputStream, n);
    }
    
    @Override
    public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) throws IOException {
        return this.buf.readBytes(gatheringByteChannel, n);
    }
    
    @Override
    public ByteBuf skipBytes(final int n) {
        return this.buf.skipBytes(n);
    }
    
    @Override
    public ByteBuf writeBoolean(final boolean b) {
        return this.buf.writeBoolean(b);
    }
    
    @Override
    public ByteBuf writeByte(final int n) {
        return this.buf.writeByte(n);
    }
    
    @Override
    public ByteBuf writeShort(final int n) {
        return this.buf.writeShort(n);
    }
    
    @Override
    public ByteBuf writeMedium(final int n) {
        return this.buf.writeMedium(n);
    }
    
    @Override
    public ByteBuf writeInt(final int n) {
        return this.buf.writeInt(n);
    }
    
    @Override
    public ByteBuf writeLong(final long n) {
        return this.buf.writeLong(n);
    }
    
    @Override
    public ByteBuf writeChar(final int n) {
        return this.buf.writeChar(n);
    }
    
    @Override
    public ByteBuf writeFloat(final float n) {
        return this.buf.writeFloat(n);
    }
    
    @Override
    public ByteBuf writeDouble(final double n) {
        return this.buf.writeDouble(n);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf) {
        return this.buf.writeBytes(byteBuf);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
        return this.buf.writeBytes(byteBuf, n);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return this.buf.writeBytes(byteBuf, n, n2);
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array) {
        return this.buf.writeBytes(array);
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
        return this.buf.writeBytes(array, n, n2);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
        return this.buf.writeBytes(byteBuffer);
    }
    
    @Override
    public int writeBytes(final InputStream inputStream, final int n) throws IOException {
        return this.buf.writeBytes(inputStream, n);
    }
    
    @Override
    public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) throws IOException {
        return this.buf.writeBytes(scatteringByteChannel, n);
    }
    
    @Override
    public ByteBuf writeZero(final int n) {
        return this.buf.writeZero(n);
    }
    
    @Override
    public int indexOf(final int n, final int n2, final byte b) {
        return this.buf.indexOf(n, n2, b);
    }
    
    @Override
    public int bytesBefore(final byte b) {
        return this.buf.bytesBefore(b);
    }
    
    @Override
    public int bytesBefore(final int n, final byte b) {
        return this.buf.bytesBefore(n, b);
    }
    
    @Override
    public int bytesBefore(final int n, final int n2, final byte b) {
        return this.buf.bytesBefore(n, n2, b);
    }
    
    @Override
    public int forEachByte(final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByte(byteBufProcessor);
    }
    
    @Override
    public int forEachByte(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByte(n, n2, byteBufProcessor);
    }
    
    @Override
    public int forEachByteDesc(final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByteDesc(byteBufProcessor);
    }
    
    @Override
    public int forEachByteDesc(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByteDesc(n, n2, byteBufProcessor);
    }
    
    @Override
    public ByteBuf copy() {
        return this.buf.copy();
    }
    
    @Override
    public ByteBuf copy(final int n, final int n2) {
        return this.buf.copy(n, n2);
    }
    
    @Override
    public ByteBuf slice() {
        return this.buf.slice();
    }
    
    @Override
    public ByteBuf slice(final int n, final int n2) {
        return this.buf.slice(n, n2);
    }
    
    @Override
    public ByteBuf duplicate() {
        return this.buf.duplicate();
    }
    
    @Override
    public int nioBufferCount() {
        return this.buf.nioBufferCount();
    }
    
    @Override
    public ByteBuffer nioBuffer() {
        return this.buf.nioBuffer();
    }
    
    @Override
    public ByteBuffer nioBuffer(final int n, final int n2) {
        return this.buf.nioBuffer(n, n2);
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        return this.buf.internalNioBuffer(n, n2);
    }
    
    @Override
    public ByteBuffer[] nioBuffers() {
        return this.buf.nioBuffers();
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int n, final int n2) {
        return this.buf.nioBuffers(n, n2);
    }
    
    @Override
    public boolean hasArray() {
        return this.buf.hasArray();
    }
    
    @Override
    public byte[] array() {
        return this.buf.array();
    }
    
    @Override
    public int arrayOffset() {
        return this.buf.arrayOffset();
    }
    
    @Override
    public boolean hasMemoryAddress() {
        return this.buf.hasMemoryAddress();
    }
    
    @Override
    public long memoryAddress() {
        return this.buf.memoryAddress();
    }
    
    @Override
    public String toString(final Charset charset) {
        return this.buf.toString(charset);
    }
    
    @Override
    public String toString(final int n, final int n2, final Charset charset) {
        return this.buf.toString(n, n2, charset);
    }
    
    @Override
    public int hashCode() {
        return this.buf.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this.buf.equals(o);
    }
    
    @Override
    public int compareTo(final ByteBuf byteBuf) {
        return this.buf.compareTo(byteBuf);
    }
    
    @Override
    public String toString() {
        return this.buf.toString();
    }
    
    @Override
    public ByteBuf retain(final int n) {
        return this.buf.retain(n);
    }
    
    @Override
    public ByteBuf retain() {
        return this.buf.retain();
    }
    
    @Override
    public int refCnt() {
        return this.buf.refCnt();
    }
    
    @Override
    public boolean release() {
        return this.buf.release();
    }
    
    @Override
    public boolean release(final int n) {
        return this.buf.release(n);
    }
    
    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
    
    @Override
    public ReferenceCounted retain(final int n) {
        return this.retain(n);
    }
    
    static {
        __OBFID = "CL_00001251";
    }
}
