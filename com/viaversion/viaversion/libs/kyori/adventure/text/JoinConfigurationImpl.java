package com.viaversion.viaversion.libs.kyori.adventure.text;

import java.util.function.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import java.util.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

final class JoinConfigurationImpl implements JoinConfiguration
{
    static final Function DEFAULT_CONVERTOR;
    static final Predicate DEFAULT_PREDICATE;
    static final JoinConfigurationImpl NULL;
    private final Component prefix;
    private final Component suffix;
    private final Component separator;
    private final Component lastSeparator;
    private final Component lastSeparatorIfSerial;
    private final Function convertor;
    private final Predicate predicate;
    
    private JoinConfigurationImpl() {
        this.prefix = null;
        this.suffix = null;
        this.separator = null;
        this.lastSeparator = null;
        this.lastSeparatorIfSerial = null;
        this.convertor = JoinConfigurationImpl.DEFAULT_CONVERTOR;
        this.predicate = JoinConfigurationImpl.DEFAULT_PREDICATE;
    }
    
    private JoinConfigurationImpl(@NotNull final BuilderImpl builder) {
        this.prefix = ((BuilderImpl.access$000(builder) == null) ? null : BuilderImpl.access$000(builder).asComponent());
        this.suffix = ((BuilderImpl.access$100(builder) == null) ? null : BuilderImpl.access$100(builder).asComponent());
        this.separator = ((BuilderImpl.access$200(builder) == null) ? null : BuilderImpl.access$200(builder).asComponent());
        this.lastSeparator = ((BuilderImpl.access$300(builder) == null) ? null : BuilderImpl.access$300(builder).asComponent());
        this.lastSeparatorIfSerial = ((BuilderImpl.access$400(builder) == null) ? null : BuilderImpl.access$400(builder).asComponent());
        this.convertor = BuilderImpl.access$500(builder);
        this.predicate = BuilderImpl.access$600(builder);
    }
    
    @Nullable
    @Override
    public Component prefix() {
        return this.prefix;
    }
    
    @Nullable
    @Override
    public Component suffix() {
        return this.suffix;
    }
    
    @Nullable
    @Override
    public Component separator() {
        return this.separator;
    }
    
    @Nullable
    @Override
    public Component lastSeparator() {
        return this.lastSeparator;
    }
    
    @Nullable
    @Override
    public Component lastSeparatorIfSerial() {
        return this.lastSeparatorIfSerial;
    }
    
    @NotNull
    @Override
    public Function convertor() {
        return this.convertor;
    }
    
    @NotNull
    @Override
    public Predicate predicate() {
        return this.predicate;
    }
    
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this, null);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("prefix", this.prefix), ExaminableProperty.of("suffix", this.suffix), ExaminableProperty.of("separator", this.separator), ExaminableProperty.of("lastSeparator", this.lastSeparator), ExaminableProperty.of("lastSeparatorIfSerial", this.lastSeparatorIfSerial), ExaminableProperty.of("convertor", this.convertor), ExaminableProperty.of("predicate", this.predicate) });
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    @Contract(pure = true)
    @NotNull
    static Component join(@NotNull final JoinConfiguration config, @NotNull final Iterable components) {
        Objects.requireNonNull(config, "config");
        Objects.requireNonNull(components, "components");
        final Iterator<Iterable<Iterable<Iterable<T>>>> iterator = components.iterator();
        final Component prefix = config.prefix();
        final Component suffix = config.suffix();
        final Function convertor = config.convertor();
        final Predicate predicate = config.predicate();
        if (!iterator.hasNext()) {
            return singleElementJoin(config, null);
        }
        ComponentLike component = Objects.requireNonNull((ComponentLike)iterator.next(), "Null elements in \"components\" are not allowed");
        if (!iterator.hasNext()) {
            return singleElementJoin(config, component);
        }
        final Component separator = config.separator();
        final boolean b = separator != null;
        final TextComponent.Builder text = Component.text();
        if (prefix != null) {
            text.append(prefix);
        }
        while (component != null) {
            if (!predicate.test(component)) {
                if (!iterator.hasNext()) {
                    break;
                }
                component = (ComponentLike)iterator.next();
            }
            else {
                text.append(Objects.requireNonNull((Component)convertor.apply(component), "Null output from \"convertor\" is not allowed"));
                int n = 0;
                ++n;
                if (!iterator.hasNext()) {
                    component = null;
                }
                else {
                    component = Objects.requireNonNull((ComponentLike)iterator.next(), "Null elements in \"components\" are not allowed");
                    if (iterator.hasNext()) {
                        if (!b) {
                            continue;
                        }
                        text.append(separator);
                    }
                    else {
                        Component component2 = null;
                        if (0 > 1) {
                            component2 = config.lastSeparatorIfSerial();
                        }
                        if (component2 == null) {
                            component2 = config.lastSeparator();
                        }
                        if (component2 == null) {
                            component2 = config.separator();
                        }
                        if (component2 == null) {
                            continue;
                        }
                        text.append(component2);
                    }
                }
            }
        }
        if (suffix != null) {
            text.append(suffix);
        }
        return text.build();
    }
    
    @NotNull
    static Component singleElementJoin(@NotNull final JoinConfiguration config, @Nullable final ComponentLike component) {
        final Component prefix = config.prefix();
        final Component suffix = config.suffix();
        final Function convertor = config.convertor();
        final Predicate predicate = config.predicate();
        if (prefix != null || suffix != null) {
            final TextComponent.Builder text = Component.text();
            if (prefix != null) {
                text.append(prefix);
            }
            if (component != null && predicate.test(component)) {
                text.append(convertor.apply(component));
            }
            if (suffix != null) {
                text.append(suffix);
            }
            return text.build();
        }
        if (component == null || !predicate.test(component)) {
            return Component.empty();
        }
        return convertor.apply(component);
    }
    
    @Override
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    private static boolean lambda$static$0(final ComponentLike componentLike) {
        return true;
    }
    
    static Component access$800(final JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.separator;
    }
    
    static Component access$900(final JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.lastSeparator;
    }
    
    static Component access$1000(final JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.prefix;
    }
    
    static Component access$1100(final JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.suffix;
    }
    
    static Function access$1200(final JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.convertor;
    }
    
    static Component access$1300(final JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.lastSeparatorIfSerial;
    }
    
    static Predicate access$1400(final JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.predicate;
    }
    
    JoinConfigurationImpl(final BuilderImpl builder, final JoinConfigurationImpl$1 object) {
        this(builder);
    }
    
    static {
        DEFAULT_CONVERTOR = ComponentLike::asComponent;
        DEFAULT_PREDICATE = JoinConfigurationImpl::lambda$static$0;
        NULL = new JoinConfigurationImpl();
    }
    
    static final class BuilderImpl implements Builder
    {
        private ComponentLike prefix;
        private ComponentLike suffix;
        private ComponentLike separator;
        private ComponentLike lastSeparator;
        private ComponentLike lastSeparatorIfSerial;
        private Function convertor;
        private Predicate predicate;
        
        BuilderImpl() {
            this(JoinConfigurationImpl.NULL);
        }
        
        private BuilderImpl(@NotNull final JoinConfigurationImpl joinConfig) {
            this.separator = JoinConfigurationImpl.access$800(joinConfig);
            this.lastSeparator = JoinConfigurationImpl.access$900(joinConfig);
            this.prefix = JoinConfigurationImpl.access$1000(joinConfig);
            this.suffix = JoinConfigurationImpl.access$1100(joinConfig);
            this.convertor = JoinConfigurationImpl.access$1200(joinConfig);
            this.lastSeparatorIfSerial = JoinConfigurationImpl.access$1300(joinConfig);
            this.predicate = JoinConfigurationImpl.access$1400(joinConfig);
        }
        
        @NotNull
        @Override
        public Builder prefix(@Nullable final ComponentLike prefix) {
            this.prefix = prefix;
            return this;
        }
        
        @NotNull
        @Override
        public Builder suffix(@Nullable final ComponentLike suffix) {
            this.suffix = suffix;
            return this;
        }
        
        @NotNull
        @Override
        public Builder separator(@Nullable final ComponentLike separator) {
            this.separator = separator;
            return this;
        }
        
        @NotNull
        @Override
        public Builder lastSeparator(@Nullable final ComponentLike lastSeparator) {
            this.lastSeparator = lastSeparator;
            return this;
        }
        
        @NotNull
        @Override
        public Builder lastSeparatorIfSerial(@Nullable final ComponentLike lastSeparatorIfSerial) {
            this.lastSeparatorIfSerial = lastSeparatorIfSerial;
            return this;
        }
        
        @NotNull
        @Override
        public Builder convertor(@NotNull final Function convertor) {
            this.convertor = Objects.requireNonNull(convertor, "convertor");
            return this;
        }
        
        @NotNull
        @Override
        public Builder predicate(@NotNull final Predicate predicate) {
            this.predicate = Objects.requireNonNull(predicate, "predicate");
            return this;
        }
        
        @NotNull
        @Override
        public JoinConfiguration build() {
            return new JoinConfigurationImpl(this, null);
        }
        
        @NotNull
        @Override
        public Object build() {
            return this.build();
        }
        
        static ComponentLike access$000(final BuilderImpl builderImpl) {
            return builderImpl.prefix;
        }
        
        static ComponentLike access$100(final BuilderImpl builderImpl) {
            return builderImpl.suffix;
        }
        
        static ComponentLike access$200(final BuilderImpl builderImpl) {
            return builderImpl.separator;
        }
        
        static ComponentLike access$300(final BuilderImpl builderImpl) {
            return builderImpl.lastSeparator;
        }
        
        static ComponentLike access$400(final BuilderImpl builderImpl) {
            return builderImpl.lastSeparatorIfSerial;
        }
        
        static Function access$500(final BuilderImpl builderImpl) {
            return builderImpl.convertor;
        }
        
        static Predicate access$600(final BuilderImpl builderImpl) {
            return builderImpl.predicate;
        }
        
        BuilderImpl(final JoinConfigurationImpl joinConfig, final JoinConfigurationImpl$1 object) {
            this(joinConfig);
        }
    }
}
