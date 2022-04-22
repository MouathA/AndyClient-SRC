package net.minecraft.client.gui;

import net.minecraft.client.settings.*;

public class GuiOptionButton extends GuiButton
{
    private final GameSettings.Options enumOptions;
    private static final String __OBFID;
    
    public GuiOptionButton(final int n, final int n2, final int n3, final String s) {
        this(n, n2, n3, null, s);
    }
    
    public GuiOptionButton(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        super(n, n2, n3, n4, n5, s);
        this.enumOptions = null;
    }
    
    public GuiOptionButton(final int n, final int n2, final int n3, final GameSettings.Options enumOptions, final String s) {
        super(n, n2, n3, 150, 20, s);
        this.enumOptions = enumOptions;
    }
    
    public GameSettings.Options returnEnumOptions() {
        return this.enumOptions;
    }
    
    static {
        __OBFID = "CL_00000676";
    }
}
