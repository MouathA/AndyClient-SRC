package DTool.modules.visuals;

import DTool.modules.*;

public class BetterDeathScreen extends Module
{
    public BetterDeathScreen() {
        super("BetterDeathScreen", 0, Category.Visuals);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
}
