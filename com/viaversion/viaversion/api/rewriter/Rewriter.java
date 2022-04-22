package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.protocol.*;

public interface Rewriter
{
    void register();
    
    Protocol protocol();
}
