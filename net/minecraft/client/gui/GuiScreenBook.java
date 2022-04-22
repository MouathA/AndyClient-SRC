package net.minecraft.client.gui;

import net.minecraft.entity.player.*;
import org.apache.logging.log4j.*;
import net.minecraft.nbt.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import Mood.*;
import net.minecraft.event.*;
import net.minecraft.client.*;
import java.util.*;

public class GuiScreenBook extends GuiScreen
{
    private static final Logger logger;
    private static final ResourceLocation bookGuiTextures;
    private final EntityPlayer editingPlayer;
    private final ItemStack bookObj;
    private final boolean bookIsUnsigned;
    private boolean bookIsModified;
    private boolean bookGettingSigned;
    private int updateCount;
    private int bookImageWidth;
    private int bookImageHeight;
    private int bookTotalPages;
    private int currPage;
    private NBTTagList bookPages;
    private String bookTitle;
    private List field_175386_A;
    private int field_175387_B;
    private NextPageButton buttonNextPage;
    private NextPageButton buttonPreviousPage;
    private GuiButton buttonDone;
    private GuiButton buttonSign;
    private GuiButton buttonFinalize;
    private GuiButton buttonCancel;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000744";
        logger = LogManager.getLogger();
        bookGuiTextures = new ResourceLocation("textures/gui/book.png");
    }
    
    public GuiScreenBook(final EntityPlayer editingPlayer, final ItemStack bookObj, final boolean bookIsUnsigned) {
        this.bookImageWidth = 192;
        this.bookImageHeight = 192;
        this.bookTotalPages = 1;
        this.bookTitle = "";
        this.field_175387_B = -1;
        this.editingPlayer = editingPlayer;
        this.bookObj = bookObj;
        this.bookIsUnsigned = bookIsUnsigned;
        if (bookObj.hasTagCompound()) {
            this.bookPages = bookObj.getTagCompound().getTagList("pages", 8);
            if (this.bookPages != null) {
                this.bookPages = (NBTTagList)this.bookPages.copy();
                this.bookTotalPages = this.bookPages.tagCount();
                if (this.bookTotalPages < 1) {
                    this.bookTotalPages = 1;
                }
            }
        }
        if (this.bookPages == null && bookIsUnsigned) {
            (this.bookPages = new NBTTagList()).appendTag(new NBTTagString(""));
            this.bookTotalPages = 1;
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.updateCount;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        if (this.bookIsUnsigned) {
            this.buttonList.add(this.buttonSign = new GuiButton(3, GuiScreenBook.width / 2 - 100, 4 + this.bookImageHeight, 98, 20, I18n.format("book.signButton", new Object[0])));
            this.buttonList.add(this.buttonDone = new GuiButton(0, GuiScreenBook.width / 2 + 2, 4 + this.bookImageHeight, 98, 20, I18n.format("gui.done", new Object[0])));
            this.buttonList.add(this.buttonFinalize = new GuiButton(5, GuiScreenBook.width / 2 - 100, 4 + this.bookImageHeight, 98, 20, I18n.format("book.finalizeButton", new Object[0])));
            this.buttonList.add(this.buttonCancel = new GuiButton(4, GuiScreenBook.width / 2 + 2, 4 + this.bookImageHeight, 98, 20, I18n.format("gui.cancel", new Object[0])));
        }
        else {
            this.buttonList.add(this.buttonDone = new GuiButton(0, GuiScreenBook.width / 2 - 100, 4 + this.bookImageHeight, 200, 20, I18n.format("gui.done", new Object[0])));
        }
        final int n = (GuiScreenBook.width - this.bookImageWidth) / 2;
        this.buttonList.add(this.buttonNextPage = new NextPageButton(1, n + 120, 156, true));
        this.buttonList.add(this.buttonPreviousPage = new NextPageButton(2, n + 38, 156, false));
        this.updateButtons();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    private void updateButtons() {
        this.buttonNextPage.visible = (!this.bookGettingSigned && (this.currPage < this.bookTotalPages - 1 || this.bookIsUnsigned));
        this.buttonPreviousPage.visible = (!this.bookGettingSigned && this.currPage > 0);
        this.buttonDone.visible = (!this.bookIsUnsigned || !this.bookGettingSigned);
        if (this.bookIsUnsigned) {
            this.buttonSign.visible = !this.bookGettingSigned;
            this.buttonCancel.visible = this.bookGettingSigned;
            this.buttonFinalize.visible = this.bookGettingSigned;
            this.buttonFinalize.enabled = (this.bookTitle.trim().length() > 0);
        }
    }
    
    private void sendBookToServer(final boolean p0) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/gui/GuiScreenBook.bookIsUnsigned:Z
        //     4: ifeq            288
        //     7: aload_0        
        //     8: getfield        net/minecraft/client/gui/GuiScreenBook.bookIsModified:Z
        //    11: ifeq            288
        //    14: aload_0        
        //    15: getfield        net/minecraft/client/gui/GuiScreenBook.bookPages:Lnet/minecraft/nbt/NBTTagList;
        //    18: ifnull          288
        //    21: goto            68
        //    24: aload_0        
        //    25: getfield        net/minecraft/client/gui/GuiScreenBook.bookPages:Lnet/minecraft/nbt/NBTTagList;
        //    28: aload_0        
        //    29: getfield        net/minecraft/client/gui/GuiScreenBook.bookPages:Lnet/minecraft/nbt/NBTTagList;
        //    32: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //    35: iconst_1       
        //    36: isub           
        //    37: invokevirtual   net/minecraft/nbt/NBTTagList.getStringTagAt:(I)Ljava/lang/String;
        //    40: astore_2       
        //    41: aload_2        
        //    42: invokevirtual   java/lang/String.length:()I
        //    45: ifeq            51
        //    48: goto            79
        //    51: aload_0        
        //    52: getfield        net/minecraft/client/gui/GuiScreenBook.bookPages:Lnet/minecraft/nbt/NBTTagList;
        //    55: aload_0        
        //    56: getfield        net/minecraft/client/gui/GuiScreenBook.bookPages:Lnet/minecraft/nbt/NBTTagList;
        //    59: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //    62: iconst_1       
        //    63: isub           
        //    64: invokevirtual   net/minecraft/nbt/NBTTagList.removeTag:(I)Lnet/minecraft/nbt/NBTBase;
        //    67: pop            
        //    68: aload_0        
        //    69: getfield        net/minecraft/client/gui/GuiScreenBook.bookPages:Lnet/minecraft/nbt/NBTTagList;
        //    72: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //    75: iconst_1       
        //    76: if_icmpgt       24
        //    79: aload_0        
        //    80: getfield        net/minecraft/client/gui/GuiScreenBook.bookObj:Lnet/minecraft/item/ItemStack;
        //    83: invokevirtual   net/minecraft/item/ItemStack.hasTagCompound:()Z
        //    86: ifeq            110
        //    89: aload_0        
        //    90: getfield        net/minecraft/client/gui/GuiScreenBook.bookObj:Lnet/minecraft/item/ItemStack;
        //    93: invokevirtual   net/minecraft/item/ItemStack.getTagCompound:()Lnet/minecraft/nbt/NBTTagCompound;
        //    96: astore_3       
        //    97: aload_3        
        //    98: ldc             "pages"
        //   100: aload_0        
        //   101: getfield        net/minecraft/client/gui/GuiScreenBook.bookPages:Lnet/minecraft/nbt/NBTTagList;
        //   104: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //   107: goto            123
        //   110: aload_0        
        //   111: getfield        net/minecraft/client/gui/GuiScreenBook.bookObj:Lnet/minecraft/item/ItemStack;
        //   114: ldc             "pages"
        //   116: aload_0        
        //   117: getfield        net/minecraft/client/gui/GuiScreenBook.bookPages:Lnet/minecraft/nbt/NBTTagList;
        //   120: invokevirtual   net/minecraft/item/ItemStack.setTagInfo:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //   123: ldc             "MC|BEdit"
        //   125: astore_2       
        //   126: iload_1        
        //   127: ifeq            251
        //   130: ldc             "MC|BSign"
        //   132: astore_2       
        //   133: aload_0        
        //   134: getfield        net/minecraft/client/gui/GuiScreenBook.bookObj:Lnet/minecraft/item/ItemStack;
        //   137: ldc             "author"
        //   139: new             Lnet/minecraft/nbt/NBTTagString;
        //   142: dup            
        //   143: aload_0        
        //   144: getfield        net/minecraft/client/gui/GuiScreenBook.editingPlayer:Lnet/minecraft/entity/player/EntityPlayer;
        //   147: invokevirtual   net/minecraft/entity/player/EntityPlayer.getName:()Ljava/lang/String;
        //   150: invokespecial   net/minecraft/nbt/NBTTagString.<init>:(Ljava/lang/String;)V
        //   153: invokevirtual   net/minecraft/item/ItemStack.setTagInfo:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //   156: aload_0        
        //   157: getfield        net/minecraft/client/gui/GuiScreenBook.bookObj:Lnet/minecraft/item/ItemStack;
        //   160: ldc             "title"
        //   162: new             Lnet/minecraft/nbt/NBTTagString;
        //   165: dup            
        //   166: aload_0        
        //   167: getfield        net/minecraft/client/gui/GuiScreenBook.bookTitle:Ljava/lang/String;
        //   170: invokevirtual   java/lang/String.trim:()Ljava/lang/String;
        //   173: invokespecial   net/minecraft/nbt/NBTTagString.<init>:(Ljava/lang/String;)V
        //   176: invokevirtual   net/minecraft/item/ItemStack.setTagInfo:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //   179: goto            230
        //   182: aload_0        
        //   183: getfield        net/minecraft/client/gui/GuiScreenBook.bookPages:Lnet/minecraft/nbt/NBTTagList;
        //   186: iconst_0       
        //   187: invokevirtual   net/minecraft/nbt/NBTTagList.getStringTagAt:(I)Ljava/lang/String;
        //   190: astore          4
        //   192: new             Lnet/minecraft/util/ChatComponentText;
        //   195: dup            
        //   196: aload           4
        //   198: invokespecial   net/minecraft/util/ChatComponentText.<init>:(Ljava/lang/String;)V
        //   201: astore          5
        //   203: aload           5
        //   205: invokestatic    net/minecraft/util/IChatComponent$Serializer.componentToJson:(Lnet/minecraft/util/IChatComponent;)Ljava/lang/String;
        //   208: astore          4
        //   210: aload_0        
        //   211: getfield        net/minecraft/client/gui/GuiScreenBook.bookPages:Lnet/minecraft/nbt/NBTTagList;
        //   214: iconst_0       
        //   215: new             Lnet/minecraft/nbt/NBTTagString;
        //   218: dup            
        //   219: aload           4
        //   221: invokespecial   net/minecraft/nbt/NBTTagString.<init>:(Ljava/lang/String;)V
        //   224: invokevirtual   net/minecraft/nbt/NBTTagList.set:(ILnet/minecraft/nbt/NBTBase;)V
        //   227: iinc            3, 1
        //   230: iconst_0       
        //   231: aload_0        
        //   232: getfield        net/minecraft/client/gui/GuiScreenBook.bookPages:Lnet/minecraft/nbt/NBTTagList;
        //   235: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   238: if_icmplt       182
        //   241: aload_0        
        //   242: getfield        net/minecraft/client/gui/GuiScreenBook.bookObj:Lnet/minecraft/item/ItemStack;
        //   245: getstatic       net/minecraft/init/Items.written_book:Lnet/minecraft/item/Item;
        //   248: invokevirtual   net/minecraft/item/ItemStack.setItem:(Lnet/minecraft/item/Item;)V
        //   251: new             Lnet/minecraft/network/PacketBuffer;
        //   254: dup            
        //   255: invokestatic    io/netty/buffer/Unpooled.buffer:()Lio/netty/buffer/ByteBuf;
        //   258: invokespecial   net/minecraft/network/PacketBuffer.<init>:(Lio/netty/buffer/ByteBuf;)V
        //   261: astore_3       
        //   262: aload_3        
        //   263: aload_0        
        //   264: getfield        net/minecraft/client/gui/GuiScreenBook.bookObj:Lnet/minecraft/item/ItemStack;
        //   267: invokevirtual   net/minecraft/network/PacketBuffer.writeItemStackToBuffer:(Lnet/minecraft/item/ItemStack;)V
        //   270: getstatic       net/minecraft/client/gui/GuiScreenBook.mc:Lnet/minecraft/client/Minecraft;
        //   273: invokevirtual   net/minecraft/client/Minecraft.getNetHandler:()Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   276: new             Lnet/minecraft/network/play/client/C17PacketCustomPayload;
        //   279: dup            
        //   280: aload_2        
        //   281: aload_3        
        //   282: invokespecial   net/minecraft/network/play/client/C17PacketCustomPayload.<init>:(Ljava/lang/String;Lnet/minecraft/network/PacketBuffer;)V
        //   285: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   288: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                GuiScreenBook.mc.displayGuiScreen(null);
                this.sendBookToServer(false);
            }
            else if (guiButton.id == 3 && this.bookIsUnsigned) {
                this.bookGettingSigned = true;
            }
            else if (guiButton.id == 1) {
                if (this.currPage < this.bookTotalPages - 1) {
                    ++this.currPage;
                }
                else if (this.bookIsUnsigned) {
                    this.addNewPage();
                    if (this.currPage < this.bookTotalPages - 1) {
                        ++this.currPage;
                    }
                }
            }
            else if (guiButton.id == 2) {
                if (this.currPage > 0) {
                    --this.currPage;
                }
            }
            else if (guiButton.id == 5 && this.bookGettingSigned) {
                this.sendBookToServer(true);
                GuiScreenBook.mc.displayGuiScreen(null);
            }
            else if (guiButton.id == 4 && this.bookGettingSigned) {
                this.bookGettingSigned = false;
            }
            this.updateButtons();
        }
    }
    
    private void addNewPage() {
        if (this.bookPages != null && this.bookPages.tagCount() < 50) {
            this.bookPages.appendTag(new NBTTagString(""));
            ++this.bookTotalPages;
            this.bookIsModified = true;
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        if (this.bookIsUnsigned) {
            if (this.bookGettingSigned) {
                this.keyTypedInTitle(c, n);
            }
            else {
                this.keyTypedInBook(c, n);
            }
        }
    }
    
    private void keyTypedInBook(final char c, final int n) {
        if (GuiScreen.func_175279_e(n)) {
            this.pageInsertIntoCurrent(GuiScreen.getClipboardString());
        }
        else {
            switch (n) {
                case 14: {
                    final String pageGetCurrent = this.pageGetCurrent();
                    if (pageGetCurrent.length() > 0) {
                        this.pageSetCurrent(pageGetCurrent.substring(0, pageGetCurrent.length() - 1));
                    }
                }
                case 28:
                case 156: {
                    this.pageInsertIntoCurrent("\n");
                }
                default: {
                    if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                        this.pageInsertIntoCurrent(Character.toString(c));
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    private void keyTypedInTitle(final char c, final int n) throws IOException {
        switch (n) {
            case 14: {
                if (!this.bookTitle.isEmpty()) {
                    this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
                    this.updateButtons();
                }
            }
            case 28:
            case 156: {
                if (!this.bookTitle.isEmpty()) {
                    this.sendBookToServer(true);
                    GuiScreenBook.mc.displayGuiScreen(null);
                }
            }
            default: {
                if (this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(c)) {
                    this.bookTitle = String.valueOf(this.bookTitle) + Character.toString(c);
                    this.updateButtons();
                    this.bookIsModified = true;
                }
            }
        }
    }
    
    private String pageGetCurrent() {
        return (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) ? this.bookPages.getStringTagAt(this.currPage) : "";
    }
    
    private void pageSetCurrent(final String s) {
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
            this.bookPages.set(this.currPage, new NBTTagString(s));
            this.bookIsModified = true;
        }
    }
    
    private void pageInsertIntoCurrent(final String s) {
        final String string = String.valueOf(this.pageGetCurrent()) + s;
        if (this.fontRendererObj.splitStringWidth(String.valueOf(string) + EnumChatFormatting.BLACK + "_", 118) <= 128 && string.length() < 256) {
            this.pageSetCurrent(string);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiScreenBook.mc.getTextureManager().bindTexture(GuiScreenBook.bookGuiTextures);
        final int n4 = (GuiScreenBook.width - this.bookImageWidth) / 2;
        this.drawTexturedModalRect(n4, 2, 0, 0, this.bookImageWidth, this.bookImageHeight);
        if (this.bookGettingSigned) {
            String s = this.bookTitle;
            if (this.bookIsUnsigned) {
                if (this.updateCount / 6 % 2 == 0) {
                    s = String.valueOf(s) + EnumChatFormatting.BLACK + "_";
                }
                else {
                    s = String.valueOf(s) + EnumChatFormatting.GRAY + "_";
                }
            }
            final String format = I18n.format("book.editTitle", new Object[0]);
            this.fontRendererObj.drawString(format, n4 + 36 + (116 - this.fontRendererObj.getStringWidth(format)) / 2, 34, 0);
            this.fontRendererObj.drawString(s, n4 + 36 + (116 - this.fontRendererObj.getStringWidth(s)) / 2, 50, 0);
            final String format2 = I18n.format("book.byAuthor", this.editingPlayer.getName());
            this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + format2, n4 + 36 + (116 - this.fontRendererObj.getStringWidth(format2)) / 2, 60, 0);
            this.fontRendererObj.drawSplitString(I18n.format("book.finalizeWarning", new Object[0]), n4 + 36, 82, 116, 0);
        }
        else {
            final String format3 = I18n.format("book.pageIndicator", this.currPage + 1, this.bookTotalPages);
            String s2 = "";
            if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
                s2 = this.bookPages.getStringTagAt(this.currPage);
            }
            if (this.bookIsUnsigned) {
                if (this.fontRendererObj.getBidiFlag()) {
                    s2 = String.valueOf(s2) + "_";
                }
                else if (this.updateCount / 6 % 2 == 0) {
                    s2 = String.valueOf(s2) + EnumChatFormatting.BLACK + "_";
                }
                else {
                    s2 = String.valueOf(s2) + EnumChatFormatting.GRAY + "_";
                }
            }
            else if (this.field_175387_B != this.currPage) {
                if (ItemEditableBook.validBookTagContents(this.bookObj.getTagCompound())) {
                    final IChatComponent jsonToComponent = IChatComponent.Serializer.jsonToComponent(s2);
                    this.field_175386_A = ((jsonToComponent != null) ? GuiUtilRenderComponents.func_178908_a(jsonToComponent, 116, this.fontRendererObj, true, true) : null);
                }
                else {
                    this.field_175386_A = Lists.newArrayList(new ChatComponentText(String.valueOf(EnumChatFormatting.DARK_RED.toString()) + "* Invalid book tag *"));
                }
                this.field_175387_B = this.currPage;
            }
            this.fontRendererObj.drawString(format3, n4 - this.fontRendererObj.getStringWidth(format3) + this.bookImageWidth - 44, 18, 0);
            if (this.field_175386_A == null) {
                this.fontRendererObj.drawSplitString(s2, n4 + 36, 34, 116, 0);
            }
            else {
                while (0 < Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size())) {
                    final IChatComponent chatComponent = this.field_175386_A.get(0);
                    if (chatComponent.getUnformattedText().toCharArray().length <= 100) {
                        this.fontRendererObj.drawString(chatComponent.getUnformattedText(), n4 + 36, 34 + 0 * this.fontRendererObj.FONT_HEIGHT, 0);
                    }
                    else {
                        final Client instance = Client.INSTANCE;
                        if (Client.getModuleByName("ExploitFixer").toggled) {
                            this.fontRendererObj.drawStringWithShadow("§8§l[§6§lExploit Fixer§8§l]", GuiScreenBook.width / 2 - this.fontRendererObj.getStringWidth("[Exploit Fixer]") / 2, this.fontRendererObj.FONT_HEIGHT + 20, 0);
                            this.fontRendererObj.drawStringWithShadow("  §6Karakterek Sz\u00e1ma:", GuiScreenBook.width / 2 - this.fontRendererObj.getStringWidth("Karakterek Sz\u00e1ma:") / 2, this.fontRendererObj.FONT_HEIGHT + 30, 0);
                            this.fontRendererObj.drawStringWithShadow("§7  " + chatComponent.getUnformattedText().length(), (float)(GuiScreenBook.width / 2 - this.fontRendererObj.getStringWidth(new StringBuilder().append(chatComponent.getUnformattedText().length()).toString()) / 2), (float)(this.fontRendererObj.FONT_HEIGHT + 40), 0);
                        }
                    }
                    int n5 = 0;
                    ++n5;
                }
                final IChatComponent func_175385_b = this.func_175385_b(n, n2);
                if (func_175385_b != null) {
                    this.func_175272_a(func_175385_b, n, n2);
                }
            }
        }
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (n3 == 0 && this.func_175276_a(this.func_175385_b(n, n2))) {
            return;
        }
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected boolean func_175276_a(final IChatComponent chatComponent) {
        final ClickEvent clickEvent = (chatComponent == null) ? null : chatComponent.getChatStyle().getChatClickEvent();
        if (clickEvent == null) {
            return false;
        }
        if (clickEvent.getAction() != ClickEvent.Action.CHANGE_PAGE) {
            final boolean func_175276_a = super.func_175276_a(chatComponent);
            if (func_175276_a && clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                GuiScreenBook.mc.displayGuiScreen(null);
            }
            return func_175276_a;
        }
        final int currPage = Integer.parseInt(clickEvent.getValue()) - 1;
        if (currPage >= 0 && currPage < this.bookTotalPages && currPage != this.currPage) {
            this.currPage = currPage;
            this.updateButtons();
            return true;
        }
        return false;
    }
    
    public IChatComponent func_175385_b(final int n, final int n2) {
        if (this.field_175386_A == null) {
            return null;
        }
        final int n3 = n - (GuiScreenBook.width - this.bookImageWidth) / 2 - 36;
        final int n4 = n2 - 2 - 16 - 16;
        if (n3 >= 0 && n4 >= 0) {
            final int min = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());
            if (n3 <= 116) {
                final int n5 = n4;
                final Minecraft mc = GuiScreenBook.mc;
                if (n5 < Minecraft.fontRendererObj.FONT_HEIGHT * min + min) {
                    final int n6 = n4;
                    final Minecraft mc2 = GuiScreenBook.mc;
                    final int n7 = n6 / Minecraft.fontRendererObj.FONT_HEIGHT;
                    if (n7 >= 0 && n7 < this.field_175386_A.size()) {
                        for (final IChatComponent chatComponent : this.field_175386_A.get(n7)) {
                            if (chatComponent instanceof ChatComponentText) {
                                final int n8 = 0;
                                final Minecraft mc3 = GuiScreenBook.mc;
                                final int n9 = n8 + Minecraft.fontRendererObj.getStringWidth(((ChatComponentText)chatComponent).getChatComponentText_TextValue());
                                if (0 > n3) {
                                    return chatComponent;
                                }
                                continue;
                            }
                        }
                    }
                    return null;
                }
            }
            return null;
        }
        return null;
    }
    
    static ResourceLocation access$0() {
        return GuiScreenBook.bookGuiTextures;
    }
    
    static class NextPageButton extends GuiButton
    {
        private final boolean field_146151_o;
        private static final String __OBFID;
        
        public NextPageButton(final int n, final int n2, final int n3, final boolean field_146151_o) {
            super(n, n2, n3, 23, 13, "");
            this.field_146151_o = field_146151_o;
        }
        
        @Override
        public void drawButton(final Minecraft minecraft, final int n, final int n2) {
            if (this.visible) {
                final boolean b = n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height;
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                minecraft.getTextureManager().bindTexture(GuiScreenBook.access$0());
                if (b) {
                    final int n3;
                    n3 += 23;
                }
                if (!this.field_146151_o) {
                    final int n4;
                    n4 += 13;
                }
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 192, 23, 13);
            }
        }
        
        static {
            __OBFID = "CL_00000745";
        }
    }
}
