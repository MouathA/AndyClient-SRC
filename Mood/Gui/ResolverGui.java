package Mood.Gui;

import Mood.Matrix.DefaultParticles.*;
import net.minecraft.client.*;
import Mood.Designs.MainMenuDes.*;
import java.util.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import javax.naming.directory.*;

public class ResolverGui extends GuiScreen
{
    private GuiScreen screen;
    private ArrayList results;
    private GuiTextField field;
    private InitialDirContext iDirC;
    public ParticleEngine pe;
    public static float animatedMouseX;
    public static float animatedMouseY;
    
    public ResolverGui(final GuiScreen screen) {
        this.results = new ArrayList();
        this.pe = new ParticleEngine();
        this.screen = screen;
    }
    
    public GuiScreen getScreen() {
        return this.screen;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.pe.particles.clear();
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        hashtable.put("java.naming.provider.url", "dns:");
        this.iDirC = new InitialDirContext(hashtable);
        (this.field = new GuiTextField(1, this.fontRendererObj, ResolverGui.width / 2 - 200, 5, 400, 20)).setFocused(true);
        if (!ResolverGui.mc.isSingleplayer()) {
            final Minecraft mc = ResolverGui.mc;
            if (Minecraft.theWorld != null) {
                this.field.setText(ResolverGui.mc.getCurrentServerData().serverIP);
            }
        }
        this.buttonList.add(new MButton(2, ResolverGui.width / 2 - 200, 28, 130, 20, "Keres\u00e9s"));
        this.buttonList.add(new MButton(3, ResolverGui.width / 2 - 65, 28, 130, 20, "T\u00f6rl\u00e9s"));
        this.buttonList.add(new MButton(4, ResolverGui.width / 2 + 70, 28, 130, 20, "Vissza"));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        super.actionPerformed(guiButton);
        switch (guiButton.id) {
            case 2: {
                if (this.field.getText().isEmpty()) {
                    this.results.add("§eK\u00e9rlek adj meg egy IP C\u00edmet!");
                    break;
                }
                final HashSet<String> set = new HashSet<String>();
                final String[] srVs = this.getSRVs("_minecraft._tcp." + this.field.getText());
                if (srVs == null) {
                    final String[] records = this.getRecords("A", this.field.getText());
                    if (records != null) {
                        final String[] array = records;
                        final int length = records.length;
                        while (0 < 0) {
                            set.add(String.valueOf(array[0]) + ":25565");
                            int n = 0;
                            ++n;
                        }
                    }
                }
                else {
                    final HashSet<String> set2 = new HashSet<String>();
                    final String[] array2 = srVs;
                    int length2 = srVs.length;
                    while (0 < 0) {
                        final String s = array2[0];
                        set2.add(s.substring(0, s.lastIndexOf(58)));
                        int n = 0;
                        ++n;
                    }
                    final HashMap<String, String[]> hashMap = new HashMap<String, String[]>();
                    for (final String s2 : set2) {
                        hashMap.put(s2, this.getRecords("A", s2));
                    }
                    final String[] array3 = srVs;
                    while (0 < srVs.length) {
                        final String s3 = array3[0];
                        final String[] array4 = hashMap.get(s3.substring(0, s3.lastIndexOf(58)));
                        if (array4 != null) {
                            final String substring = s3.substring(s3.lastIndexOf(58) + 1);
                            final String[] array5 = array4;
                            while (0 < array4.length) {
                                set.add(String.valueOf(array5[0]) + ":" + substring);
                                int n2 = 0;
                                ++n2;
                            }
                        }
                        ++length2;
                    }
                }
                final ArrayList results = new ArrayList<String>(set);
                String string = "";
                final Iterator<String> iterator2 = results.iterator();
                while (iterator2.hasNext()) {
                    string = String.valueOf(string) + iterator2.next() + "\n";
                }
                if (!string.isEmpty()) {
                    string.substring(0, string.length() - 1);
                }
                this.results = results;
                break;
            }
            case 3: {
                this.results.clear();
                this.field.setText("");
                break;
            }
            case 4: {
                ResolverGui.mc.displayGuiScreen(this.getScreen());
                break;
            }
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.field.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        this.field.textboxKeyTyped(c, n);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.field.updateCursorCounter();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        ResolverGui.mc.getTextureManager().bindTexture(new ResourceLocation("Jello/hatterkep.jpg"));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(ResolverGui.mc, ResolverGui.mc.displayWidth, ResolverGui.mc.displayHeight);
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - ResolverGui.animatedMouseX + ScaledResolution.getScaledWidth(), -9.0f - ResolverGui.animatedMouseY / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1920.0f, 597.0f, 1920.0f, 597.0f);
        this.field.drawTextBox();
        int n4 = 0;
        while (60 < this.results.size()) {
            Gui.drawRect(ResolverGui.width / 2 - 200, 660, ResolverGui.width / 2 + 200, 670, false ? -2144325584 : Integer.MIN_VALUE);
            ++n4;
        }
        for (final String s : this.results) {
            this.drawString(this.fontRendererObj, "§e" + s, ResolverGui.width / 2 - 200, 61, -1);
            this.drawString(this.fontRendererObj, "§6" + (this.results.indexOf(s) + 1), (this.results.indexOf(s) + 1 == 10) ? (ResolverGui.width / 2 + 200 - 12) : (ResolverGui.width / 2 + 200 - this.fontRendererObj.getStringWidth("§f" + this.results.indexOf(s))), 61, -1);
            n4 += 10;
        }
        this.pe.render(n3, n3);
        super.drawScreen(n, n2, n3);
    }
    
    public String[] getSRVs(final String s) {
        final Attribute value = this.iDirC.getAttributes(s, new String[] { "SRV" }).get("SRV");
        if (value == null) {
            return null;
        }
        final String[] array = new String[value.size()];
        while (0 < value.size()) {
            final String[] split = value.get(0).toString().split(" ");
            array[0] = split[3];
            if (array[0].endsWith(".")) {
                array[0] = array[0].substring(0, array[0].length() - 1);
            }
            array[0] = String.valueOf(array[0]) + ":" + split[2];
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public String[] getRecords(final String s, final String s2) {
        final Attribute value = this.iDirC.getAttributes(s2, new String[] { s }).get(s);
        if (value == null) {
            return null;
        }
        final String[] array = new String[value.size()];
        while (0 < value.size()) {
            array[0] = value.get(0).toString();
            int n = 0;
            ++n;
        }
        return array;
    }
}
