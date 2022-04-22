package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.types.*;

public class MetadataRewriter1_16To1_15_2 extends EntityRewriter
{
    public MetadataRewriter1_16To1_15_2(final Protocol1_16To1_15_2 protocol1_16To1_15_2) {
        super(protocol1_16To1_15_2);
        this.mapEntityType(Entity1_15Types.ZOMBIE_PIGMAN, Entity1_16Types.ZOMBIFIED_PIGLIN);
        this.mapTypes(Entity1_15Types.values(), Entity1_16Types.class);
    }
    
    public void handleMetadata(final int n, final EntityType entityType, final Metadata metadata, final List list, final UserConnection userConnection) throws Exception {
        metadata.setMetaType(Types1_16.META_TYPES.byId(metadata.metaType().typeId()));
        if (metadata.metaType() == Types1_16.META_TYPES.itemType) {
            ((Protocol1_16To1_15_2)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        }
        else if (metadata.metaType() == Types1_16.META_TYPES.blockStateType) {
            metadata.setValue(((Protocol1_16To1_15_2)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
        }
        else if (metadata.metaType() == Types1_16.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
        if (entityType == null) {
            return;
        }
        if (entityType.isOrHasParent(Entity1_16Types.MINECART_ABSTRACT) && metadata.id() == 10) {
            metadata.setValue(((Protocol1_16To1_15_2)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
        }
        if (entityType.isOrHasParent(Entity1_16Types.ABSTRACT_ARROW)) {
            if (metadata.id() == 8) {
                list.remove(metadata);
            }
            else if (metadata.id() > 8) {
                metadata.setId(metadata.id() - 1);
            }
        }
        if (entityType == Entity1_16Types.WOLF && metadata.id() == 16) {
            list.add(new Metadata(20, Types1_16.META_TYPES.varIntType, (((byte)metadata.value() & 0x2) != 0x0) ? Integer.MAX_VALUE : 0));
        }
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_16Types.getTypeFromId(n);
    }
}
