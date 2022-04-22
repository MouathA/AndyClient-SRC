package optifine;

import net.minecraft.client.gui.*;
import net.minecraft.client.settings.*;

public class GuiOptionButtonOF extends GuiOptionButton implements IOptionControl
{
    private GameSettings.Options option;
    
    public GuiOptionButtonOF(final int n, final int n2, final int n3, final GameSettings.Options option, final String s) {
        super(n, n2, n3, option, s);
        this.option = null;
        this.option = option;
    }
    
    @Override
    public GameSettings.Options getOption() {
        return this.option;
    }
}
