package com.sun.jna.platform.win32;

import com.sun.jna.ptr.*;

public class W32Service
{
    Winsvc.SC_HANDLE _handle;
    
    public W32Service(final Winsvc.SC_HANDLE handle) {
        this._handle = null;
        this._handle = handle;
    }
    
    public void close() {
        if (this._handle != null) {
            if (!Advapi32.INSTANCE.CloseServiceHandle(this._handle)) {
                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
            }
            this._handle = null;
        }
    }
    
    public Winsvc.SERVICE_STATUS_PROCESS queryStatus() {
        final IntByReference intByReference = new IntByReference();
        Advapi32.INSTANCE.QueryServiceStatusEx(this._handle, 0, null, 0, intByReference);
        final Winsvc.SERVICE_STATUS_PROCESS service_STATUS_PROCESS = new Winsvc.SERVICE_STATUS_PROCESS(intByReference.getValue());
        if (!Advapi32.INSTANCE.QueryServiceStatusEx(this._handle, 0, service_STATUS_PROCESS, service_STATUS_PROCESS.size(), intByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return service_STATUS_PROCESS;
    }
    
    public void startService() {
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState == 4) {
            return;
        }
        if (!Advapi32.INSTANCE.StartService(this._handle, 0, null)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState != 4) {
            throw new RuntimeException("Unable to start the service");
        }
    }
    
    public void stopService() {
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState == 1) {
            return;
        }
        if (!Advapi32.INSTANCE.ControlService(this._handle, 1, new Winsvc.SERVICE_STATUS())) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState != 1) {
            throw new RuntimeException("Unable to stop the service");
        }
    }
    
    public void continueService() {
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState == 4) {
            return;
        }
        if (!Advapi32.INSTANCE.ControlService(this._handle, 3, new Winsvc.SERVICE_STATUS())) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState != 4) {
            throw new RuntimeException("Unable to continue the service");
        }
    }
    
    public void pauseService() {
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState == 7) {
            return;
        }
        if (!Advapi32.INSTANCE.ControlService(this._handle, 2, new Winsvc.SERVICE_STATUS())) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        this.waitForNonPendingState();
        if (this.queryStatus().dwCurrentState != 7) {
            throw new RuntimeException("Unable to pause the service");
        }
    }
    
    public void waitForNonPendingState() {
        Winsvc.SERVICE_STATUS_PROCESS service_STATUS_PROCESS = this.queryStatus();
        int n = service_STATUS_PROCESS.dwCheckPoint;
        int n2 = Kernel32.INSTANCE.GetTickCount();
        while (this.isPendingState(service_STATUS_PROCESS.dwCurrentState)) {
            if (service_STATUS_PROCESS.dwCheckPoint != n) {
                n = service_STATUS_PROCESS.dwCheckPoint;
                n2 = Kernel32.INSTANCE.GetTickCount();
            }
            if (Kernel32.INSTANCE.GetTickCount() - n2 > service_STATUS_PROCESS.dwWaitHint) {
                throw new RuntimeException("Timeout waiting for service to change to a non-pending state.");
            }
            final int n3 = service_STATUS_PROCESS.dwWaitHint / 10;
            if (10000 >= 1000) {
                if (10000 > 10000) {}
            }
            Thread.sleep(10000);
            service_STATUS_PROCESS = this.queryStatus();
        }
    }
    
    private boolean isPendingState(final int n) {
        switch (n) {
            case 2:
            case 3:
            case 5:
            case 6: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public Winsvc.SC_HANDLE getHandle() {
        return this._handle;
    }
}
