package net.minecraft.block.material;

public class MaterialTransparent extends Material
{
    private static final String __OBFID;
    
    public MaterialTransparent(final MapColor mapColor) {
        super(mapColor);
        this.setReplaceable();
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
        __OBFID = "CL_00000540";
    }
}
