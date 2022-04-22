package org.apache.commons.compress.archivers.sevenz;

import java.util.*;

class Archive
{
    long packPos;
    long[] packSizes;
    BitSet packCrcsDefined;
    long[] packCrcs;
    Folder[] folders;
    SubStreamsInfo subStreamsInfo;
    SevenZArchiveEntry[] files;
    StreamMap streamMap;
}
