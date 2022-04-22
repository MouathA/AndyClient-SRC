package DTool.modules.visuals;

import DTool.modules.*;

public class Item360 extends Module
{
    public Item360() {
        super("Item360", 0, Category.Visuals);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
}
