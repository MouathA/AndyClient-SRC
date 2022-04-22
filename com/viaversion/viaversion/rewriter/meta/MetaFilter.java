package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.minecraft.entities.*;
import com.google.common.base.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import com.viaversion.viaversion.rewriter.*;

public class MetaFilter
{
    private final MetaHandler handler;
    private final EntityType type;
    private final int index;
    private final boolean filterFamily;
    
    public MetaFilter(final EntityType type, final boolean filterFamily, final int index, final MetaHandler handler) {
        Preconditions.checkNotNull(handler, (Object)"MetaHandler cannot be null");
        this.type = type;
        this.filterFamily = filterFamily;
        this.index = index;
        this.handler = handler;
    }
    
    public int index() {
        return this.index;
    }
    
    public EntityType type() {
        return this.type;
    }
    
    public MetaHandler handler() {
        return this.handler;
    }
    
    public boolean filterFamily() {
        return this.filterFamily;
    }
    
    public boolean isFiltered(final EntityType entityType, final Metadata metadata) {
        return (this.index == -1 || metadata.id() == this.index) && (this.type == null || this.matchesType(entityType));
    }
    
    private boolean matchesType(final EntityType entityType) {
        return entityType != null && (this.filterFamily ? entityType.isOrHasParent(this.type) : (this.type == entityType));
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final MetaFilter metaFilter = (MetaFilter)o;
        return this.index == metaFilter.index && this.filterFamily == metaFilter.filterFamily && this.handler.equals(metaFilter.handler) && Objects.equals(this.type, metaFilter.type);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * this.handler.hashCode() + ((this.type != null) ? this.type.hashCode() : 0)) + this.index) + (this.filterFamily ? 1 : 0);
    }
    
    @Override
    public String toString() {
        return "MetaFilter{type=" + this.type + ", filterFamily=" + this.filterFamily + ", index=" + this.index + ", handler=" + this.handler + '}';
    }
    
    public static final class Builder
    {
        private final EntityRewriter rewriter;
        private EntityType type;
        private int index;
        private boolean filterFamily;
        private MetaHandler handler;
        
        public Builder(final EntityRewriter rewriter) {
            this.index = -1;
            this.rewriter = rewriter;
        }
        
        public Builder type(final EntityType type) {
            Preconditions.checkArgument(this.type == null);
            this.type = type;
            return this;
        }
        
        public Builder index(final int index) {
            Preconditions.checkArgument(this.index == -1);
            this.index = index;
            return this;
        }
        
        public Builder filterFamily(final EntityType type) {
            Preconditions.checkArgument(this.type == null);
            this.type = type;
            this.filterFamily = true;
            return this;
        }
        
        public Builder handlerNoRegister(final MetaHandler handler) {
            Preconditions.checkArgument(this.handler == null);
            this.handler = handler;
            return this;
        }
        
        public void handler(final MetaHandler handler) {
            Preconditions.checkArgument(this.handler == null);
            this.handler = handler;
            this.register();
        }
        
        public void cancel(final int index) {
            this.index = index;
            this.handler(Builder::lambda$cancel$0);
        }
        
        public void toIndex(final int n) {
            Preconditions.checkArgument(this.index != -1);
            this.handler(Builder::lambda$toIndex$1);
        }
        
        public void addIndex(final int n) {
            Preconditions.checkArgument(this.index == -1);
            this.handler(Builder::lambda$addIndex$2);
        }
        
        public void removeIndex(final int n) {
            Preconditions.checkArgument(this.index == -1);
            this.handler(Builder::lambda$removeIndex$3);
        }
        
        public void register() {
            this.rewriter.registerFilter(this.build());
        }
        
        public MetaFilter build() {
            return new MetaFilter(this.type, this.filterFamily, this.index, this.handler);
        }
        
        private static void lambda$removeIndex$3(final int n, final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
            final int index = metaHandlerEvent.index();
            if (index == n) {
                metaHandlerEvent.cancel();
            }
            else if (index > n) {
                metaHandlerEvent.setIndex(index - 1);
            }
        }
        
        private static void lambda$addIndex$2(final int n, final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
            if (metaHandlerEvent.index() >= n) {
                metaHandlerEvent.setIndex(metaHandlerEvent.index() + 1);
            }
        }
        
        private static void lambda$toIndex$1(final int index, final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
            metaHandlerEvent.setIndex(index);
        }
        
        private static void lambda$cancel$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
            metaHandlerEvent.cancel();
        }
    }
}
