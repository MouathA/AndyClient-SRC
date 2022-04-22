package net.minecraft.client.resources.model;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;

public class ModelManager implements IResourceManagerReloadListener
{
    private IRegistry modelRegistry;
    private final TextureMap field_174956_b;
    private final BlockModelShapes field_174957_c;
    private IBakedModel defaultModel;
    private static final String __OBFID;
    
    public ModelManager(final TextureMap field_174956_b) {
        this.field_174956_b = field_174956_b;
        this.field_174957_c = new BlockModelShapes(this);
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.modelRegistry = new ModelBakery(resourceManager, this.field_174956_b, this.field_174957_c).setupModelRegistry();
        this.defaultModel = (IBakedModel)this.modelRegistry.getObject(ModelBakery.MODEL_MISSING);
        this.field_174957_c.func_178124_c();
    }
    
    public IBakedModel getModel(final ModelResourceLocation modelResourceLocation) {
        if (modelResourceLocation == null) {
            return this.defaultModel;
        }
        final IBakedModel bakedModel = (IBakedModel)this.modelRegistry.getObject(modelResourceLocation);
        return (bakedModel == null) ? this.defaultModel : bakedModel;
    }
    
    public IBakedModel getMissingModel() {
        return this.defaultModel;
    }
    
    public TextureMap func_174952_b() {
        return this.field_174956_b;
    }
    
    public BlockModelShapes getBlockModelShapes() {
        return this.field_174957_c;
    }
    
    static {
        __OBFID = "CL_00002388";
    }
}
