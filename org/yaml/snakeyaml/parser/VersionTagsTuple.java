package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.*;
import java.util.*;

class VersionTagsTuple
{
    private DumperOptions.Version version;
    private Map tags;
    
    public VersionTagsTuple(final DumperOptions.Version version, final Map tags) {
        this.version = version;
        this.tags = tags;
    }
    
    public DumperOptions.Version getVersion() {
        return this.version;
    }
    
    public Map getTags() {
        return this.tags;
    }
    
    @Override
    public String toString() {
        return String.format("VersionTagsTuple<%s, %s>", this.version, this.tags);
    }
}
