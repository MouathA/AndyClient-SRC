package org.lwjgl.opencl;

import java.util.*;
import org.lwjgl.opencl.api.*;
import org.lwjgl.*;
import java.nio.*;

public final class CLPlatform extends CLObject
{
    private static final CLPlatformUtil util;
    private static final FastLongMap clPlatforms;
    private final CLObjectRegistry clDevices;
    private Object caps;
    
    CLPlatform(final long n) {
        super(n);
        if (this.isValid()) {
            CLPlatform.clPlatforms.put(n, this);
            this.clDevices = new CLObjectRegistry();
        }
        else {
            this.clDevices = null;
        }
    }
    
    public static CLPlatform getCLPlatform(final long n) {
        return (CLPlatform)CLPlatform.clPlatforms.get(n);
    }
    
    public CLDevice getCLDevice(final long n) {
        return (CLDevice)this.clDevices.getObject(n);
    }
    
    static InfoUtil getInfoUtilInstance(final Class clazz, final String s) {
        return (InfoUtil)Class.forName("org.lwjgl.opencl.InfoUtilFactory").getDeclaredField(s).get(null);
    }
    
    public static List getPlatforms() {
        return getPlatforms(null);
    }
    
    public static List getPlatforms(final Filter filter) {
        return CLPlatform.util.getPlatforms(filter);
    }
    
    public String getInfoString(final int n) {
        return CLPlatform.util.getInfoString(this, n);
    }
    
    public List getDevices(final int n) {
        return this.getDevices(n, null);
    }
    
    public List getDevices(final int n, final Filter filter) {
        return CLPlatform.util.getDevices(this, n, filter);
    }
    
    void setCapabilities(final Object caps) {
        this.caps = caps;
    }
    
    Object getCapabilities() {
        return this.caps;
    }
    
    static void registerCLPlatforms(final PointerBuffer pointerBuffer, final IntBuffer intBuffer) {
        if (pointerBuffer == null) {
            return;
        }
        final int position = pointerBuffer.position();
        while (0 < Math.min(intBuffer.get(0), pointerBuffer.remaining())) {
            final long value = pointerBuffer.get(position + 0);
            if (!CLPlatform.clPlatforms.containsKey(value)) {
                new CLPlatform(value);
            }
            int n = 0;
            ++n;
        }
    }
    
    CLObjectRegistry getCLDeviceRegistry() {
        return this.clDevices;
    }
    
    void registerCLDevices(final PointerBuffer pointerBuffer, final IntBuffer intBuffer) {
        final int position = pointerBuffer.position();
        while (0 < Math.min(intBuffer.get(intBuffer.position()), pointerBuffer.remaining())) {
            final long value = pointerBuffer.get(position + 0);
            if (!this.clDevices.hasObject(value)) {
                new CLDevice(value, this);
            }
            int n = 0;
            ++n;
        }
    }
    
    void registerCLDevices(final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final int position = byteBuffer.position();
        while (0 < Math.min((int)pointerBuffer.get(pointerBuffer.position()), byteBuffer.remaining()) / PointerBuffer.getPointerSize()) {
            final int n = position + 0 * PointerBuffer.getPointerSize();
            final long n2 = PointerBuffer.is64Bit() ? byteBuffer.getLong(n) : byteBuffer.getInt(n);
            if (!this.clDevices.hasObject(n2)) {
                new CLDevice(n2, this);
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    static {
        util = (CLPlatformUtil)getInfoUtilInstance(CLPlatform.class, "CL_PLATFORM_UTIL");
        clPlatforms = new FastLongMap();
    }
    
    interface CLPlatformUtil extends InfoUtil
    {
        List getPlatforms(final Filter p0);
        
        List getDevices(final CLPlatform p0, final int p1, final Filter p2);
    }
}
