package com.sun.jna.platform;

import com.sun.jna.platform.unix.*;
import java.util.*;
import java.awt.image.*;
import javax.swing.*;
import com.sun.jna.*;
import java.awt.geom.*;
import java.awt.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.*;
import java.awt.event.*;
import java.awt.peer.*;

public class WindowUtils
{
    private static final String TRANSPARENT_OLD_BG = "transparent-old-bg";
    private static final String TRANSPARENT_OLD_OPAQUE = "transparent-old-opaque";
    private static final String TRANSPARENT_ALPHA = "transparent-alpha";
    public static final Shape MASK_NONE;
    
    private static NativeWindowUtils getInstance() {
        return Holder.INSTANCE;
    }
    
    public static void setWindowMask(final Window window, final Shape shape) {
        getInstance().setWindowMask(window, shape);
    }
    
    public static void setComponentMask(final Component component, final Shape shape) {
        getInstance().setWindowMask(component, shape);
    }
    
    public static void setWindowMask(final Window window, final Icon icon) {
        getInstance().setWindowMask(window, icon);
    }
    
    public static boolean isWindowAlphaSupported() {
        return getInstance().isWindowAlphaSupported();
    }
    
    public static GraphicsConfiguration getAlphaCompatibleGraphicsConfiguration() {
        return getInstance().getAlphaCompatibleGraphicsConfiguration();
    }
    
    public static void setWindowAlpha(final Window window, final float n) {
        getInstance().setWindowAlpha(window, Math.max(0.0f, Math.min(n, 1.0f)));
    }
    
    public static void setWindowTransparent(final Window window, final boolean b) {
        getInstance().setWindowTransparent(window, b);
    }
    
    static {
        MASK_NONE = null;
    }
    
    private static class X11WindowUtils extends NativeWindowUtils
    {
        private boolean didCheck;
        private long[] alphaVisualIDs;
        private static final long OPAQUE = 4294967295L;
        private static final String OPACITY = "_NET_WM_WINDOW_OPACITY";
        
        private X11WindowUtils() {
            this.alphaVisualIDs = new long[0];
        }
        
        private static X11.Pixmap createBitmap(final X11.Display display, final X11.Window window, final Raster raster) {
            final X11 instance = X11.INSTANCE;
            final Rectangle bounds = raster.getBounds();
            final int n = bounds.x + bounds.width;
            final int n2 = bounds.y + bounds.height;
            final X11.Pixmap xCreatePixmap = instance.XCreatePixmap(display, window, n, n2, 1);
            final X11.GC xCreateGC = instance.XCreateGC(display, xCreatePixmap, new NativeLong(0L), null);
            if (xCreateGC == null) {
                return null;
            }
            instance.XSetForeground(display, xCreateGC, new NativeLong(0L));
            instance.XFillRectangle(display, xCreatePixmap, xCreateGC, 0, 0, n, n2);
            final ArrayList<Rectangle> list = (ArrayList<Rectangle>)new ArrayList<Object>();
            RasterRangesUtils.outputOccupiedRanges(raster, new RasterRangesUtils.RangesOutput((List)list) {
                final List val$rlist;
                
                public boolean outputRange(final int n, final int n2, final int n3, final int n4) {
                    this.val$rlist.add(new Rectangle(n, n2, n3, n4));
                    return true;
                }
            });
            final X11.XRectangle[] array = (X11.XRectangle[])new X11.XRectangle().toArray(list.size());
            while (1 < array.length) {
                final Rectangle rectangle = list.get(1);
                array[1].x = (short)rectangle.x;
                array[1].y = (short)rectangle.y;
                array[1].width = (short)rectangle.width;
                array[1].height = (short)rectangle.height;
                final Pointer pointer = array[1].getPointer();
                pointer.setShort(0L, (short)rectangle.x);
                pointer.setShort(2L, (short)rectangle.y);
                pointer.setShort(4L, (short)rectangle.width);
                pointer.setShort(6L, (short)rectangle.height);
                array[1].setAutoSynch(false);
                int n3 = 0;
                ++n3;
            }
            instance.XSetForeground(display, xCreateGC, new NativeLong(1L));
            instance.XFillRectangles(display, xCreatePixmap, xCreateGC, array, array.length);
            instance.XFreeGC(display, xCreateGC);
            return xCreatePixmap;
        }
        
        private static long getVisualID(final GraphicsConfiguration graphicsConfiguration) {
            return ((Number)graphicsConfiguration.getClass().getMethod("getVisual", (Class<?>[])null).invoke(graphicsConfiguration, (Object[])null)).longValue();
        }
        
        @Override
        public GraphicsConfiguration getAlphaCompatibleGraphicsConfiguration() {
            if (this > 0) {
                final GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
                while (0 < screenDevices.length) {
                    final GraphicsConfiguration[] configurations = screenDevices[0].getConfigurations();
                    while (0 < configurations.length) {
                        final long visualID = getVisualID(configurations[0]);
                        final long[] alphaVisualIDs = this.getAlphaVisualIDs();
                        while (0 < alphaVisualIDs.length) {
                            if (visualID == alphaVisualIDs[0]) {
                                return configurations[0];
                            }
                            int n = 0;
                            ++n;
                        }
                        int n2 = 0;
                        ++n2;
                    }
                    int n3 = 0;
                    ++n3;
                }
            }
            return super.getAlphaCompatibleGraphicsConfiguration();
        }
        
        private synchronized long[] getAlphaVisualIDs() {
            if (this.didCheck) {
                return this.alphaVisualIDs;
            }
            this.didCheck = true;
            final X11 instance = X11.INSTANCE;
            final X11.Display xOpenDisplay = instance.XOpenDisplay(null);
            if (xOpenDisplay == null) {
                return this.alphaVisualIDs;
            }
            final int xDefaultScreen = instance.XDefaultScreen(xOpenDisplay);
            final X11.XVisualInfo xVisualInfo = new X11.XVisualInfo();
            xVisualInfo.screen = xDefaultScreen;
            xVisualInfo.depth = 32;
            xVisualInfo.c_class = 4;
            final NativeLong nativeLong = new NativeLong(14L);
            final IntByReference intByReference = new IntByReference();
            final X11.XVisualInfo xGetVisualInfo = instance.XGetVisualInfo(xOpenDisplay, nativeLong, xVisualInfo, intByReference);
            if (xGetVisualInfo != null) {
                final ArrayList<X11.VisualID> list = new ArrayList<X11.VisualID>();
                final X11.XVisualInfo[] array = (X11.XVisualInfo[])xGetVisualInfo.toArray(intByReference.getValue());
                int n = 0;
                while (0 < array.length) {
                    final X11.Xrender.XRenderPictFormat xRenderFindVisualFormat = X11.Xrender.INSTANCE.XRenderFindVisualFormat(xOpenDisplay, array[0].visual);
                    if (xRenderFindVisualFormat.type == 1 && xRenderFindVisualFormat.direct.alphaMask != 0) {
                        list.add(array[0].visualid);
                    }
                    ++n;
                }
                this.alphaVisualIDs = new long[list.size()];
                while (0 < this.alphaVisualIDs.length) {
                    this.alphaVisualIDs[0] = ((X11.VisualID)list.get(0)).longValue();
                    ++n;
                }
                final long[] alphaVisualIDs = this.alphaVisualIDs;
                if (xGetVisualInfo != null) {
                    instance.XFree(xGetVisualInfo.getPointer());
                }
                instance.XCloseDisplay(xOpenDisplay);
                return alphaVisualIDs;
            }
            if (xGetVisualInfo != null) {
                instance.XFree(xGetVisualInfo.getPointer());
            }
            instance.XCloseDisplay(xOpenDisplay);
            return this.alphaVisualIDs;
        }
        
        private static X11.Window getContentWindow(final Window window, final X11.Display display, X11.Window window2, final Point point) {
            if ((window instanceof Frame && !((Frame)window).isUndecorated()) || (window instanceof Dialog && !((Dialog)window).isUndecorated())) {
                final X11 instance = X11.INSTANCE;
                final X11.WindowByReference windowByReference = new X11.WindowByReference();
                final X11.WindowByReference windowByReference2 = new X11.WindowByReference();
                final PointerByReference pointerByReference = new PointerByReference();
                final IntByReference intByReference = new IntByReference();
                instance.XQueryTree(display, window2, windowByReference, windowByReference2, pointerByReference, intByReference);
                final Pointer value = pointerByReference.getValue();
                final int[] intArray = value.getIntArray(0L, intByReference.getValue());
                if (0 < intArray.length) {
                    final X11.Window window3 = new X11.Window((long)intArray[0]);
                    final X11.XWindowAttributes xWindowAttributes = new X11.XWindowAttributes();
                    instance.XGetWindowAttributes(display, window3, xWindowAttributes);
                    point.x = -xWindowAttributes.x;
                    point.y = -xWindowAttributes.y;
                    window2 = window3;
                }
                if (value != null) {
                    instance.XFree(value);
                }
            }
            return window2;
        }
        
        private static X11.Window getDrawable(final Component component) {
            final int n = (int)Native.getComponentID(component);
            if (n == 0) {
                return null;
            }
            return new X11.Window((long)n);
        }
        
        @Override
        public void setWindowAlpha(final Window window, final float n) {
            if (this > 0) {
                throw new UnsupportedOperationException("This X11 display does not provide a 32-bit visual");
            }
            this.whenDisplayable(window, new Runnable(window, n) {
                final Window val$w;
                final float val$alpha;
                final X11WindowUtils this$0;
                
                public void run() {
                    final X11 instance = X11.INSTANCE;
                    final X11.Display xOpenDisplay = instance.XOpenDisplay(null);
                    if (xOpenDisplay == null) {
                        return;
                    }
                    final X11.Window access$800 = X11WindowUtils.access$800(this.val$w);
                    if (this.val$alpha == 1.0f) {
                        instance.XDeleteProperty(xOpenDisplay, access$800, instance.XInternAtom(xOpenDisplay, "_NET_WM_WINDOW_OPACITY", false));
                    }
                    else {
                        instance.XChangeProperty(xOpenDisplay, access$800, instance.XInternAtom(xOpenDisplay, "_NET_WM_WINDOW_OPACITY", false), X11.XA_CARDINAL, 32, 0, new IntByReference((int)((long)(this.val$alpha * 4.2949673E9f) & -1L)).getPointer(), 1);
                    }
                    instance.XCloseDisplay(xOpenDisplay);
                }
            });
        }
        
        @Override
        public void setWindowTransparent(final Window window, final boolean b) {
            if (!(window instanceof RootPaneContainer)) {
                throw new IllegalArgumentException("Window must be a RootPaneContainer");
            }
            if (this > 0) {
                throw new UnsupportedOperationException("This X11 display does not provide a 32-bit visual");
            }
            if (!window.getGraphicsConfiguration().equals(this.getAlphaCompatibleGraphicsConfiguration())) {
                throw new IllegalArgumentException("Window GraphicsConfiguration '" + window.getGraphicsConfiguration() + "' does not support transparency");
            }
            if (b == (window.getBackground() != null && window.getBackground().getAlpha() == 0)) {
                return;
            }
            this.whenDisplayable(window, new Runnable(window, b) {
                final Window val$w;
                final boolean val$transparent;
                final X11WindowUtils this$0;
                
                public void run() {
                    final JRootPane rootPane = ((RootPaneContainer)this.val$w).getRootPane();
                    final JLayeredPane layeredPane = rootPane.getLayeredPane();
                    final Container contentPane = rootPane.getContentPane();
                    if (contentPane instanceof X11TransparentContentPane) {
                        ((X11TransparentContentPane)contentPane).setTransparent(this.val$transparent);
                    }
                    else if (this.val$transparent) {
                        final X11TransparentContentPane contentPane2 = this.this$0.new X11TransparentContentPane(contentPane);
                        rootPane.setContentPane(contentPane2);
                        layeredPane.add(new RepaintTrigger(contentPane2), JLayeredPane.DRAG_LAYER);
                    }
                    this.this$0.setLayersTransparent(this.val$w, this.val$transparent);
                    this.this$0.setForceHeavyweightPopups(this.val$w, this.val$transparent);
                    this.this$0.setDoubleBuffered(this.val$w, !this.val$transparent);
                }
            });
        }
        
        private void setWindowShape(final Window window, final PixmapSource pixmapSource) {
            this.whenDisplayable(window, new Runnable(window, pixmapSource) {
                final Window val$w;
                final PixmapSource val$src;
                final X11WindowUtils this$0;
                
                public void run() {
                    final X11 instance = X11.INSTANCE;
                    final X11.Display xOpenDisplay = instance.XOpenDisplay(null);
                    if (xOpenDisplay == null) {
                        return;
                    }
                    final X11.Window access$800 = X11WindowUtils.access$800(this.val$w);
                    final X11.Pixmap pixmap = this.val$src.getPixmap(xOpenDisplay, access$800);
                    X11.Xext.INSTANCE.XShapeCombineMask(xOpenDisplay, access$800, 0, 0, 0, (pixmap == null) ? X11.Pixmap.None : pixmap, 0);
                    if (pixmap != null) {
                        instance.XFreePixmap(xOpenDisplay, pixmap);
                    }
                    instance.XCloseDisplay(xOpenDisplay);
                    this.this$0.setForceHeavyweightPopups(this.this$0.getWindow(this.val$w), pixmap != null);
                }
            });
        }
        
        @Override
        protected void setMask(final Component component, final Raster raster) {
            this.setWindowShape(this.getWindow(component), new PixmapSource(raster) {
                final Raster val$raster;
                final X11WindowUtils this$0;
                
                public X11.Pixmap getPixmap(final X11.Display display, final X11.Window window) {
                    return (this.val$raster != null) ? X11WindowUtils.access$1000(display, window, this.val$raster) : null;
                }
            });
        }
        
        X11WindowUtils(final WindowUtils$1 object) {
            this();
        }
        
        static X11.Window access$800(final Component component) {
            return getDrawable(component);
        }
        
        static X11.Window access$900(final Window window, final X11.Display display, final X11.Window window2, final Point point) {
            return getContentWindow(window, display, window2, point);
        }
        
        static X11.Pixmap access$1000(final X11.Display display, final X11.Window window, final Raster raster) {
            return createBitmap(display, window, raster);
        }
        
        private interface PixmapSource
        {
            X11.Pixmap getPixmap(final X11.Display p0, final X11.Window p1);
        }
        
        private class X11TransparentContentPane extends TransparentContentPane
        {
            private static final long serialVersionUID = 1L;
            private Memory buffer;
            private int[] pixels;
            private final int[] pixel;
            final X11WindowUtils this$0;
            
            public X11TransparentContentPane(final X11WindowUtils this$0, final Container container) {
                this.this$0 = this$0.super(container);
                this.pixel = new int[4];
            }
            
            @Override
            protected void paintDirect(final BufferedImage bufferedImage, final Rectangle rectangle) {
                final Window windowAncestor = SwingUtilities.getWindowAncestor(this);
                final X11 instance = X11.INSTANCE;
                final X11.Display xOpenDisplay = instance.XOpenDisplay(null);
                final X11.Window access$800 = X11WindowUtils.access$800(windowAncestor);
                final Point point = new Point();
                final X11.Window access$801 = X11WindowUtils.access$900(windowAncestor, xOpenDisplay, access$800, point);
                final X11.GC xCreateGC = instance.XCreateGC(xOpenDisplay, access$801, new NativeLong(0L), null);
                final Raster data = bufferedImage.getData();
                final int width = rectangle.width;
                final int height = rectangle.height;
                if (this.buffer == null || this.buffer.size() != width * height * 4) {
                    this.buffer = new Memory(width * height * 4);
                    this.pixels = new int[width * height];
                }
                while (0 < height) {
                    while (0 < width) {
                        data.getPixel(0, 0, this.pixel);
                        this.pixels[0 * width + 0] = ((this.pixel[3] & 0xFF) << 24 | (this.pixel[0] & 0xFF) << 16 | (this.pixel[1] & 0xFF) << 8 | (this.pixel[2] & 0xFF));
                        int n = 0;
                        ++n;
                    }
                    int n2 = 0;
                    ++n2;
                }
                final X11.XWindowAttributes xWindowAttributes = new X11.XWindowAttributes();
                instance.XGetWindowAttributes(xOpenDisplay, access$801, xWindowAttributes);
                final X11.XImage xCreateImage = instance.XCreateImage(xOpenDisplay, xWindowAttributes.visual, 32, 2, 0, this.buffer, width, height, 32, width * 4);
                this.buffer.write(0L, this.pixels, 0, this.pixels.length);
                final Point point2 = point;
                point2.x += rectangle.x;
                final Point point3 = point;
                point3.y += rectangle.y;
                instance.XPutImage(xOpenDisplay, access$801, xCreateGC, xCreateImage, 0, 0, point.x, point.y, width, height);
                instance.XFree(xCreateImage.getPointer());
                instance.XFreeGC(xOpenDisplay, xCreateGC);
                instance.XCloseDisplay(xOpenDisplay);
            }
        }
    }
    
    public abstract static class NativeWindowUtils
    {
        protected Window getWindow(final Component component) {
            return (Window)((component instanceof Window) ? component : SwingUtilities.getWindowAncestor(component));
        }
        
        protected void whenDisplayable(final Component component, final Runnable runnable) {
            if (component.isDisplayable() && (!Holder.requiresVisible || component.isVisible())) {
                runnable.run();
            }
            else if (Holder.requiresVisible) {
                this.getWindow(component).addWindowListener(new WindowAdapter(runnable) {
                    final Runnable val$action;
                    final NativeWindowUtils this$0;
                    
                    @Override
                    public void windowOpened(final WindowEvent windowEvent) {
                        windowEvent.getWindow().removeWindowListener(this);
                        this.val$action.run();
                    }
                    
                    @Override
                    public void windowClosed(final WindowEvent windowEvent) {
                        windowEvent.getWindow().removeWindowListener(this);
                    }
                });
            }
            else {
                component.addHierarchyListener(new HierarchyListener(runnable) {
                    final Runnable val$action;
                    final NativeWindowUtils this$0;
                    
                    public void hierarchyChanged(final HierarchyEvent hierarchyEvent) {
                        if ((hierarchyEvent.getChangeFlags() & 0x2L) != 0x0L && hierarchyEvent.getComponent().isDisplayable()) {
                            hierarchyEvent.getComponent().removeHierarchyListener(this);
                            this.val$action.run();
                        }
                    }
                });
            }
        }
        
        protected Raster toRaster(final Shape shape) {
            Raster raster = null;
            if (shape != WindowUtils.MASK_NONE) {
                final Rectangle bounds = shape.getBounds();
                if (bounds.width > 0 && bounds.height > 0) {
                    final BufferedImage bufferedImage = new BufferedImage(bounds.x + bounds.width, bounds.y + bounds.height, 12);
                    final Graphics2D graphics = bufferedImage.createGraphics();
                    graphics.setColor(Color.black);
                    graphics.fillRect(0, 0, bounds.x + bounds.width, bounds.y + bounds.height);
                    graphics.setColor(Color.white);
                    graphics.fill(shape);
                    raster = bufferedImage.getRaster();
                }
            }
            return raster;
        }
        
        protected Raster toRaster(final Component component, final Icon icon) {
            Raster alphaRaster = null;
            if (icon != null) {
                final Rectangle rectangle = new Rectangle(0, 0, icon.getIconWidth(), icon.getIconHeight());
                final BufferedImage bufferedImage = new BufferedImage(rectangle.width, rectangle.height, 2);
                final Graphics2D graphics = bufferedImage.createGraphics();
                graphics.setComposite(AlphaComposite.Clear);
                graphics.fillRect(0, 0, rectangle.width, rectangle.height);
                graphics.setComposite(AlphaComposite.SrcOver);
                icon.paintIcon(component, graphics, 0, 0);
                alphaRaster = bufferedImage.getAlphaRaster();
            }
            return alphaRaster;
        }
        
        protected Shape toShape(final Raster raster) {
            final Area area = new Area(new Rectangle(0, 0, 0, 0));
            RasterRangesUtils.outputOccupiedRanges(raster, new RasterRangesUtils.RangesOutput(area) {
                final Area val$area;
                final NativeWindowUtils this$0;
                
                public boolean outputRange(final int n, final int n2, final int n3, final int n4) {
                    this.val$area.add(new Area(new Rectangle(n, n2, n3, n4)));
                    return true;
                }
            });
            return area;
        }
        
        public void setWindowAlpha(final Window window, final float n) {
        }
        
        public boolean isWindowAlphaSupported() {
            return false;
        }
        
        public GraphicsConfiguration getAlphaCompatibleGraphicsConfiguration() {
            return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        }
        
        public void setWindowTransparent(final Window window, final boolean b) {
        }
        
        protected void setDoubleBuffered(final Component component, final boolean doubleBuffered) {
            if (component instanceof JComponent) {
                ((JComponent)component).setDoubleBuffered(doubleBuffered);
            }
            if (component instanceof JRootPane && doubleBuffered) {
                ((JRootPane)component).setDoubleBuffered(true);
            }
            else if (component instanceof Container) {
                final Component[] components = ((Container)component).getComponents();
                while (0 < components.length) {
                    this.setDoubleBuffered(components[0], doubleBuffered);
                    int n = 0;
                    ++n;
                }
            }
        }
        
        protected void setLayersTransparent(final Window window, final boolean b) {
            Color background = b ? new Color(0, 0, 0, 0) : null;
            if (window instanceof RootPaneContainer) {
                final JRootPane rootPane = ((RootPaneContainer)window).getRootPane();
                final JLayeredPane layeredPane = rootPane.getLayeredPane();
                final Container contentPane = rootPane.getContentPane();
                final JComponent component = (contentPane instanceof JComponent) ? ((JComponent)contentPane) : null;
                if (b) {
                    layeredPane.putClientProperty("transparent-old-opaque", layeredPane.isOpaque());
                    layeredPane.setOpaque(false);
                    rootPane.putClientProperty("transparent-old-opaque", rootPane.isOpaque());
                    rootPane.setOpaque(false);
                    if (component != null) {
                        component.putClientProperty("transparent-old-opaque", component.isOpaque());
                        component.setOpaque(false);
                    }
                    rootPane.putClientProperty("transparent-old-bg", rootPane.getParent().getBackground());
                }
                else {
                    layeredPane.setOpaque(Boolean.TRUE.equals(layeredPane.getClientProperty("transparent-old-opaque")));
                    layeredPane.putClientProperty("transparent-old-opaque", null);
                    rootPane.setOpaque(Boolean.TRUE.equals(rootPane.getClientProperty("transparent-old-opaque")));
                    rootPane.putClientProperty("transparent-old-opaque", null);
                    if (component != null) {
                        component.setOpaque(Boolean.TRUE.equals(component.getClientProperty("transparent-old-opaque")));
                        component.putClientProperty("transparent-old-opaque", null);
                    }
                    background = (Color)rootPane.getClientProperty("transparent-old-bg");
                    rootPane.putClientProperty("transparent-old-bg", null);
                }
            }
            window.setBackground(background);
        }
        
        protected void setMask(final Component component, final Raster raster) {
            throw new UnsupportedOperationException("Window masking is not available");
        }
        
        protected void setWindowMask(final Component component, final Raster raster) {
            if (component.isLightweight()) {
                throw new IllegalArgumentException("Component must be heavyweight: " + component);
            }
            this.setMask(component, raster);
        }
        
        public void setWindowMask(final Component component, final Shape shape) {
            this.setWindowMask(component, this.toRaster(shape));
        }
        
        public void setWindowMask(final Component component, final Icon icon) {
            this.setWindowMask(component, this.toRaster(component, icon));
        }
        
        protected void setForceHeavyweightPopups(final Window window, final boolean b) {
            if (!(window instanceof HeavyweightForcer)) {
                final Window[] ownedWindows = window.getOwnedWindows();
                while (0 < ownedWindows.length) {
                    if (ownedWindows[0] instanceof HeavyweightForcer) {
                        if (b) {
                            return;
                        }
                        ownedWindows[0].dispose();
                    }
                    int n = 0;
                    ++n;
                }
                final Boolean value = Boolean.valueOf(System.getProperty("jna.force_hw_popups", "true"));
                if (b && value) {
                    new HeavyweightForcer(window);
                }
            }
        }
        
        protected abstract class TransparentContentPane extends JPanel implements AWTEventListener
        {
            private static final long serialVersionUID = 1L;
            private boolean transparent;
            final NativeWindowUtils this$0;
            
            public TransparentContentPane(final NativeWindowUtils this$0, final Container container) {
                this.this$0 = this$0;
                super(new BorderLayout());
                this.add(container, "Center");
                this.setTransparent(true);
                if (container instanceof JPanel) {
                    ((JComponent)container).setOpaque(false);
                }
            }
            
            @Override
            public void addNotify() {
                super.addNotify();
                Toolkit.getDefaultToolkit().addAWTEventListener(this, 2L);
            }
            
            @Override
            public void removeNotify() {
                Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                super.removeNotify();
            }
            
            public void setTransparent(final boolean transparent) {
                this.transparent = transparent;
                this.setOpaque(!transparent);
                this.setDoubleBuffered(!transparent);
                this.repaint();
            }
            
            public void eventDispatched(final AWTEvent awtEvent) {
                if (awtEvent.getID() == 300 && SwingUtilities.isDescendingFrom(((ContainerEvent)awtEvent).getChild(), this)) {
                    this.this$0.setDoubleBuffered(((ContainerEvent)awtEvent).getChild(), false);
                }
            }
            
            @Override
            public void paint(final Graphics graphics) {
                if (this.transparent) {
                    final Rectangle clipBounds = graphics.getClipBounds();
                    final int width = clipBounds.width;
                    final int height = clipBounds.height;
                    if (this.getWidth() > 0 && this.getHeight() > 0) {
                        final BufferedImage bufferedImage = new BufferedImage(width, height, 3);
                        final Graphics2D graphics2 = bufferedImage.createGraphics();
                        graphics2.setComposite(AlphaComposite.Clear);
                        graphics2.fillRect(0, 0, width, height);
                        graphics2.dispose();
                        final Graphics2D graphics3 = bufferedImage.createGraphics();
                        graphics3.translate(-clipBounds.x, -clipBounds.y);
                        super.paint(graphics3);
                        graphics3.dispose();
                        this.paintDirect(bufferedImage, clipBounds);
                    }
                }
                else {
                    super.paint(graphics);
                }
            }
            
            protected abstract void paintDirect(final BufferedImage p0, final Rectangle p1);
        }
    }
    
    private static class HeavyweightForcer extends Window
    {
        private static final long serialVersionUID = 1L;
        private final boolean packed;
        
        public HeavyweightForcer(final Window window) {
            super(window);
            this.pack();
            this.packed = true;
        }
        
        @Override
        public boolean isVisible() {
            return this.packed;
        }
        
        @Override
        public Rectangle getBounds() {
            return this.getOwner().getBounds();
        }
    }
    
    private static class Holder
    {
        public static boolean requiresVisible;
        public static final NativeWindowUtils INSTANCE;
        
        static {
            if (Platform.isWindows()) {
                INSTANCE = new W32WindowUtils(null);
            }
            else if (Platform.isMac()) {
                INSTANCE = new MacWindowUtils(null);
            }
            else {
                if (!Platform.isX11()) {
                    throw new UnsupportedOperationException("No support for " + System.getProperty("os.name"));
                }
                INSTANCE = new X11WindowUtils(null);
                Holder.requiresVisible = System.getProperty("java.version").matches("^1\\.4\\..*");
            }
        }
    }
    
    private static class W32WindowUtils extends NativeWindowUtils
    {
        private W32WindowUtils() {
        }
        
        private WinDef.HWND getHWnd(final Component component) {
            final WinDef.HWND hwnd = new WinDef.HWND();
            hwnd.setPointer(Native.getComponentPointer(component));
            return hwnd;
        }
        
        @Override
        public boolean isWindowAlphaSupported() {
            return Boolean.getBoolean("sun.java2d.noddraw");
        }
        
        private boolean usingUpdateLayeredWindow(final Window window) {
            return window instanceof RootPaneContainer && ((RootPaneContainer)window).getRootPane().getClientProperty("transparent-old-bg") != null;
        }
        
        private void storeAlpha(final Window window, final byte b) {
            if (window instanceof RootPaneContainer) {
                ((RootPaneContainer)window).getRootPane().putClientProperty("transparent-alpha", (b == -1) ? null : new Byte(b));
            }
        }
        
        private byte getAlpha(final Window window) {
            if (window instanceof RootPaneContainer) {
                final Byte b = (Byte)((RootPaneContainer)window).getRootPane().getClientProperty("transparent-alpha");
                if (b != null) {
                    return b;
                }
            }
            return -1;
        }
        
        @Override
        public void setWindowAlpha(final Window window, final float n) {
            if (!this.isWindowAlphaSupported()) {
                throw new UnsupportedOperationException("Set sun.java2d.noddraw=true to enable transparent windows");
            }
            this.whenDisplayable(window, new Runnable(window, n) {
                final Window val$w;
                final float val$alpha;
                final W32WindowUtils this$0;
                
                public void run() {
                    final WinDef.HWND access$400 = W32WindowUtils.access$400(this.this$0, this.val$w);
                    final User32 instance = User32.INSTANCE;
                    final int getWindowLong = instance.GetWindowLong(access$400, -20);
                    final byte sourceConstantAlpha = (byte)((int)(255.0f * this.val$alpha) & 0xFF);
                    if (W32WindowUtils.access$500(this.this$0, this.val$w)) {
                        final WinUser.BLENDFUNCTION blendfunction = new WinUser.BLENDFUNCTION();
                        blendfunction.SourceConstantAlpha = sourceConstantAlpha;
                        blendfunction.AlphaFormat = 1;
                        instance.UpdateLayeredWindow(access$400, null, null, null, null, null, 0, blendfunction, 2);
                    }
                    else if (this.val$alpha == 1.0f) {
                        instance.SetWindowLong(access$400, -20, getWindowLong & 0xFFF7FFFF);
                    }
                    else {
                        instance.SetWindowLong(access$400, -20, getWindowLong | 0x80000);
                        instance.SetLayeredWindowAttributes(access$400, 0, sourceConstantAlpha, 2);
                    }
                    this.this$0.setForceHeavyweightPopups(this.val$w, this.val$alpha != 1.0f);
                    W32WindowUtils.access$600(this.this$0, this.val$w, sourceConstantAlpha);
                }
            });
        }
        
        @Override
        public void setWindowTransparent(final Window window, final boolean b) {
            if (!(window instanceof RootPaneContainer)) {
                throw new IllegalArgumentException("Window must be a RootPaneContainer");
            }
            if (!this.isWindowAlphaSupported()) {
                throw new UnsupportedOperationException("Set sun.java2d.noddraw=true to enable transparent windows");
            }
            if (b == (window.getBackground() != null && window.getBackground().getAlpha() == 0)) {
                return;
            }
            this.whenDisplayable(window, new Runnable(window, b) {
                final Window val$w;
                final boolean val$transparent;
                final W32WindowUtils this$0;
                
                public void run() {
                    final User32 instance = User32.INSTANCE;
                    final WinDef.HWND access$400 = W32WindowUtils.access$400(this.this$0, this.val$w);
                    final int getWindowLong = instance.GetWindowLong(access$400, -20);
                    final JRootPane rootPane = ((RootPaneContainer)this.val$w).getRootPane();
                    final JLayeredPane layeredPane = rootPane.getLayeredPane();
                    final Container contentPane = rootPane.getContentPane();
                    if (contentPane instanceof W32TransparentContentPane) {
                        ((W32TransparentContentPane)contentPane).setTransparent(this.val$transparent);
                    }
                    else if (this.val$transparent) {
                        final W32TransparentContentPane contentPane2 = this.this$0.new W32TransparentContentPane(contentPane);
                        rootPane.setContentPane(contentPane2);
                        layeredPane.add(new RepaintTrigger(contentPane2), JLayeredPane.DRAG_LAYER);
                    }
                    if (this.val$transparent && !W32WindowUtils.access$500(this.this$0, this.val$w)) {
                        instance.SetWindowLong(access$400, -20, getWindowLong | 0x80000);
                    }
                    else if (!this.val$transparent && W32WindowUtils.access$500(this.this$0, this.val$w)) {
                        instance.SetWindowLong(access$400, -20, getWindowLong & 0xFFF7FFFF);
                    }
                    this.this$0.setLayersTransparent(this.val$w, this.val$transparent);
                    this.this$0.setForceHeavyweightPopups(this.val$w, this.val$transparent);
                    this.this$0.setDoubleBuffered(this.val$w, !this.val$transparent);
                }
            });
        }
        
        @Override
        public void setWindowMask(final Component component, final Shape shape) {
            if (shape instanceof Area && ((Area)shape).isPolygonal()) {
                this.setMask(component, (Area)shape);
            }
            else {
                super.setWindowMask(component, shape);
            }
        }
        
        private void setWindowRegion(final Component component, final WinDef.HRGN hrgn) {
            this.whenDisplayable(component, new Runnable(component, hrgn) {
                final Component val$w;
                final WinDef.HRGN val$hrgn;
                final W32WindowUtils this$0;
                
                public void run() {
                    final GDI32 instance = GDI32.INSTANCE;
                    User32.INSTANCE.SetWindowRgn(W32WindowUtils.access$400(this.this$0, this.val$w), this.val$hrgn, true);
                    this.this$0.setForceHeavyweightPopups(this.this$0.getWindow(this.val$w), this.val$hrgn != null);
                    instance.DeleteObject(this.val$hrgn);
                }
            });
        }
        
        private void setMask(final Component component, final Area area) {
            final GDI32 instance = GDI32.INSTANCE;
            final PathIterator pathIterator = area.getPathIterator(null);
            final int n = (pathIterator.getWindingRule() == 1) ? 2 : 1;
            final float[] array = new float[6];
            final ArrayList<WinUser.POINT> list = new ArrayList<WinUser.POINT>();
            final ArrayList<Integer> list2 = new ArrayList<Integer>();
            while (!pathIterator.isDone()) {
                final int currentSegment = pathIterator.currentSegment(array);
                if (currentSegment == 0) {
                    list.add(new WinUser.POINT((int)array[0], (int)array[1]));
                }
                else if (currentSegment == 1) {
                    int n2 = 0;
                    ++n2;
                    list.add(new WinUser.POINT((int)array[0], (int)array[1]));
                }
                else {
                    if (currentSegment != 4) {
                        throw new RuntimeException("Area is not polygonal: " + area);
                    }
                    list2.add(new Integer(1));
                }
                pathIterator.next();
            }
            final WinUser.POINT[] array2 = (WinUser.POINT[])new WinUser.POINT().toArray(list.size());
            final WinUser.POINT[] array3 = list.toArray(new WinUser.POINT[list.size()]);
            while (0 < array2.length) {
                array2[0].x = array3[0].x;
                array2[0].y = array3[0].y;
                int n3 = 0;
                ++n3;
            }
            final int[] array4 = new int[list2.size()];
            while (0 < array4.length) {
                array4[0] = list2.get(0);
                int n4 = 0;
                ++n4;
            }
            this.setWindowRegion(component, instance.CreatePolyPolygonRgn(array2, array4, array4.length, n));
        }
        
        @Override
        protected void setMask(final Component component, final Raster raster) {
            final GDI32 instance = GDI32.INSTANCE;
            final WinDef.HRGN hrgn = (raster != null) ? instance.CreateRectRgn(0, 0, 0, 0) : null;
            if (hrgn != null) {
                final WinDef.HRGN createRectRgn = instance.CreateRectRgn(0, 0, 0, 0);
                RasterRangesUtils.outputOccupiedRanges(raster, new RasterRangesUtils.RangesOutput(createRectRgn, hrgn) {
                    final WinDef.HRGN val$tempRgn;
                    final WinDef.HRGN val$region;
                    final W32WindowUtils this$0;
                    
                    public boolean outputRange(final int n, final int n2, final int n3, final int n4) {
                        final GDI32 instance = GDI32.INSTANCE;
                        instance.SetRectRgn(this.val$tempRgn, n, n2, n + n3, n2 + n4);
                        return instance.CombineRgn(this.val$region, this.val$region, this.val$tempRgn, 2) != 0;
                    }
                });
                instance.DeleteObject(createRectRgn);
            }
            this.setWindowRegion(component, hrgn);
        }
        
        W32WindowUtils(final WindowUtils$1 object) {
            this();
        }
        
        static WinDef.HWND access$400(final W32WindowUtils w32WindowUtils, final Component component) {
            return w32WindowUtils.getHWnd(component);
        }
        
        static boolean access$500(final W32WindowUtils w32WindowUtils, final Window window) {
            return w32WindowUtils.usingUpdateLayeredWindow(window);
        }
        
        static void access$600(final W32WindowUtils w32WindowUtils, final Window window, final byte b) {
            w32WindowUtils.storeAlpha(window, b);
        }
        
        static byte access$700(final W32WindowUtils w32WindowUtils, final Window window) {
            return w32WindowUtils.getAlpha(window);
        }
        
        private class W32TransparentContentPane extends TransparentContentPane
        {
            private static final long serialVersionUID = 1L;
            private WinDef.HDC memDC;
            private WinDef.HBITMAP hBitmap;
            private Pointer pbits;
            private Dimension bitmapSize;
            final W32WindowUtils this$0;
            
            public W32TransparentContentPane(final W32WindowUtils this$0, final Container container) {
                this.this$0 = this$0.super(container);
            }
            
            private void disposeBackingStore() {
                final GDI32 instance = GDI32.INSTANCE;
                if (this.hBitmap != null) {
                    instance.DeleteObject(this.hBitmap);
                    this.hBitmap = null;
                }
                if (this.memDC != null) {
                    instance.DeleteDC(this.memDC);
                    this.memDC = null;
                }
            }
            
            @Override
            public void removeNotify() {
                super.removeNotify();
                this.disposeBackingStore();
            }
            
            @Override
            public void setTransparent(final boolean transparent) {
                super.setTransparent(transparent);
                if (!transparent) {
                    this.disposeBackingStore();
                }
            }
            
            @Override
            protected void paintDirect(final BufferedImage bufferedImage, final Rectangle rectangle) {
                final Window windowAncestor = SwingUtilities.getWindowAncestor(this);
                final GDI32 instance = GDI32.INSTANCE;
                final User32 instance2 = User32.INSTANCE;
                final Point convertPoint = SwingUtilities.convertPoint(this, rectangle.x, rectangle.y, windowAncestor);
                final int width = rectangle.width;
                final int height = rectangle.height;
                final int width2 = windowAncestor.getWidth();
                final int height2 = windowAncestor.getHeight();
                final WinDef.HDC getDC = instance2.GetDC(null);
                if (this.memDC == null) {
                    this.memDC = instance.CreateCompatibleDC(getDC);
                }
                if (this.hBitmap == null || !windowAncestor.getSize().equals(this.bitmapSize)) {
                    if (this.hBitmap != null) {
                        instance.DeleteObject(this.hBitmap);
                        this.hBitmap = null;
                    }
                    final WinGDI.BITMAPINFO bitmapinfo = new WinGDI.BITMAPINFO();
                    bitmapinfo.bmiHeader.biWidth = width2;
                    bitmapinfo.bmiHeader.biHeight = height2;
                    bitmapinfo.bmiHeader.biPlanes = 1;
                    bitmapinfo.bmiHeader.biBitCount = 32;
                    bitmapinfo.bmiHeader.biCompression = 0;
                    bitmapinfo.bmiHeader.biSizeImage = width2 * height2 * 4;
                    final PointerByReference pointerByReference = new PointerByReference();
                    this.hBitmap = instance.CreateDIBSection(this.memDC, bitmapinfo, 0, pointerByReference, null, 0);
                    this.pbits = pointerByReference.getValue();
                    this.bitmapSize = new Dimension(width2, height2);
                }
                final WinNT.HANDLE selectObject = instance.SelectObject(this.memDC, this.hBitmap);
                final Raster data = bufferedImage.getData();
                final int[] array = new int[4];
                final int[] array2 = new int[width];
                while (0 < height) {
                    while (0 < width) {
                        data.getPixel(0, 0, array);
                        array2[0] = ((array[3] & 0xFF) << 24 | (array[2] & 0xFF) | (array[1] & 0xFF) << 8 | (array[0] & 0xFF) << 16);
                        int n = 0;
                        ++n;
                    }
                    int n = height2 - (convertPoint.y + 0) - 1;
                    this.pbits.write((0 * width2 + convertPoint.x) * 4, array2, 0, array2.length);
                    int n2 = 0;
                    ++n2;
                }
                final WinUser.SIZE size = new WinUser.SIZE();
                size.cx = windowAncestor.getWidth();
                size.cy = windowAncestor.getHeight();
                final WinUser.POINT point = new WinUser.POINT();
                point.x = windowAncestor.getX();
                point.y = windowAncestor.getY();
                final WinUser.POINT point2 = new WinUser.POINT();
                final WinUser.BLENDFUNCTION blendfunction = new WinUser.BLENDFUNCTION();
                final WinDef.HWND access$400 = W32WindowUtils.access$400(this.this$0, windowAncestor);
                final ByteByReference byteByReference = new ByteByReference();
                final IntByReference intByReference = new IntByReference();
                byte sourceConstantAlpha = W32WindowUtils.access$700(this.this$0, windowAncestor);
                if (instance2.GetLayeredWindowAttributes(access$400, null, byteByReference, intByReference) && (intByReference.getValue() & 0x2) != 0x0) {
                    sourceConstantAlpha = byteByReference.getValue();
                }
                blendfunction.SourceConstantAlpha = sourceConstantAlpha;
                blendfunction.AlphaFormat = 1;
                instance2.UpdateLayeredWindow(access$400, getDC, point, size, this.memDC, point2, 0, blendfunction, 2);
                instance2.ReleaseDC(null, getDC);
                if (this.memDC != null && selectObject != null) {
                    instance.SelectObject(this.memDC, selectObject);
                }
            }
        }
    }
    
    protected static class RepaintTrigger extends JComponent
    {
        private static final long serialVersionUID = 1L;
        private final Listener listener;
        private final JComponent content;
        private Rectangle dirty;
        
        public RepaintTrigger(final JComponent content) {
            this.listener = this.createListener();
            this.content = content;
        }
        
        @Override
        public void addNotify() {
            super.addNotify();
            final Window windowAncestor = SwingUtilities.getWindowAncestor(this);
            this.setSize(this.getParent().getSize());
            windowAncestor.addComponentListener(this.listener);
            windowAncestor.addWindowListener(this.listener);
            Toolkit.getDefaultToolkit().addAWTEventListener(this.listener, 48L);
        }
        
        @Override
        public void removeNotify() {
            Toolkit.getDefaultToolkit().removeAWTEventListener(this.listener);
            final Window windowAncestor = SwingUtilities.getWindowAncestor(this);
            windowAncestor.removeComponentListener(this.listener);
            windowAncestor.removeWindowListener(this.listener);
            super.removeNotify();
        }
        
        @Override
        protected void paintComponent(final Graphics graphics) {
            final Rectangle clipBounds = graphics.getClipBounds();
            if (this.dirty == null || !this.dirty.contains(clipBounds)) {
                if (this.dirty == null) {
                    this.dirty = clipBounds;
                }
                else {
                    this.dirty = this.dirty.union(clipBounds);
                }
                this.content.repaint(this.dirty);
            }
            else {
                this.dirty = null;
            }
        }
        
        protected Listener createListener() {
            return new Listener();
        }
        
        static JComponent access$000(final RepaintTrigger repaintTrigger) {
            return repaintTrigger.content;
        }
        
        protected class Listener extends WindowAdapter implements ComponentListener, HierarchyListener, AWTEventListener
        {
            final RepaintTrigger this$0;
            
            protected Listener(final RepaintTrigger this$0) {
                this.this$0 = this$0;
            }
            
            @Override
            public void windowOpened(final WindowEvent windowEvent) {
                this.this$0.repaint();
            }
            
            public void componentHidden(final ComponentEvent componentEvent) {
            }
            
            public void componentMoved(final ComponentEvent componentEvent) {
            }
            
            public void componentResized(final ComponentEvent componentEvent) {
                this.this$0.setSize(this.this$0.getParent().getSize());
                this.this$0.repaint();
            }
            
            public void componentShown(final ComponentEvent componentEvent) {
                this.this$0.repaint();
            }
            
            public void hierarchyChanged(final HierarchyEvent hierarchyEvent) {
                this.this$0.repaint();
            }
            
            public void eventDispatched(final AWTEvent awtEvent) {
                if (awtEvent instanceof MouseEvent) {
                    final Component component = ((MouseEvent)awtEvent).getComponent();
                    if (component != null && SwingUtilities.isDescendingFrom(component, RepaintTrigger.access$000(this.this$0))) {
                        final MouseEvent convertMouseEvent = SwingUtilities.convertMouseEvent(component, (MouseEvent)awtEvent, RepaintTrigger.access$000(this.this$0));
                        final Component deepestComponent = SwingUtilities.getDeepestComponentAt(RepaintTrigger.access$000(this.this$0), convertMouseEvent.getX(), convertMouseEvent.getY());
                        if (deepestComponent != null) {
                            this.this$0.setCursor(deepestComponent.getCursor());
                        }
                    }
                }
            }
        }
    }
    
    private static class MacWindowUtils extends NativeWindowUtils
    {
        private static final String WDRAG = "apple.awt.draggableWindowBackground";
        
        private MacWindowUtils() {
        }
        
        @Override
        public boolean isWindowAlphaSupported() {
            return true;
        }
        
        private OSXMaskingContentPane installMaskingPane(final Window window) {
            OSXMaskingContentPane contentPane2;
            if (window instanceof RootPaneContainer) {
                final RootPaneContainer rootPaneContainer = (RootPaneContainer)window;
                final Container contentPane = rootPaneContainer.getContentPane();
                if (contentPane instanceof OSXMaskingContentPane) {
                    contentPane2 = (OSXMaskingContentPane)contentPane;
                }
                else {
                    contentPane2 = new OSXMaskingContentPane(contentPane);
                    rootPaneContainer.setContentPane(contentPane2);
                }
            }
            else {
                final Component component = (window.getComponentCount() > 0) ? window.getComponent(0) : null;
                if (component instanceof OSXMaskingContentPane) {
                    contentPane2 = (OSXMaskingContentPane)component;
                }
                else {
                    contentPane2 = new OSXMaskingContentPane(component);
                    window.add(contentPane2);
                }
            }
            return contentPane2;
        }
        
        @Override
        public void setWindowTransparent(final Window window, final boolean b) {
            if (b != (window.getBackground() != null && window.getBackground().getAlpha() == 0)) {
                this.setBackgroundTransparent(window, b, "setWindowTransparent");
            }
        }
        
        private void fixWindowDragging(final Window window, final String s) {
            if (window instanceof RootPaneContainer) {
                final JRootPane rootPane = ((RootPaneContainer)window).getRootPane();
                if (rootPane.getClientProperty("apple.awt.draggableWindowBackground") == null) {
                    rootPane.putClientProperty("apple.awt.draggableWindowBackground", Boolean.FALSE);
                    if (window.isDisplayable()) {
                        System.err.println(s + "(): To avoid content dragging, " + s + "() must be called before the window is realized, or " + "apple.awt.draggableWindowBackground" + " must be set to Boolean.FALSE before the window is realized.  If you really want content dragging, set " + "apple.awt.draggableWindowBackground" + " on the window's root pane to Boolean.TRUE before calling " + s + "() to hide this message.");
                    }
                }
            }
        }
        
        @Override
        public void setWindowAlpha(final Window window, final float n) {
            if (window instanceof RootPaneContainer) {
                ((RootPaneContainer)window).getRootPane().putClientProperty("Window.alpha", new Float(n));
                this.fixWindowDragging(window, "setWindowAlpha");
            }
            this.whenDisplayable(window, new Runnable(window, n) {
                final Window val$w;
                final float val$alpha;
                final MacWindowUtils this$0;
                
                public void run() {
                    final ComponentPeer peer = this.val$w.getPeer();
                    peer.getClass().getMethod("setAlpha", Float.TYPE).invoke(peer, new Float(this.val$alpha));
                }
            });
        }
        
        @Override
        protected void setWindowMask(final Component component, final Raster raster) {
            if (raster != null) {
                this.setWindowMask(component, this.toShape(raster));
            }
            else {
                this.setWindowMask(component, new Rectangle(0, 0, component.getWidth(), component.getHeight()));
            }
        }
        
        @Override
        public void setWindowMask(final Component component, final Shape mask) {
            if (component instanceof Window) {
                final Window window = (Window)component;
                this.installMaskingPane(window).setMask(mask);
                this.setBackgroundTransparent(window, mask != WindowUtils.MASK_NONE, "setWindowMask");
            }
        }
        
        private void setBackgroundTransparent(final Window window, final boolean b, final String s) {
            final JRootPane rootPane = (window instanceof RootPaneContainer) ? ((RootPaneContainer)window).getRootPane() : null;
            if (b) {
                if (rootPane != null) {
                    rootPane.putClientProperty("transparent-old-bg", window.getBackground());
                }
                window.setBackground(new Color(0, 0, 0, 0));
            }
            else if (rootPane != null) {
                Color background = (Color)rootPane.getClientProperty("transparent-old-bg");
                if (background != null) {
                    background = new Color(background.getRed(), background.getGreen(), background.getBlue(), background.getAlpha());
                }
                window.setBackground(background);
                rootPane.putClientProperty("transparent-old-bg", null);
            }
            else {
                window.setBackground(null);
            }
            this.fixWindowDragging(window, s);
        }
        
        MacWindowUtils(final WindowUtils$1 object) {
            this();
        }
        
        private static class OSXMaskingContentPane extends JPanel
        {
            private static final long serialVersionUID = 1L;
            private Shape shape;
            
            public OSXMaskingContentPane(final Component component) {
                super(new BorderLayout());
                if (component != null) {
                    this.add(component, "Center");
                }
            }
            
            public void setMask(final Shape shape) {
                this.shape = shape;
                this.repaint();
            }
            
            @Override
            public void paint(final Graphics graphics) {
                final Graphics2D graphics2D = (Graphics2D)graphics.create();
                graphics2D.setComposite(AlphaComposite.Clear);
                graphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
                graphics2D.dispose();
                if (this.shape != null) {
                    final Graphics2D graphics2D2 = (Graphics2D)graphics.create();
                    graphics2D2.setClip(this.shape);
                    super.paint(graphics2D2);
                    graphics2D2.dispose();
                }
                else {
                    super.paint(graphics);
                }
            }
        }
    }
}
