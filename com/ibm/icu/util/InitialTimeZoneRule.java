package com.ibm.icu.util;

import java.util.*;

public class InitialTimeZoneRule extends TimeZoneRule
{
    private static final long serialVersionUID = 1876594993064051206L;
    
    public InitialTimeZoneRule(final String s, final int n, final int n2) {
        super(s, n, n2);
    }
    
    @Override
    public boolean isEquivalentTo(final TimeZoneRule timeZoneRule) {
        return timeZoneRule instanceof InitialTimeZoneRule && super.isEquivalentTo(timeZoneRule);
    }
    
    @Override
    public Date getFinalStart(final int n, final int n2) {
        return null;
    }
    
    @Override
    public Date getFirstStart(final int n, final int n2) {
        return null;
    }
    
    @Override
    public Date getNextStart(final long n, final int n2, final int n3, final boolean b) {
        return null;
    }
    
    @Override
    public Date getPreviousStart(final long n, final int n2, final int n3, final boolean b) {
        return null;
    }
    
    @Override
    public boolean isTransitionRule() {
        return false;
    }
}
