package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.composer.*;
import org.yaml.snakeyaml.introspector.*;
import org.yaml.snakeyaml.*;
import java.lang.reflect.*;
import java.util.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.nodes.*;

public abstract class BaseConstructor
{
    protected final Map yamlClassConstructors;
    protected final Map yamlConstructors;
    protected final Map yamlMultiConstructors;
    protected Composer composer;
    final Map constructedObjects;
    private final Set recursiveObjects;
    private final ArrayList maps2fill;
    private final ArrayList sets2fill;
    protected Tag rootTag;
    private PropertyUtils propertyUtils;
    private boolean explicitPropertyUtils;
    private boolean allowDuplicateKeys;
    private boolean wrappedToRootException;
    protected final Map typeDefinitions;
    protected final Map typeTags;
    protected LoaderOptions loadingConfig;
    
    public BaseConstructor() {
        this(new LoaderOptions());
    }
    
    public BaseConstructor(final LoaderOptions loadingConfig) {
        this.yamlClassConstructors = new EnumMap(NodeId.class);
        this.yamlConstructors = new HashMap();
        this.yamlMultiConstructors = new HashMap();
        this.allowDuplicateKeys = true;
        this.wrappedToRootException = false;
        this.constructedObjects = new HashMap();
        this.recursiveObjects = new HashSet();
        this.maps2fill = new ArrayList();
        this.sets2fill = new ArrayList();
        this.typeDefinitions = new HashMap();
        this.typeTags = new HashMap();
        this.rootTag = null;
        this.explicitPropertyUtils = false;
        this.typeDefinitions.put(SortedMap.class, new TypeDescription(SortedMap.class, Tag.OMAP, TreeMap.class));
        this.typeDefinitions.put(SortedSet.class, new TypeDescription(SortedSet.class, Tag.SET, TreeSet.class));
        this.loadingConfig = loadingConfig;
    }
    
    public void setComposer(final Composer composer) {
        this.composer = composer;
    }
    
    public boolean checkData() {
        return this.composer.checkNode();
    }
    
    public Object getData() throws NoSuchElementException {
        if (!this.composer.checkNode()) {
            throw new NoSuchElementException("No document is available.");
        }
        final Node node = this.composer.getNode();
        if (this.rootTag != null) {
            node.setTag(this.rootTag);
        }
        return this.constructDocument(node);
    }
    
    public Object getSingleData(final Class clazz) {
        final Node singleNode = this.composer.getSingleNode();
        if (singleNode != null && !Tag.NULL.equals(singleNode.getTag())) {
            if (Object.class != clazz) {
                singleNode.setTag(new Tag(clazz));
            }
            else if (this.rootTag != null) {
                singleNode.setTag(this.rootTag);
            }
            return this.constructDocument(singleNode);
        }
        return this.yamlConstructors.get(Tag.NULL).construct(singleNode);
    }
    
    protected final Object constructDocument(final Node node) {
        final Object constructObject = this.constructObject(node);
        this.fillRecursive();
        final Object o = constructObject;
        this.constructedObjects.clear();
        this.recursiveObjects.clear();
        return o;
    }
    
    private void fillRecursive() {
        if (!this.maps2fill.isEmpty()) {
            for (final RecursiveTuple recursiveTuple : this.maps2fill) {
                final RecursiveTuple recursiveTuple2 = (RecursiveTuple)recursiveTuple._2();
                ((Map)recursiveTuple._1()).put(recursiveTuple2._1(), recursiveTuple2._2());
            }
            this.maps2fill.clear();
        }
        if (!this.sets2fill.isEmpty()) {
            for (final RecursiveTuple recursiveTuple3 : this.sets2fill) {
                ((Set)recursiveTuple3._1()).add(recursiveTuple3._2());
            }
            this.sets2fill.clear();
        }
    }
    
    protected Object constructObject(final Node node) {
        if (this.constructedObjects.containsKey(node)) {
            return this.constructedObjects.get(node);
        }
        return this.constructObjectNoCheck(node);
    }
    
    protected Object constructObjectNoCheck(final Node node) {
        if (this.recursiveObjects.contains(node)) {
            throw new ConstructorException(null, null, "found unconstructable recursive node", node.getStartMark());
        }
        this.recursiveObjects.add(node);
        final Construct constructor = this.getConstructor(node);
        final Object o = this.constructedObjects.containsKey(node) ? this.constructedObjects.get(node) : constructor.construct(node);
        this.finalizeConstruction(node, o);
        this.constructedObjects.put(node, o);
        this.recursiveObjects.remove(node);
        if (node.isTwoStepsConstruction()) {
            constructor.construct2ndStep(node, o);
        }
        return o;
    }
    
    protected Construct getConstructor(final Node node) {
        if (node.useClassConstructor()) {
            return this.yamlClassConstructors.get(node.getNodeId());
        }
        final Construct construct = this.yamlConstructors.get(node.getTag());
        if (construct == null) {
            for (final String s : this.yamlMultiConstructors.keySet()) {
                if (node.getTag().startsWith(s)) {
                    return (Construct)this.yamlMultiConstructors.get(s);
                }
            }
            return this.yamlConstructors.get(null);
        }
        return construct;
    }
    
    protected String constructScalar(final ScalarNode scalarNode) {
        return scalarNode.getValue();
    }
    
    protected List createDefaultList(final int n) {
        return new ArrayList(n);
    }
    
    protected Set createDefaultSet(final int n) {
        return new LinkedHashSet(n);
    }
    
    protected Map createDefaultMap(final int n) {
        return new LinkedHashMap(n);
    }
    
    protected Object createArray(final Class clazz, final int n) {
        return Array.newInstance(clazz.getComponentType(), n);
    }
    
    protected Object finalizeConstruction(final Node node, final Object o) {
        final Class type = node.getType();
        if (this.typeDefinitions.containsKey(type)) {
            return ((TypeDescription)this.typeDefinitions.get(type)).finalizeConstruction(o);
        }
        return o;
    }
    
    protected Object newInstance(final Node node) {
        return this.newInstance(Object.class, node);
    }
    
    protected final Object newInstance(final Class clazz, final Node node) throws InstantiationException {
        return this.newInstance(clazz, node, true);
    }
    
    protected Object newInstance(final Class clazz, final Node node, final boolean b) throws InstantiationException {
        final Class type = node.getType();
        if (this.typeDefinitions.containsKey(type)) {
            final Object instance = this.typeDefinitions.get(type).newInstance(node);
            if (instance != null) {
                return instance;
            }
        }
        if (b && clazz.isAssignableFrom(type) && !Modifier.isAbstract(type.getModifiers())) {
            final Constructor<Object> declaredConstructor = type.getDeclaredConstructor((Class<?>[])new Class[0]);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(new Object[0]);
        }
        throw new InstantiationException();
    }
    
    protected Set newSet(final CollectionNode collectionNode) {
        return (Set)this.newInstance(Set.class, collectionNode);
    }
    
    protected List newList(final SequenceNode sequenceNode) {
        return (List)this.newInstance(List.class, sequenceNode);
    }
    
    protected Map newMap(final MappingNode mappingNode) {
        return (Map)this.newInstance(Map.class, mappingNode);
    }
    
    protected List constructSequence(final SequenceNode sequenceNode) {
        final List list = this.newList(sequenceNode);
        this.constructSequenceStep2(sequenceNode, list);
        return list;
    }
    
    protected Set constructSet(final SequenceNode sequenceNode) {
        final Set set = this.newSet(sequenceNode);
        this.constructSequenceStep2(sequenceNode, set);
        return set;
    }
    
    protected Object constructArray(final SequenceNode sequenceNode) {
        return this.constructArrayStep2(sequenceNode, this.createArray(sequenceNode.getType(), sequenceNode.getValue().size()));
    }
    
    protected void constructSequenceStep2(final SequenceNode sequenceNode, final Collection collection) {
        final Iterator<Node> iterator = sequenceNode.getValue().iterator();
        while (iterator.hasNext()) {
            collection.add(this.constructObject(iterator.next()));
        }
    }
    
    protected Object constructArrayStep2(final SequenceNode sequenceNode, final Object o) {
        final Class componentType = sequenceNode.getType().getComponentType();
        for (final Node node : sequenceNode.getValue()) {
            if (node.getType() == Object.class) {
                node.setType(componentType);
            }
            final Object constructObject = this.constructObject(node);
            if (componentType.isPrimitive()) {
                if (constructObject == null) {
                    throw new NullPointerException("Unable to construct element value for " + node);
                }
                if (Byte.TYPE.equals(componentType)) {
                    Array.setByte(o, 0, ((Number)constructObject).byteValue());
                }
                else if (Short.TYPE.equals(componentType)) {
                    Array.setShort(o, 0, ((Number)constructObject).shortValue());
                }
                else if (Integer.TYPE.equals(componentType)) {
                    Array.setInt(o, 0, ((Number)constructObject).intValue());
                }
                else if (Long.TYPE.equals(componentType)) {
                    Array.setLong(o, 0, ((Number)constructObject).longValue());
                }
                else if (Float.TYPE.equals(componentType)) {
                    Array.setFloat(o, 0, ((Number)constructObject).floatValue());
                }
                else if (Double.TYPE.equals(componentType)) {
                    Array.setDouble(o, 0, ((Number)constructObject).doubleValue());
                }
                else if (Character.TYPE.equals(componentType)) {
                    Array.setChar(o, 0, (char)constructObject);
                }
                else {
                    if (!Boolean.TYPE.equals(componentType)) {
                        throw new YAMLException("unexpected primitive type");
                    }
                    Array.setBoolean(o, 0, (boolean)constructObject);
                }
            }
            else {
                Array.set(o, 0, constructObject);
            }
            int n = 0;
            ++n;
        }
        return o;
    }
    
    protected Set constructSet(final MappingNode mappingNode) {
        final Set set = this.newSet(mappingNode);
        this.constructSet2ndStep(mappingNode, set);
        return set;
    }
    
    protected Map constructMapping(final MappingNode mappingNode) {
        final Map map = this.newMap(mappingNode);
        this.constructMapping2ndStep(mappingNode, map);
        return map;
    }
    
    protected void constructMapping2ndStep(final MappingNode mappingNode, final Map map) {
        for (final NodeTuple nodeTuple : mappingNode.getValue()) {
            final Node keyNode = nodeTuple.getKeyNode();
            final Node valueNode = nodeTuple.getValueNode();
            final Object constructObject = this.constructObject(keyNode);
            if (constructObject != null) {
                constructObject.hashCode();
            }
            final Object constructObject2 = this.constructObject(valueNode);
            if (keyNode.isTwoStepsConstruction()) {
                if (!this.loadingConfig.getAllowRecursiveKeys()) {
                    throw new YAMLException("Recursive key for mapping is detected but it is not configured to be allowed.");
                }
                this.postponeMapFilling(map, constructObject, constructObject2);
            }
            else {
                map.put(constructObject, constructObject2);
            }
        }
    }
    
    protected void postponeMapFilling(final Map map, final Object o, final Object o2) {
        this.maps2fill.add(0, new RecursiveTuple(map, new RecursiveTuple(o, o2)));
    }
    
    protected void constructSet2ndStep(final MappingNode mappingNode, final Set set) {
        final Iterator<NodeTuple> iterator = mappingNode.getValue().iterator();
        while (iterator.hasNext()) {
            final Node keyNode = iterator.next().getKeyNode();
            final Object constructObject = this.constructObject(keyNode);
            if (constructObject != null) {
                constructObject.hashCode();
            }
            if (keyNode.isTwoStepsConstruction()) {
                this.postponeSetFilling(set, constructObject);
            }
            else {
                set.add(constructObject);
            }
        }
    }
    
    protected void postponeSetFilling(final Set set, final Object o) {
        this.sets2fill.add(0, new RecursiveTuple(set, o));
    }
    
    public void setPropertyUtils(final PropertyUtils propertyUtils) {
        this.propertyUtils = propertyUtils;
        this.explicitPropertyUtils = true;
        final Iterator<TypeDescription> iterator = this.typeDefinitions.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().setPropertyUtils(propertyUtils);
        }
    }
    
    public final PropertyUtils getPropertyUtils() {
        if (this.propertyUtils == null) {
            this.propertyUtils = new PropertyUtils();
        }
        return this.propertyUtils;
    }
    
    public TypeDescription addTypeDescription(final TypeDescription typeDescription) {
        if (typeDescription == null) {
            throw new NullPointerException("TypeDescription is required.");
        }
        this.typeTags.put(typeDescription.getTag(), typeDescription.getType());
        typeDescription.setPropertyUtils(this.getPropertyUtils());
        return this.typeDefinitions.put(typeDescription.getType(), typeDescription);
    }
    
    public final boolean isExplicitPropertyUtils() {
        return this.explicitPropertyUtils;
    }
    
    public boolean isAllowDuplicateKeys() {
        return this.allowDuplicateKeys;
    }
    
    public void setAllowDuplicateKeys(final boolean allowDuplicateKeys) {
        this.allowDuplicateKeys = allowDuplicateKeys;
    }
    
    public boolean isWrappedToRootException() {
        return this.wrappedToRootException;
    }
    
    public void setWrappedToRootException(final boolean wrappedToRootException) {
        this.wrappedToRootException = wrappedToRootException;
    }
    
    private static class RecursiveTuple
    {
        private final Object _1;
        private final Object _2;
        
        public RecursiveTuple(final Object 1, final Object 2) {
            this._1 = 1;
            this._2 = 2;
        }
        
        public Object _2() {
            return this._2;
        }
        
        public Object _1() {
            return this._1;
        }
    }
}
