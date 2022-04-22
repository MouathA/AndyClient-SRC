package com.ibm.icu.util;

import java.io.*;

final class STZInfo implements Serializable
{
    private static final long serialVersionUID = -7849612037842370168L;
    int sy;
    int sm;
    int sdwm;
    int sdw;
    int st;
    int sdm;
    boolean sa;
    int em;
    int edwm;
    int edw;
    int et;
    int edm;
    boolean ea;
    
    STZInfo() {
        this.sy = -1;
        this.sm = -1;
        this.em = -1;
    }
    
    void setStart(final int sm, final int sdwm, final int sdw, final int st, final int sdm, final boolean sa) {
        this.sm = sm;
        this.sdwm = sdwm;
        this.sdw = sdw;
        this.st = st;
        this.sdm = sdm;
        this.sa = sa;
    }
    
    void setEnd(final int em, final int edwm, final int edw, final int et, final int edm, final boolean ea) {
        this.em = em;
        this.edwm = edwm;
        this.edw = edw;
        this.et = et;
        this.edm = edm;
        this.ea = ea;
    }
    
    void applyTo(final SimpleTimeZone simpleTimeZone) {
        if (this.sy != -1) {
            simpleTimeZone.setStartYear(this.sy);
        }
        if (this.sm != -1) {
            if (this.sdm == -1) {
                simpleTimeZone.setStartRule(this.sm, this.sdwm, this.sdw, this.st);
            }
            else if (this.sdw == -1) {
                simpleTimeZone.setStartRule(this.sm, this.sdm, this.st);
            }
            else {
                simpleTimeZone.setStartRule(this.sm, this.sdm, this.sdw, this.st, this.sa);
            }
        }
        if (this.em != -1) {
            if (this.edm == -1) {
                simpleTimeZone.setEndRule(this.em, this.edwm, this.edw, this.et);
            }
            else if (this.edw == -1) {
                simpleTimeZone.setEndRule(this.em, this.edm, this.et);
            }
            else {
                simpleTimeZone.setEndRule(this.em, this.edm, this.edw, this.et, this.ea);
            }
        }
    }
}
