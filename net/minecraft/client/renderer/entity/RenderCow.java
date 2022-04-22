package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class RenderCow extends RenderLiving
{
    private static final ResourceLocation cowTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000984";
        cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
    }
    
    public RenderCow(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    protected ResourceLocation func_180572_a(final EntityCow entityCow) {
        return RenderCow.cowTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180572_a((EntityCow)entity);
    }
}
