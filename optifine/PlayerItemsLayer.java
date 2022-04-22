package optifine;

import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import java.util.*;

public class PlayerItemsLayer implements LayerRenderer
{
    private RenderPlayer renderPlayer;
    
    public PlayerItemsLayer(final RenderPlayer renderPlayer) {
        this.renderPlayer = null;
        this.renderPlayer = renderPlayer;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.renderEquippedItems(entityLivingBase, n7, n3);
    }
    
    protected void renderEquippedItems(final EntityLivingBase entityLivingBase, final float n, final float n2) {
        if (Config.isShowCapes() && entityLivingBase instanceof AbstractClientPlayer) {
            final AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer)entityLivingBase;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            PlayerConfigurations.renderPlayerItems((ModelBiped)this.renderPlayer.getMainModel(), abstractClientPlayer, n, n2);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    public static void register(final Map map) {
        final Iterator<Object> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            final V value = map.get(iterator.next());
            if (value instanceof RenderPlayer) {
                final RenderPlayer renderPlayer = (RenderPlayer)value;
                renderPlayer.addLayer(new PlayerItemsLayer(renderPlayer));
            }
        }
    }
}
