package net.minecraft.realms;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.client.entity.*;
import com.mojang.util.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;

public class RealmsScreen
{
    protected Minecraft minecraft;
    public int width;
    public int height;
    private GuiScreenRealmsProxy proxy;
    private static final String __OBFID;
    
    public RealmsScreen() {
        this.proxy = new GuiScreenRealmsProxy(this);
    }
    
    public GuiScreenRealmsProxy getProxy() {
        return this.proxy;
    }
    
    public void init() {
    }
    
    public void init(final Minecraft minecraft, final int n, final int n2) {
    }
    
    public void drawCenteredString(final String s, final int n, final int n2, final int n3) {
        this.proxy.func_154325_a(s, n, n2, n3);
    }
    
    public void drawString(final String s, final int n, final int n2, final int n3) {
        this.proxy.func_154322_b(s, n, n2, n3);
    }
    
    public void blit(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.proxy.drawTexturedModalRect(n, n2, n3, n4, n5, n6);
    }
    
    public static void blit(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final int n7, final int n8, final float n9, final float n10) {
        Gui.drawScaledCustomSizeModalRect(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
    }
    
    public static void blit(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final float n7, final float n8) {
        Gui.drawModalRectWithCustomSizedTexture(n, n2, n3, n4, n5, n6, n7, n8);
    }
    
    public void fillGradient(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.proxy.drawGradientRect(n, n2, n3, n4, n5, n6);
    }
    
    public void renderBackground() {
        this.proxy.drawDefaultBackground();
    }
    
    public boolean isPauseScreen() {
        return this.proxy.doesGuiPauseGame();
    }
    
    public void renderBackground(final int n) {
        this.proxy.drawWorldBackground(n);
    }
    
    public void render(final int n, final int n2, final float n3) {
        while (0 < this.proxy.func_154320_j().size()) {
            this.proxy.func_154320_j().get(0).render(n, n2);
            int n4 = 0;
            ++n4;
        }
    }
    
    public void renderTooltip(final ItemStack itemStack, final int n, final int n2) {
        this.proxy.renderToolTip(itemStack, n, n2);
    }
    
    public void renderTooltip(final String s, final int n, final int n2) {
        this.proxy.drawCreativeTabHoveringText(s, n, n2);
    }
    
    public void renderTooltip(final List list, final int n, final int n2) {
        this.proxy.drawHoveringText(list, n, n2);
    }
    
    public static void bindFace(final String s, final String s2) {
        ResourceLocation resourceLocation = AbstractClientPlayer.getLocationSkin(s2);
        if (resourceLocation == null) {
            resourceLocation = DefaultPlayerSkin.func_177334_a(UUIDTypeAdapter.fromString(s));
        }
        AbstractClientPlayer.getDownloadImageSkin(resourceLocation, s2);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
    }
    
    public static void bind(final String s) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(s));
    }
    
    public void tick() {
    }
    
    public int width() {
        return GuiScreenRealmsProxy.width;
    }
    
    public int height() {
        return GuiScreenRealmsProxy.height;
    }
    
    public int fontLineHeight() {
        return this.proxy.func_154329_h();
    }
    
    public int fontWidth(final String s) {
        return this.proxy.func_154326_c(s);
    }
    
    public void fontDrawShadow(final String s, final int n, final int n2, final int n3) {
        this.proxy.func_154319_c(s, n, n2, n3);
    }
    
    public List fontSplit(final String s, final int n) {
        return this.proxy.func_154323_a(s, n);
    }
    
    public void buttonClicked(final RealmsButton realmsButton) {
    }
    
    public static RealmsButton newButton(final int n, final int n2, final int n3, final String s) {
        return new RealmsButton(n, n2, n3, s);
    }
    
    public static RealmsButton newButton(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        return new RealmsButton(n, n2, n3, n4, n5, s);
    }
    
    public void buttonsClear() {
        this.proxy.func_154324_i();
    }
    
    public void buttonsAdd(final RealmsButton realmsButton) {
        this.proxy.func_154327_a(realmsButton);
    }
    
    public List buttons() {
        return this.proxy.func_154320_j();
    }
    
    public void buttonsRemove(final RealmsButton realmsButton) {
        this.proxy.func_154328_b(realmsButton);
    }
    
    public RealmsEditBox newEditBox(final int n, final int n2, final int n3, final int n4, final int n5) {
        return new RealmsEditBox(n, n2, n3, n4, n5);
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
    }
    
    public void mouseEvent() {
    }
    
    public void keyboardEvent() {
    }
    
    public void mouseReleased(final int n, final int n2, final int n3) {
    }
    
    public void mouseDragged(final int n, final int n2, final int n3, final long n4) {
    }
    
    public void keyPressed(final char c, final int n) {
    }
    
    public void confirmResult(final boolean b, final int n) {
    }
    
    public static String getLocalizedString(final String s) {
        return I18n.format(s, new Object[0]);
    }
    
    public static String getLocalizedString(final String s, final Object... array) {
        return I18n.format(s, array);
    }
    
    public RealmsAnvilLevelStorageSource getLevelStorageSource() {
        return new RealmsAnvilLevelStorageSource(Minecraft.getMinecraft().getSaveLoader());
    }
    
    public void removed() {
    }
    
    static {
        __OBFID = "CL_00001898";
    }
}
