package org.apache.commons.io.output;

import java.util.*;
import org.apache.commons.io.*;
import java.io.*;

public class TaggedOutputStream extends ProxyOutputStream
{
    private final Serializable tag;
    
    public TaggedOutputStream(final OutputStream outputStream) {
        super(outputStream);
        this.tag = UUID.randomUUID();
    }
    
    public boolean isCauseOf(final Exception ex) {
        return TaggedIOException.isTaggedWith(ex, this.tag);
    }
    
    public void throwIfCauseOf(final Exception ex) throws IOException {
        TaggedIOException.throwCauseIfTaggedWith(ex, this.tag);
    }
    
    @Override
    protected void handleIOException(final IOException ex) throws IOException {
        throw new TaggedIOException(ex, this.tag);
    }
}
