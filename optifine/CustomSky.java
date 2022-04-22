package optifine;

import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.settings.*;

public class CustomSky
{
    private static CustomSkyLayer[][] worldSkyLayers;
    
    static {
        CustomSky.worldSkyLayers = null;
    }
    
    public static void reset() {
        CustomSky.worldSkyLayers = null;
    }
    
    public static void update() {
        if (Config.isCustomSky()) {
            CustomSky.worldSkyLayers = readCustomSkies();
        }
    }
    
    private static CustomSkyLayer[][] readCustomSkies() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: iconst_0       
        //     3: multianewarray  [[Loptifine/CustomSkyLayer;
        //     7: astore_0       
        //     8: ldc             "mcpatcher/sky/world"
        //    10: astore_1       
        //    11: goto            326
        //    14: new             Ljava/lang/StringBuilder;
        //    17: dup            
        //    18: aload_1        
        //    19: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //    22: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    25: iconst_0       
        //    26: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    29: ldc             "/sky"
        //    31: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    34: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    37: astore          4
        //    39: new             Ljava/util/ArrayList;
        //    42: dup            
        //    43: invokespecial   java/util/ArrayList.<init>:()V
        //    46: astore          5
        //    48: iconst_1       
        //    49: sipush          1000
        //    52: if_icmpge       292
        //    55: new             Ljava/lang/StringBuilder;
        //    58: dup            
        //    59: aload           4
        //    61: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //    64: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    67: iconst_1       
        //    68: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    71: ldc             ".properties"
        //    73: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    76: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    79: astore          7
        //    81: new             Lnet/minecraft/util/ResourceLocation;
        //    84: dup            
        //    85: aload           7
        //    87: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //    90: astore          8
        //    92: aload           8
        //    94: invokestatic    optifine/Config.getResourceStream:(Lnet/minecraft/util/ResourceLocation;)Ljava/io/InputStream;
        //    97: astore          9
        //    99: aload           9
        //   101: ifnonnull       107
        //   104: goto            292
        //   107: new             Ljava/util/Properties;
        //   110: dup            
        //   111: invokespecial   java/util/Properties.<init>:()V
        //   114: astore          10
        //   116: aload           10
        //   118: aload           9
        //   120: invokevirtual   java/util/Properties.load:(Ljava/io/InputStream;)V
        //   123: aload           9
        //   125: invokevirtual   java/io/InputStream.close:()V
        //   128: new             Ljava/lang/StringBuilder;
        //   131: dup            
        //   132: ldc             "CustomSky properties: "
        //   134: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   137: aload           7
        //   139: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   142: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   145: invokestatic    optifine/Config.dbg:(Ljava/lang/String;)V
        //   148: new             Ljava/lang/StringBuilder;
        //   151: dup            
        //   152: aload           4
        //   154: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   157: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   160: iconst_1       
        //   161: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   164: ldc             ".png"
        //   166: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   169: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   172: astore          11
        //   174: new             Loptifine/CustomSkyLayer;
        //   177: dup            
        //   178: aload           10
        //   180: aload           11
        //   182: invokespecial   optifine/CustomSkyLayer.<init>:(Ljava/util/Properties;Ljava/lang/String;)V
        //   185: astore          12
        //   187: aload           12
        //   189: aload           7
        //   191: invokevirtual   optifine/CustomSkyLayer.isValid:(Ljava/lang/String;)Z
        //   194: ifeq            286
        //   197: new             Lnet/minecraft/util/ResourceLocation;
        //   200: dup            
        //   201: aload           12
        //   203: getfield        optifine/CustomSkyLayer.source:Ljava/lang/String;
        //   206: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //   209: astore          13
        //   211: aload           13
        //   213: invokestatic    optifine/TextureUtils.getTexture:(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/renderer/texture/ITextureObject;
        //   216: astore          14
        //   218: aload           14
        //   220: ifnonnull       246
        //   223: new             Ljava/lang/StringBuilder;
        //   226: dup            
        //   227: ldc             "CustomSky: Texture not found: "
        //   229: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   232: aload           13
        //   234: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   237: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   240: invokestatic    optifine/Config.log:(Ljava/lang/String;)V
        //   243: goto            286
        //   246: aload           12
        //   248: aload           14
        //   250: invokeinterface net/minecraft/client/renderer/texture/ITextureObject.getGlTextureId:()I
        //   255: putfield        optifine/CustomSkyLayer.textureId:I
        //   258: aload           5
        //   260: aload           12
        //   262: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   265: pop            
        //   266: aload           9
        //   268: invokevirtual   java/io/InputStream.close:()V
        //   271: goto            286
        //   274: astore          8
        //   276: goto            292
        //   279: astore          8
        //   281: aload           8
        //   283: invokevirtual   java/io/IOException.printStackTrace:()V
        //   286: iinc            6, 1
        //   289: goto            48
        //   292: aload           5
        //   294: invokevirtual   java/util/ArrayList.size:()I
        //   297: ifle            323
        //   300: aload           5
        //   302: aload           5
        //   304: invokevirtual   java/util/ArrayList.size:()I
        //   307: anewarray       Loptifine/CustomSkyLayer;
        //   310: invokevirtual   java/util/ArrayList.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   313: checkcast       [Loptifine/CustomSkyLayer;
        //   316: astore          7
        //   318: aload_0        
        //   319: iconst_0       
        //   320: aload           7
        //   322: aastore        
        //   323: iinc            3, 1
        //   326: iconst_0       
        //   327: aload_0        
        //   328: arraylength    
        //   329: if_icmplt       14
        //   332: iconst_m1      
        //   333: ifge            338
        //   336: aconst_null    
        //   337: areturn        
        //   338: iconst_0       
        //   339: iconst_0       
        //   340: multianewarray  [[Loptifine/CustomSkyLayer;
        //   344: astore          4
        //   346: goto            359
        //   349: aload           4
        //   351: iconst_0       
        //   352: aload_0        
        //   353: iconst_0       
        //   354: aaload         
        //   355: aastore        
        //   356: iinc            5, 1
        //   359: iconst_0       
        //   360: aload           4
        //   362: arraylength    
        //   363: if_icmplt       349
        //   366: aload           4
        //   368: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static void renderSky(final World world, final TextureManager textureManager, final float n, final float n2) {
        if (CustomSky.worldSkyLayers != null) {
            Config.getGameSettings();
            if (GameSettings.renderDistanceChunks >= 8) {
                final int dimensionId = world.provider.getDimensionId();
                if (dimensionId >= 0 && dimensionId < CustomSky.worldSkyLayers.length) {
                    final CustomSkyLayer[] array = CustomSky.worldSkyLayers[dimensionId];
                    if (array != null) {
                        final int n3 = (int)(world.getWorldTime() % 24000L);
                        while (0 < array.length) {
                            final CustomSkyLayer customSkyLayer = array[0];
                            if (customSkyLayer.isActive(world, n3)) {
                                customSkyLayer.render(n3, n, n2);
                            }
                            int n4 = 0;
                            ++n4;
                        }
                        Blender.clearBlend(n2);
                    }
                }
            }
        }
    }
    
    public static boolean hasSkyLayers(final World world) {
        if (CustomSky.worldSkyLayers == null) {
            return false;
        }
        Config.getGameSettings();
        if (GameSettings.renderDistanceChunks < 8) {
            return false;
        }
        final int dimensionId = world.provider.getDimensionId();
        if (dimensionId >= 0 && dimensionId < CustomSky.worldSkyLayers.length) {
            final CustomSkyLayer[] array = CustomSky.worldSkyLayers[dimensionId];
            return array != null && array.length > 0;
        }
        return false;
    }
}
