package net.java.games.input;

import java.io.*;

class RawMouseInfo extends RawDeviceInfo
{
    private final RawDevice device;
    private final int id;
    private final int num_buttons;
    private final int sample_rate;
    
    public RawMouseInfo(final RawDevice device, final int id, final int num_buttons, final int sample_rate) {
        this.device = device;
        this.id = id;
        this.num_buttons = num_buttons;
        this.sample_rate = sample_rate;
    }
    
    public final int getUsage() {
        return 2;
    }
    
    public final int getUsagePage() {
        return 1;
    }
    
    public final long getHandle() {
        return this.device.getHandle();
    }
    
    public final Controller createControllerFromDevice(final RawDevice rawDevice, final SetupAPIDevice setupAPIDevice) throws IOException {
        if (this.num_buttons == 0) {
            return null;
        }
        final Component[] array2;
        final Component[] array = array2 = new Component[3 + this.num_buttons];
        final int n = 0;
        int n2 = 0;
        ++n2;
        array2[n] = new RawMouse.Axis(rawDevice, Component.Identifier.Axis.X);
        final Component[] array3 = array;
        final int n3 = 0;
        ++n2;
        array3[n3] = new RawMouse.Axis(rawDevice, Component.Identifier.Axis.Y);
        final Component[] array4 = array;
        final int n4 = 0;
        ++n2;
        array4[n4] = new RawMouse.Axis(rawDevice, Component.Identifier.Axis.Z);
        while (0 < this.num_buttons) {
            final Component.Identifier.Button mapMouseButtonIdentifier = DIIdentifierMap.mapMouseButtonIdentifier(DIIdentifierMap.getButtonIdentifier(0));
            final Component[] array5 = array;
            final int n5 = 0;
            ++n2;
            array5[n5] = new RawMouse.Button(rawDevice, mapMouseButtonIdentifier, 0);
            int n6 = 0;
            ++n6;
        }
        return new RawMouse(setupAPIDevice.getName(), rawDevice, array, new Controller[0], new Rumbler[0]);
    }
}
