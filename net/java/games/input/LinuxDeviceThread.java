package net.java.games.input;

import java.util.*;
import java.io.*;

final class LinuxDeviceThread extends Thread
{
    private final List tasks;
    
    public LinuxDeviceThread() {
        this.tasks = new ArrayList();
        this.setDaemon(true);
        this.start();
    }
    
    public final synchronized void run() {
        while (true) {
            if (!this.tasks.isEmpty()) {
                final LinuxDeviceTask linuxDeviceTask = this.tasks.remove(0);
                linuxDeviceTask.doExecute();
                // monitorenter(linuxDeviceTask2 = linuxDeviceTask)
                linuxDeviceTask.notify();
            }
            // monitorexit(linuxDeviceTask2)
            else {
                this.wait();
            }
        }
    }
    
    public final Object execute(final LinuxDeviceTask linuxDeviceTask) throws IOException {
        // monitorenter(this)
        this.tasks.add(linuxDeviceTask);
        this.notify();
        // monitorexit(this)
        // monitorenter(linuxDeviceTask)
        while (linuxDeviceTask.getState() == 1) {
            linuxDeviceTask.wait();
        }
        // monitorexit(linuxDeviceTask)
        switch (linuxDeviceTask.getState()) {
            case 2: {
                return linuxDeviceTask.getResult();
            }
            case 3: {
                throw linuxDeviceTask.getException();
            }
            default: {
                throw new RuntimeException("Invalid task state: " + linuxDeviceTask.getState());
            }
        }
    }
}
