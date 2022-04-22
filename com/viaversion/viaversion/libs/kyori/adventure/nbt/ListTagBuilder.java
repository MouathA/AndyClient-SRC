package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.*;
import org.jetbrains.annotations.*;

final class ListTagBuilder implements ListBinaryTag.Builder
{
    @Nullable
    private List tags;
    private BinaryTagType elementType;
    
    ListTagBuilder() {
        this(BinaryTagTypes.END);
    }
    
    ListTagBuilder(final BinaryTagType type) {
        this.elementType = type;
    }
    
    @Override
    public ListBinaryTag.Builder add(final BinaryTag tag) {
        ListBinaryTagImpl.noAddEnd(tag);
        if (this.elementType == BinaryTagTypes.END) {
            this.elementType = tag.type();
        }
        ListBinaryTagImpl.mustBeSameType(tag, this.elementType);
        if (this.tags == null) {
            this.tags = new ArrayList();
        }
        this.tags.add(tag);
        return this;
    }
    
    @Override
    public ListBinaryTag.Builder add(final Iterable tagsToAdd) {
        final Iterator<BinaryTag> iterator = tagsToAdd.iterator();
        while (iterator.hasNext()) {
            this.add((BinaryTag)iterator.next());
        }
        return this;
    }
    
    @NotNull
    @Override
    public ListBinaryTag build() {
        if (this.tags == null) {
            return ListBinaryTag.empty();
        }
        return new ListBinaryTagImpl(this.elementType, new ArrayList(this.tags));
    }
    
    @Override
    public Object add(final Iterable tagsToAdd) {
        return this.add(tagsToAdd);
    }
    
    @Override
    public Object add(final BinaryTag tag) {
        return this.add(tag);
    }
}
