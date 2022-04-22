package com.viaversion.viaversion.libs.javassist;

import java.io.*;
import java.net.*;

final class DirClassPath implements ClassPath
{
    String directory;
    
    DirClassPath(final String directory) {
        this.directory = directory;
    }
    
    @Override
    public InputStream openClassfile(final String s) {
        final char separatorChar = File.separatorChar;
        return new FileInputStream((this.directory + separatorChar + s.replace('.', separatorChar) + ".class").toString());
    }
    
    @Override
    public URL find(final String s) {
        final char separatorChar = File.separatorChar;
        final File file = new File(this.directory + separatorChar + s.replace('.', separatorChar) + ".class");
        if (file.exists()) {
            return file.getCanonicalFile().toURI().toURL();
        }
        return null;
    }
    
    @Override
    public String toString() {
        return this.directory;
    }
}
