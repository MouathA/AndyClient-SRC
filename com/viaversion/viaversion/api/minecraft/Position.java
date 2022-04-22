package com.viaversion.viaversion.api.minecraft;

public class Position
{
    private final int x;
    private final int y;
    private final int z;
    
    public Position(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Deprecated
    public Position(final int n, final short n2, final int n3) {
        this(n, (int)n2, n3);
    }
    
    @Deprecated
    public Position(final Position position) {
        this(position.x(), position.y(), position.z());
    }
    
    public Position getRelative(final BlockFace blockFace) {
        return new Position(this.x + blockFace.modX(), (short)(this.y + blockFace.modY()), this.z + blockFace.modZ());
    }
    
    public int x() {
        return this.x;
    }
    
    public int y() {
        return this.y;
    }
    
    public int z() {
        return this.z;
    }
    
    @Deprecated
    public int getX() {
        return this.x;
    }
    
    @Deprecated
    public int getY() {
        return this.y;
    }
    
    @Deprecated
    public int getZ() {
        return this.z;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Position position = (Position)o;
        return this.x == position.x && this.y == position.y && this.z == position.z;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * this.x + this.y) + this.z;
    }
    
    @Override
    public String toString() {
        return "Position{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }
}
