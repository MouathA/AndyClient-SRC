package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.settings.*;
import java.io.*;

public class GuiCustomizeSkin extends GuiScreen
{
    private final GuiScreen field_175361_a;
    private String field_175360_f;
    private static final String __OBFID;
    
    public GuiCustomizeSkin(final GuiScreen field_175361_a) {
        this.field_175361_a = field_175361_a;
    }
    
    @Override
    public void initGui() {
        this.field_175360_f = I18n.format("options.skinCustomisation.title", new Object[0]);
        final EnumPlayerModelParts[] values = EnumPlayerModelParts.values();
        int n = 0;
        while (0 < values.length) {
            final EnumPlayerModelParts enumPlayerModelParts = values[0];
            this.buttonList.add(new ButtonPart(enumPlayerModelParts.func_179328_b(), GuiCustomizeSkin.width / 2 - 155 + 0, GuiCustomizeSkin.height / 6 + 0, 150, 20, enumPlayerModelParts, null));
            ++n;
            int n2 = 0;
            ++n2;
        }
        if (false == true) {
            ++n;
        }
        this.buttonList.add(new GuiButton(200, GuiCustomizeSkin.width / 2 - 100, GuiCustomizeSkin.height / 6 + 0, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 200) {
                GuiCustomizeSkin.mc.gameSettings.saveOptions();
                GuiCustomizeSkin.mc.displayGuiScreen(this.field_175361_a);
            }
            else if (guiButton instanceof ButtonPart) {
                final EnumPlayerModelParts access$0 = ButtonPart.access$0((ButtonPart)guiButton);
                final GameSettings gameSettings = GuiCustomizeSkin.mc.gameSettings;
                GameSettings.func_178877_a(access$0);
                guiButton.displayString = this.func_175358_a(access$0);
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.field_175360_f, GuiCustomizeSkin.width / 2, 20, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    private String func_175358_a(final EnumPlayerModelParts enumPlayerModelParts) {
        final GameSettings gameSettings = GuiCustomizeSkin.mc.gameSettings;
        String s;
        if (GameSettings.func_178876_d().contains(enumPlayerModelParts)) {
            s = I18n.format("options.on", new Object[0]);
        }
        else {
            s = I18n.format("options.off", new Object[0]);
        }
        return String.valueOf(enumPlayerModelParts.func_179326_d().getFormattedText()) + ": " + s;
    }
    
    static String access$0(final GuiCustomizeSkin guiCustomizeSkin, final EnumPlayerModelParts enumPlayerModelParts) {
        return guiCustomizeSkin.func_175358_a(enumPlayerModelParts);
    }
    
    static {
        __OBFID = "CL_00001932";
    }
    
    class ButtonPart extends GuiButton
    {
        private final EnumPlayerModelParts field_175234_p;
        private static final String __OBFID;
        final GuiCustomizeSkin this$0;
        
        private ButtonPart(final GuiCustomizeSkin this$0, final int n, final int n2, final int n3, final int n4, final int n5, final EnumPlayerModelParts field_175234_p) {
            this.this$0 = this$0;
            super(n, n2, n3, n4, n5, GuiCustomizeSkin.access$0(this$0, field_175234_p));
            this.field_175234_p = field_175234_p;
        }
        
        ButtonPart(final GuiCustomizeSkin guiCustomizeSkin, final int n, final int n2, final int n3, final int n4, final int n5, final EnumPlayerModelParts enumPlayerModelParts, final Object o) {
            this(guiCustomizeSkin, n, n2, n3, n4, n5, enumPlayerModelParts);
        }
        
        static EnumPlayerModelParts access$0(final ButtonPart buttonPart) {
            return buttonPart.field_175234_p;
        }
        
        static {
            __OBFID = "CL_00001930";
        }
    }
}
