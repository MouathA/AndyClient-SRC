package com.mojang.realmsclient.exception;

import com.mojang.realmsclient.client.*;
import net.minecraft.realms.*;

public class RealmsServiceException extends Exception
{
    public final int httpResultCode;
    public final String httpResponseContent;
    public final int errorCode;
    public final String errorMsg;
    
    public RealmsServiceException(final int httpResultCode, final String httpResponseContent, final RealmsError realmsError) {
        super(httpResponseContent);
        this.httpResultCode = httpResultCode;
        this.httpResponseContent = httpResponseContent;
        this.errorCode = realmsError.getErrorCode();
        this.errorMsg = realmsError.getErrorMessage();
    }
    
    public RealmsServiceException(final int httpResultCode, final String httpResponseContent, final int errorCode, final String errorMsg) {
        super(httpResponseContent);
        this.httpResultCode = httpResultCode;
        this.httpResponseContent = httpResponseContent;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    @Override
    public String toString() {
        if (this.errorCode != -1) {
            final String string = "mco.errorMessage." + this.errorCode;
            final String localizedString = RealmsScreen.getLocalizedString(string);
            return (localizedString.equals(string) ? this.errorMsg : localizedString) + " - " + this.errorCode;
        }
        return "Realms (" + this.httpResultCode + ") " + this.httpResponseContent;
    }
}
