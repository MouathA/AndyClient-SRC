package org.apache.commons.compress.archivers;

import org.apache.commons.compress.archivers.arj.*;
import org.apache.commons.compress.archivers.dump.*;
import org.apache.commons.compress.archivers.ar.*;
import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.archivers.tar.*;
import org.apache.commons.compress.archivers.jar.*;
import org.apache.commons.compress.archivers.cpio.*;
import org.apache.commons.compress.utils.*;
import org.apache.commons.compress.archivers.sevenz.*;
import java.io.*;

public class ArchiveStreamFactory
{
    public static final String AR = "ar";
    public static final String ARJ = "arj";
    public static final String CPIO = "cpio";
    public static final String DUMP = "dump";
    public static final String JAR = "jar";
    public static final String TAR = "tar";
    public static final String ZIP = "zip";
    public static final String SEVEN_Z = "7z";
    private String entryEncoding;
    
    public ArchiveStreamFactory() {
        this.entryEncoding = null;
    }
    
    public String getEntryEncoding() {
        return this.entryEncoding;
    }
    
    public void setEntryEncoding(final String entryEncoding) {
        this.entryEncoding = entryEncoding;
    }
    
    public ArchiveInputStream createArchiveInputStream(final String s, final InputStream inputStream) throws ArchiveException {
        if (s == null) {
            throw new IllegalArgumentException("Archivername must not be null.");
        }
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream must not be null.");
        }
        if ("ar".equalsIgnoreCase(s)) {
            return new ArArchiveInputStream(inputStream);
        }
        if ("arj".equalsIgnoreCase(s)) {
            if (this.entryEncoding != null) {
                return new ArjArchiveInputStream(inputStream, this.entryEncoding);
            }
            return new ArjArchiveInputStream(inputStream);
        }
        else if ("zip".equalsIgnoreCase(s)) {
            if (this.entryEncoding != null) {
                return new ZipArchiveInputStream(inputStream, this.entryEncoding);
            }
            return new ZipArchiveInputStream(inputStream);
        }
        else if ("tar".equalsIgnoreCase(s)) {
            if (this.entryEncoding != null) {
                return new TarArchiveInputStream(inputStream, this.entryEncoding);
            }
            return new TarArchiveInputStream(inputStream);
        }
        else {
            if ("jar".equalsIgnoreCase(s)) {
                return new JarArchiveInputStream(inputStream);
            }
            if ("cpio".equalsIgnoreCase(s)) {
                if (this.entryEncoding != null) {
                    return new CpioArchiveInputStream(inputStream, this.entryEncoding);
                }
                return new CpioArchiveInputStream(inputStream);
            }
            else if ("dump".equalsIgnoreCase(s)) {
                if (this.entryEncoding != null) {
                    return new DumpArchiveInputStream(inputStream, this.entryEncoding);
                }
                return new DumpArchiveInputStream(inputStream);
            }
            else {
                if ("7z".equalsIgnoreCase(s)) {
                    throw new StreamingNotSupportedException("7z");
                }
                throw new ArchiveException("Archiver: " + s + " not found.");
            }
        }
    }
    
    public ArchiveOutputStream createArchiveOutputStream(final String s, final OutputStream outputStream) throws ArchiveException {
        if (s == null) {
            throw new IllegalArgumentException("Archivername must not be null.");
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("OutputStream must not be null.");
        }
        if ("ar".equalsIgnoreCase(s)) {
            return new ArArchiveOutputStream(outputStream);
        }
        if ("zip".equalsIgnoreCase(s)) {
            final ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(outputStream);
            if (this.entryEncoding != null) {
                zipArchiveOutputStream.setEncoding(this.entryEncoding);
            }
            return zipArchiveOutputStream;
        }
        if ("tar".equalsIgnoreCase(s)) {
            if (this.entryEncoding != null) {
                return new TarArchiveOutputStream(outputStream, this.entryEncoding);
            }
            return new TarArchiveOutputStream(outputStream);
        }
        else {
            if ("jar".equalsIgnoreCase(s)) {
                return new JarArchiveOutputStream(outputStream);
            }
            if ("cpio".equalsIgnoreCase(s)) {
                if (this.entryEncoding != null) {
                    return new CpioArchiveOutputStream(outputStream, this.entryEncoding);
                }
                return new CpioArchiveOutputStream(outputStream);
            }
            else {
                if ("7z".equalsIgnoreCase(s)) {
                    throw new StreamingNotSupportedException("7z");
                }
                throw new ArchiveException("Archiver: " + s + " not found.");
            }
        }
    }
    
    public ArchiveInputStream createArchiveInputStream(final InputStream inputStream) throws ArchiveException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Stream must not be null.");
        }
        if (!inputStream.markSupported()) {
            throw new IllegalArgumentException("Mark is not supported.");
        }
        final byte[] array = new byte[12];
        inputStream.mark(array.length);
        final int fully = IOUtils.readFully(inputStream, array);
        inputStream.reset();
        if (ZipArchiveInputStream.matches(array, fully)) {
            if (this.entryEncoding != null) {
                return new ZipArchiveInputStream(inputStream, this.entryEncoding);
            }
            return new ZipArchiveInputStream(inputStream);
        }
        else {
            if (JarArchiveInputStream.matches(array, fully)) {
                return new JarArchiveInputStream(inputStream);
            }
            if (ArArchiveInputStream.matches(array, fully)) {
                return new ArArchiveInputStream(inputStream);
            }
            if (CpioArchiveInputStream.matches(array, fully)) {
                return new CpioArchiveInputStream(inputStream);
            }
            if (ArjArchiveInputStream.matches(array, fully)) {
                return new ArjArchiveInputStream(inputStream);
            }
            if (SevenZFile.matches(array, fully)) {
                throw new StreamingNotSupportedException("7z");
            }
            final byte[] array2 = new byte[32];
            inputStream.mark(array2.length);
            final int fully2 = IOUtils.readFully(inputStream, array2);
            inputStream.reset();
            if (DumpArchiveInputStream.matches(array2, fully2)) {
                return new DumpArchiveInputStream(inputStream);
            }
            final byte[] array3 = new byte[512];
            inputStream.mark(array3.length);
            final int fully3 = IOUtils.readFully(inputStream, array3);
            inputStream.reset();
            if (!TarArchiveInputStream.matches(array3, fully3)) {
                if (fully3 >= 512) {
                    final TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(new ByteArrayInputStream(array3));
                    if (tarArchiveInputStream.getNextTarEntry().isCheckSumOK()) {
                        final TarArchiveInputStream tarArchiveInputStream2 = new TarArchiveInputStream(inputStream);
                        IOUtils.closeQuietly(tarArchiveInputStream);
                        return tarArchiveInputStream2;
                    }
                    IOUtils.closeQuietly(tarArchiveInputStream);
                }
                throw new ArchiveException("No Archiver found for the stream signature");
            }
            if (this.entryEncoding != null) {
                return new TarArchiveInputStream(inputStream, this.entryEncoding);
            }
            return new TarArchiveInputStream(inputStream);
        }
    }
}
