package DTool.modules.movement;

import DTool.modules.*;

public class Highjump extends Module
{
    double height;
    
    public Highjump() {
        super("Highjump", 0, Category.Movement);
        this.height = 6.0;
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public double getAdditionalJumpMotion() {
        return this.isEnable() ? (this.height * 0.1) : 0.0;
    }
}
