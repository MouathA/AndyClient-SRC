package joptsimple;

import java.util.*;

public interface OptionDeclarer
{
    OptionSpecBuilder accepts(final String p0);
    
    OptionSpecBuilder accepts(final String p0, final String p1);
    
    OptionSpecBuilder acceptsAll(final Collection p0);
    
    OptionSpecBuilder acceptsAll(final Collection p0, final String p1);
    
    NonOptionArgumentSpec nonOptions();
    
    NonOptionArgumentSpec nonOptions(final String p0);
    
    void posixlyCorrect(final boolean p0);
    
    void allowsUnrecognizedOptions();
    
    void recognizeAlternativeLongOptions(final boolean p0);
}
