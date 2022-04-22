package org.apache.commons.compress.archivers.jar;

import org.apache.commons.compress.archivers.zip.*;
import java.security.cert.*;
import java.util.zip.*;
import java.util.jar.*;

public class JarArchiveEntry extends ZipArchiveEntry
{
    private final Attributes manifestAttributes;
    private final Certificate[] certificates;
    
    public JarArchiveEntry(final ZipEntry zipEntry) throws ZipException {
        super(zipEntry);
        this.manifestAttributes = null;
        this.certificates = null;
    }
    
    public JarArchiveEntry(final String s) {
        super(s);
        this.manifestAttributes = null;
        this.certificates = null;
    }
    
    public JarArchiveEntry(final ZipArchiveEntry zipArchiveEntry) throws ZipException {
        super(zipArchiveEntry);
        this.manifestAttributes = null;
        this.certificates = null;
    }
    
    public JarArchiveEntry(final JarEntry jarEntry) throws ZipException {
        super(jarEntry);
        this.manifestAttributes = null;
        this.certificates = null;
    }
    
    @Deprecated
    public Attributes getManifestAttributes() {
        return this.manifestAttributes;
    }
    
    @Deprecated
    public Certificate[] getCertificates() {
        if (this.certificates != null) {
            final Certificate[] array = new Certificate[this.certificates.length];
            System.arraycopy(this.certificates, 0, array, 0, array.length);
            return array;
        }
        return null;
    }
}
