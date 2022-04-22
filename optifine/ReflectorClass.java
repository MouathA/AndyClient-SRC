package optifine;

public class ReflectorClass
{
    private String targetClassName;
    private boolean checked;
    private Class targetClass;
    
    public ReflectorClass(final String s) {
        this(s, false);
    }
    
    public ReflectorClass(final String targetClassName, final boolean b) {
        this.targetClassName = null;
        this.checked = false;
        this.targetClass = null;
        this.targetClassName = targetClassName;
        if (!b) {
            this.getTargetClass();
        }
    }
    
    public ReflectorClass(final Class targetClass) {
        this.targetClassName = null;
        this.checked = false;
        this.targetClass = null;
        this.targetClass = targetClass;
        this.targetClassName = targetClass.getName();
        this.checked = true;
    }
    
    public Class getTargetClass() {
        if (this.checked) {
            return this.targetClass;
        }
        this.checked = true;
        return this.targetClass = Class.forName(this.targetClassName);
    }
    
    public boolean exists() {
        return this.getTargetClass() != null;
    }
    
    public String getTargetClassName() {
        return this.targetClassName;
    }
    
    public boolean isInstance(final Object o) {
        return this.getTargetClass() != null && this.getTargetClass().isInstance(o);
    }
    
    public ReflectorField makeField(final String s) {
        return new ReflectorField(this, s);
    }
    
    public ReflectorMethod makeMethod(final String s) {
        return new ReflectorMethod(this, s);
    }
    
    public ReflectorMethod makeMethod(final String s, final Class[] array) {
        return new ReflectorMethod(this, s, array);
    }
    
    public ReflectorMethod makeMethod(final String s, final Class[] array, final boolean b) {
        return new ReflectorMethod(this, s, array, b);
    }
}
