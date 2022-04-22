package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import com.google.gson.*;

public class FontMetadataSectionSerializer extends BaseMetadataSectionSerializer
{
    private static final String __OBFID;
    
    @Override
    public FontMetadataSection deserialize(final JsonElement p0, final Type p1, final JsonDeserializationContext p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/google/gson/JsonElement.getAsJsonObject:()Lcom/google/gson/JsonObject;
        //     4: astore          4
        //     6: sipush          256
        //     9: newarray        F
        //    11: astore          5
        //    13: sipush          256
        //    16: newarray        F
        //    18: astore          6
        //    20: sipush          256
        //    23: newarray        F
        //    25: astore          7
        //    27: fconst_1       
        //    28: fstore          8
        //    30: fconst_0       
        //    31: fstore          9
        //    33: fconst_0       
        //    34: fstore          10
        //    36: aload           4
        //    38: ldc             "characters"
        //    40: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
        //    43: ifeq            385
        //    46: aload           4
        //    48: ldc             "characters"
        //    50: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //    53: invokevirtual   com/google/gson/JsonElement.isJsonObject:()Z
        //    56: ifne            89
        //    59: new             Lcom/google/gson/JsonParseException;
        //    62: dup            
        //    63: new             Ljava/lang/StringBuilder;
        //    66: dup            
        //    67: ldc             "Invalid font->characters: expected object, was "
        //    69: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    72: aload           4
        //    74: ldc             "characters"
        //    76: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //    79: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    82: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    85: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
        //    88: athrow         
        //    89: aload           4
        //    91: ldc             "characters"
        //    93: invokevirtual   com/google/gson/JsonObject.getAsJsonObject:(Ljava/lang/String;)Lcom/google/gson/JsonObject;
        //    96: astore          11
        //    98: aload           11
        //   100: ldc             "default"
        //   102: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
        //   105: ifeq            229
        //   108: aload           11
        //   110: ldc             "default"
        //   112: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   115: invokevirtual   com/google/gson/JsonElement.isJsonObject:()Z
        //   118: ifne            151
        //   121: new             Lcom/google/gson/JsonParseException;
        //   124: dup            
        //   125: new             Ljava/lang/StringBuilder;
        //   128: dup            
        //   129: ldc             "Invalid font->characters->default: expected object, was "
        //   131: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   134: aload           11
        //   136: ldc             "default"
        //   138: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   141: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   144: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   147: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
        //   150: athrow         
        //   151: aload           11
        //   153: ldc             "default"
        //   155: invokevirtual   com/google/gson/JsonObject.getAsJsonObject:(Ljava/lang/String;)Lcom/google/gson/JsonObject;
        //   158: astore          12
        //   160: aload           12
        //   162: ldc             "width"
        //   164: fload           8
        //   166: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectFloatFieldValueOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   169: fstore          8
        //   171: dconst_0       
        //   172: ldc2_w          3.4028234663852886E38
        //   175: fload           8
        //   177: f2d            
        //   178: ldc             "Invalid default width"
        //   180: invokestatic    org/apache/commons/lang3/Validate.inclusiveBetween:(DDDLjava/lang/String;)V
        //   183: aload           12
        //   185: ldc             "spacing"
        //   187: fload           9
        //   189: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectFloatFieldValueOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   192: fstore          9
        //   194: dconst_0       
        //   195: ldc2_w          3.4028234663852886E38
        //   198: fload           9
        //   200: f2d            
        //   201: ldc             "Invalid default spacing"
        //   203: invokestatic    org/apache/commons/lang3/Validate.inclusiveBetween:(DDDLjava/lang/String;)V
        //   206: aload           12
        //   208: ldc             "left"
        //   210: fload           9
        //   212: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectFloatFieldValueOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   215: fstore          10
        //   217: dconst_0       
        //   218: ldc2_w          3.4028234663852886E38
        //   221: fload           10
        //   223: f2d            
        //   224: ldc             "Invalid default left"
        //   226: invokestatic    org/apache/commons/lang3/Validate.inclusiveBetween:(DDDLjava/lang/String;)V
        //   229: goto            378
        //   232: aload           11
        //   234: iconst_0       
        //   235: invokestatic    java/lang/Integer.toString:(I)Ljava/lang/String;
        //   238: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   241: astore          13
        //   243: fload           8
        //   245: fstore          14
        //   247: fload           9
        //   249: fstore          15
        //   251: fload           10
        //   253: fstore          16
        //   255: aload           13
        //   257: ifnull          357
        //   260: aload           13
        //   262: new             Ljava/lang/StringBuilder;
        //   265: dup            
        //   266: ldc             "characters["
        //   268: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   271: iconst_0       
        //   272: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   275: ldc             "]"
        //   277: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   280: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   283: invokestatic    net/minecraft/util/JsonUtils.getElementAsJsonObject:(Lcom/google/gson/JsonElement;Ljava/lang/String;)Lcom/google/gson/JsonObject;
        //   286: astore          17
        //   288: aload           17
        //   290: ldc             "width"
        //   292: fload           8
        //   294: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectFloatFieldValueOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   297: fstore          14
        //   299: dconst_0       
        //   300: ldc2_w          3.4028234663852886E38
        //   303: fload           14
        //   305: f2d            
        //   306: ldc             "Invalid width"
        //   308: invokestatic    org/apache/commons/lang3/Validate.inclusiveBetween:(DDDLjava/lang/String;)V
        //   311: aload           17
        //   313: ldc             "spacing"
        //   315: fload           9
        //   317: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectFloatFieldValueOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   320: fstore          15
        //   322: dconst_0       
        //   323: ldc2_w          3.4028234663852886E38
        //   326: fload           15
        //   328: f2d            
        //   329: ldc             "Invalid spacing"
        //   331: invokestatic    org/apache/commons/lang3/Validate.inclusiveBetween:(DDDLjava/lang/String;)V
        //   334: aload           17
        //   336: ldc             "left"
        //   338: fload           10
        //   340: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectFloatFieldValueOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   343: fstore          16
        //   345: dconst_0       
        //   346: ldc2_w          3.4028234663852886E38
        //   349: fload           16
        //   351: f2d            
        //   352: ldc             "Invalid left"
        //   354: invokestatic    org/apache/commons/lang3/Validate.inclusiveBetween:(DDDLjava/lang/String;)V
        //   357: aload           5
        //   359: iconst_0       
        //   360: fload           14
        //   362: fastore        
        //   363: aload           6
        //   365: iconst_0       
        //   366: fload           15
        //   368: fastore        
        //   369: aload           7
        //   371: iconst_0       
        //   372: fload           16
        //   374: fastore        
        //   375: iinc            12, 1
        //   378: iconst_0       
        //   379: sipush          256
        //   382: if_icmplt       232
        //   385: new             Lnet/minecraft/client/resources/data/FontMetadataSection;
        //   388: dup            
        //   389: aload           5
        //   391: aload           7
        //   393: aload           6
        //   395: invokespecial   net/minecraft/client/resources/data/FontMetadataSection.<init>:([F[F[F)V
        //   398: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public String getSectionName() {
        return "font";
    }
    
    @Override
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
    
    static {
        __OBFID = "CL_00001109";
    }
}
