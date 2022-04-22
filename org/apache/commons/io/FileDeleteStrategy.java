package org.apache.commons.io;

import java.io.*;

public class FileDeleteStrategy
{
    public static final FileDeleteStrategy NORMAL;
    public static final FileDeleteStrategy FORCE;
    private final String name;
    
    protected FileDeleteStrategy(final String name) {
        this.name = name;
    }
    
    public boolean deleteQuietly(final File file) {
        return file == null || !file.exists() || this.doDelete(file);
    }
    
    public void delete(final File file) throws IOException {
        if (file.exists() && !this.doDelete(file)) {
            throw new IOException("Deletion failed: " + file);
        }
    }
    
    protected boolean doDelete(final File file) throws IOException {
        return file.delete();
    }
    
    @Override
    public String toString() {
        return "FileDeleteStrategy[" + this.name + "]";
    }
    
    static {
        NORMAL = new FileDeleteStrategy("Normal");
        FORCE = new ForceFileDeleteStrategy();
    }
    
    static class ForceFileDeleteStrategy extends FileDeleteStrategy
    {
        ForceFileDeleteStrategy() {
            super("Force");
        }
        
        @Override
        protected boolean doDelete(final File file) throws IOException {
            FileUtils.forceDelete(file);
            return true;
        }
    }
}
