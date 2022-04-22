package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import java.util.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.function.*;

final class GsonComponentSerializerImpl implements GsonComponentSerializer
{
    private static final Optional SERVICE;
    static final Consumer BUILDER;
    private final Gson serializer;
    private final UnaryOperator populator;
    private final boolean downsampleColor;
    @Nullable
    private final LegacyHoverEventSerializer legacyHoverSerializer;
    private final boolean emitLegacyHover;
    
    GsonComponentSerializerImpl(final boolean downsampleColor, @Nullable final LegacyHoverEventSerializer legacyHoverSerializer, final boolean emitLegacyHover) {
        this.downsampleColor = downsampleColor;
        this.legacyHoverSerializer = legacyHoverSerializer;
        this.emitLegacyHover = emitLegacyHover;
        this.populator = GsonComponentSerializerImpl::lambda$new$2;
        this.serializer = ((GsonBuilder)this.populator.apply(new GsonBuilder())).create();
    }
    
    @NotNull
    @Override
    public Gson serializer() {
        return this.serializer;
    }
    
    @NotNull
    @Override
    public UnaryOperator populator() {
        return this.populator;
    }
    
    @NotNull
    public Component deserialize(@NotNull final String string) {
        final Component component = (Component)this.serializer().fromJson(string, Component.class);
        if (component == null) {
            throw ComponentSerializerImpl.notSureHowToDeserialize(string);
        }
        return component;
    }
    
    @NotNull
    @Override
    public String serialize(@NotNull final Component component) {
        return this.serializer().toJson(component);
    }
    
    @NotNull
    @Override
    public Component deserializeFromTree(@NotNull final JsonElement input) {
        final Component component = (Component)this.serializer().fromJson(input, Component.class);
        if (component == null) {
            throw ComponentSerializerImpl.notSureHowToDeserialize(input);
        }
        return component;
    }
    
    @NotNull
    @Override
    public JsonElement serializeToTree(@NotNull final Component component) {
        return this.serializer().toJsonTree(component);
    }
    
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }
    
    @NotNull
    @Override
    public Object serialize(@NotNull final Component component) {
        return this.serialize(component);
    }
    
    @NotNull
    @Override
    public Component deserialize(@NotNull final Object string) {
        return this.deserialize((String)string);
    }
    
    @NotNull
    @Override
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    private static GsonBuilder lambda$new$2(final boolean downsampleColors, final LegacyHoverEventSerializer legacyHoverSerializer, final boolean emitLegacyHover, final GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapterFactory(new SerializerFactory(downsampleColors, legacyHoverSerializer, emitLegacyHover));
        return gsonBuilder;
    }
    
    private static Consumer lambda$static$1() {
        return GsonComponentSerializerImpl::lambda$static$0;
    }
    
    private static void lambda$static$0(final Builder builder) {
    }
    
    static Optional access$000() {
        return GsonComponentSerializerImpl.SERVICE;
    }
    
    static boolean access$100(final GsonComponentSerializerImpl gsonComponentSerializerImpl) {
        return gsonComponentSerializerImpl.downsampleColor;
    }
    
    static boolean access$200(final GsonComponentSerializerImpl gsonComponentSerializerImpl) {
        return gsonComponentSerializerImpl.emitLegacyHover;
    }
    
    static LegacyHoverEventSerializer access$300(final GsonComponentSerializerImpl gsonComponentSerializerImpl) {
        return gsonComponentSerializerImpl.legacyHoverSerializer;
    }
    
    static {
        SERVICE = Services.service(Provider.class);
        BUILDER = GsonComponentSerializerImpl.SERVICE.map(Provider::builder).orElseGet(GsonComponentSerializerImpl::lambda$static$1);
    }
    
    static final class BuilderImpl implements Builder
    {
        private boolean downsampleColor;
        @Nullable
        private LegacyHoverEventSerializer legacyHoverSerializer;
        private boolean emitLegacyHover;
        
        BuilderImpl() {
            this.downsampleColor = false;
            this.emitLegacyHover = false;
            GsonComponentSerializerImpl.BUILDER.accept(this);
        }
        
        BuilderImpl(final GsonComponentSerializerImpl serializer) {
            this();
            this.downsampleColor = GsonComponentSerializerImpl.access$100(serializer);
            this.emitLegacyHover = GsonComponentSerializerImpl.access$200(serializer);
            this.legacyHoverSerializer = GsonComponentSerializerImpl.access$300(serializer);
        }
        
        @NotNull
        @Override
        public Builder downsampleColors() {
            this.downsampleColor = true;
            return this;
        }
        
        @NotNull
        @Override
        public Builder legacyHoverEventSerializer(@Nullable final LegacyHoverEventSerializer serializer) {
            this.legacyHoverSerializer = serializer;
            return this;
        }
        
        @NotNull
        @Override
        public Builder emitLegacyHoverEvent() {
            this.emitLegacyHover = true;
            return this;
        }
        
        @NotNull
        @Override
        public GsonComponentSerializer build() {
            if (this.legacyHoverSerializer == null) {
                return this.downsampleColor ? Instances.LEGACY_INSTANCE : Instances.INSTANCE;
            }
            return new GsonComponentSerializerImpl(this.downsampleColor, this.legacyHoverSerializer, this.emitLegacyHover);
        }
        
        @NotNull
        @Override
        public Object build() {
            return this.build();
        }
    }
    
    static final class Instances
    {
        static final GsonComponentSerializer INSTANCE;
        static final GsonComponentSerializer LEGACY_INSTANCE;
        
        private static GsonComponentSerializer lambda$static$1() {
            return new GsonComponentSerializerImpl(true, null, true);
        }
        
        private static GsonComponentSerializer lambda$static$0() {
            return new GsonComponentSerializerImpl(false, null, false);
        }
        
        static {
            INSTANCE = GsonComponentSerializerImpl.access$000().map(Provider::gson).orElseGet(Instances::lambda$static$0);
            LEGACY_INSTANCE = GsonComponentSerializerImpl.access$000().map(Provider::gsonLegacy).orElseGet(Instances::lambda$static$1);
        }
    }
}
