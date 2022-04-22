package net.minecraft.client.resources;

import net.minecraft.client.resources.data.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;
import java.io.*;

public class FallbackResourceManager implements IResourceManager
{
    private static final Logger field_177246_b;
    protected final List resourcePacks;
    private final IMetadataSerializer frmMetadataSerializer;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001074";
        field_177246_b = LogManager.getLogger();
    }
    
    public FallbackResourceManager(final IMetadataSerializer frmMetadataSerializer) {
        this.resourcePacks = Lists.newArrayList();
        this.frmMetadataSerializer = frmMetadataSerializer;
    }
    
    public void addResourcePack(final IResourcePack resourcePack) {
        this.resourcePacks.add(resourcePack);
    }
    
    @Override
    public Set getResourceDomains() {
        return null;
    }
    
    @Override
    public IResource getResource(final ResourceLocation resourceLocation) throws IOException {
        IResourcePack resourcePack = null;
        final ResourceLocation locationMcmeta = getLocationMcmeta(resourceLocation);
        for (int i = this.resourcePacks.size() - 1; i >= 0; --i) {
            final IResourcePack resourcePack2 = this.resourcePacks.get(i);
            if (resourcePack == null && resourcePack2.resourceExists(locationMcmeta)) {
                resourcePack = resourcePack2;
            }
            if (resourcePack2.resourceExists(resourceLocation)) {
                InputStream func_177245_a = null;
                if (resourcePack != null) {
                    func_177245_a = this.func_177245_a(locationMcmeta, resourcePack);
                }
                return new SimpleResource(resourcePack2.getPackName(), resourceLocation, this.func_177245_a(resourceLocation, resourcePack2), func_177245_a, this.frmMetadataSerializer);
            }
        }
        throw new FileNotFoundException(resourceLocation.toString());
    }
    
    protected InputStream func_177245_a(final ResourceLocation resourceLocation, final IResourcePack resourcePack) throws IOException {
        final InputStream inputStream = resourcePack.getInputStream(resourceLocation);
        return FallbackResourceManager.field_177246_b.isDebugEnabled() ? new ImputStreamLeakedResourceLogger(inputStream, resourceLocation, resourcePack.getPackName()) : inputStream;
    }
    
    @Override
    public List getAllResources(final ResourceLocation resourceLocation) throws IOException {
        final ArrayList arrayList = Lists.newArrayList();
        final ResourceLocation locationMcmeta = getLocationMcmeta(resourceLocation);
        for (final IResourcePack resourcePack : this.resourcePacks) {
            if (resourcePack.resourceExists(resourceLocation)) {
                arrayList.add(new SimpleResource(resourcePack.getPackName(), resourceLocation, this.func_177245_a(resourceLocation, resourcePack), resourcePack.resourceExists(locationMcmeta) ? this.func_177245_a(locationMcmeta, resourcePack) : null, this.frmMetadataSerializer));
            }
        }
        if (arrayList.isEmpty()) {
            throw new FileNotFoundException(resourceLocation.toString());
        }
        return arrayList;
    }
    
    static ResourceLocation getLocationMcmeta(final ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getResourceDomain(), String.valueOf(resourceLocation.getResourcePath()) + ".mcmeta");
    }
    
    static Logger access$0() {
        return FallbackResourceManager.field_177246_b;
    }
    
    static class ImputStreamLeakedResourceLogger extends InputStream
    {
        private final InputStream field_177330_a;
        private final String field_177328_b;
        private boolean field_177329_c;
        private static final String __OBFID;
        
        public ImputStreamLeakedResourceLogger(final InputStream field_177330_a, final ResourceLocation resourceLocation, final String s) {
            this.field_177329_c = false;
            this.field_177330_a = field_177330_a;
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new Exception().printStackTrace(new PrintStream(byteArrayOutputStream));
            this.field_177328_b = "Leaked resource: '" + resourceLocation + "' loaded from pack: '" + s + "'\n" + byteArrayOutputStream.toString();
        }
        
        @Override
        public void close() throws IOException {
            this.field_177330_a.close();
            this.field_177329_c = true;
        }
        
        @Override
        protected void finalize() throws Throwable {
            if (!this.field_177329_c) {
                FallbackResourceManager.access$0().warn(this.field_177328_b);
            }
            super.finalize();
        }
        
        @Override
        public int read() throws IOException {
            return this.field_177330_a.read();
        }
        
        static {
            __OBFID = "CL_00002395";
        }
    }
}
