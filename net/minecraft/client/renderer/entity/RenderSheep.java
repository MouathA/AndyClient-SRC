package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class RenderSheep extends RenderLiving
{
    private static final ResourceLocation shearedSheepTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001021";
        shearedSheepTextures = new ResourceLocation("textures/entity/sheep/sheep.png");
    }
    
    public RenderSheep(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        this.addLayer(new LayerSheepWool(this));
    }
    
    protected ResourceLocation getEntityTexture(final EntitySheep entitySheep) {
        return RenderSheep.shearedSheepTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySheep)entity);
    }
}
