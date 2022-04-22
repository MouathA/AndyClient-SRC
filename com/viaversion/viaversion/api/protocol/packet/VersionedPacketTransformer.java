package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.connection.*;
import java.util.function.*;

public interface VersionedPacketTransformer
{
    boolean send(final PacketWrapper p0) throws Exception;
    
    boolean send(final UserConnection p0, final ClientboundPacketType p1, final Consumer p2) throws Exception;
    
    boolean send(final UserConnection p0, final ServerboundPacketType p1, final Consumer p2) throws Exception;
    
    boolean scheduleSend(final PacketWrapper p0) throws Exception;
    
    boolean scheduleSend(final UserConnection p0, final ClientboundPacketType p1, final Consumer p2) throws Exception;
    
    boolean scheduleSend(final UserConnection p0, final ServerboundPacketType p1, final Consumer p2) throws Exception;
    
    PacketWrapper transform(final PacketWrapper p0) throws Exception;
    
    PacketWrapper transform(final UserConnection p0, final ClientboundPacketType p1, final Consumer p2) throws Exception;
    
    PacketWrapper transform(final UserConnection p0, final ServerboundPacketType p1, final Consumer p2) throws Exception;
}
