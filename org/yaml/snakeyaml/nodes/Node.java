package org.yaml.snakeyaml.nodes;

import org.yaml.snakeyaml.error.*;

public abstract class Node
{
    private Tag tag;
    private Mark startMark;
    protected Mark endMark;
    private Class type;
    private boolean twoStepsConstruction;
    private String anchor;
    protected boolean resolved;
    protected Boolean useClassConstructor;
    
    public Node(final Tag tag, final Mark startMark, final Mark endMark) {
        this.setTag(tag);
        this.startMark = startMark;
        this.endMark = endMark;
        this.type = Object.class;
        this.twoStepsConstruction = false;
        this.resolved = true;
        this.useClassConstructor = null;
    }
    
    public Tag getTag() {
        return this.tag;
    }
    
    public Mark getEndMark() {
        return this.endMark;
    }
    
    public abstract NodeId getNodeId();
    
    public Mark getStartMark() {
        return this.startMark;
    }
    
    public void setTag(final Tag tag) {
        if (tag == null) {
            throw new NullPointerException("tag in a Node is required.");
        }
        this.tag = tag;
    }
    
    @Override
    public final boolean equals(final Object o) {
        return super.equals(o);
    }
    
    public Class getType() {
        return this.type;
    }
    
    public void setType(final Class type) {
        if (!type.isAssignableFrom(this.type)) {
            this.type = type;
        }
    }
    
    public void setTwoStepsConstruction(final boolean twoStepsConstruction) {
        this.twoStepsConstruction = twoStepsConstruction;
    }
    
    public boolean isTwoStepsConstruction() {
        return this.twoStepsConstruction;
    }
    
    @Override
    public final int hashCode() {
        return super.hashCode();
    }
    
    public boolean useClassConstructor() {
        if (this.useClassConstructor == null) {
            return (!this.tag.isSecondary() && this.resolved && !Object.class.equals(this.type) && !this.tag.equals(Tag.NULL)) || this.tag.isCompatible(this.getType());
        }
        return this.useClassConstructor;
    }
    
    public void setUseClassConstructor(final Boolean useClassConstructor) {
        this.useClassConstructor = useClassConstructor;
    }
    
    @Deprecated
    public boolean isResolved() {
        return this.resolved;
    }
    
    public String getAnchor() {
        return this.anchor;
    }
    
    public void setAnchor(final String anchor) {
        this.anchor = anchor;
    }
}
