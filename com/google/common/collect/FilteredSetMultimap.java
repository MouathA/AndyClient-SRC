package com.google.common.collect;

import com.google.common.annotations.*;

@GwtCompatible
interface FilteredSetMultimap extends FilteredMultimap, SetMultimap
{
    SetMultimap unfiltered();
}
