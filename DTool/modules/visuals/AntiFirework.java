package DTool.modules.visuals;

import DTool.modules.*;

public class AntiFirework extends Module
{
    public AntiFirework() {
        super("AntiFirework", 0, Category.Visuals);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
}
