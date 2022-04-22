package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.impl.*;
import java.util.*;

public class BasicPeriodFormatterFactory implements PeriodFormatterFactory
{
    private final PeriodFormatterDataService ds;
    private PeriodFormatterData data;
    private Customizations customizations;
    private boolean customizationsInUse;
    private String localeName;
    
    BasicPeriodFormatterFactory(final PeriodFormatterDataService ds) {
        this.ds = ds;
        this.customizations = new Customizations();
        this.localeName = Locale.getDefault().toString();
    }
    
    public static BasicPeriodFormatterFactory getDefault() {
        return (BasicPeriodFormatterFactory)BasicPeriodFormatterService.getInstance().newPeriodFormatterFactory();
    }
    
    public PeriodFormatterFactory setLocale(final String localeName) {
        this.data = null;
        this.localeName = localeName;
        return this;
    }
    
    public PeriodFormatterFactory setDisplayLimit(final boolean displayLimit) {
        this.updateCustomizations().displayLimit = displayLimit;
        return this;
    }
    
    public boolean getDisplayLimit() {
        return this.customizations.displayLimit;
    }
    
    public PeriodFormatterFactory setDisplayPastFuture(final boolean displayDirection) {
        this.updateCustomizations().displayDirection = displayDirection;
        return this;
    }
    
    public boolean getDisplayPastFuture() {
        return this.customizations.displayDirection;
    }
    
    public PeriodFormatterFactory setSeparatorVariant(final int n) {
        this.updateCustomizations().separatorVariant = (byte)n;
        return this;
    }
    
    public int getSeparatorVariant() {
        return this.customizations.separatorVariant;
    }
    
    public PeriodFormatterFactory setUnitVariant(final int n) {
        this.updateCustomizations().unitVariant = (byte)n;
        return this;
    }
    
    public int getUnitVariant() {
        return this.customizations.unitVariant;
    }
    
    public PeriodFormatterFactory setCountVariant(final int n) {
        this.updateCustomizations().countVariant = (byte)n;
        return this;
    }
    
    public int getCountVariant() {
        return this.customizations.countVariant;
    }
    
    public PeriodFormatter getFormatter() {
        this.customizationsInUse = true;
        return new BasicPeriodFormatter(this, this.localeName, this.getData(), this.customizations);
    }
    
    private Customizations updateCustomizations() {
        if (this.customizationsInUse) {
            this.customizations = this.customizations.copy();
            this.customizationsInUse = false;
        }
        return this.customizations;
    }
    
    PeriodFormatterData getData() {
        if (this.data == null) {
            this.data = this.ds.get(this.localeName);
        }
        return this.data;
    }
    
    PeriodFormatterData getData(final String s) {
        return this.ds.get(s);
    }
    
    static class Customizations
    {
        boolean displayLimit;
        boolean displayDirection;
        byte separatorVariant;
        byte unitVariant;
        byte countVariant;
        
        Customizations() {
            this.displayLimit = true;
            this.displayDirection = true;
            this.separatorVariant = 2;
            this.unitVariant = 0;
            this.countVariant = 0;
        }
        
        public Customizations copy() {
            final Customizations customizations = new Customizations();
            customizations.displayLimit = this.displayLimit;
            customizations.displayDirection = this.displayDirection;
            customizations.separatorVariant = this.separatorVariant;
            customizations.unitVariant = this.unitVariant;
            customizations.countVariant = this.countVariant;
            return customizations;
        }
    }
}
