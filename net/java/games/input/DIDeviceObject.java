package net.java.games.input;

import java.io.*;

final class DIDeviceObject
{
    private final IDirectInputDevice device;
    private final byte[] guid;
    private final int identifier;
    private final int type;
    private final int instance;
    private final int guid_type;
    private final int flags;
    private final String name;
    private final Component.Identifier id;
    private final int format_offset;
    private final long min;
    private final long max;
    private final int deadzone;
    private int last_poll_value;
    private int last_event_value;
    
    public DIDeviceObject(final IDirectInputDevice device, final Component.Identifier id, final byte[] guid, final int guid_type, final int identifier, final int type, final int instance, final int flags, final String name, final int format_offset) throws IOException {
        this.device = device;
        this.id = id;
        this.guid = guid;
        this.identifier = identifier;
        this.type = type;
        this.instance = instance;
        this.guid_type = guid_type;
        this.flags = flags;
        this.name = name;
        this.format_offset = format_offset;
        if (this != 0 && this != 0) {
            final long[] rangeProperty = device.getRangeProperty(identifier);
            this.min = rangeProperty[0];
            this.max = rangeProperty[1];
            this.deadzone = device.getDeadzoneProperty(identifier);
        }
        else {
            this.min = -2147483648L;
            this.max = 2147483647L;
            this.deadzone = 0;
        }
    }
    
    public final synchronized int getRelativePollValue(final int last_poll_value) {
        if (this.device.areAxesRelative()) {
            return last_poll_value;
        }
        final int n = last_poll_value - this.last_poll_value;
        this.last_poll_value = last_poll_value;
        return n;
    }
    
    public final synchronized int getRelativeEventValue(final int last_event_value) {
        if (this.device.areAxesRelative()) {
            return last_event_value;
        }
        final int n = last_event_value - this.last_event_value;
        this.last_event_value = last_event_value;
        return n;
    }
    
    public final int getGUIDType() {
        return this.guid_type;
    }
    
    public final int getFormatOffset() {
        return this.format_offset;
    }
    
    public final IDirectInputDevice getDevice() {
        return this.device;
    }
    
    public final int getDIIdentifier() {
        return this.identifier;
    }
    
    public final Component.Identifier getIdentifier() {
        return this.id;
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final int getInstance() {
        return this.instance;
    }
    
    public final int getType() {
        return this.type;
    }
    
    public final byte[] getGUID() {
        return this.guid;
    }
    
    public final int getFlags() {
        return this.flags;
    }
    
    public final long getMin() {
        return this.min;
    }
    
    public final long getMax() {
        return this.max;
    }
    
    public final float getDeadzone() {
        return (float)this.deadzone;
    }
    
    public final boolean isAnalog() {
        return this != 0 && this.id != Component.Identifier.Axis.POV;
    }
    
    public final float convertValue(final float n) {
        if (this.getDevice().getType() == 18 && this.id == Component.Identifier.Axis.Z) {
            return n / 120.0f;
        }
        if (this != 0) {
            return (((int)n & 0x80) != 0x0) ? 1.0f : 0.0f;
        }
        if (this.id == Component.Identifier.Axis.POV) {
            final int n2 = (int)n;
            if ((n2 & 0xFFFF) == 0xFFFF) {
                return 0.0f;
            }
            if (n2 >= 0 && n2 < 2250) {
                return 0.25f;
            }
            if (n2 < 6750) {
                return 0.375f;
            }
            if (n2 < 11250) {
                return 0.5f;
            }
            if (n2 < 15750) {
                return 0.625f;
            }
            if (n2 < 20250) {
                return 0.75f;
            }
            if (n2 < 24750) {
                return 0.875f;
            }
            if (n2 < 29250) {
                return 1.0f;
            }
            if (n2 < 33750) {
                return 0.125f;
            }
            return 0.25f;
        }
        else {
            if (this != 0 && this != 0) {
                return 2.0f * (n - this.min) / (this.max - this.min) - 1.0f;
            }
            return n;
        }
    }
}
