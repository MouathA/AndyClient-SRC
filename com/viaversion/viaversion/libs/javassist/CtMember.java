package com.viaversion.viaversion.libs.javassist;

public abstract class CtMember
{
    CtMember next;
    protected CtClass declaringClass;
    
    protected CtMember(final CtClass declaringClass) {
        this.declaringClass = declaringClass;
        this.next = null;
    }
    
    final CtMember next() {
        return this.next;
    }
    
    void nameReplaced() {
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(this.getClass().getName());
        sb.append("@");
        sb.append(Integer.toHexString(this.hashCode()));
        sb.append("[");
        sb.append(Modifier.toString(this.getModifiers()));
        this.extendToString(sb);
        sb.append("]");
        return sb.toString();
    }
    
    protected abstract void extendToString(final StringBuffer p0);
    
    public CtClass getDeclaringClass() {
        return this.declaringClass;
    }
    
    public boolean visibleFrom(final CtClass ctClass) {
        final int modifiers = this.getModifiers();
        if (Modifier.isPublic(modifiers)) {
            return true;
        }
        if (Modifier.isPrivate(modifiers)) {
            return ctClass == this.declaringClass;
        }
        final String packageName = this.declaringClass.getPackageName();
        final String packageName2 = ctClass.getPackageName();
        boolean equals;
        if (packageName == null) {
            equals = (packageName2 == null);
        }
        else {
            equals = packageName.equals(packageName2);
        }
        if (!equals && Modifier.isProtected(modifiers)) {
            return ctClass.subclassOf(this.declaringClass);
        }
        return equals;
    }
    
    public abstract int getModifiers();
    
    public abstract void setModifiers(final int p0);
    
    public boolean hasAnnotation(final Class clazz) {
        return this.hasAnnotation(clazz.getName());
    }
    
    public abstract boolean hasAnnotation(final String p0);
    
    public abstract Object getAnnotation(final Class p0) throws ClassNotFoundException;
    
    public abstract Object[] getAnnotations() throws ClassNotFoundException;
    
    public abstract Object[] getAvailableAnnotations();
    
    public abstract String getName();
    
    public abstract String getSignature();
    
    public abstract String getGenericSignature();
    
    public abstract void setGenericSignature(final String p0);
    
    public abstract byte[] getAttribute(final String p0);
    
    public abstract void setAttribute(final String p0, final byte[] p1);
    
    static class Cache extends CtMember
    {
        private CtMember methodTail;
        private CtMember consTail;
        private CtMember fieldTail;
        
        @Override
        protected void extendToString(final StringBuffer sb) {
        }
        
        @Override
        public boolean hasAnnotation(final String s) {
            return false;
        }
        
        @Override
        public Object getAnnotation(final Class clazz) throws ClassNotFoundException {
            return null;
        }
        
        @Override
        public Object[] getAnnotations() throws ClassNotFoundException {
            return null;
        }
        
        @Override
        public byte[] getAttribute(final String s) {
            return null;
        }
        
        @Override
        public Object[] getAvailableAnnotations() {
            return null;
        }
        
        @Override
        public int getModifiers() {
            return 0;
        }
        
        @Override
        public String getName() {
            return null;
        }
        
        @Override
        public String getSignature() {
            return null;
        }
        
        @Override
        public void setAttribute(final String s, final byte[] array) {
        }
        
        @Override
        public void setModifiers(final int n) {
        }
        
        @Override
        public String getGenericSignature() {
            return null;
        }
        
        @Override
        public void setGenericSignature(final String s) {
        }
        
        Cache(final CtClassType ctClassType) {
            super(ctClassType);
            this.methodTail = this;
            this.consTail = this;
            this.fieldTail = this;
            this.fieldTail.next = this;
        }
        
        CtMember methodHead() {
            return this;
        }
        
        CtMember lastMethod() {
            return this.methodTail;
        }
        
        CtMember consHead() {
            return this.methodTail;
        }
        
        CtMember lastCons() {
            return this.consTail;
        }
        
        CtMember fieldHead() {
            return this.consTail;
        }
        
        CtMember lastField() {
            return this.fieldTail;
        }
        
        void addMethod(final CtMember ctMember) {
            ctMember.next = this.methodTail.next;
            this.methodTail.next = ctMember;
            if (this.methodTail == this.consTail) {
                this.consTail = ctMember;
                if (this.methodTail == this.fieldTail) {
                    this.fieldTail = ctMember;
                }
            }
            this.methodTail = ctMember;
        }
        
        void addConstructor(final CtMember consTail) {
            consTail.next = this.consTail.next;
            this.consTail.next = consTail;
            if (this.consTail == this.fieldTail) {
                this.fieldTail = consTail;
            }
            this.consTail = consTail;
        }
        
        void addField(final CtMember ctMember) {
            ctMember.next = this;
            this.fieldTail.next = ctMember;
            this.fieldTail = ctMember;
        }
        
        static int count(CtMember next, final CtMember ctMember) {
            while (next != ctMember) {
                int n = 0;
                ++n;
                next = next.next;
            }
            return 0;
        }
        
        void remove(final CtMember ctMember) {
            CtMember next = this;
            CtMember next2;
            while ((next2 = next.next) != this) {
                if (next2 == ctMember) {
                    next.next = next2.next;
                    if (next2 == this.methodTail) {
                        this.methodTail = next;
                    }
                    if (next2 == this.consTail) {
                        this.consTail = next;
                    }
                    if (next2 == this.fieldTail) {
                        this.fieldTail = next;
                        break;
                    }
                    break;
                }
                else {
                    next = next.next;
                }
            }
        }
    }
}
