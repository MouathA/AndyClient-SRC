package com.viaversion.viaversion.libs.javassist.tools.rmi;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.tools.web.*;
import java.io.*;

public class AppletServer extends Webserver
{
    private StubGenerator stubGen;
    private Map exportedNames;
    private List exportedObjects;
    private static final byte[] okHeader;
    
    public AppletServer(final String s) throws IOException, NotFoundException, CannotCompileException {
        this(Integer.parseInt(s));
    }
    
    public AppletServer(final int n) throws IOException, NotFoundException, CannotCompileException {
        this(ClassPool.getDefault(), new StubGenerator(), n);
    }
    
    public AppletServer(final int n, final ClassPool classPool) throws IOException, NotFoundException, CannotCompileException {
        this(new ClassPool(classPool), new StubGenerator(), n);
    }
    
    private AppletServer(final ClassPool classPool, final StubGenerator stubGen, final int n) throws IOException, NotFoundException, CannotCompileException {
        super(n);
        this.exportedNames = new Hashtable();
        this.exportedObjects = new Vector();
        this.addTranslator(classPool, this.stubGen = stubGen);
    }
    
    @Override
    public void run() {
        super.run();
    }
    
    public synchronized int exportObject(final String s, final Object object) throws CannotCompileException {
        final Class<?> class1 = object.getClass();
        final ExportedObject exportedObject = new ExportedObject();
        exportedObject.object = object;
        exportedObject.methods = class1.getMethods();
        this.exportedObjects.add(exportedObject);
        exportedObject.identifier = this.exportedObjects.size() - 1;
        if (s != null) {
            this.exportedNames.put(s, exportedObject);
        }
        this.stubGen.makeProxyClass(class1);
        return exportedObject.identifier;
    }
    
    @Override
    public void doReply(final InputStream inputStream, final OutputStream outputStream, final String s) throws IOException, BadHttpRequest {
        if (s.startsWith("POST /rmi ")) {
            this.processRMI(inputStream, outputStream);
        }
        else if (s.startsWith("POST /lookup ")) {
            this.lookupName(s, inputStream, outputStream);
        }
        else {
            super.doReply(inputStream, outputStream, s);
        }
    }
    
    private void processRMI(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        final ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        final int int1 = objectInputStream.readInt();
        final int int2 = objectInputStream.readInt();
        final Throwable t = null;
        final ExportedObject exportedObject = this.exportedObjects.get(int1);
        final Object convertRvalue = this.convertRvalue(exportedObject.methods[int2].invoke(exportedObject.object, this.readParameters(objectInputStream)));
        outputStream.write(AppletServer.okHeader);
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        if (t != null) {
            objectOutputStream.writeBoolean(false);
            objectOutputStream.writeUTF(t.toString());
        }
        else {
            objectOutputStream.writeBoolean(true);
            objectOutputStream.writeObject(convertRvalue);
        }
        objectOutputStream.flush();
        objectOutputStream.close();
        objectInputStream.close();
    }
    
    private Object[] readParameters(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final int int1 = objectInputStream.readInt();
        final Object[] array = new Object[int1];
        while (0 < int1) {
            Object o = objectInputStream.readObject();
            if (o instanceof RemoteRef) {
                o = ((ExportedObject)this.exportedObjects.get(((RemoteRef)o).oid)).object;
            }
            array[0] = o;
            int n = 0;
            ++n;
        }
        return array;
    }
    
    private Object convertRvalue(final Object o) throws CannotCompileException {
        if (o == null) {
            return null;
        }
        final String name = o.getClass().getName();
        if (this.stubGen.isProxyClass(name)) {
            return new RemoteRef(this.exportObject(null, o), name);
        }
        return o;
    }
    
    private void lookupName(final String s, final InputStream inputStream, final OutputStream outputStream) throws IOException {
        final ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        final String utf = DataInputStream.readUTF(objectInputStream);
        final ExportedObject exportedObject = this.exportedNames.get(utf);
        outputStream.write(AppletServer.okHeader);
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        if (exportedObject == null) {
            this.logging2(utf + "not found.");
            objectOutputStream.writeInt(-1);
            objectOutputStream.writeUTF("error");
        }
        else {
            this.logging2(utf);
            objectOutputStream.writeInt(exportedObject.identifier);
            objectOutputStream.writeUTF(exportedObject.object.getClass().getName());
        }
        objectOutputStream.flush();
        objectOutputStream.close();
        objectInputStream.close();
    }
    
    static {
        okHeader = "HTTP/1.0 200 OK\r\n\r\n".getBytes();
    }
}
