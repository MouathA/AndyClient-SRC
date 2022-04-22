package org.yaml.snakeyaml.extensions.compactnotation;

import org.yaml.snakeyaml.constructor.*;
import java.util.regex.*;
import org.yaml.snakeyaml.introspector.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;
import org.yaml.snakeyaml.nodes.*;

public class CompactConstructor extends Constructor
{
    private static final Pattern GUESS_COMPACT;
    private static final Pattern FIRST_PATTERN;
    private static final Pattern PROPERTY_NAME_PATTERN;
    private Construct compactConstruct;
    
    protected Object constructCompactFormat(final ScalarNode scalarNode, final CompactData compactData) {
        final Object instance = this.createInstance(scalarNode, compactData);
        this.setProperties(instance, new HashMap(compactData.getProperties()));
        return instance;
    }
    
    protected Object createInstance(final ScalarNode scalarNode, final CompactData compactData) throws Exception {
        final Class classForName = this.getClassForName(compactData.getPrefix());
        final Class[] array = new Class[compactData.getArguments().size()];
        while (0 < array.length) {
            array[0] = String.class;
            int n = 0;
            ++n;
        }
        final java.lang.reflect.Constructor<Object> declaredConstructor = classForName.getDeclaredConstructor((Class<?>[])array);
        declaredConstructor.setAccessible(true);
        return declaredConstructor.newInstance(compactData.getArguments().toArray());
    }
    
    protected void setProperties(final Object o, final Map map) throws Exception {
        if (map == null) {
            throw new NullPointerException("Data for Compact Object Notation cannot be null.");
        }
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            this.getPropertyUtils().getProperty(o.getClass(), entry.getKey()).set(o, entry.getValue());
        }
    }
    
    public CompactData getCompactData(final String s) {
        if (!s.endsWith(")")) {
            return null;
        }
        if (s.indexOf(40) < 0) {
            return null;
        }
        final Matcher matcher = CompactConstructor.FIRST_PATTERN.matcher(s);
        if (!matcher.matches()) {
            return null;
        }
        final String trim = matcher.group(1).trim();
        final String group = matcher.group(3);
        final CompactData compactData = new CompactData(trim);
        if (group.length() == 0) {
            return compactData;
        }
        final String[] split = group.split("\\s*,\\s*");
        while (0 < split.length) {
            final String s2 = split[0];
            if (s2.indexOf(61) < 0) {
                compactData.getArguments().add(s2);
            }
            else {
                final Matcher matcher2 = CompactConstructor.PROPERTY_NAME_PATTERN.matcher(s2);
                if (!matcher2.matches()) {
                    return null;
                }
                compactData.getProperties().put(matcher2.group(1), matcher2.group(2).trim());
            }
            int n = 0;
            ++n;
        }
        return compactData;
    }
    
    private Construct getCompactConstruct() {
        if (this.compactConstruct == null) {
            this.compactConstruct = this.createCompactConstruct();
        }
        return this.compactConstruct;
    }
    
    protected Construct createCompactConstruct() {
        return new ConstructCompactObject();
    }
    
    @Override
    protected Construct getConstructor(final Node node) {
        if (node instanceof MappingNode) {
            final List value = ((MappingNode)node).getValue();
            if (value.size() == 1) {
                final Node keyNode = value.get(0).getKeyNode();
                if (keyNode instanceof ScalarNode && CompactConstructor.GUESS_COMPACT.matcher(((ScalarNode)keyNode).getValue()).matches()) {
                    return this.getCompactConstruct();
                }
            }
        }
        else if (node instanceof ScalarNode && CompactConstructor.GUESS_COMPACT.matcher(((ScalarNode)node).getValue()).matches()) {
            return this.getCompactConstruct();
        }
        return super.getConstructor(node);
    }
    
    protected void applySequence(final Object o, final List list) {
        this.getPropertyUtils().getProperty(o.getClass(), this.getSequencePropertyName(o.getClass())).set(o, list);
    }
    
    protected String getSequencePropertyName(final Class clazz) {
        final Set properties = this.getPropertyUtils().getProperties(clazz);
        final Iterator<Property> iterator = properties.iterator();
        while (iterator.hasNext()) {
            if (!List.class.isAssignableFrom(iterator.next().getType())) {
                iterator.remove();
            }
        }
        if (properties.size() == 0) {
            throw new YAMLException("No list property found in " + clazz);
        }
        if (properties.size() > 1) {
            throw new YAMLException("Many list properties found in " + clazz + "; Please override getSequencePropertyName() to specify which property to use.");
        }
        return properties.iterator().next().getName();
    }
    
    static List access$000(final CompactConstructor compactConstructor, final SequenceNode sequenceNode) {
        return compactConstructor.constructSequence(sequenceNode);
    }
    
    static String access$100(final CompactConstructor compactConstructor, final ScalarNode scalarNode) {
        return compactConstructor.constructScalar(scalarNode);
    }
    
    static {
        GUESS_COMPACT = Pattern.compile("\\p{Alpha}.*\\s*\\((?:,?\\s*(?:(?:\\w*)|(?:\\p{Alpha}\\w*\\s*=.+))\\s*)+\\)");
        FIRST_PATTERN = Pattern.compile("(\\p{Alpha}.*)(\\s*)\\((.*?)\\)");
        PROPERTY_NAME_PATTERN = Pattern.compile("\\s*(\\p{Alpha}\\w*)\\s*=(.+)");
    }
    
    public class ConstructCompactObject extends ConstructMapping
    {
        final CompactConstructor this$0;
        
        public ConstructCompactObject(final CompactConstructor this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public void construct2ndStep(final Node node, final Object o) {
            final Node valueNode = ((MappingNode)node).getValue().iterator().next().getValueNode();
            if (valueNode instanceof MappingNode) {
                valueNode.setType(o.getClass());
                this.constructJavaBean2ndStep((MappingNode)valueNode, o);
            }
            else {
                this.this$0.applySequence(o, CompactConstructor.access$000(this.this$0, (SequenceNode)valueNode));
            }
        }
        
        @Override
        public Object construct(final Node node) {
            ScalarNode scalarNode;
            if (node instanceof MappingNode) {
                final NodeTuple nodeTuple = ((MappingNode)node).getValue().iterator().next();
                node.setTwoStepsConstruction(true);
                scalarNode = (ScalarNode)nodeTuple.getKeyNode();
            }
            else {
                scalarNode = (ScalarNode)node;
            }
            final CompactData compactData = this.this$0.getCompactData(scalarNode.getValue());
            if (compactData == null) {
                return CompactConstructor.access$100(this.this$0, scalarNode);
            }
            return this.this$0.constructCompactFormat(scalarNode, compactData);
        }
    }
}
