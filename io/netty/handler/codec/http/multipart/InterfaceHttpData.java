package io.netty.handler.codec.http.multipart;

import io.netty.util.*;

public interface InterfaceHttpData extends Comparable, ReferenceCounted
{
    String getName();
    
    HttpDataType getHttpDataType();
    
    public enum HttpDataType
    {
        Attribute("Attribute", 0), 
        FileUpload("FileUpload", 1), 
        InternalAttribute("InternalAttribute", 2);
        
        private static final HttpDataType[] $VALUES;
        
        private HttpDataType(final String s, final int n) {
        }
        
        static {
            $VALUES = new HttpDataType[] { HttpDataType.Attribute, HttpDataType.FileUpload, HttpDataType.InternalAttribute };
        }
    }
}
