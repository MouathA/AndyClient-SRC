package DTool.gui;

import Mood.Matrix.DefaultParticles.*;
import Mood.Designs.MainMenuDes.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import java.awt.*;
import java.io.*;
import net.minecraft.client.gui.*;

public class Udvozlunk extends GuiScreen
{
    public ParticleEngine pe;
    protected GuiScreen prevScreen;
    public static float animatedMouseX;
    public static float animatedMouseY;
    
    public Udvozlunk() {
        this.pe = new ParticleEngine();
    }
    
    @Override
    public void initGui() {
        this.pe.particles.clear();
        final int n = Udvozlunk.height / 4 + 48;
        final int n2 = Udvozlunk.height / 4 + 48;
        final int n3 = Udvozlunk.height / 4 + 48;
        this.buttonList.add(new MButton(1, Udvozlunk.width / 2 - 25, Udvozlunk.height - 50, 50, 20, "Rajta!"));
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        Udvozlunk.mc.getTextureManager().bindTexture(new ResourceLocation("Jello/hatterkep.jpg"));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(Udvozlunk.mc, Udvozlunk.mc.displayWidth, Udvozlunk.mc.displayHeight);
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - Udvozlunk.animatedMouseX + ScaledResolution.getScaledWidth(), -9.0f - Udvozlunk.animatedMouseY / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1920.0f, 597.0f, 1920.0f, 597.0f);
        final int n4 = Udvozlunk.height / 4 - 40;
        final String[] array = { "\u00dcdv\u00f6zl\u00fcnk az§8§l Andy§r Kliensben!", "Ezt a remek klienst", "k\u00e9t intelligens cs\u00e1v\u00f3", "k\u00e9sz\u00edtette el:", "§c§nCl3sTerMan§r & §c§nDennyel§r.", "§4§lFONTOS!§r Kiemeln\u00fcnk azt, hogy ez nem", "a teljes v\u00e1ltozata a kliensnek, n\u00e9h\u00e1ny funkci\u00f3", "nem el\u00e9rhet\u0151 benne.", "A Kliens Prefixe nem m\u00e1s mint:§7 '§e-§7'§r.", "Ez egy Demo v\u00e1ltozata a kliensnek,", "teh\u00e1t felmer\u00fclhet nem is egy hiba a kliensben!", "Ut\u00f3\u00edrat: K\u00f6sz\u00f6nj\u00fck sz\u00e9pen a§c§l 800§r feliratkoz\u00f3t!", "Discord Szerver:", "§7§nhttps://discord.gg/wQ2PxRDUBd" };
        while (0 != array.length) {
            final String s = array[0];
            final Minecraft mc = Udvozlunk.mc;
            Gui.drawCenteredString(Minecraft.fontRendererObj, s, Udvozlunk.width / 2, Udvozlunk.height / 2 - array.length * 5 + 0, Color.WHITE.getRGB());
            int n5 = 0;
            ++n5;
        }
        this.pe.render(n3, n3);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            Udvozlunk.mc.displayGuiScreen(new GuiMainMenu());
        }
        super.keyTyped(c, n);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            Udvozlunk.mc.displayGuiScreen(new GuiMainMenu());
        }
    }
}
