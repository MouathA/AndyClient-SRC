package org.apache.commons.compress.changes;

import org.apache.commons.compress.archivers.*;
import java.io.*;
import java.util.*;

public final class ChangeSet
{
    private final Set changes;
    
    public ChangeSet() {
        this.changes = new LinkedHashSet();
    }
    
    public void delete(final String s) {
        this.addDeletion(new Change(s, 1));
    }
    
    public void deleteDir(final String s) {
        this.addDeletion(new Change(s, 4));
    }
    
    public void add(final ArchiveEntry archiveEntry, final InputStream inputStream) {
        this.add(archiveEntry, inputStream, true);
    }
    
    public void add(final ArchiveEntry archiveEntry, final InputStream inputStream, final boolean b) {
        this.addAddition(new Change(archiveEntry, inputStream, b));
    }
    
    private void addAddition(final Change change) {
        if (2 != change.type() || change.getInput() == null) {
            return;
        }
        if (!this.changes.isEmpty()) {
            final Iterator<Change> iterator = (Iterator<Change>)this.changes.iterator();
            while (iterator.hasNext()) {
                final Change change2 = iterator.next();
                if (change2.type() == 2 && change2.getEntry() != null && change2.getEntry().equals(change.getEntry())) {
                    if (change.isReplaceMode()) {
                        iterator.remove();
                        this.changes.add(change);
                    }
                    return;
                }
            }
        }
        this.changes.add(change);
    }
    
    private void addDeletion(final Change change) {
        if ((1 != change.type() && 4 != change.type()) || change.targetFile() == null) {
            return;
        }
        final String targetFile = change.targetFile();
        if (targetFile != null && !this.changes.isEmpty()) {
            final Iterator<Change> iterator = (Iterator<Change>)this.changes.iterator();
            while (iterator.hasNext()) {
                final Change change2 = iterator.next();
                if (change2.type() == 2 && change2.getEntry() != null) {
                    final String name = change2.getEntry().getName();
                    if (name == null) {
                        continue;
                    }
                    if (1 == change.type() && targetFile.equals(name)) {
                        iterator.remove();
                    }
                    else {
                        if (4 != change.type() || !name.matches(targetFile + "/.*")) {
                            continue;
                        }
                        iterator.remove();
                    }
                }
            }
        }
        this.changes.add(change);
    }
    
    Set getChanges() {
        return new LinkedHashSet(this.changes);
    }
}
