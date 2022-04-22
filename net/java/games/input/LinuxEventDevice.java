package net.java.games.input;

import java.io.*;
import java.util.*;

final class LinuxEventDevice implements LinuxDevice
{
    private final Map component_map;
    private final Rumbler[] rumblers;
    private final long fd;
    private final String name;
    private final LinuxInputID input_id;
    private final List components;
    private final Controller.Type type;
    private boolean closed;
    private final byte[] key_states;
    static Class class$net$java$games$input$Component$Identifier$Axis;
    static Class class$net$java$games$input$Component$Identifier$Key;
    
    public LinuxEventDevice(final String s) throws IOException {
        this.component_map = new HashMap();
        this.key_states = new byte[64];
        this.fd = nOpen(s, true);
        this.name = this.getDeviceName();
        this.input_id = this.getDeviceInputID();
        this.components = this.getDeviceComponents();
        this.rumblers = new Rumbler[0];
        this.type = this.guessType();
    }
    
    private static final native long nOpen(final String p0, final boolean p1) throws IOException;
    
    public final Controller.Type getType() {
        return this.type;
    }
    
    private static final int countComponents(final List list, final Class clazz, final boolean b) {
        while (0 < list.size()) {
            final LinuxEventComponent linuxEventComponent = list.get(0);
            if (clazz.isInstance(linuxEventComponent.getIdentifier()) && b == linuxEventComponent.isRelative()) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    private final Controller.Type guessType() throws IOException {
        final Controller.Type guessTypeFromUsages = this.guessTypeFromUsages();
        if (guessTypeFromUsages == Controller.Type.UNKNOWN) {
            return this.guessTypeFromComponents();
        }
        return guessTypeFromUsages;
    }
    
    private final Controller.Type guessTypeFromUsages() throws IOException {
        this.getDeviceUsageBits();
        return Controller.Type.STICK;
    }
    
    private final Controller.Type guessTypeFromComponents() throws IOException {
        final List components = this.getComponents();
        if (components.size() == 0) {
            return Controller.Type.UNKNOWN;
        }
        final int countComponents = countComponents(components, (LinuxEventDevice.class$net$java$games$input$Component$Identifier$Axis == null) ? (LinuxEventDevice.class$net$java$games$input$Component$Identifier$Axis = class$("net.java.games.input.Component$Identifier$Axis")) : LinuxEventDevice.class$net$java$games$input$Component$Identifier$Axis, true);
        final int countComponents2 = countComponents(components, (LinuxEventDevice.class$net$java$games$input$Component$Identifier$Axis == null) ? (LinuxEventDevice.class$net$java$games$input$Component$Identifier$Axis = class$("net.java.games.input.Component$Identifier$Axis")) : LinuxEventDevice.class$net$java$games$input$Component$Identifier$Axis, false);
        countComponents(components, (LinuxEventDevice.class$net$java$games$input$Component$Identifier$Key == null) ? (LinuxEventDevice.class$net$java$games$input$Component$Identifier$Key = class$("net.java.games.input.Component$Identifier$Key")) : LinuxEventDevice.class$net$java$games$input$Component$Identifier$Key, false);
        int n = 0;
        if (this.name.toLowerCase().indexOf("mouse") != -1) {
            ++n;
        }
        if (this.name.toLowerCase().indexOf("keyboard") != -1) {
            int n2 = 0;
            ++n2;
        }
        int n3 = 0;
        if (this.name.toLowerCase().indexOf("joystick") != -1) {
            ++n3;
        }
        int n4 = 0;
        if (this.name.toLowerCase().indexOf("gamepad") != -1) {
            ++n4;
        }
        while (0 < components.size()) {
            final LinuxEventComponent linuxEventComponent = components.get(0);
            if (linuxEventComponent.getButtonTrait() == Controller.Type.MOUSE) {
                int n5 = 0;
                ++n5;
            }
            else if (linuxEventComponent.getButtonTrait() == Controller.Type.KEYBOARD) {
                int n6 = 0;
                ++n6;
            }
            else if (linuxEventComponent.getButtonTrait() == Controller.Type.GAMEPAD) {
                int n7 = 0;
                ++n7;
            }
            else if (linuxEventComponent.getButtonTrait() == Controller.Type.STICK) {
                int n8 = 0;
                ++n8;
            }
            int n9 = 0;
            ++n9;
        }
        ++n;
        if (countComponents >= 2) {
            ++n;
        }
        if (countComponents2 >= 2) {
            ++n3;
            ++n4;
        }
        return Controller.Type.MOUSE;
    }
    
    private final Rumbler[] enumerateRumblers() {
        final ArrayList list = new ArrayList();
        if (this.getNumEffects() <= 0) {
            return (Rumbler[])list.toArray(new Rumbler[0]);
        }
        this.getForceFeedbackBits();
        return (Rumbler[])list.toArray(new Rumbler[0]);
    }
    
    public final Rumbler[] getRumblers() {
        return this.rumblers;
    }
    
    public final synchronized int uploadRumbleEffect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) throws IOException {
        this.checkClosed();
        return nUploadRumbleEffect(this.fd, n, n3, n2, n4, n5, n6, n7, n8);
    }
    
    private static final native int nUploadRumbleEffect(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8) throws IOException;
    
    public final synchronized int uploadConstantEffect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11) throws IOException {
        this.checkClosed();
        return nUploadConstantEffect(this.fd, n, n3, n2, n4, n5, n6, n7, n8, n9, n10, n11);
    }
    
    private static final native int nUploadConstantEffect(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final int p11) throws IOException;
    
    final void eraseEffect(final int n) throws IOException {
        nEraseEffect(this.fd, n);
    }
    
    private static final native void nEraseEffect(final long p0, final int p1) throws IOException;
    
    public final synchronized void writeEvent(final int n, final int n2, final int n3) throws IOException {
        this.checkClosed();
        nWriteEvent(this.fd, n, n2, n3);
    }
    
    private static final native void nWriteEvent(final long p0, final int p1, final int p2, final int p3) throws IOException;
    
    public final void registerComponent(final LinuxAxisDescriptor linuxAxisDescriptor, final LinuxComponent linuxComponent) {
        this.component_map.put(linuxAxisDescriptor, linuxComponent);
    }
    
    public final LinuxComponent mapDescriptor(final LinuxAxisDescriptor linuxAxisDescriptor) {
        return this.component_map.get(linuxAxisDescriptor);
    }
    
    public final Controller.PortType getPortType() throws IOException {
        return this.input_id.getPortType();
    }
    
    public final LinuxInputID getInputID() {
        return this.input_id;
    }
    
    private final LinuxInputID getDeviceInputID() throws IOException {
        return nGetInputID(this.fd);
    }
    
    private static final native LinuxInputID nGetInputID(final long p0) throws IOException;
    
    public final int getNumEffects() throws IOException {
        return nGetNumEffects(this.fd);
    }
    
    private static final native int nGetNumEffects(final long p0) throws IOException;
    
    private final int getVersion() throws IOException {
        return nGetVersion(this.fd);
    }
    
    private static final native int nGetVersion(final long p0) throws IOException;
    
    public final synchronized boolean getNextEvent(final LinuxEvent linuxEvent) throws IOException {
        this.checkClosed();
        return nGetNextEvent(this.fd, linuxEvent);
    }
    
    private static final native boolean nGetNextEvent(final long p0, final LinuxEvent p1) throws IOException;
    
    public final synchronized void getAbsInfo(final int n, final LinuxAbsInfo linuxAbsInfo) throws IOException {
        this.checkClosed();
        nGetAbsInfo(this.fd, n, linuxAbsInfo);
    }
    
    private static final native void nGetAbsInfo(final long p0, final int p1, final LinuxAbsInfo p2) throws IOException;
    
    private final void addKeys(final List p0) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   net/java/games/input/LinuxEventDevice.getKeysBits:()[B
        //     4: astore_2       
        //     5: iconst_0       
        //     6: aload_2        
        //     7: arraylength    
        //     8: bipush          8
        //    10: imul           
        //    11: if_icmpge       50
        //    14: aload_2        
        //    15: goto            44
        //    18: iconst_0       
        //    19: invokestatic    net/java/games/input/LinuxNativeTypesMap.getButtonID:(I)Lnet/java/games/input/Component$Identifier;
        //    22: astore          4
        //    24: aload_1        
        //    25: new             Lnet/java/games/input/LinuxEventComponent;
        //    28: dup            
        //    29: aload_0        
        //    30: aload           4
        //    32: iconst_0       
        //    33: iconst_1       
        //    34: iconst_0       
        //    35: invokespecial   net/java/games/input/LinuxEventComponent.<init>:(Lnet/java/games/input/LinuxEventDevice;Lnet/java/games/input/Component$Identifier;ZII)V
        //    38: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    43: pop            
        //    44: iinc            3, 1
        //    47: goto            5
        //    50: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0005 (coming from #0047).
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
    
    private final void addAbsoluteAxes(final List p0) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   net/java/games/input/LinuxEventDevice.getAbsoluteAxesBits:()[B
        //     4: astore_2       
        //     5: iconst_0       
        //     6: aload_2        
        //     7: arraylength    
        //     8: bipush          8
        //    10: imul           
        //    11: if_icmpge       50
        //    14: aload_2        
        //    15: goto            44
        //    18: iconst_0       
        //    19: invokestatic    net/java/games/input/LinuxNativeTypesMap.getAbsAxisID:(I)Lnet/java/games/input/Component$Identifier;
        //    22: astore          4
        //    24: aload_1        
        //    25: new             Lnet/java/games/input/LinuxEventComponent;
        //    28: dup            
        //    29: aload_0        
        //    30: aload           4
        //    32: iconst_0       
        //    33: iconst_3       
        //    34: iconst_0       
        //    35: invokespecial   net/java/games/input/LinuxEventComponent.<init>:(Lnet/java/games/input/LinuxEventDevice;Lnet/java/games/input/Component$Identifier;ZII)V
        //    38: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    43: pop            
        //    44: iinc            3, 1
        //    47: goto            5
        //    50: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0005 (coming from #0047).
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
    
    private final void addRelativeAxes(final List p0) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   net/java/games/input/LinuxEventDevice.getRelativeAxesBits:()[B
        //     4: astore_2       
        //     5: iconst_0       
        //     6: aload_2        
        //     7: arraylength    
        //     8: bipush          8
        //    10: imul           
        //    11: if_icmpge       50
        //    14: aload_2        
        //    15: goto            44
        //    18: iconst_0       
        //    19: invokestatic    net/java/games/input/LinuxNativeTypesMap.getRelAxisID:(I)Lnet/java/games/input/Component$Identifier;
        //    22: astore          4
        //    24: aload_1        
        //    25: new             Lnet/java/games/input/LinuxEventComponent;
        //    28: dup            
        //    29: aload_0        
        //    30: aload           4
        //    32: iconst_1       
        //    33: iconst_2       
        //    34: iconst_0       
        //    35: invokespecial   net/java/games/input/LinuxEventComponent.<init>:(Lnet/java/games/input/LinuxEventDevice;Lnet/java/games/input/Component$Identifier;ZII)V
        //    38: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    43: pop            
        //    44: iinc            3, 1
        //    47: goto            5
        //    50: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0005 (coming from #0047).
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
    
    public final List getComponents() {
        return this.components;
    }
    
    private final List getDeviceComponents() throws IOException {
        final ArrayList list = new ArrayList();
        this.getEventTypeBits();
        this.addKeys(list);
        return list;
    }
    
    private final byte[] getForceFeedbackBits() throws IOException {
        final byte[] array = new byte[16];
        nGetBits(this.fd, 21, array);
        return array;
    }
    
    private final byte[] getKeysBits() throws IOException {
        final byte[] array = new byte[64];
        nGetBits(this.fd, 1, array);
        return array;
    }
    
    private final byte[] getAbsoluteAxesBits() throws IOException {
        final byte[] array = new byte[8];
        nGetBits(this.fd, 3, array);
        return array;
    }
    
    private final byte[] getRelativeAxesBits() throws IOException {
        final byte[] array = new byte[2];
        nGetBits(this.fd, 2, array);
        return array;
    }
    
    private final byte[] getEventTypeBits() throws IOException {
        final byte[] array = new byte[4];
        nGetBits(this.fd, 0, array);
        return array;
    }
    
    private static final native void nGetBits(final long p0, final int p1, final byte[] p2) throws IOException;
    
    private final byte[] getDeviceUsageBits() throws IOException {
        final byte[] array = new byte[2];
        if (this.getVersion() >= 65537) {
            nGetDeviceUsageBits(this.fd, array);
        }
        return array;
    }
    
    private static final native void nGetDeviceUsageBits(final long p0, final byte[] p1) throws IOException;
    
    public final synchronized void pollKeyStates() throws IOException {
        nGetKeyStates(this.fd, this.key_states);
    }
    
    private static final native void nGetKeyStates(final long p0, final byte[] p1) throws IOException;
    
    public final boolean isKeySet(final int n) {
        return isBitSet(this.key_states, n);
    }
    
    public final String getName() {
        return this.name;
    }
    
    private final String getDeviceName() throws IOException {
        return nGetName(this.fd);
    }
    
    private static final native String nGetName(final long p0) throws IOException;
    
    public final synchronized void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        LinuxEnvironmentPlugin.execute(new LinuxDeviceTask() {
            private final LinuxEventDevice this$0;
            
            protected final Object execute() throws IOException {
                LinuxEventDevice.access$100(LinuxEventDevice.access$000(this.this$0));
                return null;
            }
        });
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
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static long access$000(final LinuxEventDevice linuxEventDevice) {
        return linuxEventDevice.fd;
    }
    
    static void access$100(final long n) throws IOException {
        nClose(n);
    }
}
