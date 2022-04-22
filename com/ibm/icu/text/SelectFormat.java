package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.text.*;
import java.io.*;

public class SelectFormat extends Format
{
    private static final long serialVersionUID = 2993154333257524984L;
    private String pattern;
    private transient MessagePattern msgPattern;
    static final boolean $assertionsDisabled;
    
    public SelectFormat(final String s) {
        this.pattern = null;
        this.applyPattern(s);
    }
    
    private void reset() {
        this.pattern = null;
        if (this.msgPattern != null) {
            this.msgPattern.clear();
        }
    }
    
    public void applyPattern(final String pattern) {
        this.pattern = pattern;
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        this.msgPattern.parseSelectStyle(pattern);
    }
    
    public String toPattern() {
        return this.pattern;
    }
    
    static int findSubMessage(final MessagePattern messagePattern, int limitPartIndex, final String s) {
        do {
            final MessagePattern.Part part = messagePattern.getPart(limitPartIndex++);
            final MessagePattern.Part.Type type = part.getType();
            if (type == MessagePattern.Part.Type.ARG_LIMIT) {
                break;
            }
            assert type == MessagePattern.Part.Type.ARG_SELECTOR;
            if (messagePattern.partSubstringMatches(part, s)) {
                return limitPartIndex;
            }
            if (!false && messagePattern.partSubstringMatches(part, "other")) {}
            limitPartIndex = messagePattern.getLimitPartIndex(limitPartIndex);
        } while (++limitPartIndex < messagePattern.countParts());
        return 0;
    }
    
    public final String format(final String s) {
        if (!PatternProps.isIdentifier(s)) {
            throw new IllegalArgumentException("Invalid formatting argument.");
        }
        if (this.msgPattern == null || this.msgPattern.countParts() == 0) {
            throw new IllegalStateException("Invalid format error.");
        }
        final int subMessage = findSubMessage(this.msgPattern, 0, s);
        if (!this.msgPattern.jdkAposMode()) {
            return this.msgPattern.getPatternString().substring(this.msgPattern.getPart(subMessage).getLimit(), this.msgPattern.getPatternIndex(this.msgPattern.getLimitPartIndex(subMessage)));
        }
        StringBuilder sb = null;
        int n = this.msgPattern.getPart(subMessage).getLimit();
        int limitPartIndex = subMessage;
        int index;
        while (true) {
            final MessagePattern.Part part = this.msgPattern.getPart(++limitPartIndex);
            final MessagePattern.Part.Type type = part.getType();
            index = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                break;
            }
            if (type == MessagePattern.Part.Type.SKIP_SYNTAX) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(this.pattern, n, index);
                n = part.getLimit();
            }
            else {
                if (type != MessagePattern.Part.Type.ARG_START) {
                    continue;
                }
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(this.pattern, n, index);
                final int n2 = index;
                limitPartIndex = this.msgPattern.getLimitPartIndex(limitPartIndex);
                final int limit = this.msgPattern.getPart(limitPartIndex).getLimit();
                MessagePattern.appendReducedApostrophes(this.pattern, n2, limit, sb);
                n = limit;
            }
        }
        if (sb == null) {
            return this.pattern.substring(n, index);
        }
        return sb.append(this.pattern, n, index).toString();
    }
    
    @Override
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (o instanceof String) {
            sb.append(this.format((String)o));
            return sb;
        }
        throw new IllegalArgumentException("'" + o + "' is not a String");
    }
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final SelectFormat selectFormat = (SelectFormat)o;
        return (this.msgPattern == null) ? (selectFormat.msgPattern == null) : this.msgPattern.equals(selectFormat.msgPattern);
    }
    
    @Override
    public int hashCode() {
        if (this.pattern != null) {
            return this.pattern.hashCode();
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return "pattern='" + this.pattern + "'";
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.pattern != null) {
            this.applyPattern(this.pattern);
        }
    }
    
    static {
        $assertionsDisabled = !SelectFormat.class.desiredAssertionStatus();
    }
}
