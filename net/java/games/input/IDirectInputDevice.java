package net.java.games.input;

import java.io.*;
import java.util.*;

final class IDirectInputDevice
{
    private final DummyWindow window;
    private final long address;
    private final int dev_type;
    private final int dev_subtype;
    private final String instance_name;
    private final String product_name;
    private final List objects;
    private final List effects;
    private final List rumblers;
    private final int[] device_state;
    private final Map object_to_component;
    private final boolean axes_in_relative_mode;
    private boolean released;
    private DataQueue queue;
    private int button_counter;
    private int current_format_offset;
    static Class class$net$java$games$input$DIDeviceObjectData;
    
    public IDirectInputDevice(final DummyWindow window, final long address, final byte[] array, final byte[] array2, final int dev_type, final int dev_subtype, final String instance_name, final String product_name) throws IOException {
        this.objects = new ArrayList();
        this.effects = new ArrayList();
        this.rumblers = new ArrayList();
        this.object_to_component = new HashMap();
        this.window = window;
        this.address = address;
        this.product_name = product_name;
        this.instance_name = instance_name;
        this.dev_type = dev_type;
        this.dev_subtype = dev_subtype;
        this.enumObjects();
        this.enumEffects();
        this.createRumblers();
        while (0 < this.objects.size()) {
            final DIDeviceObject diDeviceObject = this.objects.get(0);
            if (diDeviceObject.isAxis() && !diDeviceObject.isRelative()) {
                break;
            }
            int n = 0;
            ++n;
        }
        this.axes_in_relative_mode = false;
        int n = 1;
        this.setDataFormat(0);
        if (this.rumblers.size() > 0) {
            this.setCooperativeLevel(9);
        }
        else {
            this.setCooperativeLevel(10);
        }
        this.setBufferSize(32);
        this.acquire();
        this.device_state = new int[this.objects.size()];
    }
    
    public final boolean areAxesRelative() {
        return this.axes_in_relative_mode;
    }
    
    public final Rumbler[] getRumblers() {
        return this.rumblers.toArray(new Rumbler[0]);
    }
    
    private final List createRumblers() throws IOException {
        final DIDeviceObject lookupObjectByGUID = this.lookupObjectByGUID(1);
        if (lookupObjectByGUID == null) {
            return this.rumblers;
        }
        final DIDeviceObject[] array = { lookupObjectByGUID };
        final long[] array2 = { 0L };
        while (0 < this.effects.size()) {
            final DIEffectInfo diEffectInfo = this.effects.get(0);
            if ((diEffectInfo.getEffectType() & 0xFF) == 0x3 && (diEffectInfo.getDynamicParams() & 0x4) != 0x0) {
                this.rumblers.add(this.createPeriodicRumbler(array, array2, diEffectInfo));
            }
            int n = 0;
            ++n;
        }
        return this.rumblers;
    }
    
    private final Rumbler createPeriodicRumbler(final DIDeviceObject[] array, final long[] array2, final DIEffectInfo diEffectInfo) throws IOException {
        final int[] array3 = new int[array.length];
        while (0 < array3.length) {
            array3[0] = array[0].getDIIdentifier();
            int n = 0;
            ++n;
        }
        return new IDirectInputEffect(nCreatePeriodicEffect(this.address, diEffectInfo.getGUID(), 17, -1, 0, 10000, -1, 0, array3, array2, 0, 0, 0, 0, 10000, 0, 0, 50000, 0), diEffectInfo);
    }
    
    private static final native long nCreatePeriodicEffect(final long p0, final byte[] p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int[] p8, final long[] p9, final int p10, final int p11, final int p12, final int p13, final int p14, final int p15, final int p16, final int p17, final int p18) throws IOException;
    
    private final DIDeviceObject lookupObjectByGUID(final int n) {
        while (0 < this.objects.size()) {
            final DIDeviceObject diDeviceObject = this.objects.get(0);
            if (n == diDeviceObject.getGUIDType()) {
                return diDeviceObject;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    public final int getPollData(final DIDeviceObject diDeviceObject) {
        return this.device_state[diDeviceObject.getFormatOffset()];
    }
    
    public final DIDeviceObject mapEvent(final DIDeviceObjectData diDeviceObjectData) {
        return this.objects.get(diDeviceObjectData.getFormatOffset() / 4);
    }
    
    public final DIComponent mapObject(final DIDeviceObject diDeviceObject) {
        return this.object_to_component.get(diDeviceObject);
    }
    
    public final void registerComponent(final DIDeviceObject diDeviceObject, final DIComponent diComponent) {
        this.object_to_component.put(diDeviceObject, diComponent);
    }
    
    public final synchronized void pollAll() throws IOException {
        this.checkReleased();
        this.poll();
        this.getDeviceState(this.device_state);
        this.queue.compact();
        this.getDeviceData(this.queue);
        this.queue.flip();
    }
    
    public final synchronized boolean getNextEvent(final DIDeviceObjectData diDeviceObjectData) {
        final DIDeviceObjectData diDeviceObjectData2 = (DIDeviceObjectData)this.queue.get();
        if (diDeviceObjectData2 == null) {
            return false;
        }
        diDeviceObjectData.set(diDeviceObjectData2);
        return true;
    }
    
    private final void poll() throws IOException {
        final int nPoll = nPoll(this.address);
        if (nPoll == 0 || nPoll == 1) {
            return;
        }
        if (nPoll == -2147024868) {
            this.acquire();
            return;
        }
        throw new IOException("Failed to poll device (" + Integer.toHexString(nPoll) + ")");
    }
    
    private static final native int nPoll(final long p0) throws IOException;
    
    private final void acquire() throws IOException {
        final int nAcquire = nAcquire(this.address);
        if (nAcquire != 0 && nAcquire != -2147024891 && nAcquire != 1) {
            throw new IOException("Failed to acquire device (" + Integer.toHexString(nAcquire) + ")");
        }
    }
    
    private static final native int nAcquire(final long p0);
    
    private final void unacquire() throws IOException {
        final int nUnacquire = nUnacquire(this.address);
        if (nUnacquire != 0 && nUnacquire != 1) {
            throw new IOException("Failed to unAcquire device (" + Integer.toHexString(nUnacquire) + ")");
        }
    }
    
    private static final native int nUnacquire(final long p0);
    
    private final boolean getDeviceData(final DataQueue dataQueue) throws IOException {
        final int nGetDeviceData = nGetDeviceData(this.address, 0, dataQueue, dataQueue.getElements(), dataQueue.position(), dataQueue.remaining());
        if (nGetDeviceData == 0 || nGetDeviceData == 1) {
            return true;
        }
        if (nGetDeviceData == -2147024868) {
            this.acquire();
            return false;
        }
        throw new IOException("Failed to get device data (" + Integer.toHexString(nGetDeviceData) + ")");
    }
    
    private static final native int nGetDeviceData(final long p0, final int p1, final DataQueue p2, final Object[] p3, final int p4, final int p5);
    
    private final void getDeviceState(final int[] array) throws IOException {
        final int nGetDeviceState = nGetDeviceState(this.address, array);
        if (nGetDeviceState == 0) {
            return;
        }
        if (nGetDeviceState == -2147024868) {
            Arrays.fill(array, 0);
            this.acquire();
            return;
        }
        throw new IOException("Failed to get device state (" + Integer.toHexString(nGetDeviceState) + ")");
    }
    
    private static final native int nGetDeviceState(final long p0, final int[] p1);
    
    private final void setDataFormat(final int n) throws IOException {
        final DIDeviceObject[] array = new DIDeviceObject[this.objects.size()];
        this.objects.toArray(array);
        final int nSetDataFormat = nSetDataFormat(this.address, n, array);
        if (nSetDataFormat != 0) {
            throw new IOException("Failed to set data format (" + Integer.toHexString(nSetDataFormat) + ")");
        }
    }
    
    private static final native int nSetDataFormat(final long p0, final int p1, final DIDeviceObject[] p2);
    
    public final String getProductName() {
        return this.product_name;
    }
    
    public final int getType() {
        return this.dev_type;
    }
    
    public final List getObjects() {
        return this.objects;
    }
    
    private final void enumEffects() throws IOException {
        final int nEnumEffects = this.nEnumEffects(this.address, 0);
        if (nEnumEffects != 0) {
            throw new IOException("Failed to enumerate effects (" + Integer.toHexString(nEnumEffects) + ")");
        }
    }
    
    private final native int nEnumEffects(final long p0, final int p1);
    
    private final void addEffect(final byte[] array, final int n, final int n2, final int n3, final int n4, final String s) {
        this.effects.add(new DIEffectInfo(this, array, n, n2, n3, n4, s));
    }
    
    private final void enumObjects() throws IOException {
        final int nEnumObjects = this.nEnumObjects(this.address, 31);
        if (nEnumObjects != 0) {
            throw new IOException("Failed to enumerate objects (" + Integer.toHexString(nEnumObjects) + ")");
        }
    }
    
    private final native int nEnumObjects(final long p0, final int p1);
    
    public final synchronized long[] getRangeProperty(final int n) throws IOException {
        this.checkReleased();
        final long[] array = new long[2];
        final int nGetRangeProperty = nGetRangeProperty(this.address, n, array);
        if (nGetRangeProperty != 0) {
            throw new IOException("Failed to get object range (" + nGetRangeProperty + ")");
        }
        return array;
    }
    
    private static final native int nGetRangeProperty(final long p0, final int p1, final long[] p2);
    
    public final synchronized int getDeadzoneProperty(final int n) throws IOException {
        this.checkReleased();
        return nGetDeadzoneProperty(this.address, n);
    }
    
    private static final native int nGetDeadzoneProperty(final long p0, final int p1) throws IOException;
    
    private final void addObject(final byte[] array, final int n, final int n2, final int n3, final int n4, final int n5, final String s) throws IOException {
        this.objects.add(new DIDeviceObject(this, this.getIdentifier(n, n3, n4), array, n, n2, n3, n4, n5, s, this.current_format_offset++));
    }
    
    private static final Component.Identifier.Key getKeyIdentifier(final int n) {
        return DIIdentifierMap.getKeyIdentifier(n);
    }
    
    private final Component.Identifier.Button getNextButtonIdentifier() {
        return DIIdentifierMap.getButtonIdentifier(this.button_counter++);
    }
    
    private final Component.Identifier getIdentifier(final int n, final int n2, final int n3) {
        switch (n) {
            case 1: {
                return Component.Identifier.Axis.X;
            }
            case 2: {
                return Component.Identifier.Axis.Y;
            }
            case 3: {
                return Component.Identifier.Axis.Z;
            }
            case 4: {
                return Component.Identifier.Axis.RX;
            }
            case 5: {
                return Component.Identifier.Axis.RY;
            }
            case 6: {
                return Component.Identifier.Axis.RZ;
            }
            case 7: {
                return Component.Identifier.Axis.SLIDER;
            }
            case 10: {
                return Component.Identifier.Axis.POV;
            }
            case 9: {
                return getKeyIdentifier(n3);
            }
            case 8: {
                return this.getNextButtonIdentifier();
            }
            default: {
                return Component.Identifier.Axis.UNKNOWN;
            }
        }
    }
    
    public final synchronized void setBufferSize(final int n) throws IOException {
        this.checkReleased();
        this.unacquire();
        final int nSetBufferSize = nSetBufferSize(this.address, n);
        if (nSetBufferSize != 0 && nSetBufferSize != 1 && nSetBufferSize != 2) {
            throw new IOException("Failed to set buffer size (" + Integer.toHexString(nSetBufferSize) + ")");
        }
        (this.queue = new DataQueue(n, (IDirectInputDevice.class$net$java$games$input$DIDeviceObjectData == null) ? (IDirectInputDevice.class$net$java$games$input$DIDeviceObjectData = class$("net.java.games.input.DIDeviceObjectData")) : IDirectInputDevice.class$net$java$games$input$DIDeviceObjectData)).position(this.queue.limit());
        this.acquire();
    }
    
    private static final native int nSetBufferSize(final long p0, final int p1);
    
    public final synchronized void setCooperativeLevel(final int n) throws IOException {
        this.checkReleased();
        final int nSetCooperativeLevel = nSetCooperativeLevel(this.address, this.window.getHwnd(), n);
        if (nSetCooperativeLevel != 0) {
            throw new IOException("Failed to set cooperative level (" + Integer.toHexString(nSetCooperativeLevel) + ")");
        }
    }
    
    private static final native int nSetCooperativeLevel(final long p0, final long p1, final int p2);
    
    public final synchronized void release() {
        if (!this.released) {
            this.released = true;
            while (0 < this.rumblers.size()) {
                this.rumblers.get(0).release();
                int n = 0;
                ++n;
            }
            nRelease(this.address);
        }
    }
    
    private static final native void nRelease(final long p0);
    
    private final void checkReleased() throws IOException {
        if (this.released) {
            throw new IOException("Device is released");
        }
    }
    
    protected void finalize() {
        this.release();
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
}
