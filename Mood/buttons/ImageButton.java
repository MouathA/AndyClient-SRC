package Mood.buttons;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import java.awt.*;

public class ImageButton extends GuiButton
{
    private String src;
    
    public ImageButton(final int n, final int n2, final int n3, final String s, final String s2) {
        this(n, n2, n3, 200, 20, s, s2);
    }
    
    public ImageButton(final int n, final int n2, final int n3, final int n4, final int n5, final String s, final String src) {
        super(n, n2, n3, n4, n5, s);
        this.src = src;
    }
    
    @Override
    public void drawButton(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            this.hovered = (n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height);
            if (!this.src.trim().isEmpty()) {
                if (this.hovered) {
                    RenderHelper.renderImage(this.src, this.xPosition + 2, this.yPosition - 2, this.width, this.height);
                }
                else {
                    RenderHelper.renderImage(this.src, this.xPosition, this.yPosition, this.width, this.height);
                }
            }
            this.drawString(Minecraft.fontRendererObj, this.displayString, this.xPosition + this.width / 2 - Minecraft.fontRendererObj.getStringWidth(this.displayString) / 2, this.yPosition + this.height + 2, Color.white.getRGB());
        }
    }
    
    public String getSrc() {
        return this.src;
    }
    
    public void setSrc(final String src) {
        this.src = src;
    }
}
