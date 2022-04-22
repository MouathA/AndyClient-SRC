package shadersmod.client;

import org.lwjgl.opengl.*;

public class ShaderUniformFloat4 extends ShaderUniformBase
{
    private float[] values;
    
    public ShaderUniformFloat4(final String s) {
        super(s);
        this.values = new float[4];
    }
    
    @Override
    protected void onProgramChanged() {
        this.values[0] = 0.0f;
        this.values[1] = 0.0f;
        this.values[2] = 0.0f;
        this.values[3] = 0.0f;
    }
    
    public void setValue(final float n, final float n2, final float n3, final float n4) {
        if (this.getLocation() >= 0 && (this.values[0] != n || this.values[1] != n2 || this.values[2] != n3 || this.values[3] != n4)) {
            ARBShaderObjects.glUniform4fARB(this.getLocation(), n, n2, n3, n4);
            Shaders.checkGLError(this.getName());
            this.values[0] = n;
            this.values[1] = n2;
            this.values[2] = n3;
            this.values[3] = n4;
        }
    }
    
    public float[] getValues() {
        return this.values;
    }
}
