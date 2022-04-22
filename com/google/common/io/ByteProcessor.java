package com.google.common.io;

import com.google.common.annotations.*;
import java.io.*;

@Beta
public interface ByteProcessor
{
    boolean processBytes(final byte[] p0, final int p1, final int p2) throws IOException;
    
    Object getResult();
}
