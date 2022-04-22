package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class RenderMooshroom extends RenderLiving
{
    private static final ResourceLocation mooshroomTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001016";
        mooshroomTextures = new ResourceLocation("textures/entity/cow/mooshroom.png");
    }
    
    public RenderMooshroom(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        this.addLayer(new LayerMooshroomMushroom(this));
    }
    
    protected ResourceLocation func_180582_a(final EntityMooshroom entityMooshroom) {
        return RenderMooshroom.mooshroomTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180582_a((EntityMooshroom)entity);
    }
}
