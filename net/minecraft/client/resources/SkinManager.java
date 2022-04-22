package net.minecraft.client.resources;

import java.io.*;
import java.util.concurrent.*;
import com.google.common.cache.*;
import com.mojang.authlib.*;
import net.minecraft.client.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.util.*;
import java.awt.image.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import com.google.common.collect.*;
import java.util.*;

public class SkinManager
{
    private static final ExecutorService THREAD_POOL;
    private final TextureManager textureManager;
    private final File skinCacheDir;
    private final MinecraftSessionService sessionService;
    private final LoadingCache skinCacheLoader;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001830";
        THREAD_POOL = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    }
    
    public SkinManager(final TextureManager textureManager, final File skinCacheDir, final MinecraftSessionService sessionService) {
        this.textureManager = textureManager;
        this.skinCacheDir = skinCacheDir;
        this.sessionService = sessionService;
        this.skinCacheLoader = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new CacheLoader() {
            private static final String __OBFID;
            final SkinManager this$0;
            
            public Map func_152786_a(final GameProfile gameProfile) {
                return Minecraft.getMinecraft().getSessionService().getTextures(gameProfile, false);
            }
            
            @Override
            public Object load(final Object o) {
                return this.func_152786_a((GameProfile)o);
            }
            
            static {
                __OBFID = "CL_00001829";
            }
        });
    }
    
    public ResourceLocation loadSkin(final MinecraftProfileTexture minecraftProfileTexture, final MinecraftProfileTexture.Type type) {
        return this.loadSkin(minecraftProfileTexture, type, null);
    }
    
    public ResourceLocation loadSkin(final MinecraftProfileTexture minecraftProfileTexture, final MinecraftProfileTexture.Type type, final SkinAvailableCallback skinAvailableCallback) {
        final ResourceLocation resourceLocation = new ResourceLocation("skins/" + minecraftProfileTexture.getHash());
        if (this.textureManager.getTexture(resourceLocation) != null) {
            if (skinAvailableCallback != null) {
                skinAvailableCallback.func_180521_a(type, resourceLocation, minecraftProfileTexture);
            }
        }
        else {
            this.textureManager.loadTexture(resourceLocation, new ThreadDownloadImageData(new File(new File(this.skinCacheDir, minecraftProfileTexture.getHash().substring(0, 2)), minecraftProfileTexture.getHash()), minecraftProfileTexture.getUrl(), DefaultPlayerSkin.func_177335_a(), new IImageBuffer((type == MinecraftProfileTexture.Type.SKIN) ? new ImageBufferDownload() : null, skinAvailableCallback, type, resourceLocation, minecraftProfileTexture) {
                private static final String __OBFID;
                final SkinManager this$0;
                private final ImageBufferDownload val$var8;
                private final SkinAvailableCallback val$p_152789_3_;
                private final MinecraftProfileTexture.Type val$p_152789_2_;
                private final ResourceLocation val$var4;
                private final MinecraftProfileTexture val$p_152789_1_;
                
                @Override
                public BufferedImage parseUserSkin(BufferedImage userSkin) {
                    if (this.val$var8 != null) {
                        userSkin = this.val$var8.parseUserSkin(userSkin);
                    }
                    return userSkin;
                }
                
                @Override
                public void func_152634_a() {
                    if (this.val$var8 != null) {
                        this.val$var8.func_152634_a();
                    }
                    if (this.val$p_152789_3_ != null) {
                        this.val$p_152789_3_.func_180521_a(this.val$p_152789_2_, this.val$var4, this.val$p_152789_1_);
                    }
                }
                
                static {
                    __OBFID = "CL_00001828";
                }
            }));
        }
        return resourceLocation;
    }
    
    public void func_152790_a(final GameProfile gameProfile, final SkinAvailableCallback skinAvailableCallback, final boolean b) {
        SkinManager.THREAD_POOL.submit(new Runnable(gameProfile, b, skinAvailableCallback) {
            private static final String __OBFID;
            final SkinManager this$0;
            private final GameProfile val$p_152790_1_;
            private final boolean val$p_152790_3_;
            private final SkinAvailableCallback val$p_152790_2_;
            
            @Override
            public void run() {
                final HashMap hashMap = Maps.newHashMap();
                hashMap.putAll(SkinManager.access$0(this.this$0).getTextures(this.val$p_152790_1_, this.val$p_152790_3_));
                if (hashMap.isEmpty()) {
                    final UUID id = this.val$p_152790_1_.getId();
                    Minecraft.getMinecraft();
                    if (id.equals(Minecraft.getSession().getProfile().getId())) {
                        hashMap.putAll(SkinManager.access$0(this.this$0).getTextures(SkinManager.access$0(this.this$0).fillProfileProperties(this.val$p_152790_1_, false), false));
                    }
                }
                Minecraft.getMinecraft().addScheduledTask(new Runnable(hashMap, this.val$p_152790_2_) {
                    private static final String __OBFID;
                    final SkinManager$3 this$1;
                    private final HashMap val$var1;
                    private final SkinAvailableCallback val$p_152790_2_;
                    
                    @Override
                    public void run() {
                        if (this.val$var1.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                            SkinManager$3.access$0(this.this$1).loadSkin(this.val$var1.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN, this.val$p_152790_2_);
                        }
                        if (this.val$var1.containsKey(MinecraftProfileTexture.Type.CAPE)) {
                            SkinManager$3.access$0(this.this$1).loadSkin(this.val$var1.get(MinecraftProfileTexture.Type.CAPE), MinecraftProfileTexture.Type.CAPE, this.val$p_152790_2_);
                        }
                    }
                    
                    static {
                        __OBFID = "CL_00001826";
                    }
                });
            }
            
            static SkinManager access$0(final SkinManager$3 runnable) {
                return runnable.this$0;
            }
            
            static {
                __OBFID = "CL_00001827";
            }
        });
    }
    
    public Map loadSkinFromCache(final GameProfile gameProfile) {
        return (Map)this.skinCacheLoader.getUnchecked(gameProfile);
    }
    
    static MinecraftSessionService access$0(final SkinManager skinManager) {
        return skinManager.sessionService;
    }
    
    public interface SkinAvailableCallback
    {
        void func_180521_a(final MinecraftProfileTexture.Type p0, final ResourceLocation p1, final MinecraftProfileTexture p2);
    }
}
