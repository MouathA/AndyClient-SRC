package com.ibm.icu.util;

import com.ibm.icu.lang.*;

public class CaseInsensitiveString
{
    private String string;
    private int hash;
    private String folded;
    
    private static String foldCase(final String s) {
        return UCharacter.foldCase(s, true);
    }
    
    private void getFolded() {
        if (this.folded == null) {
            this.folded = foldCase(this.string);
        }
    }
    
    public CaseInsensitiveString(final String string) {
        this.hash = 0;
        this.folded = null;
        this.string = string;
    }
    
    public String getString() {
        return this.string;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        this.getFolded();
        final CaseInsensitiveString caseInsensitiveString = (CaseInsensitiveString)o;
        caseInsensitiveString.getFolded();
        return this.folded.equals(caseInsensitiveString.folded);
    }
    
    @Override
    public int hashCode() {
        this.getFolded();
        if (this.hash == 0) {
            this.hash = this.folded.hashCode();
        }
        return this.hash;
    }
    
    @Override
    public String toString() {
        return this.string;
    }
}
