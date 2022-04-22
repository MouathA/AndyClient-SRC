package shadersmod.client;

import java.io.*;
import optifine.*;
import java.util.zip.*;

public class ShaderPackZip implements IShaderPack
{
    protected File packFile;
    protected ZipFile packZipFile;
    
    public ShaderPackZip(final String s, final File packFile) {
        this.packFile = packFile;
        this.packZipFile = null;
    }
    
    @Override
    public void close() {
        if (this.packZipFile != null) {
            this.packZipFile.close();
            this.packZipFile = null;
        }
    }
    
    @Override
    public InputStream getResourceAsStream(final String s) {
        if (this.packZipFile == null) {
            this.packZipFile = new ZipFile(this.packFile);
        }
        final ZipEntry entry = this.packZipFile.getEntry(StrUtils.removePrefix(s, "/"));
        return (entry == null) ? null : this.packZipFile.getInputStream(entry);
    }
    
    @Override
    public boolean hasDirectory(final String s) {
        if (this.packZipFile == null) {
            this.packZipFile = new ZipFile(this.packFile);
        }
        return this.packZipFile.getEntry(StrUtils.removePrefix(s, "/")) != null;
    }
    
    @Override
    public String getName() {
        return this.packFile.getName();
    }
}
