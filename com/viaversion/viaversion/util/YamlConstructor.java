package com.viaversion.viaversion.util;

import org.yaml.snakeyaml.constructor.*;
import org.yaml.snakeyaml.nodes.*;
import java.util.concurrent.*;
import java.util.*;

public class YamlConstructor extends SafeConstructor
{
    public YamlConstructor() {
        this.yamlClassConstructors.put(NodeId.mapping, new ConstructYamlMap());
        this.yamlConstructors.put(Tag.OMAP, new ConstructYamlOmap());
    }
    
    class ConstructYamlOmap extends SafeConstructor.ConstructYamlOmap
    {
        final YamlConstructor this$0;
        
        ConstructYamlOmap(final YamlConstructor this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public Object construct(final Node node) {
            final Object construct = super.construct(node);
            if (construct instanceof Map && !(construct instanceof ConcurrentSkipListMap)) {
                return new ConcurrentSkipListMap((java.util.Map<?, ?>)construct);
            }
            return construct;
        }
    }
    
    class Map extends ConstructYamlMap
    {
        final YamlConstructor this$0;
        
        Map(final YamlConstructor this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public Object construct(final Node node) {
            final Object construct = super.construct(node);
            if (construct instanceof Map && !(construct instanceof ConcurrentSkipListMap)) {
                return new ConcurrentSkipListMap((java.util.Map<?, ?>)construct);
            }
            return construct;
        }
    }
}
