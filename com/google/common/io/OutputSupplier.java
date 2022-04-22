package com.google.common.io;

import java.io.*;

@Deprecated
public interface OutputSupplier
{
    Object getOutput() throws IOException;
}
