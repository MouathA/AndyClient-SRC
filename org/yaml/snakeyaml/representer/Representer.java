package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.introspector.*;
import org.yaml.snakeyaml.nodes.*;
import java.util.*;

public class Representer extends SafeRepresenter
{
    protected Map typeDefinitions;
    
    public Representer() {
        this.typeDefinitions = Collections.emptyMap();
        this.representers.put(null, new RepresentJavaBean());
    }
    
    public Representer(final DumperOptions dumperOptions) {
        super(dumperOptions);
        this.typeDefinitions = Collections.emptyMap();
        this.representers.put(null, new RepresentJavaBean());
    }
    
    public TypeDescription addTypeDescription(final TypeDescription typeDescription) {
        if (Collections.EMPTY_MAP == this.typeDefinitions) {
            this.typeDefinitions = new HashMap();
        }
        if (typeDescription.getTag() != null) {
            this.addClassTag(typeDescription.getType(), typeDescription.getTag());
        }
        typeDescription.setPropertyUtils(this.getPropertyUtils());
        return this.typeDefinitions.put(typeDescription.getType(), typeDescription);
    }
    
    @Override
    public void setPropertyUtils(final PropertyUtils propertyUtils) {
        super.setPropertyUtils(propertyUtils);
        final Iterator<TypeDescription> iterator = this.typeDefinitions.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().setPropertyUtils(propertyUtils);
        }
    }
    
    protected MappingNode representJavaBean(final Set set, final Object o) {
        final ArrayList<NodeTuple> list = new ArrayList<NodeTuple>(set.size());
        final Tag tag = this.classTags.get(o.getClass());
        final MappingNode mappingNode = new MappingNode((tag != null) ? tag : new Tag(o.getClass()), list, DumperOptions.FlowStyle.AUTO);
        this.representedObjects.put(o, mappingNode);
        DumperOptions.FlowStyle flowStyle = DumperOptions.FlowStyle.FLOW;
        for (final Property property : set) {
            final Object value = property.get(o);
            final NodeTuple representJavaBeanProperty = this.representJavaBeanProperty(o, property, value, (value == null) ? null : this.classTags.get(value.getClass()));
            if (representJavaBeanProperty == null) {
                continue;
            }
            if (!((ScalarNode)representJavaBeanProperty.getKeyNode()).isPlain()) {
                flowStyle = DumperOptions.FlowStyle.BLOCK;
            }
            final Node valueNode = representJavaBeanProperty.getValueNode();
            if (!(valueNode instanceof ScalarNode) || !((ScalarNode)valueNode).isPlain()) {
                flowStyle = DumperOptions.FlowStyle.BLOCK;
            }
            list.add(representJavaBeanProperty);
        }
        if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
            mappingNode.setFlowStyle(this.defaultFlowStyle);
        }
        else {
            mappingNode.setFlowStyle(flowStyle);
        }
        return mappingNode;
    }
    
    protected NodeTuple representJavaBeanProperty(final Object o, final Property property, final Object o2, final Tag tag) {
        final ScalarNode scalarNode = (ScalarNode)this.representData(property.getName());
        final boolean containsKey = this.representedObjects.containsKey(o2);
        final Node representData = this.representData(o2);
        if (o2 != null && !containsKey) {
            final NodeId nodeId = representData.getNodeId();
            if (tag == null) {
                if (nodeId == NodeId.scalar) {
                    if (property.getType() != Enum.class && o2 instanceof Enum) {
                        representData.setTag(Tag.STR);
                    }
                }
                else {
                    if (nodeId == NodeId.mapping && property.getType() == o2.getClass() && !(o2 instanceof Map) && !representData.getTag().equals(Tag.SET)) {
                        representData.setTag(Tag.MAP);
                    }
                    this.checkGlobalTag(property, representData, o2);
                }
            }
        }
        return new NodeTuple(scalarNode, representData);
    }
    
    protected void checkGlobalTag(final Property property, final Node node, final Object o) {
        if (o.getClass().isArray() && o.getClass().getComponentType().isPrimitive()) {
            return;
        }
        final Class[] actualTypeArguments = property.getActualTypeArguments();
        if (actualTypeArguments != null) {
            if (node.getNodeId() == NodeId.sequence) {
                final Class clazz = actualTypeArguments[0];
                final SequenceNode sequenceNode = (SequenceNode)node;
                Iterable<Object> iterable = (Iterable<Object>)Collections.EMPTY_LIST;
                if (o.getClass().isArray()) {
                    iterable = Arrays.asList((Object[])o);
                }
                else if (o instanceof Iterable) {
                    iterable = (Iterable<Object>)o;
                }
                final Iterator<Object> iterator = iterable.iterator();
                if (iterator.hasNext()) {
                    for (final Node node2 : sequenceNode.getValue()) {
                        final Object next = iterator.next();
                        if (next != null && clazz.equals(next.getClass()) && node2.getNodeId() == NodeId.mapping) {
                            node2.setTag(Tag.MAP);
                        }
                    }
                }
            }
            else if (o instanceof Set) {
                final Class clazz2 = actualTypeArguments[0];
                final Iterator<NodeTuple> iterator3 = ((MappingNode)node).getValue().iterator();
                for (final Object next2 : (Set)o) {
                    final Node keyNode = iterator3.next().getKeyNode();
                    if (clazz2.equals(next2.getClass()) && keyNode.getNodeId() == NodeId.mapping) {
                        keyNode.setTag(Tag.MAP);
                    }
                }
            }
            else if (o instanceof Map) {
                final Class clazz3 = actualTypeArguments[0];
                final Class clazz4 = actualTypeArguments[1];
                for (final NodeTuple nodeTuple : ((MappingNode)node).getValue()) {
                    this.resetTag(clazz3, nodeTuple.getKeyNode());
                    this.resetTag(clazz4, nodeTuple.getValueNode());
                }
            }
        }
    }
    
    private void resetTag(final Class clazz, final Node node) {
        if (node.getTag().matches(clazz)) {
            if (Enum.class.isAssignableFrom(clazz)) {
                node.setTag(Tag.STR);
            }
            else {
                node.setTag(Tag.MAP);
            }
        }
    }
    
    protected Set getProperties(final Class clazz) {
        if (this.typeDefinitions.containsKey(clazz)) {
            return this.typeDefinitions.get(clazz).getProperties();
        }
        return this.getPropertyUtils().getProperties(clazz);
    }
    
    @Override
    public void setTimeZone(final TimeZone timeZone) {
        super.setTimeZone(timeZone);
    }
    
    @Override
    public TimeZone getTimeZone() {
        return super.getTimeZone();
    }
    
    @Override
    public Tag addClassTag(final Class clazz, final Tag tag) {
        return super.addClassTag(clazz, tag);
    }
    
    protected class RepresentJavaBean implements Represent
    {
        final Representer this$0;
        
        protected RepresentJavaBean(final Representer this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            return this.this$0.representJavaBean(this.this$0.getProperties(o.getClass()), o);
        }
    }
}
