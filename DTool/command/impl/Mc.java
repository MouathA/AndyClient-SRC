package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import Mood.*;
import java.net.*;
import org.lwjgl.*;
import java.io.*;

public class Mc extends Command
{
    public Mc() {
        super("Mc", "Mc", "Mc", new String[] { "Mc" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        final File mcDataDir = Minecraft.mcDataDir;
        final String absolutePath = mcDataDir.getAbsolutePath();
        if (Util.getOSType() == Util.EnumOS.OSX) {
            Client.getInstance();
            Client.getLogger().info(absolutePath);
            Runtime.getRuntime().exec(new String[] { "/usr/bin/open", absolutePath });
            return;
        }
        if (Util.getOSType() == Util.EnumOS.WINDOWS) {
            Runtime.getRuntime().exec(String.format("cmd.exe /C start \"Open file\" \"%s\"", absolutePath));
            return;
        }
        final Class<?> forName = Class.forName("java.awt.Desktop");
        forName.getMethod("browse", URI.class).invoke(forName.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]), mcDataDir.toURI());
        if (true) {
            Client.getInstance();
            Client.getLogger().info(".");
            Sys.openURL("file://" + absolutePath);
        }
    }
}
