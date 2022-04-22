package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RenderEnderCrystal extends Render
{
    private static final ResourceLocation enderCrystalTextures;
    private ModelBase field_76995_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000987";
        enderCrystalTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
    }
    
    public RenderEnderCrystal(final RenderManager renderManager) {
        super(renderManager);
        this.field_76995_b = new ModelEnderCrystal(0.0f, true);
        this.shadowSize = 0.5f;
    }
    
    public void doRender(final EntityEnderCrystal entityEnderCrystal, final double n, final double n2, final double n3, final float n4, final float n5) {
        final float n6 = entityEnderCrystal.innerRotation + n5;
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        this.bindTexture(RenderEnderCrystal.enderCrystalTextures);
        final float n7 = MathHelper.sin(n6 * 0.2f) / 2.0f + 0.5f;
        this.field_76995_b.render(entityEnderCrystal, 0.0f, n6 * 3.0f, (n7 + n7 * n7) * 0.2f, 0.0f, 0.0f, 0.0625f);
        super.doRender(entityEnderCrystal, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation func_180554_a(final EntityEnderCrystal entityEnderCrystal) {
        return RenderEnderCrystal.enderCrystalTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180554_a((EntityEnderCrystal)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityEnderCrystal)entity, n, n2, n3, n4, n5);
    }
}
