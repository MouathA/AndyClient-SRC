package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.*;

public class MetadataRewriter1_14To1_13_2 extends EntityRewriter
{
    public MetadataRewriter1_14To1_13_2(final Protocol1_14To1_13_2 protocol1_14To1_13_2) {
        super(protocol1_14To1_13_2);
        this.mapTypes(Entity1_13Types.EntityType.values(), Entity1_14Types.class);
        this.mapEntityType(Entity1_13Types.EntityType.OCELOT, Entity1_14Types.CAT);
    }
    
    @Override
    protected void handleMetadata(final int n, final EntityType entityType, final Metadata metadata, final List list, final UserConnection userConnection) throws Exception {
        metadata.setMetaType(Types1_14.META_TYPES.byId(metadata.metaType().typeId()));
        final EntityTracker1_14 entityTracker1_14 = (EntityTracker1_14)this.tracker(userConnection);
        if (metadata.metaType() == Types1_14.META_TYPES.itemType) {
            ((Protocol1_14To1_13_2)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        }
        else if (metadata.metaType() == Types1_14.META_TYPES.blockStateType) {
            metadata.setValue(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
        }
        else if (metadata.metaType() == Types1_14.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
        if (entityType == null) {
            return;
        }
        if (metadata.id() > 5) {
            metadata.setId(metadata.id() + 1);
        }
        if (metadata.id() == 8 && entityType.isOrHasParent(Entity1_14Types.LIVINGENTITY) && Float.isNaN(((Number)metadata.getValue()).floatValue()) && Via.getConfig().is1_14HealthNaNFix()) {
            metadata.setValue(1.0f);
        }
        if (metadata.id() > 11 && entityType.isOrHasParent(Entity1_14Types.LIVINGENTITY)) {
            metadata.setId(metadata.id() + 1);
        }
        if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_INSENTIENT) && metadata.id() == 13) {
            entityTracker1_14.setInsentientData(n, (byte)((((Number)metadata.getValue()).byteValue() & 0xFFFFFFFB) | (entityTracker1_14.getInsentientData(n) & 0x4)));
            metadata.setValue(entityTracker1_14.getInsentientData(n));
        }
        if (entityType.isOrHasParent(Entity1_14Types.PLAYER)) {
            if (n != entityTracker1_14.clientEntityId()) {
                if (metadata.id() == 0) {
                    entityTracker1_14.setEntityFlags(n, ((Number)metadata.getValue()).byteValue());
                }
                else if (metadata.id() == 7) {
                    entityTracker1_14.setRiptide(n, (((Number)metadata.getValue()).byteValue() & 0x4) != 0x0);
                }
                if (metadata.id() == 0 || metadata.id() == 7) {
                    list.add(new Metadata(6, Types1_14.META_TYPES.poseType, recalculatePlayerPose(n, entityTracker1_14)));
                }
            }
        }
        else if (entityType.isOrHasParent(Entity1_14Types.ZOMBIE)) {
            if (metadata.id() == 16) {
                entityTracker1_14.setInsentientData(n, (byte)((entityTracker1_14.getInsentientData(n) & 0xFFFFFFFB) | (metadata.getValue() ? 4 : 0)));
                list.remove(metadata);
                list.add(new Metadata(13, Types1_14.META_TYPES.byteType, entityTracker1_14.getInsentientData(n)));
            }
            else if (metadata.id() > 16) {
                metadata.setId(metadata.id() - 1);
            }
        }
        if (entityType.isOrHasParent(Entity1_14Types.MINECART_ABSTRACT)) {
            if (metadata.id() == 10) {
                metadata.setValue(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
            }
        }
        else if (entityType.is(Entity1_14Types.HORSE)) {
            if (metadata.id() == 18) {
                list.remove(metadata);
                final int intValue = (int)metadata.getValue();
                Object o = null;
                if (intValue == 1) {
                    o = new DataItem(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewItemId(727), (byte)1, (short)0, null);
                }
                else if (intValue == 2) {
                    o = new DataItem(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewItemId(728), (byte)1, (short)0, null);
                }
                else if (intValue == 3) {
                    o = new DataItem(((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewItemId(729), (byte)1, (short)0, null);
                }
                final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_14.ENTITY_EQUIPMENT, null, userConnection);
                create.write(Type.VAR_INT, n);
                create.write(Type.VAR_INT, 4);
                create.write(Type.FLAT_VAR_INT_ITEM, o);
                create.scheduleSend(Protocol1_14To1_13_2.class);
            }
        }
        else if (entityType.is(Entity1_14Types.VILLAGER)) {
            if (metadata.id() == 15) {
                metadata.setTypeAndValue(Types1_14.META_TYPES.villagerDatatType, new VillagerData(2, getNewProfessionId((int)metadata.getValue()), 0));
            }
        }
        else if (entityType.is(Entity1_14Types.ZOMBIE_VILLAGER)) {
            if (metadata.id() == 18) {
                metadata.setTypeAndValue(Types1_14.META_TYPES.villagerDatatType, new VillagerData(2, getNewProfessionId((int)metadata.getValue()), 0));
            }
        }
        else if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_ARROW)) {
            if (metadata.id() >= 9) {
                metadata.setId(metadata.id() + 1);
            }
        }
        else if (entityType.is(Entity1_14Types.FIREWORK_ROCKET)) {
            if (metadata.id() == 8) {
                metadata.setMetaType(Types1_14.META_TYPES.optionalVarIntType);
                if (metadata.getValue().equals(0)) {
                    metadata.setValue(null);
                }
            }
        }
        else if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_SKELETON) && metadata.id() == 14) {
            entityTracker1_14.setInsentientData(n, (byte)((entityTracker1_14.getInsentientData(n) & 0xFFFFFFFB) | (metadata.getValue() ? 4 : 0)));
            list.remove(metadata);
            list.add(new Metadata(13, Types1_14.META_TYPES.byteType, entityTracker1_14.getInsentientData(n)));
        }
        if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_ILLAGER_BASE) && metadata.id() == 14) {
            entityTracker1_14.setInsentientData(n, (byte)((entityTracker1_14.getInsentientData(n) & 0xFFFFFFFB) | ((((Number)metadata.getValue()).byteValue() != 0) ? 4 : 0)));
            list.remove(metadata);
            list.add(new Metadata(13, Types1_14.META_TYPES.byteType, entityTracker1_14.getInsentientData(n)));
        }
        if ((entityType.is(Entity1_14Types.WITCH) || entityType.is(Entity1_14Types.RAVAGER) || entityType.isOrHasParent(Entity1_14Types.ABSTRACT_ILLAGER_BASE)) && metadata.id() >= 14) {
            metadata.setId(metadata.id() + 1);
        }
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_14Types.getTypeFromId(n);
    }
    
    private static boolean isSneaking(final byte b) {
        return (b & 0x2) != 0x0;
    }
    
    private static boolean isSwimming(final byte b) {
        return (b & 0x10) != 0x0;
    }
    
    private static int getNewProfessionId(final int n) {
        switch (n) {
            case 0: {
                return 5;
            }
            case 1: {
                return 9;
            }
            case 2: {
                return 4;
            }
            case 3: {
                return 1;
            }
            case 4: {
                return 2;
            }
            case 5: {
                return 11;
            }
            default: {
                return 0;
            }
        }
    }
    
    private static boolean isFallFlying(final int n) {
        return (n & 0x80) != 0x0;
    }
    
    public static int recalculatePlayerPose(final int n, final EntityTracker1_14 entityTracker1_14) {
        final byte entityFlags = entityTracker1_14.getEntityFlags(n);
        if (!isFallFlying(entityFlags)) {
            if (!entityTracker1_14.isSleeping(n)) {
                if (!isSwimming(entityFlags)) {
                    if (!entityTracker1_14.isRiptide(n)) {
                        if (isSneaking(entityFlags)) {}
                    }
                }
            }
        }
        return 5;
    }
}
