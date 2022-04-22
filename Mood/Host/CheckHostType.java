package Mood.Host;

public enum CheckHostType
{
    PING("PING", 0, "ping"), 
    TCP("TCP", 1, "tcp"), 
    UDP("UDP", 2, "udp"), 
    HTTP("HTTP", 3, "http"), 
    DNS("DNS", 4, "dns");
    
    private final String value;
    private static final CheckHostType[] ENUM$VALUES;
    
    static {
        ENUM$VALUES = new CheckHostType[] { CheckHostType.PING, CheckHostType.TCP, CheckHostType.UDP, CheckHostType.HTTP, CheckHostType.DNS };
    }
    
    private CheckHostType(final String s, final int n, final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}
