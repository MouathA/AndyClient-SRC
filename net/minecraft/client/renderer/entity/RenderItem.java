package net.minecraft.client.renderer.entity;

import net.minecraft.client.resources.model.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.util.*;
import optifine.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.gui.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.client.resources.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class RenderItem implements IResourceManagerReloadListener
{
    private static final ResourceLocation RES_ITEM_GLINT;
    private boolean field_175058_l;
    public float zLevel;
    private final ItemModelMesher itemModelMesher;
    private final TextureManager field_175057_n;
    private static final String __OBFID;
    private ModelResourceLocation modelLocation;
    
    static {
        __OBFID = "CL_00001003";
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
    
    public RenderItem(final TextureManager field_175057_n, final ModelManager modelManager) {
        this.field_175058_l = true;
        this.modelLocation = null;
        this.field_175057_n = field_175057_n;
        Config.setModelManager(modelManager);
        if (Reflector.ItemModelMesherForge_Constructor.exists()) {
            this.itemModelMesher = (ItemModelMesher)Reflector.newInstance(Reflector.ItemModelMesherForge_Constructor, modelManager);
        }
        else {
            this.itemModelMesher = new ItemModelMesher(modelManager);
        }
        this.registerItems();
    }
    
    public void func_175039_a(final boolean field_175058_l) {
        this.field_175058_l = field_175058_l;
    }
    
    public ItemModelMesher getItemModelMesher() {
        return this.itemModelMesher;
    }
    
    protected void registerItem(final Item item, final int n, final String s) {
        this.itemModelMesher.register(item, n, new ModelResourceLocation(s, "inventory"));
    }
    
    protected void registerBlock(final Block block, final int n, final String s) {
        this.registerItem(Item.getItemFromBlock(block), n, s);
    }
    
    private void registerBlock(final Block block, final String s) {
        this.registerBlock(block, 0, s);
    }
    
    private void registerItem(final Item item, final String s) {
        this.registerItem(item, 0, s);
    }
    
    private void func_175036_a(final IBakedModel bakedModel, final ItemStack itemStack) {
        this.func_175045_a(bakedModel, -1, itemStack);
    }
    
    public void func_175035_a(final IBakedModel bakedModel, final int n) {
        this.func_175045_a(bakedModel, n, null);
    }
    
    private void func_175045_a(final IBakedModel bakedModel, final int n, final ItemStack itemStack) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final boolean textureBound = Minecraft.getMinecraft().getTextureMapBlocks().isTextureBound();
        final boolean b = Config.isMultiTexture() && textureBound;
        if (b) {
            worldRenderer.setBlockLayer(EnumWorldBlockLayer.SOLID);
        }
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexFormat(DefaultVertexFormats.field_176599_b);
        final EnumFacing[] values = EnumFacing.VALUES;
        while (0 < values.length) {
            this.func_175032_a(worldRenderer, bakedModel.func_177551_a(values[0]), n, itemStack);
            int n2 = 0;
            ++n2;
        }
        this.func_175032_a(worldRenderer, bakedModel.func_177550_a(), n, itemStack);
        instance.draw();
        if (b) {
            worldRenderer.setBlockLayer(null);
        }
    }
    
    public void func_180454_a(final ItemStack itemStack, IBakedModel customItemModel) {
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        if (customItemModel.isBuiltInRenderer()) {
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, -0.5f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            TileEntityRendererChestHelper.instance.renderByItem(itemStack);
        }
        else {
            if (Config.isCustomItems()) {
                customItemModel = CustomItems.getCustomItemModel(itemStack, customItemModel, this.modelLocation);
            }
            GlStateManager.translate(-0.5f, -0.5f, -0.5f);
            this.func_175036_a(customItemModel, itemStack);
            if (itemStack.hasEffect() && (!Config.isCustomItems() || !CustomItems.renderCustomEffect(this, itemStack, customItemModel))) {
                this.renderEffect(customItemModel);
            }
        }
    }
    
    private void renderEffect(final IBakedModel bakedModel) {
        if (!Config.isCustomItems() || CustomItems.isUseGlint()) {
            GlStateManager.depthMask(false);
            GlStateManager.depthFunc(514);
            GlStateManager.blendFunc(768, 1);
            this.field_175057_n.bindTexture(RenderItem.RES_ITEM_GLINT);
            GlStateManager.matrixMode(5890);
            GlStateManager.scale(8.0f, 8.0f, 8.0f);
            GlStateManager.translate(Minecraft.getSystemTime() % 3000L / 3000.0f / 8.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-50.0f, 0.0f, 0.0f, 1.0f);
            this.func_175035_a(bakedModel, -8372020);
            GlStateManager.scale(8.0f, 8.0f, 8.0f);
            GlStateManager.translate(-(Minecraft.getSystemTime() % 4873L / 4873.0f / 8.0f), 0.0f, 0.0f);
            GlStateManager.rotate(10.0f, 0.0f, 0.0f, 1.0f);
            this.func_175035_a(bakedModel, -8372020);
            GlStateManager.matrixMode(5888);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            this.field_175057_n.bindTexture(TextureMap.locationBlocksTexture);
        }
    }
    
    private void func_175038_a(final WorldRenderer worldRenderer, final BakedQuad bakedQuad) {
        final Vec3i directionVec = bakedQuad.getFace().getDirectionVec();
        worldRenderer.func_178975_e((float)directionVec.getX(), (float)directionVec.getY(), (float)directionVec.getZ());
    }
    
    private void func_175033_a(final WorldRenderer worldRenderer, final BakedQuad bakedQuad, final int n) {
        if (worldRenderer.isMultiTexture()) {
            worldRenderer.func_178981_a(bakedQuad.getVertexDataSingle());
            worldRenderer.putSprite(bakedQuad.getSprite());
        }
        else {
            worldRenderer.func_178981_a(bakedQuad.func_178209_a());
        }
        if (Reflector.IColoredBakedQuad.exists() && Reflector.IColoredBakedQuad.isInstance(bakedQuad)) {
            forgeHooksClient_putQuadColor(worldRenderer, bakedQuad, n);
        }
        else {
            worldRenderer.func_178968_d(n);
        }
        this.func_175038_a(worldRenderer, bakedQuad);
    }
    
    private void func_175032_a(final WorldRenderer worldRenderer, final List list, final int n, final ItemStack itemStack) {
        final boolean b = n == -1 && itemStack != null;
        for (final BakedQuad bakedQuad : list) {
            int n2 = n;
            if (b && bakedQuad.func_178212_b()) {
                int n3 = itemStack.getItem().getColorFromItemStack(itemStack, bakedQuad.func_178211_c());
                if (Config.isCustomColors()) {
                    n3 = CustomColors.getColorFromItemStack(itemStack, bakedQuad.func_178211_c(), n3);
                }
                if (EntityRenderer.anaglyphEnable) {
                    n3 = TextureUtil.func_177054_c(n3);
                }
                n2 = (n3 | 0xFF000000);
            }
            this.func_175033_a(worldRenderer, bakedQuad, n2);
        }
    }
    
    public boolean func_175050_a(final ItemStack itemStack) {
        final IBakedModel itemModel = this.itemModelMesher.getItemModel(itemStack);
        return itemModel != null && itemModel.isAmbientOcclusionEnabled();
    }
    
    private void func_175046_c(final ItemStack itemStack) {
        final IBakedModel itemModel = this.itemModelMesher.getItemModel(itemStack);
        if (itemStack.getItem() != null) {
            if (!itemModel.isAmbientOcclusionEnabled()) {
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public void func_175043_b(final ItemStack itemStack) {
        this.func_175040_a(itemStack, this.itemModelMesher.getItemModel(itemStack), ItemCameraTransforms.TransformType.NONE);
    }
    
    public void func_175049_a(final ItemStack itemStack, final EntityLivingBase entityLivingBase, final ItemCameraTransforms.TransformType transformType) {
        IBakedModel bakedModel = this.itemModelMesher.getItemModel(itemStack);
        if (entityLivingBase instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
            final Item item = itemStack.getItem();
            ModelResourceLocation modelLocation = null;
            if (item == Items.fishing_rod && entityPlayer.fishEntity != null) {
                modelLocation = new ModelResourceLocation("fishing_rod_cast", "inventory");
            }
            else if (item == Items.bow && entityPlayer.getItemInUse() != null) {
                final int n = itemStack.getMaxItemUseDuration() - entityPlayer.getItemInUseCount();
                if (n >= 18) {
                    modelLocation = new ModelResourceLocation("bow_pulling_2", "inventory");
                }
                else if (n > 13) {
                    modelLocation = new ModelResourceLocation("bow_pulling_1", "inventory");
                }
                else if (n > 0) {
                    modelLocation = new ModelResourceLocation("bow_pulling_0", "inventory");
                }
            }
            else if (Reflector.ForgeItem_getModel.exists()) {
                modelLocation = (ModelResourceLocation)Reflector.call(item, Reflector.ForgeItem_getModel, itemStack, entityPlayer, entityPlayer.getItemInUseCount());
            }
            if ((this.modelLocation = modelLocation) != null) {
                bakedModel = this.itemModelMesher.getModelManager().getModel(modelLocation);
            }
        }
        this.func_175040_a(itemStack, bakedModel, transformType);
        this.modelLocation = null;
    }
    
    protected void func_175034_a(final ItemTransformVec3f itemTransformVec3f) {
        applyVanillaTransform(itemTransformVec3f);
    }
    
    public static void applyVanillaTransform(final ItemTransformVec3f itemTransformVec3f) {
        if (itemTransformVec3f != ItemTransformVec3f.field_178366_a) {
            GlStateManager.translate(itemTransformVec3f.field_178365_c.x + 0.0f, itemTransformVec3f.field_178365_c.y + 0.0f, itemTransformVec3f.field_178365_c.z + 0.0f);
            GlStateManager.rotate(itemTransformVec3f.field_178364_b.y + 0.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(itemTransformVec3f.field_178364_b.x + 0.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(itemTransformVec3f.field_178364_b.z + 0.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.scale(itemTransformVec3f.field_178363_d.x + 0.0f, itemTransformVec3f.field_178363_d.y + 0.0f, itemTransformVec3f.field_178363_d.z + 0.0f);
        }
    }
    
    protected void func_175040_a(final ItemStack itemStack, IBakedModel bakedModel, final ItemCameraTransforms.TransformType transformType) {
        this.field_175057_n.bindTexture(TextureMap.locationBlocksTexture);
        this.field_175057_n.getTexture(TextureMap.locationBlocksTexture).func_174936_b(false, false);
        this.func_175046_c(itemStack);
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
            bakedModel = (IBakedModel)Reflector.call(Reflector.ForgeHooksClient_handleCameraTransforms, bakedModel, transformType);
        }
        else {
            switch (SwitchTransformType.field_178640_a[transformType.ordinal()]) {
                case 2: {
                    this.func_175034_a(bakedModel.getItemCameraTransforms().field_178355_b);
                    break;
                }
                case 3: {
                    this.func_175034_a(bakedModel.getItemCameraTransforms().field_178356_c);
                    break;
                }
                case 4: {
                    this.func_175034_a(bakedModel.getItemCameraTransforms().field_178353_d);
                    break;
                }
                case 5: {
                    this.func_175034_a(bakedModel.getItemCameraTransforms().field_178354_e);
                    break;
                }
            }
        }
        this.func_180454_a(itemStack, bakedModel);
        this.field_175057_n.bindTexture(TextureMap.locationBlocksTexture);
        this.field_175057_n.getTexture(TextureMap.locationBlocksTexture).func_174935_a();
    }
    
    public void func_175042_a(final ItemStack itemStack, final int n, final int n2) {
        IBakedModel itemModel = this.itemModelMesher.getItemModel(itemStack);
        this.field_175057_n.bindTexture(TextureMap.locationBlocksTexture);
        this.field_175057_n.getTexture(TextureMap.locationBlocksTexture).func_174936_b(false, false);
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.func_180452_a(n, n2, itemModel.isAmbientOcclusionEnabled());
        if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
            itemModel = (IBakedModel)Reflector.call(Reflector.ForgeHooksClient_handleCameraTransforms, itemModel, ItemCameraTransforms.TransformType.GUI);
        }
        else {
            this.func_175034_a(itemModel.getItemCameraTransforms().field_178354_e);
        }
        this.func_180454_a(itemStack, itemModel);
        this.field_175057_n.bindTexture(TextureMap.locationBlocksTexture);
        this.field_175057_n.getTexture(TextureMap.locationBlocksTexture).func_174935_a();
    }
    
    private void func_180452_a(final int n, final int n2, final boolean b) {
        GlStateManager.translate((float)n, (float)n2, 100.0f + this.zLevel);
        GlStateManager.translate(8.0f, 8.0f, 0.0f);
        GlStateManager.scale(1.0f, 1.0f, -1.0f);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        if (b) {
            GlStateManager.scale(40.0f, 40.0f, 40.0f);
            GlStateManager.rotate(210.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        }
        else {
            GlStateManager.scale(64.0f, 64.0f, 64.0f);
            GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        }
    }
    
    public void func_180450_b(final ItemStack itemStack, final int n, final int n2) {
        if (itemStack != null) {
            this.zLevel += 50.0f;
            this.func_175042_a(itemStack, n, n2);
            this.zLevel -= 50.0f;
        }
    }
    
    public void func_175030_a(final FontRenderer fontRenderer, final ItemStack itemStack, final int n, final int n2) {
        this.func_180453_a(fontRenderer, itemStack, n, n2, null);
    }
    
    public void func_180453_a(final FontRenderer p0, final ItemStack p1, final int p2, final int p3, final String p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnull          368
        //     4: aload_2        
        //     5: getfield        net/minecraft/item/ItemStack.stackSize:I
        //     8: iconst_1       
        //     9: if_icmpne       17
        //    12: aload           5
        //    14: ifnull          109
        //    17: aload           5
        //    19: ifnonnull       32
        //    22: aload_2        
        //    23: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    26: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //    29: goto            34
        //    32: aload           5
        //    34: astore          6
        //    36: aload           5
        //    38: ifnonnull       77
        //    41: aload_2        
        //    42: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    45: iconst_1       
        //    46: if_icmpge       77
        //    49: new             Ljava/lang/StringBuilder;
        //    52: dup            
        //    53: invokespecial   java/lang/StringBuilder.<init>:()V
        //    56: getstatic       net/minecraft/util/EnumChatFormatting.RED:Lnet/minecraft/util/EnumChatFormatting;
        //    59: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    62: aload_2        
        //    63: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    66: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //    69: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    72: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    75: astore          6
        //    77: aload_1        
        //    78: aload           6
        //    80: iload_3        
        //    81: bipush          19
        //    83: iadd           
        //    84: iconst_2       
        //    85: isub           
        //    86: aload_1        
        //    87: aload           6
        //    89: invokevirtual   net/minecraft/client/gui/FontRenderer.getStringWidth:(Ljava/lang/String;)I
        //    92: isub           
        //    93: i2f            
        //    94: iload           4
        //    96: bipush          6
        //    98: iadd           
        //    99: iconst_3       
        //   100: iadd           
        //   101: i2f            
        //   102: ldc_w           16777215
        //   105: invokevirtual   net/minecraft/client/gui/FontRenderer.func_175063_a:(Ljava/lang/String;FFI)I
        //   108: pop            
        //   109: aload_2        
        //   110: invokevirtual   net/minecraft/item/ItemStack.isItemDamaged:()Z
        //   113: istore          6
        //   115: getstatic       optifine/Reflector.ForgeItem_showDurabilityBar:Loptifine/ReflectorMethod;
        //   118: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //   121: ifeq            144
        //   124: aload_2        
        //   125: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   128: getstatic       optifine/Reflector.ForgeItem_showDurabilityBar:Loptifine/ReflectorMethod;
        //   131: iconst_1       
        //   132: anewarray       Ljava/lang/Object;
        //   135: dup            
        //   136: iconst_0       
        //   137: aload_2        
        //   138: aastore        
        //   139: invokestatic    optifine/Reflector.callBoolean:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
        //   142: istore          6
        //   144: iload           6
        //   146: ifeq            368
        //   149: ldc2_w          13.0
        //   152: aload_2        
        //   153: invokevirtual   net/minecraft/item/ItemStack.getItemDamage:()I
        //   156: i2d            
        //   157: ldc2_w          13.0
        //   160: dmul           
        //   161: aload_2        
        //   162: invokevirtual   net/minecraft/item/ItemStack.getMaxDamage:()I
        //   165: i2d            
        //   166: ddiv           
        //   167: dsub           
        //   168: invokestatic    java/lang/Math.round:(D)J
        //   171: l2i            
        //   172: istore          7
        //   174: ldc2_w          255.0
        //   177: aload_2        
        //   178: invokevirtual   net/minecraft/item/ItemStack.getItemDamage:()I
        //   181: i2d            
        //   182: ldc2_w          255.0
        //   185: dmul           
        //   186: aload_2        
        //   187: invokevirtual   net/minecraft/item/ItemStack.getMaxDamage:()I
        //   190: i2d            
        //   191: ddiv           
        //   192: dsub           
        //   193: invokestatic    java/lang/Math.round:(D)J
        //   196: l2i            
        //   197: istore          8
        //   199: getstatic       optifine/Reflector.ForgeItem_getDurabilityForDisplay:Loptifine/ReflectorMethod;
        //   202: invokevirtual   optifine/ReflectorMethod.exists:()Z
        //   205: ifeq            260
        //   208: aload_2        
        //   209: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   212: getstatic       optifine/Reflector.ForgeItem_getDurabilityForDisplay:Loptifine/ReflectorMethod;
        //   215: iconst_1       
        //   216: anewarray       Ljava/lang/Object;
        //   219: dup            
        //   220: iconst_0       
        //   221: aload_2        
        //   222: aastore        
        //   223: invokestatic    optifine/Reflector.callDouble:(Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)D
        //   226: dstore          9
        //   228: ldc2_w          13.0
        //   231: dload           9
        //   233: ldc2_w          13.0
        //   236: dmul           
        //   237: dsub           
        //   238: invokestatic    java/lang/Math.round:(D)J
        //   241: l2i            
        //   242: istore          7
        //   244: ldc2_w          255.0
        //   247: dload           9
        //   249: ldc2_w          255.0
        //   252: dmul           
        //   253: dsub           
        //   254: invokestatic    java/lang/Math.round:(D)J
        //   257: l2i            
        //   258: istore          8
        //   260: invokestatic    net/minecraft/client/renderer/Tessellator.getInstance:()Lnet/minecraft/client/renderer/Tessellator;
        //   263: astore          9
        //   265: aload           9
        //   267: invokevirtual   net/minecraft/client/renderer/Tessellator.getWorldRenderer:()Lnet/minecraft/client/renderer/WorldRenderer;
        //   270: astore          10
        //   272: sipush          255
        //   275: iload           8
        //   277: isub           
        //   278: bipush          16
        //   280: ishl           
        //   281: iload           8
        //   283: bipush          8
        //   285: ishl           
        //   286: ior            
        //   287: istore          11
        //   289: sipush          255
        //   292: iload           8
        //   294: isub           
        //   295: iconst_4       
        //   296: idiv           
        //   297: bipush          16
        //   299: ishl           
        //   300: sipush          16128
        //   303: ior            
        //   304: istore          12
        //   306: aload_0        
        //   307: aload           10
        //   309: iload_3        
        //   310: iconst_2       
        //   311: iadd           
        //   312: iload           4
        //   314: bipush          13
        //   316: iadd           
        //   317: bipush          13
        //   319: iconst_2       
        //   320: iconst_0       
        //   321: invokespecial   net/minecraft/client/renderer/entity/RenderItem.func_175044_a:(Lnet/minecraft/client/renderer/WorldRenderer;IIIII)V
        //   324: aload_0        
        //   325: aload           10
        //   327: iload_3        
        //   328: iconst_2       
        //   329: iadd           
        //   330: iload           4
        //   332: bipush          13
        //   334: iadd           
        //   335: bipush          12
        //   337: iconst_1       
        //   338: iload           12
        //   340: invokespecial   net/minecraft/client/renderer/entity/RenderItem.func_175044_a:(Lnet/minecraft/client/renderer/WorldRenderer;IIIII)V
        //   343: aload_0        
        //   344: aload           10
        //   346: iload_3        
        //   347: iconst_2       
        //   348: iadd           
        //   349: iload           4
        //   351: bipush          13
        //   353: iadd           
        //   354: iload           7
        //   356: iconst_1       
        //   357: iload           11
        //   359: invokespecial   net/minecraft/client/renderer/entity/RenderItem.func_175044_a:(Lnet/minecraft/client/renderer/WorldRenderer;IIIII)V
        //   362: getstatic       optifine/Reflector.ForgeHooksClient:Loptifine/ReflectorClass;
        //   365: invokevirtual   optifine/ReflectorClass.exists:()Z
        //   368: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0368 (coming from #0365).
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
    
    private void func_175044_a(final WorldRenderer worldRenderer, final int n, final int n2, final int n3, final int n4, final int n5) {
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178991_c(n5);
        worldRenderer.addVertex(n + 0, n2 + 0, 0.0);
        worldRenderer.addVertex(n + 0, n2 + n4, 0.0);
        worldRenderer.addVertex(n + n3, n2 + n4, 0.0);
        worldRenderer.addVertex(n + n3, n2 + 0, 0.0);
        Tessellator.getInstance().draw();
    }
    
    private void registerItems() {
        this.registerBlock(Blocks.anvil, "anvil_intact");
        this.registerBlock(Blocks.anvil, 1, "anvil_slightly_damaged");
        this.registerBlock(Blocks.anvil, 2, "anvil_very_damaged");
        this.registerBlock(Blocks.carpet, EnumDyeColor.BLACK.func_176765_a(), "black_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.BLUE.func_176765_a(), "blue_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.BROWN.func_176765_a(), "brown_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.CYAN.func_176765_a(), "cyan_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.GRAY.func_176765_a(), "gray_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.GREEN.func_176765_a(), "green_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.LIGHT_BLUE.func_176765_a(), "light_blue_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.LIME.func_176765_a(), "lime_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.MAGENTA.func_176765_a(), "magenta_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.ORANGE.func_176765_a(), "orange_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.PINK.func_176765_a(), "pink_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.PURPLE.func_176765_a(), "purple_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.RED.func_176765_a(), "red_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.SILVER.func_176765_a(), "silver_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.WHITE.func_176765_a(), "white_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.YELLOW.func_176765_a(), "yellow_carpet");
        this.registerBlock(Blocks.cobblestone_wall, BlockWall.EnumType.MOSSY.func_176657_a(), "mossy_cobblestone_wall");
        this.registerBlock(Blocks.cobblestone_wall, BlockWall.EnumType.NORMAL.func_176657_a(), "cobblestone_wall");
        this.registerBlock(Blocks.dirt, BlockDirt.DirtType.COARSE_DIRT.getMetadata(), "coarse_dirt");
        this.registerBlock(Blocks.dirt, BlockDirt.DirtType.DIRT.getMetadata(), "dirt");
        this.registerBlock(Blocks.dirt, BlockDirt.DirtType.PODZOL.getMetadata(), "podzol");
        this.registerBlock(Blocks.double_plant, BlockDoublePlant.EnumPlantType.FERN.func_176936_a(), "double_fern");
        this.registerBlock(Blocks.double_plant, BlockDoublePlant.EnumPlantType.GRASS.func_176936_a(), "double_grass");
        this.registerBlock(Blocks.double_plant, BlockDoublePlant.EnumPlantType.PAEONIA.func_176936_a(), "paeonia");
        this.registerBlock(Blocks.double_plant, BlockDoublePlant.EnumPlantType.ROSE.func_176936_a(), "double_rose");
        this.registerBlock(Blocks.double_plant, BlockDoublePlant.EnumPlantType.SUNFLOWER.func_176936_a(), "sunflower");
        this.registerBlock(Blocks.double_plant, BlockDoublePlant.EnumPlantType.SYRINGA.func_176936_a(), "syringa");
        this.registerBlock(Blocks.leaves, BlockPlanks.EnumType.BIRCH.func_176839_a(), "birch_leaves");
        this.registerBlock(Blocks.leaves, BlockPlanks.EnumType.JUNGLE.func_176839_a(), "jungle_leaves");
        this.registerBlock(Blocks.leaves, BlockPlanks.EnumType.OAK.func_176839_a(), "oak_leaves");
        this.registerBlock(Blocks.leaves, BlockPlanks.EnumType.SPRUCE.func_176839_a(), "spruce_leaves");
        this.registerBlock(Blocks.leaves2, BlockPlanks.EnumType.ACACIA.func_176839_a() - 4, "acacia_leaves");
        this.registerBlock(Blocks.leaves2, BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4, "dark_oak_leaves");
        this.registerBlock(Blocks.log, BlockPlanks.EnumType.BIRCH.func_176839_a(), "birch_log");
        this.registerBlock(Blocks.log, BlockPlanks.EnumType.JUNGLE.func_176839_a(), "jungle_log");
        this.registerBlock(Blocks.log, BlockPlanks.EnumType.OAK.func_176839_a(), "oak_log");
        this.registerBlock(Blocks.log, BlockPlanks.EnumType.SPRUCE.func_176839_a(), "spruce_log");
        this.registerBlock(Blocks.log2, BlockPlanks.EnumType.ACACIA.func_176839_a() - 4, "acacia_log");
        this.registerBlock(Blocks.log2, BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4, "dark_oak_log");
        this.registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.CHISELED_STONEBRICK.func_176881_a(), "chiseled_brick_monster_egg");
        this.registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.COBBLESTONE.func_176881_a(), "cobblestone_monster_egg");
        this.registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.CRACKED_STONEBRICK.func_176881_a(), "cracked_brick_monster_egg");
        this.registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.MOSSY_STONEBRICK.func_176881_a(), "mossy_brick_monster_egg");
        this.registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.STONE.func_176881_a(), "stone_monster_egg");
        this.registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.STONEBRICK.func_176881_a(), "stone_brick_monster_egg");
        this.registerBlock(Blocks.planks, BlockPlanks.EnumType.ACACIA.func_176839_a(), "acacia_planks");
        this.registerBlock(Blocks.planks, BlockPlanks.EnumType.BIRCH.func_176839_a(), "birch_planks");
        this.registerBlock(Blocks.planks, BlockPlanks.EnumType.DARK_OAK.func_176839_a(), "dark_oak_planks");
        this.registerBlock(Blocks.planks, BlockPlanks.EnumType.JUNGLE.func_176839_a(), "jungle_planks");
        this.registerBlock(Blocks.planks, BlockPlanks.EnumType.OAK.func_176839_a(), "oak_planks");
        this.registerBlock(Blocks.planks, BlockPlanks.EnumType.SPRUCE.func_176839_a(), "spruce_planks");
        this.registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.BRICKS.getMetadata(), "prismarine_bricks");
        this.registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.DARK.getMetadata(), "dark_prismarine");
        this.registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.ROUGH.getMetadata(), "prismarine");
        this.registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.CHISELED.getMetaFromState(), "chiseled_quartz_block");
        this.registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.DEFAULT.getMetaFromState(), "quartz_block");
        this.registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.LINES_Y.getMetaFromState(), "quartz_column");
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.ALLIUM.func_176968_b(), "allium");
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.BLUE_ORCHID.func_176968_b(), "blue_orchid");
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.HOUSTONIA.func_176968_b(), "houstonia");
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.ORANGE_TULIP.func_176968_b(), "orange_tulip");
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.OXEYE_DAISY.func_176968_b(), "oxeye_daisy");
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.PINK_TULIP.func_176968_b(), "pink_tulip");
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.POPPY.func_176968_b(), "poppy");
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.RED_TULIP.func_176968_b(), "red_tulip");
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.WHITE_TULIP.func_176968_b(), "white_tulip");
        this.registerBlock(Blocks.sand, BlockSand.EnumType.RED_SAND.func_176688_a(), "red_sand");
        this.registerBlock(Blocks.sand, BlockSand.EnumType.SAND.func_176688_a(), "sand");
        this.registerBlock(Blocks.sandstone, BlockSandStone.EnumType.CHISELED.func_176675_a(), "chiseled_sandstone");
        this.registerBlock(Blocks.sandstone, BlockSandStone.EnumType.DEFAULT.func_176675_a(), "sandstone");
        this.registerBlock(Blocks.sandstone, BlockSandStone.EnumType.SMOOTH.func_176675_a(), "smooth_sandstone");
        this.registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.CHISELED.getMetaFromState(), "chiseled_red_sandstone");
        this.registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.DEFAULT.getMetaFromState(), "red_sandstone");
        this.registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.SMOOTH.getMetaFromState(), "smooth_red_sandstone");
        this.registerBlock(Blocks.sapling, BlockPlanks.EnumType.ACACIA.func_176839_a(), "acacia_sapling");
        this.registerBlock(Blocks.sapling, BlockPlanks.EnumType.BIRCH.func_176839_a(), "birch_sapling");
        this.registerBlock(Blocks.sapling, BlockPlanks.EnumType.DARK_OAK.func_176839_a(), "dark_oak_sapling");
        this.registerBlock(Blocks.sapling, BlockPlanks.EnumType.JUNGLE.func_176839_a(), "jungle_sapling");
        this.registerBlock(Blocks.sapling, BlockPlanks.EnumType.OAK.func_176839_a(), "oak_sapling");
        this.registerBlock(Blocks.sapling, BlockPlanks.EnumType.SPRUCE.func_176839_a(), "spruce_sapling");
        this.registerBlock(Blocks.sponge, 0, "sponge");
        this.registerBlock(Blocks.sponge, 1, "sponge_wet");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.BLACK.func_176765_a(), "black_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.BLUE.func_176765_a(), "blue_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.BROWN.func_176765_a(), "brown_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.CYAN.func_176765_a(), "cyan_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.GRAY.func_176765_a(), "gray_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.GREEN.func_176765_a(), "green_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.LIGHT_BLUE.func_176765_a(), "light_blue_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.LIME.func_176765_a(), "lime_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.MAGENTA.func_176765_a(), "magenta_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.ORANGE.func_176765_a(), "orange_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.PINK.func_176765_a(), "pink_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.PURPLE.func_176765_a(), "purple_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.RED.func_176765_a(), "red_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.SILVER.func_176765_a(), "silver_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.WHITE.func_176765_a(), "white_stained_glass");
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.YELLOW.func_176765_a(), "yellow_stained_glass");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.BLACK.func_176765_a(), "black_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.BLUE.func_176765_a(), "blue_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.BROWN.func_176765_a(), "brown_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.CYAN.func_176765_a(), "cyan_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.GRAY.func_176765_a(), "gray_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.GREEN.func_176765_a(), "green_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.LIGHT_BLUE.func_176765_a(), "light_blue_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.LIME.func_176765_a(), "lime_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.MAGENTA.func_176765_a(), "magenta_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.ORANGE.func_176765_a(), "orange_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.PINK.func_176765_a(), "pink_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.PURPLE.func_176765_a(), "purple_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.RED.func_176765_a(), "red_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.SILVER.func_176765_a(), "silver_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.WHITE.func_176765_a(), "white_stained_glass_pane");
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.YELLOW.func_176765_a(), "yellow_stained_glass_pane");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BLACK.func_176765_a(), "black_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BLUE.func_176765_a(), "blue_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BROWN.func_176765_a(), "brown_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.CYAN.func_176765_a(), "cyan_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.GRAY.func_176765_a(), "gray_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.GREEN.func_176765_a(), "green_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.LIGHT_BLUE.func_176765_a(), "light_blue_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.LIME.func_176765_a(), "lime_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.MAGENTA.func_176765_a(), "magenta_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.ORANGE.func_176765_a(), "orange_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.PINK.func_176765_a(), "pink_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.PURPLE.func_176765_a(), "purple_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.RED.func_176765_a(), "red_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.SILVER.func_176765_a(), "silver_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.WHITE.func_176765_a(), "white_stained_hardened_clay");
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.YELLOW.func_176765_a(), "yellow_stained_hardened_clay");
        this.registerBlock(Blocks.stone, BlockStone.EnumType.ANDESITE.getMetaFromState(), "andesite");
        this.registerBlock(Blocks.stone, BlockStone.EnumType.ANDESITE_SMOOTH.getMetaFromState(), "andesite_smooth");
        this.registerBlock(Blocks.stone, BlockStone.EnumType.DIORITE.getMetaFromState(), "diorite");
        this.registerBlock(Blocks.stone, BlockStone.EnumType.DIORITE_SMOOTH.getMetaFromState(), "diorite_smooth");
        this.registerBlock(Blocks.stone, BlockStone.EnumType.GRANITE.getMetaFromState(), "granite");
        this.registerBlock(Blocks.stone, BlockStone.EnumType.GRANITE_SMOOTH.getMetaFromState(), "granite_smooth");
        this.registerBlock(Blocks.stone, BlockStone.EnumType.STONE.getMetaFromState(), "stone");
        this.registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.CRACKED.getMetaFromState(), "cracked_stonebrick");
        this.registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.DEFAULT.getMetaFromState(), "stonebrick");
        this.registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.CHISELED.getMetaFromState(), "chiseled_stonebrick");
        this.registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.MOSSY.getMetaFromState(), "mossy_stonebrick");
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.BRICK.func_176624_a(), "brick_slab");
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.COBBLESTONE.func_176624_a(), "cobblestone_slab");
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.WOOD.func_176624_a(), "old_wood_slab");
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.NETHERBRICK.func_176624_a(), "nether_brick_slab");
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.QUARTZ.func_176624_a(), "quartz_slab");
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.SAND.func_176624_a(), "sandstone_slab");
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a(), "stone_brick_slab");
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.STONE.func_176624_a(), "stone_slab");
        this.registerBlock(Blocks.stone_slab2, BlockStoneSlabNew.EnumType.RED_SANDSTONE.func_176915_a(), "red_sandstone_slab");
        this.registerBlock(Blocks.tallgrass, BlockTallGrass.EnumType.DEAD_BUSH.func_177044_a(), "dead_bush");
        this.registerBlock(Blocks.tallgrass, BlockTallGrass.EnumType.FERN.func_177044_a(), "fern");
        this.registerBlock(Blocks.tallgrass, BlockTallGrass.EnumType.GRASS.func_177044_a(), "tall_grass");
        this.registerBlock(Blocks.wooden_slab, BlockPlanks.EnumType.ACACIA.func_176839_a(), "acacia_slab");
        this.registerBlock(Blocks.wooden_slab, BlockPlanks.EnumType.BIRCH.func_176839_a(), "birch_slab");
        this.registerBlock(Blocks.wooden_slab, BlockPlanks.EnumType.DARK_OAK.func_176839_a(), "dark_oak_slab");
        this.registerBlock(Blocks.wooden_slab, BlockPlanks.EnumType.JUNGLE.func_176839_a(), "jungle_slab");
        this.registerBlock(Blocks.wooden_slab, BlockPlanks.EnumType.OAK.func_176839_a(), "oak_slab");
        this.registerBlock(Blocks.wooden_slab, BlockPlanks.EnumType.SPRUCE.func_176839_a(), "spruce_slab");
        this.registerBlock(Blocks.wool, EnumDyeColor.BLACK.func_176765_a(), "black_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.BLUE.func_176765_a(), "blue_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.BROWN.func_176765_a(), "brown_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.CYAN.func_176765_a(), "cyan_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.GRAY.func_176765_a(), "gray_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.GREEN.func_176765_a(), "green_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.LIGHT_BLUE.func_176765_a(), "light_blue_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.LIME.func_176765_a(), "lime_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.MAGENTA.func_176765_a(), "magenta_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.ORANGE.func_176765_a(), "orange_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.PINK.func_176765_a(), "pink_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.PURPLE.func_176765_a(), "purple_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.RED.func_176765_a(), "red_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.SILVER.func_176765_a(), "silver_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.WHITE.func_176765_a(), "white_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.YELLOW.func_176765_a(), "yellow_wool");
        this.registerBlock(Blocks.acacia_stairs, "acacia_stairs");
        this.registerBlock(Blocks.activator_rail, "activator_rail");
        this.registerBlock(Blocks.beacon, "beacon");
        this.registerBlock(Blocks.bedrock, "bedrock");
        this.registerBlock(Blocks.birch_stairs, "birch_stairs");
        this.registerBlock(Blocks.bookshelf, "bookshelf");
        this.registerBlock(Blocks.brick_block, "brick_block");
        this.registerBlock(Blocks.brick_block, "brick_block");
        this.registerBlock(Blocks.brick_stairs, "brick_stairs");
        this.registerBlock(Blocks.brown_mushroom, "brown_mushroom");
        this.registerBlock(Blocks.cactus, "cactus");
        this.registerBlock(Blocks.clay, "clay");
        this.registerBlock(Blocks.coal_block, "coal_block");
        this.registerBlock(Blocks.coal_ore, "coal_ore");
        this.registerBlock(Blocks.cobblestone, "cobblestone");
        this.registerBlock(Blocks.crafting_table, "crafting_table");
        this.registerBlock(Blocks.dark_oak_stairs, "dark_oak_stairs");
        this.registerBlock(Blocks.daylight_detector, "daylight_detector");
        this.registerBlock(Blocks.deadbush, "dead_bush");
        this.registerBlock(Blocks.detector_rail, "detector_rail");
        this.registerBlock(Blocks.diamond_block, "diamond_block");
        this.registerBlock(Blocks.diamond_ore, "diamond_ore");
        this.registerBlock(Blocks.dispenser, "dispenser");
        this.registerBlock(Blocks.dropper, "dropper");
        this.registerBlock(Blocks.emerald_block, "emerald_block");
        this.registerBlock(Blocks.emerald_ore, "emerald_ore");
        this.registerBlock(Blocks.enchanting_table, "enchanting_table");
        this.registerBlock(Blocks.end_portal_frame, "end_portal_frame");
        this.registerBlock(Blocks.end_stone, "end_stone");
        this.registerBlock(Blocks.oak_fence, "oak_fence");
        this.registerBlock(Blocks.spruce_fence, "spruce_fence");
        this.registerBlock(Blocks.birch_fence, "birch_fence");
        this.registerBlock(Blocks.jungle_fence, "jungle_fence");
        this.registerBlock(Blocks.dark_oak_fence, "dark_oak_fence");
        this.registerBlock(Blocks.acacia_fence, "acacia_fence");
        this.registerBlock(Blocks.oak_fence_gate, "oak_fence_gate");
        this.registerBlock(Blocks.spruce_fence_gate, "spruce_fence_gate");
        this.registerBlock(Blocks.birch_fence_gate, "birch_fence_gate");
        this.registerBlock(Blocks.jungle_fence_gate, "jungle_fence_gate");
        this.registerBlock(Blocks.dark_oak_fence_gate, "dark_oak_fence_gate");
        this.registerBlock(Blocks.acacia_fence_gate, "acacia_fence_gate");
        this.registerBlock(Blocks.furnace, "furnace");
        this.registerBlock(Blocks.glass, "glass");
        this.registerBlock(Blocks.glass_pane, "glass_pane");
        this.registerBlock(Blocks.glowstone, "glowstone");
        this.registerBlock(Blocks.golden_rail, "golden_rail");
        this.registerBlock(Blocks.gold_block, "gold_block");
        this.registerBlock(Blocks.gold_ore, "gold_ore");
        this.registerBlock(Blocks.grass, "grass");
        this.registerBlock(Blocks.gravel, "gravel");
        this.registerBlock(Blocks.hardened_clay, "hardened_clay");
        this.registerBlock(Blocks.hay_block, "hay_block");
        this.registerBlock(Blocks.heavy_weighted_pressure_plate, "heavy_weighted_pressure_plate");
        this.registerBlock(Blocks.hopper, "hopper");
        this.registerBlock(Blocks.ice, "ice");
        this.registerBlock(Blocks.iron_bars, "iron_bars");
        this.registerBlock(Blocks.iron_block, "iron_block");
        this.registerBlock(Blocks.iron_ore, "iron_ore");
        this.registerBlock(Blocks.iron_trapdoor, "iron_trapdoor");
        this.registerBlock(Blocks.jukebox, "jukebox");
        this.registerBlock(Blocks.jungle_stairs, "jungle_stairs");
        this.registerBlock(Blocks.ladder, "ladder");
        this.registerBlock(Blocks.lapis_block, "lapis_block");
        this.registerBlock(Blocks.lapis_ore, "lapis_ore");
        this.registerBlock(Blocks.lever, "lever");
        this.registerBlock(Blocks.light_weighted_pressure_plate, "light_weighted_pressure_plate");
        this.registerBlock(Blocks.lit_pumpkin, "lit_pumpkin");
        this.registerBlock(Blocks.melon_block, "melon_block");
        this.registerBlock(Blocks.mossy_cobblestone, "mossy_cobblestone");
        this.registerBlock(Blocks.mycelium, "mycelium");
        this.registerBlock(Blocks.netherrack, "netherrack");
        this.registerBlock(Blocks.nether_brick, "nether_brick");
        this.registerBlock(Blocks.nether_brick_fence, "nether_brick_fence");
        this.registerBlock(Blocks.nether_brick_stairs, "nether_brick_stairs");
        this.registerBlock(Blocks.noteblock, "noteblock");
        this.registerBlock(Blocks.oak_stairs, "oak_stairs");
        this.registerBlock(Blocks.obsidian, "obsidian");
        this.registerBlock(Blocks.packed_ice, "packed_ice");
        this.registerBlock(Blocks.piston, "piston");
        this.registerBlock(Blocks.pumpkin, "pumpkin");
        this.registerBlock(Blocks.quartz_ore, "quartz_ore");
        this.registerBlock(Blocks.quartz_stairs, "quartz_stairs");
        this.registerBlock(Blocks.rail, "rail");
        this.registerBlock(Blocks.redstone_block, "redstone_block");
        this.registerBlock(Blocks.redstone_lamp, "redstone_lamp");
        this.registerBlock(Blocks.redstone_ore, "redstone_ore");
        this.registerBlock(Blocks.redstone_torch, "redstone_torch");
        this.registerBlock(Blocks.red_mushroom, "red_mushroom");
        this.registerBlock(Blocks.sandstone_stairs, "sandstone_stairs");
        this.registerBlock(Blocks.red_sandstone_stairs, "red_sandstone_stairs");
        this.registerBlock(Blocks.sea_lantern, "sea_lantern");
        this.registerBlock(Blocks.slime_block, "slime");
        this.registerBlock(Blocks.snow, "snow");
        this.registerBlock(Blocks.snow_layer, "snow_layer");
        this.registerBlock(Blocks.soul_sand, "soul_sand");
        this.registerBlock(Blocks.spruce_stairs, "spruce_stairs");
        this.registerBlock(Blocks.sticky_piston, "sticky_piston");
        this.registerBlock(Blocks.stone_brick_stairs, "stone_brick_stairs");
        this.registerBlock(Blocks.stone_button, "stone_button");
        this.registerBlock(Blocks.stone_pressure_plate, "stone_pressure_plate");
        this.registerBlock(Blocks.stone_stairs, "stone_stairs");
        this.registerBlock(Blocks.tnt, "tnt");
        this.registerBlock(Blocks.torch, "torch");
        this.registerBlock(Blocks.trapdoor, "trapdoor");
        this.registerBlock(Blocks.tripwire_hook, "tripwire_hook");
        this.registerBlock(Blocks.vine, "vine");
        this.registerBlock(Blocks.waterlily, "waterlily");
        this.registerBlock(Blocks.web, "web");
        this.registerBlock(Blocks.wooden_button, "wooden_button");
        this.registerBlock(Blocks.wooden_pressure_plate, "wooden_pressure_plate");
        this.registerBlock(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION.func_176968_b(), "dandelion");
        this.registerBlock(Blocks.chest, "chest");
        this.registerBlock(Blocks.trapped_chest, "trapped_chest");
        this.registerBlock(Blocks.ender_chest, "ender_chest");
        this.registerItem(Items.iron_shovel, "iron_shovel");
        this.registerItem(Items.iron_pickaxe, "iron_pickaxe");
        this.registerItem(Items.iron_axe, "iron_axe");
        this.registerItem(Items.flint_and_steel, "flint_and_steel");
        this.registerItem(Items.apple, "apple");
        this.registerItem(Items.bow, 0, "bow");
        this.registerItem(Items.bow, 1, "bow_pulling_0");
        this.registerItem(Items.bow, 2, "bow_pulling_1");
        this.registerItem(Items.bow, 3, "bow_pulling_2");
        this.registerItem(Items.arrow, "arrow");
        this.registerItem(Items.coal, 0, "coal");
        this.registerItem(Items.coal, 1, "charcoal");
        this.registerItem(Items.diamond, "diamond");
        this.registerItem(Items.iron_ingot, "iron_ingot");
        this.registerItem(Items.gold_ingot, "gold_ingot");
        this.registerItem(Items.iron_sword, "iron_sword");
        this.registerItem(Items.wooden_sword, "wooden_sword");
        this.registerItem(Items.wooden_shovel, "wooden_shovel");
        this.registerItem(Items.wooden_pickaxe, "wooden_pickaxe");
        this.registerItem(Items.wooden_axe, "wooden_axe");
        this.registerItem(Items.stone_sword, "stone_sword");
        this.registerItem(Items.stone_shovel, "stone_shovel");
        this.registerItem(Items.stone_pickaxe, "stone_pickaxe");
        this.registerItem(Items.stone_axe, "stone_axe");
        this.registerItem(Items.diamond_sword, "diamond_sword");
        this.registerItem(Items.diamond_shovel, "diamond_shovel");
        this.registerItem(Items.diamond_pickaxe, "diamond_pickaxe");
        this.registerItem(Items.diamond_axe, "diamond_axe");
        this.registerItem(Items.stick, "stick");
        this.registerItem(Items.bowl, "bowl");
        this.registerItem(Items.mushroom_stew, "mushroom_stew");
        this.registerItem(Items.golden_sword, "golden_sword");
        this.registerItem(Items.golden_shovel, "golden_shovel");
        this.registerItem(Items.golden_pickaxe, "golden_pickaxe");
        this.registerItem(Items.golden_axe, "golden_axe");
        this.registerItem(Items.string, "string");
        this.registerItem(Items.feather, "feather");
        this.registerItem(Items.gunpowder, "gunpowder");
        this.registerItem(Items.wooden_hoe, "wooden_hoe");
        this.registerItem(Items.stone_hoe, "stone_hoe");
        this.registerItem(Items.iron_hoe, "iron_hoe");
        this.registerItem(Items.diamond_hoe, "diamond_hoe");
        this.registerItem(Items.golden_hoe, "golden_hoe");
        this.registerItem(Items.wheat_seeds, "wheat_seeds");
        this.registerItem(Items.wheat, "wheat");
        this.registerItem(Items.bread, "bread");
        this.registerItem(Items.leather_helmet, "leather_helmet");
        this.registerItem(Items.leather_chestplate, "leather_chestplate");
        this.registerItem(Items.leather_leggings, "leather_leggings");
        this.registerItem(Items.leather_boots, "leather_boots");
        this.registerItem(Items.chainmail_helmet, "chainmail_helmet");
        this.registerItem(Items.chainmail_chestplate, "chainmail_chestplate");
        this.registerItem(Items.chainmail_leggings, "chainmail_leggings");
        this.registerItem(Items.chainmail_boots, "chainmail_boots");
        this.registerItem(Items.iron_helmet, "iron_helmet");
        this.registerItem(Items.iron_chestplate, "iron_chestplate");
        this.registerItem(Items.iron_leggings, "iron_leggings");
        this.registerItem(Items.iron_boots, "iron_boots");
        this.registerItem(Items.diamond_helmet, "diamond_helmet");
        this.registerItem(Items.diamond_chestplate, "diamond_chestplate");
        this.registerItem(Items.diamond_leggings, "diamond_leggings");
        this.registerItem(Items.diamond_boots, "diamond_boots");
        this.registerItem(Items.golden_helmet, "golden_helmet");
        this.registerItem(Items.golden_chestplate, "golden_chestplate");
        this.registerItem(Items.golden_leggings, "golden_leggings");
        this.registerItem(Items.golden_boots, "golden_boots");
        this.registerItem(Items.flint, "flint");
        this.registerItem(Items.porkchop, "porkchop");
        this.registerItem(Items.cooked_porkchop, "cooked_porkchop");
        this.registerItem(Items.painting, "painting");
        this.registerItem(Items.golden_apple, "golden_apple");
        this.registerItem(Items.golden_apple, 1, "golden_apple");
        this.registerItem(Items.sign, "sign");
        this.registerItem(Items.oak_door, "oak_door");
        this.registerItem(Items.spruce_door, "spruce_door");
        this.registerItem(Items.birch_door, "birch_door");
        this.registerItem(Items.jungle_door, "jungle_door");
        this.registerItem(Items.acacia_door, "acacia_door");
        this.registerItem(Items.dark_oak_door, "dark_oak_door");
        this.registerItem(Items.bucket, "bucket");
        this.registerItem(Items.water_bucket, "water_bucket");
        this.registerItem(Items.lava_bucket, "lava_bucket");
        this.registerItem(Items.minecart, "minecart");
        this.registerItem(Items.saddle, "saddle");
        this.registerItem(Items.iron_door, "iron_door");
        this.registerItem(Items.redstone, "redstone");
        this.registerItem(Items.snowball, "snowball");
        this.registerItem(Items.boat, "boat");
        this.registerItem(Items.leather, "leather");
        this.registerItem(Items.milk_bucket, "milk_bucket");
        this.registerItem(Items.brick, "brick");
        this.registerItem(Items.clay_ball, "clay_ball");
        this.registerItem(Items.reeds, "reeds");
        this.registerItem(Items.paper, "paper");
        this.registerItem(Items.book, "book");
        this.registerItem(Items.slime_ball, "slime_ball");
        this.registerItem(Items.chest_minecart, "chest_minecart");
        this.registerItem(Items.furnace_minecart, "furnace_minecart");
        this.registerItem(Items.egg, "egg");
        this.registerItem(Items.compass, "compass");
        this.registerItem(Items.fishing_rod, "fishing_rod");
        this.registerItem(Items.fishing_rod, 1, "fishing_rod_cast");
        this.registerItem(Items.clock, "clock");
        this.registerItem(Items.glowstone_dust, "glowstone_dust");
        this.registerItem(Items.fish, ItemFishFood.FishType.COD.getItemDamage(), "cod");
        this.registerItem(Items.fish, ItemFishFood.FishType.SALMON.getItemDamage(), "salmon");
        this.registerItem(Items.fish, ItemFishFood.FishType.CLOWNFISH.getItemDamage(), "clownfish");
        this.registerItem(Items.fish, ItemFishFood.FishType.PUFFERFISH.getItemDamage(), "pufferfish");
        this.registerItem(Items.cooked_fish, ItemFishFood.FishType.COD.getItemDamage(), "cooked_cod");
        this.registerItem(Items.cooked_fish, ItemFishFood.FishType.SALMON.getItemDamage(), "cooked_salmon");
        this.registerItem(Items.dye, EnumDyeColor.BLACK.getDyeColorDamage(), "dye_black");
        this.registerItem(Items.dye, EnumDyeColor.RED.getDyeColorDamage(), "dye_red");
        this.registerItem(Items.dye, EnumDyeColor.GREEN.getDyeColorDamage(), "dye_green");
        this.registerItem(Items.dye, EnumDyeColor.BROWN.getDyeColorDamage(), "dye_brown");
        this.registerItem(Items.dye, EnumDyeColor.BLUE.getDyeColorDamage(), "dye_blue");
        this.registerItem(Items.dye, EnumDyeColor.PURPLE.getDyeColorDamage(), "dye_purple");
        this.registerItem(Items.dye, EnumDyeColor.CYAN.getDyeColorDamage(), "dye_cyan");
        this.registerItem(Items.dye, EnumDyeColor.SILVER.getDyeColorDamage(), "dye_silver");
        this.registerItem(Items.dye, EnumDyeColor.GRAY.getDyeColorDamage(), "dye_gray");
        this.registerItem(Items.dye, EnumDyeColor.PINK.getDyeColorDamage(), "dye_pink");
        this.registerItem(Items.dye, EnumDyeColor.LIME.getDyeColorDamage(), "dye_lime");
        this.registerItem(Items.dye, EnumDyeColor.YELLOW.getDyeColorDamage(), "dye_yellow");
        this.registerItem(Items.dye, EnumDyeColor.LIGHT_BLUE.getDyeColorDamage(), "dye_light_blue");
        this.registerItem(Items.dye, EnumDyeColor.MAGENTA.getDyeColorDamage(), "dye_magenta");
        this.registerItem(Items.dye, EnumDyeColor.ORANGE.getDyeColorDamage(), "dye_orange");
        this.registerItem(Items.dye, EnumDyeColor.WHITE.getDyeColorDamage(), "dye_white");
        this.registerItem(Items.bone, "bone");
        this.registerItem(Items.sugar, "sugar");
        this.registerItem(Items.cake, "cake");
        this.registerItem(Items.bed, "bed");
        this.registerItem(Items.repeater, "repeater");
        this.registerItem(Items.cookie, "cookie");
        this.registerItem(Items.shears, "shears");
        this.registerItem(Items.melon, "melon");
        this.registerItem(Items.pumpkin_seeds, "pumpkin_seeds");
        this.registerItem(Items.melon_seeds, "melon_seeds");
        this.registerItem(Items.beef, "beef");
        this.registerItem(Items.cooked_beef, "cooked_beef");
        this.registerItem(Items.chicken, "chicken");
        this.registerItem(Items.cooked_chicken, "cooked_chicken");
        this.registerItem(Items.rabbit, "rabbit");
        this.registerItem(Items.cooked_rabbit, "cooked_rabbit");
        this.registerItem(Items.mutton, "mutton");
        this.registerItem(Items.cooked_mutton, "cooked_mutton");
        this.registerItem(Items.rabbit_foot, "rabbit_foot");
        this.registerItem(Items.rabbit_hide, "rabbit_hide");
        this.registerItem(Items.rabbit_stew, "rabbit_stew");
        this.registerItem(Items.rotten_flesh, "rotten_flesh");
        this.registerItem(Items.ender_pearl, "ender_pearl");
        this.registerItem(Items.blaze_rod, "blaze_rod");
        this.registerItem(Items.ghast_tear, "ghast_tear");
        this.registerItem(Items.gold_nugget, "gold_nugget");
        this.registerItem(Items.nether_wart, "nether_wart");
        this.itemModelMesher.register(Items.potionitem, new ItemMeshDefinition() {
            private static final String __OBFID;
            final RenderItem this$0;
            
            @Override
            public ModelResourceLocation getModelLocation(final ItemStack itemStack) {
                return ItemPotion.isSplash(itemStack.getMetadata()) ? new ModelResourceLocation("bottle_splash", "inventory") : new ModelResourceLocation("bottle_drinkable", "inventory");
            }
            
            static {
                __OBFID = "CL_00002440";
            }
        });
        this.registerItem(Items.glass_bottle, "glass_bottle");
        this.registerItem(Items.spider_eye, "spider_eye");
        this.registerItem(Items.fermented_spider_eye, "fermented_spider_eye");
        this.registerItem(Items.blaze_powder, "blaze_powder");
        this.registerItem(Items.magma_cream, "magma_cream");
        this.registerItem(Items.brewing_stand, "brewing_stand");
        this.registerItem(Items.cauldron, "cauldron");
        this.registerItem(Items.ender_eye, "ender_eye");
        this.registerItem(Items.speckled_melon, "speckled_melon");
        this.itemModelMesher.register(Items.spawn_egg, new ItemMeshDefinition() {
            private static final String __OBFID;
            final RenderItem this$0;
            
            @Override
            public ModelResourceLocation getModelLocation(final ItemStack itemStack) {
                return new ModelResourceLocation("spawn_egg", "inventory");
            }
            
            static {
                __OBFID = "CL_00002439";
            }
        });
        this.registerItem(Items.experience_bottle, "experience_bottle");
        this.registerItem(Items.fire_charge, "fire_charge");
        this.registerItem(Items.writable_book, "writable_book");
        this.registerItem(Items.emerald, "emerald");
        this.registerItem(Items.item_frame, "item_frame");
        this.registerItem(Items.flower_pot, "flower_pot");
        this.registerItem(Items.carrot, "carrot");
        this.registerItem(Items.potato, "potato");
        this.registerItem(Items.baked_potato, "baked_potato");
        this.registerItem(Items.poisonous_potato, "poisonous_potato");
        this.registerItem(Items.map, "map");
        this.registerItem(Items.golden_carrot, "golden_carrot");
        this.registerItem(Items.skull, 0, "skull_skeleton");
        this.registerItem(Items.skull, 1, "skull_wither");
        this.registerItem(Items.skull, 2, "skull_zombie");
        this.registerItem(Items.skull, 3, "skull_char");
        this.registerItem(Items.skull, 4, "skull_creeper");
        this.registerItem(Items.carrot_on_a_stick, "carrot_on_a_stick");
        this.registerItem(Items.nether_star, "nether_star");
        this.registerItem(Items.pumpkin_pie, "pumpkin_pie");
        this.registerItem(Items.firework_charge, "firework_charge");
        this.registerItem(Items.comparator, "comparator");
        this.registerItem(Items.netherbrick, "netherbrick");
        this.registerItem(Items.quartz, "quartz");
        this.registerItem(Items.tnt_minecart, "tnt_minecart");
        this.registerItem(Items.hopper_minecart, "hopper_minecart");
        this.registerItem(Items.armor_stand, "armor_stand");
        this.registerItem(Items.iron_horse_armor, "iron_horse_armor");
        this.registerItem(Items.golden_horse_armor, "golden_horse_armor");
        this.registerItem(Items.diamond_horse_armor, "diamond_horse_armor");
        this.registerItem(Items.lead, "lead");
        this.registerItem(Items.name_tag, "name_tag");
        this.itemModelMesher.register(Items.banner, new ItemMeshDefinition() {
            private static final String __OBFID;
            final RenderItem this$0;
            
            @Override
            public ModelResourceLocation getModelLocation(final ItemStack itemStack) {
                return new ModelResourceLocation("banner", "inventory");
            }
            
            static {
                __OBFID = "CL_00002438";
            }
        });
        this.registerItem(Items.record_13, "record_13");
        this.registerItem(Items.record_cat, "record_cat");
        this.registerItem(Items.record_blocks, "record_blocks");
        this.registerItem(Items.record_chirp, "record_chirp");
        this.registerItem(Items.record_far, "record_far");
        this.registerItem(Items.record_mall, "record_mall");
        this.registerItem(Items.record_mellohi, "record_mellohi");
        this.registerItem(Items.record_stal, "record_stal");
        this.registerItem(Items.record_strad, "record_strad");
        this.registerItem(Items.record_ward, "record_ward");
        this.registerItem(Items.record_11, "record_11");
        this.registerItem(Items.record_wait, "record_wait");
        this.registerItem(Items.prismarine_shard, "prismarine_shard");
        this.registerItem(Items.prismarine_crystals, "prismarine_crystals");
        this.itemModelMesher.register(Items.enchanted_book, new ItemMeshDefinition() {
            private static final String __OBFID;
            final RenderItem this$0;
            
            @Override
            public ModelResourceLocation getModelLocation(final ItemStack itemStack) {
                return new ModelResourceLocation("enchanted_book", "inventory");
            }
            
            static {
                __OBFID = "CL_00002437";
            }
        });
        this.itemModelMesher.register(Items.filled_map, new ItemMeshDefinition() {
            private static final String __OBFID;
            final RenderItem this$0;
            
            @Override
            public ModelResourceLocation getModelLocation(final ItemStack itemStack) {
                return new ModelResourceLocation("filled_map", "inventory");
            }
            
            static {
                __OBFID = "CL_00002436";
            }
        });
        this.registerBlock(Blocks.command_block, "command_block");
        this.registerItem(Items.fireworks, "fireworks");
        this.registerItem(Items.command_block_minecart, "command_block_minecart");
        this.registerBlock(Blocks.barrier, "barrier");
        this.registerBlock(Blocks.mob_spawner, "mob_spawner");
        this.registerItem(Items.written_book, "written_book");
        this.registerBlock(Blocks.brown_mushroom_block, BlockHugeMushroom.EnumType.ALL_INSIDE.func_176896_a(), "brown_mushroom_block");
        this.registerBlock(Blocks.red_mushroom_block, BlockHugeMushroom.EnumType.ALL_INSIDE.func_176896_a(), "red_mushroom_block");
        this.registerBlock(Blocks.dragon_egg, "dragon_egg");
        if (Reflector.ModelLoader_onRegisterItems.exists()) {
            Reflector.call(Reflector.ModelLoader_onRegisterItems, this.itemModelMesher);
        }
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.itemModelMesher.rebuildCache();
    }
    
    public static void forgeHooksClient_putQuadColor(final WorldRenderer worldRenderer, final BakedQuad bakedQuad, final int n) {
        final float n2 = (float)(n & 0xFF);
        final float n3 = (float)(n >>> 8 & 0xFF);
        final float n4 = (float)(n >>> 16 & 0xFF);
        final float n5 = (float)(n >>> 24 & 0xFF);
        final int[] func_178209_a = bakedQuad.func_178209_a();
        final int n6 = func_178209_a.length / 4;
        while (0 < 4) {
            final int n7 = func_178209_a[3 + n6 * 0];
            worldRenderer.func_178972_a(worldRenderer.getGLCallListForPass(4), Math.min(255, (int)(n2 * (n7 & 0xFF) / 255.0f)), Math.min(255, (int)(n3 * (n7 >>> 8 & 0xFF) / 255.0f)), Math.min(255, (int)(n4 * (n7 >>> 16 & 0xFF) / 255.0f)), Math.min(255, (int)(n5 * (n7 >>> 24 & 0xFF) / 255.0f)));
            int n8 = 0;
            ++n8;
        }
    }
    
    public void renderItemOverlays(final FontRenderer fontRenderer, final ItemStack itemStack, final int n, final int n2) {
        this.func_180453_a(fontRenderer, itemStack, n, n2, null);
    }
    
    static final class SwitchTransformType
    {
        static final int[] field_178640_a;
        private static final String __OBFID;
        private static final String[] lIIllIIlllIIlIIl;
        private static String[] lIIllIIlllIIlllI;
        
        static {
            lllIIIlIIlllIlll();
            lllIIIlIIlllIllI();
            __OBFID = SwitchTransformType.lIIllIIlllIIlIIl[0];
            field_178640_a = new int[ItemCameraTransforms.TransformType.values().length];
            try {
                SwitchTransformType.field_178640_a[ItemCameraTransforms.TransformType.NONE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchTransformType.field_178640_a[ItemCameraTransforms.TransformType.THIRD_PERSON.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchTransformType.field_178640_a[ItemCameraTransforms.TransformType.FIRST_PERSON.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchTransformType.field_178640_a[ItemCameraTransforms.TransformType.HEAD.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchTransformType.field_178640_a[ItemCameraTransforms.TransformType.GUI.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
        
        private static void lllIIIlIIlllIllI() {
            (lIIllIIlllIIlIIl = new String[1])[0] = lllIIIlIIlllIIlI(SwitchTransformType.lIIllIIlllIIlllI[0], SwitchTransformType.lIIllIIlllIIlllI[1]);
            SwitchTransformType.lIIllIIlllIIlllI = null;
        }
        
        private static void lllIIIlIIlllIlll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchTransformType.lIIllIIlllIIlllI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lllIIIlIIlllIIlI(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                final Cipher instance = Cipher.getInstance("Blowfish");
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
