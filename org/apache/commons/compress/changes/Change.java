package org.apache.commons.compress.changes;

import org.apache.commons.compress.archivers.*;
import java.io.*;

class Change
{
    private final String targetFile;
    private final ArchiveEntry entry;
    private final InputStream input;
    private final boolean replaceMode;
    private final int type;
    static final int TYPE_DELETE = 1;
    static final int TYPE_ADD = 2;
    static final int TYPE_MOVE = 3;
    static final int TYPE_DELETE_DIR = 4;
    
    Change(final String targetFile, final int type) {
        if (targetFile == null) {
            throw new NullPointerException();
        }
        this.targetFile = targetFile;
        this.type = type;
        this.input = null;
        this.entry = null;
        this.replaceMode = true;
    }
    
    Change(final ArchiveEntry entry, final InputStream input, final boolean replaceMode) {
        if (entry == null || input == null) {
            throw new NullPointerException();
        }
        this.entry = entry;
        this.input = input;
        this.type = 2;
        this.targetFile = null;
        this.replaceMode = replaceMode;
    }
    
    ArchiveEntry getEntry() {
        return this.entry;
    }
    
    InputStream getInput() {
        return this.input;
    }
    
    String targetFile() {
        return this.targetFile;
    }
    
    int type() {
        return this.type;
    }
    
    boolean isReplaceMode() {
        return this.replaceMode;
    }
}
