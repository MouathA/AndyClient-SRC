package com.viaversion.viaversion.libs.javassist.tools.rmi;

import java.io.*;

public class RemoteRef implements Serializable
{
    private static final long serialVersionUID = 1L;
    public int oid;
    public String classname;
    
    public RemoteRef(final int oid) {
        this.oid = oid;
        this.classname = null;
    }
    
    public RemoteRef(final int oid, final String classname) {
        this.oid = oid;
        this.classname = classname;
    }
}
