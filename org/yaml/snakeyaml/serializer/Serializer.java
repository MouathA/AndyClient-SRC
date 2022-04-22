package org.yaml.snakeyaml.serializer;

import org.yaml.snakeyaml.emitter.*;
import org.yaml.snakeyaml.resolver.*;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.error.*;
import java.io.*;
import java.util.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.events.*;

public final class Serializer
{
    private final Emitable emitter;
    private final Resolver resolver;
    private boolean explicitStart;
    private boolean explicitEnd;
    private DumperOptions.Version useVersion;
    private Map useTags;
    private Set serializedNodes;
    private Map anchors;
    private AnchorGenerator anchorGenerator;
    private Boolean closed;
    private Tag explicitRoot;
    
    public Serializer(final Emitable emitter, final Resolver resolver, final DumperOptions dumperOptions, final Tag explicitRoot) {
        this.emitter = emitter;
        this.resolver = resolver;
        this.explicitStart = dumperOptions.isExplicitStart();
        this.explicitEnd = dumperOptions.isExplicitEnd();
        if (dumperOptions.getVersion() != null) {
            this.useVersion = dumperOptions.getVersion();
        }
        this.useTags = dumperOptions.getTags();
        this.serializedNodes = new HashSet();
        this.anchors = new HashMap();
        this.anchorGenerator = dumperOptions.getAnchorGenerator();
        this.closed = null;
        this.explicitRoot = explicitRoot;
    }
    
    public void open() throws IOException {
        if (this.closed == null) {
            this.emitter.emit(new StreamStartEvent(null, null));
            this.closed = Boolean.FALSE;
            return;
        }
        if (Boolean.TRUE.equals(this.closed)) {
            throw new SerializerException("serializer is closed");
        }
        throw new SerializerException("serializer is already opened");
    }
    
    public void close() throws IOException {
        if (this.closed == null) {
            throw new SerializerException("serializer is not opened");
        }
        if (!Boolean.TRUE.equals(this.closed)) {
            this.emitter.emit(new StreamEndEvent(null, null));
            this.closed = Boolean.TRUE;
            this.serializedNodes.clear();
            this.anchors.clear();
        }
    }
    
    public void serialize(final Node node) throws IOException {
        if (this.closed == null) {
            throw new SerializerException("serializer is not opened");
        }
        if (this.closed) {
            throw new SerializerException("serializer is closed");
        }
        this.emitter.emit(new DocumentStartEvent(null, null, this.explicitStart, this.useVersion, this.useTags));
        this.anchorNode(node);
        if (this.explicitRoot != null) {
            node.setTag(this.explicitRoot);
        }
        this.serializeNode(node, null);
        this.emitter.emit(new DocumentEndEvent(null, null, this.explicitEnd));
        this.serializedNodes.clear();
        this.anchors.clear();
    }
    
    private void anchorNode(Node realNode) {
        if (realNode.getNodeId() == NodeId.anchor) {
            realNode = ((AnchorNode)realNode).getRealNode();
        }
        if (this.anchors.containsKey(realNode)) {
            if (null == this.anchors.get(realNode)) {
                this.anchors.put(realNode, this.anchorGenerator.nextAnchor(realNode));
            }
        }
        else {
            this.anchors.put(realNode, (realNode.getAnchor() != null) ? this.anchorGenerator.nextAnchor(realNode) : null);
            switch (realNode.getNodeId()) {
                case sequence: {
                    final Iterator<Node> iterator = (Iterator<Node>)((SequenceNode)realNode).getValue().iterator();
                    while (iterator.hasNext()) {
                        this.anchorNode(iterator.next());
                    }
                    break;
                }
                case mapping: {
                    for (final NodeTuple nodeTuple : ((MappingNode)realNode).getValue()) {
                        final Node keyNode = nodeTuple.getKeyNode();
                        final Node valueNode = nodeTuple.getValueNode();
                        this.anchorNode(keyNode);
                        this.anchorNode(valueNode);
                    }
                    break;
                }
            }
        }
    }
    
    private void serializeNode(Node realNode, final Node node) throws IOException {
        if (realNode.getNodeId() == NodeId.anchor) {
            realNode = ((AnchorNode)realNode).getRealNode();
        }
        final String s = this.anchors.get(realNode);
        if (this.serializedNodes.contains(realNode)) {
            this.emitter.emit(new AliasEvent(s, null, null));
        }
        else {
            this.serializedNodes.add(realNode);
            switch (realNode.getNodeId()) {
                case scalar: {
                    final ScalarNode scalarNode = (ScalarNode)realNode;
                    this.emitter.emit(new ScalarEvent(s, realNode.getTag().getValue(), new ImplicitTuple(realNode.getTag().equals(this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), true)), realNode.getTag().equals(this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), false))), scalarNode.getValue(), null, null, scalarNode.getScalarStyle()));
                    break;
                }
                case sequence: {
                    final SequenceNode sequenceNode = (SequenceNode)realNode;
                    this.emitter.emit(new SequenceStartEvent(s, realNode.getTag().getValue(), realNode.getTag().equals(this.resolver.resolve(NodeId.sequence, null, true)), null, null, sequenceNode.getFlowStyle()));
                    final Iterator iterator = sequenceNode.getValue().iterator();
                    while (iterator.hasNext()) {
                        this.serializeNode(iterator.next(), realNode);
                    }
                    this.emitter.emit(new SequenceEndEvent(null, null));
                    break;
                }
                default: {
                    this.emitter.emit(new MappingStartEvent(s, realNode.getTag().getValue(), realNode.getTag().equals(this.resolver.resolve(NodeId.mapping, null, true)), null, null, ((CollectionNode)realNode).getFlowStyle()));
                    final MappingNode mappingNode = (MappingNode)realNode;
                    for (final NodeTuple nodeTuple : mappingNode.getValue()) {
                        final Node keyNode = nodeTuple.getKeyNode();
                        final Node valueNode = nodeTuple.getValueNode();
                        this.serializeNode(keyNode, mappingNode);
                        this.serializeNode(valueNode, mappingNode);
                    }
                    this.emitter.emit(new MappingEndEvent(null, null));
                    break;
                }
            }
        }
    }
}
