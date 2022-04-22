package Mood.Matrix;

import Mood.Cosmetics.*;
import net.minecraft.client.gui.*;

public enum MatrixBGManagerForInGame
{
    INSTANCE("INSTANCE", 0);
    
    public static boolean Particle;
    private static final MatrixBGManagerForInGame[] ENUM$VALUES;
    
    static {
        ENUM$VALUES = new MatrixBGManagerForInGame[] { MatrixBGManagerForInGame.INSTANCE };
        MatrixBGManagerForInGame.Particle = ModToggle.loadEnabledFromFile("Particle");
    }
    
    private MatrixBGManagerForInGame(final String s, final int n) {
    }
    
    public void onDrawStarFallBackGround() {
        Gui.drawRect(0, 0, GuiScreen.width + 100, GuiScreen.height + 100, -16777216);
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
        while (0 < 1000) {
            MatrixBG.INSTANCE.drawMatrixBackground();
            int n = 0;
            ++n;
        }
    }
    
    public void onDrawMatrixBackGround(final boolean b) {
        if (MatrixBGManagerForInGame.Particle) {
            MatrixBG.INSTANCE.drawMatrixBackground();
        }
        if (b) {
            Gui.drawRect(0, 0, GuiScreen.width + 60, GuiScreen.height + 60, -1442840576);
        }
    }
    
    public void onDrawMatrixOverlay(final boolean b) {
        MatrixBG.INSTANCE.drawMatrixBackground();
        if (b) {
            Gui.drawRect(0, 0, GuiScreen.width + 60, GuiScreen.height + 60, -1442840576);
        }
    }
}
