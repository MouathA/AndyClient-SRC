package com.viaversion.viaversion.libs.javassist.util.proxy;

public interface ProxyObject extends Proxy
{
    void setHandler(final MethodHandler p0);
    
    MethodHandler getHandler();
}
