package org.lwjgl.util.glu.tessellation;

class Dict
{
    DictNode head;
    Object frame;
    DictLeq leq;
    
    private Dict() {
    }
    
    static Dict dictNewDict(final Object frame, final DictLeq leq) {
        final Dict dict = new Dict();
        dict.head = new DictNode();
        dict.head.key = null;
        dict.head.next = dict.head;
        dict.head.prev = dict.head;
        dict.frame = frame;
        dict.leq = leq;
        return dict;
    }
    
    static void dictDeleteDict(final Dict dict) {
        dict.head = null;
        dict.frame = null;
        dict.leq = null;
    }
    
    static DictNode dictInsert(final Dict dict, final Object o) {
        return dictInsertBefore(dict, dict.head, o);
    }
    
    static DictNode dictInsertBefore(final Dict dict, DictNode prev, final Object key) {
        do {
            prev = prev.prev;
        } while (prev.key != null && !dict.leq.leq(dict.frame, prev.key, key));
        final DictNode dictNode = new DictNode();
        dictNode.key = key;
        dictNode.next = prev.next;
        prev.next.prev = dictNode;
        dictNode.prev = prev;
        return prev.next = dictNode;
    }
    
    static Object dictKey(final DictNode dictNode) {
        return dictNode.key;
    }
    
    static DictNode dictSucc(final DictNode dictNode) {
        return dictNode.next;
    }
    
    static DictNode dictPred(final DictNode dictNode) {
        return dictNode.prev;
    }
    
    static DictNode dictMin(final Dict dict) {
        return dict.head.next;
    }
    
    static DictNode dictMax(final Dict dict) {
        return dict.head.prev;
    }
    
    static void dictDelete(final Dict dict, final DictNode dictNode) {
        dictNode.next.prev = dictNode.prev;
        dictNode.prev.next = dictNode.next;
    }
    
    static DictNode dictSearch(final Dict dict, final Object o) {
        DictNode dictNode = dict.head;
        do {
            dictNode = dictNode.next;
        } while (dictNode.key != null && !dict.leq.leq(dict.frame, o, dictNode.key));
        return dictNode;
    }
    
    public interface DictLeq
    {
        boolean leq(final Object p0, final Object p1, final Object p2);
    }
}
