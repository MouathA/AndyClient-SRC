package org.apache.http.cookie;

import java.util.*;

public interface Cookie
{
    String getName();
    
    String getValue();
    
    String getComment();
    
    String getCommentURL();
    
    Date getExpiryDate();
    
    boolean isPersistent();
    
    String getDomain();
    
    String getPath();
    
    int[] getPorts();
    
    boolean isSecure();
    
    int getVersion();
    
    boolean isExpired(final Date p0);
}
