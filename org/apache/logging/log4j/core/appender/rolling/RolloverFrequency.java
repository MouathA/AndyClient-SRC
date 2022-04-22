package org.apache.logging.log4j.core.appender.rolling;

public enum RolloverFrequency
{
    ANNUALLY("ANNUALLY", 0), 
    MONTHLY("MONTHLY", 1), 
    WEEKLY("WEEKLY", 2), 
    DAILY("DAILY", 3), 
    HOURLY("HOURLY", 4), 
    EVERY_MINUTE("EVERY_MINUTE", 5), 
    EVERY_SECOND("EVERY_SECOND", 6), 
    EVERY_MILLISECOND("EVERY_MILLISECOND", 7);
    
    private static final RolloverFrequency[] $VALUES;
    
    private RolloverFrequency(final String s, final int n) {
    }
    
    static {
        $VALUES = new RolloverFrequency[] { RolloverFrequency.ANNUALLY, RolloverFrequency.MONTHLY, RolloverFrequency.WEEKLY, RolloverFrequency.DAILY, RolloverFrequency.HOURLY, RolloverFrequency.EVERY_MINUTE, RolloverFrequency.EVERY_SECOND, RolloverFrequency.EVERY_MILLISECOND };
    }
}
