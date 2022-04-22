package DTool.modules.visuals;

import DTool.modules.*;

public class CustomModel extends Module
{
    public CustomModel() {
        super("CustomModel", 0, Category.Visuals);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
}
