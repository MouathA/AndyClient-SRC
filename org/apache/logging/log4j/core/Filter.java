package org.apache.logging.log4j.core;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.util.*;

public interface Filter
{
    Result getOnMismatch();
    
    Result getOnMatch();
    
    Result filter(final Logger p0, final Level p1, final Marker p2, final String p3, final Object... p4);
    
    Result filter(final Logger p0, final Level p1, final Marker p2, final Object p3, final Throwable p4);
    
    Result filter(final Logger p0, final Level p1, final Marker p2, final Message p3, final Throwable p4);
    
    Result filter(final LogEvent p0);
    
    public enum Result
    {
        ACCEPT("ACCEPT", 0), 
        NEUTRAL("NEUTRAL", 1), 
        DENY("DENY", 2);
        
        private static final Result[] $VALUES;
        
        private Result(final String s, final int n) {
        }
        
        public static Result toResult(final String s) {
            return toResult(s, null);
        }
        
        public static Result toResult(final String s, final Result result) {
            return (Result)EnglishEnums.valueOf(Result.class, s, result);
        }
        
        static {
            $VALUES = new Result[] { Result.ACCEPT, Result.NEUTRAL, Result.DENY };
        }
    }
}
