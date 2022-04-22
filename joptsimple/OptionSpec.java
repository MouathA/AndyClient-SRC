package joptsimple;

import java.util.*;

public interface OptionSpec
{
    List values(final OptionSet p0);
    
    Object value(final OptionSet p0);
    
    Collection options();
    
    boolean isForHelp();
}
