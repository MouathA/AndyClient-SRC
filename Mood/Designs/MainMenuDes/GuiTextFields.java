package Mood.Designs.MainMenuDes;

import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import net.minecraft.client.renderer.*;

public class GuiTextFields extends Gui
{
    private final int field_175208_g;
    private final FontRenderer fontRendererInstance;
    public int xPosition;
    public int yPosition;
    private String presetString;
    public static int width;
    private static int height;
    private String text;
    private int maxStringLength;
    private int cursorCounter;
    private boolean enableBackgroundDrawing;
    private boolean canLoseFocus;
    private boolean isFocused;
    private boolean isEnabled;
    private int lineScrollOffset;
    private int cursorPosition;
    private int selectionEnd;
    private int enabledColor;
    private int disabledColor;
    private boolean visible;
    private GuiPageButtonList.GuiResponder field_175210_x;
    private Predicate field_175209_y;
    private static final String __OBFID;
    
    public GuiTextFields(final int field_175208_g, final FontRenderer fontRendererInstance, final int xPosition, final int yPosition, final int width, final int height) {
        this.presetString = "";
        this.text = "";
        this.maxStringLength = 9999999;
        this.enableBackgroundDrawing = true;
        this.canLoseFocus = true;
        this.isEnabled = true;
        this.enabledColor = 14737632;
        this.disabledColor = 7368816;
        this.visible = true;
        this.field_175209_y = Predicates.alwaysTrue();
        this.field_175208_g = field_175208_g;
        this.fontRendererInstance = fontRendererInstance;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        GuiTextFields.width = width;
        GuiTextFields.height = height;
    }
    
    public void func_175207_a(final GuiPageButtonList.GuiResponder field_175210_x) {
        this.field_175210_x = field_175210_x;
    }
    
    public void updateCursorCounter() {
        ++this.cursorCounter;
    }
    
    public void setText(final String text) {
        if (this.field_175209_y.apply(text)) {
            if (text.length() > this.maxStringLength) {
                this.text = text.substring(0, this.maxStringLength);
            }
            else {
                this.text = text;
            }
            this.setCursorPositionEnd();
        }
    }
    
    public String getText() {
        return this.text;
    }
    
    public String getSelectedText() {
        return this.text.substring((this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd, (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition);
    }
    
    public void func_175205_a(final Predicate field_175209_y) {
        this.field_175209_y = field_175209_y;
    }
    
    public void writeText(final String s) {
        String string = "";
        final String filterAllowedCharacters = ChatAllowedCharacters.filterAllowedCharacters(s);
        final int n = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
        final int n2 = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
        final int n3 = this.maxStringLength - this.text.length() - (n - n2);
        if (this.text.length() > 0) {
            string = String.valueOf(string) + this.text.substring(0, n);
        }
        String text;
        int length;
        if (n3 < filterAllowedCharacters.length()) {
            text = String.valueOf(string) + filterAllowedCharacters.substring(0, n3);
            length = n3;
        }
        else {
            text = String.valueOf(string) + filterAllowedCharacters;
            length = filterAllowedCharacters.length();
        }
        if (this.text.length() > 0 && n2 < this.text.length()) {
            text = String.valueOf(text) + this.text.substring(n2);
        }
        if (this.field_175209_y.apply(text)) {
            this.text = text;
            this.moveCursorBy(n - this.selectionEnd + length);
            if (this.field_175210_x != null) {
                this.field_175210_x.func_175319_a(this.field_175208_g, this.text);
            }
        }
    }
    
    public void deleteWords(final int n) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            }
            else {
                this.deleteFromCursor(this.getNthWordFromCursor(n) - this.cursorPosition);
            }
        }
    }
    
    public void deleteFromCursor(final int n) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            }
            else {
                final boolean b = n < 0;
                final int n2 = b ? (this.cursorPosition + n) : this.cursorPosition;
                final int n3 = b ? this.cursorPosition : (this.cursorPosition + n);
                String text = "";
                if (n2 >= 0) {
                    text = this.text.substring(0, n2);
                }
                if (n3 < this.text.length()) {
                    text = String.valueOf(text) + this.text.substring(n3);
                }
                this.text = text;
                if (b) {
                    this.moveCursorBy(n);
                }
                if (this.field_175210_x != null) {
                    this.field_175210_x.func_175319_a(this.field_175208_g, this.text);
                }
            }
        }
    }
    
    public int func_175206_d() {
        return this.field_175208_g;
    }
    
    public int getNthWordFromCursor(final int n) {
        return this.getNthWordFromPos(n, this.getCursorPosition());
    }
    
    public int getNthWordFromPos(final int n, final int n2) {
        return this.func_146197_a(n, n2, true);
    }
    
    public int func_146197_a(final int n, final int n2, final boolean b) {
        int i = n2;
        final boolean b2 = n < 0;
        while (0 < Math.abs(n)) {
            if (b2) {
                while (b && i > 0) {
                    if (this.text.charAt(i - 1) != ' ') {
                        break;
                    }
                    --i;
                }
                while (i > 0) {
                    if (this.text.charAt(i - 1) == ' ') {
                        break;
                    }
                    --i;
                }
            }
            else {
                final int length = this.text.length();
                i = this.text.indexOf(32, i);
                if (i == -1) {
                    i = length;
                }
                else {
                    while (b && i < length && this.text.charAt(i) == ' ') {
                        ++i;
                    }
                }
            }
            int n3 = 0;
            ++n3;
        }
        return i;
    }
    
    public void moveCursorBy(final int n) {
        this.setCursorPosition(this.selectionEnd + n);
    }
    
    public void setCursorPosition(final int cursorPosition) {
        this.cursorPosition = cursorPosition;
        this.setSelectionPos(this.cursorPosition = MathHelper.clamp_int(this.cursorPosition, 0, this.text.length()));
    }
    
    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }
    
    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }
    
    public boolean textboxKeyTyped(final char c, final int n) {
        if (!this.isFocused) {
            return false;
        }
        if (GuiScreen.func_175278_g(n)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
        }
        if (GuiScreen.func_175280_f(n)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            return true;
        }
        if (GuiScreen.func_175279_e(n)) {
            if (this.isEnabled) {
                this.writeText(GuiScreen.getClipboardString());
            }
            return true;
        }
        if (GuiScreen.func_175277_d(n)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            if (this.isEnabled) {
                this.writeText("");
            }
            return true;
        }
        switch (n) {
            case 14: {
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled) {
                        this.deleteWords(-1);
                    }
                }
                else if (this.isEnabled) {
                    this.deleteFromCursor(-1);
                }
                return true;
            }
            case 199: {
                if (GuiScreen.isShiftKeyDown()) {
                    this.setSelectionPos(0);
                }
                else {
                    this.setCursorPositionZero();
                }
                return true;
            }
            case 203: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                    }
                    else {
                        this.setSelectionPos(this.getSelectionEnd() - 1);
                    }
                }
                else if (GuiScreen.isCtrlKeyDown()) {
                    this.setCursorPosition(this.getNthWordFromCursor(-1));
                }
                else {
                    this.moveCursorBy(-1);
                }
                return true;
            }
            case 205: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                    }
                    else {
                        this.setSelectionPos(this.getSelectionEnd() + 1);
                    }
                }
                else if (GuiScreen.isCtrlKeyDown()) {
                    this.setCursorPosition(this.getNthWordFromCursor(1));
                }
                else {
                    this.moveCursorBy(1);
                }
                return true;
            }
            case 207: {
                if (GuiScreen.isShiftKeyDown()) {
                    this.setSelectionPos(this.text.length());
                }
                else {
                    this.setCursorPositionEnd();
                }
                return true;
            }
            case 211: {
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled) {
                        this.deleteWords(1);
                    }
                }
                else if (this.isEnabled) {
                    this.deleteFromCursor(1);
                }
                return true;
            }
            default: {
                if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                    if (this.isEnabled) {
                        this.writeText(Character.toString(c));
                    }
                    return true;
                }
                return false;
            }
        }
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
        final boolean focused = n >= this.xPosition && n < this.xPosition + GuiTextFields.width && n2 >= this.yPosition && n2 < this.yPosition + GuiTextFields.height;
        if (this.canLoseFocus) {
            this.setFocused(focused);
        }
        if (this.isFocused && focused && n3 == 0) {
            int n4 = n - this.xPosition;
            if (this.enableBackgroundDrawing) {
                n4 -= 4;
            }
            this.setCursorPosition(this.fontRendererInstance.trimStringToWidth(this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth()), n4).length() + this.lineScrollOffset);
        }
    }
    
    public void drawTextBox() {
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                Gui.drawRect(this.xPosition - 1, this.yPosition - 1, this.xPosition + GuiTextFields.width + 1, this.yPosition + GuiTextFields.height + 1, Color.DARK_GRAY.getRGB());
                Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + GuiTextFields.width, this.yPosition + GuiTextFields.height, -24536);
            }
            final int n = this.isEnabled ? this.enabledColor : this.disabledColor;
            final int n2 = this.cursorPosition - this.lineScrollOffset;
            int length = this.selectionEnd - this.lineScrollOffset;
            final String trimStringToWidth = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            final boolean b = n2 >= 0 && n2 <= trimStringToWidth.length();
            final boolean b2 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && b;
            final int n3 = this.enableBackgroundDrawing ? (this.xPosition + 4) : this.xPosition;
            final int n4 = this.enableBackgroundDrawing ? (this.yPosition + (GuiTextFields.height - 8) / 2) : this.yPosition;
            int func_175063_a = n3;
            if (length > trimStringToWidth.length()) {
                length = trimStringToWidth.length();
            }
            if (trimStringToWidth.length() > 0) {
                func_175063_a = this.fontRendererInstance.func_175063_a(b ? trimStringToWidth.substring(0, n2) : trimStringToWidth, (float)n3, (float)n4, n);
            }
            final boolean b3 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int n5 = func_175063_a;
            if (!b) {
                n5 = ((n2 > 0) ? (n3 + GuiTextFields.width) : n3);
            }
            else if (b3) {
                n5 = func_175063_a - 1;
                --func_175063_a;
            }
            if (trimStringToWidth.length() > 0 && b && n2 < trimStringToWidth.length()) {
                this.fontRendererInstance.func_175063_a(trimStringToWidth.substring(n2), (float)func_175063_a, (float)n4, n);
            }
            if (b2) {
                if (b3) {
                    Gui.drawRect(n5, n4 - 1, n5 + 1, n4 + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
                }
                else {
                    this.fontRendererInstance.func_175063_a("§l>", (float)n5, (float)n4, n);
                }
            }
            if (length != n2) {
                this.drawCursorVertical(n5, n4 - 1, n3 + this.fontRendererInstance.getStringWidth(trimStringToWidth.substring(0, length)) - 1, n4 + 1 + this.fontRendererInstance.FONT_HEIGHT);
            }
        }
    }
    
    private void drawCursorVertical(int n, int n2, int n3, int n4) {
        if (n < n3) {
            final int n5 = n;
            n = n3;
            n3 = n5;
        }
        if (n2 < n4) {
            final int n6 = n2;
            n2 = n4;
            n4 = n6;
        }
        if (n3 > this.xPosition + GuiTextFields.width) {
            n3 = this.xPosition + GuiTextFields.width;
        }
        if (n > this.xPosition + GuiTextFields.width) {
            n = this.xPosition + GuiTextFields.width;
        }
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.color(0.0f, 0.0f, 255.0f, 255.0f);
        GlStateManager.colorLogicOp(5387);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(n, n4, 0.0);
        worldRenderer.addVertex(n3, n4, 0.0);
        worldRenderer.addVertex(n3, n2, 0.0);
        worldRenderer.addVertex(n, n2, 0.0);
        instance.draw();
    }
    
    public void setMaxStringLength(final int maxStringLength) {
        this.maxStringLength = maxStringLength;
        if (this.text.length() > maxStringLength) {
            this.text = this.text.substring(0, maxStringLength);
        }
    }
    
    public int getMaxStringLength() {
        return this.maxStringLength;
    }
    
    public int getCursorPosition() {
        return this.cursorPosition;
    }
    
    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }
    
    public void setEnableBackgroundDrawing(final boolean enableBackgroundDrawing) {
        this.enableBackgroundDrawing = enableBackgroundDrawing;
    }
    
    public void setTextColor(final int enabledColor) {
        this.enabledColor = enabledColor;
    }
    
    public void setDisabledTextColour(final int disabledColor) {
        this.disabledColor = disabledColor;
    }
    
    public void setFocused(final boolean isFocused) {
        if (isFocused && !this.isFocused) {
            this.cursorCounter = 0;
        }
        this.isFocused = isFocused;
    }
    
    public boolean isFocused() {
        return this.isFocused;
    }
    
    public void setEnabled(final boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    
    public int getSelectionEnd() {
        return this.selectionEnd;
    }
    
    public int getWidth() {
        return this.getEnableBackgroundDrawing() ? (GuiTextFields.width - 8) : GuiTextFields.width;
    }
    
    public void setSelectionPos(int n) {
        final int length = this.text.length();
        if (0 > length) {
            n = length;
        }
        this.selectionEnd = 0;
        if (this.fontRendererInstance != null) {
            if (this.lineScrollOffset > length) {
                this.lineScrollOffset = length;
            }
            final int width = this.getWidth();
            final int n2 = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), width).length() + this.lineScrollOffset;
            if (0 == this.lineScrollOffset) {
                this.lineScrollOffset -= this.fontRendererInstance.trimStringToWidth(this.text, width, true).length();
            }
            if (0 > n2) {
                this.lineScrollOffset += 0 - n2;
            }
            else if (0 <= this.lineScrollOffset) {
                this.lineScrollOffset -= this.lineScrollOffset - 0;
            }
            this.lineScrollOffset = MathHelper.clamp_int(this.lineScrollOffset, 0, length);
        }
    }
    
    public void setCanLoseFocus(final boolean canLoseFocus) {
        this.canLoseFocus = canLoseFocus;
    }
    
    public boolean getVisible() {
        return this.visible;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public void setPresetString(final String presetString) {
        this.presetString = presetString;
    }
    
    static {
        __OBFID = "CL_00000670";
    }
}
