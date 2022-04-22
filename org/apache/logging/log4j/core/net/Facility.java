package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.util.*;

public enum Facility
{
    KERN("KERN", 0, 0), 
    USER("USER", 1, 1), 
    MAIL("MAIL", 2, 2), 
    DAEMON("DAEMON", 3, 3), 
    AUTH("AUTH", 4, 4), 
    SYSLOG("SYSLOG", 5, 5), 
    LPR("LPR", 6, 6), 
    NEWS("NEWS", 7, 7), 
    UUCP("UUCP", 8, 8), 
    CRON("CRON", 9, 9), 
    AUTHPRIV("AUTHPRIV", 10, 10), 
    FTP("FTP", 11, 11), 
    NTP("NTP", 12, 12), 
    LOG_AUDIT("LOG_AUDIT", 13, 13), 
    LOG_ALERT("LOG_ALERT", 14, 14), 
    CLOCK("CLOCK", 15, 15), 
    LOCAL0("LOCAL0", 16, 16), 
    LOCAL1("LOCAL1", 17, 17), 
    LOCAL2("LOCAL2", 18, 18), 
    LOCAL3("LOCAL3", 19, 19), 
    LOCAL4("LOCAL4", 20, 20), 
    LOCAL5("LOCAL5", 21, 21), 
    LOCAL6("LOCAL6", 22, 22), 
    LOCAL7("LOCAL7", 23, 23);
    
    private final int code;
    private static final Facility[] $VALUES;
    
    private Facility(final String s, final int n, final int code) {
        this.code = code;
    }
    
    public static Facility toFacility(final String s) {
        return toFacility(s, null);
    }
    
    public static Facility toFacility(final String s, final Facility facility) {
        return (Facility)EnglishEnums.valueOf(Facility.class, s, facility);
    }
    
    public int getCode() {
        return this.code;
    }
    
    public boolean isEqual(final String s) {
        return this.name().equalsIgnoreCase(s);
    }
    
    static {
        $VALUES = new Facility[] { Facility.KERN, Facility.USER, Facility.MAIL, Facility.DAEMON, Facility.AUTH, Facility.SYSLOG, Facility.LPR, Facility.NEWS, Facility.UUCP, Facility.CRON, Facility.AUTHPRIV, Facility.FTP, Facility.NTP, Facility.LOG_AUDIT, Facility.LOG_ALERT, Facility.CLOCK, Facility.LOCAL0, Facility.LOCAL1, Facility.LOCAL2, Facility.LOCAL3, Facility.LOCAL4, Facility.LOCAL5, Facility.LOCAL6, Facility.LOCAL7 };
    }
}
