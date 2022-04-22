package net.java.games.input;

import java.io.*;

final class LinuxEventComponent
{
    private final LinuxEventDevice device;
    private final Component.Identifier identifier;
    private final Controller.Type button_trait;
    private final boolean is_relative;
    private final LinuxAxisDescriptor descriptor;
    private final int min;
    private final int max;
    private final int flat;
    static final boolean $assertionsDisabled;
    static Class class$net$java$games$input$LinuxEventComponent;
    
    public LinuxEventComponent(final LinuxEventDevice device, final Component.Identifier identifier, final boolean is_relative, final int n, final int n2) throws IOException {
        this.device = device;
        this.identifier = identifier;
        if (n == 1) {
            this.button_trait = LinuxNativeTypesMap.guessButtonTrait(n2);
        }
        else {
            this.button_trait = Controller.Type.UNKNOWN;
        }
        this.is_relative = is_relative;
        (this.descriptor = new LinuxAxisDescriptor()).set(n, n2);
        if (n == 3) {
            final LinuxAbsInfo linuxAbsInfo = new LinuxAbsInfo();
            this.getAbsInfo(linuxAbsInfo);
            this.min = linuxAbsInfo.getMin();
            this.max = linuxAbsInfo.getMax();
            this.flat = linuxAbsInfo.getFlat();
        }
        else {
            this.min = Integer.MIN_VALUE;
            this.max = Integer.MAX_VALUE;
            this.flat = 0;
        }
    }
    
    public final LinuxEventDevice getDevice() {
        return this.device;
    }
    
    public final void getAbsInfo(final LinuxAbsInfo linuxAbsInfo) throws IOException {
        assert this.descriptor.getType() == 3;
        this.device.getAbsInfo(this.descriptor.getCode(), linuxAbsInfo);
    }
    
    public final Controller.Type getButtonTrait() {
        return this.button_trait;
    }
    
    public final Component.Identifier getIdentifier() {
        return this.identifier;
    }
    
    public final LinuxAxisDescriptor getDescriptor() {
        return this.descriptor;
    }
    
    public final boolean isRelative() {
        return this.is_relative;
    }
    
    public final boolean isAnalog() {
        return this.identifier instanceof Component.Identifier.Axis && this.identifier != Component.Identifier.Axis.POV;
    }
    
    final float convertValue(float n) {
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
    
    final float getDeadZone() {
        return this.flat / (2.0f * (this.max - this.min));
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static {
        $assertionsDisabled = !((LinuxEventComponent.class$net$java$games$input$LinuxEventComponent == null) ? (LinuxEventComponent.class$net$java$games$input$LinuxEventComponent = class$("net.java.games.input.LinuxEventComponent")) : LinuxEventComponent.class$net$java$games$input$LinuxEventComponent).desiredAssertionStatus();
    }
}
