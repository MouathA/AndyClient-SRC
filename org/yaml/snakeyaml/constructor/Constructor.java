package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.error.*;
import java.math.*;
import java.util.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.introspector.*;

public class Constructor extends SafeConstructor
{
    public Constructor() {
        this(Object.class);
    }
    
    public Constructor(final LoaderOptions loaderOptions) {
        this(Object.class, loaderOptions);
    }
    
    public Constructor(final Class clazz) {
        this(new TypeDescription(checkRoot(clazz)));
    }
    
    public Constructor(final Class clazz, final LoaderOptions loaderOptions) {
        this(new TypeDescription(checkRoot(clazz)), loaderOptions);
    }
    
    private static Class checkRoot(final Class clazz) {
        if (clazz == null) {
            throw new NullPointerException("Root class must be provided.");
        }
        return clazz;
    }
    
    public Constructor(final TypeDescription typeDescription) {
        this(typeDescription, null, new LoaderOptions());
    }
    
    public Constructor(final TypeDescription typeDescription, final LoaderOptions loaderOptions) {
        this(typeDescription, null, loaderOptions);
    }
    
    public Constructor(final TypeDescription typeDescription, final Collection collection) {
        this(typeDescription, collection, new LoaderOptions());
    }
    
    public Constructor(final TypeDescription typeDescription, final Collection collection, final LoaderOptions loaderOptions) {
        super(loaderOptions);
        if (typeDescription == null) {
            throw new NullPointerException("Root type must be provided.");
        }
        this.yamlConstructors.put(null, new ConstructYamlObject());
        if (!Object.class.equals(typeDescription.getType())) {
            this.rootTag = new Tag(typeDescription.getType());
        }
        this.yamlClassConstructors.put(NodeId.scalar, new ConstructScalar());
        this.yamlClassConstructors.put(NodeId.mapping, new ConstructMapping());
        this.yamlClassConstructors.put(NodeId.sequence, new ConstructSequence());
        this.addTypeDescription(typeDescription);
        if (collection != null) {
            final Iterator<TypeDescription> iterator = collection.iterator();
            while (iterator.hasNext()) {
                this.addTypeDescription(iterator.next());
            }
        }
    }
    
    public Constructor(final String s) throws ClassNotFoundException {
        this(Class.forName(check(s)));
    }
    
    public Constructor(final String s, final LoaderOptions loaderOptions) throws ClassNotFoundException {
        this(Class.forName(check(s)), loaderOptions);
    }
    
    private static final String check(final String s) {
        if (s == null) {
            throw new NullPointerException("Root type must be provided.");
        }
        if (s.trim().length() == 0) {
            throw new YAMLException("Root type must be provided.");
        }
        return s;
    }
    
    protected Class getClassForNode(final Node node) {
        final Class clazz = this.typeTags.get(node.getTag());
        if (clazz == null) {
            final Class classForName = this.getClassForName(node.getTag().getClassName());
            this.typeTags.put(node.getTag(), classForName);
            return classForName;
        }
        return clazz;
    }
    
    protected Class getClassForName(final String s) throws ClassNotFoundException {
        return Class.forName(s, true, Thread.currentThread().getContextClassLoader());
    }
    
    protected class ConstructSequence implements Construct
    {
        final Constructor this$0;
        
        protected ConstructSequence(final Constructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            final SequenceNode sequenceNode = (SequenceNode)node;
            if (Set.class.isAssignableFrom(node.getType())) {
                if (node.isTwoStepsConstruction()) {
                    throw new YAMLException("Set cannot be recursive.");
                }
                return this.this$0.constructSet(sequenceNode);
            }
            else if (Collection.class.isAssignableFrom(node.getType())) {
                if (node.isTwoStepsConstruction()) {
                    return this.this$0.newList(sequenceNode);
                }
                return this.this$0.constructSequence(sequenceNode);
            }
            else {
                if (!node.getType().isArray()) {
                    final ArrayList list = new ArrayList<java.lang.reflect.Constructor<Object>>(sequenceNode.getValue().size());
                    final java.lang.reflect.Constructor[] declaredConstructors = node.getType().getDeclaredConstructors();
                    int n = 0;
                    while (0 < declaredConstructors.length) {
                        final java.lang.reflect.Constructor constructor = declaredConstructors[0];
                        if (sequenceNode.getValue().size() == constructor.getParameterTypes().length) {
                            list.add((java.lang.reflect.Constructor<Object>)constructor);
                        }
                        ++n;
                    }
                    if (!list.isEmpty()) {
                        if (list.size() == 1) {
                            final Object[] array = new Object[sequenceNode.getValue().size()];
                            final java.lang.reflect.Constructor<Object> constructor2 = list.get(0);
                            for (final Node node2 : sequenceNode.getValue()) {
                                node2.setType(constructor2.getParameterTypes()[0]);
                                final Object[] array2 = array;
                                final int n2 = 0;
                                ++n;
                                array2[n2] = this.this$0.constructObject(node2);
                            }
                            constructor2.setAccessible(true);
                            return constructor2.newInstance(array);
                        }
                        final List constructSequence = this.this$0.constructSequence(sequenceNode);
                        final Class[] array3 = new Class[constructSequence.size()];
                        final Iterator<Object> iterator2 = constructSequence.iterator();
                        while (iterator2.hasNext()) {
                            array3[0] = iterator2.next().getClass();
                            ++n;
                        }
                        for (final java.lang.reflect.Constructor<Object> constructor3 : list) {
                            final Class<?>[] parameterTypes = constructor3.getParameterTypes();
                            while (0 < parameterTypes.length && this.wrapIfPrimitive(parameterTypes[0]).isAssignableFrom(array3[0])) {
                                int n3 = 0;
                                ++n3;
                            }
                            if (false) {
                                constructor3.setAccessible(true);
                                return constructor3.newInstance(constructSequence.toArray());
                            }
                        }
                    }
                    throw new YAMLException("No suitable constructor with " + String.valueOf(sequenceNode.getValue().size()) + " arguments found for " + node.getType());
                }
                if (node.isTwoStepsConstruction()) {
                    return this.this$0.createArray(node.getType(), sequenceNode.getValue().size());
                }
                return this.this$0.constructArray(sequenceNode);
            }
        }
        
        private final Class wrapIfPrimitive(final Class clazz) {
            if (!clazz.isPrimitive()) {
                return clazz;
            }
            if (clazz == Integer.TYPE) {
                return Integer.class;
            }
            if (clazz == Float.TYPE) {
                return Float.class;
            }
            if (clazz == Double.TYPE) {
                return Double.class;
            }
            if (clazz == Boolean.TYPE) {
                return Boolean.class;
            }
            if (clazz == Long.TYPE) {
                return Long.class;
            }
            if (clazz == Character.TYPE) {
                return Character.class;
            }
            if (clazz == Short.TYPE) {
                return Short.class;
            }
            if (clazz == Byte.TYPE) {
                return Byte.class;
            }
            throw new YAMLException("Unexpected primitive " + clazz);
        }
        
        @Override
        public void construct2ndStep(final Node node, final Object o) {
            final SequenceNode sequenceNode = (SequenceNode)node;
            if (List.class.isAssignableFrom(node.getType())) {
                this.this$0.constructSequenceStep2(sequenceNode, (Collection)o);
            }
            else {
                if (!node.getType().isArray()) {
                    throw new YAMLException("Immutable objects cannot be recursive.");
                }
                this.this$0.constructArrayStep2(sequenceNode, o);
            }
        }
    }
    
    protected class ConstructScalar extends AbstractConstruct
    {
        final Constructor this$0;
        
        protected ConstructScalar(final Constructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            final ScalarNode scalarNode = (ScalarNode)node;
            return this.this$0.newInstance(scalarNode.getType(), scalarNode, false);
        }
        
        private Object constructStandardJavaInstance(final Class clazz, final ScalarNode scalarNode) {
            Object o;
            if (clazz == String.class) {
                o = this.this$0.yamlConstructors.get(Tag.STR).construct(scalarNode);
            }
            else if (clazz == Boolean.class || clazz == Boolean.TYPE) {
                o = this.this$0.yamlConstructors.get(Tag.BOOL).construct(scalarNode);
            }
            else if (clazz == Character.class || clazz == Character.TYPE) {
                final String s = (String)this.this$0.yamlConstructors.get(Tag.STR).construct(scalarNode);
                if (s.length() == 0) {
                    o = null;
                }
                else {
                    if (s.length() != 1) {
                        throw new YAMLException("Invalid node Character: '" + s + "'; length: " + s.length());
                    }
                    o = s.charAt(0);
                }
            }
            else if (Date.class.isAssignableFrom(clazz)) {
                final Date date = (Date)this.this$0.yamlConstructors.get(Tag.TIMESTAMP).construct(scalarNode);
                if (clazz == Date.class) {
                    o = date;
                }
                else {
                    o = clazz.getConstructor(Long.TYPE).newInstance(date.getTime());
                }
            }
            else if (clazz == Float.class || clazz == Double.class || clazz == Float.TYPE || clazz == Double.TYPE || clazz == BigDecimal.class) {
                if (clazz == BigDecimal.class) {
                    o = new BigDecimal(scalarNode.getValue());
                }
                else {
                    o = this.this$0.yamlConstructors.get(Tag.FLOAT).construct(scalarNode);
                    if (clazz == Float.class || clazz == Float.TYPE) {
                        o = ((Double)o).floatValue();
                    }
                }
            }
            else if (clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == Long.class || clazz == BigInteger.class || clazz == Byte.TYPE || clazz == Short.TYPE || clazz == Integer.TYPE || clazz == Long.TYPE) {
                final Object construct = this.this$0.yamlConstructors.get(Tag.INT).construct(scalarNode);
                if (clazz == Byte.class || clazz == Byte.TYPE) {
                    o = Integer.valueOf(construct.toString()).byteValue();
                }
                else if (clazz == Short.class || clazz == Short.TYPE) {
                    o = Integer.valueOf(construct.toString()).shortValue();
                }
                else if (clazz == Integer.class || clazz == Integer.TYPE) {
                    o = Integer.parseInt(construct.toString());
                }
                else if (clazz == Long.class || clazz == Long.TYPE) {
                    o = Long.valueOf(construct.toString());
                }
                else {
                    o = new BigInteger(construct.toString());
                }
            }
            else if (Enum.class.isAssignableFrom(clazz)) {
                o = Enum.valueOf((Class<Double>)clazz, scalarNode.getValue());
            }
            else if (Calendar.class.isAssignableFrom(clazz)) {
                final ConstructYamlTimestamp constructYamlTimestamp = new ConstructYamlTimestamp();
                constructYamlTimestamp.construct(scalarNode);
                o = constructYamlTimestamp.getCalendar();
            }
            else if (Number.class.isAssignableFrom(clazz)) {
                o = this.this$0.new ConstructYamlFloat().construct(scalarNode);
            }
            else if (UUID.class == clazz) {
                o = UUID.fromString(scalarNode.getValue());
            }
            else {
                if (!this.this$0.yamlConstructors.containsKey(scalarNode.getTag())) {
                    throw new YAMLException("Unsupported class: " + clazz);
                }
                o = this.this$0.yamlConstructors.get(scalarNode.getTag()).construct(scalarNode);
            }
            return o;
        }
    }
    
    protected class ConstructYamlObject implements Construct
    {
        final Constructor this$0;
        
        protected ConstructYamlObject(final Constructor this$0) {
            this.this$0 = this$0;
        }
        
        private Construct getConstructor(final Node node) {
            node.setType(this.this$0.getClassForNode(node));
            return this.this$0.yamlClassConstructors.get(node.getNodeId());
        }
        
        @Override
        public Object construct(final Node node) {
            return this.getConstructor(node).construct(node);
        }
        
        @Override
        public void construct2ndStep(final Node node, final Object o) {
            this.getConstructor(node).construct2ndStep(node, o);
        }
    }
    
    protected class ConstructMapping implements Construct
    {
        final Constructor this$0;
        
        protected ConstructMapping(final Constructor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object construct(final Node node) {
            final MappingNode mappingNode = (MappingNode)node;
            if (Map.class.isAssignableFrom(node.getType())) {
                if (node.isTwoStepsConstruction()) {
                    return this.this$0.newMap(mappingNode);
                }
                return this.this$0.constructMapping(mappingNode);
            }
            else if (Collection.class.isAssignableFrom(node.getType())) {
                if (node.isTwoStepsConstruction()) {
                    return this.this$0.newSet(mappingNode);
                }
                return this.this$0.constructSet(mappingNode);
            }
            else {
                final Object instance = this.this$0.newInstance(mappingNode);
                if (node.isTwoStepsConstruction()) {
                    return instance;
                }
                return this.constructJavaBean2ndStep(mappingNode, instance);
            }
        }
        
        @Override
        public void construct2ndStep(final Node node, final Object o) {
            if (Map.class.isAssignableFrom(node.getType())) {
                this.this$0.constructMapping2ndStep((MappingNode)node, (Map)o);
            }
            else if (Set.class.isAssignableFrom(node.getType())) {
                this.this$0.constructSet2ndStep((MappingNode)node, (Set)o);
            }
            else {
                this.constructJavaBean2ndStep((MappingNode)node, o);
            }
        }
        
        protected Object constructJavaBean2ndStep(final MappingNode mappingNode, final Object o) {
            this.this$0.flattenMapping(mappingNode);
            final Class type = mappingNode.getType();
            for (final NodeTuple nodeTuple : mappingNode.getValue()) {
                if (!(nodeTuple.getKeyNode() instanceof ScalarNode)) {
                    throw new YAMLException("Keys must be scalars but found: " + nodeTuple.getKeyNode());
                }
                final ScalarNode scalarNode = (ScalarNode)nodeTuple.getKeyNode();
                final Node valueNode = nodeTuple.getValueNode();
                scalarNode.setType(String.class);
                final String s = (String)this.this$0.constructObject(scalarNode);
                final TypeDescription typeDescription = this.this$0.typeDefinitions.get(type);
                final Property property = (typeDescription == null) ? this.getProperty(type, s) : typeDescription.getProperty(s);
                if (!property.isWritable()) {
                    throw new YAMLException("No writable property '" + s + "' on class: " + type.getName());
                }
                valueNode.setType(property.getType());
                if ((typeDescription == null || !typeDescription.setupPropertyType(s, valueNode)) && valueNode.getNodeId() != NodeId.scalar) {
                    final Class[] actualTypeArguments = property.getActualTypeArguments();
                    if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                        if (valueNode.getNodeId() == NodeId.sequence) {
                            ((SequenceNode)valueNode).setListType(actualTypeArguments[0]);
                        }
                        else if (Set.class.isAssignableFrom(valueNode.getType())) {
                            final Class onlyKeyType = actualTypeArguments[0];
                            final MappingNode mappingNode2 = (MappingNode)valueNode;
                            mappingNode2.setOnlyKeyType(onlyKeyType);
                            mappingNode2.setUseClassConstructor(true);
                        }
                        else if (Map.class.isAssignableFrom(valueNode.getType())) {
                            final Class clazz = actualTypeArguments[0];
                            final Class clazz2 = actualTypeArguments[1];
                            final MappingNode mappingNode3 = (MappingNode)valueNode;
                            mappingNode3.setTypes(clazz, clazz2);
                            mappingNode3.setUseClassConstructor(true);
                        }
                    }
                }
                Object value = (typeDescription != null) ? this.newInstance(typeDescription, s, valueNode) : this.this$0.constructObject(valueNode);
                if ((property.getType() == Float.TYPE || property.getType() == Float.class) && value instanceof Double) {
                    value = ((Double)value).floatValue();
                }
                if (property.getType() == String.class && Tag.BINARY.equals(valueNode.getTag()) && value instanceof byte[]) {
                    value = new String((byte[])value);
                }
                if (typeDescription != null && typeDescription.setProperty(o, s, value)) {
                    continue;
                }
                property.set(o, value);
            }
            return o;
        }
        
        private Object newInstance(final TypeDescription typeDescription, final String s, final Node node) {
            final Object instance = typeDescription.newInstance(s, node);
            if (instance != null) {
                this.this$0.constructedObjects.put(node, instance);
                return this.this$0.constructObjectNoCheck(node);
            }
            return this.this$0.constructObject(node);
        }
        
        protected Property getProperty(final Class clazz, final String s) {
            return this.this$0.getPropertyUtils().getProperty(clazz, s);
        }
    }
}
