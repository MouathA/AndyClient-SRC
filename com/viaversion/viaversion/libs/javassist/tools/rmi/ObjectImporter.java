package com.viaversion.viaversion.libs.javassist.tools.rmi;

import java.applet.*;
import java.net.*;
import java.io.*;

public class ObjectImporter implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final byte[] endofline;
    private String servername;
    private String orgServername;
    private int port;
    private int orgPort;
    protected byte[] lookupCommand;
    protected byte[] rmiCommand;
    private static final Class[] proxyConstructorParamTypes;
    
    public ObjectImporter(final Applet applet) {
        this.endofline = new byte[] { 13, 10 };
        this.lookupCommand = "POST /lookup HTTP/1.0".getBytes();
        this.rmiCommand = "POST /rmi HTTP/1.0".getBytes();
        final URL codeBase = applet.getCodeBase();
        final String host = codeBase.getHost();
        this.servername = host;
        this.orgServername = host;
        final int port = codeBase.getPort();
        this.port = port;
        this.orgPort = port;
    }
    
    public ObjectImporter(final String s, final int n) {
        this.endofline = new byte[] { 13, 10 };
        this.lookupCommand = "POST /lookup HTTP/1.0".getBytes();
        this.rmiCommand = "POST /rmi HTTP/1.0".getBytes();
        this.servername = s;
        this.orgServername = s;
        this.port = n;
        this.orgPort = n;
    }
    
    public Object getObject(final String s) {
        return this.lookupObject(s);
    }
    
    public void setHttpProxy(final String servername, final int port) {
        final String string = "POST http://" + this.orgServername + ":" + this.orgPort;
        this.lookupCommand = (string + "/lookup HTTP/1.0").getBytes();
        this.rmiCommand = (string + "/rmi HTTP/1.0").getBytes();
        this.servername = servername;
        this.port = port;
    }
    
    public Object lookupObject(final String s) throws ObjectNotFoundException {
        final Socket socket = new Socket(this.servername, this.port);
        final OutputStream outputStream = socket.getOutputStream();
        outputStream.write(this.lookupCommand);
        outputStream.write(this.endofline);
        outputStream.write(this.endofline);
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeUTF(s);
        objectOutputStream.flush();
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        this.skipHeader(bufferedInputStream);
        final ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
        final int int1 = objectInputStream.readInt();
        final String utf = objectInputStream.readUTF();
        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
        if (int1 >= 0) {
            return this.createProxy(int1, utf);
        }
        throw new ObjectNotFoundException(s);
    }
    
    private Object createProxy(final int n, final String s) throws Exception {
        return Class.forName(s).getConstructor((Class<?>[])ObjectImporter.proxyConstructorParamTypes).newInstance(this, n);
    }
    
    public Object call(final int n, final int n2, final Object[] array) throws RemoteException {
        final Socket socket = new Socket(this.servername, this.port);
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        bufferedOutputStream.write(this.rmiCommand);
        bufferedOutputStream.write(this.endofline);
        bufferedOutputStream.write(this.endofline);
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
        objectOutputStream.writeInt(n);
        objectOutputStream.writeInt(n2);
        this.writeParameters(objectOutputStream, array);
        objectOutputStream.flush();
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        this.skipHeader(bufferedInputStream);
        final ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
        final boolean boolean1 = objectInputStream.readBoolean();
        Object o = null;
        String utf = null;
        if (boolean1) {
            o = objectInputStream.readObject();
        }
        else {
            utf = objectInputStream.readUTF();
        }
        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
        if (o instanceof RemoteRef) {
            final RemoteRef remoteRef = (RemoteRef)o;
            o = this.createProxy(remoteRef.oid, remoteRef.classname);
        }
        if (boolean1) {
            return o;
        }
        throw new RemoteException(utf);
    }
    
    private void skipHeader(final InputStream inputStream) throws IOException {
        while (true) {
            final int read;
            if ((read = inputStream.read()) >= 0 && read != 13) {
                int n = 0;
                ++n;
            }
            else {
                inputStream.read();
                if (0 <= 0) {
                    break;
                }
                continue;
            }
        }
    }
    
    private void writeParameters(final ObjectOutputStream objectOutputStream, final Object[] array) throws IOException {
        final int length = array.length;
        objectOutputStream.writeInt(length);
        while (0 < length) {
            if (array[0] instanceof Proxy) {
                objectOutputStream.writeObject(new RemoteRef(((Proxy)array[0])._getObjectId()));
            }
            else {
                objectOutputStream.writeObject(array[0]);
            }
            int n = 0;
            ++n;
        }
    }
    
    static {
        proxyConstructorParamTypes = new Class[] { ObjectImporter.class, Integer.TYPE };
    }
}
