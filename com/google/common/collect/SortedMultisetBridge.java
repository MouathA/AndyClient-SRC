package com.google.common.collect;

import java.util.*;

interface SortedMultisetBridge extends Multiset
{
    SortedSet elementSet();
}
