package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import java.util.*;
import com.google.common.collect.*;

public class SimpleBakedModel implements IBakedModel
{
    protected final List field_177563_a;
    protected final List field_177561_b;
    protected final boolean field_177562_c;
    protected final boolean ambientOcclusion;
    protected final TextureAtlasSprite texture;
    protected final ItemCameraTransforms field_177558_f;
    private static final String __OBFID;
    
    public SimpleBakedModel(final List field_177563_a, final List field_177561_b, final boolean field_177562_c, final boolean ambientOcclusion, final TextureAtlasSprite texture, final ItemCameraTransforms field_177558_f) {
        this.field_177563_a = field_177563_a;
        this.field_177561_b = field_177561_b;
        this.field_177562_c = field_177562_c;
        this.ambientOcclusion = ambientOcclusion;
        this.texture = texture;
        this.field_177558_f = field_177558_f;
    }
    
    @Override
    public List func_177551_a(final EnumFacing enumFacing) {
        return this.field_177561_b.get(enumFacing.ordinal());
    }
    
    @Override
    public List func_177550_a() {
        return this.field_177563_a;
    }
    
    @Override
    public boolean isGui3d() {
        return this.field_177562_c;
    }
    
    @Override
    public boolean isAmbientOcclusionEnabled() {
        return this.ambientOcclusion;
    }
    
    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }
    
    @Override
    public TextureAtlasSprite getTexture() {
        return this.texture;
    }
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.field_177558_f;
    }
    
    static {
        __OBFID = "CL_00002386";
    }
    
    public static class Builder
    {
        private final List field_177656_a;
        private final List field_177654_b;
        private final boolean field_177655_c;
        private TextureAtlasSprite field_177652_d;
        private boolean field_177653_e;
        private ItemCameraTransforms field_177651_f;
        private static final String __OBFID;
        
        public Builder(final ModelBlock modelBlock) {
            this(modelBlock.func_178309_b(), modelBlock.isAmbientOcclusionEnabled(), new ItemCameraTransforms(modelBlock.getThirdPersonTransform(), modelBlock.getFirstPersonTransform(), modelBlock.getHeadTransform(), modelBlock.getInGuiTransform()));
        }
        
        public Builder(final IBakedModel bakedModel, final TextureAtlasSprite textureAtlasSprite) {
            this(bakedModel.isGui3d(), bakedModel.isAmbientOcclusionEnabled(), bakedModel.getItemCameraTransforms());
            this.field_177652_d = bakedModel.getTexture();
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                this.func_177649_a(bakedModel, textureAtlasSprite, values[0]);
                int n = 0;
                ++n;
            }
            this.func_177647_a(bakedModel, textureAtlasSprite);
        }
        
        private void func_177649_a(final IBakedModel bakedModel, final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing) {
            final Iterator<BakedQuad> iterator = bakedModel.func_177551_a(enumFacing).iterator();
            while (iterator.hasNext()) {
                this.func_177650_a(enumFacing, new BreakingFour(iterator.next(), textureAtlasSprite));
            }
        }
        
        private void func_177647_a(final IBakedModel bakedModel, final TextureAtlasSprite textureAtlasSprite) {
            final Iterator<BakedQuad> iterator = bakedModel.func_177550_a().iterator();
            while (iterator.hasNext()) {
                this.func_177648_a(new BreakingFour(iterator.next(), textureAtlasSprite));
            }
        }
        
        private Builder(final boolean field_177655_c, final boolean field_177653_e, final ItemCameraTransforms field_177651_f) {
            this.field_177656_a = Lists.newArrayList();
            this.field_177654_b = Lists.newArrayListWithCapacity(6);
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                final EnumFacing enumFacing = values[0];
                this.field_177654_b.add(Lists.newArrayList());
                int n = 0;
                ++n;
            }
            this.field_177655_c = field_177655_c;
            this.field_177653_e = field_177653_e;
            this.field_177651_f = field_177651_f;
        }
        
        public Builder func_177650_a(final EnumFacing enumFacing, final BakedQuad bakedQuad) {
            this.field_177654_b.get(enumFacing.ordinal()).add(bakedQuad);
            return this;
        }
        
        public Builder func_177648_a(final BakedQuad bakedQuad) {
            this.field_177656_a.add(bakedQuad);
            return this;
        }
        
        public Builder func_177646_a(final TextureAtlasSprite field_177652_d) {
            this.field_177652_d = field_177652_d;
            return this;
        }
        
        public IBakedModel func_177645_b() {
            if (this.field_177652_d == null) {
                throw new RuntimeException("Missing particle!");
            }
            return new SimpleBakedModel(this.field_177656_a, this.field_177654_b, this.field_177655_c, this.field_177653_e, this.field_177652_d, this.field_177651_f);
        }
        
        static {
            __OBFID = "CL_00002385";
        }
    }
}
