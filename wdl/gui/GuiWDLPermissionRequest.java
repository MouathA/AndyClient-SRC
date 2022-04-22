package wdl.gui;

import wdl.chan.*;
import net.minecraft.client.resources.*;
import java.util.*;
import java.io.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;

public class GuiWDLPermissionRequest extends GuiScreen
{
    private List fields;
    private int nextFieldButtonY;
    private TextList list;
    private final GuiScreen parent;
    private GuiTextField requestField;
    private GuiButton submitButton;
    
    public GuiWDLPermissionRequest(final GuiScreen parent) {
        this.parent = parent;
        this.nextFieldButtonY = 67;
        this.fields = new ArrayList();
        final Iterator<String> iterator = WDLPluginChannels.BOOLEAN_REQUEST_FIELDS.iterator();
        while (iterator.hasNext()) {
            this.fields.add(new BoolFieldButton(iterator.next()));
        }
    }
    
    @Override
    public void initGui() {
        (this.list = new TextList(GuiWDLPermissionRequest.mc, GuiWDLPermissionRequest.width, GuiWDLPermissionRequest.height, 61, 32)).addLine("§c§lThis is a work in progress.");
        this.list.addLine("You can request permissions in this GUI, although it currently requires manually specifying the names.");
        this.list.addBlankLine();
        this.list.addLine("Boolean fields: " + WDLPluginChannels.BOOLEAN_REQUEST_FIELDS);
        this.list.addLine("Integer fields: " + WDLPluginChannels.INTEGER_REQUEST_FIELDS);
        this.list.addBlankLine();
        this.requestField = new GuiTextField(0, this.fontRendererObj, GuiWDLPermissionRequest.width / 2 - 155, 18, 150, 20);
        this.submitButton = new GuiButton(1, GuiWDLPermissionRequest.width / 2 + 5, 18, 150, 20, "Submit request");
        this.submitButton.enabled = !WDLPluginChannels.getRequests().isEmpty();
        this.buttonList.add(this.submitButton);
        this.buttonList.add(new GuiButton(100, GuiWDLPermissionRequest.width / 2 - 100, GuiWDLPermissionRequest.height - 29, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(new GuiButton(200, GuiWDLPermissionRequest.width / 2 - 155, 39, 100, 20, I18n.format("wdl.gui.permissions.current", new Object[0])));
        this.buttonList.add(new GuiButton(201, GuiWDLPermissionRequest.width / 2 - 50, 39, 100, 20, I18n.format("wdl.gui.permissions.request", new Object[0])));
        this.buttonList.add(new GuiButton(202, GuiWDLPermissionRequest.width / 2 + 55, 39, 100, 20, I18n.format("wdl.gui.permissions.overrides", new Object[0])));
        this.buttonList.addAll(this.fields);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            WDLPluginChannels.sendRequests("Request reason not yet implemented clientside");
            guiButton.displayString = "Submitted!";
        }
        if (guiButton.id == 100) {
            GuiWDLPermissionRequest.mc.displayGuiScreen(this.parent);
        }
        if (guiButton.id == 200) {
            GuiWDLPermissionRequest.mc.displayGuiScreen(new GuiWDLPermissions(this.parent));
        }
        final int id = guiButton.id;
        if (guiButton.id == 202) {
            GuiWDLPermissionRequest.mc.displayGuiScreen(new GuiWDLChunkOverrides(this.parent));
        }
    }
    
    @Override
    public void updateScreen() {
        this.requestField.updateCursorCounter();
        super.updateScreen();
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.requestField.mouseClicked(n, n2, n3);
        this.list.func_148179_a(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        this.requestField.textboxKeyTyped(c, n);
        if (this.requestField.isFocused()) {
            final String text = this.requestField.getText();
            if (text.contains("=")) {
                final String[] split = text.split("=", 2);
                if (split.length == 2) {
                    final String s = split[0];
                    final String s2 = split[1];
                    WDLPluginChannels.isValidRequest(s, s2);
                    if (false && n == 28) {
                        this.requestField.setText("");
                        WDLPluginChannels.addRequest(s, s2);
                        this.list.addLine("Requesting '" + s + "' to be '" + s2 + "'.");
                        this.submitButton.enabled = true;
                    }
                }
            }
            this.requestField.setTextColor(false ? 4251712 : 14696512);
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.func_178039_p();
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (this.list.func_148181_b(n, n2, n3)) {
            return;
        }
        super.mouseReleased(n, n2, n3);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.list == null) {
            return;
        }
        this.list.drawScreen(n, n2, n3);
        this.requestField.drawTextBox();
        Gui.drawCenteredString(this.fontRendererObj, "Permission request", GuiWDLPermissionRequest.width / 2, 8, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    static int access$0(final GuiWDLPermissionRequest guiWDLPermissionRequest) {
        return guiWDLPermissionRequest.nextFieldButtonY;
    }
    
    static void access$1(final GuiWDLPermissionRequest guiWDLPermissionRequest, final int nextFieldButtonY) {
        guiWDLPermissionRequest.nextFieldButtonY = nextFieldButtonY;
    }
    
    private class BoolFieldButton extends GuiButton
    {
        private Boolean value;
        private final String field;
        final GuiWDLPermissionRequest this$0;
        
        public BoolFieldButton(final GuiWDLPermissionRequest this$0, final String field) {
            this.this$0 = this$0;
            super(-1, 0, GuiWDLPermissionRequest.access$0(this$0), null);
            this.value = null;
            final String request = WDLPluginChannels.getRequest(field);
            if (request == null) {
                this.value = null;
            }
            else {
                this.value = Boolean.parseBoolean(request);
            }
            this.displayString = String.valueOf(field) + ": " + this.value;
            GuiWDLPermissionRequest.access$1(this$0, GuiWDLPermissionRequest.access$0(this$0) + 22);
            this.field = field;
        }
        
        @Override
        public void drawButton(final Minecraft minecraft, final int n, final int n2) {
            this.xPosition = GuiWDLPermissionRequest.width / 2 - this.width / 2;
            super.drawButton(minecraft, n, n2);
        }
        
        @Override
        public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
            if (super.mousePressed(minecraft, n, n2)) {
                if (this.value == null) {
                    this.value = true;
                }
                else if (this.value) {
                    this.value = false;
                }
                else if (!this.value) {
                    this.value = null;
                }
                this.displayString = String.valueOf(this.field) + ": " + this.value;
                if (this.value != null) {
                    WDLPluginChannels.addRequest(this.field, new StringBuilder().append(this.value).toString());
                }
                else {
                    WDLPluginChannels.removeRequest(this.field);
                }
                return true;
            }
            return false;
        }
    }
}
