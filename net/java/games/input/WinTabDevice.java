package net.java.games.input;

import java.io.*;
import java.util.*;

public class WinTabDevice extends AbstractController
{
    private WinTabContext context;
    private List eventList;
    
    private WinTabDevice(final WinTabContext context, final int n, final String s, final Component[] array) {
        super(s, array, new Controller[0], new Rumbler[0]);
        this.eventList = new ArrayList();
        this.context = context;
    }
    
    protected boolean getNextDeviceEvent(final Event event) throws IOException {
        if (this.eventList.size() > 0) {
            event.set(this.eventList.remove(0));
            return true;
        }
        return false;
    }
    
    protected void pollDevice() throws IOException {
        this.context.processEvents();
        super.pollDevice();
    }
    
    public Controller.Type getType() {
        return Controller.Type.TRACKPAD;
    }
    
    public void processPacket(final WinTabPacket winTabPacket) {
        final Component[] components = this.getComponents();
        while (0 < components.length) {
            final Event processPacket = ((WinTabComponent)components[0]).processPacket(winTabPacket);
            if (processPacket != null) {
                this.eventList.add(processPacket);
            }
            int n = 0;
            ++n;
        }
    }
    
    public static WinTabDevice createDevice(final WinTabContext winTabContext, final int n) {
        final String nGetName = nGetName(n);
        ControllerEnvironment.logln("Device " + n + ", name: " + nGetName);
        final ArrayList list = new ArrayList();
        final int[] nGetAxisDetails = nGetAxisDetails(n, 1);
        if (nGetAxisDetails.length == 0) {
            ControllerEnvironment.logln("ZAxis not supported");
        }
        else {
            ControllerEnvironment.logln("Xmin: " + nGetAxisDetails[0] + ", Xmax: " + nGetAxisDetails[1]);
            list.addAll(WinTabComponent.createComponents(winTabContext, n, 1, nGetAxisDetails));
        }
        final int[] nGetAxisDetails2 = nGetAxisDetails(n, 2);
        if (nGetAxisDetails2.length == 0) {
            ControllerEnvironment.logln("YAxis not supported");
        }
        else {
            ControllerEnvironment.logln("Ymin: " + nGetAxisDetails2[0] + ", Ymax: " + nGetAxisDetails2[1]);
            list.addAll(WinTabComponent.createComponents(winTabContext, n, 2, nGetAxisDetails2));
        }
        final int[] nGetAxisDetails3 = nGetAxisDetails(n, 3);
        if (nGetAxisDetails3.length == 0) {
            ControllerEnvironment.logln("ZAxis not supported");
        }
        else {
            ControllerEnvironment.logln("Zmin: " + nGetAxisDetails3[0] + ", Zmax: " + nGetAxisDetails3[1]);
            list.addAll(WinTabComponent.createComponents(winTabContext, n, 3, nGetAxisDetails3));
        }
        final int[] nGetAxisDetails4 = nGetAxisDetails(n, 4);
        if (nGetAxisDetails4.length == 0) {
            ControllerEnvironment.logln("NPressureAxis not supported");
        }
        else {
            ControllerEnvironment.logln("NPressMin: " + nGetAxisDetails4[0] + ", NPressMax: " + nGetAxisDetails4[1]);
            list.addAll(WinTabComponent.createComponents(winTabContext, n, 4, nGetAxisDetails4));
        }
        final int[] nGetAxisDetails5 = nGetAxisDetails(n, 5);
        if (nGetAxisDetails5.length == 0) {
            ControllerEnvironment.logln("TPressureAxis not supported");
        }
        else {
            ControllerEnvironment.logln("TPressureAxismin: " + nGetAxisDetails5[0] + ", TPressureAxismax: " + nGetAxisDetails5[1]);
            list.addAll(WinTabComponent.createComponents(winTabContext, n, 5, nGetAxisDetails5));
        }
        final int[] nGetAxisDetails6 = nGetAxisDetails(n, 6);
        if (nGetAxisDetails6.length == 0) {
            ControllerEnvironment.logln("OrientationAxis not supported");
        }
        else {
            ControllerEnvironment.logln("OrientationAxis mins/maxs: " + nGetAxisDetails6[0] + "," + nGetAxisDetails6[1] + ", " + nGetAxisDetails6[2] + "," + nGetAxisDetails6[3] + ", " + nGetAxisDetails6[4] + "," + nGetAxisDetails6[5]);
            list.addAll(WinTabComponent.createComponents(winTabContext, n, 6, nGetAxisDetails6));
        }
        final int[] nGetAxisDetails7 = nGetAxisDetails(n, 7);
        if (nGetAxisDetails7.length == 0) {
            ControllerEnvironment.logln("RotationAxis not supported");
        }
        else {
            ControllerEnvironment.logln("RotationAxis is supported (by the device, not by this plugin)");
            list.addAll(WinTabComponent.createComponents(winTabContext, n, 7, nGetAxisDetails7));
        }
        final String[] nGetCursorNames = nGetCursorNames(n);
        list.addAll(WinTabComponent.createCursors(winTabContext, n, nGetCursorNames));
        while (0 < nGetCursorNames.length) {
            ControllerEnvironment.logln("Cursor " + 0 + "'s name: " + nGetCursorNames[0]);
            int nGetMaxButtonCount = 0;
            ++nGetMaxButtonCount;
        }
        int nGetMaxButtonCount = nGetMaxButtonCount(n);
        ControllerEnvironment.logln("Device has " + 0 + " buttons");
        list.addAll(WinTabComponent.createButtons(winTabContext, n, 0));
        return new WinTabDevice(winTabContext, n, nGetName, (Component[])list.toArray(new Component[0]));
    }
    
    private static final native String nGetName(final int p0);
    
    private static final native int[] nGetAxisDetails(final int p0, final int p1);
    
    private static final native String[] nGetCursorNames(final int p0);
    
    private static final native int nGetMaxButtonCount(final int p0);
}
