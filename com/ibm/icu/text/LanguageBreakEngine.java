package com.ibm.icu.text;

import java.text.*;
import java.util.*;

interface LanguageBreakEngine
{
    boolean handles(final int p0, final int p1);
    
    int findBreaks(final CharacterIterator p0, final int p1, final int p2, final boolean p3, final int p4, final Stack p5);
}
