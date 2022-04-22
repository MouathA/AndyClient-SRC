package Mood;

import DTool.modules.*;
import java.util.concurrent.*;
import DTool.ui.*;
import net.minecraft.client.*;
import Mood.discord.*;
import DTool.command.*;
import DTool.util.*;
import Mood.hologram.*;
import Mood.Gui.clickgui.setting.*;
import Mood.Helpers.*;
import org.apache.logging.log4j.*;
import org.lwjgl.opengl.*;
import lumien.chunkanimator.*;
import Mood.creativetab.*;
import DTool.modules.world.*;
import DTool.modules.Exploits.*;
import DTool.modules.combat.*;
import DTool.modules.movement.*;
import DTool.modules.visuals.*;
import DTool.modules.player.*;
import org.apache.commons.io.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.event.*;
import DTool.events.*;
import DTool.events.listeners.*;
import java.util.*;

public class Client
{
    public static String name;
    public static String version;
    public static String ClientBy;
    public static String exploitfixer;
    static InstanceRender r;
    public transient Module playerESP;
    public static CopyOnWriteArrayList modules;
    public static HUD hud;
    public static Minecraft mc;
    private File directory;
    private static Logger logger;
    private ConfigRegistry configRegistry;
    private static DiscordRP discordRP;
    public static CommandManager commandManager;
    public static Client INSTANCE;
    public static EnumColor borderColor;
    public static EnumColor panlColor;
    public static String shaderSource;
    public static boolean indev;
    public static final Client theClient;
    private HologramManager hologramManager;
    private SettingsManager settingsmanager;
    private ClientSettings clientSettings;
    public boolean updateShaderState;
    
    static {
        Client.name = "Andy";
        Client.version = "1.8";
        Client.ClientBy = "iTzMatthew1337";
        Client.exploitfixer = "ExploitFixer";
        Client.modules = new CopyOnWriteArrayList();
        Client.hud = new HUD();
        Client.mc = Minecraft.getMinecraft();
        Client.logger = LogManager.getLogger("Andy");
        Client.discordRP = new DiscordRP();
        Client.commandManager = new CommandManager();
        Client.INSTANCE = new Client();
        Client.shaderSource = "";
        theClient = new Client();
    }
    
    public Client() {
        this.directory = new File(Minecraft.mcDataDir, "Andy");
        this.hologramManager = new HologramManager();
        this.settingsmanager = new SettingsManager();
    }
    
    public static void startup() {
        System.out.println("[MooDTool Info] Starting " + Client.name + " " + Client.version);
        Display.setTitle(Client.name + " Feat. DTool V.1");
        Client.discordRP.start();
        new BooksTab();
        new EmotionTab();
        new ChunkAnimator();
        new MoodTabArmor();
        new MoodTabExploits();
        new MoodTabHeads();
        new MoodTabPotion();
        new MoodTabSpawners();
        new MoodTabSwords();
        new MoodTabZaszlok();
        Client.modules.add(new NameTags());
        Client.modules.add(new IamFuckingSanic());
        Client.modules.add(new CustomModel());
        Client.modules.add(new AddToChestButtons());
        Client.modules.add(new SimsESP());
        Client.modules.add(new ImageESP());
        Client.modules.add(new WaterSpeed());
        Client.modules.add(new FastLadder());
        Client.modules.add(new Highjump());
        Client.modules.add(new NoTroll());
        Client.modules.add(new TrueSight());
        Client.modules.add(new EveryItemOnArmor());
        Client.modules.add(new NoSlowDown());
        Client.modules.add(new NoPumpkin());
        Client.modules.add(new Waypoints());
        Client.modules.add(new Trajectories());
        Client.modules.add(new Cl3sTerHud());
        Client.modules.add(new ItemESP());
        Client.modules.add(new Shop());
        Client.modules.add(new Triggerbot());
        Client.modules.add(new Cl3sTerConfig());
        Client.modules.add(new AntiFirework());
        Client.modules.add(new NoWeb());
        Client.modules.add(new Drugs());
        Client.modules.add(new BedESP());
        Client.modules.add(new StorageESP());
        Client.modules.add(new Spider());
        Client.modules.add(new Safewalk());
        Client.modules.add(new BetterChunkLoader());
        Client.modules.add(new SmallItems());
        Client.modules.add(new NemVagyokEgyBuziGyerek());
        Client.modules.add(new Teleport());
        Client.modules.add(new FastBreak());
        Client.modules.add(new Sneak());
        Client.modules.add(new Speed());
        Client.modules.add(new NoHead());
        Client.modules.add(new AntiPotion());
        Client.modules.add(new Spammer());
        Client.modules.add(new Step());
        Client.modules.add(new Nuker());
        Client.modules.add(new Freecam());
        Client.modules.add(new Blink());
        Client.modules.add(new PerfectHorseJump());
        Client.modules.add(new Regen());
        Client.modules.add(new AutoSoup());
        Client.modules.add(new SkinBlinker());
        Client.modules.add(new BetterDeathScreen());
        Client.modules.add(new NotchFlyEffect());
        Client.modules.add(new Dicks());
        Client.modules.add(new Tower());
        Client.modules.add(new Tracers());
        Client.modules.add(new AutoClicker());
        Client.modules.add(new ItemPhysics());
        Client.modules.add(new NoRain());
        Client.modules.add(new AntiBan());
        Client.modules.add(new AntiVPNBypass());
        Client.modules.add(new ClientClearLag());
        Client.modules.add(new ExploitFixer());
        Client.modules.add(new SpawnerPackager());
        Client.modules.add(new WDLBypass());
        Client.modules.add(new BungeeHack());
        Client.modules.add(new FastBow());
        Client.modules.add(new AutoArmor());
        Client.modules.add(new AimBot());
        Client.modules.add(new HitFly());
        Client.modules.add(new LongJump());
        Client.modules.add(new InfiniteAura());
        Client.modules.add(new KillAura());
        Client.modules.add(new BowAimBot());
        Client.modules.add(new JetPack());
        Client.modules.add(new FastPlace());
        Client.modules.add(new InventoryMove());
        Client.modules.add(new Flight());
        Client.modules.add(new Sprint());
        Client.modules.add(new FullBright());
        Client.modules.add(new NoFall());
        final Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("MooDTool/HackedItems/GlitchArmorNBT.txt")).getInputStream(), Charsets.UTF_8)));
        while (scanner.hasNextLine()) {
            HackerItemsHelper.Client_rdmText.add(scanner.nextLine());
        }
        scanner.close();
        final Scanner scanner2 = new Scanner(new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("MooDTool/HackedItems/ThrowItems.txt")).getInputStream(), Charsets.UTF_8)));
        while (scanner2.hasNextLine()) {
            HackerItemsHelper.ThrowItemsData.add(scanner2.nextLine());
        }
        scanner2.close();
        final Scanner scanner3 = new Scanner(new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("MooDTool/WebhookSpammer/randomnames.txt")).getInputStream(), Charsets.UTF_8)));
        while (scanner3.hasNextLine()) {
            HackerItemsHelper.RandomNames.add(scanner3.nextLine());
        }
        scanner3.close();
        final Scanner scanner4 = new Scanner(new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("MooDTool/WebhookSpammer/randompfp.txt")).getInputStream(), Charsets.UTF_8)));
        while (scanner4.hasNextLine()) {
            HackerItemsHelper.RandomProfilPictures.add(scanner4.nextLine());
        }
        scanner4.close();
        ChunkAnimator.INSTANCE.onStart();
    }
    
    public String EzLeszAWatermark() {
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "------[§7That's Client is outdated!§4]---------"));
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Sajn\u00e1lattal k\u00f6z\u00f6lj\u00fck, hogy a kliens"));
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§e§lBETA§c verzi\u00f3j\u00e1t haszn\u00e1lhatja."));
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Sajnos ez a verzi\u00f3 m\u00e1r"));
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§7nem eg\u00e9szen§c hasznos."));
        final ChatComponentText chatComponentText = new ChatComponentText(new StringBuilder().append(EnumChatFormatting.DARK_RED).append(EnumChatFormatting.UNDERLINE).append("§7§l[§8§lKattints ide§7§l]§r§c a§e premium").toString());
        chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/c/Cl3sTerMan/videos"));
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(chatComponentText);
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "§cverzi\u00f3 megv\u00e1s\u00e1rol\u00e1s\u00e1hoz!"));
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "-------------------------------------"));
        return null;
    }
    
    public static Logger getLogger() {
        return Client.logger;
    }
    
    public HologramManager getHologramManager() {
        return this.hologramManager;
    }
    
    public SettingsManager getSettingsManager() {
        return this.settingsmanager;
    }
    
    public static void onEvent(final Event event) {
        if (event instanceof EventChat) {
            Client.commandManager.handleChat((EventChat)event);
        }
        for (final Module module : Client.modules) {
            if (!module.toggled) {
                continue;
            }
            module.onEvent(event);
        }
    }
    
    public static DiscordRP getDiscordRP() {
        return Client.discordRP;
    }
    
    public static void KeyPress(final int n) {
        onEvent(new EventKey(n));
        for (final Module module : Client.modules) {
            if (module.getKey() == n) {
                module.toggle();
            }
        }
    }
    
    public static Module getModuleByName(final String s) {
        for (final Module module : Client.modules) {
            if (module.getName().equalsIgnoreCase(s)) {
                return module;
            }
        }
        return null;
    }
    
    public static List getModuleByCategory(final Module.Category category) {
        final ArrayList<Module> list = new ArrayList<Module>();
        for (final Module module : Client.modules) {
            if (module.category == category) {
                list.add(module);
            }
        }
        return list;
    }
    
    public static void addChatMessage(String string) {
        string = "§7[ §9" + Client.name + "§7 ] > " + string;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(new ChatComponentText(string));
    }
    
    public static final Client getInstance() {
        return Client.INSTANCE;
    }
    
    public File getDirectory() {
        return this.directory;
    }
    
    public ClientSettings getClientSettings() {
        return this.clientSettings;
    }
    
    public ConfigRegistry getConfigRegistry() {
        return this.configRegistry;
    }
    
    public static InstanceRender getRender() {
        return Client.r;
    }
}
