package com.sun.jna.platform.dnd;

import java.awt.datatransfer.*;
import javax.swing.text.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.dnd.*;

public abstract class DragHandler implements DragSourceListener, DragSourceMotionListener, DragGestureListener
{
    public static final Dimension MAX_GHOST_SIZE;
    public static final float DEFAULT_GHOST_ALPHA = 0.5f;
    public static final int UNKNOWN_MODIFIERS = -1;
    public static final Transferable UNKNOWN_TRANSFERABLE;
    protected static final int MOVE = 2;
    protected static final int COPY = 1;
    protected static final int LINK = 1073741824;
    protected static final int NONE = 0;
    static final int MOVE_MASK = 64;
    static final boolean OSX;
    static final int KEY_MASK = 9152;
    private static Transferable transferable;
    private int supportedActions;
    private boolean fixCursor;
    private Component dragSource;
    private GhostedDragImage ghost;
    private Point imageOffset;
    private Dimension maxGhostSize;
    private float ghostAlpha;
    private String lastAction;
    private boolean moved;
    
    static int getModifiers() {
        return -1;
    }
    
    public static Transferable getTransferable(final DropTargetEvent dropTargetEvent) {
        if (dropTargetEvent instanceof DropTargetDragEvent) {
            return (Transferable)dropTargetEvent.getClass().getMethod("getTransferable", (Class<?>[])null).invoke(dropTargetEvent, (Object[])null);
        }
        if (dropTargetEvent instanceof DropTargetDropEvent) {
            return ((DropTargetDropEvent)dropTargetEvent).getTransferable();
        }
        return DragHandler.transferable;
    }
    
    protected DragHandler(final Component dragSource, final int supportedActions) {
        this.fixCursor = true;
        this.maxGhostSize = DragHandler.MAX_GHOST_SIZE;
        this.ghostAlpha = 0.5f;
        this.dragSource = dragSource;
        this.supportedActions = supportedActions;
        final String property = System.getProperty("DragHandler.alpha");
        if (property != null) {
            this.ghostAlpha = Float.parseFloat(property);
        }
        final String property2 = System.getProperty("DragHandler.maxDragImageSize");
        if (property2 != null) {
            final String[] split = property2.split("x");
            if (split.length == 2) {
                this.maxGhostSize = new Dimension(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            }
        }
        this.disableSwingDragSupport(dragSource);
        DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(dragSource, this.supportedActions, this);
    }
    
    private void disableSwingDragSupport(final Component component) {
        if (component instanceof JTree) {
            ((JTree)component).setDragEnabled(false);
        }
        else if (component instanceof JList) {
            ((JList)component).setDragEnabled(false);
        }
        else if (component instanceof JTable) {
            ((JTable)component).setDragEnabled(false);
        }
        else if (component instanceof JTextComponent) {
            ((JTextComponent)component).setDragEnabled(false);
        }
        else if (component instanceof JColorChooser) {
            ((JColorChooser)component).setDragEnabled(false);
        }
        else if (component instanceof JFileChooser) {
            ((JFileChooser)component).setDragEnabled(false);
        }
    }
    
    protected boolean canDrag(final DragGestureEvent dragGestureEvent) {
        final int n = dragGestureEvent.getTriggerEvent().getModifiersEx() & 0x23C0;
        if (n == 64) {
            return (this.supportedActions & 0x2) != 0x0;
        }
        if (n == 128) {
            return (this.supportedActions & 0x1) != 0x0;
        }
        return n != 192 || (this.supportedActions & 0x40000000) != 0x0;
    }
    
    protected void setModifiers(final int modifiers) {
        DragHandler.modifiers = modifiers;
    }
    
    protected abstract Transferable getTransferable(final DragGestureEvent p0);
    
    protected Icon getDragIcon(final DragGestureEvent dragGestureEvent, final Point point) {
        return null;
    }
    
    protected void dragStarted(final DragGestureEvent dragGestureEvent) {
    }
    
    public void dragGestureRecognized(final DragGestureEvent dragGestureEvent) {
        if ((dragGestureEvent.getDragAction() & this.supportedActions) != 0x0 && this.canDrag(dragGestureEvent)) {
            this.setModifiers(dragGestureEvent.getTriggerEvent().getModifiersEx() & 0x23C0);
            final Transferable transferable = this.getTransferable(dragGestureEvent);
            if (transferable == null) {
                return;
            }
            final Point point = new Point(0, 0);
            final Icon dragIcon = this.getDragIcon(dragGestureEvent, point);
            final Point dragOrigin = dragGestureEvent.getDragOrigin();
            this.imageOffset = new Point(point.x - dragOrigin.x, point.y - dragOrigin.y);
            final Icon scaleDragIcon = this.scaleDragIcon(dragIcon, this.imageOffset);
            final Cursor cursor = null;
            if (scaleDragIcon != null && DragSource.isDragImageSupported()) {
                dragGestureEvent.startDrag(cursor, this.createDragImage(dragGestureEvent.getComponent().getGraphicsConfiguration(), scaleDragIcon), this.imageOffset, transferable, this);
            }
            else {
                if (scaleDragIcon != null) {
                    final Point locationOnScreen = this.dragSource.getLocationOnScreen();
                    locationOnScreen.translate(dragOrigin.x, dragOrigin.y);
                    (this.ghost = new GhostedDragImage(this.dragSource, scaleDragIcon, this.getImageLocation(locationOnScreen), new Point(-this.imageOffset.x, -this.imageOffset.y))).setAlpha(this.ghostAlpha);
                }
                dragGestureEvent.startDrag(cursor, transferable, this);
            }
            this.dragStarted(dragGestureEvent);
            this.moved = false;
            dragGestureEvent.getDragSource().addDragSourceMotionListener(this);
            DragHandler.transferable = transferable;
        }
    }
    
    protected Icon scaleDragIcon(final Icon icon, final Point point) {
        return icon;
    }
    
    protected Image createDragImage(final GraphicsConfiguration graphicsConfiguration, final Icon icon) {
        final int iconWidth = icon.getIconWidth();
        final int iconHeight = icon.getIconHeight();
        final BufferedImage compatibleImage = graphicsConfiguration.createCompatibleImage(iconWidth, iconHeight, 3);
        final Graphics2D graphics2D = (Graphics2D)compatibleImage.getGraphics();
        graphics2D.setComposite(AlphaComposite.Clear);
        graphics2D.fillRect(0, 0, iconWidth, iconHeight);
        graphics2D.setComposite(AlphaComposite.getInstance(2, this.ghostAlpha));
        icon.paintIcon(this.dragSource, graphics2D, 0, 0);
        graphics2D.dispose();
        return compatibleImage;
    }
    
    private int reduce(final int n) {
        if ((n & 0x2) != 0x0 && n != 2) {
            return 2;
        }
        if ((n & 0x1) != 0x0 && n != 1) {
            return 1;
        }
        return n;
    }
    
    protected Cursor getCursorForAction(final int n) {
        switch (n) {
            case 2: {
                return DragSource.DefaultMoveDrop;
            }
            case 1: {
                return DragSource.DefaultCopyDrop;
            }
            case 1073741824: {
                return DragSource.DefaultLinkDrop;
            }
            default: {
                return DragSource.DefaultMoveNoDrop;
            }
        }
    }
    
    protected int getAcceptableDropAction(final int n) {
        return this.reduce(this.supportedActions & n);
    }
    
    protected int getDropAction(final DragSourceEvent dragSourceEvent) {
        if (dragSourceEvent instanceof DragSourceDragEvent) {
            return ((DragSourceDragEvent)dragSourceEvent).getDropAction();
        }
        if (dragSourceEvent instanceof DragSourceDropEvent) {
            return ((DragSourceDropEvent)dragSourceEvent).getDropAction();
        }
        return 0;
    }
    
    protected int adjustDropAction(final DragSourceEvent dragSourceEvent) {
        int n = this.getDropAction(dragSourceEvent);
        if (dragSourceEvent instanceof DragSourceDragEvent) {
            final DragSourceDragEvent dragSourceDragEvent = (DragSourceDragEvent)dragSourceEvent;
            if (n == 0 && (dragSourceDragEvent.getGestureModifiersEx() & 0x23C0) == 0x0) {
                n = this.getAcceptableDropAction(dragSourceDragEvent.getTargetActions());
            }
        }
        return n;
    }
    
    protected void updateCursor(final DragSourceEvent dragSourceEvent) {
        if (!this.fixCursor) {
            return;
        }
        dragSourceEvent.getDragSourceContext().setCursor(this.getCursorForAction(this.adjustDropAction(dragSourceEvent)));
    }
    
    static String actionString(final int n) {
        switch (n) {
            case 2: {
                return "MOVE";
            }
            case 3: {
                return "MOVE|COPY";
            }
            case 1073741826: {
                return "MOVE|LINK";
            }
            case 1073741827: {
                return "MOVE|COPY|LINK";
            }
            case 1: {
                return "COPY";
            }
            case 1073741825: {
                return "COPY|LINK";
            }
            case 1073741824: {
                return "LINK";
            }
            default: {
                return "NONE";
            }
        }
    }
    
    private void describe(final String s, final DragSourceEvent dragSourceEvent) {
    }
    
    public void dragDropEnd(final DragSourceDropEvent dragSourceDropEvent) {
        this.describe("end", dragSourceDropEvent);
        this.setModifiers(-1);
        DragHandler.transferable = DragHandler.UNKNOWN_TRANSFERABLE;
        if (this.ghost != null) {
            if (dragSourceDropEvent.getDropSuccess()) {
                this.ghost.dispose();
            }
            else {
                this.ghost.returnToOrigin();
            }
            this.ghost = null;
        }
        dragSourceDropEvent.getDragSourceContext().getDragSource().removeDragSourceMotionListener(this);
        this.moved = false;
    }
    
    private Point getImageLocation(final Point point) {
        point.translate(this.imageOffset.x, this.imageOffset.y);
        return point;
    }
    
    public void dragEnter(final DragSourceDragEvent dragSourceDragEvent) {
        this.describe("enter", dragSourceDragEvent);
        if (this.ghost != null) {
            this.ghost.move(this.getImageLocation(dragSourceDragEvent.getLocation()));
        }
        this.updateCursor(dragSourceDragEvent);
    }
    
    public void dragMouseMoved(final DragSourceDragEvent dragSourceDragEvent) {
        this.describe("move", dragSourceDragEvent);
        if (this.ghost != null) {
            this.ghost.move(this.getImageLocation(dragSourceDragEvent.getLocation()));
        }
        if (this.moved) {
            this.updateCursor(dragSourceDragEvent);
        }
        this.moved = true;
    }
    
    public void dragOver(final DragSourceDragEvent dragSourceDragEvent) {
        this.describe("over", dragSourceDragEvent);
        if (this.ghost != null) {
            this.ghost.move(this.getImageLocation(dragSourceDragEvent.getLocation()));
        }
        this.updateCursor(dragSourceDragEvent);
    }
    
    public void dragExit(final DragSourceEvent dragSourceEvent) {
        this.describe("exit", dragSourceEvent);
    }
    
    public void dropActionChanged(final DragSourceDragEvent dragSourceDragEvent) {
        this.describe("change", dragSourceDragEvent);
        this.setModifiers(dragSourceDragEvent.getGestureModifiersEx() & 0x23C0);
        if (this.ghost != null) {
            this.ghost.move(this.getImageLocation(dragSourceDragEvent.getLocation()));
        }
        this.updateCursor(dragSourceDragEvent);
    }
    
    static {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: sipush          250
        //     7: sipush          250
        //    10: invokespecial   java/awt/Dimension.<init>:(II)V
        //    13: putstatic       com/sun/jna/platform/dnd/DragHandler.MAX_GHOST_SIZE:Ljava/awt/Dimension;
        //    16: aconst_null    
        //    17: putstatic       com/sun/jna/platform/dnd/DragHandler.UNKNOWN_TRANSFERABLE:Ljava/awt/datatransfer/Transferable;
        //    20: ldc_w           "os.name"
        //    23: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //    26: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //    29: ldc_w           "mac"
        //    32: invokevirtual   java/lang/String.indexOf:(Ljava/lang/String;)I
        //    35: iconst_m1      
        //    36: if_icmpeq       43
        //    39: iconst_1       
        //    40: goto            44
        //    43: iconst_0       
        //    44: putstatic       com/sun/jna/platform/dnd/DragHandler.OSX:Z
        //    47: getstatic       com/sun/jna/platform/dnd/DragHandler.OSX:Z
        //    50: ifeq            59
        //    53: sipush          512
        //    56: goto            59
        //    59: getstatic       com/sun/jna/platform/dnd/DragHandler.OSX:Z
        //    62: ifeq            71
        //    65: sipush          768
        //    68: goto            71
        //    71: getstatic       com/sun/jna/platform/dnd/DragHandler.UNKNOWN_TRANSFERABLE:Ljava/awt/datatransfer/Transferable;
        //    74: putstatic       com/sun/jna/platform/dnd/DragHandler.transferable:Ljava/awt/datatransfer/Transferable;
        //    77: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0071 (coming from #0068).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
