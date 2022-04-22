package net.minecraft.client.renderer.texture;

import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.resources.data.*;
import optifine.*;
import shadersmod.client.*;
import net.minecraft.client.resources.*;
import java.awt.image.*;
import java.io.*;

public class SimpleTexture extends AbstractTexture
{
    private static final Logger logger;
    protected final ResourceLocation textureLocation;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001052";
        logger = LogManager.getLogger();
    }
    
    public SimpleTexture(final ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        final IResource resource = resourceManager.getResource(this.textureLocation);
        final InputStream inputStream = resource.getInputStream();
        final BufferedImage func_177053_a = TextureUtil.func_177053_a(inputStream);
        if (resource.hasMetadata()) {
            final TextureMetadataSection textureMetadataSection = (TextureMetadataSection)resource.getMetadata("texture");
            if (textureMetadataSection != null) {
                textureMetadataSection.getTextureBlur();
                textureMetadataSection.getTextureClamp();
            }
        }
        if (Config.isShaders()) {
            ShadersTex.loadSimpleTexture(this.getGlTextureId(), func_177053_a, false, false, resourceManager, this.textureLocation, this.getMultiTexID());
        }
        else {
            TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), func_177053_a, false, false);
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }
}
