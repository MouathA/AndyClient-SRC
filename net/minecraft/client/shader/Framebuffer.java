package net.minecraft.client.shader;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.nio.*;

public class Framebuffer
{
    public int framebufferTextureWidth;
    public int framebufferTextureHeight;
    public int framebufferWidth;
    public int framebufferHeight;
    public boolean useDepth;
    public int framebufferObject;
    public int framebufferTexture;
    public int depthBuffer;
    public float[] framebufferColor;
    public int framebufferFilter;
    private static final String __OBFID;
    
    public Framebuffer(final int n, final int n2, final boolean useDepth) {
        this.useDepth = useDepth;
        this.framebufferObject = -1;
        this.framebufferTexture = -1;
        this.depthBuffer = -1;
        (this.framebufferColor = new float[4])[0] = 1.0f;
        this.framebufferColor[1] = 1.0f;
        this.framebufferColor[2] = 1.0f;
        this.framebufferColor[3] = 0.0f;
        this.createBindFramebuffer(n, n2);
    }
    
    public void createBindFramebuffer(final int framebufferWidth, final int framebufferHeight) {
        if (!OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferWidth = framebufferWidth;
            this.framebufferHeight = framebufferHeight;
        }
        else {
            if (this.framebufferObject >= 0) {
                this.deleteFramebuffer();
            }
            this.createFramebuffer(framebufferWidth, framebufferHeight);
            this.checkFramebufferComplete();
            OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
        }
    }
    
    public void deleteFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            this.unbindFramebufferTexture();
            this.unbindFramebuffer();
            if (this.depthBuffer > -1) {
                OpenGlHelper.func_153184_g(this.depthBuffer);
                this.depthBuffer = -1;
            }
            if (this.framebufferTexture > -1) {
                TextureUtil.deleteTexture(this.framebufferTexture);
                this.framebufferTexture = -1;
            }
            if (this.framebufferObject > -1) {
                OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
                OpenGlHelper.func_153174_h(this.framebufferObject);
                this.framebufferObject = -1;
            }
        }
    }
    
    public void createFramebuffer(final int n, final int n2) {
        this.framebufferWidth = n;
        this.framebufferHeight = n2;
        this.framebufferTextureWidth = n;
        this.framebufferTextureHeight = n2;
        if (!OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferClear();
        }
        else {
            this.framebufferObject = OpenGlHelper.func_153165_e();
            this.framebufferTexture = TextureUtil.glGenTextures();
            if (this.useDepth) {
                this.depthBuffer = OpenGlHelper.func_153185_f();
            }
            this.setFramebufferFilter(9728);
            GlStateManager.func_179144_i(this.framebufferTexture);
            GL11.glTexImage2D(3553, 0, 32856, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6408, 5121, (ByteBuffer)null);
            OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, this.framebufferObject);
            OpenGlHelper.func_153188_a(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, this.framebufferTexture, 0);
            if (this.useDepth) {
                OpenGlHelper.func_153176_h(OpenGlHelper.field_153199_f, this.depthBuffer);
                OpenGlHelper.func_153186_a(OpenGlHelper.field_153199_f, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
                OpenGlHelper.func_153190_b(OpenGlHelper.field_153198_e, OpenGlHelper.field_153201_h, OpenGlHelper.field_153199_f, this.depthBuffer);
            }
            this.framebufferClear();
            this.unbindFramebufferTexture();
        }
    }
    
    public void setFramebufferFilter(final int framebufferFilter) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferFilter = framebufferFilter;
            GlStateManager.func_179144_i(this.framebufferTexture);
            GL11.glTexParameterf(3553, 10241, (float)framebufferFilter);
            GL11.glTexParameterf(3553, 10240, (float)framebufferFilter);
            GL11.glTexParameterf(3553, 10242, 10496.0f);
            GL11.glTexParameterf(3553, 10243, 10496.0f);
            GlStateManager.func_179144_i(0);
        }
    }
    
    public void checkFramebufferComplete() {
        final int func_153167_i = OpenGlHelper.func_153167_i(OpenGlHelper.field_153198_e);
        if (func_153167_i == OpenGlHelper.field_153202_i) {
            return;
        }
        if (func_153167_i == OpenGlHelper.field_153203_j) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
        }
        if (func_153167_i == OpenGlHelper.field_153204_k) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
        }
        if (func_153167_i == OpenGlHelper.field_153205_l) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
        }
        if (func_153167_i == OpenGlHelper.field_153206_m) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
        }
        throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + func_153167_i);
    }
    
    public void bindFramebufferTexture() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.func_179144_i(this.framebufferTexture);
        }
    }
    
    public void unbindFramebufferTexture() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.func_179144_i(0);
        }
    }
    
    public void bindFramebuffer(final boolean b) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, this.framebufferObject);
            if (b) {
                GlStateManager.viewport(0, 0, this.framebufferWidth, this.framebufferHeight);
            }
        }
    }
    
    public void unbindFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
        }
    }
    
    public void setFramebufferColor(final float n, final float n2, final float n3, final float n4) {
        this.framebufferColor[0] = n;
        this.framebufferColor[1] = n2;
        this.framebufferColor[2] = n3;
        this.framebufferColor[3] = n4;
    }
    
    public void framebufferRender(final int n, final int n2) {
        this.func_178038_a(n, n2, true);
    }
    
    public void func_178038_a(final int p0, final int p1, final boolean p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            204
        //     6: iconst_1       
        //     7: iconst_1       
        //     8: iconst_1       
        //     9: iconst_0       
        //    10: invokestatic    net/minecraft/client/renderer/GlStateManager.colorMask:(ZZZZ)V
        //    13: iconst_0       
        //    14: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //    17: sipush          5889
        //    20: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //    23: dconst_0       
        //    24: iload_1        
        //    25: i2d            
        //    26: iload_2        
        //    27: i2d            
        //    28: dconst_0       
        //    29: ldc2_w          1000.0
        //    32: ldc2_w          3000.0
        //    35: invokestatic    net/minecraft/client/renderer/GlStateManager.ortho:(DDDDDD)V
        //    38: sipush          5888
        //    41: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //    44: fconst_0       
        //    45: fconst_0       
        //    46: ldc             -2000.0
        //    48: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //    51: iconst_0       
        //    52: iconst_0       
        //    53: iload_1        
        //    54: iload_2        
        //    55: invokestatic    net/minecraft/client/renderer/GlStateManager.viewport:(IIII)V
        //    58: iload_3        
        //    59: fconst_1       
        //    60: fconst_1       
        //    61: fconst_1       
        //    62: fconst_1       
        //    63: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //    66: aload_0        
        //    67: invokevirtual   net/minecraft/client/shader/Framebuffer.bindFramebufferTexture:()V
        //    70: iload_1        
        //    71: i2f            
        //    72: fstore          4
        //    74: iload_2        
        //    75: i2f            
        //    76: fstore          5
        //    78: aload_0        
        //    79: getfield        net/minecraft/client/shader/Framebuffer.framebufferWidth:I
        //    82: i2f            
        //    83: aload_0        
        //    84: getfield        net/minecraft/client/shader/Framebuffer.framebufferTextureWidth:I
        //    87: i2f            
        //    88: fdiv           
        //    89: fstore          6
        //    91: aload_0        
        //    92: getfield        net/minecraft/client/shader/Framebuffer.framebufferHeight:I
        //    95: i2f            
        //    96: aload_0        
        //    97: getfield        net/minecraft/client/shader/Framebuffer.framebufferTextureHeight:I
        //   100: i2f            
        //   101: fdiv           
        //   102: fstore          7
        //   104: invokestatic    net/minecraft/client/renderer/Tessellator.getInstance:()Lnet/minecraft/client/renderer/Tessellator;
        //   107: astore          8
        //   109: aload           8
        //   111: invokevirtual   net/minecraft/client/renderer/Tessellator.getWorldRenderer:()Lnet/minecraft/client/renderer/WorldRenderer;
        //   114: astore          9
        //   116: aload           9
        //   118: invokevirtual   net/minecraft/client/renderer/WorldRenderer.startDrawingQuads:()V
        //   121: aload           9
        //   123: iconst_m1      
        //   124: invokevirtual   net/minecraft/client/renderer/WorldRenderer.func_178991_c:(I)V
        //   127: aload           9
        //   129: dconst_0       
        //   130: fload           5
        //   132: f2d            
        //   133: dconst_0       
        //   134: dconst_0       
        //   135: dconst_0       
        //   136: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   139: aload           9
        //   141: fload           4
        //   143: f2d            
        //   144: fload           5
        //   146: f2d            
        //   147: dconst_0       
        //   148: fload           6
        //   150: f2d            
        //   151: dconst_0       
        //   152: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   155: aload           9
        //   157: fload           4
        //   159: f2d            
        //   160: dconst_0       
        //   161: dconst_0       
        //   162: fload           6
        //   164: f2d            
        //   165: fload           7
        //   167: f2d            
        //   168: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   171: aload           9
        //   173: dconst_0       
        //   174: dconst_0       
        //   175: dconst_0       
        //   176: dconst_0       
        //   177: fload           7
        //   179: f2d            
        //   180: invokevirtual   net/minecraft/client/renderer/WorldRenderer.addVertexWithUV:(DDDDD)V
        //   183: aload           8
        //   185: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()I
        //   188: pop            
        //   189: aload_0        
        //   190: invokevirtual   net/minecraft/client/shader/Framebuffer.unbindFramebufferTexture:()V
        //   193: iconst_1       
        //   194: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   197: iconst_1       
        //   198: iconst_1       
        //   199: iconst_1       
        //   200: iconst_1       
        //   201: invokestatic    net/minecraft/client/renderer/GlStateManager.colorMask:(ZZZZ)V
        //   204: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0204 (coming from #0201).
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
    
    public void framebufferClear() {
        this.bindFramebuffer(true);
        GlStateManager.clearColor(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
        if (this.useDepth) {
            GlStateManager.clearDepth(1.0);
        }
        GlStateManager.clear(16384);
        this.unbindFramebuffer();
    }
    
    static {
        __OBFID = "CL_00000959";
    }
}
