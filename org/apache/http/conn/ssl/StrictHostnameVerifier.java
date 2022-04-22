package org.apache.http.conn.ssl;

import org.apache.http.annotation.*;
import javax.net.ssl.*;

@Immutable
public class StrictHostnameVerifier extends AbstractVerifier
{
    public final void verify(final String s, final String[] array, final String[] array2) throws SSLException {
        this.verify(s, array, array2, true);
    }
    
    @Override
    public final String toString() {
        return "STRICT";
    }
}
