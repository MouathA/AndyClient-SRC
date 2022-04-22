package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.*;

public final class RtspVersions
{
    public static final HttpVersion RTSP_1_0;
    
    public static HttpVersion valueOf(String upperCase) {
        if (upperCase == null) {
            throw new NullPointerException("text");
        }
        upperCase = upperCase.trim().toUpperCase();
        if ("RTSP/1.0".equals(upperCase)) {
            return RtspVersions.RTSP_1_0;
        }
        return new HttpVersion(upperCase, true);
    }
    
    private RtspVersions() {
    }
    
    static {
        RTSP_1_0 = new HttpVersion("RTSP", 1, 0, true);
    }
}
