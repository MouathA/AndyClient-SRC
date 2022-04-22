package org.lwjgl.opengl;

import java.nio.*;

final class StateTracker
{
    private ReferencesStack references_stack;
    private final StateStack attrib_stack;
    private boolean insideBeginEnd;
    private final FastIntMap vaoMap;
    
    StateTracker() {
        this.vaoMap = new FastIntMap();
        this.attrib_stack = new StateStack(0);
    }
    
    void init() {
        this.references_stack = new ReferencesStack();
    }
    
    static void setBeginEnd(final ContextCapabilities contextCapabilities, final boolean insideBeginEnd) {
        contextCapabilities.tracker.insideBeginEnd = insideBeginEnd;
    }
    
    boolean isBeginEnd() {
        return this.insideBeginEnd;
    }
    
    static void popAttrib(final ContextCapabilities contextCapabilities) {
        contextCapabilities.tracker.doPopAttrib();
    }
    
    private void doPopAttrib() {
        this.references_stack.popState(this.attrib_stack.popState());
    }
    
    static void pushAttrib(final ContextCapabilities contextCapabilities, final int n) {
        contextCapabilities.tracker.doPushAttrib(n);
    }
    
    private void doPushAttrib(final int n) {
        this.attrib_stack.pushState(n);
        this.references_stack.pushState();
    }
    
    static References getReferences(final ContextCapabilities contextCapabilities) {
        return contextCapabilities.tracker.references_stack.getReferences();
    }
    
    static void bindBuffer(final ContextCapabilities contextCapabilities, final int n, final int n2) {
        final References references = getReferences(contextCapabilities);
        switch (n) {
            case 34962: {
                references.arrayBuffer = n2;
                break;
            }
            case 34963: {
                if (references.vertexArrayObject != 0) {
                    ((VAOState)contextCapabilities.tracker.vaoMap.get(references.vertexArrayObject)).elementArrayBuffer = n2;
                    break;
                }
                references.elementArrayBuffer = n2;
                break;
            }
            case 35051: {
                references.pixelPackBuffer = n2;
                break;
            }
            case 35052: {
                references.pixelUnpackBuffer = n2;
                break;
            }
            case 36671: {
                references.indirectBuffer = n2;
                break;
            }
        }
    }
    
    static void bindVAO(final ContextCapabilities contextCapabilities, final int vertexArrayObject) {
        final FastIntMap vaoMap = contextCapabilities.tracker.vaoMap;
        if (!vaoMap.containsKey(vertexArrayObject)) {
            vaoMap.put(vertexArrayObject, new VAOState(null));
        }
        getReferences(contextCapabilities).vertexArrayObject = vertexArrayObject;
    }
    
    static void deleteVAO(final ContextCapabilities contextCapabilities, final IntBuffer intBuffer) {
        for (int i = intBuffer.position(); i < intBuffer.limit(); ++i) {
            deleteVAO(contextCapabilities, intBuffer.get(i));
        }
    }
    
    static void deleteVAO(final ContextCapabilities contextCapabilities, final int n) {
        contextCapabilities.tracker.vaoMap.remove(n);
        final References references = getReferences(contextCapabilities);
        if (references.vertexArrayObject == n) {
            references.vertexArrayObject = 0;
        }
    }
    
    static int getElementArrayBufferBound(final ContextCapabilities contextCapabilities) {
        final References references = getReferences(contextCapabilities);
        if (references.vertexArrayObject == 0) {
            return references.elementArrayBuffer;
        }
        return ((VAOState)contextCapabilities.tracker.vaoMap.get(references.vertexArrayObject)).elementArrayBuffer;
    }
    
    private static class VAOState
    {
        int elementArrayBuffer;
        
        private VAOState() {
        }
        
        VAOState(final StateTracker$1 object) {
            this();
        }
    }
}
