package org.lwjgl.util;

import org.lwjgl.opengl.*;
import org.lwjgl.*;
import java.util.*;

public final class Display
{
    private static final boolean DEBUG = false;
    static final boolean $assertionsDisabled;
    
    public static DisplayMode[] getAvailableDisplayModes(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) throws LWJGLException {
        final DisplayMode[] availableDisplayModes = org.lwjgl.opengl.Display.getAvailableDisplayModes();
        int length = 0;
        if (LWJGLUtil.DEBUG) {
            System.out.println("Available screen modes:");
            final DisplayMode[] array = availableDisplayModes;
            length = array.length;
            while (0 < 0) {
                System.out.println(array[0]);
                int n9 = 0;
                ++n9;
            }
        }
        final ArrayList list = new ArrayList<DisplayMode>(availableDisplayModes.length);
        while (0 < availableDisplayModes.length) {
            assert availableDisplayModes[0] != null : "" + 0 + " " + availableDisplayModes.length;
            Label_0306: {
                if (n == -1 || availableDisplayModes[0].getWidth() >= n) {
                    if (n3 == -1 || availableDisplayModes[0].getWidth() <= n3) {
                        if (n2 == -1 || availableDisplayModes[0].getHeight() >= n2) {
                            if (n4 == -1 || availableDisplayModes[0].getHeight() <= n4) {
                                if (n5 == -1 || availableDisplayModes[0].getBitsPerPixel() >= n5) {
                                    if (n6 == -1 || availableDisplayModes[0].getBitsPerPixel() <= n6) {
                                        if (availableDisplayModes[0].getFrequency() != 0) {
                                            if (n7 != -1 && availableDisplayModes[0].getFrequency() < n7) {
                                                break Label_0306;
                                            }
                                            if (n8 != -1 && availableDisplayModes[0].getFrequency() > n8) {
                                                break Label_0306;
                                            }
                                        }
                                        list.add(availableDisplayModes[0]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            ++length;
        }
        final DisplayMode[] array2 = new DisplayMode[list.size()];
        list.toArray(array2);
        LWJGLUtil.DEBUG;
        return array2;
    }
    
    public static DisplayMode setDisplayMode(final DisplayMode[] array, final String[] array2) throws Exception {
        Arrays.sort(array, new Sorter(array2));
        if (LWJGLUtil.DEBUG) {
            System.out.println("Sorted display modes:");
            while (0 < array.length) {
                System.out.println(array[0]);
                int n = 0;
                ++n;
            }
        }
        if (0 < array.length) {
            final DisplayMode displayMode = array[0];
            if (LWJGLUtil.DEBUG) {
                System.out.println("Attempting to set displaymode: " + displayMode);
            }
            org.lwjgl.opengl.Display.setDisplayMode(displayMode);
            return displayMode;
        }
        throw new Exception("Failed to set display mode.");
    }
    
    static {
        $assertionsDisabled = !Display.class.desiredAssertionStatus();
    }
    
    class Sorter implements Comparator
    {
        final FieldAccessor[] accessors;
        final String[] val$param;
        
        Sorter(final String[] val$param) {
            this.val$param = val$param;
            this.accessors = new FieldAccessor[this.val$param.length];
            while (0 < this.accessors.length) {
                final int index = this.val$param[0].indexOf(61);
                if (index > 0) {
                    this.accessors[0] = new FieldAccessor(this.val$param[0].substring(0, index), 0, Integer.parseInt(this.val$param[0].substring(index + 1, this.val$param[0].length())), true);
                }
                else if (this.val$param[0].charAt(0) == '-') {
                    this.accessors[0] = new FieldAccessor(this.val$param[0].substring(1), -1, 0, false);
                }
                else {
                    this.accessors[0] = new FieldAccessor(this.val$param[0], 1, 0, false);
                }
                int n = 0;
                ++n;
            }
        }
        
        public int compare(final DisplayMode displayMode, final DisplayMode displayMode2) {
            final FieldAccessor[] accessors = this.accessors;
            while (0 < accessors.length) {
                final FieldAccessor fieldAccessor = accessors[0];
                final int int1 = fieldAccessor.getInt(displayMode);
                final int int2 = fieldAccessor.getInt(displayMode2);
                if (fieldAccessor.usePreferred && int1 != int2) {
                    if (int1 == fieldAccessor.preferred) {
                        return -1;
                    }
                    if (int2 == fieldAccessor.preferred) {
                        return 1;
                    }
                    final int abs = Math.abs(int1 - fieldAccessor.preferred);
                    final int abs2 = Math.abs(int2 - fieldAccessor.preferred);
                    if (abs < abs2) {
                        return -1;
                    }
                    if (abs > abs2) {
                        return 1;
                    }
                }
                else {
                    if (int1 < int2) {
                        return fieldAccessor.order;
                    }
                    if (int1 != int2) {
                        return -fieldAccessor.order;
                    }
                }
                int n = 0;
                ++n;
            }
            return 0;
        }
        
        public int compare(final Object o, final Object o2) {
            return this.compare((DisplayMode)o, (DisplayMode)o2);
        }
    }
}
