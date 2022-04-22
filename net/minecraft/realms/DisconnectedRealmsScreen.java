package net.minecraft.realms;

import net.minecraft.util.*;
import java.util.*;

public class DisconnectedRealmsScreen extends RealmsScreen
{
    private String title;
    private IChatComponent reason;
    private List lines;
    private final RealmsScreen parent;
    private static final String __OBFID;
    
    public DisconnectedRealmsScreen(final RealmsScreen parent, final String s, final IChatComponent reason) {
        this.parent = parent;
        this.title = RealmsScreen.getLocalizedString(s);
        this.reason = reason;
    }
    
    @Override
    public void init() {
        this.buttonsClear();
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 - 100, this.height() / 4 + 120 + 12, RealmsScreen.getLocalizedString("gui.back")));
        this.lines = this.fontSplit(this.reason.getFormattedText(), this.width() - 50);
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            Realms.setScreen(this.parent);
        }
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (realmsButton.id() == 0) {
            Realms.setScreen(this.parent);
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString(this.title, this.width() / 2, this.height() / 2 - 50, 11184810);
        int n4 = this.height() / 2 - 30;
        if (this.lines != null) {
            final Iterator<String> iterator = this.lines.iterator();
            while (iterator.hasNext()) {
                this.drawCenteredString(iterator.next(), this.width() / 2, n4, 16777215);
                n4 += this.fontLineHeight();
            }
        }
        super.render(n, n2, n3);
    }
    
    static {
        __OBFID = "CL_00002145";
    }
}
