package com.google.common.base;

import java.nio.charset.*;
import com.google.common.annotations.*;

@GwtCompatible(emulated = true)
public final class Charsets
{
    @GwtIncompatible("Non-UTF-8 Charset")
    public static final Charset US_ASCII;
    @GwtIncompatible("Non-UTF-8 Charset")
    public static final Charset ISO_8859_1;
    public static final Charset UTF_8;
    @GwtIncompatible("Non-UTF-8 Charset")
    public static final Charset UTF_16BE;
    @GwtIncompatible("Non-UTF-8 Charset")
    public static final Charset UTF_16LE;
    @GwtIncompatible("Non-UTF-8 Charset")
    public static final Charset UTF_16;
    
    private Charsets() {
    }
    
    static {
        US_ASCII = Charset.forName("US-ASCII");
        ISO_8859_1 = Charset.forName("ISO-8859-1");
        UTF_8 = Charset.forName("UTF-8");
        UTF_16BE = Charset.forName("UTF-16BE");
        UTF_16LE = Charset.forName("UTF-16LE");
        UTF_16 = Charset.forName("UTF-16");
    }
}
