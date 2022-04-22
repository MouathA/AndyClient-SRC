package Mood.Gui;

import Mood.Matrix.DefaultParticles.*;
import Mood.Designs.MainMenuDes.*;
import java.io.*;
import Mood.AndyConnection.integracio.webhookspam.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class GuiDiscordWeebHookSpammer extends GuiScreen
{
    protected GuiTextFields DiscordWebhookField;
    protected GuiTextFields Tamadas;
    protected GuiTextFields Uzenet;
    protected GuiScreen prevScreen;
    public ParticleEngine pe;
    public static float animatedMouseX;
    public static float animatedMouseY;
    
    public GuiDiscordWeebHookSpammer(final GuiScreen prevScreen) {
        this.pe = new ParticleEngine();
        this.prevScreen = prevScreen;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.pe.particles.clear();
        this.buttonList.add(new MButton(2, GuiDiscordWeebHookSpammer.width / 2 - 100, GuiDiscordWeebHookSpammer.height / 4 + 95 + 20 + 28, 200, 20, "M\u00e9gse"));
        this.buttonList.add(new MButton(3, GuiDiscordWeebHookSpammer.width / 2 - 100, GuiDiscordWeebHookSpammer.height / 4 + 95 + 20 + 4, 200, 20, "SPAM!"));
        this.Uzenet = new GuiTextFields(2, this.fontRendererObj, GuiDiscordWeebHookSpammer.width / 2 - 100, GuiDiscordWeebHookSpammer.height / 5, 200, 20);
        this.Tamadas = new GuiTextFields(1, this.fontRendererObj, GuiDiscordWeebHookSpammer.width / 2 - 100, GuiDiscordWeebHookSpammer.height / 5 + 40, 200, 20);
        this.DiscordWebhookField = new GuiTextFields(0, this.fontRendererObj, GuiDiscordWeebHookSpammer.width / 2 - 100, GuiDiscordWeebHookSpammer.height / 5 + 80, 200, 20);
        this.Tamadas.setText("Webhook");
        this.Uzenet.setText("Sz\u00f6veg");
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 2) {
            GuiDiscordWeebHookSpammer.mc.displayGuiScreen(this.prevScreen);
        }
        else if (guiButton.id == 3) {
            DiscordWebhookSpammer.setL(this.Tamadas.getText());
            DiscordWebhookSpammer.setK(this.Uzenet.getText());
            new DiscordWebhookSpammer();
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.DiscordWebhookField.mouseClicked(n, n2, n3);
        this.Tamadas.mouseClicked(n, n2, n3);
        this.Uzenet.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            GuiDiscordWeebHookSpammer.mc.displayGuiScreen(this.prevScreen);
            return;
        }
        if (n == 15) {
            if (this.Uzenet.isFocused()) {
                this.Uzenet.setFocused(false);
                this.Tamadas.setFocused(true);
            }
            else if (this.Tamadas.isFocused()) {
                this.Tamadas.setFocused(false);
                this.DiscordWebhookField.setFocused(true);
            }
            else if (this.DiscordWebhookField.isFocused()) {
                this.DiscordWebhookField.setFocused(false);
                this.Uzenet.setFocused(true);
            }
        }
        if (this.DiscordWebhookField.isFocused()) {
            this.DiscordWebhookField.textboxKeyTyped(c, n);
        }
        if (this.Tamadas.isFocused()) {
            this.Tamadas.textboxKeyTyped(c, n);
        }
        if (this.Uzenet.isFocused()) {
            this.Uzenet.textboxKeyTyped(c, n);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        GuiDiscordWeebHookSpammer.mc.getTextureManager().bindTexture(new ResourceLocation("Jello/hatterkep.jpg"));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(GuiDiscordWeebHookSpammer.mc, GuiDiscordWeebHookSpammer.mc.displayWidth, GuiDiscordWeebHookSpammer.mc.displayHeight);
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - GuiDiscordWeebHookSpammer.animatedMouseX + ScaledResolution.getScaledWidth(), -9.0f - GuiDiscordWeebHookSpammer.animatedMouseY / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1920.0f, 597.0f, 1920.0f, 597.0f);
        final String s = "Discord Webhook§6 Spammer";
        final String s2 = "Creators:§6 iTzMatthew1337, Dambludor";
        this.drawString(this.fontRendererObj, s, GuiDiscordWeebHookSpammer.width - this.fontRendererObj.getStringWidth(s) - 8, 8, 16777215);
        this.drawString(this.fontRendererObj, s2, GuiDiscordWeebHookSpammer.width - this.fontRendererObj.getStringWidth(s2) - 8, 18, 16777215);
        GuiDiscordWeebHookSpammer.mc.getTextureManager().bindTexture(new ResourceLocation("Jello/hatterkep.jpg"));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawCenteredString(this.fontRendererObj, "\u00cdrd be ide azt a sz\u00f6veget, amit k\u00edv\u00e1nsz spammelni:", GuiDiscordWeebHookSpammer.width / 2, this.Uzenet.yPosition - 15, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "\u00cdrd be ide a Webhookot!", GuiDiscordWeebHookSpammer.width / 2, this.DiscordWebhookField.yPosition - 15, 16777215);
        this.Tamadas.drawTextBox();
        this.Uzenet.drawTextBox();
        this.pe.render(n3, n3);
        super.drawScreen(n, n2, n3);
    }
}
