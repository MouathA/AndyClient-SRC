package org.apache.commons.io.input;

import java.util.*;
import org.apache.commons.io.*;
import java.io.*;

public class TaggedInputStream extends ProxyInputStream
{
    private final Serializable tag;
    
    public TaggedInputStream(final InputStream inputStream) {
        super(inputStream);
        this.tag = UUID.randomUUID();
    }
    
    public boolean isCauseOf(final Throwable t) {
        return TaggedIOException.isTaggedWith(t, this.tag);
    }
    
    public void throwIfCauseOf(final Throwable t) throws IOException {
        TaggedIOException.throwCauseIfTaggedWith(t, this.tag);
    }
    
    @Override
    protected void handleIOException(final IOException ex) throws IOException {
        throw new TaggedIOException(ex, this.tag);
    }
}
