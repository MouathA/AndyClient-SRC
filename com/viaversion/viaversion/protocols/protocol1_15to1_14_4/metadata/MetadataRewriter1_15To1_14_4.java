package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.*;

public class MetadataRewriter1_15To1_14_4 extends EntityRewriter
{
    public MetadataRewriter1_15To1_14_4(final Protocol1_15To1_14_4 protocol1_15To1_14_4) {
        super(protocol1_15To1_14_4);
    }
    
    public void handleMetadata(final int n, final EntityType entityType, final Metadata metadata, final List list, final UserConnection userConnection) throws Exception {
        if (metadata.metaType() == Types1_14.META_TYPES.itemType) {
            ((Protocol1_15To1_14_4)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        }
        else if (metadata.metaType() == Types1_14.META_TYPES.blockStateType) {
            metadata.setValue(((Protocol1_15To1_14_4)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
        }
        else if (metadata.metaType() == Types1_14.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
        if (entityType == null) {
            return;
        }
        if (entityType.isOrHasParent(Entity1_15Types.MINECART_ABSTRACT) && metadata.id() == 10) {
            metadata.setValue(((Protocol1_15To1_14_4)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
        }
        if (metadata.id() > 11 && entityType.isOrHasParent(Entity1_15Types.LIVINGENTITY)) {
            metadata.setId(metadata.id() + 1);
        }
        if (entityType.isOrHasParent(Entity1_15Types.WOLF)) {
            if (metadata.id() == 18) {
                list.remove(metadata);
            }
            else if (metadata.id() > 18) {
                metadata.setId(metadata.id() - 1);
            }
        }
    }
    
    @Override
    public int newEntityId(final int n) {
        return EntityPackets.getNewEntityId(n);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_15Types.getTypeFromId(n);
    }
}
