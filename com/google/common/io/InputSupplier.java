package com.google.common.io;

import java.io.*;

@Deprecated
public interface InputSupplier
{
    Object getInput() throws IOException;
}
