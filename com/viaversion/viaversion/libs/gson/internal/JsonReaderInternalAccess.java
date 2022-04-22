package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.stream.*;
import java.io.*;

public abstract class JsonReaderInternalAccess
{
    public static JsonReaderInternalAccess INSTANCE;
    
    public abstract void promoteNameToValue(final JsonReader p0) throws IOException;
}
