package org.lwjgl.input;

import org.lwjgl.opengl.*;
import java.security.*;
import java.lang.reflect.*;

final class OpenGLPackageAccess
{
    static final Object global_lock;
    
    static InputImplementation createImplementation() {
        return AccessController.doPrivileged((PrivilegedExceptionAction<InputImplementation>)new PrivilegedExceptionAction() {
            public InputImplementation run() throws Exception {
                final Method declaredMethod = Display.class.getDeclaredMethod("getImplementation", (Class<?>[])new Class[0]);
                declaredMethod.setAccessible(true);
                return (InputImplementation)declaredMethod.invoke(null, new Object[0]);
            }
            
            public Object run() throws Exception {
                return this.run();
            }
        });
    }
    
    static {
        global_lock = AccessController.doPrivileged((PrivilegedExceptionAction<Object>)new PrivilegedExceptionAction() {
            public Object run() throws Exception {
                final Field declaredField = Class.forName("org.lwjgl.opengl.GlobalLock").getDeclaredField("lock");
                declaredField.setAccessible(true);
                return declaredField.get(null);
            }
        });
    }
}
