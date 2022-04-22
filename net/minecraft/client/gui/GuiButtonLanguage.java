package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class GuiButtonLanguage extends GuiButton
{
    private static final String __OBFID;
    
    public GuiButtonLanguage(final int n, final int n2, final int n3) {
        super(n, n2, n3, 20, 20, "");
    }
    
    @Override
    public void drawButton(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            minecraft.getTextureManager().bindTexture(GuiButton.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height) {
                final int n3 = 106 + this.height;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 106, this.width, this.height);
        }
    }
    
    static {
        __OBFID = "CL_00000672";
    }
}
