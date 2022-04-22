package Mood.Helpers;

import Mood.Host.Helper.*;
import java.text.*;
import java.util.*;

public class LagMeter
{
    public static LagMeter instance;
    private int packetsPerSecondTemp;
    private int packetsPerSecond;
    private long lastMS;
    private boolean doneOneTime;
    private long startTime;
    private long lastReceiveTime;
    public static double tps;
    private List tpsList;
    private float listTime;
    private int tempTicks;
    private TimeHelper th;
    private DecimalFormat df;
    
    public LagMeter() {
        this.packetsPerSecondTemp = 0;
        this.listTime = 300.0f;
        this.tempTicks = 0;
        this.tpsList = new ArrayList();
        this.th = new TimeHelper();
        this.df = new DecimalFormat();
        LagMeter.instance = this;
    }
    
    public long getServerLagTime() {
        if (this.startTime <= 0L) {
            return 0L;
        }
        return System.currentTimeMillis() - this.startTime;
    }
    
    public static char getTPSColorCode(final float n) {
        if (n >= 17.0f) {
            return 'a';
        }
        if (n >= 13.0f) {
            return 'e';
        }
        if (n > 9.0f) {
            return 'c';
        }
        return '4';
    }
}
