package com.sun.jna.platform.win32;

import com.sun.jna.ptr.*;
import com.sun.jna.*;

public abstract class WinspoolUtil
{
    public static Winspool.PRINTER_INFO_1[] getPrinterInfo1() {
        final IntByReference intByReference = new IntByReference();
        final IntByReference intByReference2 = new IntByReference();
        Winspool.INSTANCE.EnumPrinters(2, null, 1, null, 0, intByReference, intByReference2);
        if (intByReference.getValue() <= 0) {
            return new Winspool.PRINTER_INFO_1[0];
        }
        final Winspool.PRINTER_INFO_1 printer_INFO_1 = new Winspool.PRINTER_INFO_1(intByReference.getValue());
        if (!Winspool.INSTANCE.EnumPrinters(2, null, 1, printer_INFO_1.getPointer(), intByReference.getValue(), intByReference, intByReference2)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return (Winspool.PRINTER_INFO_1[])printer_INFO_1.toArray(intByReference2.getValue());
    }
    
    public static Winspool.PRINTER_INFO_4[] getPrinterInfo4() {
        final IntByReference intByReference = new IntByReference();
        final IntByReference intByReference2 = new IntByReference();
        Winspool.INSTANCE.EnumPrinters(2, null, 4, null, 0, intByReference, intByReference2);
        if (intByReference.getValue() <= 0) {
            return new Winspool.PRINTER_INFO_4[0];
        }
        final Winspool.PRINTER_INFO_4 printer_INFO_4 = new Winspool.PRINTER_INFO_4(intByReference.getValue());
        if (!Winspool.INSTANCE.EnumPrinters(2, null, 4, printer_INFO_4.getPointer(), intByReference.getValue(), intByReference, intByReference2)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return (Winspool.PRINTER_INFO_4[])printer_INFO_4.toArray(intByReference2.getValue());
    }
}
