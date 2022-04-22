package net.minecraft.client.shader;

import net.minecraft.client.resources.*;
import javax.vecmath.*;
import net.minecraft.client.renderer.texture.*;
import com.google.common.collect.*;
import net.minecraft.client.util.*;
import com.google.common.base.*;
import org.apache.commons.io.*;
import net.minecraft.util.*;
import java.io.*;
import com.google.gson.*;
import java.util.*;

public class ShaderGroup
{
    private Framebuffer mainFramebuffer;
    private IResourceManager resourceManager;
    private String shaderGroupName;
    private final List listShaders;
    private final Map mapFramebuffers;
    private final List listFramebuffers;
    private Matrix4f projectionMatrix;
    private int mainFramebufferWidth;
    private int mainFramebufferHeight;
    private float field_148036_j;
    private float field_148037_k;
    private static final String __OBFID;
    
    public ShaderGroup(final TextureManager textureManager, final IResourceManager resourceManager, final Framebuffer mainFramebuffer, final ResourceLocation resourceLocation) throws JsonException {
        this.listShaders = Lists.newArrayList();
        this.mapFramebuffers = Maps.newHashMap();
        this.listFramebuffers = Lists.newArrayList();
        this.resourceManager = resourceManager;
        this.mainFramebuffer = mainFramebuffer;
        this.field_148036_j = 0.0f;
        this.field_148037_k = 0.0f;
        this.mainFramebufferWidth = mainFramebuffer.framebufferWidth;
        this.mainFramebufferHeight = mainFramebuffer.framebufferHeight;
        this.shaderGroupName = resourceLocation.toString();
        this.resetProjectionMatrix();
        this.parseGroup(textureManager, resourceLocation);
    }
    
    public void parseGroup(final TextureManager textureManager, final ResourceLocation resourceLocation) throws JsonException {
        final JsonParser jsonParser = new JsonParser();
        final InputStream inputStream = this.resourceManager.getResource(resourceLocation).getInputStream();
        final JsonObject asJsonObject = jsonParser.parse(IOUtils.toString(inputStream, Charsets.UTF_8)).getAsJsonObject();
        int n = 0;
        if (JsonUtils.jsonObjectFieldTypeIsArray(asJsonObject, "targets")) {
            final Iterator iterator = asJsonObject.getAsJsonArray("targets").iterator();
            while (iterator.hasNext()) {
                this.initTarget(iterator.next());
                ++n;
            }
        }
        if (JsonUtils.jsonObjectFieldTypeIsArray(asJsonObject, "passes")) {
            final Iterator iterator2 = asJsonObject.getAsJsonArray("passes").iterator();
            while (iterator2.hasNext()) {
                this.parsePass(textureManager, iterator2.next());
                ++n;
            }
        }
        IOUtils.closeQuietly(inputStream);
    }
    
    private void initTarget(final JsonElement jsonElement) throws JsonException {
        if (JsonUtils.jsonElementTypeIsString(jsonElement)) {
            this.addFramebuffer(jsonElement.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
        }
        else {
            final JsonObject elementAsJsonObject = JsonUtils.getElementAsJsonObject(jsonElement, "target");
            final String jsonObjectStringFieldValue = JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject, "name");
            final int jsonObjectIntegerFieldValueOrDefault = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(elementAsJsonObject, "width", this.mainFramebufferWidth);
            final int jsonObjectIntegerFieldValueOrDefault2 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(elementAsJsonObject, "height", this.mainFramebufferHeight);
            if (this.mapFramebuffers.containsKey(jsonObjectStringFieldValue)) {
                throw new JsonException(String.valueOf(jsonObjectStringFieldValue) + " is already defined");
            }
            this.addFramebuffer(jsonObjectStringFieldValue, jsonObjectIntegerFieldValueOrDefault, jsonObjectIntegerFieldValueOrDefault2);
        }
    }
    
    private void parsePass(final TextureManager p0, final JsonElement p1) throws JsonException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "pass"
        //     3: invokestatic    net/minecraft/util/JsonUtils.getElementAsJsonObject:(Lcom/google/gson/JsonElement;Ljava/lang/String;)Lcom/google/gson/JsonObject;
        //     6: astore_3       
        //     7: aload_3        
        //     8: ldc             "name"
        //    10: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectStringFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //    13: astore          4
        //    15: aload_3        
        //    16: ldc             "intarget"
        //    18: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectStringFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //    21: astore          5
        //    23: aload_3        
        //    24: ldc             "outtarget"
        //    26: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectStringFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //    29: astore          6
        //    31: aload_0        
        //    32: aload           5
        //    34: invokespecial   net/minecraft/client/shader/ShaderGroup.getFramebuffer:(Ljava/lang/String;)Lnet/minecraft/client/shader/Framebuffer;
        //    37: astore          7
        //    39: aload_0        
        //    40: aload           6
        //    42: invokespecial   net/minecraft/client/shader/ShaderGroup.getFramebuffer:(Ljava/lang/String;)Lnet/minecraft/client/shader/Framebuffer;
        //    45: astore          8
        //    47: aload           7
        //    49: ifnonnull       83
        //    52: new             Lnet/minecraft/client/util/JsonException;
        //    55: dup            
        //    56: new             Ljava/lang/StringBuilder;
        //    59: dup            
        //    60: ldc             "Input target '"
        //    62: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    65: aload           5
        //    67: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    70: ldc_w           "' does not exist"
        //    73: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    76: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    79: invokespecial   net/minecraft/client/util/JsonException.<init>:(Ljava/lang/String;)V
        //    82: athrow         
        //    83: aload           8
        //    85: ifnonnull       120
        //    88: new             Lnet/minecraft/client/util/JsonException;
        //    91: dup            
        //    92: new             Ljava/lang/StringBuilder;
        //    95: dup            
        //    96: ldc_w           "Output target '"
        //    99: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   102: aload           6
        //   104: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   107: ldc_w           "' does not exist"
        //   110: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   113: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   116: invokespecial   net/minecraft/client/util/JsonException.<init>:(Ljava/lang/String;)V
        //   119: athrow         
        //   120: aload_0        
        //   121: aload           4
        //   123: aload           7
        //   125: aload           8
        //   127: invokevirtual   net/minecraft/client/shader/ShaderGroup.addShader:(Ljava/lang/String;Lnet/minecraft/client/shader/Framebuffer;Lnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/client/shader/Shader;
        //   130: astore          9
        //   132: aload_3        
        //   133: ldc_w           "auxtargets"
        //   136: aconst_null    
        //   137: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectJsonArrayFieldOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;Lcom/google/gson/JsonArray;)Lcom/google/gson/JsonArray;
        //   140: astore          10
        //   142: aload           10
        //   144: ifnull          489
        //   147: aload           10
        //   149: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
        //   152: astore          12
        //   154: goto            479
        //   157: aload           12
        //   159: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   164: checkcast       Lcom/google/gson/JsonElement;
        //   167: astore          13
        //   169: aload           13
        //   171: ldc_w           "auxtarget"
        //   174: invokestatic    net/minecraft/util/JsonUtils.getElementAsJsonObject:(Lcom/google/gson/JsonElement;Ljava/lang/String;)Lcom/google/gson/JsonObject;
        //   177: astore          14
        //   179: aload           14
        //   181: ldc             "name"
        //   183: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectStringFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //   186: astore          15
        //   188: aload           14
        //   190: ldc_w           "id"
        //   193: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectStringFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //   196: astore          16
        //   198: aload_0        
        //   199: aload           16
        //   201: invokespecial   net/minecraft/client/shader/ShaderGroup.getFramebuffer:(Ljava/lang/String;)Lnet/minecraft/client/shader/Framebuffer;
        //   204: astore          17
        //   206: aload           17
        //   208: ifnonnull       415
        //   211: new             Lnet/minecraft/util/ResourceLocation;
        //   214: dup            
        //   215: new             Ljava/lang/StringBuilder;
        //   218: dup            
        //   219: ldc_w           "textures/effect/"
        //   222: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   225: aload           16
        //   227: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   230: ldc_w           ".png"
        //   233: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   236: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   239: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //   242: astore          18
        //   244: aload_0        
        //   245: getfield        net/minecraft/client/shader/ShaderGroup.resourceManager:Lnet/minecraft/client/resources/IResourceManager;
        //   248: aload           18
        //   250: invokeinterface net/minecraft/client/resources/IResourceManager.getResource:(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/resources/IResource;
        //   255: pop            
        //   256: goto            293
        //   259: astore          19
        //   261: new             Lnet/minecraft/client/util/JsonException;
        //   264: dup            
        //   265: new             Ljava/lang/StringBuilder;
        //   268: dup            
        //   269: ldc_w           "Render target or texture '"
        //   272: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   275: aload           16
        //   277: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   280: ldc_w           "' does not exist"
        //   283: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   286: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   289: invokespecial   net/minecraft/client/util/JsonException.<init>:(Ljava/lang/String;)V
        //   292: athrow         
        //   293: aload_1        
        //   294: aload           18
        //   296: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //   299: aload_1        
        //   300: aload           18
        //   302: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.getTexture:(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/renderer/texture/ITextureObject;
        //   305: astore          19
        //   307: aload           14
        //   309: ldc             "width"
        //   311: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectIntegerFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)I
        //   314: istore          20
        //   316: aload           14
        //   318: ldc             "height"
        //   320: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectIntegerFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)I
        //   323: istore          21
        //   325: aload           14
        //   327: ldc_w           "bilinear"
        //   330: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectBooleanFieldValue:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Z
        //   333: istore          22
        //   335: iload           22
        //   337: ifeq            367
        //   340: sipush          3553
        //   343: sipush          10241
        //   346: sipush          9729
        //   349: invokestatic    org/lwjgl/opengl/GL11.glTexParameteri:(III)V
        //   352: sipush          3553
        //   355: sipush          10240
        //   358: sipush          9729
        //   361: invokestatic    org/lwjgl/opengl/GL11.glTexParameteri:(III)V
        //   364: goto            391
        //   367: sipush          3553
        //   370: sipush          10241
        //   373: sipush          9728
        //   376: invokestatic    org/lwjgl/opengl/GL11.glTexParameteri:(III)V
        //   379: sipush          3553
        //   382: sipush          10240
        //   385: sipush          9728
        //   388: invokestatic    org/lwjgl/opengl/GL11.glTexParameteri:(III)V
        //   391: aload           9
        //   393: aload           15
        //   395: aload           19
        //   397: invokeinterface net/minecraft/client/renderer/texture/ITextureObject.getGlTextureId:()I
        //   402: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   405: iload           20
        //   407: iload           21
        //   409: invokevirtual   net/minecraft/client/shader/Shader.addAuxFramebuffer:(Ljava/lang/String;Ljava/lang/Object;II)V
        //   412: goto            476
        //   415: aload           9
        //   417: aload           15
        //   419: aload           17
        //   421: aload           17
        //   423: getfield        net/minecraft/client/shader/Framebuffer.framebufferTextureWidth:I
        //   426: aload           17
        //   428: getfield        net/minecraft/client/shader/Framebuffer.framebufferTextureHeight:I
        //   431: invokevirtual   net/minecraft/client/shader/Shader.addAuxFramebuffer:(Ljava/lang/String;Ljava/lang/Object;II)V
        //   434: goto            476
        //   437: astore          14
        //   439: aload           14
        //   441: invokestatic    net/minecraft/client/util/JsonException.func_151379_a:(Ljava/lang/Exception;)Lnet/minecraft/client/util/JsonException;
        //   444: astore          15
        //   446: aload           15
        //   448: new             Ljava/lang/StringBuilder;
        //   451: dup            
        //   452: ldc_w           "auxtargets["
        //   455: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   458: iconst_0       
        //   459: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   462: ldc             "]"
        //   464: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   467: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   470: invokevirtual   net/minecraft/client/util/JsonException.func_151380_a:(Ljava/lang/String;)V
        //   473: aload           15
        //   475: athrow         
        //   476: iinc            11, 1
        //   479: aload           12
        //   481: invokeinterface java/util/Iterator.hasNext:()Z
        //   486: ifne            157
        //   489: aload_3        
        //   490: ldc_w           "uniforms"
        //   493: aconst_null    
        //   494: invokestatic    net/minecraft/util/JsonUtils.getJsonObjectJsonArrayFieldOrDefault:(Lcom/google/gson/JsonObject;Ljava/lang/String;Lcom/google/gson/JsonArray;)Lcom/google/gson/JsonArray;
        //   497: astore          11
        //   499: aload           11
        //   501: ifnull          587
        //   504: aload           11
        //   506: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
        //   509: astore          13
        //   511: goto            577
        //   514: aload           13
        //   516: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   521: checkcast       Lcom/google/gson/JsonElement;
        //   524: astore          14
        //   526: aload_0        
        //   527: aload           14
        //   529: invokespecial   net/minecraft/client/shader/ShaderGroup.initUniform:(Lcom/google/gson/JsonElement;)V
        //   532: goto            574
        //   535: astore          15
        //   537: aload           15
        //   539: invokestatic    net/minecraft/client/util/JsonException.func_151379_a:(Ljava/lang/Exception;)Lnet/minecraft/client/util/JsonException;
        //   542: astore          16
        //   544: aload           16
        //   546: new             Ljava/lang/StringBuilder;
        //   549: dup            
        //   550: ldc_w           "uniforms["
        //   553: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   556: iconst_0       
        //   557: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   560: ldc             "]"
        //   562: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   565: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   568: invokevirtual   net/minecraft/client/util/JsonException.func_151380_a:(Ljava/lang/String;)V
        //   571: aload           16
        //   573: athrow         
        //   574: iinc            12, 1
        //   577: aload           13
        //   579: invokeinterface java/util/Iterator.hasNext:()Z
        //   584: ifne            514
        //   587: return         
        //    Exceptions:
        //  throws net.minecraft.client.util.JsonException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void initUniform(final JsonElement jsonElement) throws JsonException {
        final JsonObject elementAsJsonObject = JsonUtils.getElementAsJsonObject(jsonElement, "uniform");
        final String jsonObjectStringFieldValue = JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject, "name");
        final ShaderUniform shaderUniform = this.listShaders.get(this.listShaders.size() - 1).getShaderManager().getShaderUniform(jsonObjectStringFieldValue);
        if (shaderUniform == null) {
            throw new JsonException("Uniform '" + jsonObjectStringFieldValue + "' does not exist");
        }
        final float[] array = new float[4];
        final Iterator iterator = JsonUtils.getJsonObjectJsonArrayField(elementAsJsonObject, "values").iterator();
        while (iterator.hasNext()) {
            array[0] = JsonUtils.getJsonElementFloatValue(iterator.next(), "value");
            int n = 0;
            ++n;
        }
        switch (false) {
            case 1: {
                shaderUniform.set(array[0]);
                break;
            }
            case 2: {
                shaderUniform.set(array[0], array[1]);
                break;
            }
            case 3: {
                shaderUniform.set(array[0], array[1], array[2]);
                break;
            }
            case 4: {
                shaderUniform.set(array[0], array[1], array[2], array[3]);
                break;
            }
        }
    }
    
    public Framebuffer func_177066_a(final String s) {
        return this.mapFramebuffers.get(s);
    }
    
    public void addFramebuffer(final String s, final int n, final int n2) {
        final Framebuffer framebuffer = new Framebuffer(n, n2, true);
        framebuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.mapFramebuffers.put(s, framebuffer);
        if (n == this.mainFramebufferWidth && n2 == this.mainFramebufferHeight) {
            this.listFramebuffers.add(framebuffer);
        }
    }
    
    public void deleteShaderGroup() {
        final Iterator<Framebuffer> iterator = this.mapFramebuffers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().deleteFramebuffer();
        }
        final Iterator<Shader> iterator2 = this.listShaders.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().deleteShader();
        }
        this.listShaders.clear();
    }
    
    public Shader addShader(final String s, final Framebuffer framebuffer, final Framebuffer framebuffer2) throws JsonException {
        final Shader shader = new Shader(this.resourceManager, s, framebuffer, framebuffer2);
        this.listShaders.add(this.listShaders.size(), shader);
        return shader;
    }
    
    private void resetProjectionMatrix() {
        (this.projectionMatrix = new Matrix4f()).setIdentity();
        this.projectionMatrix.m00 = 2.0f / this.mainFramebuffer.framebufferTextureWidth;
        this.projectionMatrix.m11 = 2.0f / -this.mainFramebuffer.framebufferTextureHeight;
        this.projectionMatrix.m22 = -0.0020001999f;
        this.projectionMatrix.m33 = 1.0f;
        this.projectionMatrix.m03 = -1.0f;
        this.projectionMatrix.m13 = 1.0f;
        this.projectionMatrix.m23 = -1.0001999f;
    }
    
    public void createBindFramebuffers(final int n, final int n2) {
        this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
        this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
        this.resetProjectionMatrix();
        final Iterator<Shader> iterator = this.listShaders.iterator();
        while (iterator.hasNext()) {
            iterator.next().setProjectionMatrix(this.projectionMatrix);
        }
        final Iterator<Framebuffer> iterator2 = this.listFramebuffers.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().createBindFramebuffer(n, n2);
        }
    }
    
    public void loadShaderGroup(final float field_148037_k) {
        if (field_148037_k < this.field_148037_k) {
            this.field_148036_j += 1.0f - this.field_148037_k;
            this.field_148036_j += field_148037_k;
        }
        else {
            this.field_148036_j += field_148037_k - this.field_148037_k;
        }
        this.field_148037_k = field_148037_k;
        while (this.field_148036_j > 20.0f) {
            this.field_148036_j -= 20.0f;
        }
        final Iterator<Shader> iterator = this.listShaders.iterator();
        while (iterator.hasNext()) {
            iterator.next().loadShader(this.field_148036_j / 20.0f);
        }
    }
    
    public final String getShaderGroupName() {
        return this.shaderGroupName;
    }
    
    private Framebuffer getFramebuffer(final String s) {
        return (s == null) ? null : (s.equals("minecraft:main") ? this.mainFramebuffer : this.mapFramebuffers.get(s));
    }
    
    static {
        __OBFID = "CL_00001041";
    }
}
