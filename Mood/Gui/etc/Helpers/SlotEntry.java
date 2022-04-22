package Mood.Gui.etc.Helpers;

import net.minecraft.client.gui.*;

public interface SlotEntry
{
    void draw(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final GuiScreen p7);
    
    void onClick(final boolean p0, final int p1, final int p2, final int p3, final GuiScreen p4);
}
