package net.minecraft.client.resources;

import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.data.*;
import com.google.common.base.*;
import java.io.*;
import org.apache.commons.io.*;
import com.google.gson.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;

public abstract class AbstractResourcePack implements IResourcePack
{
    private static final Logger resourceLog;
    public final File resourcePackFile;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001072";
        resourceLog = LogManager.getLogger();
    }
    
    public AbstractResourcePack(final File resourcePackFile) {
        this.resourcePackFile = resourcePackFile;
    }
    
    private static String locationToName(final ResourceLocation resourceLocation) {
        return String.format("%s/%s/%s", "assets", resourceLocation.getResourceDomain(), resourceLocation.getResourcePath());
    }
    
    protected static String getRelativeName(final File file, final File file2) {
        return file.toURI().relativize(file2.toURI()).getPath();
    }
    
    @Override
    public InputStream getInputStream(final ResourceLocation resourceLocation) throws IOException {
        return this.getInputStreamByName(locationToName(resourceLocation));
    }
    
    @Override
    public boolean resourceExists(final ResourceLocation resourceLocation) {
        return this.hasResourceName(locationToName(resourceLocation));
    }
    
    protected abstract InputStream getInputStreamByName(final String p0) throws IOException;
    
    protected abstract boolean hasResourceName(final String p0);
    
    protected void logNameNotLowercase(final String s) {
        AbstractResourcePack.resourceLog.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", s, this.resourcePackFile);
    }
    
    @Override
    public IMetadataSection getPackMetadata(final IMetadataSerializer metadataSerializer, final String s) throws IOException {
        return readMetadata(metadataSerializer, this.getInputStreamByName("pack.mcmeta"), s);
    }
    
    static IMetadataSection readMetadata(final IMetadataSerializer metadataSerializer, final InputStream inputStream, final String s) {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
        final JsonObject asJsonObject = new JsonParser().parse(bufferedReader).getAsJsonObject();
        IOUtils.closeQuietly(bufferedReader);
        return metadataSerializer.parseMetadataSection(s, asJsonObject);
    }
    
    @Override
    public BufferedImage getPackImage() throws IOException {
        return TextureUtil.func_177053_a(this.getInputStreamByName("pack.png"));
    }
    
    @Override
    public String getPackName() {
        return this.resourcePackFile.getName();
    }
}
