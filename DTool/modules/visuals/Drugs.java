package DTool.modules.visuals;

import DTool.modules.*;

public class Drugs extends Module
{
    public Drugs() {
        super("Drugs", 0, Category.Visuals);
    }
    
    @Override
    public boolean onEnable() {
        return Drugs.mc.gameSettings.viewBobbing = true;
    }
    
    @Override
    public void onDisable() {
        Drugs.mc.gameSettings.viewBobbing = false;
    }
}
