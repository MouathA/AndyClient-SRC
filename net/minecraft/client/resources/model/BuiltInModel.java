package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public class BuiltInModel implements IBakedModel
{
    private ItemCameraTransforms field_177557_a;
    private static final String __OBFID;
    
    public BuiltInModel(final ItemCameraTransforms field_177557_a) {
        this.field_177557_a = field_177557_a;
    }
    
    @Override
    public List func_177551_a(final EnumFacing enumFacing) {
        return null;
    }
    
    @Override
    public List func_177550_a() {
        return null;
    }
    
    @Override
    public boolean isGui3d() {
        return false;
    }
    
    @Override
    public boolean isAmbientOcclusionEnabled() {
        return true;
    }
    
    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }
    
    @Override
    public TextureAtlasSprite getTexture() {
        return null;
    }
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.field_177557_a;
    }
    
    static {
        __OBFID = "CL_00002392";
    }
}
