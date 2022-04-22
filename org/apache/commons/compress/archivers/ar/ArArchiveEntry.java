package org.apache.commons.compress.archivers.ar;

import org.apache.commons.compress.archivers.*;
import java.io.*;
import java.util.*;

public class ArArchiveEntry implements ArchiveEntry
{
    public static final String HEADER = "!<arch>\n";
    public static final String TRAILER = "`\n";
    private final String name;
    private final int userId;
    private final int groupId;
    private final int mode;
    private static final int DEFAULT_MODE = 33188;
    private final long lastModified;
    private final long length;
    
    public ArArchiveEntry(final String s, final long n) {
        this(s, n, 0, 0, 33188, System.currentTimeMillis() / 1000L);
    }
    
    public ArArchiveEntry(final String name, final long length, final int userId, final int groupId, final int mode, final long lastModified) {
        this.name = name;
        this.length = length;
        this.userId = userId;
        this.groupId = groupId;
        this.mode = mode;
        this.lastModified = lastModified;
    }
    
    public ArArchiveEntry(final File file, final String s) {
        this(s, file.isFile() ? file.length() : 0L, 0, 0, 33188, file.lastModified() / 1000L);
    }
    
    public long getSize() {
        return this.getLength();
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getUserId() {
        return this.userId;
    }
    
    public int getGroupId() {
        return this.groupId;
    }
    
    public int getMode() {
        return this.mode;
    }
    
    public long getLastModified() {
        return this.lastModified;
    }
    
    public Date getLastModifiedDate() {
        return new Date(1000L * this.getLastModified());
    }
    
    public long getLength() {
        return this.length;
    }
    
    public boolean isDirectory() {
        return false;
    }
    
    @Override
    public int hashCode() {
        final int n = 31 + ((this.name == null) ? 0 : this.name.hashCode());
        return 1;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ArArchiveEntry arArchiveEntry = (ArArchiveEntry)o;
        if (this.name == null) {
            if (arArchiveEntry.name != null) {
                return false;
            }
        }
        else if (!this.name.equals(arArchiveEntry.name)) {
            return false;
        }
        return true;
    }
}
