package org.lwjgl.opengl;

import java.io.*;
import java.util.regex.*;
import org.lwjgl.*;
import java.util.*;

public class XRandR
{
    private static Screen[] current;
    private static String primaryScreenIdentifier;
    private static Screen[] savedConfiguration;
    private static Map screens;
    private static final Pattern WHITESPACE_PATTERN;
    private static final Pattern SCREEN_HEADER_PATTERN;
    private static final Pattern SCREEN_MODELINE_PATTERN;
    private static final Pattern FREQ_PATTERN;
    
    private static void populate() {
        if (XRandR.screens != null) {
            return;
        }
        XRandR.screens = new HashMap();
        final Process exec = Runtime.getRuntime().exec(new String[] { "xrandr", "-q" });
        final ArrayList list = new ArrayList();
        final ArrayList list2 = new ArrayList();
        String primaryScreenIdentifier = null;
        final int[] array = new int[2];
        String line;
        while ((line = new BufferedReader(new InputStreamReader(exec.getInputStream())).readLine()) != null) {
            final String[] split = XRandR.WHITESPACE_PATTERN.split(line.trim());
            if ("connected".equals(split[1])) {
                if (primaryScreenIdentifier != null) {
                    XRandR.screens.put(primaryScreenIdentifier, list2.toArray(new Screen[list2.size()]));
                    list2.clear();
                }
                primaryScreenIdentifier = split[0];
                if ("primary".equals(split[2])) {
                    parseScreenHeader(array, split[3]);
                    XRandR.primaryScreenIdentifier = primaryScreenIdentifier;
                }
                else {
                    parseScreenHeader(array, split[2]);
                }
            }
            else {
                final Matcher matcher = XRandR.SCREEN_MODELINE_PATTERN.matcher(split[0]);
                if (!matcher.matches()) {
                    continue;
                }
                parseScreenModeline(list2, list, primaryScreenIdentifier, Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), split, array);
            }
        }
        XRandR.screens.put(primaryScreenIdentifier, list2.toArray(new Screen[list2.size()]));
        XRandR.current = (Screen[])list.toArray(new Screen[list.size()]);
        if (XRandR.primaryScreenIdentifier == null) {
            long n = Long.MIN_VALUE;
            final Screen[] current = XRandR.current;
            while (0 < current.length) {
                final Screen screen = current[0];
                if (1L * screen.width * screen.height > n) {
                    XRandR.primaryScreenIdentifier = screen.name;
                    n = 1L * screen.width * screen.height;
                }
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    public static Screen[] getConfiguration() {
        final Screen[] current = XRandR.current;
        while (0 < current.length) {
            final Screen screen = current[0];
            if (screen.name.equals(XRandR.primaryScreenIdentifier)) {
                return new Screen[] { screen };
            }
            int n = 0;
            ++n;
        }
        return XRandR.current.clone();
    }
    
    public static void setConfiguration(final boolean b, final Screen... current) {
        if (current.length == 0) {
            throw new IllegalArgumentException("Must specify at least one screen");
        }
        final ArrayList<String> list = new ArrayList<String>();
        list.add("xrandr");
        int n2 = 0;
        if (b) {
            final Screen[] current2 = XRandR.current;
            while (0 < current2.length) {
                final Screen screen = current2[0];
                while (0 < current.length && !current[0].name.equals(screen.name)) {
                    int n = 0;
                    ++n;
                }
                if (false) {
                    list.add("--output");
                    list.add(screen.name);
                    list.add("--off");
                }
                ++n2;
            }
        }
        while (0 < current.length) {
            Screen.access$000(current[0], list);
            ++n2;
        }
        String line;
        while ((line = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(list.toArray(new String[list.size()])).getInputStream())).readLine()) != null) {
            LWJGLUtil.log("Unexpected output from xrandr process: " + line);
        }
        XRandR.current = current;
    }
    
    public static void saveConfiguration() {
        XRandR.savedConfiguration = XRandR.current.clone();
    }
    
    public static void restoreConfiguration() {
        if (XRandR.savedConfiguration != null) {
            setConfiguration(true, XRandR.savedConfiguration);
        }
    }
    
    public static String[] getScreenNames() {
        return (String[])XRandR.screens.keySet().toArray(new String[XRandR.screens.size()]);
    }
    
    public static Screen[] getResolutions(final String s) {
        return XRandR.screens.get(s).clone();
    }
    
    private static void parseScreenModeline(final List list, final List list2, final String s, final int n, final int n2, final String[] array, final int[] array2) {
        while (1 < array.length) {
            final String s2 = array[1];
            if (!"+".equals(s2)) {
                final Matcher matcher = XRandR.FREQ_PATTERN.matcher(s2);
                if (!matcher.matches()) {
                    LWJGLUtil.log("Frequency match failed: " + Arrays.toString(array));
                    return;
                }
                final int int1 = Integer.parseInt(matcher.group(1));
                final Screen screen = new Screen(s, n, n2, int1, 0, 0);
                if (s2.contains("*")) {
                    list2.add(new Screen(s, n, n2, int1, array2[0], array2[1]));
                    list.add(0, screen);
                }
                else {
                    list.add(screen);
                }
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    private static void parseScreenHeader(final int[] array, final String s) {
        final Matcher matcher = XRandR.SCREEN_HEADER_PATTERN.matcher(s);
        if (!matcher.matches()) {
            array[1] = (array[0] = 0);
            return;
        }
        array[0] = Integer.parseInt(matcher.group(3));
        array[1] = Integer.parseInt(matcher.group(4));
    }
    
    static Screen DisplayModetoScreen(final DisplayMode displayMode) {
        final Screen primary = findPrimary(XRandR.current);
        return new Screen(primary.name, displayMode.getWidth(), displayMode.getHeight(), displayMode.getFrequency(), primary.xPos, primary.yPos);
    }
    
    static DisplayMode ScreentoDisplayMode(final Screen... array) {
        final Screen primary = findPrimary(array);
        return new DisplayMode(primary.width, primary.height, 24, primary.freq);
    }
    
    private static Screen findPrimary(final Screen... array) {
        while (0 < array.length) {
            final Screen screen = array[0];
            if (screen.name.equals(XRandR.primaryScreenIdentifier)) {
                return screen;
            }
            int n = 0;
            ++n;
        }
        return array[0];
    }
    
    static {
        WHITESPACE_PATTERN = Pattern.compile("\\s+");
        SCREEN_HEADER_PATTERN = Pattern.compile("^(\\d+)x(\\d+)[+](\\d+)[+](\\d+)$");
        SCREEN_MODELINE_PATTERN = Pattern.compile("^(\\d+)x(\\d+)$");
        FREQ_PATTERN = Pattern.compile("^(\\d+)[.](\\d+)(?:\\s*[*])?(?:\\s*[+])?$");
    }
    
    public static class Screen implements Cloneable
    {
        public final String name;
        public final int width;
        public final int height;
        public final int freq;
        public int xPos;
        public int yPos;
        
        Screen(final String name, final int width, final int height, final int freq, final int xPos, final int yPos) {
            this.name = name;
            this.width = width;
            this.height = height;
            this.freq = freq;
            this.xPos = xPos;
            this.yPos = yPos;
        }
        
        private void getArgs(final List list) {
            list.add("--output");
            list.add(this.name);
            list.add("--mode");
            list.add(this.width + "x" + this.height);
            list.add("--rate");
            list.add(Integer.toString(this.freq));
            list.add("--pos");
            list.add(this.xPos + "x" + this.yPos);
        }
        
        @Override
        public String toString() {
            return this.name + " " + this.width + "x" + this.height + " @ " + this.xPos + "x" + this.yPos + " with " + this.freq + "Hz";
        }
        
        static void access$000(final Screen screen, final List list) {
            screen.getArgs(list);
        }
    }
}
