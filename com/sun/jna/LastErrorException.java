package com.sun.jna;

public class LastErrorException extends RuntimeException
{
    private int errorCode;
    
    private static String formatMessage(final int n) {
        return Platform.isWindows() ? ("GetLastError() returned " + n) : ("errno was " + n);
    }
    
    private static String parseMessage(final String s) {
        return formatMessage(Integer.parseInt(s));
    }
    
    public LastErrorException(String substring) {
        super(parseMessage(substring.trim()));
        if (substring.startsWith("[")) {
            substring = substring.substring(1, substring.indexOf("]"));
        }
        this.errorCode = Integer.parseInt(substring);
    }
    
    public int getErrorCode() {
        return this.errorCode;
    }
    
    public LastErrorException(final int errorCode) {
        super(formatMessage(errorCode));
        this.errorCode = errorCode;
    }
}
