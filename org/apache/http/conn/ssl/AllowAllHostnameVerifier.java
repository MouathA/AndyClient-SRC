package org.apache.http.conn.ssl;

import org.apache.http.annotation.*;

@Immutable
public class AllowAllHostnameVerifier extends AbstractVerifier
{
    public final void verify(final String s, final String[] array, final String[] array2) {
    }
    
    @Override
    public final String toString() {
        return "ALLOW_ALL";
    }
}
