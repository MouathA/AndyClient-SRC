package Mood.Helpers;

import java.util.*;

public class ServerPerformanceCalculator
{
    public static ArrayList times;
    public static ArrayList tpsList;
    public static long currentLag;
    public static double lastTps;
    public static TimeHelper timeUtils;
    
    static {
        ServerPerformanceCalculator.times = new ArrayList();
        ServerPerformanceCalculator.tpsList = new ArrayList();
        ServerPerformanceCalculator.timeUtils = new TimeHelper();
    }
    
    public static void calculate() {
    }
    
    public static String getFormatLag() {
        final long n = ServerPerformanceCalculator.currentLag - System.currentTimeMillis();
        if (System.currentTimeMillis() - ServerPerformanceCalculator.currentLag > 2500L && System.currentTimeMillis() - ServerPerformanceCalculator.currentLag < 500000L) {
            final String replace = String.valueOf(n).replace("-", "");
            ServerPerformanceCalculator.lastTps = 0.0;
            return String.valueOf(replace) + "ms";
        }
        return "";
    }
    
    private static void calculateLag() {
        ServerPerformanceCalculator.currentLag = System.currentTimeMillis();
    }
    
    public static String getFormatTps() {
        String s = String.format("%.3f", ServerPerformanceCalculator.lastTps);
        if (s.startsWith(".")) {
            s = "0" + s;
        }
        final double n = ServerPerformanceCalculator.lastTps / 20.0 * 100.0;
        Arrays.sort(ServerPerformanceCalculator.tpsList.toArray());
        double doubleValue;
        if (ServerPerformanceCalculator.tpsList.toArray().length % 2 == 0) {
            doubleValue = ((double)ServerPerformanceCalculator.tpsList.toArray()[ServerPerformanceCalculator.tpsList.toArray().length / 2] + (double)ServerPerformanceCalculator.tpsList.toArray()[ServerPerformanceCalculator.tpsList.toArray().length / 2 - 1]) / 2.0;
        }
        else {
            doubleValue = (double)ServerPerformanceCalculator.tpsList.toArray()[ServerPerformanceCalculator.tpsList.toArray().length / 2];
        }
        if (System.currentTimeMillis() - ServerPerformanceCalculator.currentLag > 2500L && System.currentTimeMillis() - ServerPerformanceCalculator.currentLag < 500000L) {
            return "§40,000";
        }
        return String.valueOf(MiscUtils.getTPSColor(ServerPerformanceCalculator.lastTps)) + s + " §7(§e±" + MiscUtils.getTPSColor(doubleValue) + String.format("%.3f", 20.0 - doubleValue) + "§7) (" + MiscUtils.getTPSColorByProzent(n) + String.format("%.3f", n) + "§7/" + MiscUtils.getTPSColorByProzent(100.0 - n) + String.format("%.3f", 100.0 - n) + "§e§l%§7)";
    }
    
    private static void calculateTps() {
        ServerPerformanceCalculator.times.add(Math.max(1000L, ServerPerformanceCalculator.timeUtils.getDelay()));
        long n = 0L;
        if (ServerPerformanceCalculator.times.size() > 5) {
            ServerPerformanceCalculator.times.remove(0);
        }
        final Iterator<Long> iterator = ServerPerformanceCalculator.times.iterator();
        while (iterator.hasNext()) {
            n += iterator.next();
        }
        final long n2 = n / ServerPerformanceCalculator.times.size();
        if (System.currentTimeMillis() - ServerPerformanceCalculator.currentLag > 2500L && System.currentTimeMillis() - ServerPerformanceCalculator.currentLag < 500000L) {
            ServerPerformanceCalculator.lastTps = 0.0;
        }
        else {
            ServerPerformanceCalculator.lastTps = 20.0 / n2 * 1000.0;
        }
        ServerPerformanceCalculator.tpsList.add(ServerPerformanceCalculator.lastTps);
        ServerPerformanceCalculator.timeUtils.reset();
    }
    
    public static class TimeHelper
    {
        private long lastMS;
        
        public TimeHelper() {
            this.lastMS = 0L;
            this.reset();
        }
        
        public int convertToMS(final int n) {
            return 1000 / n;
        }
        
        public long getDelay() {
            return System.currentTimeMillis() - this.lastMS;
        }
        
        public boolean hasTimeReached(final long n) {
            return this.getDelay() >= n;
        }
        
        public void reset() {
            this.setLastMS(System.currentTimeMillis());
        }
        
        public void setLastMS(final long lastMS) {
            this.lastMS = lastMS;
        }
    }
}
