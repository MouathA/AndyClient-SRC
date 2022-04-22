package shadersmod.client;

import java.io.*;

public class ShaderPackNone implements IShaderPack
{
    @Override
    public void close() {
    }
    
    @Override
    public InputStream getResourceAsStream(final String s) {
        return null;
    }
    
    @Override
    public boolean hasDirectory(final String s) {
        return false;
    }
    
    @Override
    public String getName() {
        return Shaders.packNameNone;
    }
}
