package org.lwjgl.util.mapped;

public class MappedSet
{
    public static MappedSet2 create(final MappedObject mappedObject, final MappedObject mappedObject2) {
        return new MappedSet2(mappedObject, mappedObject2);
    }
    
    public static MappedSet3 create(final MappedObject mappedObject, final MappedObject mappedObject2, final MappedObject mappedObject3) {
        return new MappedSet3(mappedObject, mappedObject2, mappedObject3);
    }
    
    public static MappedSet4 create(final MappedObject mappedObject, final MappedObject mappedObject2, final MappedObject mappedObject3, final MappedObject mappedObject4) {
        return new MappedSet4(mappedObject, mappedObject2, mappedObject3, mappedObject4);
    }
}
