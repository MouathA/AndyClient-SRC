package net.minecraft.client.resources;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;

public class GrassColorReloadListener implements IResourceManagerReloadListener
{
    private static final ResourceLocation field_130078_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001078";
        field_130078_a = new ResourceLocation("textures/colormap/grass.png");
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        ColorizerGrass.setGrassBiomeColorizer(TextureUtil.readImageData(resourceManager, GrassColorReloadListener.field_130078_a));
    }
}
