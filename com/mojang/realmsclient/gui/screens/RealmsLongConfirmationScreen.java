package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.gui.*;
import net.minecraft.realms.*;

public class RealmsLongConfirmationScreen extends RealmsScreen
{
    private final Type type;
    private final String line2;
    private final String line3;
    protected final RealmsScreen parent;
    protected final String yesButton;
    protected final String noButton;
    private final String okButton;
    protected final int id;
    private final boolean yesNoQuestion;
    
    public RealmsLongConfirmationScreen(final RealmsScreen parent, final Type type, final String line2, final String line3, final boolean yesNoQuestion, final int id) {
        this.parent = parent;
        this.id = id;
        this.type = type;
        this.line2 = line2;
        this.line3 = line3;
        this.yesNoQuestion = yesNoQuestion;
        this.yesButton = RealmsScreen.getLocalizedString("gui.yes");
        this.noButton = RealmsScreen.getLocalizedString("gui.no");
        this.okButton = RealmsScreen.getLocalizedString("mco.gui.ok");
    }
    
    @Override
    public void init() {
        if (this.yesNoQuestion) {
            this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 - 105, RealmsConstants.row(8), 100, 20, this.yesButton));
            this.buttonsAdd(RealmsScreen.newButton(1, this.width() / 2 + 5, RealmsConstants.row(8), 100, 20, this.noButton));
        }
        else {
            this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 - 50, RealmsConstants.row(8), 100, 20, this.okButton));
        }
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        this.parent.confirmResult(realmsButton.id() == 0, this.id);
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            this.parent.confirmResult(false, this.id);
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString(this.type.text, this.width() / 2, RealmsConstants.row(2), this.type.colorCode);
        this.drawCenteredString(this.line2, this.width() / 2, RealmsConstants.row(4), 16777215);
        this.drawCenteredString(this.line3, this.width() / 2, RealmsConstants.row(6), 16777215);
        super.render(n, n2, n3);
    }
    
    public enum Type
    {
        Warning("Warning", 0, "Warning!", 16711680), 
        Info("Info", 1, "Info!", 8226750);
        
        public final int colorCode;
        public final String text;
        private static final Type[] $VALUES;
        
        private Type(final String s, final int n, final String text, final int colorCode) {
            this.text = text;
            this.colorCode = colorCode;
        }
        
        static {
            $VALUES = new Type[] { Type.Warning, Type.Info };
        }
    }
}
