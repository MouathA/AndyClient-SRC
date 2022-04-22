package com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.chunks;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.*;
import java.util.*;

public class BlockEntity
{
    private static final Map types;
    
    public static void handle(final List list, final UserConnection userConnection) {
        for (final CompoundTag compoundTag : list) {
            if (!compoundTag.contains("id")) {
                throw new Exception("NBT tag not handled because the id key is missing");
            }
            final String s = (String)compoundTag.get("id").getValue();
            if (!BlockEntity.types.containsKey(s)) {
                throw new Exception("Not handled id: " + s);
            }
            final int intValue = BlockEntity.types.get(s);
            if (intValue == -1) {
                continue;
            }
            updateBlockEntity(new Position(((NumberTag)compoundTag.get("x")).asInt(), (short)((NumberTag)compoundTag.get("y")).asInt(), ((NumberTag)compoundTag.get("z")).asInt()), (short)intValue, compoundTag, userConnection);
        }
    }
    
    private static void updateBlockEntity(final Position position, final short n, final CompoundTag compoundTag, final UserConnection userConnection) throws Exception {
        final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, null, userConnection);
        create.write(Type.POSITION, position);
        create.write(Type.UNSIGNED_BYTE, n);
        create.write(Type.NBT, compoundTag);
        create.scheduleSend(Protocol1_9_1_2To1_9_3_4.class, false);
    }
    
    static {
        (types = new HashMap()).put("MobSpawner", 1);
        BlockEntity.types.put("Control", 2);
        BlockEntity.types.put("Beacon", 3);
        BlockEntity.types.put("Skull", 4);
        BlockEntity.types.put("FlowerPot", 5);
        BlockEntity.types.put("Banner", 6);
        BlockEntity.types.put("UNKNOWN", 7);
        BlockEntity.types.put("EndGateway", 8);
        BlockEntity.types.put("Sign", 9);
    }
}
