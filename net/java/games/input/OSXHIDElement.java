package net.java.games.input;

import java.io.*;

final class OSXHIDElement
{
    private final OSXHIDDevice device;
    private final UsagePair usage_pair;
    private final long element_cookie;
    private final ElementType element_type;
    private final int min;
    private final int max;
    private final Component.Identifier identifier;
    private final boolean is_relative;
    
    public OSXHIDElement(final OSXHIDDevice device, final UsagePair usage_pair, final long element_cookie, final ElementType element_type, final int min, final int max, final boolean is_relative) {
        this.device = device;
        this.usage_pair = usage_pair;
        this.element_cookie = element_cookie;
        this.element_type = element_type;
        this.min = min;
        this.max = max;
        this.identifier = this.computeIdentifier();
        this.is_relative = is_relative;
    }
    
    private final Component.Identifier computeIdentifier() {
        if (this.usage_pair.getUsagePage() == UsagePage.GENERIC_DESKTOP) {
            return ((GenericDesktopUsage)this.usage_pair.getUsage()).getIdentifier();
        }
        if (this.usage_pair.getUsagePage() == UsagePage.BUTTON) {
            return ((ButtonUsage)this.usage_pair.getUsage()).getIdentifier();
        }
        if (this.usage_pair.getUsagePage() == UsagePage.KEYBOARD_OR_KEYPAD) {
            return ((KeyboardUsage)this.usage_pair.getUsage()).getIdentifier();
        }
        return null;
    }
    
    final Component.Identifier getIdentifier() {
        return this.identifier;
    }
    
    final long getCookie() {
        return this.element_cookie;
    }
    
    final ElementType getType() {
        return this.element_type;
    }
    
    final boolean isRelative() {
        return this.is_relative && this.identifier instanceof Component.Identifier.Axis;
    }
    
    final boolean isAnalog() {
        return this.identifier instanceof Component.Identifier.Axis && this.identifier != Component.Identifier.Axis.POV;
    }
    
    private UsagePair getUsagePair() {
        return this.usage_pair;
    }
    
    final void getElementValue(final OSXEvent osxEvent) throws IOException {
        this.device.getElementValue(this.element_cookie, osxEvent);
    }
    
    final float convertValue(float n) {
        if (this.identifier == Component.Identifier.Axis.POV) {
            switch ((int)n) {
                case 0: {
                    return 0.25f;
                }
                case 1: {
                    return 0.375f;
                }
                case 2: {
                    return 0.5f;
                }
                case 3: {
                    return 0.625f;
                }
                case 4: {
                    return 0.75f;
                }
                case 5: {
                    return 0.875f;
                }
                case 6: {
                    return 1.0f;
                }
                case 7: {
                    return 0.125f;
                }
                case 8: {
                    return 0.0f;
                }
                default: {
                    return 0.0f;
                }
            }
        }
        else {
            if (!(this.identifier instanceof Component.Identifier.Axis) || this.is_relative) {
                return n;
            }
            if (this.min == this.max) {
                return 0.0f;
            }
            if (n > this.max) {
                n = (float)this.max;
            }
            else if (n < this.min) {
                n = (float)this.min;
            }
            return 2.0f * (n - this.min) / (this.max - this.min) - 1.0f;
        }
    }
}
