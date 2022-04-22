package optifine;

import net.minecraft.util.*;
import java.nio.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;

public class TextureAnimation
{
    private String srcTex;
    private String dstTex;
    ResourceLocation dstTexLoc;
    private int dstTextId;
    private int dstX;
    private int dstY;
    private int frameWidth;
    private int frameHeight;
    private TextureAnimationFrame[] frames;
    private int activeFrame;
    byte[] srcData;
    private ByteBuffer imageData;
    
    public TextureAnimation(final String srcTex, final byte[] srcData, final String dstTex, final ResourceLocation dstTexLoc, final int dstX, final int dstY, final int frameWidth, final int frameHeight, final Properties properties, final int n) {
        this.srcTex = null;
        this.dstTex = null;
        this.dstTexLoc = null;
        this.dstTextId = -1;
        this.dstX = 0;
        this.dstY = 0;
        this.frameWidth = 0;
        this.frameHeight = 0;
        this.frames = null;
        this.activeFrame = 0;
        this.srcData = null;
        this.imageData = null;
        this.srcTex = srcTex;
        this.dstTex = dstTex;
        this.dstTexLoc = dstTexLoc;
        this.dstX = dstX;
        this.dstY = dstY;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        final int n2 = frameWidth * frameHeight * 4;
        if (srcData.length % n2 != 0) {
            Config.warn("Invalid animated texture length: " + srcData.length + ", frameWidth: " + frameWidth + ", frameHeight: " + frameHeight);
        }
        this.srcData = srcData;
        final int n3 = srcData.length / n2;
        if (properties.get("tile.0") != null) {
            while (properties.get("tile." + 0) != null) {
                int n4 = 0;
                ++n4;
            }
        }
        final int int1 = Config.parseInt(((Hashtable<K, String>)properties).get("duration"), n);
        this.frames = new TextureAnimationFrame[1];
        while (0 < this.frames.length) {
            this.frames[0] = new TextureAnimationFrame(Config.parseInt(((Hashtable<K, String>)properties).get("tile." + 0), 0), Config.parseInt(((Hashtable<K, String>)properties).get("duration." + 0), int1));
            int n5 = 0;
            ++n5;
        }
    }
    
    public boolean nextFrame() {
        if (this.frames.length <= 0) {
            return false;
        }
        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
        }
        final TextureAnimationFrame textureAnimationFrame2;
        final TextureAnimationFrame textureAnimationFrame = textureAnimationFrame2 = this.frames[this.activeFrame];
        ++textureAnimationFrame2.counter;
        if (textureAnimationFrame.counter < textureAnimationFrame.duration) {
            return false;
        }
        textureAnimationFrame.counter = 0;
        ++this.activeFrame;
        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
        }
        return true;
    }
    
    public int getActiveFrameIndex() {
        if (this.frames.length <= 0) {
            return 0;
        }
        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
        }
        return this.frames[this.activeFrame].index;
    }
    
    public int getFrameCount() {
        return this.frames.length;
    }
    
    public boolean updateTexture() {
        if (this.dstTextId < 0) {
            final ITextureObject texture = TextureUtils.getTexture(this.dstTexLoc);
            if (texture == null) {
                return false;
            }
            this.dstTextId = texture.getGlTextureId();
        }
        if (this.imageData == null) {
            (this.imageData = GLAllocation.createDirectByteBuffer(this.srcData.length)).put(this.srcData);
            this.srcData = null;
        }
        if (!this.nextFrame()) {
            return false;
        }
        final int n = this.frameWidth * this.frameHeight * 4;
        final int n2 = n * this.getActiveFrameIndex();
        if (n2 + n > this.imageData.capacity()) {
            return false;
        }
        this.imageData.position(n2);
        GlStateManager.func_179144_i(this.dstTextId);
        GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.imageData);
        return true;
    }
    
    public String getSrcTex() {
        return this.srcTex;
    }
    
    public String getDstTex() {
        return this.dstTex;
    }
    
    public ResourceLocation getDstTexLoc() {
        return this.dstTexLoc;
    }
}
