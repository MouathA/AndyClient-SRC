package org.lwjgl.input;

import java.util.*;
import net.java.games.input.*;

class JInputController implements Controller
{
    private net.java.games.input.Controller target;
    private int index;
    private ArrayList buttons;
    private ArrayList axes;
    private ArrayList pov;
    private Rumbler[] rumblers;
    private boolean[] buttonState;
    private float[] povValues;
    private float[] axesValue;
    private float[] axesMax;
    private float[] deadZones;
    private int xaxis;
    private int yaxis;
    private int zaxis;
    private int rxaxis;
    private int ryaxis;
    private int rzaxis;
    
    JInputController(final int p0, final net.java.games.input.Controller p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: aload_0        
        //     5: new             Ljava/util/ArrayList;
        //     8: dup            
        //     9: invokespecial   java/util/ArrayList.<init>:()V
        //    12: putfield        org/lwjgl/input/JInputController.buttons:Ljava/util/ArrayList;
        //    15: aload_0        
        //    16: new             Ljava/util/ArrayList;
        //    19: dup            
        //    20: invokespecial   java/util/ArrayList.<init>:()V
        //    23: putfield        org/lwjgl/input/JInputController.axes:Ljava/util/ArrayList;
        //    26: aload_0        
        //    27: new             Ljava/util/ArrayList;
        //    30: dup            
        //    31: invokespecial   java/util/ArrayList.<init>:()V
        //    34: putfield        org/lwjgl/input/JInputController.pov:Ljava/util/ArrayList;
        //    37: aload_0        
        //    38: iconst_m1      
        //    39: putfield        org/lwjgl/input/JInputController.xaxis:I
        //    42: aload_0        
        //    43: iconst_m1      
        //    44: putfield        org/lwjgl/input/JInputController.yaxis:I
        //    47: aload_0        
        //    48: iconst_m1      
        //    49: putfield        org/lwjgl/input/JInputController.zaxis:I
        //    52: aload_0        
        //    53: iconst_m1      
        //    54: putfield        org/lwjgl/input/JInputController.rxaxis:I
        //    57: aload_0        
        //    58: iconst_m1      
        //    59: putfield        org/lwjgl/input/JInputController.ryaxis:I
        //    62: aload_0        
        //    63: iconst_m1      
        //    64: putfield        org/lwjgl/input/JInputController.rzaxis:I
        //    67: aload_0        
        //    68: aload_2        
        //    69: putfield        org/lwjgl/input/JInputController.target:Lnet/java/games/input/Controller;
        //    72: aload_0        
        //    73: iload_1        
        //    74: putfield        org/lwjgl/input/JInputController.index:I
        //    77: aload_2        
        //    78: invokeinterface net/java/games/input/Controller.getComponents:()[Lnet/java/games/input/Component;
        //    83: astore_3       
        //    84: aload_3        
        //    85: astore          4
        //    87: aload           4
        //    89: arraylength    
        //    90: istore          5
        //    92: iconst_0       
        //    93: iconst_0       
        //    94: if_icmpge       174
        //    97: aload           4
        //    99: iconst_0       
        //   100: aaload         
        //   101: astore          7
        //   103: aload           7
        //   105: invokeinterface net/java/games/input/Component.getIdentifier:()Lnet/java/games/input/Component$Identifier;
        //   110: instanceof      Lnet/java/games/input/Component$Identifier$Button;
        //   113: ifeq            129
        //   116: aload_0        
        //   117: getfield        org/lwjgl/input/JInputController.buttons:Ljava/util/ArrayList;
        //   120: aload           7
        //   122: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   125: pop            
        //   126: goto            168
        //   129: aload           7
        //   131: invokeinterface net/java/games/input/Component.getIdentifier:()Lnet/java/games/input/Component$Identifier;
        //   136: getstatic       net/java/games/input/Component$Identifier$Axis.POV:Lnet/java/games/input/Component$Identifier$Axis;
        //   139: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   142: ifeq            158
        //   145: aload_0        
        //   146: getfield        org/lwjgl/input/JInputController.pov:Ljava/util/ArrayList;
        //   149: aload           7
        //   151: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   154: pop            
        //   155: goto            168
        //   158: aload_0        
        //   159: getfield        org/lwjgl/input/JInputController.axes:Ljava/util/ArrayList;
        //   162: aload           7
        //   164: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   167: pop            
        //   168: iinc            6, 1
        //   171: goto            92
        //   174: aload_0        
        //   175: aload_0        
        //   176: getfield        org/lwjgl/input/JInputController.buttons:Ljava/util/ArrayList;
        //   179: invokevirtual   java/util/ArrayList.size:()I
        //   182: newarray        Z
        //   184: putfield        org/lwjgl/input/JInputController.buttonState:[Z
        //   187: aload_0        
        //   188: aload_0        
        //   189: getfield        org/lwjgl/input/JInputController.pov:Ljava/util/ArrayList;
        //   192: invokevirtual   java/util/ArrayList.size:()I
        //   195: newarray        F
        //   197: putfield        org/lwjgl/input/JInputController.povValues:[F
        //   200: aload_0        
        //   201: aload_0        
        //   202: getfield        org/lwjgl/input/JInputController.axes:Ljava/util/ArrayList;
        //   205: invokevirtual   java/util/ArrayList.size:()I
        //   208: newarray        F
        //   210: putfield        org/lwjgl/input/JInputController.axesValue:[F
        //   213: aload_3        
        //   214: astore          6
        //   216: aload           6
        //   218: arraylength    
        //   219: istore          7
        //   221: iconst_0       
        //   222: iload           7
        //   224: if_icmpge       442
        //   227: aload           6
        //   229: iconst_0       
        //   230: aaload         
        //   231: astore          9
        //   233: aload           9
        //   235: invokeinterface net/java/games/input/Component.getIdentifier:()Lnet/java/games/input/Component$Identifier;
        //   240: instanceof      Lnet/java/games/input/Component$Identifier$Button;
        //   243: ifeq            275
        //   246: aload_0        
        //   247: getfield        org/lwjgl/input/JInputController.buttonState:[Z
        //   250: iconst_0       
        //   251: aload           9
        //   253: invokeinterface net/java/games/input/Component.getPollData:()F
        //   258: fconst_0       
        //   259: fcmpl          
        //   260: ifeq            267
        //   263: iconst_1       
        //   264: goto            268
        //   267: iconst_0       
        //   268: bastore        
        //   269: iinc            4, 1
        //   272: goto            436
        //   275: aload           9
        //   277: invokeinterface net/java/games/input/Component.getIdentifier:()Lnet/java/games/input/Component$Identifier;
        //   282: getstatic       net/java/games/input/Component$Identifier$Axis.POV:Lnet/java/games/input/Component$Identifier$Axis;
        //   285: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   288: ifeq            294
        //   291: goto            436
        //   294: aload_0        
        //   295: getfield        org/lwjgl/input/JInputController.axesValue:[F
        //   298: iconst_0       
        //   299: aload           9
        //   301: invokeinterface net/java/games/input/Component.getPollData:()F
        //   306: fastore        
        //   307: aload           9
        //   309: invokeinterface net/java/games/input/Component.getIdentifier:()Lnet/java/games/input/Component$Identifier;
        //   314: getstatic       net/java/games/input/Component$Identifier$Axis.X:Lnet/java/games/input/Component$Identifier$Axis;
        //   317: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   320: ifeq            328
        //   323: aload_0        
        //   324: iconst_0       
        //   325: putfield        org/lwjgl/input/JInputController.xaxis:I
        //   328: aload           9
        //   330: invokeinterface net/java/games/input/Component.getIdentifier:()Lnet/java/games/input/Component$Identifier;
        //   335: getstatic       net/java/games/input/Component$Identifier$Axis.Y:Lnet/java/games/input/Component$Identifier$Axis;
        //   338: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   341: ifeq            349
        //   344: aload_0        
        //   345: iconst_0       
        //   346: putfield        org/lwjgl/input/JInputController.yaxis:I
        //   349: aload           9
        //   351: invokeinterface net/java/games/input/Component.getIdentifier:()Lnet/java/games/input/Component$Identifier;
        //   356: getstatic       net/java/games/input/Component$Identifier$Axis.Z:Lnet/java/games/input/Component$Identifier$Axis;
        //   359: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   362: ifeq            370
        //   365: aload_0        
        //   366: iconst_0       
        //   367: putfield        org/lwjgl/input/JInputController.zaxis:I
        //   370: aload           9
        //   372: invokeinterface net/java/games/input/Component.getIdentifier:()Lnet/java/games/input/Component$Identifier;
        //   377: getstatic       net/java/games/input/Component$Identifier$Axis.RX:Lnet/java/games/input/Component$Identifier$Axis;
        //   380: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   383: ifeq            391
        //   386: aload_0        
        //   387: iconst_0       
        //   388: putfield        org/lwjgl/input/JInputController.rxaxis:I
        //   391: aload           9
        //   393: invokeinterface net/java/games/input/Component.getIdentifier:()Lnet/java/games/input/Component$Identifier;
        //   398: getstatic       net/java/games/input/Component$Identifier$Axis.RY:Lnet/java/games/input/Component$Identifier$Axis;
        //   401: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   404: ifeq            412
        //   407: aload_0        
        //   408: iconst_0       
        //   409: putfield        org/lwjgl/input/JInputController.ryaxis:I
        //   412: aload           9
        //   414: invokeinterface net/java/games/input/Component.getIdentifier:()Lnet/java/games/input/Component$Identifier;
        //   419: getstatic       net/java/games/input/Component$Identifier$Axis.RZ:Lnet/java/games/input/Component$Identifier$Axis;
        //   422: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   425: ifeq            433
        //   428: aload_0        
        //   429: iconst_0       
        //   430: putfield        org/lwjgl/input/JInputController.rzaxis:I
        //   433: iinc            5, 1
        //   436: iinc            8, 1
        //   439: goto            221
        //   442: aload_0        
        //   443: aload_0        
        //   444: getfield        org/lwjgl/input/JInputController.axes:Ljava/util/ArrayList;
        //   447: invokevirtual   java/util/ArrayList.size:()I
        //   450: newarray        F
        //   452: putfield        org/lwjgl/input/JInputController.axesMax:[F
        //   455: aload_0        
        //   456: aload_0        
        //   457: getfield        org/lwjgl/input/JInputController.axes:Ljava/util/ArrayList;
        //   460: invokevirtual   java/util/ArrayList.size:()I
        //   463: newarray        F
        //   465: putfield        org/lwjgl/input/JInputController.deadZones:[F
        //   468: iconst_0       
        //   469: aload_0        
        //   470: getfield        org/lwjgl/input/JInputController.axesMax:[F
        //   473: arraylength    
        //   474: if_icmpge       498
        //   477: aload_0        
        //   478: getfield        org/lwjgl/input/JInputController.axesMax:[F
        //   481: iconst_0       
        //   482: fconst_1       
        //   483: fastore        
        //   484: aload_0        
        //   485: getfield        org/lwjgl/input/JInputController.deadZones:[F
        //   488: iconst_0       
        //   489: ldc             0.05
        //   491: fastore        
        //   492: iinc            6, 1
        //   495: goto            468
        //   498: aload_0        
        //   499: aload_2        
        //   500: invokeinterface net/java/games/input/Controller.getRumblers:()[Lnet/java/games/input/Rumbler;
        //   505: putfield        org/lwjgl/input/JInputController.rumblers:[Lnet/java/games/input/Rumbler;
        //   508: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public String getName() {
        return this.target.getName();
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public int getButtonCount() {
        return this.buttons.size();
    }
    
    public String getButtonName(final int n) {
        return this.buttons.get(n).getName();
    }
    
    public boolean isButtonPressed(final int n) {
        return this.buttonState[n];
    }
    
    public void poll() {
        this.target.poll();
        final Event event = new Event();
        while (this.target.getEventQueue().getNextEvent(event)) {
            if (this.buttons.contains(event.getComponent())) {
                final int index = this.buttons.indexOf(event.getComponent());
                this.buttonState[index] = (event.getValue() != 0.0f);
                Controllers.addEvent(new ControllerEvent(this, event.getNanos(), 1, index, this.buttonState[index], false, false, 0.0f, 0.0f));
            }
            if (this.pov.contains(event.getComponent())) {
                final int index2 = this.pov.indexOf(event.getComponent());
                final float povX = this.getPovX();
                final float povY = this.getPovY();
                this.povValues[index2] = event.getValue();
                if (povX != this.getPovX()) {
                    Controllers.addEvent(new ControllerEvent(this, event.getNanos(), 3, 0, false, false));
                }
                if (povY != this.getPovY()) {
                    Controllers.addEvent(new ControllerEvent(this, event.getNanos(), 4, 0, false, false));
                }
            }
            if (this.axes.contains(event.getComponent())) {
                final Component component = event.getComponent();
                final int index3 = this.axes.indexOf(component);
                float pollData = component.getPollData();
                float n = 0.0f;
                float n2 = 0.0f;
                if (Math.abs(pollData) < this.deadZones[index3]) {
                    pollData = 0.0f;
                }
                if (Math.abs(pollData) < component.getDeadZone()) {
                    pollData = 0.0f;
                }
                if (Math.abs(pollData) > this.axesMax[index3]) {
                    this.axesMax[index3] = Math.abs(pollData);
                }
                final float n3 = pollData / this.axesMax[index3];
                if (index3 == this.xaxis) {
                    n = n3;
                }
                if (index3 == this.yaxis) {
                    n2 = n3;
                }
                Controllers.addEvent(new ControllerEvent(this, event.getNanos(), 2, index3, false, index3 == this.xaxis, index3 == this.yaxis, n, n2));
                this.axesValue[index3] = n3;
            }
        }
    }
    
    public int getAxisCount() {
        return this.axes.size();
    }
    
    public String getAxisName(final int n) {
        return this.axes.get(n).getName();
    }
    
    public float getAxisValue(final int n) {
        return this.axesValue[n];
    }
    
    public float getXAxisValue() {
        if (this.xaxis == -1) {
            return 0.0f;
        }
        return this.getAxisValue(this.xaxis);
    }
    
    public float getYAxisValue() {
        if (this.yaxis == -1) {
            return 0.0f;
        }
        return this.getAxisValue(this.yaxis);
    }
    
    public float getXAxisDeadZone() {
        if (this.xaxis == -1) {
            return 0.0f;
        }
        return this.getDeadZone(this.xaxis);
    }
    
    public float getYAxisDeadZone() {
        if (this.yaxis == -1) {
            return 0.0f;
        }
        return this.getDeadZone(this.yaxis);
    }
    
    public void setXAxisDeadZone(final float n) {
        this.setDeadZone(this.xaxis, n);
    }
    
    public void setYAxisDeadZone(final float n) {
        this.setDeadZone(this.yaxis, n);
    }
    
    public float getDeadZone(final int n) {
        return this.deadZones[n];
    }
    
    public void setDeadZone(final int n, final float n2) {
        this.deadZones[n] = n2;
    }
    
    public float getZAxisValue() {
        if (this.zaxis == -1) {
            return 0.0f;
        }
        return this.getAxisValue(this.zaxis);
    }
    
    public float getZAxisDeadZone() {
        if (this.zaxis == -1) {
            return 0.0f;
        }
        return this.getDeadZone(this.zaxis);
    }
    
    public void setZAxisDeadZone(final float n) {
        this.setDeadZone(this.zaxis, n);
    }
    
    public float getRXAxisValue() {
        if (this.rxaxis == -1) {
            return 0.0f;
        }
        return this.getAxisValue(this.rxaxis);
    }
    
    public float getRXAxisDeadZone() {
        if (this.rxaxis == -1) {
            return 0.0f;
        }
        return this.getDeadZone(this.rxaxis);
    }
    
    public void setRXAxisDeadZone(final float n) {
        this.setDeadZone(this.rxaxis, n);
    }
    
    public float getRYAxisValue() {
        if (this.ryaxis == -1) {
            return 0.0f;
        }
        return this.getAxisValue(this.ryaxis);
    }
    
    public float getRYAxisDeadZone() {
        if (this.ryaxis == -1) {
            return 0.0f;
        }
        return this.getDeadZone(this.ryaxis);
    }
    
    public void setRYAxisDeadZone(final float n) {
        this.setDeadZone(this.ryaxis, n);
    }
    
    public float getRZAxisValue() {
        if (this.rzaxis == -1) {
            return 0.0f;
        }
        return this.getAxisValue(this.rzaxis);
    }
    
    public float getRZAxisDeadZone() {
        if (this.rzaxis == -1) {
            return 0.0f;
        }
        return this.getDeadZone(this.rzaxis);
    }
    
    public void setRZAxisDeadZone(final float n) {
        this.setDeadZone(this.rzaxis, n);
    }
    
    public float getPovX() {
        if (this.pov.size() == 0) {
            return 0.0f;
        }
        final float n = this.povValues[0];
        if (n == 0.875f || n == 0.125f || n == 1.0f) {
            return -1.0f;
        }
        if (n == 0.625f || n == 0.375f || n == 0.5f) {
            return 1.0f;
        }
        return 0.0f;
    }
    
    public float getPovY() {
        if (this.pov.size() == 0) {
            return 0.0f;
        }
        final float n = this.povValues[0];
        if (n == 0.875f || n == 0.625f || n == 0.75f) {
            return 1.0f;
        }
        if (n == 0.125f || n == 0.375f || n == 0.25f) {
            return -1.0f;
        }
        return 0.0f;
    }
    
    public int getRumblerCount() {
        return this.rumblers.length;
    }
    
    public String getRumblerName(final int n) {
        return this.rumblers[n].getAxisName();
    }
    
    public void setRumblerStrength(final int n, final float n2) {
        this.rumblers[n].rumble(n2);
    }
}
