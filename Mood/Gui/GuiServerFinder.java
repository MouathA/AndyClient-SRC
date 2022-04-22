package Mood.Gui;

import Mood.Matrix.DefaultParticles.*;
import org.lwjgl.input.*;
import Mood.Designs.MainMenuDes.*;
import java.net.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class GuiServerFinder extends GuiScreen
{
    private static final String[] stateStrings;
    private static GuiMultiplayer prevMenu;
    private GuiTextFields ipBox;
    private GuiTextFields portBox;
    private GuiTextFields maxThreadsBox;
    protected GuiScreen prevScreen;
    private int checked;
    private int working;
    public ParticleEngine pe;
    private ServerFinderState state;
    private GuiScreen prevGui;
    public static float animatedMouseX;
    public static float animatedMouseY;
    private static final String[] lIllIIIIIIIlIlll;
    private static String[] lIllIIIIIIIlllll;
    
    static {
        lIIIIIIllIIIlIIIl();
        lIIIIIIllIIIIllll();
        stateStrings = new String[] { GuiServerFinder.lIllIIIIIIIlIlll[0], GuiServerFinder.lIllIIIIIIIlIlll[1], GuiServerFinder.lIllIIIIIIIlIlll[2], GuiServerFinder.lIllIIIIIIIlIlll[3], GuiServerFinder.lIllIIIIIIIlIlll[4], GuiServerFinder.lIllIIIIIIIlIlll[5], GuiServerFinder.lIllIIIIIIIlIlll[6] };
    }
    
    public GuiServerFinder(final GuiMultiplayer prevMenu) {
        this.pe = new ParticleEngine();
        GuiServerFinder.prevMenu = prevMenu;
    }
    
    public GuiServerFinder(final GuiScreen prevGui) {
        this.pe = new ParticleEngine();
        this.prevGui = prevGui;
    }
    
    @Override
    public void updateScreen() {
        this.ipBox.updateCursorCounter();
        this.buttonList.get(0).displayString = (this.state.isRunning() ? GuiServerFinder.lIllIIIIIIIlIlll[7] : GuiServerFinder.lIllIIIIIIIlIlll[8]);
        this.ipBox.setEnabled(!this.state.isRunning());
        this.portBox.setEnabled(!this.state.isRunning());
        this.maxThreadsBox.setEnabled(!this.state.isRunning());
        this.buttonList.get(0).enabled = (this.isInteger(this.maxThreadsBox.getText()) && !this.ipBox.getText().isEmpty());
    }
    
    @Override
    public void initGui() {
        this.pe.particles.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new MButton(0, GuiServerFinder.width / 2 - 100, GuiServerFinder.height / 4 + 96 + 12 + 16, GuiServerFinder.lIllIIIIIIIlIlll[9]));
        this.buttonList.add(new MButton(2, GuiServerFinder.width / 2 - 100, GuiServerFinder.height / 4 + 120 + 12 + 16, GuiServerFinder.lIllIIIIIIIlIlll[10]));
        (this.ipBox = new GuiTextFields(0, this.fontRendererObj, GuiServerFinder.width / 2 - 100, GuiServerFinder.height / 4 + 34, 200, 20)).setMaxStringLength(200);
        this.ipBox.setFocused(true);
        (this.portBox = new GuiTextFields(1, this.fontRendererObj, GuiServerFinder.width / 2 - 32, GuiServerFinder.height / 4 + 76, 40, 12)).setMaxStringLength(5);
        this.portBox.setFocused(false);
        this.portBox.setText(Integer.toString(25565));
        (this.maxThreadsBox = new GuiTextFields(1, this.fontRendererObj, GuiServerFinder.width / 2 - 32, GuiServerFinder.height / 4 + 58, 48, 12)).setMaxStringLength(5);
        this.maxThreadsBox.setFocused(false);
        this.maxThreadsBox.setText(Integer.toString(128));
        this.state = ServerFinderState.NOT_RUNNING;
    }
    
    @Override
    public void onGuiClosed() {
        this.state = ServerFinderState.CANCELLED;
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                if (this.state.isRunning()) {
                    this.state = ServerFinderState.CANCELLED;
                }
                else {
                    this.state = ServerFinderState.RESOLVING;
                    this.checked = 0;
                    this.working = 0;
                    new Thread(GuiServerFinder.lIllIIIIIIIlIlll[11]) {
                        final GuiServerFinder this$0;
                        
                        @Override
                        public void run() {
                            final InetAddress byName = InetAddress.getByName(GuiServerFinder.access$1(this.this$0).getText().split(":")[0].trim());
                            final int int1 = Integer.parseInt(GuiServerFinder.access$2(this.this$0).getText());
                            final int[] array = new int[4];
                            while (0 < 4) {
                                array[0] = (byName.getAddress()[0] & 0xFF);
                                int n = 0;
                                ++n;
                            }
                            GuiServerFinder.access$3(this.this$0, ServerFinderState.SEARCHING);
                            final ArrayList<Checker> list = new ArrayList<Checker>();
                            int[] array2;
                            while (0 < (array2 = new int[] { 0, 1, -1, 2, -2, 3, -3 }).length) {
                                final int n2 = array2[0];
                                while (0 <= 1020) {
                                    if (GuiServerFinder.access$4(this.this$0) == ServerFinderState.CANCELLED) {
                                        return;
                                    }
                                    final int[] array3 = array.clone();
                                    array3[2] = (array[2] + n2 & 0xFF);
                                    array3[3] = 0;
                                    final String string = String.valueOf(String.valueOf(array3[0])) + "." + array3[1] + "." + array3[2] + "." + array3[3];
                                    final Checker checker = new Checker();
                                    checker.ping(string, int1);
                                    list.add(checker);
                                    while (list.size() >= Integer.parseInt(GuiServerFinder.access$5(this.this$0).getText())) {
                                        if (GuiServerFinder.access$4(this.this$0) == ServerFinderState.CANCELLED) {
                                            return;
                                        }
                                        GuiServerFinder.access$6(this.this$0, list);
                                    }
                                    int n3 = 0;
                                    ++n3;
                                }
                                int n4 = 0;
                                ++n4;
                            }
                            while (list.size() > 0) {
                                if (GuiServerFinder.access$4(this.this$0) == ServerFinderState.CANCELLED) {
                                    return;
                                }
                                GuiServerFinder.access$6(this.this$0, list);
                            }
                            GuiServerFinder.access$3(this.this$0, ServerFinderState.DONE);
                        }
                    }.start();
                }
            }
            else if (guiButton.id == 2) {
                GuiServerFinder.mc.displayGuiScreen(this.prevScreen);
            }
        }
    }
    
    private void updatePingers(final ArrayList list) {
        for (int i = 0; i < list.size(); ++i) {
            if (!list.get(i).isStillPinging()) {
                ++this.checked;
                if (list.get(i).isWorking()) {
                    ++this.working;
                }
            }
            list.remove(i);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) {
        this.ipBox.textboxKeyTyped(c, n);
        this.maxThreadsBox.textboxKeyTyped(c, n);
        this.portBox.textboxKeyTyped(c, n);
        if (n == 28 || n == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.ipBox.mouseClicked(n, n2, n3);
        this.maxThreadsBox.mouseClicked(n, n2, n3);
        this.portBox.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        GuiServerFinder.mc.getTextureManager().bindTexture(new ResourceLocation(GuiServerFinder.lIllIIIIIIIlIlll[12]));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(GuiServerFinder.mc, GuiServerFinder.mc.displayWidth, GuiServerFinder.mc.displayHeight);
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - GuiServerFinder.animatedMouseX + ScaledResolution.getScaledWidth(), -9.0f - GuiServerFinder.animatedMouseY / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1920.0f, 597.0f, 1920.0f, 597.0f);
        Gui.drawCenteredString(this.fontRendererObj, GuiServerFinder.lIllIIIIIIIlIlll[13], GuiServerFinder.width / 2, 20, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, GuiServerFinder.lIllIIIIIIIlIlll[14], GuiServerFinder.width / 2, 40, 10526880);
        Gui.drawCenteredString(this.fontRendererObj, GuiServerFinder.lIllIIIIIIIlIlll[15], GuiServerFinder.width / 2, 50, 10526880);
        Gui.drawCenteredString(this.fontRendererObj, GuiServerFinder.lIllIIIIIIIlIlll[16], GuiServerFinder.width / 2, 60, 10526880);
        this.drawString(this.fontRendererObj, GuiServerFinder.lIllIIIIIIIlIlll[17], GuiServerFinder.width / 2 - 100, GuiServerFinder.height / 4 + 24, 10526880);
        this.ipBox.drawTextBox();
        this.portBox.drawTextBox();
        this.drawString(this.fontRendererObj, GuiServerFinder.lIllIIIIIIIlIlll[18], GuiServerFinder.width / 2 - 100, GuiServerFinder.height / 4 + 60, 10526880);
        this.maxThreadsBox.drawTextBox();
        this.drawString(this.fontRendererObj, GuiServerFinder.lIllIIIIIIIlIlll[19], GuiServerFinder.width / 2 - 100, GuiServerFinder.height / 4 + 60 + 20, 10526880);
        Gui.drawCenteredString(this.fontRendererObj, this.state.toString(), GuiServerFinder.width / 2, GuiServerFinder.height / 4 + 1, 10526880);
        this.drawString(this.fontRendererObj, GuiServerFinder.lIllIIIIIIIlIlll[20] + this.checked + GuiServerFinder.lIllIIIIIIIlIlll[21], GuiServerFinder.width / 2 - 100, GuiServerFinder.height / 4 + 84 + 16, 10526880);
        this.drawString(this.fontRendererObj, GuiServerFinder.lIllIIIIIIIlIlll[22] + this.working, GuiServerFinder.width / 2 - 100, GuiServerFinder.height / 4 + 94 + 16, 10526880);
        this.pe.render(n3, n3);
        super.drawScreen(n, n2, n3);
    }
    
    public boolean isInteger(final String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    static String[] access$0() {
        return GuiServerFinder.stateStrings;
    }
    
    static GuiTextFields access$1(final GuiServerFinder guiServerFinder) {
        return guiServerFinder.ipBox;
    }
    
    static GuiTextFields access$2(final GuiServerFinder guiServerFinder) {
        return guiServerFinder.portBox;
    }
    
    static void access$3(final GuiServerFinder guiServerFinder, final ServerFinderState state) {
        guiServerFinder.state = state;
    }
    
    static ServerFinderState access$4(final GuiServerFinder guiServerFinder) {
        return guiServerFinder.state;
    }
    
    static GuiTextFields access$5(final GuiServerFinder guiServerFinder) {
        return guiServerFinder.maxThreadsBox;
    }
    
    static void access$6(final GuiServerFinder guiServerFinder, final ArrayList list) {
        guiServerFinder.updatePingers(list);
    }
    
    private static void lIIIIIIllIIIIllll() {
        (lIllIIIIIIIlIlll = new String[23])[0] = lIIIIIIlIllllllll(GuiServerFinder.lIllIIIIIIIlllll[0], GuiServerFinder.lIllIIIIIIIlllll[1]);
        GuiServerFinder.lIllIIIIIIIlIlll[1] = lIIIIIIllIIIIIIII(GuiServerFinder.lIllIIIIIIIlllll[2], GuiServerFinder.lIllIIIIIIIlllll[3]);
        GuiServerFinder.lIllIIIIIIIlIlll[2] = lIIIIIIllIIIIIIII(GuiServerFinder.lIllIIIIIIIlllll[4], GuiServerFinder.lIllIIIIIIIlllll[5]);
        GuiServerFinder.lIllIIIIIIIlIlll[3] = lIIIIIIllIIIIIIIl(GuiServerFinder.lIllIIIIIIIlllll[6], GuiServerFinder.lIllIIIIIIIlllll[7]);
        GuiServerFinder.lIllIIIIIIIlIlll[4] = lIIIIIIlIllllllll(GuiServerFinder.lIllIIIIIIIlllll[8], GuiServerFinder.lIllIIIIIIIlllll[9]);
        GuiServerFinder.lIllIIIIIIIlIlll[5] = lIIIIIIllIIIIIIIl(GuiServerFinder.lIllIIIIIIIlllll[10], GuiServerFinder.lIllIIIIIIIlllll[11]);
        GuiServerFinder.lIllIIIIIIIlIlll[6] = lIIIIIIlIllllllll(GuiServerFinder.lIllIIIIIIIlllll[12], GuiServerFinder.lIllIIIIIIIlllll[13]);
        GuiServerFinder.lIllIIIIIIIlIlll[7] = lIIIIIIllIIIIIIlI(GuiServerFinder.lIllIIIIIIIlllll[14], GuiServerFinder.lIllIIIIIIIlllll[15]);
        GuiServerFinder.lIllIIIIIIIlIlll[8] = lIIIIIIllIIIIIIII(GuiServerFinder.lIllIIIIIIIlllll[16], GuiServerFinder.lIllIIIIIIIlllll[17]);
        GuiServerFinder.lIllIIIIIIIlIlll[9] = lIIIIIIlIllllllll(GuiServerFinder.lIllIIIIIIIlllll[18], GuiServerFinder.lIllIIIIIIIlllll[19]);
        GuiServerFinder.lIllIIIIIIIlIlll[10] = lIIIIIIlIllllllll(GuiServerFinder.lIllIIIIIIIlllll[20], GuiServerFinder.lIllIIIIIIIlllll[21]);
        GuiServerFinder.lIllIIIIIIIlIlll[11] = lIIIIIIlIllllllll(GuiServerFinder.lIllIIIIIIIlllll[22], GuiServerFinder.lIllIIIIIIIlllll[23]);
        GuiServerFinder.lIllIIIIIIIlIlll[12] = lIIIIIIllIIIIIIlI(GuiServerFinder.lIllIIIIIIIlllll[24], GuiServerFinder.lIllIIIIIIIlllll[25]);
        GuiServerFinder.lIllIIIIIIIlIlll[13] = lIIIIIIllIIIIIIIl(GuiServerFinder.lIllIIIIIIIlllll[26], GuiServerFinder.lIllIIIIIIIlllll[27]);
        GuiServerFinder.lIllIIIIIIIlIlll[14] = lIIIIIIllIIIIIIlI(GuiServerFinder.lIllIIIIIIIlllll[28], GuiServerFinder.lIllIIIIIIIlllll[29]);
        GuiServerFinder.lIllIIIIIIIlIlll[15] = lIIIIIIlIllllllll("w6huKQcaK3kFRiQKeRLChxwuKh4SHzstRBUKKisSAwJvEDRGE8KiNMKNElAmNQgDAzU9BgNQJj0BSA==", "OYdfp");
        GuiServerFinder.lIllIIIIIIIlIlll[16] = lIIIIIIllIIIIIIII("TfuyD9uc3mSItgDd3ipYYLz5X9hIp0s/7+zluYhWlUAFifWVRati00lIWL95V8UEqgaJGm/EXmVtJbsH7mjKC+/sqtcAH+SmYUHAfo+9ffALYQO5gbjGxQ==", "FKqxh");
        GuiServerFinder.lIllIIIIIIIlIlll[17] = lIIIIIIllIIIIIIII("oQrsn7jiXi35Iu8rXIW4iUej11S5idPCsUYkGsokPXk=", "Ijxfx");
        GuiServerFinder.lIllIIIIIIIlIlll[18] = lIIIIIIllIIIIIIII("6UxBkgbUXZLRgee4UkftRg==", "jZzgm");
        GuiServerFinder.lIllIIIIIIIlIlll[19] = lIIIIIIllIIIIIIIl("GGSrZSsr+5Jf0VdX4KhVzQ==", "nUNrG");
        GuiServerFinder.lIllIIIIIIIlIlll[20] = lIIIIIIllIIIIIIlI("mRiqBu5Vp93f7n9WCi/XbQ==", "zSsmm");
        GuiServerFinder.lIllIIIIIIIlIlll[21] = lIIIIIIllIIIIIIII("qRKKZLz1eFY=", "Lrjtt");
        GuiServerFinder.lIllIIIIIIIlIlll[22] = lIIIIIIllIIIIIIII("0oFnCDuAJphqyCdRKF6RGQ==", "SNrkQ");
        GuiServerFinder.lIllIIIIIIIlllll = null;
    }
    
    private static void lIIIIIIllIIIlIIIl() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        GuiServerFinder.lIllIIIIIIIlllll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIIIIIIllIIIIIIII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIIIIIllIIIIIIlI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIIIIIlIllllllll(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
    
    private static String lIIIIIIllIIIIIIIl(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    enum ServerFinderState
    {
        NOT_RUNNING("NOT_RUNNING", 0), 
        SEARCHING("SEARCHING", 1), 
        RESOLVING("RESOLVING", 2), 
        UNKNOWN_HOST("UNKNOWN_HOST", 3), 
        CANCELLED("CANCELLED", 4), 
        DONE("DONE", 5), 
        ERROR("ERROR", 6);
        
        private static final ServerFinderState[] ENUM$VALUES;
        
        static {
            ENUM$VALUES = new ServerFinderState[] { ServerFinderState.NOT_RUNNING, ServerFinderState.SEARCHING, ServerFinderState.RESOLVING, ServerFinderState.UNKNOWN_HOST, ServerFinderState.CANCELLED, ServerFinderState.DONE, ServerFinderState.ERROR };
        }
        
        private ServerFinderState(final String s, final int n) {
        }
        
        public boolean isRunning() {
            return this == ServerFinderState.SEARCHING || this == ServerFinderState.RESOLVING;
        }
        
        @Override
        public String toString() {
            return GuiServerFinder.access$0()[this.ordinal()];
        }
    }
}
