package com.viaversion.viaversion.libs.javassist.tools.rmi;

public class RemoteException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public RemoteException(final String s) {
        super(s);
    }
    
    public RemoteException(final Exception ex) {
        super("by " + ex.toString());
    }
}
