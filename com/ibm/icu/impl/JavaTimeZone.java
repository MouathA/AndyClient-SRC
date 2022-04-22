package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import java.lang.reflect.*;
import java.util.*;
import java.io.*;

public class JavaTimeZone extends TimeZone
{
    private static final long serialVersionUID = 6977448185543929364L;
    private static final TreeSet AVAILABLESET;
    private java.util.TimeZone javatz;
    private transient Calendar javacal;
    private static Method mObservesDaylightTime;
    private transient boolean isFrozen;
    
    public JavaTimeZone() {
        this(java.util.TimeZone.getDefault(), null);
    }
    
    public JavaTimeZone(final java.util.TimeZone javatz, String id) {
        this.isFrozen = false;
        if (id == null) {
            id = javatz.getID();
        }
        this.javatz = javatz;
        this.setID(id);
        this.javacal = new GregorianCalendar(this.javatz);
    }
    
    public static JavaTimeZone createTimeZone(final String s) {
        java.util.TimeZone timeZone = null;
        if (JavaTimeZone.AVAILABLESET.contains(s)) {
            timeZone = java.util.TimeZone.getTimeZone(s);
        }
        if (timeZone == null) {
            final boolean[] array = { false };
            final String canonicalID = TimeZone.getCanonicalID(s, array);
            if (array[0] && JavaTimeZone.AVAILABLESET.contains(canonicalID)) {
                timeZone = java.util.TimeZone.getTimeZone(canonicalID);
            }
        }
        if (timeZone == null) {
            return null;
        }
        return new JavaTimeZone(timeZone, s);
    }
    
    @Override
    public int getOffset(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return this.javatz.getOffset(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void getOffset(final long timeInMillis, final boolean b, final int[] array) {
        // monitorenter(javacal = this.javacal)
        if (b) {
            final int[] array2 = new int[6];
            Grego.timeToFields(timeInMillis, array2);
            final int n = array2[5];
            final int n2 = n % 1000;
            final int n3 = n / 1000;
            final int n4 = n3 % 60;
            final int n5 = n3 / 60;
            final int n6 = n5 % 60;
            final int n7 = n5 / 60;
            this.javacal.clear();
            this.javacal.set(array2[0], array2[1], array2[2], n7, n6, n4);
            this.javacal.set(14, n2);
            final int value = this.javacal.get(6);
            final int value2 = this.javacal.get(11);
            final int value3 = this.javacal.get(12);
            final int value4 = this.javacal.get(13);
            final int value5 = this.javacal.get(14);
            if (array2[4] != value || n7 != value2 || n6 != value3 || n4 != value4 || n2 != value5) {
                this.javacal.setTimeInMillis(this.javacal.getTimeInMillis() - ((((((Math.abs(value - array2[4]) > 1) ? 1 : (value - array2[4])) * 24 + value2 - n7) * 60 + value3 - n6) * 60 + value4 - n4) * 1000 + value5 - n2) - 1L);
            }
        }
        else {
            this.javacal.setTimeInMillis(timeInMillis);
        }
        array[0] = this.javacal.get(15);
        array[1] = this.javacal.get(16);
    }
    // monitorexit(javacal)
    
    @Override
    public int getRawOffset() {
        return this.javatz.getRawOffset();
    }
    
    @Override
    public boolean inDaylightTime(final Date date) {
        return this.javatz.inDaylightTime(date);
    }
    
    @Override
    public void setRawOffset(final int rawOffset) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen JavaTimeZone instance.");
        }
        this.javatz.setRawOffset(rawOffset);
    }
    
    @Override
    public boolean useDaylightTime() {
        return this.javatz.useDaylightTime();
    }
    
    @Override
    public boolean observesDaylightTime() {
        if (JavaTimeZone.mObservesDaylightTime != null) {
            return (boolean)JavaTimeZone.mObservesDaylightTime.invoke(this.javatz, (Object[])null);
        }
        return super.observesDaylightTime();
    }
    
    @Override
    public int getDSTSavings() {
        return this.javatz.getDSTSavings();
    }
    
    public java.util.TimeZone unwrap() {
        return this.javatz;
    }
    
    @Override
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() + this.javatz.hashCode();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.javacal = new GregorianCalendar(this.javatz);
    }
    
    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }
    
    @Override
    public TimeZone freeze() {
        this.isFrozen = true;
        return this;
    }
    
    @Override
    public TimeZone cloneAsThawed() {
        final JavaTimeZone javaTimeZone = (JavaTimeZone)super.cloneAsThawed();
        javaTimeZone.javatz = (java.util.TimeZone)this.javatz.clone();
        javaTimeZone.javacal = (GregorianCalendar)this.javacal.clone();
        javaTimeZone.isFrozen = false;
        return javaTimeZone;
    }
    
    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    @Override
    public Object freeze() {
        return this.freeze();
    }
    
    static {
        AVAILABLESET = new TreeSet();
        final String[] availableIDs = java.util.TimeZone.getAvailableIDs();
        while (0 < availableIDs.length) {
            JavaTimeZone.AVAILABLESET.add(availableIDs[0]);
            int n = 0;
            ++n;
        }
        JavaTimeZone.mObservesDaylightTime = java.util.TimeZone.class.getMethod("observesDaylightTime", (Class<?>[])null);
    }
}
