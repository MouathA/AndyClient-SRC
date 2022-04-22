package shadersmod.client;

import org.lwjgl.opengl.*;
import org.lwjgl.*;
import net.minecraft.client.renderer.*;
import java.nio.*;

public class HFNoiseTexture
{
    public int texID;
    public int textureUnit;
    
    public HFNoiseTexture(final int n, final int n2) {
        this.texID = GL11.glGenTextures();
        this.textureUnit = 15;
        final byte[] genHFNoiseImage = this.genHFNoiseImage(n, n2);
        final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(genHFNoiseImage.length);
        byteBuffer.put(genHFNoiseImage);
        byteBuffer.flip();
        GlStateManager.func_179144_i(this.texID);
        GL11.glTexImage2D(3553, 0, 6407, n, n2, 0, 6407, 5121, byteBuffer);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10241, 9729);
        GlStateManager.func_179144_i(0);
    }
    
    public int getID() {
        return this.texID;
    }
    
    public void destroy() {
        GlStateManager.func_179150_h(this.texID);
        this.texID = 0;
    }
    
    private int random(int n) {
        n ^= n << 13;
        n ^= n >> 17;
        n ^= n << 5;
        return n;
    }
    
    private byte random(final int n, final int n2, final int n3) {
        return (byte)(this.random((this.random(n) + this.random(n2 * 19)) * this.random(n3 * 23) - n3) % 128);
    }
    
    private byte[] genHFNoiseImage(final int n, final int n2) {
        final byte[] array = new byte[n * n2 * 3];
        while (0 < n2) {
            while (0 < n) {
                while (1 < 4) {
                    final byte[] array2 = array;
                    final int n3 = 0;
                    int n4 = 0;
                    ++n4;
                    array2[n3] = this.random(0, 0, 1);
                    int n5 = 0;
                    ++n5;
                }
                int n6 = 0;
                ++n6;
            }
            int n7 = 0;
            ++n7;
        }
        return array;
    }
}
