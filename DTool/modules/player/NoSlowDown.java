package DTool.modules.player;

import DTool.modules.*;
import net.minecraft.init.*;

public class NoSlowDown extends Module
{
    public NoSlowDown() {
        super("NoSlowDown", 0, Category.Player);
    }
    
    @Override
    public void onDisable() {
        this.normalIce();
        super.onDisable();
    }
    
    @Override
    public boolean onEnable() {
        this.fastIce();
        return super.onEnable();
    }
    
    private void fastIce() {
        Blocks.ice.slipperiness = 0.39f;
        Blocks.packed_ice.slipperiness = 0.39f;
    }
    
    private void normalIce() {
        Blocks.ice.slipperiness = 0.98f;
        Blocks.packed_ice.slipperiness = 0.98f;
    }
}
