package net.minecraft.client.gui;

import com.google.common.collect.*;
import net.minecraft.world.storage.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class MapItemRenderer
{
    private static final ResourceLocation mapIcons;
    private final TextureManager textureManager;
    private final Map loadedMaps;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000663";
        mapIcons = new ResourceLocation("textures/map/map_icons.png");
    }
    
    public MapItemRenderer(final TextureManager textureManager) {
        this.loadedMaps = Maps.newHashMap();
        this.textureManager = textureManager;
    }
    
    public void func_148246_a(final MapData mapData) {
        Instance.access$0(this.func_148248_b(mapData));
    }
    
    public void func_148250_a(final MapData mapData, final boolean b) {
        Instance.access$1(this.func_148248_b(mapData), b);
    }
    
    private Instance func_148248_b(final MapData mapData) {
        Instance instance = this.loadedMaps.get(mapData.mapName);
        if (instance == null) {
            instance = new Instance(mapData, null);
            this.loadedMaps.put(mapData.mapName, instance);
        }
        return instance;
    }
    
    public void func_148249_a() {
        final Iterator<Instance> iterator = this.loadedMaps.values().iterator();
        while (iterator.hasNext()) {
            this.textureManager.deleteTexture(Instance.access$2(iterator.next()));
        }
        this.loadedMaps.clear();
    }
    
    static TextureManager access$0(final MapItemRenderer mapItemRenderer) {
        return mapItemRenderer.textureManager;
    }
    
    static ResourceLocation access$1() {
        return MapItemRenderer.mapIcons;
    }
    
    class Instance
    {
        private final MapData field_148242_b;
        private final DynamicTexture field_148243_c;
        private final ResourceLocation field_148240_d;
        private final int[] field_148241_e;
        private static final String __OBFID;
        final MapItemRenderer this$0;
        
        private Instance(final MapItemRenderer this$0, final MapData field_148242_b) {
            this.this$0 = this$0;
            this.field_148242_b = field_148242_b;
            this.field_148243_c = new DynamicTexture(128, 128);
            this.field_148241_e = this.field_148243_c.getTextureData();
            this.field_148240_d = MapItemRenderer.access$0(this$0).getDynamicTextureLocation("map/" + field_148242_b.mapName, this.field_148243_c);
            while (0 < this.field_148241_e.length) {
                this.field_148241_e[0] = 0;
                int n = 0;
                ++n;
            }
        }
        
        private void func_148236_a() {
            while (0 < 16384) {
                final int n = this.field_148242_b.colors[0] & 0xFF;
                if (n / 4 == 0) {
                    this.field_148241_e[0] = 268435456;
                }
                else {
                    this.field_148241_e[0] = MapColor.mapColorArray[n / 4].func_151643_b(n & 0x3);
                }
                int n2 = 0;
                ++n2;
            }
            this.field_148243_c.updateDynamicTexture();
        }
        
        private void func_148237_a(final boolean b) {
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final float n = 0.0f;
            MapItemRenderer.access$0(this.this$0).bindTexture(this.field_148240_d);
            GlStateManager.tryBlendFuncSeparate(1, 771, 0, 1);
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertexWithUV(0 + n, 128 - n, -0.009999999776482582, 0.0, 1.0);
            worldRenderer.addVertexWithUV(128 - n, 128 - n, -0.009999999776482582, 1.0, 1.0);
            worldRenderer.addVertexWithUV(128 - n, 0 + n, -0.009999999776482582, 1.0, 0.0);
            worldRenderer.addVertexWithUV(0 + n, 0 + n, -0.009999999776482582, 0.0, 0.0);
            instance.draw();
            MapItemRenderer.access$0(this.this$0).bindTexture(MapItemRenderer.access$1());
            for (final Vec4b vec4b : this.field_148242_b.playersVisibleOnMap.values()) {
                if (!b || vec4b.func_176110_a() == 1) {
                    GlStateManager.translate(0 + vec4b.func_176112_b() / 2.0f + 64.0f, 0 + vec4b.func_176113_c() / 2.0f + 64.0f, -0.02f);
                    GlStateManager.rotate(vec4b.func_176111_d() * 360 / 16.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.scale(4.0f, 4.0f, 3.0f);
                    GlStateManager.translate(-0.125f, 0.125f, 0.0f);
                    final byte func_176110_a = vec4b.func_176110_a();
                    final float n2 = (func_176110_a % 4 + 0) / 4.0f;
                    final float n3 = (func_176110_a / 4 + 0) / 4.0f;
                    final float n4 = (func_176110_a % 4 + 1) / 4.0f;
                    final float n5 = (func_176110_a / 4 + 1) / 4.0f;
                    worldRenderer.startDrawingQuads();
                    worldRenderer.addVertexWithUV(-1.0, 1.0, 0 * 0.001f, n2, n3);
                    worldRenderer.addVertexWithUV(1.0, 1.0, 0 * 0.001f, n4, n3);
                    worldRenderer.addVertexWithUV(1.0, -1.0, 0 * 0.001f, n4, n5);
                    worldRenderer.addVertexWithUV(-1.0, -1.0, 0 * 0.001f, n2, n5);
                    instance.draw();
                    int n6 = 0;
                    ++n6;
                }
            }
            GlStateManager.translate(0.0f, 0.0f, -0.04f);
            GlStateManager.scale(1.0f, 1.0f, 1.0f);
        }
        
        Instance(final MapItemRenderer mapItemRenderer, final MapData mapData, final Object o) {
            this(mapItemRenderer, mapData);
        }
        
        static void access$0(final Instance instance) {
            instance.func_148236_a();
        }
        
        static void access$1(final Instance instance, final boolean b) {
            instance.func_148237_a(b);
        }
        
        static ResourceLocation access$2(final Instance instance) {
            return instance.field_148240_d;
        }
        
        static {
            __OBFID = "CL_00000665";
        }
    }
}
