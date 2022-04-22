package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import net.minecraft.tileentity.*;

public class TileEntityBannerRenderer extends TileEntitySpecialRenderer
{
    private static final Map field_178466_c;
    private static final ResourceLocation field_178464_d;
    private ModelBanner field_178465_e;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002473";
        field_178466_c = Maps.newHashMap();
        field_178464_d = new ResourceLocation("textures/entity/banner_base.png");
    }
    
    public TileEntityBannerRenderer() {
        this.field_178465_e = new ModelBanner();
    }
    
    public void func_180545_a(final TileEntityBanner tileEntityBanner, final double n, final double n2, final double n3, final float n4, final int n5) {
        final boolean b = tileEntityBanner.getWorld() != null;
        final boolean b2 = !b || tileEntityBanner.getBlockType() == Blocks.standing_banner;
        final int n6 = b ? tileEntityBanner.getBlockMetadata() : 0;
        final long n7 = b ? tileEntityBanner.getWorld().getTotalWorldTime() : 0L;
        final float n8 = 0.6666667f;
        if (b2) {
            GlStateManager.translate((float)n + 0.5f, (float)n2 + 0.75f * n8, (float)n3 + 0.5f);
            GlStateManager.rotate(-(n6 * 360 / 16.0f), 0.0f, 1.0f, 0.0f);
            this.field_178465_e.bannerStand.showModel = true;
        }
        else {
            float n9 = 0.0f;
            if (n6 == 2) {
                n9 = 180.0f;
            }
            if (n6 == 4) {
                n9 = 90.0f;
            }
            if (n6 == 5) {
                n9 = -90.0f;
            }
            GlStateManager.translate((float)n + 0.5f, (float)n2 - 0.25f * n8, (float)n3 + 0.5f);
            GlStateManager.rotate(-n9, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.3125f, -0.4375f);
            this.field_178465_e.bannerStand.showModel = false;
        }
        final BlockPos pos = tileEntityBanner.getPos();
        this.field_178465_e.bannerSlate.rotateAngleX = (-0.0125f + 0.01f * MathHelper.cos((pos.getX() * 7 + pos.getY() * 9 + pos.getZ() * 13 + (float)n7 + n4) * 3.1415927f * 0.02f)) * 3.1415927f;
        final ResourceLocation func_178463_a = this.func_178463_a(tileEntityBanner);
        if (func_178463_a != null) {
            this.bindTexture(func_178463_a);
            GlStateManager.scale(n8, -n8, -n8);
            this.field_178465_e.func_178687_a();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private ResourceLocation func_178463_a(final TileEntityBanner tileEntityBanner) {
        final String func_175116_e = tileEntityBanner.func_175116_e();
        if (func_175116_e.isEmpty()) {
            return null;
        }
        TimedBannerTexture timedBannerTexture = TileEntityBannerRenderer.field_178466_c.get(func_175116_e);
        if (timedBannerTexture == null) {
            if (TileEntityBannerRenderer.field_178466_c.size() >= 256) {
                final long currentTimeMillis = System.currentTimeMillis();
                final Iterator<String> iterator = TileEntityBannerRenderer.field_178466_c.keySet().iterator();
                while (iterator.hasNext()) {
                    final TimedBannerTexture timedBannerTexture2 = TileEntityBannerRenderer.field_178466_c.get(iterator.next());
                    if (currentTimeMillis - timedBannerTexture2.field_178472_a > 60000L) {
                        Minecraft.getMinecraft().getTextureManager().deleteTexture(timedBannerTexture2.field_178471_b);
                        iterator.remove();
                    }
                }
                if (TileEntityBannerRenderer.field_178466_c.size() >= 256) {
                    return null;
                }
            }
            final List func_175114_c = tileEntityBanner.func_175114_c();
            final List func_175110_d = tileEntityBanner.func_175110_d();
            final ArrayList arrayList = Lists.newArrayList();
            final Iterator<TileEntityBanner.EnumBannerPattern> iterator2 = func_175114_c.iterator();
            while (iterator2.hasNext()) {
                arrayList.add("textures/entity/banner/" + iterator2.next().func_177271_a() + ".png");
            }
            timedBannerTexture = new TimedBannerTexture(null);
            timedBannerTexture.field_178471_b = new ResourceLocation(func_175116_e);
            Minecraft.getMinecraft().getTextureManager().loadTexture(timedBannerTexture.field_178471_b, new LayeredColorMaskTexture(TileEntityBannerRenderer.field_178464_d, arrayList, func_175110_d));
            TileEntityBannerRenderer.field_178466_c.put(func_175116_e, timedBannerTexture);
        }
        timedBannerTexture.field_178472_a = System.currentTimeMillis();
        return timedBannerTexture.field_178471_b;
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.func_180545_a((TileEntityBanner)tileEntity, n, n2, n3, n4, n5);
    }
    
    static class TimedBannerTexture
    {
        public long field_178472_a;
        public ResourceLocation field_178471_b;
        private static final String __OBFID;
        
        private TimedBannerTexture() {
        }
        
        TimedBannerTexture(final Object o) {
            this();
        }
        
        static {
            __OBFID = "CL_00002471";
        }
    }
}
