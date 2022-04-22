package net.minecraft.dispenser;

public class PositionImpl implements IPosition
{
    protected final double x;
    protected final double y;
    protected final double z;
    private static final String __OBFID;
    
    public PositionImpl(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public double getX() {
        return this.x;
    }
    
    @Override
    public double getY() {
        return this.y;
    }
    
    @Override
    public double getZ() {
        return this.z;
    }
    
    static {
        __OBFID = "CL_00001208";
    }
}
