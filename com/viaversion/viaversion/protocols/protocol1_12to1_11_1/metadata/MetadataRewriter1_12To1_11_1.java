package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.minecraft.entities.*;

public class MetadataRewriter1_12To1_11_1 extends EntityRewriter
{
    public MetadataRewriter1_12To1_11_1(final Protocol1_12To1_11_1 protocol1_12To1_11_1) {
        super(protocol1_12To1_11_1);
    }
    
    @Override
    protected void handleMetadata(final int n, final EntityType entityType, final Metadata metadata, final List list, final UserConnection userConnection) {
        if (metadata.getValue() instanceof Item) {
            metadata.setValue(((Protocol1_12To1_11_1)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue()));
        }
        if (entityType == null) {
            return;
        }
        if (entityType == Entity1_12Types.EntityType.EVOCATION_ILLAGER && metadata.id() == 12) {
            metadata.setId(13);
        }
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_12Types.getTypeFromId(n, false);
    }
    
    @Override
    public EntityType objectTypeFromId(final int n) {
        return Entity1_12Types.getTypeFromId(n, true);
    }
}
