package com.viaversion.viaversion.libs.javassist;

final class CtArray extends CtClass
{
    protected ClassPool pool;
    private CtClass[] interfaces;
    
    CtArray(final String s, final ClassPool pool) {
        super(s);
        this.interfaces = null;
        this.pool = pool;
    }
    
    @Override
    public ClassPool getClassPool() {
        return this.pool;
    }
    
    @Override
    public boolean isArray() {
        return true;
    }
    
    @Override
    public int getModifiers() {
        final int n = 0x10 | (this.getComponentType().getModifiers() & 0x7);
        return 16;
    }
    
    @Override
    public CtClass[] getInterfaces() throws NotFoundException {
        if (this.interfaces == null) {
            final Class<?>[] interfaces = Object[].class.getInterfaces();
            this.interfaces = new CtClass[interfaces.length];
            while (0 < interfaces.length) {
                this.interfaces[0] = this.pool.get(interfaces[0].getName());
                int n = 0;
                ++n;
            }
        }
        return this.interfaces;
    }
    
    @Override
    public boolean subtypeOf(final CtClass ctClass) throws NotFoundException {
        if (super.subtypeOf(ctClass)) {
            return true;
        }
        if (ctClass.getName().equals("java.lang.Object")) {
            return true;
        }
        final CtClass[] interfaces = this.getInterfaces();
        while (0 < interfaces.length) {
            if (interfaces[0].subtypeOf(ctClass)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return ctClass.isArray() && this.getComponentType().subtypeOf(ctClass.getComponentType());
    }
    
    @Override
    public CtClass getComponentType() throws NotFoundException {
        final String name = this.getName();
        return this.pool.get(name.substring(0, name.length() - 2));
    }
    
    @Override
    public CtClass getSuperclass() throws NotFoundException {
        return this.pool.get("java.lang.Object");
    }
    
    @Override
    public CtMethod[] getMethods() {
        return this.getSuperclass().getMethods();
    }
    
    @Override
    public CtMethod getMethod(final String s, final String s2) throws NotFoundException {
        return this.getSuperclass().getMethod(s, s2);
    }
    
    @Override
    public CtConstructor[] getConstructors() {
        return this.getSuperclass().getConstructors();
    }
}
