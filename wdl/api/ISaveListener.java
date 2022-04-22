package wdl.api;

import java.io.*;

public interface ISaveListener extends IWDLMod
{
    void afterChunksSaved(final File p0);
}
