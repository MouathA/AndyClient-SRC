package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

abstract class MacOSXCanvasPeerInfo extends MacOSXPeerInfo
{
    private final AWTSurfaceLock awt_surface;
    public ByteBuffer window_handle;
    
    protected MacOSXCanvasPeerInfo(final PixelFormat pixelFormat, final ContextAttribs contextAttribs, final boolean b) throws LWJGLException {
        super(pixelFormat, contextAttribs, true, true, b, true);
        this.awt_surface = new AWTSurfaceLock();
    }
    
    protected void initHandle(final Canvas canvas) throws LWJGLException {
        final String property = System.getProperty("java.version");
        if (!property.startsWith("1.5") && !property.startsWith("1.6")) {
            if (property.startsWith("1.7")) {}
        }
        final Insets insets = getInsets(canvas);
        this.window_handle = nInitHandle(this.awt_surface.lockAndGetHandle(canvas), this.getHandle(), this.window_handle, false, false, canvas.getX() - ((insets != null) ? insets.left : 0), canvas.getY() - ((insets != null) ? insets.top : 0));
        if (property.startsWith("1.7")) {
            this.addComponentListener(canvas);
            reSetLayerBounds(canvas, this.getHandle());
        }
    }
    
    private void addComponentListener(final Canvas canvas) {
        final ComponentListener[] componentListeners = canvas.getComponentListeners();
        while (0 < componentListeners.length) {
            if (componentListeners[0].toString() == "CanvasPeerInfoListener") {
                return;
            }
            int n = 0;
            ++n;
        }
        canvas.addComponentListener(new ComponentListener(canvas) {
            final Canvas val$component;
            final MacOSXCanvasPeerInfo this$0;
            
            public void componentHidden(final ComponentEvent componentEvent) {
            }
            
            public void componentMoved(final ComponentEvent componentEvent) {
                MacOSXCanvasPeerInfo.access$000(this.val$component, this.this$0.getHandle());
            }
            
            public void componentResized(final ComponentEvent componentEvent) {
                MacOSXCanvasPeerInfo.access$000(this.val$component, this.this$0.getHandle());
            }
            
            public void componentShown(final ComponentEvent componentEvent) {
            }
            
            @Override
            public String toString() {
                return "CanvasPeerInfoListener";
            }
        });
    }
    
    private static native ByteBuffer nInitHandle(final ByteBuffer p0, final ByteBuffer p1, final ByteBuffer p2, final boolean p3, final boolean p4, final int p5, final int p6) throws LWJGLException;
    
    private static native void nSetLayerPosition(final ByteBuffer p0, final int p1, final int p2);
    
    private static native void nSetLayerBounds(final ByteBuffer p0, final int p1, final int p2, final int p3, final int p4);
    
    private static void reSetLayerBounds(final Canvas p0, final ByteBuffer p1) {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
        // 
        // The error that occurred was:
        // 
        // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'org/lwjgl/opengl/MacOSXCanvasPeerInfo.reSetLayerBounds:(Ljava/awt/Canvas;Ljava/nio/ByteBuffer;)V'.
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:66)
        //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:729)
        //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
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
        // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 49.
        //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
        //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:235)
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
        //     ... 17 more
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void doUnlock() throws LWJGLException {
        this.awt_surface.unlock();
    }
    
    private static Insets getInsets(final Canvas canvas) {
        final JRootPane rootPane = SwingUtilities.getRootPane(canvas);
        if (rootPane != null) {
            return rootPane.getInsets();
        }
        return new Insets(0, 0, 0, 0);
    }
    
    static void access$000(final Canvas canvas, final ByteBuffer byteBuffer) {
        reSetLayerBounds(canvas, byteBuffer);
    }
}
