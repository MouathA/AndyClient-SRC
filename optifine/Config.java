package optifine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.model.*;
import shadersmod.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import org.lwjgl.*;
import org.lwjgl.util.glu.*;
import java.io.*;
import java.util.regex.*;
import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.server.integrated.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.*;
import java.nio.*;
import org.apache.commons.io.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraft.client.*;
import java.lang.reflect.*;
import java.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.texture.*;

public class Config
{
    public static final String OF_NAME;
    public static final String MC_VERSION;
    public static final String OF_EDITION;
    public static final String OF_RELEASE;
    public static final String VERSION;
    private static String newRelease;
    private static boolean notify64BitJava;
    public static String openGlVersion;
    public static String openGlRenderer;
    public static String openGlVendor;
    public static boolean fancyFogAvailable;
    public static boolean occlusionAvailable;
    private static GameSettings gameSettings;
    private static Minecraft minecraft;
    private static boolean initialized;
    private static Thread minecraftThread;
    private static DisplayMode desktopDisplayMode;
    public static boolean zoomMode;
    public static boolean waterOpacityChanged;
    private static boolean fullscreenModeChecked;
    private static boolean desktopModeChecked;
    private static DefaultResourcePack defaultResourcePack;
    private static ModelManager modelManager;
    private static PrintStream systemOut;
    public static final Float DEF_ALPHA_FUNC_LEVEL;
    
    static {
        OF_NAME = "OptiFine";
        OF_RELEASE = "H6";
        OF_EDITION = "HD_U";
        VERSION = "OptiFine_1.8_HD_U_H6";
        MC_VERSION = "1.8";
        Config.newRelease = null;
        Config.notify64BitJava = false;
        Config.openGlVersion = null;
        Config.openGlRenderer = null;
        Config.openGlVendor = null;
        Config.fancyFogAvailable = false;
        Config.occlusionAvailable = false;
        Config.gameSettings = null;
        Config.minecraft = null;
        Config.initialized = false;
        Config.minecraftThread = null;
        Config.desktopDisplayMode = null;
        Config.zoomMode = false;
        Config.waterOpacityChanged = false;
        Config.fullscreenModeChecked = false;
        Config.desktopModeChecked = false;
        Config.defaultResourcePack = null;
        Config.modelManager = null;
        Config.systemOut = new PrintStream(new FileOutputStream(FileDescriptor.out));
        DEF_ALPHA_FUNC_LEVEL = 0.1f;
    }
    
    public static String getVersion() {
        return "OptiFine_1.8_HD_U_H6";
    }
    
    public static String getVersionDebug() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: bipush          32
        //     6: invokespecial   java/lang/StringBuffer.<init>:(I)V
        //     9: astore_0       
        //    10: if_icmpeq       38
        //    13: aload_0        
        //    14: ldc             "DL: "
        //    16: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    19: pop            
        //    20: aload_0        
        //    21: invokestatic    optifine/DynamicLights.getCount:()I
        //    24: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //    27: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    30: pop            
        //    31: aload_0        
        //    32: ldc             ", "
        //    34: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    37: pop            
        //    38: aload_0        
        //    39: ldc             "OptiFine_1.8_HD_U_H6"
        //    41: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    44: pop            
        //    45: invokestatic    shadersmod/client/Shaders.getShaderPackName:()Ljava/lang/String;
        //    48: astore_1       
        //    49: aload_1        
        //    50: ifnull          66
        //    53: aload_0        
        //    54: ldc             ", "
        //    56: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    59: pop            
        //    60: aload_0        
        //    61: aload_1        
        //    62: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    65: pop            
        //    66: aload_0        
        //    67: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //    70: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static void initGameSettings(final GameSettings gameSettings) {
        if (Config.gameSettings == null) {
            Config.gameSettings = gameSettings;
            Config.minecraft = Minecraft.getMinecraft();
            Config.desktopDisplayMode = Display.getDesktopDisplayMode();
            ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
        }
    }
    
    public static void initDisplay() {
        Config.antialiasingLevel = Config.gameSettings.ofAaLevel;
        Config.minecraftThread = Thread.currentThread();
        Shaders.startup(Minecraft.getMinecraft());
    }
    
    public static void checkInitialized() {
        if (!Config.initialized && Display.isCreated()) {
            Config.initialized = true;
        }
    }
    
    private static void checkOpenGlCaps() {
        log("");
        log(getVersion());
        log("Build: " + getBuild());
        log("OS: " + "\u83dc\u83c0\u839d\u83dd\u83d2\u83de\u83d6" + " (" + "\u83dc\u83c0\u839d\u83d2\u83c1\u83d0\u83db" + ") version " + "\u83dc\u83c0\u839d\u83c5\u83d6\u83c1\u83c0\u83da\u83dc\u83dd");
        log("Java: " + "\u83d9\u83d2\u83c5\u83d2\u839d\u83c5\u83d6\u83c1\u83c0\u83da\u83dc\u83dd" + ", " + "\u83d9\u83d2\u83c5\u83d2\u839d\u83c5\u83d6\u83dd\u83d7\u83dc\u83c1");
        log("VM: " + "\u83d9\u83d2\u83c5\u83d2\u839d\u83c5\u83de\u839d\u83dd\u83d2\u83de\u83d6" + " (" + "\u83d9\u83d2\u83c5\u83d2\u839d\u83c5\u83de\u839d\u83da\u83dd\u83d5\u83dc" + "), " + "\u83d9\u83d2\u83c5\u83d2\u839d\u83c5\u83de\u839d\u83c5\u83d6\u83dd\u83d7\u83dc\u83c1");
        log("LWJGL: " + Sys.getVersion());
        Config.openGlVersion = GL11.glGetString(7938);
        Config.openGlRenderer = GL11.glGetString(7937);
        Config.openGlVendor = GL11.glGetString(7936);
        log("OpenGL: " + Config.openGlRenderer + ", version " + Config.openGlVersion + ", " + Config.openGlVendor);
        log("OpenGL Version: " + getOpenGlVersionString());
        if (!GLContext.getCapabilities().OpenGL12) {
            log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
        }
        if (!(Config.fancyFogAvailable = GLContext.getCapabilities().GL_NV_fog_distance)) {
            log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }
        if (!(Config.occlusionAvailable = GLContext.getCapabilities().GL_ARB_occlusion_query)) {
            log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }
        final int glMaximumTextureSize = Minecraft.getGLMaximumTextureSize();
        dbg("Maximum texture size: " + glMaximumTextureSize + "x" + glMaximumTextureSize);
    }
    
    private static String getBuild() {
        final InputStream resourceAsStream = Config.class.getResourceAsStream("/buildof.txt");
        if (resourceAsStream == null) {
            return null;
        }
        return readLines(resourceAsStream)[0];
    }
    
    public static boolean isFancyFogAvailable() {
        return Config.fancyFogAvailable;
    }
    
    public static boolean isOcclusionAvailable() {
        return Config.occlusionAvailable;
    }
    
    public static String getOpenGlVersionString() {
        final int openGlVersion = getOpenGlVersion();
        return openGlVersion / 10 + "." + openGlVersion % 10;
    }
    
    private static int getOpenGlVersion() {
        return GLContext.getCapabilities().OpenGL11 ? (GLContext.getCapabilities().OpenGL12 ? (GLContext.getCapabilities().OpenGL13 ? (GLContext.getCapabilities().OpenGL14 ? (GLContext.getCapabilities().OpenGL15 ? (GLContext.getCapabilities().OpenGL20 ? (GLContext.getCapabilities().OpenGL21 ? (GLContext.getCapabilities().OpenGL30 ? (GLContext.getCapabilities().OpenGL31 ? (GLContext.getCapabilities().OpenGL32 ? (GLContext.getCapabilities().OpenGL33 ? (GLContext.getCapabilities().OpenGL40 ? 40 : 33) : 32) : 31) : 30) : 21) : 20) : 15) : 14) : 13) : 12) : 11) : 10;
    }
    
    public static void updateThreadPriorities() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: invokestatic    optifine/Config.isSmoothWorld:()Z
        //     6: ifeq            27
        //     9: getstatic       optifine/Config.minecraftThread:Ljava/lang/Thread;
        //    12: bipush          10
        //    14: invokevirtual   java/lang/Thread.setPriority:(I)V
        //    17: ldc_w           "Server thread"
        //    20: iconst_1       
        //    21: invokestatic    optifine/Config.setThreadPriority:(Ljava/lang/String;I)V
        //    24: goto            59
        //    27: getstatic       optifine/Config.minecraftThread:Ljava/lang/Thread;
        //    30: iconst_5       
        //    31: invokevirtual   java/lang/Thread.setPriority:(I)V
        //    34: ldc_w           "Server thread"
        //    37: iconst_5       
        //    38: invokestatic    optifine/Config.setThreadPriority:(Ljava/lang/String;I)V
        //    41: goto            59
        //    44: getstatic       optifine/Config.minecraftThread:Ljava/lang/Thread;
        //    47: bipush          10
        //    49: invokevirtual   java/lang/Thread.setPriority:(I)V
        //    52: ldc_w           "Server thread"
        //    55: iconst_5       
        //    56: invokestatic    optifine/Config.setThreadPriority:(Ljava/lang/String;I)V
        //    59: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static void setThreadPriority(final String s, final int priority) {
        final ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        if (threadGroup == null) {
            return;
        }
        final Thread[] array = new Thread[(threadGroup.activeCount() + 10) * 2];
        threadGroup.enumerate(array, false);
        while (0 < array.length) {
            final Thread thread = array[0];
            if (thread != null && thread.getName().startsWith(s)) {
                thread.setPriority(priority);
            }
            int n = 0;
            ++n;
        }
    }
    
    public static boolean isMinecraftThread() {
        return Thread.currentThread() == Config.minecraftThread;
    }
    
    private static void startVersionCheckThread() {
        new VersionCheckThread().start();
    }
    
    public static boolean isMipmaps() {
        return Config.gameSettings.mipmapLevels > 0;
    }
    
    public static int getMipmapLevels() {
        return Config.gameSettings.mipmapLevels;
    }
    
    public static int getMipmapType() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: getfield        net/minecraft/client/settings/GameSettings.ofMipmapType:I
        //     6: tableswitch {
        //                0: 36
        //                1: 40
        //                2: 44
        //                3: 55
        //          default: 66
        //        }
        //    36: sipush          9986
        //    39: ireturn        
        //    40: sipush          9986
        //    43: ireturn        
        //    44: if_icmple       51
        //    47: sipush          9985
        //    50: ireturn        
        //    51: sipush          9986
        //    54: ireturn        
        //    55: if_icmple       62
        //    58: sipush          9987
        //    61: ireturn        
        //    62: sipush          9986
        //    65: ireturn        
        //    66: sipush          9986
        //    69: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static boolean isUseAlphaFunc() {
        return getAlphaFuncLevel() > Config.DEF_ALPHA_FUNC_LEVEL + 1.0E-5f;
    }
    
    public static float getAlphaFuncLevel() {
        return Config.DEF_ALPHA_FUNC_LEVEL;
    }
    
    public static boolean isFogFancy() {
        return isFancyFogAvailable() && Config.gameSettings.ofFogType == 2;
    }
    
    public static boolean isFogFast() {
        return Config.gameSettings.ofFogType == 1;
    }
    
    public static boolean isFogOff() {
        return Config.gameSettings.ofFogType == 3;
    }
    
    public static float getFogStart() {
        return Config.gameSettings.ofFogStart;
    }
    
    public static void dbg(final String s) {
        Config.systemOut.print("[OptiFine] ");
        Config.systemOut.println(s);
    }
    
    public static void warn(final String s) {
        Config.systemOut.print("[OptiFine] [WARN] ");
        Config.systemOut.println(s);
    }
    
    public static void error(final String s) {
        Config.systemOut.print("[OptiFine] [ERROR] ");
        Config.systemOut.println(s);
    }
    
    public static void log(final String s) {
        dbg(s);
    }
    
    public static int getUpdatesPerFrame() {
        return Config.gameSettings.ofChunkUpdates;
    }
    
    public static boolean isDynamicUpdates() {
        return Config.gameSettings.ofChunkUpdatesDynamic;
    }
    
    public static boolean isRainFancy() {
        return (Config.gameSettings.ofRain == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofRain == 2);
    }
    
    public static boolean isRainOff() {
        return Config.gameSettings.ofRain == 3;
    }
    
    public static boolean isCloudsFancy() {
        return (Config.gameSettings.ofClouds != 0) ? (Config.gameSettings.ofClouds == 2) : ((isShaders() && !Shaders.shaderPackClouds.isDefault()) ? Shaders.shaderPackClouds.isFancy() : Config.gameSettings.fancyGraphics);
    }
    
    public static boolean isCloudsOff() {
        return (Config.gameSettings.ofClouds != 0) ? (Config.gameSettings.ofClouds == 3) : (isShaders() && !Shaders.shaderPackClouds.isDefault() && Shaders.shaderPackClouds.isOff());
    }
    
    public static void updateTexturePackClouds() {
        Config.texturePackClouds = 0;
        final IResourceManager resourceManager = getResourceManager();
        if (resourceManager != null) {
            final InputStream inputStream = resourceManager.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
            if (inputStream == null) {
                return;
            }
            final Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
            final String property = properties.getProperty("clouds");
            if (property == null) {
                return;
            }
            dbg("Texture pack clouds: " + property);
            final String lowerCase = property.toLowerCase();
            if (lowerCase.equals("fast")) {
                Config.texturePackClouds = 1;
            }
            if (lowerCase.equals("fancy")) {
                Config.texturePackClouds = 2;
            }
            if (lowerCase.equals("off")) {
                Config.texturePackClouds = 3;
            }
        }
    }
    
    public static void setModelManager(ModelManager modelManager) {
        modelManager = modelManager;
    }
    
    public static ModelManager getModelManager() {
        return Config.modelManager;
    }
    
    public static boolean isTreesFancy() {
        return (Config.gameSettings.ofTrees == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofTrees != 1);
    }
    
    public static boolean isTreesSmart() {
        return Config.gameSettings.ofTrees == 4;
    }
    
    public static boolean isCullFacesLeaves() {
        return (Config.gameSettings.ofTrees == 0) ? (!Config.gameSettings.fancyGraphics) : (Config.gameSettings.ofTrees == 4);
    }
    
    public static boolean isDroppedItemsFancy() {
        return (Config.gameSettings.ofDroppedItems == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofDroppedItems == 2);
    }
    
    public static int limit(final int n, final int n2, final int n3) {
        return (n < n2) ? n2 : ((n > n3) ? n3 : n);
    }
    
    public static float limit(final float n, final float n2, final float n3) {
        return (n < n2) ? n2 : ((n > n3) ? n3 : n);
    }
    
    public static double limit(final double n, final double n2, final double n3) {
        return (n < n2) ? n2 : ((n > n3) ? n3 : n);
    }
    
    public static float limitTo1(final float n) {
        return (n < 0.0f) ? 0.0f : ((n > 1.0f) ? 1.0f : n);
    }
    
    public static boolean isAnimatedWater() {
        return Config.gameSettings.ofAnimatedWater != 2;
    }
    
    public static boolean isGeneratedWater() {
        return Config.gameSettings.ofAnimatedWater == 1;
    }
    
    public static boolean isAnimatedPortal() {
        return Config.gameSettings.ofAnimatedPortal;
    }
    
    public static boolean isAnimatedLava() {
        return Config.gameSettings.ofAnimatedLava != 2;
    }
    
    public static boolean isGeneratedLava() {
        return Config.gameSettings.ofAnimatedLava == 1;
    }
    
    public static boolean isAnimatedFire() {
        return Config.gameSettings.ofAnimatedFire;
    }
    
    public static boolean isAnimatedRedstone() {
        return Config.gameSettings.ofAnimatedRedstone;
    }
    
    public static boolean isAnimatedExplosion() {
        return Config.gameSettings.ofAnimatedExplosion;
    }
    
    public static boolean isAnimatedFlame() {
        return Config.gameSettings.ofAnimatedFlame;
    }
    
    public static boolean isAnimatedSmoke() {
        return Config.gameSettings.ofAnimatedSmoke;
    }
    
    public static boolean isVoidParticles() {
        return Config.gameSettings.ofVoidParticles;
    }
    
    public static boolean isWaterParticles() {
        return Config.gameSettings.ofWaterParticles;
    }
    
    public static boolean isRainSplash() {
        return Config.gameSettings.ofRainSplash;
    }
    
    public static boolean isPortalParticles() {
        return Config.gameSettings.ofPortalParticles;
    }
    
    public static boolean isPotionParticles() {
        return Config.gameSettings.ofPotionParticles;
    }
    
    public static boolean isFireworkParticles() {
        return Config.gameSettings.ofFireworkParticles;
    }
    
    public static float getAmbientOcclusionLevel() {
        return Config.gameSettings.ofAoLevel;
    }
    
    private static Method getMethod(final Class clazz, final String s, final Object[] array) {
        final Method[] methods = clazz.getMethods();
        while (0 < methods.length) {
            final Method method = methods[0];
            if (method.getName().equals(s) && method.getParameterTypes().length == array.length) {
                return method;
            }
            int n = 0;
            ++n;
        }
        warn("No method found for: " + clazz.getName() + "." + s + "(" + arrayToString(array) + ")");
        return null;
    }
    
    public static String arrayToString(final Object[] array) {
        if (array == null) {
            return "";
        }
        final StringBuffer sb = new StringBuffer(array.length * 5);
        while (0 < array.length) {
            sb.append(String.valueOf(array[0]));
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static String arrayToString(final int[] array) {
        if (array == null) {
            return "";
        }
        final StringBuffer sb = new StringBuffer(array.length * 5);
        while (0 < array.length) {
            sb.append(String.valueOf(array[0]));
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static Minecraft getMinecraft() {
        return Config.minecraft;
    }
    
    public static TextureManager getTextureManager() {
        return Config.minecraft.getTextureManager();
    }
    
    public static IResourceManager getResourceManager() {
        return Config.minecraft.getResourceManager();
    }
    
    public static InputStream getResourceStream(final ResourceLocation resourceLocation) throws IOException {
        return getResourceStream(Config.minecraft.getResourceManager(), resourceLocation);
    }
    
    public static InputStream getResourceStream(final IResourceManager resourceManager, final ResourceLocation resourceLocation) throws IOException {
        final IResource resource = resourceManager.getResource(resourceLocation);
        return (resource == null) ? null : resource.getInputStream();
    }
    
    public static IResource getResource(final ResourceLocation resourceLocation) throws IOException {
        return Config.minecraft.getResourceManager().getResource(resourceLocation);
    }
    
    public static boolean hasResource(final ResourceLocation resourceLocation) {
        return getResource(resourceLocation) != null;
    }
    
    public static boolean hasResource(final IResourceManager resourceManager, final ResourceLocation resourceLocation) {
        return resourceManager.getResource(resourceLocation) != null;
    }
    
    public static IResourcePack[] getResourcePacks() {
        final ResourcePackRepository resourcePackRepository = Config.minecraft.getResourcePackRepository();
        final List repositoryEntries = resourcePackRepository.getRepositoryEntries();
        final ArrayList<IResourcePack> list = new ArrayList<IResourcePack>();
        final Iterator<ResourcePackRepository.Entry> iterator = repositoryEntries.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getResourcePack());
        }
        if (resourcePackRepository.getResourcePackInstance() != null) {
            list.add(resourcePackRepository.getResourcePackInstance());
        }
        return list.toArray(new IResourcePack[list.size()]);
    }
    
    public static String getResourcePackNames() {
        if (Config.minecraft == null) {
            return "";
        }
        if (Config.minecraft.getResourcePackRepository() == null) {
            return "";
        }
        final IResourcePack[] resourcePacks = getResourcePacks();
        if (resourcePacks.length <= 0) {
            return getDefaultResourcePack().getPackName();
        }
        final String[] array = new String[resourcePacks.length];
        while (0 < resourcePacks.length) {
            array[0] = resourcePacks[0].getPackName();
            int n = 0;
            ++n;
        }
        return arrayToString(array);
    }
    
    public static DefaultResourcePack getDefaultResourcePack() {
        if (Config.defaultResourcePack == null) {
            final Minecraft minecraft = Minecraft.getMinecraft();
            final Field[] declaredFields = minecraft.getClass().getDeclaredFields();
            while (0 < declaredFields.length) {
                final Field field = declaredFields[0];
                if (field.getType() == DefaultResourcePack.class) {
                    field.setAccessible(true);
                    Config.defaultResourcePack = (DefaultResourcePack)field.get(minecraft);
                    break;
                }
                int n = 0;
                ++n;
            }
            if (Config.defaultResourcePack == null) {
                final ResourcePackRepository resourcePackRepository = minecraft.getResourcePackRepository();
                if (resourcePackRepository != null) {
                    Config.defaultResourcePack = (DefaultResourcePack)resourcePackRepository.rprDefaultResourcePack;
                }
            }
        }
        return Config.defaultResourcePack;
    }
    
    public static boolean isFromDefaultResourcePack(final ResourceLocation resourceLocation) {
        return getDefiningResourcePack(resourceLocation) == getDefaultResourcePack();
    }
    
    public static IResourcePack getDefiningResourcePack(final ResourceLocation resourceLocation) {
        final IResourcePack[] resourcePacks = getResourcePacks();
        for (int i = resourcePacks.length - 1; i >= 0; --i) {
            final IResourcePack resourcePack = resourcePacks[i];
            if (resourcePack.resourceExists(resourceLocation)) {
                return resourcePack;
            }
        }
        if (getDefaultResourcePack().resourceExists(resourceLocation)) {
            return getDefaultResourcePack();
        }
        return null;
    }
    
    public static RenderGlobal getRenderGlobal() {
        return (Config.minecraft == null) ? null : Config.minecraft.renderGlobal;
    }
    
    public static boolean isBetterGrass() {
        return Config.gameSettings.ofBetterGrass != 3;
    }
    
    public static boolean isBetterGrassFancy() {
        return Config.gameSettings.ofBetterGrass == 2;
    }
    
    public static boolean isWeatherEnabled() {
        return Config.gameSettings.ofWeather;
    }
    
    public static boolean isSkyEnabled() {
        return Config.gameSettings.ofSky;
    }
    
    public static boolean isSunMoonEnabled() {
        return Config.gameSettings.ofSunMoon;
    }
    
    public static boolean isVignetteEnabled() {
        return (Config.gameSettings.ofVignette == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofVignette == 2);
    }
    
    public static boolean isStarsEnabled() {
        return Config.gameSettings.ofStars;
    }
    
    public static void sleep(final long n) {
        Thread.currentThread();
        Thread.sleep(n);
    }
    
    public static boolean isTimeDayOnly() {
        return Config.gameSettings.ofTime == 1;
    }
    
    public static boolean isTimeDefault() {
        return Config.gameSettings.ofTime == 0;
    }
    
    public static boolean isTimeNightOnly() {
        return Config.gameSettings.ofTime == 2;
    }
    
    public static boolean isClearWater() {
        return Config.gameSettings.ofClearWater;
    }
    
    public static int getAnisotropicFilterLevel() {
        return Config.gameSettings.ofAfLevel;
    }
    
    public static boolean isAnisotropicFiltering() {
        return getAnisotropicFilterLevel() > 1;
    }
    
    public static int getAntialiasingLevel() {
        return 0;
    }
    
    public static boolean isAntialiasing() {
        return getAntialiasingLevel() > 0;
    }
    
    public static boolean isAntialiasingConfigured() {
        return getGameSettings().ofAaLevel > 0;
    }
    
    public static boolean between(final int n, final int n2, final int n3) {
        return n >= n2 && n <= n3;
    }
    
    public static boolean isDrippingWaterLava() {
        return Config.gameSettings.ofDrippingWaterLava;
    }
    
    public static boolean isBetterSnow() {
        return Config.gameSettings.ofBetterSnow;
    }
    
    public static Dimension getFullscreenDimension() {
        if (Config.desktopDisplayMode == null) {
            return null;
        }
        if (Config.gameSettings == null) {
            return new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight());
        }
        final String ofFullscreenMode = Config.gameSettings.ofFullscreenMode;
        if (ofFullscreenMode.equals("Default")) {
            return new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight());
        }
        final String[] tokenize = tokenize(ofFullscreenMode, " x");
        return (tokenize.length < 2) ? new Dimension(Config.desktopDisplayMode.getWidth(), Config.desktopDisplayMode.getHeight()) : new Dimension(parseInt(tokenize[0], -1), parseInt(tokenize[1], -1));
    }
    
    public static int parseInt(String trim, final int n) {
        if (trim == null) {
            return n;
        }
        trim = trim.trim();
        return Integer.parseInt(trim);
    }
    
    public static float parseFloat(String trim, final float n) {
        if (trim == null) {
            return n;
        }
        trim = trim.trim();
        return Float.parseFloat(trim);
    }
    
    public static boolean parseBoolean(String trim, final boolean b) {
        if (trim == null) {
            return b;
        }
        trim = trim.trim();
        return Boolean.parseBoolean(trim);
    }
    
    public static String[] tokenize(final String s, final String s2) {
        final StringTokenizer stringTokenizer = new StringTokenizer(s, s2);
        final ArrayList<String> list = new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static DisplayMode getDesktopDisplayMode() {
        return Config.desktopDisplayMode;
    }
    
    public static DisplayMode[] getFullscreenDisplayModes() {
        final DisplayMode[] availableDisplayModes = Display.getAvailableDisplayModes();
        final ArrayList<DisplayMode> list = new ArrayList<DisplayMode>();
        while (0 < availableDisplayModes.length) {
            final DisplayMode displayMode = availableDisplayModes[0];
            if (Config.desktopDisplayMode == null || (displayMode.getBitsPerPixel() == Config.desktopDisplayMode.getBitsPerPixel() && displayMode.getFrequency() == Config.desktopDisplayMode.getFrequency())) {
                list.add(displayMode);
            }
            int n = 0;
            ++n;
        }
        final DisplayMode[] array = list.toArray(new DisplayMode[list.size()]);
        Arrays.sort(array, new Comparator() {
            @Override
            public int compare(final Object o, final Object o2) {
                final DisplayMode displayMode = (DisplayMode)o;
                final DisplayMode displayMode2 = (DisplayMode)o2;
                return (displayMode.getWidth() != displayMode2.getWidth()) ? (displayMode2.getWidth() - displayMode.getWidth()) : ((displayMode.getHeight() != displayMode2.getHeight()) ? (displayMode2.getHeight() - displayMode.getHeight()) : 0);
            }
        });
        return array;
    }
    
    public static String[] getFullscreenModes() {
        final DisplayMode[] fullscreenDisplayModes = getFullscreenDisplayModes();
        final String[] array = new String[fullscreenDisplayModes.length];
        while (0 < fullscreenDisplayModes.length) {
            final DisplayMode displayMode = fullscreenDisplayModes[0];
            array[0] = displayMode.getWidth() + "x" + displayMode.getHeight();
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public static DisplayMode getDisplayMode(final Dimension dimension) throws LWJGLException {
        final DisplayMode[] availableDisplayModes = Display.getAvailableDisplayModes();
        while (0 < availableDisplayModes.length) {
            final DisplayMode displayMode = availableDisplayModes[0];
            if (displayMode.getWidth() == dimension.width && displayMode.getHeight() == dimension.height && (Config.desktopDisplayMode == null || (displayMode.getBitsPerPixel() == Config.desktopDisplayMode.getBitsPerPixel() && displayMode.getFrequency() == Config.desktopDisplayMode.getFrequency()))) {
                return displayMode;
            }
            int n = 0;
            ++n;
        }
        return Config.desktopDisplayMode;
    }
    
    public static boolean isAnimatedTerrain() {
        return Config.gameSettings.ofAnimatedTerrain;
    }
    
    public static boolean isAnimatedTextures() {
        return Config.gameSettings.ofAnimatedTextures;
    }
    
    public static boolean isSwampColors() {
        return Config.gameSettings.ofSwampColors;
    }
    
    public static boolean isRandomMobs() {
        return Config.gameSettings.ofRandomMobs;
    }
    
    public static void checkGlError(final String s) {
        final int glGetError = GL11.glGetError();
        if (glGetError != 0) {
            error("OpenGlError: " + glGetError + " (" + GLU.gluErrorString(glGetError) + "), at: " + s);
        }
    }
    
    public static boolean isSmoothBiomes() {
        return Config.gameSettings.ofSmoothBiomes;
    }
    
    public static boolean isCustomColors() {
        return Config.gameSettings.ofCustomColors;
    }
    
    public static boolean isCustomSky() {
        return Config.gameSettings.ofCustomSky;
    }
    
    public static boolean isCustomFonts() {
        return Config.gameSettings.ofCustomFonts;
    }
    
    public static boolean isShowCapes() {
        return Config.gameSettings.ofShowCapes;
    }
    
    public static boolean isConnectedTextures() {
        return Config.gameSettings.ofConnectedTextures != 3;
    }
    
    public static boolean isNaturalTextures() {
        return Config.gameSettings.ofNaturalTextures;
    }
    
    public static boolean isConnectedTexturesFancy() {
        return Config.gameSettings.ofConnectedTextures == 2;
    }
    
    public static boolean isFastRender() {
        return Config.gameSettings.ofFastRender;
    }
    
    public static boolean isTranslucentBlocksFancy() {
        return (Config.gameSettings.ofTranslucentBlocks == 0) ? Config.gameSettings.fancyGraphics : (Config.gameSettings.ofTranslucentBlocks == 2);
    }
    
    public static boolean isShaders() {
        return Shaders.shaderPackLoaded;
    }
    
    public static String[] readLines(final File file) throws IOException {
        return readLines(new FileInputStream(file));
    }
    
    public static String[] readLines(final InputStream inputStream) throws IOException {
        final ArrayList<String> list = new ArrayList<String>();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ASCII"));
        while (true) {
            final String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            list.add(line);
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static String readFile(final File file) throws IOException {
        return readInputStream(new FileInputStream(file), "ASCII");
    }
    
    public static String readInputStream(final InputStream inputStream) throws IOException {
        return readInputStream(inputStream, "ASCII");
    }
    
    public static String readInputStream(final InputStream inputStream, final String s) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, s));
        final StringBuffer sb = new StringBuffer();
        while (true) {
            final String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public static byte[] readAll(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final byte[] array = new byte[1024];
        while (true) {
            final int read = inputStream.read(array);
            if (read < 0) {
                break;
            }
            byteArrayOutputStream.write(array, 0, read);
        }
        inputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    
    public static GameSettings getGameSettings() {
        return Config.gameSettings;
    }
    
    public static String getNewRelease() {
        return Config.newRelease;
    }
    
    public static void setNewRelease(String s) {
        s = s;
    }
    
    public static int compareRelease(final String s, final String s2) {
        final String[] splitRelease = splitRelease(s);
        final String[] splitRelease2 = splitRelease(s2);
        final String s3 = splitRelease[0];
        final String s4 = splitRelease2[0];
        if (!s3.equals(s4)) {
            return s3.compareTo(s4);
        }
        final int int1 = parseInt(splitRelease[1], -1);
        final int int2 = parseInt(splitRelease2[1], -1);
        if (int1 != int2) {
            return int1 - int2;
        }
        final String s5 = splitRelease[2];
        final String s6 = splitRelease2[2];
        if (!s5.equals(s6)) {
            if (s5.isEmpty()) {
                return 1;
            }
            if (s6.isEmpty()) {
                return -1;
            }
        }
        return s5.compareTo(s6);
    }
    
    private static String[] splitRelease(final String s) {
        if (s == null || s.length() <= 0) {
            return new String[] { "", "", "" };
        }
        final Matcher matcher = Pattern.compile("([A-Z])([0-9]+)(.*)").matcher(s);
        if (!matcher.matches()) {
            return new String[] { "", "", "" };
        }
        return new String[] { normalize(matcher.group(1)), normalize(matcher.group(2)), normalize(matcher.group(3)) };
    }
    
    public static int intHash(int n) {
        n = (n ^ 0x3D ^ n >> 16);
        n += n << 3;
        n ^= n >> 4;
        n *= 668265261;
        n ^= n >> 15;
        return n;
    }
    
    public static int getRandom(final BlockPos blockPos, final int n) {
        return intHash(intHash(intHash(intHash(n + 37) + blockPos.getX()) + blockPos.getZ()) + blockPos.getY());
    }
    
    public static WorldServer getWorldServer() {
        if (Config.minecraft == null) {
            return null;
        }
        final WorldClient theWorld = Minecraft.theWorld;
        if (theWorld == null) {
            return null;
        }
        if (!Config.minecraft.isIntegratedServerRunning()) {
            return null;
        }
        final IntegratedServer integratedServer = Config.minecraft.getIntegratedServer();
        if (integratedServer == null) {
            return null;
        }
        final WorldProvider provider = theWorld.provider;
        if (provider == null) {
            return null;
        }
        return integratedServer.worldServerForDimension(provider.getDimensionId());
    }
    
    public static int getAvailableProcessors() {
        return 0;
    }
    
    public static void updateAvailableProcessors() {
        Config.availableProcessors = Runtime.getRuntime().availableProcessors();
    }
    
    public static boolean isSmoothWorld() {
        return Config.gameSettings.ofSmoothWorld;
    }
    
    public static boolean isLazyChunkLoading() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: iconst_0       
        //     4: goto            13
        //     7: getstatic       optifine/Config.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    10: getfield        net/minecraft/client/settings/GameSettings.ofLazyChunkLoading:Z
        //    13: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static boolean isDynamicFov() {
        return Config.gameSettings.ofDynamicFov;
    }
    
    public static int getChunkViewDistance() {
        if (Config.gameSettings == null) {
            return 10;
        }
        return GameSettings.renderDistanceChunks;
    }
    
    public static boolean equals(final Object o, final Object o2) {
        return o == o2 || (o != null && o.equals(o2));
    }
    
    public static String normalize(final String s) {
        return (s == null) ? "" : s;
    }
    
    public static void checkDisplaySettings() {
        final int antialiasingLevel = getAntialiasingLevel();
        if (antialiasingLevel > 0) {
            final DisplayMode displayMode = Display.getDisplayMode();
            dbg("FSAA Samples: " + antialiasingLevel);
            Display.setDisplayMode(displayMode);
            Display.create(new PixelFormat().withDepthBits(24).withSamples(antialiasingLevel));
            Display.setResizable(false);
            Display.setResizable(true);
            if (!Minecraft.isRunningOnMac && getDefaultResourcePack() != null) {
                final InputStream func_152780_c = getDefaultResourcePack().func_152780_c(new ResourceLocation("icons/icon_16x16.png"));
                final InputStream func_152780_c2 = getDefaultResourcePack().func_152780_c(new ResourceLocation("icons/icon_32x32.png"));
                if (func_152780_c != null && func_152780_c2 != null) {
                    Display.setIcon(new ByteBuffer[] { readIconImage(func_152780_c), readIconImage(func_152780_c2) });
                }
                IOUtils.closeQuietly(func_152780_c);
                IOUtils.closeQuietly(func_152780_c2);
            }
        }
    }
    
    private static ByteBuffer readIconImage(final InputStream inputStream) throws IOException {
        final BufferedImage read = ImageIO.read(inputStream);
        final int[] rgb = read.getRGB(0, 0, read.getWidth(), read.getHeight(), null, 0, read.getWidth());
        final ByteBuffer allocate = ByteBuffer.allocate(4 * rgb.length);
        final int[] array = rgb;
        while (0 < rgb.length) {
            final int n = array[0];
            allocate.putInt(n << 8 | (n >> 24 & 0xFF));
            int n2 = 0;
            ++n2;
        }
        allocate.flip();
        return allocate;
    }
    
    public static void checkDisplayMode() {
        if (Config.minecraft.isFullScreen()) {
            if (Config.fullscreenModeChecked) {
                return;
            }
            Config.fullscreenModeChecked = true;
            Config.desktopModeChecked = false;
            final DisplayMode displayMode = Display.getDisplayMode();
            final Dimension fullscreenDimension = getFullscreenDimension();
            if (fullscreenDimension == null) {
                return;
            }
            if (displayMode.getWidth() == fullscreenDimension.width && displayMode.getHeight() == fullscreenDimension.height) {
                return;
            }
            final DisplayMode displayMode2 = getDisplayMode(fullscreenDimension);
            if (displayMode2 == null) {
                return;
            }
            Display.setDisplayMode(displayMode2);
            Config.minecraft.displayWidth = Display.getDisplayMode().getWidth();
            Config.minecraft.displayHeight = Display.getDisplayMode().getHeight();
            if (Config.minecraft.displayWidth <= 0) {
                Config.minecraft.displayWidth = 1;
            }
            if (Config.minecraft.displayHeight <= 0) {
                Config.minecraft.displayHeight = 1;
            }
            if (Config.minecraft.currentScreen != null) {
                final ScaledResolution scaledResolution = new ScaledResolution(Config.minecraft, Config.minecraft.displayWidth, Config.minecraft.displayHeight);
                Config.minecraft.currentScreen.setWorldAndResolution(Config.minecraft, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
            }
            Config.minecraft.loadingScreen = new LoadingScreenRenderer(Config.minecraft);
            Display.setFullscreen(true);
            Config.minecraft.gameSettings.updateVSync();
        }
        else {
            if (Config.desktopModeChecked) {
                return;
            }
            Config.desktopModeChecked = true;
            Config.fullscreenModeChecked = false;
            Config.minecraft.gameSettings.updateVSync();
            Display.setResizable(false);
            Display.setResizable(true);
        }
    }
    
    public static void updateFramebufferSize() {
        Config.minecraft.getFramebuffer().createBindFramebuffer(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        if (Config.minecraft.entityRenderer != null) {
            Config.minecraft.entityRenderer.updateShaderGroupSize(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        }
    }
    
    public static Object[] addObjectToArray(final Object[] array, final Object o) {
        if (array == null) {
            throw new NullPointerException("The given array is NULL");
        }
        final int length = array.length;
        final Object[] array2 = (Object[])Array.newInstance(array.getClass().getComponentType(), length + 1);
        System.arraycopy(array, 0, array2, 0, length);
        array2[length] = o;
        return array2;
    }
    
    public static Object[] addObjectToArray(final Object[] array, final Object o, final int n) {
        final ArrayList<Object> list = new ArrayList<Object>(Arrays.asList(array));
        list.add(n, o);
        return list.toArray((Object[])Array.newInstance(array.getClass().getComponentType(), list.size()));
    }
    
    public static Object[] addObjectsToArray(final Object[] array, final Object[] array2) {
        if (array == null) {
            throw new NullPointerException("The given array is NULL");
        }
        if (array2.length == 0) {
            return array;
        }
        final int length = array.length;
        final Object[] array3 = (Object[])Array.newInstance(array.getClass().getComponentType(), length + array2.length);
        System.arraycopy(array, 0, array3, 0, length);
        System.arraycopy(array2, 0, array3, length, array2.length);
        return array3;
    }
    
    public static boolean isCustomItems() {
        return Config.gameSettings.ofCustomItems;
    }
    
    public static void drawFps() {
        final Minecraft minecraft = Config.minecraft;
        Minecraft.fontRendererObj.drawString(Minecraft.func_175610_ah() + " fps, C: " + Config.minecraft.renderGlobal.getCountActiveRenderers() + ", E: " + Config.minecraft.renderGlobal.getCountEntitiesRendered() + "+" + Config.minecraft.renderGlobal.getCountTileEntitiesRendered() + ", U: " + getUpdates(Config.minecraft.debug), 2, 2, -2039584);
    }
    
    private static String getUpdates(final String s) {
        final int index = s.indexOf(40);
        if (index < 0) {
            return "";
        }
        final int index2 = s.indexOf(32, index);
        return (index2 < 0) ? "" : s.substring(index + 1, index2);
    }
    
    public static int getBitsOs() {
        return ("\u83e3\u83c1\u83dc\u83d4\u83c1\u83d2\u83de\u83f5\u83da\u83df\u83d6\u83c0\u839b\u83eb\u838b\u8385\u839a" != null) ? 64 : 32;
    }
    
    public static int getBitsJre() {
        final String[] array = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
        while (0 < array.length) {
            final String property = System.getProperty(array[0]);
            if (property != null && property.contains("64")) {
                return 64;
            }
            int n = 0;
            ++n;
        }
        return 32;
    }
    
    public static boolean isNotify64BitJava() {
        return Config.notify64BitJava;
    }
    
    public static void setNotify64BitJava(final boolean notify64BitJava) {
        Config.notify64BitJava = notify64BitJava;
    }
    
    public static boolean isConnectedModels() {
        return false;
    }
    
    public static String fillLeft(String s, final int n, final char c) {
        if (s == null) {
            s = "";
        }
        if (s.length() >= n) {
            return s;
        }
        final StringBuffer sb = new StringBuffer(s);
        while (sb.length() < n - s.length()) {
            sb.append(c);
        }
        return String.valueOf(sb.toString()) + s;
    }
    
    public static String fillRight(String s, final int n, final char c) {
        if (s == null) {
            s = "";
        }
        if (s.length() >= n) {
            return s;
        }
        final StringBuffer sb = new StringBuffer(s);
        while (sb.length() < n) {
            sb.append(c);
        }
        return sb.toString();
    }
    
    public static void showGuiMessage(final String s, final String s2) {
        Config.minecraft.displayGuiScreen(new GuiMessage(Config.minecraft.currentScreen, s, s2));
    }
    
    public static int[] addIntToArray(final int[] array, final int n) {
        return addIntsToArray(array, new int[] { n });
    }
    
    public static int[] addIntsToArray(final int[] array, final int[] array2) {
        if (array != null && array2 != null) {
            final int length = array.length;
            final int[] array3 = new int[length + array2.length];
            System.arraycopy(array, 0, array3, 0, length);
            while (0 < array2.length) {
                array3[0 + length] = array2[0];
                int n = 0;
                ++n;
            }
            return array3;
        }
        throw new NullPointerException("The given array is NULL");
    }
    
    public static DynamicTexture getMojangLogoTexture(final DynamicTexture dynamicTexture) {
        final InputStream resourceStream = getResourceStream(new ResourceLocation("textures/gui/title/mojang.png"));
        if (resourceStream == null) {
            return dynamicTexture;
        }
        final BufferedImage read = ImageIO.read(resourceStream);
        if (read == null) {
            return dynamicTexture;
        }
        return new DynamicTexture(read);
    }
    
    public static void writeFile(final File file, final String s) throws IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(s.getBytes("ASCII"));
        fileOutputStream.close();
    }
    
    public static TextureMap getTextureMap() {
        return getMinecraft().getTextureMapBlocks();
    }
    
    public static boolean isDynamicLightsFast() {
        return Config.gameSettings.ofDynamicLights == 1;
    }
    
    public static boolean isDynamicHandLight() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: iconst_0       
        //     4: goto            20
        //     7: invokestatic    optifine/Config.isShaders:()Z
        //    10: ifeq            19
        //    13: invokestatic    shadersmod/client/Shaders.isDynamicHandLight:()Z
        //    16: goto            20
        //    19: iconst_1       
        //    20: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
