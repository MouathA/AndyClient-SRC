package net.minecraft.client.model;

import com.google.common.collect.*;
import net.minecraft.entity.*;
import java.util.*;

public abstract class ModelBase
{
    public float swingProgress;
    public boolean isRiding;
    public boolean isChild;
    public List boxList;
    private Map modelTextureMap;
    public int textureWidth;
    public int textureHeight;
    private static final String __OBFID;
    
    public ModelBase() {
        this.isChild = true;
        this.boxList = Lists.newArrayList();
        this.modelTextureMap = Maps.newHashMap();
        this.textureWidth = 64;
        this.textureHeight = 32;
    }
    
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
    }
    
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
    }
    
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
    }
    
    public ModelRenderer getRandomModelBox(final Random random) {
        return this.boxList.get(random.nextInt(this.boxList.size()));
    }
    
    public void setTextureOffset(final String s, final int n, final int n2) {
        this.modelTextureMap.put(s, new TextureOffset(n, n2));
    }
    
    public TextureOffset getTextureOffset(final String s) {
        return this.modelTextureMap.get(s);
    }
    
    public static void func_178685_a(final ModelRenderer modelRenderer, final ModelRenderer modelRenderer2) {
        modelRenderer2.rotateAngleX = modelRenderer.rotateAngleX;
        modelRenderer2.rotateAngleY = modelRenderer.rotateAngleY;
        modelRenderer2.rotateAngleZ = modelRenderer.rotateAngleZ;
        modelRenderer2.rotationPointX = modelRenderer.rotationPointX;
        modelRenderer2.rotationPointY = modelRenderer.rotationPointY;
        modelRenderer2.rotationPointZ = modelRenderer.rotationPointZ;
    }
    
    public void setModelAttributes(final ModelBase modelBase) {
        this.swingProgress = modelBase.swingProgress;
        this.isRiding = modelBase.isRiding;
        this.isChild = modelBase.isChild;
    }
    
    static {
        __OBFID = "CL_00000845";
    }
}
