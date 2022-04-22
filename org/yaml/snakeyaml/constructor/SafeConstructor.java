package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.*;
import java.math.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.nodes.*;
import java.util.*;
import java.util.regex.*;
import org.yaml.snakeyaml.external.biz.base64Coder.*;

public class SafeConstructor extends BaseConstructor
{
    public static final ConstructUndefined undefinedConstructor;
    private static final Map BOOL_VALUES;
    private static final int[][] RADIX_MAX;
    private static final Pattern TIMESTAMP_REGEXP;
    private static final Pattern YMD_REGEXP;
    
    public SafeConstructor() {
        this(new LoaderOptions());
    }
    
    public SafeConstructor(final LoaderOptions loaderOptions) {
        super(loaderOptions);
        this.yamlConstructors.put(Tag.NULL, new ConstructYamlNull());
        this.yamlConstructors.put(Tag.BOOL, new ConstructYamlBool());
        this.yamlConstructors.put(Tag.INT, new ConstructYamlInt());
        this.yamlConstructors.put(Tag.FLOAT, new ConstructYamlFloat());
        this.yamlConstructors.put(Tag.BINARY, new ConstructYamlBinary());
        this.yamlConstructors.put(Tag.TIMESTAMP, new ConstructYamlTimestamp());
        this.yamlConstructors.put(Tag.OMAP, new ConstructYamlOmap());
        this.yamlConstructors.put(Tag.PAIRS, new ConstructYamlPairs());
        this.yamlConstructors.put(Tag.SET, new ConstructYamlSet());
        this.yamlConstructors.put(Tag.STR, new ConstructYamlStr());
        this.yamlConstructors.put(Tag.SEQ, new ConstructYamlSeq());
        this.yamlConstructors.put(Tag.MAP, new ConstructYamlMap());
        this.yamlConstructors.put(null, SafeConstructor.undefinedConstructor);
        this.yamlClassConstructors.put(NodeId.scalar, SafeConstructor.undefinedConstructor);
        this.yamlClassConstructors.put(NodeId.sequence, SafeConstructor.undefinedConstructor);
        this.yamlClassConstructors.put(NodeId.mapping, SafeConstructor.undefinedConstructor);
    }
    
    protected void flattenMapping(final MappingNode mappingNode) {
        this.processDuplicateKeys(mappingNode);
        if (mappingNode.isMerged()) {
            mappingNode.setValue(this.mergeNode(mappingNode, true, new HashMap(), new ArrayList()));
        }
    }
    
    protected void processDuplicateKeys(final MappingNode mappingNode) {
        final List value = mappingNode.getValue();
        final HashMap hashMap = new HashMap<Object, Integer>(value.size());
        final TreeSet<Integer> set = new TreeSet<Integer>();
        for (final NodeTuple nodeTuple : value) {
            final Node keyNode = nodeTuple.getKeyNode();
            if (!keyNode.getTag().equals(Tag.MERGE)) {
                final Object constructObject = this.constructObject(keyNode);
                if (constructObject != null) {
                    constructObject.hashCode();
                }
                final Integer n = hashMap.put(constructObject, Integer.valueOf(0));
                if (n == null) {
                    continue;
                }
                if (!this.isAllowDuplicateKeys()) {
                    throw new DuplicateKeyException(mappingNode.getStartMark(), constructObject, nodeTuple.getKeyNode().getStartMark());
                }
                set.add(n);
            }
        }
        final Iterator<Integer> descendingIterator = set.descendingIterator();
        while (descendingIterator.hasNext()) {
            value.remove((int)descendingIterator.next());
        }
    }
    
    private List mergeNode(final MappingNode mappingNode, final boolean b, final Map map, final List list) {
        final Iterator<NodeTuple> iterator = (Iterator<NodeTuple>)mappingNode.getValue().iterator();
        while (iterator.hasNext()) {
            final NodeTuple nodeTuple = iterator.next();
            final Node keyNode = nodeTuple.getKeyNode();
            final Node valueNode = nodeTuple.getValueNode();
            if (keyNode.getTag().equals(Tag.MERGE)) {
                iterator.remove();
                switch (valueNode.getNodeId()) {
                    case mapping: {
                        this.mergeNode((MappingNode)valueNode, false, map, list);
                        continue;
                    }
                    case sequence: {
                        for (final Node node : ((SequenceNode)valueNode).getValue()) {
                            if (!(node instanceof MappingNode)) {
                                throw new ConstructorException("while constructing a mapping", mappingNode.getStartMark(), "expected a mapping for merging, but found " + node.getNodeId(), node.getStartMark());
                            }
                            this.mergeNode((MappingNode)node, false, map, list);
                        }
                        continue;
                    }
                    default: {
                        throw new ConstructorException("while constructing a mapping", mappingNode.getStartMark(), "expected a mapping or list of mappings for merging, but found " + valueNode.getNodeId(), valueNode.getStartMark());
                    }
                }
            }
            else {
                final Object constructObject = this.constructObject(keyNode);
                if (!map.containsKey(constructObject)) {
                    list.add(nodeTuple);
                    map.put(constructObject, list.size() - 1);
                }
                else {
                    if (!b) {
                        continue;
                    }
                    list.set(map.get(constructObject), nodeTuple);
                }
            }
        }
        return list;
    }
    
    @Override
    protected void constructMapping2ndStep(final MappingNode mappingNode, final Map map) {
        this.flattenMapping(mappingNode);
        super.constructMapping2ndStep(mappingNode, map);
    }
    
    @Override
    protected void constructSet2ndStep(final MappingNode mappingNode, final Set set) {
        this.flattenMapping(mappingNode);
        super.constructSet2ndStep(mappingNode, set);
    }
    
    private static int maxLen(final int n, final int n2) {
        return Integer.toString(n, n2).length();
    }
    
    private static int maxLen(final long n, final int n2) {
        return Long.toString(n, n2).length();
    }
    
    private Number createNumber(final int n, String string, final int n2) {
        final int n3 = (string != null) ? string.length() : 0;
        if (n < 0) {
            string = "-" + string;
        }
        final int[] array = (int[])((n2 < SafeConstructor.RADIX_MAX.length) ? SafeConstructor.RADIX_MAX[n2] : null);
        if (array == null || n3 <= array[0]) {
            return Integer.valueOf(string, n2);
        }
        if (n3 > array[1]) {
            return new BigInteger(string, n2);
        }
        return createLongOrBigInteger(string, n2);
    }
    
    protected static Number createLongOrBigInteger(final String s, final int n) {
        return Long.valueOf(s, n);
    }
    
    static Map access$000() {
        return SafeConstructor.BOOL_VALUES;
    }
    
    static Number access$100(final SafeConstructor safeConstructor, final int n, final String s, final int n2) {
        return safeConstructor.createNumber(n, s, n2);
    }
    
    static Pattern access$200() {
        return SafeConstructor.YMD_REGEXP;
    }
    
    static Pattern access$300() {
        return SafeConstructor.TIMESTAMP_REGEXP;
    }
    
    static {
        undefinedConstructor = new ConstructUndefined();
        (BOOL_VALUES = new HashMap()).put("yes", Boolean.TRUE);
        SafeConstructor.BOOL_VALUES.put("no", Boolean.FALSE);
        SafeConstructor.BOOL_VALUES.put("true", Boolean.TRUE);
        SafeConstructor.BOOL_VALUES.put("false", Boolean.FALSE);
        SafeConstructor.BOOL_VALUES.put("on", Boolean.TRUE);
        SafeConstructor.BOOL_VALUES.put("off", Boolean.FALSE);
        RADIX_MAX = new int[17][2];
        final int[] array = { 2, 8, 10, 16 };
        while (0 < array.length) {
            final int n = array[0];
            SafeConstructor.RADIX_MAX[n] = new int[] { maxLen(Integer.MAX_VALUE, n), maxLen(Long.MAX_VALUE, n) };
            int n2 = 0;
            ++n2;
        }
        TIMESTAMP_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)(?:(?:[Tt]|[ \t]+)([0-9][0-9]?):([0-9][0-9]):([0-9][0-9])(?:\\.([0-9]*))?(?:[ \t]*(?:Z|([-+][0-9][0-9]?)(?::([0-9][0-9])?)?))?)?$");
        YMD_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)$");
    }
    
    public static final class ConstructUndefined extends AbstractConstruct
    {
        @Override
        public Object construct(final Node node) {
            throw new ConstructorException(null, null, "could not determine a constructor for the tag " + node.getTag(), node.getStartMark());
        }
    }
    
    public class ConstructYamlMap implements Construct
    {
        final SafeConstructor this$0;
        
        public ConstructYamlMap(final SafeConstructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            final MappingNode mappingNode = (MappingNode)node;
            if (node.isTwoStepsConstruction()) {
                return this.this$0.createDefaultMap(mappingNode.getValue().size());
            }
            return this.this$0.constructMapping(mappingNode);
        }
        
        @Override
        public void construct2ndStep(final Node node, final Object o) {
            if (node.isTwoStepsConstruction()) {
                this.this$0.constructMapping2ndStep((MappingNode)node, (Map)o);
                return;
            }
            throw new YAMLException("Unexpected recursive mapping structure. Node: " + node);
        }
    }
    
    public class ConstructYamlSeq implements Construct
    {
        final SafeConstructor this$0;
        
        public ConstructYamlSeq(final SafeConstructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            final SequenceNode sequenceNode = (SequenceNode)node;
            if (node.isTwoStepsConstruction()) {
                return this.this$0.newList(sequenceNode);
            }
            return this.this$0.constructSequence(sequenceNode);
        }
        
        @Override
        public void construct2ndStep(final Node node, final Object o) {
            if (node.isTwoStepsConstruction()) {
                this.this$0.constructSequenceStep2((SequenceNode)node, (Collection)o);
                return;
            }
            throw new YAMLException("Unexpected recursive sequence structure. Node: " + node);
        }
    }
    
    public class ConstructYamlStr extends AbstractConstruct
    {
        final SafeConstructor this$0;
        
        public ConstructYamlStr(final SafeConstructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            return this.this$0.constructScalar((ScalarNode)node);
        }
    }
    
    public class ConstructYamlSet implements Construct
    {
        final SafeConstructor this$0;
        
        public ConstructYamlSet(final SafeConstructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            if (node.isTwoStepsConstruction()) {
                return this.this$0.constructedObjects.containsKey(node) ? this.this$0.constructedObjects.get(node) : this.this$0.createDefaultSet(((MappingNode)node).getValue().size());
            }
            return this.this$0.constructSet((MappingNode)node);
        }
        
        @Override
        public void construct2ndStep(final Node node, final Object o) {
            if (node.isTwoStepsConstruction()) {
                this.this$0.constructSet2ndStep((MappingNode)node, (Set)o);
                return;
            }
            throw new YAMLException("Unexpected recursive set structure. Node: " + node);
        }
    }
    
    public class ConstructYamlPairs extends AbstractConstruct
    {
        final SafeConstructor this$0;
        
        public ConstructYamlPairs(final SafeConstructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            if (!(node instanceof SequenceNode)) {
                throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a sequence, but found " + node.getNodeId(), node.getStartMark());
            }
            final SequenceNode sequenceNode = (SequenceNode)node;
            final ArrayList list = new ArrayList<Object[]>(sequenceNode.getValue().size());
            for (final Node node2 : sequenceNode.getValue()) {
                if (!(node2 instanceof MappingNode)) {
                    throw new ConstructorException("while constructingpairs", node.getStartMark(), "expected a mapping of length 1, but found " + node2.getNodeId(), node2.getStartMark());
                }
                final MappingNode mappingNode = (MappingNode)node2;
                if (mappingNode.getValue().size() != 1) {
                    throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a single mapping item, but found " + mappingNode.getValue().size() + " items", mappingNode.getStartMark());
                }
                list.add(new Object[] { this.this$0.constructObject(((NodeTuple)mappingNode.getValue().get(0)).getKeyNode()), this.this$0.constructObject(((NodeTuple)mappingNode.getValue().get(0)).getValueNode()) });
            }
            return list;
        }
    }
    
    public class ConstructYamlOmap extends AbstractConstruct
    {
        final SafeConstructor this$0;
        
        public ConstructYamlOmap(final SafeConstructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            final LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
            if (!(node instanceof SequenceNode)) {
                throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a sequence, but found " + node.getNodeId(), node.getStartMark());
            }
            for (final Node node2 : ((SequenceNode)node).getValue()) {
                if (!(node2 instanceof MappingNode)) {
                    throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a mapping of length 1, but found " + node2.getNodeId(), node2.getStartMark());
                }
                final MappingNode mappingNode = (MappingNode)node2;
                if (mappingNode.getValue().size() != 1) {
                    throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a single mapping item, but found " + mappingNode.getValue().size() + " items", mappingNode.getStartMark());
                }
                linkedHashMap.put(this.this$0.constructObject(((NodeTuple)mappingNode.getValue().get(0)).getKeyNode()), this.this$0.constructObject(((NodeTuple)mappingNode.getValue().get(0)).getValueNode()));
            }
            return linkedHashMap;
        }
    }
    
    public static class ConstructYamlTimestamp extends AbstractConstruct
    {
        private Calendar calendar;
        
        public Calendar getCalendar() {
            return this.calendar;
        }
        
        @Override
        public Object construct(final Node node) {
            final String value = ((ScalarNode)node).getValue();
            final Matcher matcher = SafeConstructor.access$200().matcher(value);
            if (matcher.matches()) {
                final String group = matcher.group(1);
                final String group2 = matcher.group(2);
                final String group3 = matcher.group(3);
                (this.calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))).clear();
                this.calendar.set(1, Integer.parseInt(group));
                this.calendar.set(2, Integer.parseInt(group2) - 1);
                this.calendar.set(5, Integer.parseInt(group3));
                return this.calendar.getTime();
            }
            final Matcher matcher2 = SafeConstructor.access$300().matcher(value);
            if (!matcher2.matches()) {
                throw new YAMLException("Unexpected timestamp: " + value);
            }
            final String group4 = matcher2.group(1);
            final String group5 = matcher2.group(2);
            final String group6 = matcher2.group(3);
            final String group7 = matcher2.group(4);
            final String group8 = matcher2.group(5);
            String s = matcher2.group(6);
            final String group9 = matcher2.group(7);
            if (group9 != null) {
                s = s + "." + group9;
            }
            final double double1 = Double.parseDouble(s);
            final int n = (int)Math.round(Math.floor(double1));
            final int n2 = (int)Math.round((double1 - n) * 1000.0);
            final String group10 = matcher2.group(8);
            final String group11 = matcher2.group(9);
            TimeZone timeZone;
            if (group10 != null) {
                timeZone = TimeZone.getTimeZone("GMT" + group10 + ((group11 != null) ? (":" + group11) : "00"));
            }
            else {
                timeZone = TimeZone.getTimeZone("UTC");
            }
            (this.calendar = Calendar.getInstance(timeZone)).set(1, Integer.parseInt(group4));
            this.calendar.set(2, Integer.parseInt(group5) - 1);
            this.calendar.set(5, Integer.parseInt(group6));
            this.calendar.set(11, Integer.parseInt(group7));
            this.calendar.set(12, Integer.parseInt(group8));
            this.calendar.set(13, n);
            this.calendar.set(14, n2);
            return this.calendar.getTime();
        }
    }
    
    public class ConstructYamlBinary extends AbstractConstruct
    {
        final SafeConstructor this$0;
        
        public ConstructYamlBinary(final SafeConstructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            return Base64Coder.decode(this.this$0.constructScalar((ScalarNode)node).toString().replaceAll("\\s", "").toCharArray());
        }
    }
    
    public class ConstructYamlFloat extends AbstractConstruct
    {
        final SafeConstructor this$0;
        
        public ConstructYamlFloat(final SafeConstructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            String s = this.this$0.constructScalar((ScalarNode)node).toString().replaceAll("_", "");
            final char char1 = s.charAt(0);
            if (char1 == '-') {
                s = s.substring(1);
            }
            else if (char1 == '+') {
                s = s.substring(1);
            }
            final String lowerCase = s.toLowerCase();
            if (".inf".equals(lowerCase)) {
                return Double.NEGATIVE_INFINITY;
            }
            if (".nan".equals(lowerCase)) {
                return Double.NaN;
            }
            if (s.indexOf(58) != -1) {
                final String[] split = s.split(":");
                double n = 0.0;
                final int length = split.length;
                while (0 < length) {
                    n += Double.parseDouble(split[length - 0 - 1]) * 1;
                    int n2 = 0;
                    ++n2;
                }
                return -1 * n;
            }
            return Double.valueOf(s) * -1;
        }
    }
    
    public class ConstructYamlInt extends AbstractConstruct
    {
        final SafeConstructor this$0;
        
        public ConstructYamlInt(final SafeConstructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            String s = this.this$0.constructScalar((ScalarNode)node).toString().replaceAll("_", "");
            final char char1 = s.charAt(0);
            if (char1 == '-') {
                s = s.substring(1);
            }
            else if (char1 == '+') {
                s = s.substring(1);
            }
            if ("0".equals(s)) {
                return 0;
            }
            String s2;
            if (s.startsWith("0b")) {
                s2 = s.substring(2);
            }
            else if (s.startsWith("0x")) {
                s2 = s.substring(2);
            }
            else if (s.startsWith("0")) {
                s2 = s.substring(1);
            }
            else {
                if (s.indexOf(58) != -1) {
                    final String[] split = s.split(":");
                    final int length = split.length;
                    while (0 < length) {
                        final int n = (int)(0 + Long.parseLong(split[length - 0 - 1]) * 1);
                        int n2 = 0;
                        ++n2;
                    }
                    return SafeConstructor.access$100(this.this$0, -1, String.valueOf(0), 10);
                }
                return SafeConstructor.access$100(this.this$0, -1, s, 10);
            }
            return SafeConstructor.access$100(this.this$0, -1, s2, 8);
        }
    }
    
    public class ConstructYamlBool extends AbstractConstruct
    {
        final SafeConstructor this$0;
        
        public ConstructYamlBool(final SafeConstructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            return SafeConstructor.access$000().get(this.this$0.constructScalar((ScalarNode)node).toLowerCase());
        }
    }
    
    public class ConstructYamlNull extends AbstractConstruct
    {
        final SafeConstructor this$0;
        
        public ConstructYamlNull(final SafeConstructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            if (node != null) {
                this.this$0.constructScalar((ScalarNode)node);
            }
            return null;
        }
    }
}
