package net.java.games.input;

import java.io.*;
import java.util.*;

public class WinTabComponent extends AbstractComponent
{
    private int min;
    private int max;
    protected float lastKnownValue;
    private boolean analog;
    static Class class$net$java$games$input$Component$Identifier$Button;
    
    protected WinTabComponent(final WinTabContext winTabContext, final int n, final String s, final Component.Identifier identifier, final int min, final int max) {
        super(s, identifier);
        this.min = min;
        this.max = max;
        this.analog = true;
    }
    
    protected WinTabComponent(final WinTabContext winTabContext, final int n, final String s, final Component.Identifier identifier) {
        super(s, identifier);
        this.min = 0;
        this.max = 1;
        this.analog = false;
    }
    
    protected float poll() throws IOException {
        return this.lastKnownValue;
    }
    
    public boolean isAnalog() {
        return this.analog;
    }
    
    public boolean isRelative() {
        return false;
    }
    
    public static List createComponents(final WinTabContext winTabContext, final int n, final int n2, final int[] array) {
        final ArrayList<WinTabComponent> list = new ArrayList<WinTabComponent>();
        switch (n2) {
            case 1: {
                final Component.Identifier.Axis x = Component.Identifier.Axis.X;
                list.add(new WinTabComponent(winTabContext, n, x.getName(), x, array[0], array[1]));
                break;
            }
            case 2: {
                final Component.Identifier.Axis y = Component.Identifier.Axis.Y;
                list.add(new WinTabComponent(winTabContext, n, y.getName(), y, array[0], array[1]));
                break;
            }
            case 3: {
                final Component.Identifier.Axis z = Component.Identifier.Axis.Z;
                list.add(new WinTabComponent(winTabContext, n, z.getName(), z, array[0], array[1]));
                break;
            }
            case 4: {
                final Component.Identifier.Axis x_FORCE = Component.Identifier.Axis.X_FORCE;
                list.add(new WinTabComponent(winTabContext, n, x_FORCE.getName(), x_FORCE, array[0], array[1]));
                break;
            }
            case 5: {
                final Component.Identifier.Axis y_FORCE = Component.Identifier.Axis.Y_FORCE;
                list.add(new WinTabComponent(winTabContext, n, y_FORCE.getName(), y_FORCE, array[0], array[1]));
                break;
            }
            case 6: {
                final Component.Identifier.Axis rx = Component.Identifier.Axis.RX;
                list.add(new WinTabComponent(winTabContext, n, rx.getName(), rx, array[0], array[1]));
                final Component.Identifier.Axis ry = Component.Identifier.Axis.RY;
                list.add(new WinTabComponent(winTabContext, n, ry.getName(), ry, array[2], array[3]));
                final Component.Identifier.Axis rz = Component.Identifier.Axis.RZ;
                list.add(new WinTabComponent(winTabContext, n, rz.getName(), rz, array[4], array[5]));
                break;
            }
            case 7: {
                final Component.Identifier.Axis rx2 = Component.Identifier.Axis.RX;
                list.add(new WinTabComponent(winTabContext, n, rx2.getName(), rx2, array[0], array[1]));
                final Component.Identifier.Axis ry2 = Component.Identifier.Axis.RY;
                list.add(new WinTabComponent(winTabContext, n, ry2.getName(), ry2, array[2], array[3]));
                final Component.Identifier.Axis rz2 = Component.Identifier.Axis.RZ;
                list.add(new WinTabComponent(winTabContext, n, rz2.getName(), rz2, array[4], array[5]));
                break;
            }
        }
        return list;
    }
    
    public static Collection createButtons(final WinTabContext winTabContext, final int n, final int n2) {
        final ArrayList<WinTabButtonComponent> list = new ArrayList<WinTabButtonComponent>();
        while (0 < n2) {
            final Component.Identifier identifier = (Component.Identifier)((WinTabComponent.class$net$java$games$input$Component$Identifier$Button == null) ? (WinTabComponent.class$net$java$games$input$Component$Identifier$Button = class$("net.java.games.input.Component$Identifier$Button")) : WinTabComponent.class$net$java$games$input$Component$Identifier$Button).getField("_" + 0).get(null);
            list.add(new WinTabButtonComponent(winTabContext, n, identifier.getName(), identifier, 0));
            int n3 = 0;
            ++n3;
        }
        return list;
    }
    
    public Event processPacket(final WinTabPacket winTabPacket) {
        float lastKnownValue = this.lastKnownValue;
        if (this.getIdentifier() == Component.Identifier.Axis.X) {
            lastKnownValue = this.normalise((float)winTabPacket.PK_X);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.Y) {
            lastKnownValue = this.normalise((float)winTabPacket.PK_Y);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.Z) {
            lastKnownValue = this.normalise((float)winTabPacket.PK_Z);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.X_FORCE) {
            lastKnownValue = this.normalise((float)winTabPacket.PK_NORMAL_PRESSURE);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.Y_FORCE) {
            lastKnownValue = this.normalise((float)winTabPacket.PK_TANGENT_PRESSURE);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.RX) {
            lastKnownValue = this.normalise((float)winTabPacket.PK_ORIENTATION_ALT);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.RY) {
            lastKnownValue = this.normalise((float)winTabPacket.PK_ORIENTATION_AZ);
        }
        if (this.getIdentifier() == Component.Identifier.Axis.RZ) {
            lastKnownValue = this.normalise((float)winTabPacket.PK_ORIENTATION_TWIST);
        }
        if (lastKnownValue != this.getPollData()) {
            this.lastKnownValue = lastKnownValue;
            final Event event = new Event();
            event.set(this, lastKnownValue, winTabPacket.PK_TIME * 1000L);
            return event;
        }
        return null;
    }
    
    private float normalise(final float n) {
        if (this.max == this.min) {
            return n;
        }
        return (n - this.min) / (this.max - this.min);
    }
    
    public static Collection createCursors(final WinTabContext winTabContext, final int n, final String[] array) {
        final ArrayList<WinTabCursorComponent> list = new ArrayList<WinTabCursorComponent>();
        while (0 < array.length) {
            Component.Identifier.Button button;
            if (array[0].matches("Puck")) {
                button = Component.Identifier.Button.TOOL_FINGER;
            }
            else if (array[0].matches("Eraser.*")) {
                button = Component.Identifier.Button.TOOL_RUBBER;
            }
            else {
                button = Component.Identifier.Button.TOOL_PEN;
            }
            list.add(new WinTabCursorComponent(winTabContext, n, button.getName(), button, 0));
            int n2 = 0;
            ++n2;
        }
        return list;
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
}
