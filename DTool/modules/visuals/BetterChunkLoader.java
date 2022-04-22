package DTool.modules.visuals;

import DTool.modules.*;

public class BetterChunkLoader extends Module
{
    public BetterChunkLoader() {
        super("BetterChunkLoader", 0, Category.Visuals);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
}
