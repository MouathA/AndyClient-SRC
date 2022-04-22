package com.viaversion.viaversion.api.protocol;

import com.google.common.base.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.*;
import java.util.logging.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.exception.*;

public abstract class AbstractProtocol implements Protocol
{
    private final Map serverbound;
    private final Map clientbound;
    private final Map storedObjects;
    protected final Class oldClientboundPacketEnum;
    protected final Class newClientboundPacketEnum;
    protected final Class oldServerboundPacketEnum;
    protected final Class newServerboundPacketEnum;
    private boolean initialized;
    
    protected AbstractProtocol() {
        this(null, null, null, null);
    }
    
    protected AbstractProtocol(final Class oldClientboundPacketEnum, final Class newClientboundPacketEnum, final Class oldServerboundPacketEnum, final Class newServerboundPacketEnum) {
        this.serverbound = new HashMap();
        this.clientbound = new HashMap();
        this.storedObjects = new HashMap();
        this.oldClientboundPacketEnum = oldClientboundPacketEnum;
        this.newClientboundPacketEnum = newClientboundPacketEnum;
        this.oldServerboundPacketEnum = oldServerboundPacketEnum;
        this.newServerboundPacketEnum = newServerboundPacketEnum;
    }
    
    @Override
    public final void initialize() {
        Preconditions.checkArgument(!this.initialized);
        this.initialized = true;
        this.registerPackets();
        if (this.oldClientboundPacketEnum != null && this.newClientboundPacketEnum != null && this.oldClientboundPacketEnum != this.newClientboundPacketEnum) {
            this.registerClientboundChannelIdChanges();
        }
        if (this.oldServerboundPacketEnum != null && this.newServerboundPacketEnum != null && this.oldServerboundPacketEnum != this.newServerboundPacketEnum) {
            this.registerServerboundChannelIdChanges();
        }
    }
    
    protected void registerClientboundChannelIdChanges() {
        final ClientboundPacketType[] array = this.newClientboundPacketEnum.getEnumConstants();
        final HashMap hashMap = new HashMap<Object, Object>(array.length);
        final ClientboundPacketType[] array2 = array;
        int n = 0;
        while (0 < array2.length) {
            final ClientboundPacketType clientboundPacketType = array2[0];
            hashMap.put(clientboundPacketType.getName(), clientboundPacketType);
            ++n;
        }
        final ClientboundPacketType[] array3 = this.oldClientboundPacketEnum.getEnumConstants();
        while (0 < array3.length) {
            final ClientboundPacketType clientboundPacketType2 = array3[0];
            final ClientboundPacketType clientboundPacketType3 = hashMap.get(clientboundPacketType2.getName());
            if (clientboundPacketType3 == null) {
                Preconditions.checkArgument(this.hasRegisteredClientbound(clientboundPacketType2), (Object)("Packet " + clientboundPacketType2 + " in " + this.getClass().getSimpleName() + " has no mapping - it needs to be manually cancelled or remapped!"));
            }
            else if (!this.hasRegisteredClientbound(clientboundPacketType2)) {
                this.registerClientbound(clientboundPacketType2, clientboundPacketType3);
            }
            ++n;
        }
    }
    
    protected void registerServerboundChannelIdChanges() {
        final ServerboundPacketType[] array = this.oldServerboundPacketEnum.getEnumConstants();
        final HashMap hashMap = new HashMap<Object, Object>(array.length);
        final ServerboundPacketType[] array2 = array;
        int n = 0;
        while (0 < array2.length) {
            final ServerboundPacketType serverboundPacketType = array2[0];
            hashMap.put(serverboundPacketType.getName(), serverboundPacketType);
            ++n;
        }
        final ServerboundPacketType[] array3 = this.newServerboundPacketEnum.getEnumConstants();
        while (0 < array3.length) {
            final ServerboundPacketType serverboundPacketType2 = array3[0];
            final ServerboundPacketType serverboundPacketType3 = hashMap.get(serverboundPacketType2.getName());
            if (serverboundPacketType3 == null) {
                Preconditions.checkArgument(this.hasRegisteredServerbound(serverboundPacketType2), (Object)("Packet " + serverboundPacketType2 + " in " + this.getClass().getSimpleName() + " has no mapping - it needs to be manually cancelled or remapped!"));
            }
            else if (!this.hasRegisteredServerbound(serverboundPacketType2)) {
                this.registerServerbound(serverboundPacketType2, serverboundPacketType3);
            }
            ++n;
        }
    }
    
    protected void registerPackets() {
    }
    
    @Override
    public final void loadMappingData() {
        this.getMappingData().load();
        this.onMappingDataLoaded();
    }
    
    protected void onMappingDataLoaded() {
    }
    
    protected void addEntityTracker(final UserConnection userConnection, final EntityTracker entityTracker) {
        userConnection.addEntityTracker(this.getClass(), entityTracker);
    }
    
    @Override
    public void registerServerbound(final State state, final int n, final int n2, final PacketRemapper packetRemapper, final boolean b) {
        final ProtocolPacket protocolPacket = new ProtocolPacket(state, n, n2, packetRemapper);
        final Packet packet = new Packet(state, n2);
        if (!b && this.serverbound.containsKey(packet)) {
            Via.getPlatform().getLogger().log(Level.WARNING, packet + " already registered! If this override is intentional, set override to true. Stacktrace: ", new Exception());
        }
        this.serverbound.put(packet, protocolPacket);
    }
    
    @Override
    public void cancelServerbound(final State state, final int n, final int n2) {
        this.registerServerbound(state, n, n2, new PacketRemapper() {
            final AbstractProtocol this$0;
            
            @Override
            public void registerMap() {
                this.handler(PacketWrapper::cancel);
            }
        });
    }
    
    @Override
    public void cancelServerbound(final State state, final int n) {
        this.cancelServerbound(state, -1, n);
    }
    
    @Override
    public void cancelClientbound(final State state, final int n, final int n2) {
        this.registerClientbound(state, n, n2, new PacketRemapper() {
            final AbstractProtocol this$0;
            
            @Override
            public void registerMap() {
                this.handler(PacketWrapper::cancel);
            }
        });
    }
    
    @Override
    public void cancelClientbound(final State state, final int n) {
        this.cancelClientbound(state, n, -1);
    }
    
    @Override
    public void registerClientbound(final State state, final int n, final int n2, final PacketRemapper packetRemapper, final boolean b) {
        final ProtocolPacket protocolPacket = new ProtocolPacket(state, n, n2, packetRemapper);
        final Packet packet = new Packet(state, n);
        if (!b && this.clientbound.containsKey(packet)) {
            Via.getPlatform().getLogger().log(Level.WARNING, packet + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
        }
        this.clientbound.put(packet, protocolPacket);
    }
    
    @Override
    public void registerClientbound(final ClientboundPacketType clientboundPacketType, final PacketRemapper packetRemapper) {
        this.checkPacketType(clientboundPacketType, this.oldClientboundPacketEnum == null || clientboundPacketType.getClass() == this.oldClientboundPacketEnum);
        final ClientboundPacketType clientboundPacketType2 = (this.oldClientboundPacketEnum == this.newClientboundPacketEnum) ? clientboundPacketType : Arrays.stream(this.newClientboundPacketEnum.getEnumConstants()).filter(AbstractProtocol::lambda$registerClientbound$0).findAny().orElse(null);
        Preconditions.checkNotNull(clientboundPacketType2, (Object)("Packet type " + clientboundPacketType + " in " + clientboundPacketType.getClass().getSimpleName() + " could not be automatically mapped!"));
        this.registerClientbound(clientboundPacketType, clientboundPacketType2, packetRemapper);
    }
    
    @Override
    public void registerClientbound(final ClientboundPacketType clientboundPacketType, final ClientboundPacketType clientboundPacketType2, final PacketRemapper packetRemapper, final boolean b) {
        this.register(this.clientbound, clientboundPacketType, clientboundPacketType2, this.oldClientboundPacketEnum, this.newClientboundPacketEnum, packetRemapper, b);
    }
    
    @Override
    public void cancelClientbound(final ClientboundPacketType clientboundPacketType) {
        this.registerClientbound(clientboundPacketType, null, new PacketRemapper() {
            final AbstractProtocol this$0;
            
            @Override
            public void registerMap() {
                this.handler(PacketWrapper::cancel);
            }
        });
    }
    
    @Override
    public void registerServerbound(final ServerboundPacketType serverboundPacketType, final PacketRemapper packetRemapper) {
        this.checkPacketType(serverboundPacketType, this.newServerboundPacketEnum == null || serverboundPacketType.getClass() == this.newServerboundPacketEnum);
        final ServerboundPacketType serverboundPacketType2 = (this.oldServerboundPacketEnum == this.newServerboundPacketEnum) ? serverboundPacketType : Arrays.stream(this.oldServerboundPacketEnum.getEnumConstants()).filter(AbstractProtocol::lambda$registerServerbound$1).findAny().orElse(null);
        Preconditions.checkNotNull(serverboundPacketType2, (Object)("Packet type " + serverboundPacketType + " in " + serverboundPacketType.getClass().getSimpleName() + " could not be automatically mapped!"));
        this.registerServerbound(serverboundPacketType, serverboundPacketType2, packetRemapper);
    }
    
    @Override
    public void registerServerbound(final ServerboundPacketType serverboundPacketType, final ServerboundPacketType serverboundPacketType2, final PacketRemapper packetRemapper, final boolean b) {
        this.register(this.serverbound, serverboundPacketType, serverboundPacketType2, this.newServerboundPacketEnum, this.oldServerboundPacketEnum, packetRemapper, b);
    }
    
    @Override
    public void cancelServerbound(final ServerboundPacketType serverboundPacketType) {
        this.registerServerbound(serverboundPacketType, null, new PacketRemapper() {
            final AbstractProtocol this$0;
            
            @Override
            public void registerMap() {
                this.handler(PacketWrapper::cancel);
            }
        });
    }
    
    private void register(final Map map, final PacketType packetType, final PacketType packetType2, final Class clazz, final Class clazz2, final PacketRemapper packetRemapper, final boolean b) {
        this.checkPacketType(packetType, clazz == null || packetType.getClass() == clazz);
        this.checkPacketType(packetType2, packetType2 == null || clazz2 == null || packetType2.getClass() == clazz2);
        Preconditions.checkArgument(packetType2 == null || packetType.state() == packetType2.state(), (Object)"Packet type state does not match mapped packet type state");
        final ProtocolPacket protocolPacket = new ProtocolPacket(packetType.state(), packetType, packetType2, packetRemapper);
        final Packet packet = new Packet(packetType.state(), packetType.getId());
        if (!b && map.containsKey(packet)) {
            Via.getPlatform().getLogger().log(Level.WARNING, packet + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
        }
        map.put(packet, protocolPacket);
    }
    
    @Override
    public boolean hasRegisteredClientbound(final ClientboundPacketType clientboundPacketType) {
        return this.hasRegisteredClientbound(clientboundPacketType.state(), clientboundPacketType.getId());
    }
    
    @Override
    public boolean hasRegisteredServerbound(final ServerboundPacketType serverboundPacketType) {
        return this.hasRegisteredServerbound(serverboundPacketType.state(), serverboundPacketType.getId());
    }
    
    @Override
    public boolean hasRegisteredClientbound(final State state, final int n) {
        return this.clientbound.containsKey(new Packet(state, n));
    }
    
    @Override
    public boolean hasRegisteredServerbound(final State state, final int n) {
        return this.serverbound.containsKey(new Packet(state, n));
    }
    
    @Override
    public void transform(final Direction direction, final State state, final PacketWrapper packetWrapper) throws Exception {
        final ProtocolPacket protocolPacket = ((direction == Direction.CLIENTBOUND) ? this.clientbound : this.serverbound).get(new Packet(state, packetWrapper.getId()));
        if (protocolPacket == null) {
            return;
        }
        final int id = packetWrapper.getId();
        if (protocolPacket.isMappedOverTypes()) {
            packetWrapper.setPacketType(protocolPacket.getMappedPacketType());
        }
        else {
            final int id2 = (direction == Direction.CLIENTBOUND) ? protocolPacket.getNewId() : protocolPacket.getOldId();
            if (id != id2) {
                packetWrapper.setId(id2);
            }
        }
        final PacketRemapper remapper = protocolPacket.getRemapper();
        if (remapper != null) {
            remapper.remap(packetWrapper);
            if (packetWrapper.isCancelled()) {
                throw CancelException.generate();
            }
        }
    }
    
    private void throwRemapError(final Direction direction, final State state, final int n, final int n2, final InformativeException ex) throws InformativeException {
        if (state == State.HANDSHAKE) {
            throw ex;
        }
        final Class clazz = (state == State.PLAY) ? ((direction == Direction.CLIENTBOUND) ? this.oldClientboundPacketEnum : this.newServerboundPacketEnum) : null;
        if (clazz != null) {
            final PacketType[] array = clazz.getEnumConstants();
            Via.getPlatform().getLogger().warning("ERROR IN " + this.getClass().getSimpleName() + " IN REMAP OF " + ((n < array.length && n >= 0) ? array[n] : null) + " (" + this.toNiceHex(n) + ")");
        }
        else {
            Via.getPlatform().getLogger().warning("ERROR IN " + this.getClass().getSimpleName() + " IN REMAP OF " + this.toNiceHex(n) + "->" + this.toNiceHex(n2));
        }
        throw ex;
    }
    
    private String toNiceHex(final int n) {
        final String upperCase = Integer.toHexString(n).toUpperCase();
        return ((upperCase.length() == 1) ? "0x0" : "0x") + upperCase;
    }
    
    private void checkPacketType(final PacketType packetType, final boolean b) {
        if (!b) {
            throw new IllegalArgumentException("Packet type " + packetType + " in " + packetType.getClass().getSimpleName() + " is taken from the wrong enum");
        }
    }
    
    @Override
    public Object get(final Class clazz) {
        return this.storedObjects.get(clazz);
    }
    
    @Override
    public void put(final Object o) {
        this.storedObjects.put(o.getClass(), o);
    }
    
    @Override
    public boolean hasMappingDataToLoad() {
        return this.getMappingData() != null;
    }
    
    @Override
    public String toString() {
        return "Protocol:" + this.getClass().getSimpleName();
    }
    
    private static boolean lambda$registerServerbound$1(final ServerboundPacketType serverboundPacketType, final ServerboundPacketType serverboundPacketType2) {
        return serverboundPacketType2.getName().equals(serverboundPacketType.getName());
    }
    
    private static boolean lambda$registerClientbound$0(final ClientboundPacketType clientboundPacketType, final ClientboundPacketType clientboundPacketType2) {
        return clientboundPacketType2.getName().equals(clientboundPacketType.getName());
    }
    
    public static final class ProtocolPacket
    {
        private final State state;
        private final int oldId;
        private final int newId;
        private final PacketType unmappedPacketType;
        private final PacketType mappedPacketType;
        private final PacketRemapper remapper;
        
        @Deprecated
        public ProtocolPacket(final State state, final int oldId, final int newId, final PacketRemapper remapper) {
            this.state = state;
            this.oldId = oldId;
            this.newId = newId;
            this.remapper = remapper;
            this.unmappedPacketType = null;
            this.mappedPacketType = null;
        }
        
        public ProtocolPacket(final State state, final PacketType unmappedPacketType, final PacketType mappedPacketType, final PacketRemapper remapper) {
            this.state = state;
            this.unmappedPacketType = unmappedPacketType;
            if (unmappedPacketType.direction() == Direction.CLIENTBOUND) {
                this.oldId = unmappedPacketType.getId();
                this.newId = ((mappedPacketType != null) ? mappedPacketType.getId() : -1);
            }
            else {
                this.oldId = ((mappedPacketType != null) ? mappedPacketType.getId() : -1);
                this.newId = unmappedPacketType.getId();
            }
            this.mappedPacketType = mappedPacketType;
            this.remapper = remapper;
        }
        
        public State getState() {
            return this.state;
        }
        
        @Deprecated
        public int getOldId() {
            return this.oldId;
        }
        
        @Deprecated
        public int getNewId() {
            return this.newId;
        }
        
        public PacketType getUnmappedPacketType() {
            return this.unmappedPacketType;
        }
        
        public PacketType getMappedPacketType() {
            return this.mappedPacketType;
        }
        
        public boolean isMappedOverTypes() {
            return this.unmappedPacketType != null;
        }
        
        public PacketRemapper getRemapper() {
            return this.remapper;
        }
    }
    
    public static final class Packet
    {
        private final State state;
        private final int packetId;
        
        public Packet(final State state, final int packetId) {
            this.state = state;
            this.packetId = packetId;
        }
        
        public State getState() {
            return this.state;
        }
        
        public int getPacketId() {
            return this.packetId;
        }
        
        @Override
        public String toString() {
            return "Packet{state=" + this.state + ", packetId=" + this.packetId + '}';
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final Packet packet = (Packet)o;
            return this.packetId == packet.packetId && this.state == packet.state;
        }
        
        @Override
        public int hashCode() {
            return 31 * ((this.state != null) ? this.state.hashCode() : 0) + this.packetId;
        }
    }
}
