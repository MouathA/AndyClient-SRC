package net.minecraft.client.settings;

import com.google.gson.*;
import net.minecraft.client.*;
import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.audio.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.resources.*;
import org.lwjgl.input.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.stream.*;
import java.io.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import shadersmod.client.*;
import optifine.*;
import net.minecraft.world.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class GameSettings
{
    private static final Logger logger;
    private static final Gson gson;
    private static final ParameterizedType typeListString;
    private static final String[] GUISCALES;
    private static final String[] PARTICLES;
    private static final String[] AMBIENT_OCCLUSIONS;
    private static final String[] STREAM_COMPRESSIONS;
    private static final String[] STREAM_CHAT_MODES;
    private static final String[] STREAM_CHAT_FILTER_MODES;
    private static final String[] STREAM_MIC_MODES;
    public boolean ofSmartAnimations;
    public float mouseSensitivity;
    public boolean invertMouse;
    public static int renderDistanceChunks;
    public boolean viewBobbing;
    public boolean anaglyph;
    public boolean fboEnable;
    public int limitFramerate;
    public boolean clouds;
    public boolean fancyGraphics;
    public int ambientOcclusion;
    public List resourcePacks;
    public static EntityPlayer.EnumChatVisibility chatVisibility;
    public static boolean chatColours;
    public boolean chatLinks;
    public boolean chatLinksPrompt;
    public float chatOpacity;
    public boolean snooperEnabled;
    public boolean fullScreen;
    public boolean enableVsync;
    public boolean field_178881_t;
    public boolean field_178880_u;
    public boolean field_178879_v;
    public boolean hideServerAddress;
    public boolean useNativeTransport;
    public boolean advancedItemTooltips;
    public boolean pauseOnLostFocus;
    private static Set field_178882_aU;
    public boolean touchscreen;
    public int overrideWidth;
    public int overrideHeight;
    public boolean heldItemTooltips;
    public float chatScale;
    public float chatWidth;
    public float chatHeightUnfocused;
    public float chatHeightFocused;
    public boolean showInventoryAchievementHint;
    public int mipmapLevels;
    private Map mapSoundLevels;
    public float streamBytesPerPixel;
    public float streamMicVolume;
    public float streamGameVolume;
    public float streamKbps;
    public float streamFps;
    public int streamCompression;
    public boolean streamSendMetadata;
    public String streamPreferredServer;
    public int streamChatEnabled;
    public int streamChatUserFilter;
    public int streamMicToggleBehavior;
    public KeyBinding keyBindForward;
    public KeyBinding keyBindLeft;
    public KeyBinding keyBindBack;
    public KeyBinding keyBindRight;
    public KeyBinding keyBindJump;
    public KeyBinding keyBindSneak;
    public KeyBinding keyBindInventory;
    public KeyBinding keyBindUseItem;
    public KeyBinding keyBindDrop;
    public KeyBinding keyBindAttack;
    public KeyBinding keyBindPickBlock;
    public KeyBinding keyBindSprint;
    public KeyBinding keyBindChat;
    public KeyBinding keyBindPlayerList;
    public KeyBinding keyBindCommand;
    public KeyBinding keyBindScreenshot;
    public KeyBinding keyBindTogglePerspective;
    public KeyBinding keyBindSmoothCamera;
    public KeyBinding keyBindFullscreen;
    public KeyBinding field_178883_an;
    public KeyBinding keyBindStreamStartStop;
    public KeyBinding keyBindStreamPauseUnpause;
    public KeyBinding keyBindStreamCommercials;
    public KeyBinding keyBindStreamToggleMic;
    public KeyBinding[] keyBindsHotbar;
    public KeyBinding[] keyBindings;
    protected Minecraft mc;
    private File optionsFile;
    public EnumDifficulty difficulty;
    public boolean hideGUI;
    public int thirdPersonView;
    public boolean showDebugInfo;
    public boolean showDebugProfilerChart;
    public String lastServer;
    private final Set setModelParts;
    public boolean smoothCamera;
    public boolean debugCamEnable;
    public float fovSetting;
    public float gammaSetting;
    public float saturation;
    public int guiScale;
    public int particleSetting;
    public static String language;
    public boolean forceUnicodeFont;
    private static final String __OBFID;
    public int ofFogType;
    public float ofFogStart;
    public int ofMipmapType;
    public boolean ofOcclusionFancy;
    public boolean ofSmoothFps;
    public boolean ofSmoothWorld;
    public boolean ofLazyChunkLoading;
    public float ofAoLevel;
    public int ofAaLevel;
    public int ofAfLevel;
    public int ofClouds;
    public float ofCloudsHeight;
    public int ofTrees;
    public int ofRain;
    public int ofDroppedItems;
    public int ofBetterGrass;
    public int ofAutoSaveTicks;
    public boolean ofLagometer;
    public boolean ofProfiler;
    public boolean ofShowFps;
    public boolean ofWeather;
    public boolean ofSky;
    public boolean ofStars;
    public boolean ofSunMoon;
    public int ofVignette;
    public int ofChunkUpdates;
    public boolean ofChunkUpdatesDynamic;
    public int ofTime;
    public boolean ofClearWater;
    public boolean ofBetterSnow;
    public String ofFullscreenMode;
    public boolean ofSwampColors;
    public boolean ofRandomMobs;
    public boolean ofSmoothBiomes;
    public boolean ofCustomFonts;
    public boolean ofCustomColors;
    public boolean ofCustomSky;
    public boolean ofShowCapes;
    public int ofConnectedTextures;
    public boolean ofCustomItems;
    public boolean ofNaturalTextures;
    public boolean ofFastMath;
    public boolean ofFastRender;
    public int ofTranslucentBlocks;
    public boolean ofDynamicFov;
    public int ofDynamicLights;
    public int ofAnimatedWater;
    public int ofAnimatedLava;
    public boolean ofAnimatedFire;
    public boolean ofAnimatedPortal;
    public boolean ofAnimatedRedstone;
    public boolean ofAnimatedExplosion;
    public boolean ofAnimatedFlame;
    public boolean ofAnimatedSmoke;
    public boolean ofVoidParticles;
    public boolean ofWaterParticles;
    public boolean ofRainSplash;
    public boolean ofPortalParticles;
    public boolean ofPotionParticles;
    public boolean ofFireworkParticles;
    public boolean ofDrippingWaterLava;
    public boolean ofAnimatedTerrain;
    public boolean ofAnimatedTextures;
    public static final int DEFAULT;
    public static final int FAST;
    public static final int FANCY;
    public static final int OFF;
    public static final int SMART;
    public static final int ANIM_ON;
    public static final int ANIM_GENERATED;
    public static final int ANIM_OFF;
    public static final String DEFAULT_STR;
    private static final int[] OF_TREES_VALUES;
    private static final int[] OF_DYNAMIC_LIGHTS;
    private static final String[] KEYS_DYNAMIC_LIGHTS;
    public KeyBinding ofKeyBindZoom;
    private File optionsFileOF;
    private static final String[] llIllIIIllllIIl;
    private static String[] llIllIIllIIlIIl;
    
    static {
        lIlIIIIIIIIlIlll();
        lIlIIIIIIIIlIIII();
        ANIM_OFF = 2;
        SMART = 4;
        FAST = 1;
        ANIM_ON = 0;
        DEFAULT_STR = GameSettings.llIllIIIllllIIl[0];
        ANIM_GENERATED = 1;
        __OBFID = GameSettings.llIllIIIllllIIl[1];
        FANCY = 2;
        DEFAULT = 0;
        OFF = 3;
        logger = LogManager.getLogger();
        gson = new Gson();
        typeListString = new ParameterizedType() {
            private static final String __OBFID;
            
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { String.class };
            }
            
            @Override
            public Type getRawType() {
                return List.class;
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
            
            static {
                __OBFID = "CL_00000651";
            }
        };
        GUISCALES = new String[] { GameSettings.llIllIIIllllIIl[2], GameSettings.llIllIIIllllIIl[3], GameSettings.llIllIIIllllIIl[4], GameSettings.llIllIIIllllIIl[5] };
        PARTICLES = new String[] { GameSettings.llIllIIIllllIIl[6], GameSettings.llIllIIIllllIIl[7], GameSettings.llIllIIIllllIIl[8] };
        AMBIENT_OCCLUSIONS = new String[] { GameSettings.llIllIIIllllIIl[9], GameSettings.llIllIIIllllIIl[10], GameSettings.llIllIIIllllIIl[11] };
        STREAM_COMPRESSIONS = new String[] { GameSettings.llIllIIIllllIIl[12], GameSettings.llIllIIIllllIIl[13], GameSettings.llIllIIIllllIIl[14] };
        STREAM_CHAT_MODES = new String[] { GameSettings.llIllIIIllllIIl[15], GameSettings.llIllIIIllllIIl[16], GameSettings.llIllIIIllllIIl[17] };
        STREAM_CHAT_FILTER_MODES = new String[] { GameSettings.llIllIIIllllIIl[18], GameSettings.llIllIIIllllIIl[19], GameSettings.llIllIIIllllIIl[20] };
        STREAM_MIC_MODES = new String[] { GameSettings.llIllIIIllllIIl[21], GameSettings.llIllIIIllllIIl[22] };
        GameSettings.renderDistanceChunks = -1;
        OF_TREES_VALUES = new int[] { 0, 1, 4, 2 };
        OF_DYNAMIC_LIGHTS = new int[] { 3, 1, 2 };
        KEYS_DYNAMIC_LIGHTS = new String[] { GameSettings.llIllIIIllllIIl[23], GameSettings.llIllIIIllllIIl[24], GameSettings.llIllIIIllllIIl[25] };
    }
    
    public GameSettings(final Minecraft mc, final File file) {
        this.ofSmartAnimations = false;
        this.mouseSensitivity = 0.5f;
        this.viewBobbing = true;
        this.fboEnable = true;
        this.limitFramerate = 120;
        this.clouds = true;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.resourcePacks = Lists.newArrayList();
        this.useNativeTransport = true;
        this.setModelParts = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapType = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofRain = 0;
        this.ofDroppedItems = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofShowFps = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofVignette = 0;
        this.ofChunkUpdates = 1;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = GameSettings.llIllIIIllllIIl[26];
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofCustomItems = true;
        this.ofNaturalTextures = false;
        this.ofFastMath = false;
        this.ofFastRender = true;
        this.ofTranslucentBlocks = 0;
        this.ofDynamicFov = true;
        this.ofDynamicLights = 3;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofFireworkParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedTextures = true;
        GameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
        GameSettings.chatColours = true;
        this.chatLinks = true;
        this.chatLinksPrompt = true;
        this.chatOpacity = 1.0f;
        this.snooperEnabled = true;
        this.enableVsync = true;
        this.field_178881_t = false;
        this.field_178880_u = true;
        this.field_178879_v = false;
        this.pauseOnLostFocus = true;
        GameSettings.field_178882_aU = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
        this.heldItemTooltips = true;
        this.chatScale = 1.0f;
        this.chatWidth = 1.0f;
        this.chatHeightUnfocused = 0.44366196f;
        this.chatHeightFocused = 1.0f;
        this.showInventoryAchievementHint = true;
        this.mipmapLevels = 4;
        this.mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
        this.streamBytesPerPixel = 0.5f;
        this.streamMicVolume = 1.0f;
        this.streamGameVolume = 1.0f;
        this.streamKbps = 0.5412844f;
        this.streamFps = 0.31690142f;
        this.streamCompression = 1;
        this.streamSendMetadata = true;
        this.streamPreferredServer = GameSettings.llIllIIIllllIIl[27];
        this.streamChatEnabled = 0;
        this.streamChatUserFilter = 0;
        this.streamMicToggleBehavior = 0;
        this.keyBindForward = new KeyBinding(GameSettings.llIllIIIllllIIl[28], 17, GameSettings.llIllIIIllllIIl[29]);
        this.keyBindLeft = new KeyBinding(GameSettings.llIllIIIllllIIl[30], 30, GameSettings.llIllIIIllllIIl[31]);
        this.keyBindBack = new KeyBinding(GameSettings.llIllIIIllllIIl[32], 31, GameSettings.llIllIIIllllIIl[33]);
        this.keyBindRight = new KeyBinding(GameSettings.llIllIIIllllIIl[34], 32, GameSettings.llIllIIIllllIIl[35]);
        this.keyBindJump = new KeyBinding(GameSettings.llIllIIIllllIIl[36], 57, GameSettings.llIllIIIllllIIl[37]);
        this.keyBindSneak = new KeyBinding(GameSettings.llIllIIIllllIIl[38], 42, GameSettings.llIllIIIllllIIl[39]);
        this.keyBindInventory = new KeyBinding(GameSettings.llIllIIIllllIIl[40], 18, GameSettings.llIllIIIllllIIl[41]);
        this.keyBindUseItem = new KeyBinding(GameSettings.llIllIIIllllIIl[42], -99, GameSettings.llIllIIIllllIIl[43]);
        this.keyBindDrop = new KeyBinding(GameSettings.llIllIIIllllIIl[44], 16, GameSettings.llIllIIIllllIIl[45]);
        this.keyBindAttack = new KeyBinding(GameSettings.llIllIIIllllIIl[46], -100, GameSettings.llIllIIIllllIIl[47]);
        this.keyBindPickBlock = new KeyBinding(GameSettings.llIllIIIllllIIl[48], -98, GameSettings.llIllIIIllllIIl[49]);
        this.keyBindSprint = new KeyBinding(GameSettings.llIllIIIllllIIl[50], 29, GameSettings.llIllIIIllllIIl[51]);
        this.keyBindChat = new KeyBinding(GameSettings.llIllIIIllllIIl[52], 20, GameSettings.llIllIIIllllIIl[53]);
        this.keyBindPlayerList = new KeyBinding(GameSettings.llIllIIIllllIIl[54], 15, GameSettings.llIllIIIllllIIl[55]);
        this.keyBindCommand = new KeyBinding(GameSettings.llIllIIIllllIIl[56], 53, GameSettings.llIllIIIllllIIl[57]);
        this.keyBindScreenshot = new KeyBinding(GameSettings.llIllIIIllllIIl[58], 60, GameSettings.llIllIIIllllIIl[59]);
        this.keyBindTogglePerspective = new KeyBinding(GameSettings.llIllIIIllllIIl[60], 63, GameSettings.llIllIIIllllIIl[61]);
        this.keyBindSmoothCamera = new KeyBinding(GameSettings.llIllIIIllllIIl[62], 0, GameSettings.llIllIIIllllIIl[63]);
        this.keyBindFullscreen = new KeyBinding(GameSettings.llIllIIIllllIIl[64], 87, GameSettings.llIllIIIllllIIl[65]);
        this.field_178883_an = new KeyBinding(GameSettings.llIllIIIllllIIl[66], 0, GameSettings.llIllIIIllllIIl[67]);
        this.keyBindStreamStartStop = new KeyBinding(GameSettings.llIllIIIllllIIl[68], 64, GameSettings.llIllIIIllllIIl[69]);
        this.keyBindStreamPauseUnpause = new KeyBinding(GameSettings.llIllIIIllllIIl[70], 65, GameSettings.llIllIIIllllIIl[71]);
        this.keyBindStreamCommercials = new KeyBinding(GameSettings.llIllIIIllllIIl[72], 0, GameSettings.llIllIIIllllIIl[73]);
        this.keyBindStreamToggleMic = new KeyBinding(GameSettings.llIllIIIllllIIl[74], 0, GameSettings.llIllIIIllllIIl[75]);
        this.keyBindsHotbar = new KeyBinding[] { new KeyBinding(GameSettings.llIllIIIllllIIl[76], 2, GameSettings.llIllIIIllllIIl[77]), new KeyBinding(GameSettings.llIllIIIllllIIl[78], 3, GameSettings.llIllIIIllllIIl[79]), new KeyBinding(GameSettings.llIllIIIllllIIl[80], 4, GameSettings.llIllIIIllllIIl[81]), new KeyBinding(GameSettings.llIllIIIllllIIl[82], 5, GameSettings.llIllIIIllllIIl[83]), new KeyBinding(GameSettings.llIllIIIllllIIl[84], 6, GameSettings.llIllIIIllllIIl[85]), new KeyBinding(GameSettings.llIllIIIllllIIl[86], 7, GameSettings.llIllIIIllllIIl[87]), new KeyBinding(GameSettings.llIllIIIllllIIl[88], 8, GameSettings.llIllIIIllllIIl[89]), new KeyBinding(GameSettings.llIllIIIllllIIl[90], 9, GameSettings.llIllIIIllllIIl[91]), new KeyBinding(GameSettings.llIllIIIllllIIl[92], 10, GameSettings.llIllIIIllllIIl[93]) };
        this.keyBindings = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindSprint, this.keyBindStreamStartStop, this.keyBindStreamPauseUnpause, this.keyBindStreamCommercials, this.keyBindStreamToggleMic, this.keyBindFullscreen, this.field_178883_an }, (Object[])this.keyBindsHotbar);
        this.difficulty = EnumDifficulty.NORMAL;
        this.lastServer = GameSettings.llIllIIIllllIIl[94];
        this.fovSetting = 70.0f;
        GameSettings.language = GameSettings.llIllIIIllllIIl[95];
        this.forceUnicodeFont = false;
        this.mc = mc;
        this.optionsFile = new File(file, GameSettings.llIllIIIllllIIl[96]);
        this.optionsFileOF = new File(file, GameSettings.llIllIIIllllIIl[97]);
        this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
        this.ofKeyBindZoom = new KeyBinding(GameSettings.llIllIIIllllIIl[98], 46, GameSettings.llIllIIIllllIIl[99]);
        this.keyBindings = (KeyBinding[])ArrayUtils.add(this.keyBindings, this.ofKeyBindZoom);
        Options.RENDER_DISTANCE.setValueMax(32.0f);
        GameSettings.renderDistanceChunks = 8;
        this.loadOptions();
        Config.initGameSettings(this);
    }
    
    public GameSettings() {
        this.ofSmartAnimations = false;
        this.mouseSensitivity = 0.5f;
        this.viewBobbing = true;
        this.fboEnable = true;
        this.limitFramerate = 120;
        this.clouds = true;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.resourcePacks = Lists.newArrayList();
        this.useNativeTransport = true;
        this.setModelParts = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapType = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofRain = 0;
        this.ofDroppedItems = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofShowFps = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofVignette = 0;
        this.ofChunkUpdates = 1;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = GameSettings.llIllIIIllllIIl[100];
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofCustomItems = true;
        this.ofNaturalTextures = false;
        this.ofFastMath = false;
        this.ofFastRender = true;
        this.ofTranslucentBlocks = 0;
        this.ofDynamicFov = true;
        this.ofDynamicLights = 3;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofFireworkParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedTextures = true;
        GameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
        GameSettings.chatColours = true;
        this.chatLinks = true;
        this.chatLinksPrompt = true;
        this.chatOpacity = 1.0f;
        this.snooperEnabled = true;
        this.enableVsync = true;
        this.field_178881_t = false;
        this.field_178880_u = true;
        this.field_178879_v = false;
        this.pauseOnLostFocus = true;
        GameSettings.field_178882_aU = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
        this.heldItemTooltips = true;
        this.chatScale = 1.0f;
        this.chatWidth = 1.0f;
        this.chatHeightUnfocused = 0.44366196f;
        this.chatHeightFocused = 1.0f;
        this.showInventoryAchievementHint = true;
        this.mipmapLevels = 4;
        this.mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
        this.streamBytesPerPixel = 0.5f;
        this.streamMicVolume = 1.0f;
        this.streamGameVolume = 1.0f;
        this.streamKbps = 0.5412844f;
        this.streamFps = 0.31690142f;
        this.streamCompression = 1;
        this.streamSendMetadata = true;
        this.streamPreferredServer = GameSettings.llIllIIIllllIIl[101];
        this.streamChatEnabled = 0;
        this.streamChatUserFilter = 0;
        this.streamMicToggleBehavior = 0;
        this.keyBindForward = new KeyBinding(GameSettings.llIllIIIllllIIl[102], 17, GameSettings.llIllIIIllllIIl[103]);
        this.keyBindLeft = new KeyBinding(GameSettings.llIllIIIllllIIl[104], 30, GameSettings.llIllIIIllllIIl[105]);
        this.keyBindBack = new KeyBinding(GameSettings.llIllIIIllllIIl[106], 31, GameSettings.llIllIIIllllIIl[107]);
        this.keyBindRight = new KeyBinding(GameSettings.llIllIIIllllIIl[108], 32, GameSettings.llIllIIIllllIIl[109]);
        this.keyBindJump = new KeyBinding(GameSettings.llIllIIIllllIIl[110], 57, GameSettings.llIllIIIllllIIl[111]);
        this.keyBindSneak = new KeyBinding(GameSettings.llIllIIIllllIIl[112], 42, GameSettings.llIllIIIllllIIl[113]);
        this.keyBindInventory = new KeyBinding(GameSettings.llIllIIIllllIIl[114], 18, GameSettings.llIllIIIllllIIl[115]);
        this.keyBindUseItem = new KeyBinding(GameSettings.llIllIIIllllIIl[116], -99, GameSettings.llIllIIIllllIIl[117]);
        this.keyBindDrop = new KeyBinding(GameSettings.llIllIIIllllIIl[118], 16, GameSettings.llIllIIIllllIIl[119]);
        this.keyBindAttack = new KeyBinding(GameSettings.llIllIIIllllIIl[120], -100, GameSettings.llIllIIIllllIIl[121]);
        this.keyBindPickBlock = new KeyBinding(GameSettings.llIllIIIllllIIl[122], -98, GameSettings.llIllIIIllllIIl[123]);
        this.keyBindSprint = new KeyBinding(GameSettings.llIllIIIllllIIl[124], 29, GameSettings.llIllIIIllllIIl[125]);
        this.keyBindChat = new KeyBinding(GameSettings.llIllIIIllllIIl[126], 20, GameSettings.llIllIIIllllIIl[127]);
        this.keyBindPlayerList = new KeyBinding(GameSettings.llIllIIIllllIIl[128], 15, GameSettings.llIllIIIllllIIl[129]);
        this.keyBindCommand = new KeyBinding(GameSettings.llIllIIIllllIIl[130], 53, GameSettings.llIllIIIllllIIl[131]);
        this.keyBindScreenshot = new KeyBinding(GameSettings.llIllIIIllllIIl[132], 60, GameSettings.llIllIIIllllIIl[133]);
        this.keyBindTogglePerspective = new KeyBinding(GameSettings.llIllIIIllllIIl[134], 63, GameSettings.llIllIIIllllIIl[135]);
        this.keyBindSmoothCamera = new KeyBinding(GameSettings.llIllIIIllllIIl[136], 0, GameSettings.llIllIIIllllIIl[137]);
        this.keyBindFullscreen = new KeyBinding(GameSettings.llIllIIIllllIIl[138], 87, GameSettings.llIllIIIllllIIl[139]);
        this.field_178883_an = new KeyBinding(GameSettings.llIllIIIllllIIl[140], 0, GameSettings.llIllIIIllllIIl[141]);
        this.keyBindStreamStartStop = new KeyBinding(GameSettings.llIllIIIllllIIl[142], 64, GameSettings.llIllIIIllllIIl[143]);
        this.keyBindStreamPauseUnpause = new KeyBinding(GameSettings.llIllIIIllllIIl[144], 65, GameSettings.llIllIIIllllIIl[145]);
        this.keyBindStreamCommercials = new KeyBinding(GameSettings.llIllIIIllllIIl[146], 0, GameSettings.llIllIIIllllIIl[147]);
        this.keyBindStreamToggleMic = new KeyBinding(GameSettings.llIllIIIllllIIl[148], 0, GameSettings.llIllIIIllllIIl[149]);
        this.keyBindsHotbar = new KeyBinding[] { new KeyBinding(GameSettings.llIllIIIllllIIl[150], 2, GameSettings.llIllIIIllllIIl[151]), new KeyBinding(GameSettings.llIllIIIllllIIl[152], 3, GameSettings.llIllIIIllllIIl[153]), new KeyBinding(GameSettings.llIllIIIllllIIl[154], 4, GameSettings.llIllIIIllllIIl[155]), new KeyBinding(GameSettings.llIllIIIllllIIl[156], 5, GameSettings.llIllIIIllllIIl[157]), new KeyBinding(GameSettings.llIllIIIllllIIl[158], 6, GameSettings.llIllIIIllllIIl[159]), new KeyBinding(GameSettings.llIllIIIllllIIl[160], 7, GameSettings.llIllIIIllllIIl[161]), new KeyBinding(GameSettings.llIllIIIllllIIl[162], 8, GameSettings.llIllIIIllllIIl[163]), new KeyBinding(GameSettings.llIllIIIllllIIl[164], 9, GameSettings.llIllIIIllllIIl[165]), new KeyBinding(GameSettings.llIllIIIllllIIl[166], 10, GameSettings.llIllIIIllllIIl[167]) };
        this.keyBindings = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindSprint, this.keyBindStreamStartStop, this.keyBindStreamPauseUnpause, this.keyBindStreamCommercials, this.keyBindStreamToggleMic, this.keyBindFullscreen, this.field_178883_an }, (Object[])this.keyBindsHotbar);
        this.difficulty = EnumDifficulty.NORMAL;
        this.lastServer = GameSettings.llIllIIIllllIIl[168];
        this.fovSetting = 70.0f;
        GameSettings.language = GameSettings.llIllIIIllllIIl[169];
        this.forceUnicodeFont = false;
    }
    
    public static String getKeyDisplayString(final int n) {
        return (n < 0) ? I18n.format(GameSettings.llIllIIIllllIIl[170], n + 101) : ((n < 256) ? Keyboard.getKeyName(n) : String.format(GameSettings.llIllIIIllllIIl[171], (char)(n - 256)).toUpperCase());
    }
    
    public static boolean isKeyDown(final KeyBinding keyBinding) {
        final int keyCode = keyBinding.getKeyCode();
        return keyCode >= -100 && keyCode <= 255 && keyBinding.getKeyCode() != 0 && ((keyBinding.getKeyCode() < 0) ? Mouse.isButtonDown(keyBinding.getKeyCode() + 100) : Keyboard.isKeyDown(keyBinding.getKeyCode()));
    }
    
    public void setOptionKeyBinding(final KeyBinding keyBinding, final int keyCode) {
        keyBinding.setKeyCode(keyCode);
        this.saveOptions();
    }
    
    public void setOptionFloatValue(final Options options, final float streamFps) {
        this.setOptionFloatValueOF(options, streamFps);
        if (options == Options.SENSITIVITY) {
            this.mouseSensitivity = streamFps;
        }
        if (options == Options.FOV) {
            this.fovSetting = streamFps;
        }
        if (options == Options.GAMMA) {
            this.gammaSetting = streamFps;
        }
        if (options == Options.FRAMERATE_LIMIT) {
            this.limitFramerate = (int)streamFps;
            this.enableVsync = false;
            if (this.limitFramerate <= 0) {
                this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
                this.enableVsync = true;
            }
            this.updateVSync();
        }
        if (options == Options.CHAT_OPACITY) {
            this.chatOpacity = streamFps;
            Minecraft.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.CHAT_HEIGHT_FOCUSED) {
            this.chatHeightFocused = streamFps;
            Minecraft.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.CHAT_HEIGHT_UNFOCUSED) {
            this.chatHeightUnfocused = streamFps;
            Minecraft.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.CHAT_WIDTH) {
            this.chatWidth = streamFps;
            Minecraft.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.CHAT_SCALE) {
            this.chatScale = streamFps;
            Minecraft.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.MIPMAP_LEVELS) {
            final int mipmapLevels = this.mipmapLevels;
            this.mipmapLevels = (int)streamFps;
            if (mipmapLevels != streamFps) {
                this.mc.getTextureMapBlocks().setMipmapLevels(this.mipmapLevels);
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.mc.getTextureMapBlocks().func_174937_a(false, this.mipmapLevels > 0);
                this.mc.func_175603_A();
            }
        }
        if (options == Options.BLOCK_ALTERNATIVES) {
            this.field_178880_u = !this.field_178880_u;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.RENDER_DISTANCE) {
            GameSettings.renderDistanceChunks = (int)streamFps;
            this.mc.renderGlobal.func_174979_m();
        }
        if (options == Options.STREAM_BYTES_PER_PIXEL) {
            this.streamBytesPerPixel = streamFps;
        }
        if (options == Options.STREAM_VOLUME_MIC) {
            this.streamMicVolume = streamFps;
            this.mc.getTwitchStream().func_152915_s();
        }
        if (options == Options.STREAM_VOLUME_SYSTEM) {
            this.streamGameVolume = streamFps;
            this.mc.getTwitchStream().func_152915_s();
        }
        if (options == Options.STREAM_KBPS) {
            this.streamKbps = streamFps;
        }
        if (options == Options.STREAM_FPS) {
            this.streamFps = streamFps;
        }
    }
    
    public void setOptionValue(final Options options, final int n) {
        this.setOptionValueOF(options, n);
        if (options == Options.INVERT_MOUSE) {
            this.invertMouse = !this.invertMouse;
        }
        if (options == Options.GUI_SCALE) {
            this.guiScale = (this.guiScale + n & 0x3);
        }
        if (options == Options.PARTICLES) {
            this.particleSetting = (this.particleSetting + n) % 3;
        }
        if (options == Options.VIEW_BOBBING) {
            this.viewBobbing = !this.viewBobbing;
        }
        if (options == Options.RENDER_CLOUDS) {
            this.clouds = !this.clouds;
        }
        if (options == Options.FORCE_UNICODE_FONT) {
            this.forceUnicodeFont = !this.forceUnicodeFont;
            Minecraft.fontRendererObj.setUnicodeFlag(this.mc.getLanguageManager().isCurrentLocaleUnicode() || this.forceUnicodeFont);
        }
        if (options == Options.FBO_ENABLE) {
            this.fboEnable = !this.fboEnable;
        }
        if (options == Options.ANAGLYPH) {
            this.anaglyph = !this.anaglyph;
            this.mc.refreshResources();
        }
        if (options == Options.GRAPHICS) {
            this.fancyGraphics = !this.fancyGraphics;
            this.updateRenderClouds();
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.AMBIENT_OCCLUSION) {
            this.ambientOcclusion = (this.ambientOcclusion + n) % 3;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.CHAT_VISIBILITY) {
            GameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((GameSettings.chatVisibility.getChatVisibility() + n) % 3);
        }
        if (options == Options.STREAM_COMPRESSION) {
            this.streamCompression = (this.streamCompression + n) % 3;
        }
        if (options == Options.STREAM_SEND_METADATA) {
            this.streamSendMetadata = !this.streamSendMetadata;
        }
        if (options == Options.STREAM_CHAT_ENABLED) {
            this.streamChatEnabled = (this.streamChatEnabled + n) % 3;
        }
        if (options == Options.STREAM_CHAT_USER_FILTER) {
            this.streamChatUserFilter = (this.streamChatUserFilter + n) % 3;
        }
        if (options == Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
            this.streamMicToggleBehavior = (this.streamMicToggleBehavior + n) % 2;
        }
        if (options == Options.CHAT_COLOR) {
            GameSettings.chatColours = !GameSettings.chatColours;
        }
        if (options == Options.CHAT_LINKS) {
            this.chatLinks = !this.chatLinks;
        }
        if (options == Options.CHAT_LINKS_PROMPT) {
            this.chatLinksPrompt = !this.chatLinksPrompt;
        }
        if (options == Options.SNOOPER_ENABLED) {
            this.snooperEnabled = !this.snooperEnabled;
        }
        if (options == Options.TOUCHSCREEN) {
            this.touchscreen = !this.touchscreen;
        }
        if (options == Options.USE_FULLSCREEN) {
            this.fullScreen = !this.fullScreen;
            if (this.mc.isFullScreen() != this.fullScreen) {
                this.mc.toggleFullscreen();
            }
        }
        if (options == Options.ENABLE_VSYNC) {
            Display.setVSyncEnabled(this.enableVsync = !this.enableVsync);
        }
        if (options == Options.USE_VBO) {
            this.field_178881_t = !this.field_178881_t;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.BLOCK_ALTERNATIVES) {
            this.field_178880_u = !this.field_178880_u;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.REDUCED_DEBUG_INFO) {
            this.field_178879_v = !this.field_178879_v;
        }
        this.saveOptions();
    }
    
    public float getOptionFloatValue(final Options options) {
        return (options == Options.CLOUD_HEIGHT) ? this.ofCloudsHeight : ((options == Options.AO_LEVEL) ? this.ofAoLevel : ((options == Options.AA_LEVEL) ? ((float)this.ofAaLevel) : ((options == Options.AF_LEVEL) ? ((float)this.ofAfLevel) : ((options == Options.MIPMAP_TYPE) ? ((float)this.ofMipmapType) : ((options == Options.FRAMERATE_LIMIT) ? ((this.limitFramerate == Options.FRAMERATE_LIMIT.getValueMax() && this.enableVsync) ? 0.0f : ((float)this.limitFramerate)) : ((options == Options.FOV) ? this.fovSetting : ((options == Options.GAMMA) ? this.gammaSetting : ((options == Options.SATURATION) ? this.saturation : ((options == Options.SENSITIVITY) ? this.mouseSensitivity : ((options == Options.CHAT_OPACITY) ? this.chatOpacity : ((options == Options.CHAT_HEIGHT_FOCUSED) ? this.chatHeightFocused : ((options == Options.CHAT_HEIGHT_UNFOCUSED) ? this.chatHeightUnfocused : ((options == Options.CHAT_SCALE) ? this.chatScale : ((options == Options.CHAT_WIDTH) ? this.chatWidth : ((options == Options.FRAMERATE_LIMIT) ? ((float)this.limitFramerate) : ((options == Options.MIPMAP_LEVELS) ? ((float)this.mipmapLevels) : ((options == Options.RENDER_DISTANCE) ? ((float)GameSettings.renderDistanceChunks) : ((options == Options.STREAM_BYTES_PER_PIXEL) ? this.streamBytesPerPixel : ((options == Options.STREAM_VOLUME_MIC) ? this.streamMicVolume : ((options == Options.STREAM_VOLUME_SYSTEM) ? this.streamGameVolume : ((options == Options.STREAM_KBPS) ? this.streamKbps : ((options == Options.STREAM_FPS) ? this.streamFps : 0.0f))))))))))))))))))))));
    }
    
    public boolean getOptionOrdinalValue(final Options options) {
        switch (SwitchOptions.optionIds[options.ordinal()]) {
            case 1: {
                return this.invertMouse;
            }
            case 2: {
                return this.viewBobbing;
            }
            case 3: {
                return this.anaglyph;
            }
            case 4: {
                return this.fboEnable;
            }
            case 5: {
                return this.clouds;
            }
            case 6: {
                return GameSettings.chatColours;
            }
            case 7: {
                return this.chatLinks;
            }
            case 8: {
                return this.chatLinksPrompt;
            }
            case 9: {
                return this.snooperEnabled;
            }
            case 10: {
                return this.fullScreen;
            }
            case 11: {
                return this.enableVsync;
            }
            case 12: {
                return this.field_178881_t;
            }
            case 13: {
                return this.touchscreen;
            }
            case 14: {
                return this.streamSendMetadata;
            }
            case 15: {
                return this.forceUnicodeFont;
            }
            case 16: {
                return this.field_178880_u;
            }
            case 17: {
                return this.field_178879_v;
            }
            default: {
                return false;
            }
        }
    }
    
    private static String getTranslation(final String[] array, int n) {
        if (n < 0 || n >= array.length) {
            n = 0;
        }
        return I18n.format(array[n], new Object[0]);
    }
    
    public String getKeyBinding(final Options options) {
        final String keyBindingOF = this.getKeyBindingOF(options);
        if (keyBindingOF != null) {
            return keyBindingOF;
        }
        final String string = String.valueOf(I18n.format(options.getEnumString(), new Object[0])) + GameSettings.llIllIIIllllIIl[172];
        if (options.getEnumFloat()) {
            final float optionFloatValue = this.getOptionFloatValue(options);
            final float normalizeValue = options.normalizeValue(optionFloatValue);
            return (options == Options.SENSITIVITY) ? ((normalizeValue == 0.0f) ? (String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[173], new Object[0])) : ((normalizeValue == 1.0f) ? (String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[174], new Object[0])) : (String.valueOf(string) + (int)(normalizeValue * 200.0f) + GameSettings.llIllIIIllllIIl[175]))) : ((options == Options.FOV) ? ((optionFloatValue == 70.0f) ? (String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[176], new Object[0])) : ((optionFloatValue == 110.0f) ? (String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[177], new Object[0])) : (String.valueOf(string) + (int)optionFloatValue))) : ((options == Options.FRAMERATE_LIMIT) ? ((optionFloatValue == Options.access$2(options)) ? (String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[178], new Object[0])) : (String.valueOf(string) + (int)optionFloatValue + GameSettings.llIllIIIllllIIl[179])) : ((options == Options.RENDER_CLOUDS) ? ((optionFloatValue == Options.access$3(options)) ? (String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[180], new Object[0])) : (String.valueOf(string) + ((int)optionFloatValue + 128))) : ((options == Options.GAMMA) ? ((normalizeValue == 0.0f) ? (String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[181], new Object[0])) : ((normalizeValue == 1.0f) ? (String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[182], new Object[0])) : (String.valueOf(string) + GameSettings.llIllIIIllllIIl[183] + (int)(normalizeValue * 100.0f) + GameSettings.llIllIIIllllIIl[184]))) : ((options == Options.SATURATION) ? (String.valueOf(string) + (int)(normalizeValue * 400.0f) + GameSettings.llIllIIIllllIIl[185]) : ((options == Options.CHAT_OPACITY) ? (String.valueOf(string) + (int)(normalizeValue * 90.0f + 10.0f) + GameSettings.llIllIIIllllIIl[186]) : ((options == Options.CHAT_HEIGHT_UNFOCUSED) ? (String.valueOf(string) + GuiNewChat.calculateChatboxHeight(normalizeValue) + GameSettings.llIllIIIllllIIl[187]) : ((options == Options.CHAT_HEIGHT_FOCUSED) ? (String.valueOf(string) + GuiNewChat.calculateChatboxHeight(normalizeValue) + GameSettings.llIllIIIllllIIl[188]) : ((options == Options.CHAT_WIDTH) ? (String.valueOf(string) + GuiNewChat.calculateChatboxWidth(normalizeValue) + GameSettings.llIllIIIllllIIl[189]) : ((options == Options.RENDER_DISTANCE) ? (String.valueOf(string) + (int)optionFloatValue + GameSettings.llIllIIIllllIIl[190]) : ((options == Options.MIPMAP_LEVELS) ? ((optionFloatValue == 0.0f) ? (String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[191], new Object[0])) : (String.valueOf(string) + (int)optionFloatValue)) : ((options == Options.STREAM_FPS) ? (String.valueOf(string) + TwitchStream.func_152948_a(normalizeValue) + GameSettings.llIllIIIllllIIl[192]) : ((options == Options.STREAM_KBPS) ? (String.valueOf(string) + TwitchStream.func_152946_b(normalizeValue) + GameSettings.llIllIIIllllIIl[193]) : ((options == Options.STREAM_BYTES_PER_PIXEL) ? (String.valueOf(string) + String.format(GameSettings.llIllIIIllllIIl[194], TwitchStream.func_152947_c(normalizeValue))) : ((normalizeValue == 0.0f) ? (String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[195], new Object[0])) : (String.valueOf(string) + (int)(normalizeValue * 100.0f) + GameSettings.llIllIIIllllIIl[196]))))))))))))))));
        }
        if (options.getEnumBoolean()) {
            return this.getOptionOrdinalValue(options) ? (String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[197], new Object[0])) : (String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[198], new Object[0]));
        }
        if (options == Options.GUI_SCALE) {
            return String.valueOf(string) + getTranslation(GameSettings.GUISCALES, this.guiScale);
        }
        if (options == Options.CHAT_VISIBILITY) {
            return String.valueOf(string) + I18n.format(GameSettings.chatVisibility.getResourceKey(), new Object[0]);
        }
        if (options == Options.PARTICLES) {
            return String.valueOf(string) + getTranslation(GameSettings.PARTICLES, this.particleSetting);
        }
        if (options == Options.AMBIENT_OCCLUSION) {
            return String.valueOf(string) + getTranslation(GameSettings.AMBIENT_OCCLUSIONS, this.ambientOcclusion);
        }
        if (options == Options.STREAM_COMPRESSION) {
            return String.valueOf(string) + getTranslation(GameSettings.STREAM_COMPRESSIONS, this.streamCompression);
        }
        if (options == Options.STREAM_CHAT_ENABLED) {
            return String.valueOf(string) + getTranslation(GameSettings.STREAM_CHAT_MODES, this.streamChatEnabled);
        }
        if (options == Options.STREAM_CHAT_USER_FILTER) {
            return String.valueOf(string) + getTranslation(GameSettings.STREAM_CHAT_FILTER_MODES, this.streamChatUserFilter);
        }
        if (options == Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
            return String.valueOf(string) + getTranslation(GameSettings.STREAM_MIC_MODES, this.streamMicToggleBehavior);
        }
        if (options != Options.GRAPHICS) {
            return string;
        }
        if (this.fancyGraphics) {
            return String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[199], new Object[0]);
        }
        final String s = GameSettings.llIllIIIllllIIl[200];
        return String.valueOf(string) + I18n.format(GameSettings.llIllIIIllllIIl[201], new Object[0]);
    }
    
    public void loadOptions() {
        try {
            if (!this.optionsFile.exists()) {
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.optionsFile));
            final String s = GameSettings.llIllIIIllllIIl[202];
            this.mapSoundLevels.clear();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    final String[] split = line.split(GameSettings.llIllIIIllllIIl[203]);
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[204])) {
                        this.mouseSensitivity = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[205])) {
                        this.fovSetting = this.parseFloat(split[1]) * 40.0f + 70.0f;
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[206])) {
                        this.gammaSetting = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[207])) {
                        this.saturation = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[208])) {
                        this.invertMouse = split[1].equals(GameSettings.llIllIIIllllIIl[209]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[210])) {
                        GameSettings.renderDistanceChunks = Integer.parseInt(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[211])) {
                        this.guiScale = Integer.parseInt(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[212])) {
                        this.particleSetting = Integer.parseInt(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[213])) {
                        this.viewBobbing = split[1].equals(GameSettings.llIllIIIllllIIl[214]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[215])) {
                        this.anaglyph = split[1].equals(GameSettings.llIllIIIllllIIl[216]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[217])) {
                        this.limitFramerate = Integer.parseInt(split[1]);
                        this.enableVsync = false;
                        if (this.limitFramerate <= 0) {
                            this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
                            this.enableVsync = true;
                        }
                        this.updateVSync();
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[218])) {
                        this.fboEnable = split[1].equals(GameSettings.llIllIIIllllIIl[219]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[220])) {
                        this.difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(split[1]));
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[221])) {
                        this.fancyGraphics = split[1].equals(GameSettings.llIllIIIllllIIl[222]);
                        this.updateRenderClouds();
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[223])) {
                        if (split[1].equals(GameSettings.llIllIIIllllIIl[224])) {
                            this.ambientOcclusion = 2;
                        }
                        else if (split[1].equals(GameSettings.llIllIIIllllIIl[225])) {
                            this.ambientOcclusion = 0;
                        }
                        else {
                            this.ambientOcclusion = Integer.parseInt(split[1]);
                        }
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[226])) {
                        this.clouds = split[1].equals(GameSettings.llIllIIIllllIIl[227]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[228])) {
                        this.resourcePacks = (List)GameSettings.gson.fromJson(line.substring(line.indexOf(58) + 1), GameSettings.typeListString);
                        if (this.resourcePacks == null) {
                            this.resourcePacks = Lists.newArrayList();
                        }
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[229]) && split.length >= 2) {
                        this.lastServer = line.substring(line.indexOf(58) + 1);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[230]) && split.length >= 2) {
                        GameSettings.language = split[1];
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[231])) {
                        GameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(split[1]));
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[232])) {
                        GameSettings.chatColours = split[1].equals(GameSettings.llIllIIIllllIIl[233]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[234])) {
                        this.chatLinks = split[1].equals(GameSettings.llIllIIIllllIIl[235]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[236])) {
                        this.chatLinksPrompt = split[1].equals(GameSettings.llIllIIIllllIIl[237]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[238])) {
                        this.chatOpacity = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[239])) {
                        this.snooperEnabled = split[1].equals(GameSettings.llIllIIIllllIIl[240]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[241])) {
                        this.fullScreen = split[1].equals(GameSettings.llIllIIIllllIIl[242]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[243])) {
                        this.enableVsync = split[1].equals(GameSettings.llIllIIIllllIIl[244]);
                        this.updateVSync();
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[245])) {
                        this.field_178881_t = split[1].equals(GameSettings.llIllIIIllllIIl[246]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[247])) {
                        this.hideServerAddress = split[1].equals(GameSettings.llIllIIIllllIIl[248]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[249])) {
                        this.advancedItemTooltips = split[1].equals(GameSettings.llIllIIIllllIIl[250]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[251])) {
                        this.pauseOnLostFocus = split[1].equals(GameSettings.llIllIIIllllIIl[252]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[253])) {
                        this.touchscreen = split[1].equals(GameSettings.llIllIIIllllIIl[254]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[255])) {
                        this.overrideHeight = Integer.parseInt(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[256])) {
                        this.overrideWidth = Integer.parseInt(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[257])) {
                        this.heldItemTooltips = split[1].equals(GameSettings.llIllIIIllllIIl[258]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[259])) {
                        this.chatHeightFocused = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[260])) {
                        this.chatHeightUnfocused = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[261])) {
                        this.chatScale = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[262])) {
                        this.chatWidth = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[263])) {
                        this.showInventoryAchievementHint = split[1].equals(GameSettings.llIllIIIllllIIl[264]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[265])) {
                        this.mipmapLevels = Integer.parseInt(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[266])) {
                        this.streamBytesPerPixel = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[267])) {
                        this.streamMicVolume = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[268])) {
                        this.streamGameVolume = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[269])) {
                        this.streamKbps = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[270])) {
                        this.streamFps = this.parseFloat(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[271])) {
                        this.streamCompression = Integer.parseInt(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[272])) {
                        this.streamSendMetadata = split[1].equals(GameSettings.llIllIIIllllIIl[273]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[274]) && split.length >= 2) {
                        this.streamPreferredServer = line.substring(line.indexOf(58) + 1);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[275])) {
                        this.streamChatEnabled = Integer.parseInt(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[276])) {
                        this.streamChatUserFilter = Integer.parseInt(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[277])) {
                        this.streamMicToggleBehavior = Integer.parseInt(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[278])) {
                        this.forceUnicodeFont = split[1].equals(GameSettings.llIllIIIllllIIl[279]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[280])) {
                        this.field_178880_u = split[1].equals(GameSettings.llIllIIIllllIIl[281]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[282])) {
                        this.field_178879_v = split[1].equals(GameSettings.llIllIIIllllIIl[283]);
                    }
                    for (final KeyBinding keyBinding : this.keyBindings) {
                        if (split[0].equals(GameSettings.llIllIIIllllIIl[284] + keyBinding.getKeyDescription())) {
                            keyBinding.setKeyCode(Integer.parseInt(split[1]));
                        }
                    }
                    for (final SoundCategory soundCategory : SoundCategory.values()) {
                        if (split[0].equals(GameSettings.llIllIIIllllIIl[285] + soundCategory.getCategoryName())) {
                            this.mapSoundLevels.put(soundCategory, this.parseFloat(split[1]));
                        }
                    }
                    for (final EnumPlayerModelParts enumPlayerModelParts : EnumPlayerModelParts.values()) {
                        if (split[0].equals(GameSettings.llIllIIIllllIIl[286] + enumPlayerModelParts.func_179329_c())) {
                            this.func_178878_a(enumPlayerModelParts, split[1].equals(GameSettings.llIllIIIllllIIl[287]));
                        }
                    }
                }
                catch (Exception ex) {
                    GameSettings.logger.warn(GameSettings.llIllIIIllllIIl[288] + line);
                    ex.printStackTrace();
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedReader.close();
        }
        catch (Exception ex2) {
            GameSettings.logger.error(GameSettings.llIllIIIllllIIl[289], ex2);
        }
        this.loadOfOptions();
    }
    
    private float parseFloat(final String s) {
        return s.equals(GameSettings.llIllIIIllllIIl[290]) ? 1.0f : (s.equals(GameSettings.llIllIIIllllIIl[291]) ? 0.0f : Float.parseFloat(s));
    }
    
    public void saveOptions() {
        if (Reflector.FMLClientHandler.exists()) {
            final Object call = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
            if (call != null && Reflector.callBoolean(call, Reflector.FMLClientHandler_isLoading, new Object[0])) {
                return;
            }
        }
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(this.optionsFile));
            printWriter.println(GameSettings.llIllIIIllllIIl[292] + this.invertMouse);
            printWriter.println(GameSettings.llIllIIIllllIIl[293] + this.mouseSensitivity);
            printWriter.println(GameSettings.llIllIIIllllIIl[294] + (this.fovSetting - 70.0f) / 40.0f);
            printWriter.println(GameSettings.llIllIIIllllIIl[295] + this.gammaSetting);
            printWriter.println(GameSettings.llIllIIIllllIIl[296] + this.saturation);
            printWriter.println(GameSettings.llIllIIIllllIIl[297] + GameSettings.renderDistanceChunks);
            printWriter.println(GameSettings.llIllIIIllllIIl[298] + this.guiScale);
            printWriter.println(GameSettings.llIllIIIllllIIl[299] + this.particleSetting);
            printWriter.println(GameSettings.llIllIIIllllIIl[300] + this.viewBobbing);
            printWriter.println(GameSettings.llIllIIIllllIIl[301] + this.anaglyph);
            printWriter.println(GameSettings.llIllIIIllllIIl[302] + this.limitFramerate);
            printWriter.println(GameSettings.llIllIIIllllIIl[303] + this.fboEnable);
            printWriter.println(GameSettings.llIllIIIllllIIl[304] + this.difficulty.getDifficultyId());
            printWriter.println(GameSettings.llIllIIIllllIIl[305] + this.fancyGraphics);
            printWriter.println(GameSettings.llIllIIIllllIIl[306] + this.ambientOcclusion);
            printWriter.println(GameSettings.llIllIIIllllIIl[307] + this.clouds);
            printWriter.println(GameSettings.llIllIIIllllIIl[308] + GameSettings.gson.toJson(this.resourcePacks));
            printWriter.println(GameSettings.llIllIIIllllIIl[309] + this.lastServer);
            printWriter.println(GameSettings.llIllIIIllllIIl[310] + GameSettings.language);
            printWriter.println(GameSettings.llIllIIIllllIIl[311] + GameSettings.chatVisibility.getChatVisibility());
            printWriter.println(GameSettings.llIllIIIllllIIl[312] + GameSettings.chatColours);
            printWriter.println(GameSettings.llIllIIIllllIIl[313] + this.chatLinks);
            printWriter.println(GameSettings.llIllIIIllllIIl[314] + this.chatLinksPrompt);
            printWriter.println(GameSettings.llIllIIIllllIIl[315] + this.chatOpacity);
            printWriter.println(GameSettings.llIllIIIllllIIl[316] + this.snooperEnabled);
            printWriter.println(GameSettings.llIllIIIllllIIl[317] + this.fullScreen);
            printWriter.println(GameSettings.llIllIIIllllIIl[318] + this.enableVsync);
            printWriter.println(GameSettings.llIllIIIllllIIl[319] + this.field_178881_t);
            printWriter.println(GameSettings.llIllIIIllllIIl[320] + this.hideServerAddress);
            printWriter.println(GameSettings.llIllIIIllllIIl[321] + this.advancedItemTooltips);
            printWriter.println(GameSettings.llIllIIIllllIIl[322] + this.pauseOnLostFocus);
            printWriter.println(GameSettings.llIllIIIllllIIl[323] + this.touchscreen);
            printWriter.println(GameSettings.llIllIIIllllIIl[324] + this.overrideWidth);
            printWriter.println(GameSettings.llIllIIIllllIIl[325] + this.overrideHeight);
            printWriter.println(GameSettings.llIllIIIllllIIl[326] + this.heldItemTooltips);
            printWriter.println(GameSettings.llIllIIIllllIIl[327] + this.chatHeightFocused);
            printWriter.println(GameSettings.llIllIIIllllIIl[328] + this.chatHeightUnfocused);
            printWriter.println(GameSettings.llIllIIIllllIIl[329] + this.chatScale);
            printWriter.println(GameSettings.llIllIIIllllIIl[330] + this.chatWidth);
            printWriter.println(GameSettings.llIllIIIllllIIl[331] + this.showInventoryAchievementHint);
            printWriter.println(GameSettings.llIllIIIllllIIl[332] + this.mipmapLevels);
            printWriter.println(GameSettings.llIllIIIllllIIl[333] + this.streamBytesPerPixel);
            printWriter.println(GameSettings.llIllIIIllllIIl[334] + this.streamMicVolume);
            printWriter.println(GameSettings.llIllIIIllllIIl[335] + this.streamGameVolume);
            printWriter.println(GameSettings.llIllIIIllllIIl[336] + this.streamKbps);
            printWriter.println(GameSettings.llIllIIIllllIIl[337] + this.streamFps);
            printWriter.println(GameSettings.llIllIIIllllIIl[338] + this.streamCompression);
            printWriter.println(GameSettings.llIllIIIllllIIl[339] + this.streamSendMetadata);
            printWriter.println(GameSettings.llIllIIIllllIIl[340] + this.streamPreferredServer);
            printWriter.println(GameSettings.llIllIIIllllIIl[341] + this.streamChatEnabled);
            printWriter.println(GameSettings.llIllIIIllllIIl[342] + this.streamChatUserFilter);
            printWriter.println(GameSettings.llIllIIIllllIIl[343] + this.streamMicToggleBehavior);
            printWriter.println(GameSettings.llIllIIIllllIIl[344] + this.forceUnicodeFont);
            printWriter.println(GameSettings.llIllIIIllllIIl[345] + this.field_178880_u);
            printWriter.println(GameSettings.llIllIIIllllIIl[346] + this.field_178879_v);
            for (final KeyBinding keyBinding : this.keyBindings) {
                printWriter.println(GameSettings.llIllIIIllllIIl[347] + keyBinding.getKeyDescription() + GameSettings.llIllIIIllllIIl[348] + keyBinding.getKeyCode());
            }
            for (final SoundCategory soundCategory : SoundCategory.values()) {
                printWriter.println(GameSettings.llIllIIIllllIIl[349] + soundCategory.getCategoryName() + GameSettings.llIllIIIllllIIl[350] + this.getSoundLevel(soundCategory));
            }
            for (final EnumPlayerModelParts enumPlayerModelParts : EnumPlayerModelParts.values()) {
                printWriter.println(GameSettings.llIllIIIllllIIl[351] + enumPlayerModelParts.func_179329_c() + GameSettings.llIllIIIllllIIl[352] + GameSettings.field_178882_aU.contains(enumPlayerModelParts));
            }
            printWriter.close();
        }
        catch (Exception ex) {
            GameSettings.logger.error(GameSettings.llIllIIIllllIIl[353], ex);
        }
        this.saveOfOptions();
        sendSettingsToServer();
    }
    
    public float getSoundLevel(final SoundCategory soundCategory) {
        return this.mapSoundLevels.containsKey(soundCategory) ? this.mapSoundLevels.get(soundCategory) : 1.0f;
    }
    
    public void setSoundLevel(final SoundCategory soundCategory, final float n) {
        Minecraft.getSoundHandler().setSoundLevel(soundCategory, n);
        this.mapSoundLevels.put(soundCategory, n);
    }
    
    public static void sendSettingsToServer() {
        if (Minecraft.thePlayer != null) {
            int n = 0;
            final Iterator<EnumPlayerModelParts> iterator = GameSettings.field_178882_aU.iterator();
            while (iterator.hasNext()) {
                n |= iterator.next().func_179327_a();
            }
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C15PacketClientSettings(GameSettings.language, GameSettings.renderDistanceChunks, GameSettings.chatVisibility, GameSettings.chatColours, n));
        }
    }
    
    public static Set func_178876_d() {
        return ImmutableSet.copyOf(GameSettings.field_178882_aU);
    }
    
    public void func_178878_a(final EnumPlayerModelParts enumPlayerModelParts, final boolean b) {
        if (b) {
            GameSettings.field_178882_aU.add(enumPlayerModelParts);
        }
        else {
            GameSettings.field_178882_aU.remove(enumPlayerModelParts);
        }
        sendSettingsToServer();
    }
    
    public static void func_178877_a(final EnumPlayerModelParts enumPlayerModelParts) {
        if (!func_178876_d().contains(enumPlayerModelParts)) {
            GameSettings.field_178882_aU.add(enumPlayerModelParts);
        }
        else {
            GameSettings.field_178882_aU.remove(enumPlayerModelParts);
        }
        sendSettingsToServer();
    }
    
    public boolean shouldRenderClouds() {
        return GameSettings.renderDistanceChunks >= 4 && this.clouds;
    }
    
    private void setOptionFloatValueOF(final Options options, final float n) {
        if (options == Options.CLOUD_HEIGHT) {
            this.ofCloudsHeight = n;
            this.mc.renderGlobal.resetClouds();
        }
        if (options == Options.AO_LEVEL) {
            this.ofAoLevel = n;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.AA_LEVEL) {
            final int n2 = (int)n;
            if (n2 > 0 && Config.isShaders()) {
                Config.showGuiMessage(Lang.get(GameSettings.llIllIIIllllIIl[354]), Lang.get(GameSettings.llIllIIIllllIIl[355]));
                return;
            }
            final int[] array = { 0, 2, 4, 6, 8, 12, 16 };
            this.ofAaLevel = 0;
            for (int i = 0; i < array.length; ++i) {
                if (n2 >= array[i]) {
                    this.ofAaLevel = array[i];
                }
            }
            this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
        }
        if (options == Options.AF_LEVEL) {
            final int n3 = (int)n;
            if (n3 > 1 && Config.isShaders()) {
                Config.showGuiMessage(Lang.get(GameSettings.llIllIIIllllIIl[356]), Lang.get(GameSettings.llIllIIIllllIIl[357]));
                return;
            }
            this.ofAfLevel = 1;
            while (this.ofAfLevel * 2 <= n3) {
                this.ofAfLevel *= 2;
            }
            this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
            this.mc.refreshResources();
        }
        if (options == Options.MIPMAP_TYPE) {
            this.ofMipmapType = Config.limit((int)n, 0, 3);
            this.mc.refreshResources();
        }
    }
    
    private void setOptionValueOF(final Options options, final int n) {
        if (options == Options.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    this.ofFogType = 2;
                    if (!Config.isFancyFogAvailable()) {
                        this.ofFogType = 3;
                        break;
                    }
                    break;
                }
                case 2: {
                    this.ofFogType = 3;
                    break;
                }
                case 3: {
                    this.ofFogType = 1;
                    break;
                }
                default: {
                    this.ofFogType = 1;
                    break;
                }
            }
        }
        if (options == Options.FOG_START) {
            this.ofFogStart += 0.2f;
            if (this.ofFogStart > 0.81f) {
                this.ofFogStart = 0.2f;
            }
        }
        if (options == Options.SMOOTH_FPS) {
            this.ofSmoothFps = !this.ofSmoothFps;
        }
        if (options == Options.SMOOTH_WORLD) {
            this.ofSmoothWorld = !this.ofSmoothWorld;
            Config.updateThreadPriorities();
        }
        if (options == Options.CLOUDS) {
            ++this.ofClouds;
            if (this.ofClouds > 3) {
                this.ofClouds = 0;
            }
            this.updateRenderClouds();
            this.mc.renderGlobal.resetClouds();
        }
        if (options == Options.TREES) {
            this.ofTrees = nextValue(this.ofTrees, GameSettings.OF_TREES_VALUES);
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.DROPPED_ITEMS) {
            ++this.ofDroppedItems;
            if (this.ofDroppedItems > 2) {
                this.ofDroppedItems = 0;
            }
        }
        if (options == Options.RAIN) {
            ++this.ofRain;
            if (this.ofRain > 3) {
                this.ofRain = 0;
            }
        }
        if (options == Options.ANIMATED_WATER) {
            ++this.ofAnimatedWater;
            if (this.ofAnimatedWater == 1) {
                ++this.ofAnimatedWater;
            }
            if (this.ofAnimatedWater > 2) {
                this.ofAnimatedWater = 0;
            }
        }
        if (options == Options.ANIMATED_LAVA) {
            ++this.ofAnimatedLava;
            if (this.ofAnimatedLava == 1) {
                ++this.ofAnimatedLava;
            }
            if (this.ofAnimatedLava > 2) {
                this.ofAnimatedLava = 0;
            }
        }
        if (options == Options.ANIMATED_FIRE) {
            this.ofAnimatedFire = !this.ofAnimatedFire;
        }
        if (options == Options.ANIMATED_PORTAL) {
            this.ofAnimatedPortal = !this.ofAnimatedPortal;
        }
        if (options == Options.ANIMATED_REDSTONE) {
            this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
        }
        if (options == Options.ANIMATED_EXPLOSION) {
            this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
        }
        if (options == Options.ANIMATED_FLAME) {
            this.ofAnimatedFlame = !this.ofAnimatedFlame;
        }
        if (options == Options.ANIMATED_SMOKE) {
            this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
        }
        if (options == Options.VOID_PARTICLES) {
            this.ofVoidParticles = !this.ofVoidParticles;
        }
        if (options == Options.WATER_PARTICLES) {
            this.ofWaterParticles = !this.ofWaterParticles;
        }
        if (options == Options.PORTAL_PARTICLES) {
            this.ofPortalParticles = !this.ofPortalParticles;
        }
        if (options == Options.POTION_PARTICLES) {
            this.ofPotionParticles = !this.ofPotionParticles;
        }
        if (options == Options.FIREWORK_PARTICLES) {
            this.ofFireworkParticles = !this.ofFireworkParticles;
        }
        if (options == Options.DRIPPING_WATER_LAVA) {
            this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
        }
        if (options == Options.ANIMATED_TERRAIN) {
            this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
        }
        if (options == Options.ANIMATED_TEXTURES) {
            this.ofAnimatedTextures = !this.ofAnimatedTextures;
        }
        if (options == Options.RAIN_SPLASH) {
            this.ofRainSplash = !this.ofRainSplash;
        }
        if (options == Options.LAGOMETER) {
            this.ofLagometer = !this.ofLagometer;
        }
        if (options == Options.SHOW_FPS) {
            this.ofShowFps = !this.ofShowFps;
        }
        if (options == Options.AUTOSAVE_TICKS) {
            this.ofAutoSaveTicks *= 10;
            if (this.ofAutoSaveTicks > 40000) {
                this.ofAutoSaveTicks = 40;
            }
        }
        if (options == Options.BETTER_GRASS) {
            ++this.ofBetterGrass;
            if (this.ofBetterGrass > 3) {
                this.ofBetterGrass = 1;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.CONNECTED_TEXTURES) {
            ++this.ofConnectedTextures;
            if (this.ofConnectedTextures > 3) {
                this.ofConnectedTextures = 1;
            }
            if (this.ofConnectedTextures != 2) {
                this.mc.refreshResources();
            }
        }
        if (options == Options.WEATHER) {
            this.ofWeather = !this.ofWeather;
        }
        if (options == Options.SKY) {
            this.ofSky = !this.ofSky;
        }
        if (options == Options.STARS) {
            this.ofStars = !this.ofStars;
        }
        if (options == Options.SUN_MOON) {
            this.ofSunMoon = !this.ofSunMoon;
        }
        if (options == Options.VIGNETTE) {
            ++this.ofVignette;
            if (this.ofVignette > 2) {
                this.ofVignette = 0;
            }
        }
        if (options == Options.CHUNK_UPDATES) {
            ++this.ofChunkUpdates;
            if (this.ofChunkUpdates > 5) {
                this.ofChunkUpdates = 1;
            }
        }
        if (options == Options.CHUNK_UPDATES_DYNAMIC) {
            this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
        }
        if (options == Options.TIME) {
            ++this.ofTime;
            if (this.ofTime > 2) {
                this.ofTime = 0;
            }
        }
        if (options == Options.CLEAR_WATER) {
            this.ofClearWater = !this.ofClearWater;
            this.updateWaterOpacity();
        }
        if (options == Options.PROFILER) {
            this.ofProfiler = !this.ofProfiler;
        }
        if (options == Options.BETTER_SNOW) {
            this.ofBetterSnow = !this.ofBetterSnow;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.SWAMP_COLORS) {
            this.ofSwampColors = !this.ofSwampColors;
            CustomColors.updateUseDefaultGrassFoliageColors();
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.RANDOM_MOBS) {
            this.ofRandomMobs = !this.ofRandomMobs;
            RandomMobs.resetTextures();
        }
        if (options == Options.SMOOTH_BIOMES) {
            this.ofSmoothBiomes = !this.ofSmoothBiomes;
            CustomColors.updateUseDefaultGrassFoliageColors();
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.CUSTOM_FONTS) {
            this.ofCustomFonts = !this.ofCustomFonts;
            Minecraft.fontRendererObj.onResourceManagerReload(Config.getResourceManager());
            this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
        }
        if (options == Options.CUSTOM_COLORS) {
            this.ofCustomColors = !this.ofCustomColors;
            CustomColors.update();
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.CUSTOM_ITEMS) {
            this.ofCustomItems = !this.ofCustomItems;
            this.mc.refreshResources();
        }
        if (options == Options.CUSTOM_SKY) {
            this.ofCustomSky = !this.ofCustomSky;
            CustomSky.update();
        }
        if (options == Options.SHOW_CAPES) {
            this.ofShowCapes = !this.ofShowCapes;
        }
        if (options == Options.NATURAL_TEXTURES) {
            this.ofNaturalTextures = !this.ofNaturalTextures;
            NaturalTextures.update();
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.FAST_MATH) {
            this.ofFastMath = !this.ofFastMath;
            MathHelper.fastMath = this.ofFastMath;
        }
        if (options == Options.FAST_RENDER) {
            if (!this.ofFastRender && Config.isShaders()) {
                Config.showGuiMessage(Lang.get(GameSettings.llIllIIIllllIIl[358]), Lang.get(GameSettings.llIllIIIllllIIl[359]));
                return;
            }
            this.ofFastRender = !this.ofFastRender;
            if (this.ofFastRender) {
                this.mc.entityRenderer.stopUseShader();
            }
            Config.updateFramebufferSize();
        }
        if (options == Options.TRANSLUCENT_BLOCKS) {
            if (this.ofTranslucentBlocks == 0) {
                this.ofTranslucentBlocks = 1;
            }
            else if (this.ofTranslucentBlocks == 1) {
                this.ofTranslucentBlocks = 2;
            }
            else if (this.ofTranslucentBlocks == 2) {
                this.ofTranslucentBlocks = 0;
            }
            else {
                this.ofTranslucentBlocks = 0;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.LAZY_CHUNK_LOADING) {
            this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
            Config.updateAvailableProcessors();
            if (!Config.isSingleProcessor()) {
                this.ofLazyChunkLoading = false;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.FULLSCREEN_MODE) {
            final List<String> list = Arrays.asList(Config.getFullscreenModes());
            if (this.ofFullscreenMode.equals(GameSettings.llIllIIIllllIIl[360])) {
                this.ofFullscreenMode = list.get(0);
            }
            else {
                int index = list.indexOf(this.ofFullscreenMode);
                if (index < 0) {
                    this.ofFullscreenMode = GameSettings.llIllIIIllllIIl[361];
                }
                else if (++index >= list.size()) {
                    this.ofFullscreenMode = GameSettings.llIllIIIllllIIl[362];
                }
                else {
                    this.ofFullscreenMode = list.get(index);
                }
            }
        }
        if (options == Options.DYNAMIC_FOV) {
            this.ofDynamicFov = !this.ofDynamicFov;
        }
        if (options == Options.DYNAMIC_LIGHTS) {
            this.ofDynamicLights = nextValue(this.ofDynamicLights, GameSettings.OF_DYNAMIC_LIGHTS);
            DynamicLights.removeLights(this.mc.renderGlobal);
        }
        if (options == Options.HELD_ITEM_TOOLTIPS) {
            this.heldItemTooltips = !this.heldItemTooltips;
        }
    }
    
    private String getKeyBindingOF(final Options options) {
        String s = String.valueOf(I18n.format(options.getEnumString(), new Object[0])) + GameSettings.llIllIIIllllIIl[363];
        if (s == null) {
            s = options.getEnumString();
        }
        if (options == Options.RENDER_DISTANCE) {
            final int n = (int)this.getOptionFloatValue(options);
            String s2 = I18n.format(GameSettings.llIllIIIllllIIl[364], new Object[0]);
            int n2 = 2;
            if (n >= 4) {
                s2 = I18n.format(GameSettings.llIllIIIllllIIl[365], new Object[0]);
                n2 = 4;
            }
            if (n >= 8) {
                s2 = I18n.format(GameSettings.llIllIIIllllIIl[366], new Object[0]);
                n2 = 8;
            }
            if (n >= 16) {
                s2 = I18n.format(GameSettings.llIllIIIllllIIl[367], new Object[0]);
                n2 = 16;
            }
            if (n >= 32) {
                s2 = Lang.get(GameSettings.llIllIIIllllIIl[368]);
                n2 = 32;
            }
            final int n3 = GameSettings.renderDistanceChunks - n2;
            String string = s2;
            if (n3 > 0) {
                string = String.valueOf(s2) + GameSettings.llIllIIIllllIIl[369];
            }
            return String.valueOf(s) + n + GameSettings.llIllIIIllllIIl[370] + string;
        }
        if (options == Options.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    return String.valueOf(s) + Lang.getFast();
                }
                case 2: {
                    return String.valueOf(s) + Lang.getFancy();
                }
                case 3: {
                    return String.valueOf(s) + Lang.getOff();
                }
                default: {
                    return String.valueOf(s) + Lang.getOff();
                }
            }
        }
        else {
            if (options == Options.FOG_START) {
                return String.valueOf(s) + this.ofFogStart;
            }
            if (options == Options.MIPMAP_TYPE) {
                switch (this.ofMipmapType) {
                    case 0: {
                        return String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[371]);
                    }
                    case 1: {
                        return String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[372]);
                    }
                    case 2: {
                        return String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[373]);
                    }
                    case 3: {
                        return String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[374]);
                    }
                    default: {
                        return String.valueOf(s) + GameSettings.llIllIIIllllIIl[375];
                    }
                }
            }
            else {
                if (options == Options.SMOOTH_FPS) {
                    return this.ofSmoothFps ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                }
                if (options == Options.SMOOTH_WORLD) {
                    return this.ofSmoothWorld ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                }
                if (options == Options.CLOUDS) {
                    switch (this.ofClouds) {
                        case 1: {
                            return String.valueOf(s) + Lang.getFast();
                        }
                        case 2: {
                            return String.valueOf(s) + Lang.getFancy();
                        }
                        case 3: {
                            return String.valueOf(s) + Lang.getOff();
                        }
                        default: {
                            return String.valueOf(s) + Lang.getDefault();
                        }
                    }
                }
                else if (options == Options.TREES) {
                    switch (this.ofTrees) {
                        case 1: {
                            return String.valueOf(s) + Lang.getFast();
                        }
                        case 2: {
                            return String.valueOf(s) + Lang.getFancy();
                        }
                        default: {
                            return String.valueOf(s) + Lang.getDefault();
                        }
                        case 4: {
                            return String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[376]);
                        }
                    }
                }
                else if (options == Options.DROPPED_ITEMS) {
                    switch (this.ofDroppedItems) {
                        case 1: {
                            return String.valueOf(s) + Lang.getFast();
                        }
                        case 2: {
                            return String.valueOf(s) + Lang.getFancy();
                        }
                        default: {
                            return String.valueOf(s) + Lang.getDefault();
                        }
                    }
                }
                else if (options == Options.RAIN) {
                    switch (this.ofRain) {
                        case 1: {
                            return String.valueOf(s) + Lang.getFast();
                        }
                        case 2: {
                            return String.valueOf(s) + Lang.getFancy();
                        }
                        case 3: {
                            return String.valueOf(s) + Lang.getOff();
                        }
                        default: {
                            return String.valueOf(s) + Lang.getDefault();
                        }
                    }
                }
                else if (options == Options.ANIMATED_WATER) {
                    switch (this.ofAnimatedWater) {
                        case 1: {
                            return String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[377]);
                        }
                        case 2: {
                            return String.valueOf(s) + Lang.getOff();
                        }
                        default: {
                            return String.valueOf(s) + Lang.getOn();
                        }
                    }
                }
                else if (options == Options.ANIMATED_LAVA) {
                    switch (this.ofAnimatedLava) {
                        case 1: {
                            return String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[378]);
                        }
                        case 2: {
                            return String.valueOf(s) + Lang.getOff();
                        }
                        default: {
                            return String.valueOf(s) + Lang.getOn();
                        }
                    }
                }
                else {
                    if (options == Options.ANIMATED_FIRE) {
                        return this.ofAnimatedFire ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.ANIMATED_PORTAL) {
                        return this.ofAnimatedPortal ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.ANIMATED_REDSTONE) {
                        return this.ofAnimatedRedstone ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.ANIMATED_EXPLOSION) {
                        return this.ofAnimatedExplosion ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.ANIMATED_FLAME) {
                        return this.ofAnimatedFlame ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.ANIMATED_SMOKE) {
                        return this.ofAnimatedSmoke ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.VOID_PARTICLES) {
                        return this.ofVoidParticles ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.WATER_PARTICLES) {
                        return this.ofWaterParticles ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.PORTAL_PARTICLES) {
                        return this.ofPortalParticles ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.POTION_PARTICLES) {
                        return this.ofPotionParticles ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.FIREWORK_PARTICLES) {
                        return this.ofFireworkParticles ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.DRIPPING_WATER_LAVA) {
                        return this.ofDrippingWaterLava ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.ANIMATED_TERRAIN) {
                        return this.ofAnimatedTerrain ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.ANIMATED_TEXTURES) {
                        return this.ofAnimatedTextures ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.RAIN_SPLASH) {
                        return this.ofRainSplash ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.LAGOMETER) {
                        return this.ofLagometer ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.SHOW_FPS) {
                        return this.ofShowFps ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                    }
                    if (options == Options.AUTOSAVE_TICKS) {
                        return (this.ofAutoSaveTicks <= 40) ? (String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[379])) : ((this.ofAutoSaveTicks <= 400) ? (String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[380])) : ((this.ofAutoSaveTicks <= 4000) ? (String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[381])) : (String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[382]))));
                    }
                    if (options == Options.BETTER_GRASS) {
                        switch (this.ofBetterGrass) {
                            case 1: {
                                return String.valueOf(s) + Lang.getFast();
                            }
                            case 2: {
                                return String.valueOf(s) + Lang.getFancy();
                            }
                            default: {
                                return String.valueOf(s) + Lang.getOff();
                            }
                        }
                    }
                    else if (options == Options.CONNECTED_TEXTURES) {
                        switch (this.ofConnectedTextures) {
                            case 1: {
                                return String.valueOf(s) + Lang.getFast();
                            }
                            case 2: {
                                return String.valueOf(s) + Lang.getFancy();
                            }
                            default: {
                                return String.valueOf(s) + Lang.getOff();
                            }
                        }
                    }
                    else {
                        if (options == Options.WEATHER) {
                            return this.ofWeather ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                        }
                        if (options == Options.SKY) {
                            return this.ofSky ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                        }
                        if (options == Options.STARS) {
                            return this.ofStars ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                        }
                        if (options == Options.SUN_MOON) {
                            return this.ofSunMoon ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                        }
                        if (options == Options.VIGNETTE) {
                            switch (this.ofVignette) {
                                case 1: {
                                    return String.valueOf(s) + Lang.getFast();
                                }
                                case 2: {
                                    return String.valueOf(s) + Lang.getFancy();
                                }
                                default: {
                                    return String.valueOf(s) + Lang.getDefault();
                                }
                            }
                        }
                        else {
                            if (options == Options.CHUNK_UPDATES) {
                                return String.valueOf(s) + this.ofChunkUpdates;
                            }
                            if (options == Options.CHUNK_UPDATES_DYNAMIC) {
                                return this.ofChunkUpdatesDynamic ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.TIME) {
                                return (this.ofTime == 1) ? (String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[383])) : ((this.ofTime == 2) ? (String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[384])) : (String.valueOf(s) + Lang.getDefault()));
                            }
                            if (options == Options.CLEAR_WATER) {
                                return this.ofClearWater ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.AA_LEVEL) {
                                String string2 = GameSettings.llIllIIIllllIIl[385];
                                if (this.ofAaLevel != Config.getAntialiasingLevel()) {
                                    string2 = GameSettings.llIllIIIllllIIl[386] + Lang.get(GameSettings.llIllIIIllllIIl[387]) + GameSettings.llIllIIIllllIIl[388];
                                }
                                return (this.ofAaLevel == 0) ? (String.valueOf(s) + Lang.getOff() + string2) : (String.valueOf(s) + this.ofAaLevel + string2);
                            }
                            if (options == Options.AF_LEVEL) {
                                return (this.ofAfLevel == 1) ? (String.valueOf(s) + Lang.getOff()) : (String.valueOf(s) + this.ofAfLevel);
                            }
                            if (options == Options.PROFILER) {
                                return this.ofProfiler ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.BETTER_SNOW) {
                                return this.ofBetterSnow ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.SWAMP_COLORS) {
                                return this.ofSwampColors ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.RANDOM_MOBS) {
                                return this.ofRandomMobs ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.SMOOTH_BIOMES) {
                                return this.ofSmoothBiomes ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.CUSTOM_FONTS) {
                                return this.ofCustomFonts ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.CUSTOM_COLORS) {
                                return this.ofCustomColors ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.CUSTOM_SKY) {
                                return this.ofCustomSky ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.SHOW_CAPES) {
                                return this.ofShowCapes ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.CUSTOM_ITEMS) {
                                return this.ofCustomItems ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.NATURAL_TEXTURES) {
                                return this.ofNaturalTextures ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.FAST_MATH) {
                                return this.ofFastMath ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.FAST_RENDER) {
                                return this.ofFastRender ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.TRANSLUCENT_BLOCKS) {
                                return (this.ofTranslucentBlocks == 1) ? (String.valueOf(s) + Lang.getFast()) : ((this.ofTranslucentBlocks == 2) ? (String.valueOf(s) + Lang.getFancy()) : (String.valueOf(s) + Lang.getDefault()));
                            }
                            if (options == Options.LAZY_CHUNK_LOADING) {
                                return this.ofLazyChunkLoading ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.DYNAMIC_FOV) {
                                return this.ofDynamicFov ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.DYNAMIC_LIGHTS) {
                                return String.valueOf(s) + getTranslation(GameSettings.KEYS_DYNAMIC_LIGHTS, indexOf(this.ofDynamicLights, GameSettings.OF_DYNAMIC_LIGHTS));
                            }
                            if (options == Options.FULLSCREEN_MODE) {
                                return this.ofFullscreenMode.equals(GameSettings.llIllIIIllllIIl[389]) ? (String.valueOf(s) + Lang.getDefault()) : (String.valueOf(s) + this.ofFullscreenMode);
                            }
                            if (options == Options.HELD_ITEM_TOOLTIPS) {
                                return this.heldItemTooltips ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
                            }
                            if (options == Options.FRAMERATE_LIMIT) {
                                final float optionFloatValue = this.getOptionFloatValue(options);
                                return (optionFloatValue == 0.0f) ? (String.valueOf(s) + Lang.get(GameSettings.llIllIIIllllIIl[390])) : ((optionFloatValue == Options.access$2(options)) ? (String.valueOf(s) + I18n.format(GameSettings.llIllIIIllllIIl[391], new Object[0])) : (String.valueOf(s) + (int)optionFloatValue + GameSettings.llIllIIIllllIIl[392]));
                            }
                            return null;
                        }
                    }
                }
            }
        }
    }
    
    public void loadOfOptions() {
        try {
            File file = this.optionsFileOF;
            if (!file.exists()) {
                file = this.optionsFile;
            }
            if (!file.exists()) {
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            final String s = GameSettings.llIllIIIllllIIl[393];
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    final String[] split = line.split(GameSettings.llIllIIIllllIIl[394]);
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[395]) && split.length >= 2) {
                        GameSettings.renderDistanceChunks = Integer.valueOf(split[1]);
                        GameSettings.renderDistanceChunks = Config.limit(GameSettings.renderDistanceChunks, 2, 32);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[396]) && split.length >= 2) {
                        this.ofFogType = Integer.valueOf(split[1]);
                        this.ofFogType = Config.limit(this.ofFogType, 1, 3);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[397]) && split.length >= 2) {
                        this.ofFogStart = Float.valueOf(split[1]);
                        if (this.ofFogStart < 0.2f) {
                            this.ofFogStart = 0.2f;
                        }
                        if (this.ofFogStart > 0.81f) {
                            this.ofFogStart = 0.8f;
                        }
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[398]) && split.length >= 2) {
                        this.ofMipmapType = Integer.valueOf(split[1]);
                        this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[399]) && split.length >= 2) {
                        this.ofOcclusionFancy = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[400]) && split.length >= 2) {
                        this.ofSmoothFps = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[401]) && split.length >= 2) {
                        this.ofSmoothWorld = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[402]) && split.length >= 2) {
                        this.ofAoLevel = Float.valueOf(split[1]);
                        this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0f, 1.0f);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[403]) && split.length >= 2) {
                        this.ofClouds = Integer.valueOf(split[1]);
                        this.ofClouds = Config.limit(this.ofClouds, 0, 3);
                        this.updateRenderClouds();
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[404]) && split.length >= 2) {
                        this.ofCloudsHeight = Float.valueOf(split[1]);
                        this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0f, 1.0f);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[405]) && split.length >= 2) {
                        this.ofTrees = Integer.valueOf(split[1]);
                        this.ofTrees = limit(this.ofTrees, GameSettings.OF_TREES_VALUES);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[406]) && split.length >= 2) {
                        this.ofDroppedItems = Integer.valueOf(split[1]);
                        this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[407]) && split.length >= 2) {
                        this.ofRain = Integer.valueOf(split[1]);
                        this.ofRain = Config.limit(this.ofRain, 0, 3);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[408]) && split.length >= 2) {
                        this.ofAnimatedWater = Integer.valueOf(split[1]);
                        this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[409]) && split.length >= 2) {
                        this.ofAnimatedLava = Integer.valueOf(split[1]);
                        this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[410]) && split.length >= 2) {
                        this.ofAnimatedFire = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[411]) && split.length >= 2) {
                        this.ofAnimatedPortal = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[412]) && split.length >= 2) {
                        this.ofAnimatedRedstone = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[413]) && split.length >= 2) {
                        this.ofAnimatedExplosion = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[414]) && split.length >= 2) {
                        this.ofAnimatedFlame = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[415]) && split.length >= 2) {
                        this.ofAnimatedSmoke = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[416]) && split.length >= 2) {
                        this.ofVoidParticles = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[417]) && split.length >= 2) {
                        this.ofWaterParticles = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[418]) && split.length >= 2) {
                        this.ofPortalParticles = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[419]) && split.length >= 2) {
                        this.ofPotionParticles = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[420]) && split.length >= 2) {
                        this.ofFireworkParticles = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[421]) && split.length >= 2) {
                        this.ofDrippingWaterLava = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[422]) && split.length >= 2) {
                        this.ofAnimatedTerrain = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[423]) && split.length >= 2) {
                        this.ofAnimatedTextures = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[424]) && split.length >= 2) {
                        this.ofRainSplash = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[425]) && split.length >= 2) {
                        this.ofLagometer = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[426]) && split.length >= 2) {
                        this.ofShowFps = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[427]) && split.length >= 2) {
                        this.ofAutoSaveTicks = Integer.valueOf(split[1]);
                        this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[428]) && split.length >= 2) {
                        this.ofBetterGrass = Integer.valueOf(split[1]);
                        this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[429]) && split.length >= 2) {
                        this.ofConnectedTextures = Integer.valueOf(split[1]);
                        this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[430]) && split.length >= 2) {
                        this.ofWeather = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[431]) && split.length >= 2) {
                        this.ofSky = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[432]) && split.length >= 2) {
                        this.ofStars = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[433]) && split.length >= 2) {
                        this.ofSunMoon = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[434]) && split.length >= 2) {
                        this.ofVignette = Integer.valueOf(split[1]);
                        this.ofVignette = Config.limit(this.ofVignette, 0, 2);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[435]) && split.length >= 2) {
                        this.ofChunkUpdates = Integer.valueOf(split[1]);
                        this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[436]) && split.length >= 2) {
                        this.ofChunkUpdatesDynamic = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[437]) && split.length >= 2) {
                        this.ofTime = Integer.valueOf(split[1]);
                        this.ofTime = Config.limit(this.ofTime, 0, 2);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[438]) && split.length >= 2) {
                        this.ofClearWater = Boolean.valueOf(split[1]);
                        this.updateWaterOpacity();
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[439]) && split.length >= 2) {
                        this.ofAaLevel = Integer.valueOf(split[1]);
                        this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[440]) && split.length >= 2) {
                        this.ofAfLevel = Integer.valueOf(split[1]);
                        this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[441]) && split.length >= 2) {
                        this.ofProfiler = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[442]) && split.length >= 2) {
                        this.ofBetterSnow = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[443]) && split.length >= 2) {
                        this.ofSwampColors = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[444]) && split.length >= 2) {
                        this.ofRandomMobs = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[445]) && split.length >= 2) {
                        this.ofSmoothBiomes = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[446]) && split.length >= 2) {
                        this.ofCustomFonts = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[447]) && split.length >= 2) {
                        this.ofCustomColors = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[448]) && split.length >= 2) {
                        this.ofCustomItems = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[449]) && split.length >= 2) {
                        this.ofCustomSky = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[450]) && split.length >= 2) {
                        this.ofShowCapes = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[451]) && split.length >= 2) {
                        this.ofNaturalTextures = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[452]) && split.length >= 2) {
                        this.ofLazyChunkLoading = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[453]) && split.length >= 2) {
                        this.ofDynamicFov = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[454]) && split.length >= 2) {
                        this.ofDynamicLights = Integer.valueOf(split[1]);
                        this.ofDynamicLights = limit(this.ofDynamicLights, GameSettings.OF_DYNAMIC_LIGHTS);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[455]) && split.length >= 2) {
                        this.ofFullscreenMode = split[1];
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[456]) && split.length >= 2) {
                        this.ofFastMath = Boolean.valueOf(split[1]);
                        MathHelper.fastMath = this.ofFastMath;
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[457]) && split.length >= 2) {
                        this.ofFastRender = Boolean.valueOf(split[1]);
                    }
                    if (split[0].equals(GameSettings.llIllIIIllllIIl[458]) && split.length >= 2) {
                        this.ofTranslucentBlocks = Integer.valueOf(split[1]);
                        this.ofTranslucentBlocks = Config.limit(this.ofTranslucentBlocks, 0, 2);
                    }
                    if (!split[0].equals(GameSettings.llIllIIIllllIIl[459] + this.ofKeyBindZoom.getKeyDescription())) {
                        continue;
                    }
                    this.ofKeyBindZoom.setKeyCode(Integer.parseInt(split[1]));
                }
                catch (Exception ex) {
                    Config.dbg(GameSettings.llIllIIIllllIIl[460] + line);
                    ex.printStackTrace();
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedReader.close();
        }
        catch (Exception ex2) {
            Config.warn(GameSettings.llIllIIIllllIIl[461]);
            ex2.printStackTrace();
        }
    }
    
    public Set getModelParts() {
        return ImmutableSet.copyOf(this.setModelParts);
    }
    
    public void saveOfOptions() {
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(this.optionsFileOF));
            printWriter.println(GameSettings.llIllIIIllllIIl[462] + GameSettings.renderDistanceChunks);
            printWriter.println(GameSettings.llIllIIIllllIIl[463] + this.ofFogType);
            printWriter.println(GameSettings.llIllIIIllllIIl[464] + this.ofFogStart);
            printWriter.println(GameSettings.llIllIIIllllIIl[465] + this.ofMipmapType);
            printWriter.println(GameSettings.llIllIIIllllIIl[466] + this.ofOcclusionFancy);
            printWriter.println(GameSettings.llIllIIIllllIIl[467] + this.ofSmoothFps);
            printWriter.println(GameSettings.llIllIIIllllIIl[468] + this.ofSmoothWorld);
            printWriter.println(GameSettings.llIllIIIllllIIl[469] + this.ofAoLevel);
            printWriter.println(GameSettings.llIllIIIllllIIl[470] + this.ofClouds);
            printWriter.println(GameSettings.llIllIIIllllIIl[471] + this.ofCloudsHeight);
            printWriter.println(GameSettings.llIllIIIllllIIl[472] + this.ofTrees);
            printWriter.println(GameSettings.llIllIIIllllIIl[473] + this.ofDroppedItems);
            printWriter.println(GameSettings.llIllIIIllllIIl[474] + this.ofRain);
            printWriter.println(GameSettings.llIllIIIllllIIl[475] + this.ofAnimatedWater);
            printWriter.println(GameSettings.llIllIIIllllIIl[476] + this.ofAnimatedLava);
            printWriter.println(GameSettings.llIllIIIllllIIl[477] + this.ofAnimatedFire);
            printWriter.println(GameSettings.llIllIIIllllIIl[478] + this.ofAnimatedPortal);
            printWriter.println(GameSettings.llIllIIIllllIIl[479] + this.ofAnimatedRedstone);
            printWriter.println(GameSettings.llIllIIIllllIIl[480] + this.ofAnimatedExplosion);
            printWriter.println(GameSettings.llIllIIIllllIIl[481] + this.ofAnimatedFlame);
            printWriter.println(GameSettings.llIllIIIllllIIl[482] + this.ofAnimatedSmoke);
            printWriter.println(GameSettings.llIllIIIllllIIl[483] + this.ofVoidParticles);
            printWriter.println(GameSettings.llIllIIIllllIIl[484] + this.ofWaterParticles);
            printWriter.println(GameSettings.llIllIIIllllIIl[485] + this.ofPortalParticles);
            printWriter.println(GameSettings.llIllIIIllllIIl[486] + this.ofPotionParticles);
            printWriter.println(GameSettings.llIllIIIllllIIl[487] + this.ofFireworkParticles);
            printWriter.println(GameSettings.llIllIIIllllIIl[488] + this.ofDrippingWaterLava);
            printWriter.println(GameSettings.llIllIIIllllIIl[489] + this.ofAnimatedTerrain);
            printWriter.println(GameSettings.llIllIIIllllIIl[490] + this.ofAnimatedTextures);
            printWriter.println(GameSettings.llIllIIIllllIIl[491] + this.ofRainSplash);
            printWriter.println(GameSettings.llIllIIIllllIIl[492] + this.ofLagometer);
            printWriter.println(GameSettings.llIllIIIllllIIl[493] + this.ofShowFps);
            printWriter.println(GameSettings.llIllIIIllllIIl[494] + this.ofAutoSaveTicks);
            printWriter.println(GameSettings.llIllIIIllllIIl[495] + this.ofBetterGrass);
            printWriter.println(GameSettings.llIllIIIllllIIl[496] + this.ofConnectedTextures);
            printWriter.println(GameSettings.llIllIIIllllIIl[497] + this.ofWeather);
            printWriter.println(GameSettings.llIllIIIllllIIl[498] + this.ofSky);
            printWriter.println(GameSettings.llIllIIIllllIIl[499] + this.ofStars);
            printWriter.println(GameSettings.llIllIIIllllIIl[500] + this.ofSunMoon);
            printWriter.println(GameSettings.llIllIIIllllIIl[501] + this.ofVignette);
            printWriter.println(GameSettings.llIllIIIllllIIl[502] + this.ofChunkUpdates);
            printWriter.println(GameSettings.llIllIIIllllIIl[503] + this.ofChunkUpdatesDynamic);
            printWriter.println(GameSettings.llIllIIIllllIIl[504] + this.ofTime);
            printWriter.println(GameSettings.llIllIIIllllIIl[505] + this.ofClearWater);
            printWriter.println(GameSettings.llIllIIIllllIIl[506] + this.ofAaLevel);
            printWriter.println(GameSettings.llIllIIIllllIIl[507] + this.ofAfLevel);
            printWriter.println(GameSettings.llIllIIIllllIIl[508] + this.ofProfiler);
            printWriter.println(GameSettings.llIllIIIllllIIl[509] + this.ofBetterSnow);
            printWriter.println(GameSettings.llIllIIIllllIIl[510] + this.ofSwampColors);
            printWriter.println(GameSettings.llIllIIIllllIIl[511] + this.ofRandomMobs);
            printWriter.println(GameSettings.llIllIIIllllIIl[512] + this.ofSmoothBiomes);
            printWriter.println(GameSettings.llIllIIIllllIIl[513] + this.ofCustomFonts);
            printWriter.println(GameSettings.llIllIIIllllIIl[514] + this.ofCustomColors);
            printWriter.println(GameSettings.llIllIIIllllIIl[515] + this.ofCustomItems);
            printWriter.println(GameSettings.llIllIIIllllIIl[516] + this.ofCustomSky);
            printWriter.println(GameSettings.llIllIIIllllIIl[517] + this.ofShowCapes);
            printWriter.println(GameSettings.llIllIIIllllIIl[518] + this.ofNaturalTextures);
            printWriter.println(GameSettings.llIllIIIllllIIl[519] + this.ofLazyChunkLoading);
            printWriter.println(GameSettings.llIllIIIllllIIl[520] + this.ofDynamicFov);
            printWriter.println(GameSettings.llIllIIIllllIIl[521] + this.ofDynamicLights);
            printWriter.println(GameSettings.llIllIIIllllIIl[522] + this.ofFullscreenMode);
            printWriter.println(GameSettings.llIllIIIllllIIl[523] + this.ofFastMath);
            printWriter.println(GameSettings.llIllIIIllllIIl[524] + this.ofFastRender);
            printWriter.println(GameSettings.llIllIIIllllIIl[525] + this.ofTranslucentBlocks);
            printWriter.println(GameSettings.llIllIIIllllIIl[526] + this.ofKeyBindZoom.getKeyDescription() + GameSettings.llIllIIIllllIIl[527] + this.ofKeyBindZoom.getKeyCode());
            printWriter.close();
        }
        catch (Exception ex) {
            Config.warn(GameSettings.llIllIIIllllIIl[528]);
            ex.printStackTrace();
        }
    }
    
    private void updateRenderClouds() {
        switch (this.ofClouds) {
            default: {
                this.clouds = true;
                break;
            }
            case 3: {
                this.clouds = false;
                break;
            }
        }
    }
    
    public void resetSettings() {
        GameSettings.renderDistanceChunks = 8;
        this.viewBobbing = true;
        this.anaglyph = false;
        this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
        this.enableVsync = false;
        this.updateVSync();
        this.mipmapLevels = 4;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.clouds = true;
        this.fovSetting = 70.0f;
        this.gammaSetting = 0.0f;
        this.guiScale = 0;
        this.particleSetting = 0;
        this.heldItemTooltips = true;
        this.field_178881_t = false;
        this.field_178880_u = true;
        this.forceUnicodeFont = false;
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapType = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        Config.updateAvailableProcessors();
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofFastMath = false;
        this.ofFastRender = false;
        this.ofTranslucentBlocks = 0;
        this.ofDynamicFov = true;
        this.ofDynamicLights = 3;
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofRain = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofShowFps = false;
        this.ofProfiler = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofVignette = 0;
        this.ofChunkUpdates = 1;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = GameSettings.llIllIIIllllIIl[529];
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomItems = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofNaturalTextures = false;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofFireworkParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedTextures = true;
        Shaders.setShaderPack(Shaders.packNameNone);
        Shaders.configAntialiasingLevel = 0;
        Shaders.uninit();
        Shaders.storeConfig();
        this.updateWaterOpacity();
        this.mc.refreshResources();
        this.saveOptions();
    }
    
    public void updateVSync() {
        Display.setVSyncEnabled(this.enableVsync);
    }
    
    private void updateWaterOpacity() {
        if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
            Config.waterOpacityChanged = true;
        }
        ClearWater.updateWaterOpacity(this, Minecraft.theWorld);
    }
    
    public void setAllAnimations(final boolean ofAnimatedTextures) {
        final int n = ofAnimatedTextures ? 0 : 2;
        this.ofAnimatedWater = n;
        this.ofAnimatedLava = n;
        this.ofAnimatedFire = ofAnimatedTextures;
        this.ofAnimatedPortal = ofAnimatedTextures;
        this.ofAnimatedRedstone = ofAnimatedTextures;
        this.ofAnimatedExplosion = ofAnimatedTextures;
        this.ofAnimatedFlame = ofAnimatedTextures;
        this.ofAnimatedSmoke = ofAnimatedTextures;
        this.ofVoidParticles = ofAnimatedTextures;
        this.ofWaterParticles = ofAnimatedTextures;
        this.ofRainSplash = ofAnimatedTextures;
        this.ofPortalParticles = ofAnimatedTextures;
        this.ofPotionParticles = ofAnimatedTextures;
        this.ofFireworkParticles = ofAnimatedTextures;
        this.particleSetting = (ofAnimatedTextures ? 0 : 2);
        this.ofDrippingWaterLava = ofAnimatedTextures;
        this.ofAnimatedTerrain = ofAnimatedTextures;
        this.ofAnimatedTextures = ofAnimatedTextures;
    }
    
    private static int nextValue(final int n, final int[] array) {
        int index = indexOf(n, array);
        if (index < 0) {
            return array[0];
        }
        if (++index >= array.length) {
            index = 0;
        }
        return array[index];
    }
    
    private static int limit(final int n, final int[] array) {
        return (indexOf(n, array) < 0) ? array[0] : n;
    }
    
    private static int indexOf(final int n, final int[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == n) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean isUsingNativeTransport() {
        return this.useNativeTransport;
    }
    
    public void setModelPartEnabled(final EnumPlayerModelParts enumPlayerModelParts, final boolean b) {
        if (b) {
            this.setModelParts.add(enumPlayerModelParts);
        }
        else {
            this.setModelParts.remove(enumPlayerModelParts);
        }
        sendSettingsToServer();
    }
    
    private static void lIlIIIIIIIIlIIII() {
        (llIllIIIllllIIl = new String[530])[0] = lIIllllllIlIIIll(GameSettings.llIllIIllIIlIIl[0], GameSettings.llIllIIllIIlIIl[1]);
        GameSettings.llIllIIIllllIIl[1] = lIIllllllIlIIlII(GameSettings.llIllIIllIIlIIl[2], GameSettings.llIllIIllIIlIIl[3]);
        GameSettings.llIllIIIllllIIl[2] = lIIllllllIlIIlII(GameSettings.llIllIIllIIlIIl[4], GameSettings.llIllIIllIIlIIl[5]);
        GameSettings.llIllIIIllllIIl[3] = lIIllllllIlIIlIl(GameSettings.llIllIIllIIlIIl[6], GameSettings.llIllIIllIIlIIl[7]);
        GameSettings.llIllIIIllllIIl[4] = lIIllllllIlIIlII(GameSettings.llIllIIllIIlIIl[8], GameSettings.llIllIIllIIlIIl[9]);
        GameSettings.llIllIIIllllIIl[5] = lIIllllllIlIIlII(GameSettings.llIllIIllIIlIIl[10], GameSettings.llIllIIllIIlIIl[11]);
        GameSettings.llIllIIIllllIIl[6] = lIIllllllIlIIllI(GameSettings.llIllIIllIIlIIl[12], GameSettings.llIllIIllIIlIIl[13]);
        GameSettings.llIllIIIllllIIl[7] = lIIllllllIlIIllI(GameSettings.llIllIIllIIlIIl[14], GameSettings.llIllIIllIIlIIl[15]);
        GameSettings.llIllIIIllllIIl[8] = lIIllllllIlIIIll(GameSettings.llIllIIllIIlIIl[16], GameSettings.llIllIIllIIlIIl[17]);
        GameSettings.llIllIIIllllIIl[9] = lIIllllllIlIIllI(GameSettings.llIllIIllIIlIIl[18], GameSettings.llIllIIllIIlIIl[19]);
        GameSettings.llIllIIIllllIIl[10] = lIIllllllIlIIllI(GameSettings.llIllIIllIIlIIl[20], GameSettings.llIllIIllIIlIIl[21]);
        GameSettings.llIllIIIllllIIl[11] = lIIllllllIlIIlII(GameSettings.llIllIIllIIlIIl[22], GameSettings.llIllIIllIIlIIl[23]);
        GameSettings.llIllIIIllllIIl[12] = lIIllllllIlIIlIl(GameSettings.llIllIIllIIlIIl[24], GameSettings.llIllIIllIIlIIl[25]);
        GameSettings.llIllIIIllllIIl[13] = lIIllllllIlIIllI("F7rIM60JlgfbwZrPfW65/SQ9MV3jcN1xzT/dkjbapsGrDinuf0crfA==", "EueeF");
        GameSettings.llIllIIIllllIIl[14] = lIIllllllIlIIlIl("SlB2D4axWKKt5/VSRu6g2M+j2J6V7PMMSapnpWPRPbw=", "zgobs");
        GameSettings.llIllIIIllllIIl[15] = lIIllllllIlIIllI("n+8Ge1TARimgnqqFI2v+WuSutbAV+VR5ObGm4fgJ5oIbs2iVM0gqcg==", "YYCPP");
        GameSettings.llIllIIIllllIIl[16] = lIIllllllIlIIlIl("7aiS0ByP2M14ohK1j159pP5ZRN1ZvdMn/yC4UOR/y0aSvMldlsYSWY0ZupoixryM", "Fksgs");
        GameSettings.llIllIIIllllIIl[17] = lIIllllllIlIIllI("l9YOVnPvHspTftwFQowi74cwyYqiZriMLLxBMuxeqF9jLS+RGP6zKQ==", "HMyBw");
        GameSettings.llIllIIIllllIIl[18] = lIIllllllIlIIlIl("SHSnaOvYvl4Zx3crNE3FsLUauc2NPUMsP1Nt3ucp5utlKriVtUBG+wLwtilrmjdM", "XHgBR");
        GameSettings.llIllIIIllllIIl[19] = lIIllllllIlIIllI("99Cc1cp2ui1TSWN421YbKBMuAAzVBl8V9uB61QShFBm0nhxc8Ii8TA==", "Waaee");
        GameSettings.llIllIIIllllIIl[20] = lIIllllllIlIIlIl("pqemjvIAtzUV9HYtlAqT/y9Bh3TbwtuwrVx3867tq7A43DAberDLI9OHp4NRtRcu", "CANbp");
        GameSettings.llIllIIIllllIIl[21] = lIIllllllIlIIllI("BegOj+h9heZlLre1FUXHboALqBMDzPhg1vw1eCgoQyo=", "reYYj");
        GameSettings.llIllIIIllllIIl[22] = lIIllllllIlIIlIl("yE28qy1tw8qzUSE6r7fBfNUnCUIaEFPSwVHKdgRd7KY=", "wYSVl");
        GameSettings.llIllIIIllllIIl[23] = lIIllllllIlIIlIl("5a79Wwh+xPIDmUE1nPU0ag==", "iwhhj");
        GameSettings.llIllIIIllllIIl[24] = lIIllllllIlIIIll("FSo7GwAUKWEVHRsqJxsMCXQpExwO", "zZOro");
        GameSettings.llIllIIIllllIIl[25] = lIIllllllIlIIIll("KCQ5DhcpJ2MACiYkJQ4bNHorBhYkLQ==", "GTMgx");
        GameSettings.llIllIIIllllIIl[26] = lIIllllllIlIIllI("RAmZCz8+b70=", "oYvaN");
        GameSettings.llIllIIIllllIIl[27] = lIIllllllIlIIIll("", "pfhuA");
        GameSettings.llIllIIIllllIIl[28] = lIIllllllIlIIllI("29frTUuyA9KOhncLECrNLw==", "eewoM");
        GameSettings.llIllIIIllllIIl[29] = lIIllllllIlIIlII("TfN4JTqe8XZ/thg6wKgK8kNCkcCvldaJ", "DlRrM");
        GameSettings.llIllIIIllllIIl[30] = lIIllllllIlIIIll("OCQIWT82JwU=", "SAqwS");
        GameSettings.llIllIIIllllIIl[31] = lIIllllllIlIIIll("BxUyXSENBC4ULR4ZLgBsAR89Fi8JHj8=", "lpKsB");
        GameSettings.llIllIIIllllIIl[32] = lIIllllllIlIIlIl("ziMqCeCfiV/Q4pE6SaJg4w==", "nuzqe");
        GameSettings.llIllIIIllllIIl[33] = lIIllllllIlIIllI("RwBTyYJcWNQhhI/16V4t6zg+HP3a/spV", "GVFHu");
        GameSettings.llIllIIIllllIIl[34] = lIIllllllIlIIlIl("fKKSjriMCHfYpyd9XCcQ8w==", "riwWo");
        GameSettings.llIllIIIllllIIl[35] = lIIllllllIlIIlII("ZorXKOpWOuMyV1uy5eLkph9sG4/1/oV0", "qJfWq");
        GameSettings.llIllIIIllllIIl[36] = lIIllllllIlIIIll("Byo/dCUZIjY=", "lOFZO");
        GameSettings.llIllIIIllllIIl[37] = lIIllllllIlIIlIl("L3VYjyhq3ZFBIR522mDlXnkEV9XNO6mdv740PxTYbKI=", "pqYFU");
        GameSettings.llIllIIIllllIIl[38] = lIIllllllIlIIllI("1gZfAE4TQC1v1+VaPOgzYg==", "yNkSA");
        GameSettings.llIllIIIllllIIl[39] = lIIllllllIlIIIll("DDAoey0GITQyIRU8NCZgCjonMCMCOyU=", "gUQUN");
        GameSettings.llIllIIIllllIIl[40] = lIIllllllIlIIIll("ID00ZgMlLigmHiQqNA==", "KXMHj");
        GameSettings.llIllIIIllllIIl[41] = lIIllllllIlIIlIl("v136AKGHPmqNrd3FIkanCO1cO8cu0Ihmjh+i1hkjSpg=", "cNrad");
        GameSettings.llIllIIIllllIIl[42] = lIIllllllIlIIllI("mFY9TCKtiyQ=", "dffhS");
        GameSettings.llIllIIIllllIIl[43] = lIIllllllIlIIlIl("Cvimozl6YWOFTbtQKsn5BNBzCPTg83CwwqcEa55Wb+0=", "svwiH");
        GameSettings.llIllIIIllllIIl[44] = lIIllllllIlIIllI("ROl1kPpLTIjW2qTsX7Y/Bg==", "DVWOD");
        GameSettings.llIllIIIllllIIl[45] = lIIllllllIlIIlII("wYZTlPr0UcT6JSI0iDU5KpIW7nZA7vy+", "QTjXo");
        GameSettings.llIllIIIllllIIl[46] = lIIllllllIlIIlII("SAuGGN8mOICm/YhIM7in9A==", "JUERE");
        GameSettings.llIllIIIllllIIl[47] = lIIllllllIlIIlII("/Kcm2xVaWZcrdydydqYY0TYhsxe+PQhJ", "LktIU");
        GameSettings.llIllIIIllllIIl[48] = lIIllllllIlIIIll("MQ0RXjszCwM5Pz8F", "ZhhpK");
        GameSettings.llIllIIIllllIIl[49] = lIIllllllIlIIllI("P8jbxa0N5w/wi3wYyYHdGWqPRXdV9VxI", "bFAnJ");
        GameSettings.llIllIIIllllIIl[50] = lIIllllllIlIIIll("Kic8WzQxMCwbMw==", "ABEuG");
        GameSettings.llIllIIIllllIIl[51] = lIIllllllIlIIIll("ARU0ejQLBCgzOBgZKCd5DREgMScGETQ=", "jpMTW");
        GameSettings.llIllIIIllllIIl[52] = lIIllllllIlIIIll("PDM0QBI/Nzk=", "WVMnq");
        GameSettings.llIllIIIllllIIl[53] = lIIllllllIlIIIll("ITc4VCIrJiQdLjg7JAlvJyctDig6PiADJDg=", "JRAzA");
        GameSettings.llIllIIIllllIIl[54] = lIIllllllIlIIlIl("VPWlAtGdfcVb8+WxcYR5TA==", "tOVov");
        GameSettings.llIllIIIllllIIl[55] = lIIllllllIlIIllI("0/d2VKA47IGCKVvBNEBFQLF88/nhqCeEijZU8vVlwjg=", "DhgmY");
        GameSettings.llIllIIIllllIIl[56] = lIIllllllIlIIIll("HicrRAsaLz8LBhE=", "uBRjh");
        GameSettings.llIllIIIllllIIl[57] = lIIllllllIlIIlII("FPA7jZf1EGbGMP3EYIWwyOarE0rOA27SWOpGgfU2sPE=", "mWSKv");
        GameSettings.llIllIIIllllIIl[58] = lIIllllllIlIIIll("Iiw1Tx4qOykEAzohIxU=", "IILam");
        GameSettings.llIllIIIllllIIl[59] = lIIllllllIlIIlII("iClWTDxE4t+lgpaoAyx1u20aXOQxDOfe", "BkxHm");
        GameSettings.llIllIIIllllIIl[60] = lIIllllllIlIIlII("B/QwOB0kEe39qmhoMJw1hQlk8kv+wAX/", "wQNXf");
        GameSettings.llIllIIIllllIIl[61] = lIIllllllIlIIlII("3CwhUqhPzAqybqKTfpxbFW0VLjMG9B9P", "QAJRf");
        GameSettings.llIllIIIllllIIl[62] = lIIllllllIlIIlII("FIMa5+0+Vvz+kYB3fBr/7kxxmOMG5uOQ", "AIgWp");
        GameSettings.llIllIIIllllIIl[63] = lIIllllllIlIIIll("JBQKXxEuBRYWHT0YFgJcIhgAEg==", "Oqsqr");
        GameSettings.llIllIIIllllIIl[64] = lIIllllllIlIIllI("5GhD/BGpaMqzx0OXky4aiw==", "LYFfm");
        GameSettings.llIllIIIllllIIl[65] = lIIllllllIlIIlII("1a5QdJpTs19vOWVPRuPBxAtSZKqO03SQ", "eIOaB");
        GameSettings.llIllIIIllllIIl[66] = lIIllllllIlIIlII("Wxb8X/vGuhhJ/IgzfSeJ8ZIKvV66QI4U", "eindk");
        GameSettings.llIllIIIllllIIl[67] = lIIllllllIlIIlII("0o6TOpKBH2zyC9CMNP2XezVwh+TVrz4L", "cXQqz");
        GameSettings.llIllIIIllllIIl[68] = lIIllllllIlIIlII("PExN3f6X9AkwKw0zB1MX/x8cH6gcRM15", "RiaVE");
        GameSettings.llIllIIIllllIIl[69] = lIIllllllIlIIIll("DzwzYCoFLS8pJhYwLz1nFy04KygJ", "dYJNI");
        GameSettings.llIllIIIllllIIl[70] = lIIllllllIlIIIll("KRILexI2BRc0DBIWByYEFxkCNBQxEg==", "BwrUa");
        GameSettings.llIllIIIllllIIl[71] = lIIllllllIlIIlIl("25ecrCMebBFPw3j1GeyMYF5UtdRW2Z3PONItLfgZiAA=", "EfSTI");
        GameSettings.llIllIIIllllIIl[72] = lIIllllllIlIIllI("z9jb4LBBaggk1ezd9fVRa4Xc3F5xM6K/", "FAeJq");
        GameSettings.llIllIIIllllIIl[73] = lIIllllllIlIIlIl("L7AV2KZKYemQnV5KqoS7/U5ZpUSakMUsNWESjKIxtoA=", "GOktB");
        GameSettings.llIllIIIllllIIl[74] = lIIllllllIlIIlIl("fBnTu/XKW0B4lYUjAiEgX77DJs+sMe3cDMAyC/XW7XE=", "WwkCQ");
        GameSettings.llIllIIIllllIIl[75] = lIIllllllIlIIlIl("y8K2CEYetXqqBnf0z1gPv7iguNyjv28bYq50ackRP5k=", "fDqyB");
        GameSettings.llIllIIIllllIIl[76] = lIIllllllIlIIlII("X0nGr+XOLvupQpws82eD7Q==", "NKwzH");
        GameSettings.llIllIIIllllIIl[77] = lIIllllllIlIIllI("s8K8lmI2jCfU6F6ELY1EqCJwhA9iSSIUuNNGw/XmF28=", "lBxfD");
        GameSettings.llIllIIIllllIIl[78] = lIIllllllIlIIlII("9uM52Sxfa5aUXHk1eV4Tbw==", "bkjsj");
        GameSettings.llIllIIIllllIIl[79] = lIIllllllIlIIlIl("mpusIFhT/yzOUbi7+X8KyxPPUn3UQFeowOA4vqUKH24=", "RlUUF");
        GameSettings.llIllIIIllllIIl[80] = lIIllllllIlIIllI("QDD5dGL/J+YvfpjRDSTHTg==", "mVeqa");
        GameSettings.llIllIIIllllIIl[81] = lIIllllllIlIIllI("u92T4DE+UzOFJ2WSCxJHrWvd6ASPPxRVhJitnuPR0A4=", "ZLSfx");
        GameSettings.llIllIIIllllIIl[82] = lIIllllllIlIIllI("x1rGE62EO+plBgXU6htF5w==", "pMxFY");
        GameSettings.llIllIIIllllIIl[83] = lIIllllllIlIIIll("IhwwXxMoDSwWHzsQLAJeIBc/FB49FjsI", "IyIqp");
        GameSettings.llIllIIIllllIIl[84] = lIIllllllIlIIllI("/W6ihJEeqS8gm0l2QiK8tw==", "BDnjb");
        GameSettings.llIllIIIllllIIl[85] = lIIllllllIlIIIll("LCQUVC8mNQgdIzUoCAliLi8bHyIzLh8D", "GAmzL");
        GameSettings.llIllIIIllllIIl[86] = lIIllllllIlIIlIl("cEf7UiBppDFPT/rhhBQSOA==", "jfLxV");
        GameSettings.llIllIIIllllIIl[87] = lIIllllllIlIIIll("GzQKXiURJRYXKQI4FgNoGT8FFSgEPgEJ", "pQspF");
        GameSettings.llIllIIIllllIIl[88] = lIIllllllIlIIllI("lfFf9VOkvk+RJI6c9oQ4wA==", "VgNrz");
        GameSettings.llIllIIIllllIIl[89] = lIIllllllIlIIIll("Pi44Vw40PyQeAiciJApDPCU3HAMhJDMA", "UKAym");
        GameSettings.llIllIIIllllIIl[90] = lIIllllllIlIIlIl("yC1DPZBeoPjSj82HXwMPsg==", "RGFlU");
        GameSettings.llIllIIIllllIIl[91] = lIIllllllIlIIlIl("ET3lUPT/9FmGnc4hXzx6MCgvxq6b1H4jhhZw/1j7Vh4=", "DbpmH");
        GameSettings.llIllIIIllllIIl[92] = lIIllllllIlIIlIl("DMVoa4cMY7u+eYRQMs/BWA==", "DeRVs");
        GameSettings.llIllIIIllllIIl[93] = lIIllllllIlIIlIl("9NvvPNVxFVtrVnUwpXBix6NNYBWUhA27J+lefCdeazI=", "LtbHk");
        GameSettings.llIllIIIllllIIl[94] = lIIllllllIlIIllI("IsofbuEG84g=", "ilgFh");
        GameSettings.llIllIIIllllIIl[95] = lIIllllllIlIIlIl("+t4mwC1psrWFsaG8/j0kMg==", "IoyzC");
        GameSettings.llIllIIIllllIIl[96] = lIIllllllIlIIlIl("TZJGA30vx0YVJJ3Skd9XCQ==", "FyjfA");
        GameSettings.llIllIIIllllIIl[97] = lIIllllllIlIIIll("Gjk8LyYbOicgZwExPA==", "uIHFI");
        GameSettings.llIllIIIllllIIl[98] = lIIllllllIlIIllI("/3GPKwA8UWrAAyNqEzHEFw==", "BAQER");
        GameSettings.llIllIIIllllIIl[99] = lIIllllllIlIIllI("BXAD89pL2iUUSDDbxhffDyUwlTo03se+", "QjmqM");
        GameSettings.llIllIIIllllIIl[100] = lIIllllllIlIIlIl("HoDCzfvxWNqJ2kuIHo6S9Q==", "mKHAz");
        GameSettings.llIllIIIllllIIl[101] = lIIllllllIlIIlII("xz5jZ+ky6S0=", "sRggv");
        GameSettings.llIllIIIllllIIl[102] = lIIllllllIlIIlII("mUA5ziyZT5XWBvguZTJtYA==", "LkMjx");
        GameSettings.llIllIIIllllIIl[103] = lIIllllllIlIIIll("CQg3ZQ4DGSssAhAEKzhDDwI4LgAHAzo=", "bmNKm");
        GameSettings.llIllIIIllllIIl[104] = lIIllllllIlIIIll("CQ08ayUHDjE=", "bhEEI");
        GameSettings.llIllIIIllllIIl[105] = lIIllllllIlIIlIl("D7smNN1nFk0dnT9CARxs2YK+p+NvKmycJ0t9cAsS9gQ=", "Wlbde");
        GameSettings.llIllIIIllllIIl[106] = lIIllllllIlIIlII("vkIDUkrNtdusuTCpJZNOlw==", "hfNjc");
        GameSettings.llIllIIIllllIIl[107] = lIIllllllIlIIIll("LC0UVwcmPAgeCzUhCApKKicbHAkiJhk=", "GHmyd");
        GameSettings.llIllIIIllllIIl[108] = lIIllllllIlIIIll("KSwwTzorLiEV", "BIIaH");
        GameSettings.llIllIIIllllIIl[109] = lIIllllllIlIIIll("AjAxfRoIIS00Fhs8LSBXBDo+NhQMOzw=", "iUHSy");
        GameSettings.llIllIIIllllIIl[110] = lIIllllllIlIIIll("My8abCUtJxM=", "XJcBO");
        GameSettings.llIllIIIllllIIl[111] = lIIllllllIlIIllI("kzxxMNGYAlKmJaDTaq8Y04E5AwqNvYWy", "EWaED");
        GameSettings.llIllIIIllllIIl[112] = lIIllllllIlIIlII("GsFWuQm6HLrXkuXw1sddQA==", "zORiy");
        GameSettings.llIllIIIllllIIl[113] = lIIllllllIlIIlIl("MIktCl+FonKlV4jJgTPmb+/KQ56OhvE/iG6s5Y+MBo8=", "phZWK");
        GameSettings.llIllIIIllllIIl[114] = lIIllllllIlIIIll("JxI/diYiASM2OyMFPw==", "LwFXO");
        GameSettings.llIllIIIllllIIl[115] = lIIllllllIlIIlII("Krh82d73glwcLbkQor9JDyJoWzx62j6LjSjMM1YBQko=", "vDItx");
        GameSettings.llIllIIIllllIIl[116] = lIIllllllIlIIIll("ODMcSSMgMw==", "SVegV");
        GameSettings.llIllIIIllllIIl[117] = lIIllllllIlIIIll("Gg4BShQQHx0DGAMCHRdZFgoVAQcdCgE=", "qkxdw");
        GameSettings.llIllIIIllllIIl[118] = lIIllllllIlIIlII("Lbfaao5aROLuP4b6sR1vRg==", "HROYz");
        GameSettings.llIllIIIllllIIl[119] = lIIllllllIlIIlIl("i/keoJ0C1u+Yu7DOkXuiiURO2j1aBnj62kBe6K+eqRs=", "uSrRS");
        GameSettings.llIllIIIllllIIl[120] = lIIllllllIlIIlIl("7u697owbrYjHeSJeSquLiA==", "nTkzx");
        GameSettings.llIllIIIllllIIl[121] = lIIllllllIlIIlIl("/kud0VSg6PlV5FF0frkbzphQZ+SgNszvBD9ZqiEmK4E=", "Zgrwe");
        GameSettings.llIllIIIllllIIl[122] = lIIllllllIlIIIll("KgwbdygoCgkQLCQE", "AibYX");
        GameSettings.llIllIIIllllIIl[123] = lIIllllllIlIIllI("FLmW0zy5Jh7PIT3mkBoX3iy1Zm5K5ias", "mfLyH");
        GameSettings.llIllIIIllllIIl[124] = lIIllllllIlIIlIl("7XyRdB9z7y93FiYG6QjnxQ==", "MddFz");
        GameSettings.llIllIIIllllIIl[125] = lIIllllllIlIIlII("QG+tgA4hfkRW8K7gLm7zDf92GjLo0NUo", "bwkdq");
        GameSettings.llIllIIIllllIIl[126] = lIIllllllIlIIllI("rn5iNURSyUq5oKvkj17M5A==", "Neojz");
        GameSettings.llIllIIIllllIIl[127] = lIIllllllIlIIllI("Jn8MRN8C+EvMHF6Cgr+V23mzX6X85QxOvTtJIFPD1mU=", "LmPPO");
        GameSettings.llIllIIIllllIIl[128] = lIIllllllIlIIlII("UIXAfTkT2PCfnhPNIh6bJg==", "fbLol");
        GameSettings.llIllIIIllllIIl[129] = lIIllllllIlIIlIl("BLEHDd8TGws9k27nzWRaf3ELRqc6ZpLKbuvOXAA/PEE=", "QfssW");
        GameSettings.llIllIIIllllIIl[130] = lIIllllllIlIIlII("B4nPAEfSU+5st7jIjijejw==", "gSgDt");
        GameSettings.llIllIIIllllIIl[131] = lIIllllllIlIIlII("lQqitmgzadhz5MxC155nlzyGpk9HBcamFXfMexOHiHc=", "IGLqP");
        GameSettings.llIllIIIllllIIl[132] = lIIllllllIlIIlII("8cXqc79hOM6HYFXc4xlTSQ==", "HTnSo");
        GameSettings.llIllIIIllllIIl[133] = lIIllllllIlIIIll("HS8+ejkXPiIzNQQjIid0GyM0Nw==", "vJGTZ");
        GameSettings.llIllIIIllllIIl[134] = lIIllllllIlIIlII("ynl/Gc0WHZyffYZuatn9giFI8QycJu3E", "cvbqJ");
        GameSettings.llIllIIIllllIIl[135] = lIIllllllIlIIlII("fjiCuUcxLq9bKMt5k1DXoZDtSSUHs4gK", "uBPqb");
        GameSettings.llIllIIIllllIIl[136] = lIIllllllIlIIIll("AgQfeyAEDgkhOyoACzAhCA==", "iafUS");
        GameSettings.llIllIIIllllIIl[137] = lIIllllllIlIIIll("LSsKRAgnOhYNBDQnFhlFKycACQ==", "FNsjk");
        GameSettings.llIllIIIllllIIl[138] = lIIllllllIlIIlII("n36pWhi/T+iejx2jZ0i+2g==", "vFdyJ");
        GameSettings.llIllIIIllllIIl[139] = lIIllllllIlIIllI("QRXg1rnyokqbWpbQ/93MHXlKSUcAFELm", "kLZJo");
        GameSettings.llIllIIIllllIIl[140] = lIIllllllIlIIlII("ZjK3bnq/j8k1HieOHb1Nu+h0O43U0OAy", "ijQfw");
        GameSettings.llIllIIIllllIIl[141] = lIIllllllIlIIlIl("SFNxzQxJd6KYUyBtSB6IrgdoH1TQ0Iq1ICDTGqU/X8U=", "izFmy");
        GameSettings.llIllIIIllllIIl[142] = lIIllllllIlIIlIl("O2s+LW1xgA6wuUG4Q+XJBxkANZ7uCd7kKwBidqT2lUo=", "sQzke");
        GameSettings.llIllIIIllllIIl[143] = lIIllllllIlIIllI("rwptJPSgJ4A42CDkEomn0u70pDF41Bqo", "BxLQL");
        GameSettings.llIllIIIllllIIl[144] = lIIllllllIlIIlIl("WwOteTZSEu8kTCJRBzEKvG5Xjf+cZ0R1EO0j9K6+Q7g=", "BPJQX");
        GameSettings.llIllIIIllllIIl[145] = lIIllllllIlIIllI("IRiy3b2TNUhQY3YlDdP0iHt18/2Y+KOV", "ZqeEa");
        GameSettings.llIllIIIllllIIl[146] = lIIllllllIlIIllI("kcVOfdSKXWEk/s6qflNLU2/+aBfkiRUI", "lhpln");
        GameSettings.llIllIIIllllIIl[147] = lIIllllllIlIIIll("DBU4ezAGBCQyPBUZJCZ9FAQzMDIK", "gpAUS");
        GameSettings.llIllIIIllllIIl[148] = lIIllllllIlIIllI("4/8ugXIgUqwT/UqA0JWRLG/t6SAoxIdQ", "OvWTN");
        GameSettings.llIllIIIllllIIl[149] = lIIllllllIlIIIll("DSA4SSwHMSQAIBQsJBRhFTEzAi4L", "fEAgO");
        GameSettings.llIllIIIllllIIl[150] = lIIllllllIlIIlIl("xIsIKD9vtCJ7cAgs89NAlQ==", "ieiAb");
        GameSettings.llIllIIIllllIIl[151] = lIIllllllIlIIllI("G7vhTaVvTeu9izFJ8y4dNnToKzuGlPalWPQDS7CG9ds=", "YOkjf");
        GameSettings.llIllIIIllllIIl[152] = lIIllllllIlIIlII("gdjlP2FcuNk2nyxaTbYbAw==", "IJPTi");
        GameSettings.llIllIIIllllIIl[153] = lIIllllllIlIIllI("U7MYbwSj93mcbQy2ME9lNKNFG/4kJcwRZUWBEkuimV8=", "dNygy");
        GameSettings.llIllIIIllllIIl[154] = lIIllllllIlIIlIl("sALEAFGdUmlkwWPAFUftfA==", "nkEml");
        GameSettings.llIllIIIllllIIl[155] = lIIllllllIlIIllI("ViQKj9ykk/btXqDrW8qvWcVPp1aZ/8X/hv+9UZsirt8=", "MHmot");
        GameSettings.llIllIIIllllIIl[156] = lIIllllllIlIIllI("spIxKWxeOvDFmiF+pjO4Tw==", "UKurV");
        GameSettings.llIllIIIllllIIl[157] = lIIllllllIlIIllI("0whVXMtAUpk6njvpG/c0sCWjm3CZsRjZ/ehGn0CCsHQ=", "CccQe");
        GameSettings.llIllIIIllllIIl[158] = lIIllllllIlIIllI("AqjVq5cE1Y5H5rWEFwhAWA==", "yQwoJ");
        GameSettings.llIllIIIllllIIl[159] = lIIllllllIlIIlII("eZ3d5tr/hs5+jrXwgT6LsmUZ+XWgU/sQDL4N4H2vZ6s=", "sYTVw");
        GameSettings.llIllIIIllllIIl[160] = lIIllllllIlIIllI("Dx7jVmUGz5uQj5xVocpFOg==", "WbWVC");
        GameSettings.llIllIIIllllIIl[161] = lIIllllllIlIIlII("Sbm3iqawpDj5bhhXCE1oagWS7/EWyUzFRcQy3Ow//2s=", "Qlzyk");
        GameSettings.llIllIIIllllIIl[162] = lIIllllllIlIIllI("mzqFXw4WmgHKwBoNQfO1cA==", "khNSD");
        GameSettings.llIllIIIllllIIl[163] = lIIllllllIlIIlIl("7La27aCMwgB1pGuqCQ191CnL/QtXe+PWkRhHaQkJSnY=", "vVSyN");
        GameSettings.llIllIIIllllIIl[164] = lIIllllllIlIIlIl("zUZ9Rzbg5vgXoKQZxWjT+Q==", "DlFlX");
        GameSettings.llIllIIIllllIIl[165] = lIIllllllIlIIlIl("9eAtRjyEiMB8Fs1i8hfwcL/s+tF9CI/ZXJMSGRyupIE=", "bppwp");
        GameSettings.llIllIIIllllIIl[166] = lIIllllllIlIIIll("PwkRSy87GAoENXpV", "TlheG");
        GameSettings.llIllIIIllllIIl[167] = lIIllllllIlIIlIl("KtYO3Jjx0leyY+3UlJBz5Q+oXqdcBZxKAVHtgqJV+ug=", "eXWMb");
        GameSettings.llIllIIIllllIIl[168] = lIIllllllIlIIllI("BX3oVLyQdjs=", "XhThg");
        GameSettings.llIllIIIllllIIl[169] = lIIllllllIlIIlIl("do/J86rHj2Qa/9LWRTkz6w==", "nzZuc");
        GameSettings.llIllIIIllllIIl[170] = lIIllllllIlIIlII("pCq2L1he+VNt9w36r6XrbA==", "NNytx");
        GameSettings.llIllIIIllllIIl[171] = lIIllllllIlIIlIl("SdQJQkNZdiNy0aSO0u6/jQ==", "Rwdfg");
        GameSettings.llIllIIIllllIIl[172] = lIIllllllIlIIlIl("936xGEKgbeCYceAoTNRC0w==", "gUnYJ");
        GameSettings.llIllIIIllllIIl[173] = lIIllllllIlIIllI("uSVZS3AY09DtfZNB5b9UgoItcXiPIZ7B", "NAsbY");
        GameSettings.llIllIIIllllIIl[174] = lIIllllllIlIIIll("FgcCBzUXBFgdPxcEHxozDx4CF3QUFg4=", "ywvnZ");
        GameSettings.llIllIIIllllIIl[175] = lIIllllllIlIIlII("kDGDkI1re+c=", "cXwcO");
        GameSettings.llIllIIIllllIIl[176] = lIIllllllIlIIlIl("kYS9NN5bffbgF72DIKW5nQ==", "rXRjF");
        GameSettings.llIllIIIllllIIl[177] = lIIllllllIlIIIll("NgkuAQs3CnQOCy9XNwkc", "YyZhd");
        GameSettings.llIllIIIllllIIl[178] = lIIllllllIlIIllI("M0N4xQwnqiBuXhJbi/OzzcSsMS0bFoann26BWix6424=", "FEFZa");
        GameSettings.llIllIIIllllIIl[179] = lIIllllllIlIIlII("V4OKLlHitIk=", "xgjgl");
        GameSettings.llIllIIIllllIIl[180] = lIIllllllIlIIlIl("gdHW9J+3DyZifzlWdHeXu78RiRc/KjMzcmknfFj0csQ=", "qzNEm");
        GameSettings.llIllIIIllllIIl[181] = lIIllllllIlIIlII("y8LN3Vz9mjRD41v+GgD/bEeYrIXo0xgR", "EojHL");
        GameSettings.llIllIIIllllIIl[182] = lIIllllllIlIIlII("AXeSg3G4aY0rlPnDJ6VN5Z6zaHLMCw8+", "DgOjY");
        GameSettings.llIllIIIllllIIl[183] = lIIllllllIlIIlIl("mtGwRvorf7swCYXS664uHA==", "owyPS");
        GameSettings.llIllIIIllllIIl[184] = lIIllllllIlIIIll("aw==", "NTGJZ");
        GameSettings.llIllIIIllllIIl[185] = lIIllllllIlIIllI("u+L9XUPnYao=", "GJlTd");
        GameSettings.llIllIIIllllIIl[186] = lIIllllllIlIIlIl("VFvs1nJ1j6HsLLQZw07aUw==", "eNHDp");
        GameSettings.llIllIIIllllIIl[187] = lIIllllllIlIIlIl("lrDBITm8yhRI+lIUHwXydA==", "vpvOr");
        GameSettings.llIllIIIllllIIl[188] = lIIllllllIlIIlII("PbLr4xovaFY=", "XJcsn");
        GameSettings.llIllIIIllllIIl[189] = lIIllllllIlIIlIl("XGOdAyas7WUyqHHtyDyMNw==", "zPGUF");
        GameSettings.llIllIIIllllIIl[190] = lIIllllllIlIIlII("BsLda89cuQM=", "aukwg");
        GameSettings.llIllIIIllllIIl[191] = lIIllllllIlIIlII("OHC1C6Smib0zfy5Rj6/Qgw==", "tkwph");
        GameSettings.llIllIIIllllIIl[192] = lIIllllllIlIIIll("YQs9EQ==", "AmMbr");
        GameSettings.llIllIIIllllIIl[193] = lIIllllllIlIIllI("lVVkoajd0h0=", "zZRyd");
        GameSettings.llIllIIIllllIIl[194] = lIIllllllIlIIIll("dEdgHkwzGSM=", "QiSxl");
        GameSettings.llIllIIIllllIIl[195] = lIIllllllIlIIlII("aY50uL+RqFBj0xM7HP6V8w==", "oTmMK");
        GameSettings.llIllIIIllllIIl[196] = lIIllllllIlIIllI("iiEZfdteyDM=", "FEtyz");
        GameSettings.llIllIIIllllIIl[197] = lIIllllllIlIIIll("Fwc2ECQWBGwWJQ==", "xwByK");
        GameSettings.llIllIIIllllIIl[198] = lIIllllllIlIIIll("AxYhDgoCFXsIAwo=", "lfUge");
        GameSettings.llIllIIIllllIIl[199] = lIIllllllIlIIIll("FhUsBxkXFnYJBBgVMAcVCks+DxgaHA==", "yeXnv");
        GameSettings.llIllIIIllllIIl[200] = lIIllllllIlIIIll("FTMyDAsUMGgCFhszLgwHCW0gBBcO", "zCFed");
        GameSettings.llIllIIIllllIIl[201] = lIIllllllIlIIIll("Fj8VPCYXPE8yOxg/CTwqCmEHNDoN", "yOaUI");
        GameSettings.llIllIIIllllIIl[202] = lIIllllllIlIIllI("2LwJSCKA8Rg=", "NJLNp");
        GameSettings.llIllIIIllllIIl[203] = lIIllllllIlIIlII("OmIyCpX6Hao=", "FQSqT");
        GameSettings.llIllIIIllllIIl[204] = lIIllllllIlIIlIl("cuUBE98y15yUUPVlkvH4zpFAjuXevpjuuvLhBtbPmkY=", "njCem");
        GameSettings.llIllIIIllllIIl[205] = lIIllllllIlIIlIl("aXAgqC9xxFG7etgRTb28gw==", "bJxvt");
        GameSettings.llIllIIIllllIIl[206] = lIIllllllIlIIlII("sarerFTkSFU=", "rjAYb");
        GameSettings.llIllIIIllllIIl[207] = lIIllllllIlIIllI("bdzxbAMEnoU4draMM/h7fw==", "qmteP");
        GameSettings.llIllIIIllllIIl[208] = lIIllllllIlIIlIl("mXZLxX9qdtDz0KV0SzTfrg==", "PBXIE");
        GameSettings.llIllIIIllllIIl[209] = lIIllllllIlIIllI("lsJKKo66yuo=", "pIBcE");
        GameSettings.llIllIIIllllIIl[210] = lIIllllllIlIIIll("NSYfHDA1BxgLISYtEh0=", "GCqxU");
        GameSettings.llIllIIIllllIIl[211] = lIIllllllIlIIlIl("BOQxGi4VwyDV/tPKZi6xbA==", "YforR");
        GameSettings.llIllIIIllllIIl[212] = lIIllllllIlIIlII("AgaaPz+qaaIVvmUB2+LUKw==", "GSJgD");
        GameSettings.llIllIIIllllIIl[213] = lIIllllllIlIIlIl("AHjvtUOStGJLg146jCjasA==", "xywqD");
        GameSettings.llIllIIIllllIIl[214] = lIIllllllIlIIIll("LBEXAg==", "XcbgQ");
        GameSettings.llIllIIIllllIIl[215] = lIIllllllIlIIIll("ESoxDioJNDhaIg==", "pDPiF");
        GameSettings.llIllIIIllllIIl[216] = lIIllllllIlIIlII("2reXzpWgHEw=", "QoODV");
        GameSettings.llIllIIIllllIIl[217] = lIIllllllIlIIlIl("HQsNwvy7jWYJhtHVZkyoKw==", "wpFZu");
        GameSettings.llIllIIIllllIIl[218] = lIIllllllIlIIllI("eLBHIyloD/MOr37m4gFrDw==", "lRDet");
        GameSettings.llIllIIIllllIIl[219] = lIIllllllIlIIlIl("4QUh6igNtd5MEkRUoGs55w==", "tItCu");
        GameSettings.llIllIIIllllIIl[220] = lIIllllllIlIIlIl("ZvD2xTDhrbrpu9TnX9+idA==", "RwNqk");
        GameSettings.llIllIIIllllIIl[221] = lIIllllllIlIIlII("UJq5SVYZ86SkNXqNw408og==", "SxiGF");
        GameSettings.llIllIIIllllIIl[222] = lIIllllllIlIIlIl("K3Se/DeAyCzvhujGKSJkrA==", "BDjBz");
        GameSettings.llIllIIIllllIIl[223] = lIIllllllIlIIlIl("cXGIlKoASF+y0JE3N1m5GQ==", "vlTCc");
        GameSettings.llIllIIIllllIIl[224] = lIIllllllIlIIIll("OzQUMA==", "OFaUe");
        GameSettings.llIllIIIllllIIl[225] = lIIllllllIlIIlII("nuh2bIEwKG8=", "AKYug");
        GameSettings.llIllIIIllllIIl[226] = lIIllllllIlIIlIl("rYVp/EVgoNLI3dJZCur2QQ==", "NjgkG");
        GameSettings.llIllIIIllllIIl[227] = lIIllllllIlIIllI("zSebFwv5hVA=", "gPkaZ");
        GameSettings.llIllIIIllllIIl[228] = lIIllllllIlIIlIl("ihgcZImCS0MlmGxjym7gMw==", "WPYyx");
        GameSettings.llIllIIIllllIIl[229] = lIIllllllIlIIlIl("UpzucYu7spntztqJsTbOLg==", "DtYLN");
        GameSettings.llIllIIIllllIIl[230] = lIIllllllIlIIIll("KwwJFQ==", "Gmgrg");
        GameSettings.llIllIIIllllIIl[231] = lIIllllllIlIIIll("AQ4KJDkLFQIyBg4PHyk=", "bfkPo");
        GameSettings.llIllIIIllllIIl[232] = lIIllllllIlIIIll("LREbBDohFRUCCg==", "Nyzpy");
        GameSettings.llIllIIIllllIIl[233] = lIIllllllIlIIlIl("IjztYIp/wno5VhjLaKfpog==", "PHvUS");
        GameSettings.llIllIIIllllIIl[234] = lIIllllllIlIIlII("J+MK5HaQPlHxdYQz8kogrg==", "zoCuV");
        GameSettings.llIllIIIllllIIl[235] = lIIllllllIlIIlII("nrh6NBA0O6U=", "PbHDZ");
        GameSettings.llIllIIIllllIIl[236] = lIIllllllIlIIlIl("HpBEwU6Pu0K+FyiXNi5yqw==", "VDoao");
        GameSettings.llIllIIIllllIIl[237] = lIIllllllIlIIllI("0ETiQJsH1/c=", "nkKPC");
        GameSettings.llIllIIIllllIIl[238] = lIIllllllIlIIllI("L0Q7zl8Y7BW8BkhoA8jw0w==", "zswkC");
        GameSettings.llIllIIIllllIIl[239] = lIIllllllIlIIllI("TZfv8+5Pa7THstgg8InPTg==", "enoEl");
        GameSettings.llIllIIIllllIIl[240] = lIIllllllIlIIIll("JxQ0Pw==", "SfAZV");
        GameSettings.llIllIIIllllIIl[241] = lIIllllllIlIIllI("5EzpoCm/QF8qlfwcLmyfsg==", "Gxpln");
        GameSettings.llIllIIIllllIIl[242] = lIIllllllIlIIlII("VxjNmcbSfpM=", "EmgGC");
        GameSettings.llIllIIIllllIIl[243] = lIIllllllIlIIlII("1owmT+H++sUgFkb0yOpgRA==", "WbAcM");
        GameSettings.llIllIIIllllIIl[244] = lIIllllllIlIIlII("/LbZIXYvWzc=", "zFqAW");
        GameSettings.llIllIIIllllIIl[245] = lIIllllllIlIIllI("g+SZ/UO6CW4=", "vCQwP");
        GameSettings.llIllIIIllllIIl[246] = lIIllllllIlIIlIl("1SDzaFdJ8WHPlBkeONmAGA==", "phWDz");
        GameSettings.llIllIIIllllIIl[247] = lIIllllllIlIIllI("4Qn4fus26ybx+QXgmgT/cR4dv5BCjt4o", "RXULF");
        GameSettings.llIllIIIllllIIl[248] = lIIllllllIlIIlIl("IdwGHmcMtywX5LX2IHzH1A==", "HlJpf");
        GameSettings.llIllIIIllllIIl[249] = lIIllllllIlIIlII("PUlY+cnMpRQuqdA1O1QztvqKIE6bH2Lw", "cFZyG");
        GameSettings.llIllIIIllllIIl[250] = lIIllllllIlIIIll("NSIlMA==", "APPUB");
        GameSettings.llIllIIIllllIIl[251] = lIIllllllIlIIlIl("lN0plWYxzhJSqRR3ijjBDvuumwfFxKnNctIv0pR7k20=", "EIpmk");
        GameSettings.llIllIIIllllIIl[252] = lIIllllllIlIIlIl("hWVAssXBEMLIflfwOo0BZQ==", "BlhuV");
        GameSettings.llIllIIIllllIIl[253] = lIIllllllIlIIlIl("bka4uEAh94IkHkpVBAsb0g==", "DdVwM");
        GameSettings.llIllIIIllllIIl[254] = lIIllllllIlIIlII("k6XlJiITvSo=", "EoCCy");
        GameSettings.llIllIIIllllIIl[255] = lIIllllllIlIIlIl("+Dege2QYLhVm+PqS28g2Mg==", "JzSwv");
        GameSettings.llIllIIIllllIIl[256] = lIIllllllIlIIlIl("1j20SjAUr+2GtpQf2ukMEQ==", "CRNzU");
        GameSettings.llIllIIIllllIIl[257] = lIIllllllIlIIlIl("YscXfbd55zChcnqwhvzSUKLKFlk10Z/j1o1CYj+cmpQ=", "CygOq");
        GameSettings.llIllIIIllllIIl[258] = lIIllllllIlIIllI("9x2wSPMttro=", "gTUVb");
        GameSettings.llIllIIIllllIIl[259] = lIIllllllIlIIlII("Ea+pSBt4xODY2eZxRJJwKK6mKfOiB6TP", "eiFqL");
        GameSettings.llIllIIIllllIIl[260] = lIIllllllIlIIIll("CTwqJgcPPSw6Oz86LT0sHycuNg==", "jTKRO");
        GameSettings.llIllIIIllllIIl[261] = lIIllllllIlIIlII("rTFJS7FamMhV3GObWnVPuw==", "WCtuf");
        GameSettings.llIllIIIllllIIl[262] = lIIllllllIlIIlII("8Mt9NAC14KT14KpHk6+puQ==", "jBYvm");
        GameSettings.llIllIIIllllIIl[263] = lIIllllllIlIIlIl("HrJ5fkCSoRQy15Kl/47EPWMRlg8/EtkAXeaVl9NBT3U=", "CnaTX");
        GameSettings.llIllIIIllllIIl[264] = lIIllllllIlIIlIl("GRCC69RmTuUoNnLJtlPI3g==", "aPpZB");
        GameSettings.llIllIIIllllIIl[265] = lIIllllllIlIIIll("FxggACQKPTUbIBYC", "zqPmE");
        GameSettings.llIllIIIllllIIl[266] = lIIllllllIlIIlIl("pAgTlxCjbMMpqwNu1N6f7b1PP5ZWvro0A1mEKwSTBUY=", "LRtnf");
        GameSettings.llIllIIIllllIIl[267] = lIIllllllIlIIlIl("7Fq9mcaSWokWTYvUkQyF5Q==", "uKIZl");
        GameSettings.llIllIIIllllIIl[268] = lIIllllllIlIIllI("f30AZ0iWS+oH8GWKoyxwLEOHkLtT63n4", "TOZKG");
        GameSettings.llIllIIIllllIIl[269] = lIIllllllIlIIIll("HwIqPDYBPTopJA==", "lvXYW");
        GameSettings.llIllIIIllllIIl[270] = lIIllllllIlIIlIl("Ge4gAuVQwmlRRDMKbwzWlw==", "oHioN");
        GameSettings.llIllIIIllllIIl[271] = lIIllllllIlIIlIl("wW/bRnxVJe5QCrTVx16zFH0reio14il+V9GM9wbqDyI=", "yLlRG");
        GameSettings.llIllIIIllllIIl[272] = lIIllllllIlIIlII("cc99Fx7tXXxnTEPo7v03ATnOuEvhxD+C", "QeYZj");
        GameSettings.llIllIIIllllIIl[273] = lIIllllllIlIIIll("JTkPJw==", "QKzBU");
        GameSettings.llIllIIIllllIIl[274] = lIIllllllIlIIIll("BiA9Ii0YBD0iKhAmPSIoJjE9MSkH", "uTOGL");
        GameSettings.llIllIIIllllIIl[275] = lIIllllllIlIIlII("ocg1saMc5djfeSHt5Jokr/CHGq/5Z+8r", "xLXJf");
        GameSettings.llIllIIIllllIIl[276] = lIIllllllIlIIlIl("3kvX2OLxm6CFT2ZVSsnVS/IMI/AZmeYkCp5GfPY3aas=", "sIIbq");
        GameSettings.llIllIIIllllIIl[277] = lIIllllllIlIIlIl("DkV1ynummMb5MSRt8MvDk6v9Kq5RVvdZ0R+xrLGB1SE=", "PHypV");
        GameSettings.llIllIIIllllIIl[278] = lIIllllllIlIIlIl("DEQLHdOCboORaQINNGhrLvaSv+tAWSONqD9wu5bhF+I=", "BrOJQ");
        GameSettings.llIllIIIllllIIl[279] = lIIllllllIlIIllI("Lx7fbkuKUz0=", "SGZoD");
        GameSettings.llIllIIIllllIIl[280] = lIIllllllIlIIlII("5dRU5w00vb44lQgC/n0bG4doapSHSy/V", "GHpCU");
        GameSettings.llIllIIIllllIIl[281] = lIIllllllIlIIIll("GigMBg==", "nZycY");
        GameSettings.llIllIIIllllIIl[282] = lIIllllllIlIIllI("ITAd7GZiDUKj5WpN2Bm+rPJmnYWGwMGC", "LHIkN");
        GameSettings.llIllIIIllllIIl[283] = lIIllllllIlIIlIl("cu9tPDiETw4xWv9kyTuhhA==", "tieaP");
        GameSettings.llIllIIIllllIIl[284] = lIIllllllIlIIlII("cUEOHAP+jMM=", "XTGIF");
        GameSettings.llIllIIIllllIIl[285] = lIIllllllIlIIlIl("BKfrqJ8imBE8RaCrFHIv8w==", "JOhWR");
        GameSettings.llIllIIIllllIIl[286] = lIIllllllIlIIllI("jsoGyJwfMLETtRJ6l32B/w==", "cIBTA");
        GameSettings.llIllIIIllllIIl[287] = lIIllllllIlIIIll("JggeMQ==", "RzkTI");
        GameSettings.llIllIIIllllIIl[288] = lIIllllllIlIIIll("BxwRHTM9GR9NITUTWAIzIB4XA3l0", "TwxmC");
        GameSettings.llIllIIIllllIIl[289] = lIIllllllIlIIllI("meVR1b83fvKzD9NxTPeJMOaxhEdaA61z", "KStZN");
        GameSettings.llIllIIIllllIIl[290] = lIIllllllIlIIlII("KQJcd7WcWpY=", "VktIZ");
        GameSettings.llIllIIIllllIIl[291] = lIIllllllIlIIllI("Bt9Xf9RUjYM=", "IRLkm");
        GameSettings.llIllIIIllllIIl[292] = lIIllllllIlIIlIl("RDMyC7KjwdZmqmJmCe7kww==", "aKGVC");
        GameSettings.llIllIIIllllIIl[293] = lIIllllllIlIIlIl("nxfOC1l2sGGMHCjW39S/gZzcnR8Ue801lJm6eawlRmc=", "JkRMP");
        GameSettings.llIllIIIllllIIl[294] = lIIllllllIlIIllI("no4CmJH8rXQ=", "YBmiA");
        GameSettings.llIllIIIllllIIl[295] = lIIllllllIlIIIll("Py0hGiNi", "XLLwB");
        GameSettings.llIllIIIllllIIl[296] = lIIllllllIlIIIll("GDANAwYKJRAZGlE=", "kQyvt");
        GameSettings.llIllIIIllllIIl[297] = lIIllllllIlIIllI("7etyhF2QtU8nizIimpus3g==", "FRFlp");
        GameSettings.llIllIIIllllIIl[298] = lIIllllllIlIIlII("ba0qI11Bj1OI8t2pcd78Wg==", "KjTzM");
        GameSettings.llIllIIIllllIIl[299] = lIIllllllIlIIlIl("YvocQcWZIDLPoIy6nplP6w==", "OtvMk");
        GameSettings.llIllIIIllllIIl[300] = lIIllllllIlIIlII("Re5Znx2eOiLf4LM2WoyrLw==", "agBgO");
        GameSettings.llIllIIIllllIIl[301] = lIIllllllIlIIlIl("pkmqUgKnWt4OU8CrqsORcg==", "LHRtU");
        GameSettings.llIllIIIllllIIl[302] = lIIllllllIlIIIll("FxcZIQAJTA==", "zvagp");
        GameSettings.llIllIIIllllIIl[303] = lIIllllllIlIIIll("DCwFHR0LLAY9SQ==", "jNjXs");
        GameSettings.llIllIIIllllIIl[304] = lIIllllllIlIIlIl("Asa+uObMt75DScyOVl8gnw==", "fuCGI");
        GameSettings.llIllIIIllllIIl[305] = lIIllllllIlIIllI("ACbwNCB2xmFNLMyY5xbRCQ==", "QNHXn");
        GameSettings.llIllIIIllllIIl[306] = lIIllllllIlIIllI("p8S1eNlkVhg=", "yJOlY");
        GameSettings.llIllIIIllllIIl[307] = lIIllllllIlIIlII("T5eMSx5y52vavJ8kL7RsQw==", "QMSHo");
        GameSettings.llIllIIIllllIIl[308] = lIIllllllIlIIllI("/hgM6tGUxvCUjh2L377lfg==", "hUXJQ");
        GameSettings.llIllIIIllllIIl[309] = lIIllllllIlIIlII("wXPHqqVU9tg7xQDyspnZDQ==", "RzxAB");
        GameSettings.llIllIIIllllIIl[310] = lIIllllllIlIIllI("Q3PtSyeQl1E=", "oPYpt");
        GameSettings.llIllIIIllllIIl[311] = lIIllllllIlIIllI("or/DjHyOabbhcp6k1qPHZg==", "PQlvO");
        GameSettings.llIllIIIllllIIl[312] = lIIllllllIlIIlII("1GiPjK03BqBkWXETGG0OiQ==", "wbIen");
        GameSettings.llIllIIIllllIIl[313] = lIIllllllIlIIlIl("fVSrmWeFWLojularVZoOiQ==", "pxxac");
        GameSettings.llIllIIIllllIIl[314] = lIIllllllIlIIlIl("zJY4w28CbMtEwNcqZuEyGe+2O5kavTbzhD6MYGspqaI=", "fIveL");
        GameSettings.llIllIIIllllIIl[315] = lIIllllllIlIIllI("9/n0OnZ5Fkb4oPXPL+vNbw==", "leyOX");
        GameSettings.llIllIIIllllIIl[316] = lIIllllllIlIIIll("HT4bBxgLIjEGCQw8EQxS", "nPthh");
        GameSettings.llIllIIIllllIIl[317] = lIIllllllIlIIllI("jC95PXVzSjZUOMxSVTNBVA==", "moCxW");
        GameSettings.llIllIIIllllIIl[318] = lIIllllllIlIIIll("MQEENwUxORYsBzdV", "ToeUi");
        GameSettings.llIllIIIllllIIl[319] = lIIllllllIlIIIll("NwcDHBQtTg==", "BtfJv");
        GameSettings.llIllIIIllllIIl[320] = lIIllllllIlIIIll("DQQCKjsAHxAqGiQJAj0NFh5c", "emfOh");
        GameSettings.llIllIIIllllIIl[321] = lIIllllllIlIIllI("P7O9mRmgmZZsM9ZR18XaaXGAcj3VmXSe", "qYrkt");
        GameSettings.llIllIIIllllIIl[322] = lIIllllllIlIIlII("AWf86je9ZSbZoQXlfEwIYB8nGke1TYAp", "XPoHd");
        GameSettings.llIllIIIllllIIl[323] = lIIllllllIlIIllI("RVpq4RRA6L/5Vcqi6aS5Lg==", "HXIpU");
        GameSettings.llIllIIIllllIIl[324] = lIIllllllIlIIIll("CA8BNRkOHQEQAgMNDH0=", "gydGk");
        GameSettings.llIllIIIllllIIl[325] = lIIllllllIlIIlII("b+px8GSgpUpTr2Fd+N7hEw==", "tnWLx");
        GameSettings.llIllIIIllllIIl[326] = lIIllllllIlIIIll("LzIkKj4zMiUaGCg7PCcHNG0=", "GWHNw");
        GameSettings.llIllIIIllllIIl[327] = lIIllllllIlIIllI("x3DrjSeWLM1LO+81kAo09HHRPhgOF7jg", "uRjQh");
        GameSettings.llIllIIIllllIIl[328] = lIIllllllIlIIlIl("VW/h+CFoK0l41OpQRQ+2A/k8gyoyVUYidj3tfjyHvho=", "gYhZU");
        GameSettings.llIllIIIllllIIl[329] = lIIllllllIlIIlII("y+xYWNzfLhHDlCt9ihm7+w==", "ahcWb");
        GameSettings.llIllIIIllllIIl[330] = lIIllllllIlIIlII("LiRMWZ7mHeb9KBl8dU4W2Q==", "sgWiR");
        GameSettings.llIllIIIllllIIl[331] = lIIllllllIlIIllI("lrFGR2IYTOIzo55cxm9S1bN6Zz/p2yxR5ym2acJBhmw=", "eNKfa");
        GameSettings.llIllIIIllllIIl[332] = lIIllllllIlIIIll("Kg8JOQU3KhwiASsVQw==", "GfyTd");
        GameSettings.llIllIIIllllIIl[333] = lIIllllllIlIIIll("AiM6HDEcFTENNQIHLQsAGC8tFWo=", "qWHyP");
        GameSettings.llIllIIIllllIIl[334] = lIIllllllIlIIlIl("FAipF1sf2hqu5CFCRq1EGB7EzBTU4FtlV2fU4DAHigE=", "FGMak");
        GameSettings.llIllIIIllllIIl[335] = lIIllllllIlIIlIl("VGGeMD+Gf65+Gz4nX9fx5XLPumKBXmKD7+bgTpE5EgI=", "puLOV");
        GameSettings.llIllIIIllllIIl[336] = lIIllllllIlIIIll("AB4CLi0eIRI7P0k=", "sjpKL");
        GameSettings.llIllIIIllllIIl[337] = lIIllllllIlIIllI("xGrDZ0iiDHDH06wsT3NR4A==", "Vjmad");
        GameSettings.llIllIIIllllIIl[338] = lIIllllllIlIIlIl("AkOQDcBtQw8ZBwEMarSprYHGXT9M4r4jAjOPXp/zVUA=", "eSgwy");
        GameSettings.llIllIIIllllIIl[339] = lIIllllllIlIIllI("p9/FMb08SeZ6kaic5O7MQGp/UMLgmm9e", "BgBjn");
        GameSettings.llIllIIIllllIIl[340] = lIIllllllIlIIlIl("h4Hn9aMgVzCrknLZHKY/0yuAzqjp9cp9Nd5a0AQBBmU=", "Asqur");
        GameSettings.llIllIIIllllIIl[341] = lIIllllllIlIIlIl("AAI0o22rSRie8b/dqqNoxE7XybdWU5DBVjOpcBAba2Q=", "enJiB");
        GameSettings.llIllIIIllllIIl[342] = lIIllllllIlIIlIl("TNsvtjFVGBGNsX6wzC7/sphhDD8XZ4yfUR12cQzTgHE=", "FqkKP");
        GameSettings.llIllIIIllllIIl[343] = lIIllllllIlIIIll("NAY8DgwqPycIOSgVKQcIBRcmChsuHTxR", "GrNkm");
        GameSettings.llIllIIIllllIIl[344] = lIIllllllIlIIIll("DAQjIQs/BTghAQ4OFy0AHlE=", "jkQBn");
        GameSettings.llIllIIIllllIIl[345] = lIIllllllIlIIlII("FzQ2rqYTZw0RdtFcW8aI0w3J1plFLDl3", "pMFHi");
        GameSettings.llIllIIIllllIIl[346] = lIIllllllIlIIIll("NywgHBUgLQAMFDAuDQcQKnM=", "EIDiv");
        GameSettings.llIllIIIllllIIl[347] = lIIllllllIlIIllI("8em4o1dkvpg=", "kmWIj");
        GameSettings.llIllIIIllllIIl[348] = lIIllllllIlIIIll("bw==", "UVtqT");
        GameSettings.llIllIIIllllIIl[349] = lIIllllllIlIIlII("fm1WejFO0SoXlK0WM7ITIg==", "SQgCh");
        GameSettings.llIllIIIllllIIl[350] = lIIllllllIlIIlIl("S/KcwOhrVwnz90cwQwBxbg==", "AUygE");
        GameSettings.llIllIIIllllIIl[351] = lIIllllllIlIIlII("c158mwD10bzgCTXoN7vCDw==", "RWkay");
        GameSettings.llIllIIIllllIIl[352] = lIIllllllIlIIlIl("TVaM2ENieruDzJWetgtGtQ==", "GYRiJ");
        GameSettings.llIllIIIllllIIl[353] = lIIllllllIlIIlIl("mUiso+ef7+Giwt5FEs1UPXHtaw0/6uBVvJ46279aX5g=", "wecVu");
        GameSettings.llIllIIIllllIIl[354] = lIIllllllIlIIIll("HgB/NDQCFTA+NF8HMHciGQc1PCMCVw==", "qfQYQ");
        GameSettings.llIllIIIllllIIl[355] = lIIllllllIlIIIll("DANfNwEQFhA9AU0EEHQXCwQVPxYQVw==", "ceqZd");
        GameSettings.llIllIIIllllIIl[356] = lIIllllllIlIIIll("PDBLIisgJQQoK303A2E9OzcBKjwgZw==", "SVeON");
        GameSettings.llIllIIIllllIIl[357] = lIIllllllIlIIIll("PyxIOgAjOQcwAH4rAHkWOCsCMhcjeA==", "PJfWe");
        GameSettings.llIllIIIllllIIl[358] = lIIllllllIlIIlII("plIG558FAgFhzmy90m2lNEJ1s8Am/ln/", "JUxMg");
        GameSettings.llIllIIIllllIIl[359] = lIIllllllIlIIllI("w547saE7dd0jwakoPZxUHwEQ+w3wNPU7", "eDlSD");
        GameSettings.llIllIIIllllIIl[360] = lIIllllllIlIIlII("Drh/5wKAcLI=", "UNshc");
        GameSettings.llIllIIIllllIIl[361] = lIIllllllIlIIllI("KkKQExgXXGY=", "OzMWL");
        GameSettings.llIllIIIllllIIl[362] = lIIllllllIlIIlIl("PbRYm3rSd5/qB8UugE8Zgg==", "MdPHt");
        GameSettings.llIllIIIllllIIl[363] = lIIllllllIlIIllI("m2p9QVN8I/M=", "EMnam");
        GameSettings.llIllIIIllllIIl[364] = lIIllllllIlIIlII("C6S6gEaZuT5VgU80IT1tS0W7e2s8nd4Ow/i0vMoULW8=", "gaFcQ");
        GameSettings.llIllIIIllllIIl[365] = lIIllllllIlIIlIl("juFboSPCov1268oFFtr9GdrR6Jnz4LuIt732y0/33L8=", "XvcUK");
        GameSettings.llIllIIIllllIIl[366] = lIIllllllIlIIllI("9qC7UMZ8+YUMxxQ3kI11Ypptkf0nPMHcCRMVXKU+V74=", "ZBzUw");
        GameSettings.llIllIIIllllIIl[367] = lIIllllllIlIIlIl("XSFlefBPS2jY9Dkh9LSA0OBOB2qDCa0Om3WiLO0v6jU=", "Skhdg");
        GameSettings.llIllIIIllllIIl[368] = lIIllllllIlIIllI("FvIgdig+D7jKgCkmuwU+S2zfpS81kITPryY5uI9pE7mlQZ1U7GsoUQ==", "BIMtE");
        GameSettings.llIllIIIllllIIl[369] = lIIllllllIlIIIll("Ug==", "ygqdN");
        GameSettings.llIllIIIllllIIl[370] = lIIllllllIlIIllI("itVQKA35RU0=", "pKqMa");
        GameSettings.llIllIIIllllIIl[371] = lIIllllllIlIIlII("YtE0ZbOC35S7LtFpaNgOTKQXenRBUo8HrXLwsMD0VRE=", "NPkCT");
        GameSettings.llIllIIIllllIIl[372] = lIIllllllIlIIIll("DShHLR4WJwYsHUwjADIDAz5HLgcMKwgw", "bNiBn");
        GameSettings.llIllIIIllllIIl[373] = lIIllllllIlIIIll("KDNpPBszPCg9GGk4LiMGJiVpMQIrPCk2CjU=", "GUGSk");
        GameSettings.llIllIIIllllIIl[374] = lIIllllllIlIIlIl("sIOrP9SxWCflNXz947oC4EnpnNOUV1y7X9h8PIBLXOc=", "Prxmn");
        GameSettings.llIllIIIllllIIl[375] = lIIllllllIlIIllI("48WSnzp1U31S/wkmR3gPm9AmYH275tgohki6nVs3F7o=", "heeUx");
        GameSettings.llIllIIIllllIIl[376] = lIIllllllIlIIllI("lsw1UsSID9ld1zmnEB4H77wu8uMUhKrF", "oUPaK");
        GameSettings.llIllIIIllllIIl[377] = lIIllllllIlIIlII("dRd3fVVdfbCB5mRX92QifrxsRbqw4FoM5o1jbTCuMls=", "wZUZf");
        GameSettings.llIllIIIllllIIl[378] = lIIllllllIlIIllI("vqbRDBglmoY0Dqau+Z0YPM11KtQOlz8X8em4qYZsqNE=", "VYgHV");
        GameSettings.llIllIIIllllIIl[379] = lIIllllllIlIIlIl("x7eJKaacpt+m3C79d3mBpUKWA+dEby3D3mLpBbk8wkI=", "vnUdg");
        GameSettings.llIllIIIllllIIl[380] = lIIllllllIlIIlIl("uNMUZUbNPR23y8Mytild4X/FVQr5DAJnyQeewKZ+DRc=", "MqUKH");
        GameSettings.llIllIIIllllIIl[381] = lIIllllllIlIIlII("/0iMMEdVRI881+qAWALBapv9VZlerfRE", "kXxAc");
        GameSettings.llIllIIIllllIIl[382] = lIIllllllIlIIllI("S2UDIv64FDclpQaIFwsdq1H/h2ZnRuIo", "IUTpo");
        GameSettings.llIllIIIllllIIl[383] = lIIllllllIlIIlIl("st171nC3bd21kcHEhzkMA7gi7KFlbM2OiMXtv5JMAEk=", "UjjNv");
        GameSettings.llIllIIIllllIIl[384] = lIIllllllIlIIIll("CDxdIxYTMxwiFUkuGiEDSTQaKw4TFR0gHw==", "gZsLf");
        GameSettings.llIllIIIllllIIl[385] = lIIllllllIlIIIll("", "xyoFB");
        GameSettings.llIllIIIllllIIl[386] = lIIllllllIlIIIll("c2I=", "SJphs");
        GameSettings.llIllIIIllllIIl[387] = lIIllllllIlIIllI("zFVSI6XGnU+1cS0YPYd51124PbNJ6R+k", "Uibpo");
        GameSettings.llIllIIIllllIIl[388] = lIIllllllIlIIIll("ew==", "RSYnM");
        GameSettings.llIllIIIllllIIl[389] = lIIllllllIlIIIll("ASkTKy0pOA==", "ELuJX");
        GameSettings.llIllIIIllllIIl[390] = lIIllllllIlIIllI("nmmj28KnjDUUXlBl5tGKCC0lB1aLuy/BWXIZdNNcKiY=", "rUVyC");
        GameSettings.llIllIIIllllIIl[391] = lIIllllllIlIIIll("ADo6PSsBOWAyNg4nKyYlGy8CPSkGPmA5JRc=", "oJNTD");
        GameSettings.llIllIIIllllIIl[392] = lIIllllllIlIIlII("TSfw8CYCdDI=", "yaVtl");
        GameSettings.llIllIIIllllIIl[393] = lIIllllllIlIIlII("XXgB1AQdpBI=", "ksFXi");
        GameSettings.llIllIIIllllIIl[394] = lIIllllllIlIIlII("WOsQa32qyOM=", "xrzix");
        GameSettings.llIllIIIllllIIl[395] = lIIllllllIlIIlIl("SFk3z2If4cOoag2PGRjadLL166re4tLBWbzss2URiaM=", "zDYSI");
        GameSettings.llIllIIIllllIIl[396] = lIIllllllIlIIlII("DaISnUhlWPa4UWhmNVOXJA==", "QtqWT");
        GameSettings.llIllIIIllllIIl[397] = lIIllllllIlIIIll("LSA0DAgRMhMRGw==", "BFrco");
        GameSettings.llIllIIIllllIIl[398] = lIIllllllIlIIllI("qm9eNqvKkauqEBBA9p71cA==", "lYROB");
        GameSettings.llIllIIIllllIIl[399] = lIIllllllIlIIllI("iZNTOgjw5qo1n7zY/Mnw8gmc9EFKdOhw", "QQLfF");
        GameSettings.llIllIIIllllIIl[400] = lIIllllllIlIIIll("IAc9BC0gFQYvMjw=", "OaniB");
        GameSettings.llIllIIIllllIIl[401] = lIIllllllIlIIIll("Fgc+BCgWFQU+KAsNCQ==", "yamiG");
        GameSettings.llIllIIIllllIIl[402] = lIIllllllIlIIlIl("62wbJJU9n1G3vdveFvkuwA==", "AIcNm");
        GameSettings.llIllIIIllllIIl[403] = lIIllllllIlIIlII("e8sV74vqJHSzIDqDfJ8Edw==", "KfuLs");
        GameSettings.llIllIIIllllIIl[404] = lIIllllllIlIIIll("OBYTFQEiFCMxCz4XOA0=", "WpPyn");
        GameSettings.llIllIIIllllIIl[405] = lIIllllllIlIIIll("AzM2CBwJJg==", "lUbzy");
        GameSettings.llIllIIIllllIIl[406] = lIIllllllIlIIllI("Yb2jzAn8HzxL9VGeA9DmtQ==", "OFkUK");
        GameSettings.llIllIIIllllIIl[407] = lIIllllllIlIIlII("aviJDZ98QoY=", "UoeJC");
        GameSettings.llIllIIIllllIIl[408] = lIIllllllIlIIIll("DTQEDwwPMzEEATUzMQQX", "bREae");
        GameSettings.llIllIIIllllIIl[409] = lIIllllllIlIIlIl("gHcvkpgTghOVA8Jm+5WAeQ==", "RnqZI");
        GameSettings.llIllIIIllllIIl[410] = lIIllllllIlIIlII("KleIE64xbhgY0MXR2oK0/A==", "VwwPU");
        GameSettings.llIllIIIllllIIl[411] = lIIllllllIlIIIll("KQA1DRorBwAGFxYJBhcSKg==", "Fftcs");
        GameSettings.llIllIIIllllIIl[412] = lIIllllllIlIIllI("2bMYwNnKmajwQURW6rpJ3f/u7WQsSDAm", "hoBDR");
        GameSettings.llIllIIIllllIIl[413] = lIIllllllIlIIllI("HMN6kxj9o0EEZ0RL41GyshTnQeXKEkKE", "uwoVK");
        GameSettings.llIllIIIllllIIl[414] = lIIllllllIlIIIll("AxYUBAoBESEPByocNAcG", "lpUjc");
        GameSettings.llIllIIIllllIIl[415] = lIIllllllIlIIlIl("E/aMIGrvyx/Lt0hHLAPCng==", "oajoz");
        GameSettings.llIllIIIllllIIl[416] = lIIllllllIlIIllI("qhVCAwTXIAjHkThllm9ghA==", "EwByy");
        GameSettings.llIllIIIllllIIl[417] = lIIllllllIlIIlII("Sw/cq0A895YvV0jxD+QPD5huo1ZlQwPi", "TwEvX");
        GameSettings.llIllIIIllllIIl[418] = lIIllllllIlIIIll("Ow0nGgggChslGyYfHhYWMRg=", "Tkwuz");
        GameSettings.llIllIIIllllIIl[419] = lIIllllllIlIIllI("DAmHePh6XnS0mXHnqXIRuLbnVx8kKTVy", "lLKoJ");
        GameSettings.llIllIIIllllIIl[420] = lIIllllllIlIIlIl("xU56mNkTn3C99lWyDRPmYTkTRgJEmQMETIY0812gLGQ=", "RHLdZ");
        GameSettings.llIllIIIllllIIl[421] = lIIllllllIlIIlIl("kjySQBCGsUZ2pVWrjKHq0OtkRRbB+AqZEZYn4kMXsAM=", "BZCWW");
        GameSettings.llIllIIIllllIIl[422] = lIIllllllIlIIIll("ASkbAgoDLi4JBzoqKB4CByE=", "nOZlc");
        GameSettings.llIllIIIllllIIl[423] = lIIllllllIlIIlIl("AMlblBSZiVHLK0DL9t+Dy7MrpyGrrVvqBIbMUs1dyuY=", "UNfxN");
        GameSettings.llIllIIIllllIIl[424] = lIIllllllIlIIllI("wkkceRa/sarEiKtV8zCO4g==", "KEQuo");
        GameSettings.llIllIIIllllIIl[425] = lIIllllllIlIIllI("VfWjowsIG+cfGiQDU8Q2iQ==", "jkSal");
        GameSettings.llIllIIIllllIIl[426] = lIIllllllIlIIllI("uoJvl2sOMgUnofz3ugAGRg==", "imeyY");
        GameSettings.llIllIIIllllIIl[427] = lIIllllllIlIIlIl("ZIqgwtOQNvp7sYI494ou/g==", "ekNsc");
        GameSettings.llIllIIIllllIIl[428] = lIIllllllIlIIlIl("8NyDRrq35gdxsxrM31vpcg==", "oWuBP");
        GameSettings.llIllIIIllllIIl[429] = lIIllllllIlIIllI("yuFpg6NtuNLQJLZwOWsfEnVY8k4VDEv/", "XzrOu");
        GameSettings.llIllIIIllllIIl[430] = lIIllllllIlIIIll("OA8ZByMjASsQ", "WiNbB");
        GameSettings.llIllIIIllllIIl[431] = lIIllllllIlIIlIl("aE6ilAUhW0aww0AZ0TWieg==", "YLJJm");
        GameSettings.llIllIIIllllIIl[432] = lIIllllllIlIIIll("KxUlOQY2AA==", "DsvMg");
        GameSettings.llIllIIIllllIIl[433] = lIIllllllIlIIllI("0JQZh9+bnUioDcWf+zgIYg==", "bfVJS");
        GameSettings.llIllIIIllllIIl[434] = lIIllllllIlIIlIl("moqUsdFDknaYu5I3xZk61g==", "DbIoW");
        GameSettings.llIllIIIllllIIl[435] = lIIllllllIlIIIll("JzAtBR8mPTsdDikiCx4=", "HVnmj");
        GameSettings.llIllIIIllllIIl[436] = lIIllllllIlIIllI("/Nbhw2Z6Xh/7q/+HR/1v5d1ejCDSNRqK", "hoYcu");
        GameSettings.llIllIIIllllIIl[437] = lIIllllllIlIIlII("AJFCNCxSB0k=", "Zlcch");
        GameSettings.llIllIIIllllIIl[438] = lIIllllllIlIIlIl("Hsg1x0WGBycbiLcsj+cjiw==", "ClrUE");
        GameSettings.llIllIIIllllIIl[439] = lIIllllllIlIIlIl("CnVSpsPl1nIPm5TerWVIZQ==", "ZEjpP");
        GameSettings.llIllIIIllllIIl[440] = lIIllllllIlIIlII("SVUEwvScqq6p2MbNDLD5WQ==", "hVPNU");
        GameSettings.llIllIIIllllIIl[441] = lIIllllllIlIIIll("DQg/BScEBwMSOg==", "bnowH");
        GameSettings.llIllIIIllllIIl[442] = lIIllllllIlIIlIl("SlFLCwZc/UbLnLzHf2Jaag==", "VSptX");
        GameSettings.llIllIIIllllIIl[443] = lIIllllllIlIIlII("H7/KG+jRLp1A4SUoe3F8ag==", "jgZie");
        GameSettings.llIllIIIllllIIl[444] = lIIllllllIlIIlIl("PTc3XOR3/MVNaaQLrmzmQQ==", "CeXyB");
        GameSettings.llIllIIIllllIIl[445] = lIIllllllIlIIllI("gEowMta8/mCW0i2DOe6DDA==", "WciAb");
        GameSettings.llIllIIIllllIIl[446] = lIIllllllIlIIlII("Slo+GTgx82QV0MZeT2s2tQ==", "TCUCs");
        GameSettings.llIllIIIllllIIl[447] = lIIllllllIlIIlIl("WDVdQ1ll9/qnnVUt0RkgTw==", "sqzEH");
        GameSettings.llIllIIIllllIIl[448] = lIIllllllIlIIIll("KDMrAwczOgU/ACI4Gw==", "GUhvt");
        GameSettings.llIllIIIllllIIl[449] = lIIllllllIlIIllI("x9fQRe5g9AKyQD6F1d7ZbQ==", "Fujyj");
        GameSettings.llIllIIIllllIIl[450] = lIIllllllIlIIlIl("RKvhdAa/wGBWWqIh8Zj8Lg==", "jZBgn");
        GameSettings.llIllIIIllllIIl[451] = lIIllllllIlIIIll("PywrOS0lOAQ0DTUyES0rNTk=", "PJeXY");
        GameSettings.llIllIIIllllIIl[452] = lIIllllllIlIIllI("oa1yt+y0tPkTmfUShl+FM1ORA++mxLz0", "hTTWC");
        GameSettings.llIllIIIllllIIl[453] = lIIllllllIlIIIll("KDMPFwEmOCINKSgj", "GUKno");
        GameSettings.llIllIIIllllIIl[454] = lIIllllllIlIIlII("zMALDH4gcVwi3T34bEJzxQ==", "jzcND");
        GameSettings.llIllIIIllllIIl[455] = lIIllllllIlIIlIl("I+MHEHTtuuU8hUye7Ww/olYrjzTQFYr25YfmP/5i5g0=", "kRTqm");
        GameSettings.llIllIIIllllIIl[456] = lIIllllllIlIIlIl("lf/HpMuI0stcbz0NZXbizA==", "PKWYZ");
        GameSettings.llIllIIIllllIIl[457] = lIIllllllIlIIlIl("1Wqybe32b+rlrz+998YZ4w==", "RLzoo");
        GameSettings.llIllIIIllllIIl[458] = lIIllllllIlIIlIl("TAacT/S9sjlTM7XuRD4M/CVHDI4F5FNjlDUU57aSmVo=", "Jsxzj");
        GameSettings.llIllIIIllllIIl[459] = lIIllllllIlIIIll("CCcOBg==", "cBwYS");
        GameSettings.llIllIIIllllIIl[460] = lIIllllllIlIIllI("JFDeVmXZEcI/wMeHts+MsYtObNXFnJUn", "XOPRD");
        GameSettings.llIllIIIllllIIl[461] = lIIllllllIlIIllI("u8e8X1QZ2BT/krAxp56pou+SiT8DdPbL", "ejCLS");
        GameSettings.llIllIIIllllIIl[462] = lIIllllllIlIIlIl("r5JeTLhHQp93CgZB2bqT2a4OXOxVBZqn0u6yIn4E9n0=", "IUWxc");
        GameSettings.llIllIIIllllIIl[463] = lIIllllllIlIIlII("P0oY4LgC6DCw2EEv3omV0A==", "HUBqS");
        GameSettings.llIllIIIllllIIl[464] = lIIllllllIlIIIll("GTAhOx8lIgYmDEw=", "vVgTx");
        GameSettings.llIllIIIllllIIl[465] = lIIllllllIlIIlIl("U9/NmzVeoeOOKMmZo4T1Ng==", "HpKph");
        GameSettings.llIllIIIllllIIl[466] = lIIllllllIlIIlIl("hgZk7P2I1gFGqXGPzRidDnWT3alNQENnRJFTd8A/ybQ=", "BZJRa");
        GameSettings.llIllIIIllllIIl[467] = lIIllllllIlIIllI("lye4fYWMapt2HnkmFQNlXw==", "jzDRf");
        GameSettings.llIllIIIllllIIl[468] = lIIllllllIlIIllI("0dOiBzBlpBtMUpRLIWL5cA==", "yYaAH");
        GameSettings.llIllIIIllllIIl[469] = lIIllllllIlIIllI("qEBsaN8cayjRivngAZh//g==", "MGchY");
        GameSettings.llIllIIIllllIIl[470] = lIIllllllIlIIlIl("bEOzZUzAEpoObXrfUwhyiA==", "JxCHf");
        GameSettings.llIllIIIllllIIl[471] = lIIllllllIlIIlIl("HJu7ETCtBEA+KMwM3NYHcw==", "MpUwF");
        GameSettings.llIllIIIllllIIl[472] = lIIllllllIlIIIll("OjQZARIwIXc=", "URMsw");
        GameSettings.llIllIIIllllIIl[473] = lIIllllllIlIIIll("OAw2HQ0nGhcLKyMPHxxY", "Wjrob");
        GameSettings.llIllIIIllllIIl[474] = lIIllllllIlIIlIl("IvLMPDLfFUqiARTWvqoIEw==", "GULsv");
        GameSettings.llIllIIIllllIIl[475] = lIIllllllIlIIIll("Kx8lJiMpGBAtLhMYEC04fg==", "DydHJ");
        GameSettings.llIllIIIllllIIl[476] = lIIllllllIlIIlIl("tr2AtJbRrKZ1dqhKrS/IXw==", "urkwL");
        GameSettings.llIllIIIllllIIl[477] = lIIllllllIlIIIll("GhAnOB4YFxIzEzMfFDNN", "uvfVw");
        GameSettings.llIllIIIllllIIl[478] = lIIllllllIlIIlIl("pMa22tEkenv65CS9y68Uj1hmH7NgW07hJhoT9OvW33k=", "gkonF");
        GameSettings.llIllIIIllllIIl[479] = lIIllllllIlIIlII("e6xiqnBjkpSRKYSN5NY2itvPWBjzuuEu", "xfDal");
        GameSettings.llIllIIIllllIIl[480] = lIIllllllIlIIllI("W7NmBAY6hiP0YnUoHN5MCEe01kMvR7vQ", "zvWVr");
        GameSettings.llIllIIIllllIIl[481] = lIIllllllIlIIlII("8r7WrOEb3OU6VUO5fK5VR4oNscHefwLL", "CHIsO");
        GameSettings.llIllIIIllllIIl[482] = lIIllllllIlIIlIl("Q/Y3U+JiiFJdtGCyX1vzfuj04WvlcK+G2SMkBWomWq4=", "dUsnn");
        GameSettings.llIllIIIllllIIl[483] = lIIllllllIlIIlII("Vg8buj/ZZMlz+HgFlFtWxQ27lsxa28f0", "YVRUU");
        GameSettings.llIllIIIllllIIl[484] = lIIllllllIlIIIll("HD4/OQEWKjg5BwcxCzQQAGI=", "sXhXu");
        GameSettings.llIllIIIllllIIl[485] = lIIllllllIlIIllI("JiytsZfs1J6drxtT/D9ZNSoLOUZu0At9", "PoTLw");
        GameSettings.llIllIIIllllIIl[486] = lIIllllllIlIIlIl("jNsrAPgiTFnyssVjsYZBC6cA7pUrs7tX6ifwSjZuGdc=", "areev");
        GameSettings.llIllIIIllllIIl[487] = lIIllllllIlIIlIl("4fEZ7+XJB6V2EFXLZdL3hibyUU1mf6uk70MKIUUAoqc=", "rfAab");
        GameSettings.llIllIIIllllIIl[488] = lIIllllllIlIIlII("nW3SDfrdlSd45QXNydMh6jbAWYJ6TCax", "aTyEK");
        GameSettings.llIllIIIllllIIl[489] = lIIllllllIlIIlIl("JsjNyGdRkEkyPGphNIMzXBpZFLJxsRaq43+xFcMT5Bg=", "saciv");
        GameSettings.llIllIIIllllIIl[490] = lIIllllllIlIIIll("HBIrDA4eFR4HAycREhYSAREZWA==", "stjbg");
        GameSettings.llIllIIIllllIIl[491] = lIIllllllIlIIllI("z54CoGT+8W6GOdT/oCo6Hg==", "sSpEw");
        GameSettings.llIllIIIllllIIl[492] = lIIllllllIlIIlIl("jkrnBuKZZVwCFxn7T/P6sA==", "KgnyB");
        GameSettings.llIllIIIllllIIl[493] = lIIllllllIlIIlIl("fsQVYXxzd706RcFdb09s6Q==", "sBuKA");
        GameSettings.llIllIIIllllIIl[494] = lIIllllllIlIIlIl("g9xk9pudKkrrsvdx3DfMzsnlyToztFBnqKYv+u5jxxE=", "inuif");
        GameSettings.llIllIIIllllIIl[495] = lIIllllllIlIIlII("a7mGL9WsfXgwwCWtwvN1ew==", "HHFSf");
        GameSettings.llIllIIIllllIIl[496] = lIIllllllIlIIIll("JDUWLB4lNjY3FS8HMDsEPiEwMEo=", "KSUCp");
        GameSettings.llIllIIIllllIIl[497] = lIIllllllIlIIllI("SLriQghTNpVXtoRg/UxHuA==", "oKyDS");
        GameSettings.llIllIIIllllIIl[498] = lIIllllllIlIIllI("POCJdzZwGVg=", "ifZYg");
        GameSettings.llIllIIIllllIIl[499] = lIIllllllIlIIlII("op3LW0+Mr9y67wwlzs0YeQ==", "LJVdK");
        GameSettings.llIllIIIllllIIl[500] = lIIllllllIlIIlII("XDwmRPAF1+tPUtyLcl4tUw==", "cLHLh");
        GameSettings.llIllIIIllllIIl[501] = lIIllllllIlIIlII("f2wNUuIp+qX91Bi84+uxKw==", "enOpP");
        GameSettings.llIllIIIllllIIl[502] = lIIllllllIlIIIll("HAc0Kz8dCiIzLhIVEjBw", "sawCJ");
        GameSettings.llIllIIIllllIIl[503] = lIIllllllIlIIllI("5gl9x22YdBMrJKnNHWEWaLj8pu4lCFGR", "bOXfs");
        GameSettings.llIllIIIllllIIl[504] = lIIllllllIlIIlII("XhCTnvTrq1Y=", "VJdOT");
        GameSettings.llIllIIIllllIIl[505] = lIIllllllIlIIllI("jlat9/zuB6PVlVjxf7GIwQ==", "tnRKs");
        GameSettings.llIllIIIllllIIl[506] = lIIllllllIlIIIll("LSk2LCQnORIhUg==", "BOwMh");
        GameSettings.llIllIIIllllIIl[507] = lIIllllllIlIIlIl("GmXNWvCxHM5co3GCGVwQUg==", "bHslP");
        GameSettings.llIllIIIllllIIl[508] = lIIllllllIlIIIll("CRw8Ax8AEwAUAlw=", "fzlqp");
        GameSettings.llIllIIIllllIIl[509] = lIIllllllIlIIIll("NTcAKjkuNDAcIzUmeA==", "ZQBOM");
        GameSettings.llIllIIIllllIIl[510] = lIIllllllIlIIlII("bb92SEHybEp3LB9NvX2+QA==", "xbatR");
        GameSettings.llIllIIIllllIIl[511] = lIIllllllIlIIIll("NQ4KBQ8+BzUpDjgbYg==", "ZhXda");
        GameSettings.llIllIIIllllIIl[512] = lIIllllllIlIIllI("elRi7ldhUV3P7MJP8xINzw==", "naNMG");
        GameSettings.llIllIIIllllIIl[513] = lIIllllllIlIIllI("Cc+CehPfqAB+dUFBqdKt9A==", "Tcdjl");
        GameSettings.llIllIIIllllIIl[514] = lIIllllllIlIIIll("GyoIByYAIyYxOhgjOQFv", "tLKrU");
        GameSettings.llIllIIIllllIIl[515] = lIIllllllIlIIlIl("/yxWlmQVrsxszak87ohzmw==", "yHQis");
        GameSettings.llIllIIIllllIIl[516] = lIIllllllIlIIllI("WXN5mJhkHGrlAbSUULGQgA==", "RNLbK");
        GameSettings.llIllIIIllllIIl[517] = lIIllllllIlIIlIl("RUTDWkcK0so9ZYw6PNZ3Gg==", "lwbuG");
        GameSettings.llIllIIIllllIIl[518] = lIIllllllIlIIlIl("3q+ZqQoN10dkWl54M5aeANGZiyEIjJuIfe7LYVXdWn8=", "wmFsE");
        GameSettings.llIllIIIllllIIl[519] = lIIllllllIlIIllI("UbTdS7O8Cq9C7kjgV+lWWzbuSXFr7Ca6", "UDSHh");
        GameSettings.llIllIIIllllIIl[520] = lIIllllllIlIIlII("f+ojdCxH6+DFr0EjHehDQw==", "ZCqGN");
        GameSettings.llIllIIIllllIIl[521] = lIIllllllIlIIlIl("JSGfHnIaE9wj+K/YrqG5V20UXrXW/OOYGsiriKNoDK8=", "zqLHs");
        GameSettings.llIllIIIllllIIl[522] = lIIllllllIlIIlIl("JdQpgmWBY1TV77jK88k7qdjqoOErC9oXbbNWqmuHSxc=", "whhzJ");
        GameSettings.llIllIIIllllIIl[523] = lIIllllllIlIIlIl("Fh2N6gmb5XPynSIr1F8pcQ==", "FvcAn");
        GameSettings.llIllIIIllllIIl[524] = lIIllllllIlIIllI("LWFFwBMiutIcIY07H193Ww==", "NgJCk");
        GameSettings.llIllIIIllllIIl[525] = lIIllllllIlIIIll("JTIeNTckJyYyNS86PgU6JTchNGw=", "JTJGV");
        GameSettings.llIllIIIllllIIl[526] = lIIllllllIlIIIll("Iwg2Jw==", "HmOxH");
        GameSettings.llIllIIIllllIIl[527] = lIIllllllIlIIlII("P3SpqQIrI0Y=", "jsvSO");
        GameSettings.llIllIIIllllIIl[528] = lIIllllllIlIIllI("A5RVYC5nwxq3uueer0ZR4Cc8nhLL954l", "pBVOw");
        GameSettings.llIllIIIllllIIl[529] = lIIllllllIlIIIll("NhwRCQ0eDQ==", "rywhx");
        GameSettings.llIllIIllIIlIIl = null;
    }
    
    private static void lIlIIIIIIIIlIlll() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        GameSettings.llIllIIllIIlIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIIllllllIlIIlII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIllllllIlIIlIl(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIllllllIlIIllI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIllllllIlIIIll(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
    
    public enum Options
    {
        INVERT_MOUSE("INVERT_MOUSE", 0, "INVERT_MOUSE", 0, "INVERT_MOUSE", 0, "options.invertMouse", false, true), 
        SENSITIVITY("SENSITIVITY", 1, "SENSITIVITY", 1, "SENSITIVITY", 1, "options.sensitivity", true, false), 
        FOV("FOV", 2, "FOV", 2, "FOV", 2, "options.fov", true, false, 30.0f, 110.0f, 1.0f), 
        GAMMA("GAMMA", 3, "GAMMA", 3, "GAMMA", 3, "options.gamma", true, false), 
        SATURATION("SATURATION", 4, "SATURATION", 4, "SATURATION", 4, "options.saturation", true, false), 
        RENDER_DISTANCE("RENDER_DISTANCE", 5, "RENDER_DISTANCE", 5, "RENDER_DISTANCE", 5, "options.renderDistance", true, false, 2.0f, 16.0f, 1.0f), 
        VIEW_BOBBING("VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "options.viewBobbing", false, true), 
        ANAGLYPH("ANAGLYPH", 7, "ANAGLYPH", 7, "ANAGLYPH", 7, "options.anaglyph", false, true), 
        FRAMERATE_LIMIT("FRAMERATE_LIMIT", 8, "FRAMERATE_LIMIT", 8, "FRAMERATE_LIMIT", 8, "options.framerateLimit", true, false, 0.0f, 260.0f, 5.0f), 
        FBO_ENABLE("FBO_ENABLE", 9, "FBO_ENABLE", 9, "FBO_ENABLE", 9, "options.fboEnable", false, true), 
        RENDER_CLOUDS("RENDER_CLOUDS", 10, "RENDER_CLOUDS", 10, "RENDER_CLOUDS", 10, "options.renderClouds", false, true), 
        GRAPHICS("GRAPHICS", 11, "GRAPHICS", 11, "GRAPHICS", 11, "options.graphics", false, false), 
        AMBIENT_OCCLUSION("AMBIENT_OCCLUSION", 12, "AMBIENT_OCCLUSION", 12, "AMBIENT_OCCLUSION", 12, "options.ao", false, false), 
        GUI_SCALE("GUI_SCALE", 13, "GUI_SCALE", 13, "GUI_SCALE", 13, "options.guiScale", false, false), 
        PARTICLES("PARTICLES", 14, "PARTICLES", 14, "PARTICLES", 14, "options.particles", false, false), 
        CHAT_VISIBILITY("CHAT_VISIBILITY", 15, "CHAT_VISIBILITY", 15, "CHAT_VISIBILITY", 15, "options.chat.visibility", false, false), 
        CHAT_COLOR("CHAT_COLOR", 16, "CHAT_COLOR", 16, "CHAT_COLOR", 16, "options.chat.color", false, true), 
        CHAT_LINKS("CHAT_LINKS", 17, "CHAT_LINKS", 17, "CHAT_LINKS", 17, "options.chat.links", false, true), 
        CHAT_OPACITY("CHAT_OPACITY", 18, "CHAT_OPACITY", 18, "CHAT_OPACITY", 18, "options.chat.opacity", true, false), 
        CHAT_LINKS_PROMPT("CHAT_LINKS_PROMPT", 19, "CHAT_LINKS_PROMPT", 19, "CHAT_LINKS_PROMPT", 19, "options.chat.links.prompt", false, true), 
        SNOOPER_ENABLED("SNOOPER_ENABLED", 20, "SNOOPER_ENABLED", 20, "SNOOPER_ENABLED", 20, "options.snooper", false, true), 
        USE_FULLSCREEN("USE_FULLSCREEN", 21, "USE_FULLSCREEN", 21, "USE_FULLSCREEN", 21, "options.fullscreen", false, true), 
        ENABLE_VSYNC("ENABLE_VSYNC", 22, "ENABLE_VSYNC", 22, "ENABLE_VSYNC", 22, "options.vsync", false, true), 
        USE_VBO("USE_VBO", 23, "USE_VBO", 23, "USE_VBO", 23, "options.vbo", false, true), 
        TOUCHSCREEN("TOUCHSCREEN", 24, "TOUCHSCREEN", 24, "TOUCHSCREEN", 24, "options.touchscreen", false, true), 
        CHAT_SCALE("CHAT_SCALE", 25, "CHAT_SCALE", 25, "CHAT_SCALE", 25, "options.chat.scale", true, false), 
        CHAT_WIDTH("CHAT_WIDTH", 26, "CHAT_WIDTH", 26, "CHAT_WIDTH", 26, "options.chat.width", true, false), 
        CHAT_HEIGHT_FOCUSED("CHAT_HEIGHT_FOCUSED", 27, "CHAT_HEIGHT_FOCUSED", 27, "CHAT_HEIGHT_FOCUSED", 27, "options.chat.height.focused", true, false), 
        CHAT_HEIGHT_UNFOCUSED("CHAT_HEIGHT_UNFOCUSED", 28, "CHAT_HEIGHT_UNFOCUSED", 28, "CHAT_HEIGHT_UNFOCUSED", 28, "options.chat.height.unfocused", true, false), 
        MIPMAP_LEVELS("MIPMAP_LEVELS", 29, "MIPMAP_LEVELS", 29, "MIPMAP_LEVELS", 29, "options.mipmapLevels", true, false, 0.0f, 4.0f, 1.0f), 
        FORCE_UNICODE_FONT("FORCE_UNICODE_FONT", 30, "FORCE_UNICODE_FONT", 30, "FORCE_UNICODE_FONT", 30, "options.forceUnicodeFont", false, true), 
        STREAM_BYTES_PER_PIXEL("STREAM_BYTES_PER_PIXEL", 31, "STREAM_BYTES_PER_PIXEL", 31, "STREAM_BYTES_PER_PIXEL", 31, "options.stream.bytesPerPixel", true, false), 
        STREAM_VOLUME_MIC("STREAM_VOLUME_MIC", 32, "STREAM_VOLUME_MIC", 32, "STREAM_VOLUME_MIC", 32, "options.stream.micVolumne", true, false), 
        STREAM_VOLUME_SYSTEM("STREAM_VOLUME_SYSTEM", 33, "STREAM_VOLUME_SYSTEM", 33, "STREAM_VOLUME_SYSTEM", 33, "options.stream.systemVolume", true, false), 
        STREAM_KBPS("STREAM_KBPS", 34, "STREAM_KBPS", 34, "STREAM_KBPS", 34, "options.stream.kbps", true, false), 
        STREAM_FPS("STREAM_FPS", 35, "STREAM_FPS", 35, "STREAM_FPS", 35, "options.stream.fps", true, false), 
        STREAM_COMPRESSION("STREAM_COMPRESSION", 36, "STREAM_COMPRESSION", 36, "STREAM_COMPRESSION", 36, "options.stream.compression", false, false), 
        STREAM_SEND_METADATA("STREAM_SEND_METADATA", 37, "STREAM_SEND_METADATA", 37, "STREAM_SEND_METADATA", 37, "options.stream.sendMetadata", false, true), 
        STREAM_CHAT_ENABLED("STREAM_CHAT_ENABLED", 38, "STREAM_CHAT_ENABLED", 38, "STREAM_CHAT_ENABLED", 38, "options.stream.chat.enabled", false, false), 
        STREAM_CHAT_USER_FILTER("STREAM_CHAT_USER_FILTER", 39, "STREAM_CHAT_USER_FILTER", 39, "STREAM_CHAT_USER_FILTER", 39, "options.stream.chat.userFilter", false, false), 
        STREAM_MIC_TOGGLE_BEHAVIOR("STREAM_MIC_TOGGLE_BEHAVIOR", 40, "STREAM_MIC_TOGGLE_BEHAVIOR", 40, "STREAM_MIC_TOGGLE_BEHAVIOR", 40, "options.stream.micToggleBehavior", false, false), 
        BLOCK_ALTERNATIVES("BLOCK_ALTERNATIVES", 41, "BLOCK_ALTERNATIVES", 41, "BLOCK_ALTERNATIVES", 41, "options.blockAlternatives", false, true), 
        REDUCED_DEBUG_INFO("REDUCED_DEBUG_INFO", 42, "REDUCED_DEBUG_INFO", 42, "REDUCED_DEBUG_INFO", 42, "options.reducedDebugInfo", false, true), 
        FOG_FANCY("FOG_FANCY", 43, "FOG_FANCY", 43, "", 999, "of.options.FOG_FANCY", false, false), 
        FOG_START("FOG_START", 44, "FOG_START", 44, "", 999, "of.options.FOG_START", false, false), 
        MIPMAP_TYPE("MIPMAP_TYPE", 45, "MIPMAP_TYPE", 45, "", 999, "of.options.MIPMAP_TYPE", true, false, 0.0f, 3.0f, 1.0f), 
        SMOOTH_FPS("SMOOTH_FPS", 46, "SMOOTH_FPS", 46, "", 999, "of.options.SMOOTH_FPS", false, false), 
        CLOUDS("CLOUDS", 47, "CLOUDS", 47, "", 999, "of.options.CLOUDS", false, false), 
        CLOUD_HEIGHT("CLOUD_HEIGHT", 48, "CLOUD_HEIGHT", 48, "", 999, "of.options.CLOUD_HEIGHT", true, false), 
        TREES("TREES", 49, "TREES", 49, "", 999, "of.options.TREES", false, false), 
        RAIN("RAIN", 50, "RAIN", 50, "", 999, "of.options.RAIN", false, false), 
        ANIMATED_WATER("ANIMATED_WATER", 51, "ANIMATED_WATER", 51, "", 999, "of.options.ANIMATED_WATER", false, false), 
        ANIMATED_LAVA("ANIMATED_LAVA", 52, "ANIMATED_LAVA", 52, "", 999, "of.options.ANIMATED_LAVA", false, false), 
        ANIMATED_FIRE("ANIMATED_FIRE", 53, "ANIMATED_FIRE", 53, "", 999, "of.options.ANIMATED_FIRE", false, false), 
        ANIMATED_PORTAL("ANIMATED_PORTAL", 54, "ANIMATED_PORTAL", 54, "", 999, "of.options.ANIMATED_PORTAL", false, false), 
        AO_LEVEL("AO_LEVEL", 55, "AO_LEVEL", 55, "", 999, "of.options.AO_LEVEL", true, false), 
        LAGOMETER("LAGOMETER", 56, "LAGOMETER", 56, "", 999, "of.options.LAGOMETER", false, false), 
        SHOW_FPS("SHOW_FPS", 57, "SHOW_FPS", 57, "", 999, "of.options.SHOW_FPS", false, false), 
        AUTOSAVE_TICKS("AUTOSAVE_TICKS", 58, "AUTOSAVE_TICKS", 58, "", 999, "of.options.AUTOSAVE_TICKS", false, false), 
        BETTER_GRASS("BETTER_GRASS", 59, "BETTER_GRASS", 59, "", 999, "of.options.BETTER_GRASS", false, false), 
        ANIMATED_REDSTONE("ANIMATED_REDSTONE", 60, "ANIMATED_REDSTONE", 60, "", 999, "of.options.ANIMATED_REDSTONE", false, false), 
        ANIMATED_EXPLOSION("ANIMATED_EXPLOSION", 61, "ANIMATED_EXPLOSION", 61, "", 999, "of.options.ANIMATED_EXPLOSION", false, false), 
        ANIMATED_FLAME("ANIMATED_FLAME", 62, "ANIMATED_FLAME", 62, "", 999, "of.options.ANIMATED_FLAME", false, false), 
        ANIMATED_SMOKE("ANIMATED_SMOKE", 63, "ANIMATED_SMOKE", 63, "", 999, "of.options.ANIMATED_SMOKE", false, false), 
        WEATHER("WEATHER", 64, "WEATHER", 64, "", 999, "of.options.WEATHER", false, false), 
        SKY("SKY", 65, "SKY", 65, "", 999, "of.options.SKY", false, false), 
        STARS("STARS", 66, "STARS", 66, "", 999, "of.options.STARS", false, false), 
        SUN_MOON("SUN_MOON", 67, "SUN_MOON", 67, "", 999, "of.options.SUN_MOON", false, false), 
        VIGNETTE("VIGNETTE", 68, "VIGNETTE", 68, "", 999, "of.options.VIGNETTE", false, false), 
        CHUNK_UPDATES("CHUNK_UPDATES", 69, "CHUNK_UPDATES", 69, "", 999, "of.options.CHUNK_UPDATES", false, false), 
        CHUNK_UPDATES_DYNAMIC("CHUNK_UPDATES_DYNAMIC", 70, "CHUNK_UPDATES_DYNAMIC", 70, "", 999, "of.options.CHUNK_UPDATES_DYNAMIC", false, false), 
        TIME("TIME", 71, "TIME", 71, "", 999, "of.options.TIME", false, false), 
        CLEAR_WATER("CLEAR_WATER", 72, "CLEAR_WATER", 72, "", 999, "of.options.CLEAR_WATER", false, false), 
        SMOOTH_WORLD("SMOOTH_WORLD", 73, "SMOOTH_WORLD", 73, "", 999, "of.options.SMOOTH_WORLD", false, false), 
        VOID_PARTICLES("VOID_PARTICLES", 74, "VOID_PARTICLES", 74, "", 999, "of.options.VOID_PARTICLES", false, false), 
        WATER_PARTICLES("WATER_PARTICLES", 75, "WATER_PARTICLES", 75, "", 999, "of.options.WATER_PARTICLES", false, false), 
        RAIN_SPLASH("RAIN_SPLASH", 76, "RAIN_SPLASH", 76, "", 999, "of.options.RAIN_SPLASH", false, false), 
        PORTAL_PARTICLES("PORTAL_PARTICLES", 77, "PORTAL_PARTICLES", 77, "", 999, "of.options.PORTAL_PARTICLES", false, false), 
        POTION_PARTICLES("POTION_PARTICLES", 78, "POTION_PARTICLES", 78, "", 999, "of.options.POTION_PARTICLES", false, false), 
        FIREWORK_PARTICLES("FIREWORK_PARTICLES", 79, "FIREWORK_PARTICLES", 79, "", 999, "of.options.FIREWORK_PARTICLES", false, false), 
        PROFILER("PROFILER", 80, "PROFILER", 80, "", 999, "of.options.PROFILER", false, false), 
        DRIPPING_WATER_LAVA("DRIPPING_WATER_LAVA", 81, "DRIPPING_WATER_LAVA", 81, "", 999, "of.options.DRIPPING_WATER_LAVA", false, false), 
        BETTER_SNOW("BETTER_SNOW", 82, "BETTER_SNOW", 82, "", 999, "of.options.BETTER_SNOW", false, false), 
        FULLSCREEN_MODE("FULLSCREEN_MODE", 83, "FULLSCREEN_MODE", 83, "", 999, "of.options.FULLSCREEN_MODE", false, false), 
        ANIMATED_TERRAIN("ANIMATED_TERRAIN", 84, "ANIMATED_TERRAIN", 84, "", 999, "of.options.ANIMATED_TERRAIN", false, false), 
        SWAMP_COLORS("SWAMP_COLORS", 85, "SWAMP_COLORS", 85, "", 999, "of.options.SWAMP_COLORS", false, false), 
        RANDOM_MOBS("RANDOM_MOBS", 86, "RANDOM_MOBS", 86, "", 999, "of.options.RANDOM_MOBS", false, false), 
        SMOOTH_BIOMES("SMOOTH_BIOMES", 87, "SMOOTH_BIOMES", 87, "", 999, "of.options.SMOOTH_BIOMES", false, false), 
        CUSTOM_FONTS("CUSTOM_FONTS", 88, "CUSTOM_FONTS", 88, "", 999, "of.options.CUSTOM_FONTS", false, false), 
        CUSTOM_COLORS("CUSTOM_COLORS", 89, "CUSTOM_COLORS", 89, "", 999, "of.options.CUSTOM_COLORS", false, false), 
        SHOW_CAPES("SHOW_CAPES", 90, "SHOW_CAPES", 90, "", 999, "of.options.SHOW_CAPES", false, false), 
        CONNECTED_TEXTURES("CONNECTED_TEXTURES", 91, "CONNECTED_TEXTURES", 91, "", 999, "of.options.CONNECTED_TEXTURES", false, false), 
        CUSTOM_ITEMS("CUSTOM_ITEMS", 92, "CUSTOM_ITEMS", 92, "", 999, "of.options.CUSTOM_ITEMS", false, false), 
        AA_LEVEL("AA_LEVEL", 93, "AA_LEVEL", 93, "", 999, "of.options.AA_LEVEL", true, false, 0.0f, 16.0f, 1.0f), 
        AF_LEVEL("AF_LEVEL", 94, "AF_LEVEL", 94, "", 999, "of.options.AF_LEVEL", true, false, 1.0f, 16.0f, 1.0f), 
        ANIMATED_TEXTURES("ANIMATED_TEXTURES", 95, "ANIMATED_TEXTURES", 95, "", 999, "of.options.ANIMATED_TEXTURES", false, false), 
        NATURAL_TEXTURES("NATURAL_TEXTURES", 96, "NATURAL_TEXTURES", 96, "", 999, "of.options.NATURAL_TEXTURES", false, false), 
        HELD_ITEM_TOOLTIPS("HELD_ITEM_TOOLTIPS", 97, "HELD_ITEM_TOOLTIPS", 97, "", 999, "of.options.HELD_ITEM_TOOLTIPS", false, false), 
        DROPPED_ITEMS("DROPPED_ITEMS", 98, "DROPPED_ITEMS", 98, "", 999, "of.options.DROPPED_ITEMS", false, false), 
        LAZY_CHUNK_LOADING("LAZY_CHUNK_LOADING", 99, "LAZY_CHUNK_LOADING", 99, "", 999, "of.options.LAZY_CHUNK_LOADING", false, false), 
        CUSTOM_SKY("CUSTOM_SKY", 100, "CUSTOM_SKY", 100, "", 999, "of.options.CUSTOM_SKY", false, false), 
        FAST_MATH("FAST_MATH", 101, "FAST_MATH", 101, "", 999, "of.options.FAST_MATH", false, false), 
        FAST_RENDER("FAST_RENDER", 102, "FAST_RENDER", 102, "", 999, "of.options.FAST_RENDER", false, false), 
        TRANSLUCENT_BLOCKS("TRANSLUCENT_BLOCKS", 103, "TRANSLUCENT_BLOCKS", 103, "", 999, "of.options.TRANSLUCENT_BLOCKS", false, false), 
        DYNAMIC_FOV("DYNAMIC_FOV", 104, "DYNAMIC_FOV", 104, "", 999, "of.options.DYNAMIC_FOV", false, false), 
        DYNAMIC_LIGHTS("DYNAMIC_LIGHTS", 105, "DYNAMIC_LIGHTS", 105, "", 999, "of.options.DYNAMIC_LIGHTS", false, false);
        
        private final boolean enumFloat;
        private final boolean enumBoolean;
        private final String enumString;
        private final float valueStep;
        private float valueMin;
        private float valueMax;
        private static final Options[] $VALUES;
        private static final String __OBFID;
        private static final Options[] $VALUES$;
        private static final Options[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00000653";
            ENUM$VALUES = new Options[] { Options.INVERT_MOUSE, Options.SENSITIVITY, Options.FOV, Options.GAMMA, Options.SATURATION, Options.RENDER_DISTANCE, Options.VIEW_BOBBING, Options.ANAGLYPH, Options.FRAMERATE_LIMIT, Options.FBO_ENABLE, Options.RENDER_CLOUDS, Options.GRAPHICS, Options.AMBIENT_OCCLUSION, Options.GUI_SCALE, Options.PARTICLES, Options.CHAT_VISIBILITY, Options.CHAT_COLOR, Options.CHAT_LINKS, Options.CHAT_OPACITY, Options.CHAT_LINKS_PROMPT, Options.SNOOPER_ENABLED, Options.USE_FULLSCREEN, Options.ENABLE_VSYNC, Options.USE_VBO, Options.TOUCHSCREEN, Options.CHAT_SCALE, Options.CHAT_WIDTH, Options.CHAT_HEIGHT_FOCUSED, Options.CHAT_HEIGHT_UNFOCUSED, Options.MIPMAP_LEVELS, Options.FORCE_UNICODE_FONT, Options.STREAM_BYTES_PER_PIXEL, Options.STREAM_VOLUME_MIC, Options.STREAM_VOLUME_SYSTEM, Options.STREAM_KBPS, Options.STREAM_FPS, Options.STREAM_COMPRESSION, Options.STREAM_SEND_METADATA, Options.STREAM_CHAT_ENABLED, Options.STREAM_CHAT_USER_FILTER, Options.STREAM_MIC_TOGGLE_BEHAVIOR, Options.BLOCK_ALTERNATIVES, Options.REDUCED_DEBUG_INFO, Options.FOG_FANCY, Options.FOG_START, Options.MIPMAP_TYPE, Options.SMOOTH_FPS, Options.CLOUDS, Options.CLOUD_HEIGHT, Options.TREES, Options.RAIN, Options.ANIMATED_WATER, Options.ANIMATED_LAVA, Options.ANIMATED_FIRE, Options.ANIMATED_PORTAL, Options.AO_LEVEL, Options.LAGOMETER, Options.SHOW_FPS, Options.AUTOSAVE_TICKS, Options.BETTER_GRASS, Options.ANIMATED_REDSTONE, Options.ANIMATED_EXPLOSION, Options.ANIMATED_FLAME, Options.ANIMATED_SMOKE, Options.WEATHER, Options.SKY, Options.STARS, Options.SUN_MOON, Options.VIGNETTE, Options.CHUNK_UPDATES, Options.CHUNK_UPDATES_DYNAMIC, Options.TIME, Options.CLEAR_WATER, Options.SMOOTH_WORLD, Options.VOID_PARTICLES, Options.WATER_PARTICLES, Options.RAIN_SPLASH, Options.PORTAL_PARTICLES, Options.POTION_PARTICLES, Options.FIREWORK_PARTICLES, Options.PROFILER, Options.DRIPPING_WATER_LAVA, Options.BETTER_SNOW, Options.FULLSCREEN_MODE, Options.ANIMATED_TERRAIN, Options.SWAMP_COLORS, Options.RANDOM_MOBS, Options.SMOOTH_BIOMES, Options.CUSTOM_FONTS, Options.CUSTOM_COLORS, Options.SHOW_CAPES, Options.CONNECTED_TEXTURES, Options.CUSTOM_ITEMS, Options.AA_LEVEL, Options.AF_LEVEL, Options.ANIMATED_TEXTURES, Options.NATURAL_TEXTURES, Options.HELD_ITEM_TOOLTIPS, Options.DROPPED_ITEMS, Options.LAZY_CHUNK_LOADING, Options.CUSTOM_SKY, Options.FAST_MATH, Options.FAST_RENDER, Options.TRANSLUCENT_BLOCKS, Options.DYNAMIC_FOV, Options.DYNAMIC_LIGHTS };
            $VALUES = new Options[] { Options.INVERT_MOUSE, Options.SENSITIVITY, Options.FOV, Options.GAMMA, Options.SATURATION, Options.RENDER_DISTANCE, Options.VIEW_BOBBING, Options.ANAGLYPH, Options.FRAMERATE_LIMIT, Options.FBO_ENABLE, Options.RENDER_CLOUDS, Options.GRAPHICS, Options.AMBIENT_OCCLUSION, Options.GUI_SCALE, Options.PARTICLES, Options.CHAT_VISIBILITY, Options.CHAT_COLOR, Options.CHAT_LINKS, Options.CHAT_OPACITY, Options.CHAT_LINKS_PROMPT, Options.SNOOPER_ENABLED, Options.USE_FULLSCREEN, Options.ENABLE_VSYNC, Options.USE_VBO, Options.TOUCHSCREEN, Options.CHAT_SCALE, Options.CHAT_WIDTH, Options.CHAT_HEIGHT_FOCUSED, Options.CHAT_HEIGHT_UNFOCUSED, Options.MIPMAP_LEVELS, Options.FORCE_UNICODE_FONT, Options.STREAM_BYTES_PER_PIXEL, Options.STREAM_VOLUME_MIC, Options.STREAM_VOLUME_SYSTEM, Options.STREAM_KBPS, Options.STREAM_FPS, Options.STREAM_COMPRESSION, Options.STREAM_SEND_METADATA, Options.STREAM_CHAT_ENABLED, Options.STREAM_CHAT_USER_FILTER, Options.STREAM_MIC_TOGGLE_BEHAVIOR, Options.BLOCK_ALTERNATIVES, Options.REDUCED_DEBUG_INFO };
            $VALUES$ = new Options[] { Options.INVERT_MOUSE, Options.SENSITIVITY, Options.FOV, Options.GAMMA, Options.SATURATION, Options.RENDER_DISTANCE, Options.VIEW_BOBBING, Options.ANAGLYPH, Options.FRAMERATE_LIMIT, Options.FBO_ENABLE, Options.RENDER_CLOUDS, Options.GRAPHICS, Options.AMBIENT_OCCLUSION, Options.GUI_SCALE, Options.PARTICLES, Options.CHAT_VISIBILITY, Options.CHAT_COLOR, Options.CHAT_LINKS, Options.CHAT_OPACITY, Options.CHAT_LINKS_PROMPT, Options.SNOOPER_ENABLED, Options.USE_FULLSCREEN, Options.ENABLE_VSYNC, Options.USE_VBO, Options.TOUCHSCREEN, Options.CHAT_SCALE, Options.CHAT_WIDTH, Options.CHAT_HEIGHT_FOCUSED, Options.CHAT_HEIGHT_UNFOCUSED, Options.MIPMAP_LEVELS, Options.FORCE_UNICODE_FONT, Options.STREAM_BYTES_PER_PIXEL, Options.STREAM_VOLUME_MIC, Options.STREAM_VOLUME_SYSTEM, Options.STREAM_KBPS, Options.STREAM_FPS, Options.STREAM_COMPRESSION, Options.STREAM_SEND_METADATA, Options.STREAM_CHAT_ENABLED, Options.STREAM_CHAT_USER_FILTER, Options.STREAM_MIC_TOGGLE_BEHAVIOR, Options.BLOCK_ALTERNATIVES, Options.REDUCED_DEBUG_INFO, Options.FOG_FANCY, Options.FOG_START, Options.MIPMAP_TYPE, Options.SMOOTH_FPS, Options.CLOUDS, Options.CLOUD_HEIGHT, Options.TREES, Options.RAIN, Options.ANIMATED_WATER, Options.ANIMATED_LAVA, Options.ANIMATED_FIRE, Options.ANIMATED_PORTAL, Options.AO_LEVEL, Options.LAGOMETER, Options.SHOW_FPS, Options.AUTOSAVE_TICKS, Options.BETTER_GRASS, Options.ANIMATED_REDSTONE, Options.ANIMATED_EXPLOSION, Options.ANIMATED_FLAME, Options.ANIMATED_SMOKE, Options.WEATHER, Options.SKY, Options.STARS, Options.SUN_MOON, Options.VIGNETTE, Options.CHUNK_UPDATES, Options.CHUNK_UPDATES_DYNAMIC, Options.TIME, Options.CLEAR_WATER, Options.SMOOTH_WORLD, Options.VOID_PARTICLES, Options.WATER_PARTICLES, Options.RAIN_SPLASH, Options.PORTAL_PARTICLES, Options.POTION_PARTICLES, Options.FIREWORK_PARTICLES, Options.PROFILER, Options.DRIPPING_WATER_LAVA, Options.BETTER_SNOW, Options.FULLSCREEN_MODE, Options.ANIMATED_TERRAIN, Options.SWAMP_COLORS, Options.RANDOM_MOBS, Options.SMOOTH_BIOMES, Options.CUSTOM_FONTS, Options.CUSTOM_COLORS, Options.SHOW_CAPES, Options.CONNECTED_TEXTURES, Options.CUSTOM_ITEMS, Options.AA_LEVEL, Options.AF_LEVEL, Options.ANIMATED_TEXTURES, Options.NATURAL_TEXTURES, Options.HELD_ITEM_TOOLTIPS, Options.DROPPED_ITEMS, Options.LAZY_CHUNK_LOADING, Options.CUSTOM_SKY, Options.FAST_MATH, Options.FAST_RENDER, Options.TRANSLUCENT_BLOCKS, Options.DYNAMIC_FOV, Options.DYNAMIC_LIGHTS };
        }
        
        public static Options getEnumOptions(final int n) {
            final Options[] values = values();
            while (0 < values.length) {
                final Options options = values[0];
                if (options.returnEnumOrdinal() == n) {
                    return options;
                }
                int n2 = 0;
                ++n2;
            }
            return null;
        }
        
        private Options(final String s, final int n, final String s2, final int n2, final String s3, final int n3, final String s4, final boolean b, final boolean b2) {
            this(s, n, s2, n2, s3, n3, s4, b, b2, 0.0f, 1.0f, 0.0f);
        }
        
        private Options(final String s, final int n, final String s2, final int n2, final String s3, final int n3, final String enumString, final boolean enumFloat, final boolean enumBoolean, final float valueMin, final float valueMax, final float valueStep) {
            this.enumString = enumString;
            this.enumFloat = enumFloat;
            this.enumBoolean = enumBoolean;
            this.valueMin = valueMin;
            this.valueMax = valueMax;
            this.valueStep = valueStep;
        }
        
        public boolean getEnumFloat() {
            return this.enumFloat;
        }
        
        public boolean getEnumBoolean() {
            return this.enumBoolean;
        }
        
        public int returnEnumOrdinal() {
            return this.ordinal();
        }
        
        public String getEnumString() {
            return this.enumString;
        }
        
        public float getValueMax() {
            return this.valueMax;
        }
        
        public void setValueMax(final float valueMax) {
            this.valueMax = valueMax;
        }
        
        public float normalizeValue(final float n) {
            return MathHelper.clamp_float((this.snapToStepClamp(n) - this.valueMin) / (this.valueMax - this.valueMin), 0.0f, 1.0f);
        }
        
        public float denormalizeValue(final float n) {
            return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(n, 0.0f, 1.0f));
        }
        
        public float snapToStepClamp(float snapToStep) {
            snapToStep = this.snapToStep(snapToStep);
            return MathHelper.clamp_float(snapToStep, this.valueMin, this.valueMax);
        }
        
        protected float snapToStep(float n) {
            if (this.valueStep > 0.0f) {
                n = this.valueStep * Math.round(n / this.valueStep);
            }
            return n;
        }
        
        static float access$2(final Options options) {
            return options.valueMax;
        }
        
        static float access$3(final Options options) {
            return options.valueMin;
        }
    }
    
    static final class SwitchOptions
    {
        static final int[] optionIds;
        private static final String __OBFID;
        private static final String[] lIIIllllllIIllll;
        private static String[] lIIIllllllIlIIII;
        
        static {
            llIlIlIIIIIlIIII();
            llIlIlIIIIIIllll();
            __OBFID = SwitchOptions.lIIIllllllIIllll[0];
            optionIds = new int[Options.values().length];
            try {
                SwitchOptions.optionIds[Options.INVERT_MOUSE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchOptions.optionIds[Options.VIEW_BOBBING.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchOptions.optionIds[Options.ANAGLYPH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchOptions.optionIds[Options.FBO_ENABLE.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchOptions.optionIds[Options.RENDER_CLOUDS.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchOptions.optionIds[Options.CHAT_COLOR.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchOptions.optionIds[Options.CHAT_LINKS.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchOptions.optionIds[Options.CHAT_LINKS_PROMPT.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchOptions.optionIds[Options.SNOOPER_ENABLED.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                SwitchOptions.optionIds[Options.USE_FULLSCREEN.ordinal()] = 10;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                SwitchOptions.optionIds[Options.ENABLE_VSYNC.ordinal()] = 11;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                SwitchOptions.optionIds[Options.USE_VBO.ordinal()] = 12;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
            try {
                SwitchOptions.optionIds[Options.TOUCHSCREEN.ordinal()] = 13;
            }
            catch (NoSuchFieldError noSuchFieldError13) {}
            try {
                SwitchOptions.optionIds[Options.STREAM_SEND_METADATA.ordinal()] = 14;
            }
            catch (NoSuchFieldError noSuchFieldError14) {}
            try {
                SwitchOptions.optionIds[Options.FORCE_UNICODE_FONT.ordinal()] = 15;
            }
            catch (NoSuchFieldError noSuchFieldError15) {}
            try {
                SwitchOptions.optionIds[Options.BLOCK_ALTERNATIVES.ordinal()] = 16;
            }
            catch (NoSuchFieldError noSuchFieldError16) {}
            try {
                SwitchOptions.optionIds[Options.REDUCED_DEBUG_INFO.ordinal()] = 17;
            }
            catch (NoSuchFieldError noSuchFieldError17) {}
        }
        
        private static void llIlIlIIIIIIllll() {
            (lIIIllllllIIllll = new String[1])[0] = llIlIlIIIIIIlllI(SwitchOptions.lIIIllllllIlIIII[0], SwitchOptions.lIIIllllllIlIIII[1]);
            SwitchOptions.lIIIllllllIlIIII = null;
        }
        
        private static void llIlIlIIIIIlIIII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchOptions.lIIIllllllIlIIII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llIlIlIIIIIIlllI(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
                final Cipher instance = Cipher.getInstance("AES");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
