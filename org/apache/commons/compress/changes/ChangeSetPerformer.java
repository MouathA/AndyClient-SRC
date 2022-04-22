package org.apache.commons.compress.changes;

import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.utils.*;
import java.io.*;
import java.util.*;
import org.apache.commons.compress.archivers.zip.*;

public class ChangeSetPerformer
{
    private final Set changes;
    
    public ChangeSetPerformer(final ChangeSet set) {
        this.changes = set.getChanges();
    }
    
    public ChangeSetResults perform(final ArchiveInputStream archiveInputStream, final ArchiveOutputStream archiveOutputStream) throws IOException {
        return this.perform(new ArchiveInputStreamIterator(archiveInputStream), archiveOutputStream);
    }
    
    public ChangeSetResults perform(final ZipFile zipFile, final ArchiveOutputStream archiveOutputStream) throws IOException {
        return this.perform(new ZipFileIterator(zipFile), archiveOutputStream);
    }
    
    private ChangeSetResults perform(final ArchiveEntryIterator archiveEntryIterator, final ArchiveOutputStream archiveOutputStream) throws IOException {
        final ChangeSetResults changeSetResults = new ChangeSetResults();
        final LinkedHashSet<Change> set = new LinkedHashSet<Change>(this.changes);
        final Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            final Change change = iterator.next();
            if (change.type() == 2 && change.isReplaceMode()) {
                this.copyStream(change.getInput(), archiveOutputStream, change.getEntry());
                iterator.remove();
                changeSetResults.addedFromChangeSet(change.getEntry().getName());
            }
        }
        while (archiveEntryIterator.hasNext()) {
            final ArchiveEntry next = archiveEntryIterator.next();
            final Iterator<Object> iterator2 = set.iterator();
            while (iterator2.hasNext()) {
                final Change change2 = iterator2.next();
                final int type = change2.type();
                final String name = next.getName();
                if (type == 1 && name != null) {
                    if (name.equals(change2.targetFile())) {
                        iterator2.remove();
                        changeSetResults.deleted(name);
                        break;
                    }
                    continue;
                }
                else {
                    if (type == 4 && name != null && name.startsWith(change2.targetFile() + "/")) {
                        changeSetResults.deleted(name);
                        break;
                    }
                    continue;
                }
            }
            if (false && !this.isDeletedLater(set, next) && !changeSetResults.hasBeenAdded(next.getName())) {
                this.copyStream(archiveEntryIterator.getInputStream(), archiveOutputStream, next);
                changeSetResults.addedFromStream(next.getName());
            }
        }
        final Iterator<Object> iterator3 = set.iterator();
        while (iterator3.hasNext()) {
            final Change change3 = iterator3.next();
            if (change3.type() == 2 && !change3.isReplaceMode() && !changeSetResults.hasBeenAdded(change3.getEntry().getName())) {
                this.copyStream(change3.getInput(), archiveOutputStream, change3.getEntry());
                iterator3.remove();
                changeSetResults.addedFromChangeSet(change3.getEntry().getName());
            }
        }
        archiveOutputStream.finish();
        return changeSetResults;
    }
    
    private boolean isDeletedLater(final Set set, final ArchiveEntry archiveEntry) {
        final String name = archiveEntry.getName();
        if (!set.isEmpty()) {
            for (final Change change : set) {
                final int type = change.type();
                final String targetFile = change.targetFile();
                if (type == 1 && name.equals(targetFile)) {
                    return true;
                }
                if (type == 4 && name.startsWith(targetFile + "/")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void copyStream(final InputStream inputStream, final ArchiveOutputStream archiveOutputStream, final ArchiveEntry archiveEntry) throws IOException {
        archiveOutputStream.putArchiveEntry(archiveEntry);
        IOUtils.copy(inputStream, archiveOutputStream);
        archiveOutputStream.closeArchiveEntry();
    }
    
    private static class ZipFileIterator implements ArchiveEntryIterator
    {
        private final ZipFile in;
        private final Enumeration nestedEnum;
        private ZipArchiveEntry current;
        
        ZipFileIterator(final ZipFile in) {
            this.in = in;
            this.nestedEnum = in.getEntriesInPhysicalOrder();
        }
        
        public boolean hasNext() {
            return this.nestedEnum.hasMoreElements();
        }
        
        public ArchiveEntry next() {
            return this.current = this.nestedEnum.nextElement();
        }
        
        public InputStream getInputStream() throws IOException {
            return this.in.getInputStream(this.current);
        }
    }
    
    interface ArchiveEntryIterator
    {
        boolean hasNext() throws IOException;
        
        ArchiveEntry next();
        
        InputStream getInputStream() throws IOException;
    }
    
    private static class ArchiveInputStreamIterator implements ArchiveEntryIterator
    {
        private final ArchiveInputStream in;
        private ArchiveEntry next;
        
        ArchiveInputStreamIterator(final ArchiveInputStream in) {
            this.in = in;
        }
        
        public boolean hasNext() throws IOException {
            final ArchiveEntry nextEntry = this.in.getNextEntry();
            this.next = nextEntry;
            return nextEntry != null;
        }
        
        public ArchiveEntry next() {
            return this.next;
        }
        
        public InputStream getInputStream() {
            return this.in;
        }
    }
}
