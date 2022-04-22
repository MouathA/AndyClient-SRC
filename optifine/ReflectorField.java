package optifine;

import java.lang.reflect.*;

public class ReflectorField
{
    private ReflectorClass reflectorClass;
    private String targetFieldName;
    private boolean checked;
    private Field targetField;
    
    public ReflectorField(final ReflectorClass reflectorClass, final String targetFieldName) {
        this.reflectorClass = null;
        this.targetFieldName = null;
        this.checked = false;
        this.targetField = null;
        this.reflectorClass = reflectorClass;
        this.targetFieldName = targetFieldName;
        this.getTargetField();
    }
    
    public Field getTargetField() {
        if (this.checked) {
            return this.targetField;
        }
        this.checked = true;
        final Class targetClass = this.reflectorClass.getTargetClass();
        if (targetClass == null) {
            return null;
        }
        (this.targetField = targetClass.getDeclaredField(this.targetFieldName)).setAccessible(true);
        return this.targetField;
    }
    
    public Object getValue() {
        return Reflector.getFieldValue(null, this);
    }
    
    public void setValue(final Object o) {
        Reflector.setFieldValue(null, this, o);
    }
    
    public boolean exists() {
        return this.checked ? (this.targetField != null) : (this.getTargetField() != null);
    }
}
