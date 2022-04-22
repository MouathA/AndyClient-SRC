package org.apache.commons.compress.changes;

import java.util.*;

public class ChangeSetResults
{
    private final List addedFromChangeSet;
    private final List addedFromStream;
    private final List deleted;
    
    public ChangeSetResults() {
        this.addedFromChangeSet = new ArrayList();
        this.addedFromStream = new ArrayList();
        this.deleted = new ArrayList();
    }
    
    void deleted(final String s) {
        this.deleted.add(s);
    }
    
    void addedFromStream(final String s) {
        this.addedFromStream.add(s);
    }
    
    void addedFromChangeSet(final String s) {
        this.addedFromChangeSet.add(s);
    }
    
    public List getAddedFromChangeSet() {
        return this.addedFromChangeSet;
    }
    
    public List getAddedFromStream() {
        return this.addedFromStream;
    }
    
    public List getDeleted() {
        return this.deleted;
    }
    
    boolean hasBeenAdded(final String s) {
        return this.addedFromChangeSet.contains(s) || this.addedFromStream.contains(s);
    }
}
