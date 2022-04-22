package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;

public class MetadataRewriter1_13To1_12_2 extends EntityRewriter
{
    public MetadataRewriter1_13To1_12_2(final Protocol1_13To1_12_2 protocol1_13To1_12_2) {
        super(protocol1_13To1_12_2);
    }
    
    @Override
    protected void handleMetadata(final int n, final EntityType entityType, final Metadata metadata, final List list, final UserConnection userConnection) throws Exception {
        if (metadata.metaType().typeId() > 4) {
            metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId() + 1));
        }
        else {
            metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
        }
        if (metadata.id() == 2) {
            if (metadata.getValue() != null && !((String)metadata.getValue()).isEmpty()) {
                metadata.setTypeAndValue(Types1_13.META_TYPES.optionalComponentType, ChatRewriter.legacyTextToJson((String)metadata.getValue()));
            }
            else {
                metadata.setTypeAndValue(Types1_13.META_TYPES.optionalComponentType, null);
            }
        }
        if (entityType == Entity1_13Types.EntityType.ENDERMAN && metadata.id() == 12) {
            final int intValue = (int)metadata.getValue();
            metadata.setValue((intValue & 0xFFF) << 4 | (intValue >> 12 & 0xF & 0xF));
        }
        if (metadata.metaType() == Types1_13.META_TYPES.itemType) {
            metadata.setMetaType(Types1_13.META_TYPES.itemType);
            ((Protocol1_13To1_12_2)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        }
        else if (metadata.metaType() == Types1_13.META_TYPES.blockStateType) {
            metadata.setValue(WorldPackets.toNewId((int)metadata.getValue()));
        }
        if (entityType == null) {
            return;
        }
        if (entityType == Entity1_13Types.EntityType.WOLF && metadata.id() == 17) {
            metadata.setValue(15 - (int)metadata.getValue());
        }
        if (entityType.isOrHasParent(Entity1_13Types.EntityType.ZOMBIE) && metadata.id() > 14) {
            metadata.setId(metadata.id() + 1);
        }
        if (entityType.isOrHasParent(Entity1_13Types.EntityType.MINECART_ABSTRACT) && metadata.id() == 9) {
            final int intValue2 = (int)metadata.getValue();
            metadata.setValue(WorldPackets.toNewId((intValue2 & 0xFFF) << 4 | (intValue2 >> 12 & 0xF)));
        }
        if (entityType == Entity1_13Types.EntityType.AREA_EFFECT_CLOUD) {
            if (metadata.id() == 9) {
                final int intValue3 = (int)metadata.getValue();
                final Metadata metaByIndex = this.metaByIndex(10, list);
                final Metadata metaByIndex2 = this.metaByIndex(11, list);
                final Particle rewriteParticle = ParticleRewriter.rewriteParticle(intValue3, new Integer[] { (Integer)((metaByIndex != null) ? metaByIndex.getValue() : 0), (Integer)((metaByIndex2 != null) ? metaByIndex2.getValue() : 0) });
                if (rewriteParticle != null && rewriteParticle.getId() != -1) {
                    list.add(new Metadata(9, Types1_13.META_TYPES.particleType, rewriteParticle));
                }
            }
            if (metadata.id() >= 9) {
                list.remove(metadata);
            }
        }
        if (metadata.id() == 0) {
            metadata.setValue((byte)((byte)metadata.getValue() & 0xFFFFFFEF));
        }
    }
    
    @Override
    public int newEntityId(final int n) {
        return EntityTypeRewriter.getNewId(n);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_13Types.getTypeFromId(n, false);
    }
    
    @Override
    public EntityType objectTypeFromId(final int n) {
        return Entity1_13Types.getTypeFromId(n, true);
    }
}
