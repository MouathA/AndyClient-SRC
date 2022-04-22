package net.minecraft.client.renderer.entity;

import net.minecraft.entity.effect.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RenderLightningBolt extends Render
{
    private static final String __OBFID;
    
    public RenderLightningBolt(final RenderManager renderManager) {
        super(renderManager);
    }
    
    public void doRender(final EntityLightningBolt entityLightningBolt, final double n, final double n2, final double n3, final float n4, final float n5) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.blendFunc(770, 1);
        final double[] array = new double[8];
        final double[] array2 = new double[8];
        double n6 = 0.0;
        double n7 = 0.0;
        final Random random = new Random(entityLightningBolt.boltVertex);
        int n8 = 0;
        while (0 >= 0) {
            array[0] = n6;
            array2[0] = n7;
            n6 += random.nextInt(11) - 5;
            n7 += random.nextInt(11) - 5;
            --n8;
        }
        while (0 < 4) {
            final Random random2 = new Random(entityLightningBolt.boltVertex);
            while (0 < 3) {
                if (0 > 0) {}
                if (0 > 0) {}
                double n9 = array[7] - n6;
                double n10 = array2[7] - n7;
                while (7 >= 0) {
                    final double n11 = n9;
                    final double n12 = n10;
                    if (!false) {
                        n9 += random2.nextInt(11) - 5;
                        n10 += random2.nextInt(11) - 5;
                    }
                    else {
                        n9 += random2.nextInt(31) - 15;
                        n10 += random2.nextInt(31) - 15;
                    }
                    worldRenderer.startDrawing(5);
                    final float n13 = 0.5f;
                    worldRenderer.func_178960_a(0.9f * n13, 0.9f * n13, 1.0f * n13, 0.3f);
                    double n14 = 0.1 + 0 * 0.2;
                    if (!false) {
                        n14 *= 7 * 0.1 + 1.0;
                    }
                    double n15 = 0.1 + 0 * 0.2;
                    if (!false) {
                        n15 *= 6 * 0.1 + 1.0;
                    }
                    while (0 < 5) {
                        double n16 = n + 0.5 - n14;
                        double n17 = n3 + 0.5 - n14;
                        if (false == true || 0 == 2) {
                            n16 += n14 * 2.0;
                        }
                        if (0 == 2 || 0 == 3) {
                            n17 += n14 * 2.0;
                        }
                        double n18 = n + 0.5 - n15;
                        double n19 = n3 + 0.5 - n15;
                        if (false == true || 0 == 2) {
                            n18 += n15 * 2.0;
                        }
                        if (0 == 2 || 0 == 3) {
                            n19 += n15 * 2.0;
                        }
                        worldRenderer.addVertex(n18 + n9, n2 + 112, n19 + n10);
                        worldRenderer.addVertex(n16 + n11, n2 + 128, n17 + n12);
                        int n20 = 0;
                        ++n20;
                    }
                    instance.draw();
                    int n21 = 0;
                    --n21;
                }
                int n22 = 0;
                ++n22;
            }
            ++n8;
        }
    }
    
    protected ResourceLocation getEntityTexture(final EntityLightningBolt entityLightningBolt) {
        return null;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityLightningBolt)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLightningBolt)entity, n, n2, n3, n4, n5);
    }
    
    static {
        __OBFID = "CL_00001011";
    }
}
