package net.minecraft.client.resources;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;

public class FoliageColorReloadListener implements IResourceManagerReloadListener
{
    private static final ResourceLocation field_130079_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001077";
        field_130079_a = new ResourceLocation("textures/colormap/foliage.png");
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        ColorizerFoliage.setFoliageBiomeColorizer(TextureUtil.readImageData(resourceManager, FoliageColorReloadListener.field_130079_a));
    }
}
