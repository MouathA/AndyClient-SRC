package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.impl.*;
import java.util.*;

public class BasicPeriodFormatterService implements PeriodFormatterService
{
    private static BasicPeriodFormatterService instance;
    private PeriodFormatterDataService ds;
    
    public static BasicPeriodFormatterService getInstance() {
        if (BasicPeriodFormatterService.instance == null) {
            BasicPeriodFormatterService.instance = new BasicPeriodFormatterService(ResourceBasedPeriodFormatterDataService.getInstance());
        }
        return BasicPeriodFormatterService.instance;
    }
    
    public BasicPeriodFormatterService(final PeriodFormatterDataService ds) {
        this.ds = ds;
    }
    
    public DurationFormatterFactory newDurationFormatterFactory() {
        return new BasicDurationFormatterFactory(this);
    }
    
    public PeriodFormatterFactory newPeriodFormatterFactory() {
        return new BasicPeriodFormatterFactory(this.ds);
    }
    
    public PeriodBuilderFactory newPeriodBuilderFactory() {
        return new BasicPeriodBuilderFactory(this.ds);
    }
    
    public Collection getAvailableLocaleNames() {
        return this.ds.getAvailableLocales();
    }
}
