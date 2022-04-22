package Mood.Gui.clickgui.comp;

import Mood.Gui.clickgui.*;
import DTool.modules.*;
import Mood.Gui.clickgui.setting.*;

public class Comp
{
    public double x;
    public double y;
    public double x2;
    public double y2;
    public Clickgui parent;
    public Module module;
    public Setting setting;
    
    public void mouseClicked(final int n, final int n2, final int n3) {
    }
    
    public void mouseReleased(final int n, final int n2, final int n3) {
    }
    
    public void drawScreen(final int n, final int n2) {
    }
    
    public boolean isInside(final int n, final int n2, final double n3, final double n4, final double n5, final double n6) {
        return n > n3 && n < n5 && n2 > n4 && n2 < n6;
    }
    
    public void keyTyped(final char c, final int n) {
    }
}
