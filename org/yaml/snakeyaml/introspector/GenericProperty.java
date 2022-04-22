package org.yaml.snakeyaml.introspector;

import java.lang.reflect.*;

public abstract class GenericProperty extends Property
{
    private Type genType;
    private boolean actualClassesChecked;
    private Class[] actualClasses;
    
    public GenericProperty(final String s, final Class clazz, final Type genType) {
        super(s, clazz);
        this.genType = genType;
        this.actualClassesChecked = (genType == null);
    }
    
    @Override
    public Class[] getActualTypeArguments() {
        if (!this.actualClassesChecked) {
            if (this.genType instanceof ParameterizedType) {
                final Type[] actualTypeArguments = ((ParameterizedType)this.genType).getActualTypeArguments();
                if (actualTypeArguments.length > 0) {
                    this.actualClasses = new Class[actualTypeArguments.length];
                    while (0 < actualTypeArguments.length) {
                        if (actualTypeArguments[0] instanceof Class) {
                            this.actualClasses[0] = (Class)actualTypeArguments[0];
                        }
                        else if (actualTypeArguments[0] instanceof ParameterizedType) {
                            this.actualClasses[0] = (Class)((ParameterizedType)actualTypeArguments[0]).getRawType();
                        }
                        else {
                            if (!(actualTypeArguments[0] instanceof GenericArrayType)) {
                                this.actualClasses = null;
                                break;
                            }
                            final Type genericComponentType = ((GenericArrayType)actualTypeArguments[0]).getGenericComponentType();
                            if (!(genericComponentType instanceof Class)) {
                                this.actualClasses = null;
                                break;
                            }
                            this.actualClasses[0] = Array.newInstance((Class<?>)genericComponentType, 0).getClass();
                        }
                        int n = 0;
                        ++n;
                    }
                }
            }
            else if (this.genType instanceof GenericArrayType) {
                final Type genericComponentType2 = ((GenericArrayType)this.genType).getGenericComponentType();
                if (genericComponentType2 instanceof Class) {
                    this.actualClasses = new Class[] { (Class)genericComponentType2 };
                }
            }
            else if (this.genType instanceof Class && ((Class)this.genType).isArray()) {
                (this.actualClasses = new Class[1])[0] = this.getType().getComponentType();
            }
            this.actualClassesChecked = true;
        }
        return this.actualClasses;
    }
}
