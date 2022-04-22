package optifine;

import java.awt.*;
import com.google.gson.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;

public class PlayerItemParser
{
    private static JsonParser jsonParser;
    public static final String ITEM_TYPE;
    public static final String ITEM_TEXTURE_SIZE;
    public static final String ITEM_USE_PLAYER_TEXTURE;
    public static final String ITEM_MODELS;
    public static final String MODEL_ID;
    public static final String MODEL_BASE_ID;
    public static final String MODEL_TYPE;
    public static final String MODEL_ATTACH_TO;
    public static final String MODEL_INVERT_AXIS;
    public static final String MODEL_MIRROR_TEXTURE;
    public static final String MODEL_TRANSLATE;
    public static final String MODEL_ROTATE;
    public static final String MODEL_SCALE;
    public static final String MODEL_BOXES;
    public static final String MODEL_SPRITES;
    public static final String MODEL_SUBMODEL;
    public static final String MODEL_SUBMODELS;
    public static final String BOX_TEXTURE_OFFSET;
    public static final String BOX_COORDINATES;
    public static final String BOX_SIZE_ADD;
    public static final String ITEM_TYPE_MODEL;
    public static final String MODEL_TYPE_BOX;
    
    static {
        ITEM_TEXTURE_SIZE = "textureSize";
        BOX_COORDINATES = "coordinates";
        BOX_SIZE_ADD = "sizeAdd";
        BOX_TEXTURE_OFFSET = "textureOffset";
        MODEL_TYPE = "type";
        MODEL_ATTACH_TO = "attachTo";
        MODEL_SPRITES = "sprites";
        MODEL_SUBMODEL = "submodel";
        MODEL_TYPE_BOX = "ModelBox";
        ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
        MODEL_ID = "id";
        ITEM_TYPE_MODEL = "PlayerItem";
        ITEM_TYPE = "type";
        MODEL_INVERT_AXIS = "invertAxis";
        MODEL_SUBMODELS = "submodels";
        MODEL_TRANSLATE = "translate";
        MODEL_ROTATE = "rotate";
        ITEM_MODELS = "models";
        MODEL_BASE_ID = "baseId";
        MODEL_MIRROR_TEXTURE = "mirrorTexture";
        MODEL_BOXES = "boxes";
        MODEL_SCALE = "scale";
        PlayerItemParser.jsonParser = new JsonParser();
    }
    
    public static PlayerItemModel parseItemModel(final JsonObject jsonObject) {
        final String string = Json.getString(jsonObject, "type");
        if (!Config.equals(string, "PlayerItem")) {
            throw new JsonParseException("Unknown model type: " + string);
        }
        final int[] intArray = Json.parseIntArray(jsonObject.get("textureSize"), 2);
        checkNull(intArray, "Missing texture size");
        final Dimension dimension = new Dimension(intArray[0], intArray[1]);
        final boolean boolean1 = Json.getBoolean(jsonObject, "usePlayerTexture", false);
        final JsonArray jsonArray = (JsonArray)jsonObject.get("models");
        checkNull(jsonArray, "Missing elements");
        final HashMap<Object, JsonObject> hashMap = (HashMap<Object, JsonObject>)new HashMap<String, JsonObject>();
        final ArrayList<PlayerItemRenderer> list = new ArrayList<PlayerItemRenderer>();
        new ArrayList();
        while (0 < jsonArray.size()) {
            final JsonObject jsonObject2 = (JsonObject)jsonArray.get(0);
            final String string2 = Json.getString(jsonObject2, "baseId");
            Label_0353: {
                if (string2 != null) {
                    final JsonObject jsonObject3 = hashMap.get(string2);
                    if (jsonObject3 == null) {
                        Config.warn("BaseID not found: " + string2);
                        break Label_0353;
                    }
                    for (final Map.Entry<String, V> entry : jsonObject3.entrySet()) {
                        if (!jsonObject2.has(entry.getKey())) {
                            jsonObject2.add(entry.getKey(), (JsonElement)entry.getValue());
                        }
                    }
                }
                final String string3 = Json.getString(jsonObject2, "id");
                if (string3 != null) {
                    if (!hashMap.containsKey(string3)) {
                        hashMap.put(string3, jsonObject2);
                    }
                    else {
                        Config.warn("Duplicate model ID: " + string3);
                    }
                }
                final PlayerItemRenderer itemRenderer = parseItemRenderer(jsonObject2, dimension);
                if (itemRenderer != null) {
                    list.add(itemRenderer);
                }
            }
            int n = 0;
            ++n;
        }
        return new PlayerItemModel(dimension, boolean1, list.toArray(new PlayerItemRenderer[list.size()]));
    }
    
    private static void checkNull(final Object o, final String s) {
        if (o == null) {
            throw new JsonParseException(s);
        }
    }
    
    private static ResourceLocation makeResourceLocation(final String s) {
        final int index = s.indexOf(58);
        if (index < 0) {
            return new ResourceLocation(s);
        }
        return new ResourceLocation(s.substring(0, index), s.substring(index + 1));
    }
    
    private static int parseAttachModel(final String s) {
        if (s == null) {
            return 0;
        }
        if (s.equals("body")) {
            return 0;
        }
        if (s.equals("head")) {
            return 1;
        }
        if (s.equals("leftArm")) {
            return 2;
        }
        if (s.equals("rightArm")) {
            return 3;
        }
        if (s.equals("leftLeg")) {
            return 4;
        }
        if (s.equals("rightLeg")) {
            return 5;
        }
        if (s.equals("cape")) {
            return 6;
        }
        Config.warn("Unknown attachModel: " + s);
        return 0;
    }
    
    private static PlayerItemRenderer parseItemRenderer(final JsonObject jsonObject, final Dimension dimension) {
        final String string = Json.getString(jsonObject, "type");
        if (!Config.equals(string, "ModelBox")) {
            Config.warn("Unknown model type: " + string);
            return null;
        }
        final int attachModel = parseAttachModel(Json.getString(jsonObject, "attachTo"));
        final float float1 = Json.getFloat(jsonObject, "scale", 1.0f);
        final ModelPlayerItem modelPlayerItem = new ModelPlayerItem();
        modelPlayerItem.textureWidth = dimension.width;
        modelPlayerItem.textureHeight = dimension.height;
        return new PlayerItemRenderer(attachModel, float1, parseModelRenderer(jsonObject, modelPlayerItem));
    }
    
    private static ModelRenderer parseModelRenderer(final JsonObject p0, final ModelBase p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: aload_1        
        //     5: invokespecial   net/minecraft/client/model/ModelRenderer.<init>:(Lnet/minecraft/client/model/ModelBase;)V
        //     8: astore_2       
        //     9: aload_0        
        //    10: ldc             "invertAxis"
        //    12: ldc_w           ""
        //    15: invokestatic    optifine/Json.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //    18: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //    21: astore_3       
        //    22: aload_3        
        //    23: ldc_w           "x"
        //    26: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    29: istore          4
        //    31: aload_3        
        //    32: ldc_w           "y"
        //    35: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    38: istore          5
        //    40: aload_3        
        //    41: ldc_w           "z"
        //    44: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    47: istore          6
        //    49: aload_0        
        //    50: ldc             "translate"
        //    52: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //    55: iconst_3       
        //    56: iconst_3       
        //    57: newarray        F
        //    59: invokestatic    optifine/Json.parseFloatArray:(Lcom/google/gson/JsonElement;I[F)[F
        //    62: astore          7
        //    64: iload           4
        //    66: ifeq            78
        //    69: aload           7
        //    71: iconst_0       
        //    72: aload           7
        //    74: iconst_0       
        //    75: faload         
        //    76: fneg           
        //    77: fastore        
        //    78: iload           5
        //    80: ifeq            92
        //    83: aload           7
        //    85: iconst_1       
        //    86: aload           7
        //    88: iconst_1       
        //    89: faload         
        //    90: fneg           
        //    91: fastore        
        //    92: iload           6
        //    94: ifeq            106
        //    97: aload           7
        //    99: iconst_2       
        //   100: aload           7
        //   102: iconst_2       
        //   103: faload         
        //   104: fneg           
        //   105: fastore        
        //   106: aload_0        
        //   107: ldc             "rotate"
        //   109: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   112: iconst_3       
        //   113: iconst_3       
        //   114: newarray        F
        //   116: invokestatic    optifine/Json.parseFloatArray:(Lcom/google/gson/JsonElement;I[F)[F
        //   119: astore          8
        //   121: goto            143
        //   124: aload           8
        //   126: iconst_0       
        //   127: aload           8
        //   129: iconst_0       
        //   130: faload         
        //   131: ldc_w           180.0
        //   134: fdiv           
        //   135: ldc_w           3.1415927
        //   138: fmul           
        //   139: fastore        
        //   140: iinc            9, 1
        //   143: iconst_0       
        //   144: aload           8
        //   146: arraylength    
        //   147: if_icmplt       124
        //   150: iload           4
        //   152: ifeq            164
        //   155: aload           8
        //   157: iconst_0       
        //   158: aload           8
        //   160: iconst_0       
        //   161: faload         
        //   162: fneg           
        //   163: fastore        
        //   164: iload           5
        //   166: ifeq            178
        //   169: aload           8
        //   171: iconst_1       
        //   172: aload           8
        //   174: iconst_1       
        //   175: faload         
        //   176: fneg           
        //   177: fastore        
        //   178: iload           6
        //   180: ifeq            192
        //   183: aload           8
        //   185: iconst_2       
        //   186: aload           8
        //   188: iconst_2       
        //   189: faload         
        //   190: fneg           
        //   191: fastore        
        //   192: aload_2        
        //   193: aload           7
        //   195: iconst_0       
        //   196: faload         
        //   197: aload           7
        //   199: iconst_1       
        //   200: faload         
        //   201: aload           7
        //   203: iconst_2       
        //   204: faload         
        //   205: invokevirtual   net/minecraft/client/model/ModelRenderer.setRotationPoint:(FFF)V
        //   208: aload_2        
        //   209: aload           8
        //   211: iconst_0       
        //   212: faload         
        //   213: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   216: aload_2        
        //   217: aload           8
        //   219: iconst_1       
        //   220: faload         
        //   221: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleY:F
        //   224: aload_2        
        //   225: aload           8
        //   227: iconst_2       
        //   228: faload         
        //   229: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleZ:F
        //   232: aload_0        
        //   233: ldc             "mirrorTexture"
        //   235: ldc_w           ""
        //   238: invokestatic    optifine/Json.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   241: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //   244: astore          9
        //   246: aload           9
        //   248: ldc_w           "u"
        //   251: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   254: istore          10
        //   256: aload           9
        //   258: ldc_w           "v"
        //   261: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   264: istore          11
        //   266: iload           10
        //   268: ifeq            276
        //   271: aload_2        
        //   272: iconst_1       
        //   273: putfield        net/minecraft/client/model/ModelRenderer.mirror:Z
        //   276: iload           11
        //   278: ifeq            286
        //   281: aload_2        
        //   282: iconst_1       
        //   283: putfield        net/minecraft/client/model/ModelRenderer.mirrorV:Z
        //   286: aload_0        
        //   287: ldc             "boxes"
        //   289: invokevirtual   com/google/gson/JsonObject.getAsJsonArray:(Ljava/lang/String;)Lcom/google/gson/JsonArray;
        //   292: astore          12
        //   294: aload           12
        //   296: ifnull          497
        //   299: goto            488
        //   302: aload           12
        //   304: iconst_0       
        //   305: invokevirtual   com/google/gson/JsonArray.get:(I)Lcom/google/gson/JsonElement;
        //   308: invokevirtual   com/google/gson/JsonElement.getAsJsonObject:()Lcom/google/gson/JsonObject;
        //   311: astore          13
        //   313: aload           13
        //   315: ldc             "textureOffset"
        //   317: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   320: iconst_2       
        //   321: invokestatic    optifine/Json.parseIntArray:(Lcom/google/gson/JsonElement;I)[I
        //   324: astore          15
        //   326: aload           15
        //   328: ifnonnull       342
        //   331: new             Lcom/google/gson/JsonParseException;
        //   334: dup            
        //   335: ldc_w           "Texture offset not specified"
        //   338: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
        //   341: athrow         
        //   342: aload           13
        //   344: ldc             "coordinates"
        //   346: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   349: bipush          6
        //   351: invokestatic    optifine/Json.parseFloatArray:(Lcom/google/gson/JsonElement;I)[F
        //   354: astore          16
        //   356: aload           16
        //   358: ifnonnull       372
        //   361: new             Lcom/google/gson/JsonParseException;
        //   364: dup            
        //   365: ldc_w           "Coordinates not specified"
        //   368: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
        //   371: athrow         
        //   372: iload           4
        //   374: ifeq            391
        //   377: aload           16
        //   379: iconst_0       
        //   380: aload           16
        //   382: iconst_0       
        //   383: faload         
        //   384: fneg           
        //   385: aload           16
        //   387: iconst_3       
        //   388: faload         
        //   389: fsub           
        //   390: fastore        
        //   391: iload           5
        //   393: ifeq            410
        //   396: aload           16
        //   398: iconst_1       
        //   399: aload           16
        //   401: iconst_1       
        //   402: faload         
        //   403: fneg           
        //   404: aload           16
        //   406: iconst_4       
        //   407: faload         
        //   408: fsub           
        //   409: fastore        
        //   410: iload           6
        //   412: ifeq            429
        //   415: aload           16
        //   417: iconst_2       
        //   418: aload           16
        //   420: iconst_2       
        //   421: faload         
        //   422: fneg           
        //   423: aload           16
        //   425: iconst_5       
        //   426: faload         
        //   427: fsub           
        //   428: fastore        
        //   429: aload           13
        //   431: ldc             "sizeAdd"
        //   433: fconst_0       
        //   434: invokestatic    optifine/Json.getFloat:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   437: fstore          17
        //   439: aload_2        
        //   440: aload           15
        //   442: iconst_0       
        //   443: iaload         
        //   444: aload           15
        //   446: iconst_1       
        //   447: iaload         
        //   448: invokevirtual   net/minecraft/client/model/ModelRenderer.setTextureOffset:(II)Lnet/minecraft/client/model/ModelRenderer;
        //   451: pop            
        //   452: aload_2        
        //   453: aload           16
        //   455: iconst_0       
        //   456: faload         
        //   457: aload           16
        //   459: iconst_1       
        //   460: faload         
        //   461: aload           16
        //   463: iconst_2       
        //   464: faload         
        //   465: aload           16
        //   467: iconst_3       
        //   468: faload         
        //   469: f2i            
        //   470: aload           16
        //   472: iconst_4       
        //   473: faload         
        //   474: f2i            
        //   475: aload           16
        //   477: iconst_5       
        //   478: faload         
        //   479: f2i            
        //   480: fload           17
        //   482: invokevirtual   net/minecraft/client/model/ModelRenderer.addBox:(FFFIIIF)V
        //   485: iinc            14, 1
        //   488: iconst_0       
        //   489: aload           12
        //   491: invokevirtual   com/google/gson/JsonArray.size:()I
        //   494: if_icmplt       302
        //   497: aload_0        
        //   498: ldc             "sprites"
        //   500: invokevirtual   com/google/gson/JsonObject.getAsJsonArray:(Ljava/lang/String;)Lcom/google/gson/JsonArray;
        //   503: astore          14
        //   505: aload           14
        //   507: ifnull          708
        //   510: goto            699
        //   513: aload           14
        //   515: iconst_0       
        //   516: invokevirtual   com/google/gson/JsonArray.get:(I)Lcom/google/gson/JsonElement;
        //   519: invokevirtual   com/google/gson/JsonElement.getAsJsonObject:()Lcom/google/gson/JsonObject;
        //   522: astore          16
        //   524: aload           16
        //   526: ldc             "textureOffset"
        //   528: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   531: iconst_2       
        //   532: invokestatic    optifine/Json.parseIntArray:(Lcom/google/gson/JsonElement;I)[I
        //   535: astore          17
        //   537: aload           17
        //   539: ifnonnull       553
        //   542: new             Lcom/google/gson/JsonParseException;
        //   545: dup            
        //   546: ldc_w           "Texture offset not specified"
        //   549: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
        //   552: athrow         
        //   553: aload           16
        //   555: ldc             "coordinates"
        //   557: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   560: bipush          6
        //   562: invokestatic    optifine/Json.parseFloatArray:(Lcom/google/gson/JsonElement;I)[F
        //   565: astore          18
        //   567: aload           18
        //   569: ifnonnull       583
        //   572: new             Lcom/google/gson/JsonParseException;
        //   575: dup            
        //   576: ldc_w           "Coordinates not specified"
        //   579: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
        //   582: athrow         
        //   583: iload           4
        //   585: ifeq            602
        //   588: aload           18
        //   590: iconst_0       
        //   591: aload           18
        //   593: iconst_0       
        //   594: faload         
        //   595: fneg           
        //   596: aload           18
        //   598: iconst_3       
        //   599: faload         
        //   600: fsub           
        //   601: fastore        
        //   602: iload           5
        //   604: ifeq            621
        //   607: aload           18
        //   609: iconst_1       
        //   610: aload           18
        //   612: iconst_1       
        //   613: faload         
        //   614: fneg           
        //   615: aload           18
        //   617: iconst_4       
        //   618: faload         
        //   619: fsub           
        //   620: fastore        
        //   621: iload           6
        //   623: ifeq            640
        //   626: aload           18
        //   628: iconst_2       
        //   629: aload           18
        //   631: iconst_2       
        //   632: faload         
        //   633: fneg           
        //   634: aload           18
        //   636: iconst_5       
        //   637: faload         
        //   638: fsub           
        //   639: fastore        
        //   640: aload           16
        //   642: ldc             "sizeAdd"
        //   644: fconst_0       
        //   645: invokestatic    optifine/Json.getFloat:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   648: fstore          19
        //   650: aload_2        
        //   651: aload           17
        //   653: iconst_0       
        //   654: iaload         
        //   655: aload           17
        //   657: iconst_1       
        //   658: iaload         
        //   659: invokevirtual   net/minecraft/client/model/ModelRenderer.setTextureOffset:(II)Lnet/minecraft/client/model/ModelRenderer;
        //   662: pop            
        //   663: aload_2        
        //   664: aload           18
        //   666: iconst_0       
        //   667: faload         
        //   668: aload           18
        //   670: iconst_1       
        //   671: faload         
        //   672: aload           18
        //   674: iconst_2       
        //   675: faload         
        //   676: aload           18
        //   678: iconst_3       
        //   679: faload         
        //   680: f2i            
        //   681: aload           18
        //   683: iconst_4       
        //   684: faload         
        //   685: f2i            
        //   686: aload           18
        //   688: iconst_5       
        //   689: faload         
        //   690: f2i            
        //   691: fload           19
        //   693: invokevirtual   net/minecraft/client/model/ModelRenderer.addSprite:(FFFIIIF)V
        //   696: iinc            15, 1
        //   699: iconst_0       
        //   700: aload           14
        //   702: invokevirtual   com/google/gson/JsonArray.size:()I
        //   705: if_icmplt       513
        //   708: aload_0        
        //   709: ldc             "submodel"
        //   711: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   714: checkcast       Lcom/google/gson/JsonObject;
        //   717: astore          13
        //   719: aload           13
        //   721: ifnull          738
        //   724: aload           13
        //   726: aload_1        
        //   727: invokestatic    optifine/PlayerItemParser.parseModelRenderer:(Lcom/google/gson/JsonObject;Lnet/minecraft/client/model/ModelBase;)Lnet/minecraft/client/model/ModelRenderer;
        //   730: astore          15
        //   732: aload_2        
        //   733: aload           15
        //   735: invokevirtual   net/minecraft/client/model/ModelRenderer.addChild:(Lnet/minecraft/client/model/ModelRenderer;)V
        //   738: aload_0        
        //   739: ldc             "submodels"
        //   741: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   744: checkcast       Lcom/google/gson/JsonArray;
        //   747: astore          15
        //   749: aload           15
        //   751: ifnull          794
        //   754: goto            785
        //   757: aload           15
        //   759: iconst_0       
        //   760: invokevirtual   com/google/gson/JsonArray.get:(I)Lcom/google/gson/JsonElement;
        //   763: checkcast       Lcom/google/gson/JsonObject;
        //   766: astore          17
        //   768: aload           17
        //   770: aload_1        
        //   771: invokestatic    optifine/PlayerItemParser.parseModelRenderer:(Lcom/google/gson/JsonObject;Lnet/minecraft/client/model/ModelBase;)Lnet/minecraft/client/model/ModelRenderer;
        //   774: astore          18
        //   776: aload_2        
        //   777: aload           18
        //   779: invokevirtual   net/minecraft/client/model/ModelRenderer.addChild:(Lnet/minecraft/client/model/ModelRenderer;)V
        //   782: iinc            16, 1
        //   785: iconst_0       
        //   786: aload           15
        //   788: invokevirtual   com/google/gson/JsonArray.size:()I
        //   791: if_icmplt       757
        //   794: aload_2        
        //   795: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
