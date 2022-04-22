package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.texture.*;
import shadersmod.client.*;
import optifine.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.util.*;
import java.nio.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class WorldRenderer
{
    private ByteBuffer byteBuffer;
    public IntBuffer rawIntBuffer;
    public FloatBuffer rawFloatBuffer;
    public int vertexCount;
    private double field_178998_e;
    private double field_178995_f;
    private int field_178996_g;
    private int field_179007_h;
    private VertexFormatElement field_181677_f;
    private int field_181678_g;
    public int rawBufferIndex;
    private boolean needsUpdate;
    public int drawMode;
    private double xOffset;
    private double yOffset;
    private double zOffset;
    private int field_179003_o;
    private int field_179012_p;
    private VertexFormat vertexFormat;
    private boolean isDrawing;
    private int bufferSize;
    private static final String __OBFID;
    private EnumWorldBlockLayer blockLayer;
    private boolean[] drawnIcons;
    private TextureAtlasSprite[] quadSprites;
    private TextureAtlasSprite[] quadSpritesPrev;
    private TextureAtlasSprite quadSprite;
    public SVertexBuilder sVertexBuilder;
    
    public WorldRenderer(int bufferSize) {
        this.blockLayer = null;
        this.drawnIcons = new boolean[256];
        this.quadSprites = null;
        this.quadSpritesPrev = null;
        this.quadSprite = null;
        if (Config.isShaders()) {
            bufferSize *= 2;
        }
        this.bufferSize = bufferSize;
        this.byteBuffer = GLAllocation.createDirectByteBuffer(bufferSize * 4);
        this.rawIntBuffer = this.byteBuffer.asIntBuffer();
        this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
        (this.vertexFormat = new VertexFormat()).func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
        SVertexBuilder.initVertexBuilder(this);
    }
    
    private void growBuffer(int n) {
        if (Config.isShaders()) {
            n *= 2;
        }
        LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + this.bufferSize * 4 + " bytes, new size " + (this.bufferSize * 4 + n) + " bytes.");
        this.bufferSize += n / 4;
        final ByteBuffer directByteBuffer = GLAllocation.createDirectByteBuffer(this.bufferSize * 4);
        this.rawIntBuffer.position(0);
        directByteBuffer.asIntBuffer().put(this.rawIntBuffer);
        this.byteBuffer = directByteBuffer;
        this.rawIntBuffer = this.byteBuffer.asIntBuffer();
        this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
        if (this.quadSprites != null) {
            final TextureAtlasSprite[] quadSprites = this.quadSprites;
            System.arraycopy(quadSprites, 0, this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()], 0, Math.min(quadSprites.length, this.quadSprites.length));
            this.quadSpritesPrev = null;
        }
    }
    
    public State getVertexState(final float n, final float n2, final float n3) {
        final int[] array = new int[this.rawBufferIndex];
        final PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>(this.rawBufferIndex, new QuadComparator(this.rawFloatBuffer, (float)(n + this.xOffset), (float)(n2 + this.yOffset), (float)(n3 + this.zOffset), this.vertexFormat.func_177338_f() / 4));
        final int func_177338_f = this.vertexFormat.func_177338_f();
        TextureAtlasSprite[] array2 = null;
        final int n4 = this.vertexFormat.func_177338_f() / 4 * 4;
        if (this.quadSprites != null) {
            array2 = new TextureAtlasSprite[this.vertexCount / 4];
        }
        while (0 < this.rawBufferIndex) {
            priorityQueue.add(0);
        }
        while (!priorityQueue.isEmpty()) {
            final int intValue = priorityQueue.remove();
            while (0 < func_177338_f) {
                array[0] = this.rawIntBuffer.get(intValue + 0);
                int n5 = 0;
                ++n5;
            }
            if (array2 != null) {
                final int n5 = intValue / n4;
                array2[0 / n4] = this.quadSprites[0];
            }
        }
        this.rawIntBuffer.clear();
        this.rawIntBuffer.put(array);
        if (this.quadSprites != null) {
            System.arraycopy(array2, 0, this.quadSprites, 0, array2.length);
        }
        return new State(array, this.rawBufferIndex, this.vertexCount, new VertexFormat(this.vertexFormat), array2);
    }
    
    public void setVertexState(final State state) {
        if (state.func_179013_a().length > this.rawIntBuffer.capacity()) {
            this.growBuffer(2097152);
        }
        this.rawIntBuffer.clear();
        this.rawIntBuffer.put(state.func_179013_a());
        this.rawBufferIndex = state.getRawBufferIndex();
        this.vertexCount = state.getVertexCount();
        this.vertexFormat = new VertexFormat(state.func_179016_d());
        if (state.stateQuadSprites != null) {
            if (this.quadSprites == null) {
                this.quadSprites = this.quadSpritesPrev;
            }
            if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
                this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
            }
            System.arraycopy(state.stateQuadSprites, 0, this.quadSprites, 0, state.stateQuadSprites.length);
        }
        else {
            if (this.quadSprites != null) {
                this.quadSpritesPrev = this.quadSprites;
            }
            this.quadSprites = null;
        }
    }
    
    public void reset() {
        this.vertexCount = 0;
        this.rawBufferIndex = 0;
        this.vertexFormat.clear();
        this.vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
        if (this.blockLayer != null && Config.isMultiTexture()) {
            if (this.quadSprites == null) {
                this.quadSprites = this.quadSpritesPrev;
            }
            if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
                this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
            }
        }
        else {
            if (this.quadSprites != null) {
                this.quadSpritesPrev = this.quadSprites;
            }
            this.quadSprites = null;
        }
        this.quadSprite = null;
    }
    
    public void startDrawingQuads() {
        this.startDrawing(7);
    }
    
    public void begin(final int drawMode, final VertexFormat vertexFormat) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already building!");
        }
        this.isDrawing = true;
        this.reset();
        this.drawMode = drawMode;
        this.needsUpdate = false;
    }
    
    public void startDrawing(final int drawMode) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already building!");
        }
        this.isDrawing = true;
        this.reset();
        this.drawMode = drawMode;
        this.needsUpdate = false;
    }
    
    public void setTextureUV(final double field_178998_e, final double field_178995_f) {
        if (!this.vertexFormat.func_177347_a(0) && !this.vertexFormat.func_177347_a(1)) {
            this.vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
        }
        this.field_178998_e = field_178998_e;
        this.field_178995_f = field_178995_f;
    }
    
    public void func_178963_b(final int field_178996_g) {
        if (!this.vertexFormat.func_177347_a(1)) {
            if (!this.vertexFormat.func_177347_a(0)) {
                this.vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
            }
            this.vertexFormat.func_177349_a(new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.UV, 2));
        }
        this.field_178996_g = field_178996_g;
    }
    
    public void func_178986_b(final float n, final float n2, final float n3) {
        this.setPosition((int)(n * 255.0f), (int)(n2 * 255.0f), (int)(n3 * 255.0f));
    }
    
    public void func_178960_a(final float n, final float n2, final float n3, final float n4) {
        this.func_178961_b((int)(n * 255.0f), (int)(n2 * 255.0f), (int)(n3 * 255.0f), (int)(n4 * 255.0f));
    }
    
    public void setPosition(final int n, final int n2, final int n3) {
        this.func_178961_b(n, n2, n3, 255);
    }
    
    public void func_178962_a(final int n, final int n2, final int n3, final int n4) {
        final int n5 = (this.vertexCount - 4) * (this.vertexFormat.func_177338_f() / 4) + this.vertexFormat.func_177344_b(1) / 4;
        final int n6 = this.vertexFormat.func_177338_f() >> 2;
        this.rawIntBuffer.put(n5, n);
        this.rawIntBuffer.put(n5 + n6, n2);
        this.rawIntBuffer.put(n5 + n6 * 2, n3);
        this.rawIntBuffer.put(n5 + n6 * 3, n4);
    }
    
    public void func_178987_a(final double n, final double n2, final double n3) {
        if (this.rawBufferIndex >= this.bufferSize - this.vertexFormat.func_177338_f()) {
            this.growBuffer(2097152);
        }
        final int n4 = this.vertexFormat.func_177338_f() / 4;
        final int n5 = (this.vertexCount - 4) * n4;
        while (0 < 4) {
            final int n6 = n5 + 0 * n4;
            final int n7 = n6 + 1;
            final int n8 = n7 + 1;
            this.rawIntBuffer.put(n6, Float.floatToRawIntBits((float)(n + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(n6))));
            this.rawIntBuffer.put(n7, Float.floatToRawIntBits((float)(n2 + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(n7))));
            this.rawIntBuffer.put(n8, Float.floatToRawIntBits((float)(n3 + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(n8))));
            int n9 = 0;
            ++n9;
        }
    }
    
    public int getGLCallListForPass(final int n) {
        return ((this.vertexCount - n) * this.vertexFormat.func_177338_f() + this.vertexFormat.func_177340_e()) / 4;
    }
    
    public void func_178978_a(final float n, final float n2, final float n3, final int n4) {
        final int glCallListForPass = this.getGLCallListForPass(n4);
        this.rawIntBuffer.get(glCallListForPass);
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            final int n5 = -1 | ((int)(255 * n3) << 16 | (int)(255 * n2) << 8 | (int)(255 * n));
        }
        else {
            final int n6 = -1 | ((int)((this.field_179007_h >> 24 & 0xFF) * n) << 24 | (int)((this.field_179007_h >> 16 & 0xFF) * n2) << 16 | (int)((this.field_179007_h >> 8 & 0xFF) * n3) << 8);
        }
        if (this.needsUpdate) {}
        this.rawIntBuffer.put(glCallListForPass, -1);
    }
    
    private void func_178988_b(final int n, final int n2) {
        this.func_178972_a(this.getGLCallListForPass(n2), n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n >> 24 & 0xFF);
    }
    
    public void func_178994_b(final float n, final float n2, final float n3, final int n4) {
        this.func_178972_a(this.getGLCallListForPass(n4), MathHelper.clamp_int((int)(n * 255.0f), 0, 255), MathHelper.clamp_int((int)(n2 * 255.0f), 0, 255), MathHelper.clamp_int((int)(n3 * 255.0f), 0, 255), 255);
    }
    
    public void func_178972_a(final int n, final int n2, final int n3, final int n4, final int n5) {
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.rawIntBuffer.put(n, n5 << 24 | n4 << 16 | n3 << 8 | n2);
        }
        else {
            this.rawIntBuffer.put(n, n2 << 24 | n3 << 16 | n4 << 8 | n5);
        }
    }
    
    public void func_178961_b(final int n, final int n2, final int n3, final int n4) {
        if (!this.needsUpdate) {
            if (0 > 255) {}
            if (0 > 255) {}
            if (0 > 255) {}
            if (0 > 255) {}
            if (0 < 0) {}
            if (0 < 0) {}
            if (0 < 0) {}
            if (0 < 0) {}
            if (!this.vertexFormat.func_177346_d()) {
                this.vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.COLOR, 4));
            }
            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                this.field_179007_h = 0;
            }
            else {
                this.field_179007_h = 0;
            }
        }
    }
    
    public void func_178982_a(final byte b, final byte b2, final byte b3) {
        this.setPosition(b & 0xFF, b2 & 0xFF, b3 & 0xFF);
    }
    
    public void addVertexWithUV(final double n, final double n2, final double n3, double n4, double n5) {
        if (this.quadSprite != null && this.quadSprites != null) {
            n4 = this.quadSprite.toSingleU((float)n4);
            n5 = this.quadSprite.toSingleV((float)n5);
            this.quadSprites[this.vertexCount / 4] = this.quadSprite;
        }
        this.setTextureUV(n4, n5);
        this.addVertex(n, n2, n3);
    }
    
    public void setVertexFormat(final VertexFormat vertexFormat) {
        this.vertexFormat = new VertexFormat(vertexFormat);
        if (Config.isShaders()) {
            SVertexBuilder.endSetVertexFormat(this);
        }
    }
    
    public void func_178981_a(final int[] array) {
        if (Config.isShaders()) {
            SVertexBuilder.beginAddVertexData(this, array);
        }
        this.vertexCount += array.length / (this.vertexFormat.func_177338_f() / 4);
        this.rawIntBuffer.position(this.rawBufferIndex);
        this.rawIntBuffer.put(array);
        this.rawBufferIndex += array.length;
        if (Config.isShaders()) {
            SVertexBuilder.endAddVertexData(this);
        }
    }
    
    public void addVertex(final double n, final double n2, final double n3) {
        if (Config.isShaders()) {
            SVertexBuilder.beginAddVertex(this);
        }
        if (this.rawBufferIndex >= this.bufferSize - this.vertexFormat.func_177338_f()) {
            this.growBuffer(2097152);
        }
        final List func_177343_g = this.vertexFormat.func_177343_g();
        while (0 < func_177343_g.size()) {
            final VertexFormatElement vertexFormatElement = func_177343_g.get(0);
            final int n4 = this.rawBufferIndex + (vertexFormatElement.func_177373_a() >> 2);
            switch (SwitchEnumUseage.field_178959_a[vertexFormatElement.func_177375_c().ordinal()]) {
                case 1: {
                    this.rawIntBuffer.put(n4, Float.floatToRawIntBits((float)(n + this.xOffset)));
                    this.rawIntBuffer.put(n4 + 1, Float.floatToRawIntBits((float)(n2 + this.yOffset)));
                    this.rawIntBuffer.put(n4 + 2, Float.floatToRawIntBits((float)(n3 + this.zOffset)));
                    break;
                }
                case 2: {
                    this.rawIntBuffer.put(n4, this.field_179007_h);
                    break;
                }
                case 3: {
                    if (vertexFormatElement.func_177369_e() == 0) {
                        this.rawIntBuffer.put(n4, Float.floatToRawIntBits((float)this.field_178998_e));
                        this.rawIntBuffer.put(n4 + 1, Float.floatToRawIntBits((float)this.field_178995_f));
                        break;
                    }
                    this.rawIntBuffer.put(n4, this.field_178996_g);
                    break;
                }
                case 4: {
                    this.rawIntBuffer.put(n4, this.field_179003_o);
                    break;
                }
            }
            int n5 = 0;
            ++n5;
        }
        this.rawBufferIndex += this.vertexFormat.func_177338_f() >> 2;
        ++this.vertexCount;
        if (Config.isShaders()) {
            SVertexBuilder.endAddVertex(this);
        }
    }
    
    public void func_178991_c(final int n) {
        this.setPosition(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF);
    }
    
    public void func_178974_a(final int n, final int n2) {
        this.func_178961_b(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, n2);
    }
    
    public void markDirty() {
        this.needsUpdate = true;
    }
    
    public void func_178980_d(final float n, final float n2, final float n3) {
        if (!this.vertexFormat.func_177350_b()) {
            this.vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.NORMAL, 3));
            this.vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.PADDING, 1));
        }
        this.field_179003_o = (((byte)(n * 127.0f) & 0xFF) | ((byte)(n2 * 127.0f) & 0xFF) << 8 | ((byte)(n3 * 127.0f) & 0xFF) << 16);
    }
    
    public void func_178975_e(final float n, final float n2, final float n3) {
        final byte b = (byte)(n * 127.0f);
        final byte b2 = (byte)(n2 * 127.0f);
        final byte b3 = (byte)(n3 * 127.0f);
        final int n4 = this.vertexFormat.func_177338_f() >> 2;
        final int n5 = (this.vertexCount - 4) * n4 + this.vertexFormat.func_177342_c() / 4;
        this.field_179003_o = ((b & 0xFF) | (b2 & 0xFF) << 8 | (b3 & 0xFF) << 16);
        this.rawIntBuffer.put(n5, this.field_179003_o);
        this.rawIntBuffer.put(n5 + n4, this.field_179003_o);
        this.rawIntBuffer.put(n5 + n4 * 2, this.field_179003_o);
        this.rawIntBuffer.put(n5 + n4 * 3, this.field_179003_o);
    }
    
    public void setTranslation(final double xOffset, final double yOffset, final double zOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }
    
    public int draw() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not building!");
        }
        this.isDrawing = false;
        if (this.vertexCount > 0) {
            this.byteBuffer.position(0);
            this.byteBuffer.limit(this.rawBufferIndex * 4);
        }
        return this.field_179012_p = this.rawBufferIndex * 4;
    }
    
    public int func_178976_e() {
        return this.field_179012_p;
    }
    
    public ByteBuffer func_178966_f() {
        return this.byteBuffer;
    }
    
    public VertexFormat func_178973_g() {
        return this.vertexFormat;
    }
    
    public int func_178989_h() {
        return this.vertexCount;
    }
    
    public int getDrawMode() {
        return this.drawMode;
    }
    
    public void func_178968_d(final int n) {
        while (0 < 4) {
            this.func_178988_b(n, 1);
            int n2 = 0;
            ++n2;
        }
    }
    
    public void func_178990_f(final float n, final float n2, final float n3) {
        while (0 < 4) {
            this.func_178994_b(n, n2, n3, 1);
            int n4 = 0;
            ++n4;
        }
    }
    
    public void putSprite(final TextureAtlasSprite textureAtlasSprite) {
        if (this.quadSprites != null) {
            this.quadSprites[this.vertexCount / 4 - 1] = textureAtlasSprite;
        }
    }
    
    public void setSprite(final TextureAtlasSprite quadSprite) {
        if (this.quadSprites != null) {
            this.quadSprite = quadSprite;
        }
    }
    
    public boolean isMultiTexture() {
        return this.quadSprites != null;
    }
    
    public void drawMultiTexture() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/renderer/WorldRenderer.quadSprites:[Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //     4: ifnull          157
        //     7: invokestatic    optifine/Config.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //    10: invokevirtual   net/minecraft/client/Minecraft.getTextureMapBlocks:()Lnet/minecraft/client/renderer/texture/TextureMap;
        //    13: invokevirtual   net/minecraft/client/renderer/texture/TextureMap.getCountRegisteredSprites:()I
        //    16: istore_1       
        //    17: aload_0        
        //    18: getfield        net/minecraft/client/renderer/WorldRenderer.drawnIcons:[Z
        //    21: arraylength    
        //    22: iload_1        
        //    23: if_icmpgt       35
        //    26: aload_0        
        //    27: iload_1        
        //    28: iconst_1       
        //    29: iadd           
        //    30: newarray        Z
        //    32: putfield        net/minecraft/client/renderer/WorldRenderer.drawnIcons:[Z
        //    35: aload_0        
        //    36: getfield        net/minecraft/client/renderer/WorldRenderer.drawnIcons:[Z
        //    39: iconst_0       
        //    40: invokestatic    java/util/Arrays.fill:([ZZ)V
        //    43: aload_0        
        //    44: getfield        net/minecraft/client/renderer/WorldRenderer.vertexCount:I
        //    47: iconst_4       
        //    48: idiv           
        //    49: istore          4
        //    51: goto            134
        //    54: aload_0        
        //    55: getfield        net/minecraft/client/renderer/WorldRenderer.quadSprites:[Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //    58: iconst_0       
        //    59: aaload         
        //    60: astore          6
        //    62: aload           6
        //    64: ifnull          131
        //    67: aload           6
        //    69: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getIndexInMap:()I
        //    72: istore          7
        //    74: aload_0        
        //    75: getfield        net/minecraft/client/renderer/WorldRenderer.drawnIcons:[Z
        //    78: iload           7
        //    80: baload         
        //    81: ifne            131
        //    84: aload           6
        //    86: getstatic       optifine/TextureUtils.iconGrassSideOverlay:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //    89: if_acmpne       99
        //    92: iconst_m1      
        //    93: ifge            131
        //    96: goto            131
        //    99: aload_0        
        //   100: aload           6
        //   102: iconst_0       
        //   103: invokespecial   net/minecraft/client/renderer/WorldRenderer.drawForIcon:(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)I
        //   106: iconst_1       
        //   107: isub           
        //   108: istore          5
        //   110: iinc            2, 1
        //   113: aload_0        
        //   114: getfield        net/minecraft/client/renderer/WorldRenderer.blockLayer:Lnet/minecraft/util/EnumWorldBlockLayer;
        //   117: getstatic       net/minecraft/util/EnumWorldBlockLayer.TRANSLUCENT:Lnet/minecraft/util/EnumWorldBlockLayer;
        //   120: if_acmpeq       131
        //   123: aload_0        
        //   124: getfield        net/minecraft/client/renderer/WorldRenderer.drawnIcons:[Z
        //   127: iload           7
        //   129: iconst_1       
        //   130: bastore        
        //   131: iinc            5, 1
        //   134: iconst_0       
        //   135: iload           4
        //   137: if_icmplt       54
        //   140: iconst_m1      
        //   141: iflt            156
        //   144: aload_0        
        //   145: getstatic       optifine/TextureUtils.iconGrassSideOverlay:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //   148: iconst_m1      
        //   149: invokespecial   net/minecraft/client/renderer/WorldRenderer.drawForIcon:(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)I
        //   152: pop            
        //   153: iinc            2, 1
        //   156: iconst_0       
        //   157: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0157 (coming from #0156).
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
    
    private int drawForIcon(final TextureAtlasSprite textureAtlasSprite, final int n) {
        GL11.glBindTexture(3553, textureAtlasSprite.glSpriteTextureId);
        final int n2 = this.vertexCount / 4;
        for (int i = n; i < n2; ++i) {
            if (this.quadSprites[i] == textureAtlasSprite) {
                if (-1 < 0) {}
            }
            else if (-1 >= 0) {
                this.draw(-1, i);
                if (this.blockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
                    return i;
                }
                if (-1 < 0) {}
            }
        }
        if (-1 >= 0) {
            this.draw(-1, n2);
        }
        if (-1 < 0) {}
        return -1;
    }
    
    private void draw(final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 > 0) {
            GL11.glDrawArrays(this.drawMode, n * 4, n3 * 4);
        }
    }
    
    public void setBlockLayer(final EnumWorldBlockLayer blockLayer) {
        this.blockLayer = blockLayer;
        if (blockLayer == null) {
            if (this.quadSprites != null) {
                this.quadSpritesPrev = this.quadSprites;
            }
            this.quadSprites = null;
            this.quadSprite = null;
        }
    }
    
    private int getBufferQuadSize() {
        return this.rawIntBuffer.capacity() * 4 / (this.vertexFormat.func_177338_f() * 4);
    }
    
    public void checkAndGrow() {
        if (this.rawBufferIndex >= this.bufferSize - this.vertexFormat.func_177338_f()) {
            this.growBuffer(2097152);
        }
    }
    
    public boolean isColorDisabled() {
        return this.needsUpdate;
    }
    
    public void func_181667_k() {
        if (!this.vertexFormat.func_177350_b()) {
            this.vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.NORMAL, 3));
            this.vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.PADDING, 1));
        }
    }
    
    public void endVertex() {
        ++this.vertexCount;
        this.growBuffer(this.vertexFormat.func_181719_f());
    }
    
    public WorldRenderer pos(final double n, final double n2, final double n3) {
        final int n4 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
            case 1: {
                this.byteBuffer.putFloat(n4, (float)(n + this.xOffset));
                this.byteBuffer.putFloat(n4 + 4, (float)(n2 + this.yOffset));
                this.byteBuffer.putFloat(n4 + 8, (float)(n3 + this.zOffset));
                break;
            }
            case 2:
            case 3: {
                this.byteBuffer.putInt(n4, Float.floatToRawIntBits((float)(n + this.xOffset)));
                this.byteBuffer.putInt(n4 + 4, Float.floatToRawIntBits((float)(n2 + this.yOffset)));
                this.byteBuffer.putInt(n4 + 8, Float.floatToRawIntBits((float)(n3 + this.zOffset)));
                break;
            }
            case 4:
            case 5: {
                this.byteBuffer.putShort(n4, (short)(n + this.xOffset));
                this.byteBuffer.putShort(n4 + 2, (short)(n2 + this.yOffset));
                this.byteBuffer.putShort(n4 + 4, (short)(n3 + this.zOffset));
                break;
            }
            case 6:
            case 7: {
                this.byteBuffer.put(n4, (byte)(n + this.xOffset));
                this.byteBuffer.put(n4 + 1, (byte)(n2 + this.yOffset));
                this.byteBuffer.put(n4 + 2, (byte)(n3 + this.zOffset));
                break;
            }
        }
        this.func_181667_k();
        return this;
    }
    
    static {
        __OBFID = "CL_00000942";
    }
    
    public class State
    {
        private final int[] field_179019_b;
        private final int field_179020_c;
        private final int field_179017_d;
        private final VertexFormat field_179018_e;
        private static final String __OBFID;
        public TextureAtlasSprite[] stateQuadSprites;
        final WorldRenderer this$0;
        
        public State(final WorldRenderer this$0, final int[] field_179019_b, final int field_179020_c, final int field_179017_d, final VertexFormat field_179018_e, final TextureAtlasSprite[] stateQuadSprites) {
            this.this$0 = this$0;
            this.field_179019_b = field_179019_b;
            this.field_179020_c = field_179020_c;
            this.field_179017_d = field_179017_d;
            this.field_179018_e = field_179018_e;
            this.stateQuadSprites = stateQuadSprites;
        }
        
        public State(final WorldRenderer this$0, final int[] field_179019_b, final int field_179020_c, final int field_179017_d, final VertexFormat field_179018_e) {
            this.this$0 = this$0;
            this.field_179019_b = field_179019_b;
            this.field_179020_c = field_179020_c;
            this.field_179017_d = field_179017_d;
            this.field_179018_e = field_179018_e;
        }
        
        public int[] func_179013_a() {
            return this.field_179019_b;
        }
        
        public int getRawBufferIndex() {
            return this.field_179020_c;
        }
        
        public int getVertexCount() {
            return this.field_179017_d;
        }
        
        public VertexFormat func_179016_d() {
            return this.field_179018_e;
        }
        
        static {
            __OBFID = "CL_00002568";
        }
    }
    
    static final class SwitchEnumUseage
    {
        static final int[] field_178959_a;
        
        static {
            field_178959_a = new int[VertexFormatElement.EnumUseage.values().length];
            try {
                SwitchEnumUseage.field_178959_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumUseage.field_178959_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumUseage.field_178959_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumUseage.field_178959_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
    
    static final class WorldRenderer$2
    {
        static final int[] field_181661_a;
        private static final String __OBFID;
        private static final String[] lIlIIIIIIlIIIlll;
        private static String[] lIlIIIIIIlIIlIIl;
        
        static {
            lllIlIllllIIlIlI();
            lllIlIllllIIlIIl();
            __OBFID = WorldRenderer$2.lIlIIIIIIlIIIlll[0];
            field_181661_a = new int[VertexFormatElement.EnumType.values().length];
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.FLOAT.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.UINT.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.INT.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.USHORT.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.SHORT.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.UBYTE.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.BYTE.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
        }
        
        private static void lllIlIllllIIlIIl() {
            (lIlIIIIIIlIIIlll = new String[1])[0] = lllIlIllllIIlIII(WorldRenderer$2.lIlIIIIIIlIIlIIl[0], WorldRenderer$2.lIlIIIIIIlIIlIIl[1]);
            WorldRenderer$2.lIlIIIIIIlIIlIIl = null;
        }
        
        private static void lllIlIllllIIlIlI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            WorldRenderer$2.lIlIIIIIIlIIlIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lllIlIllllIIlIII(final String s, final String s2) {
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
