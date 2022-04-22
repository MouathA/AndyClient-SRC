package net.minecraft.block.material;

public class MaterialLiquid extends Material
{
    private static final String __OBFID;
    
    public MaterialLiquid(final MapColor mapColor) {
        super(mapColor);
        this.setReplaceable();
        this.setNoPushMobility();
    }
    
    @Override
    public boolean isLiquid() {
        return true;
    }
    
    @Override
    public boolean blocksMovement() {
        return false;
    }
    
    @Override
    public boolean isSolid() {
        return false;
    }
    
    static {
        __OBFID = "CL_00000541";
    }
}
