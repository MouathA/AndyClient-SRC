package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;

public class LayerDeadmau5Head implements LayerRenderer
{
    private final RenderPlayer field_177208_a;
    private static final String __OBFID;
    
    public LayerDeadmau5Head(final RenderPlayer field_177208_a) {
        this.field_177208_a = field_177208_a;
    }
    
    public void func_177207_a(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (abstractClientPlayer.getName().equals("deadmau5") && abstractClientPlayer.hasSkin() && !abstractClientPlayer.isInvisible()) {
            this.field_177208_a.bindTexture(abstractClientPlayer.getLocationSkin());
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.func_177207_a((AbstractClientPlayer)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    static {
        __OBFID = "CL_00002421";
    }
}
