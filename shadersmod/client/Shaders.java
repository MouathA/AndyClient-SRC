package shadersmod.client;

import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import java.nio.*;
import org.lwjgl.*;
import shadersmod.common.*;
import net.minecraft.client.renderer.vertex.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import java.io.*;
import java.util.regex.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.client.settings.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import optifine.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class Shaders
{
    static Minecraft mc;
    static EntityRenderer entityRenderer;
    public static boolean isInitializedOnce;
    public static boolean isShaderPackInitialized;
    public static ContextCapabilities capabilities;
    public static String glVersionString;
    public static String glVendorString;
    public static String glRendererString;
    public static boolean hasGlGenMipmap;
    public static boolean hasForge;
    public static int numberResetDisplayList;
    static boolean needResetModels;
    private static int renderDisplayWidth;
    private static int renderDisplayHeight;
    public static int renderWidth;
    public static int renderHeight;
    public static boolean isRenderingWorld;
    public static boolean isRenderingSky;
    public static boolean isCompositeRendered;
    public static boolean isRenderingDfb;
    public static boolean isShadowPass;
    public static boolean isSleeping;
    public static boolean isHandRendered;
    public static boolean renderItemPass1DepthMask;
    public static ItemStack itemToRender;
    static float[] sunPosition;
    static float[] moonPosition;
    static float[] shadowLightPosition;
    static float[] upPosition;
    static float[] shadowLightPositionVector;
    static float[] upPosModelView;
    static float[] sunPosModelView;
    static float[] moonPosModelView;
    private static float[] tempMat;
    static float clearColorR;
    static float clearColorG;
    static float clearColorB;
    static float skyColorR;
    static float skyColorG;
    static float skyColorB;
    static long worldTime;
    static long lastWorldTime;
    static long diffWorldTime;
    static float celestialAngle;
    static float sunAngle;
    static float shadowAngle;
    static int moonPhase;
    static long systemTime;
    static long lastSystemTime;
    static long diffSystemTime;
    static int frameCounter;
    static float frameTimeCounter;
    static int systemTimeInt32;
    static float rainStrength;
    static float wetness;
    public static float wetnessHalfLife;
    public static float drynessHalfLife;
    public static float eyeBrightnessHalflife;
    static boolean usewetness;
    static int isEyeInWater;
    static int eyeBrightness;
    static float eyeBrightnessFadeX;
    static float eyeBrightnessFadeY;
    static float eyePosY;
    static float centerDepth;
    static float centerDepthSmooth;
    static float centerDepthSmoothHalflife;
    static boolean centerDepthSmoothEnabled;
    static int superSamplingLevel;
    static boolean updateChunksErrorRecorded;
    static boolean lightmapEnabled;
    static boolean fogEnabled;
    public static int entityAttrib;
    public static int midTexCoordAttrib;
    public static int tangentAttrib;
    public static boolean useEntityAttrib;
    public static boolean useMidTexCoordAttrib;
    public static boolean useMultiTexCoord3Attrib;
    public static boolean useTangentAttrib;
    public static boolean progUseEntityAttrib;
    public static boolean progUseMidTexCoordAttrib;
    public static boolean progUseTangentAttrib;
    public static int atlasSizeX;
    public static int atlasSizeY;
    public static ShaderUniformFloat4 uniformEntityColor;
    public static ShaderUniformInt uniformEntityId;
    public static ShaderUniformInt uniformBlockEntityId;
    static double previousCameraPositionX;
    static double previousCameraPositionY;
    static double previousCameraPositionZ;
    static double cameraPositionX;
    static double cameraPositionY;
    static double cameraPositionZ;
    static int shadowPassInterval;
    public static boolean needResizeShadow;
    static int shadowMapWidth;
    static int shadowMapHeight;
    static int spShadowMapWidth;
    static int spShadowMapHeight;
    static float shadowMapFOV;
    static float shadowMapHalfPlane;
    static boolean shadowMapIsOrtho;
    static int shadowPassCounter;
    static int preShadowPassThirdPersonView;
    public static boolean shouldSkipDefaultShadow;
    static boolean waterShadowEnabled;
    static final int MaxDrawBuffers;
    static final int MaxColorBuffers;
    static final int MaxDepthBuffers;
    static final int MaxShadowColorBuffers;
    static final int MaxShadowDepthBuffers;
    static int usedColorBuffers;
    static int usedDepthBuffers;
    static int usedShadowColorBuffers;
    static int usedShadowDepthBuffers;
    static int usedColorAttachs;
    static int usedDrawBuffers;
    static int dfb;
    static int sfb;
    private static int[] gbuffersFormat;
    public static int activeProgram;
    public static final int ProgramNone;
    public static final int ProgramBasic;
    public static final int ProgramTextured;
    public static final int ProgramTexturedLit;
    public static final int ProgramSkyBasic;
    public static final int ProgramSkyTextured;
    public static final int ProgramClouds;
    public static final int ProgramTerrain;
    public static final int ProgramTerrainSolid;
    public static final int ProgramTerrainCutoutMip;
    public static final int ProgramTerrainCutout;
    public static final int ProgramDamagedBlock;
    public static final int ProgramWater;
    public static final int ProgramBlock;
    public static final int ProgramBeaconBeam;
    public static final int ProgramItem;
    public static final int ProgramEntities;
    public static final int ProgramArmorGlint;
    public static final int ProgramSpiderEyes;
    public static final int ProgramHand;
    public static final int ProgramWeather;
    public static final int ProgramComposite;
    public static final int ProgramComposite1;
    public static final int ProgramComposite2;
    public static final int ProgramComposite3;
    public static final int ProgramComposite4;
    public static final int ProgramComposite5;
    public static final int ProgramComposite6;
    public static final int ProgramComposite7;
    public static final int ProgramFinal;
    public static final int ProgramShadow;
    public static final int ProgramShadowSolid;
    public static final int ProgramShadowCutout;
    public static final int ProgramCount;
    public static final int MaxCompositePasses;
    private static final String[] programNames;
    private static final int[] programBackups;
    static int[] programsID;
    private static int[] programsRef;
    private static int programIDCopyDepth;
    private static String[] programsDrawBufSettings;
    private static String newDrawBufSetting;
    static IntBuffer[] programsDrawBuffers;
    static IntBuffer activeDrawBuffers;
    private static String[] programsColorAtmSettings;
    private static String newColorAtmSetting;
    private static String activeColorAtmSettings;
    private static int[] programsCompositeMipmapSetting;
    private static int newCompositeMipmapSetting;
    private static int activeCompositeMipmapSetting;
    public static Properties loadedShaders;
    public static Properties shadersConfig;
    public static ITextureObject defaultTexture;
    public static boolean normalMapEnabled;
    public static boolean[] shadowHardwareFilteringEnabled;
    public static boolean[] shadowMipmapEnabled;
    public static boolean[] shadowFilterNearest;
    public static boolean[] shadowColorMipmapEnabled;
    public static boolean[] shadowColorFilterNearest;
    public static boolean configTweakBlockDamage;
    public static boolean configCloudShadow;
    public static float configHandDepthMul;
    public static float configRenderResMul;
    public static float configShadowResMul;
    public static int configTexMinFilB;
    public static int configTexMinFilN;
    public static int configTexMinFilS;
    public static int configTexMagFilB;
    public static int configTexMagFilN;
    public static int configTexMagFilS;
    public static boolean configShadowClipFrustrum;
    public static boolean configNormalMap;
    public static boolean configSpecularMap;
    public static PropertyDefaultTrueFalse configOldLighting;
    public static int configAntialiasingLevel;
    public static final int texMinFilRange;
    public static final int texMagFilRange;
    public static final String[] texMinFilDesc;
    public static final String[] texMagFilDesc;
    public static final int[] texMinFilValue;
    public static final int[] texMagFilValue;
    static IShaderPack shaderPack;
    public static boolean shaderPackLoaded;
    static File currentshader;
    static String currentshadername;
    public static String packNameNone;
    static String packNameDefault;
    static String shaderpacksdirname;
    static String optionsfilename;
    static File shadersdir;
    static File shaderpacksdir;
    static File configFile;
    static ShaderOption[] shaderPackOptions;
    static ShaderProfile[] shaderPackProfiles;
    static Map shaderPackGuiScreens;
    public static PropertyDefaultFastFancyOff shaderPackClouds;
    public static PropertyDefaultTrueFalse shaderPackOldLighting;
    public static PropertyDefaultTrueFalse shaderPackDynamicHandLight;
    private static Map shaderPackResources;
    private static World currentWorld;
    private static List shaderPackDimensions;
    public static final boolean enableShadersOption;
    private static final boolean enableShadersDebug;
    private static final boolean saveFinalShaders;
    public static float blockLightLevel05;
    public static float blockLightLevel06;
    public static float blockLightLevel08;
    public static float aoLevel;
    public static float blockAoLight;
    public static float sunPathRotation;
    public static float shadowAngleInterval;
    public static int fogMode;
    public static float fogColorR;
    public static float fogColorG;
    public static float fogColorB;
    public static float shadowIntervalSize;
    public static int terrainIconSize;
    public static int[] terrainTextureSize;
    private static HFNoiseTexture noiseTexture;
    private static boolean noiseTextureEnabled;
    private static int noiseTextureResolution;
    static final int[] dfbColorTexturesA;
    static final int[] colorTexturesToggle;
    static final int[] colorTextureTextureImageUnit;
    static final boolean[][] programsToggleColorTextures;
    private static final int bigBufferSize;
    private static final ByteBuffer bigBuffer;
    static final float[] faProjection;
    static final float[] faProjectionInverse;
    static final float[] faModelView;
    static final float[] faModelViewInverse;
    static final float[] faShadowProjection;
    static final float[] faShadowProjectionInverse;
    static final float[] faShadowModelView;
    static final float[] faShadowModelViewInverse;
    static final FloatBuffer projection;
    static final FloatBuffer projectionInverse;
    static final FloatBuffer modelView;
    static final FloatBuffer modelViewInverse;
    static final FloatBuffer shadowProjection;
    static final FloatBuffer shadowProjectionInverse;
    static final FloatBuffer shadowModelView;
    static final FloatBuffer shadowModelViewInverse;
    static final FloatBuffer previousProjection;
    static final FloatBuffer previousModelView;
    static final FloatBuffer tempMatrixDirectBuffer;
    static final FloatBuffer tempDirectFloatBuffer;
    static final IntBuffer dfbColorTextures;
    static final IntBuffer dfbDepthTextures;
    static final IntBuffer sfbColorTextures;
    static final IntBuffer sfbDepthTextures;
    static final IntBuffer dfbDrawBuffers;
    static final IntBuffer sfbDrawBuffers;
    static final IntBuffer drawBuffersNone;
    static final IntBuffer drawBuffersAll;
    static final IntBuffer drawBuffersClear0;
    static final IntBuffer drawBuffersClear1;
    static final IntBuffer drawBuffersClearColor;
    static final IntBuffer drawBuffersColorAtt0;
    static final IntBuffer[] drawBuffersBuffer;
    static Map mapBlockToEntityData;
    private static final Pattern gbufferFormatPattern;
    private static final Pattern gbufferMipmapEnabledPattern;
    private static final String[] formatNames;
    private static final int[] formatIds;
    private static final Pattern patternLoadEntityDataMap;
    public static int[] entityData;
    public static int entityDataIndex;
    private static final String[] lIIllIIIlIlIllIl;
    private static String[] lIIllIIIlllIllll;
    
    static {
        lllIIIIlIIlllllI();
        lllIIIIlIIllIIll();
        ProgramComposite1 = 22;
        ProgramComposite6 = 27;
        ProgramShadowCutout = 32;
        MaxShadowDepthBuffers = 2;
        ProgramComposite7 = 28;
        enableShadersDebug = true;
        ProgramClouds = 6;
        ProgramTerrain = 7;
        enableShadersOption = true;
        bigBufferSize = 2196;
        ProgramEntities = 16;
        ProgramCount = 33;
        ProgramBlock = 13;
        ProgramTerrainSolid = 8;
        ProgramWater = 12;
        ProgramShadowSolid = 31;
        ProgramBasic = 1;
        MaxCompositePasses = 8;
        ProgramComposite = 21;
        texMagFilRange = 2;
        ProgramWeather = 20;
        ProgramTerrainCutoutMip = 9;
        ProgramTexturedLit = 3;
        ProgramArmorGlint = 17;
        texMinFilRange = 3;
        ProgramComposite3 = 24;
        ProgramComposite5 = 26;
        ProgramItem = 15;
        MaxDrawBuffers = 8;
        MaxShadowColorBuffers = 8;
        ProgramTextured = 2;
        ProgramNone = 0;
        ProgramComposite2 = 23;
        ProgramSkyBasic = 4;
        ProgramHand = 19;
        ProgramBeaconBeam = 14;
        ProgramComposite4 = 25;
        MaxDepthBuffers = 3;
        ProgramFinal = 29;
        ProgramSkyTextured = 5;
        MaxColorBuffers = 8;
        ProgramDamagedBlock = 11;
        ProgramSpiderEyes = 18;
        ProgramTerrainCutout = 10;
        ProgramShadow = 30;
        Shaders.mc = Minecraft.getMinecraft();
        Shaders.isInitializedOnce = false;
        Shaders.isShaderPackInitialized = false;
        Shaders.hasGlGenMipmap = false;
        Shaders.hasForge = false;
        Shaders.numberResetDisplayList = 0;
        Shaders.needResetModels = false;
        Shaders.renderDisplayWidth = 0;
        Shaders.renderDisplayHeight = 0;
        Shaders.renderWidth = 0;
        Shaders.renderHeight = 0;
        Shaders.isRenderingWorld = false;
        Shaders.isRenderingSky = false;
        Shaders.isCompositeRendered = false;
        Shaders.isRenderingDfb = false;
        Shaders.isShadowPass = false;
        Shaders.renderItemPass1DepthMask = false;
        Shaders.sunPosition = new float[4];
        Shaders.moonPosition = new float[4];
        Shaders.shadowLightPosition = new float[4];
        Shaders.upPosition = new float[4];
        Shaders.shadowLightPositionVector = new float[4];
        Shaders.upPosModelView = new float[] { 0.0f, 100.0f, 0.0f, 0.0f };
        Shaders.sunPosModelView = new float[] { 0.0f, 100.0f, 0.0f, 0.0f };
        Shaders.moonPosModelView = new float[] { 0.0f, -100.0f, 0.0f, 0.0f };
        Shaders.tempMat = new float[16];
        Shaders.worldTime = 0L;
        Shaders.lastWorldTime = 0L;
        Shaders.diffWorldTime = 0L;
        Shaders.celestialAngle = 0.0f;
        Shaders.sunAngle = 0.0f;
        Shaders.shadowAngle = 0.0f;
        Shaders.moonPhase = 0;
        Shaders.systemTime = 0L;
        Shaders.lastSystemTime = 0L;
        Shaders.diffSystemTime = 0L;
        Shaders.frameCounter = 0;
        Shaders.frameTimeCounter = 0.0f;
        Shaders.systemTimeInt32 = 0;
        Shaders.rainStrength = 0.0f;
        Shaders.wetness = 0.0f;
        Shaders.wetnessHalfLife = 600.0f;
        Shaders.drynessHalfLife = 200.0f;
        Shaders.eyeBrightnessHalflife = 10.0f;
        Shaders.usewetness = false;
        Shaders.isEyeInWater = 0;
        Shaders.eyeBrightness = 0;
        Shaders.eyeBrightnessFadeX = 0.0f;
        Shaders.eyeBrightnessFadeY = 0.0f;
        Shaders.eyePosY = 0.0f;
        Shaders.centerDepth = 0.0f;
        Shaders.centerDepthSmooth = 0.0f;
        Shaders.centerDepthSmoothHalflife = 1.0f;
        Shaders.centerDepthSmoothEnabled = false;
        Shaders.superSamplingLevel = 1;
        Shaders.updateChunksErrorRecorded = false;
        Shaders.lightmapEnabled = false;
        Shaders.fogEnabled = true;
        Shaders.entityAttrib = 10;
        Shaders.midTexCoordAttrib = 11;
        Shaders.tangentAttrib = 12;
        Shaders.useEntityAttrib = false;
        Shaders.useMidTexCoordAttrib = false;
        Shaders.useMultiTexCoord3Attrib = false;
        Shaders.useTangentAttrib = false;
        Shaders.progUseEntityAttrib = false;
        Shaders.progUseMidTexCoordAttrib = false;
        Shaders.progUseTangentAttrib = false;
        Shaders.atlasSizeX = 0;
        Shaders.atlasSizeY = 0;
        Shaders.uniformEntityColor = new ShaderUniformFloat4(Shaders.lIIllIIIlIlIllIl[0]);
        Shaders.uniformEntityId = new ShaderUniformInt(Shaders.lIIllIIIlIlIllIl[1]);
        Shaders.uniformBlockEntityId = new ShaderUniformInt(Shaders.lIIllIIIlIlIllIl[2]);
        Shaders.shadowPassInterval = 0;
        Shaders.needResizeShadow = false;
        Shaders.shadowMapWidth = 1024;
        Shaders.shadowMapHeight = 1024;
        Shaders.spShadowMapWidth = 1024;
        Shaders.spShadowMapHeight = 1024;
        Shaders.shadowMapFOV = 90.0f;
        Shaders.shadowMapHalfPlane = 160.0f;
        Shaders.shadowMapIsOrtho = true;
        Shaders.shadowPassCounter = 0;
        Shaders.shouldSkipDefaultShadow = false;
        Shaders.waterShadowEnabled = false;
        Shaders.usedColorBuffers = 0;
        Shaders.usedDepthBuffers = 0;
        Shaders.usedShadowColorBuffers = 0;
        Shaders.usedShadowDepthBuffers = 0;
        Shaders.usedColorAttachs = 0;
        Shaders.usedDrawBuffers = 0;
        Shaders.dfb = 0;
        Shaders.sfb = 0;
        Shaders.gbuffersFormat = new int[8];
        Shaders.activeProgram = 0;
        programNames = new String[] { Shaders.lIIllIIIlIlIllIl[3], Shaders.lIIllIIIlIlIllIl[4], Shaders.lIIllIIIlIlIllIl[5], Shaders.lIIllIIIlIlIllIl[6], Shaders.lIIllIIIlIlIllIl[7], Shaders.lIIllIIIlIlIllIl[8], Shaders.lIIllIIIlIlIllIl[9], Shaders.lIIllIIIlIlIllIl[10], Shaders.lIIllIIIlIlIllIl[11], Shaders.lIIllIIIlIlIllIl[12], Shaders.lIIllIIIlIlIllIl[13], Shaders.lIIllIIIlIlIllIl[14], Shaders.lIIllIIIlIlIllIl[15], Shaders.lIIllIIIlIlIllIl[16], Shaders.lIIllIIIlIlIllIl[17], Shaders.lIIllIIIlIlIllIl[18], Shaders.lIIllIIIlIlIllIl[19], Shaders.lIIllIIIlIlIllIl[20], Shaders.lIIllIIIlIlIllIl[21], Shaders.lIIllIIIlIlIllIl[22], Shaders.lIIllIIIlIlIllIl[23], Shaders.lIIllIIIlIlIllIl[24], Shaders.lIIllIIIlIlIllIl[25], Shaders.lIIllIIIlIlIllIl[26], Shaders.lIIllIIIlIlIllIl[27], Shaders.lIIllIIIlIlIllIl[28], Shaders.lIIllIIIlIlIllIl[29], Shaders.lIIllIIIlIlIllIl[30], Shaders.lIIllIIIlIlIllIl[31], Shaders.lIIllIIIlIlIllIl[32], Shaders.lIIllIIIlIlIllIl[33], Shaders.lIIllIIIlIlIllIl[34], Shaders.lIIllIIIlIlIllIl[35] };
        programBackups = new int[] { 0, 0, 1, 2, 1, 2, 2, 3, 7, 7, 7, 7, 7, 7, 2, 3, 3, 2, 2, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 30 };
        Shaders.programsID = new int[33];
        Shaders.programsRef = new int[33];
        Shaders.programIDCopyDepth = 0;
        Shaders.programsDrawBufSettings = new String[33];
        Shaders.newDrawBufSetting = null;
        Shaders.programsDrawBuffers = new IntBuffer[33];
        Shaders.activeDrawBuffers = null;
        Shaders.programsColorAtmSettings = new String[33];
        Shaders.newColorAtmSetting = null;
        Shaders.activeColorAtmSettings = null;
        Shaders.programsCompositeMipmapSetting = new int[33];
        Shaders.newCompositeMipmapSetting = 0;
        Shaders.activeCompositeMipmapSetting = 0;
        Shaders.loadedShaders = null;
        Shaders.shadersConfig = null;
        Shaders.defaultTexture = null;
        Shaders.normalMapEnabled = false;
        Shaders.shadowHardwareFilteringEnabled = new boolean[2];
        Shaders.shadowMipmapEnabled = new boolean[2];
        Shaders.shadowFilterNearest = new boolean[2];
        Shaders.shadowColorMipmapEnabled = new boolean[8];
        Shaders.shadowColorFilterNearest = new boolean[8];
        Shaders.configTweakBlockDamage = true;
        Shaders.configCloudShadow = true;
        Shaders.configHandDepthMul = 0.125f;
        Shaders.configRenderResMul = 1.0f;
        Shaders.configShadowResMul = 1.0f;
        Shaders.configTexMinFilB = 0;
        Shaders.configTexMinFilN = 0;
        Shaders.configTexMinFilS = 0;
        Shaders.configTexMagFilB = 0;
        Shaders.configTexMagFilN = 0;
        Shaders.configTexMagFilS = 0;
        Shaders.configShadowClipFrustrum = true;
        Shaders.configNormalMap = true;
        Shaders.configSpecularMap = true;
        Shaders.configOldLighting = new PropertyDefaultTrueFalse(Shaders.lIIllIIIlIlIllIl[36], Shaders.lIIllIIIlIlIllIl[37], 2);
        Shaders.configAntialiasingLevel = 0;
        texMinFilDesc = new String[] { Shaders.lIIllIIIlIlIllIl[38], Shaders.lIIllIIIlIlIllIl[39], Shaders.lIIllIIIlIlIllIl[40] };
        texMagFilDesc = new String[] { Shaders.lIIllIIIlIlIllIl[41], Shaders.lIIllIIIlIlIllIl[42] };
        texMinFilValue = new int[] { 9728, 9984, 9986 };
        texMagFilValue = new int[] { 9728, 9729 };
        Shaders.shaderPack = null;
        Shaders.shaderPackLoaded = false;
        Shaders.packNameNone = Shaders.lIIllIIIlIlIllIl[43];
        Shaders.packNameDefault = Shaders.lIIllIIIlIlIllIl[44];
        Shaders.shaderpacksdirname = Shaders.lIIllIIIlIlIllIl[45];
        Shaders.optionsfilename = Shaders.lIIllIIIlIlIllIl[46];
        Minecraft.getMinecraft();
        Shaders.shadersdir = new File(Minecraft.mcDataDir, Shaders.lIIllIIIlIlIllIl[47]);
        Minecraft.getMinecraft();
        Shaders.shaderpacksdir = new File(Minecraft.mcDataDir, Shaders.shaderpacksdirname);
        Minecraft.getMinecraft();
        Shaders.configFile = new File(Minecraft.mcDataDir, Shaders.optionsfilename);
        Shaders.shaderPackOptions = null;
        Shaders.shaderPackProfiles = null;
        Shaders.shaderPackGuiScreens = null;
        Shaders.shaderPackClouds = new PropertyDefaultFastFancyOff(Shaders.lIIllIIIlIlIllIl[48], Shaders.lIIllIIIlIlIllIl[49], 0);
        Shaders.shaderPackOldLighting = new PropertyDefaultTrueFalse(Shaders.lIIllIIIlIlIllIl[50], Shaders.lIIllIIIlIlIllIl[51], 0);
        Shaders.shaderPackDynamicHandLight = new PropertyDefaultTrueFalse(Shaders.lIIllIIIlIlIllIl[52], Shaders.lIIllIIIlIlIllIl[53], 0);
        Shaders.shaderPackResources = new HashMap();
        Shaders.currentWorld = null;
        Shaders.shaderPackDimensions = new ArrayList();
        saveFinalShaders = System.getProperty(Shaders.lIIllIIIlIlIllIl[54], Shaders.lIIllIIIlIlIllIl[55]).equals(Shaders.lIIllIIIlIlIllIl[56]);
        Shaders.blockLightLevel05 = 0.5f;
        Shaders.blockLightLevel06 = 0.6f;
        Shaders.blockLightLevel08 = 0.8f;
        Shaders.aoLevel = 0.8f;
        Shaders.blockAoLight = 1.0f - Shaders.aoLevel;
        Shaders.sunPathRotation = 0.0f;
        Shaders.shadowAngleInterval = 0.0f;
        Shaders.fogMode = 0;
        Shaders.shadowIntervalSize = 2.0f;
        Shaders.terrainIconSize = 16;
        Shaders.terrainTextureSize = new int[2];
        Shaders.noiseTextureEnabled = false;
        Shaders.noiseTextureResolution = 256;
        dfbColorTexturesA = new int[16];
        colorTexturesToggle = new int[8];
        colorTextureTextureImageUnit = new int[] { 0, 1, 2, 3, 7, 8, 9, 10 };
        programsToggleColorTextures = new boolean[33][8];
        bigBuffer = BufferUtils.createByteBuffer(2196).limit(0);
        faProjection = new float[16];
        faProjectionInverse = new float[16];
        faModelView = new float[16];
        faModelViewInverse = new float[16];
        faShadowProjection = new float[16];
        faShadowProjectionInverse = new float[16];
        faShadowModelView = new float[16];
        faShadowModelViewInverse = new float[16];
        projection = nextFloatBuffer(16);
        projectionInverse = nextFloatBuffer(16);
        modelView = nextFloatBuffer(16);
        modelViewInverse = nextFloatBuffer(16);
        shadowProjection = nextFloatBuffer(16);
        shadowProjectionInverse = nextFloatBuffer(16);
        shadowModelView = nextFloatBuffer(16);
        shadowModelViewInverse = nextFloatBuffer(16);
        previousProjection = nextFloatBuffer(16);
        previousModelView = nextFloatBuffer(16);
        tempMatrixDirectBuffer = nextFloatBuffer(16);
        tempDirectFloatBuffer = nextFloatBuffer(16);
        dfbColorTextures = nextIntBuffer(16);
        dfbDepthTextures = nextIntBuffer(3);
        sfbColorTextures = nextIntBuffer(8);
        sfbDepthTextures = nextIntBuffer(2);
        dfbDrawBuffers = nextIntBuffer(8);
        sfbDrawBuffers = nextIntBuffer(8);
        drawBuffersNone = nextIntBuffer(8);
        drawBuffersAll = nextIntBuffer(8);
        drawBuffersClear0 = nextIntBuffer(8);
        drawBuffersClear1 = nextIntBuffer(8);
        drawBuffersClearColor = nextIntBuffer(8);
        drawBuffersColorAtt0 = nextIntBuffer(8);
        drawBuffersBuffer = nextIntBufferArray(33, 8);
        Shaders.drawBuffersNone.limit(0);
        Shaders.drawBuffersColorAtt0.put(36064).position(0).limit(1);
        gbufferFormatPattern = Pattern.compile(Shaders.lIIllIIIlIlIllIl[57]);
        gbufferMipmapEnabledPattern = Pattern.compile(Shaders.lIIllIIIlIlIllIl[58]);
        formatNames = new String[] { Shaders.lIIllIIIlIlIllIl[59], Shaders.lIIllIIIlIlIllIl[60], Shaders.lIIllIIIlIlIllIl[61], Shaders.lIIllIIIlIlIllIl[62], Shaders.lIIllIIIlIlIllIl[63], Shaders.lIIllIIIlIlIllIl[64], Shaders.lIIllIIIlIlIllIl[65], Shaders.lIIllIIIlIlIllIl[66], Shaders.lIIllIIIlIlIllIl[67], Shaders.lIIllIIIlIlIllIl[68], Shaders.lIIllIIIlIlIllIl[69], Shaders.lIIllIIIlIlIllIl[70], Shaders.lIIllIIIlIlIllIl[71], Shaders.lIIllIIIlIlIllIl[72], Shaders.lIIllIIIlIlIllIl[73], Shaders.lIIllIIIlIlIllIl[74], Shaders.lIIllIIIlIlIllIl[75], Shaders.lIIllIIIlIlIllIl[76], Shaders.lIIllIIIlIlIllIl[77], Shaders.lIIllIIIlIlIllIl[78], Shaders.lIIllIIIlIlIllIl[79], Shaders.lIIllIIIlIlIllIl[80], Shaders.lIIllIIIlIlIllIl[81], Shaders.lIIllIIIlIlIllIl[82], Shaders.lIIllIIIlIlIllIl[83], Shaders.lIIllIIIlIlIllIl[84], Shaders.lIIllIIIlIlIllIl[85], Shaders.lIIllIIIlIlIllIl[86] };
        formatIds = new int[] { 33321, 33323, 32849, 32856, 36756, 36757, 36758, 36759, 33322, 33324, 32852, 32859, 36760, 36761, 36762, 36763, 33326, 33328, 34837, 34836, 33333, 33339, 36227, 36226, 33334, 33340, 36209, 36208 };
        patternLoadEntityDataMap = Pattern.compile(Shaders.lIIllIIIlIlIllIl[87]);
        Shaders.entityData = new int[32];
        Shaders.entityDataIndex = 0;
    }
    
    private static ByteBuffer nextByteBuffer(final int n) {
        final ByteBuffer bigBuffer = Shaders.bigBuffer;
        final int limit = bigBuffer.limit();
        bigBuffer.position(limit).limit(limit + n);
        return bigBuffer.slice();
    }
    
    private static IntBuffer nextIntBuffer(final int n) {
        final ByteBuffer bigBuffer = Shaders.bigBuffer;
        final int limit = bigBuffer.limit();
        bigBuffer.position(limit).limit(limit + n * 4);
        return bigBuffer.asIntBuffer();
    }
    
    private static FloatBuffer nextFloatBuffer(final int n) {
        final ByteBuffer bigBuffer = Shaders.bigBuffer;
        final int limit = bigBuffer.limit();
        bigBuffer.position(limit).limit(limit + n * 4);
        return bigBuffer.asFloatBuffer();
    }
    
    private static IntBuffer[] nextIntBufferArray(final int n, final int n2) {
        final IntBuffer[] array = new IntBuffer[n];
        for (int i = 0; i < n; ++i) {
            array[i] = nextIntBuffer(n2);
        }
        return array;
    }
    
    public static void loadConfig() {
        SMCLog.info(Shaders.lIIllIIIlIlIllIl[88]);
        try {
            if (!Shaders.shaderpacksdir.exists()) {
                Shaders.shaderpacksdir.mkdir();
            }
        }
        catch (Exception ex) {
            SMCLog.severe(Shaders.lIIllIIIlIlIllIl[89] + Shaders.shaderpacksdir);
        }
        (Shaders.shadersConfig = new PropertiesOrdered()).setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), Shaders.lIIllIIIlIlIllIl[90]);
        if (Shaders.configFile.exists()) {
            try {
                final FileReader fileReader = new FileReader(Shaders.configFile);
                Shaders.shadersConfig.load(fileReader);
                fileReader.close();
            }
            catch (Exception ex2) {}
        }
        if (!Shaders.configFile.exists()) {
            try {
                storeConfig();
            }
            catch (Exception ex3) {}
        }
        final EnumShaderOption[] values = EnumShaderOption.values();
        for (int i = 0; i < values.length; ++i) {
            final EnumShaderOption enumShaderOption = values[i];
            setEnumShaderOption(enumShaderOption, Shaders.shadersConfig.getProperty(enumShaderOption.getPropertyKey(), enumShaderOption.getValueDefault()));
        }
        loadShaderPack();
    }
    
    private static void setEnumShaderOption(final EnumShaderOption enumShaderOption, String valueDefault) {
        if (valueDefault == null) {
            valueDefault = enumShaderOption.getValueDefault();
        }
        switch (NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[enumShaderOption.ordinal()]) {
            case 1: {
                Shaders.configAntialiasingLevel = Config.parseInt(valueDefault, 0);
                break;
            }
            case 2: {
                Shaders.configNormalMap = Config.parseBoolean(valueDefault, true);
                break;
            }
            case 3: {
                Shaders.configSpecularMap = Config.parseBoolean(valueDefault, true);
                break;
            }
            case 4: {
                Shaders.configRenderResMul = Config.parseFloat(valueDefault, 1.0f);
                break;
            }
            case 5: {
                Shaders.configShadowResMul = Config.parseFloat(valueDefault, 1.0f);
                break;
            }
            case 6: {
                Shaders.configHandDepthMul = Config.parseFloat(valueDefault, 0.125f);
                break;
            }
            case 7: {
                Shaders.configCloudShadow = Config.parseBoolean(valueDefault, true);
                break;
            }
            case 8: {
                Shaders.configOldLighting.setPropertyValue(valueDefault);
                break;
            }
            case 9: {
                Shaders.currentshadername = valueDefault;
                break;
            }
            case 10: {
                Shaders.configTweakBlockDamage = Config.parseBoolean(valueDefault, true);
                break;
            }
            case 11: {
                Shaders.configShadowClipFrustrum = Config.parseBoolean(valueDefault, true);
                break;
            }
            case 12: {
                Shaders.configTexMinFilB = Config.parseInt(valueDefault, 0);
                break;
            }
            case 13: {
                Shaders.configTexMinFilN = Config.parseInt(valueDefault, 0);
                break;
            }
            case 14: {
                Shaders.configTexMinFilS = Config.parseInt(valueDefault, 0);
                break;
            }
            case 15: {
                Shaders.configTexMagFilB = Config.parseInt(valueDefault, 0);
                break;
            }
            case 16: {
                Shaders.configTexMagFilB = Config.parseInt(valueDefault, 0);
                break;
            }
            case 17: {
                Shaders.configTexMagFilB = Config.parseInt(valueDefault, 0);
                break;
            }
            default: {
                throw new IllegalArgumentException(Shaders.lIIllIIIlIlIllIl[91] + enumShaderOption);
            }
        }
    }
    
    public static void storeConfig() {
        SMCLog.info(Shaders.lIIllIIIlIlIllIl[92]);
        if (Shaders.shadersConfig == null) {
            Shaders.shadersConfig = new PropertiesOrdered();
        }
        final EnumShaderOption[] values = EnumShaderOption.values();
        for (int i = 0; i < values.length; ++i) {
            final EnumShaderOption enumShaderOption = values[i];
            Shaders.shadersConfig.setProperty(enumShaderOption.getPropertyKey(), getEnumShaderOption(enumShaderOption));
        }
        try {
            final FileWriter fileWriter = new FileWriter(Shaders.configFile);
            Shaders.shadersConfig.store(fileWriter, null);
            fileWriter.close();
        }
        catch (Exception ex) {
            SMCLog.severe(Shaders.lIIllIIIlIlIllIl[93] + ex.getClass().getName() + Shaders.lIIllIIIlIlIllIl[94] + ex.getMessage());
        }
    }
    
    public static String getEnumShaderOption(final EnumShaderOption enumShaderOption) {
        switch (NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[enumShaderOption.ordinal()]) {
            case 1: {
                return Integer.toString(Shaders.configAntialiasingLevel);
            }
            case 2: {
                return Boolean.toString(Shaders.configNormalMap);
            }
            case 3: {
                return Boolean.toString(Shaders.configSpecularMap);
            }
            case 4: {
                return Float.toString(Shaders.configRenderResMul);
            }
            case 5: {
                return Float.toString(Shaders.configShadowResMul);
            }
            case 6: {
                return Float.toString(Shaders.configHandDepthMul);
            }
            case 7: {
                return Boolean.toString(Shaders.configCloudShadow);
            }
            case 8: {
                return Shaders.configOldLighting.getPropertyValue();
            }
            case 9: {
                return Shaders.currentshadername;
            }
            case 10: {
                return Boolean.toString(Shaders.configTweakBlockDamage);
            }
            case 11: {
                return Boolean.toString(Shaders.configShadowClipFrustrum);
            }
            case 12: {
                return Integer.toString(Shaders.configTexMinFilB);
            }
            case 13: {
                return Integer.toString(Shaders.configTexMinFilN);
            }
            case 14: {
                return Integer.toString(Shaders.configTexMinFilS);
            }
            case 15: {
                return Integer.toString(Shaders.configTexMagFilB);
            }
            case 16: {
                return Integer.toString(Shaders.configTexMagFilB);
            }
            case 17: {
                return Integer.toString(Shaders.configTexMagFilB);
            }
            default: {
                throw new IllegalArgumentException(Shaders.lIIllIIIlIlIllIl[95] + enumShaderOption);
            }
        }
    }
    
    public static void setShaderPack(final String currentshadername) {
        Shaders.currentshadername = currentshadername;
        Shaders.shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), currentshadername);
        loadShaderPack();
    }
    
    public static void loadShaderPack() {
        final boolean shaderPackLoaded = Shaders.shaderPackLoaded;
        final boolean oldLighting = isOldLighting();
        Shaders.shaderPackLoaded = false;
        if (Shaders.shaderPack != null) {
            Shaders.shaderPack.close();
            Shaders.shaderPack = null;
            Shaders.shaderPackResources.clear();
            Shaders.shaderPackDimensions.clear();
            Shaders.shaderPackOptions = null;
            Shaders.shaderPackProfiles = null;
            Shaders.shaderPackGuiScreens = null;
            Shaders.shaderPackClouds.resetValue();
            Shaders.shaderPackDynamicHandLight.resetValue();
            Shaders.shaderPackOldLighting.resetValue();
        }
        boolean b = false;
        if (Config.isAntialiasing()) {
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[96] + Config.getAntialiasingLevel() + Shaders.lIIllIIIlIlIllIl[97]);
            b = true;
        }
        if (Config.isAnisotropicFiltering()) {
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[98] + Config.getAnisotropicFilterLevel() + Shaders.lIIllIIIlIlIllIl[99]);
            b = true;
        }
        if (Config.isFastRender()) {
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[100]);
            b = true;
        }
        final String property = Shaders.shadersConfig.getProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), Shaders.packNameDefault);
        if (!property.isEmpty() && !property.equals(Shaders.packNameNone) && !b) {
            if (property.equals(Shaders.packNameDefault)) {
                Shaders.shaderPack = new ShaderPackDefault();
                Shaders.shaderPackLoaded = true;
            }
            else {
                try {
                    final File file = new File(Shaders.shaderpacksdir, property);
                    if (file.isDirectory()) {
                        Shaders.shaderPack = new ShaderPackFolder(property, file);
                        Shaders.shaderPackLoaded = true;
                    }
                    else if (file.isFile() && property.toLowerCase().endsWith(Shaders.lIIllIIIlIlIllIl[101])) {
                        Shaders.shaderPack = new ShaderPackZip(property, file);
                        Shaders.shaderPackLoaded = true;
                    }
                }
                catch (Exception ex) {}
            }
        }
        if (Shaders.shaderPack != null) {
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[102] + getShaderPackName());
        }
        else {
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[103]);
            Shaders.shaderPack = new ShaderPackNone();
        }
        loadShaderPackResources();
        loadShaderPackDimensions();
        Shaders.shaderPackOptions = loadShaderPackOptions();
        loadShaderPackProperties();
        final boolean b2 = Shaders.shaderPackLoaded ^ shaderPackLoaded;
        final boolean b3 = isOldLighting() ^ oldLighting;
        if (b2 || b3) {
            DefaultVertexFormats.updateVertexFormats();
            if (Reflector.LightUtil.exists()) {
                Reflector.LightUtil_itemConsumer.setValue(null);
                Reflector.LightUtil_tessellator.setValue(null);
            }
            updateBlockLightLevel();
            Shaders.mc.func_175603_A();
        }
    }
    
    private static void loadShaderPackDimensions() {
        Shaders.shaderPackDimensions.clear();
        final StringBuffer sb = new StringBuffer();
        for (int i = -128; i <= 128; ++i) {
            if (Shaders.shaderPack.hasDirectory(Shaders.lIIllIIIlIlIllIl[104] + i)) {
                Shaders.shaderPackDimensions.add(i);
                sb.append(Shaders.lIIllIIIlIlIllIl[105] + i);
            }
        }
        if (sb.length() > 0) {
            Config.dbg(Shaders.lIIllIIIlIlIllIl[106] + (Object)sb);
        }
    }
    
    private static void loadShaderPackProperties() {
        Shaders.shaderPackClouds.resetValue();
        Shaders.shaderPackDynamicHandLight.resetValue();
        Shaders.shaderPackOldLighting.resetValue();
        if (Shaders.shaderPack != null) {
            final String s = Shaders.lIIllIIIlIlIllIl[107];
            try {
                final InputStream resourceAsStream = Shaders.shaderPack.getResourceAsStream(s);
                if (resourceAsStream == null) {
                    return;
                }
                final PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(resourceAsStream);
                resourceAsStream.close();
                Shaders.shaderPackClouds.loadFrom(propertiesOrdered);
                Shaders.shaderPackDynamicHandLight.loadFrom(propertiesOrdered);
                Shaders.shaderPackOldLighting.loadFrom(propertiesOrdered);
                Shaders.shaderPackProfiles = ShaderPackParser.parseProfiles(propertiesOrdered, Shaders.shaderPackOptions);
                Shaders.shaderPackGuiScreens = ShaderPackParser.parseGuiScreens(propertiesOrdered, Shaders.shaderPackProfiles, Shaders.shaderPackOptions);
            }
            catch (IOException ex) {
                Config.warn(Shaders.lIIllIIIlIlIllIl[108] + s);
            }
        }
    }
    
    public static ShaderOption[] getShaderPackOptions(final String s) {
        ShaderOption[] array = Shaders.shaderPackOptions.clone();
        if (Shaders.shaderPackGuiScreens == null) {
            if (Shaders.shaderPackProfiles != null) {
                array = (ShaderOption[])Config.addObjectToArray(array, new ShaderOptionProfile(Shaders.shaderPackProfiles, array), 0);
            }
            return getVisibleOptions(array);
        }
        final ShaderOption[] array2 = Shaders.shaderPackGuiScreens.get((s != null) ? (Shaders.lIIllIIIlIlIllIl[109] + s) : Shaders.lIIllIIIlIlIllIl[110]);
        if (array2 == null) {
            return new ShaderOption[0];
        }
        final ArrayList<ShaderOption> list = new ArrayList<ShaderOption>();
        for (int i = 0; i < array2.length; ++i) {
            final ShaderOption shaderOption = array2[i];
            if (shaderOption == null) {
                list.add(null);
            }
            else if (shaderOption instanceof ShaderOptionRest) {
                list.addAll(Arrays.asList(getShaderOptionsRest(Shaders.shaderPackGuiScreens, array)));
            }
            else {
                list.add(shaderOption);
            }
        }
        return list.toArray(new ShaderOption[list.size()]);
    }
    
    private static ShaderOption[] getShaderOptionsRest(final Map map, final ShaderOption[] array) {
        final HashSet<String> set = new HashSet<String>();
        final Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            final ShaderOption[] array2 = (Object)map.get(iterator.next());
            for (int i = 0; i < array2.length; ++i) {
                final ShaderOption shaderOption = array2[i];
                if (shaderOption != null) {
                    set.add(shaderOption.getName());
                }
            }
        }
        final ArrayList<ShaderOption> list = new ArrayList<ShaderOption>();
        for (int j = 0; j < array.length; ++j) {
            final ShaderOption shaderOption2 = array[j];
            if (shaderOption2.isVisible() && !set.contains(shaderOption2.getName())) {
                list.add(shaderOption2);
            }
        }
        return list.toArray(new ShaderOption[list.size()]);
    }
    
    public static ShaderOption getShaderOption(final String s) {
        return ShaderUtils.getShaderOption(s, Shaders.shaderPackOptions);
    }
    
    public static ShaderOption[] getShaderPackOptions() {
        return Shaders.shaderPackOptions;
    }
    
    private static ShaderOption[] getVisibleOptions(final ShaderOption[] array) {
        final ArrayList<ShaderOption> list = new ArrayList<ShaderOption>();
        for (int i = 0; i < array.length; ++i) {
            final ShaderOption shaderOption = array[i];
            if (shaderOption.isVisible()) {
                list.add(shaderOption);
            }
        }
        return list.toArray(new ShaderOption[list.size()]);
    }
    
    public static void saveShaderPackOptions() {
        saveShaderPackOptions(Shaders.shaderPackOptions, Shaders.shaderPack);
    }
    
    private static void saveShaderPackOptions(final ShaderOption[] array, final IShaderPack shaderPack) {
        final Properties properties = new Properties();
        if (Shaders.shaderPackOptions != null) {
            for (int i = 0; i < array.length; ++i) {
                final ShaderOption shaderOption = array[i];
                if (shaderOption.isChanged() && shaderOption.isEnabled()) {
                    properties.setProperty(shaderOption.getName(), shaderOption.getValue());
                }
            }
        }
        try {
            saveOptionProperties(shaderPack, properties);
        }
        catch (IOException ex) {
            Config.warn(Shaders.lIIllIIIlIlIllIl[111] + Shaders.shaderPack.getName());
            ex.printStackTrace();
        }
    }
    
    private static void saveOptionProperties(final IShaderPack shaderPack, final Properties properties) throws IOException {
        final String string = String.valueOf(Shaders.shaderpacksdirname) + Shaders.lIIllIIIlIlIllIl[112] + shaderPack.getName() + Shaders.lIIllIIIlIlIllIl[113];
        Minecraft.getMinecraft();
        final File file = new File(Minecraft.mcDataDir, string);
        if (properties.isEmpty()) {
            file.delete();
        }
        else {
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            properties.store(fileOutputStream, null);
            fileOutputStream.flush();
            fileOutputStream.close();
        }
    }
    
    private static ShaderOption[] loadShaderPackOptions() {
        try {
            final ShaderOption[] shaderPackOptions = ShaderPackParser.parseShaderPackOptions(Shaders.shaderPack, Shaders.programNames, Shaders.shaderPackDimensions);
            final Properties loadOptionProperties = loadOptionProperties(Shaders.shaderPack);
            for (int i = 0; i < shaderPackOptions.length; ++i) {
                final ShaderOption shaderOption = shaderPackOptions[i];
                final String property = loadOptionProperties.getProperty(shaderOption.getName());
                if (property != null) {
                    shaderOption.resetValue();
                    if (!shaderOption.setValue(property)) {
                        Config.warn(Shaders.lIIllIIIlIlIllIl[114] + shaderOption.getName() + Shaders.lIIllIIIlIlIllIl[115] + property);
                    }
                }
            }
            return shaderPackOptions;
        }
        catch (IOException ex) {
            Config.warn(Shaders.lIIllIIIlIlIllIl[116] + Shaders.shaderPack.getName());
            ex.printStackTrace();
            return null;
        }
    }
    
    private static Properties loadOptionProperties(final IShaderPack shaderPack) throws IOException {
        final Properties properties = new Properties();
        final String string = String.valueOf(Shaders.shaderpacksdirname) + Shaders.lIIllIIIlIlIllIl[117] + shaderPack.getName() + Shaders.lIIllIIIlIlIllIl[118];
        Minecraft.getMinecraft();
        final File file = new File(Minecraft.mcDataDir, string);
        if (file.exists() && file.isFile() && file.canRead()) {
            final FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
            fileInputStream.close();
            return properties;
        }
        return properties;
    }
    
    public static ShaderOption[] getChangedOptions(final ShaderOption[] array) {
        final ArrayList<ShaderOption> list = new ArrayList<ShaderOption>();
        for (int i = 0; i < array.length; ++i) {
            final ShaderOption shaderOption = array[i];
            if (shaderOption.isEnabled() && shaderOption.isChanged()) {
                list.add(shaderOption);
            }
        }
        return list.toArray(new ShaderOption[list.size()]);
    }
    
    private static String applyOptions(String sourceLine, final ShaderOption[] array) {
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; ++i) {
                final ShaderOption shaderOption = array[i];
                shaderOption.getName();
                if (shaderOption.matchesLine(sourceLine)) {
                    sourceLine = shaderOption.getSourceLine();
                    break;
                }
            }
            return sourceLine;
        }
        return sourceLine;
    }
    
    static ArrayList listOfShaders() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add(Shaders.packNameNone);
        list.add(Shaders.packNameDefault);
        try {
            if (!Shaders.shaderpacksdir.exists()) {
                Shaders.shaderpacksdir.mkdir();
            }
            final File[] listFiles = Shaders.shaderpacksdir.listFiles();
            for (int i = 0; i < listFiles.length; ++i) {
                final File file = listFiles[i];
                final String name = file.getName();
                if (file.isDirectory()) {
                    final File file2 = new File(file, Shaders.lIIllIIIlIlIllIl[119]);
                    if (file2.exists() && file2.isDirectory()) {
                        list.add(name);
                    }
                }
                else if (file.isFile() && name.toLowerCase().endsWith(Shaders.lIIllIIIlIlIllIl[120])) {
                    list.add(name);
                }
            }
        }
        catch (Exception ex) {}
        return list;
    }
    
    static String versiontostring(final int n) {
        final String string = Integer.toString(n);
        return String.valueOf(Integer.toString(Integer.parseInt(string.substring(1, 3)))) + Shaders.lIIllIIIlIlIllIl[121] + Integer.toString(Integer.parseInt(string.substring(3, 5))) + Shaders.lIIllIIIlIlIllIl[122] + Integer.toString(Integer.parseInt(string.substring(5)));
    }
    
    static void checkOptifine() {
    }
    
    public static int checkFramebufferStatus(final String s) {
        final int glCheckFramebufferStatusEXT = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
        if (glCheckFramebufferStatusEXT != 36053) {
            System.err.format(Shaders.lIIllIIIlIlIllIl[123], glCheckFramebufferStatusEXT, s);
        }
        return glCheckFramebufferStatusEXT;
    }
    
    public static int checkGLError(final String s) {
        final int glGetError = GL11.glGetError();
        if (glGetError != 0 && !false) {
            if (glGetError == 1286) {
                System.err.format(Shaders.lIIllIIIlIlIllIl[124], glGetError, GLU.gluErrorString(glGetError), EXTFramebufferObject.glCheckFramebufferStatusEXT(36160), s);
            }
            else {
                System.err.format(Shaders.lIIllIIIlIlIllIl[125], glGetError, GLU.gluErrorString(glGetError), s);
            }
        }
        return glGetError;
    }
    
    public static int checkGLError(final String s, final String s2) {
        final int glGetError = GL11.glGetError();
        if (glGetError != 0) {
            System.err.format(Shaders.lIIllIIIlIlIllIl[126], glGetError, GLU.gluErrorString(glGetError), s, s2);
        }
        return glGetError;
    }
    
    public static int checkGLError(final String s, final String s2, final String s3) {
        final int glGetError = GL11.glGetError();
        if (glGetError != 0) {
            System.err.format(Shaders.lIIllIIIlIlIllIl[127], glGetError, GLU.gluErrorString(glGetError), s, s2, s3);
        }
        return glGetError;
    }
    
    private static void printChat(final String s) {
        Minecraft.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(s));
    }
    
    private static void printChatAndLogError(final String s) {
        SMCLog.severe(s);
        Minecraft.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(s));
    }
    
    public static void printIntBuffer(final String s, final IntBuffer intBuffer) {
        final StringBuilder sb = new StringBuilder(128);
        sb.append(s).append(Shaders.lIIllIIIlIlIllIl[128]).append(intBuffer.position()).append(Shaders.lIIllIIIlIlIllIl[129]).append(intBuffer.limit()).append(Shaders.lIIllIIIlIlIllIl[130]).append(intBuffer.capacity()).append(Shaders.lIIllIIIlIlIllIl[131]);
        for (int limit = intBuffer.limit(), i = 0; i < limit; ++i) {
            sb.append(Shaders.lIIllIIIlIlIllIl[132]).append(intBuffer.get(i));
        }
        sb.append(Shaders.lIIllIIIlIlIllIl[133]);
        SMCLog.info(sb.toString());
    }
    
    public static void startup(final Minecraft mc) {
        checkShadersModInstalled();
        Shaders.mc = mc;
        Shaders.capabilities = GLContext.getCapabilities();
        Shaders.glVersionString = GL11.glGetString(7938);
        Shaders.glVendorString = GL11.glGetString(7936);
        Shaders.glRendererString = GL11.glGetString(7937);
        SMCLog.info(Shaders.lIIllIIIlIlIllIl[134]);
        SMCLog.info(Shaders.lIIllIIIlIlIllIl[135] + Shaders.glVersionString);
        SMCLog.info(Shaders.lIIllIIIlIlIllIl[136] + Shaders.glVendorString);
        SMCLog.info(Shaders.lIIllIIIlIlIllIl[137] + Shaders.glRendererString);
        SMCLog.info(Shaders.lIIllIIIlIlIllIl[138] + (Shaders.capabilities.OpenGL20 ? Shaders.lIIllIIIlIlIllIl[139] : Shaders.lIIllIIIlIlIllIl[140]) + (Shaders.capabilities.OpenGL21 ? Shaders.lIIllIIIlIlIllIl[141] : Shaders.lIIllIIIlIlIllIl[142]) + (Shaders.capabilities.OpenGL30 ? Shaders.lIIllIIIlIlIllIl[143] : Shaders.lIIllIIIlIlIllIl[144]) + (Shaders.capabilities.OpenGL32 ? Shaders.lIIllIIIlIlIllIl[145] : Shaders.lIIllIIIlIlIllIl[146]) + (Shaders.capabilities.OpenGL40 ? Shaders.lIIllIIIlIlIllIl[147] : Shaders.lIIllIIIlIlIllIl[148]));
        SMCLog.info(Shaders.lIIllIIIlIlIllIl[149] + GL11.glGetInteger(34852));
        SMCLog.info(Shaders.lIIllIIIlIlIllIl[150] + GL11.glGetInteger(36063));
        SMCLog.info(Shaders.lIIllIIIlIlIllIl[151] + GL11.glGetInteger(34930));
        Shaders.hasGlGenMipmap = Shaders.capabilities.OpenGL30;
        loadConfig();
    }
    
    private static String toStringYN(final boolean b) {
        return b ? Shaders.lIIllIIIlIlIllIl[152] : Shaders.lIIllIIIlIlIllIl[153];
    }
    
    public static void updateBlockLightLevel() {
        if (isOldLighting()) {
            Shaders.blockLightLevel05 = 0.5f;
            Shaders.blockLightLevel06 = 0.6f;
            Shaders.blockLightLevel08 = 0.8f;
        }
        else {
            Shaders.blockLightLevel05 = 1.0f;
            Shaders.blockLightLevel06 = 1.0f;
            Shaders.blockLightLevel08 = 1.0f;
        }
    }
    
    public static boolean isDynamicHandLight() {
        return Shaders.shaderPackDynamicHandLight.isDefault() || Shaders.shaderPackDynamicHandLight.isTrue();
    }
    
    public static boolean isOldLighting() {
        return Shaders.configOldLighting.isDefault() ? (Shaders.shaderPackOldLighting.isDefault() || Shaders.shaderPackOldLighting.isTrue()) : Shaders.configOldLighting.isTrue();
    }
    
    public static void init() {
        boolean b;
        if (!Shaders.isInitializedOnce) {
            Shaders.isInitializedOnce = true;
            b = true;
        }
        else {
            b = false;
        }
        if (!Shaders.isShaderPackInitialized) {
            checkGLError(Shaders.lIIllIIIlIlIllIl[154]);
            if (getShaderPackName() != null) {}
            if (!Shaders.capabilities.OpenGL20) {
                printChatAndLogError(Shaders.lIIllIIIlIlIllIl[155]);
            }
            if (!Shaders.capabilities.GL_EXT_framebuffer_object) {
                printChatAndLogError(Shaders.lIIllIIIlIlIllIl[156]);
            }
            Shaders.dfbDrawBuffers.position(0).limit(8);
            Shaders.dfbColorTextures.position(0).limit(16);
            Shaders.dfbDepthTextures.position(0).limit(3);
            Shaders.sfbDrawBuffers.position(0).limit(8);
            Shaders.sfbDepthTextures.position(0).limit(2);
            Shaders.sfbColorTextures.position(0).limit(8);
            Shaders.usedColorBuffers = 4;
            Shaders.usedDepthBuffers = 1;
            Shaders.usedShadowColorBuffers = 0;
            Shaders.usedShadowDepthBuffers = 0;
            Shaders.usedColorAttachs = 1;
            Shaders.usedDrawBuffers = 1;
            Arrays.fill(Shaders.gbuffersFormat, 6408);
            Arrays.fill(Shaders.shadowHardwareFilteringEnabled, false);
            Arrays.fill(Shaders.shadowMipmapEnabled, false);
            Arrays.fill(Shaders.shadowFilterNearest, false);
            Arrays.fill(Shaders.shadowColorMipmapEnabled, false);
            Arrays.fill(Shaders.shadowColorFilterNearest, false);
            Shaders.centerDepthSmoothEnabled = false;
            Shaders.noiseTextureEnabled = false;
            Shaders.sunPathRotation = 0.0f;
            Shaders.shadowIntervalSize = 2.0f;
            Shaders.aoLevel = 0.8f;
            Shaders.blockAoLight = 1.0f - Shaders.aoLevel;
            Shaders.useEntityAttrib = false;
            Shaders.useMidTexCoordAttrib = false;
            Shaders.useMultiTexCoord3Attrib = false;
            Shaders.useTangentAttrib = false;
            Shaders.waterShadowEnabled = false;
            Shaders.updateChunksErrorRecorded = false;
            updateBlockLightLevel();
            final ShaderProfile detectProfile = ShaderUtils.detectProfile(Shaders.shaderPackProfiles, Shaders.shaderPackOptions, false);
            String string = Shaders.lIIllIIIlIlIllIl[157];
            if (Shaders.currentWorld != null) {
                final int dimensionId = Shaders.currentWorld.provider.getDimensionId();
                if (Shaders.shaderPackDimensions.contains(dimensionId)) {
                    string = Shaders.lIIllIIIlIlIllIl[158] + dimensionId + Shaders.lIIllIIIlIlIllIl[159];
                }
            }
            if (Shaders.saveFinalShaders) {
                clearDirectory(new File(Shaders.shaderpacksdir, Shaders.lIIllIIIlIlIllIl[160]));
            }
            for (int i = 0; i < 33; ++i) {
                final String s = Shaders.programNames[i];
                if (s.equals(Shaders.lIIllIIIlIlIllIl[161])) {
                    Shaders.programsID[i] = (Shaders.programsRef[i] = 0);
                    Shaders.programsDrawBufSettings[i] = null;
                    Shaders.programsColorAtmSettings[i] = null;
                    Shaders.programsCompositeMipmapSetting[i] = 0;
                }
                else {
                    Shaders.newDrawBufSetting = null;
                    Shaders.newColorAtmSetting = null;
                    Shaders.newCompositeMipmapSetting = 0;
                    String s2 = String.valueOf(string) + s;
                    if (detectProfile != null && detectProfile.isProgramDisabled(s2)) {
                        SMCLog.info(Shaders.lIIllIIIlIlIllIl[162] + s2);
                        s2 = String.valueOf(string) + Shaders.lIIllIIIlIlIllIl[163];
                    }
                    final String string2 = Shaders.lIIllIIIlIlIllIl[164] + s2;
                    final int setupProgram = setupProgram(i, String.valueOf(string2) + Shaders.lIIllIIIlIlIllIl[165], String.valueOf(string2) + Shaders.lIIllIIIlIlIllIl[166]);
                    if (setupProgram > 0) {
                        SMCLog.info(Shaders.lIIllIIIlIlIllIl[167] + s2);
                    }
                    Shaders.programsID[i] = (Shaders.programsRef[i] = setupProgram);
                    Shaders.programsDrawBufSettings[i] = ((setupProgram != 0) ? Shaders.newDrawBufSetting : null);
                    Shaders.programsColorAtmSettings[i] = ((setupProgram != 0) ? Shaders.newColorAtmSetting : null);
                    Shaders.programsCompositeMipmapSetting[i] = ((setupProgram != 0) ? Shaders.newCompositeMipmapSetting : 0);
                }
            }
            final int glGetInteger = GL11.glGetInteger(34852);
            new HashMap();
            for (int j = 0; j < 33; ++j) {
                Arrays.fill(Shaders.programsToggleColorTextures[j], false);
                if (j == 29) {
                    Shaders.programsDrawBuffers[j] = null;
                }
                else if (Shaders.programsID[j] == 0) {
                    if (j == 30) {
                        Shaders.programsDrawBuffers[j] = Shaders.drawBuffersNone;
                    }
                    else {
                        Shaders.programsDrawBuffers[j] = Shaders.drawBuffersColorAtt0;
                    }
                }
                else {
                    final String s3 = Shaders.programsDrawBufSettings[j];
                    if (s3 != null) {
                        final IntBuffer intBuffer = Shaders.drawBuffersBuffer[j];
                        int length = s3.length();
                        if (length > Shaders.usedDrawBuffers) {
                            Shaders.usedDrawBuffers = length;
                        }
                        if (length > glGetInteger) {
                            length = glGetInteger;
                        }
                        (Shaders.programsDrawBuffers[j] = intBuffer).limit(length);
                        for (int k = 0; k < length; ++k) {
                            int n = 0;
                            if (s3.length() > k) {
                                final int usedShadowColorBuffers = s3.charAt(k) - '0';
                                if (j != 30) {
                                    if (usedShadowColorBuffers >= 0 && usedShadowColorBuffers <= 7) {
                                        Shaders.programsToggleColorTextures[j][usedShadowColorBuffers] = true;
                                        n = usedShadowColorBuffers + 36064;
                                        if (usedShadowColorBuffers > Shaders.usedColorAttachs) {
                                            Shaders.usedColorAttachs = usedShadowColorBuffers;
                                        }
                                        if (usedShadowColorBuffers > Shaders.usedColorBuffers) {
                                            Shaders.usedColorBuffers = usedShadowColorBuffers;
                                        }
                                    }
                                }
                                else if (usedShadowColorBuffers >= 0 && usedShadowColorBuffers <= 1) {
                                    n = usedShadowColorBuffers + 36064;
                                    if (usedShadowColorBuffers > Shaders.usedShadowColorBuffers) {
                                        Shaders.usedShadowColorBuffers = usedShadowColorBuffers;
                                    }
                                }
                            }
                            intBuffer.put(k, n);
                        }
                    }
                    else if (j != 30 && j != 31 && j != 32) {
                        Shaders.programsDrawBuffers[j] = Shaders.dfbDrawBuffers;
                        Shaders.usedDrawBuffers = Shaders.usedColorBuffers;
                        Arrays.fill(Shaders.programsToggleColorTextures[j], 0, Shaders.usedColorBuffers, true);
                    }
                    else {
                        Shaders.programsDrawBuffers[j] = Shaders.sfbDrawBuffers;
                    }
                }
            }
            Shaders.usedColorAttachs = Shaders.usedColorBuffers;
            Shaders.shadowPassInterval = ((Shaders.usedShadowDepthBuffers > 0) ? 1 : 0);
            Shaders.shouldSkipDefaultShadow = (Shaders.usedShadowDepthBuffers > 0);
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[168] + Shaders.usedColorBuffers);
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[169] + Shaders.usedDepthBuffers);
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[170] + Shaders.usedShadowColorBuffers);
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[171] + Shaders.usedShadowDepthBuffers);
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[172] + Shaders.usedColorAttachs);
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[173] + Shaders.usedDrawBuffers);
            Shaders.dfbDrawBuffers.position(0).limit(Shaders.usedDrawBuffers);
            Shaders.dfbColorTextures.position(0).limit(Shaders.usedColorBuffers * 2);
            for (int l = 0; l < Shaders.usedDrawBuffers; ++l) {
                Shaders.dfbDrawBuffers.put(l, 36064 + l);
            }
            if (Shaders.usedDrawBuffers > glGetInteger) {
                printChatAndLogError(Shaders.lIIllIIIlIlIllIl[174] + Shaders.usedDrawBuffers + Shaders.lIIllIIIlIlIllIl[175] + glGetInteger);
            }
            Shaders.sfbDrawBuffers.position(0).limit(Shaders.usedShadowColorBuffers);
            for (int n2 = 0; n2 < Shaders.usedShadowColorBuffers; ++n2) {
                Shaders.sfbDrawBuffers.put(n2, 36064 + n2);
            }
            for (int n3 = 0; n3 < 33; ++n3) {
                int n4;
                for (n4 = n3; Shaders.programsID[n4] == 0 && Shaders.programBackups[n4] != n4; n4 = Shaders.programBackups[n4]) {}
                if (n4 != n3 && n3 != 30) {
                    Shaders.programsID[n3] = Shaders.programsID[n4];
                    Shaders.programsDrawBufSettings[n3] = Shaders.programsDrawBufSettings[n4];
                    Shaders.programsDrawBuffers[n3] = Shaders.programsDrawBuffers[n4];
                }
            }
            resize();
            resizeShadow();
            if (Shaders.noiseTextureEnabled) {
                setupNoiseTexture();
            }
            if (Shaders.defaultTexture == null) {
                Shaders.defaultTexture = ShadersTex.createDefaultTexture();
            }
            GlStateManager.pushMatrix();
            GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
            preCelestialRotate();
            postCelestialRotate();
            GlStateManager.popMatrix();
            Shaders.isShaderPackInitialized = true;
            loadEntityDataMap();
            resetDisplayList();
            if (!b) {}
            checkGLError(Shaders.lIIllIIIlIlIllIl[176]);
        }
    }
    
    public static void resetDisplayList() {
        ++Shaders.numberResetDisplayList;
        Shaders.needResetModels = true;
        SMCLog.info(Shaders.lIIllIIIlIlIllIl[177]);
        Shaders.mc.renderGlobal.loadRenderers();
    }
    
    public static void resetDisplayListModels() {
        if (Shaders.needResetModels) {
            Shaders.needResetModels = false;
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[178]);
            for (final Render render : Shaders.mc.getRenderManager().getEntityRenderMap().values()) {
                if (render instanceof RendererLivingEntity) {
                    resetDisplayListModel(((RendererLivingEntity)render).getMainModel());
                }
            }
        }
    }
    
    public static void resetDisplayListModel(final ModelBase modelBase) {
        if (modelBase != null) {
            for (final ModelRenderer next : modelBase.boxList) {
                if (next instanceof ModelRenderer) {
                    resetDisplayListModelRenderer(next);
                }
            }
        }
    }
    
    public static void resetDisplayListModelRenderer(final ModelRenderer modelRenderer) {
        modelRenderer.resetDisplayList();
        if (modelRenderer.childModels != null) {
            for (int i = 0; i < modelRenderer.childModels.size(); ++i) {
                resetDisplayListModelRenderer((ModelRenderer)modelRenderer.childModels.get(i));
            }
        }
    }
    
    private static int setupProgram(final int n, final String s, final String s2) {
        checkGLError(Shaders.lIIllIIIlIlIllIl[179]);
        int glCreateProgramObjectARB = ARBShaderObjects.glCreateProgramObjectARB();
        checkGLError(Shaders.lIIllIIIlIlIllIl[180]);
        if (glCreateProgramObjectARB != 0) {
            Shaders.progUseEntityAttrib = false;
            Shaders.progUseMidTexCoordAttrib = false;
            Shaders.progUseTangentAttrib = false;
            final int vertShader = createVertShader(s);
            final int fragShader = createFragShader(s2);
            checkGLError(Shaders.lIIllIIIlIlIllIl[181]);
            if (vertShader == 0 && fragShader == 0) {
                ARBShaderObjects.glDeleteObjectARB(glCreateProgramObjectARB);
                glCreateProgramObjectARB = 0;
            }
            else {
                if (vertShader != 0) {
                    ARBShaderObjects.glAttachObjectARB(glCreateProgramObjectARB, vertShader);
                    checkGLError(Shaders.lIIllIIIlIlIllIl[182]);
                }
                if (fragShader != 0) {
                    ARBShaderObjects.glAttachObjectARB(glCreateProgramObjectARB, fragShader);
                    checkGLError(Shaders.lIIllIIIlIlIllIl[183]);
                }
                if (Shaders.progUseEntityAttrib) {
                    ARBVertexShader.glBindAttribLocationARB(glCreateProgramObjectARB, Shaders.entityAttrib, Shaders.lIIllIIIlIlIllIl[184]);
                    checkGLError(Shaders.lIIllIIIlIlIllIl[185]);
                }
                if (Shaders.progUseMidTexCoordAttrib) {
                    ARBVertexShader.glBindAttribLocationARB(glCreateProgramObjectARB, Shaders.midTexCoordAttrib, Shaders.lIIllIIIlIlIllIl[186]);
                    checkGLError(Shaders.lIIllIIIlIlIllIl[187]);
                }
                if (Shaders.progUseTangentAttrib) {
                    ARBVertexShader.glBindAttribLocationARB(glCreateProgramObjectARB, Shaders.tangentAttrib, Shaders.lIIllIIIlIlIllIl[188]);
                    checkGLError(Shaders.lIIllIIIlIlIllIl[189]);
                }
                ARBShaderObjects.glLinkProgramARB(glCreateProgramObjectARB);
                if (GL20.glGetProgrami(glCreateProgramObjectARB, 35714) != 1) {
                    SMCLog.severe(Shaders.lIIllIIIlIlIllIl[190] + glCreateProgramObjectARB);
                }
                printLogInfo(glCreateProgramObjectARB, String.valueOf(s) + Shaders.lIIllIIIlIlIllIl[191] + s2);
                if (vertShader != 0) {
                    ARBShaderObjects.glDetachObjectARB(glCreateProgramObjectARB, vertShader);
                    ARBShaderObjects.glDeleteObjectARB(vertShader);
                }
                if (fragShader != 0) {
                    ARBShaderObjects.glDetachObjectARB(glCreateProgramObjectARB, fragShader);
                    ARBShaderObjects.glDeleteObjectARB(fragShader);
                }
                Shaders.programsID[n] = glCreateProgramObjectARB;
                useProgram(n);
                ARBShaderObjects.glValidateProgramARB(glCreateProgramObjectARB);
                useProgram(0);
                printLogInfo(glCreateProgramObjectARB, String.valueOf(s) + Shaders.lIIllIIIlIlIllIl[192] + s2);
                if (GL20.glGetProgrami(glCreateProgramObjectARB, 35715) != 1) {
                    final String s3 = Shaders.lIIllIIIlIlIllIl[193];
                    printChatAndLogError(Shaders.lIIllIIIlIlIllIl[194] + s3 + Shaders.programNames[n] + s3);
                    ARBShaderObjects.glDeleteObjectARB(glCreateProgramObjectARB);
                    glCreateProgramObjectARB = 0;
                }
            }
        }
        return glCreateProgramObjectARB;
    }
    
    private static int createVertShader(final String s) {
        final int glCreateShaderObjectARB = ARBShaderObjects.glCreateShaderObjectARB(35633);
        if (glCreateShaderObjectARB == 0) {
            return 0;
        }
        final StringBuilder sb = new StringBuilder(131072);
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Shaders.shaderPack.getResourceAsStream(s)));
        }
        catch (Exception ex2) {
            try {
                bufferedReader = new BufferedReader(new FileReader(new File(s)));
            }
            catch (Exception ex3) {
                ARBShaderObjects.glDeleteObjectARB(glCreateShaderObjectARB);
                return 0;
            }
        }
        final ShaderOption[] changedOptions = getChangedOptions(Shaders.shaderPackOptions);
        if (bufferedReader != null) {
            try {
                final BufferedReader resolveIncludes = ShaderPackParser.resolveIncludes(bufferedReader, s, Shaders.shaderPack, 0);
                String line;
                while ((line = resolveIncludes.readLine()) != null) {
                    final String applyOptions = applyOptions(line, changedOptions);
                    sb.append(applyOptions).append('\n');
                    if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[195])) {
                        Shaders.useEntityAttrib = true;
                        Shaders.progUseEntityAttrib = true;
                    }
                    else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[196])) {
                        Shaders.useMidTexCoordAttrib = true;
                        Shaders.progUseMidTexCoordAttrib = true;
                    }
                    else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[197])) {
                        Shaders.useMultiTexCoord3Attrib = true;
                    }
                    else {
                        if (!applyOptions.matches(Shaders.lIIllIIIlIlIllIl[198])) {
                            continue;
                        }
                        Shaders.useTangentAttrib = true;
                        Shaders.progUseTangentAttrib = true;
                    }
                }
                resolveIncludes.close();
            }
            catch (Exception ex) {
                SMCLog.severe(Shaders.lIIllIIIlIlIllIl[199] + s + Shaders.lIIllIIIlIlIllIl[200]);
                ex.printStackTrace();
                ARBShaderObjects.glDeleteObjectARB(glCreateShaderObjectARB);
                return 0;
            }
        }
        if (Shaders.saveFinalShaders) {
            saveShader(s, sb.toString());
        }
        ARBShaderObjects.glShaderSourceARB(glCreateShaderObjectARB, sb);
        ARBShaderObjects.glCompileShaderARB(glCreateShaderObjectARB);
        if (GL20.glGetShaderi(glCreateShaderObjectARB, 35713) != 1) {
            SMCLog.severe(Shaders.lIIllIIIlIlIllIl[201] + s);
        }
        printShaderLogInfo(glCreateShaderObjectARB, s);
        return glCreateShaderObjectARB;
    }
    
    private static int createFragShader(final String s) {
        final int glCreateShaderObjectARB = ARBShaderObjects.glCreateShaderObjectARB(35632);
        if (glCreateShaderObjectARB == 0) {
            return 0;
        }
        final StringBuilder sb = new StringBuilder(131072);
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Shaders.shaderPack.getResourceAsStream(s)));
        }
        catch (Exception ex2) {
            try {
                bufferedReader = new BufferedReader(new FileReader(new File(s)));
            }
            catch (Exception ex3) {
                ARBShaderObjects.glDeleteObjectARB(glCreateShaderObjectARB);
                return 0;
            }
        }
        final ShaderOption[] changedOptions = getChangedOptions(Shaders.shaderPackOptions);
        if (bufferedReader != null) {
            try {
                final BufferedReader resolveIncludes = ShaderPackParser.resolveIncludes(bufferedReader, s, Shaders.shaderPack, 0);
                String line;
                while ((line = resolveIncludes.readLine()) != null) {
                    final String applyOptions = applyOptions(line, changedOptions);
                    sb.append(applyOptions).append('\n');
                    if (!applyOptions.matches(Shaders.lIIllIIIlIlIllIl[202])) {
                        if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[203])) {
                            if (Shaders.usedShadowDepthBuffers >= 1) {
                                continue;
                            }
                            Shaders.usedShadowDepthBuffers = 1;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[204])) {
                            Shaders.waterShadowEnabled = true;
                            if (Shaders.usedShadowDepthBuffers >= 2) {
                                continue;
                            }
                            Shaders.usedShadowDepthBuffers = 2;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[205])) {
                            if (Shaders.usedShadowDepthBuffers >= 1) {
                                continue;
                            }
                            Shaders.usedShadowDepthBuffers = 1;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[206])) {
                            if (Shaders.usedShadowDepthBuffers >= 2) {
                                continue;
                            }
                            Shaders.usedShadowDepthBuffers = 2;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[207])) {
                            if (Shaders.usedShadowColorBuffers >= 1) {
                                continue;
                            }
                            Shaders.usedShadowColorBuffers = 1;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[208])) {
                            if (Shaders.usedShadowColorBuffers >= 1) {
                                continue;
                            }
                            Shaders.usedShadowColorBuffers = 1;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[209])) {
                            if (Shaders.usedShadowColorBuffers >= 2) {
                                continue;
                            }
                            Shaders.usedShadowColorBuffers = 2;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[210])) {
                            if (Shaders.usedShadowColorBuffers >= 3) {
                                continue;
                            }
                            Shaders.usedShadowColorBuffers = 3;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[211])) {
                            if (Shaders.usedShadowColorBuffers >= 4) {
                                continue;
                            }
                            Shaders.usedShadowColorBuffers = 4;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[212])) {
                            if (Shaders.usedDepthBuffers >= 1) {
                                continue;
                            }
                            Shaders.usedDepthBuffers = 1;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[213])) {
                            if (Shaders.usedDepthBuffers >= 2) {
                                continue;
                            }
                            Shaders.usedDepthBuffers = 2;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[214])) {
                            if (Shaders.usedDepthBuffers >= 3) {
                                continue;
                            }
                            Shaders.usedDepthBuffers = 3;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[215])) {
                            if (Shaders.gbuffersFormat[1] != 6408) {
                                continue;
                            }
                            Shaders.gbuffersFormat[1] = 34836;
                        }
                        else if (Shaders.usedColorBuffers < 5 && applyOptions.matches(Shaders.lIIllIIIlIlIllIl[216])) {
                            Shaders.usedColorBuffers = 5;
                        }
                        else if (Shaders.usedColorBuffers < 6 && applyOptions.matches(Shaders.lIIllIIIlIlIllIl[217])) {
                            Shaders.usedColorBuffers = 6;
                        }
                        else if (Shaders.usedColorBuffers < 7 && applyOptions.matches(Shaders.lIIllIIIlIlIllIl[218])) {
                            Shaders.usedColorBuffers = 7;
                        }
                        else if (Shaders.usedColorBuffers < 8 && applyOptions.matches(Shaders.lIIllIIIlIlIllIl[219])) {
                            Shaders.usedColorBuffers = 8;
                        }
                        else if (Shaders.usedColorBuffers < 5 && applyOptions.matches(Shaders.lIIllIIIlIlIllIl[220])) {
                            Shaders.usedColorBuffers = 5;
                        }
                        else if (Shaders.usedColorBuffers < 6 && applyOptions.matches(Shaders.lIIllIIIlIlIllIl[221])) {
                            Shaders.usedColorBuffers = 6;
                        }
                        else if (Shaders.usedColorBuffers < 7 && applyOptions.matches(Shaders.lIIllIIIlIlIllIl[222])) {
                            Shaders.usedColorBuffers = 7;
                        }
                        else if (Shaders.usedColorBuffers < 8 && applyOptions.matches(Shaders.lIIllIIIlIlIllIl[223])) {
                            Shaders.usedColorBuffers = 8;
                        }
                        else if (Shaders.usedColorBuffers < 8 && applyOptions.matches(Shaders.lIIllIIIlIlIllIl[224])) {
                            Shaders.centerDepthSmoothEnabled = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[225])) {
                            final String[] split = applyOptions.split(Shaders.lIIllIIIlIlIllIl[226], 4);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[227] + split[2]);
                            Shaders.spShadowMapWidth = (Shaders.spShadowMapHeight = Integer.parseInt(split[2]));
                            Shaders.shadowMapWidth = (Shaders.shadowMapHeight = Math.round(Shaders.spShadowMapWidth * Shaders.configShadowResMul));
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[228])) {
                            final String[] split2 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[229]);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[230] + split2[1]);
                            Shaders.spShadowMapWidth = (Shaders.spShadowMapHeight = Integer.parseInt(split2[1]));
                            Shaders.shadowMapWidth = (Shaders.shadowMapHeight = Math.round(Shaders.spShadowMapWidth * Shaders.configShadowResMul));
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[231])) {
                            final String[] split3 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[232], 4);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[233] + split3[2]);
                            Shaders.shadowMapFOV = Float.parseFloat(split3[2]);
                            Shaders.shadowMapIsOrtho = false;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[234])) {
                            final String[] split4 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[235], 4);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[236] + split4[2]);
                            Shaders.shadowMapHalfPlane = Float.parseFloat(split4[2]);
                            Shaders.shadowMapIsOrtho = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[237])) {
                            final String[] split5 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[238]);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[239] + split5[1]);
                            Shaders.shadowMapHalfPlane = Float.parseFloat(split5[1]);
                            Shaders.shadowMapIsOrtho = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[240])) {
                            final String[] split6 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[241]);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[242] + split6[1]);
                            Shaders.shadowIntervalSize = Float.parseFloat(split6[1]);
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[243])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[244]);
                            Arrays.fill(Shaders.shadowMipmapEnabled, true);
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[245])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[246]);
                            Arrays.fill(Shaders.shadowColorMipmapEnabled, true);
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[247])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[248]);
                            Arrays.fill(Shaders.shadowHardwareFilteringEnabled, true);
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[249])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[250]);
                            Shaders.shadowHardwareFilteringEnabled[0] = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[251])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[252]);
                            Shaders.shadowHardwareFilteringEnabled[1] = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[253])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[254]);
                            Shaders.shadowMipmapEnabled[0] = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[255])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[256]);
                            Shaders.shadowMipmapEnabled[1] = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[257])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[258]);
                            Shaders.shadowColorMipmapEnabled[0] = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[259])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[260]);
                            Shaders.shadowColorMipmapEnabled[1] = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[261])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[262]);
                            Shaders.shadowFilterNearest[0] = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[263])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[264]);
                            Shaders.shadowFilterNearest[1] = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[265])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[266]);
                            Shaders.shadowColorFilterNearest[0] = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[267])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[268]);
                            Shaders.shadowColorFilterNearest[1] = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[269])) {
                            final String[] split7 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[270], 4);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[271] + split7[2]);
                            Shaders.wetnessHalfLife = Float.parseFloat(split7[2]);
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[272])) {
                            final String[] split8 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[273]);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[274] + split8[1]);
                            Shaders.wetnessHalfLife = Float.parseFloat(split8[1]);
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[275])) {
                            final String[] split9 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[276], 4);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[277] + split9[2]);
                            Shaders.drynessHalfLife = Float.parseFloat(split9[2]);
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[278])) {
                            final String[] split10 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[279]);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[280] + split10[1]);
                            Shaders.drynessHalfLife = Float.parseFloat(split10[1]);
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[281])) {
                            final String[] split11 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[282]);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[283] + split11[1]);
                            Shaders.eyeBrightnessHalflife = Float.parseFloat(split11[1]);
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[284])) {
                            final String[] split12 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[285]);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[286] + split12[1]);
                            Shaders.centerDepthSmoothHalflife = Float.parseFloat(split12[1]);
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[287])) {
                            final String[] split13 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[288]);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[289] + split13[1]);
                            Shaders.sunPathRotation = Float.parseFloat(split13[1]);
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[290])) {
                            final String[] split14 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[291]);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[292] + split14[1]);
                            Shaders.aoLevel = Float.parseFloat(split14[1]);
                            Shaders.blockAoLight = 1.0f - Shaders.aoLevel;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[293])) {
                            final int int1 = Integer.parseInt(applyOptions.split(Shaders.lIIllIIIlIlIllIl[294])[1]);
                            if (int1 > 1) {
                                SMCLog.info(Shaders.lIIllIIIlIlIllIl[295] + int1 + Shaders.lIIllIIIlIlIllIl[296]);
                                Shaders.superSamplingLevel = int1;
                            }
                            else {
                                Shaders.superSamplingLevel = 1;
                            }
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[297])) {
                            final String[] split15 = applyOptions.split(Shaders.lIIllIIIlIlIllIl[298]);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[299]);
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[300] + split15[1]);
                            Shaders.noiseTextureResolution = Integer.parseInt(split15[1]);
                            Shaders.noiseTextureEnabled = true;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[301])) {
                            final Matcher matcher = Shaders.gbufferFormatPattern.matcher(applyOptions);
                            matcher.matches();
                            final String group = matcher.group(1);
                            final String group2 = matcher.group(2);
                            final int bufferIndexFromString = getBufferIndexFromString(group);
                            final int textureFormatFromString = getTextureFormatFromString(group2);
                            if (bufferIndexFromString < 0 || textureFormatFromString == 0) {
                                continue;
                            }
                            Shaders.gbuffersFormat[bufferIndexFromString] = textureFormatFromString;
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[302], group, group2);
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[303])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[304]);
                            Shaders.gbuffersFormat[7] = 34836;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[305])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[306]);
                            Shaders.gbuffersFormat[7] = 34837;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[307])) {
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[308]);
                            Shaders.gbuffersFormat[7] = 32852;
                        }
                        else if (applyOptions.matches(Shaders.lIIllIIIlIlIllIl[309])) {
                            if (!s.matches(Shaders.lIIllIIIlIlIllIl[310]) && !s.matches(Shaders.lIIllIIIlIlIllIl[311])) {
                                continue;
                            }
                            final Matcher matcher2 = Shaders.gbufferMipmapEnabledPattern.matcher(applyOptions);
                            matcher2.matches();
                            final String group3 = matcher2.group(1);
                            final int bufferIndexFromString2 = getBufferIndexFromString(group3);
                            if (bufferIndexFromString2 < 0) {
                                continue;
                            }
                            Shaders.newCompositeMipmapSetting |= 1 << bufferIndexFromString2;
                            SMCLog.info(Shaders.lIIllIIIlIlIllIl[312], group3, s);
                        }
                        else {
                            if (!applyOptions.matches(Shaders.lIIllIIIlIlIllIl[313])) {
                                continue;
                            }
                            Shaders.newDrawBufSetting = applyOptions.split(Shaders.lIIllIIIlIlIllIl[314], 4)[2];
                        }
                    }
                }
                resolveIncludes.close();
            }
            catch (Exception ex) {
                SMCLog.severe(Shaders.lIIllIIIlIlIllIl[315] + s + Shaders.lIIllIIIlIlIllIl[316]);
                ex.printStackTrace();
                ARBShaderObjects.glDeleteObjectARB(glCreateShaderObjectARB);
                return 0;
            }
        }
        if (Shaders.saveFinalShaders) {
            saveShader(s, sb.toString());
        }
        ARBShaderObjects.glShaderSourceARB(glCreateShaderObjectARB, sb);
        ARBShaderObjects.glCompileShaderARB(glCreateShaderObjectARB);
        if (GL20.glGetShaderi(glCreateShaderObjectARB, 35713) != 1) {
            SMCLog.severe(Shaders.lIIllIIIlIlIllIl[317] + s);
        }
        printShaderLogInfo(glCreateShaderObjectARB, s);
        return glCreateShaderObjectARB;
    }
    
    private static void saveShader(final String s, final String s2) {
        try {
            final File file = new File(Shaders.shaderpacksdir, Shaders.lIIllIIIlIlIllIl[318] + s);
            file.getParentFile().mkdirs();
            Config.writeFile(file, s2);
        }
        catch (IOException ex) {
            Config.warn(Shaders.lIIllIIIlIlIllIl[319] + s);
            ex.printStackTrace();
        }
    }
    
    private static void clearDirectory(final File file) {
        if (file.exists() && file.isDirectory()) {
            final File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (int i = 0; i < listFiles.length; ++i) {
                    final File file2 = listFiles[i];
                    if (file2.isDirectory()) {
                        clearDirectory(file2);
                    }
                    file2.delete();
                }
            }
        }
    }
    
    private static boolean printLogInfo(final int n, final String s) {
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        ARBShaderObjects.glGetObjectParameterARB(n, 35716, intBuffer);
        final int value = intBuffer.get();
        if (value > 1) {
            final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(value);
            intBuffer.flip();
            ARBShaderObjects.glGetInfoLogARB(n, intBuffer, byteBuffer);
            final byte[] array = new byte[value];
            byteBuffer.get(array);
            if (array[value - 1] == 0) {
                array[value - 1] = 10;
            }
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[320] + s + Shaders.lIIllIIIlIlIllIl[321] + new String(array));
            return false;
        }
        return true;
    }
    
    private static boolean printShaderLogInfo(final int n, final String s) {
        BufferUtils.createIntBuffer(1);
        final int glGetShaderi = GL20.glGetShaderi(n, 35716);
        if (glGetShaderi > 1) {
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[322] + s + Shaders.lIIllIIIlIlIllIl[323] + GL20.glGetShaderInfoLog(n, glGetShaderi));
            return false;
        }
        return true;
    }
    
    public static void setDrawBuffers(IntBuffer drawBuffersNone) {
        if (drawBuffersNone == null) {
            drawBuffersNone = Shaders.drawBuffersNone;
        }
        if (Shaders.activeDrawBuffers != drawBuffersNone) {
            GL20.glDrawBuffers(Shaders.activeDrawBuffers = drawBuffersNone);
        }
    }
    
    public static void useProgram(int activeProgram) {
        checkGLError(Shaders.lIIllIIIlIlIllIl[324]);
        if (Shaders.isShadowPass) {
            activeProgram = 30;
            if (Shaders.programsID[30] == 0) {
                Shaders.normalMapEnabled = false;
                return;
            }
        }
        if (Shaders.activeProgram != activeProgram) {
            Shaders.activeProgram = activeProgram;
            ARBShaderObjects.glUseProgramObjectARB(Shaders.programsID[activeProgram]);
            if (Shaders.programsID[activeProgram] == 0) {
                Shaders.normalMapEnabled = false;
            }
            else {
                if (checkGLError(Shaders.lIIllIIIlIlIllIl[325], Shaders.programNames[activeProgram]) != 0) {
                    Shaders.programsID[activeProgram] = 0;
                }
                final IntBuffer drawBuffers = Shaders.programsDrawBuffers[activeProgram];
                if (Shaders.isRenderingDfb) {
                    setDrawBuffers(drawBuffers);
                    checkGLError(Shaders.programNames[activeProgram], Shaders.lIIllIIIlIlIllIl[326], Shaders.programsDrawBufSettings[activeProgram]);
                }
                Shaders.activeCompositeMipmapSetting = Shaders.programsCompositeMipmapSetting[activeProgram];
                Shaders.uniformEntityColor.setProgram(Shaders.programsID[Shaders.activeProgram]);
                Shaders.uniformEntityId.setProgram(Shaders.programsID[Shaders.activeProgram]);
                Shaders.uniformBlockEntityId.setProgram(Shaders.programsID[Shaders.activeProgram]);
                switch (activeProgram) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 16:
                    case 18:
                    case 19:
                    case 20: {
                        Shaders.normalMapEnabled = true;
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[327], 0);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[328], 1);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[329], 2);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[330], 3);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[331], Shaders.waterShadowEnabled ? 5 : 4);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[332], 4);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[333], 4);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[334], 5);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[335], 6);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[336], 12);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[337], 13);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[338], 13);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[339], 14);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[340], 15);
                        break;
                    }
                    default: {
                        Shaders.normalMapEnabled = false;
                        break;
                    }
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29: {
                        Shaders.normalMapEnabled = false;
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[341], 0);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[342], 1);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[343], 2);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[344], 3);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[345], 7);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[346], 8);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[347], 9);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[348], 10);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[349], 0);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[350], 1);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[351], 2);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[352], 3);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[353], 7);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[354], 8);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[355], 9);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[356], 10);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[357], Shaders.waterShadowEnabled ? 5 : 4);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[358], 4);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[359], 4);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[360], 5);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[361], 6);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[362], 6);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[363], 11);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[364], 12);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[365], 13);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[366], 13);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[367], 14);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[368], 15);
                        break;
                    }
                    case 30:
                    case 31:
                    case 32: {
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[369], 0);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[370], 0);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[371], 1);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[372], 2);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[373], 3);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[374], Shaders.waterShadowEnabled ? 5 : 4);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[375], 4);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[376], 4);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[377], 5);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[378], 13);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[379], 13);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[380], 14);
                        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[381], 15);
                        break;
                    }
                }
                final ItemStack currentEquippedItem = Minecraft.thePlayer.getCurrentEquippedItem();
                final Item item = (currentEquippedItem != null) ? currentEquippedItem.getItem() : null;
                int idForObject;
                Block block;
                if (item != null) {
                    idForObject = Item.itemRegistry.getIDForObject(item);
                    block = (Block)Block.blockRegistry.getObjectById(idForObject);
                }
                else {
                    idForObject = -1;
                    block = null;
                }
                final int n = (block != null) ? block.getLightValue() : 0;
                setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[382], idForObject);
                setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[383], n);
                setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[384], Shaders.fogEnabled ? Shaders.fogMode : 0);
                setProgramUniform3f(Shaders.lIIllIIIlIlIllIl[385], Shaders.fogColorR, Shaders.fogColorG, Shaders.fogColorB);
                setProgramUniform3f(Shaders.lIIllIIIlIlIllIl[386], Shaders.skyColorR, Shaders.skyColorG, Shaders.skyColorB);
                setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[387], (int)Shaders.worldTime % 24000);
                setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[388], Shaders.moonPhase);
                setProgramUniform1f(Shaders.lIIllIIIlIlIllIl[389], Shaders.frameTimeCounter);
                setProgramUniform1f(Shaders.lIIllIIIlIlIllIl[390], Shaders.sunAngle);
                setProgramUniform1f(Shaders.lIIllIIIlIlIllIl[391], Shaders.shadowAngle);
                setProgramUniform1f(Shaders.lIIllIIIlIlIllIl[392], Shaders.rainStrength);
                setProgramUniform1f(Shaders.lIIllIIIlIlIllIl[393], Shaders.renderWidth / (float)Shaders.renderHeight);
                setProgramUniform1f(Shaders.lIIllIIIlIlIllIl[394], (float)Shaders.renderWidth);
                setProgramUniform1f(Shaders.lIIllIIIlIlIllIl[395], (float)Shaders.renderHeight);
                setProgramUniform1f(Shaders.lIIllIIIlIlIllIl[396], 0.05f);
                final String s = Shaders.lIIllIIIlIlIllIl[397];
                final GameSettings gameSettings = Shaders.mc.gameSettings;
                setProgramUniform1f(s, (float)(GameSettings.renderDistanceChunks * 16));
                setProgramUniform3f(Shaders.lIIllIIIlIlIllIl[398], Shaders.sunPosition[0], Shaders.sunPosition[1], Shaders.sunPosition[2]);
                setProgramUniform3f(Shaders.lIIllIIIlIlIllIl[399], Shaders.moonPosition[0], Shaders.moonPosition[1], Shaders.moonPosition[2]);
                setProgramUniform3f(Shaders.lIIllIIIlIlIllIl[400], Shaders.shadowLightPosition[0], Shaders.shadowLightPosition[1], Shaders.shadowLightPosition[2]);
                setProgramUniform3f(Shaders.lIIllIIIlIlIllIl[401], Shaders.upPosition[0], Shaders.upPosition[1], Shaders.upPosition[2]);
                setProgramUniform3f(Shaders.lIIllIIIlIlIllIl[402], (float)Shaders.previousCameraPositionX, (float)Shaders.previousCameraPositionY, (float)Shaders.previousCameraPositionZ);
                setProgramUniform3f(Shaders.lIIllIIIlIlIllIl[403], (float)Shaders.cameraPositionX, (float)Shaders.cameraPositionY, (float)Shaders.cameraPositionZ);
                setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[404], false, Shaders.modelView);
                setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[405], false, Shaders.modelViewInverse);
                setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[406], false, Shaders.previousProjection);
                setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[407], false, Shaders.projection);
                setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[408], false, Shaders.projectionInverse);
                setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[409], false, Shaders.previousModelView);
                if (Shaders.usedShadowDepthBuffers > 0) {
                    setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[410], false, Shaders.shadowProjection);
                    setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[411], false, Shaders.shadowProjectionInverse);
                    setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[412], false, Shaders.shadowModelView);
                    setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[413], false, Shaders.shadowModelViewInverse);
                }
                setProgramUniform1f(Shaders.lIIllIIIlIlIllIl[414], Shaders.wetness);
                setProgramUniform1f(Shaders.lIIllIIIlIlIllIl[415], Shaders.eyePosY);
                setProgramUniform2i(Shaders.lIIllIIIlIlIllIl[416], Shaders.eyeBrightness & 0xFFFF, Shaders.eyeBrightness >> 16);
                setProgramUniform2i(Shaders.lIIllIIIlIlIllIl[417], Math.round(Shaders.eyeBrightnessFadeX), Math.round(Shaders.eyeBrightnessFadeY));
                setProgramUniform2i(Shaders.lIIllIIIlIlIllIl[418], Shaders.terrainTextureSize[0], Shaders.terrainTextureSize[1]);
                setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[419], Shaders.terrainIconSize);
                setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[420], Shaders.isEyeInWater);
                setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[421], Shaders.mc.gameSettings.hideGUI ? 1 : 0);
                setProgramUniform1f(Shaders.lIIllIIIlIlIllIl[422], Shaders.centerDepthSmooth);
                setProgramUniform2i(Shaders.lIIllIIIlIlIllIl[423], Shaders.atlasSizeX, Shaders.atlasSizeY);
                checkGLError(Shaders.lIIllIIIlIlIllIl[424], Shaders.programNames[activeProgram]);
            }
        }
    }
    
    public static void setProgramUniform1i(final String s, final int n) {
        final int n2 = Shaders.programsID[Shaders.activeProgram];
        if (n2 != 0) {
            ARBShaderObjects.glUniform1iARB(ARBShaderObjects.glGetUniformLocationARB(n2, s), n);
            checkGLError(Shaders.programNames[Shaders.activeProgram], s);
        }
    }
    
    public static void setProgramUniform2i(final String s, final int n, final int n2) {
        final int n3 = Shaders.programsID[Shaders.activeProgram];
        if (n3 != 0) {
            ARBShaderObjects.glUniform2iARB(ARBShaderObjects.glGetUniformLocationARB(n3, s), n, n2);
            checkGLError(Shaders.programNames[Shaders.activeProgram], s);
        }
    }
    
    public static void setProgramUniform1f(final String s, final float n) {
        final int n2 = Shaders.programsID[Shaders.activeProgram];
        if (n2 != 0) {
            ARBShaderObjects.glUniform1fARB(ARBShaderObjects.glGetUniformLocationARB(n2, s), n);
            checkGLError(Shaders.programNames[Shaders.activeProgram], s);
        }
    }
    
    public static void setProgramUniform3f(final String s, final float n, final float n2, final float n3) {
        final int n4 = Shaders.programsID[Shaders.activeProgram];
        if (n4 != 0) {
            ARBShaderObjects.glUniform3fARB(ARBShaderObjects.glGetUniformLocationARB(n4, s), n, n2, n3);
            checkGLError(Shaders.programNames[Shaders.activeProgram], s);
        }
    }
    
    public static void setProgramUniformMatrix4ARB(final String s, final boolean b, final FloatBuffer floatBuffer) {
        final int n = Shaders.programsID[Shaders.activeProgram];
        if (n != 0 && floatBuffer != null) {
            ARBShaderObjects.glUniformMatrix4ARB(ARBShaderObjects.glGetUniformLocationARB(n, s), b, floatBuffer);
            checkGLError(Shaders.programNames[Shaders.activeProgram], s);
        }
    }
    
    private static int getBufferIndexFromString(final String s) {
        return (!s.equals(Shaders.lIIllIIIlIlIllIl[425]) && !s.equals(Shaders.lIIllIIIlIlIllIl[426])) ? ((!s.equals(Shaders.lIIllIIIlIlIllIl[427]) && !s.equals(Shaders.lIIllIIIlIlIllIl[428])) ? ((!s.equals(Shaders.lIIllIIIlIlIllIl[429]) && !s.equals(Shaders.lIIllIIIlIlIllIl[430])) ? ((!s.equals(Shaders.lIIllIIIlIlIllIl[431]) && !s.equals(Shaders.lIIllIIIlIlIllIl[432])) ? ((!s.equals(Shaders.lIIllIIIlIlIllIl[433]) && !s.equals(Shaders.lIIllIIIlIlIllIl[434])) ? ((!s.equals(Shaders.lIIllIIIlIlIllIl[435]) && !s.equals(Shaders.lIIllIIIlIlIllIl[436])) ? ((!s.equals(Shaders.lIIllIIIlIlIllIl[437]) && !s.equals(Shaders.lIIllIIIlIlIllIl[438])) ? ((!s.equals(Shaders.lIIllIIIlIlIllIl[439]) && !s.equals(Shaders.lIIllIIIlIlIllIl[440])) ? -1 : 7) : 6) : 5) : 4) : 3) : 2) : 1) : 0;
    }
    
    private static int getTextureFormatFromString(String trim) {
        trim = trim.trim();
        for (int i = 0; i < Shaders.formatNames.length; ++i) {
            if (trim.equals(Shaders.formatNames[i])) {
                return Shaders.formatIds[i];
            }
        }
        return 0;
    }
    
    private static void setupNoiseTexture() {
        if (Shaders.noiseTexture == null) {
            Shaders.noiseTexture = new HFNoiseTexture(Shaders.noiseTextureResolution, Shaders.noiseTextureResolution);
        }
    }
    
    private static void loadEntityDataMap() {
        Shaders.mapBlockToEntityData = new IdentityHashMap(300);
        if (Shaders.mapBlockToEntityData.isEmpty()) {
            final Iterator<ResourceLocation> iterator = Block.blockRegistry.getKeys().iterator();
            while (iterator.hasNext()) {
                final Block block = (Block)Block.blockRegistry.getObject(iterator.next());
                Shaders.mapBlockToEntityData.put(block, Block.blockRegistry.getIDForObject(block));
            }
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Shaders.shaderPack.getResourceAsStream(Shaders.lIIllIIIlIlIllIl[441])));
        }
        catch (Exception ex) {}
        if (bufferedReader != null) {
            try {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    final Matcher matcher = Shaders.patternLoadEntityDataMap.matcher(line);
                    if (matcher.matches()) {
                        final String group = matcher.group(1);
                        final int int1 = Integer.parseInt(matcher.group(2));
                        final Block blockFromName = Block.getBlockFromName(group);
                        if (blockFromName != null) {
                            Shaders.mapBlockToEntityData.put(blockFromName, int1);
                        }
                        else {
                            SMCLog.warning(Shaders.lIIllIIIlIlIllIl[442], group);
                        }
                    }
                    else {
                        SMCLog.warning(Shaders.lIIllIIIlIlIllIl[443], line);
                    }
                }
            }
            catch (Exception ex2) {
                SMCLog.warning(Shaders.lIIllIIIlIlIllIl[444]);
            }
        }
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            }
            catch (Exception ex3) {}
        }
    }
    
    private static IntBuffer fillIntBufferZero(final IntBuffer intBuffer) {
        for (int limit = intBuffer.limit(), i = intBuffer.position(); i < limit; ++i) {
            intBuffer.put(i, 0);
        }
        return intBuffer;
    }
    
    public static void uninit() {
        if (Shaders.isShaderPackInitialized) {
            checkGLError(Shaders.lIIllIIIlIlIllIl[445]);
            for (int i = 0; i < 33; ++i) {
                if (Shaders.programsRef[i] != 0) {
                    ARBShaderObjects.glDeleteObjectARB(Shaders.programsRef[i]);
                    checkGLError(Shaders.lIIllIIIlIlIllIl[446]);
                }
                Shaders.programsRef[i] = 0;
                Shaders.programsID[i] = 0;
                Shaders.programsDrawBufSettings[i] = null;
                Shaders.programsDrawBuffers[i] = null;
                Shaders.programsCompositeMipmapSetting[i] = 0;
            }
            if (Shaders.dfb != 0) {
                EXTFramebufferObject.glDeleteFramebuffersEXT(Shaders.dfb);
                Shaders.dfb = 0;
                checkGLError(Shaders.lIIllIIIlIlIllIl[447]);
            }
            if (Shaders.sfb != 0) {
                EXTFramebufferObject.glDeleteFramebuffersEXT(Shaders.sfb);
                Shaders.sfb = 0;
                checkGLError(Shaders.lIIllIIIlIlIllIl[448]);
            }
            if (Shaders.dfbDepthTextures != null) {
                GlStateManager.deleteTextures(Shaders.dfbDepthTextures);
                fillIntBufferZero(Shaders.dfbDepthTextures);
                checkGLError(Shaders.lIIllIIIlIlIllIl[449]);
            }
            if (Shaders.dfbColorTextures != null) {
                GlStateManager.deleteTextures(Shaders.dfbColorTextures);
                fillIntBufferZero(Shaders.dfbColorTextures);
                checkGLError(Shaders.lIIllIIIlIlIllIl[450]);
            }
            if (Shaders.sfbDepthTextures != null) {
                GlStateManager.deleteTextures(Shaders.sfbDepthTextures);
                fillIntBufferZero(Shaders.sfbDepthTextures);
                checkGLError(Shaders.lIIllIIIlIlIllIl[451]);
            }
            if (Shaders.sfbColorTextures != null) {
                GlStateManager.deleteTextures(Shaders.sfbColorTextures);
                fillIntBufferZero(Shaders.sfbColorTextures);
                checkGLError(Shaders.lIIllIIIlIlIllIl[452]);
            }
            if (Shaders.dfbDrawBuffers != null) {
                fillIntBufferZero(Shaders.dfbDrawBuffers);
            }
            if (Shaders.noiseTexture != null) {
                Shaders.noiseTexture.destroy();
                Shaders.noiseTexture = null;
            }
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[453]);
            Shaders.shadowPassInterval = 0;
            Shaders.shouldSkipDefaultShadow = false;
            Shaders.isShaderPackInitialized = false;
            checkGLError(Shaders.lIIllIIIlIlIllIl[454]);
        }
    }
    
    public static void scheduleResize() {
        Shaders.renderDisplayHeight = 0;
    }
    
    public static void scheduleResizeShadow() {
        Shaders.needResizeShadow = true;
    }
    
    private static void resize() {
        Shaders.renderDisplayWidth = Shaders.mc.displayWidth;
        Shaders.renderDisplayHeight = Shaders.mc.displayHeight;
        Shaders.renderWidth = Math.round(Shaders.renderDisplayWidth * Shaders.configRenderResMul);
        Shaders.renderHeight = Math.round(Shaders.renderDisplayHeight * Shaders.configRenderResMul);
        setupFrameBuffer();
    }
    
    private static void resizeShadow() {
        Shaders.needResizeShadow = false;
        Shaders.shadowMapWidth = Math.round(Shaders.spShadowMapWidth * Shaders.configShadowResMul);
        Shaders.shadowMapHeight = Math.round(Shaders.spShadowMapHeight * Shaders.configShadowResMul);
        setupShadowFrameBuffer();
    }
    
    private static void setupFrameBuffer() {
        if (Shaders.dfb != 0) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(Shaders.dfb);
            GlStateManager.deleteTextures(Shaders.dfbDepthTextures);
            GlStateManager.deleteTextures(Shaders.dfbColorTextures);
        }
        Shaders.dfb = EXTFramebufferObject.glGenFramebuffersEXT();
        GL11.glGenTextures(Shaders.dfbDepthTextures.clear().limit(Shaders.usedDepthBuffers));
        GL11.glGenTextures(Shaders.dfbColorTextures.clear().limit(16));
        Shaders.dfbDepthTextures.position(0);
        Shaders.dfbColorTextures.position(0);
        Shaders.dfbColorTextures.get(Shaders.dfbColorTexturesA).position(0);
        EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
        GL20.glDrawBuffers(0);
        GL11.glReadBuffer(0);
        for (int i = 0; i < Shaders.usedDepthBuffers; ++i) {
            GlStateManager.func_179144_i(Shaders.dfbDepthTextures.get(i));
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
            GL11.glTexParameteri(3553, 10241, 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
            GL11.glTexParameteri(3553, 34891, 6409);
            GL11.glTexImage2D(3553, 0, 6402, Shaders.renderWidth, Shaders.renderHeight, 0, 6402, 5126, (FloatBuffer)null);
        }
        EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, Shaders.dfbDepthTextures.get(0), 0);
        GL20.glDrawBuffers(Shaders.dfbDrawBuffers);
        GL11.glReadBuffer(0);
        checkGLError(Shaders.lIIllIIIlIlIllIl[455]);
        for (int j = 0; j < Shaders.usedColorBuffers; ++j) {
            GlStateManager.func_179144_i(Shaders.dfbColorTexturesA[j]);
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexImage2D(3553, 0, Shaders.gbuffersFormat[j], Shaders.renderWidth, Shaders.renderHeight, 0, 32993, 33639, (ByteBuffer)null);
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, Shaders.dfbColorTexturesA[j], 0);
            checkGLError(Shaders.lIIllIIIlIlIllIl[456]);
        }
        for (int k = 0; k < Shaders.usedColorBuffers; ++k) {
            GlStateManager.func_179144_i(Shaders.dfbColorTexturesA[8 + k]);
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexImage2D(3553, 0, Shaders.gbuffersFormat[k], Shaders.renderWidth, Shaders.renderHeight, 0, 32993, 33639, (ByteBuffer)null);
            checkGLError(Shaders.lIIllIIIlIlIllIl[457]);
        }
        int n = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
        if (n == 36058) {
            printChatAndLogError(Shaders.lIIllIIIlIlIllIl[458]);
            for (int l = 0; l < Shaders.usedColorBuffers; ++l) {
                GlStateManager.func_179144_i(Shaders.dfbColorTextures.get(l));
                GL11.glTexImage2D(3553, 0, 6408, Shaders.renderWidth, Shaders.renderHeight, 0, 32993, 33639, (ByteBuffer)null);
                EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + l, 3553, Shaders.dfbColorTextures.get(l), 0);
                checkGLError(Shaders.lIIllIIIlIlIllIl[459]);
            }
            n = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
            if (n == 36053) {
                SMCLog.info(Shaders.lIIllIIIlIlIllIl[460]);
            }
        }
        GlStateManager.func_179144_i(0);
        if (n != 36053) {
            printChatAndLogError(Shaders.lIIllIIIlIlIllIl[461] + n + Shaders.lIIllIIIlIlIllIl[462]);
        }
        else {
            SMCLog.info(Shaders.lIIllIIIlIlIllIl[463]);
        }
    }
    
    private static void setupShadowFrameBuffer() {
        if (Shaders.usedShadowDepthBuffers != 0) {
            if (Shaders.sfb != 0) {
                EXTFramebufferObject.glDeleteFramebuffersEXT(Shaders.sfb);
                GlStateManager.deleteTextures(Shaders.sfbDepthTextures);
                GlStateManager.deleteTextures(Shaders.sfbColorTextures);
            }
            EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.sfb = EXTFramebufferObject.glGenFramebuffersEXT());
            GL11.glDrawBuffer(0);
            GL11.glReadBuffer(0);
            GL11.glGenTextures(Shaders.sfbDepthTextures.clear().limit(Shaders.usedShadowDepthBuffers));
            GL11.glGenTextures(Shaders.sfbColorTextures.clear().limit(Shaders.usedShadowColorBuffers));
            Shaders.sfbDepthTextures.position(0);
            Shaders.sfbColorTextures.position(0);
            for (int i = 0; i < Shaders.usedShadowDepthBuffers; ++i) {
                GlStateManager.func_179144_i(Shaders.sfbDepthTextures.get(i));
                GL11.glTexParameterf(3553, 10242, 10496.0f);
                GL11.glTexParameterf(3553, 10243, 10496.0f);
                final int n = Shaders.shadowFilterNearest[i] ? 9728 : 9729;
                GL11.glTexParameteri(3553, 10241, n);
                GL11.glTexParameteri(3553, 10240, n);
                if (Shaders.shadowHardwareFilteringEnabled[i]) {
                    GL11.glTexParameteri(3553, 34892, 34894);
                }
                GL11.glTexImage2D(3553, 0, 6402, Shaders.shadowMapWidth, Shaders.shadowMapHeight, 0, 6402, 5126, (FloatBuffer)null);
            }
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, Shaders.sfbDepthTextures.get(0), 0);
            checkGLError(Shaders.lIIllIIIlIlIllIl[464]);
            for (int j = 0; j < Shaders.usedShadowColorBuffers; ++j) {
                GlStateManager.func_179144_i(Shaders.sfbColorTextures.get(j));
                GL11.glTexParameterf(3553, 10242, 10496.0f);
                GL11.glTexParameterf(3553, 10243, 10496.0f);
                final int n2 = Shaders.shadowColorFilterNearest[j] ? 9728 : 9729;
                GL11.glTexParameteri(3553, 10241, n2);
                GL11.glTexParameteri(3553, 10240, n2);
                GL11.glTexImage2D(3553, 0, 6408, Shaders.shadowMapWidth, Shaders.shadowMapHeight, 0, 32993, 33639, (ByteBuffer)null);
                EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, Shaders.sfbColorTextures.get(j), 0);
                checkGLError(Shaders.lIIllIIIlIlIllIl[465]);
            }
            GlStateManager.func_179144_i(0);
            if (Shaders.usedShadowColorBuffers > 0) {
                GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
            }
            final int glCheckFramebufferStatusEXT = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
            if (glCheckFramebufferStatusEXT != 36053) {
                printChatAndLogError(Shaders.lIIllIIIlIlIllIl[466] + glCheckFramebufferStatusEXT + Shaders.lIIllIIIlIlIllIl[467]);
            }
            else {
                SMCLog.info(Shaders.lIIllIIIlIlIllIl[468]);
            }
        }
    }
    
    public static void beginRender(final Minecraft mc, final float n, final long n2) {
        checkGLError(Shaders.lIIllIIIlIlIllIl[469]);
        checkWorldChanged(Minecraft.theWorld);
        Shaders.mc = mc;
        Shaders.mc.mcProfiler.startSection(Shaders.lIIllIIIlIlIllIl[470]);
        Shaders.entityRenderer = Shaders.mc.entityRenderer;
        if (!Shaders.isShaderPackInitialized) {
            try {
                init();
            }
            catch (IllegalStateException ex) {
                if (Config.normalize(ex.getMessage()).equals(Shaders.lIIllIIIlIlIllIl[471])) {
                    printChatAndLogError(Shaders.lIIllIIIlIlIllIl[472] + ex.getMessage());
                    ex.printStackTrace();
                    setShaderPack(Shaders.packNameNone);
                    return;
                }
            }
        }
        if (Shaders.mc.displayWidth != Shaders.renderDisplayWidth || Shaders.mc.displayHeight != Shaders.renderDisplayHeight) {
            resize();
        }
        if (Shaders.needResizeShadow) {
            resizeShadow();
        }
        Shaders.worldTime = Minecraft.theWorld.getWorldTime();
        Shaders.diffWorldTime = (Shaders.worldTime - Shaders.lastWorldTime) % 24000L;
        if (Shaders.diffWorldTime < 0L) {
            Shaders.diffWorldTime += 24000L;
        }
        Shaders.lastWorldTime = Shaders.worldTime;
        Shaders.moonPhase = Minecraft.theWorld.getMoonPhase();
        Shaders.systemTime = System.currentTimeMillis();
        if (Shaders.lastSystemTime == 0L) {
            Shaders.lastSystemTime = Shaders.systemTime;
        }
        Shaders.diffSystemTime = Shaders.systemTime - Shaders.lastSystemTime;
        Shaders.lastSystemTime = Shaders.systemTime;
        Shaders.frameTimeCounter += Shaders.diffSystemTime * 0.001f;
        Shaders.frameTimeCounter %= 3600.0f;
        Shaders.rainStrength = Minecraft.theWorld.getRainStrength(n);
        final float n3 = (float)Math.exp(Math.log(0.5) * (Shaders.diffSystemTime * 0.01f) / ((Shaders.wetness < Shaders.rainStrength) ? Shaders.drynessHalfLife : Shaders.wetnessHalfLife));
        Shaders.wetness = Shaders.wetness * n3 + Shaders.rainStrength * (1.0f - n3);
        final Entity func_175606_aa = Shaders.mc.func_175606_aa();
        Shaders.isSleeping = (func_175606_aa instanceof EntityLivingBase && ((EntityLivingBase)func_175606_aa).isPlayerSleeping());
        Shaders.eyePosY = (float)func_175606_aa.posY * n + (float)func_175606_aa.lastTickPosY * (1.0f - n);
        Shaders.eyeBrightness = func_175606_aa.getBrightnessForRender(n);
        final float n4 = (float)Math.exp(Math.log(0.5) * (Shaders.diffSystemTime * 0.01f) / Shaders.eyeBrightnessHalflife);
        Shaders.eyeBrightnessFadeX = Shaders.eyeBrightnessFadeX * n4 + (Shaders.eyeBrightness & 0xFFFF) * (1.0f - n4);
        Shaders.eyeBrightnessFadeY = Shaders.eyeBrightnessFadeY * n4 + (Shaders.eyeBrightness >> 16) * (1.0f - n4);
        Shaders.isEyeInWater = ((Shaders.mc.gameSettings.thirdPersonView == 0 && !Shaders.isSleeping && Minecraft.thePlayer.isInsideOfMaterial(Material.water)) ? 1 : 0);
        final Vec3 skyColor = Minecraft.theWorld.getSkyColor(Shaders.mc.func_175606_aa(), n);
        Shaders.skyColorR = (float)skyColor.xCoord;
        Shaders.skyColorG = (float)skyColor.yCoord;
        Shaders.skyColorB = (float)skyColor.zCoord;
        Shaders.isRenderingWorld = true;
        Shaders.isCompositeRendered = false;
        Shaders.isHandRendered = false;
        if (Shaders.usedShadowDepthBuffers >= 1) {
            GlStateManager.setActiveTexture(33988);
            GlStateManager.func_179144_i(Shaders.sfbDepthTextures.get(0));
            if (Shaders.usedShadowDepthBuffers >= 2) {
                GlStateManager.setActiveTexture(33989);
                GlStateManager.func_179144_i(Shaders.sfbDepthTextures.get(1));
            }
        }
        GlStateManager.setActiveTexture(33984);
        for (int i = 0; i < Shaders.usedColorBuffers; ++i) {
            GlStateManager.func_179144_i(Shaders.dfbColorTexturesA[i]);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9729);
            GlStateManager.func_179144_i(Shaders.dfbColorTexturesA[8 + i]);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9729);
        }
        GlStateManager.func_179144_i(0);
        for (int n5 = 0; n5 < 4 && 4 + n5 < Shaders.usedColorBuffers; ++n5) {
            GlStateManager.setActiveTexture(33991 + n5);
            GlStateManager.func_179144_i(Shaders.dfbColorTextures.get(4 + n5));
        }
        GlStateManager.setActiveTexture(33990);
        GlStateManager.func_179144_i(Shaders.dfbDepthTextures.get(0));
        if (Shaders.usedDepthBuffers >= 2) {
            GlStateManager.setActiveTexture(33995);
            GlStateManager.func_179144_i(Shaders.dfbDepthTextures.get(1));
            if (Shaders.usedDepthBuffers >= 3) {
                GlStateManager.setActiveTexture(33996);
                GlStateManager.func_179144_i(Shaders.dfbDepthTextures.get(2));
            }
        }
        for (int j = 0; j < Shaders.usedShadowColorBuffers; ++j) {
            GlStateManager.setActiveTexture(33997 + j);
            GlStateManager.func_179144_i(Shaders.sfbColorTextures.get(j));
        }
        if (Shaders.noiseTextureEnabled) {
            GlStateManager.setActiveTexture(33984 + Shaders.noiseTexture.textureUnit);
            GlStateManager.func_179144_i(Shaders.noiseTexture.getID());
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9729);
        }
        GlStateManager.setActiveTexture(33984);
        Shaders.previousCameraPositionX = Shaders.cameraPositionX;
        Shaders.previousCameraPositionY = Shaders.cameraPositionY;
        Shaders.previousCameraPositionZ = Shaders.cameraPositionZ;
        Shaders.previousProjection.position(0);
        Shaders.projection.position(0);
        Shaders.previousProjection.put(Shaders.projection);
        Shaders.previousProjection.position(0);
        Shaders.projection.position(0);
        Shaders.previousModelView.position(0);
        Shaders.modelView.position(0);
        Shaders.previousModelView.put(Shaders.modelView);
        Shaders.previousModelView.position(0);
        Shaders.modelView.position(0);
        checkGLError(Shaders.lIIllIIIlIlIllIl[473]);
        ShadersRender.renderShadowMap(Shaders.entityRenderer, 0, n, n2);
        Shaders.mc.mcProfiler.endSection();
        EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
        for (int k = 0; k < Shaders.usedColorBuffers; ++k) {
            Shaders.colorTexturesToggle[k] = 0;
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, Shaders.dfbColorTexturesA[k], 0);
        }
        checkGLError(Shaders.lIIllIIIlIlIllIl[474]);
    }
    
    private static void checkWorldChanged(final World currentWorld) {
        if (Shaders.currentWorld != currentWorld) {
            final World currentWorld2 = Shaders.currentWorld;
            Shaders.currentWorld = currentWorld;
            if (currentWorld2 != null && currentWorld != null) {
                final int dimensionId = currentWorld2.provider.getDimensionId();
                final int dimensionId2 = currentWorld.provider.getDimensionId();
                final boolean contains = Shaders.shaderPackDimensions.contains(dimensionId);
                final boolean contains2 = Shaders.shaderPackDimensions.contains(dimensionId2);
                if (contains || contains2) {
                    uninit();
                }
            }
        }
    }
    
    public static void beginRenderPass(final int n, final float n2, final long n3) {
        if (!Shaders.isShadowPass) {
            EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
            GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
            Shaders.activeDrawBuffers = null;
            ShadersTex.bindNSTextures(Shaders.defaultTexture.getMultiTexID());
            useProgram(2);
            checkGLError(Shaders.lIIllIIIlIlIllIl[475]);
        }
    }
    
    public static void setViewport(final int n, final int n2, final int n3, final int n4) {
        GlStateManager.colorMask(true, true, true, true);
        if (Shaders.isShadowPass) {
            GL11.glViewport(0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
        }
        else {
            GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
            EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
            Shaders.isRenderingDfb = true;
            GlStateManager.enableCull();
            GlStateManager.enableDepth();
            setDrawBuffers(Shaders.drawBuffersNone);
            useProgram(2);
            checkGLError(Shaders.lIIllIIIlIlIllIl[476]);
        }
    }
    
    public static int setFogMode(final int fogMode) {
        return Shaders.fogMode = fogMode;
    }
    
    public static void setFogColor(final float fogColorR, final float fogColorG, final float fogColorB) {
        Shaders.fogColorR = fogColorR;
        Shaders.fogColorG = fogColorG;
        Shaders.fogColorB = fogColorB;
    }
    
    public static void setClearColor(final float clearColorR, final float clearColorG, final float clearColorB, final float n) {
        GlStateManager.clearColor(clearColorR, clearColorG, clearColorB, n);
        Shaders.clearColorR = clearColorR;
        Shaders.clearColorG = clearColorG;
        Shaders.clearColorB = clearColorB;
    }
    
    public static void clearRenderBuffer() {
        if (Shaders.isShadowPass) {
            checkGLError(Shaders.lIIllIIIlIlIllIl[477]);
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, Shaders.sfbDepthTextures.get(0), 0);
            GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            GL20.glDrawBuffers(Shaders.programsDrawBuffers[30]);
            checkFramebufferStatus(Shaders.lIIllIIIlIlIllIl[478]);
            GL11.glClear(16640);
            checkGLError(Shaders.lIIllIIIlIlIllIl[479]);
        }
        else {
            checkGLError(Shaders.lIIllIIIlIlIllIl[480]);
            GL20.glDrawBuffers(36064);
            GL11.glClear(16384);
            GL20.glDrawBuffers(36065);
            GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glClear(16384);
            for (int i = 2; i < Shaders.usedColorBuffers; ++i) {
                GL20.glDrawBuffers(36064 + i);
                GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GL11.glClear(16384);
            }
            setDrawBuffers(Shaders.dfbDrawBuffers);
            checkFramebufferStatus(Shaders.lIIllIIIlIlIllIl[481]);
            checkGLError(Shaders.lIIllIIIlIlIllIl[482]);
        }
    }
    
    public static void setCamera(final float n) {
        final Entity func_175606_aa = Shaders.mc.func_175606_aa();
        final double cameraPositionX = func_175606_aa.lastTickPosX + (func_175606_aa.posX - func_175606_aa.lastTickPosX) * n;
        final double cameraPositionY = func_175606_aa.lastTickPosY + (func_175606_aa.posY - func_175606_aa.lastTickPosY) * n;
        final double cameraPositionZ = func_175606_aa.lastTickPosZ + (func_175606_aa.posZ - func_175606_aa.lastTickPosZ) * n;
        Shaders.cameraPositionX = cameraPositionX;
        Shaders.cameraPositionY = cameraPositionY;
        Shaders.cameraPositionZ = cameraPositionZ;
        GL11.glGetFloat(2983, Shaders.projection.position(0));
        SMath.invertMat4FBFA(Shaders.projectionInverse.position(0), Shaders.projection.position(0), Shaders.faProjectionInverse, Shaders.faProjection);
        Shaders.projection.position(0);
        Shaders.projectionInverse.position(0);
        GL11.glGetFloat(2982, Shaders.modelView.position(0));
        SMath.invertMat4FBFA(Shaders.modelViewInverse.position(0), Shaders.modelView.position(0), Shaders.faModelViewInverse, Shaders.faModelView);
        Shaders.modelView.position(0);
        Shaders.modelViewInverse.position(0);
        checkGLError(Shaders.lIIllIIIlIlIllIl[483]);
    }
    
    public static void setCameraShadow(final float n) {
        final Entity func_175606_aa = Shaders.mc.func_175606_aa();
        final double cameraPositionX = func_175606_aa.lastTickPosX + (func_175606_aa.posX - func_175606_aa.lastTickPosX) * n;
        final double cameraPositionY = func_175606_aa.lastTickPosY + (func_175606_aa.posY - func_175606_aa.lastTickPosY) * n;
        final double cameraPositionZ = func_175606_aa.lastTickPosZ + (func_175606_aa.posZ - func_175606_aa.lastTickPosZ) * n;
        Shaders.cameraPositionX = cameraPositionX;
        Shaders.cameraPositionY = cameraPositionY;
        Shaders.cameraPositionZ = cameraPositionZ;
        GL11.glGetFloat(2983, Shaders.projection.position(0));
        SMath.invertMat4FBFA(Shaders.projectionInverse.position(0), Shaders.projection.position(0), Shaders.faProjectionInverse, Shaders.faProjection);
        Shaders.projection.position(0);
        Shaders.projectionInverse.position(0);
        GL11.glGetFloat(2982, Shaders.modelView.position(0));
        SMath.invertMat4FBFA(Shaders.modelViewInverse.position(0), Shaders.modelView.position(0), Shaders.faModelViewInverse, Shaders.faModelView);
        Shaders.modelView.position(0);
        Shaders.modelViewInverse.position(0);
        GL11.glViewport(0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        if (Shaders.shadowMapIsOrtho) {
            GL11.glOrtho(-Shaders.shadowMapHalfPlane, Shaders.shadowMapHalfPlane, -Shaders.shadowMapHalfPlane, Shaders.shadowMapHalfPlane, 0.05000000074505806, 256.0);
        }
        else {
            GLU.gluPerspective(Shaders.shadowMapFOV, Shaders.shadowMapWidth / (float)Shaders.shadowMapHeight, 0.05f, 256.0f);
        }
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -100.0f);
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        Shaders.celestialAngle = Minecraft.theWorld.getCelestialAngle(n);
        Shaders.sunAngle = ((Shaders.celestialAngle < 0.75f) ? (Shaders.celestialAngle + 0.25f) : (Shaders.celestialAngle - 0.75f));
        final float n2 = Shaders.celestialAngle * -360.0f;
        final float n3 = (Shaders.shadowAngleInterval > 0.0f) ? (n2 % Shaders.shadowAngleInterval - Shaders.shadowAngleInterval * 0.5f) : 0.0f;
        if (Shaders.sunAngle <= 0.5) {
            GL11.glRotatef(n2 - n3, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(Shaders.sunPathRotation, 1.0f, 0.0f, 0.0f);
            Shaders.shadowAngle = Shaders.sunAngle;
        }
        else {
            GL11.glRotatef(n2 + 180.0f - n3, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(Shaders.sunPathRotation, 1.0f, 0.0f, 0.0f);
            Shaders.shadowAngle = Shaders.sunAngle - 0.5f;
        }
        if (Shaders.shadowMapIsOrtho) {
            final float shadowIntervalSize = Shaders.shadowIntervalSize;
            final float n4 = shadowIntervalSize / 2.0f;
            GL11.glTranslatef((float)cameraPositionX % shadowIntervalSize - n4, (float)cameraPositionY % shadowIntervalSize - n4, (float)cameraPositionZ % shadowIntervalSize - n4);
        }
        final float n5 = Shaders.sunAngle * 6.2831855f;
        final float n6 = (float)Math.cos(n5);
        final float n7 = (float)Math.sin(n5);
        final float n8 = Shaders.sunPathRotation * 6.2831855f;
        float n9 = n6;
        float n10 = n7 * (float)Math.cos(n8);
        float n11 = n7 * (float)Math.sin(n8);
        if (Shaders.sunAngle > 0.5) {
            n9 = -n6;
            n10 = -n10;
            n11 = -n11;
        }
        Shaders.shadowLightPositionVector[0] = n9;
        Shaders.shadowLightPositionVector[1] = n10;
        Shaders.shadowLightPositionVector[2] = n11;
        Shaders.shadowLightPositionVector[3] = 0.0f;
        GL11.glGetFloat(2983, Shaders.shadowProjection.position(0));
        SMath.invertMat4FBFA(Shaders.shadowProjectionInverse.position(0), Shaders.shadowProjection.position(0), Shaders.faShadowProjectionInverse, Shaders.faShadowProjection);
        Shaders.shadowProjection.position(0);
        Shaders.shadowProjectionInverse.position(0);
        GL11.glGetFloat(2982, Shaders.shadowModelView.position(0));
        SMath.invertMat4FBFA(Shaders.shadowModelViewInverse.position(0), Shaders.shadowModelView.position(0), Shaders.faShadowModelViewInverse, Shaders.faShadowModelView);
        Shaders.shadowModelView.position(0);
        Shaders.shadowModelViewInverse.position(0);
        setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[484], false, Shaders.projection);
        setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[485], false, Shaders.projectionInverse);
        setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[486], false, Shaders.previousProjection);
        setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[487], false, Shaders.modelView);
        setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[488], false, Shaders.modelViewInverse);
        setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[489], false, Shaders.previousModelView);
        setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[490], false, Shaders.shadowProjection);
        setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[491], false, Shaders.shadowProjectionInverse);
        setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[492], false, Shaders.shadowModelView);
        setProgramUniformMatrix4ARB(Shaders.lIIllIIIlIlIllIl[493], false, Shaders.shadowModelViewInverse);
        Shaders.mc.gameSettings.thirdPersonView = 1;
        checkGLError(Shaders.lIIllIIIlIlIllIl[494]);
    }
    
    public static void preCelestialRotate() {
        setUpPosition();
        GL11.glRotatef(Shaders.sunPathRotation * 1.0f, 0.0f, 0.0f, 1.0f);
        checkGLError(Shaders.lIIllIIIlIlIllIl[495]);
    }
    
    public static void postCelestialRotate() {
        final FloatBuffer tempMatrixDirectBuffer = Shaders.tempMatrixDirectBuffer;
        tempMatrixDirectBuffer.clear();
        GL11.glGetFloat(2982, tempMatrixDirectBuffer);
        tempMatrixDirectBuffer.get(Shaders.tempMat, 0, 16);
        SMath.multiplyMat4xVec4(Shaders.sunPosition, Shaders.tempMat, Shaders.sunPosModelView);
        SMath.multiplyMat4xVec4(Shaders.moonPosition, Shaders.tempMat, Shaders.moonPosModelView);
        System.arraycopy((Shaders.shadowAngle == Shaders.sunAngle) ? Shaders.sunPosition : Shaders.moonPosition, 0, Shaders.shadowLightPosition, 0, 3);
        checkGLError(Shaders.lIIllIIIlIlIllIl[496]);
    }
    
    public static void setUpPosition() {
        final FloatBuffer tempMatrixDirectBuffer = Shaders.tempMatrixDirectBuffer;
        tempMatrixDirectBuffer.clear();
        GL11.glGetFloat(2982, tempMatrixDirectBuffer);
        tempMatrixDirectBuffer.get(Shaders.tempMat, 0, 16);
        SMath.multiplyMat4xVec4(Shaders.upPosition, Shaders.tempMat, Shaders.upPosModelView);
    }
    
    public static void genCompositeMipmap() {
        if (Shaders.hasGlGenMipmap) {
            for (int i = 0; i < Shaders.usedColorBuffers; ++i) {
                if ((Shaders.activeCompositeMipmapSetting & 1 << i) != 0x0) {
                    GlStateManager.setActiveTexture(33984 + Shaders.colorTextureTextureImageUnit[i]);
                    GL11.glTexParameteri(3553, 10241, 9987);
                    GL30.glGenerateMipmap(3553);
                }
            }
            GlStateManager.setActiveTexture(33984);
        }
    }
    
    public static void drawComposite() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(1.0f, 1.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, 1.0f, 0.0f);
        GL11.glEnd();
    }
    
    public static void renderCompositeFinal() {
        if (!Shaders.isShadowPass) {
            checkGLError(Shaders.lIIllIIIlIlIllIl[497]);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, 1.0, 0.0, 1.0, 0.0, 1.0);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179098_w();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.enableDepth();
            GlStateManager.depthFunc(519);
            GlStateManager.depthMask(false);
            GlStateManager.disableLighting();
            if (Shaders.usedShadowDepthBuffers >= 1) {
                GlStateManager.setActiveTexture(33988);
                GlStateManager.func_179144_i(Shaders.sfbDepthTextures.get(0));
                if (Shaders.usedShadowDepthBuffers >= 2) {
                    GlStateManager.setActiveTexture(33989);
                    GlStateManager.func_179144_i(Shaders.sfbDepthTextures.get(1));
                }
            }
            for (int i = 0; i < Shaders.usedColorBuffers; ++i) {
                GlStateManager.setActiveTexture(33984 + Shaders.colorTextureTextureImageUnit[i]);
                GlStateManager.func_179144_i(Shaders.dfbColorTexturesA[i]);
            }
            GlStateManager.setActiveTexture(33990);
            GlStateManager.func_179144_i(Shaders.dfbDepthTextures.get(0));
            if (Shaders.usedDepthBuffers >= 2) {
                GlStateManager.setActiveTexture(33995);
                GlStateManager.func_179144_i(Shaders.dfbDepthTextures.get(1));
                if (Shaders.usedDepthBuffers >= 3) {
                    GlStateManager.setActiveTexture(33996);
                    GlStateManager.func_179144_i(Shaders.dfbDepthTextures.get(2));
                }
            }
            for (int j = 0; j < Shaders.usedShadowColorBuffers; ++j) {
                GlStateManager.setActiveTexture(33997 + j);
                GlStateManager.func_179144_i(Shaders.sfbColorTextures.get(j));
            }
            if (Shaders.noiseTextureEnabled) {
                GlStateManager.setActiveTexture(33984 + Shaders.noiseTexture.textureUnit);
                GlStateManager.func_179144_i(Shaders.noiseTexture.getID());
                GL11.glTexParameteri(3553, 10242, 10497);
                GL11.glTexParameteri(3553, 10243, 10497);
                GL11.glTexParameteri(3553, 10240, 9729);
                GL11.glTexParameteri(3553, 10241, 9729);
            }
            GlStateManager.setActiveTexture(33984);
            for (int k = 0; k < Shaders.usedColorBuffers; ++k) {
                EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, Shaders.dfbColorTexturesA[8 + k], 0);
            }
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, Shaders.dfbDepthTextures.get(0), 0);
            GL20.glDrawBuffers(Shaders.dfbDrawBuffers);
            checkGLError(Shaders.lIIllIIIlIlIllIl[498]);
            for (int l = 0; l < 8; ++l) {
                if (Shaders.programsID[21 + l] != 0) {
                    useProgram(21 + l);
                    checkGLError(Shaders.programNames[21 + l]);
                    if (Shaders.activeCompositeMipmapSetting != 0) {
                        genCompositeMipmap();
                    }
                    drawComposite();
                    for (int n = 0; n < Shaders.usedColorBuffers; ++n) {
                        if (Shaders.programsToggleColorTextures[21 + l][n]) {
                            final int n2 = Shaders.colorTexturesToggle[n];
                            final int[] colorTexturesToggle = Shaders.colorTexturesToggle;
                            final int n3 = n;
                            final int n4 = 8 - n2;
                            colorTexturesToggle[n3] = n4;
                            final int n5 = n4;
                            GlStateManager.setActiveTexture(33984 + Shaders.colorTextureTextureImageUnit[n]);
                            GlStateManager.func_179144_i(Shaders.dfbColorTexturesA[n5 + n]);
                            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + n, 3553, Shaders.dfbColorTexturesA[n2 + n], 0);
                        }
                    }
                    GlStateManager.setActiveTexture(33984);
                }
            }
            checkGLError(Shaders.lIIllIIIlIlIllIl[499]);
            Shaders.isRenderingDfb = false;
            Shaders.mc.getFramebuffer().bindFramebuffer(true);
            OpenGlHelper.func_153188_a(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, Shaders.mc.getFramebuffer().framebufferTexture, 0);
            GL11.glViewport(0, 0, Shaders.mc.displayWidth, Shaders.mc.displayHeight);
            if (EntityRenderer.anaglyphEnable) {
                final boolean b = EntityRenderer.anaglyphField != 0;
                GlStateManager.colorMask(b, !b, !b, true);
            }
            GlStateManager.depthMask(true);
            GL11.glClearColor(Shaders.clearColorR, Shaders.clearColorG, Shaders.clearColorB, 1.0f);
            GL11.glClear(16640);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179098_w();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.enableDepth();
            GlStateManager.depthFunc(519);
            GlStateManager.depthMask(false);
            checkGLError(Shaders.lIIllIIIlIlIllIl[500]);
            useProgram(29);
            checkGLError(Shaders.lIIllIIIlIlIllIl[501]);
            if (Shaders.activeCompositeMipmapSetting != 0) {
                genCompositeMipmap();
            }
            drawComposite();
            checkGLError(Shaders.lIIllIIIlIlIllIl[502]);
            Shaders.isCompositeRendered = true;
            GlStateManager.enableLighting();
            GlStateManager.func_179098_w();
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            useProgram(0);
        }
    }
    
    public static void endRender() {
        if (Shaders.isShadowPass) {
            checkGLError(Shaders.lIIllIIIlIlIllIl[503]);
        }
        else {
            if (!Shaders.isCompositeRendered) {
                renderCompositeFinal();
            }
            Shaders.isRenderingWorld = false;
            GlStateManager.colorMask(true, true, true, true);
            useProgram(0);
            RenderHelper.disableStandardItemLighting();
            checkGLError(Shaders.lIIllIIIlIlIllIl[504]);
        }
    }
    
    public static void beginSky() {
        Shaders.isRenderingSky = true;
        Shaders.fogEnabled = true;
        setDrawBuffers(Shaders.dfbDrawBuffers);
        useProgram(5);
        pushEntity(-2, 0);
    }
    
    public static void setSkyColor(final Vec3 vec3) {
        Shaders.skyColorR = (float)vec3.xCoord;
        Shaders.skyColorG = (float)vec3.yCoord;
        Shaders.skyColorB = (float)vec3.zCoord;
        setProgramUniform3f(Shaders.lIIllIIIlIlIllIl[505], Shaders.skyColorR, Shaders.skyColorG, Shaders.skyColorB);
    }
    
    public static void drawHorizon() {
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        final GameSettings gameSettings = Shaders.mc.gameSettings;
        final float n = (float)(GameSettings.renderDistanceChunks * 16);
        final double n2 = n * 0.9238;
        final double n3 = n * 0.3826;
        final double n4 = -n3;
        final double n5 = -n2;
        final double n6 = 16.0;
        final double n7 = -Shaders.cameraPositionY;
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(n4, n7, n5);
        worldRenderer.addVertex(n4, n6, n5);
        worldRenderer.addVertex(n5, n6, n4);
        worldRenderer.addVertex(n5, n7, n4);
        worldRenderer.addVertex(n5, n7, n4);
        worldRenderer.addVertex(n5, n6, n4);
        worldRenderer.addVertex(n5, n6, n3);
        worldRenderer.addVertex(n5, n7, n3);
        worldRenderer.addVertex(n5, n7, n3);
        worldRenderer.addVertex(n5, n6, n3);
        worldRenderer.addVertex(n4, n6, n3);
        worldRenderer.addVertex(n4, n7, n3);
        worldRenderer.addVertex(n4, n7, n3);
        worldRenderer.addVertex(n4, n6, n3);
        worldRenderer.addVertex(n3, n6, n2);
        worldRenderer.addVertex(n3, n7, n2);
        worldRenderer.addVertex(n3, n7, n2);
        worldRenderer.addVertex(n3, n6, n2);
        worldRenderer.addVertex(n2, n6, n3);
        worldRenderer.addVertex(n2, n7, n3);
        worldRenderer.addVertex(n2, n7, n3);
        worldRenderer.addVertex(n2, n6, n3);
        worldRenderer.addVertex(n2, n6, n4);
        worldRenderer.addVertex(n2, n7, n4);
        worldRenderer.addVertex(n2, n7, n4);
        worldRenderer.addVertex(n2, n6, n4);
        worldRenderer.addVertex(n3, n6, n5);
        worldRenderer.addVertex(n3, n7, n5);
        worldRenderer.addVertex(n3, n7, n5);
        worldRenderer.addVertex(n3, n6, n5);
        worldRenderer.addVertex(n4, n6, n5);
        worldRenderer.addVertex(n4, n7, n5);
        Tessellator.getInstance().draw();
    }
    
    public static void preSkyList() {
        GL11.glColor3f(Shaders.fogColorR, Shaders.fogColorG, Shaders.fogColorB);
        drawHorizon();
        GL11.glColor3f(Shaders.skyColorR, Shaders.skyColorG, Shaders.skyColorB);
    }
    
    public static void endSky() {
        Shaders.isRenderingSky = false;
        setDrawBuffers(Shaders.dfbDrawBuffers);
        useProgram(Shaders.lightmapEnabled ? 3 : 2);
        popEntity();
    }
    
    public static void beginUpdateChunks() {
        checkGLError(Shaders.lIIllIIIlIlIllIl[506]);
        checkFramebufferStatus(Shaders.lIIllIIIlIlIllIl[507]);
        if (!Shaders.isShadowPass) {
            useProgram(7);
        }
        checkGLError(Shaders.lIIllIIIlIlIllIl[508]);
        checkFramebufferStatus(Shaders.lIIllIIIlIlIllIl[509]);
    }
    
    public static void endUpdateChunks() {
        checkGLError(Shaders.lIIllIIIlIlIllIl[510]);
        checkFramebufferStatus(Shaders.lIIllIIIlIlIllIl[511]);
        if (!Shaders.isShadowPass) {
            useProgram(7);
        }
        checkGLError(Shaders.lIIllIIIlIlIllIl[512]);
        checkFramebufferStatus(Shaders.lIIllIIIlIlIllIl[513]);
    }
    
    public static boolean shouldRenderClouds(final GameSettings gameSettings) {
        if (!Shaders.shaderPackLoaded) {
            return true;
        }
        checkGLError(Shaders.lIIllIIIlIlIllIl[514]);
        return Shaders.isShadowPass ? Shaders.configCloudShadow : gameSettings.clouds;
    }
    
    public static void beginClouds() {
        Shaders.fogEnabled = true;
        pushEntity(-3, 0);
        useProgram(6);
    }
    
    public static void endClouds() {
        disableFog();
        popEntity();
        useProgram(Shaders.lightmapEnabled ? 3 : 2);
    }
    
    public static void beginEntities() {
        if (Shaders.isRenderingWorld) {
            useProgram(16);
            resetDisplayListModels();
        }
    }
    
    public static void nextEntity(final Entity entityId) {
        if (Shaders.isRenderingWorld) {
            useProgram(16);
            setEntityId(entityId);
        }
    }
    
    public static void setEntityId(final Entity entity) {
        if (Shaders.isRenderingWorld && !Shaders.isShadowPass && Shaders.uniformEntityId.isDefined()) {
            Shaders.uniformEntityId.setValue(EntityList.getEntityID(entity));
        }
    }
    
    public static void beginSpiderEyes() {
        if (Shaders.isRenderingWorld && Shaders.programsID[18] != Shaders.programsID[0]) {
            useProgram(18);
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, 0.0f);
            GlStateManager.blendFunc(770, 771);
        }
    }
    
    public static void endEntities() {
        if (Shaders.isRenderingWorld) {
            useProgram(Shaders.lightmapEnabled ? 3 : 2);
        }
    }
    
    public static void setEntityColor(final float n, final float n2, final float n3, final float n4) {
        if (Shaders.isRenderingWorld && !Shaders.isShadowPass) {
            Shaders.uniformEntityColor.setValue(n, n2, n3, n4);
        }
    }
    
    public static void beginLivingDamage() {
        if (Shaders.isRenderingWorld) {
            ShadersTex.bindTexture(Shaders.defaultTexture);
            if (!Shaders.isShadowPass) {
                setDrawBuffers(Shaders.drawBuffersColorAtt0);
            }
        }
    }
    
    public static void endLivingDamage() {
        if (Shaders.isRenderingWorld && !Shaders.isShadowPass) {
            setDrawBuffers(Shaders.programsDrawBuffers[16]);
        }
    }
    
    public static void beginBlockEntities() {
        if (Shaders.isRenderingWorld) {
            checkGLError(Shaders.lIIllIIIlIlIllIl[515]);
            useProgram(13);
        }
    }
    
    public static void nextBlockEntity(final TileEntity blockEntityId) {
        if (Shaders.isRenderingWorld) {
            checkGLError(Shaders.lIIllIIIlIlIllIl[516]);
            useProgram(13);
            setBlockEntityId(blockEntityId);
        }
    }
    
    public static void setBlockEntityId(final TileEntity tileEntity) {
        if (Shaders.isRenderingWorld && !Shaders.isShadowPass && Shaders.uniformBlockEntityId.isDefined()) {
            Shaders.uniformBlockEntityId.setValue(Block.getIdFromBlock(tileEntity.getBlockType()));
        }
    }
    
    public static void endBlockEntities() {
        if (Shaders.isRenderingWorld) {
            checkGLError(Shaders.lIIllIIIlIlIllIl[517]);
            useProgram(Shaders.lightmapEnabled ? 3 : 2);
            ShadersTex.bindNSTextures(Shaders.defaultTexture.getMultiTexID());
        }
    }
    
    public static void beginLitParticles() {
        useProgram(3);
    }
    
    public static void beginParticles() {
        useProgram(2);
    }
    
    public static void endParticles() {
        useProgram(3);
    }
    
    public static void readCenterDepth() {
        if (!Shaders.isShadowPass && Shaders.centerDepthSmoothEnabled) {
            Shaders.tempDirectFloatBuffer.clear();
            GL11.glReadPixels(Shaders.renderWidth / 2, Shaders.renderHeight / 2, 1, 1, 6402, 5126, Shaders.tempDirectFloatBuffer);
            Shaders.centerDepth = Shaders.tempDirectFloatBuffer.get(0);
            final float n = (float)Math.exp(Math.log(0.5) * (Shaders.diffSystemTime * 0.01f) / Shaders.centerDepthSmoothHalflife);
            Shaders.centerDepthSmooth = Shaders.centerDepthSmooth * n + Shaders.centerDepth * (1.0f - n);
        }
    }
    
    public static void beginWeather() {
        if (!Shaders.isShadowPass) {
            if (Shaders.usedDepthBuffers >= 3) {
                GlStateManager.setActiveTexture(33996);
                GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
                GlStateManager.setActiveTexture(33984);
            }
            GlStateManager.enableDepth();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.enableAlpha();
            useProgram(20);
        }
    }
    
    public static void endWeather() {
        GlStateManager.disableBlend();
        useProgram(3);
    }
    
    public static void preWater() {
        if (Shaders.usedDepthBuffers >= 2) {
            GlStateManager.setActiveTexture(33995);
            checkGLError(Shaders.lIIllIIIlIlIllIl[518]);
            GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
            checkGLError(Shaders.lIIllIIIlIlIllIl[519]);
            GlStateManager.setActiveTexture(33984);
        }
        ShadersTex.bindNSTextures(Shaders.defaultTexture.getMultiTexID());
    }
    
    public static void beginWater() {
        if (Shaders.isRenderingWorld) {
            if (!Shaders.isShadowPass) {
                useProgram(12);
                GlStateManager.enableBlend();
                GlStateManager.depthMask(true);
            }
            else {
                GlStateManager.depthMask(true);
            }
        }
    }
    
    public static void endWater() {
        if (Shaders.isRenderingWorld) {
            if (Shaders.isShadowPass) {}
            useProgram(Shaders.lightmapEnabled ? 3 : 2);
        }
    }
    
    public static void beginProjectRedHalo() {
        if (Shaders.isRenderingWorld) {
            useProgram(1);
        }
    }
    
    public static void endProjectRedHalo() {
        if (Shaders.isRenderingWorld) {
            useProgram(3);
        }
    }
    
    public static void applyHandDepth() {
        if (Shaders.configHandDepthMul != 1.0) {
            GL11.glScaled(1.0, 1.0, Shaders.configHandDepthMul);
        }
    }
    
    public static void beginHand() {
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5888);
        useProgram(19);
        checkGLError(Shaders.lIIllIIIlIlIllIl[520]);
        checkFramebufferStatus(Shaders.lIIllIIIlIlIllIl[521]);
    }
    
    public static void endHand() {
        checkGLError(Shaders.lIIllIIIlIlIllIl[522]);
        checkFramebufferStatus(Shaders.lIIllIIIlIlIllIl[523]);
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GlStateManager.blendFunc(770, 771);
        checkGLError(Shaders.lIIllIIIlIlIllIl[524]);
    }
    
    public static void beginFPOverlay() {
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }
    
    public static void endFPOverlay() {
    }
    
    public static void glEnableWrapper(final int n) {
        GL11.glEnable(n);
        if (n == 3553) {
            enableTexture2D();
        }
        else if (n == 2912) {
            enableFog();
        }
    }
    
    public static void glDisableWrapper(final int n) {
        GL11.glDisable(n);
        if (n == 3553) {
            disableTexture2D();
        }
        else if (n == 2912) {
            disableFog();
        }
    }
    
    public static void sglEnableT2D(final int n) {
        GL11.glEnable(n);
        enableTexture2D();
    }
    
    public static void sglDisableT2D(final int n) {
        GL11.glDisable(n);
        disableTexture2D();
    }
    
    public static void sglEnableFog(final int n) {
        GL11.glEnable(n);
        enableFog();
    }
    
    public static void sglDisableFog(final int n) {
        GL11.glDisable(n);
        disableFog();
    }
    
    public static void enableTexture2D() {
        if (Shaders.isRenderingSky) {
            useProgram(5);
        }
        else if (Shaders.activeProgram == 1) {
            useProgram(Shaders.lightmapEnabled ? 3 : 2);
        }
    }
    
    public static void disableTexture2D() {
        if (Shaders.isRenderingSky) {
            useProgram(4);
        }
        else if (Shaders.activeProgram == 2 || Shaders.activeProgram == 3) {
            useProgram(1);
        }
    }
    
    public static void beginLeash() {
        useProgram(1);
    }
    
    public static void endLeash() {
        useProgram(16);
    }
    
    public static void enableFog() {
        Shaders.fogEnabled = true;
        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[525], Shaders.fogMode);
    }
    
    public static void disableFog() {
        Shaders.fogEnabled = false;
        setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[526], 0);
    }
    
    public static void setFog(int fog) {
        GlStateManager.setFog(fog);
        fog = fog;
        if (Shaders.fogEnabled) {
            setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[527], fog);
        }
    }
    
    public static void sglFogi(final int n, final int fogMode) {
        GL11.glFogi(n, fogMode);
        if (n == 2917) {
            Shaders.fogMode = fogMode;
            if (Shaders.fogEnabled) {
                setProgramUniform1i(Shaders.lIIllIIIlIlIllIl[528], Shaders.fogMode);
            }
        }
    }
    
    public static void enableLightmap() {
        Shaders.lightmapEnabled = true;
        if (Shaders.activeProgram == 2) {
            useProgram(3);
        }
    }
    
    public static void disableLightmap() {
        Shaders.lightmapEnabled = false;
        if (Shaders.activeProgram == 3) {
            useProgram(2);
        }
    }
    
    public static int getEntityData() {
        return Shaders.entityData[Shaders.entityDataIndex * 2];
    }
    
    public static int getEntityData2() {
        return Shaders.entityData[Shaders.entityDataIndex * 2 + 1];
    }
    
    public static int setEntityData1(final int n) {
        Shaders.entityData[Shaders.entityDataIndex * 2] = ((Shaders.entityData[Shaders.entityDataIndex * 2] & 0xFFFF) | n << 16);
        return n;
    }
    
    public static int setEntityData2(final int n) {
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = ((Shaders.entityData[Shaders.entityDataIndex * 2 + 1] & 0xFFFF0000) | (n & 0xFFFF));
        return n;
    }
    
    public static void pushEntity(final int n, final int n2) {
        ++Shaders.entityDataIndex;
        Shaders.entityData[Shaders.entityDataIndex * 2] = ((n & 0xFFFF) | n2 << 16);
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = 0;
    }
    
    public static void pushEntity(final int n) {
        ++Shaders.entityDataIndex;
        Shaders.entityData[Shaders.entityDataIndex * 2] = (n & 0xFFFF);
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = 0;
    }
    
    public static void pushEntity(final Block block) {
        ++Shaders.entityDataIndex;
        Shaders.entityData[Shaders.entityDataIndex * 2] = ((Block.blockRegistry.getIDForObject(block) & 0xFFFF) | block.getRenderType() << 16);
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = 0;
    }
    
    public static void popEntity() {
        Shaders.entityData[Shaders.entityDataIndex * 2] = 0;
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = 0;
        --Shaders.entityDataIndex;
    }
    
    public static void mcProfilerEndSection() {
        Shaders.mc.mcProfiler.endSection();
    }
    
    public static String getShaderPackName() {
        return (Shaders.shaderPack == null) ? null : ((Shaders.shaderPack instanceof ShaderPackNone) ? null : Shaders.shaderPack.getName());
    }
    
    public static void nextAntialiasingLevel() {
        Shaders.configAntialiasingLevel += 2;
        Shaders.configAntialiasingLevel = Shaders.configAntialiasingLevel / 2 * 2;
        if (Shaders.configAntialiasingLevel > 4) {
            Shaders.configAntialiasingLevel = 0;
        }
        Shaders.configAntialiasingLevel = Config.limit(Shaders.configAntialiasingLevel, 0, 4);
    }
    
    public static void checkShadersModInstalled() {
        try {
            Class.forName(Shaders.lIIllIIIlIlIllIl[529]);
        }
        catch (Throwable t) {
            return;
        }
        throw new RuntimeException(Shaders.lIIllIIIlIlIllIl[530]);
    }
    
    public static void resourcesReloaded() {
        loadShaderPackResources();
    }
    
    private static void loadShaderPackResources() {
        Shaders.shaderPackResources = new HashMap();
        if (Shaders.shaderPackLoaded) {
            final ArrayList<String> list = new ArrayList<String>();
            final String s = Shaders.lIIllIIIlIlIllIl[531];
            final String s2 = Shaders.lIIllIIIlIlIllIl[532];
            final String s3 = Shaders.lIIllIIIlIlIllIl[533];
            list.add(String.valueOf(s) + s2 + s3);
            Config.getGameSettings();
            if (!GameSettings.language.equals(s2)) {
                final ArrayList<String> list2 = list;
                final StringBuilder sb = new StringBuilder(String.valueOf(s));
                Config.getGameSettings();
                list2.add(sb.append(GameSettings.language).append(s3).toString());
            }
            try {
                final Iterator<String> iterator = list.iterator();
                while (iterator.hasNext()) {
                    final InputStream resourceAsStream = Shaders.shaderPack.getResourceAsStream(iterator.next());
                    if (resourceAsStream != null) {
                        final Properties properties = new Properties();
                        Lang.loadLocaleData(resourceAsStream, properties);
                        resourceAsStream.close();
                        for (final String s4 : ((Hashtable<String, V>)properties).keySet()) {
                            Shaders.shaderPackResources.put(s4, properties.getProperty(s4));
                        }
                    }
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static String translate(final String s, final String s2) {
        final String s3 = Shaders.shaderPackResources.get(s);
        return (s3 == null) ? s2 : s3;
    }
    
    public static boolean isProgramPath(String substring) {
        if (substring == null) {
            return false;
        }
        if (substring.length() <= 0) {
            return false;
        }
        final int lastIndex = substring.lastIndexOf(Shaders.lIIllIIIlIlIllIl[534]);
        if (lastIndex >= 0) {
            substring = substring.substring(lastIndex + 1);
        }
        return Arrays.asList(Shaders.programNames).contains(substring);
    }
    
    private static void lllIIIIlIIllIIll() {
        (lIIllIIIlIlIllIl = new String[535])[0] = lllIIIIIllIlIIII(Shaders.lIIllIIIlllIllll[0], Shaders.lIIllIIIlllIllll[1]);
        Shaders.lIIllIIIlIlIllIl[1] = lllIIIIIllIlIIII(Shaders.lIIllIIIlllIllll[2], Shaders.lIIllIIIlllIllll[3]);
        Shaders.lIIllIIIlIlIllIl[2] = lllIIIIIllIlIIII(Shaders.lIIllIIIlllIllll[4], Shaders.lIIllIIIlllIllll[5]);
        Shaders.lIIllIIIlIlIllIl[3] = lllIIIIIllIlIlIl(Shaders.lIIllIIIlllIllll[6], Shaders.lIIllIIIlllIllll[7]);
        Shaders.lIIllIIIlIlIllIl[4] = lllIIIIIllIlIIII(Shaders.lIIllIIIlllIllll[8], Shaders.lIIllIIIlllIllll[9]);
        Shaders.lIIllIIIlIlIllIl[5] = lllIIIIIllIlIllI(Shaders.lIIllIIIlllIllll[10], Shaders.lIIllIIIlllIllll[11]);
        Shaders.lIIllIIIlIlIllIl[6] = lllIIIIIllIlIlll(Shaders.lIIllIIIlllIllll[12], Shaders.lIIllIIIlllIllll[13]);
        Shaders.lIIllIIIlIlIllIl[7] = lllIIIIIllIlIlll(Shaders.lIIllIIIlllIllll[14], Shaders.lIIllIIIlllIllll[15]);
        Shaders.lIIllIIIlIlIllIl[8] = lllIIIIIllIlIIII(Shaders.lIIllIIIlllIllll[16], Shaders.lIIllIIIlllIllll[17]);
        Shaders.lIIllIIIlIlIllIl[9] = lllIIIIIllIlIlll(Shaders.lIIllIIIlllIllll[18], Shaders.lIIllIIIlllIllll[19]);
        Shaders.lIIllIIIlIlIllIl[10] = lllIIIIIllIlIIII(Shaders.lIIllIIIlllIllll[20], Shaders.lIIllIIIlllIllll[21]);
        Shaders.lIIllIIIlIlIllIl[11] = lllIIIIIllIlIllI(Shaders.lIIllIIIlllIllll[22], Shaders.lIIllIIIlllIllll[23]);
        Shaders.lIIllIIIlIlIllIl[12] = lllIIIIIllIlIIII(Shaders.lIIllIIIlllIllll[24], Shaders.lIIllIIIlllIllll[25]);
        Shaders.lIIllIIIlIlIllIl[13] = lllIIIIIllIlIlIl("KSU+EA8rNTgpHSs1ORcAIBgoAx0hMj8=", "NGKvi");
        Shaders.lIIllIIIlIlIllIl[14] = lllIIIIIllIlIlll("cdPuyBdQDzGiO46ZhoRCHYpOxbuKASBM", "hhILI");
        Shaders.lIIllIIIlIlIllIl[15] = lllIIIIIllIlIlll("937xHi77Ozj3BuYzckCnuQ==", "qPZPL");
        Shaders.lIIllIIIlIlIllIl[16] = lllIIIIIllIlIlll("u50e0sPUvN2j08K9ZGvw5g==", "SFXVS");
        Shaders.lIIllIIIlIlIllIl[17] = lllIIIIIllIlIIII("i/x/HudB/CkUGlw4tQz2r94iAkrRAbY/", "usREP");
        Shaders.lIIllIIIlIlIllIl[18] = lllIIIIIllIlIIII("MWoH5BmGim9O6epoxkbw6g==", "vAYBy");
        Shaders.lIIllIIIlIlIllIl[19] = lllIIIIIllIlIllI("/kJwUTxcNRa5dYFVb4lN+JBBS0nUH9gi1K1Qm1gofPE=", "QuVHK");
        Shaders.lIIllIIIlIlIllIl[20] = lllIIIIIllIlIllI("wZ9VGGK4AXpoAkTm5lQ8fs2/hmX+12ONCDlS3z5fpuc=", "wFEni");
        Shaders.lIIllIIIlIlIllIl[21] = lllIIIIIllIlIIII("8YkpLuikcuoGFcW6PgSiy7KQEFI1lY7u", "oXbLY");
        Shaders.lIIllIIIlIlIllIl[22] = lllIIIIIllIlIlIl("CyEmMjMJMSALPQ0tNw==", "lCSTU");
        Shaders.lIIllIIIlIlIllIl[23] = lllIIIIIllIlIlll("QAFA9BLtWbQpJhVzRB9ZkdAfG3cYTx4I", "UAkqM");
        Shaders.lIIllIIIlIlIllIl[24] = lllIIIIIllIlIlIl("Jx0hFRg3GzgA", "DrLew");
        Shaders.lIIllIIIlIlIllIl[25] = lllIIIIIllIlIllI("2/BoRTu2wk5yPz4wgldTrw==", "CYstv");
        Shaders.lIIllIIIlIlIllIl[26] = lllIIIIIllIlIlIl("FSwnNxYFKj4iSw==", "vCJGy");
        Shaders.lIIllIIIlIlIllIl[27] = lllIIIIIllIlIllI("Oi5BrDIQtDfl+uWuSA1wxg==", "FLsIM");
        Shaders.lIIllIIIlIlIllIl[28] = lllIIIIIllIlIlll("MPyBVaG1Gi7puDQXSnGBQA==", "ehxJY");
        Shaders.lIIllIIIlIlIllIl[29] = lllIIIIIllIlIlll("HotZnceFeUHlk5wO83M4BQ==", "mHBLC");
        Shaders.lIIllIIIlIlIllIl[30] = lllIIIIIllIlIlIl("GRgBKB4JHhg9Rw==", "zwlXq");
        Shaders.lIIllIIIlIlIllIl[31] = lllIIIIIllIlIllI("dU12YFh4QybNKdkYfGdqGw==", "kkEHA");
        Shaders.lIIllIIIlIlIllIl[32] = lllIIIIIllIlIlll("8s/rYMaVXe4=", "eaisr");
        Shaders.lIIllIIIlIlIllIl[33] = lllIIIIIllIlIlIl("IjI0Piom", "QZUZE");
        Shaders.lIIllIIIlIlIllIl[34] = lllIIIIIllIlIllI("DZEeayx+it8saED9GcfrwQ==", "szSxm");
        Shaders.lIIllIIIlIlIllIl[35] = lllIIIIIllIlIllI("kA9lqO2fu8JwhBfjhLWFwQ==", "ftegy");
        Shaders.lIIllIIIlIlIllIl[36] = lllIIIIIllIlIllI("iYg9U6XJPKafZBiCaQsNag==", "uxmfg");
        Shaders.lIIllIIIlIlIllIl[37] = lllIIIIIllIlIllI("/PVdrkUV6m0ruCpk6LTyv71+tBHqFa4ErhGHx1Gm6RI=", "KJvUb");
        Shaders.lIIllIIIlIlIllIl[38] = lllIIIIIllIlIIII("pliiqWhMxbk=", "RIIlW");
        Shaders.lIIllIIIlIlIllIl[39] = lllIIIIIllIlIIII("fMN7VtdEtsH2DX0WZzth8A==", "BnpmE");
        Shaders.lIIllIIIlIlIllIl[40] = lllIIIIIllIlIlIl("FBUvCjwpBGM0MDQVLwo=", "ZpNxY");
        Shaders.lIIllIIIlIlIllIl[41] = lllIIIIIllIlIIII("5Lf4IBA1aqU=", "lSCFK");
        Shaders.lIIllIIIlIlIllIl[42] = lllIIIIIllIlIlll("GsDCp5kFqhI=", "EoElJ");
        Shaders.lIIllIIIlIlIllIl[43] = lllIIIIIllIlIlIl("Ky8u", "diheV");
        Shaders.lIIllIIIlIlIllIl[44] = lllIIIIIllIlIlIl("ejw9HzUgOzIHeQ==", "RUSkP");
        Shaders.lIIllIIIlIlIllIl[45] = lllIIIIIllIlIlll("YGdrRe35wfAc6W9Jvp6pHA==", "FaRBJ");
        Shaders.lIIllIIIlIlIllIl[46] = lllIIIIIllIlIIII("kfk+eUkTtR+UxpZkCKo738C9X869zAZg", "bZXXU");
        Shaders.lIIllIIIlIlIllIl[47] = lllIIIIIllIlIlll("wPFNap3i6lw=", "eBgLI");
        Shaders.lIIllIIIlIlIllIl[48] = lllIIIIIllIlIlll("5qkztFWb9yI=", "MLpza");
        Shaders.lIIllIIIlIlIllIl[49] = lllIIIIIllIlIllI("Q+4Ep7w74+i/Zu2xISKlYA==", "OGjNf");
        Shaders.lIIllIIIlIlIllIl[50] = lllIIIIIllIlIlll("2bR0KiBxT+YeA7n56dbQYw==", "rsfns");
        Shaders.lIIllIIIlIlIllIl[51] = lllIIIIIllIlIllI("maU72jt3VlM3jryua0diYFN3+XUj37BiX8XPnnjk1ZI=", "sTyGo");
        Shaders.lIIllIIIlIlIllIl[52] = lllIIIIIllIlIllI("M/YvMKvIg5lBlw4RYrz05kSZSYQFlhhddkLmcmRq8AQ=", "ogCYn");
        Shaders.lIIllIIIlIlIllIl[53] = lllIIIIIllIlIlIl("EzQqCiQ+LmQjKDkpZCcgMCUw", "WMDkI");
        Shaders.lIIllIIIlIlIllIl[54] = lllIIIIIllIlIlll("ZtmOGJ8R3LTG0rAi4UzadVVR/aojqcDv", "ikavp");
        Shaders.lIIllIIIlIlIllIl[55] = lllIIIIIllIlIlll("Z6pAp1gLzRg=", "WkQCL");
        Shaders.lIIllIIIlIlIllIl[56] = lllIIIIIllIlIIII("0CV/f7eckiU=", "UVBwy");
        Shaders.lIIllIIIlIlIllIl[57] = lllIIIIIllIlIIII("8ejSJTCdmrqLM3dV241pofRj/Cb06IFK2Cy2x/wUaK4CblsIwxu4RvNvrwHfR0vviCmw5j7pufet4Q6Fw7n6x3CjqFwORZMiZtGlWwOgK8I=", "LFpNN");
        Shaders.lIIllIIIlIlIllIl[58] = lllIIIIIllIlIlIl("MVRzME0JGxQeEzFUczBNCBsVATxKfSdHTzYDUUQqAwQXDBcvGhsPCw8QIU1uN15HNkdjKVAZFR8RIU1uN15BQ00=", "jtzmg");
        Shaders.lIIllIIIlIlIllIl[59] = lllIIIIIllIlIllI("zO6YwcR3/4grftLFFNYniQ==", "WzrSr");
        Shaders.lIIllIIIlIlIllIl[60] = lllIIIIIllIlIIII("2nsh1apEDPg=", "momhq");
        Shaders.lIIllIIIlIlIllIl[61] = lllIIIIIllIlIllI("Y1c7xcTRBZTqv7TGbWbOsg==", "tXpaX");
        Shaders.lIIllIIIlIlIllIl[62] = lllIIIIIllIlIllI("vPCDvOF7LwtJkho+Tjd6Hg==", "yrnbV");
        Shaders.lIIllIIIlIlIllIl[63] = lllIIIIIllIlIllI("38i2vNDe6cLQME8GPxyGBA==", "yfckr");
        Shaders.lIIllIIIlIlIllIl[64] = lllIIIIIllIlIlIl("ERJxKwUNGhs5", "CUItV");
        Shaders.lIIllIIIlIlIllIl[65] = lllIIIIIllIlIIII("0OfbFhQH3J+hIUAuE6utgg==", "QVWDB");
        Shaders.lIIllIIIlIlIllIl[66] = lllIIIIIllIlIlll("cOkmhlPfSlS02frxjHsOmQ==", "Knqku");
        Shaders.lIIllIIIlIlIllIl[67] = lllIIIIIllIlIlIl("NlhA", "divaM");
        Shaders.lIIllIIIlIlIllIl[68] = lllIIIIIllIlIlIl("FiZARw==", "Daqqc");
        Shaders.lIIllIIIlIlIllIl[69] = lllIIIIIllIlIlIl("CzYySV4=", "Yqpxh");
        Shaders.lIIllIIIlIlIllIl[70] = lllIIIIIllIlIlIl("PDUQOUhY", "nrRxy");
        Shaders.lIIllIIIlIlIllIl[71] = lllIIIIIllIlIIII("sSMFw8/TkuEvhvlY3bXMWw==", "nJUlJ");
        Shaders.lIIllIIIlIlIllIl[72] = lllIIIIIllIlIIII("+OY3qmR/jKvUKswv2zPgMw==", "mGQea");
        Shaders.lIIllIIIlIlIllIl[73] = lllIIIIIllIlIlll("61DfMc+l95SaC/jdRBUpng==", "XMZGj");
        Shaders.lIIllIIIlIlIllIl[74] = lllIIIIIllIlIlll("FblTVKDMvzNDOeij+o1pVg==", "ESDow");
        Shaders.lIIllIIIlIlIllIl[75] = lllIIIIIllIlIlIl("EVVBMA==", "Cfsvq");
        Shaders.lIIllIIIlIlIllIl[76] = lllIIIIIllIlIIII("qEK1B2789mE=", "jnscs");
        Shaders.lIIllIIIlIlIllIl[77] = lllIIIIIllIlIIII("EH6N6mhPUpk=", "cPJIH");
        Shaders.lIIllIIIlIlIllIl[78] = lllIIIIIllIlIlll("nekZi+RG+Ms=", "MHWTM");
        Shaders.lIIllIIIlIlIllIl[79] = lllIIIIIllIlIlIl("IV91HA==", "slGUH");
        Shaders.lIIllIIIlIlIllIl[80] = lllIIIIIllIlIllI("k75qDAX5ajVrfvWD0okQrQ==", "ihJdQ");
        Shaders.lIIllIIIlIlIllIl[81] = lllIIIIIllIlIllI("X49F9rLiRw6N/DkgQwy9fQ==", "XtVgf");
        Shaders.lIIllIIIlIlIllIl[82] = lllIIIIIllIlIlll("/6l/sSBGFE0=", "kYVcq");
        Shaders.lIIllIIIlIlIllIl[83] = lllIIIIIllIlIllI("qpZSDHaTpHIZVqIqujpgKg==", "mNHzd");
        Shaders.lIIllIIIlIlIllIl[84] = lllIIIIIllIlIIII("R+dXM4HjnkI=", "lASjO");
        Shaders.lIIllIIIlIlIllIl[85] = lllIIIIIllIlIIII("IOGpw7mJ0UU=", "eorNW");
        Shaders.lIIllIIIlIlIllIl[86] = lllIIIIIllIlIIII("eif0Qu6VhCPzvp1semwLIA==", "MjFnG");
        Shaders.lIIllIIIlIlIllIl[87] = lllIIIIIllIlIllI("lMayURL2QAtdeqFTMqimYbD4+G5tSZEqskdODRY+9j8=", "nJrFL");
        Shaders.lIIllIIIlIlIllIl[88] = lllIIIIIllIlIlIl("HhcRMEgBEBEwDSALPTsMchsfOg47HwUmCSYRHzpG", "RxpTh");
        Shaders.lIIllIIIlIlIllIl[89] = lllIIIIIllIlIlIl("PhkENhUcWBk1UBcICDRQDBAIegMQGQk/AggZDjEDWBwEKBUbDAIoCUJY", "xxmZp");
        Shaders.lIIllIIIlIlIllIl[90] = lllIIIIIllIlIlll("5ZL/i/tMzxo=", "hgBfX");
        Shaders.lIIllIIIlIlIllIl[91] = lllIIIIIllIlIllI("ammr7Icsof8f4hJj4/KFry5/sWb8vfzeOoHmcd2Ol4g=", "nlOzh");
        Shaders.lIIllIIIlIlIllIl[92] = lllIIIIIllIlIlll("6AhRsl8LE78M1WYM0JlfEM5OYXgjd9n4f3RXKEPM/mo=", "oxPBx");
        Shaders.lIIllIIIlIlIllIl[93] = lllIIIIIllIlIlIl("HwcrKzV6BjgyLjQSeScoNBMwIzIoFC0tKDRPeQ==", "ZuYDG");
        Shaders.lIIllIIIlIlIllIl[94] = lllIIIIIllIlIllI("S9cn9lWs8FsPcNcENmz35w==", "EEfCj");
        Shaders.lIIllIIIlIlIllIl[95] = lllIIIIIllIlIllI("ZKtpW5v2QKmkSsWbY4FrEIQ3cJAsvkMMVjuYmkf124s=", "tAqbc");
        Shaders.lIIllIIIlIlIllIl[96] = lllIIIIIllIlIlIl("CgUjNhwrHmIxGDdNLD0NeQ8nchU2DCY3HXVNAzwNMAwuOxgqBCw1WTAeYjcXOA8uNx1jTQ==", "YmBRy");
        Shaders.lIIllIIIlIlIllIl[97] = lllIIIIIllIlIIII("2yoWGlI/2AE=", "zqZWH");
        Shaders.lIIllIIIlIlIllIl[98] = lllIIIIIllIlIlIl("EhkmKi0zAmctKS9RKSE8YRMibiQuECMrLG1RBiAhMh4zPCcxGCRuDigdMys6KB8gbiEyUSIgKSMdIipyYQ==", "AqGNH");
        Shaders.lIIllIIIlIlIllIl[99] = lllIIIIIllIlIllI("JF/LtTiN+2V/5ytTFBtllw==", "HfcVT");
        Shaders.lIIllIIIlIlIllIl[100] = lllIIIIIllIlIlIl("BQ8CMgYkFEM1AjhHDTkXdgUGdg85BgczB3pHJTcQIkcxMw0yAhF2CiVHBjgCNAsGMk0=", "VgcVc");
        Shaders.lIIllIIIlIlIllIl[101] = lllIIIIIllIlIlll("iQPhRIan7AI=", "cACSy");
        Shaders.lIIllIIIlIlIllIl[102] = lllIIIIIllIlIlIl("DhgEFyQmVxYbICYSFwMgIRxfUw==", "BwesA");
        Shaders.lIIllIIIlIlIllIl[103] = lllIIIIIllIlIlIl("Ai5KKR0tJQ8oBS0iAXoZIyAOPxFi", "LAjZu");
        Shaders.lIIllIIIlIlIllIl[104] = lllIIIIIllIlIlIl("Qz8FDgEJPh5AEgM+AQs=", "lLmoe");
        Shaders.lIIllIIIlIlIllIl[105] = lllIIIIIllIlIIII("+CiHoI60ufI=", "rzJbL");
        Shaders.lIIllIIIlIlIllIl[106] = lllIIIIIllIlIlll("/Qf5sI30Oo4EfHfCGME6sfb0p/UXCFux", "uktcr");
        Shaders.lIIllIIIlIlIllIl[107] = lllIIIIIllIlIllI("KOryO9Oqrkvlg5mm2Nh4espZ4PyDkH8O+sdncJT/170=", "XNXkO");
        Shaders.lIIllIIIlIlIllIl[108] = lllIIIIIllIlIIII("ZN5eHgHRxcke9z1iuSZQTEXWIWrP1ENFjoqBam95YFg=", "Jaexe");
        Shaders.lIIllIIIlIlIllIl[109] = lllIIIIIllIlIIII("lRImjPjd8us=", "YDRok");
        Shaders.lIIllIIIlIlIllIl[110] = lllIIIIIllIlIllI("Y8IhFfBq2TSo/cLe3hp/dA==", "FiRmt");
        Shaders.lIIllIIIlIlIllIl[111] = lllIIIIIllIlIlIl("EyQ7GDMtBSAkdw0FIRYlaAQyDz4mEHMaOCYROh4iOhYnEDgmVzUWJWg=", "HwSyW");
        Shaders.lIIllIIIlIlIllIl[112] = lllIIIIIllIlIlll("VkP4K2VRsDQ=", "alJEI");
        Shaders.lIIllIIIlIlIllIl[113] = lllIIIIIllIlIIII("dcPwXFk6Qeg=", "uQJtS");
        Shaders.lIIllIIIlIlIllIl[114] = lllIIIIIllIlIIII("DFoU7hb3geCLFe8Je2LnGxrDjVhsT6nhC20iJerk90brdPZd/bKAnw==", "fZMJu");
        Shaders.lIIllIIIlIlIllIl[115] = lllIIIIIllIlIlll("LQfHBtYL9ah8mr5b+AB/qg==", "nJSSD");
        Shaders.lIIllIIIlIlIllIl[116] = lllIIIIIllIlIIII("0wn/q4pbmNoGdcra53AECFFq0ztYN4cYX+tzt5nmB9NXNMRJmBRJLsWb5IgUiVga", "kRZgm");
        Shaders.lIIllIIIlIlIllIl[117] = lllIIIIIllIlIIII("IbYGg4wsRak=", "XpjJi");
        Shaders.lIIllIIIlIlIllIl[118] = lllIIIIIllIlIIII("g5HqorVmsRs=", "xfMYl");
        Shaders.lIIllIIIlIlIllIl[119] = lllIIIIIllIlIlIl("IAIHHgYhGQ==", "Sjfzc");
        Shaders.lIIllIIIlIlIllIl[120] = lllIIIIIllIlIlIl("ZAAoCg==", "JzAzT");
        Shaders.lIIllIIIlIlIllIl[121] = lllIIIIIllIlIlIl("Zw==", "IOECC");
        Shaders.lIIllIIIlIlIllIl[122] = lllIIIIIllIlIllI("+ozCTEG75MTSlTJPRvYUQQ==", "lqarx");
        Shaders.lIIllIIIlIlIllIl[123] = lllIIIIIllIlIlIl("CAIwASwsBTcKLDwjJQ09OwNxXDFrQGU0aS8EcUk6RA==", "NpQlI");
        Shaders.lIIllIIIlIlIllIl[124] = lllIIIIIllIlIlll("Yfo4kDiaBK1AhXVAf9GwTwQ2a5QejkgwmJ6vLu/JV+faCDIj8OhKAQbPtO2zFDvq", "nEmEk");
        Shaders.lIIllIIIlIlIllIl[125] = lllIIIIIllIlIllI("RQzp37jR5pC0OA+fHQX7kyWIB53oovQNfFVq2xbi3AQ=", "WjmAC");
        Shaders.lIIllIIIlIlIllIl[126] = lllIIIIIllIlIIII("wXUSRGcLKafpuDqu5OerR12ZZoUWtwcvko64qWFv0aI=", "hMAUQ");
        Shaders.lIIllIIIlIlIllIl[127] = lllIIIIIllIlIIII("1aEvJ51CBWkF3NXPGSzhWuzEBR4vzK0mcuuwCtiATsg0zQUq8gc8kA==", "lGipO");
        Shaders.lIIllIIIlIlIllIl[128] = lllIIIIIllIlIlIl("dDglLiJ0", "TcUAQ");
        Shaders.lIIllIIIlIlIllIl[129] = lllIIIIIllIlIllI("/0vnrWjal385CQcsnoIJQA==", "YtUqn");
        Shaders.lIIllIIIlIlIllIl[130] = lllIIIIIllIlIlIl("dxUtNHk=", "WvLDY");
        Shaders.lIIllIIIlIlIllIl[131] = lllIIIIIllIlIlll("KgDUBPKnusg=", "UfRDA");
        Shaders.lIIllIIIlIlIllIl[132] = lllIIIIIllIlIllI("qBw51huNmA0p8bPuQd1IGg==", "JspYP");
        Shaders.lIIllIIIlIlIllIl[133] = lllIIIIIllIlIllI("kJEpLeRSUav0hUCyOB6vlA==", "vnBkP");
        Shaders.lIIllIIIlIlIllIl[134] = lllIIIIIllIlIlIl("GysSFi46MD4dL2g1FgA4ISwdSGt6bUdceno=", "HCsrK");
        Shaders.lIIllIIIlIlIllIl[135] = lllIIIIIllIlIlll("VboFUPsvf29qVOpArBYbzPMe1jua6A3w", "LMDuD");
        Shaders.lIIllIIIlIlIllIl[136] = lllIIIIIllIlIllI("FNGRXvUANIjkClJtIcw9gg==", "AGMHC");
        Shaders.lIIllIIIlIlIllIl[137] = lllIIIIIllIlIIII("FSirOC97tELG9kq/WogOZQ==", "MHAwD");
        Shaders.lIIllIIIlIlIllIl[138] = lllIIIIIllIlIllI("sszNGUc/snpMq8C21n/31g==", "dgswj");
        Shaders.lIIllIIIlIlIllIl[139] = lllIIIIIllIlIlIl("a2VXYkw=", "KWyRl");
        Shaders.lIIllIIIlIlIllIl[140] = lllIIIIIllIlIIII("U4SUS0WY6Kc=", "DYMDq");
        Shaders.lIIllIIIlIlIllIl[141] = lllIIIIIllIlIllI("gxYUyHsypc0XHMJkm8xZhA==", "hIFoS");
        Shaders.lIIllIIIlIlIllIl[142] = lllIIIIIllIlIllI("L76ag2bUPZJg+a6AiV9fYw==", "KUsjv");
        Shaders.lIIllIIIlIlIllIl[143] = lllIIIIIllIlIIII("M56ndQvMn08=", "wKiAs");
        Shaders.lIIllIIIlIlIllIl[144] = lllIIIIIllIlIlll("678gFsU2Y4U=", "AdAOz");
        Shaders.lIIllIIIlIlIllIl[145] = lllIIIIIllIlIlll("3smWW/M0Xhw=", "eIjUm");
        Shaders.lIIllIIIlIlIllIl[146] = lllIIIIIllIlIlIl("Z15B", "GsagB");
        Shaders.lIIllIIIlIlIllIl[147] = lllIIIIIllIlIlIl("RUxCSVc=", "exlyw");
        Shaders.lIIllIIIlIlIllIl[148] = lllIIIIIllIlIIII("8le3ECt4m6c=", "qtgLp");
        Shaders.lIIllIIIlIlIllIl[149] = lllIIIIIllIlIllI("EoKvRoKunpy/bl25/R/5xnq+UHw0mpq2XJXsq5ySRS0=", "rqOdd");
        Shaders.lIIllIIIlIlIllIl[150] = lllIIIIIllIlIIII("VZsCVr9W3TP5HSdp5Myp+q6F6BDPdAhA0Sx0PHJnE4Y=", "JjSTO");
        Shaders.lIIllIIIlIlIllIl[151] = lllIIIIIllIlIlll("5euEcesUCqdjOUhr9oYZyFb+J/MVclZ2CApRiwztoPM=", "HVjbW");
        Shaders.lIIllIIIlIlIllIl[152] = lllIIIIIllIlIlIl("PQ==", "dHdmp");
        Shaders.lIIllIIIlIlIllIl[153] = lllIIIIIllIlIllI("bVytRiz0yZNHJVrhGOiQ1Q==", "QxXAi");
        Shaders.lIIllIIIlIlIllIl[154] = lllIIIIIllIlIlll("7medhB9bWlWXTTt6hTxdhhdTq7xjnRZq", "wrgsV");
        Shaders.lIIllIIIlIlIllIl[155] = lllIIIIIllIlIlll("lYbWKqJW9cAIq7e5HPlXcw==", "RAeTx");
        Shaders.lIIllIIIlIlIllIl[156] = lllIIIIIllIlIllI("jvL3IfntscLKNuxIMueqNZvnOd1nB3FUR6nhUe16Xjc=", "JeJSv");
        Shaders.lIIllIIIlIlIllIl[157] = lllIIIIIllIlIIII("v38MMkPga58=", "RBlNH");
        Shaders.lIIllIIIlIlIllIl[158] = lllIIIIIllIlIllI("vI6KIsBckAI8iDIN60WgUg==", "oVSKb");
        Shaders.lIIllIIIlIlIllIl[159] = lllIIIIIllIlIIII("XB1M8TskSpE=", "gwDct");
        Shaders.lIIllIIIlIlIllIl[160] = lllIIIIIllIlIlll("rcLVlilp4EU=", "DmlTk");
        Shaders.lIIllIIIlIlIllIl[161] = lllIIIIIllIlIlIl("", "ZDawI");
        Shaders.lIIllIIIlIlIllIl[162] = lllIIIIIllIlIllI("4T3wSodfWHsuPSiNS9SPDVn3gVCpnhvCWOFqPQ6Fnm8=", "UVsId");
        Shaders.lIIllIIIlIlIllIl[163] = lllIIIIIllIlIllI("iA2bfFq827o2vpDUBbXiEQ==", "xePyS");
        Shaders.lIIllIIIlIlIllIl[164] = lllIIIIIllIlIIII("s5J7Zl2e8Qgha3vrpB2U6A==", "tArgD");
        Shaders.lIIllIIIlIlIllIl[165] = lllIIIIIllIlIlIl("SQU2Gg==", "gsErZ");
        Shaders.lIIllIIIlIlIllIl[166] = lllIIIIIllIlIllI("2RbOb8ue5Fe5YFBvJIZL/A==", "TxzbO");
        Shaders.lIIllIIIlIlIllIl[167] = lllIIIIIllIlIlIl("MQoXNT0AFVg+IAAcHTZ1QQ==", "axxRO");
        Shaders.lIIllIIIlIlIllIl[168] = lllIIIIIllIlIlIl("MhcBIBYoCAs2FzICAiEnNF5E", "GddDU");
        Shaders.lIIllIIIlIlIllIl[169] = lllIIIIIllIlIlll("V0zp0Qn7/ek5DnodVC+kQo52FLrVNuxP", "gHXQY");
        Shaders.lIIllIIIlIlIllIl[170] = lllIIIIIllIlIllI("x2ak3szkBqHTfteeR7i34Dmt2iTzIUfjfvFwvLKIk3g=", "kRoxV");
        Shaders.lIIllIIIlIlIllIl[171] = lllIIIIIllIlIlll("B0kHDd4oDwq0uYbFaZXLHyxszMrbxCnvGfafhVkEDaE=", "pvEEs");
        Shaders.lIIllIIIlIlIllIl[172] = lllIIIIIllIlIIII("J+lOaSWU1opx4azaNPZL/KYpxmt7usVt", "aHRnU");
        Shaders.lIIllIIIlIlIllIl[173] = lllIIIIIllIlIlIl("DCM/HQsLMS07Oh82Pws8Q3A=", "yPZyO");
        Shaders.lIIllIIIlIlIllIl[174] = lllIIIIIllIlIlIl("IQIMNyEfIxcLZT8jFjk3QHEqOTFaNAo5MB05RDI3GyZENDAcNwEkNlZxCjMgHjQAbGU=", "zQdVE");
        Shaders.lIIllIIIlIlIllIl[175] = lllIIIIIllIlIlll("BLRj2R5V4VBVeKnt0+9fDQ==", "SXFGl");
        Shaders.lIIllIIIlIlIllIl[176] = lllIIIIIllIlIIII("hCZVn8rRP29I3moNiEZXYw==", "EKVKN");
        Shaders.lIIllIIIlIlIllIl[177] = lllIIIIIllIlIlll("yftWYGtzERdhUV/r+v5DqApJthVqHJYU", "TJyuV");
        Shaders.lIIllIIIlIlIllIl[178] = lllIIIIIllIlIlll("CuVOa2M49e6WIdu9xHNswbuGZDxZYzWw", "irmwa");
        Shaders.lIIllIIIlIlIllIl[179] = lllIIIIIllIlIIII("MhmfKic/+18B9QhF3bKwaxWOioOKkD4K", "ypXQh");
        Shaders.lIIllIIIlIlIllIl[180] = lllIIIIIllIlIlll("3xKCEeKaAgg=", "xnyFy");
        Shaders.lIIllIIIlIlIllIl[181] = lllIIIIIllIlIllI("VWos5dsVcQCxd5VP4Zfr5g==", "DvZzP");
        Shaders.lIIllIIIlIlIllIl[182] = lllIIIIIllIlIllI("zzmKJPd+80p6ziEXJ9Kekg==", "CnPQf");
        Shaders.lIIllIIIlIlIllIl[183] = lllIIIIIllIlIlll("OA/n1qmSz5o=", "tKsnI");
        Shaders.lIIllIIIlIlIllIl[184] = lllIIIIIllIlIlIl("DA08EyQVBxcv", "ancVJ");
        Shaders.lIIllIIIlIlIllIl[185] = lllIIIIIllIlIIII("ZtkRC/PAcyR9boUHuF5pHQ==", "BKXju");
        Shaders.lIIllIIIlIlIllIl[186] = lllIIIIIllIlIlll("LfvPmRpGONlLXPFzZQEDUg==", "NJBdQ");
        Shaders.lIIllIIIlIlIllIl[187] = lllIIIIIllIlIllI("jJveSQ4K9YfjdfDeglwIsA==", "chBPC");
        Shaders.lIIllIIIlIlIllIl[188] = lllIIIIIllIlIlIl("IhotNQYtCRcvEw==", "CnrAg");
        Shaders.lIIllIIIlIlIllIl[189] = lllIIIIIllIlIlll("idJOJdaZvK/QDkmugICBYA==", "tPvmz");
        Shaders.lIIllIIIlIlIllIl[190] = lllIIIIIllIlIlll("P9mbrSdGiqndjrCZDz0VCfzC+2ZycE7n", "zCUxQ");
        Shaders.lIIllIIIlIlIllIl[191] = lllIIIIIllIlIllI("xJgYjnEx57H8kfPzVQX9rw==", "SYBqq");
        Shaders.lIIllIIIlIlIllIl[192] = lllIIIIIllIlIlll("NQBwN9HDim8=", "lzJVi");
        Shaders.lIIllIIIlIlIllIl[193] = lllIIIIIllIlIlIl("ag==", "HQvxO");
        Shaders.lIIllIIIlIlIllIl[194] = lllIIIIIllIlIlll("+eoMxPb6wCRDVJWJGcKrkNMti6LxmNMx2mkQBaaBRIp9Ogz+mpOHHg==", "INHxS");
        Shaders.lIIllIIIlIlIllIl[195] = lllIIIIIllIlIlIl("NxcFNwQ0FgUgTQ08EGgXF04rdUBvPlplADU8NCsZPxcIa0c=", "VcqEm");
        Shaders.lIIllIIIlIlIllIl[196] = lllIIIIIllIlIIII("8apaTbsfYfEncgPmqNViZMSPb455LXOppvq49ZjbuqxrzDvSb9F6aHqK8NFOQWIv", "ZlqNC");
        Shaders.lIIllIIIlIlIllIl[197] = lllIIIIIllIlIlIl("aGkSKAkLNhkwPxImDQc5KTERd3hs", "FCuDV");
        Shaders.lIIllIIIlIlIllIl[198] = lllIIIIIllIlIlll("GMLYkyv2gnZMSYum2WZhbj0wl3FIWXvjt53K2WLAi88Nr+4xAKj5LA==", "Jmcal");
        Shaders.lIIllIIIlIlIllIl[199] = lllIIIIIllIlIllI("4yAqIMoKSUJ3xXSzOHSZ6Q==", "VnFrL");
        Shaders.lIIllIIIlIlIllIl[200] = lllIIIIIllIlIllI("7ZsRyvwYuxd1ssiPIILHdQ==", "KhewR");
        Shaders.lIIllIIIlIlIllIl[201] = lllIIIIIllIlIlll("9MXHaGHSKrfAnE4iELnjhlA9KozwrOrb8yqi17MWOTs=", "bqmjI");
        Shaders.lIIllIIIlIlIllIl[202] = lllIIIIIllIlIllI("ooeSPF3QcC5QJe+z88si0Q==", "ocyNT");
        Shaders.lIIllIIIlIlIllIl[203] = lllIIIIIllIlIlll("tOmPbSxyNMfqDAmelBs4yzfl4gupw+F4/PAf3zcd91o5m4iFL8j6Nw==", "BMdcR");
        Shaders.lIIllIIIlIlIllIl[204] = lllIIIIIllIlIlll("sMRGXLgwdQ4dOnOeOjRWWeTAon8nmRyIX0eDmOxoGb/lfLQiAUxdpg==", "CfbgH");
        Shaders.lIIllIIIlIlIllIl[205] = lllIIIIIllIlIllI("u4jCPHhI8dFNLj6tr1M714kZsGGc8XAlboLMlfz+Fgm4TMBYA3AB0Azoq2Nwl4iB", "Clhqk");
        Shaders.lIIllIIIlIlIllIl[206] = lllIIIIIllIlIllI("tjSInrBWCwcTTlj/bBEZiboubEB0LWExr/TaA2VF0eMQkOlDwNFwaQrkHo3RROQ7", "HblLR");
        Shaders.lIIllIIIlIlIllIl[207] = lllIIIIIllIlIlll("KiIehpq19bKqY419niocTE9197gw/7wMUs5MPad+fO6QX0czxH17ew==", "wKHkG");
        Shaders.lIIllIIIlIlIllIl[208] = lllIIIIIllIlIlll("LolZJHmB36/2VyAyi6TpoqsTfWJxEpvPeecfL5512WQDVexMM9ZKyg==", "oFtwP");
        Shaders.lIIllIIIlIlIllIl[209] = lllIIIIIllIlIllI("pKRnO8qOBnz5NXPFD6sfgCc0I84XPdGEWgtDLtQv5blZpU1JCnNjFS7LP9gOTDNg", "wetVC");
        Shaders.lIIllIIIlIlIllIl[210] = lllIIIIIllIlIllI("ZU5kxjUC9GoefO6vNxaA7ZyMWyE+WMzrutlbvG1ha2Qkskytg1RMVDq6rv24/rqK", "MQdqd");
        Shaders.lIIllIIIlIlIllIl[211] = lllIIIIIllIlIIII("tTqHudkyIgjZL843jW8nso5+bjn6L4oX17kLKJtDEbTghi0zOneZgw==", "czNRC");
        Shaders.lIIllIIIlIlIllIl[212] = lllIIIIIllIlIllI("eNO5g8Qb1ydKtmnncytmqEfwb5BNfJc/9YkbBlMLz4thJtnDFAH6goTtGXfBcJjx", "irzZS");
        Shaders.lIIllIIIlIlIllIl[213] = lllIIIIIllIlIllI("VL5XLdvnaoxR90WRPgp5vzxHM980zHv0ie9KFhoqZgPQnsmeIU8cC9vxnUzTqq4f", "lwqdR");
        Shaders.lIIllIIIlIlIllIl[214] = lllIIIIIllIlIlIl("NiUDHzYxJkoieRwqRwMYbhFaVGAeYEodPDM/Ag08O3lRV3M=", "CKjyY");
        Shaders.lIIllIIIlIlIllIl[215] = lllIIIIIllIlIIII("QXL985RPnEyVc9m9nF8X95TZ2ADBJcIAB0fBQB9Samn+HEzjiIN1yQ==", "CrVEy");
        Shaders.lIIllIIIlIlIllIl[216] = lllIIIIIllIlIIII("XE7YORRdonNXWaxl+kbt81Vb8OiaF/eYiMAAW71U5r0=", "GwYad");
        Shaders.lIIllIIIlIlIllIl[217] = lllIIIIIllIlIllI("G+ivnAjXi4qLFyx9kZxW7rNgSYoIPhiwmwKAeKXAtpQ=", "DZNPS");
        Shaders.lIIllIIIlIlIllIl[218] = lllIIIIIllIlIllI("mHtrX06a7+ASjQA1bp6AD9OZW3kgSR12kQMAQx4Jzec=", "ATTzv");
        Shaders.lIIllIIIlIlIllIl[219] = lllIIIIIllIlIIII("HhYm5TZIbFsqVlxIIzLt09L5ndhYnyDfqTEtoZZjUtM=", "CcFER");
        Shaders.lIIllIIIlIlIllIl[220] = lllIIIIIllIlIlIl("BxoILSoAGUEQZS0VTDEEXy5RZnwvX0EoKh4bEz8gCkBaZW8=", "rtaKE");
        Shaders.lIIllIIIlIlIllIl[221] = lllIIIIIllIlIllI("PDrhDs93HGmDjQX1S+orsgn5WgB027dC0+lekPgiUvbr3PDqboEj66eF2xg5V9Rs", "MsnMO");
        Shaders.lIIllIIIlIlIllIl[222] = lllIIIIIllIlIllI("/Lt2tT2WjJfrn+HmyNv3U5xxw7ufdCtdm2J1UvJK+jautxtO2RCLTQHKiCDFkMx9", "oxZgo");
        Shaders.lIIllIIIlIlIllIl[223] = lllIIIIIllIlIllI("7zQwUp7EG+6eg6RAuZF2zaCHYmCxuHQsNGjrFLaTgLOuDlNVlpHR0t/pV9MHwTnD", "ZuwQU");
        Shaders.lIIllIIIlIlIllIl[224] = lllIIIIIllIlIllI("JquFV01cchLv+rSxOof5xZuhsRgZ3PFvEf9l2Jrsr/rLhizHxQHOuPNA/t+89PxR", "wyamH");
        Shaders.lIIllIIIlIlIllIl[225] = lllIIIIIllIlIlIl("SAtHVgkvFik5DTUSPkwBV3pUK3FHC0dZdE0=", "gWmvZ");
        Shaders.lIIllIIIlIlIllIl[226] = lllIIIIIllIlIIII("2YMbkb4i1hQ=", "lotsL");
        Shaders.lIIllIIIlIlIllIl[227] = lllIIIIIllIlIllI("klWfGGSiHgXUGLG+wTJTFcJ0wgMCX3BrPZdQwQEZsps=", "sAocb");
        Shaders.lIIllIIIlIlIllIl[228] = lllIIIIIllIlIIII("ht0eAciZgD4H8EVttrof6uT40gGocMc5s8ZE2FheqfL9OqinIy7haroAILuIbmm7b9/cwKcGVFBsB8OTsc6rjF+yLqAtGobY", "pKWOV");
        Shaders.lIIllIIIlIlIllIl[229] = lllIIIIIllIlIlll("L1SXYxs56oufkiIiQbnM2Q==", "mqIJV");
        Shaders.lIIllIIIlIlIllIl[230] = lllIIIIIllIlIllI("m5YcTLX2vD81KCWJYM0Mf79+Vpk7NSZYx0Lon0RCbDo=", "oFtaq");
        Shaders.lIIllIIIlIlIllIl[231] = lllIIIIIllIlIllI("6otU6cIOBQ1pKuGUqIQrhkKc3Cv7maBQabWUvvA4yA4=", "piYKy");
        Shaders.lIIllIIIlIlIllIl[232] = lllIIIIIllIlIlll("ekSLNCCy0qA=", "zZTXn");
        Shaders.lIIllIIIlIlIllIl[233] = lllIIIIIllIlIlIl("JTsjKQwBcy8sE1Y1KygPEnMtK0MAOic6WVY=", "vSBMc");
        Shaders.lIIllIIIlIlIllIl[234] = lllIIIIIllIlIllI("MTOJwYsm0JmrPsYGkk7qg8R/fdao+7B1/JLgPx4cyLw=", "BMbmB");
        Shaders.lIIllIIIlIlIllIl[235] = lllIIIIIllIlIIII("w2sfJt/11gY=", "wAVOn");
        Shaders.lIIllIIIlIlIllIl[236] = lllIIIIIllIlIllI("g2oj0kI+HMbiEpXXOslMZJDz8dLjKb2ILTxcZ+9TvlY=", "HxXsV");
        Shaders.lIIllIIIlIlIllIl[237] = lllIIIIIllIlIlll("LzTCSWxffwF3Q0WUpw/hQux0EIYsgVuuJR5xMXdw7GHaTFoRYb21S4LJi97g1WMkcarFB4oP9mC2QU769QBQAwbbsygIyeim", "tREVq");
        Shaders.lIIllIIIlIlIllIl[238] = lllIIIIIllIlIIII("/3BeV18MihHfuFvobG0KWA==", "gUOEp");
        Shaders.lIIllIIIlIlIllIl[239] = lllIIIIIllIlIllI("cG/iJYUPXic9Mgl0fRuiRzHTzjT63kaJ4i1xYakXtbA=", "yNrMn");
        Shaders.lIIllIIIlIlIllIl[240] = lllIIIIIllIlIlll("VmoA4s3AoQZtUY03TjGlacz/rVXjSE8EQ8g4x8Pa2rG+wi+HIhR8PedWcgLJETo9SnKaATv80sASs4YMdSYn9ro9fvB5+9Gs", "ReDUP");
        Shaders.lIIllIIIlIlIllIl[241] = lllIIIIIllIlIllI("oIAFlm2QEJxEc3caJRfAuw==", "KccZZ");
        Shaders.lIIllIIIlIlIllIl[242] = lllIIIIIllIlIIII("HhhryRGaw17JXcclkygcXPBRUti+gErAvSgW3qzFS9U=", "LdLSn");
        Shaders.lIIllIIIlIlIllIl[243] = lllIIIIIllIlIlIl("I3l8GkYbNhs0GCN5fBpGGjYaKzdYUChtCx03EDUNDDwmLw0cNgIKBQg0FDc3WFAobVEjeXwaRgwrACI3WFAobVdWcw==", "xYuGl");
        Shaders.lIIllIIIlIlIllIl[244] = lllIIIIIllIlIlIl("MQkgMwcXGCt2Bh4NKjkCVgEnJhgXHA==", "vlNVu");
        Shaders.lIIllIIIlIlIllIl[245] = lllIIIIIllIlIlIl("F3B6NmgvPx0YNhdwejZoLj8cBxlsWS5BJSk+FhkjODUgAyMoPwQoLSA/ASYrPD0SGxlsWS5BfxdwejZoOCIGDhlsWS5BeWJ6", "LPskB");
        Shaders.lIIllIIIlIlIllIl[246] = lllIIIIIllIlIlll("ssXSuww/rU0mRh8R/WARRAsqUXY6vV/OKrmme+6Ga3k=", "PcDbQ");
        Shaders.lIIllIIIlIlIllIl[247] = lllIIIIIllIlIlIl("CExLCHswAywmJQhMSwh7MQMtOQpzZR9/IjsNJjomGw0wMSYyHicTOD8YJyc4PQsZdVgORn8OcVoxaCEjJgkZdVgORnl7ew==", "SlBUQ");
        Shaders.lIIllIIIlIlIllIl[248] = lllIIIIIllIlIIII("mX3V7XaiMtd90/wIyzbtdj+sHdBmarTWcJ1Df+LJZcR7ELkd8ToBHg==", "QHpXW");
        Shaders.lIIllIIIlIlIllIl[249] = lllIIIIIllIlIlIl("CVBtF2QxHwo5OglQbRdkMB8LJhVyeTlgPToRACU5GhEWLjkzAgEMJz4EATgnPBdUEW5bLU53FXJ5OWA6IAUBEW5bLU5xYHg=", "RpdJN");
        Shaders.lIIllIIIlIlIllIl[250] = lllIIIIIllIlIIII("Xr1y1TuiuZ405c8p4RNYguLaIunqhoLWDwvIVnCSWK4=", "qJhXH");
        Shaders.lIIllIIIlIlIllIl[251] = lllIIIIIllIlIllI("DrMLE71jivN8vAQ0m2GSVZgBsZU1c8v5YennYYYZVe/mXaZ6dSjYIdVsHeLm7y/vxLUDtBuYVvhQeGxdoq5eQXHwPSW4bVmDo3DH8rBJVbY=", "jjrzr");
        Shaders.lIIllIIIlIlIllIl[252] = lllIIIIIllIlIIII("l2aGRGaaqPdzZy/oidVzwRJrQIKT9k6DnDDl9QqXtRg=", "lYWBv");
        Shaders.lIIllIIIlIlIllIl[253] = lllIIIIIllIlIlIl("LXlIBWYVNi8rOC15SAVmFDYuNBdWUBxyZAUxIDwjAS0kIHw7MDE1LQYlMjAtEjY2LCkOFCgoIRcpaANsfwRrZRdWUBxyOAQsJANsfwRrY2Jc", "vYAXL");
        Shaders.lIIllIIIlIlIllIl[254] = lllIIIIIllIlIllI("AkJLOvCpL5e8xuUYYN18Ux/r0gU4TWNUHb5uZKXA8A8=", "nuIQi");
        Shaders.lIIllIIIlIlIllIl[255] = lllIIIIIllIlIIII("YDLieWstVWynvoa/TDULjeND2DQTtDBxzPKSjfCYAkkLwibbr+NUImr+7MP6+f/5/2CclUi8NrqfS6pNMSM96q0MZb9L9O5Z", "hPSdA");
        Shaders.lIIllIIIlIlIllIl[256] = lllIIIIIllIlIlIl("CSsCHT8NNwYBYTcqExQxCg==", "zCcyP");
        Shaders.lIIllIIIlIlIllIl[257] = lllIIIIIllIlIllI("zI5yESpnpCNwWPT2e2OCyagiS6WhlDVoTuvNeSXckI2z90raE7HlrdLapUdhPlCQN1XO5A9zr5/31LkA2uYDbLZpxFO5kYkRZlcV9cWuJuE4b27ZYmkv9YSaiaVpk9yX", "GuCtD");
        Shaders.lIIllIIIlIlIllIl[258] = lllIIIIIllIlIIII("5zn8Z4db8nwGHySx7rQV/FJR5fhcJ4Nf", "FtHZk");
        Shaders.lIIllIIIlIlIllIl[259] = lllIIIIIllIlIlll("7uCbmn1mMWgo2d3Q7snERu43p+ayxSfvJKWuK8EWA2BkagdoMSAx6mJNwgCz/6pCvKXw3uKwrNfIDwkoRFfJr/ikZzsmJDdgsjM+21oNzwKjOpOgvpoigg==", "EyBbA");
        Shaders.lIIllIIIlIlIllIl[260] = lllIIIIIllIlIlll("47L8J4s/XR/1hGL7EDeb6DUIr0tlR0H4", "tSIvy");
        Shaders.lIIllIIIlIlIllIl[261] = lllIIIIIllIlIIII("228yF0u1US4F0CLLHoQhnAD0aBgqyMCWr/CkzwfRAPTrSl26/U0nS+e9C/9MxXo4IK1rgTInckCRX0B/x6Z8pUV/iAvQl6EPJsMSkdPV1DNKC1LEq82equ7ATC61pESgcLtt2BYMVObsMJyXGZbH8A==", "Ukkou");
        Shaders.lIIllIIIlIlIllIl[262] = lllIIIIIllIlIlIl("Jzk7MgAjJT8uXxo0OyQKJyU=", "TQZVo");
        Shaders.lIIllIIIlIlIllIl[263] = lllIIIIIllIlIlll("AeXu8cjNID0N7Pc3ddJDXPatBxQYfqhSZS8VM1voU1NSs832y7P5OJGImzHwTmCEfiWOsWr/bz3J5acEnR401JRxzhBHraiMtPxK7wCEsazdwDGvHdqlAQ==", "xzErF");
        Shaders.lIIllIIIlIlIllIl[264] = lllIIIIIllIlIllI("/WgjErbiwtH8RNv1h1K9aJBk1iYCZVXU0YvoVMmr8cI=", "nFkVV");
        Shaders.lIIllIIIlIlIllIl[265] = lllIIIIIllIlIIII("3NN4m2RIA3nIJLX8nQiyg3Hzqf1NttTOqSRZwTORLszIswG/UGfM8uLBzbh4BfuN18g0GfvDmP6cKPzdWnbh8GE2WMw1HsASEHgAXf+Tu7cqDMXjwicF7faw2Evb4nv3o+GSx8cEgJDCyGZ+eBf62xEvHsFM8Ole", "uLbcL");
        Shaders.lIIllIIIlIlIllIl[266] = lllIIIIIllIlIIII("0q+LhwPAp+c/n+MeGt23FLp8GFG4eBvr", "ifOwj");
        Shaders.lIIllIIIlIlIllIl[267] = lllIIIIIllIlIlll("nqAGx7va0mT0RcenFWnR21Rfdk5Q1+wWvKQo2/dpSD0GVDBLdWqtrKZG3HI4xgZc3Tme1/e/50mvR4GKRNI9IKzXVPXE6Iif/4WPqkoIFZoLen+E1iQ/z59APRfrDFddQvkZ9E8WMHmRPJ/qywdeISkX+m+wVKlK", "IOirg");
        Shaders.lIIllIIIlIlIllIl[268] = lllIIIIIllIlIlIl("GjIRBTUeOR8NNRtrPgQ7Gz8DFQ==", "iZpaZ");
        Shaders.lIIllIIIlIlIllIl[269] = lllIIIIIllIlIlIl("VTpATB4/MiQpGikuJlYSSktTMGcnTUowY1VIQA==", "zfjlI");
        Shaders.lIIllIIIlIlIllIl[270] = lllIIIIIllIlIlll("YwXh+xGyumg=", "fiQsW");
        Shaders.lIIllIIIlIlIllIl[271] = lllIIIIIllIlIlIl("Fh0ZGjcyC00cMy0eAR00JEJN", "AxmtR");
        Shaders.lIIllIIIlIlIllIl[272] = lllIIIIIllIlIllI("75i05Yt/aExO2KgY34D7qopG9jM7cWmFH7EJe1iQvgCFc0PgPe51uWDjPRhIk3Fei1jUdJZnJ4yEZ8WabmVK1UC+TpRXQbw9VtIVG8r/VMg=", "YPlSI");
        Shaders.lIIllIIIlIlIllIl[273] = lllIIIIIllIlIIII("wJ2jFWPUpx1sVpiVlBx/tA==", "wrByI");
        Shaders.lIIllIIIlIlIllIl[274] = lllIIIIIllIlIIII("WAHRv2MJZIz7S+6+Yrodj6rn7iUhd2Oh", "jKUqv");
        Shaders.lIIllIIIlIlIllIl[275] = lllIIIIIllIlIlIl("aRBAUjQUFSQ3IxUEJkgrdmFTLl4bZ0ouWmliQA==", "FLjrp");
        Shaders.lIIllIIIlIlIllIl[276] = lllIIIIIllIlIIII("fJO5oo1x+0w=", "tsrqB");
        Shaders.lIIllIIIlIlIllIl[277] = lllIIIIIllIlIlll("QeSfzYkPdPqf0FFasQn41IHzX2De9fD8", "eOewG");
        Shaders.lIIllIIIlIlIllIl[278] = lllIIIIIllIlIlll("9GoMXG6ixpZs7N+a+o7eqSakGkGpt2qjJEQ5P5njDQdWvj2/8dGrtgOIpD+TNZyJb7A59x1B9YTYlj9leT2aKwLTkVNuZRDd", "otKOC");
        Shaders.lIIllIIIlIlIllIl[279] = lllIIIIIllIlIlIl("UFUKZnAlQi19UA==", "xhQFy");
        Shaders.lIIllIIIlIlIllIl[280] = lllIIIIIllIlIllI("r8p0TUyoBBtr6pFduRZw+DTkvnhoQxpU23VGaN74xG8=", "brHNg");
        Shaders.lIIllIIIlIlIllIl[281] = lllIIIIIllIlIlll("Zw6zRfoXgR+2kp5e/6CpmXllIwlxfedRvpVwuCaZpzFIbAmR01qH463IhwP8VSBKy1qbkV3hURTATDdUTVIHpp7w94D6uFMq", "lvimf");
        Shaders.lIIllIIIlIlIllIl[282] = lllIIIIIllIlIlll("cqukDupMKUOo4NOeOgpKdA==", "NZUfg");
        Shaders.lIIllIIIlIlIllIl[283] = lllIIIIIllIlIllI("l8psF854qH0lO+kwf7jK7wzvLeLoMbF8VM27tIKtmxQ=", "cHBJt");
        Shaders.lIIllIIIlIlIllIl[284] = lllIIIIIllIlIllI("byeKpoEQy0m/4ekk3kW8w/KEM0W/jePtkTit0TFSaJyul6SiwCiD1nNKoGc+8Wkk89NISUCRora9Mg8X7pd6az24PElwjnMRp3C+6DrC6go=", "dICyi");
        Shaders.lIIllIIIlIlIllIl[285] = lllIIIIIllIlIlll("5Xg1w4FsYrBZL47G/t7BPg==", "xosyq");
        Shaders.lIIllIIIlIlIllIl[286] = lllIIIIIllIlIllI("M0m96PR4XVUxD7xmYXNxqdZK3ts/Cty4Y2xb3bJMqdQ=", "olCYZ");
        Shaders.lIIllIIIlIlIllIl[287] = lllIIIIIllIlIlll("/yJWOuNcQBEF1XBrm0fnkY/NUasXs5gxfehBDgDf8L2G1lG7PFYPRJNUcuLihpIqu6ahTK/6e7cwBtSMG+P2O9H1MqQ0WGS5", "xzgGz");
        Shaders.lIIllIIIlIlIllIl[288] = lllIIIIIllIlIIII("4H8XImeVA0b/icPxtP+Jlg==", "lwBrA");
        Shaders.lIIllIIIlIlIllIl[289] = lllIIIIIllIlIlIl("Gz4ERygpPwJHKic/CxMxJyVQRw==", "HKjgX");
        Shaders.lIIllIIIlIlIllIl[290] = lllIIIIIllIlIIII("1WcWA9+qyiFzHV7RE229wCyk1sTCu6+ueiu2uY2eknJmMyqFxf7bqVdKcWPLQBXPwCI/tRUglZloV+RE/G+FDsrxPRcN9c4L", "gRCeh");
        Shaders.lIIllIIIlIlIllIl[291] = lllIIIIIllIlIIII("swJBNe7+gNYoyJwqDhNxUQ==", "AUtlO");
        Shaders.lIIllIIIlIlIllIl[292] = lllIIIIIllIlIlll("5WF8C+hYqFxTYo1sTaCWCQ==", "MaxjM");
        Shaders.lIIllIIIlIlIllIl[293] = lllIIIIIllIlIllI("fkfsYlSDrlYsE6PNBm1ifeNGhq33h4hNTUuqv0+8XwQU2kbIRpW9t6IwewNYObAmgTK1cB+/sqrU4YvPDoRZZCXlmjtiIhcZxsdIW28ex9k=", "vamtc");
        Shaders.lIIllIIIlIlIllIl[294] = lllIIIIIllIlIlIl("ZVwVc3wQSzJoXA==", "MaNSu");
        Shaders.lIIllIIIlIlIllIl[295] = lllIIIIIllIlIllI("0jAHoFHZX/iVNREmpcWkRTv34W7u3cf2NQOY7G6MrUU=", "vyNbS");
        Shaders.lIIllIIIlIlIllIl[296] = lllIIIIIllIlIIII("f98YxPf7sZU=", "JBqUU");
        Shaders.lIIllIIIlIlIllIl[297] = lllIIIIIllIlIIII("49AWcTnK05xLiZ5azAknnu+Y0sKhFD8XNYmZvRpm7V9kXnvMl9g/ZGD0qrCwZs08YlX5xoSYpZYM/INw6gJ9Tk7JX0BIeALH", "JWqBx");
        Shaders.lIIllIIIlIlIllIl[298] = lllIIIIIllIlIlIl("S0ciUGc+UAVLRw==", "czypn");
        Shaders.lIIllIIIlIlIllIl[299] = lllIIIIIllIlIllI("dcRpetIn5WC/2quLOw0zhr8kvUHnqJ7t9w4R/etBaFw=", "QZyZz");
        Shaders.lIIllIIIlIlIllIl[300] = lllIIIIIllIlIllI("wQKkTwqbR8tsInVhISUdZx8noWMG7Xuwcd1hXHUCcZc=", "tizsl");
        Shaders.lIIllIIIlIlIllIl[301] = lllIIIIIllIlIlll("y5MG7KaW80mIwDOpcaj9lrcPzGlLnd61FwnrnbMemOnaFMAjpN9iymrsaf879u0ckePfajMpRZpLCgJoOI0Ucb0x+G5oMqd2tsa3aCrPV80=", "nNWgC");
        Shaders.lIIllIIIlIlIllIl[302] = lllIIIIIllIlIlIl("VCVlDicDOyQcclFzNg==", "qVEhH");
        Shaders.lIIllIIIlIlIllIl[303] = lllIIIIIllIlIllI("4Ml8sy5CIIbddvkKHMb4Jx1ounfea12MZlm5tFtHCrM=", "yucql");
        Shaders.lIIllIIIlIlIllIl[304] = lllIIIIIllIlIllI("o8DnSxi07k4Zn4brg42nlOcs6yA+f46m98GvlvY+I+I=", "OGuTA");
        Shaders.lIIllIIIlIlIllIl[305] = lllIIIIIllIlIIII("9kPo2NT5zQntOexXazHn3Aw2kcFqI4LmHhe4t47bn+Q=", "UPkxa");
        Shaders.lIIllIIIlIlIllIl[306] = lllIIIIIllIlIlIl("FjscLk5RPAYkFxAuSWxaIx0rZUg3", "qZiVz");
        Shaders.lIIllIIIlIlIllIl[307] = lllIIIIIllIlIIII("iO4MB4IO2nYkS4GiJp90eV+9yUHe+lSEWGw3KtkEV0Y=", "dgwQe");
        Shaders.lIIllIIIlIlIllIl[308] = lllIIIIIllIlIIII("Sf+2QkYreate5G3RJGA2uhp6p8OfDJIb", "IIfHn");
        Shaders.lIIllIIIlIlIllIl[309] = lllIIIIIllIlIIII("+Txkfth/Xj0ImQp69ExzAm5kv4Iz/z8BKVUvzG/XfaPmCiYHPqK1WpXg3RsRdUOGu7kYUUxM95WXY3eIiAekRQ==", "JTvPJ");
        Shaders.lIIllIIIlIlIllIl[310] = lllIIIIIllIlIllI("xNm3wYZ118lPDnMnLZFP4wTGNeIgIeSJzdHXMussCd4=", "XFuUJ");
        Shaders.lIIllIIIlIlIllIl[311] = lllIIIIIllIlIllI("vCmHyGCHlWY98o4YIM14Pw==", "zUqfK");
        Shaders.lIIllIIIlIlIllIl[312] = lllIIIIIllIlIlll("KUkWHGR65YQx5o18sCiU7CpC9t10TwvTubxSGIJA7EY=", "hwFUk");
        Shaders.lIIllIIIlIlIllIl[313] = lllIIIIIllIlIlIl("Vz5CbDAqIz8OIT4kLR4nQjlYYUM2P0JsKFJNRmY=", "xbhLt");
        Shaders.lIIllIIIlIlIllIl[314] = lllIIIIIllIlIIII("FP7iCXvrAc4=", "Qkglc");
        Shaders.lIIllIIIlIlIllIl[315] = lllIIIIIllIlIIII("Y/0h73OvjKqWKjtr8Z9mrw==", "OBqGx");
        Shaders.lIIllIIIlIlIllIl[316] = lllIIIIIllIlIlll("6oT1TyqR7L4=", "wJHqJ");
        Shaders.lIIllIIIlIlIllIl[317] = lllIIIIIllIlIllI("G1Kjq2brqT6/9wFsdeIL/RzGcFR9xu1cT/+lUE5WEx1zdezbVLKh+GL1L47oVGjR", "wAtqy");
        Shaders.lIIllIIIlIlIllIl[318] = lllIIIIIllIlIlll("nlgmoQn/BTU=", "mDaBB");
        Shaders.lIIllIIIlIlIllIl[319] = lllIIIIIllIlIlll("A2CZSyQ4sBYoqAaeSXUg+Q==", "qhVmV");
        Shaders.lIIllIIIlIlIllIl[320] = lllIIIIIllIlIlll("k0CqAnf+EqjlOvhALfSzow==", "EqyaQ");
        Shaders.lIIllIIIlIlIllIl[321] = lllIIIIIllIlIllI("7ymjyEfart23eGmFQt+eEQ==", "TdFSF");
        Shaders.lIIllIIIlIlIllIl[322] = lllIIIIIllIlIllI("Xx40C0O6y6rP5stdD+mzOYRBPTzX+/PJFHrRhnUR16I=", "EUnWr");
        Shaders.lIIllIIIlIlIllIl[323] = lllIIIIIllIlIllI("JG29eI5aHlFGwW+QRsqg4w==", "ZhHOp");
        Shaders.lIIllIIIlIlIllIl[324] = lllIIIIIllIlIIII("6Xj52kjIxYVXbbPRC5yoGg==", "CuDVb");
        Shaders.lIIllIIIlIlIllIl[325] = lllIIIIIllIlIlll("VZmYiQOwQQXsiJ1vKvMI+A==", "SFXWl");
        Shaders.lIIllIIIlIlIllIl[326] = lllIIIIIllIlIlIl("agc6CiBqAT0NMS8RO0tqag==", "JcHkW");
        Shaders.lIIllIIIlIlIllIl[327] = lllIIIIIllIlIlIl("ECs2FiwWKw==", "dNNbY");
        Shaders.lIIllIIIlIlIllIl[328] = lllIIIIIllIlIlIl("HDMmMB0dOzE=", "pZAXi");
        Shaders.lIIllIIIlIlIllIl[329] = lllIIIIIllIlIlll("ZmV5kWpsa7c=", "NSMln");
        Shaders.lIIllIIIlIlIllIl[330] = lllIIIIIllIlIlll("8Tymhfxe63C7DMh158jVzg==", "epPin");
        Shaders.lIIllIIIlIlIllIl[331] = lllIIIIIllIlIlIl("JCkuFSEg", "WAOqN");
        Shaders.lIIllIIIlIlIllIl[332] = lllIIIIIllIlIIII("EU0iFp+Mt1XD544Gx2vUJg==", "psbHk");
        Shaders.lIIllIIIlIlIllIl[333] = lllIIIIIllIlIlIl("NjA4DAIyLDwQXQ==", "EXYhm");
        Shaders.lIIllIIIlIlIllIl[334] = lllIIIIIllIlIlll("EVTqU01OTwybz8dOS+fk+w==", "WsXds");
        Shaders.lIIllIIIlIlIllIl[335] = lllIIIIIllIlIllI("FRgdGH1GoYxihCTsaeJm/w==", "SFVEz");
        Shaders.lIIllIIIlIlIllIl[336] = lllIIIIIllIlIIII("Kk0k38FXRW6rJUBLx/GdtA==", "SQpFd");
        Shaders.lIIllIIIlIlIllIl[337] = lllIIIIIllIlIIII("9W9ZT3KcsQAZg14Mj+YL+A==", "zFQYE");
        Shaders.lIIllIIIlIlIllIl[338] = lllIIIIIllIlIlll("KosQTVGJp9bxXX2BTfpPCA==", "WaYAE");
        Shaders.lIIllIIIlIlIllIl[339] = lllIIIIIllIlIllI("bRjnU7cs0Kl6RObuSDhoFw==", "bTxhH");
        Shaders.lIIllIIIlIlIllIl[340] = lllIIIIIllIlIIII("AWeosJT5G6eKOETpKVJ5KQ==", "mNxDK");
        Shaders.lIIllIIIlIlIllIl[341] = lllIIIIIllIlIllI("f0Wi4ya6WIz900eo7GzZBw==", "QzpBb");
        Shaders.lIIllIIIlIlIllIl[342] = lllIIIIIllIlIlll("N3GkUl5yaVs=", "lWsNU");
        Shaders.lIIllIIIlIlIllIl[343] = lllIIIIIllIlIIII("35g6bICK3RM=", "kTcgX");
        Shaders.lIIllIIIlIlIllIl[344] = lllIIIIIllIlIlll("++pcS6wJ8zOfzbcgyg2l8w==", "otYJv");
        Shaders.lIIllIIIlIlIllIl[345] = lllIIIIIllIlIllI("LMgi+lN2qRGe8zfqc0cVQA==", "owBHv");
        Shaders.lIIllIIIlIlIllIl[346] = lllIIIIIllIlIIII("2RKO9Dmmv6k=", "ADcCG");
        Shaders.lIIllIIIlIlIllIl[347] = lllIIIIIllIlIllI("U0WhwkjM9leX/HZGlLxXsw==", "dImyQ");
        Shaders.lIIllIIIlIlIllIl[348] = lllIIIIIllIlIlll("wfjMhKPulWs=", "unSuN");
        Shaders.lIIllIIIlIlIllIl[349] = lllIIIIIllIlIlll("uUnm7LsEPnKDDsUGh6hWtw==", "GaAJy");
        Shaders.lIIllIIIlIlIllIl[350] = lllIIIIIllIlIlll("yI3blfyOAK6YCUVlXJ/iTA==", "JmhRs");
        Shaders.lIIllIIIlIlIllIl[351] = lllIIIIIllIlIllI("BiH7waU1YqEz/ANc1BlRFQ==", "OQfvq");
        Shaders.lIIllIIIlIlIllIl[352] = lllIIIIIllIlIlll("g1OZ6EwSj7lhw4mpafosPw==", "dTQIy");
        Shaders.lIIllIIIlIlIllIl[353] = lllIIIIIllIlIllI("7p8VZUS3lSdQK9R8yru+IA==", "mfREM");
        Shaders.lIIllIIIlIlIllIl[354] = lllIIIIIllIlIllI("Blv+olPGekyx8H9AVMsFMQ==", "KRAyt");
        Shaders.lIIllIIIlIlIllIl[355] = lllIIIIIllIlIIII("1Lr09zquq5qbpt/zzd57eA==", "FOTpt");
        Shaders.lIIllIIIlIlIllIl[356] = lllIIIIIllIlIllI("V1aqbKdfUWqpBBcJu1nkCw==", "znqNQ");
        Shaders.lIIllIIIlIlIllIl[357] = lllIIIIIllIlIllI("SrpOKK1szPyhJh6ZGxeEEQ==", "HdDtB");
        Shaders.lIIllIIIlIlIllIl[358] = lllIIIIIllIlIllI("MpXFvX/puHYZ78SCJjKlQg==", "urBHh");
        Shaders.lIIllIIIlIlIllIl[359] = lllIIIIIllIlIlIl("Fi83Ay0SMzMfcg==", "eGVgB");
        Shaders.lIIllIIIlIlIllIl[360] = lllIIIIIllIlIlIl("NwkKFzYzFQ4LaA==", "DaksY");
        Shaders.lIIllIIIlIlIllIl[361] = lllIIIIIllIlIlIl("MiYHIRs9Ngcp", "UBbQo");
        Shaders.lIIllIIIlIlIllIl[362] = lllIIIIIllIlIlll("eDzAY/zjsWeytPluAgCaMQ==", "ZGjfc");
        Shaders.lIIllIIIlIlIllIl[363] = lllIIIIIllIlIllI("iVGnmsf5S2ANQ/aL+wM01Q==", "BsofP");
        Shaders.lIIllIIIlIlIllIl[364] = lllIIIIIllIlIlIl("MzUzFTEjNTtT", "WPCaY");
        Shaders.lIIllIIIlIlIllIl[365] = lllIIIIIllIlIlll("cFiqHw43TQcMF8Sg0QmmDA==", "SoVPm");
        Shaders.lIIllIIIlIlIllIl[366] = lllIIIIIllIlIlIl("IRkILi0lEgYmLSBB", "RqiJB");
        Shaders.lIIllIIIlIlIllIl[367] = lllIIIIIllIlIIII("xX/13H88nm/3kS/9cmO8qg==", "ECuiG");
        Shaders.lIIllIIIlIlIllIl[368] = lllIIIIIllIlIIII("ZDK9OlaW01GexjzLPeS0Qg==", "ejLsf");
        Shaders.lIIllIIIlIlIllIl[369] = lllIIIIIllIlIllI("/pK2fq3fEVBGn+DSjJNQYQ==", "hfFzs");
        Shaders.lIIllIIIlIlIllIl[370] = lllIIIIIllIlIlIl("DBMsBicKEw==", "xvTrR");
        Shaders.lIIllIIIlIlIllIl[371] = lllIIIIIllIlIIII("aB5TaWtEmaz6XAGT3gNVCg==", "amobW");
        Shaders.lIIllIIIlIlIllIl[372] = lllIIIIIllIlIlIl("Hi4kPSMcMg==", "pAVPB");
        Shaders.lIIllIIIlIlIllIl[373] = lllIIIIIllIlIIII("nUVqNyDKtc6Vb2SHnJ6CQQ==", "siQhf");
        Shaders.lIIllIIIlIlIllIl[374] = lllIIIIIllIlIlll("oUhSic7YrME=", "mbXVF");
        Shaders.lIIllIIIlIlIllIl[375] = lllIIIIIllIlIlIl("PAg1ExY4ASASCzw=", "KiAvd");
        Shaders.lIIllIIIlIlIllIl[376] = lllIIIIIllIlIllI("f58yzHNux0cQ5acLvmJKEA==", "PATfj");
        Shaders.lIIllIIIlIlIllIl[377] = lllIIIIIllIlIlIl("Gh8OBwMeAwobXQ==", "iwocl");
        Shaders.lIIllIIIlIlIllIl[378] = lllIIIIIllIlIlll("xkv/uQkRMW2Ff9Fzc38Xnw==", "CksBg");
        Shaders.lIIllIIIlIlIllIl[379] = lllIIIIIllIlIlIl("PxkGFRk7EggdGT5B", "Lqgqv");
        Shaders.lIIllIIIlIlIllIl[380] = lllIIIIIllIlIlll("lWP7g4gPcAysOp6Nrggv3g==", "pFHhr");
        Shaders.lIIllIIIlIlIllIl[381] = lllIIIIIllIlIlIl("Lxs7NAE1ESo=", "AtRGd");
        Shaders.lIIllIIIlIlIllIl[382] = lllIIIIIllIlIIII("m0M9dd542hCYM0B4P14+dA==", "ClCxj");
        Shaders.lIIllIIIlIlIllIl[383] = lllIIIIIllIlIlll("04qZ+98sOVbYvX6FugC3XotEjaC95Z8m", "hkfAN");
        Shaders.lIIllIIIlIlIllIl[384] = lllIIIIIllIlIlIl("MRkgJC4zEw==", "WvGiA");
        Shaders.lIIllIIIlIlIllIl[385] = lllIIIIIllIlIllI("OmN3WiRM1/RTci8uXTOSyA==", "EVtzV");
        Shaders.lIIllIIIlIlIllIl[386] = lllIIIIIllIlIllI("9Qo9MiPMpE2yupQvC7pw2w==", "YZgBr");
        Shaders.lIIllIIIlIlIllIl[387] = lllIIIIIllIlIIII("0KUyiXVOHWou53JTB0UGBQ==", "pHbEk");
        Shaders.lIIllIIIlIlIllIl[388] = lllIIIIIllIlIlll("7kWJYpIKpou2qaF/hruY1Q==", "BqtiI");
        Shaders.lIIllIIIlIlIllIl[389] = lllIIIIIllIlIlll("x+JHV3gjpI8RDo6SGsbMyg2GH17Dilt9", "aeZSx");
        Shaders.lIIllIIIlIlIllIl[390] = lllIIIIIllIlIlIl("Ix8ZAwA3BhI=", "PjwBn");
        Shaders.lIIllIIIlIlIllIl[391] = lllIIIIIllIlIlll("ynAFWk/7wnziBWykCmOTMA==", "pYHpa");
        Shaders.lIIllIIIlIlIllIl[392] = lllIIIIIllIlIIII("YZbuPaw78du2v+gJndxaPQ==", "wRpTr");
        Shaders.lIIllIIIlIlIllIl[393] = lllIIIIIllIlIlIl("NBkACxAhOBEaGjo=", "Ujpns");
        Shaders.lIIllIIIlIlIllIl[394] = lllIIIIIllIlIlll("g4BMDO3YsTsJtgxERWCSOQ==", "jFyMZ");
        Shaders.lIIllIIIlIlIllIl[395] = lllIIIIIllIlIIII("IN3YNPhP+5ZwwfGBrqxLjg==", "ptGFM");
        Shaders.lIIllIIIlIlIllIl[396] = lllIIIIIllIlIllI("RbbiYG3ZaEVOr8ArC4HQrA==", "OZxDv");
        Shaders.lIIllIIIlIlIllIl[397] = lllIIIIIllIlIlll("GVyftWpmQpw=", "gWjXd");
        Shaders.lIIllIIIlIlIllIl[398] = lllIIIIIllIlIlll("xVt0UVTKfVbtfnLhwujBEw==", "iYbLG");
        Shaders.lIIllIIIlIlIllIl[399] = lllIIIIIllIlIlIl("DC0YFB8OMR4OJg4s", "aBwzO");
        Shaders.lIIllIIIlIlIllIl[400] = lllIIIIIllIlIllI("OkblXcRkK0El7l0+vd71TYfT+2fk+4h5Pk9ev9M29zE=", "CmQym");
        Shaders.lIIllIIIlIlIllIl[401] = lllIIIIIllIlIIII("OaQvAIELtcjHWzdN784D0A==", "XOvjz");
        Shaders.lIIllIIIlIlIllIl[402] = lllIIIIIllIlIlIl("CjQEIy4VMxIWJhcjEzQXFTUIIS4VKA==", "zFaUG");
        Shaders.lIIllIIIlIlIllIl[403] = lllIIIIIllIlIllI("QttAOX1fTnlO8xzI75Cscg==", "PuPsT");
        Shaders.lIIllIIIlIlIllIl[404] = lllIIIIIllIlIllI("e8ZaanPc11kB57asaVZMTmN+9yeCSWBTFfmjEGLOwhI=", "SEkLA");
        Shaders.lIIllIIIlIlIllIl[405] = lllIIIIIllIlIIII("kpB7oRrdw27aRcNRR3+Zh+EVEP7kWN0U", "MNsyt");
        Shaders.lIIllIIIlIlIllIl[406] = lllIIIIIllIlIlIl("AAAlJAwCEAAwDxELPzcZNxA/KA8EFjktBA==", "gbPBj");
        Shaders.lIIllIIIlIlIllIl[407] = lllIIIIIllIlIlll("7U/bM8efAWAmvbkya+Mb3OnTkAh/1OAE", "qkPhp");
        Shaders.lIIllIIIlIlIllIl[408] = lllIIIIIllIlIIII("Ns/LVvGgjf7+2Q82JD2W9xWG80eziSYa6ThM4k2jZR0=", "ycdNp");
        Shaders.lIIllIIIlIlIllIl[409] = lllIIIIIllIlIIII("sk/NbYqPRy/wj4x4kR6xTE8qriaE2iM6OXZA+iDo96o=", "XuKDX");
        Shaders.lIIllIIIlIlIllIl[410] = lllIIIIIllIlIlll("6T0o/stOt292O4Zfv6N+JV0PpR4qWsBn", "BWCnV");
        Shaders.lIIllIIIlIlIllIl[411] = lllIIIIIllIlIllI("rlsjluIP1+Zji7jpSIf3teG5/Ixl2YfhPITl05F4syQ=", "dtwkf");
        Shaders.lIIllIIIlIlIllIl[412] = lllIIIIIllIlIlll("MIB22R6l06/zBS1SIQErbA==", "kMrVk");
        Shaders.lIIllIIIlIlIllIl[413] = lllIIIIIllIlIlIl("Oic5FQU+AjcVDyUZMRQdACEuFBg6Kg==", "IOXqj");
        Shaders.lIIllIIIlIlIllIl[414] = lllIIIIIllIlIlIl("Mz0jCgs3Kw==", "DXWdn");
        Shaders.lIIllIIIlIlIllIl[415] = lllIIIIIllIlIllI("2SHWrC7Xjuk9O39o1N8dSA==", "tvsAV");
        Shaders.lIIllIIIlIlIllIl[416] = lllIIIIIllIlIlIl("FRIKFCEZDAciPRUYHA==", "pkoVS");
        Shaders.lIIllIIIlIlIllIl[417] = lllIIIIIllIlIlll("x4dfooJVU4mIIqEnEZWmjQjjIrrQyZbT", "ywSkc");
        Shaders.lIIllIIIlIlIllIl[418] = lllIIIIIllIlIlll("J4huSeWBa0mepGMMvmDP8g3GFtvLXOVs", "ExxcF");
        Shaders.lIIllIIIlIlIllIl[419] = lllIIIIIllIlIlll("eHHr35pKE+ysNMMDy1byJA==", "qEwjA");
        Shaders.lIIllIIIlIlIllIl[420] = lllIIIIIllIlIlIl("Cj0RAw0qIAMbHAY8", "cNTzh");
        Shaders.lIIllIIIlIlIllIl[421] = lllIIIIIllIlIIII("v0qlAqsdNUs=", "TNXDd");
        Shaders.lIIllIIIlIlIllIl[422] = lllIIIIIllIlIIII("RLRT2AkNpSwN14kfvJ3EjGmx8H0sWlrL", "xxwHt");
        Shaders.lIIllIIIlIlIllIl[423] = lllIIIIIllIlIlll("zJlvkyLRumuoaYxUIQcMlw==", "EyBHZ");
        Shaders.lIIllIIIlIlIllIl[424] = lllIIIIIllIlIlIl("IDonKTA6LjAYL3U=", "UIByB");
        Shaders.lIIllIIIlIlIllIl[425] = lllIIIIIllIlIllI("P2DaGJngSYeH+pfZTBOPXw==", "TSqhB");
        Shaders.lIIllIIIlIlIllIl[426] = lllIIIIIllIlIlIl("FhINCDUD", "qqbdZ");
        Shaders.lIIllIIIlIlIllIl[427] = lllIIIIIllIlIllI("mP15JR1vTTT81WZ91mMC1Q==", "ooAKF");
        Shaders.lIIllIIIlIlIllIl[428] = lllIIIIIllIlIllI("yeP5XaL994caH6xw+/GJ5w==", "NINoR");
        Shaders.lIIllIIIlIlIllIl[429] = lllIIIIIllIlIlIl("ERgfBBsGEgtZ", "rwski");
        Shaders.lIIllIIIlIlIllIl[430] = lllIIIIIllIlIIII("AMnPibfyj+A=", "pYRME");
        Shaders.lIIllIIIlIlIllIl[431] = lllIIIIIllIlIIII("rarE0kdKHv62RJdHz/f7dQ==", "zlkTp");
        Shaders.lIIllIIIlIlIllIl[432] = lllIIIIIllIlIlIl("IiUfFSUyIwYA", "AJreJ");
        Shaders.lIIllIIIlIlIllIl[433] = lllIIIIIllIlIlll("UkCudArsbmvnEJ+Eyu8YcA==", "GwsgN");
        Shaders.lIIllIIIlIlIllIl[434] = lllIIIIIllIlIlll("KDLZqu7IsEI=", "cPRTZ");
        Shaders.lIIllIIIlIlIllIl[435] = lllIIIIIllIlIllI("4I4Ty7xl0R/DAqrIJUXHGw==", "wYvCY");
        Shaders.lIIllIIIlIlIllIl[436] = lllIIIIIllIlIlll("uCeB+kosghc=", "SsfEj");
        Shaders.lIIllIIIlIlIllIl[437] = lllIIIIIllIlIlll("+HtrZN0aLbKZnz1aY1EZDw==", "CRVKJ");
        Shaders.lIIllIIIlIlIllIl[438] = lllIIIIIllIlIIII("b80u+BWAR5M=", "nXeUs");
        Shaders.lIIllIIIlIlIllIl[439] = lllIIIIIllIlIlll("atGDDCwt/acmHqfkmQQLzQ==", "WFRuT");
        Shaders.lIIllIIIlIlIllIl[440] = lllIIIIIllIlIIII("DO6gpiBAu2o=", "qGNuw");
        Shaders.lIIllIIIlIlIllIl[441] = lllIIIIIllIlIlll("HjTbqNP0M1ddUHTlwqJgblBsCwd1iyob", "VJhBF");
        Shaders.lIIllIIIlIlIllIl[442] = lllIIIIIllIlIIII("XoaBS0qydkZLqv4NO+9wC3BKTi945x7c", "kRJQc");
        Shaders.lIIllIIIlIlIllIl[443] = lllIIIIIllIlIlll("WEHTVeskO+IeWGnTKKmmAg==", "MViaq");
        Shaders.lIIllIIIlIlIllIl[444] = lllIIIIIllIlIllI("fc5k3QAXSRJTFkhlTlYNSwRM7la/KTMwJztGyNZaFtE=", "Lvgpx");
        Shaders.lIIllIIIlIlIllIl[445] = lllIIIIIllIlIllI("XlXuTlN7sfLFVlSUWpgREs5Pb2YPyBPaMmyhY+U3QA8=", "OFJhW");
        Shaders.lIIllIIIlIlIllIl[446] = lllIIIIIllIlIllI("0gaerc7U3/MplUgl1UmPOg==", "LWwxi");
        Shaders.lIIllIIIlIlIllIl[447] = lllIIIIIllIlIlll("2/5RKnhpYk4=", "BMhkC");
        Shaders.lIIllIIIlIlIllIl[448] = lllIIIIIllIlIIII("Lv43BztR4zg=", "GaZrG");
        Shaders.lIIllIIIlIlIllIl[449] = lllIIIIIllIlIIII("+8TgKxJe3SZojpxe81Xjbinvpdhgqfcg", "fxqTd");
        Shaders.lIIllIIIlIlIllIl[450] = lllIIIIIllIlIlll("QTOLaxM52wolITGrlscXjw==", "AQLmz");
        Shaders.lIIllIIIlIlIllIl[451] = lllIIIIIllIlIllI("7Fm0hhezVOz48H2NxiyKIwyIm27wqy7FCGo8dyfd9bE=", "GtkOO");
        Shaders.lIIllIIIlIlIllIl[452] = lllIIIIIllIlIlIl("DxUraTIDESMmNksTKCUuGQ==", "kpGIA");
        Shaders.lIIllIIIlIlIllIl[453] = lllIIIIIllIlIlIl("IB0OODwB", "usgVU");
        Shaders.lIIllIIIlIlIllIl[454] = lllIIIIIllIlIlll("c/bD9ouw9zrMAdQSQlG5Fw==", "PjDvv");
        Shaders.lIIllIIIlIlIllIl[455] = lllIIIIIllIlIIII("DV7jpIB6osU=", "ZmLbY");
        Shaders.lIIllIIIlIlIllIl[456] = lllIIIIIllIlIllI("9I34hEWaoDO8AU69SzvELQ==", "JpuIq");
        Shaders.lIIllIIIlIlIllIl[457] = lllIIIIIllIlIlll("VMGmpXg0w/8=", "WWNzg");
        Shaders.lIIllIIIlIlIllIl[458] = lllIIIIIllIlIllI("38TxTC9iTLk9Y5LqKwZEaLZOa6WGiSE37gJvNj8tkXNNyeuM/m8yLFkjKx536KpDHQoww4OTlfqr/Hg/e72ejQ==", "mWYtb");
        Shaders.lIIllIIIlIlIllIl[459] = lllIIIIIllIlIllI("3VoFGp9/xb5y6MhHKExnpA==", "weiKi");
        Shaders.lIIllIIIlIlIllIl[460] = lllIIIIIllIlIIII("rI8NR+GYpovzhI8h+ghuig==", "nOswD");
        Shaders.lIIllIIIlIlIllIl[461] = lllIIIIIllIlIlIl("NRQwFzcLNSsrcys1KhkhVGceFzoCIjxWMBwiOQI6ACB4ECEPKj0UJgghPQRyTm8LAjIaMitW", "nGXvS");
        Shaders.lIIllIIIlIlIllIl[462] = lllIIIIIllIlIIII("ZipgRPzvQiU=", "QcouW");
        Shaders.lIIllIIIlIlIllIl[463] = lllIIIIIllIlIlIl("IzcHIQkHMAAqCRdlBT4JBDEDKEI=", "eEfLl");
        Shaders.lIIllIIIlIlIllIl[464] = lllIIIIIllIlIllI("iJYnrN3G/Spl+tNNdyg7mQ==", "OVKpQ");
        Shaders.lIIllIIIlIlIllIl[465] = lllIIIIIllIlIllI("szJb3OFoi0nWZ7jKh/NheA==", "wqoaY");
        Shaders.lIIllIIIlIlIllIl[466] = lllIIIIIllIlIlll("WhRfJtp6N1xCRtAsznje/WQSSPzPVtFgdRS7QNqKWW6MaVzdp8iCqtANgM16RuPdgN5ZF8o2FusH+zsyNfHSDA==", "FExCN");
        Shaders.lIIllIIIlIlIllIl[467] = lllIIIIIllIlIllI("vraCmkBGMYulvMx/GqvBXQ==", "tiWQf");
        Shaders.lIIllIIIlIlIllIl[468] = lllIIIIIllIlIIII("Crh9dTYzS91aOi7sGrlbyOe/4ey0H3CPJXgG5LhwXpU=", "CoGVj");
        Shaders.lIIllIIIlIlIllIl[469] = lllIIIIIllIlIIII("jugWSc9hO5KlWYmIFolQeQ==", "pwqXe");
        Shaders.lIIllIIIlIlIllIl[470] = lllIIIIIllIlIllI("sXg27nz7N8i+IWoWejd/rw==", "OfQzb");
        Shaders.lIIllIIIlIlIllIl[471] = lllIIIIIllIlIIII("+O3D8ZqiQ6m3n+bZOQpK6ZjmChTob52lb1VFyu9W+B0=", "dQNlp");
        Shaders.lIIllIIIlIlIllIl[472] = lllIIIIIllIlIllI("cGrI9BCd4oos+Fc0wo4/psGSbk2h4lslut1SSEOQxWA=", "ZRaBY");
        Shaders.lIIllIIIlIlIllIl[473] = lllIIIIIllIlIlIl("FCodLSMkKhQgKAQ=", "vOzDM");
        Shaders.lIIllIIIlIlIllIl[474] = lllIIIIIllIlIlll("JzvG9cNpD/quUEf/a7LsZQ==", "HfXXF");
        Shaders.lIIllIIIlIlIllIl[475] = lllIIIIIllIlIllI("12HyxHCGaWk+hZbXDWzcBdGvkga3QoZLFkrUvpAt5NM=", "UWgQg");
        Shaders.lIIllIIIlIlIllIl[476] = lllIIIIIllIlIIII("l1I/1V2U/RCC/pLJJ2ZRhw==", "SRFzO");
        Shaders.lIIllIIIlIlIllIl[477] = lllIIIIIllIlIlll("irSIa70wXh0FzvKyR7E2DWI5H0HGH3D7", "LpCTh");
        Shaders.lIIllIIIlIlIllIl[478] = lllIIIIIllIlIlll("nFIKoJ2Ry6zHnDhc5r+iEg==", "tSvYb");
        Shaders.lIIllIIIlIlIllIl[479] = lllIIIIIllIlIlll("5fprX2Lo0OeTQZ4cXtBIQQ==", "nmvCu");
        Shaders.lIIllIIIlIlIllIl[480] = lllIIIIIllIlIlll("J5JE+KiiR0K8ATqgwU6rtg==", "JyBHE");
        Shaders.lIIllIIIlIlIllIl[481] = lllIIIIIllIlIlIl("DQItDRU=", "nnHlg");
        Shaders.lIIllIIIlIlIllIl[482] = lllIIIIIllIlIIII("6PJiUP9UXUU=", "fXbUv");
        Shaders.lIIllIIIlIlIllIl[483] = lllIIIIIllIlIlIl("GjIXOwgEMhEZ", "iWcxi");
        Shaders.lIIllIIIlIlIllIl[484] = lllIIIIIllIlIllI("pB3Jt4mzsxRnnx7ud1T5xgzD2A3nDSXcmFjs/7Qry/s=", "DGWbU");
        Shaders.lIIllIIIlIlIllIl[485] = lllIIIIIllIlIIII("T1jN5J0eD8DuabwqpKIuRAaSNU/5XJRJo3mQS4WasXA=", "ycAji");
        Shaders.lIIllIIIlIlIllIl[486] = lllIIIIIllIlIlIl("FQwkESAXHAEFIwQHPgI1Ihw+HSMRGjgYKA==", "rnQwF");
        Shaders.lIIllIIIlIlIllIl[487] = lllIIIIIllIlIlll("3zTnFxeEaWR2fX3ouszzfd2MUNNpFfkG", "zJaGU");
        Shaders.lIIllIIIlIlIllIl[488] = lllIIIIIllIlIlll("Wqi6JFfl+x44PKJbnAzurL5r5tPzTYig", "VtsIN");
        Shaders.lIIllIIIlIlIllIl[489] = lllIIIIIllIlIlIl("ERgzIi8TCBY2LAATKTE6OxUiISUgEyMz", "vzFDI");
        Shaders.lIIllIIIlIlIllIl[490] = lllIIIIIllIlIlll("7LasvGiz2j4B+z79qCc2wA6jBHpiOWXJ", "RAaUB");
        Shaders.lIIllIIIlIlIllIl[491] = lllIIIIIllIlIIII("9QhWTeoGFFn9GyPRRzk2CRohHdbOa7zu", "JQkWR");
        Shaders.lIIllIIIlIlIllIl[492] = lllIIIIIllIlIllI("YUhcGdpPnYMwy5JmuP+x+g==", "zuYVB");
        Shaders.lIIllIIIlIlIllIl[493] = lllIIIIIllIlIllI("XwKlj6wXOZQsoEPJVi/GyPGRBd+jJHRgUe819P0W5dw=", "aGwnz");
        Shaders.lIIllIIIlIlIllIl[494] = lllIIIIIllIlIlll("H9Cp7ogx6dJpkLDj5nbnQw==", "XxxUG");
        Shaders.lIIllIIIlIlIllIl[495] = lllIIIIIllIlIlIl("EQIVNgENFQMBDQAcIhoQAAQV", "appud");
        Shaders.lIIllIIIlIlIllIl[496] = lllIIIIIllIlIllI("l1G2Wwugefse0Skr+wufs7GBFAJUQUgtiWHp0g9EM/Q=", "DKoJo");
        Shaders.lIIllIIIlIlIllIl[497] = lllIIIIIllIlIllI("DB4ENSTeLuTGjArsNzHdaE6ZPwY0X53HBI2UGkD8Scs=", "uhxqU");
        Shaders.lIIllIIIlIlIllIl[498] = lllIIIIIllIlIIII("sE1KAzDoN8cIrwmFJ+imtw==", "vlPXC");
        Shaders.lIIllIIIlIlIllIl[499] = lllIIIIIllIlIlIl("MDs3IS0gPS40", "STZQB");
        Shaders.lIIllIIIlIlIllIl[500] = lllIIIIIllIlIllI("AIQyqm14VugU2K9PbtM5dw==", "lfOwv");
        Shaders.lIIllIIIlIlIllIl[501] = lllIIIIIllIlIlll("zh7qy2J1fZA=", "bPZlT");
        Shaders.lIIllIIIlIlIllIl[502] = lllIIIIIllIlIIII("kiqEwFOzYGjKf9z6QEh5UgTCMZFGF7nD", "cMceW");
        Shaders.lIIllIIIlIlIllIl[503] = lllIIIIIllIlIllI("SBPF79OdozeveM+Ek4XAmHX/p9PljA1Z/yLPPUmnu7Y=", "wbBvJ");
        Shaders.lIIllIIIlIlIllIl[504] = lllIIIIIllIlIlIl("KScXHBEiLRY8VCknFw==", "LIsNt");
        Shaders.lIIllIIIlIlIllIl[505] = lllIIIIIllIlIllI("SnkpbT1EIPkdrh2bYIVReA==", "PtIRP");
        Shaders.lIIllIIIlIlIllIl[506] = lllIIIIIllIlIlIl("JikzECERPDAYOyEPPAwhLz9l", "DLTyO");
        Shaders.lIIllIIIlIlIllIl[507] = lllIIIIIllIlIIII("ijq8BFEmBToFNRq4YvWrfjf0fDisdrg/", "KdoOG");
        Shaders.lIIllIIIlIlIllIl[508] = lllIIIIIllIlIlll("EgbiK3625NpqS9+rkEa51m3p6yHDmN8E", "bBIvb");
        Shaders.lIIllIIIlIlIllIl[509] = lllIIIIIllIlIllI("r/M3CguBkidzed1MlshjyrQB0PXKDoeBtCMZr2dXaeA=", "crxtt");
        Shaders.lIIllIIIlIlIllIl[510] = lllIIIIIllIlIlIl("CCk8MzYJJiwDBQUyNg01XA==", "mGXfF");
        Shaders.lIIllIIIlIlIllIl[511] = lllIIIIIllIlIlIl("LAgoETYtBzghBSETIi81eA==", "IfLDF");
        Shaders.lIIllIIIlIlIllIl[512] = lllIIIIIllIlIlll("jjUrXtGlHK2lSc5DMVUQbNU8wqiVtzzl", "iKfxK");
        Shaders.lIIllIIIlIlIllIl[513] = lllIIIIIllIlIlll("e6i/SiGi2FizjQ6VYhwypMlhnrFhYgEc", "EAjsa");
        Shaders.lIIllIIIlIlIllIl[514] = lllIIIIIllIlIIII("Kq4u+vwpi50cPi5QLZtOCC0X4If75yfS", "LxGKr");
        Shaders.lIIllIIIlIlIllIl[515] = lllIIIIIllIlIlIl("Dg0yMAMuBDo6BikGITAZBQ0m", "lhUYm");
        Shaders.lIIllIIIlIlIllIl[516] = lllIIIIIllIlIlIl("NBMsJjc2GTc5MDQCPSYM", "ZvTRu");
        Shaders.lIIllIIIlIlIllIl[517] = lllIIIIIllIlIllI("DDL8IAzFWXDO702HxlxurDLB42lk95oKU6DC0PzDtDQ=", "UNcjn");
        Shaders.lIIllIIIlIlIllIl[518] = lllIIIIIllIlIllI("Lf9ZavaieWlIONvNnH4pZQ==", "joomL");
        Shaders.lIIllIIIlIlIllIl[519] = lllIIIIIllIlIlll("53jSsDwlSsn/7M+QPgddmA==", "CTEGl");
        Shaders.lIIllIIIlIlIllIl[520] = lllIIIIIllIlIIII("a06uFmj1tFKfmxrSFTpocw==", "HoOQZ");
        Shaders.lIIllIIIlIlIllIl[521] = lllIIIIIllIlIllI("p/EaGS4dtR0i+mYUmOgqYw==", "pejLc");
        Shaders.lIIllIIIlIlIllIl[522] = lllIIIIIllIlIlll("26808g96YkbHrKJQEBebPA==", "ouiQK");
        Shaders.lIIllIIIlIlIllIl[523] = lllIIIIIllIlIlll("HzUjVQIUGCQUMHZqaJGoig==", "DGEKr");
        Shaders.lIIllIIIlIlIllIl[524] = lllIIIIIllIlIIII("SSXkKPDQ6r4=", "RFPMz");
        Shaders.lIIllIIIlIlIllIl[525] = lllIIIIIllIlIIII("5MmD2oaCyFI=", "WYgMN");
        Shaders.lIIllIIIlIlIllIl[526] = lllIIIIIllIlIllI("QsUqjRn2uxtULSm72WKykQ==", "kefcy");
        Shaders.lIIllIIIlIlIllIl[527] = lllIIIIIllIlIIII("kgB0PNP5PFc=", "uKBcb");
        Shaders.lIIllIIIlIlIllIl[528] = lllIIIIIllIlIlll("pC4KBY8hneE=", "mcIZS");
        Shaders.lIIllIIIlIlIllIl[529] = lllIIIIIllIlIlll("4Qpce7G606Xfr9CMPGDu8vUfu2eiNGvoPBlc2PIIMSFcWSveGF/RisywPa8By6mM", "PMGYl");
        Shaders.lIIllIIIlIlIllIl[530] = lllIIIIIllIlIlIl("JSMWPQsEOFcUARJrEzwaEygDPApYayc1Cxc4EnkcEyYYLwtWIgN1Tjk7AzAoHyUSeQYXOFc7Gx8nA3QHGGsELB4GJAUtThAkBXkdHioTPBwFZQ==", "vKwYn");
        Shaders.lIIllIIIlIlIllIl[531] = lllIIIIIllIlIIII("EoljDC1pCtiXlrwekCoBLQ==", "Gqwep");
        Shaders.lIIllIIIlIlIllIl[532] = lllIIIIIllIlIlIl("ASg9GD8=", "dFbMl");
        Shaders.lIIllIIIlIlIllIl[533] = lllIIIIIllIlIllI("+1PYY9Q8RPV9ht61XzmIdA==", "wvyLo");
        Shaders.lIIllIIIlIlIllIl[534] = lllIIIIIllIlIIII("zFGgnp3gJrk=", "fdoZh");
        Shaders.lIIllIIIlllIllll = null;
    }
    
    private static void lllIIIIlIIlllllI() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        Shaders.lIIllIIIlllIllll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lllIIIIIllIlIIII(final String s, final String s2) {
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
    
    private static String lllIIIIIllIlIlll(final String s, final String s2) {
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
    
    private static String lllIIIIIllIlIllI(final String s, final String s2) {
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
    
    private static String lllIIIIIllIlIlIl(String s, final String s2) {
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
    
    static class NamelessClass341846571
    {
        static final int[] $SwitchMap$shadersmod$client$EnumShaderOption;
        
        static {
            $SwitchMap$shadersmod$client$EnumShaderOption = new int[EnumShaderOption.values().length];
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.ANTIALIASING.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.NORMAL_MAP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.SPECULAR_MAP.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.RENDER_RES_MUL.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.SHADOW_RES_MUL.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.HAND_DEPTH_MUL.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.CLOUD_SHADOW.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.OLD_LIGHTING.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.SHADER_PACK.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.TWEAK_BLOCK_DAMAGE.ordinal()] = 10;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.SHADOW_CLIP_FRUSTRUM.ordinal()] = 11;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MIN_FIL_B.ordinal()] = 12;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MIN_FIL_N.ordinal()] = 13;
            }
            catch (NoSuchFieldError noSuchFieldError13) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MIN_FIL_S.ordinal()] = 14;
            }
            catch (NoSuchFieldError noSuchFieldError14) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MAG_FIL_B.ordinal()] = 15;
            }
            catch (NoSuchFieldError noSuchFieldError15) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MAG_FIL_N.ordinal()] = 16;
            }
            catch (NoSuchFieldError noSuchFieldError16) {}
            try {
                NamelessClass341846571.$SwitchMap$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MAG_FIL_S.ordinal()] = 17;
            }
            catch (NoSuchFieldError noSuchFieldError17) {}
        }
    }
}
