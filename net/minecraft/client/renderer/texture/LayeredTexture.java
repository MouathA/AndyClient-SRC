package net.minecraft.client.renderer.texture;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class LayeredTexture extends AbstractTexture
{
    private static final Logger logger;
    public final List layeredTextureNames;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001051";
        logger = LogManager.getLogger();
    }
    
    public LayeredTexture(final String... array) {
        this.layeredTextureNames = Lists.newArrayList((Object[])array);
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        BufferedImage bufferedImage = null;
        for (final String s : this.layeredTextureNames) {
            if (s != null) {
                final BufferedImage func_177053_a = TextureUtil.func_177053_a(resourceManager.getResource(new ResourceLocation(s)).getInputStream());
                if (bufferedImage == null) {
                    bufferedImage = new BufferedImage(func_177053_a.getWidth(), func_177053_a.getHeight(), 2);
                }
                bufferedImage.getGraphics().drawImage(func_177053_a, 0, 0, null);
            }
        }
        TextureUtil.uploadTextureImage(this.getGlTextureId(), bufferedImage);
    }
}
