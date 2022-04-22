package org.yaml.snakeyaml.nodes;

public enum NodeId
{
    scalar("scalar", 0), 
    sequence("sequence", 1), 
    mapping("mapping", 2), 
    anchor("anchor", 3);
    
    private static final NodeId[] $VALUES;
    
    private NodeId(final String s, final int n) {
    }
    
    static {
        $VALUES = new NodeId[] { NodeId.scalar, NodeId.sequence, NodeId.mapping, NodeId.anchor };
    }
}
