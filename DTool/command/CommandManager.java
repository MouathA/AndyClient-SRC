package DTool.command;

import DTool.command.impl.*;
import DTool.events.listeners.*;
import java.util.*;

public class CommandManager
{
    public List commands;
    public static String prefix;
    
    static {
        CommandManager.prefix = "-";
    }
    
    public CommandManager() {
        this.commands = new ArrayList();
        this.setup();
    }
    
    public void setup() {
        this.commands.add(new Eco());
        this.commands.add(new ForceOPSpawner());
        this.commands.add(new KickHologram());
        this.commands.add(new ImageSkull());
        this.commands.add(new CrashSkullHologram());
        this.commands.add(new LaggSignOFF());
        this.commands.add(new ThrowItem());
        this.commands.add(new GlitchArmor());
        this.commands.add(new Mods());
        this.commands.add(new OldVersion());
        this.commands.add(new ArmorStand());
        this.commands.add(new HeadFollower());
        this.commands.add(new CopyItem());
        this.commands.add(new Rename());
        this.commands.add(new Give());
        this.commands.add(new TntPackager());
        this.commands.add(new CreaItmSpam());
        this.commands.add(new Hologram());
        this.commands.add(new Crasher());
        this.commands.add(new EntityFucker());
        this.commands.add(new Teleport());
        this.commands.add(new FlyingBlocks());
        this.commands.add(new FlyingItems());
        this.commands.add(new Mc());
        this.commands.add(new BookHack());
        this.commands.add(new ForceOPSign());
        this.commands.add(new Grief());
        this.commands.add(new Title());
        this.commands.add(new BookPageExploit());
        this.commands.add(new CheckCMD());
        this.commands.add(new ClearChat());
        this.commands.add(new Connect());
        this.commands.add(new CopyNBTData());
        this.commands.add(new BugItem());
        this.commands.add(new Skull());
        this.commands.add(new Help());
        this.commands.add(new Weather());
        this.commands.add(new Night());
        this.commands.add(new Day());
        this.commands.add(new Gamemode());
        this.commands.add(new CopyIP());
        this.commands.add(new RainbowArmor());
        this.commands.add(new GriefTool());
        this.commands.add(new ObjectLoader());
        this.commands.add(new MultiHolo());
        this.commands.add(new Jumper());
        this.commands.add(new PicHologram());
        this.commands.add(new Toggle());
    }
    
    public void handleChat(final EventChat eventChat) {
        final String message = eventChat.getMessage();
        if (!message.startsWith(CommandManager.prefix)) {
            return;
        }
        eventChat.setCancelled(true);
        final String substring = message.substring(CommandManager.prefix.length());
        if (substring.split(" ").length > 0) {
            final String s = substring.split(" ")[0];
            for (final Command command : this.commands) {
                if (command.aliases.contains(s) || command.name.equalsIgnoreCase(s)) {
                    command.onCommand(Arrays.copyOfRange(substring.split(" "), 1, substring.split(" ").length), substring);
                    break;
                }
            }
        }
    }
}
