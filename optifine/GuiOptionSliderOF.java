package optifine;

import net.minecraft.client.gui.*;
import net.minecraft.client.settings.*;

public class GuiOptionSliderOF extends GuiOptionSlider implements IOptionControl
{
    private GameSettings.Options option;
    
    public GuiOptionSliderOF(final int n, final int n2, final int n3, final GameSettings.Options option) {
        super(n, n2, n3, option);
        this.option = null;
        this.option = option;
    }
    
    @Override
    public GameSettings.Options getOption() {
        return this.option;
    }
}
