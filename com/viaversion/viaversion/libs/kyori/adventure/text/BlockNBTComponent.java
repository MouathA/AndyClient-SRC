package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import java.util.regex.*;

public interface BlockNBTComponent extends NBTComponent, ScopedComponent
{
    @NotNull
    Pos pos();
    
    @Contract(pure = true)
    @NotNull
    BlockNBTComponent pos(@NotNull final Pos pos);
    
    @Contract(pure = true)
    @NotNull
    default BlockNBTComponent localPos(final double left, final double up, final double forwards) {
        return this.pos(LocalPos.of(left, up, forwards));
    }
    
    @Contract(pure = true)
    @NotNull
    default BlockNBTComponent worldPos(final WorldPos.Coordinate x, final WorldPos.Coordinate y, final WorldPos.Coordinate z) {
        return this.pos(WorldPos.of(x, y, z));
    }
    
    @Contract(pure = true)
    @NotNull
    default BlockNBTComponent absoluteWorldPos(final int x, final int y, final int z) {
        return this.worldPos(WorldPos.Coordinate.absolute(x), WorldPos.Coordinate.absolute(y), WorldPos.Coordinate.absolute(z));
    }
    
    @Contract(pure = true)
    @NotNull
    default BlockNBTComponent relativeWorldPos(final int x, final int y, final int z) {
        return this.worldPos(WorldPos.Coordinate.relative(x), WorldPos.Coordinate.relative(y), WorldPos.Coordinate.relative(z));
    }
    
    public interface WorldPos extends Pos
    {
        @NotNull
        default WorldPos of(@NotNull final Coordinate x, @NotNull final Coordinate y, @NotNull final Coordinate z) {
            return new BlockNBTComponentImpl.WorldPosImpl(x, y, z);
        }
        
        @NotNull
        Coordinate x();
        
        @NotNull
        Coordinate y();
        
        @NotNull
        Coordinate z();
        
        public interface Coordinate extends Examinable
        {
            @NotNull
            default Coordinate absolute(final int value) {
                return of(value, Type.ABSOLUTE);
            }
            
            @NotNull
            default Coordinate relative(final int value) {
                return of(value, Type.RELATIVE);
            }
            
            @NotNull
            default Coordinate of(final int value, @NotNull final Type type) {
                return new BlockNBTComponentImpl.WorldPosImpl.CoordinateImpl(value, type);
            }
            
            int value();
            
            @NotNull
            Type type();
            
            public enum Type
            {
                ABSOLUTE, 
                RELATIVE;
                
                private static final Type[] $VALUES;
                
                static {
                    $VALUES = new Type[] { Type.ABSOLUTE, Type.RELATIVE };
                }
            }
        }
    }
    
    public interface LocalPos extends Pos
    {
        @NotNull
        default LocalPos of(final double left, final double up, final double forwards) {
            return new BlockNBTComponentImpl.LocalPosImpl(left, up, forwards);
        }
        
        double left();
        
        double up();
        
        double forwards();
    }
    
    public interface Pos extends Examinable
    {
        @NotNull
        default Pos fromString(@NotNull final String input) throws IllegalArgumentException {
            final Matcher matcher = BlockNBTComponentImpl.Tokens.LOCAL_PATTERN.matcher(input);
            if (matcher.matches()) {
                return LocalPos.of(Double.parseDouble(matcher.group(1)), Double.parseDouble(matcher.group(3)), Double.parseDouble(matcher.group(5)));
            }
            final Matcher matcher2 = BlockNBTComponentImpl.Tokens.WORLD_PATTERN.matcher(input);
            if (matcher2.matches()) {
                return WorldPos.of(BlockNBTComponentImpl.Tokens.deserializeCoordinate(matcher2.group(1), matcher2.group(2)), BlockNBTComponentImpl.Tokens.deserializeCoordinate(matcher2.group(3), matcher2.group(4)), BlockNBTComponentImpl.Tokens.deserializeCoordinate(matcher2.group(5), matcher2.group(6)));
            }
            throw new IllegalArgumentException("Cannot convert position specification '" + input + "' into a position");
        }
        
        @NotNull
        String asString();
    }
    
    public interface Builder extends NBTComponentBuilder
    {
        @Contract("_ -> this")
        @NotNull
        Builder pos(@NotNull final Pos pos);
        
        @Contract("_, _, _ -> this")
        @NotNull
        default Builder localPos(final double left, final double up, final double forwards) {
            return this.pos(LocalPos.of(left, up, forwards));
        }
        
        @Contract("_, _, _ -> this")
        @NotNull
        default Builder worldPos(final WorldPos.Coordinate x, final WorldPos.Coordinate y, final WorldPos.Coordinate z) {
            return this.pos(WorldPos.of(x, y, z));
        }
        
        @Contract("_, _, _ -> this")
        @NotNull
        default Builder absoluteWorldPos(final int x, final int y, final int z) {
            return this.worldPos(WorldPos.Coordinate.absolute(x), WorldPos.Coordinate.absolute(y), WorldPos.Coordinate.absolute(z));
        }
        
        @Contract("_, _, _ -> this")
        @NotNull
        default Builder relativeWorldPos(final int x, final int y, final int z) {
            return this.worldPos(WorldPos.Coordinate.relative(x), WorldPos.Coordinate.relative(y), WorldPos.Coordinate.relative(z));
        }
    }
}
