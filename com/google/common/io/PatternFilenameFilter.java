package com.google.common.io;

import com.google.common.annotations.*;
import java.util.regex.*;
import com.google.common.base.*;
import java.io.*;
import javax.annotation.*;

@Beta
public final class PatternFilenameFilter implements FilenameFilter
{
    private final Pattern pattern;
    
    public PatternFilenameFilter(final String s) {
        this(Pattern.compile(s));
    }
    
    public PatternFilenameFilter(final Pattern pattern) {
        this.pattern = (Pattern)Preconditions.checkNotNull(pattern);
    }
    
    @Override
    public boolean accept(@Nullable final File file, final String s) {
        return this.pattern.matcher(s).matches();
    }
}
