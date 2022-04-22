package Mood.Cosmetics.impl;

import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;

public abstract class CosmeticBase implements LayerRenderer
{
    protected final RenderPlayer renderPlayer;
    
    public CosmeticBase(final RenderPlayer renderPlayer) {
        this.renderPlayer = renderPlayer;
    }
    
    public void doRenderLayer(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (abstractClientPlayer.hasPlayerInfo() && !abstractClientPlayer.isInvisible()) {
            this.render(abstractClientPlayer, n, n2, n3, n4, n5, n6, n7);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    public abstract void render(final AbstractClientPlayer p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7);
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((AbstractClientPlayer)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
}
