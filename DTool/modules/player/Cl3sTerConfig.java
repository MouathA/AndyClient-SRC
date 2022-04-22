package DTool.modules.player;

import DTool.modules.*;
import Mood.*;

public class Cl3sTerConfig extends Module
{
    public Cl3sTerConfig() {
        super("Cl3sTerConfig", 0, Category.Player);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public boolean onEnable() {
        final Client instance = Client.INSTANCE;
        Client.getModuleByName("ItemPhysics").setState(true);
        final Client instance2 = Client.INSTANCE;
        Client.getModuleByName("BetterChunkLoader").setState(true);
        final Client instance3 = Client.INSTANCE;
        Client.getModuleByName("NoRain").setState(true);
        final Client instance4 = Client.INSTANCE;
        Client.getModuleByName("StorageESP").setState(true);
        final Client instance5 = Client.INSTANCE;
        Client.getModuleByName("FullBright").setState(true);
        final Client instance6 = Client.INSTANCE;
        Client.getModuleByName("AntiFirework").setState(true);
        final Client instance7 = Client.INSTANCE;
        Client.getModuleByName("InventoryMove").setState(true);
        final Client instance8 = Client.INSTANCE;
        Client.getModuleByName("SpawnerPackager").setState(true);
        final Client instance9 = Client.INSTANCE;
        Client.getModuleByName("WDLBypass").setState(true);
        final Client instance10 = Client.INSTANCE;
        Client.getModuleByName("Sprint").setState(true);
        final Client instance11 = Client.INSTANCE;
        Client.getModuleByName("AutoArmor").setState(true);
        final Client instance12 = Client.INSTANCE;
        Client.getModuleByName("NotchFlyEffect").setState(true);
        final Client instance13 = Client.INSTANCE;
        Client.getModuleByName("AddToChestButtons").setState(true);
        final Client instance14 = Client.INSTANCE;
        Client.getModuleByName("TrueSight").setState(true);
        final Client instance15 = Client.INSTANCE;
        Client.getModuleByName("HUD").setState(true);
        final Client instance16 = Client.INSTANCE;
        Client.getModuleByName("NoTroll").setState(true);
        final Client instance17 = Client.INSTANCE;
        Client.getModuleByName("NoPumpkin").setState(true);
        final Client instance18 = Client.INSTANCE;
        Client.getModuleByName("BetterDeathScreen").setState(true);
        final Client instance19 = Client.INSTANCE;
        Client.getModuleByName("AntiPotion").setState(true);
        final Client instance20 = Client.INSTANCE;
        Client.getModuleByName("ExploitFixer").setState(true);
        final Client instance21 = Client.INSTANCE;
        Client.getModuleByName("BungeeHack").setState(true);
        final Client instance22 = Client.INSTANCE;
        Client.getModuleByName("Trajectories").setState(true);
        return super.onEnable();
    }
}
