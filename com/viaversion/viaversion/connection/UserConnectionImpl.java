package com.viaversion.viaversion.connection;

import java.util.concurrent.atomic.*;
import com.google.common.cache.*;
import java.util.concurrent.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.data.entity.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.type.*;
import io.netty.channel.*;
import java.util.function.*;
import com.viaversion.viaversion.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.*;
import com.viaversion.viaversion.util.*;

public class UserConnectionImpl implements UserConnection
{
    private static final AtomicLong IDS;
    private final long id;
    private final Map storedObjects;
    private final Map entityTrackers;
    private final PacketTracker packetTracker;
    private final Set passthroughTokens;
    private final ProtocolInfo protocolInfo;
    private final Channel channel;
    private final boolean clientSide;
    private boolean active;
    private boolean pendingDisconnect;
    private boolean packetLimiterEnabled;
    
    public UserConnectionImpl(final Channel channel, final boolean clientSide) {
        this.id = UserConnectionImpl.IDS.incrementAndGet();
        this.storedObjects = new ConcurrentHashMap();
        this.entityTrackers = new HashMap();
        this.packetTracker = new PacketTracker(this);
        this.passthroughTokens = Collections.newSetFromMap((Map<Object, Boolean>)CacheBuilder.newBuilder().expireAfterWrite(10L, TimeUnit.SECONDS).build().asMap());
        this.protocolInfo = new ProtocolInfoImpl(this);
        this.active = true;
        this.packetLimiterEnabled = true;
        this.channel = channel;
        this.clientSide = clientSide;
    }
    
    public UserConnectionImpl(final Channel channel) {
        this(channel, false);
    }
    
    @Override
    public StorableObject get(final Class clazz) {
        return this.storedObjects.get(clazz);
    }
    
    @Override
    public boolean has(final Class clazz) {
        return this.storedObjects.containsKey(clazz);
    }
    
    @Override
    public void put(final StorableObject storableObject) {
        this.storedObjects.put(storableObject.getClass(), storableObject);
    }
    
    @Override
    public Collection getEntityTrackers() {
        return this.entityTrackers.values();
    }
    
    @Override
    public EntityTracker getEntityTracker(final Class clazz) {
        return this.entityTrackers.get(clazz);
    }
    
    @Override
    public void addEntityTracker(final Class clazz, final EntityTracker entityTracker) {
        this.entityTrackers.put(clazz, entityTracker);
    }
    
    @Override
    public void clearStoredObjects() {
        this.storedObjects.clear();
        this.entityTrackers.clear();
    }
    
    @Override
    public void sendRawPacket(final ByteBuf byteBuf) {
        this.sendRawPacket(byteBuf, true);
    }
    
    @Override
    public void scheduleSendRawPacket(final ByteBuf byteBuf) {
        this.sendRawPacket(byteBuf, false);
    }
    
    private void sendRawPacket(final ByteBuf byteBuf, final boolean b) {
        Runnable runnable;
        if (this.clientSide) {
            runnable = this::lambda$sendRawPacket$0;
        }
        else {
            runnable = this::lambda$sendRawPacket$1;
        }
        if (b) {
            runnable.run();
        }
        else {
            this.channel.eventLoop().submit(runnable);
        }
    }
    
    @Override
    public ChannelFuture sendRawPacketFuture(final ByteBuf byteBuf) {
        if (this.clientSide) {
            this.getChannel().pipeline().context(Via.getManager().getInjector().getDecoderName()).fireChannelRead(byteBuf);
            return this.getChannel().newSucceededFuture();
        }
        return this.channel.pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush(byteBuf);
    }
    
    @Override
    public PacketTracker getPacketTracker() {
        return this.packetTracker;
    }
    
    @Override
    public void disconnect(final String s) {
        if (!this.channel.isOpen() || this.pendingDisconnect) {
            return;
        }
        this.pendingDisconnect = true;
        Via.getPlatform().runSync(this::lambda$disconnect$2);
    }
    
    @Override
    public void sendRawPacketToServer(final ByteBuf byteBuf) {
        if (this.clientSide) {
            this.sendRawPacketToServerClientSide(byteBuf, true);
        }
        else {
            this.sendRawPacketToServerServerSide(byteBuf, true);
        }
    }
    
    @Override
    public void scheduleSendRawPacketToServer(final ByteBuf byteBuf) {
        if (this.clientSide) {
            this.sendRawPacketToServerClientSide(byteBuf, false);
        }
        else {
            this.sendRawPacketToServerServerSide(byteBuf, false);
        }
    }
    
    private void sendRawPacketToServerServerSide(final ByteBuf byteBuf, final boolean b) {
        final ByteBuf buffer = byteBuf.alloc().buffer();
        final ChannelHandlerContext previousContext = PipelineUtil.getPreviousContext(Via.getManager().getInjector().getDecoderName(), this.channel.pipeline());
        if (this.shouldTransformPacket()) {
            Type.VAR_INT.writePrimitive(buffer, 1000);
            Type.UUID.write(buffer, this.generatePassthroughToken());
        }
        buffer.writeBytes(byteBuf);
        final Runnable runnable = this::lambda$sendRawPacketToServerServerSide$3;
        if (b) {
            runnable.run();
        }
        else {
            this.channel.eventLoop().submit(runnable);
        }
        byteBuf.release();
    }
    
    private void sendRawPacketToServerClientSide(final ByteBuf byteBuf, final boolean b) {
        final Runnable runnable = this::lambda$sendRawPacketToServerClientSide$4;
        if (b) {
            runnable.run();
        }
        else {
            this.getChannel().eventLoop().submit(runnable);
        }
    }
    
    @Override
    public boolean checkServerboundPacket() {
        return !this.pendingDisconnect && (!this.packetLimiterEnabled || !this.packetTracker.incrementReceived() || !this.packetTracker.exceedsMaxPPS());
    }
    
    @Override
    public boolean checkClientboundPacket() {
        this.packetTracker.incrementSent();
        return true;
    }
    
    @Override
    public boolean shouldTransformPacket() {
        return this.active;
    }
    
    @Override
    public void transformClientbound(final ByteBuf byteBuf, final Function function) throws Exception {
        this.transform(byteBuf, Direction.CLIENTBOUND, function);
    }
    
    @Override
    public void transformServerbound(final ByteBuf byteBuf, final Function function) throws Exception {
        this.transform(byteBuf, Direction.SERVERBOUND, function);
    }
    
    private void transform(final ByteBuf byteBuf, final Direction direction, final Function function) throws Exception {
        if (!byteBuf.isReadable()) {
            return;
        }
        final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        if (primitive != 1000) {
            final PacketWrapperImpl packetWrapperImpl = new PacketWrapperImpl(primitive, byteBuf, this);
            this.protocolInfo.getPipeline().transform(direction, this.protocolInfo.getState(), packetWrapperImpl);
            final ByteBuf buffer = byteBuf.alloc().buffer();
            packetWrapperImpl.writeToBuffer(buffer);
            byteBuf.clear().writeBytes(buffer);
            buffer.release();
            return;
        }
        if (!this.passthroughTokens.remove(Type.UUID.read(byteBuf))) {
            throw new IllegalArgumentException("Invalid token");
        }
    }
    
    @Override
    public long getId() {
        return this.id;
    }
    
    @Override
    public Channel getChannel() {
        return this.channel;
    }
    
    @Override
    public ProtocolInfo getProtocolInfo() {
        return this.protocolInfo;
    }
    
    @Override
    public Map getStoredObjects() {
        return this.storedObjects;
    }
    
    @Override
    public boolean isActive() {
        return this.active;
    }
    
    @Override
    public void setActive(final boolean active) {
        this.active = active;
    }
    
    @Override
    public boolean isPendingDisconnect() {
        return this.pendingDisconnect;
    }
    
    @Override
    public void setPendingDisconnect(final boolean pendingDisconnect) {
        this.pendingDisconnect = pendingDisconnect;
    }
    
    @Override
    public boolean isClientSide() {
        return this.clientSide;
    }
    
    @Override
    public boolean shouldApplyBlockProtocol() {
        return !this.clientSide;
    }
    
    @Override
    public boolean isPacketLimiterEnabled() {
        return this.packetLimiterEnabled;
    }
    
    @Override
    public void setPacketLimiterEnabled(final boolean packetLimiterEnabled) {
        this.packetLimiterEnabled = packetLimiterEnabled;
    }
    
    @Override
    public UUID generatePassthroughToken() {
        final UUID randomUUID = UUID.randomUUID();
        this.passthroughTokens.add(randomUUID);
        return randomUUID;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.id == ((UserConnectionImpl)o).id);
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(this.id);
    }
    
    private void lambda$sendRawPacketToServerClientSide$4(final ByteBuf byteBuf) {
        this.getChannel().pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush(byteBuf);
    }
    
    private void lambda$sendRawPacketToServerServerSide$3(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) {
        if (channelHandlerContext != null) {
            channelHandlerContext.fireChannelRead(byteBuf);
        }
        else {
            this.channel.pipeline().fireChannelRead(byteBuf);
        }
    }
    
    private void lambda$disconnect$2(final String s) {
        if (!Via.getPlatform().disconnect(this, ChatColorUtil.translateAlternateColorCodes(s))) {
            this.channel.close();
        }
    }
    
    private void lambda$sendRawPacket$1(final ByteBuf byteBuf) {
        this.channel.pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush(byteBuf);
    }
    
    private void lambda$sendRawPacket$0(final ByteBuf byteBuf) {
        this.getChannel().pipeline().context(Via.getManager().getInjector().getDecoderName()).fireChannelRead(byteBuf);
    }
    
    static {
        IDS = new AtomicLong();
    }
}
