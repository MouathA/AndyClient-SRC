package net.minecraft.client.resources;

import net.minecraft.client.resources.data.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraft.util.*;
import java.io.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class SimpleReloadableResourceManager implements IReloadableResourceManager
{
    private static final Logger logger;
    private static final Joiner joinerResourcePacks;
    private final Map domainResourceManagers;
    private final List reloadListeners;
    private final Set setResourceDomains;
    private final IMetadataSerializer rmMetadataSerializer;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001091";
        logger = LogManager.getLogger();
        joinerResourcePacks = Joiner.on(", ");
    }
    
    public SimpleReloadableResourceManager(final IMetadataSerializer rmMetadataSerializer) {
        this.domainResourceManagers = Maps.newHashMap();
        this.reloadListeners = Lists.newArrayList();
        this.setResourceDomains = Sets.newLinkedHashSet();
        this.rmMetadataSerializer = rmMetadataSerializer;
    }
    
    public void reloadResourcePack(final IResourcePack resourcePack) {
        for (final String s : resourcePack.getResourceDomains()) {
            this.setResourceDomains.add(s);
            FallbackResourceManager fallbackResourceManager = this.domainResourceManagers.get(s);
            if (fallbackResourceManager == null) {
                fallbackResourceManager = new FallbackResourceManager(this.rmMetadataSerializer);
                this.domainResourceManagers.put(s, fallbackResourceManager);
            }
            fallbackResourceManager.addResourcePack(resourcePack);
        }
    }
    
    @Override
    public Set getResourceDomains() {
        return this.setResourceDomains;
    }
    
    @Override
    public IResource getResource(final ResourceLocation resourceLocation) throws IOException {
        final IResourceManager resourceManager = this.domainResourceManagers.get(resourceLocation.getResourceDomain());
        if (resourceManager != null) {
            return resourceManager.getResource(resourceLocation);
        }
        throw new FileNotFoundException(resourceLocation.toString());
    }
    
    @Override
    public List getAllResources(final ResourceLocation resourceLocation) throws IOException {
        final IResourceManager resourceManager = this.domainResourceManagers.get(resourceLocation.getResourceDomain());
        if (resourceManager != null) {
            return resourceManager.getAllResources(resourceLocation);
        }
        throw new FileNotFoundException(resourceLocation.toString());
    }
    
    private void clearResources() {
        this.domainResourceManagers.clear();
        this.setResourceDomains.clear();
    }
    
    @Override
    public void reloadResources(final List list) {
        this.clearResources();
        SimpleReloadableResourceManager.logger.info("Reloading ResourceManager: " + SimpleReloadableResourceManager.joinerResourcePacks.join(Iterables.transform(list, new Function() {
            private static final String __OBFID;
            final SimpleReloadableResourceManager this$0;
            
            public String apply(final IResourcePack resourcePack) {
                return resourcePack.getPackName();
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((IResourcePack)o);
            }
            
            static {
                __OBFID = "CL_00001092";
            }
        })));
        final Iterator<IResourcePack> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.reloadResourcePack(iterator.next());
        }
        this.notifyReloadListeners();
    }
    
    @Override
    public void registerReloadListener(final IResourceManagerReloadListener resourceManagerReloadListener) {
        this.reloadListeners.add(resourceManagerReloadListener);
        resourceManagerReloadListener.onResourceManagerReload(this);
    }
    
    private void notifyReloadListeners() {
        final Iterator<IResourceManagerReloadListener> iterator = this.reloadListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onResourceManagerReload(this);
        }
    }
}
