package DTool.modules.visuals;

import DTool.modules.*;

public class FullBright extends Module
{
    public FullBright() {
        super("FullBright", 11, Category.Visuals);
    }
    
    @Override
    public boolean onEnable() {
        FullBright.mc.gameSettings.gammaSetting = 100.0f;
        return this.toggled;
    }
    
    @Override
    public void onDisable() {
        FullBright.mc.gameSettings.gammaSetting = 1.0f;
    }
}
