package shadersmod.client;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;

public class DefaultTexture extends AbstractTexture
{
    public DefaultTexture() {
        this.loadTexture(null);
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) {
        ShadersTex.setupTexture(this.getMultiTexID(), ShadersTex.createAIntImage(1, -1), 1, 1, false, false);
    }
}
