package optifine;

import java.lang.reflect.*;

public class ReflectorConstructor
{
    private ReflectorClass reflectorClass;
    private Class[] parameterTypes;
    private boolean checked;
    private Constructor targetConstructor;
    
    public ReflectorConstructor(final ReflectorClass reflectorClass, final Class[] parameterTypes) {
        this.reflectorClass = null;
        this.parameterTypes = null;
        this.checked = false;
        this.targetConstructor = null;
        this.reflectorClass = reflectorClass;
        this.parameterTypes = parameterTypes;
        this.getTargetConstructor();
    }
    
    public Constructor getTargetConstructor() {
        if (this.checked) {
            return this.targetConstructor;
        }
        this.checked = true;
        final Class targetClass = this.reflectorClass.getTargetClass();
        if (targetClass == null) {
            return null;
        }
        this.targetConstructor = findConstructor(targetClass, this.parameterTypes);
        if (this.targetConstructor == null) {
            Config.dbg("(Reflector) Constructor not present: " + targetClass.getName() + ", params: " + Config.arrayToString(this.parameterTypes));
        }
        if (this.targetConstructor != null) {
            this.targetConstructor.setAccessible(true);
        }
        return this.targetConstructor;
    }
    
    private static Constructor findConstructor(final Class clazz, final Class[] array) {
        final Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        while (0 < declaredConstructors.length) {
            final Constructor constructor = declaredConstructors[0];
            if (Reflector.matchesTypes(array, constructor.getParameterTypes())) {
                return constructor;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public boolean exists() {
        return this.checked ? (this.targetConstructor != null) : (this.getTargetConstructor() != null);
    }
    
    public void deactivate() {
        this.checked = true;
        this.targetConstructor = null;
    }
}
