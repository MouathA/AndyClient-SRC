package net.minecraft.client.gui.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.*;
import java.io.*;

public class GuiInventory extends InventoryEffectRenderer
{
    private float oldMouseX;
    private float oldMouseY;
    private static final String __OBFID;
    
    public GuiInventory(final EntityPlayer entityPlayer) {
        super(entityPlayer.inventoryContainer);
        this.allowUserInput = true;
    }
    
    @Override
    public void updateScreen() {
        final Minecraft mc = GuiInventory.mc;
        if (Minecraft.playerController.isInCreativeMode()) {
            final Minecraft mc2 = GuiInventory.mc;
            final Minecraft mc3 = GuiInventory.mc;
            mc2.displayGuiScreen(new GuiContainerCreative(Minecraft.thePlayer));
        }
        this.func_175378_g();
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        final Minecraft mc = GuiInventory.mc;
        if (Minecraft.playerController.isInCreativeMode()) {
            final Minecraft mc2 = GuiInventory.mc;
            final Minecraft mc3 = GuiInventory.mc;
            mc2.displayGuiScreen(new GuiContainerCreative(Minecraft.thePlayer));
        }
        else {
            super.initGui();
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        this.oldMouseX = (float)n;
        this.oldMouseY = (float)n2;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiInventory.mc.getTextureManager().bindTexture(GuiInventory.inventoryBackground);
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        final int n4 = guiLeft + 51;
        final int n5 = guiTop + 75;
        final int n6 = 30;
        final float n7 = guiLeft + 51 - this.oldMouseX;
        final float n8 = guiTop + 75 - 50 - this.oldMouseY;
        final Minecraft mc = GuiInventory.mc;
        drawEntityOnScreen(n4, n5, n6, n7, n8, Minecraft.thePlayer);
    }
    
    public static void drawEntityOnScreen(final int n, final int n2, final int n3, final float n4, final float n5, final EntityLivingBase entityLivingBase) {
        GlStateManager.translate((float)n, (float)n2, 50.0f);
        GlStateManager.scale((float)(-n3), (float)n3, (float)n3);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        final float renderYawOffset = entityLivingBase.renderYawOffset;
        final float rotationYaw = entityLivingBase.rotationYaw;
        final float rotationPitch = entityLivingBase.rotationPitch;
        final float prevRotationYawHead = entityLivingBase.prevRotationYawHead;
        final float rotationYawHead = entityLivingBase.rotationYawHead;
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-(float)Math.atan(n5 / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        entityLivingBase.renderYawOffset = (float)Math.atan(n4 / 40.0f) * 20.0f;
        entityLivingBase.rotationYaw = (float)Math.atan(n4 / 40.0f) * 40.0f;
        entityLivingBase.rotationPitch = -(float)Math.atan(n5 / 40.0f) * 20.0f;
        entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
        entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        renderManager.func_178631_a(180.0f);
        renderManager.func_178633_a(false);
        renderManager.renderEntityWithPosYaw(entityLivingBase, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        renderManager.func_178633_a(true);
        entityLivingBase.renderYawOffset = renderYawOffset;
        entityLivingBase.rotationYaw = rotationYaw;
        entityLivingBase.rotationPitch = rotationPitch;
        entityLivingBase.prevRotationYawHead = prevRotationYawHead;
        entityLivingBase.rotationYawHead = rotationYawHead;
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            final Minecraft mc = GuiInventory.mc;
            final Minecraft mc2 = GuiInventory.mc;
            mc.displayGuiScreen(new GuiAchievements(this, Minecraft.thePlayer.getStatFileWriter()));
        }
        if (guiButton.id == 1) {
            final Minecraft mc3 = GuiInventory.mc;
            final Minecraft mc4 = GuiInventory.mc;
            mc3.displayGuiScreen(new GuiStats(this, Minecraft.thePlayer.getStatFileWriter()));
        }
    }
    
    static {
        __OBFID = "CL_00000761";
    }
}
