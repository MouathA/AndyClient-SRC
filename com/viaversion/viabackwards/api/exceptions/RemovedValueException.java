package com.viaversion.viabackwards.api.exceptions;

import java.io.*;

public class RemovedValueException extends IOException
{
    public static final RemovedValueException EX;
    
    static {
        EX = new RemovedValueException() {
            @Override
            public Throwable fillInStackTrace() {
                return this;
            }
        };
    }
}
