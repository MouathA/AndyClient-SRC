package optifine;

import java.nio.*;
import java.awt.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class Mipmaps
{
    private final String iconName;
    private final int width;
    private final int height;
    private final int[] data;
    private final boolean direct;
    private int[][] mipmapDatas;
    private IntBuffer[] mipmapBuffers;
    private Dimension[] mipmapDimensions;
    
    public Mipmaps(final String iconName, final int width, final int height, final int[] data, final boolean direct) {
        this.iconName = iconName;
        this.width = width;
        this.height = height;
        this.data = data;
        this.direct = direct;
        this.mipmapDimensions = makeMipmapDimensions(width, height, iconName);
        this.mipmapDatas = generateMipMapData(data, width, height, this.mipmapDimensions);
        if (direct) {
            this.mipmapBuffers = makeMipmapBuffers(this.mipmapDimensions, this.mipmapDatas);
        }
    }
    
    public static Dimension[] makeMipmapDimensions(final int n, final int n2, final String s) {
        final int ceilPowerOfTwo = TextureUtils.ceilPowerOfTwo(n);
        final int ceilPowerOfTwo2 = TextureUtils.ceilPowerOfTwo(n2);
        if (ceilPowerOfTwo != n || ceilPowerOfTwo2 != n2) {
            Config.warn("Mipmaps not possible (power of 2 dimensions needed), texture: " + s + ", dim: " + n + "x" + n2);
            return new Dimension[0];
        }
        final ArrayList<Dimension> list = new ArrayList<Dimension>();
        while (true) {
            list.add(new Dimension(1, 1));
        }
    }
    
    public static int[][] generateMipMapData(final int[] array, final int n, final int n2, final Dimension[] array2) {
        final int[][] array3 = new int[array2.length][];
        while (0 < array2.length) {
            final Dimension dimension = array2[0];
            final int width = dimension.width;
            final int height = dimension.height;
            array3[0] = new int[width * height];
            if (width <= 1 || height <= 1) {}
            int n3 = 0;
            ++n3;
        }
        return array3;
    }
    
    public static int alphaBlend(final int n, final int n2, final int n3, final int n4) {
        return alphaBlend(alphaBlend(n, n2), alphaBlend(n3, n4));
    }
    
    private static int alphaBlend(final int n, final int n2) {
        return 0x0 | ((n >> 16 & 0xFF) * 1 + (n2 >> 16 & 0xFF) * 1) / 2 << 16 | ((n >> 8 & 0xFF) * 1 + (n2 >> 8 & 0xFF) * 1) / 2 << 8 | ((n & 0xFF) * 1 + (n2 & 0xFF) * 1) / 2;
    }
    
    private int averageColor(final int n, final int n2) {
        return (((n & 0xFF000000) >> 24 & 0xFF) + ((n2 & 0xFF000000) >> 24 & 0xFF) >> 1 << 24) + ((n & 0xFEFEFE) + (n2 & 0xFEFEFE) >> 1);
    }
    
    public static IntBuffer[] makeMipmapBuffers(final Dimension[] array, final int[][] array2) {
        if (array == null) {
            return null;
        }
        final IntBuffer[] array3 = new IntBuffer[array.length];
        while (0 < array.length) {
            final Dimension dimension = array[0];
            final IntBuffer directIntBuffer = GLAllocation.createDirectIntBuffer(dimension.width * dimension.height);
            final int[] array4 = array2[0];
            directIntBuffer.clear();
            directIntBuffer.put(array4);
            directIntBuffer.clear();
            array3[0] = directIntBuffer;
            int n = 0;
            ++n;
        }
        return array3;
    }
    
    public static void allocateMipmapTextures(final int n, final int n2, final String s) {
        final Dimension[] mipmapDimensions = makeMipmapDimensions(n, n2, s);
        while (0 < mipmapDimensions.length) {
            final Dimension dimension = mipmapDimensions[0];
            GL11.glTexImage2D(3553, 1, 6408, dimension.width, dimension.height, 0, 32993, 33639, (IntBuffer)null);
            int n3 = 0;
            ++n3;
        }
    }
}
