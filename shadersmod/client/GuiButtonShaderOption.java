package shadersmod.client;

import net.minecraft.client.gui.*;

public class GuiButtonShaderOption extends GuiButton
{
    private ShaderOption shaderOption;
    
    public GuiButtonShaderOption(final int n, final int n2, final int n3, final int n4, final int n5, final ShaderOption shaderOption, final String s) {
        super(n, n2, n3, n4, n5, s);
        this.shaderOption = null;
        this.shaderOption = shaderOption;
    }
    
    public ShaderOption getShaderOption() {
        return this.shaderOption;
    }
}
