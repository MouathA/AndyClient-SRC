package Mood.Gui;

import net.minecraft.client.gui.*;
import DTool.util.*;
import java.io.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public class AbstractGuiScreen extends GuiScreen
{
    public float scale;
    public float curWidth;
    public float curHeight;
    
    public AbstractGuiScreen() {
        this(2);
    }
    
    public AbstractGuiScreen(final int n) {
        this.curWidth = 0.0f;
        this.curHeight = 0.0f;
        this.scale = (float)n;
    }
    
    public void doInit() {
    }
    
    public void drawScr(final int n, final int n2, final float n3) {
    }
    
    public void mouseClick(final int n, final int n2, final int n3) {
    }
    
    public void mouseRelease(final int n, final int n2, final int n3) {
    }
    
    @Override
    public void initGui() {
        this.doInit();
        super.initGui();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        GLUtils.INSTANCE.rescale(this.scale);
        this.curWidth = AbstractGuiScreen.mc.displayWidth / this.scale;
        this.curHeight = AbstractGuiScreen.mc.displayHeight / this.scale;
        this.drawScr(this.getRealMouseX(), this.getRealMouseY(), n3);
        GLUtils.INSTANCE.rescaleMC();
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.mouseClick(this.getRealMouseX(), this.getRealMouseY(), n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        this.mouseRelease(this.getRealMouseX(), this.getRealMouseY(), n3);
        super.mouseReleased(n, n2, n3);
    }
    
    public int getRealMouseX() {
        return (int)(Mouse.getX() * (AbstractGuiScreen.mc.displayWidth / this.scale) / AbstractGuiScreen.mc.displayWidth);
    }
    
    public int getRealMouseY() {
        final float n = AbstractGuiScreen.mc.displayHeight / this.scale;
        return (int)(n - Mouse.getY() * n / AbstractGuiScreen.mc.displayHeight);
    }
    
    public void doGlScissor(final int n, final int n2, final float n3, final float n4) {
        while (1 < this.scale && AbstractGuiScreen.mc.displayWidth / 2 >= 320 && AbstractGuiScreen.mc.displayHeight / 2 >= 240) {
            int n5 = 0;
            ++n5;
        }
        GL11.glScissor(n * 1, (int)(AbstractGuiScreen.mc.displayHeight - (n2 + n4) * 1), (int)(n3 * 1), (int)(n4 * 1));
    }
}
