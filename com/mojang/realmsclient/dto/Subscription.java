package com.mojang.realmsclient.dto;

import com.mojang.realmsclient.util.*;
import com.google.gson.*;
import org.apache.logging.log4j.*;

public class Subscription extends ValueObject
{
    private static final Logger LOGGER;
    public long startDate;
    public int daysLeft;
    public SubscriptionType type;
    
    public Subscription() {
        this.type = SubscriptionType.NORMAL;
    }
    
    public static Subscription parse(final String s) {
        final Subscription subscription = new Subscription();
        final JsonObject asJsonObject = new JsonParser().parse(s).getAsJsonObject();
        subscription.startDate = JsonUtils.getLongOr("startDate", asJsonObject, 0L);
        subscription.daysLeft = JsonUtils.getIntOr("daysLeft", asJsonObject, 0);
        subscription.type = typeFrom(JsonUtils.getStringOr("subscriptionType", asJsonObject, SubscriptionType.NORMAL.name()));
        return subscription;
    }
    
    private static SubscriptionType typeFrom(final String s) {
        return SubscriptionType.valueOf(s);
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    public enum SubscriptionType
    {
        NORMAL("NORMAL", 0), 
        RECURRING("RECURRING", 1);
        
        private static final SubscriptionType[] $VALUES;
        
        private SubscriptionType(final String s, final int n) {
        }
        
        static {
            $VALUES = new SubscriptionType[] { SubscriptionType.NORMAL, SubscriptionType.RECURRING };
        }
    }
}
