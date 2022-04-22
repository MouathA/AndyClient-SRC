package net.minecraft.block.material;

public class MaterialLogic extends Material
{
    private static final String __OBFID;
    
    public MaterialLogic(final MapColor mapColor) {
        super(mapColor);
        this.setAdventureModeExempt();
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
        __OBFID = "CL_00000539";
    }
}
