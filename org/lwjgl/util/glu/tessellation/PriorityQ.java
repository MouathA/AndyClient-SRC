package org.lwjgl.util.glu.tessellation;

abstract class PriorityQ
{
    public static final int INIT_SIZE = 32;
    
    public static boolean LEQ(final Leq leq, final Object o, final Object o2) {
        return Geom.VertLeq((GLUvertex)o, (GLUvertex)o2);
    }
    
    static PriorityQ pqNewPriorityQ(final Leq leq) {
        return new PriorityQSort(leq);
    }
    
    abstract void pqDeletePriorityQ();
    
    abstract boolean pqInit();
    
    abstract int pqInsert(final Object p0);
    
    abstract Object pqExtractMin();
    
    abstract void pqDelete(final int p0);
    
    abstract Object pqMinimum();
    
    abstract boolean pqIsEmpty();
    
    public interface Leq
    {
        boolean leq(final Object p0, final Object p1);
    }
    
    public static class PQhandleElem
    {
        Object key;
        int node;
    }
    
    public static class PQnode
    {
        int handle;
    }
}
