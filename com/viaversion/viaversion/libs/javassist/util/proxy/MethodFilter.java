package com.viaversion.viaversion.libs.javassist.util.proxy;

import java.lang.reflect.*;

public interface MethodFilter
{
    boolean isHandled(final Method p0);
}
