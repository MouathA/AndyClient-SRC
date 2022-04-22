package net.minecraft.util;

import net.minecraft.crash.*;

public class ReportedException extends RuntimeException
{
    private final CrashReport theReportedExceptionCrashReport;
    private static final String __OBFID;
    
    public ReportedException(final CrashReport theReportedExceptionCrashReport) {
        this.theReportedExceptionCrashReport = theReportedExceptionCrashReport;
    }
    
    public CrashReport getCrashReport() {
        return this.theReportedExceptionCrashReport;
    }
    
    @Override
    public Throwable getCause() {
        return this.theReportedExceptionCrashReport.getCrashCause();
    }
    
    @Override
    public String getMessage() {
        return this.theReportedExceptionCrashReport.getDescription();
    }
    
    static {
        __OBFID = "CL_00001579";
    }
}
