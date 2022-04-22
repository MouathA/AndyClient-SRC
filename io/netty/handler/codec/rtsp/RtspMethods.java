package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.*;
import java.util.*;

public final class RtspMethods
{
    public static final HttpMethod OPTIONS;
    public static final HttpMethod DESCRIBE;
    public static final HttpMethod ANNOUNCE;
    public static final HttpMethod SETUP;
    public static final HttpMethod PLAY;
    public static final HttpMethod PAUSE;
    public static final HttpMethod TEARDOWN;
    public static final HttpMethod GET_PARAMETER;
    public static final HttpMethod SET_PARAMETER;
    public static final HttpMethod REDIRECT;
    public static final HttpMethod RECORD;
    private static final Map methodMap;
    
    public static HttpMethod valueOf(String upperCase) {
        if (upperCase == null) {
            throw new NullPointerException("name");
        }
        upperCase = upperCase.trim().toUpperCase();
        if (upperCase.isEmpty()) {
            throw new IllegalArgumentException("empty name");
        }
        final HttpMethod httpMethod = RtspMethods.methodMap.get(upperCase);
        if (httpMethod != null) {
            return httpMethod;
        }
        return new HttpMethod(upperCase);
    }
    
    private RtspMethods() {
    }
    
    static {
        OPTIONS = HttpMethod.OPTIONS;
        DESCRIBE = new HttpMethod("DESCRIBE");
        ANNOUNCE = new HttpMethod("ANNOUNCE");
        SETUP = new HttpMethod("SETUP");
        PLAY = new HttpMethod("PLAY");
        PAUSE = new HttpMethod("PAUSE");
        TEARDOWN = new HttpMethod("TEARDOWN");
        GET_PARAMETER = new HttpMethod("GET_PARAMETER");
        SET_PARAMETER = new HttpMethod("SET_PARAMETER");
        REDIRECT = new HttpMethod("REDIRECT");
        RECORD = new HttpMethod("RECORD");
        (methodMap = new HashMap()).put(RtspMethods.DESCRIBE.toString(), RtspMethods.DESCRIBE);
        RtspMethods.methodMap.put(RtspMethods.ANNOUNCE.toString(), RtspMethods.ANNOUNCE);
        RtspMethods.methodMap.put(RtspMethods.GET_PARAMETER.toString(), RtspMethods.GET_PARAMETER);
        RtspMethods.methodMap.put(RtspMethods.OPTIONS.toString(), RtspMethods.OPTIONS);
        RtspMethods.methodMap.put(RtspMethods.PAUSE.toString(), RtspMethods.PAUSE);
        RtspMethods.methodMap.put(RtspMethods.PLAY.toString(), RtspMethods.PLAY);
        RtspMethods.methodMap.put(RtspMethods.RECORD.toString(), RtspMethods.RECORD);
        RtspMethods.methodMap.put(RtspMethods.REDIRECT.toString(), RtspMethods.REDIRECT);
        RtspMethods.methodMap.put(RtspMethods.SETUP.toString(), RtspMethods.SETUP);
        RtspMethods.methodMap.put(RtspMethods.SET_PARAMETER.toString(), RtspMethods.SET_PARAMETER);
        RtspMethods.methodMap.put(RtspMethods.TEARDOWN.toString(), RtspMethods.TEARDOWN);
    }
}
