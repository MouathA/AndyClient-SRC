package com.viaversion.viaversion.libs.javassist;

import java.io.*;
import java.net.*;

final class JarDirClassPath implements ClassPath
{
    JarClassPath[] jars;
    
    JarDirClassPath(final String s) throws NotFoundException {
        final File[] listFiles = new File(s).listFiles(new FilenameFilter() {
            final JarDirClassPath this$0;
            
            @Override
            public boolean accept(final File file, String lowerCase) {
                lowerCase = lowerCase.toLowerCase();
                return lowerCase.endsWith(".jar") || lowerCase.endsWith(".zip");
            }
        });
        if (listFiles != null) {
            this.jars = new JarClassPath[listFiles.length];
            while (0 < listFiles.length) {
                this.jars[0] = new JarClassPath(listFiles[0].getPath());
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public InputStream openClassfile(final String s) throws NotFoundException {
        if (this.jars != null) {
            while (0 < this.jars.length) {
                final InputStream openClassfile = this.jars[0].openClassfile(s);
                if (openClassfile != null) {
                    return openClassfile;
                }
                int n = 0;
                ++n;
            }
        }
        return null;
    }
    
    @Override
    public URL find(final String s) {
        if (this.jars != null) {
            while (0 < this.jars.length) {
                final URL find = this.jars[0].find(s);
                if (find != null) {
                    return find;
                }
                int n = 0;
                ++n;
            }
        }
        return null;
    }
}
