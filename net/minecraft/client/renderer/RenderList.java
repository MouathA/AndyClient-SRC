package net.minecraft.client.renderer;

import net.minecraft.util.*;

public class RenderList extends ChunkRenderContainer
{
    @Override
    public void func_178001_a(final EnumWorldBlockLayer p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/renderer/RenderList.field_178007_b:Z
        //     4: ifeq            89
        //     7: aload_0        
        //     8: getfield        net/minecraft/client/renderer/RenderList.field_178009_a:Ljava/util/List;
        //    11: invokeinterface java/util/List.size:()I
        //    16: ifne            20
        //    19: return         
        //    20: aload_0        
        //    21: getfield        net/minecraft/client/renderer/RenderList.field_178009_a:Ljava/util/List;
        //    24: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    29: astore_2       
        //    30: goto            68
        //    33: aload_2        
        //    34: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    39: checkcast       Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //    42: astore_3       
        //    43: aload_3        
        //    44: checkcast       Lnet/minecraft/client/renderer/chunk/ListedRenderChunk;
        //    47: astore          4
        //    49: aload_0        
        //    50: aload_3        
        //    51: invokevirtual   net/minecraft/client/renderer/RenderList.func_178003_a:(Lnet/minecraft/client/renderer/chunk/RenderChunk;)V
        //    54: aload           4
        //    56: aload_1        
        //    57: aload           4
        //    59: invokevirtual   net/minecraft/client/renderer/chunk/ListedRenderChunk.func_178571_g:()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //    62: invokevirtual   net/minecraft/client/renderer/chunk/ListedRenderChunk.func_178600_a:(Lnet/minecraft/util/EnumWorldBlockLayer;Lnet/minecraft/client/renderer/chunk/CompiledChunk;)I
        //    65: invokestatic    org/lwjgl/opengl/GL11.glCallList:(I)V
        //    68: aload_2        
        //    69: invokeinterface java/util/Iterator.hasNext:()Z
        //    74: ifne            33
        //    77: invokestatic    optifine/Config.isMultiTexture:()Z
        //    80: aload_0        
        //    81: getfield        net/minecraft/client/renderer/RenderList.field_178009_a:Ljava/util/List;
        //    84: invokeinterface java/util/List.clear:()V
        //    89: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0089 (coming from #0084).
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
}
