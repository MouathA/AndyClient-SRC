package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class RenderPig extends RenderLiving
{
    private static final ResourceLocation pigTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001019";
        pigTextures = new ResourceLocation("textures/entity/pig/pig.png");
    }
    
    public RenderPig(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        this.addLayer(new LayerSaddle(this));
    }
    
    protected ResourceLocation func_180583_a(final EntityPig entityPig) {
        return RenderPig.pigTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180583_a((EntityPig)entity);
    }
}
