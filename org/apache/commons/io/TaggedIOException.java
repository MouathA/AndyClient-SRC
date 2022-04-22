package org.apache.commons.io;

import java.io.*;

public class TaggedIOException extends IOExceptionWithCause
{
    private static final long serialVersionUID = -6994123481142850163L;
    private final Serializable tag;
    
    public static boolean isTaggedWith(final Throwable t, final Object o) {
        return o != null && t instanceof TaggedIOException && o.equals(((TaggedIOException)t).tag);
    }
    
    public static void throwCauseIfTaggedWith(final Throwable t, final Object o) throws IOException {
        if (isTaggedWith(t, o)) {
            throw ((TaggedIOException)t).getCause();
        }
    }
    
    public TaggedIOException(final IOException ex, final Serializable tag) {
        super(ex.getMessage(), ex);
        this.tag = tag;
    }
    
    public Serializable getTag() {
        return this.tag;
    }
    
    @Override
    public IOException getCause() {
        return (IOException)super.getCause();
    }
    
    @Override
    public Throwable getCause() {
        return this.getCause();
    }
}
