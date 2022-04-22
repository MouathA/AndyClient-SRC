package org.apache.commons.lang3.text;

import java.text.*;

public class CompositeFormat extends Format
{
    private static final long serialVersionUID = -4329119827877627683L;
    private final Format parser;
    private final Format formatter;
    
    public CompositeFormat(final Format parser, final Format formatter) {
        this.parser = parser;
        this.formatter = formatter;
    }
    
    @Override
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.formatter.format(o, sb, fieldPosition);
    }
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        return this.parser.parseObject(s, parsePosition);
    }
    
    public Format getParser() {
        return this.parser;
    }
    
    public Format getFormatter() {
        return this.formatter;
    }
    
    public String reformat(final String s) throws ParseException {
        return this.format(this.parseObject(s));
    }
}
