package org.lwjgl.opengl;

import java.security.*;
import java.nio.*;
import java.awt.image.*;
import java.awt.*;
import org.lwjgl.*;

final class AWTUtil
{
    public static boolean hasWheel() {
        return true;
    }
    
    public static int getNativeCursorCapabilities() {
        if (LWJGLUtil.getPlatform() != 2 || LWJGLUtil.isMacOSXEqualsOrBetterThan(10, 4)) {
            return (Toolkit.getDefaultToolkit().getMaximumCursorColors() >= 32767 && getMaxCursorSize() > 0) ? 3 : 4;
        }
        return 0;
    }
    
    public static Robot createRobot(final Component component) {
        return AccessController.doPrivileged((PrivilegedExceptionAction<Robot>)new PrivilegedExceptionAction(component) {
            final Component val$component;
            
            public Robot run() throws Exception {
                return new Robot(this.val$component.getGraphicsConfiguration().getDevice());
            }
            
            public Object run() throws Exception {
                return this.run();
            }
        });
    }
    
    private static int transformY(final Component component, final int n) {
        return component.getHeight() - 1 - n;
    }
    
    private static Point getPointerLocation(final Component component) {
        final GraphicsConfiguration graphicsConfiguration = component.getGraphicsConfiguration();
        if (graphicsConfiguration == null) {
            return null;
        }
        final PointerInfo pointerInfo = AccessController.doPrivileged((PrivilegedExceptionAction<PointerInfo>)new PrivilegedExceptionAction() {
            public PointerInfo run() throws Exception {
                return MouseInfo.getPointerInfo();
            }
            
            public Object run() throws Exception {
                return this.run();
            }
        });
        if (pointerInfo.getDevice() == graphicsConfiguration.getDevice()) {
            return pointerInfo.getLocation();
        }
        return null;
    }
    
    public static Point getCursorPosition(final Component component) {
        final Point pointerLocation = getPointerLocation(component);
        if (pointerLocation != null) {
            final Point locationOnScreen = component.getLocationOnScreen();
            pointerLocation.translate(-locationOnScreen.x, -locationOnScreen.y);
            pointerLocation.move(pointerLocation.x, transformY(component, pointerLocation.y));
            return pointerLocation;
        }
        return null;
    }
    
    public static void setCursorPosition(final Component component, final Robot robot, final int n, final int n2) {
        if (robot != null) {
            final Point locationOnScreen = component.getLocationOnScreen();
            robot.mouseMove(locationOnScreen.x + n, locationOnScreen.y + transformY(component, n2));
        }
    }
    
    public static int getMinCursorSize() {
        final Dimension bestCursorSize = Toolkit.getDefaultToolkit().getBestCursorSize(0, 0);
        return Math.max(bestCursorSize.width, bestCursorSize.height);
    }
    
    public static int getMaxCursorSize() {
        final Dimension bestCursorSize = Toolkit.getDefaultToolkit().getBestCursorSize(10000, 10000);
        return Math.min(bestCursorSize.width, bestCursorSize.height);
    }
    
    public static Cursor createCursor(final int n, final int n2, final int n3, final int n4, final int n5, final IntBuffer intBuffer, final IntBuffer intBuffer2) throws LWJGLException {
        final BufferedImage bufferedImage = new BufferedImage(n, n2, 2);
        final int[] array = new int[intBuffer.remaining()];
        final int position = intBuffer.position();
        intBuffer.get(array);
        intBuffer.position(position);
        bufferedImage.setRGB(0, 0, n, n2, array, 0, n);
        return Toolkit.getDefaultToolkit().createCustomCursor(bufferedImage, new Point(n3, n4), "LWJGL Custom cursor");
    }
}
