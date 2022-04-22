package viamcp.gui;

import Mood.UIButtons.*;
import viamcp.protocols.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import viamcp.*;
import net.minecraft.client.renderer.*;

public class AsyncVersionSlider extends UIButtons
{
    private float dragValue;
    private final ProtocolCollection[] values;
    private float sliderValue;
    public boolean dragging;
    
    public AsyncVersionSlider(final int n, final int n2, final int n3, final int n4, final int n5) {
        super(n, n2, n3, Math.max(n4, 110), n5, "");
        this.dragValue = (ProtocolCollection.values().length - Arrays.asList(ProtocolCollection.values()).indexOf(ProtocolCollection.getProtocolCollectionById(47))) / (float)ProtocolCollection.values().length;
        this.values = ProtocolCollection.values();
        Collections.reverse(Arrays.asList(this.values));
        this.sliderValue = this.dragValue;
        this.displayString = this.values[(int)(this.sliderValue * (this.values.length - 1))].getVersion().getName();
    }
    
    @Override
    public void drawButton(final Minecraft minecraft, final int n, final int n2) {
        super.drawButton(minecraft, n, n2);
    }
    
    @Override
    protected int getHoverState(final boolean b) {
        return 0;
    }
    
    @Override
    protected void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = (n - (this.xPosition + 4)) / (float)(this.width - 8);
                this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0f, 1.0f);
                this.dragValue = this.sliderValue;
                this.displayString = this.values[(int)(this.sliderValue * (this.values.length - 1))].getVersion().getName();
                ViaMCP.getInstance().setVersion(this.values[(int)(this.sliderValue * (this.values.length - 1))].getVersion().getVersion());
            }
            minecraft.getTextureManager().bindTexture(AsyncVersionSlider.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.sliderValue = (n - (this.xPosition + 4)) / (float)(this.width - 8);
            this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0f, 1.0f);
            this.dragValue = this.sliderValue;
            this.displayString = this.values[(int)(this.sliderValue * (this.values.length - 1))].getVersion().getName();
            ViaMCP.getInstance().setVersion(this.values[(int)(this.sliderValue * (this.values.length - 1))].getVersion().getVersion());
            return this.dragging = true;
        }
        return false;
    }
    
    @Override
    public void mouseReleased(final int n, final int n2) {
        this.dragging = false;
    }
    
    public void setVersion(final int n) {
        this.dragValue = (ProtocolCollection.values().length - Arrays.asList(ProtocolCollection.values()).indexOf(ProtocolCollection.getProtocolCollectionById(n))) / (float)ProtocolCollection.values().length;
        this.sliderValue = this.dragValue;
        this.displayString = this.values[(int)(this.sliderValue * (this.values.length - 1))].getVersion().getName();
    }
}
