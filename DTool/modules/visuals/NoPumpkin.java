package DTool.modules.visuals;

import DTool.modules.*;

public class NoPumpkin extends Module
{
    public NoPumpkin() {
        super("NoPumpkin", 0, Category.Visuals);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
}
