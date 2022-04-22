package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.types.*;

public class MetadataRewriter1_16_2To1_16_1 extends EntityRewriter
{
    public MetadataRewriter1_16_2To1_16_1(final Protocol1_16_2To1_16_1 protocol1_16_2To1_16_1) {
        super(protocol1_16_2To1_16_1);
        this.mapTypes(Entity1_16Types.values(), Entity1_16_2Types.class);
    }
    
    public void handleMetadata(final int n, final EntityType entityType, final Metadata metadata, final List list, final UserConnection userConnection) throws Exception {
        if (metadata.metaType() == Types1_16.META_TYPES.itemType) {
            ((Protocol1_16_2To1_16_1)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        }
        else if (metadata.metaType() == Types1_16.META_TYPES.blockStateType) {
            metadata.setValue(((Protocol1_16_2To1_16_1)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
        }
        else if (metadata.metaType() == Types1_16.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
        if (entityType == null) {
            return;
        }
        if (entityType.isOrHasParent(Entity1_16_2Types.MINECART_ABSTRACT) && metadata.id() == 10) {
            metadata.setValue(((Protocol1_16_2To1_16_1)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
        }
        if (entityType.isOrHasParent(Entity1_16_2Types.ABSTRACT_PIGLIN)) {
            if (metadata.id() == 15) {
                metadata.setId(16);
            }
            else if (metadata.id() == 16) {
                metadata.setId(15);
            }
        }
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_16_2Types.getTypeFromId(n);
    }
}
