package org.apache.commons.codec.language.bm;

import org.apache.commons.codec.*;

public class BeiderMorseEncoder implements StringEncoder
{
    private PhoneticEngine engine;
    
    public BeiderMorseEncoder() {
        this.engine = new PhoneticEngine(NameType.GENERIC, RuleType.APPROX, true);
    }
    
    @Override
    public Object encode(final Object o) throws EncoderException {
        if (!(o instanceof String)) {
            throw new EncoderException("BeiderMorseEncoder encode parameter is not of type String");
        }
        return this.encode((String)o);
    }
    
    @Override
    public String encode(final String s) throws EncoderException {
        if (s == null) {
            return null;
        }
        return this.engine.encode(s);
    }
    
    public NameType getNameType() {
        return this.engine.getNameType();
    }
    
    public RuleType getRuleType() {
        return this.engine.getRuleType();
    }
    
    public boolean isConcat() {
        return this.engine.isConcat();
    }
    
    public void setConcat(final boolean b) {
        this.engine = new PhoneticEngine(this.engine.getNameType(), this.engine.getRuleType(), b, this.engine.getMaxPhonemes());
    }
    
    public void setNameType(final NameType nameType) {
        this.engine = new PhoneticEngine(nameType, this.engine.getRuleType(), this.engine.isConcat(), this.engine.getMaxPhonemes());
    }
    
    public void setRuleType(final RuleType ruleType) {
        this.engine = new PhoneticEngine(this.engine.getNameType(), ruleType, this.engine.isConcat(), this.engine.getMaxPhonemes());
    }
    
    public void setMaxPhonemes(final int n) {
        this.engine = new PhoneticEngine(this.engine.getNameType(), this.engine.getRuleType(), this.engine.isConcat(), n);
    }
}
