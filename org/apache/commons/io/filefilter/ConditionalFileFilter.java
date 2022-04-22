package org.apache.commons.io.filefilter;

import java.util.*;

public interface ConditionalFileFilter
{
    void addFileFilter(final IOFileFilter p0);
    
    List getFileFilters();
    
    boolean removeFileFilter(final IOFileFilter p0);
    
    void setFileFilters(final List p0);
}
