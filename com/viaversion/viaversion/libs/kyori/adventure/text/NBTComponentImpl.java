package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.util.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

abstract class NBTComponentImpl extends AbstractComponent implements NBTComponent
{
    static final boolean INTERPRET_DEFAULT = false;
    final String nbtPath;
    final boolean interpret;
    @Nullable
    final Component separator;
    
    NBTComponentImpl(@NotNull final List children, @NotNull final Style style, final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator) {
        super(children, style);
        this.nbtPath = nbtPath;
        this.interpret = interpret;
        this.separator = ComponentLike.unbox(separator);
    }
    
    @NotNull
    @Override
    public String nbtPath() {
        return this.nbtPath;
    }
    
    @Override
    public boolean interpret() {
        return this.interpret;
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NBTComponent)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        final NBTComponent nbtComponent = (NBTComponent)other;
        return Objects.equals(this.nbtPath, nbtComponent.nbtPath()) && this.interpret == nbtComponent.interpret() && Objects.equals(this.separator, nbtComponent.separator());
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * super.hashCode() + this.nbtPath.hashCode()) + Boolean.hashCode(this.interpret)) + Objects.hashCode(this.separator);
    }
    
    @NotNull
    @Override
    protected Stream examinablePropertiesWithoutChildren() {
        return Stream.concat((Stream<?>)Stream.of((T[])new ExaminableProperty[] { ExaminableProperty.of("nbtPath", this.nbtPath), ExaminableProperty.of("interpret", this.interpret), ExaminableProperty.of("separator", this.separator) }), (Stream<?>)super.examinablePropertiesWithoutChildren());
    }
    
    abstract static class BuilderImpl extends AbstractComponentBuilder implements NBTComponentBuilder
    {
        @Nullable
        protected String nbtPath;
        protected boolean interpret;
        @Nullable
        protected Component separator;
        
        BuilderImpl() {
            this.interpret = false;
        }
        
        BuilderImpl(@NotNull final NBTComponent component) {
            super(component);
            this.interpret = false;
            this.nbtPath = component.nbtPath();
            this.interpret = component.interpret();
        }
        
        @NotNull
        @Override
        public NBTComponentBuilder nbtPath(@NotNull final String nbtPath) {
            this.nbtPath = nbtPath;
            return this;
        }
        
        @NotNull
        @Override
        public NBTComponentBuilder interpret(final boolean interpret) {
            this.interpret = interpret;
            return this;
        }
        
        @NotNull
        @Override
        public NBTComponentBuilder separator(@Nullable final ComponentLike separator) {
            this.separator = ComponentLike.unbox(separator);
            return this;
        }
    }
}
