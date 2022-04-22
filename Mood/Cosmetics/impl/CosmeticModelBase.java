package Mood.Cosmetics.impl;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;

public class CosmeticModelBase extends ModelBase
{
    protected ModelBiped playerModel;
    
    public CosmeticModelBase(final RenderPlayer renderPlayer) {
        this.playerModel = (ModelBiped)renderPlayer.getMainModel();
    }
}
