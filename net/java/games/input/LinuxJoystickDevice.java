package net.java.games.input;

import java.util.*;
import java.io.*;

final class LinuxJoystickDevice implements LinuxDevice
{
    private final long fd;
    private final String name;
    private final LinuxJoystickEvent joystick_event;
    private final Event event;
    private final LinuxJoystickButton[] buttons;
    private final LinuxJoystickAxis[] axes;
    private final Map povXs;
    private final Map povYs;
    private final byte[] axisMap;
    private final char[] buttonMap;
    private EventQueue event_queue;
    private boolean closed;
    
    public LinuxJoystickDevice(final String s) throws IOException {
        this.joystick_event = new LinuxJoystickEvent();
        this.event = new Event();
        this.povXs = new HashMap();
        this.povYs = new HashMap();
        this.fd = nOpen(s);
        this.name = this.getDeviceName();
        this.setBufferSize(32);
        this.buttons = new LinuxJoystickButton[this.getNumDeviceButtons()];
        this.axes = new LinuxJoystickAxis[this.getNumDeviceAxes()];
        this.axisMap = this.getDeviceAxisMap();
        this.buttonMap = this.getDeviceButtonMap();
    }
    
    private static final native long nOpen(final String p0) throws IOException;
    
    public final synchronized void setBufferSize(final int n) {
        this.event_queue = new EventQueue(n);
    }
    
    private final void processEvent(final LinuxJoystickEvent linuxJoystickEvent) {
        final int number = linuxJoystickEvent.getNumber();
        switch (linuxJoystickEvent.getType() & 0xFFFFFF7F) {
            case 1: {
                if (number < this.getNumButtons()) {
                    final LinuxJoystickButton linuxJoystickButton = this.buttons[number];
                    if (linuxJoystickButton != null) {
                        final float value = (float)linuxJoystickEvent.getValue();
                        linuxJoystickButton.setValue(value);
                        this.event.set(linuxJoystickButton, value, linuxJoystickEvent.getNanos());
                        break;
                    }
                }
                return;
            }
            case 2: {
                if (number < this.getNumAxes()) {
                    final LinuxJoystickAxis linuxJoystickAxis = this.axes[number];
                    if (linuxJoystickAxis != null) {
                        final float value2 = linuxJoystickEvent.getValue() / 32767.0f;
                        linuxJoystickAxis.setValue(value2);
                        if (this.povXs.containsKey(new Integer(number))) {
                            final LinuxJoystickPOV linuxJoystickPOV = this.povXs.get(new Integer(number));
                            linuxJoystickPOV.updateValue();
                            this.event.set(linuxJoystickPOV, linuxJoystickPOV.getPollData(), linuxJoystickEvent.getNanos());
                            break;
                        }
                        if (this.povYs.containsKey(new Integer(number))) {
                            final LinuxJoystickPOV linuxJoystickPOV2 = this.povYs.get(new Integer(number));
                            linuxJoystickPOV2.updateValue();
                            this.event.set(linuxJoystickPOV2, linuxJoystickPOV2.getPollData(), linuxJoystickEvent.getNanos());
                            break;
                        }
                        this.event.set(linuxJoystickAxis, value2, linuxJoystickEvent.getNanos());
                        break;
                    }
                }
                return;
            }
            default: {
                return;
            }
        }
        if (!this.event_queue.isFull()) {
            this.event_queue.add(this.event);
        }
    }
    
    public final void registerAxis(final int n, final LinuxJoystickAxis linuxJoystickAxis) {
        this.axes[n] = linuxJoystickAxis;
    }
    
    public final void registerButton(final int n, final LinuxJoystickButton linuxJoystickButton) {
        this.buttons[n] = linuxJoystickButton;
    }
    
    public final void registerPOV(final LinuxJoystickPOV linuxJoystickPOV) {
        final LinuxJoystickAxis yAxis = linuxJoystickPOV.getYAxis();
        final LinuxJoystickAxis xAxis = linuxJoystickPOV.getXAxis();
        while (0 < this.axes.length) {
            if (this.axes[0] == yAxis) {
                break;
            }
            int n = 0;
            ++n;
        }
        while (0 < this.axes.length && this.axes[0] != xAxis) {
            int n2 = 0;
            ++n2;
        }
        this.povXs.put(new Integer(0), linuxJoystickPOV);
        this.povYs.put(new Integer(0), linuxJoystickPOV);
    }
    
    public final synchronized boolean getNextEvent(final Event event) throws IOException {
        return this.event_queue.getNextEvent(event);
    }
    
    public final synchronized void poll() throws IOException {
        this.checkClosed();
        while (this.getNextDeviceEvent(this.joystick_event)) {
            this.processEvent(this.joystick_event);
        }
    }
    
    private final boolean getNextDeviceEvent(final LinuxJoystickEvent linuxJoystickEvent) throws IOException {
        return nGetNextEvent(this.fd, linuxJoystickEvent);
    }
    
    private static final native boolean nGetNextEvent(final long p0, final LinuxJoystickEvent p1) throws IOException;
    
    public final int getNumAxes() {
        return this.axes.length;
    }
    
    public final int getNumButtons() {
        return this.buttons.length;
    }
    
    public final byte[] getAxisMap() {
        return this.axisMap;
    }
    
    public final char[] getButtonMap() {
        return this.buttonMap;
    }
    
    private final int getNumDeviceButtons() throws IOException {
        return nGetNumButtons(this.fd);
    }
    
    private static final native int nGetNumButtons(final long p0) throws IOException;
    
    private final int getNumDeviceAxes() throws IOException {
        return nGetNumAxes(this.fd);
    }
    
    private static final native int nGetNumAxes(final long p0) throws IOException;
    
    private final byte[] getDeviceAxisMap() throws IOException {
        return nGetAxisMap(this.fd);
    }
    
    private static final native byte[] nGetAxisMap(final long p0) throws IOException;
    
    private final char[] getDeviceButtonMap() throws IOException {
        return nGetButtonMap(this.fd);
    }
    
    private static final native char[] nGetButtonMap(final long p0) throws IOException;
    
    private final int getVersion() throws IOException {
        return nGetVersion(this.fd);
    }
    
    private static final native int nGetVersion(final long p0) throws IOException;
    
    public final String getName() {
        return this.name;
    }
    
    private final String getDeviceName() throws IOException {
        return nGetName(this.fd);
    }
    
    private static final native String nGetName(final long p0) throws IOException;
    
    public final synchronized void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            nClose(this.fd);
        }
    }
    
    private static final native void nClose(final long p0) throws IOException;
    
    private final void checkClosed() throws IOException {
        if (this.closed) {
            throw new IOException("Device is closed");
        }
    }
    
    protected void finalize() throws IOException {
        this.close();
    }
}
