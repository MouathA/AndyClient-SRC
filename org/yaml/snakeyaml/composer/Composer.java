package org.yaml.snakeyaml.composer;

import org.yaml.snakeyaml.parser.*;
import org.yaml.snakeyaml.resolver.*;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;
import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.nodes.*;

public class Composer
{
    protected final Parser parser;
    private final Resolver resolver;
    private final Map anchors;
    private final Set recursiveNodes;
    private int nonScalarAliasesCount;
    private final LoaderOptions loadingConfig;
    
    public Composer(final Parser parser, final Resolver resolver) {
        this(parser, resolver, new LoaderOptions());
    }
    
    public Composer(final Parser parser, final Resolver resolver, final LoaderOptions loadingConfig) {
        this.nonScalarAliasesCount = 0;
        this.parser = parser;
        this.resolver = resolver;
        this.anchors = new HashMap();
        this.recursiveNodes = new HashSet();
        this.loadingConfig = loadingConfig;
    }
    
    public boolean checkNode() {
        if (this.parser.checkEvent(Event.ID.StreamStart)) {
            this.parser.getEvent();
        }
        return !this.parser.checkEvent(Event.ID.StreamEnd);
    }
    
    public Node getNode() {
        this.parser.getEvent();
        final Node composeNode = this.composeNode(null);
        this.parser.getEvent();
        this.anchors.clear();
        this.recursiveNodes.clear();
        return composeNode;
    }
    
    public Node getSingleNode() {
        this.parser.getEvent();
        Node node = null;
        if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
            node = this.getNode();
        }
        if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
            throw new ComposerException("expected a single document in the stream", (node != null) ? node.getStartMark() : null, "but found another document", this.parser.getEvent().getStartMark());
        }
        this.parser.getEvent();
        return node;
    }
    
    private Node composeNode(final Node node) {
        if (node != null) {
            this.recursiveNodes.add(node);
        }
        Node node2;
        if (this.parser.checkEvent(Event.ID.Alias)) {
            final AliasEvent aliasEvent = (AliasEvent)this.parser.getEvent();
            final String anchor = aliasEvent.getAnchor();
            if (!this.anchors.containsKey(anchor)) {
                throw new ComposerException(null, null, "found undefined alias " + anchor, aliasEvent.getStartMark());
            }
            node2 = this.anchors.get(anchor);
            if (!(node2 instanceof ScalarNode)) {
                ++this.nonScalarAliasesCount;
                if (this.nonScalarAliasesCount > this.loadingConfig.getMaxAliasesForCollections()) {
                    throw new YAMLException("Number of aliases for non-scalar nodes exceeds the specified max=" + this.loadingConfig.getMaxAliasesForCollections());
                }
            }
            if (this.recursiveNodes.remove(node2)) {
                node2.setTwoStepsConstruction(true);
            }
        }
        else {
            final String anchor2 = ((NodeEvent)this.parser.peekEvent()).getAnchor();
            if (this.parser.checkEvent(Event.ID.Scalar)) {
                node2 = this.composeScalarNode(anchor2);
            }
            else if (this.parser.checkEvent(Event.ID.SequenceStart)) {
                node2 = this.composeSequenceNode(anchor2);
            }
            else {
                node2 = this.composeMappingNode(anchor2);
            }
        }
        this.recursiveNodes.remove(node);
        return node2;
    }
    
    protected Node composeScalarNode(final String anchor) {
        final ScalarEvent scalarEvent = (ScalarEvent)this.parser.getEvent();
        final String tag = scalarEvent.getTag();
        Tag resolve;
        if (tag == null || tag.equals("!")) {
            resolve = this.resolver.resolve(NodeId.scalar, scalarEvent.getValue(), scalarEvent.getImplicit().canOmitTagInPlainScalar());
        }
        else {
            resolve = new Tag(tag);
        }
        final ScalarNode scalarNode = new ScalarNode(resolve, true, scalarEvent.getValue(), scalarEvent.getStartMark(), scalarEvent.getEndMark(), scalarEvent.getScalarStyle());
        if (anchor != null) {
            scalarNode.setAnchor(anchor);
            this.anchors.put(anchor, scalarNode);
        }
        return scalarNode;
    }
    
    protected Node composeSequenceNode(final String anchor) {
        final SequenceStartEvent sequenceStartEvent = (SequenceStartEvent)this.parser.getEvent();
        final String tag = sequenceStartEvent.getTag();
        Tag resolve;
        if (tag == null || tag.equals("!")) {
            resolve = this.resolver.resolve(NodeId.sequence, null, sequenceStartEvent.getImplicit());
        }
        else {
            resolve = new Tag(tag);
        }
        final ArrayList<Node> list = new ArrayList<Node>();
        final SequenceNode sequenceNode = new SequenceNode(resolve, true, list, sequenceStartEvent.getStartMark(), null, sequenceStartEvent.getFlowStyle());
        if (anchor != null) {
            sequenceNode.setAnchor(anchor);
            this.anchors.put(anchor, sequenceNode);
        }
        while (!this.parser.checkEvent(Event.ID.SequenceEnd)) {
            list.add(this.composeNode(sequenceNode));
        }
        sequenceNode.setEndMark(this.parser.getEvent().getEndMark());
        return sequenceNode;
    }
    
    protected Node composeMappingNode(final String anchor) {
        final MappingStartEvent mappingStartEvent = (MappingStartEvent)this.parser.getEvent();
        final String tag = mappingStartEvent.getTag();
        Tag resolve;
        if (tag == null || tag.equals("!")) {
            resolve = this.resolver.resolve(NodeId.mapping, null, mappingStartEvent.getImplicit());
        }
        else {
            resolve = new Tag(tag);
        }
        final ArrayList list = new ArrayList();
        final MappingNode mappingNode = new MappingNode(resolve, true, list, mappingStartEvent.getStartMark(), null, mappingStartEvent.getFlowStyle());
        if (anchor != null) {
            mappingNode.setAnchor(anchor);
            this.anchors.put(anchor, mappingNode);
        }
        while (!this.parser.checkEvent(Event.ID.MappingEnd)) {
            this.composeMappingChildren(list, mappingNode);
        }
        mappingNode.setEndMark(this.parser.getEvent().getEndMark());
        return mappingNode;
    }
    
    protected void composeMappingChildren(final List list, final MappingNode mappingNode) {
        final Node composeKeyNode = this.composeKeyNode(mappingNode);
        if (composeKeyNode.getTag().equals(Tag.MERGE)) {
            mappingNode.setMerged(true);
        }
        list.add(new NodeTuple(composeKeyNode, this.composeValueNode(mappingNode)));
    }
    
    protected Node composeKeyNode(final MappingNode mappingNode) {
        return this.composeNode(mappingNode);
    }
    
    protected Node composeValueNode(final MappingNode mappingNode) {
        return this.composeNode(mappingNode);
    }
}
