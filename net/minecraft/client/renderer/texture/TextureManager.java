package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import optifine.*;
import shadersmod.client.*;
import java.util.*;

public class TextureManager implements ITickable, IResourceManagerReloadListener
{
    private static final Logger logger;
    private final Map mapTextureObjects;
    private final List listTickables;
    private final Map mapTextureCounters;
    private IResourceManager theResourceManager;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001064";
        logger = LogManager.getLogger();
    }
    
    public TextureManager(final IResourceManager theResourceManager) {
        this.mapTextureObjects = Maps.newHashMap();
        this.listTickables = Lists.newArrayList();
        this.mapTextureCounters = Maps.newHashMap();
        this.theResourceManager = theResourceManager;
    }
    
    public void bindTexture(ResourceLocation textureLocation) {
        if (Config.isRandomMobs()) {
            textureLocation = RandomMobs.getTextureLocation(textureLocation);
        }
        ITextureObject textureObject = this.mapTextureObjects.get(textureLocation);
        if (textureObject == null) {
            textureObject = new SimpleTexture(textureLocation);
            this.loadTexture(textureLocation, textureObject);
        }
        if (Config.isShaders()) {
            ShadersTex.bindTexture(textureObject);
        }
        else {
            TextureUtil.bindTexture(textureObject.getGlTextureId());
        }
    }
    
    public boolean loadTickableTexture(final ResourceLocation resourceLocation, final ITickableTextureObject tickableTextureObject) {
        if (this.loadTexture(resourceLocation, tickableTextureObject)) {
            this.listTickables.add(tickableTextureObject);
            return true;
        }
        return false;
    }
    
    public boolean loadTexture(final ResourceLocation resourceLocation, final ITextureObject textureObject) {
        textureObject.loadTexture(this.theResourceManager);
        this.mapTextureObjects.put(resourceLocation, textureObject);
        return false;
    }
    
    public ITextureObject getTexture(final ResourceLocation resourceLocation) {
        return this.mapTextureObjects.get(resourceLocation);
    }
    
    public ResourceLocation getDynamicTextureLocation(final String s, DynamicTexture mojangLogoTexture) {
        if (s.equals("logo")) {
            mojangLogoTexture = Config.getMojangLogoTexture(mojangLogoTexture);
        }
        final Integer n = this.mapTextureCounters.get(s);
        Integer n2;
        if (n == null) {
            n2 = 1;
        }
        else {
            n2 = n + 1;
        }
        this.mapTextureCounters.put(s, n2);
        final ResourceLocation resourceLocation = new ResourceLocation(String.format("dynamic/%s_%d", s, n2));
        this.loadTexture(resourceLocation, mojangLogoTexture);
        return resourceLocation;
    }
    
    @Override
    public void tick() {
        final Iterator<ITickable> iterator = this.listTickables.iterator();
        while (iterator.hasNext()) {
            iterator.next().tick();
        }
    }
    
    public void deleteTexture(final ResourceLocation resourceLocation) {
        final ITextureObject texture = this.getTexture(resourceLocation);
        if (texture != null) {
            TextureUtil.deleteTexture(texture.getGlTextureId());
        }
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        Config.dbg("*** Reloading textures ***");
        Config.log("Resource packs: " + Config.getResourcePackNames());
        final Iterator<ResourceLocation> iterator = this.mapTextureObjects.keySet().iterator();
        while (iterator.hasNext()) {
            final ResourceLocation resourceLocation = iterator.next();
            if (resourceLocation.getResourcePath().startsWith("mcpatcher/")) {
                final ITextureObject textureObject = this.mapTextureObjects.get(resourceLocation);
                if (textureObject instanceof AbstractTexture) {
                    ((AbstractTexture)textureObject).deleteGlTexture();
                }
                iterator.remove();
            }
        }
        for (final Map.Entry<ResourceLocation, V> entry : this.mapTextureObjects.entrySet()) {
            this.loadTexture(entry.getKey(), (ITextureObject)entry.getValue());
        }
    }
}
