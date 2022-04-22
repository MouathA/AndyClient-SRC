package optifine;

import net.minecraft.client.renderer.block.model.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class NaturalProperties
{
    public int rotation;
    public boolean flip;
    private Map[] quadMaps;
    
    public NaturalProperties(final String s) {
        this.rotation = 1;
        this.flip = false;
        this.quadMaps = new Map[8];
        if (s.equals("4")) {
            this.rotation = 4;
        }
        else if (s.equals("2")) {
            this.rotation = 2;
        }
        else if (s.equals("F")) {
            this.flip = true;
        }
        else if (s.equals("4F")) {
            this.rotation = 4;
            this.flip = true;
        }
        else if (s.equals("2F")) {
            this.rotation = 2;
            this.flip = true;
        }
        else {
            Config.warn("NaturalTextures: Unknown type: " + s);
        }
    }
    
    public boolean isValid() {
        return this.rotation == 2 || this.rotation == 4 || this.flip;
    }
    
    public synchronized BakedQuad getQuad(final BakedQuad bakedQuad, final int n, final boolean b) {
        int n2 = n;
        if (b) {
            n2 = (n | 0x4);
        }
        if (n2 > 0 && n2 < this.quadMaps.length) {
            Map<Object, BakedQuad> map = (Map<Object, BakedQuad>)this.quadMaps[n2];
            if (map == null) {
                map = new IdentityHashMap<Object, BakedQuad>(1);
                this.quadMaps[n2] = map;
            }
            BakedQuad quad = map.get(bakedQuad);
            if (quad == null) {
                quad = this.makeQuad(bakedQuad, n, b);
                map.put(bakedQuad, quad);
            }
            return quad;
        }
        return bakedQuad;
    }
    
    private BakedQuad makeQuad(final BakedQuad bakedQuad, final int n, final boolean b) {
        final int[] func_178209_a = bakedQuad.func_178209_a();
        final int func_178211_c = bakedQuad.func_178211_c();
        final EnumFacing face = bakedQuad.getFace();
        final TextureAtlasSprite sprite = bakedQuad.getSprite();
        if (bakedQuad == 0) {
            return bakedQuad;
        }
        return new BakedQuad(this.transformVertexData(func_178209_a, n, b), func_178211_c, face, sprite);
    }
    
    private int[] transformVertexData(final int[] p0, final int p1, final boolean p2) {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
        // 
        // The error that occurred was:
        // 
        // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'optifine/NaturalProperties.transformVertexData:([IIZ)[I'.
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:66)
        //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:729)
        //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
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
        // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 79.
        //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
        //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:235)
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
        //     ... 17 more
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
