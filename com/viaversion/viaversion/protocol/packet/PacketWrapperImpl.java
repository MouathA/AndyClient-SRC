package com.viaversion.viaversion.protocol.packet;

import com.viaversion.viaversion.api.protocol.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.exception.*;
import com.google.common.base.*;
import java.io.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.*;
import java.util.*;
import io.netty.channel.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class PacketWrapperImpl implements PacketWrapper
{
    private static final Protocol[] PROTOCOL_ARRAY;
    private final ByteBuf inputBuffer;
    private final UserConnection userConnection;
    private boolean send;
    private PacketType packetType;
    private int id;
    private final Deque readableObjects;
    private final List packetValues;
    
    public PacketWrapperImpl(final int id, final ByteBuf inputBuffer, final UserConnection userConnection) {
        this.send = true;
        this.readableObjects = new ArrayDeque();
        this.packetValues = new ArrayList();
        this.id = id;
        this.inputBuffer = inputBuffer;
        this.userConnection = userConnection;
    }
    
    public PacketWrapperImpl(final PacketType packetType, final ByteBuf inputBuffer, final UserConnection userConnection) {
        this.send = true;
        this.readableObjects = new ArrayDeque();
        this.packetValues = new ArrayList();
        this.packetType = packetType;
        this.id = ((packetType != null) ? packetType.getId() : -1);
        this.inputBuffer = inputBuffer;
        this.userConnection = userConnection;
    }
    
    @Override
    public Object get(final Type type, final int n) throws Exception {
        for (final Pair pair : this.packetValues) {
            if (pair.key() != type) {
                continue;
            }
            if (0 == n) {
                return pair.value();
            }
            int n2 = 0;
            ++n2;
        }
        throw new InformativeException(new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + n)).set("Type", type.getTypeName()).set("Index", n).set("Packet ID", this.getId()).set("Packet Type", this.packetType).set("Data", this.packetValues);
    }
    
    @Override
    public boolean is(final Type type, final int n) {
        final Iterator<Pair> iterator = this.packetValues.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().key() != type) {
                continue;
            }
            if (0 == n) {
                return true;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    @Override
    public boolean isReadable(final Type type, final int n) {
        final Iterator<Pair> iterator = this.readableObjects.iterator();
        while (iterator.hasNext()) {
            if (((Type)iterator.next().key()).getBaseClass() != type.getBaseClass()) {
                continue;
            }
            if (0 == n) {
                return true;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    @Override
    public void set(final Type type, final int n, final Object o) throws Exception {
        for (final Pair pair : this.packetValues) {
            if (pair.key() != type) {
                continue;
            }
            if (0 == n) {
                pair.setValue(this.attemptTransform(type, o));
                return;
            }
            int n2 = 0;
            ++n2;
        }
        throw new InformativeException(new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + n)).set("Type", type.getTypeName()).set("Index", n).set("Packet ID", this.getId()).set("Packet Type", this.packetType);
    }
    
    @Override
    public Object read(final Type type) throws Exception {
        if (type == Type.NOTHING) {
            return null;
        }
        if (this.readableObjects.isEmpty()) {
            Preconditions.checkNotNull(this.inputBuffer, (Object)"This packet does not have an input buffer.");
            return type.read(this.inputBuffer);
        }
        final Pair pair = this.readableObjects.poll();
        final Type type2 = (Type)pair.key();
        if (type2 == type || (type.getBaseClass() == type2.getBaseClass() && type.getOutputClass() == type2.getOutputClass())) {
            return pair.value();
        }
        if (type2 == Type.NOTHING) {
            return this.read(type);
        }
        throw new InformativeException(new IOException("Unable to read type " + type.getTypeName() + ", found " + ((Type)pair.key()).getTypeName())).set("Type", type.getTypeName()).set("Packet ID", this.getId()).set("Packet Type", this.packetType).set("Data", this.packetValues);
    }
    
    @Override
    public void write(final Type type, final Object o) {
        this.packetValues.add(new Pair(type, this.attemptTransform(type, o)));
    }
    
    private Object attemptTransform(final Type type, final Object o) {
        if (o != null && !type.getOutputClass().isAssignableFrom(o.getClass())) {
            if (type instanceof TypeConverter) {
                return ((TypeConverter)type).from(o);
            }
            Via.getPlatform().getLogger().warning("Possible type mismatch: " + o.getClass().getName() + " -> " + type.getOutputClass());
        }
        return o;
    }
    
    @Override
    public Object passthrough(final Type type) throws Exception {
        final Object read = this.read(type);
        this.write(type, read);
        return read;
    }
    
    @Override
    public void passthroughAll() throws Exception {
        this.packetValues.addAll(this.readableObjects);
        this.readableObjects.clear();
        if (this.inputBuffer.isReadable()) {
            this.passthrough(Type.REMAINING_BYTES);
        }
    }
    
    @Override
    public void writeToBuffer(final ByteBuf byteBuf) throws Exception {
        if (this.id != -1) {
            Type.VAR_INT.writePrimitive(byteBuf, this.id);
        }
        if (!this.readableObjects.isEmpty()) {
            this.packetValues.addAll(this.readableObjects);
            this.readableObjects.clear();
        }
        for (final Pair pair : this.packetValues) {
            ((Type)pair.key()).write(byteBuf, pair.value());
            int n = 0;
            ++n;
        }
        this.writeRemaining(byteBuf);
    }
    
    @Override
    public void clearInputBuffer() {
        if (this.inputBuffer != null) {
            this.inputBuffer.clear();
        }
        this.readableObjects.clear();
    }
    
    @Override
    public void clearPacket() {
        this.clearInputBuffer();
        this.packetValues.clear();
    }
    
    private void writeRemaining(final ByteBuf byteBuf) {
        if (this.inputBuffer != null) {
            byteBuf.writeBytes(this.inputBuffer);
        }
    }
    
    @Override
    public void send(final Class clazz, final boolean b) throws Exception {
        this.send0(clazz, b, true);
    }
    
    @Override
    public void scheduleSend(final Class clazz, final boolean b) throws Exception {
        this.send0(clazz, b, false);
    }
    
    private void send0(final Class clazz, final boolean b, final boolean b2) throws Exception {
        if (this == 0) {
            return;
        }
        final ByteBuf constructPacket = this.constructPacket(clazz, b, Direction.CLIENTBOUND);
        if (b2) {
            this.user().sendRawPacket(constructPacket);
        }
        else {
            this.user().scheduleSendRawPacket(constructPacket);
        }
    }
    
    private ByteBuf constructPacket(final Class clazz, final boolean b, final Direction direction) throws Exception {
        final Protocol[] array = this.user().getProtocolInfo().getPipeline().pipes().toArray(PacketWrapperImpl.PROTOCOL_ARRAY);
        final boolean b2 = direction == Direction.CLIENTBOUND;
        while (0 < array.length && array[0].getClass() != clazz) {
            int n = 0;
            ++n;
        }
        throw new NoSuchElementException(clazz.getCanonicalName());
    }
    
    @Override
    public ChannelFuture sendFuture(final Class clazz) throws Exception {
        if (this == 0) {
            return this.user().sendRawPacketFuture(this.constructPacket(clazz, true, Direction.CLIENTBOUND));
        }
        return this.user().getChannel().newFailedFuture(new Exception("Cancelled packet"));
    }
    
    @Override
    public void sendRaw() throws Exception {
        this.sendRaw(true);
    }
    
    @Override
    public void scheduleSendRaw() throws Exception {
        this.sendRaw(false);
    }
    
    private void sendRaw(final boolean b) throws Exception {
        if (this == 0) {
            return;
        }
        final ByteBuf byteBuf = (this.inputBuffer == null) ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        this.writeToBuffer(byteBuf);
        if (b) {
            this.user().sendRawPacket(byteBuf.retain());
        }
        else {
            this.user().scheduleSendRawPacket(byteBuf.retain());
        }
        byteBuf.release();
    }
    
    @Override
    public PacketWrapperImpl create(final int n) {
        return new PacketWrapperImpl(n, null, this.user());
    }
    
    @Override
    public PacketWrapperImpl create(final int n, final PacketHandler packetHandler) throws Exception {
        final PacketWrapperImpl create = this.create(n);
        packetHandler.handle(create);
        return create;
    }
    
    @Override
    public PacketWrapperImpl apply(final Direction direction, final State state, final int n, final List list, final boolean b) throws Exception {
        final Protocol[] array = list.toArray(PacketWrapperImpl.PROTOCOL_ARRAY);
        return this.apply(direction, state, b ? (array.length - 1) : n, array, b);
    }
    
    @Override
    public PacketWrapperImpl apply(final Direction direction, final State state, final int n, final List list) throws Exception {
        return this.apply(direction, state, n, list.toArray(PacketWrapperImpl.PROTOCOL_ARRAY), false);
    }
    
    private PacketWrapperImpl apply(final Direction direction, final State state, final int n, final Protocol[] array, final boolean b) throws Exception {
        if (b) {
            for (int i = n; i >= 0; --i) {
                array[i].transform(direction, state, this);
                this.resetReader();
            }
        }
        else {
            for (int j = n; j < array.length; ++j) {
                array[j].transform(direction, state, this);
                this.resetReader();
            }
        }
        return this;
    }
    
    @Override
    public void cancel() {
        this.send = false;
    }
    
    @Override
    public UserConnection user() {
        return this.userConnection;
    }
    
    @Override
    public void resetReader() {
        for (int i = this.packetValues.size() - 1; i >= 0; --i) {
            this.readableObjects.addFirst(this.packetValues.get(i));
        }
        this.packetValues.clear();
    }
    
    @Override
    public void sendToServerRaw() throws Exception {
        this.sendToServerRaw(true);
    }
    
    @Override
    public void scheduleSendToServerRaw() throws Exception {
        this.sendToServerRaw(false);
    }
    
    private void sendToServerRaw(final boolean b) throws Exception {
        if (this == 0) {
            return;
        }
        final ByteBuf byteBuf = (this.inputBuffer == null) ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        this.writeToBuffer(byteBuf);
        if (b) {
            this.user().sendRawPacketToServer(byteBuf.retain());
        }
        else {
            this.user().scheduleSendRawPacketToServer(byteBuf.retain());
        }
        byteBuf.release();
    }
    
    @Override
    public void sendToServer(final Class clazz, final boolean b) throws Exception {
        this.sendToServer0(clazz, b, true);
    }
    
    @Override
    public void scheduleSendToServer(final Class clazz, final boolean b) throws Exception {
        this.sendToServer0(clazz, b, false);
    }
    
    private void sendToServer0(final Class clazz, final boolean b, final boolean b2) throws Exception {
        if (this == 0) {
            return;
        }
        final ByteBuf constructPacket = this.constructPacket(clazz, b, Direction.SERVERBOUND);
        if (b2) {
            this.user().sendRawPacketToServer(constructPacket);
        }
        else {
            this.user().scheduleSendRawPacketToServer(constructPacket);
        }
    }
    
    @Override
    public PacketType getPacketType() {
        return this.packetType;
    }
    
    @Override
    public void setPacketType(final PacketType packetType) {
        this.packetType = packetType;
        this.id = ((packetType != null) ? packetType.getId() : -1);
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    @Deprecated
    @Override
    public void setId(final int id) {
        this.packetType = null;
        this.id = id;
    }
    
    public ByteBuf getInputBuffer() {
        return this.inputBuffer;
    }
    
    @Override
    public String toString() {
        return "PacketWrapper{packetValues=" + this.packetValues + ", readableObjects=" + this.readableObjects + ", id=" + this.id + ", packetType=" + this.packetType + '}';
    }
    
    @Override
    public PacketWrapper apply(final Direction direction, final State state, final int n, final List list) throws Exception {
        return this.apply(direction, state, n, list);
    }
    
    @Override
    public PacketWrapper apply(final Direction direction, final State state, final int n, final List list, final boolean b) throws Exception {
        return this.apply(direction, state, n, list, b);
    }
    
    @Override
    public PacketWrapper create(final int n, final PacketHandler packetHandler) throws Exception {
        return this.create(n, packetHandler);
    }
    
    @Override
    public PacketWrapper create(final int n) {
        return this.create(n);
    }
    
    static {
        PROTOCOL_ARRAY = new Protocol[0];
    }
}
