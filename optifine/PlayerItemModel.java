package optifine;

import java.awt.*;
import net.minecraft.util.*;
import java.awt.image.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.model.*;

public class PlayerItemModel
{
    private Dimension textureSize;
    private boolean usePlayerTexture;
    private PlayerItemRenderer[] modelRenderers;
    private ResourceLocation textureLocation;
    private BufferedImage textureImage;
    private DynamicTexture texture;
    private ResourceLocation locationMissing;
    
    public PlayerItemModel(final Dimension textureSize, final boolean usePlayerTexture, final PlayerItemRenderer[] modelRenderers) {
        this.textureSize = null;
        this.usePlayerTexture = false;
        this.modelRenderers = new PlayerItemRenderer[0];
        this.textureLocation = null;
        this.textureImage = null;
        this.texture = null;
        this.locationMissing = new ResourceLocation("textures/blocks/wool_colored_red.png");
        this.textureSize = textureSize;
        this.usePlayerTexture = usePlayerTexture;
        this.modelRenderers = modelRenderers;
    }
    
    public void render(final ModelBiped modelBiped, final AbstractClientPlayer abstractClientPlayer, final float n, final float n2) {
        final TextureManager textureManager = Config.getTextureManager();
        if (this.usePlayerTexture) {
            textureManager.bindTexture(abstractClientPlayer.getLocationSkin());
        }
        else if (this.textureLocation != null) {
            if (this.texture == null && this.textureImage != null) {
                this.texture = new DynamicTexture(this.textureImage);
                Minecraft.getMinecraft().getTextureManager().loadTexture(this.textureLocation, this.texture);
            }
            textureManager.bindTexture(this.textureLocation);
        }
        else {
            textureManager.bindTexture(this.locationMissing);
        }
        while (0 < this.modelRenderers.length) {
            final PlayerItemRenderer playerItemRenderer = this.modelRenderers[0];
            if (abstractClientPlayer.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            playerItemRenderer.render(modelBiped, n);
            int n3 = 0;
            ++n3;
        }
    }
    
    public static ModelRenderer getAttachModel(final ModelBiped modelBiped, final int n) {
        switch (n) {
            case 0: {
                return modelBiped.bipedBody;
            }
            case 1: {
                return modelBiped.bipedHead;
            }
            case 2: {
                return modelBiped.bipedLeftArm;
            }
            case 3: {
                return modelBiped.bipedRightArm;
            }
            case 4: {
                return modelBiped.bipedLeftLeg;
            }
            case 5: {
                return modelBiped.bipedRightLeg;
            }
            default: {
                return null;
            }
        }
    }
    
    public BufferedImage getTextureImage() {
        return this.textureImage;
    }
    
    public void setTextureImage(final BufferedImage textureImage) {
        this.textureImage = textureImage;
    }
    
    public DynamicTexture getTexture() {
        return this.texture;
    }
    
    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }
    
    public void setTextureLocation(final ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }
    
    public boolean isUsePlayerTexture() {
        return this.usePlayerTexture;
    }
}
