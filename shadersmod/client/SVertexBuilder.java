package shadersmod.client;

import net.minecraft.client.renderer.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.vertex.*;
import org.lwjgl.opengl.*;
import java.nio.*;

public class SVertexBuilder
{
    int vertexSize;
    int offsetNormal;
    int offsetUV;
    int offsetUVCenter;
    boolean hasNormal;
    boolean hasTangent;
    boolean hasUV;
    boolean hasUVCenter;
    long[] entityData;
    int entityDataIndex;
    
    public SVertexBuilder() {
        this.entityData = new long[10];
        this.entityDataIndex = 0;
        this.entityData[this.entityDataIndex] = 0L;
    }
    
    public static void initVertexBuilder(final WorldRenderer worldRenderer) {
        worldRenderer.sVertexBuilder = new SVertexBuilder();
    }
    
    public void pushEntity(final long n) {
        ++this.entityDataIndex;
        this.entityData[this.entityDataIndex] = n;
    }
    
    public void popEntity() {
        this.entityData[this.entityDataIndex] = 0L;
        --this.entityDataIndex;
    }
    
    public static void pushEntity(final IBlockState blockState, final BlockPos blockPos, final IBlockAccess blockAccess, final WorldRenderer worldRenderer) {
        final Block block = blockState.getBlock();
        worldRenderer.sVertexBuilder.pushEntity(((long)(block.getMetaFromState(blockState) & 0xFFFF) << 32) + (((block.getRenderType() & 0xFFFF) << 16) + (Block.getIdFromBlock(block) & 0xFFFF)));
    }
    
    public static void popEntity(final WorldRenderer worldRenderer) {
        worldRenderer.sVertexBuilder.popEntity();
    }
    
    public static boolean popEntity(final boolean b, final WorldRenderer worldRenderer) {
        worldRenderer.sVertexBuilder.popEntity();
        return b;
    }
    
    public static void endSetVertexFormat(final WorldRenderer worldRenderer) {
        final SVertexBuilder sVertexBuilder = worldRenderer.sVertexBuilder;
        final VertexFormat func_178973_g = worldRenderer.func_178973_g();
        sVertexBuilder.vertexSize = func_178973_g.func_177338_f() / 4;
        sVertexBuilder.hasNormal = func_178973_g.func_177350_b();
        sVertexBuilder.hasTangent = sVertexBuilder.hasNormal;
        sVertexBuilder.hasUV = func_178973_g.func_177347_a(0);
        sVertexBuilder.offsetNormal = (sVertexBuilder.hasNormal ? (func_178973_g.func_177342_c() / 4) : 0);
        sVertexBuilder.offsetUV = (sVertexBuilder.hasUV ? (func_178973_g.func_177344_b(0) / 4) : 0);
        sVertexBuilder.offsetUVCenter = 8;
    }
    
    public static void beginAddVertex(final WorldRenderer worldRenderer) {
        if (worldRenderer.vertexCount == 0) {
            endSetVertexFormat(worldRenderer);
        }
    }
    
    public static void endAddVertex(final WorldRenderer worldRenderer) {
        final SVertexBuilder sVertexBuilder = worldRenderer.sVertexBuilder;
        if (sVertexBuilder.vertexSize == 14) {
            if (worldRenderer.drawMode == 7 && worldRenderer.vertexCount % 4 == 0) {
                sVertexBuilder.calcNormal(worldRenderer, worldRenderer.rawBufferIndex - 4 * sVertexBuilder.vertexSize);
            }
            final long n = sVertexBuilder.entityData[sVertexBuilder.entityDataIndex];
            final int n2 = worldRenderer.rawBufferIndex - 14 + 12;
            worldRenderer.rawIntBuffer.put(n2, (int)n);
            worldRenderer.rawIntBuffer.put(n2 + 1, (int)(n >> 32));
        }
    }
    
    public static void beginAddVertexData(final WorldRenderer worldRenderer, final int[] array) {
        if (worldRenderer.vertexCount == 0) {
            endSetVertexFormat(worldRenderer);
        }
        final SVertexBuilder sVertexBuilder = worldRenderer.sVertexBuilder;
        if (sVertexBuilder.vertexSize == 14) {
            final long n = sVertexBuilder.entityData[sVertexBuilder.entityDataIndex];
            while (13 < array.length) {
                array[12] = (int)n;
                array[13] = (int)(n >> 32);
                final int n2;
                n2 += 14;
            }
        }
    }
    
    public static void endAddVertexData(final WorldRenderer worldRenderer) {
        final SVertexBuilder sVertexBuilder = worldRenderer.sVertexBuilder;
        if (sVertexBuilder.vertexSize == 14 && worldRenderer.drawMode == 7 && worldRenderer.vertexCount % 4 == 0) {
            sVertexBuilder.calcNormal(worldRenderer, worldRenderer.rawBufferIndex - 4 * sVertexBuilder.vertexSize);
        }
    }
    
    public void calcNormal(final WorldRenderer worldRenderer, final int n) {
        final FloatBuffer rawFloatBuffer = worldRenderer.rawFloatBuffer;
        final IntBuffer rawIntBuffer = worldRenderer.rawIntBuffer;
        final int rawBufferIndex = worldRenderer.rawBufferIndex;
        final float value = rawFloatBuffer.get(n + 0 * this.vertexSize);
        final float value2 = rawFloatBuffer.get(n + 0 * this.vertexSize + 1);
        final float value3 = rawFloatBuffer.get(n + 0 * this.vertexSize + 2);
        final float value4 = rawFloatBuffer.get(n + 0 * this.vertexSize + this.offsetUV);
        final float value5 = rawFloatBuffer.get(n + 0 * this.vertexSize + this.offsetUV + 1);
        final float value6 = rawFloatBuffer.get(n + 1 * this.vertexSize);
        final float value7 = rawFloatBuffer.get(n + 1 * this.vertexSize + 1);
        final float value8 = rawFloatBuffer.get(n + 1 * this.vertexSize + 2);
        final float value9 = rawFloatBuffer.get(n + 1 * this.vertexSize + this.offsetUV);
        final float value10 = rawFloatBuffer.get(n + 1 * this.vertexSize + this.offsetUV + 1);
        final float value11 = rawFloatBuffer.get(n + 2 * this.vertexSize);
        final float value12 = rawFloatBuffer.get(n + 2 * this.vertexSize + 1);
        final float value13 = rawFloatBuffer.get(n + 2 * this.vertexSize + 2);
        final float value14 = rawFloatBuffer.get(n + 2 * this.vertexSize + this.offsetUV);
        final float value15 = rawFloatBuffer.get(n + 2 * this.vertexSize + this.offsetUV + 1);
        final float value16 = rawFloatBuffer.get(n + 3 * this.vertexSize);
        final float value17 = rawFloatBuffer.get(n + 3 * this.vertexSize + 1);
        final float value18 = rawFloatBuffer.get(n + 3 * this.vertexSize + 2);
        final float value19 = rawFloatBuffer.get(n + 3 * this.vertexSize + this.offsetUV);
        final float value20 = rawFloatBuffer.get(n + 3 * this.vertexSize + this.offsetUV + 1);
        final float n2 = value11 - value;
        final float n3 = value12 - value2;
        final float n4 = value13 - value3;
        final float n5 = value16 - value6;
        final float n6 = value17 - value7;
        final float n7 = value18 - value8;
        final float n8 = n3 * n7 - n6 * n4;
        final float n9 = n4 * n5 - n7 * n2;
        final float n10 = n2 * n6 - n5 * n3;
        final float n11 = n8 * n8 + n9 * n9 + n10 * n10;
        final float n12 = (n11 != 0.0) ? ((float)(1.0 / Math.sqrt(n11))) : 1.0f;
        final float n13 = n8 * n12;
        final float n14 = n9 * n12;
        final float n15 = n10 * n12;
        final float n16 = value6 - value;
        final float n17 = value7 - value2;
        final float n18 = value8 - value3;
        final float n19 = value9 - value4;
        final float n20 = value10 - value5;
        final float n21 = value11 - value;
        final float n22 = value12 - value2;
        final float n23 = value13 - value3;
        final float n24 = value14 - value4;
        final float n25 = value15 - value5;
        final float n26 = n19 * n25 - n24 * n20;
        final float n27 = (n26 != 0.0f) ? (1.0f / n26) : 1.0f;
        final float n28 = (n25 * n16 - n20 * n21) * n27;
        final float n29 = (n25 * n17 - n20 * n22) * n27;
        final float n30 = (n25 * n18 - n20 * n23) * n27;
        final float n31 = (n19 * n21 - n24 * n16) * n27;
        final float n32 = (n19 * n22 - n24 * n17) * n27;
        final float n33 = (n19 * n23 - n24 * n18) * n27;
        final float n34 = n28 * n28 + n29 * n29 + n30 * n30;
        final float n35 = (n34 != 0.0) ? ((float)(1.0 / Math.sqrt(n34))) : 1.0f;
        final float n36 = n28 * n35;
        final float n37 = n29 * n35;
        final float n38 = n30 * n35;
        final float n39 = n31 * n31 + n32 * n32 + n33 * n33;
        final float n40 = (n39 != 0.0) ? ((float)(1.0 / Math.sqrt(n39))) : 1.0f;
        final float n41 = (n31 * n40 * (n15 * n37 - n14 * n38) + n32 * n40 * (n13 * n38 - n15 * n36) + n33 * n40 * (n14 * n36 - n13 * n37) < 0.0f) ? -1.0f : 1.0f;
        final int n42 = (((int)(n15 * 127.0f) & 0xFF) << 16) + (((int)(n14 * 127.0f) & 0xFF) << 8) + ((int)(n13 * 127.0f) & 0xFF);
        rawIntBuffer.put(n + 0 * this.vertexSize + this.offsetNormal, n42);
        rawIntBuffer.put(n + 1 * this.vertexSize + this.offsetNormal, n42);
        rawIntBuffer.put(n + 2 * this.vertexSize + this.offsetNormal, n42);
        rawIntBuffer.put(n + 3 * this.vertexSize + this.offsetNormal, n42);
        final int n43 = ((int)(n36 * 32767.0f) & 0xFFFF) + (((int)(n37 * 32767.0f) & 0xFFFF) << 16);
        final int n44 = ((int)(n38 * 32767.0f) & 0xFFFF) + (((int)(n41 * 32767.0f) & 0xFFFF) << 16);
        rawIntBuffer.put(n + 0 * this.vertexSize + 10, n43);
        rawIntBuffer.put(n + 0 * this.vertexSize + 10 + 1, n44);
        rawIntBuffer.put(n + 1 * this.vertexSize + 10, n43);
        rawIntBuffer.put(n + 1 * this.vertexSize + 10 + 1, n44);
        rawIntBuffer.put(n + 2 * this.vertexSize + 10, n43);
        rawIntBuffer.put(n + 2 * this.vertexSize + 10 + 1, n44);
        rawIntBuffer.put(n + 3 * this.vertexSize + 10, n43);
        rawIntBuffer.put(n + 3 * this.vertexSize + 10 + 1, n44);
        final float n45 = (value4 + value9 + value14 + value19) / 4.0f;
        final float n46 = (value5 + value10 + value15 + value20) / 4.0f;
        rawFloatBuffer.put(n + 0 * this.vertexSize + 8, n45);
        rawFloatBuffer.put(n + 0 * this.vertexSize + 8 + 1, n46);
        rawFloatBuffer.put(n + 1 * this.vertexSize + 8, n45);
        rawFloatBuffer.put(n + 1 * this.vertexSize + 8 + 1, n46);
        rawFloatBuffer.put(n + 2 * this.vertexSize + 8, n45);
        rawFloatBuffer.put(n + 2 * this.vertexSize + 8 + 1, n46);
        rawFloatBuffer.put(n + 3 * this.vertexSize + 8, n45);
        rawFloatBuffer.put(n + 3 * this.vertexSize + 8 + 1, n46);
    }
    
    public static void calcNormalChunkLayer(final WorldRenderer worldRenderer) {
        if (worldRenderer.func_178973_g().func_177350_b() && worldRenderer.drawMode == 7 && worldRenderer.vertexCount % 4 == 0) {
            final SVertexBuilder sVertexBuilder = worldRenderer.sVertexBuilder;
            endSetVertexFormat(worldRenderer);
            while (0 < worldRenderer.vertexCount * sVertexBuilder.vertexSize) {
                sVertexBuilder.calcNormal(worldRenderer, 0);
                final int n = 0 + sVertexBuilder.vertexSize * 4;
            }
        }
    }
    
    public static void drawArrays(final int n, final int n2, final int n3, final WorldRenderer worldRenderer) {
        if (n3 != 0) {
            final int func_177338_f = worldRenderer.func_178973_g().func_177338_f();
            if (func_177338_f == 56) {
                final ByteBuffer func_178966_f = worldRenderer.func_178966_f();
                func_178966_f.position(32);
                GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, func_177338_f, func_178966_f);
                func_178966_f.position(40);
                GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, func_177338_f, func_178966_f);
                func_178966_f.position(48);
                GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, func_177338_f, func_178966_f);
                func_178966_f.position(0);
                GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
                GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
                GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
                GL11.glDrawArrays(n, n2, n3);
                GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
                GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
                GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
            }
            else {
                GL11.glDrawArrays(n, n2, n3);
            }
        }
    }
    
    public static void startTexturedQuad(final WorldRenderer worldRenderer) {
        worldRenderer.setVertexFormat(SVertexFormat.defVertexFormatTextured);
    }
}
