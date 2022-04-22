package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;

@GwtCompatible
interface FilteredMultimap extends Multimap
{
    Multimap unfiltered();
    
    Predicate entryPredicate();
}
