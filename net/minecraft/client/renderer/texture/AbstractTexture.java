package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import shadersmod.client.*;

public abstract class AbstractTexture implements ITextureObject
{
    protected int glTextureId;
    protected boolean field_174940_b;
    protected boolean field_174941_c;
    protected boolean field_174938_d;
    protected boolean field_174939_e;
    private static final String __OBFID;
    public MultiTexID multiTex;
    
    public AbstractTexture() {
        this.glTextureId = -1;
    }
    
    public void func_174937_a(final boolean field_174940_b, final boolean field_174941_c) {
        this.field_174940_b = field_174940_b;
        this.field_174941_c = field_174941_c;
        int n;
        if (field_174940_b) {
            n = (field_174941_c ? 9987 : 9729);
        }
        else {
            n = (field_174941_c ? 9986 : 9728);
        }
        GlStateManager.func_179144_i(this.getGlTextureId());
        GL11.glTexParameteri(3553, 10241, n);
        GL11.glTexParameteri(3553, 10240, 9728);
    }
    
    @Override
    public void func_174936_b(final boolean b, final boolean b2) {
        this.field_174938_d = this.field_174940_b;
        this.field_174939_e = this.field_174941_c;
        this.func_174937_a(b, b2);
    }
    
    @Override
    public void func_174935_a() {
        this.func_174937_a(this.field_174938_d, this.field_174939_e);
    }
    
    @Override
    public int getGlTextureId() {
        if (this.glTextureId == -1) {
            this.glTextureId = TextureUtil.glGenTextures();
        }
        return this.glTextureId;
    }
    
    public void deleteGlTexture() {
        ShadersTex.deleteTextures(this, this.glTextureId);
        if (this.glTextureId != -1) {
            TextureUtil.deleteTexture(this.glTextureId);
            this.glTextureId = -1;
        }
    }
    
    @Override
    public MultiTexID getMultiTexID() {
        return ShadersTex.getMultiTexID(this);
    }
    
    static {
        __OBFID = "CL_00001047";
    }
}
