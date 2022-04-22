package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.minecraft.entities.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.data.entity.*;

public interface EntityRewriter extends Rewriter
{
    EntityType typeFromId(final int p0);
    
    default EntityType objectTypeFromId(final int n) {
        return this.typeFromId(n);
    }
    
    int newEntityId(final int p0);
    
    void handleMetadata(final int p0, final List p1, final UserConnection p2);
    
    default EntityTracker tracker(final UserConnection userConnection) {
        return userConnection.getEntityTracker(this.protocol().getClass());
    }
}
