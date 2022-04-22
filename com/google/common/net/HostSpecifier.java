package com.google.common.net;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.net.*;
import java.text.*;
import javax.annotation.*;

@Beta
public final class HostSpecifier
{
    private final String canonicalForm;
    
    private HostSpecifier(final String canonicalForm) {
        this.canonicalForm = canonicalForm;
    }
    
    public static HostSpecifier fromValid(final String s) {
        final HostAndPort fromString = HostAndPort.fromString(s);
        Preconditions.checkArgument(!fromString.hasPort());
        final String hostText = fromString.getHostText();
        final InetAddress forString = InetAddresses.forString(hostText);
        if (forString != null) {
            return new HostSpecifier(InetAddresses.toUriString(forString));
        }
        final InternetDomainName from = InternetDomainName.from(hostText);
        if (from.hasPublicSuffix()) {
            return new HostSpecifier(from.toString());
        }
        throw new IllegalArgumentException("Domain name does not have a recognized public suffix: " + hostText);
    }
    
    public static HostSpecifier from(final String s) throws ParseException {
        return fromValid(s);
    }
    
    public static boolean isValid(final String s) {
        fromValid(s);
        return true;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return this == o || (o instanceof HostSpecifier && this.canonicalForm.equals(((HostSpecifier)o).canonicalForm));
    }
    
    @Override
    public int hashCode() {
        return this.canonicalForm.hashCode();
    }
    
    @Override
    public String toString() {
        return this.canonicalForm;
    }
}
