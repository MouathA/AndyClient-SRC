package optifine;

import java.lang.reflect.*;

public class ReflectorMethod
{
    private ReflectorClass reflectorClass;
    private String targetMethodName;
    private Class[] targetMethodParameterTypes;
    private boolean checked;
    private Method targetMethod;
    
    public ReflectorMethod(final ReflectorClass reflectorClass, final String s) {
        this(reflectorClass, s, null, false);
    }
    
    public ReflectorMethod(final ReflectorClass reflectorClass, final String s, final Class[] array) {
        this(reflectorClass, s, array, false);
    }
    
    public ReflectorMethod(final ReflectorClass reflectorClass, final String targetMethodName, final Class[] targetMethodParameterTypes, final boolean b) {
        this.reflectorClass = null;
        this.targetMethodName = null;
        this.targetMethodParameterTypes = null;
        this.checked = false;
        this.targetMethod = null;
        this.reflectorClass = reflectorClass;
        this.targetMethodName = targetMethodName;
        this.targetMethodParameterTypes = targetMethodParameterTypes;
        if (!b) {
            this.getTargetMethod();
        }
    }
    
    public Method getTargetMethod() {
        if (this.checked) {
            return this.targetMethod;
        }
        this.checked = true;
        final Class targetClass = this.reflectorClass.getTargetClass();
        if (targetClass == null) {
            return null;
        }
        if (this.targetMethodParameterTypes == null) {
            final Method[] methods = Reflector.getMethods(targetClass, this.targetMethodName);
            if (methods.length <= 0) {
                Config.log("(Reflector) Method not present: " + targetClass.getName() + "." + this.targetMethodName);
                return null;
            }
            if (methods.length > 1) {
                Config.warn("(Reflector) More than one method found: " + targetClass.getName() + "." + this.targetMethodName);
                while (0 < methods.length) {
                    Config.warn("(Reflector)  - " + methods[0]);
                    int n = 0;
                    ++n;
                }
                return null;
            }
            this.targetMethod = methods[0];
        }
        else {
            this.targetMethod = Reflector.getMethod(targetClass, this.targetMethodName, this.targetMethodParameterTypes);
        }
        if (this.targetMethod == null) {
            Config.log("(Reflector) Method not present: " + targetClass.getName() + "." + this.targetMethodName);
            return null;
        }
        this.targetMethod.setAccessible(true);
        return this.targetMethod;
    }
    
    public boolean exists() {
        return this.checked ? (this.targetMethod != null) : (this.getTargetMethod() != null);
    }
    
    public Class getReturnType() {
        final Method targetMethod = this.getTargetMethod();
        return (targetMethod == null) ? null : targetMethod.getReturnType();
    }
    
    public void deactivate() {
        this.checked = true;
        this.targetMethod = null;
    }
}
