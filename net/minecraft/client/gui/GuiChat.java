package net.minecraft.client.gui;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import java.io.*;
import org.lwjgl.input.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import Mood.*;
import org.apache.commons.lang3.*;

public class GuiChat extends GuiScreen
{
    private static final Logger logger;
    private String historyBuffer;
    private int sentHistoryCursor;
    private boolean playerNamesFound;
    private boolean waitingOnAutocomplete;
    private int autocompleteIndex;
    private List foundPlayerNames;
    protected GuiTextField inputField;
    private String defaultInputFieldText;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000682";
        logger = LogManager.getLogger();
    }
    
    public GuiChat() {
        this.historyBuffer = "";
        this.sentHistoryCursor = -1;
        this.foundPlayerNames = Lists.newArrayList();
        this.defaultInputFieldText = "";
    }
    
    public GuiChat(final String defaultInputFieldText) {
        this.historyBuffer = "";
        this.sentHistoryCursor = -1;
        this.foundPlayerNames = Lists.newArrayList();
        this.defaultInputFieldText = "";
        this.defaultInputFieldText = defaultInputFieldText;
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        final Minecraft mc = GuiChat.mc;
        this.sentHistoryCursor = Minecraft.ingameGUI.getChatGUI().getSentMessages().size();
        (this.inputField = new GuiTextField(0, this.fontRendererObj, 4, GuiChat.height - 12, GuiChat.width - 4, 12)).setMaxStringLength(Integer.MAX_VALUE);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        final Minecraft mc = GuiChat.mc;
        Minecraft.ingameGUI.getChatGUI().resetScroll();
    }
    
    @Override
    public void updateScreen() {
        this.inputField.updateCursorCounter();
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        this.waitingOnAutocomplete = false;
        if (n == 15) {
            this.autocompletePlayerNames();
        }
        else {
            this.playerNamesFound = false;
        }
        if (n == 1) {
            GuiChat.mc.displayGuiScreen(null);
        }
        else if (n != 28 && n != 156) {
            if (n == 200) {
                this.getSentHistory(-1);
            }
            else if (n == 208) {
                this.getSentHistory(1);
            }
            else if (n == 201) {
                final Minecraft mc = GuiChat.mc;
                final GuiNewChat chatGUI = Minecraft.ingameGUI.getChatGUI();
                final Minecraft mc2 = GuiChat.mc;
                chatGUI.scroll(Minecraft.ingameGUI.getChatGUI().getLineCount() - 1);
            }
            else if (n == 209) {
                final Minecraft mc3 = GuiChat.mc;
                final GuiNewChat chatGUI2 = Minecraft.ingameGUI.getChatGUI();
                final Minecraft mc4 = GuiChat.mc;
                chatGUI2.scroll(-Minecraft.ingameGUI.getChatGUI().getLineCount() + 1);
            }
            else {
                this.inputField.textboxKeyTyped(c, n);
            }
        }
        else {
            final String trim = this.inputField.getText().trim();
            if (trim.length() > 0) {
                this.func_175275_f(trim);
            }
            GuiChat.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        Mouse.getEventDWheel();
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (n3 == 0) {
            final Minecraft mc = GuiChat.mc;
            if (this.func_175276_a(Minecraft.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY()))) {
                return;
            }
        }
        this.inputField.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void func_175274_a(final String text, final boolean b) {
        if (b) {
            this.inputField.setText(text);
        }
        else {
            this.inputField.writeText(text);
        }
    }
    
    public void autocompletePlayerNames() {
        if (this.playerNamesFound) {
            this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
            if (this.autocompleteIndex >= this.foundPlayerNames.size()) {
                this.autocompleteIndex = 0;
            }
        }
        else {
            final int func_146197_a = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
            this.foundPlayerNames.clear();
            this.autocompleteIndex = 0;
            this.sendAutocompleteRequest(this.inputField.getText().substring(0, this.inputField.getCursorPosition()), this.inputField.getText().substring(func_146197_a).toLowerCase());
            if (this.foundPlayerNames.isEmpty()) {
                return;
            }
            this.playerNamesFound = true;
            this.inputField.deleteFromCursor(func_146197_a - this.inputField.getCursorPosition());
        }
        if (this.foundPlayerNames.size() > 1) {
            final StringBuilder sb = new StringBuilder();
            for (final String s : this.foundPlayerNames) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(s);
            }
            final Minecraft mc = GuiChat.mc;
            Minecraft.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(sb.toString()), 1);
        }
        this.inputField.writeText(this.foundPlayerNames.get(this.autocompleteIndex++));
    }
    
    private void sendAutocompleteRequest(final String s, final String s2) {
        if (s.length() >= 1) {
            BlockPos func_178782_a = null;
            if (GuiChat.mc.objectMouseOver != null && GuiChat.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                func_178782_a = GuiChat.mc.objectMouseOver.func_178782_a();
            }
            final Minecraft mc = GuiChat.mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(s, func_178782_a));
            this.waitingOnAutocomplete = true;
        }
    }
    
    public void getSentHistory(final int n) {
        final int n2 = this.sentHistoryCursor + n;
        final Minecraft mc = GuiChat.mc;
        final int size = Minecraft.ingameGUI.getChatGUI().getSentMessages().size();
        final int clamp_int = MathHelper.clamp_int(n2, 0, size);
        if (clamp_int != this.sentHistoryCursor) {
            if (clamp_int == size) {
                this.sentHistoryCursor = size;
                this.inputField.setText(this.historyBuffer);
            }
            else {
                if (this.sentHistoryCursor == size) {
                    this.historyBuffer = this.inputField.getText();
                }
                final GuiTextField inputField = this.inputField;
                final Minecraft mc2 = GuiChat.mc;
                inputField.setText(Minecraft.ingameGUI.getChatGUI().getSentMessages().get(clamp_int));
                this.sentHistoryCursor = clamp_int;
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        final Client instance = Client.INSTANCE;
        if (Client.getModuleByName("HUD").toggled) {
            Gui.drawRect(2.0, GuiChat.height - 14, 728.0, GuiChat.height - 2, Integer.MIN_VALUE);
            Gui.drawRect(2.0, GuiChat.height - 19 + 4, 638.0, GuiChat.height - 17 + 3, -21455);
            String s = "§e";
            final int length = this.inputField.getText().length();
            if (length >= 25) {
                s = "§e";
            }
            if (length >= 50) {
                s = "§6";
            }
            if (length >= 75) {
                s = "§c";
            }
            if (length >= 100) {
                s = "§4";
            }
            final String string = String.valueOf(String.valueOf(String.valueOf(s))) + length + "§8/§6100";
            this.drawString(this.fontRendererObj, string, GuiChat.width - 2 - this.fontRendererObj.getStringWidth(string) - 94, GuiChat.height - 25, -1);
        }
        Gui.drawRect(2, GuiChat.height - 14, GuiChat.width - 2, GuiChat.height - 2, Integer.MIN_VALUE);
        this.inputField.drawTextBox();
        final Minecraft mc = GuiChat.mc;
        final IChatComponent chatComponent = Minecraft.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
        if (chatComponent != null && chatComponent.getChatStyle().getChatHoverEvent() != null) {
            this.func_175272_a(chatComponent, n, n2);
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void onAutocompleteResponse(final String[] array) {
        if (this.waitingOnAutocomplete) {
            this.playerNamesFound = false;
            this.foundPlayerNames.clear();
            while (0 < array.length) {
                final String s = array[0];
                if (s.length() > 0) {
                    this.foundPlayerNames.add(s);
                }
                int n = 0;
                ++n;
            }
            final String substring = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
            final String commonPrefix = StringUtils.getCommonPrefix(array);
            if (commonPrefix.length() > 0 && !substring.equalsIgnoreCase(commonPrefix)) {
                this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
                this.inputField.writeText(commonPrefix);
            }
            else if (this.foundPlayerNames.size() > 0) {
                this.playerNamesFound = true;
                this.autocompletePlayerNames();
            }
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
