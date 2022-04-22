package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.function.*;
import java.util.*;

public final class WrappedMetadata
{
    private final List metadataList;
    
    public WrappedMetadata(final List metadataList) {
        this.metadataList = metadataList;
    }
    
    public boolean has(final Metadata metadata) {
        return this.metadataList.contains(metadata);
    }
    
    public void remove(final Metadata metadata) {
        this.metadataList.remove(metadata);
    }
    
    public void remove(final int n) {
        this.metadataList.removeIf(WrappedMetadata::lambda$remove$0);
    }
    
    public void add(final Metadata metadata) {
        this.metadataList.add(metadata);
    }
    
    public Metadata get(final int n) {
        for (final Metadata metadata : this.metadataList) {
            if (n == metadata.id()) {
                return metadata;
            }
        }
        return null;
    }
    
    public List metadataList() {
        return this.metadataList;
    }
    
    @Override
    public String toString() {
        return "MetaStorage{metaDataList=" + this.metadataList + '}';
    }
    
    private static boolean lambda$remove$0(final int n, final Metadata metadata) {
        return metadata.id() == n;
    }
}
