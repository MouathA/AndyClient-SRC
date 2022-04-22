package net.minecraft.block.material;

public class MaterialPortal extends Material
{
    private static final String __OBFID;
    
    public MaterialPortal(final MapColor mapColor) {
        super(mapColor);
    }
    
    @Override
    public boolean isSolid() {
        return false;
    }
    
    @Override
    public boolean blocksLight() {
        return false;
    }
    
    @Override
    public boolean blocksMovement() {
        return false;
    }
    
    static {
        __OBFID = "CL_00000545";
    }
}
