package net.minecraft.client.gui;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.resources.*;

public class GuiResourcePackAvailable extends GuiResourcePackList
{
    private static final String __OBFID;
    
    public GuiResourcePackAvailable(final Minecraft minecraft, final int n, final int n2, final List list) {
        super(minecraft, n, n2, list);
    }
    
    @Override
    protected String getListHeader() {
        return I18n.format("resourcePack.available.title", new Object[0]);
    }
    
    static {
        __OBFID = "CL_00000824";
    }
}
