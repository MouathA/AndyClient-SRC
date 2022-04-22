package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;

public class MetaHandlerEventImpl implements MetaHandlerEvent
{
    private final UserConnection connection;
    private final EntityType entityType;
    private final int entityId;
    private final List metadataList;
    private final Metadata meta;
    private List extraData;
    private boolean cancel;
    
    public MetaHandlerEventImpl(final UserConnection connection, final EntityType entityType, final int entityId, final Metadata meta, final List metadataList) {
        this.connection = connection;
        this.entityType = entityType;
        this.entityId = entityId;
        this.meta = meta;
        this.metadataList = metadataList;
    }
    
    @Override
    public UserConnection user() {
        return this.connection;
    }
    
    @Override
    public int entityId() {
        return this.entityId;
    }
    
    @Override
    public EntityType entityType() {
        return this.entityType;
    }
    
    @Override
    public Metadata meta() {
        return this.meta;
    }
    
    @Override
    public void cancel() {
        this.cancel = true;
    }
    
    @Override
    public boolean cancelled() {
        return this.cancel;
    }
    
    @Override
    public Metadata metaAtIndex(final int n) {
        for (final Metadata metadata : this.metadataList) {
            if (n == metadata.id()) {
                return metadata;
            }
        }
        return null;
    }
    
    @Override
    public List metadataList() {
        return Collections.unmodifiableList((List<?>)this.metadataList);
    }
    
    @Override
    public List extraMeta() {
        return this.extraData;
    }
    
    @Override
    public void createExtraMeta(final Metadata metadata) {
        ((this.extraData != null) ? this.extraData : (this.extraData = new ArrayList())).add(metadata);
    }
}
