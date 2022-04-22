package Mood.Designs.MainMenuDes;

import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.client.gui.*;

public class MButton extends GuiButton
{
    public MButton(final int n, final int n2, final int n3, final String s) {
        this(n, n2, n3, 200, 20, s);
    }
    
    public MButton(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        super(n, n2, n3, n4, n5, s);
    }
    
    public static void drawField(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        Gui.drawRect(n3, n4, n5, n6, n2);
        Gui.drawRect(n3 - 1, n4 - 1, n5, n4, n);
        Gui.drawRect(n3 - 1, n4 - 1, n3, n6, n);
        Gui.drawRect(n5 + 1, n4 - 1, n5, n6, n);
        Gui.drawRect(n3, n6 - 1, n5, n6, n);
    }
    
    @Override
    public void drawButton(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            this.hovered = (n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height);
            if (this.hovered) {}
            final FontRenderer fontRendererObj = Minecraft.fontRendererObj;
            drawField(Color.DARK_GRAY.getRGB(), 5121, this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height);
            Gui.drawCenteredString(Minecraft.fontRendererObj, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 16777215);
        }
    }
}
