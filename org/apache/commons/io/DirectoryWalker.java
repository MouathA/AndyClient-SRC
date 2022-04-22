package org.apache.commons.io;

import org.apache.commons.io.filefilter.*;
import java.util.*;
import java.io.*;

public abstract class DirectoryWalker
{
    private final FileFilter filter;
    private final int depthLimit;
    
    protected DirectoryWalker() {
        this(null, -1);
    }
    
    protected DirectoryWalker(final FileFilter filter, final int depthLimit) {
        this.filter = filter;
        this.depthLimit = depthLimit;
    }
    
    protected DirectoryWalker(IOFileFilter directoryOnly, IOFileFilter fileOnly, final int depthLimit) {
        if (directoryOnly == null && fileOnly == null) {
            this.filter = null;
        }
        else {
            directoryOnly = ((directoryOnly != null) ? directoryOnly : TrueFileFilter.TRUE);
            fileOnly = ((fileOnly != null) ? fileOnly : TrueFileFilter.TRUE);
            directoryOnly = FileFilterUtils.makeDirectoryOnly(directoryOnly);
            fileOnly = FileFilterUtils.makeFileOnly(fileOnly);
            this.filter = FileFilterUtils.or(directoryOnly, fileOnly);
        }
        this.depthLimit = depthLimit;
    }
    
    protected final void walk(final File file, final Collection collection) throws IOException {
        if (file == null) {
            throw new NullPointerException("Start Directory is null");
        }
        this.handleStart(file, collection);
        this.walk(file, 0, collection);
        this.handleEnd(collection);
    }
    
    private void walk(final File file, final int n, final Collection collection) throws IOException {
        this.checkIfCancelled(file, n, collection);
        if (this.handleDirectory(file, n, collection)) {
            this.handleDirectoryStart(file, n, collection);
            final int n2 = n + 1;
            if (this.depthLimit < 0 || n2 <= this.depthLimit) {
                this.checkIfCancelled(file, n, collection);
                final File[] filterDirectoryContents = this.filterDirectoryContents(file, n, (this.filter == null) ? file.listFiles() : file.listFiles(this.filter));
                if (filterDirectoryContents == null) {
                    this.handleRestricted(file, n2, collection);
                }
                else {
                    final File[] array = filterDirectoryContents;
                    while (0 < array.length) {
                        final File file2 = array[0];
                        if (file2.isDirectory()) {
                            this.walk(file2, n2, collection);
                        }
                        else {
                            this.checkIfCancelled(file2, n2, collection);
                            this.handleFile(file2, n2, collection);
                            this.checkIfCancelled(file2, n2, collection);
                        }
                        int n3 = 0;
                        ++n3;
                    }
                }
            }
            this.handleDirectoryEnd(file, n, collection);
        }
        this.checkIfCancelled(file, n, collection);
    }
    
    protected final void checkIfCancelled(final File file, final int n, final Collection collection) throws IOException {
        if (this.handleIsCancelled(file, n, collection)) {
            throw new CancelException(file, n);
        }
    }
    
    protected boolean handleIsCancelled(final File file, final int n, final Collection collection) throws IOException {
        return false;
    }
    
    protected void handleCancelled(final File file, final Collection collection, final CancelException ex) throws IOException {
        throw ex;
    }
    
    protected void handleStart(final File file, final Collection collection) throws IOException {
    }
    
    protected boolean handleDirectory(final File file, final int n, final Collection collection) throws IOException {
        return true;
    }
    
    protected void handleDirectoryStart(final File file, final int n, final Collection collection) throws IOException {
    }
    
    protected File[] filterDirectoryContents(final File file, final int n, final File[] array) throws IOException {
        return array;
    }
    
    protected void handleFile(final File file, final int n, final Collection collection) throws IOException {
    }
    
    protected void handleRestricted(final File file, final int n, final Collection collection) throws IOException {
    }
    
    protected void handleDirectoryEnd(final File file, final int n, final Collection collection) throws IOException {
    }
    
    protected void handleEnd(final Collection collection) throws IOException {
    }
    
    public static class CancelException extends IOException
    {
        private static final long serialVersionUID = 1347339620135041008L;
        private final File file;
        private final int depth;
        
        public CancelException(final File file, final int n) {
            this("Operation Cancelled", file, n);
        }
        
        public CancelException(final String s, final File file, final int depth) {
            super(s);
            this.file = file;
            this.depth = depth;
        }
        
        public File getFile() {
            return this.file;
        }
        
        public int getDepth() {
            return this.depth;
        }
    }
}
