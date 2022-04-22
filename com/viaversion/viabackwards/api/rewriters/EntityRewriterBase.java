package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viabackwards.api.entities.storage.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.google.common.base.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.libs.gson.*;

public abstract class EntityRewriterBase extends EntityRewriter
{
    private final Int2ObjectMap entityDataMappings;
    private final MetaType displayNameMetaType;
    private final MetaType displayVisibilityMetaType;
    private final int displayNameIndex;
    private final int displayVisibilityIndex;
    
    EntityRewriterBase(final BackwardsProtocol backwardsProtocol, final MetaType displayNameMetaType, final int displayNameIndex, final MetaType displayVisibilityMetaType, final int displayVisibilityIndex) {
        super(backwardsProtocol, false);
        this.entityDataMappings = new Int2ObjectOpenHashMap();
        this.displayNameMetaType = displayNameMetaType;
        this.displayNameIndex = displayNameIndex;
        this.displayVisibilityMetaType = displayVisibilityMetaType;
        this.displayVisibilityIndex = displayVisibilityIndex;
    }
    
    @Override
    public void handleMetadata(final int n, final List list, final UserConnection userConnection) {
        super.handleMetadata(n, list, userConnection);
        final EntityType entityType = this.tracker(userConnection).entityType(n);
        if (entityType == null) {
            return;
        }
        final EntityData entityDataForType = this.entityDataForType(entityType);
        final Metadata meta = this.getMeta(this.displayNameIndex, list);
        if (meta != null && entityDataForType != null && entityDataForType.mobName() != null && (meta.getValue() == null || meta.getValue().toString().isEmpty()) && meta.metaType().typeId() == this.displayNameMetaType.typeId()) {
            meta.setValue(entityDataForType.mobName());
            if (ViaBackwards.getConfig().alwaysShowOriginalMobName()) {
                this.removeMeta(this.displayVisibilityIndex, list);
                list.add(new Metadata(this.displayVisibilityIndex, this.displayVisibilityMetaType, true));
            }
        }
        if (entityDataForType != null && entityDataForType.hasBaseMeta()) {
            entityDataForType.defaultMeta().createMeta(new WrappedMetadata(list));
        }
    }
    
    protected Metadata getMeta(final int n, final List list) {
        for (final Metadata metadata : list) {
            if (metadata.id() == n) {
                return metadata;
            }
        }
        return null;
    }
    
    protected void removeMeta(final int n, final List list) {
        list.removeIf(EntityRewriterBase::lambda$removeMeta$0);
    }
    
    protected boolean hasData(final EntityType entityType) {
        return this.entityDataMappings.containsKey(entityType.getId());
    }
    
    protected EntityData entityDataForType(final EntityType entityType) {
        return (EntityData)this.entityDataMappings.get(entityType.getId());
    }
    
    protected StoredEntityData storedEntityData(final MetaHandlerEvent metaHandlerEvent) {
        return this.tracker(metaHandlerEvent.user()).entityData(metaHandlerEvent.entityId());
    }
    
    protected EntityData mapEntityTypeWithData(final EntityType entityType, final EntityType entityType2) {
        Preconditions.checkArgument(entityType.getClass() == entityType2.getClass());
        final int entityId = this.newEntityId(entityType2.getId());
        final EntityData entityData = new EntityData((BackwardsProtocol)this.protocol, entityType, entityId);
        this.mapEntityType(entityType.getId(), entityId);
        this.entityDataMappings.put(entityType.getId(), entityData);
        return entityData;
    }
    
    @Override
    public void mapTypes(final EntityType[] array, final Class clazz) {
        if (this.typeMappings == null) {
            (this.typeMappings = new Int2IntOpenHashMap(array.length, 0.99f)).defaultReturnValue(-1);
        }
        while (0 < array.length) {
            final EntityType entityType = array[0];
            this.typeMappings.put(entityType.getId(), ((EntityType)Enum.valueOf((Class<Enum>)clazz, entityType.name())).getId());
            int n = 0;
            ++n;
        }
    }
    
    public void registerMetaTypeHandler(final MetaType metaType, final MetaType metaType2, final MetaType metaType3, final MetaType metaType4) {
        this.filter().handler(this::lambda$registerMetaTypeHandler$1);
    }
    
    protected PacketHandler getTrackerHandler(final Type type, final int n) {
        return this::lambda$getTrackerHandler$2;
    }
    
    protected PacketHandler getTrackerHandler() {
        return this.getTrackerHandler(Type.VAR_INT, 1);
    }
    
    protected PacketHandler getTrackerHandler(final EntityType entityType, final Type type) {
        return this::lambda$getTrackerHandler$3;
    }
    
    protected PacketHandler getDimensionHandler(final int n) {
        return EntityRewriterBase::lambda$getDimensionHandler$4;
    }
    
    private static void lambda$getDimensionHandler$4(final int n, final PacketWrapper packetWrapper) throws Exception {
        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, n));
    }
    
    private void lambda$getTrackerHandler$3(final Type type, final EntityType entityType, final PacketWrapper packetWrapper) throws Exception {
        this.tracker(packetWrapper.user()).addEntity((int)packetWrapper.get(type, 0), entityType);
    }
    
    private void lambda$getTrackerHandler$2(final Type type, final int n, final PacketWrapper packetWrapper) throws Exception {
        this.tracker(packetWrapper.user()).addEntity((int)packetWrapper.get(Type.VAR_INT, 0), this.typeFromId(((Number)packetWrapper.get(type, n)).intValue()));
    }
    
    private void lambda$registerMetaTypeHandler$1(final MetaType metaType, final MetaType metaType2, final MetaType metaType3, final MetaType metaType4, final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final MetaType metaType5 = metadata.metaType();
        if (metaType != null && metaType5 == metaType) {
            ((BackwardsProtocol)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.value());
        }
        else if (metaType2 != null && metaType5 == metaType2) {
            metadata.setValue(((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId((int)metadata.value()));
        }
        else if (metaType3 != null && metaType5 == metaType3) {
            this.rewriteParticle((Particle)metadata.value());
        }
        else if (metaType4 != null && metaType5 == metaType4) {
            final JsonElement jsonElement = (JsonElement)metadata.value();
            if (jsonElement != null) {
                ((BackwardsProtocol)this.protocol).getTranslatableRewriter().processText(jsonElement);
            }
        }
    }
    
    private static boolean lambda$removeMeta$0(final int n, final Metadata metadata) {
        return metadata.id() == n;
    }
}
