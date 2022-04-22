package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import com.google.common.collect.*;
import optifine.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public abstract class LayerArmorBase implements LayerRenderer
{
    protected static final ResourceLocation field_177188_b;
    protected ModelBase field_177189_c;
    protected ModelBase field_177186_d;
    private final RendererLivingEntity field_177190_a;
    private float field_177187_e;
    private float field_177184_f;
    private float field_177185_g;
    private float field_177192_h;
    private boolean field_177193_i;
    private static final Map field_177191_j;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002428";
        field_177188_b = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        field_177191_j = Maps.newHashMap();
    }
    
    public LayerArmorBase(final RendererLivingEntity field_177190_a) {
        this.field_177187_e = 1.0f;
        this.field_177184_f = 1.0f;
        this.field_177185_g = 1.0f;
        this.field_177192_h = 1.0f;
        this.field_177190_a = field_177190_a;
        this.func_177177_a();
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.func_177182_a(entityLivingBase, n, n2, n3, n4, n5, n6, n7, 4);
        this.func_177182_a(entityLivingBase, n, n2, n3, n4, n5, n6, n7, 3);
        this.func_177182_a(entityLivingBase, n, n2, n3, n4, n5, n6, n7, 2);
        this.func_177182_a(entityLivingBase, n, n2, n3, n4, n5, n6, n7, 1);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    private void func_177182_a(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final int n8) {
        final ItemStack func_177176_a = this.func_177176_a(entityLivingBase, n8);
        if (func_177176_a != null && func_177176_a.getItem() instanceof ItemArmor) {
            final ItemArmor itemArmor = (ItemArmor)func_177176_a.getItem();
            ModelBase func_177175_a = this.func_177175_a(n8);
            func_177175_a.setModelAttributes(this.field_177190_a.getMainModel());
            func_177175_a.setLivingAnimations(entityLivingBase, n, n2, n3);
            if (Reflector.ForgeHooksClient_getArmorModel.exists()) {
                func_177175_a = (ModelBase)Reflector.call(Reflector.ForgeHooksClient_getArmorModel, entityLivingBase, func_177176_a, n8, func_177175_a);
            }
            this.func_177179_a(func_177175_a, n8);
            final boolean func_177180_b = this.func_177180_b(n8);
            if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(func_177176_a, func_177180_b ? 2 : 1, null)) {
                if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
                    this.field_177190_a.bindTexture(this.getArmorResource(entityLivingBase, func_177176_a, func_177180_b ? 2 : 1, null));
                }
                else {
                    this.field_177190_a.bindTexture(this.func_177181_a(itemArmor, func_177180_b));
                }
            }
            if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
                final int color = itemArmor.getColor(func_177176_a);
                if (color != -1) {
                    GlStateManager.color(this.field_177184_f * ((color >> 16 & 0xFF) / 255.0f), this.field_177185_g * ((color >> 8 & 0xFF) / 255.0f), this.field_177192_h * ((color & 0xFF) / 255.0f), this.field_177187_e);
                    func_177175_a.render(entityLivingBase, n, n2, n4, n5, n6, n7);
                    if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(func_177176_a, func_177180_b ? 2 : 1, "overlay")) {
                        this.field_177190_a.bindTexture(this.getArmorResource(entityLivingBase, func_177176_a, func_177180_b ? 2 : 1, "overlay"));
                    }
                }
                GlStateManager.color(this.field_177184_f, this.field_177185_g, this.field_177192_h, this.field_177187_e);
                func_177175_a.render(entityLivingBase, n, n2, n4, n5, n6, n7);
                if (!this.field_177193_i && func_177176_a.isItemEnchanted() && (!Config.isCustomItems() || !CustomItems.renderCustomArmorEffect(entityLivingBase, func_177176_a, func_177175_a, n, n2, n3, n4, n5, n6, n7))) {
                    this.func_177183_a(entityLivingBase, func_177175_a, n, n2, n3, n4, n5, n6, n7);
                }
                return;
            }
            switch (SwitchArmorMaterial.field_178747_a[itemArmor.getArmorMaterial().ordinal()]) {
                case 1: {
                    final int color2 = itemArmor.getColor(func_177176_a);
                    GlStateManager.color(this.field_177184_f * ((color2 >> 16 & 0xFF) / 255.0f), this.field_177185_g * ((color2 >> 8 & 0xFF) / 255.0f), this.field_177192_h * ((color2 & 0xFF) / 255.0f), this.field_177187_e);
                    func_177175_a.render(entityLivingBase, n, n2, n4, n5, n6, n7);
                    if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(func_177176_a, func_177180_b ? 2 : 1, "overlay")) {
                        this.field_177190_a.bindTexture(this.func_177178_a(itemArmor, func_177180_b, "overlay"));
                    }
                }
                case 2:
                case 3:
                case 4:
                case 5: {
                    GlStateManager.color(this.field_177184_f, this.field_177185_g, this.field_177192_h, this.field_177187_e);
                    func_177175_a.render(entityLivingBase, n, n2, n4, n5, n6, n7);
                    break;
                }
            }
            if (!this.field_177193_i && func_177176_a.isItemEnchanted() && (!Config.isCustomItems() || !CustomItems.renderCustomArmorEffect(entityLivingBase, func_177176_a, func_177175_a, n, n2, n3, n4, n5, n6, n7))) {
                this.func_177183_a(entityLivingBase, func_177175_a, n, n2, n3, n4, n5, n6, n7);
            }
        }
    }
    
    public ItemStack func_177176_a(final EntityLivingBase entityLivingBase, final int n) {
        return entityLivingBase.getCurrentArmor(n - 1);
    }
    
    public ModelBase func_177175_a(final int n) {
        return (this == n) ? this.field_177189_c : this.field_177186_d;
    }
    
    private void func_177183_a(final EntityLivingBase p0, final ModelBase p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            12
        //     6: invokestatic    optifine/CustomItems.isUseGlint:()Z
        //     9: ifeq            210
        //    12: invokestatic    optifine/Config.isShaders:()Z
        //    15: ifeq            25
        //    18: getstatic       shadersmod/client/Shaders.isShadowPass:Z
        //    21: ifeq            25
        //    24: return         
        //    25: aload_1        
        //    26: getfield        net/minecraft/entity/EntityLivingBase.ticksExisted:I
        //    29: i2f            
        //    30: fload           5
        //    32: fadd           
        //    33: fstore          10
        //    35: aload_0        
        //    36: getfield        net/minecraft/client/renderer/entity/layers/LayerArmorBase.field_177190_a:Lnet/minecraft/client/renderer/entity/RendererLivingEntity;
        //    39: getstatic       net/minecraft/client/renderer/entity/layers/LayerArmorBase.field_177188_b:Lnet/minecraft/util/ResourceLocation;
        //    42: invokevirtual   net/minecraft/client/renderer/entity/RendererLivingEntity.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //    45: sipush          514
        //    48: invokestatic    net/minecraft/client/renderer/GlStateManager.depthFunc:(I)V
        //    51: iconst_0       
        //    52: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //    55: ldc             0.5
        //    57: fstore          11
        //    59: fload           11
        //    61: fload           11
        //    63: fload           11
        //    65: fconst_1       
        //    66: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //    69: goto            185
        //    72: sipush          768
        //    75: iconst_1       
        //    76: invokestatic    net/minecraft/client/renderer/GlStateManager.blendFunc:(II)V
        //    79: ldc             0.76
        //    81: fstore          13
        //    83: ldc             0.5
        //    85: fload           13
        //    87: fmul           
        //    88: ldc             0.25
        //    90: fload           13
        //    92: fmul           
        //    93: ldc             0.8
        //    95: fload           13
        //    97: fmul           
        //    98: fconst_1       
        //    99: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //   102: sipush          5890
        //   105: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   108: ldc_w           0.33333334
        //   111: fstore          14
        //   113: fload           14
        //   115: fload           14
        //   117: fload           14
        //   119: invokestatic    net/minecraft/client/renderer/GlStateManager.scale:(FFF)V
        //   122: ldc_w           30.0
        //   125: iconst_0       
        //   126: i2f            
        //   127: ldc_w           60.0
        //   130: fmul           
        //   131: fsub           
        //   132: fconst_0       
        //   133: fconst_0       
        //   134: fconst_1       
        //   135: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   138: fconst_0       
        //   139: fload           10
        //   141: ldc_w           0.001
        //   144: iconst_0       
        //   145: i2f            
        //   146: ldc_w           0.003
        //   149: fmul           
        //   150: fadd           
        //   151: fmul           
        //   152: ldc_w           20.0
        //   155: fmul           
        //   156: fconst_0       
        //   157: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   160: sipush          5888
        //   163: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   166: aload_2        
        //   167: aload_1        
        //   168: fload_3        
        //   169: fload           4
        //   171: fload           6
        //   173: fload           7
        //   175: fload           8
        //   177: fload           9
        //   179: invokevirtual   net/minecraft/client/model/ModelBase.render:(Lnet/minecraft/entity/Entity;FFFFFF)V
        //   182: iinc            12, 1
        //   185: sipush          5890
        //   188: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   191: sipush          5888
        //   194: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   197: iconst_1       
        //   198: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   201: sipush          515
        //   204: invokestatic    net/minecraft/client/renderer/GlStateManager.depthFunc:(I)V
        //   207: invokestatic    optifine/Config.isShaders:()Z
        //   210: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0210 (coming from #0009).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private ResourceLocation func_177181_a(final ItemArmor itemArmor, final boolean b) {
        return this.func_177178_a(itemArmor, b, null);
    }
    
    private ResourceLocation func_177178_a(final ItemArmor itemArmor, final boolean b, final String s) {
        final String format = String.format("textures/models/armor/%s_layer_%d%s.png", itemArmor.getArmorMaterial().func_179242_c(), b ? 2 : 1, (s == null) ? "" : String.format("_%s", s));
        ResourceLocation resourceLocation = LayerArmorBase.field_177191_j.get(format);
        if (resourceLocation == null) {
            resourceLocation = new ResourceLocation(format);
            LayerArmorBase.field_177191_j.put(format, resourceLocation);
        }
        return resourceLocation;
    }
    
    protected abstract void func_177177_a();
    
    protected abstract void func_177179_a(final ModelBase p0, final int p1);
    
    public ResourceLocation getArmorResource(final Entity entity, final ItemStack itemStack, final int n, final String s) {
        final ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
        String s2 = ((ItemArmor)itemStack.getItem()).getArmorMaterial().func_179242_c();
        String substring = "minecraft";
        final int index = s2.indexOf(58);
        if (index != -1) {
            substring = s2.substring(0, index);
            s2 = s2.substring(index + 1);
        }
        final String callString = Reflector.callString(Reflector.ForgeHooksClient_getArmorTexture, entity, itemStack, String.format("%s:textures/models/armor/%s_layer_%d%s.png", substring, s2, (n == 2) ? 2 : 1, (s == null) ? "" : String.format("_%s", s)), n, s);
        ResourceLocation resourceLocation = LayerArmorBase.field_177191_j.get(callString);
        if (resourceLocation == null) {
            resourceLocation = new ResourceLocation(callString);
            LayerArmorBase.field_177191_j.put(callString, resourceLocation);
        }
        return resourceLocation;
    }
    
    static final class SwitchArmorMaterial
    {
        static final int[] field_178747_a;
        private static final String __OBFID;
        private static final String[] lIlIIIIlIllIIIlI;
        private static String[] lIlIIIIlIllIIIll;
        
        static {
            lllIllIllIIIlIll();
            lllIllIllIIIlIlI();
            __OBFID = SwitchArmorMaterial.lIlIIIIlIllIIIlI[0];
            field_178747_a = new int[ItemArmor.ArmorMaterial.values().length];
            try {
                SwitchArmorMaterial.field_178747_a[ItemArmor.ArmorMaterial.LEATHER.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchArmorMaterial.field_178747_a[ItemArmor.ArmorMaterial.CHAIN.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchArmorMaterial.field_178747_a[ItemArmor.ArmorMaterial.IRON.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchArmorMaterial.field_178747_a[ItemArmor.ArmorMaterial.GOLD.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchArmorMaterial.field_178747_a[ItemArmor.ArmorMaterial.DIAMOND.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
        
        private static void lllIllIllIIIlIlI() {
            (lIlIIIIlIllIIIlI = new String[1])[0] = lllIllIllIIIlIIl(SwitchArmorMaterial.lIlIIIIlIllIIIll[0], SwitchArmorMaterial.lIlIIIIlIllIIIll[1]);
            SwitchArmorMaterial.lIlIIIIlIllIIIll = null;
        }
        
        private static void lllIllIllIIIlIll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchArmorMaterial.lIlIIIIlIllIIIll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lllIllIllIIIlIIl(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
                final Cipher instance = Cipher.getInstance("AES");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
