package Mood.Matrix;

import Mood.Cosmetics.*;
import net.minecraft.client.gui.*;

public enum MatrixBGManager
{
    INSTANCE("INSTANCE", 0);
    
    public static boolean Particle;
    private static final MatrixBGManager[] ENUM$VALUES;
    
    static {
        ENUM$VALUES = new MatrixBGManager[] { MatrixBGManager.INSTANCE };
        MatrixBGManager.Particle = ModToggle.loadEnabledFromFile("Particle");
    }
    
    private MatrixBGManager(final String s, final int n) {
    }
    
    public void onDrawStarFallBackGround() {
        Gui.drawRect(0, 0, GuiScreen.width + 60, GuiScreen.height + 60, -16777216);
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
        MatrixBG.INSTANCE.drawMatrixBackground();
    }
    
    public void onDrawMatrixBackGround(final boolean b) {
        MatrixBG2.INSTANCE.drawMatrixBackground();
        if (b) {
            Gui.drawRect(0, 0, GuiScreen.width + 60, GuiScreen.height + 60, -1442840576);
        }
    }
    
    public void onDrawMatrixOverlay(final boolean b) {
        MatrixBG2.INSTANCE.drawMatrixBackground();
        if (b) {
            Gui.drawRect(0, 0, GuiScreen.width + 60, GuiScreen.height + 60, -1442840576);
        }
    }
}
