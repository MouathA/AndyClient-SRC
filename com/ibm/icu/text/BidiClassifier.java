package com.ibm.icu.text;

public class BidiClassifier
{
    protected Object context;
    
    public BidiClassifier(final Object context) {
        this.context = context;
    }
    
    public void setContext(final Object context) {
        this.context = context;
    }
    
    public Object getContext() {
        return this.context;
    }
    
    public int classify(final int n) {
        return 19;
    }
}
