package net.minecraft.util;

import java.lang.reflect.*;
import java.util.*;
import com.google.gson.*;

public interface IChatComponent extends Iterable
{
    IChatComponent setChatStyle(final ChatStyle p0);
    
    ChatStyle getChatStyle();
    
    IChatComponent appendText(final String p0);
    
    IChatComponent appendSibling(final IChatComponent p0);
    
    String getUnformattedTextForChat();
    
    String getUnformattedText();
    
    String getFormattedText();
    
    List getSiblings();
    
    IChatComponent createCopy();
    
    public static class Serializer implements JsonDeserializer, JsonSerializer
    {
        private static final Gson GSON;
        private static final String __OBFID;
        
        static {
            __OBFID = "CL_00001263";
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeHierarchyAdapter(IChatComponent.class, new Serializer());
            gsonBuilder.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
            gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
            GSON = gsonBuilder.create();
        }
        
        @Override
        public IChatComponent deserialize(final JsonElement p0, final Type p1, final JsonDeserializationContext p2) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: invokevirtual   com/google/gson/JsonElement.isJsonPrimitive:()Z
            //     4: ifeq            19
            //     7: new             Lnet/minecraft/util/ChatComponentText;
            //    10: dup            
            //    11: aload_1        
            //    12: invokevirtual   com/google/gson/JsonElement.getAsString:()Ljava/lang/String;
            //    15: invokespecial   net/minecraft/util/ChatComponentText.<init>:(Ljava/lang/String;)V
            //    18: areturn        
            //    19: aload_1        
            //    20: invokevirtual   com/google/gson/JsonElement.isJsonObject:()Z
            //    23: ifne            145
            //    26: aload_1        
            //    27: invokevirtual   com/google/gson/JsonElement.isJsonArray:()Z
            //    30: ifeq            113
            //    33: aload_1        
            //    34: invokevirtual   com/google/gson/JsonElement.getAsJsonArray:()Lcom/google/gson/JsonArray;
            //    37: astore          4
            //    39: aconst_null    
            //    40: astore          5
            //    42: aload           4
            //    44: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
            //    47: astore          6
            //    49: goto            100
            //    52: aload           6
            //    54: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
            //    59: checkcast       Lcom/google/gson/JsonElement;
            //    62: astore          7
            //    64: aload_0        
            //    65: aload           7
            //    67: aload           7
            //    69: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
            //    72: aload_3        
            //    73: invokevirtual   net/minecraft/util/IChatComponent$Serializer.deserialize:(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/util/IChatComponent;
            //    76: astore          8
            //    78: aload           5
            //    80: ifnonnull       90
            //    83: aload           8
            //    85: astore          5
            //    87: goto            100
            //    90: aload           5
            //    92: aload           8
            //    94: invokeinterface net/minecraft/util/IChatComponent.appendSibling:(Lnet/minecraft/util/IChatComponent;)Lnet/minecraft/util/IChatComponent;
            //    99: pop            
            //   100: aload           6
            //   102: invokeinterface java/util/Iterator.hasNext:()Z
            //   107: ifne            52
            //   110: aload           5
            //   112: areturn        
            //   113: new             Lcom/google/gson/JsonParseException;
            //   116: dup            
            //   117: new             Ljava/lang/StringBuilder;
            //   120: dup            
            //   121: ldc             "Don't know how to turn "
            //   123: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //   126: aload_1        
            //   127: invokevirtual   com/google/gson/JsonElement.toString:()Ljava/lang/String;
            //   130: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   133: ldc             " into a Component"
            //   135: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   138: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   141: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
            //   144: athrow         
            //   145: aload_1        
            //   146: invokevirtual   com/google/gson/JsonElement.getAsJsonObject:()Lcom/google/gson/JsonObject;
            //   149: astore          4
            //   151: aload           4
            //   153: ldc             "text"
            //   155: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   158: ifeq            183
            //   161: new             Lnet/minecraft/util/ChatComponentText;
            //   164: dup            
            //   165: aload           4
            //   167: ldc             "text"
            //   169: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
            //   172: invokevirtual   com/google/gson/JsonElement.getAsString:()Ljava/lang/String;
            //   175: invokespecial   net/minecraft/util/ChatComponentText.<init>:(Ljava/lang/String;)V
            //   178: astore          5
            //   180: goto            507
            //   183: aload           4
            //   185: ldc             "translate"
            //   187: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   190: ifeq            349
            //   193: aload           4
            //   195: ldc             "translate"
            //   197: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
            //   200: invokevirtual   com/google/gson/JsonElement.getAsString:()Ljava/lang/String;
            //   203: astore          6
            //   205: aload           4
            //   207: ldc             "with"
            //   209: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   212: ifeq            331
            //   215: aload           4
            //   217: ldc             "with"
            //   219: invokevirtual   com/google/gson/JsonObject.getAsJsonArray:(Ljava/lang/String;)Lcom/google/gson/JsonArray;
            //   222: astore          7
            //   224: aload           7
            //   226: invokevirtual   com/google/gson/JsonArray.size:()I
            //   229: anewarray       Ljava/lang/Object;
            //   232: astore          8
            //   234: goto            308
            //   237: aload           8
            //   239: iconst_0       
            //   240: aload_0        
            //   241: aload           7
            //   243: iconst_0       
            //   244: invokevirtual   com/google/gson/JsonArray.get:(I)Lcom/google/gson/JsonElement;
            //   247: aload_2        
            //   248: aload_3        
            //   249: invokevirtual   net/minecraft/util/IChatComponent$Serializer.deserialize:(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/util/IChatComponent;
            //   252: aastore        
            //   253: aload           8
            //   255: iconst_0       
            //   256: aaload         
            //   257: instanceof      Lnet/minecraft/util/ChatComponentText;
            //   260: ifeq            305
            //   263: aload           8
            //   265: iconst_0       
            //   266: aaload         
            //   267: checkcast       Lnet/minecraft/util/ChatComponentText;
            //   270: astore          10
            //   272: aload           10
            //   274: invokevirtual   net/minecraft/util/ChatComponentText.getChatStyle:()Lnet/minecraft/util/ChatStyle;
            //   277: invokevirtual   net/minecraft/util/ChatStyle.isEmpty:()Z
            //   280: ifeq            305
            //   283: aload           10
            //   285: invokevirtual   net/minecraft/util/ChatComponentText.getSiblings:()Ljava/util/List;
            //   288: invokeinterface java/util/List.isEmpty:()Z
            //   293: ifeq            305
            //   296: aload           8
            //   298: iconst_0       
            //   299: aload           10
            //   301: invokevirtual   net/minecraft/util/ChatComponentText.getChatComponentText_TextValue:()Ljava/lang/String;
            //   304: aastore        
            //   305: iinc            9, 1
            //   308: iconst_0       
            //   309: aload           8
            //   311: arraylength    
            //   312: if_icmplt       237
            //   315: new             Lnet/minecraft/util/ChatComponentTranslation;
            //   318: dup            
            //   319: aload           6
            //   321: aload           8
            //   323: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
            //   326: astore          5
            //   328: goto            507
            //   331: new             Lnet/minecraft/util/ChatComponentTranslation;
            //   334: dup            
            //   335: aload           6
            //   337: iconst_0       
            //   338: anewarray       Ljava/lang/Object;
            //   341: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
            //   344: astore          5
            //   346: goto            507
            //   349: aload           4
            //   351: ldc             "score"
            //   353: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   356: ifeq            449
            //   359: aload           4
            //   361: ldc             "score"
            //   363: invokevirtual   com/google/gson/JsonObject.getAsJsonObject:(Ljava/lang/String;)Lcom/google/gson/JsonObject;
            //   366: astore          6
            //   368: aload           6
            //   370: ldc             "name"
            //   372: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   375: ifeq            388
            //   378: aload           6
            //   380: ldc             "objective"
            //   382: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   385: ifne            398
            //   388: new             Lcom/google/gson/JsonParseException;
            //   391: dup            
            //   392: ldc             "A score component needs a least a name and an objective"
            //   394: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
            //   397: athrow         
            //   398: new             Lnet/minecraft/util/ChatComponentScore;
            //   401: dup            
            //   402: aload           6
            //   404: ldc             "name"
            //   406: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectStringFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
            //   409: aload           6
            //   411: ldc             "objective"
            //   413: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectStringFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
            //   416: invokespecial   net/minecraft/util/ChatComponentScore.<init>:(Ljava/lang/String;Ljava/lang/String;)V
            //   419: astore          5
            //   421: aload           6
            //   423: ldc             "value"
            //   425: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   428: ifeq            507
            //   431: aload           5
            //   433: checkcast       Lnet/minecraft/util/ChatComponentScore;
            //   436: aload           6
            //   438: ldc             "value"
            //   440: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectStringFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
            //   443: invokevirtual   net/minecraft/util/ChatComponentScore.func_179997_b:(Ljava/lang/String;)V
            //   446: goto            507
            //   449: aload           4
            //   451: ldc             "selector"
            //   453: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   456: ifne            491
            //   459: new             Lcom/google/gson/JsonParseException;
            //   462: dup            
            //   463: new             Ljava/lang/StringBuilder;
            //   466: dup            
            //   467: ldc             "Don't know how to turn "
            //   469: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //   472: aload_1        
            //   473: invokevirtual   com/google/gson/JsonElement.toString:()Ljava/lang/String;
            //   476: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   479: ldc             " into a Component"
            //   481: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   484: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   487: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
            //   490: athrow         
            //   491: new             Lnet/minecraft/util/ChatComponentSelector;
            //   494: dup            
            //   495: aload           4
            //   497: ldc             "selector"
            //   499: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectStringFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
            //   502: invokespecial   net/minecraft/util/ChatComponentSelector.<init>:(Ljava/lang/String;)V
            //   505: astore          5
            //   507: aload           4
            //   509: ldc             "extra"
            //   511: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   514: ifeq            582
            //   517: aload           4
            //   519: ldc             "extra"
            //   521: invokevirtual   com/google/gson/JsonObject.getAsJsonArray:(Ljava/lang/String;)Lcom/google/gson/JsonArray;
            //   524: astore          6
            //   526: aload           6
            //   528: invokevirtual   com/google/gson/JsonArray.size:()I
            //   531: ifgt            544
            //   534: new             Lcom/google/gson/JsonParseException;
            //   537: dup            
            //   538: ldc             "Unexpected empty array of components"
            //   540: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
            //   543: athrow         
            //   544: goto            573
            //   547: aload           5
            //   549: checkcast       Lnet/minecraft/util/IChatComponent;
            //   552: aload_0        
            //   553: aload           6
            //   555: iconst_0       
            //   556: invokevirtual   com/google/gson/JsonArray.get:(I)Lcom/google/gson/JsonElement;
            //   559: aload_2        
            //   560: aload_3        
            //   561: invokevirtual   net/minecraft/util/IChatComponent$Serializer.deserialize:(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/util/IChatComponent;
            //   564: invokeinterface net/minecraft/util/IChatComponent.appendSibling:(Lnet/minecraft/util/IChatComponent;)Lnet/minecraft/util/IChatComponent;
            //   569: pop            
            //   570: iinc            7, 1
            //   573: iconst_0       
            //   574: aload           6
            //   576: invokevirtual   com/google/gson/JsonArray.size:()I
            //   579: if_icmplt       547
            //   582: aload           5
            //   584: checkcast       Lnet/minecraft/util/IChatComponent;
            //   587: aload_3        
            //   588: aload_1        
            //   589: ldc             Lnet/minecraft/util/ChatStyle;.class
            //   591: invokeinterface com/google/gson/JsonDeserializationContext.deserialize:(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;)Ljava/lang/Object;
            //   596: checkcast       Lnet/minecraft/util/ChatStyle;
            //   599: invokeinterface net/minecraft/util/IChatComponent.setChatStyle:(Lnet/minecraft/util/ChatStyle;)Lnet/minecraft/util/IChatComponent;
            //   604: pop            
            //   605: aload           5
            //   607: checkcast       Lnet/minecraft/util/IChatComponent;
            //   610: areturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        private void serializeChatStyle(final ChatStyle chatStyle, final JsonObject jsonObject, final JsonSerializationContext jsonSerializationContext) {
            final JsonElement serialize = jsonSerializationContext.serialize(chatStyle);
            if (serialize.isJsonObject()) {
                for (final Map.Entry<String, V> entry : ((JsonObject)serialize).entrySet()) {
                    jsonObject.add(entry.getKey(), (JsonElement)entry.getValue());
                }
            }
        }
        
        public JsonElement serialize(final IChatComponent chatComponent, final Type type, final JsonSerializationContext jsonSerializationContext) {
            if (chatComponent instanceof ChatComponentText && chatComponent.getChatStyle().isEmpty() && chatComponent.getSiblings().isEmpty()) {
                return new JsonPrimitive(((ChatComponentText)chatComponent).getChatComponentText_TextValue());
            }
            final JsonObject jsonObject = new JsonObject();
            if (!chatComponent.getChatStyle().isEmpty()) {
                this.serializeChatStyle(chatComponent.getChatStyle(), jsonObject, jsonSerializationContext);
            }
            if (!chatComponent.getSiblings().isEmpty()) {
                final JsonArray jsonArray = new JsonArray();
                for (final IChatComponent chatComponent2 : chatComponent.getSiblings()) {
                    jsonArray.add(this.serialize(chatComponent2, chatComponent2.getClass(), jsonSerializationContext));
                }
                jsonObject.add("extra", jsonArray);
            }
            if (chatComponent instanceof ChatComponentText) {
                jsonObject.addProperty("text", ((ChatComponentText)chatComponent).getChatComponentText_TextValue());
            }
            else if (chatComponent instanceof ChatComponentTranslation) {
                final ChatComponentTranslation chatComponentTranslation = (ChatComponentTranslation)chatComponent;
                jsonObject.addProperty("translate", chatComponentTranslation.getKey());
                if (chatComponentTranslation.getFormatArgs() != null && chatComponentTranslation.getFormatArgs().length > 0) {
                    final JsonArray jsonArray2 = new JsonArray();
                    final Object[] formatArgs = chatComponentTranslation.getFormatArgs();
                    while (0 < formatArgs.length) {
                        final Object o = formatArgs[0];
                        if (o instanceof IChatComponent) {
                            jsonArray2.add(this.serialize((IChatComponent)o, ((IChatComponent)o).getClass(), jsonSerializationContext));
                        }
                        else {
                            jsonArray2.add(new JsonPrimitive(String.valueOf(o)));
                        }
                        int n = 0;
                        ++n;
                    }
                    jsonObject.add("with", jsonArray2);
                }
            }
            else if (chatComponent instanceof ChatComponentScore) {
                final ChatComponentScore chatComponentScore = (ChatComponentScore)chatComponent;
                final JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("name", chatComponentScore.func_179995_g());
                jsonObject2.addProperty("objective", chatComponentScore.func_179994_h());
                jsonObject2.addProperty("value", chatComponentScore.getUnformattedTextForChat());
                jsonObject.add("score", jsonObject2);
            }
            else {
                if (!(chatComponent instanceof ChatComponentSelector)) {
                    throw new IllegalArgumentException("Don't know how to serialize " + chatComponent + " as a Component");
                }
                jsonObject.addProperty("selector", ((ChatComponentSelector)chatComponent).func_179992_g());
            }
            return jsonObject;
        }
        
        public static String componentToJson(final IChatComponent chatComponent) {
            return Serializer.GSON.toJson(chatComponent);
        }
        
        public static IChatComponent jsonToComponent(final String s) {
            return (IChatComponent)Serializer.GSON.fromJson(s, IChatComponent.class);
        }
        
        @Override
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((IChatComponent)o, type, jsonSerializationContext);
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}
