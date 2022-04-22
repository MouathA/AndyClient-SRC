package net.minecraft.client.shader;

import org.apache.logging.log4j.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class ShaderManager
{
    private static final Logger logger;
    private static final ShaderDefault defaultShaderUniform;
    private static ShaderManager staticShaderManager;
    private static boolean field_148000_e;
    private final Map shaderSamplers;
    private final List samplerNames;
    private final List shaderSamplerLocations;
    private final List shaderUniforms;
    private final List shaderUniformLocations;
    private final Map mappedShaderUniforms;
    private final int program;
    private final String programFilename;
    private final boolean useFaceCulling;
    private boolean isDirty;
    private final JsonBlendingMode field_148016_p;
    private final List field_148015_q;
    private final List field_148014_r;
    private final ShaderLoader vertexShaderLoader;
    private final ShaderLoader fragmentShaderLoader;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001040";
        logger = LogManager.getLogger();
        defaultShaderUniform = new ShaderDefault();
        ShaderManager.staticShaderManager = null;
        ShaderManager.field_148000_e = true;
    }
    
    public ShaderManager(final IResourceManager p0, final String p1) throws JsonException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   java/lang/Object.<init>:()V
        //     4: aload_0        
        //     5: invokestatic    com/google/common/collect/Maps.newHashMap:()Ljava/util/HashMap;
        //     8: putfield        net/minecraft/client/shader/ShaderManager.shaderSamplers:Ljava/util/Map;
        //    11: aload_0        
        //    12: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    15: putfield        net/minecraft/client/shader/ShaderManager.samplerNames:Ljava/util/List;
        //    18: aload_0        
        //    19: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    22: putfield        net/minecraft/client/shader/ShaderManager.shaderSamplerLocations:Ljava/util/List;
        //    25: aload_0        
        //    26: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    29: putfield        net/minecraft/client/shader/ShaderManager.shaderUniforms:Ljava/util/List;
        //    32: aload_0        
        //    33: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    36: putfield        net/minecraft/client/shader/ShaderManager.shaderUniformLocations:Ljava/util/List;
        //    39: aload_0        
        //    40: invokestatic    com/google/common/collect/Maps.newHashMap:()Ljava/util/HashMap;
        //    43: putfield        net/minecraft/client/shader/ShaderManager.mappedShaderUniforms:Ljava/util/Map;
        //    46: new             Lcom/google/gson/JsonParser;
        //    49: dup            
        //    50: invokespecial   com/google/gson/JsonParser.<init>:()V
        //    53: astore_3       
        //    54: new             Lnet/minecraft/util/ResourceLocation;
        //    57: dup            
        //    58: new             Ljava/lang/StringBuilder;
        //    61: dup            
        //    62: ldc             "shaders/program/"
        //    64: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    67: aload_2        
        //    68: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    71: ldc             ".json"
        //    73: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    76: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    79: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //    82: astore          4
        //    84: aload_0        
        //    85: aload_2        
        //    86: putfield        net/minecraft/client/shader/ShaderManager.programFilename:Ljava/lang/String;
        //    89: aconst_null    
        //    90: astore          5
        //    92: aload_1        
        //    93: aload           4
        //    95: invokeinterface net/minecraft/client/resources/IResourceManager.getResource:(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/resources/IResource;
        //   100: invokeinterface net/minecraft/client/resources/IResource.getInputStream:()Ljava/io/InputStream;
        //   105: astore          5
        //   107: aload_3        
        //   108: aload           5
        //   110: getstatic       com/google/common/base/Charsets.UTF_8:Ljava/nio/charset/Charset;
        //   113: invokestatic    org/apache/commons/io/IOUtils.toString:(Ljava/io/InputStream;Ljava/nio/charset/Charset;)Ljava/lang/String;
        //   116: invokevirtual   com/google/gson/JsonParser.parse:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   119: invokevirtual   com/google/gson/JsonElement.getAsJsonObject:()Lcom/google/gson/JsonObject;
        //   122: astore          6
        //   124: aload           6
        //   126: ldc             "vertex"
        //   128: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectStringFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //   131: astore          7
        //   133: aload           6
        //   135: ldc             "fragment"
        //   137: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectStringFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //   140: astore          8
        //   142: aload           6
        //   144: ldc             "samplers"
        //   146: aconst_null    
        //   147: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectJsonArrayFieldOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;Lcom/google/gson/JsonArray;)Lcom/google/gson/JsonArray;
        //   150: astore          9
        //   152: aload           9
        //   154: ifnull          239
        //   157: aload           9
        //   159: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
        //   162: astore          11
        //   164: goto            229
        //   167: aload           11
        //   169: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   174: checkcast       Lcom/google/gson/JsonElement;
        //   177: astore          12
        //   179: aload_0        
        //   180: aload           12
        //   182: invokespecial   net/minecraft/client/shader/ShaderManager.parseSampler:(Lcom/google/gson/JsonElement;)V
        //   185: goto            226
        //   188: astore          13
        //   190: aload           13
        //   192: invokestatic    net/minecraft/client/util/JsonException.func_151379_a:(Ljava/lang/Exception;)Lnet/minecraft/client/util/JsonException;
        //   195: astore          14
        //   197: aload           14
        //   199: new             Ljava/lang/StringBuilder;
        //   202: dup            
        //   203: ldc             "samplers["
        //   205: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   208: iconst_0       
        //   209: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   212: ldc             "]"
        //   214: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   217: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   220: invokevirtual   net/minecraft/client/util/JsonException.func_151380_a:(Ljava/lang/String;)V
        //   223: aload           14
        //   225: athrow         
        //   226: iinc            10, 1
        //   229: aload           11
        //   231: invokeinterface java/util/Iterator.hasNext:()Z
        //   236: ifne            167
        //   239: aload           6
        //   241: ldc             "attributes"
        //   243: aconst_null    
        //   244: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectJsonArrayFieldOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;Lcom/google/gson/JsonArray;)Lcom/google/gson/JsonArray;
        //   247: astore          10
        //   249: aload           10
        //   251: ifnull          374
        //   254: aload_0        
        //   255: aload           10
        //   257: invokevirtual   com/google/gson/JsonArray.size:()I
        //   260: invokestatic    com/google/common/collect/Lists.newArrayListWithCapacity:(I)Ljava/util/ArrayList;
        //   263: putfield        net/minecraft/client/shader/ShaderManager.field_148015_q:Ljava/util/List;
        //   266: aload_0        
        //   267: aload           10
        //   269: invokevirtual   com/google/gson/JsonArray.size:()I
        //   272: invokestatic    com/google/common/collect/Lists.newArrayListWithCapacity:(I)Ljava/util/ArrayList;
        //   275: putfield        net/minecraft/client/shader/ShaderManager.field_148014_r:Ljava/util/List;
        //   278: aload           10
        //   280: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
        //   283: astore          11
        //   285: goto            361
        //   288: aload           11
        //   290: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   295: checkcast       Lcom/google/gson/JsonElement;
        //   298: astore          13
        //   300: aload_0        
        //   301: getfield        net/minecraft/client/shader/ShaderManager.field_148014_r:Ljava/util/List;
        //   304: aload           13
        //   306: ldc             "attribute"
        //   308: invokestatic    net/minecraft/util/JsonUtils.getJsonElementStringValue:(Lcom/google/gson/JsonElement;Ljava/lang/String;)Ljava/lang/String;
        //   311: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   316: pop            
        //   317: goto            358
        //   320: astore          14
        //   322: aload           14
        //   324: invokestatic    net/minecraft/client/util/JsonException.func_151379_a:(Ljava/lang/Exception;)Lnet/minecraft/client/util/JsonException;
        //   327: astore          15
        //   329: aload           15
        //   331: new             Ljava/lang/StringBuilder;
        //   334: dup            
        //   335: ldc             "attributes["
        //   337: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   340: iconst_0       
        //   341: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   344: ldc             "]"
        //   346: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   349: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   352: invokevirtual   net/minecraft/client/util/JsonException.func_151380_a:(Ljava/lang/String;)V
        //   355: aload           15
        //   357: athrow         
        //   358: iinc            12, 1
        //   361: aload           11
        //   363: invokeinterface java/util/Iterator.hasNext:()Z
        //   368: ifne            288
        //   371: goto            384
        //   374: aload_0        
        //   375: aconst_null    
        //   376: putfield        net/minecraft/client/shader/ShaderManager.field_148015_q:Ljava/util/List;
        //   379: aload_0        
        //   380: aconst_null    
        //   381: putfield        net/minecraft/client/shader/ShaderManager.field_148014_r:Ljava/util/List;
        //   384: aload           6
        //   386: ldc             "uniforms"
        //   388: aconst_null    
        //   389: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectJsonArrayFieldOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;Lcom/google/gson/JsonArray;)Lcom/google/gson/JsonArray;
        //   392: astore          12
        //   394: aload           12
        //   396: ifnull          481
        //   399: aload           12
        //   401: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
        //   404: astore          14
        //   406: goto            471
        //   409: aload           14
        //   411: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   416: checkcast       Lcom/google/gson/JsonElement;
        //   419: astore          15
        //   421: aload_0        
        //   422: aload           15
        //   424: invokespecial   net/minecraft/client/shader/ShaderManager.parseUniform:(Lcom/google/gson/JsonElement;)V
        //   427: goto            468
        //   430: astore          16
        //   432: aload           16
        //   434: invokestatic    net/minecraft/client/util/JsonException.func_151379_a:(Ljava/lang/Exception;)Lnet/minecraft/client/util/JsonException;
        //   437: astore          17
        //   439: aload           17
        //   441: new             Ljava/lang/StringBuilder;
        //   444: dup            
        //   445: ldc             "uniforms["
        //   447: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   450: iconst_0       
        //   451: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   454: ldc             "]"
        //   456: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   459: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   462: invokevirtual   net/minecraft/client/util/JsonException.func_151380_a:(Ljava/lang/String;)V
        //   465: aload           17
        //   467: athrow         
        //   468: iinc            13, 1
        //   471: aload           14
        //   473: invokeinterface java/util/Iterator.hasNext:()Z
        //   478: ifne            409
        //   481: aload_0        
        //   482: aload           6
        //   484: ldc             "blend"
        //   486: aconst_null    
        //   487: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectFieldOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;
        //   490: invokestatic    net/minecraft/client/util/JsonBlendingMode.func_148110_a:(Lcom/google/gson/JsonObject;)Lnet/minecraft/client/util/JsonBlendingMode;
        //   493: putfield        net/minecraft/client/shader/ShaderManager.field_148016_p:Lnet/minecraft/client/util/JsonBlendingMode;
        //   496: aload_0        
        //   497: aload           6
        //   499: ldc             "cull"
        //   501: iconst_1       
        //   502: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectBooleanFieldValueOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;Z)Z
        //   505: putfield        net/minecraft/client/shader/ShaderManager.useFaceCulling:Z
        //   508: aload_0        
        //   509: aload_1        
        //   510: getstatic       net/minecraft/client/shader/ShaderLoader$ShaderType.VERTEX:Lnet/minecraft/client/shader/ShaderLoader$ShaderType;
        //   513: aload           7
        //   515: invokestatic    net/minecraft/client/shader/ShaderLoader.loadShader:(Lnet/minecraft/client/resources/IResourceManager;Lnet/minecraft/client/shader/ShaderLoader$ShaderType;Ljava/lang/String;)Lnet/minecraft/client/shader/ShaderLoader;
        //   518: putfield        net/minecraft/client/shader/ShaderManager.vertexShaderLoader:Lnet/minecraft/client/shader/ShaderLoader;
        //   521: aload_0        
        //   522: aload_1        
        //   523: getstatic       net/minecraft/client/shader/ShaderLoader$ShaderType.FRAGMENT:Lnet/minecraft/client/shader/ShaderLoader$ShaderType;
        //   526: aload           8
        //   528: invokestatic    net/minecraft/client/shader/ShaderLoader.loadShader:(Lnet/minecraft/client/resources/IResourceManager;Lnet/minecraft/client/shader/ShaderLoader$ShaderType;Ljava/lang/String;)Lnet/minecraft/client/shader/ShaderLoader;
        //   531: putfield        net/minecraft/client/shader/ShaderManager.fragmentShaderLoader:Lnet/minecraft/client/shader/ShaderLoader;
        //   534: aload_0        
        //   535: invokestatic    net/minecraft/client/shader/ShaderLinkHelper.getStaticShaderLinkHelper:()Lnet/minecraft/client/shader/ShaderLinkHelper;
        //   538: invokevirtual   net/minecraft/client/shader/ShaderLinkHelper.createProgram:()I
        //   541: putfield        net/minecraft/client/shader/ShaderManager.program:I
        //   544: invokestatic    net/minecraft/client/shader/ShaderLinkHelper.getStaticShaderLinkHelper:()Lnet/minecraft/client/shader/ShaderLinkHelper;
        //   547: aload_0        
        //   548: invokevirtual   net/minecraft/client/shader/ShaderLinkHelper.linkProgram:(Lnet/minecraft/client/shader/ShaderManager;)V
        //   551: aload_0        
        //   552: invokespecial   net/minecraft/client/shader/ShaderManager.setupUniforms:()V
        //   555: aload_0        
        //   556: getfield        net/minecraft/client/shader/ShaderManager.field_148014_r:Ljava/util/List;
        //   559: ifnull          659
        //   562: aload_0        
        //   563: getfield        net/minecraft/client/shader/ShaderManager.field_148014_r:Ljava/util/List;
        //   566: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   571: astore          11
        //   573: goto            614
        //   576: aload           11
        //   578: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   583: checkcast       Ljava/lang/String;
        //   586: astore          13
        //   588: aload_0        
        //   589: getfield        net/minecraft/client/shader/ShaderManager.program:I
        //   592: aload           13
        //   594: invokestatic    net/minecraft/client/renderer/OpenGlHelper.glGetAttribLocation:(ILjava/lang/CharSequence;)I
        //   597: istore          14
        //   599: aload_0        
        //   600: getfield        net/minecraft/client/shader/ShaderManager.field_148015_q:Ljava/util/List;
        //   603: iload           14
        //   605: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   608: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   613: pop            
        //   614: aload           11
        //   616: invokeinterface java/util/Iterator.hasNext:()Z
        //   621: ifne            576
        //   624: goto            659
        //   627: astore          6
        //   629: aload           6
        //   631: invokestatic    net/minecraft/client/util/JsonException.func_151379_a:(Ljava/lang/Exception;)Lnet/minecraft/client/util/JsonException;
        //   634: astore          7
        //   636: aload           7
        //   638: aload           4
        //   640: invokevirtual   net/minecraft/util/ResourceLocation.getResourcePath:()Ljava/lang/String;
        //   643: invokevirtual   net/minecraft/client/util/JsonException.func_151381_b:(Ljava/lang/String;)V
        //   646: aload           7
        //   648: athrow         
        //   649: astore          18
        //   651: aload           5
        //   653: invokestatic    org/apache/commons/io/IOUtils.closeQuietly:(Ljava/io/InputStream;)V
        //   656: aload           18
        //   658: athrow         
        //   659: aload           5
        //   661: invokestatic    org/apache/commons/io/IOUtils.closeQuietly:(Ljava/io/InputStream;)V
        //   664: aload_0        
        //   665: invokevirtual   net/minecraft/client/shader/ShaderManager.markDirty:()V
        //   668: return         
        //    Exceptions:
        //  throws net.minecraft.client.util.JsonException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void deleteShader() {
        ShaderLinkHelper.getStaticShaderLinkHelper().deleteShader(this);
    }
    
    public void endShader() {
        OpenGlHelper.glUseProgram(0);
        ShaderManager.currentProgram = -1;
        ShaderManager.staticShaderManager = null;
        ShaderManager.field_148000_e = true;
        while (0 < this.shaderSamplerLocations.size()) {
            if (this.shaderSamplers.get(this.samplerNames.get(0)) != null) {
                GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + 0);
                GlStateManager.func_179144_i(0);
            }
            int n = 0;
            ++n;
        }
    }
    
    public void useShader() {
        this.isDirty = false;
        ShaderManager.staticShaderManager = this;
        this.field_148016_p.func_148109_a();
        if (this.program != -1) {
            OpenGlHelper.glUseProgram(this.program);
            ShaderManager.currentProgram = this.program;
        }
        if (this.useFaceCulling) {}
        while (0 < this.shaderSamplerLocations.size()) {
            if (this.shaderSamplers.get(this.samplerNames.get(0)) != null) {
                GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + 0);
                final Integer value = this.shaderSamplers.get(this.samplerNames.get(0));
                if (value instanceof Framebuffer) {
                    final int framebufferTexture = ((Framebuffer)value).framebufferTexture;
                }
                else if (value instanceof ITextureObject) {
                    ((ITextureObject)value).getGlTextureId();
                }
                else if (value instanceof Integer) {
                    value;
                }
            }
            int n = 0;
            ++n;
        }
        final Iterator<ShaderUniform> iterator = this.shaderUniforms.iterator();
        while (iterator.hasNext()) {
            iterator.next().upload();
        }
    }
    
    public void markDirty() {
        this.isDirty = true;
    }
    
    public ShaderUniform getShaderUniform(final String s) {
        return this.mappedShaderUniforms.containsKey(s) ? this.mappedShaderUniforms.get(s) : null;
    }
    
    public ShaderUniform getShaderUniformOrDefault(final String s) {
        return this.mappedShaderUniforms.containsKey(s) ? this.mappedShaderUniforms.get(s) : ShaderManager.defaultShaderUniform;
    }
    
    private void setupUniforms() {
        while (0 < this.samplerNames.size()) {
            final String s = this.samplerNames.get(0);
            final int glGetUniformLocation = OpenGlHelper.glGetUniformLocation(this.program, s);
            int n = 0;
            if (glGetUniformLocation == -1) {
                ShaderManager.logger.warn("Shader " + this.programFilename + "could not find sampler named " + s + " in the specified shader program.");
                this.shaderSamplers.remove(s);
                this.samplerNames.remove(0);
                --n;
            }
            else {
                this.shaderSamplerLocations.add(glGetUniformLocation);
            }
            int n2 = 0;
            ++n2;
            ++n;
        }
        for (final ShaderUniform shaderUniform : this.shaderUniforms) {
            final String shaderName = shaderUniform.getShaderName();
            final int glGetUniformLocation2 = OpenGlHelper.glGetUniformLocation(this.program, shaderName);
            if (glGetUniformLocation2 == -1) {
                ShaderManager.logger.warn("Could not find uniform named " + shaderName + " in the specified" + " shader program.");
            }
            else {
                this.shaderUniformLocations.add(glGetUniformLocation2);
                shaderUniform.setUniformLocation(glGetUniformLocation2);
                this.mappedShaderUniforms.put(shaderName, shaderUniform);
            }
        }
    }
    
    private void parseSampler(final JsonElement jsonElement) {
        final JsonObject elementAsJsonObject = JsonUtils.getElementAsJsonObject(jsonElement, "sampler");
        final String jsonObjectStringFieldValue = JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject, "name");
        if (!JsonUtils.jsonObjectFieldTypeIsString(elementAsJsonObject, "file")) {
            this.shaderSamplers.put(jsonObjectStringFieldValue, null);
            this.samplerNames.add(jsonObjectStringFieldValue);
        }
        else {
            this.samplerNames.add(jsonObjectStringFieldValue);
        }
    }
    
    public void addSamplerTexture(final String s, final Object o) {
        if (this.shaderSamplers.containsKey(s)) {
            this.shaderSamplers.remove(s);
        }
        this.shaderSamplers.put(s, o);
        this.markDirty();
    }
    
    private void parseUniform(final JsonElement jsonElement) throws JsonException {
        final JsonObject elementAsJsonObject = JsonUtils.getElementAsJsonObject(jsonElement, "uniform");
        final String jsonObjectStringFieldValue = JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject, "name");
        final int type = ShaderUniform.parseType(JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject, "type"));
        final int jsonObjectIntegerFieldValue = JsonUtils.getJsonObjectIntegerFieldValue(elementAsJsonObject, "count");
        final float[] array = new float[Math.max(jsonObjectIntegerFieldValue, 16)];
        final JsonArray jsonObjectJsonArrayField = JsonUtils.getJsonObjectJsonArrayField(elementAsJsonObject, "values");
        if (jsonObjectJsonArrayField.size() != jsonObjectIntegerFieldValue && jsonObjectJsonArrayField.size() > 1) {
            throw new JsonException("Invalid amount of values specified (expected " + jsonObjectIntegerFieldValue + ", found " + jsonObjectJsonArrayField.size() + ")");
        }
        final Iterator iterator = jsonObjectJsonArrayField.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            array[0] = JsonUtils.getJsonElementFloatValue(iterator.next(), "value");
            ++n;
        }
        if (jsonObjectIntegerFieldValue > 1 && jsonObjectJsonArrayField.size() == 1) {
            while (0 < jsonObjectIntegerFieldValue) {
                array[0] = array[0];
                ++n;
            }
        }
        final ShaderUniform shaderUniform = new ShaderUniform(jsonObjectStringFieldValue, type + ((jsonObjectIntegerFieldValue > 1 && jsonObjectIntegerFieldValue <= 4 && type < 8) ? (jsonObjectIntegerFieldValue - 1) : 0), jsonObjectIntegerFieldValue, this);
        if (type <= 3) {
            shaderUniform.set((int)array[0], (int)array[1], (int)array[2], (int)array[3]);
        }
        else if (type <= 7) {
            shaderUniform.func_148092_b(array[0], array[1], array[2], array[3]);
        }
        else {
            shaderUniform.set(array);
        }
        this.shaderUniforms.add(shaderUniform);
    }
    
    public ShaderLoader getVertexShaderLoader() {
        return this.vertexShaderLoader;
    }
    
    public ShaderLoader getFragmentShaderLoader() {
        return this.fragmentShaderLoader;
    }
    
    public int getProgram() {
        return this.program;
    }
}
