package com.viaversion.viaversion.libs.javassist.util;

import java.io.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.request.*;
import com.sun.jdi.*;
import java.util.*;
import com.sun.jdi.event.*;

public class HotSwapper
{
    private VirtualMachine jvm;
    private MethodEntryRequest request;
    private Map newClassFiles;
    private Trigger trigger;
    private static final String HOST_NAME = "localhost";
    private static final String TRIGGER_NAME;
    
    public HotSwapper(final int n) throws IOException, IllegalConnectorArgumentsException {
        this(Integer.toString(n));
    }
    
    public HotSwapper(final String value) throws IOException, IllegalConnectorArgumentsException {
        this.jvm = null;
        this.request = null;
        this.newClassFiles = null;
        this.trigger = new Trigger();
        final AttachingConnector attachingConnector = (AttachingConnector)this.findConnector("com.sun.jdi.SocketAttach");
        final Map defaultArguments = attachingConnector.defaultArguments();
        defaultArguments.get("hostname").setValue("localhost");
        defaultArguments.get("port").setValue(value);
        this.jvm = attachingConnector.attach(defaultArguments);
        this.request = methodEntryRequests(this.jvm.eventRequestManager(), HotSwapper.TRIGGER_NAME);
    }
    
    private Connector findConnector(final String s) throws IOException {
        for (final Connector connector : Bootstrap.virtualMachineManager().allConnectors()) {
            if (connector.name().equals(s)) {
                return connector;
            }
        }
        throw new IOException("Not found: " + s);
    }
    
    private static MethodEntryRequest methodEntryRequests(final EventRequestManager eventRequestManager, final String s) {
        final MethodEntryRequest methodEntryRequest = eventRequestManager.createMethodEntryRequest();
        methodEntryRequest.addClassFilter(s);
        methodEntryRequest.setSuspendPolicy(1);
        return methodEntryRequest;
    }
    
    private void deleteEventRequest(final EventRequestManager eventRequestManager, final MethodEntryRequest methodEntryRequest) {
        eventRequestManager.deleteEventRequest((EventRequest)methodEntryRequest);
    }
    
    public void reload(final String s, final byte[] array) {
        final ReferenceType refType = this.toRefType(s);
        final HashMap<ReferenceType, byte[]> hashMap = new HashMap<ReferenceType, byte[]>();
        hashMap.put(refType, array);
        this.reload2(hashMap, s);
    }
    
    public void reload(final Map map) {
        final HashMap<ReferenceType, byte[]> hashMap = new HashMap<ReferenceType, byte[]>();
        String s = null;
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            s = entry.getKey();
            hashMap.put(this.toRefType(s), (byte[])(Object)entry.getValue());
        }
        if (s != null) {
            this.reload2(hashMap, s + " etc.");
        }
    }
    
    private ReferenceType toRefType(final String s) {
        final List classesByName = this.jvm.classesByName(s);
        if (classesByName == null || classesByName.isEmpty()) {
            throw new RuntimeException("no such class: " + s);
        }
        return classesByName.get(0);
    }
    
    private void reload2(final Map newClassFiles, final String s) {
        // monitorenter(trigger = this.trigger)
        this.startDaemon();
        this.newClassFiles = newClassFiles;
        this.request.enable();
        this.trigger.doSwap();
        this.request.disable();
        if (this.newClassFiles != null) {
            this.newClassFiles = null;
            throw new RuntimeException("failed to reload: " + s);
        }
    }
    // monitorexit(trigger)
    
    private void startDaemon() {
        new Thread() {
            final HotSwapper this$0;
            
            private void errorMsg(final Throwable t) {
                System.err.print("Exception in thread \"HotSwap\" ");
                t.printStackTrace(System.err);
            }
            
            @Override
            public void run() {
                final EventSet waitEvent = this.this$0.waitEvent();
                final EventIterator eventIterator = waitEvent.eventIterator();
                while (eventIterator.hasNext()) {
                    if (eventIterator.nextEvent() instanceof MethodEntryEvent) {
                        this.this$0.hotswap();
                        break;
                    }
                }
                if (waitEvent != null) {
                    waitEvent.resume();
                }
            }
        }.start();
    }
    
    EventSet waitEvent() throws InterruptedException {
        return this.jvm.eventQueue().remove();
    }
    
    void hotswap() {
        this.jvm.redefineClasses(this.newClassFiles);
        this.newClassFiles = null;
    }
    
    static {
        TRIGGER_NAME = Trigger.class.getName();
    }
}
