package com.sun.jna.platform.win32;

import com.sun.jna.win32.*;

public interface LMJoin extends StdCallLibrary
{
    public abstract static class NETSETUP_JOIN_STATUS
    {
        public static final int NetSetupUnknownStatus = 0;
        public static final int NetSetupUnjoined = 1;
        public static final int NetSetupWorkgroupName = 2;
        public static final int NetSetupDomainName = 3;
    }
}
