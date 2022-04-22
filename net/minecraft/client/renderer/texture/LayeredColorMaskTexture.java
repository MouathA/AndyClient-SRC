package net.minecraft.client.renderer.texture;

import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.resources.*;
import java.awt.*;
import java.awt.image.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import java.io.*;

public class LayeredColorMaskTexture extends AbstractTexture
{
    private static final Logger field_174947_f;
    private final ResourceLocation field_174948_g;
    private final List field_174949_h;
    private final List field_174950_i;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002404";
        field_174947_f = LogManager.getLogger();
    }
    
    public LayeredColorMaskTexture(final ResourceLocation field_174948_g, final List field_174949_h, final List field_174950_i) {
        this.field_174948_g = field_174948_g;
        this.field_174949_h = field_174949_h;
        this.field_174950_i = field_174950_i;
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        final BufferedImage func_177053_a = TextureUtil.func_177053_a(resourceManager.getResource(this.field_174948_g).getInputStream());
        func_177053_a.getType();
        if (6 == 0) {}
        final BufferedImage bufferedImage = new BufferedImage(func_177053_a.getWidth(), func_177053_a.getHeight(), 6);
        bufferedImage.getGraphics().drawImage(func_177053_a, 0, 0, null);
        while (0 < this.field_174949_h.size() && 0 < this.field_174950_i.size()) {
            final String s = this.field_174949_h.get(0);
            final MapColor func_176768_e = this.field_174950_i.get(0).func_176768_e();
            if (s != null) {
                final BufferedImage func_177053_a2 = TextureUtil.func_177053_a(resourceManager.getResource(new ResourceLocation(s)).getInputStream());
                if (func_177053_a2.getWidth() == bufferedImage.getWidth() && func_177053_a2.getHeight() == bufferedImage.getHeight() && func_177053_a2.getType() == 6) {
                    while (0 < func_177053_a2.getHeight()) {
                        while (0 < func_177053_a2.getWidth()) {
                            final int rgb = func_177053_a2.getRGB(0, 0);
                            if ((rgb & 0xFF000000) != 0x0) {
                                func_177053_a2.setRGB(0, 0, ((rgb & 0xFF0000) << 8 & 0xFF000000) | (MathHelper.func_180188_d(func_177053_a.getRGB(0, 0), func_176768_e.colorValue) & 0xFFFFFF));
                            }
                            int n = 0;
                            ++n;
                        }
                        int n2 = 0;
                        ++n2;
                    }
                    bufferedImage.getGraphics().drawImage(func_177053_a2, 0, 0, null);
                }
            }
            int n3 = 0;
            ++n3;
        }
        TextureUtil.uploadTextureImage(this.getGlTextureId(), bufferedImage);
    }
}
