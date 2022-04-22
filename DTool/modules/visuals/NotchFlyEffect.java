package DTool.modules.visuals;

import DTool.modules.*;

public class NotchFlyEffect extends Module
{
    public NotchFlyEffect() {
        super("NotchFlyEffect", 0, Category.Visuals);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
}
