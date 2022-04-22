package org.apache.logging.log4j.message;

import java.io.*;

public class ObjectMessage implements Message
{
    private static final long serialVersionUID = -5903272448334166185L;
    private transient Object obj;
    
    public ObjectMessage(Object obj) {
        if (obj == null) {
            obj = "null";
        }
        this.obj = obj;
    }
    
    @Override
    public String getFormattedMessage() {
        return this.obj.toString();
    }
    
    @Override
    public String getFormat() {
        return this.obj.toString();
    }
    
    @Override
    public Object[] getParameters() {
        return new Object[] { this.obj };
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ObjectMessage objectMessage = (ObjectMessage)o;
        if (this.obj != null) {
            if (!this.obj.equals(objectMessage.obj)) {
                return false;
            }
        }
        else if (objectMessage.obj != null) {
            return false;
        }
        return true;
        b = false;
        return b;
    }
    
    @Override
    public int hashCode() {
        return (this.obj != null) ? this.obj.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "ObjectMessage[obj=" + this.obj.toString() + "]";
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        if (this.obj instanceof Serializable) {
            objectOutputStream.writeObject(this.obj);
        }
        else {
            objectOutputStream.writeObject(this.obj.toString());
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.obj = objectInputStream.readObject();
    }
    
    @Override
    public Throwable getThrowable() {
        return (this.obj instanceof Throwable) ? ((Throwable)this.obj) : null;
    }
}
