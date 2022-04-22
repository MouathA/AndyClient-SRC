package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "Policies", category = "Core", printObject = true)
public final class CompositeTriggeringPolicy implements TriggeringPolicy
{
    private final TriggeringPolicy[] policies;
    
    private CompositeTriggeringPolicy(final TriggeringPolicy... policies) {
        this.policies = policies;
    }
    
    @Override
    public void initialize(final RollingFileManager rollingFileManager) {
        final TriggeringPolicy[] policies = this.policies;
        while (0 < policies.length) {
            policies[0].initialize(rollingFileManager);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean isTriggeringEvent(final LogEvent logEvent) {
        final TriggeringPolicy[] policies = this.policies;
        while (0 < policies.length) {
            if (policies[0].isTriggeringEvent(logEvent)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompositeTriggeringPolicy{");
        final TriggeringPolicy[] policies = this.policies;
        while (0 < policies.length) {
            final TriggeringPolicy triggeringPolicy = policies[0];
            if (!false) {
                sb.append(", ");
            }
            sb.append(triggeringPolicy.toString());
            int n = 0;
            ++n;
        }
        sb.append("}");
        return sb.toString();
    }
    
    @PluginFactory
    public static CompositeTriggeringPolicy createPolicy(@PluginElement("Policies") final TriggeringPolicy... array) {
        return new CompositeTriggeringPolicy(array);
    }
}
