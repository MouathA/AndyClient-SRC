package org.apache.logging.log4j.core;

import java.io.*;
import java.util.*;

public interface Layout
{
    byte[] getFooter();
    
    byte[] getHeader();
    
    byte[] toByteArray(final LogEvent p0);
    
    Serializable toSerializable(final LogEvent p0);
    
    String getContentType();
    
    Map getContentFormat();
}
