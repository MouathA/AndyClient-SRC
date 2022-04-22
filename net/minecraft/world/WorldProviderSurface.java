package net.minecraft.world;

public class WorldProviderSurface extends WorldProvider
{
    private static final String __OBFID;
    
    @Override
    public String getDimensionName() {
        return "Overworld";
    }
    
    @Override
    public String getInternalNameSuffix() {
        return "";
    }
    
    static {
        __OBFID = "CL_00000388";
    }
}
