package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.*;

public final class LiteralPatternConverter extends LogEventPatternConverter implements ArrayPatternConverter
{
    private final String literal;
    private final Configuration config;
    private final boolean substitute;
    
    public LiteralPatternConverter(final Configuration config, final String literal) {
        super("Literal", "literal");
        this.literal = literal;
        this.config = config;
        this.substitute = (config != null && literal.contains("${"));
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        sb.append(this.substitute ? this.config.getStrSubstitutor().replace(logEvent, this.literal) : this.literal);
    }
    
    @Override
    public void format(final Object o, final StringBuilder sb) {
        sb.append(this.substitute ? this.config.getStrSubstitutor().replace(this.literal) : this.literal);
    }
    
    @Override
    public void format(final StringBuilder sb, final Object... array) {
        sb.append(this.substitute ? this.config.getStrSubstitutor().replace(this.literal) : this.literal);
    }
    
    public String getLiteral() {
        return this.literal;
    }
}
