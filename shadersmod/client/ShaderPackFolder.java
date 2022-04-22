package shadersmod.client;

import optifine.*;
import java.io.*;

public class ShaderPackFolder implements IShaderPack
{
    protected File packFile;
    
    public ShaderPackFolder(final String s, final File packFile) {
        this.packFile = packFile;
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public InputStream getResourceAsStream(final String s) {
        final File file = new File(this.packFile, StrUtils.removePrefixSuffix(s, "/", "/"));
        return file.exists() ? new BufferedInputStream(new FileInputStream(file)) : null;
    }
    
    @Override
    public boolean hasDirectory(final String s) {
        final File file = new File(this.packFile, s.substring(1));
        return file.exists() && file.isDirectory();
    }
    
    @Override
    public String getName() {
        return this.packFile.getName();
    }
}
