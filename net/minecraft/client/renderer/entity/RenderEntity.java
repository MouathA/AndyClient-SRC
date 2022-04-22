package net.minecraft.client.renderer.entity;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class RenderEntity extends Render
{
    private static final String __OBFID;
    
    public RenderEntity(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        Render.renderOffsetAABB(entity.getEntityBoundingBox(), n - entity.lastTickPosX, n2 - entity.lastTickPosY, n3 - entity.lastTickPosZ);
        super.doRender(entity, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return null;
    }
    
    static {
        __OBFID = "CL_00000986";
    }
}
