package net.minecraft.realms;

import net.minecraft.client.gui.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;
import java.lang.reflect.*;

public class RealmsBridge extends RealmsScreen
{
    private static final Logger LOGGER;
    private GuiScreen previousScreen;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001869";
        LOGGER = LogManager.getLogger();
    }
    
    public void switchToRealms(final GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
        final Constructor<?> declaredConstructor = Class.forName("com.mojang.realmsclient.RealmsMainScreen").getDeclaredConstructor(RealmsScreen.class);
        declaredConstructor.setAccessible(true);
        Minecraft.getMinecraft().displayGuiScreen(((RealmsScreen)declaredConstructor.newInstance(this)).getProxy());
    }
    
    @Override
    public void init() {
        Minecraft.getMinecraft().displayGuiScreen(this.previousScreen);
    }
}
