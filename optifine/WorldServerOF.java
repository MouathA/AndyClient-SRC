package optifine;

import net.minecraft.server.*;
import net.minecraft.world.storage.*;
import net.minecraft.profiler.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;

public class WorldServerOF extends WorldServer
{
    private MinecraftServer mcServer;
    
    public WorldServerOF(final MinecraftServer mcServer, final ISaveHandler saveHandler, final WorldInfo worldInfo, final int n, final Profiler profiler) {
        super(mcServer, saveHandler, worldInfo, n, profiler);
        this.mcServer = mcServer;
    }
    
    @Override
    public void tick() {
        super.tick();
        if (!Config.isTimeDefault()) {
            this.fixWorldTime();
        }
        if (Config.waterOpacityChanged) {
            Config.waterOpacityChanged = false;
            ClearWater.updateWaterOpacity(Config.getGameSettings(), this);
        }
    }
    
    @Override
    protected void updateWeather() {
        if (!Config.isWeatherEnabled()) {
            this.fixWorldWeather();
        }
        super.updateWeather();
    }
    
    private void fixWorldWeather() {
        if (this.worldInfo.isRaining() || this.worldInfo.isThundering()) {
            this.worldInfo.setRainTime(0);
            this.worldInfo.setRaining(false);
            this.setRainStrength(0.0f);
            this.worldInfo.setThunderTime(0);
            this.worldInfo.setThundering(false);
            this.setThunderStrength(0.0f);
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(2, 0.0f));
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(7, 0.0f));
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(8, 0.0f));
        }
    }
    
    private void fixWorldTime() {
        if (this.worldInfo.getGameType().getID() == 1) {
            final long worldTime = this.getWorldTime();
            final long n = worldTime % 24000L;
            if (Config.isTimeDayOnly()) {
                if (n <= 1000L) {
                    this.setWorldTime(worldTime - n + 1001L);
                }
                if (n >= 11000L) {
                    this.setWorldTime(worldTime - n + 24001L);
                }
            }
            if (Config.isTimeNightOnly()) {
                if (n <= 14000L) {
                    this.setWorldTime(worldTime - n + 14001L);
                }
                if (n >= 22000L) {
                    this.setWorldTime(worldTime - n + 24000L + 14001L);
                }
            }
        }
    }
}
