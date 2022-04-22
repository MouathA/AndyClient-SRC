package net.minecraft.client.resources.model;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import java.util.*;
import com.google.common.collect.*;

public class WeightedBakedModel implements IBakedModel
{
    private final int totalWeight;
    private final List models;
    private final IBakedModel baseModel;
    private static final String __OBFID;
    
    public WeightedBakedModel(final List models) {
        this.models = models;
        this.totalWeight = WeightedRandom.getTotalWeight(models);
        this.baseModel = models.get(0).model;
    }
    
    @Override
    public List func_177551_a(final EnumFacing enumFacing) {
        return this.baseModel.func_177551_a(enumFacing);
    }
    
    @Override
    public List func_177550_a() {
        return this.baseModel.func_177550_a();
    }
    
    @Override
    public boolean isGui3d() {
        return this.baseModel.isGui3d();
    }
    
    @Override
    public boolean isAmbientOcclusionEnabled() {
        return this.baseModel.isAmbientOcclusionEnabled();
    }
    
    @Override
    public boolean isBuiltInRenderer() {
        return this.baseModel.isBuiltInRenderer();
    }
    
    @Override
    public TextureAtlasSprite getTexture() {
        return this.baseModel.getTexture();
    }
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.baseModel.getItemCameraTransforms();
    }
    
    public IBakedModel func_177564_a(final long n) {
        return ((MyWeighedRandomItem)WeightedRandom.func_180166_a(this.models, Math.abs((int)n >> 16) % this.totalWeight)).model;
    }
    
    static {
        __OBFID = "CL_00002384";
    }
    
    public static class Builder
    {
        private List field_177678_a;
        private static final String __OBFID;
        
        public Builder() {
            this.field_177678_a = Lists.newArrayList();
        }
        
        public Builder add(final IBakedModel bakedModel, final int n) {
            this.field_177678_a.add(new MyWeighedRandomItem(bakedModel, n));
            return this;
        }
        
        public WeightedBakedModel build() {
            Collections.sort((List<Comparable>)this.field_177678_a);
            return new WeightedBakedModel(this.field_177678_a);
        }
        
        public IBakedModel first() {
            return this.field_177678_a.get(0).model;
        }
        
        static {
            __OBFID = "CL_00002383";
        }
    }
    
    static class MyWeighedRandomItem extends WeightedRandom.Item implements Comparable
    {
        protected final IBakedModel model;
        private static final String __OBFID;
        
        public MyWeighedRandomItem(final IBakedModel model, final int n) {
            super(n);
            this.model = model;
        }
        
        public int func_177634_a(final MyWeighedRandomItem myWeighedRandomItem) {
            return ComparisonChain.start().compare(myWeighedRandomItem.itemWeight, this.itemWeight).compare(this.func_177635_a(), myWeighedRandomItem.func_177635_a()).result();
        }
        
        protected int func_177635_a() {
            int size = this.model.func_177550_a().size();
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                size += this.model.func_177551_a(values[0]).size();
                int n = 0;
                ++n;
            }
            return size;
        }
        
        @Override
        public String toString() {
            return "MyWeighedRandomItem{weight=" + this.itemWeight + ", model=" + this.model + '}';
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.func_177634_a((MyWeighedRandomItem)o);
        }
        
        static {
            __OBFID = "CL_00002382";
        }
    }
}
