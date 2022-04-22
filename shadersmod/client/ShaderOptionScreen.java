package shadersmod.client;

public class ShaderOptionScreen extends ShaderOption
{
    public ShaderOptionScreen(final String s) {
        super(s, null, null, new String[1], null, null);
    }
    
    @Override
    public String getNameText() {
        return Shaders.translate("screen." + this.getName(), this.getName());
    }
    
    @Override
    public String getDescriptionText() {
        return Shaders.translate("screen." + this.getName() + ".comment", null);
    }
}
