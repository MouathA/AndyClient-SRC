package com.viaversion.viaversion.protocols.protocol1_14_1to1_14.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.entities.*;

public class MetadataRewriter1_14_1To1_14 extends EntityRewriter
{
    public MetadataRewriter1_14_1To1_14(final Protocol1_14_1To1_14 protocol1_14_1To1_14) {
        super(protocol1_14_1To1_14);
    }
    
    public void handleMetadata(final int n, final EntityType entityType, final Metadata metadata, final List list, final UserConnection userConnection) {
        if (entityType == null) {
            return;
        }
        if ((entityType == Entity1_14Types.VILLAGER || entityType == Entity1_14Types.WANDERING_TRADER) && metadata.id() >= 15) {
            metadata.setId(metadata.id() + 1);
        }
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_14Types.getTypeFromId(n);
    }
}
