package com.google.common.reflect;

import com.google.common.base.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import javax.annotation.*;

class Element extends AccessibleObject implements Member
{
    private final AccessibleObject accessibleObject;
    private final Member member;
    
    Element(final AccessibleObject accessibleObject) {
        Preconditions.checkNotNull(accessibleObject);
        this.accessibleObject = accessibleObject;
        this.member = (Member)accessibleObject;
    }
    
    public TypeToken getOwnerType() {
        return TypeToken.of(this.getDeclaringClass());
    }
    
    @Override
    public final boolean isAnnotationPresent(final Class clazz) {
        return this.accessibleObject.isAnnotationPresent(clazz);
    }
    
    @Override
    public final Annotation getAnnotation(final Class clazz) {
        return this.accessibleObject.getAnnotation((Class<Annotation>)clazz);
    }
    
    @Override
    public final Annotation[] getAnnotations() {
        return this.accessibleObject.getAnnotations();
    }
    
    @Override
    public final Annotation[] getDeclaredAnnotations() {
        return this.accessibleObject.getDeclaredAnnotations();
    }
    
    @Override
    public final void setAccessible(final boolean accessible) throws SecurityException {
        this.accessibleObject.setAccessible(accessible);
    }
    
    @Override
    public final boolean isAccessible() {
        return this.accessibleObject.isAccessible();
    }
    
    @Override
    public Class getDeclaringClass() {
        return this.member.getDeclaringClass();
    }
    
    @Override
    public final String getName() {
        return this.member.getName();
    }
    
    @Override
    public final int getModifiers() {
        return this.member.getModifiers();
    }
    
    @Override
    public final boolean isSynthetic() {
        return this.member.isSynthetic();
    }
    
    public final boolean isPublic() {
        return Modifier.isPublic(this.getModifiers());
    }
    
    public final boolean isProtected() {
        return Modifier.isProtected(this.getModifiers());
    }
    
    public final boolean isPackagePrivate() {
        return !this.isPrivate() && !this.isPublic() && !this.isProtected();
    }
    
    public final boolean isPrivate() {
        return Modifier.isPrivate(this.getModifiers());
    }
    
    public final boolean isStatic() {
        return Modifier.isStatic(this.getModifiers());
    }
    
    public final boolean isFinal() {
        return Modifier.isFinal(this.getModifiers());
    }
    
    public final boolean isAbstract() {
        return Modifier.isAbstract(this.getModifiers());
    }
    
    public final boolean isNative() {
        return Modifier.isNative(this.getModifiers());
    }
    
    public final boolean isSynchronized() {
        return Modifier.isSynchronized(this.getModifiers());
    }
    
    final boolean isVolatile() {
        return Modifier.isVolatile(this.getModifiers());
    }
    
    final boolean isTransient() {
        return Modifier.isTransient(this.getModifiers());
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (o instanceof Element) {
            final Element element = (Element)o;
            return this.getOwnerType().equals(element.getOwnerType()) && this.member.equals(element.member);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.member.hashCode();
    }
    
    @Override
    public String toString() {
        return this.member.toString();
    }
}
