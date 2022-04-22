package com.google.common.hash;

import java.io.*;
import com.google.common.annotations.*;

@Beta
public interface Funnel extends Serializable
{
    void funnel(final Object p0, final PrimitiveSink p1);
}
