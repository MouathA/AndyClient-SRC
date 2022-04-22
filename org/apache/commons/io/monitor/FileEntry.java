package org.apache.commons.io.monitor;

import java.io.*;

public class FileEntry implements Serializable
{
    static final FileEntry[] EMPTY_ENTRIES;
    private final FileEntry parent;
    private FileEntry[] children;
    private final File file;
    private String name;
    private boolean exists;
    private boolean directory;
    private long lastModified;
    private long length;
    
    public FileEntry(final File file) {
        this(null, file);
    }
    
    public FileEntry(final FileEntry parent, final File file) {
        if (file == null) {
            throw new IllegalArgumentException("File is missing");
        }
        this.file = file;
        this.parent = parent;
        this.name = file.getName();
    }
    
    public boolean refresh(final File file) {
        final boolean exists = this.exists;
        final long lastModified = this.lastModified;
        final boolean directory = this.directory;
        final long length = this.length;
        this.name = file.getName();
        this.exists = file.exists();
        this.directory = (this.exists && file.isDirectory());
        this.lastModified = (this.exists ? file.lastModified() : 0L);
        this.length = ((this.exists && !this.directory) ? file.length() : 0L);
        return this.exists != exists || this.lastModified != lastModified || this.directory != directory || this.length != length;
    }
    
    public FileEntry newChildInstance(final File file) {
        return new FileEntry(this, file);
    }
    
    public FileEntry getParent() {
        return this.parent;
    }
    
    public int getLevel() {
        return (this.parent == null) ? 0 : (this.parent.getLevel() + 1);
    }
    
    public FileEntry[] getChildren() {
        return (this.children != null) ? this.children : FileEntry.EMPTY_ENTRIES;
    }
    
    public void setChildren(final FileEntry[] children) {
        this.children = children;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public long getLastModified() {
        return this.lastModified;
    }
    
    public void setLastModified(final long lastModified) {
        this.lastModified = lastModified;
    }
    
    public long getLength() {
        return this.length;
    }
    
    public void setLength(final long length) {
        this.length = length;
    }
    
    public boolean isExists() {
        return this.exists;
    }
    
    public void setExists(final boolean exists) {
        this.exists = exists;
    }
    
    public boolean isDirectory() {
        return this.directory;
    }
    
    public void setDirectory(final boolean directory) {
        this.directory = directory;
    }
    
    static {
        EMPTY_ENTRIES = new FileEntry[0];
    }
}
