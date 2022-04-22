package net.java.games.input;

import java.util.logging.*;
import java.io.*;
import java.util.*;

final class OSXHIDDevice
{
    private static final Logger log;
    private static final String kIOHIDTransportKey;
    private static final String kIOHIDVendorIDKey;
    private static final String kIOHIDVendorIDSourceKey;
    private static final String kIOHIDProductIDKey;
    private static final String kIOHIDVersionNumberKey;
    private static final String kIOHIDManufacturerKey;
    private static final String kIOHIDProductKey;
    private static final String kIOHIDSerialNumberKey;
    private static final String kIOHIDCountryCodeKey;
    private static final String kIOHIDLocationIDKey;
    private static final String kIOHIDDeviceUsageKey;
    private static final String kIOHIDDeviceUsagePageKey;
    private static final String kIOHIDDeviceUsagePairsKey;
    private static final String kIOHIDPrimaryUsageKey;
    private static final String kIOHIDPrimaryUsagePageKey;
    private static final String kIOHIDMaxInputReportSizeKey;
    private static final String kIOHIDMaxOutputReportSizeKey;
    private static final String kIOHIDMaxFeatureReportSizeKey;
    private static final String kIOHIDElementKey;
    private static final String kIOHIDElementCookieKey;
    private static final String kIOHIDElementTypeKey;
    private static final String kIOHIDElementCollectionTypeKey;
    private static final String kIOHIDElementUsageKey;
    private static final String kIOHIDElementUsagePageKey;
    private static final String kIOHIDElementMinKey;
    private static final String kIOHIDElementMaxKey;
    private static final String kIOHIDElementScaledMinKey;
    private static final String kIOHIDElementScaledMaxKey;
    private static final String kIOHIDElementSizeKey;
    private static final String kIOHIDElementReportSizeKey;
    private static final String kIOHIDElementReportCountKey;
    private static final String kIOHIDElementReportIDKey;
    private static final String kIOHIDElementIsArrayKey;
    private static final String kIOHIDElementIsRelativeKey;
    private static final String kIOHIDElementIsWrappingKey;
    private static final String kIOHIDElementIsNonLinearKey;
    private static final String kIOHIDElementHasPreferredStateKey;
    private static final String kIOHIDElementHasNullStateKey;
    private static final String kIOHIDElementUnitKey;
    private static final String kIOHIDElementUnitExponentKey;
    private static final String kIOHIDElementNameKey;
    private static final String kIOHIDElementValueLocationKey;
    private static final String kIOHIDElementDuplicateIndexKey;
    private static final String kIOHIDElementParentCollectionKey;
    private final long device_address;
    private final long device_interface_address;
    private final Map properties;
    private boolean released;
    static Class class$net$java$games$input$OSXHIDDevice;
    
    public OSXHIDDevice(final long device_address, final long device_interface_address) throws IOException {
        this.device_address = device_address;
        this.device_interface_address = device_interface_address;
        this.properties = this.getDeviceProperties();
        this.open();
    }
    
    public final Controller.PortType getPortType() {
        final String s = this.properties.get("Transport");
        if (s == null) {
            return Controller.PortType.UNKNOWN;
        }
        if (s.equals("USB")) {
            return Controller.PortType.USB;
        }
        return Controller.PortType.UNKNOWN;
    }
    
    public final String getProductName() {
        return this.properties.get("Product");
    }
    
    private final OSXHIDElement createElementFromElementProperties(final Map map) {
        final long longFromProperties = getLongFromProperties(map, "ElementCookie");
        final ElementType map2 = ElementType.map(getIntFromProperties(map, "Type"));
        final int n = (int)getLongFromProperties(map, "Min", 0L);
        final int n2 = (int)getLongFromProperties(map, "Max", 65536L);
        final UsagePair usagePair = this.getUsagePair();
        final boolean booleanFromProperties = getBooleanFromProperties(map, "IsRelative", usagePair != null && (usagePair.getUsage() == GenericDesktopUsage.POINTER || usagePair.getUsage() == GenericDesktopUsage.MOUSE));
        final UsagePair usagePair2 = createUsagePair(getIntFromProperties(map, "UsagePage"), getIntFromProperties(map, "Usage"));
        if (usagePair2 == null || (map2 != ElementType.INPUT_MISC && map2 != ElementType.INPUT_BUTTON && map2 != ElementType.INPUT_AXIS)) {
            return null;
        }
        return new OSXHIDElement(this, usagePair2, longFromProperties, map2, n, n2, booleanFromProperties);
    }
    
    private final void addElements(final List list, final Map map) {
        final Object[] array = map.get("Elements");
        if (array == null) {
            return;
        }
        while (0 < array.length) {
            final Map map2 = (Map)array[0];
            final OSXHIDElement elementFromElementProperties = this.createElementFromElementProperties(map2);
            if (elementFromElementProperties != null) {
                list.add(elementFromElementProperties);
            }
            this.addElements(list, map2);
            int n = 0;
            ++n;
        }
    }
    
    public final List getElements() {
        final ArrayList list = new ArrayList();
        this.addElements(list, this.properties);
        return list;
    }
    
    private static final long getLongFromProperties(final Map map, final String s, final long n) {
        final Long n2 = map.get(s);
        if (n2 == null) {
            return n;
        }
        return n2;
    }
    
    private static final boolean getBooleanFromProperties(final Map map, final String s, final boolean b) {
        return getLongFromProperties(map, s, b ? 1 : 0) != 0L;
    }
    
    private static final int getIntFromProperties(final Map map, final String s) {
        return (int)getLongFromProperties(map, s);
    }
    
    private static final long getLongFromProperties(final Map map, final String s) {
        return map.get(s);
    }
    
    private static final UsagePair createUsagePair(final int n, final int n2) {
        final UsagePage map = UsagePage.map(n);
        if (map != null) {
            final Usage mapUsage = map.mapUsage(n2);
            if (mapUsage != null) {
                return new UsagePair(map, mapUsage);
            }
        }
        return null;
    }
    
    public final UsagePair getUsagePair() {
        return createUsagePair(getIntFromProperties(this.properties, "PrimaryUsagePage"), getIntFromProperties(this.properties, "PrimaryUsage"));
    }
    
    private final void dumpProperties() {
        OSXHIDDevice.log.info(this.toString());
        dumpMap("", this.properties);
    }
    
    private static final void dumpArray(final String s, final Object[] array) {
        OSXHIDDevice.log.info(s + "{");
        while (0 < array.length) {
            dumpObject(s + "\t", array[0]);
            OSXHIDDevice.log.info(s + ",");
            int n = 0;
            ++n;
        }
        OSXHIDDevice.log.info(s + "}");
    }
    
    private static final void dumpMap(final String s, final Map map) {
        for (final Object next : map.keySet()) {
            final Object value = map.get(next);
            dumpObject(s, next);
            dumpObject(s + "\t", value);
        }
    }
    
    private static final void dumpObject(final String s, final Object o) {
        if (o instanceof Long) {
            OSXHIDDevice.log.info(s + "0x" + Long.toHexString((long)o));
        }
        else if (o instanceof Map) {
            dumpMap(s, (Map)o);
        }
        else if (o.getClass().isArray()) {
            dumpArray(s, (Object[])o);
        }
        else {
            OSXHIDDevice.log.info(s + o);
        }
    }
    
    private final Map getDeviceProperties() throws IOException {
        return nGetDeviceProperties(this.device_address);
    }
    
    private static final native Map nGetDeviceProperties(final long p0) throws IOException;
    
    public final synchronized void release() throws IOException {
        this.close();
        this.released = true;
        nReleaseDevice(this.device_address, this.device_interface_address);
    }
    
    private static final native void nReleaseDevice(final long p0, final long p1);
    
    public final synchronized void getElementValue(final long n, final OSXEvent osxEvent) throws IOException {
        this.checkReleased();
        nGetElementValue(this.device_interface_address, n, osxEvent);
    }
    
    private static final native void nGetElementValue(final long p0, final long p1, final OSXEvent p2) throws IOException;
    
    public final synchronized OSXHIDQueue createQueue(final int n) throws IOException {
        this.checkReleased();
        return new OSXHIDQueue(nCreateQueue(this.device_interface_address), n);
    }
    
    private static final native long nCreateQueue(final long p0) throws IOException;
    
    private final void open() throws IOException {
        nOpen(this.device_interface_address);
    }
    
    private static final native void nOpen(final long p0) throws IOException;
    
    private final void close() throws IOException {
        nClose(this.device_interface_address);
    }
    
    private static final native void nClose(final long p0) throws IOException;
    
    private final void checkReleased() throws IOException {
        if (this.released) {
            throw new IOException();
        }
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static {
        kIOHIDElementUnitExponentKey = "UnitExponent";
        kIOHIDElementValueLocationKey = "ValueLocation";
        kIOHIDVendorIDKey = "VendorID";
        kIOHIDProductIDKey = "ProductID";
        kIOHIDProductKey = "Product";
        kIOHIDElementHasNullStateKey = "HasNullState";
        kIOHIDDeviceUsagePageKey = "DeviceUsagePage";
        kIOHIDElementCollectionTypeKey = "CollectionType";
        kIOHIDElementMinKey = "Min";
        kIOHIDMaxOutputReportSizeKey = "MaxOutputReportSize";
        kIOHIDDeviceUsagePairsKey = "DeviceUsagePairs";
        kIOHIDSerialNumberKey = "SerialNumber";
        kIOHIDElementUsageKey = "Usage";
        kIOHIDElementCookieKey = "ElementCookie";
        kIOHIDElementHasPreferredStateKey = "HasPreferredState";
        kIOHIDElementParentCollectionKey = "ParentCollection";
        kIOHIDElementIsRelativeKey = "IsRelative";
        kIOHIDLocationIDKey = "LocationID";
        kIOHIDDeviceUsageKey = "DeviceUsage";
        kIOHIDElementScaledMaxKey = "ScaledMax";
        kIOHIDElementReportSizeKey = "ReportSize";
        kIOHIDElementReportCountKey = "ReportCount";
        kIOHIDElementScaledMinKey = "ScaledMin";
        kIOHIDElementDuplicateIndexKey = "DuplicateIndex";
        kIOHIDMaxFeatureReportSizeKey = "MaxFeatureReportSize";
        kIOHIDElementUsagePageKey = "UsagePage";
        kIOHIDElementUnitKey = "Unit";
        kIOHIDElementReportIDKey = "ReportID";
        kIOHIDTransportKey = "Transport";
        kIOHIDVersionNumberKey = "VersionNumber";
        kIOHIDPrimaryUsagePageKey = "PrimaryUsagePage";
        kIOHIDVendorIDSourceKey = "VendorIDSource";
        kIOHIDElementTypeKey = "Type";
        kIOHIDElementNameKey = "Name";
        kIOHIDElementKey = "Elements";
        kIOHIDManufacturerKey = "Manufacturer";
        kIOHIDElementMaxKey = "Max";
        kIOHIDElementIsArrayKey = "IsArray";
        kIOHIDElementSizeKey = "Size";
        kIOHIDElementIsNonLinearKey = "IsNonLinear";
        kIOHIDMaxInputReportSizeKey = "MaxInputReportSize";
        kIOHIDCountryCodeKey = "CountryCode";
        kIOHIDPrimaryUsageKey = "PrimaryUsage";
        kIOHIDElementIsWrappingKey = "IsWrapping";
        log = Logger.getLogger(((OSXHIDDevice.class$net$java$games$input$OSXHIDDevice == null) ? (OSXHIDDevice.class$net$java$games$input$OSXHIDDevice = class$("net.java.games.input.OSXHIDDevice")) : OSXHIDDevice.class$net$java$games$input$OSXHIDDevice).getName());
    }
}
