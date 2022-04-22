package DTool.modules.visuals;

import DTool.modules.*;

public class TrueSight extends Module
{
    public TrueSight() {
        super("TrueSight", 0, Category.Visuals);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
}
