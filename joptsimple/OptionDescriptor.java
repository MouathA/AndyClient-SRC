package joptsimple;

import java.util.*;

public interface OptionDescriptor
{
    Collection options();
    
    String description();
    
    List defaultValues();
    
    boolean isRequired();
    
    boolean acceptsArguments();
    
    boolean requiresArgument();
    
    String argumentDescription();
    
    String argumentTypeIndicator();
    
    boolean representsNonOptions();
}
