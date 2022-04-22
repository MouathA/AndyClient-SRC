package shadersmod.client;

import java.io.*;

public class ShaderPackDefault implements IShaderPack
{
    @Override
    public void close() {
    }
    
    @Override
    public InputStream getResourceAsStream(final String s) {
        return ShaderPackDefault.class.getResourceAsStream(s);
    }
    
    @Override
    public String getName() {
        return Shaders.packNameDefault;
    }
    
    @Override
    public boolean hasDirectory(final String s) {
        return false;
    }
}
