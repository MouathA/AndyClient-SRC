package net.minecraft.client.resources;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.resources.data.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;

public class DefaultResourcePack implements IResourcePack
{
    public static final Set defaultResourceDomains;
    private final Map field_152781_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001073";
        defaultResourceDomains = ImmutableSet.of("minecraft", "realms", "wdl");
    }
    
    public DefaultResourcePack(final Map field_152781_b) {
        this.field_152781_b = field_152781_b;
    }
    
    @Override
    public InputStream getInputStream(final ResourceLocation resourceLocation) throws IOException {
        final InputStream resourceStream = this.getResourceStream(resourceLocation);
        if (resourceStream != null) {
            return resourceStream;
        }
        final InputStream func_152780_c = this.func_152780_c(resourceLocation);
        if (func_152780_c != null) {
            return func_152780_c;
        }
        throw new FileNotFoundException(resourceLocation.getResourcePath());
    }
    
    public InputStream func_152780_c(final ResourceLocation resourceLocation) throws IOException {
        final File file = this.field_152781_b.get(resourceLocation.toString());
        return (file != null && file.isFile()) ? new FileInputStream(file) : null;
    }
    
    private InputStream getResourceStream(final ResourceLocation resourceLocation) {
        return DefaultResourcePack.class.getResourceAsStream("/assets/" + resourceLocation.getResourceDomain() + "/" + resourceLocation.getResourcePath());
    }
    
    @Override
    public boolean resourceExists(final ResourceLocation resourceLocation) {
        return this.getResourceStream(resourceLocation) != null || this.field_152781_b.containsKey(resourceLocation.toString());
    }
    
    @Override
    public Set getResourceDomains() {
        return DefaultResourcePack.defaultResourceDomains;
    }
    
    @Override
    public IMetadataSection getPackMetadata(final IMetadataSerializer metadataSerializer, final String s) throws IOException {
        return AbstractResourcePack.readMetadata(metadataSerializer, new FileInputStream(this.field_152781_b.get("pack.mcmeta")), s);
    }
    
    @Override
    public BufferedImage getPackImage() throws IOException {
        return TextureUtil.func_177053_a(DefaultResourcePack.class.getResourceAsStream("/" + new ResourceLocation("pack.png").getResourcePath()));
    }
    
    @Override
    public String getPackName() {
        return "Default";
    }
}
