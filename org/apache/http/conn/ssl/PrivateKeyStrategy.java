package org.apache.http.conn.ssl;

import java.util.*;
import java.net.*;

public interface PrivateKeyStrategy
{
    String chooseAlias(final Map p0, final Socket p1);
}
