package net.minecraft.client.renderer.texture;

import java.awt.image.*;
import optifine.*;
import shadersmod.client.*;
import net.minecraft.client.resources.*;
import java.io.*;

public class DynamicTexture extends AbstractTexture
{
    private final int[] dynamicTextureData;
    private final int width;
    private final int height;
    private static final String __OBFID;
    private boolean shadersInitialized;
    
    public DynamicTexture(final BufferedImage bufferedImage) {
        this(bufferedImage.getWidth(), bufferedImage.getHeight());
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.dynamicTextureData, 0, bufferedImage.getWidth());
        this.updateDynamicTexture();
    }
    
    public DynamicTexture(final int width, final int height) {
        this.shadersInitialized = false;
        this.width = width;
        this.height = height;
        this.dynamicTextureData = new int[width * height * 3];
        if (Config.isShaders()) {
            ShadersTex.initDynamicTexture(this.getGlTextureId(), width, height, this);
            this.shadersInitialized = true;
        }
        else {
            TextureUtil.allocateTexture(this.getGlTextureId(), width, height);
        }
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
    }
    
    public void updateDynamicTexture() {
        if (Config.isShaders()) {
            if (!this.shadersInitialized) {
                ShadersTex.initDynamicTexture(this.getGlTextureId(), this.width, this.height, this);
                this.shadersInitialized = true;
            }
            ShadersTex.updateDynamicTexture(this.getGlTextureId(), this.dynamicTextureData, this.width, this.height, this);
        }
        else {
            TextureUtil.uploadTexture(this.getGlTextureId(), this.dynamicTextureData, this.width, this.height);
        }
    }
    
    public int[] getTextureData() {
        return this.dynamicTextureData;
    }
    
    static {
        __OBFID = "CL_00001048";
    }
}
