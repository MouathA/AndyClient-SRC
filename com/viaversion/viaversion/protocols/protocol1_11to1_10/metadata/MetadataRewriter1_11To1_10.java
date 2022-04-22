package com.viaversion.viaversion.protocols.protocol1_11to1_10.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import java.util.*;

public class MetadataRewriter1_11To1_10 extends EntityRewriter
{
    public MetadataRewriter1_11To1_10(final Protocol1_11To1_10 protocol1_11To1_10) {
        super(protocol1_11To1_10);
    }
    
    @Override
    protected void handleMetadata(final int n, final EntityType entityType, final Metadata metadata, final List list, final UserConnection userConnection) {
        if (metadata.getValue() instanceof DataItem) {
            EntityIdRewriter.toClientItem((Item)metadata.getValue());
        }
        if (entityType == null) {
            return;
        }
        if ((entityType.is(Entity1_11Types.EntityType.ELDER_GUARDIAN) || entityType.is(Entity1_11Types.EntityType.GUARDIAN)) && metadata.id() == 12) {
            metadata.setTypeAndValue(MetaType1_9.Boolean, ((byte)metadata.getValue() & 0x2) == 0x2);
        }
        if (entityType.isOrHasParent(Entity1_11Types.EntityType.ABSTRACT_SKELETON)) {
            final int id = metadata.id();
            if (id == 12) {
                list.remove(metadata);
            }
            if (id == 13) {
                metadata.setId(12);
            }
        }
        if (entityType.isOrHasParent(Entity1_11Types.EntityType.ZOMBIE)) {
            if (entityType.is(Entity1_11Types.EntityType.ZOMBIE, Entity1_11Types.EntityType.HUSK) && metadata.id() == 14) {
                list.remove(metadata);
            }
            else if (metadata.id() == 15) {
                metadata.setId(14);
            }
            else if (metadata.id() == 14) {
                metadata.setId(15);
            }
        }
        if (entityType.isOrHasParent(Entity1_11Types.EntityType.ABSTRACT_HORSE)) {
            final int id2 = metadata.id();
            if (id2 == 14) {
                list.remove(metadata);
            }
            if (id2 == 16) {
                metadata.setId(14);
            }
            if (id2 == 17) {
                metadata.setId(16);
            }
            if (!entityType.is(Entity1_11Types.EntityType.HORSE)) {
                if (metadata.id() == 15 || metadata.id() == 16) {
                    list.remove(metadata);
                }
            }
            if (entityType.is(Entity1_11Types.EntityType.DONKEY, Entity1_11Types.EntityType.MULE) && metadata.id() == 13) {
                if (((byte)metadata.getValue() & 0x8) == 0x8) {
                    list.add(new Metadata(15, MetaType1_9.Boolean, true));
                }
                else {
                    list.add(new Metadata(15, MetaType1_9.Boolean, false));
                }
            }
        }
        if (entityType.is(Entity1_11Types.EntityType.ARMOR_STAND) && Via.getConfig().isHologramPatch()) {
            final Metadata metaByIndex = this.metaByIndex(11, list);
            final Metadata metaByIndex2 = this.metaByIndex(2, list);
            final Metadata metaByIndex3 = this.metaByIndex(3, list);
            if (metadata.id() == 0 && metaByIndex != null && metaByIndex2 != null && metaByIndex3 != null && ((byte)metadata.getValue() & 0x20) == 0x20 && ((byte)metaByIndex.getValue() & 0x1) == 0x1 && !((String)metaByIndex2.getValue()).isEmpty() && (boolean)metaByIndex3.getValue() && ((EntityTracker1_11)this.tracker(userConnection)).addHologram(n)) {
                final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9_3.ENTITY_POSITION, null, userConnection);
                create.write(Type.VAR_INT, n);
                create.write(Type.SHORT, 0);
                create.write(Type.SHORT, (short)(128.0 * (-Via.getConfig().getHologramYOffset() * 32.0)));
                create.write(Type.SHORT, 0);
                create.write(Type.BOOLEAN, true);
                create.send(Protocol1_11To1_10.class);
            }
        }
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_11Types.getTypeFromId(n, false);
    }
    
    @Override
    public EntityType objectTypeFromId(final int n) {
        return Entity1_11Types.getTypeFromId(n, true);
    }
    
    public static Entity1_11Types.EntityType rewriteEntityType(final int n, final List list) {
        final Optional byId = Entity1_11Types.EntityType.findById(n);
        if (!byId.isPresent()) {
            Via.getManager().getPlatform().getLogger().severe("Error: could not find Entity type " + n + " with metadata: " + list);
            return null;
        }
        final Entity1_11Types.EntityType entityType = byId.get();
        if (entityType.is(Entity1_11Types.EntityType.GUARDIAN)) {
            final Optional byId2 = getById(list, 12);
            if (byId2.isPresent() && ((byte)byId2.get().getValue() & 0x4) == 0x4) {
                return Entity1_11Types.EntityType.ELDER_GUARDIAN;
            }
        }
        if (entityType.is(Entity1_11Types.EntityType.SKELETON)) {
            final Optional byId3 = getById(list, 12);
            if (byId3.isPresent()) {
                if ((int)byId3.get().getValue() == 1) {
                    return Entity1_11Types.EntityType.WITHER_SKELETON;
                }
                if ((int)byId3.get().getValue() == 2) {
                    return Entity1_11Types.EntityType.STRAY;
                }
            }
        }
        if (entityType.is(Entity1_11Types.EntityType.ZOMBIE)) {
            final Optional byId4 = getById(list, 13);
            if (byId4.isPresent()) {
                final int intValue = (int)byId4.get().getValue();
                if (intValue > 0 && intValue < 6) {
                    list.add(new Metadata(16, MetaType1_9.VarInt, intValue - 1));
                    return Entity1_11Types.EntityType.ZOMBIE_VILLAGER;
                }
                if (intValue == 6) {
                    return Entity1_11Types.EntityType.HUSK;
                }
            }
        }
        if (entityType.is(Entity1_11Types.EntityType.HORSE)) {
            final Optional byId5 = getById(list, 14);
            if (byId5.isPresent()) {
                if ((int)byId5.get().getValue() == 0) {
                    return Entity1_11Types.EntityType.HORSE;
                }
                if ((int)byId5.get().getValue() == 1) {
                    return Entity1_11Types.EntityType.DONKEY;
                }
                if ((int)byId5.get().getValue() == 2) {
                    return Entity1_11Types.EntityType.MULE;
                }
                if ((int)byId5.get().getValue() == 3) {
                    return Entity1_11Types.EntityType.ZOMBIE_HORSE;
                }
                if ((int)byId5.get().getValue() == 4) {
                    return Entity1_11Types.EntityType.SKELETON_HORSE;
                }
            }
        }
        return entityType;
    }
    
    public static Optional getById(final List list, final int n) {
        for (final Metadata metadata : list) {
            if (metadata.id() == n) {
                return Optional.of(metadata);
            }
        }
        return Optional.empty();
    }
}
