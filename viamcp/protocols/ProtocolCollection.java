package viamcp.protocols;

import com.viaversion.viaversion.api.protocol.version.*;

public enum ProtocolCollection
{
    R1_18_2("R1_18_2", 0, new ProtocolVersion(758, 67, "1.18.2 (Snapshot)", null), ProtocolInfoCollection.R1_18_2), 
    R1_18("R1_18", 1, new ProtocolVersion(757, -1, "1.18-1.18.1", new VersionRange("1.18", 0, 1)), ProtocolInfoCollection.R1_18), 
    R1_17_1("R1_17_1", 2, new ProtocolVersion(756, "1.17.1"), ProtocolInfoCollection.R1_17_1), 
    R1_17("R1_17", 3, new ProtocolVersion(755, "1.17"), ProtocolInfoCollection.R1_17), 
    R1_16_4("R1_16_4", 4, new ProtocolVersion(754, "1.16.4-1.16.5"), ProtocolInfoCollection.R1_16_4), 
    R1_16_3("R1_16_3", 5, new ProtocolVersion(753, "1.16.3"), ProtocolInfoCollection.R1_16_3), 
    R1_16_2("R1_16_2", 6, new ProtocolVersion(751, "1.16.2"), ProtocolInfoCollection.R1_16_2), 
    R1_16_1("R1_16_1", 7, new ProtocolVersion(736, "1.16.1"), ProtocolInfoCollection.R1_16_1), 
    R1_16("R1_16", 8, new ProtocolVersion(735, "1.16"), ProtocolInfoCollection.R1_16), 
    R1_15_2("R1_15_2", 9, new ProtocolVersion(578, "1.15.2"), ProtocolInfoCollection.R1_15_2), 
    R1_15_1("R1_15_1", 10, new ProtocolVersion(575, "1.15.1"), ProtocolInfoCollection.R1_15_1), 
    R1_15("R1_15", 11, new ProtocolVersion(573, "1.15"), ProtocolInfoCollection.R1_15), 
    R1_14_4("R1_14_4", 12, new ProtocolVersion(498, "1.14.4"), ProtocolInfoCollection.R1_14_4), 
    R1_14_3("R1_14_3", 13, new ProtocolVersion(490, "1.14.3"), ProtocolInfoCollection.R1_14_3), 
    R1_14_2("R1_14_2", 14, new ProtocolVersion(485, "1.14.2"), ProtocolInfoCollection.R1_14_2), 
    R1_14_1("R1_14_1", 15, new ProtocolVersion(480, "1.14.1"), ProtocolInfoCollection.R1_14_1), 
    R1_14("R1_14", 16, new ProtocolVersion(477, "1.14"), ProtocolInfoCollection.R1_14), 
    R1_13_2("R1_13_2", 17, new ProtocolVersion(404, "1.13.2"), ProtocolInfoCollection.R1_13_2), 
    R1_13_1("R1_13_1", 18, new ProtocolVersion(401, "1.13.1"), ProtocolInfoCollection.R1_13_1), 
    R1_13("R1_13", 19, new ProtocolVersion(393, "1.13"), ProtocolInfoCollection.R1_13), 
    R1_12_2("R1_12_2", 20, new ProtocolVersion(340, "1.12.2"), ProtocolInfoCollection.R1_12_2), 
    R1_12_1("R1_12_1", 21, new ProtocolVersion(338, "1.12.1"), ProtocolInfoCollection.R1_12_1), 
    R1_12("R1_12", 22, new ProtocolVersion(335, "1.12"), ProtocolInfoCollection.R1_12), 
    R1_11_1("R1_11_1", 23, new ProtocolVersion(316, "1.11.1-1.11.2"), ProtocolInfoCollection.R1_11_1), 
    R1_11("R1_11", 24, new ProtocolVersion(315, "1.11"), ProtocolInfoCollection.R1_11), 
    R1_10("R1_10", 25, new ProtocolVersion(210, "1.10.x"), ProtocolInfoCollection.R1_10), 
    R1_9_3("R1_9_3", 26, new ProtocolVersion(110, "1.9.3-1.9.4"), ProtocolInfoCollection.R1_9_3), 
    R1_9_2("R1_9_2", 27, new ProtocolVersion(109, "1.9.2"), ProtocolInfoCollection.R1_9_2), 
    R1_9_1("R1_9_1", 28, new ProtocolVersion(108, "1.9.1"), ProtocolInfoCollection.R1_9_1), 
    R1_9("R1_9", 29, new ProtocolVersion(107, "1.9"), ProtocolInfoCollection.R1_9), 
    R1_8("R1_8", 30, new ProtocolVersion(47, "1.8.x"), ProtocolInfoCollection.R1_8), 
    R1_7_6("R1_7_6", 31, new ProtocolVersion(5, -1, "1.7.6-1.7.10", new VersionRange("1.7", 6, 10)), ProtocolInfoCollection.R1_7_6), 
    R1_7("R1_7", 32, new ProtocolVersion(4, -1, "1.7-1.7.5", new VersionRange("1.7", 0, 5)), ProtocolInfoCollection.R1_7);
    
    private ProtocolVersion version;
    private ProtocolInfo info;
    private static final ProtocolCollection[] ENUM$VALUES;
    
    static {
        ENUM$VALUES = new ProtocolCollection[] { ProtocolCollection.R1_18_2, ProtocolCollection.R1_18, ProtocolCollection.R1_17_1, ProtocolCollection.R1_17, ProtocolCollection.R1_16_4, ProtocolCollection.R1_16_3, ProtocolCollection.R1_16_2, ProtocolCollection.R1_16_1, ProtocolCollection.R1_16, ProtocolCollection.R1_15_2, ProtocolCollection.R1_15_1, ProtocolCollection.R1_15, ProtocolCollection.R1_14_4, ProtocolCollection.R1_14_3, ProtocolCollection.R1_14_2, ProtocolCollection.R1_14_1, ProtocolCollection.R1_14, ProtocolCollection.R1_13_2, ProtocolCollection.R1_13_1, ProtocolCollection.R1_13, ProtocolCollection.R1_12_2, ProtocolCollection.R1_12_1, ProtocolCollection.R1_12, ProtocolCollection.R1_11_1, ProtocolCollection.R1_11, ProtocolCollection.R1_10, ProtocolCollection.R1_9_3, ProtocolCollection.R1_9_2, ProtocolCollection.R1_9_1, ProtocolCollection.R1_9, ProtocolCollection.R1_8, ProtocolCollection.R1_7_6, ProtocolCollection.R1_7 };
    }
    
    private ProtocolCollection(final String s, final int n, final ProtocolVersion version, final ProtocolInfo info) {
        this.version = version;
        this.info = info;
    }
    
    public ProtocolVersion getVersion() {
        return this.version;
    }
    
    public ProtocolInfo getInfo() {
        return this.info;
    }
    
    public static ProtocolCollection getProtocolCollectionById(final int n) {
        ProtocolCollection[] values;
        while (0 < (values = values()).length) {
            final ProtocolCollection collection = values[0];
            if (collection.getVersion().getVersion() == n) {
                return collection;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    public static ProtocolVersion getProtocolById(final int n) {
        ProtocolCollection[] values;
        while (0 < (values = values()).length) {
            final ProtocolCollection collection = values[0];
            if (collection.getVersion().getVersion() == n) {
                return collection.getVersion();
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    public static ProtocolInfo getProtocolInfoById(final int n) {
        ProtocolCollection[] values;
        while (0 < (values = values()).length) {
            final ProtocolCollection collection = values[0];
            if (collection.getVersion().getVersion() == n) {
                return collection.getInfo();
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
}
