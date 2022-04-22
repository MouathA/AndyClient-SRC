package com.viaversion.viaversion.libs.javassist;

final class ClassPathList
{
    ClassPathList next;
    ClassPath path;
    
    ClassPathList(final ClassPath path, final ClassPathList next) {
        this.next = next;
        this.path = path;
    }
}
