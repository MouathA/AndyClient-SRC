package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.io.*;
import java.net.*;

final class ClassPoolTail
{
    protected ClassPathList pathList;
    
    public ClassPoolTail() {
        this.pathList = null;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[class path: ");
        for (ClassPathList list = this.pathList; list != null; list = list.next) {
            sb.append(list.path.toString());
            sb.append(File.pathSeparatorChar);
        }
        sb.append(']');
        return sb.toString();
    }
    
    public synchronized ClassPath insertClassPath(final ClassPath classPath) {
        this.pathList = new ClassPathList(classPath, this.pathList);
        return classPath;
    }
    
    public synchronized ClassPath appendClassPath(final ClassPath classPath) {
        final ClassPathList list = new ClassPathList(classPath, null);
        ClassPathList list2 = this.pathList;
        if (list2 == null) {
            this.pathList = list;
        }
        else {
            while (list2.next != null) {
                list2 = list2.next;
            }
            list2.next = list;
        }
        return classPath;
    }
    
    public synchronized void removeClassPath(final ClassPath classPath) {
        ClassPathList list = this.pathList;
        if (list != null) {
            if (list.path == classPath) {
                this.pathList = list.next;
            }
            else {
                while (list.next != null) {
                    if (list.next.path == classPath) {
                        list.next = list.next.next;
                    }
                    else {
                        list = list.next;
                    }
                }
            }
        }
    }
    
    public ClassPath appendSystemPath() {
        if (ClassFile.MAJOR_VERSION < 53) {
            return this.appendClassPath(new ClassClassPath());
        }
        return this.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
    }
    
    public ClassPath insertClassPath(final String s) throws NotFoundException {
        return this.insertClassPath(makePathObject(s));
    }
    
    public ClassPath appendClassPath(final String s) throws NotFoundException {
        return this.appendClassPath(makePathObject(s));
    }
    
    private static ClassPath makePathObject(final String s) throws NotFoundException {
        final String lowerCase = s.toLowerCase();
        if (lowerCase.endsWith(".jar") || lowerCase.endsWith(".zip")) {
            return new JarClassPath(s);
        }
        final int length = s.length();
        if (length > 2 && s.charAt(length - 1) == '*' && (s.charAt(length - 2) == '/' || s.charAt(length - 2) == File.separatorChar)) {
            return new JarDirClassPath(s.substring(0, length - 2));
        }
        return new DirClassPath(s);
    }
    
    void writeClassfile(final String s, final OutputStream outputStream) throws NotFoundException, IOException, CannotCompileException {
        final InputStream openClassfile = this.openClassfile(s);
        if (openClassfile == null) {
            throw new NotFoundException(s);
        }
        copyStream(openClassfile, outputStream);
        openClassfile.close();
    }
    
    InputStream openClassfile(final String s) throws NotFoundException {
        ClassPathList list = this.pathList;
        final Object o = null;
        while (list != null) {
            final InputStream openClassfile = list.path.openClassfile(s);
            if (openClassfile != null) {
                return openClassfile;
            }
            list = list.next;
        }
        if (o != null) {
            throw o;
        }
        return null;
    }
    
    public URL find(final String s) {
        for (ClassPathList list = this.pathList; list != null; list = list.next) {
            final URL find = list.path.find(s);
            if (find != null) {
                return find;
            }
        }
        return null;
    }
    
    public static byte[] readStream(final InputStream inputStream) throws IOException {
        final byte[][] array = new byte[8][];
        while (0 < 8) {
            array[0] = new byte[4096];
            do {
                inputStream.read(array[0], 0, 4096);
                if (0 >= 0) {
                    continue;
                }
                final byte[] array2 = new byte[0];
                while (0 < 0) {
                    System.arraycopy(array[0], 0, array2, 0, 4096);
                    int n = 0;
                    ++n;
                }
                System.arraycopy(array[0], 0, array2, 0, 0);
                return array2;
            } while (0 < 4096);
            int n2 = 0;
            ++n2;
        }
        throw new IOException("too much data");
    }
    
    public static void copyStream(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        byte[] array = null;
        while (0 < 64) {
            if (0 < 8) {
                array = new byte[4096];
            }
            do {
                inputStream.read(array, 0, 4096);
                if (0 >= 0) {
                    continue;
                }
                outputStream.write(array, 0, 0);
                return;
            } while (0 < 4096);
            outputStream.write(array);
            int n = 0;
            ++n;
        }
        throw new IOException("too much data");
    }
}
