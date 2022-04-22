package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import org.jetbrains.annotations.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

final class PointersImpl implements Pointers
{
    static final Pointers EMPTY;
    private final Map pointers;
    
    PointersImpl(@NotNull final BuilderImpl builder) {
        this.pointers = new HashMap(BuilderImpl.access$000(builder));
    }
    
    @NotNull
    @Override
    public Optional get(@NotNull final Pointer pointer) {
        Objects.requireNonNull(pointer, "pointer");
        final Supplier<T> supplier = this.pointers.get(pointer);
        if (supplier == null) {
            return Optional.empty();
        }
        return Optional.ofNullable((Object)supplier.get());
    }
    
    @Override
    public boolean supports(@NotNull final Pointer pointer) {
        Objects.requireNonNull(pointer, "pointer");
        return this.pointers.containsKey(pointer);
    }
    
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }
    
    @NotNull
    @Override
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    static Map access$100(final PointersImpl pointersImpl) {
        return pointersImpl.pointers;
    }
    
    static {
        EMPTY = new Pointers() {
            @NotNull
            @Override
            public Optional get(@NotNull final Pointer pointer) {
                return Optional.empty();
            }
            
            @Override
            public boolean supports(@NotNull final Pointer pointer) {
                return false;
            }
            
            @Override
            public Builder toBuilder() {
                return new BuilderImpl();
            }
            
            @Override
            public String toString() {
                return "EmptyPointers";
            }
            
            @Override
            public Buildable.Builder toBuilder() {
                return this.toBuilder();
            }
        };
    }
    
    static final class BuilderImpl implements Builder
    {
        private final Map pointers;
        
        BuilderImpl() {
            this.pointers = new HashMap();
        }
        
        BuilderImpl(@NotNull final PointersImpl pointers) {
            this.pointers = new HashMap(PointersImpl.access$100(pointers));
        }
        
        @NotNull
        @Override
        public Builder withDynamic(@NotNull final Pointer pointer, @NotNull final Supplier value) {
            this.pointers.put(Objects.requireNonNull(pointer, "pointer"), Objects.requireNonNull(value, "value"));
            return this;
        }
        
        @NotNull
        @Override
        public Pointers build() {
            return new PointersImpl(this);
        }
        
        @NotNull
        @Override
        public Object build() {
            return this.build();
        }
        
        static Map access$000(final BuilderImpl builderImpl) {
            return builderImpl.pointers;
        }
    }
}
