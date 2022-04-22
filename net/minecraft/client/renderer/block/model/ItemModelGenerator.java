package net.minecraft.client.renderer.block.model;

import com.google.common.collect.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import javax.vecmath.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class ItemModelGenerator
{
    public static final List LAYERS;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002488";
        LAYERS = Lists.newArrayList("layer0", "layer1", "layer2", "layer3", "layer4");
    }
    
    public ModelBlock func_178392_a(final TextureMap textureMap, final ModelBlock modelBlock) {
        final HashMap hashMap = Maps.newHashMap();
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < ItemModelGenerator.LAYERS.size()) {
            final String s = ItemModelGenerator.LAYERS.get(0);
            if (!modelBlock.isTexturePresent(s)) {
                break;
            }
            final String resolveTextureName = modelBlock.resolveTextureName(s);
            hashMap.put(s, resolveTextureName);
            arrayList.addAll(this.func_178394_a(0, s, textureMap.getAtlasSprite(new ResourceLocation(resolveTextureName).toString())));
            int n = 0;
            ++n;
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        hashMap.put("particle", modelBlock.isTexturePresent("particle") ? modelBlock.resolveTextureName("particle") : hashMap.get("layer0"));
        return new ModelBlock(arrayList, hashMap, false, false, new ItemCameraTransforms(modelBlock.getThirdPersonTransform(), modelBlock.getFirstPersonTransform(), modelBlock.getHeadTransform(), modelBlock.getInGuiTransform()));
    }
    
    private List func_178394_a(final int n, final String s, final TextureAtlasSprite textureAtlasSprite) {
        final HashMap hashMap = Maps.newHashMap();
        hashMap.put(EnumFacing.SOUTH, new BlockPartFace(null, n, s, new BlockFaceUV(new float[] { 0.0f, 0.0f, 16.0f, 16.0f }, 0)));
        hashMap.put(EnumFacing.NORTH, new BlockPartFace(null, n, s, new BlockFaceUV(new float[] { 16.0f, 0.0f, 0.0f, 16.0f }, 0)));
        final ArrayList arrayList = Lists.newArrayList();
        arrayList.add(new BlockPart(new Vector3f(0.0f, 0.0f, 7.5f), new Vector3f(16.0f, 16.0f, 8.5f), hashMap, null, true));
        arrayList.addAll(this.func_178397_a(textureAtlasSprite, s, n));
        return arrayList;
    }
    
    private List func_178397_a(final TextureAtlasSprite textureAtlasSprite, final String s, final int n) {
        final float n2 = (float)textureAtlasSprite.getIconWidth();
        final float n3 = (float)textureAtlasSprite.getIconHeight();
        final ArrayList arrayList = Lists.newArrayList();
        for (final Span span : this.func_178393_a(textureAtlasSprite)) {
            float n4 = 0.0f;
            float n5 = 0.0f;
            float n6 = 0.0f;
            float n7 = 0.0f;
            float n8 = 0.0f;
            float n9 = 0.0f;
            float n10 = 0.0f;
            float n11 = 0.0f;
            float n12 = 0.0f;
            float n13 = 0.0f;
            final float n14 = (float)span.func_178385_b();
            final float n15 = (float)span.func_178384_c();
            final float n16 = (float)span.func_178381_d();
            final SpanFacing func_178383_a = span.func_178383_a();
            switch (SwitchSpanFacing.field_178390_a[func_178383_a.ordinal()]) {
                case 1: {
                    n8 = n14;
                    n4 = n14;
                    n9 = (n6 = n15 + 1.0f);
                    n10 = n16;
                    n5 = n16;
                    n11 = n16;
                    n7 = n16;
                    n12 = 16.0f / n2;
                    n13 = 16.0f / (n3 - 1.0f);
                    break;
                }
                case 2: {
                    n11 = n16;
                    n10 = n16;
                    n8 = n14;
                    n4 = n14;
                    n9 = (n6 = n15 + 1.0f);
                    n5 = n16 + 1.0f;
                    n7 = n16 + 1.0f;
                    n12 = 16.0f / n2;
                    n13 = 16.0f / (n3 - 1.0f);
                    break;
                }
                case 3: {
                    n8 = n16;
                    n4 = n16;
                    n9 = n16;
                    n6 = n16;
                    n11 = n14;
                    n5 = n14;
                    n10 = (n7 = n15 + 1.0f);
                    n12 = 16.0f / (n2 - 1.0f);
                    n13 = 16.0f / n3;
                    break;
                }
                case 4: {
                    n9 = n16;
                    n8 = n16;
                    n4 = n16 + 1.0f;
                    n6 = n16 + 1.0f;
                    n11 = n14;
                    n5 = n14;
                    n10 = (n7 = n15 + 1.0f);
                    n12 = 16.0f / (n2 - 1.0f);
                    n13 = 16.0f / n3;
                    break;
                }
            }
            final float n17 = 16.0f / n2;
            final float n18 = 16.0f / n3;
            final float n19 = n4 * n17;
            final float n20 = n6 * n17;
            final float n21 = n5 * n18;
            final float n22 = n7 * n18;
            final float n23 = 16.0f - n21;
            final float n24 = 16.0f - n22;
            final float n25 = n8 * n12;
            final float n26 = n9 * n12;
            final float n27 = n10 * n13;
            final float n28 = n11 * n13;
            final HashMap hashMap = Maps.newHashMap();
            hashMap.put(func_178383_a.func_178367_a(), new BlockPartFace(null, n, s, new BlockFaceUV(new float[] { n25, n27, n26, n28 }, 0)));
            switch (SwitchSpanFacing.field_178390_a[func_178383_a.ordinal()]) {
                default: {
                    continue;
                }
                case 1: {
                    arrayList.add(new BlockPart(new Vector3f(n19, n23, 7.5f), new Vector3f(n20, n23, 8.5f), hashMap, null, true));
                    continue;
                }
                case 2: {
                    arrayList.add(new BlockPart(new Vector3f(n19, n24, 7.5f), new Vector3f(n20, n24, 8.5f), hashMap, null, true));
                    continue;
                }
                case 3: {
                    arrayList.add(new BlockPart(new Vector3f(n19, n23, 7.5f), new Vector3f(n19, n24, 8.5f), hashMap, null, true));
                    continue;
                }
                case 4: {
                    arrayList.add(new BlockPart(new Vector3f(n20, n23, 7.5f), new Vector3f(n20, n24, 8.5f), hashMap, null, true));
                    continue;
                }
            }
        }
        return arrayList;
    }
    
    private List func_178393_a(final TextureAtlasSprite p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getIconWidth:()I
        //     4: istore_2       
        //     5: aload_1        
        //     6: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getIconHeight:()I
        //     9: istore_3       
        //    10: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    13: astore          4
        //    15: goto            137
        //    18: aload_1        
        //    19: iconst_0       
        //    20: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getFrameTextureData:(I)[[I
        //    23: iconst_0       
        //    24: aaload         
        //    25: astore          6
        //    27: goto            129
        //    30: goto            121
        //    33: aload_0        
        //    34: aload           6
        //    36: iconst_0       
        //    37: iconst_0       
        //    38: iload_2        
        //    39: iload_3        
        //    40: iflt            47
        //    43: iconst_0       
        //    44: goto            48
        //    47: iconst_1       
        //    48: istore          9
        //    50: aload_0        
        //    51: getstatic       net/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing.UP:Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;
        //    54: aload           4
        //    56: aload           6
        //    58: iconst_0       
        //    59: iconst_0       
        //    60: iload_2        
        //    61: iload_3        
        //    62: iload           9
        //    64: invokespecial   net/minecraft/client/renderer/block/model/ItemModelGenerator.func_178396_a:(Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;Ljava/util/List;[IIIIIZ)V
        //    67: aload_0        
        //    68: getstatic       net/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing.DOWN:Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;
        //    71: aload           4
        //    73: aload           6
        //    75: iconst_0       
        //    76: iconst_0       
        //    77: iload_2        
        //    78: iload_3        
        //    79: iload           9
        //    81: invokespecial   net/minecraft/client/renderer/block/model/ItemModelGenerator.func_178396_a:(Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;Ljava/util/List;[IIIIIZ)V
        //    84: aload_0        
        //    85: getstatic       net/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing.LEFT:Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;
        //    88: aload           4
        //    90: aload           6
        //    92: iconst_0       
        //    93: iconst_0       
        //    94: iload_2        
        //    95: iload_3        
        //    96: iload           9
        //    98: invokespecial   net/minecraft/client/renderer/block/model/ItemModelGenerator.func_178396_a:(Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;Ljava/util/List;[IIIIIZ)V
        //   101: aload_0        
        //   102: getstatic       net/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing.RIGHT:Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;
        //   105: aload           4
        //   107: aload           6
        //   109: iconst_0       
        //   110: iconst_0       
        //   111: iload_2        
        //   112: iload_3        
        //   113: iload           9
        //   115: invokespecial   net/minecraft/client/renderer/block/model/ItemModelGenerator.func_178396_a:(Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;Ljava/util/List;[IIIIIZ)V
        //   118: iinc            8, 1
        //   121: iconst_0       
        //   122: iload_2        
        //   123: if_icmplt       33
        //   126: iinc            7, 1
        //   129: iconst_0       
        //   130: iload_3        
        //   131: if_icmplt       30
        //   134: iinc            5, 1
        //   137: iconst_0       
        //   138: aload_1        
        //   139: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getFrameCount:()I
        //   142: if_icmplt       18
        //   145: aload           4
        //   147: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0121 (coming from #0118).
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
    
    private void func_178396_a(final SpanFacing spanFacing, final List list, final int[] array, final int n, final int n2, final int n3, final int n4, final boolean b) {
        n + spanFacing.func_178372_b();
        n2 + spanFacing.func_178371_c();
        if (n4 >= 0 && b) {
            this.func_178395_a(list, spanFacing, n, n2);
        }
    }
    
    private void func_178395_a(final List list, final SpanFacing spanFacing, final int n, final int n2) {
        Span span = null;
        for (final Span span2 : list) {
            if (span2.func_178383_a() == spanFacing && span2.func_178381_d() == (SpanFacing.access$2(spanFacing) ? n2 : n)) {
                span = span2;
                break;
            }
        }
        final int n3 = SpanFacing.access$2(spanFacing) ? n2 : n;
        final int n4 = SpanFacing.access$2(spanFacing) ? n : n2;
        if (span == null) {
            list.add(new Span(spanFacing, n4, n3));
        }
        else {
            span.func_178382_a(n4);
        }
    }
    
    static class Span
    {
        private final SpanFacing field_178389_a;
        private int field_178387_b;
        private int field_178388_c;
        private final int field_178386_d;
        private static final String __OBFID;
        
        public Span(final SpanFacing field_178389_a, final int n, final int field_178386_d) {
            this.field_178389_a = field_178389_a;
            this.field_178387_b = n;
            this.field_178388_c = n;
            this.field_178386_d = field_178386_d;
        }
        
        public void func_178382_a(final int n) {
            if (n < this.field_178387_b) {
                this.field_178387_b = n;
            }
            else if (n > this.field_178388_c) {
                this.field_178388_c = n;
            }
        }
        
        public SpanFacing func_178383_a() {
            return this.field_178389_a;
        }
        
        public int func_178385_b() {
            return this.field_178387_b;
        }
        
        public int func_178384_c() {
            return this.field_178388_c;
        }
        
        public int func_178381_d() {
            return this.field_178386_d;
        }
        
        static {
            __OBFID = "CL_00002486";
        }
    }
    
    enum SpanFacing
    {
        UP("UP", 0, "UP", 0, EnumFacing.UP, 0, -1), 
        DOWN("DOWN", 1, "DOWN", 1, EnumFacing.DOWN, 0, 1), 
        LEFT("LEFT", 2, "LEFT", 2, EnumFacing.EAST, -1, 0), 
        RIGHT("RIGHT", 3, "RIGHT", 3, EnumFacing.WEST, 1, 0);
        
        private final EnumFacing field_178376_e;
        private final int field_178373_f;
        private final int field_178374_g;
        private static final SpanFacing[] $VALUES;
        private static final String __OBFID;
        private static final SpanFacing[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002485";
            ENUM$VALUES = new SpanFacing[] { SpanFacing.UP, SpanFacing.DOWN, SpanFacing.LEFT, SpanFacing.RIGHT };
            $VALUES = new SpanFacing[] { SpanFacing.UP, SpanFacing.DOWN, SpanFacing.LEFT, SpanFacing.RIGHT };
        }
        
        private SpanFacing(final String s, final int n, final String s2, final int n2, final EnumFacing field_178376_e, final int field_178373_f, final int field_178374_g) {
            this.field_178376_e = field_178376_e;
            this.field_178373_f = field_178373_f;
            this.field_178374_g = field_178374_g;
        }
        
        public EnumFacing func_178367_a() {
            return this.field_178376_e;
        }
        
        public int func_178372_b() {
            return this.field_178373_f;
        }
        
        public int func_178371_c() {
            return this.field_178374_g;
        }
        
        private boolean func_178369_d() {
            return this == SpanFacing.DOWN || this == SpanFacing.UP;
        }
        
        static boolean access$2(final SpanFacing spanFacing) {
            return spanFacing.func_178369_d();
        }
    }
    
    static final class SwitchSpanFacing
    {
        static final int[] field_178390_a;
        private static final String __OBFID;
        private static final String[] lIIIIlIlIlllIllI;
        private static String[] lIIIIlIlIllllIII;
        
        static {
            llIIIlIlIIIIlIlI();
            llIIIlIlIIIIlIIl();
            __OBFID = SwitchSpanFacing.lIIIIlIlIlllIllI[0];
            field_178390_a = new int[SpanFacing.values().length];
            try {
                SwitchSpanFacing.field_178390_a[SpanFacing.UP.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchSpanFacing.field_178390_a[SpanFacing.DOWN.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchSpanFacing.field_178390_a[SpanFacing.LEFT.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchSpanFacing.field_178390_a[SpanFacing.RIGHT.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
        
        private static void llIIIlIlIIIIlIIl() {
            (lIIIIlIlIlllIllI = new String[1])[0] = llIIIlIlIIIIlIII(SwitchSpanFacing.lIIIIlIlIllllIII[0], SwitchSpanFacing.lIIIIlIlIllllIII[1]);
            SwitchSpanFacing.lIIIIlIlIllllIII = null;
        }
        
        private static void llIIIlIlIIIIlIlI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchSpanFacing.lIIIIlIlIllllIII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llIIIlIlIIIIlIII(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                final Cipher instance = Cipher.getInstance("Blowfish");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
