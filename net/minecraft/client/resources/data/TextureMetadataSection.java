package net.minecraft.client.resources.data;

import java.util.*;

public class TextureMetadataSection implements IMetadataSection
{
    private final boolean textureBlur;
    private final boolean textureClamp;
    private final List listMipmaps;
    private static final String __OBFID;
    
    public TextureMetadataSection(final boolean textureBlur, final boolean textureClamp, final List listMipmaps) {
        this.textureBlur = textureBlur;
        this.textureClamp = textureClamp;
        this.listMipmaps = listMipmaps;
    }
    
    public boolean getTextureBlur() {
        return this.textureBlur;
    }
    
    public boolean getTextureClamp() {
        return this.textureClamp;
    }
    
    public List getListMipmaps() {
        return Collections.unmodifiableList((List<?>)this.listMipmaps);
    }
    
    static {
        __OBFID = "CL_00001114";
    }
}
