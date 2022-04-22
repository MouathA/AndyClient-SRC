package com.google.common.io;

import com.google.common.annotations.*;
import java.io.*;

@Beta
public interface LineProcessor
{
    boolean processLine(final String p0) throws IOException;
    
    Object getResult();
}
