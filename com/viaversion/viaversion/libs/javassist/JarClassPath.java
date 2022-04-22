package com.viaversion.viaversion.libs.javassist;

import java.util.jar.*;
import java.util.*;
import java.io.*;
import java.net.*;

final class JarClassPath implements ClassPath
{
    Set jarfileEntries;
    String jarfileURL;
    
    JarClassPath(final String s) throws NotFoundException {
        final JarFile jarFile = new JarFile(s);
        this.jarfileEntries = new HashSet();
        for (final JarEntry jarEntry : Collections.list(jarFile.entries())) {
            if (jarEntry.getName().endsWith(".class")) {
                this.jarfileEntries.add(jarEntry.getName());
            }
        }
        this.jarfileURL = new File(s).getCanonicalFile().toURI().toURL().toString();
        if (null != jarFile) {
            jarFile.close();
        }
    }
    
    @Override
    public InputStream openClassfile(final String s) throws NotFoundException {
        final URL find = this.find(s);
        if (null == find) {
            return null;
        }
        if (ClassPool.cacheOpenedJarFile) {
            return find.openConnection().getInputStream();
        }
        final URLConnection openConnection = find.openConnection();
        openConnection.setUseCaches(false);
        return openConnection.getInputStream();
    }
    
    @Override
    public URL find(final String s) {
        final String string = s.replace('.', '/') + ".class";
        if (this.jarfileEntries.contains(string)) {
            return new URL(String.format("jar:%s!/%s", this.jarfileURL, string));
        }
        return null;
    }
    
    @Override
    public String toString() {
        return (this.jarfileURL == null) ? "<null>" : this.jarfileURL.toString();
    }
}
