package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;

public class LayerVillagerArmor extends LayerBipedArmor
{
    private static final String __OBFID;
    
    public LayerVillagerArmor(final RendererLivingEntity rendererLivingEntity) {
        super(rendererLivingEntity);
    }
    
    @Override
    protected void func_177177_a() {
        this.field_177189_c = new ModelZombieVillager(0.5f, 0.0f, true);
        this.field_177186_d = new ModelZombieVillager(1.0f, 0.0f, true);
    }
    
    static {
        __OBFID = "CL_00002409";
    }
}
