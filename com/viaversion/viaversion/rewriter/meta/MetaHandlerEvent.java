package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;

public interface MetaHandlerEvent
{
    UserConnection user();
    
    int entityId();
    
    EntityType entityType();
    
    default int index() {
        return this.meta().id();
    }
    
    default void setIndex(final int id) {
        this.meta().setId(id);
    }
    
    Metadata meta();
    
    void cancel();
    
    boolean cancelled();
    
    Metadata metaAtIndex(final int p0);
    
    List metadataList();
    
    List extraMeta();
    
    void createExtraMeta(final Metadata p0);
}
