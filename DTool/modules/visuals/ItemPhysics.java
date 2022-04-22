package DTool.modules.visuals;

import DTool.modules.*;

public class ItemPhysics extends Module
{
    public ItemPhysics() {
        super("ItemPhysics", 0, Category.Visuals);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
}
