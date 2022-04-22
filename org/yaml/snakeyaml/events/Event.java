package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;

public abstract class Event
{
    private final Mark startMark;
    private final Mark endMark;
    
    public Event(final Mark startMark, final Mark endMark) {
        this.startMark = startMark;
        this.endMark = endMark;
    }
    
    @Override
    public String toString() {
        return "<" + this.getClass().getName() + "(" + this.getArguments() + ")>";
    }
    
    public Mark getStartMark() {
        return this.startMark;
    }
    
    public Mark getEndMark() {
        return this.endMark;
    }
    
    protected String getArguments() {
        return "";
    }
    
    public boolean is(final ID id) {
        return this.getEventId() == id;
    }
    
    public abstract ID getEventId();
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Event && this.toString().equals(o.toString());
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    public enum ID
    {
        Alias("Alias", 0), 
        DocumentEnd("DocumentEnd", 1), 
        DocumentStart("DocumentStart", 2), 
        MappingEnd("MappingEnd", 3), 
        MappingStart("MappingStart", 4), 
        Scalar("Scalar", 5), 
        SequenceEnd("SequenceEnd", 6), 
        SequenceStart("SequenceStart", 7), 
        StreamEnd("StreamEnd", 8), 
        StreamStart("StreamStart", 9);
        
        private static final ID[] $VALUES;
        
        private ID(final String s, final int n) {
        }
        
        static {
            $VALUES = new ID[] { ID.Alias, ID.DocumentEnd, ID.DocumentStart, ID.MappingEnd, ID.MappingStart, ID.Scalar, ID.SequenceEnd, ID.SequenceStart, ID.StreamEnd, ID.StreamStart };
        }
    }
}
