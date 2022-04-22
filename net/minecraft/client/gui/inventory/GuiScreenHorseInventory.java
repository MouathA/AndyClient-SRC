package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class GuiScreenHorseInventory extends GuiContainer
{
    private static final ResourceLocation horseGuiTextures;
    private IInventory field_147030_v;
    private IInventory field_147029_w;
    private EntityHorse field_147034_x;
    private float field_147033_y;
    private float field_147032_z;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000760";
        horseGuiTextures = new ResourceLocation("textures/gui/container/horse.png");
    }
    
    public GuiScreenHorseInventory(final IInventory field_147030_v, final IInventory field_147029_w, final EntityHorse field_147034_x) {
        Minecraft.getMinecraft();
        super(new ContainerHorseInventory(field_147030_v, field_147029_w, field_147034_x, Minecraft.thePlayer));
        this.field_147030_v = field_147030_v;
        this.field_147029_w = field_147029_w;
        this.field_147034_x = field_147034_x;
        this.allowUserInput = false;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(this.field_147029_w.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.field_147030_v.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiScreenHorseInventory.mc.getTextureManager().bindTexture(GuiScreenHorseInventory.horseGuiTextures);
        final int n4 = (GuiScreenHorseInventory.width - this.xSize) / 2;
        final int n5 = (GuiScreenHorseInventory.height - this.ySize) / 2;
        this.drawTexturedModalRect(n4, n5, 0, 0, this.xSize, this.ySize);
        if (this.field_147034_x.isChested()) {
            this.drawTexturedModalRect(n4 + 79, n5 + 17, 0, this.ySize, 90, 54);
        }
        if (this.field_147034_x.canWearArmor()) {
            this.drawTexturedModalRect(n4 + 7, n5 + 35, 0, this.ySize + 54, 18, 18);
        }
        GuiInventory.drawEntityOnScreen(n4 + 51, n5 + 60, 17, n4 + 51 - this.field_147033_y, n5 + 75 - 50 - this.field_147032_z, this.field_147034_x);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.field_147033_y = (float)n;
        this.field_147032_z = (float)n2;
        super.drawScreen(n, n2, n3);
    }
}
