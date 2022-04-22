package net.minecraft.client.gui;

import com.google.common.base.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.awt.*;
import org.apache.commons.lang3.*;
import java.awt.datatransfer.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.stats.*;
import net.minecraft.event.*;
import java.net.*;
import java.io.*;
import net.minecraft.client.gui.stream.*;
import tv.twitch.chat.*;
import org.lwjgl.input.*;
import Mood.Matrix.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback
{
    private static final Logger field_175287_a;
    private static final Set field_175284_f;
    private static final Splitter field_175285_g;
    public static Minecraft mc;
    protected RenderItem itemRender;
    public static int width;
    public static int height;
    protected List buttonList;
    protected List labelList;
    public boolean allowUserInput;
    protected FontRenderer fontRendererObj;
    private GuiButton selectedButton;
    private int eventButton;
    private long lastMouseEvent;
    private int touchValue;
    private URI field_175286_t;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000710";
        field_175287_a = LogManager.getLogger();
        field_175284_f = Sets.newHashSet("http", "https");
        field_175285_g = Splitter.on('\n');
    }
    
    public GuiScreen() {
        this.buttonList = Lists.newArrayList();
        this.labelList = Lists.newArrayList();
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        int n4 = 0;
        while (0 < this.buttonList.size()) {
            this.buttonList.get(0).drawButton(GuiScreen.mc, n, n2);
            ++n4;
        }
        while (0 < this.labelList.size()) {
            this.labelList.get(0).drawLabel(GuiScreen.mc, n, n2);
            ++n4;
        }
    }
    
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            GuiScreen.mc.displayGuiScreen(null);
            if (GuiScreen.mc.currentScreen == null) {
                GuiScreen.mc.setIngameFocus();
            }
        }
    }
    
    public static String getClipboardString() {
        final Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String)contents.getTransferData(DataFlavor.stringFlavor);
        }
        return "";
    }
    
    public static void setClipboardString(final String s) {
        if (!StringUtils.isEmpty(s)) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(s), null);
        }
    }
    
    protected void renderToolTip(final ItemStack itemStack, final int n, final int n2) {
        final List tooltip = itemStack.getTooltip(Minecraft.thePlayer, GuiScreen.mc.gameSettings.advancedItemTooltips);
        while (0 < tooltip.size()) {
            if (!false) {
                tooltip.set(0, itemStack.getRarity().rarityColor + tooltip.get(0));
            }
            else {
                tooltip.set(0, EnumChatFormatting.GRAY + tooltip.get(0));
            }
            int n3 = 0;
            ++n3;
        }
        this.drawHoveringText(tooltip, n, n2);
    }
    
    protected void drawCreativeTabHoveringText(final String s, final int n, final int n2) {
        this.drawHoveringText(Arrays.asList(s), n, n2);
    }
    
    protected void drawHoveringText(final List list, final int n, final int n2) {
        if (!list.isEmpty()) {
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (this.fontRendererObj.getStringWidth(iterator.next()) > 0) {
                    continue;
                }
            }
            int n3 = n + 12;
            int n4 = n2 - 12;
            if (list.size() > 1) {
                final int n5 = 8 + (2 + (list.size() - 1) * 10);
            }
            if (n3 + 0 > GuiScreen.width) {
                n3 -= 28;
            }
            if (n4 + 8 + 6 > GuiScreen.height) {
                n4 = GuiScreen.height - 8 - 6;
            }
            this.zLevel = 300.0f;
            this.itemRender.zLevel = 300.0f;
            this.drawGradientRect(n3 - 3, n4 - 4, n3 + 0 + 3, n4 - 3, -267386864, -267386864);
            this.drawGradientRect(n3 - 3, n4 + 8 + 3, n3 + 0 + 3, n4 + 8 + 4, -267386864, -267386864);
            this.drawGradientRect(n3 - 3, n4 - 3, n3 + 0 + 3, n4 + 8 + 3, -267386864, -267386864);
            this.drawGradientRect(n3 - 4, n4 - 3, n3 - 3, n4 + 8 + 3, -267386864, -267386864);
            this.drawGradientRect(n3 + 0 + 3, n4 - 3, n3 + 0 + 4, n4 + 8 + 3, -267386864, -267386864);
            this.drawGradientRect(n3 - 3, n4 - 3 + 1, n3 - 3 + 1, n4 + 8 + 3 - 1, 1347420415, 1344798847);
            this.drawGradientRect(n3 + 0 + 2, n4 - 3 + 1, n3 + 0 + 3, n4 + 8 + 3 - 1, 1347420415, 1344798847);
            this.drawGradientRect(n3 - 3, n4 - 3, n3 + 0 + 3, n4 - 3 + 1, 1347420415, 1347420415);
            this.drawGradientRect(n3 - 3, n4 + 8 + 2, n3 + 0 + 3, n4 + 8 + 3, 1344798847, 1344798847);
            while (0 < list.size()) {
                this.fontRendererObj.drawStringWithShadow(list.get(0), n3, n4, -1);
                if (!false) {
                    n4 += 2;
                }
                n4 += 10;
                int n6 = 0;
                ++n6;
            }
            this.zLevel = 0.0f;
            this.itemRender.zLevel = 0.0f;
        }
    }
    
    protected void func_175272_a(final IChatComponent chatComponent, final int n, final int n2) {
        if (chatComponent != null && chatComponent.getChatStyle().getChatHoverEvent() != null) {
            final HoverEvent chatHoverEvent = chatComponent.getChatStyle().getChatHoverEvent();
            if (chatHoverEvent.getAction() == HoverEvent.Action.SHOW_ITEM) {
                ItemStack loadItemStackFromNBT = null;
                final NBTTagCompound func_180713_a = JsonToNBT.func_180713_a(chatHoverEvent.getValue().getUnformattedText());
                if (func_180713_a instanceof NBTTagCompound) {
                    loadItemStackFromNBT = ItemStack.loadItemStackFromNBT(func_180713_a);
                }
                if (loadItemStackFromNBT != null) {
                    this.renderToolTip(loadItemStackFromNBT, n, n2);
                }
                else {
                    this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Item!", n, n2);
                }
            }
            else if (chatHoverEvent.getAction() == HoverEvent.Action.SHOW_ENTITY) {
                if (GuiScreen.mc.gameSettings.advancedItemTooltips) {
                    final NBTTagCompound func_180713_a2 = JsonToNBT.func_180713_a(chatHoverEvent.getValue().getUnformattedText());
                    if (func_180713_a2 instanceof NBTTagCompound) {
                        final ArrayList arrayList = Lists.newArrayList();
                        final NBTTagCompound nbtTagCompound = func_180713_a2;
                        arrayList.add(nbtTagCompound.getString("name"));
                        if (nbtTagCompound.hasKey("type", 8)) {
                            final String string = nbtTagCompound.getString("type");
                            arrayList.add("Type: " + string + " (" + EntityList.func_180122_a(string) + ")");
                        }
                        arrayList.add(nbtTagCompound.getString("id"));
                        this.drawHoveringText(arrayList, n, n2);
                    }
                    else {
                        this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", n, n2);
                    }
                }
            }
            else if (chatHoverEvent.getAction() == HoverEvent.Action.SHOW_TEXT) {
                this.drawHoveringText(GuiScreen.field_175285_g.splitToList(chatHoverEvent.getValue().getFormattedText()), n, n2);
            }
            else if (chatHoverEvent.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
                final StatBase oneShotStat = StatList.getOneShotStat(chatHoverEvent.getValue().getUnformattedText());
                if (oneShotStat != null) {
                    final IChatComponent statName = oneShotStat.getStatName();
                    final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("stats.tooltip.type." + (oneShotStat.isAchievement() ? "achievement" : "statistic"), new Object[0]);
                    chatComponentTranslation.getChatStyle().setItalic(true);
                    final String s = (oneShotStat instanceof Achievement) ? ((Achievement)oneShotStat).getDescription() : null;
                    final ArrayList arrayList2 = Lists.newArrayList(statName.getFormattedText(), chatComponentTranslation.getFormattedText());
                    if (s != null) {
                        arrayList2.addAll(this.fontRendererObj.listFormattedStringToWidth(s, 150));
                    }
                    this.drawHoveringText(arrayList2, n, n2);
                }
                else {
                    this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid statistic/achievement!", n, n2);
                }
            }
        }
    }
    
    protected void func_175274_a(final String s, final boolean b) {
    }
    
    protected boolean func_175276_a(final IChatComponent chatComponent) {
        if (chatComponent == null) {
            return false;
        }
        final ClickEvent chatClickEvent = chatComponent.getChatStyle().getChatClickEvent();
        if (isShiftKeyDown()) {
            if (chatComponent.getChatStyle().getInsertion() != null) {
                this.func_175274_a(chatComponent.getChatStyle().getInsertion(), false);
            }
        }
        else if (chatClickEvent != null) {
            if (chatClickEvent.getAction() == ClickEvent.Action.OPEN_URL) {
                if (!GuiScreen.mc.gameSettings.chatLinks) {
                    return false;
                }
                final URI field_175286_t = new URI(chatClickEvent.getValue());
                if (!GuiScreen.field_175284_f.contains(field_175286_t.getScheme().toLowerCase())) {
                    throw new URISyntaxException(chatClickEvent.getValue(), "Unsupported protocol: " + field_175286_t.getScheme().toLowerCase());
                }
                if (GuiScreen.mc.gameSettings.chatLinksPrompt) {
                    this.field_175286_t = field_175286_t;
                    GuiScreen.mc.displayGuiScreen(new GuiConfirmOpenLink(this, chatClickEvent.getValue(), 31102009, false));
                }
                else {
                    this.func_175282_a(field_175286_t);
                }
            }
            else if (chatClickEvent.getAction() == ClickEvent.Action.OPEN_FILE) {
                this.func_175282_a(new File(chatClickEvent.getValue()).toURI());
            }
            else if (chatClickEvent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
                this.func_175274_a(chatClickEvent.getValue(), true);
            }
            else if (chatClickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.func_175281_b(chatClickEvent.getValue(), false);
            }
            else if (chatClickEvent.getAction() == ClickEvent.Action.TWITCH_USER_INFO) {
                final ChatUserInfo func_152926_a = GuiScreen.mc.getTwitchStream().func_152926_a(chatClickEvent.getValue());
                if (func_152926_a != null) {
                    GuiScreen.mc.displayGuiScreen(new GuiTwitchUserMode(GuiScreen.mc.getTwitchStream(), func_152926_a));
                }
                else {
                    GuiScreen.field_175287_a.error("Tried to handle twitch user but couldn't find them!");
                }
            }
            else {
                GuiScreen.field_175287_a.error("Don't know how to handle " + chatClickEvent);
            }
            return true;
        }
        return false;
    }
    
    public void func_175275_f(final String s) {
        this.func_175281_b(s, true);
    }
    
    public void func_175281_b(final String s, final boolean b) {
        if (b) {
            Minecraft.ingameGUI.getChatGUI().addToSentMessages(s);
        }
        Minecraft.thePlayer.sendChatMessage(s);
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (n3 == 0) {
            while (0 < this.buttonList.size()) {
                final GuiButton selectedButton = this.buttonList.get(0);
                if (selectedButton.mousePressed(GuiScreen.mc, n, n2)) {
                    (this.selectedButton = selectedButton).playPressSound(Minecraft.getSoundHandler());
                    this.actionPerformed(selectedButton);
                }
                int n4 = 0;
                ++n4;
            }
        }
    }
    
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (this.selectedButton != null && n3 == 0) {
            this.selectedButton.mouseReleased(n, n2);
            this.selectedButton = null;
        }
    }
    
    protected void mouseClickMove(final int n, final int n2, final int n3, final long n4) {
    }
    
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
    }
    
    public void setWorldAndResolution(final Minecraft mc, final int width, final int height) {
        GuiScreen.mc = mc;
        this.itemRender = mc.getRenderItem();
        this.fontRendererObj = Minecraft.fontRendererObj;
        GuiScreen.width = width;
        GuiScreen.height = height;
        this.buttonList.clear();
        this.initGui();
    }
    
    public void initGui() {
    }
    
    public void handleInput() throws IOException {
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                this.handleMouseInput();
            }
        }
        if (Keyboard.isCreated()) {
            while (Keyboard.next()) {
                this.handleKeyboardInput();
            }
        }
    }
    
    public void handleMouseInput() throws IOException {
        final int n = Mouse.getEventX() * GuiScreen.width / GuiScreen.mc.displayWidth;
        final int n2 = GuiScreen.height - Mouse.getEventY() * GuiScreen.height / GuiScreen.mc.displayHeight - 1;
        final int eventButton = Mouse.getEventButton();
        if (Mouse.getEventButtonState()) {
            if (GuiScreen.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
                return;
            }
            this.eventButton = eventButton;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(n, n2, this.eventButton);
        }
        else if (eventButton != -1) {
            if (GuiScreen.mc.gameSettings.touchscreen && --this.touchValue > 0) {
                return;
            }
            this.eventButton = -1;
            this.mouseReleased(n, n2, eventButton);
        }
        else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
            this.mouseClickMove(n, n2, this.eventButton, Minecraft.getSystemTime() - this.lastMouseEvent);
        }
    }
    
    public void handleKeyboardInput() throws IOException {
        if (Keyboard.getEventKeyState()) {
            this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
        GuiScreen.mc.dispatchKeypresses();
    }
    
    public void updateScreen() {
    }
    
    public void onGuiClosed() {
    }
    
    public void drawDefaultBackground() {
        this.drawWorldBackground(0);
    }
    
    public void drawWorldBackground(final int n) {
        if (Minecraft.theWorld != null) {
            if (MatrixBGManagerForInGame.Particle) {
                MatrixBGManagerForInGame.INSTANCE.onDrawMatrixBackGround(false);
            }
            this.drawGradientRect(0, 0, GuiScreen.width, GuiScreen.height, -1072689136, -804253680);
            MatrixBGManagerForInGame.INSTANCE.onDrawMatrixBackGround(false);
        }
        else {
            this.drawBackground(n);
        }
    }
    
    public void drawMateFasza() {
        this.drawMateFasza(0);
    }
    
    public void drawMateFasza(final int n) {
        if (Minecraft.theWorld != null) {
            if (MatrixBGManagerForInGame.Particle) {
                GuiScreen.mc.getTextureManager().bindTexture(new ResourceLocation("Jello/hatterkep.jpg"));
            }
            GuiScreen.mc.getTextureManager().bindTexture(new ResourceLocation("Jello/hatterkep.jpg"));
        }
        else {
            this.drawMateDick(n);
        }
    }
    
    public void drawBackground(final int n) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GuiScreen.mc.getTextureManager().bindTexture(GuiScreen.optionsBackground);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final float n2 = 32.0f;
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178991_c(4210752);
        worldRenderer.addVertexWithUV(0.0, GuiScreen.height, 0.0, 0.0, GuiScreen.height / n2 + n);
        worldRenderer.addVertexWithUV(GuiScreen.width, GuiScreen.height, 0.0, GuiScreen.width / n2, GuiScreen.height / n2 + n);
        worldRenderer.addVertexWithUV(GuiScreen.width, 0.0, 0.0, GuiScreen.width / n2, n);
        worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, 0.0, n);
        instance.draw();
    }
    
    public void drawMateDick(final int n) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GuiScreen.mc.getTextureManager().bindTexture(new ResourceLocation("Jello/hatterkep.jpg"));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final float n2 = 10000.0f;
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178991_c(4210752);
        worldRenderer.addVertexWithUV(0.0, GuiScreen.height, 0.0, 0.0, GuiScreen.height / n2 + n);
        worldRenderer.addVertexWithUV(GuiScreen.width, GuiScreen.height, 0.0, GuiScreen.width / n2, GuiScreen.height / n2 + n);
        worldRenderer.addVertexWithUV(GuiScreen.width, 0.0, 0.0, GuiScreen.width / n2, n);
        worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, 0.0, n);
        instance.draw();
    }
    
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (n == 31102009) {
            if (b) {
                this.func_175282_a(this.field_175286_t);
            }
            this.field_175286_t = null;
            GuiScreen.mc.displayGuiScreen(this);
        }
    }
    
    private void func_175282_a(final URI uri) {
        final Class<?> forName = Class.forName("java.awt.Desktop");
        forName.getMethod("browse", URI.class).invoke(forName.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]), uri);
    }
    
    public static boolean isCtrlKeyDown() {
        return Minecraft.isRunningOnMac ? (Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220)) : (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));
    }
    
    public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }
    
    public static boolean func_175283_s() {
        return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
    }
    
    public static boolean func_175277_d(final int n) {
        return n == 45 && isCtrlKeyDown();
    }
    
    public static boolean func_175279_e(final int n) {
        return n == 47 && isCtrlKeyDown();
    }
    
    public static boolean func_175280_f(final int n) {
        return n == 46 && isCtrlKeyDown();
    }
    
    public static boolean func_175278_g(final int n) {
        return n == 30 && isCtrlKeyDown();
    }
    
    public void func_175273_b(final Minecraft minecraft, final int n, final int n2) {
        this.setWorldAndResolution(minecraft, n, n2);
    }
}
