package optifine;

import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.resources.model.*;
import java.util.*;

public class SmartLeaves
{
    private static IBakedModel modelLeavesCullAcacia;
    private static IBakedModel modelLeavesCullBirch;
    private static IBakedModel modelLeavesCullDarkOak;
    private static IBakedModel modelLeavesCullJungle;
    private static IBakedModel modelLeavesCullOak;
    private static IBakedModel modelLeavesCullSpruce;
    private static List generalQuadsCullAcacia;
    private static List generalQuadsCullBirch;
    private static List generalQuadsCullDarkOak;
    private static List generalQuadsCullJungle;
    private static List generalQuadsCullOak;
    private static List generalQuadsCullSpruce;
    private static IBakedModel modelLeavesDoubleAcacia;
    private static IBakedModel modelLeavesDoubleBirch;
    private static IBakedModel modelLeavesDoubleDarkOak;
    private static IBakedModel modelLeavesDoubleJungle;
    private static IBakedModel modelLeavesDoubleOak;
    private static IBakedModel modelLeavesDoubleSpruce;
    
    static {
        SmartLeaves.modelLeavesCullAcacia = null;
        SmartLeaves.modelLeavesCullBirch = null;
        SmartLeaves.modelLeavesCullDarkOak = null;
        SmartLeaves.modelLeavesCullJungle = null;
        SmartLeaves.modelLeavesCullOak = null;
        SmartLeaves.modelLeavesCullSpruce = null;
        SmartLeaves.generalQuadsCullAcacia = null;
        SmartLeaves.generalQuadsCullBirch = null;
        SmartLeaves.generalQuadsCullDarkOak = null;
        SmartLeaves.generalQuadsCullJungle = null;
        SmartLeaves.generalQuadsCullOak = null;
        SmartLeaves.generalQuadsCullSpruce = null;
        SmartLeaves.modelLeavesDoubleAcacia = null;
        SmartLeaves.modelLeavesDoubleBirch = null;
        SmartLeaves.modelLeavesDoubleDarkOak = null;
        SmartLeaves.modelLeavesDoubleJungle = null;
        SmartLeaves.modelLeavesDoubleOak = null;
        SmartLeaves.modelLeavesDoubleSpruce = null;
    }
    
    public static IBakedModel getLeavesModel(final IBakedModel bakedModel) {
        if (!Config.isTreesSmart()) {
            return bakedModel;
        }
        final List func_177550_a = bakedModel.func_177550_a();
        return (func_177550_a == SmartLeaves.generalQuadsCullAcacia) ? SmartLeaves.modelLeavesDoubleAcacia : ((func_177550_a == SmartLeaves.generalQuadsCullBirch) ? SmartLeaves.modelLeavesDoubleBirch : ((func_177550_a == SmartLeaves.generalQuadsCullDarkOak) ? SmartLeaves.modelLeavesDoubleDarkOak : ((func_177550_a == SmartLeaves.generalQuadsCullJungle) ? SmartLeaves.modelLeavesDoubleJungle : ((func_177550_a == SmartLeaves.generalQuadsCullOak) ? SmartLeaves.modelLeavesDoubleOak : ((func_177550_a == SmartLeaves.generalQuadsCullSpruce) ? SmartLeaves.modelLeavesDoubleSpruce : bakedModel)))));
    }
    
    public static void updateLeavesModels() {
        final ArrayList list = new ArrayList();
        SmartLeaves.modelLeavesCullAcacia = getModelCull("acacia", list);
        SmartLeaves.modelLeavesCullBirch = getModelCull("birch", list);
        SmartLeaves.modelLeavesCullDarkOak = getModelCull("dark_oak", list);
        SmartLeaves.modelLeavesCullJungle = getModelCull("jungle", list);
        SmartLeaves.modelLeavesCullOak = getModelCull("oak", list);
        SmartLeaves.modelLeavesCullSpruce = getModelCull("spruce", list);
        SmartLeaves.generalQuadsCullAcacia = getGeneralQuadsSafe(SmartLeaves.modelLeavesCullAcacia);
        SmartLeaves.generalQuadsCullBirch = getGeneralQuadsSafe(SmartLeaves.modelLeavesCullBirch);
        SmartLeaves.generalQuadsCullDarkOak = getGeneralQuadsSafe(SmartLeaves.modelLeavesCullDarkOak);
        SmartLeaves.generalQuadsCullJungle = getGeneralQuadsSafe(SmartLeaves.modelLeavesCullJungle);
        SmartLeaves.generalQuadsCullOak = getGeneralQuadsSafe(SmartLeaves.modelLeavesCullOak);
        SmartLeaves.generalQuadsCullSpruce = getGeneralQuadsSafe(SmartLeaves.modelLeavesCullSpruce);
        SmartLeaves.modelLeavesDoubleAcacia = getModelDoubleFace(SmartLeaves.modelLeavesCullAcacia);
        SmartLeaves.modelLeavesDoubleBirch = getModelDoubleFace(SmartLeaves.modelLeavesCullBirch);
        SmartLeaves.modelLeavesDoubleDarkOak = getModelDoubleFace(SmartLeaves.modelLeavesCullDarkOak);
        SmartLeaves.modelLeavesDoubleJungle = getModelDoubleFace(SmartLeaves.modelLeavesCullJungle);
        SmartLeaves.modelLeavesDoubleOak = getModelDoubleFace(SmartLeaves.modelLeavesCullOak);
        SmartLeaves.modelLeavesDoubleSpruce = getModelDoubleFace(SmartLeaves.modelLeavesCullSpruce);
        if (list.size() > 0) {
            Config.dbg("Enable face culling: " + Config.arrayToString(list.toArray()));
        }
    }
    
    private static List getGeneralQuadsSafe(final IBakedModel bakedModel) {
        return (bakedModel == null) ? null : bakedModel.func_177550_a();
    }
    
    static IBakedModel getModelCull(final String s, final List list) {
        final ModelManager modelManager = Config.getModelManager();
        if (modelManager == null) {
            return null;
        }
        if (Config.getDefiningResourcePack(new ResourceLocation("blockstates/" + s + "_leaves.json")) != Config.getDefaultResourcePack()) {
            return null;
        }
        if (Config.getDefiningResourcePack(new ResourceLocation("models/block/" + s + "_leaves.json")) != Config.getDefaultResourcePack()) {
            return null;
        }
        final IBakedModel model = modelManager.getModel(new ModelResourceLocation(String.valueOf(s) + "_leaves", "normal"));
        if (model == null || model == modelManager.getMissingModel()) {
            return null;
        }
        final List func_177550_a = model.func_177550_a();
        if (func_177550_a.size() == 0) {
            return model;
        }
        if (func_177550_a.size() != 6) {
            return null;
        }
        for (final BakedQuad bakedQuad : func_177550_a) {
            final List func_177551_a = model.func_177551_a(bakedQuad.getFace());
            if (func_177551_a.size() > 0) {
                return null;
            }
            func_177551_a.add(bakedQuad);
        }
        func_177550_a.clear();
        list.add(String.valueOf(s) + "_leaves");
        return model;
    }
    
    private static IBakedModel getModelDoubleFace(final IBakedModel p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: aconst_null    
        //     5: areturn        
        //     6: aload_0        
        //     7: invokeinterface net/minecraft/client/resources/model/IBakedModel.func_177550_a:()Ljava/util/List;
        //    12: invokeinterface java/util/List.size:()I
        //    17: ifle            60
        //    20: new             Ljava/lang/StringBuilder;
        //    23: dup            
        //    24: ldc             "SmartLeaves: Model is not cube, general quads: "
        //    26: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    29: aload_0        
        //    30: invokeinterface net/minecraft/client/resources/model/IBakedModel.func_177550_a:()Ljava/util/List;
        //    35: invokeinterface java/util/List.size:()I
        //    40: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    43: ldc             ", model: "
        //    45: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    48: aload_0        
        //    49: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    52: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    55: invokestatic    optifine/Config.warn:(Ljava/lang/String;)V
        //    58: aload_0        
        //    59: areturn        
        //    60: getstatic       net/minecraft/util/EnumFacing.VALUES:[Lnet/minecraft/util/EnumFacing;
        //    63: astore_1       
        //    64: goto            139
        //    67: aload_1        
        //    68: iconst_0       
        //    69: aaload         
        //    70: astore_3       
        //    71: aload_0        
        //    72: aload_3        
        //    73: invokeinterface net/minecraft/client/resources/model/IBakedModel.func_177551_a:(Lnet/minecraft/util/EnumFacing;)Ljava/util/List;
        //    78: astore          4
        //    80: aload           4
        //    82: invokeinterface java/util/List.size:()I
        //    87: iconst_1       
        //    88: if_icmpeq       136
        //    91: new             Ljava/lang/StringBuilder;
        //    94: dup            
        //    95: ldc             "SmartLeaves: Model is not cube, side: "
        //    97: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   100: aload_3        
        //   101: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   104: ldc             ", quads: "
        //   106: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   109: aload           4
        //   111: invokeinterface java/util/List.size:()I
        //   116: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   119: ldc             ", model: "
        //   121: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   124: aload_0        
        //   125: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   128: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   131: invokestatic    optifine/Config.warn:(Ljava/lang/String;)V
        //   134: aload_0        
        //   135: areturn        
        //   136: iinc            2, 1
        //   139: iconst_0       
        //   140: aload_1        
        //   141: arraylength    
        //   142: if_icmplt       67
        //   145: aload_0        
        //   146: invokestatic    optifine/ModelUtils.duplicateModel:(Lnet/minecraft/client/resources/model/IBakedModel;)Lnet/minecraft/client/resources/model/IBakedModel;
        //   149: astore_2       
        //   150: aload_1        
        //   151: arraylength    
        //   152: anewarray       Ljava/util/List;
        //   155: astore_3       
        //   156: goto            339
        //   159: aload_1        
        //   160: iconst_0       
        //   161: aaload         
        //   162: astore          5
        //   164: aload_2        
        //   165: aload           5
        //   167: invokeinterface net/minecraft/client/resources/model/IBakedModel.func_177551_a:(Lnet/minecraft/util/EnumFacing;)Ljava/util/List;
        //   172: astore          6
        //   174: aload           6
        //   176: iconst_0       
        //   177: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   182: checkcast       Lnet/minecraft/client/renderer/block/model/BakedQuad;
        //   185: astore          7
        //   187: new             Lnet/minecraft/client/renderer/block/model/BakedQuad;
        //   190: dup            
        //   191: aload           7
        //   193: invokevirtual   net/minecraft/client/renderer/block/model/BakedQuad.func_178209_a:()[I
        //   196: invokevirtual   [I.clone:()Ljava/lang/Object;
        //   199: checkcast       [I
        //   202: aload           7
        //   204: invokevirtual   net/minecraft/client/renderer/block/model/BakedQuad.func_178211_c:()I
        //   207: aload           7
        //   209: invokevirtual   net/minecraft/client/renderer/block/model/BakedQuad.getFace:()Lnet/minecraft/util/EnumFacing;
        //   212: aload           7
        //   214: invokevirtual   net/minecraft/client/renderer/block/model/BakedQuad.getSprite:()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //   217: invokespecial   net/minecraft/client/renderer/block/model/BakedQuad.<init>:([IILnet/minecraft/util/EnumFacing;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V
        //   220: astore          8
        //   222: aload           8
        //   224: invokevirtual   net/minecraft/client/renderer/block/model/BakedQuad.func_178209_a:()[I
        //   227: astore          9
        //   229: aload           9
        //   231: invokevirtual   [I.clone:()Ljava/lang/Object;
        //   234: checkcast       [I
        //   237: astore          10
        //   239: aload           9
        //   241: arraylength    
        //   242: iconst_4       
        //   243: idiv           
        //   244: istore          11
        //   246: aload           9
        //   248: iconst_0       
        //   249: iload           11
        //   251: imul           
        //   252: aload           10
        //   254: iconst_3       
        //   255: iload           11
        //   257: imul           
        //   258: iload           11
        //   260: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   263: aload           9
        //   265: iconst_1       
        //   266: iload           11
        //   268: imul           
        //   269: aload           10
        //   271: iconst_2       
        //   272: iload           11
        //   274: imul           
        //   275: iload           11
        //   277: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   280: aload           9
        //   282: iconst_2       
        //   283: iload           11
        //   285: imul           
        //   286: aload           10
        //   288: iconst_1       
        //   289: iload           11
        //   291: imul           
        //   292: iload           11
        //   294: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   297: aload           9
        //   299: iconst_3       
        //   300: iload           11
        //   302: imul           
        //   303: aload           10
        //   305: iconst_0       
        //   306: iload           11
        //   308: imul           
        //   309: iload           11
        //   311: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   314: aload           10
        //   316: iconst_0       
        //   317: aload           9
        //   319: iconst_0       
        //   320: aload           10
        //   322: arraylength    
        //   323: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   326: aload           6
        //   328: aload           8
        //   330: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   335: pop            
        //   336: iinc            4, 1
        //   339: iconst_0       
        //   340: aload_1        
        //   341: arraylength    
        //   342: if_icmplt       159
        //   345: aload_2        
        //   346: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
