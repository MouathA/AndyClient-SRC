package net.minecraft.client.resources;

import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.data.*;
import java.io.*;
import com.google.gson.*;
import org.apache.commons.io.*;

public class SimpleResource implements IResource
{
    private final Map mapMetadataSections;
    private final String field_177242_b;
    private final ResourceLocation srResourceLocation;
    private final InputStream resourceInputStream;
    private final InputStream mcmetaInputStream;
    private final IMetadataSerializer srMetadataSerializer;
    private boolean mcmetaJsonChecked;
    private JsonObject mcmetaJson;
    private static final String __OBFID;
    
    public SimpleResource(final String field_177242_b, final ResourceLocation srResourceLocation, final InputStream resourceInputStream, final InputStream mcmetaInputStream, final IMetadataSerializer srMetadataSerializer) {
        this.mapMetadataSections = Maps.newHashMap();
        this.field_177242_b = field_177242_b;
        this.srResourceLocation = srResourceLocation;
        this.resourceInputStream = resourceInputStream;
        this.mcmetaInputStream = mcmetaInputStream;
        this.srMetadataSerializer = srMetadataSerializer;
    }
    
    @Override
    public ResourceLocation func_177241_a() {
        return this.srResourceLocation;
    }
    
    @Override
    public InputStream getInputStream() {
        return this.resourceInputStream;
    }
    
    @Override
    public IMetadataSection getMetadata(final String s) {
        if (this != null) {
            return null;
        }
        if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
            this.mcmetaJsonChecked = true;
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
            this.mcmetaJson = new JsonParser().parse(bufferedReader).getAsJsonObject();
            IOUtils.closeQuietly(bufferedReader);
        }
        IMetadataSection metadataSection = this.mapMetadataSections.get(s);
        if (metadataSection == null) {
            metadataSection = this.srMetadataSerializer.parseMetadataSection(s, this.mcmetaJson);
        }
        return metadataSection;
    }
    
    @Override
    public String func_177240_d() {
        return this.field_177242_b;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleResource)) {
            return false;
        }
        final SimpleResource simpleResource = (SimpleResource)o;
        if (this.srResourceLocation != null) {
            if (!this.srResourceLocation.equals(simpleResource.srResourceLocation)) {
                return false;
            }
        }
        else if (simpleResource.srResourceLocation != null) {
            return false;
        }
        if (this.field_177242_b != null) {
            if (!this.field_177242_b.equals(simpleResource.field_177242_b)) {
                return false;
            }
        }
        else if (simpleResource.field_177242_b != null) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return 31 * ((this.field_177242_b != null) ? this.field_177242_b.hashCode() : 0) + ((this.srResourceLocation != null) ? this.srResourceLocation.hashCode() : 0);
    }
    
    static {
        __OBFID = "CL_00001093";
    }
}
