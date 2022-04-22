package org.yaml.snakeyaml.resolver;

import java.util.regex.*;
import org.yaml.snakeyaml.nodes.*;
import java.util.*;

public class Resolver
{
    public static final Pattern BOOL;
    public static final Pattern FLOAT;
    public static final Pattern INT;
    public static final Pattern MERGE;
    public static final Pattern NULL;
    public static final Pattern EMPTY;
    public static final Pattern TIMESTAMP;
    public static final Pattern VALUE;
    public static final Pattern YAML;
    protected Map yamlImplicitResolvers;
    
    protected void addImplicitResolvers() {
        this.addImplicitResolver(Tag.BOOL, Resolver.BOOL, "yYnNtTfFoO");
        this.addImplicitResolver(Tag.INT, Resolver.INT, "-+0123456789");
        this.addImplicitResolver(Tag.FLOAT, Resolver.FLOAT, "-+0123456789.");
        this.addImplicitResolver(Tag.MERGE, Resolver.MERGE, "<");
        this.addImplicitResolver(Tag.NULL, Resolver.NULL, "~nN\u0000");
        this.addImplicitResolver(Tag.NULL, Resolver.EMPTY, null);
        this.addImplicitResolver(Tag.TIMESTAMP, Resolver.TIMESTAMP, "0123456789");
        this.addImplicitResolver(Tag.YAML, Resolver.YAML, "!&*");
    }
    
    public Resolver() {
        this.yamlImplicitResolvers = new HashMap();
        this.addImplicitResolvers();
    }
    
    public void addImplicitResolver(final Tag tag, final Pattern pattern, final String s) {
        if (s == null) {
            Object o = this.yamlImplicitResolvers.get(null);
            if (o == null) {
                o = new ArrayList<ResolverTuple>();
                this.yamlImplicitResolvers.put(null, o);
            }
            ((List<ResolverTuple>)o).add(new ResolverTuple(tag, pattern));
        }
        else {
            final char[] charArray = s.toCharArray();
            while (0 < charArray.length) {
                Character value = charArray[0];
                if (value == '\0') {
                    value = null;
                }
                List<?> list = this.yamlImplicitResolvers.get(value);
                if (list == null) {
                    list = new ArrayList<Object>();
                    this.yamlImplicitResolvers.put(value, list);
                }
                list.add(new ResolverTuple(tag, pattern));
                int n = 0;
                ++n;
            }
        }
    }
    
    public Tag resolve(final NodeId nodeId, final String s, final boolean b) {
        if (nodeId == NodeId.scalar && b) {
            List<ResolverTuple> list;
            if (s.length() == 0) {
                list = this.yamlImplicitResolvers.get('\0');
            }
            else {
                list = this.yamlImplicitResolvers.get(s.charAt(0));
            }
            if (list != null) {
                for (final ResolverTuple resolverTuple : list) {
                    final Tag tag = resolverTuple.getTag();
                    if (resolverTuple.getRegexp().matcher(s).matches()) {
                        return tag;
                    }
                }
            }
            if (this.yamlImplicitResolvers.containsKey(null)) {
                for (final ResolverTuple resolverTuple2 : this.yamlImplicitResolvers.get(null)) {
                    final Tag tag2 = resolverTuple2.getTag();
                    if (resolverTuple2.getRegexp().matcher(s).matches()) {
                        return tag2;
                    }
                }
            }
        }
        switch (nodeId) {
            case scalar: {
                return Tag.STR;
            }
            case sequence: {
                return Tag.SEQ;
            }
            default: {
                return Tag.MAP;
            }
        }
    }
    
    static {
        BOOL = Pattern.compile("^(?:yes|Yes|YES|no|No|NO|true|True|TRUE|false|False|FALSE|on|On|ON|off|Off|OFF)$");
        FLOAT = Pattern.compile("^([-+]?(\\.[0-9]+|[0-9_]+(\\.[0-9_]*)?)([eE][-+]?[0-9]+)?|[-+]?[0-9][0-9_]*(?::[0-5]?[0-9])+\\.[0-9_]*|[-+]?\\.(?:inf|Inf|INF)|\\.(?:nan|NaN|NAN))$");
        INT = Pattern.compile("^(?:[-+]?0b[0-1_]+|[-+]?0[0-7_]+|[-+]?(?:0|[1-9][0-9_]*)|[-+]?0x[0-9a-fA-F_]+|[-+]?[1-9][0-9_]*(?::[0-5]?[0-9])+)$");
        MERGE = Pattern.compile("^(?:<<)$");
        NULL = Pattern.compile("^(?:~|null|Null|NULL| )$");
        EMPTY = Pattern.compile("^$");
        TIMESTAMP = Pattern.compile("^(?:[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]|[0-9][0-9][0-9][0-9]-[0-9][0-9]?-[0-9][0-9]?(?:[Tt]|[ \t]+)[0-9][0-9]?:[0-9][0-9]:[0-9][0-9](?:\\.[0-9]*)?(?:[ \t]*(?:Z|[-+][0-9][0-9]?(?::[0-9][0-9])?))?)$");
        VALUE = Pattern.compile("^(?:=)$");
        YAML = Pattern.compile("^(?:!|&|\\*)$");
    }
}
