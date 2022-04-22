package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.minecraft.entities.*;

public class MetadataRewriter1_13_1To1_13 extends EntityRewriter
{
    public MetadataRewriter1_13_1To1_13(final Protocol1_13_1To1_13 protocol1_13_1To1_13) {
        super(protocol1_13_1To1_13);
    }
    
    @Override
    protected void handleMetadata(final int n, final EntityType entityType, final Metadata metadata, final List list, final UserConnection userConnection) {
        if (metadata.metaType() == Types1_13.META_TYPES.itemType) {
            ((Protocol1_13_1To1_13)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        }
        else if (metadata.metaType() == Types1_13.META_TYPES.blockStateType) {
            metadata.setValue(((Protocol1_13_1To1_13)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
        }
        else if (metadata.metaType() == Types1_13.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
        if (entityType == null) {
            return;
        }
        if (entityType.isOrHasParent(Entity1_13Types.EntityType.MINECART_ABSTRACT) && metadata.id() == 9) {
            metadata.setValue(((Protocol1_13_1To1_13)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
        }
        else if (entityType.isOrHasParent(Entity1_13Types.EntityType.ABSTRACT_ARROW) && metadata.id() >= 7) {
            metadata.setId(metadata.id() + 1);
        }
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
