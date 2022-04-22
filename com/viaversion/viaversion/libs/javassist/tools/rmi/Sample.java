package com.viaversion.viaversion.libs.javassist.tools.rmi;

public class Sample
{
    private ObjectImporter importer;
    private int objectId;
    
    public Object forward(final Object[] array, final int n) {
        return this.importer.call(this.objectId, n, array);
    }
    
    public static Object forwardStatic(final Object[] array, final int n) throws RemoteException {
        throw new RemoteException("cannot call a static method.");
    }
}
