package Mood.Gui;

import Mood.Matrix.DefaultParticles.*;
import Mood.Designs.MainMenuDes.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.net.*;

public class GuiPortScanner extends GuiScreen
{
    private final GuiScreen prevGui;
    public ParticleEngine pe;
    private GuiTextFields hostField;
    private GuiTextFields minPortField;
    private GuiTextFields maxPortField;
    private GuiTextFields threadsField;
    private MButton buttonToggle;
    private boolean running;
    private String status;
    private String host;
    private int currentPort;
    private int maxPort;
    private int minPort;
    private int checkedPort;
    private final List ports;
    public static float animatedMouseX;
    public static float animatedMouseY;
    
    public GuiPortScanner(final GuiScreen prevGui) {
        this.pe = new ParticleEngine();
        this.status = "§7V\u00e1rakoz\u00e1s...";
        this.ports = new ArrayList();
        this.prevGui = prevGui;
    }
    
    @Override
    public void initGui() {
        this.pe.particles.clear();
        Keyboard.enableRepeatEvents(true);
        (this.hostField = new GuiTextFields(0, this.fontRendererObj, GuiPortScanner.width / 2 - 100, 60, 200, 20)).setFocused(true);
        this.hostField.setMaxStringLength(Integer.MAX_VALUE);
        this.hostField.setText("localhost");
        (this.minPortField = new GuiTextFields(1, this.fontRendererObj, GuiPortScanner.width / 2 - 100, 90, 90, 20)).setMaxStringLength(5);
        this.minPortField.setText(String.valueOf(1));
        (this.maxPortField = new GuiTextFields(2, this.fontRendererObj, GuiPortScanner.width / 2 + 10, 90, 90, 20)).setMaxStringLength(5);
        this.maxPortField.setText(String.valueOf(65535));
        (this.threadsField = new GuiTextFields(3, this.fontRendererObj, GuiPortScanner.width / 2 - 100, 90, 90, 20)).setMaxStringLength(Integer.MAX_VALUE);
        this.threadsField.setText(String.valueOf(500));
        this.buttonList.add(this.buttonToggle = new MButton(1, GuiPortScanner.width / 2 - 100, GuiPortScanner.height / 4 + 95, this.running ? "Stop" : "Start"));
        this.buttonList.add(new MButton(0, GuiPortScanner.width / 2 - 100, GuiPortScanner.height / 4 + 120, "Vissza"));
        this.buttonList.add(new MButton(2, GuiPortScanner.width / 2 - 100, GuiPortScanner.height / 4 + 155, "Export\u00e1l\u00e1s"));
        super.initGui();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        GuiPortScanner.mc.getTextureManager().bindTexture(new ResourceLocation("Jello/hatterkep.jpg"));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final ScaledResolution scaledResolution = new ScaledResolution(GuiPortScanner.mc, GuiPortScanner.mc.displayWidth, GuiPortScanner.mc.displayHeight);
        Gui.drawModalRectWithCustomSizedTexture(-960.0f - GuiPortScanner.animatedMouseX + ScaledResolution.getScaledWidth(), -9.0f - GuiPortScanner.animatedMouseY / 9.5f + ScaledResolution.getScaledHeight() / 19 - 19.0f, 0.0f, 0.0f, 1920.0f, 597.0f, 1920.0f, 597.0f);
        Gui.drawCenteredString(this.fontRendererObj, "Port Scanner", GuiPortScanner.width / 2, 34, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, this.running ? ("§a" + this.checkedPort + " §f/ §c" + this.maxPort) : ((this.status == null) ? "" : this.status), GuiPortScanner.width / 2, GuiPortScanner.height / 4 + 80, 16777215);
        this.buttonToggle.displayString = (this.running ? "Stop" : "Start");
        this.hostField.drawTextBox();
        this.minPortField.drawTextBox();
        this.maxPortField.drawTextBox();
        this.threadsField.drawTextBox();
        this.drawString(this.fontRendererObj, "§7Portok", 2, 2, Color.WHITE.hashCode());
        // monitorenter(ports = this.ports)
        final Iterator<Integer> iterator = this.ports.iterator();
        while (iterator.hasNext()) {
            this.drawString(this.fontRendererObj, String.valueOf(iterator.next()), 2, 12, Color.WHITE.hashCode());
            final int n4 = 12 + this.fontRendererObj.FONT_HEIGHT;
        }
        // monitorexit(ports)
        this.pe.render(n3, n3);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0: {
                GuiPortScanner.mc.displayGuiScreen(this.prevGui);
                break;
            }
            case 1: {
                if (this.running) {
                    this.running = false;
                }
                else {
                    this.host = this.hostField.getText();
                    if (this.host.isEmpty()) {
                        this.status = "host";
                        return;
                    }
                    this.minPort = Integer.parseInt(this.minPortField.getText());
                    this.maxPort = Integer.parseInt(this.maxPortField.getText());
                    final int int1 = Integer.parseInt(this.threadsField.getText());
                    this.ports.clear();
                    this.currentPort = this.minPort - 1;
                    this.checkedPort = this.minPort;
                    while (0 < int1) {
                        new Thread(this::lambda$0).start();
                        int n = 0;
                        ++n;
                    }
                    this.running = true;
                }
                this.buttonToggle.displayString = (this.running ? "Stop" : "Start");
                break;
            }
            case 2: {
                final File saveFileChooser = this.saveFileChooser();
                if (saveFileChooser == null || saveFileChooser.isDirectory()) {
                    return;
                }
                if (!saveFileChooser.exists()) {
                    saveFileChooser.createNewFile();
                }
                final FileWriter fileWriter = new FileWriter(saveFileChooser);
                fileWriter.write("Portscan\r\n");
                fileWriter.write("Host: " + this.host + "\r\n\r\n");
                fileWriter.write("Ports (" + this.minPort + " - " + this.maxPort + "):\r\n");
                final Iterator<Integer> iterator = (Iterator<Integer>)this.ports.iterator();
                while (iterator.hasNext()) {
                    fileWriter.write(iterator.next() + "\r\n");
                }
                fileWriter.flush();
                fileWriter.close();
                JOptionPane.showMessageDialog(null, "Az exportalas sikeresen megtortent!", "Port Scanner", 1);
                break;
            }
        }
        super.actionPerformed(guiButton);
    }
    
    public File saveFileChooser() {
        if (GuiPortScanner.mc.isFullScreen()) {
            GuiPortScanner.mc.toggleFullscreen();
        }
        final JFileChooser fileChooser = new JFileChooser();
        final JFrame frame = new JFrame();
        fileChooser.setFileSelectionMode(0);
        frame.setVisible(true);
        frame.toFront();
        frame.setVisible(false);
        final int showSaveDialog = fileChooser.showSaveDialog(frame);
        frame.dispose();
        return (showSaveDialog == 0) ? fileChooser.getSelectedFile() : null;
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (1 == n) {
            GuiPortScanner.mc.displayGuiScreen(this.prevGui);
            return;
        }
        if (this.running) {
            return;
        }
        if (this.hostField.isFocused()) {
            this.hostField.textboxKeyTyped(c, n);
        }
        if (this.minPortField.isFocused() && !Character.isLetter(c)) {
            this.minPortField.textboxKeyTyped(c, n);
        }
        if (this.maxPortField.isFocused() && !Character.isLetter(c)) {
            this.maxPortField.textboxKeyTyped(c, n);
        }
        if (this.threadsField.isFocused() && !Character.isLetter(c)) {
            this.threadsField.textboxKeyTyped(c, n);
        }
        super.keyTyped(c, n);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.hostField.mouseClicked(n, n2, n3);
        this.minPortField.mouseClicked(n, n2, n3);
        this.maxPortField.mouseClicked(n, n2, n3);
        this.threadsField.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void updateScreen() {
        this.hostField.updateCursorCounter();
        this.minPortField.updateCursorCounter();
        this.maxPortField.updateCursorCounter();
        this.threadsField.updateCursorCounter();
        super.updateScreen();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.running = false;
        super.onGuiClosed();
    }
    
    private void lambda$0() {
        while (this.running && this.currentPort < this.maxPort) {
            final int checkedPort = ++this.currentPort;
            final Socket socket = new Socket();
            socket.connect(new InetSocketAddress(this.host, checkedPort), 500);
            socket.close();
            // monitorenter(ports = this.ports)
            if (!this.ports.contains(checkedPort)) {
                this.ports.add(checkedPort);
            }
            // monitorexit(ports)
            if (this.checkedPort < checkedPort) {
                this.checkedPort = checkedPort;
            }
        }
        this.running = false;
        this.buttonToggle.displayString = "§3§lStart";
    }
}
