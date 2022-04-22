package net.minecraft.world.demo;

import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.world.storage.*;
import net.minecraft.profiler.*;

public class DemoWorldServer extends WorldServer
{
    private static final long demoWorldSeed;
    public static final WorldSettings demoWorldSettings;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001428";
        demoWorldSeed = "North Carolina".hashCode();
        demoWorldSettings = new WorldSettings(DemoWorldServer.demoWorldSeed, WorldSettings.GameType.SURVIVAL, true, false, WorldType.DEFAULT).enableBonusChest();
    }
    
    public DemoWorldServer(final MinecraftServer minecraftServer, final ISaveHandler saveHandler, final WorldInfo worldInfo, final int n, final Profiler profiler) {
        super(minecraftServer, saveHandler, worldInfo, n, profiler);
        this.worldInfo.populateFromWorldSettings(DemoWorldServer.demoWorldSettings);
    }
}
