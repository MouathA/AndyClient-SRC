package org.apache.logging.log4j.message;

import java.util.*;

public class LocalizedMessageFactory extends AbstractMessageFactory
{
    private final ResourceBundle bundle;
    private final String bundleId;
    
    public LocalizedMessageFactory(final ResourceBundle bundle) {
        this.bundle = bundle;
        this.bundleId = null;
    }
    
    public LocalizedMessageFactory(final String bundleId) {
        this.bundle = null;
        this.bundleId = bundleId;
    }
    
    @Override
    public Message newMessage(final String s, final Object... array) {
        if (this.bundle == null) {
            return new LocalizedMessage(this.bundleId, s, array);
        }
        return new LocalizedMessage(this.bundle, s, array);
    }
}
