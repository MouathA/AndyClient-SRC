package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import org.jetbrains.annotations.*;
import java.util.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import java.util.regex.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

final class BlockNBTComponentImpl extends NBTComponentImpl implements BlockNBTComponent
{
    private final Pos pos;
    
    BlockNBTComponentImpl(@NotNull final List children, @NotNull final Style style, final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, @NotNull final Pos pos) {
        super(children, style, nbtPath, interpret, separator);
        this.pos = pos;
    }
    
    @NotNull
    @Override
    public BlockNBTComponent nbtPath(@NotNull final String nbtPath) {
        if (Objects.equals(this.nbtPath, nbtPath)) {
            return this;
        }
        return new BlockNBTComponentImpl(this.children, this.style, nbtPath, this.interpret, this.separator, this.pos);
    }
    
    @NotNull
    @Override
    public BlockNBTComponent interpret(final boolean interpret) {
        if (this.interpret == interpret) {
            return this;
        }
        return new BlockNBTComponentImpl(this.children, this.style, this.nbtPath, interpret, this.separator, this.pos);
    }
    
    @Nullable
    @Override
    public Component separator() {
        return this.separator;
    }
    
    @NotNull
    @Override
    public BlockNBTComponent separator(@Nullable final ComponentLike separator) {
        return new BlockNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, separator, this.pos);
    }
    
    @NotNull
    @Override
    public Pos pos() {
        return this.pos;
    }
    
    @NotNull
    @Override
    public BlockNBTComponent pos(@NotNull final Pos pos) {
        return new BlockNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, this.separator, pos);
    }
    
    @NotNull
    @Override
    public BlockNBTComponent children(@NotNull final List children) {
        return new BlockNBTComponentImpl(children, this.style, this.nbtPath, this.interpret, this.separator, this.pos);
    }
    
    @NotNull
    @Override
    public BlockNBTComponent style(@NotNull final Style style) {
        return new BlockNBTComponentImpl(this.children, style, this.nbtPath, this.interpret, this.separator, this.pos);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other instanceof BlockNBTComponent && super.equals(other) && Objects.equals(this.pos, ((BlockNBTComponent)other).pos()));
    }
    
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.pos.hashCode();
    }
    
    @NotNull
    @Override
    protected Stream examinablePropertiesWithoutChildren() {
        return Stream.concat((Stream<?>)Stream.of((T)ExaminableProperty.of("pos", this.pos)), (Stream<?>)super.examinablePropertiesWithoutChildren());
    }
    
    @Override
    public BlockNBTComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }
    
    @NotNull
    @Override
    public NBTComponent separator(@Nullable final ComponentLike separator) {
        return this.separator(separator);
    }
    
    @NotNull
    @Override
    public NBTComponent interpret(final boolean interpret) {
        return this.interpret(interpret);
    }
    
    @NotNull
    @Override
    public NBTComponent nbtPath(@NotNull final String nbtPath) {
        return this.nbtPath(nbtPath);
    }
    
    @Override
    public ComponentBuilder toBuilder() {
        return this.toBuilder();
    }
    
    @Override
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    @NotNull
    @Override
    public Component style(@NotNull final Style style) {
        return this.style(style);
    }
    
    @NotNull
    @Override
    public Component children(@NotNull final List children) {
        return this.children(children);
    }
    
    static final class Tokens
    {
        static final Pattern LOCAL_PATTERN;
        static final Pattern WORLD_PATTERN;
        static final String LOCAL_SYMBOL = "^";
        static final String RELATIVE_SYMBOL = "~";
        static final String ABSOLUTE_SYMBOL = "";
        
        private Tokens() {
        }
        
        static WorldPos.Coordinate deserializeCoordinate(final String prefix, final String value) {
            final int int1 = Integer.parseInt(value);
            if (prefix.equals("")) {
                return WorldPos.Coordinate.absolute(int1);
            }
            if (prefix.equals("~")) {
                return WorldPos.Coordinate.relative(int1);
            }
            throw new AssertionError();
        }
        
        static String serializeLocal(final double value) {
            return "^" + value;
        }
        
        static String serializeCoordinate(final WorldPos.Coordinate coordinate) {
            return ((coordinate.type() == WorldPos.Coordinate.Type.RELATIVE) ? "~" : "") + coordinate.value();
        }
        
        static {
            LOCAL_PATTERN = Pattern.compile("^\\^(\\d+(\\.\\d+)?) \\^(\\d+(\\.\\d+)?) \\^(\\d+(\\.\\d+)?)$");
            WORLD_PATTERN = Pattern.compile("^(~?)(\\d+) (~?)(\\d+) (~?)(\\d+)$");
        }
    }
    
    static final class WorldPosImpl implements WorldPos
    {
        private final Coordinate x;
        private final Coordinate y;
        private final Coordinate z;
        
        WorldPosImpl(final Coordinate x, final Coordinate y, final Coordinate z) {
            this.x = Objects.requireNonNull(x, "x");
            this.y = Objects.requireNonNull(y, "y");
            this.z = Objects.requireNonNull(z, "z");
        }
        
        @NotNull
        @Override
        public Coordinate x() {
            return this.x;
        }
        
        @NotNull
        @Override
        public Coordinate y() {
            return this.y;
        }
        
        @NotNull
        @Override
        public Coordinate z() {
            return this.z;
        }
        
        @NotNull
        @Override
        public Stream examinableProperties() {
            return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("x", this.x), ExaminableProperty.of("y", this.y), ExaminableProperty.of("z", this.z) });
        }
        
        @Override
        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof WorldPos)) {
                return false;
            }
            final WorldPos worldPos = (WorldPos)other;
            return this.x.equals(worldPos.x()) && this.y.equals(worldPos.y()) && this.z.equals(worldPos.z());
        }
        
        @Override
        public int hashCode() {
            return 31 * (31 * this.x.hashCode() + this.y.hashCode()) + this.z.hashCode();
        }
        
        @Override
        public String toString() {
            return this.x.toString() + ' ' + this.y.toString() + ' ' + this.z.toString();
        }
        
        @NotNull
        @Override
        public String asString() {
            return Tokens.serializeCoordinate(this.x()) + ' ' + Tokens.serializeCoordinate(this.y()) + ' ' + Tokens.serializeCoordinate(this.z());
        }
        
        static final class CoordinateImpl implements Coordinate
        {
            private final int value;
            private final Type type;
            
            CoordinateImpl(final int value, @NotNull final Type type) {
                this.value = value;
                this.type = Objects.requireNonNull(type, "type");
            }
            
            @Override
            public int value() {
                return this.value;
            }
            
            @NotNull
            @Override
            public Type type() {
                return this.type;
            }
            
            @NotNull
            @Override
            public Stream examinableProperties() {
                return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("value", this.value), ExaminableProperty.of("type", this.type) });
            }
            
            @Override
            public boolean equals(@Nullable final Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Coordinate)) {
                    return false;
                }
                final Coordinate coordinate = (Coordinate)other;
                return this.value() == coordinate.value() && this.type() == coordinate.type();
            }
            
            @Override
            public int hashCode() {
                return 31 * this.value + this.type.hashCode();
            }
            
            @Override
            public String toString() {
                return ((this.type == Type.RELATIVE) ? "~" : "") + this.value;
            }
        }
    }
    
    static final class LocalPosImpl implements LocalPos
    {
        private final double left;
        private final double up;
        private final double forwards;
        
        LocalPosImpl(final double left, final double up, final double forwards) {
            this.left = left;
            this.up = up;
            this.forwards = forwards;
        }
        
        @Override
        public double left() {
            return this.left;
        }
        
        @Override
        public double up() {
            return this.up;
        }
        
        @Override
        public double forwards() {
            return this.forwards;
        }
        
        @NotNull
        @Override
        public Stream examinableProperties() {
            return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("left", this.left), ExaminableProperty.of("up", this.up), ExaminableProperty.of("forwards", this.forwards) });
        }
        
        @Override
        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LocalPos)) {
                return false;
            }
            final LocalPos localPos = (LocalPos)other;
            return ShadyPines.equals(localPos.left(), this.left()) && ShadyPines.equals(localPos.up(), this.up()) && ShadyPines.equals(localPos.forwards(), this.forwards());
        }
        
        @Override
        public int hashCode() {
            return 31 * (31 * Double.hashCode(this.left) + Double.hashCode(this.up)) + Double.hashCode(this.forwards);
        }
        
        @Override
        public String toString() {
            return String.format("^%f ^%f ^%f", this.left, this.up, this.forwards);
        }
        
        @NotNull
        @Override
        public String asString() {
            return Tokens.serializeLocal(this.left) + ' ' + Tokens.serializeLocal(this.up) + ' ' + Tokens.serializeLocal(this.forwards);
        }
    }
    
    static final class BuilderImpl extends NBTComponentImpl.BuilderImpl implements BlockNBTComponent.Builder
    {
        @Nullable
        private Pos pos;
        
        BuilderImpl() {
        }
        
        BuilderImpl(@NotNull final BlockNBTComponent component) {
            super(component);
            this.pos = component.pos();
        }
        
        @Override
        public BlockNBTComponent.Builder pos(@NotNull final Pos pos) {
            this.pos = pos;
            return this;
        }
        
        @NotNull
        @Override
        public BlockNBTComponent build() {
            if (this.nbtPath == null) {
                throw new IllegalStateException("nbt path must be set");
            }
            if (this.pos == null) {
                throw new IllegalStateException("pos must be set");
            }
            return new BlockNBTComponentImpl(this.children, this.buildStyle(), this.nbtPath, this.interpret, this.separator, this.pos);
        }
        
        @NotNull
        @Override
        public BuildableComponent build() {
            return this.build();
        }
        
        @NotNull
        @Override
        public Object build() {
            return this.build();
        }
    }
}
