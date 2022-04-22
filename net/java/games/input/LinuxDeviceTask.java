package net.java.games.input;

import java.io.*;

abstract class LinuxDeviceTask
{
    private Object result;
    private IOException exception;
    private int state;
    
    LinuxDeviceTask() {
        this.state = 1;
    }
    
    public final void doExecute() {
        this.result = this.execute();
        this.state = 2;
    }
    
    public final IOException getException() {
        return this.exception;
    }
    
    public final Object getResult() {
        return this.result;
    }
    
    public final int getState() {
        return this.state;
    }
    
    protected abstract Object execute() throws IOException;
}
