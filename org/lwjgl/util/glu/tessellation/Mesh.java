package org.lwjgl.util.glu.tessellation;

class Mesh
{
    static final boolean $assertionsDisabled;
    
    private Mesh() {
    }
    
    static GLUhalfEdge MakeEdge(GLUhalfEdge sym) {
        final GLUhalfEdge glUhalfEdge = new GLUhalfEdge(true);
        final GLUhalfEdge glUhalfEdge2 = new GLUhalfEdge(false);
        if (!sym.first) {
            sym = sym.Sym;
        }
        final GLUhalfEdge next = sym.Sym.next;
        glUhalfEdge2.next = next;
        next.Sym.next = glUhalfEdge;
        glUhalfEdge.next = sym;
        sym.Sym.next = glUhalfEdge2;
        glUhalfEdge.Sym = glUhalfEdge2;
        glUhalfEdge.Onext = glUhalfEdge;
        glUhalfEdge.Lnext = glUhalfEdge2;
        glUhalfEdge.Org = null;
        glUhalfEdge.Lface = null;
        glUhalfEdge.winding = 0;
        glUhalfEdge.activeRegion = null;
        glUhalfEdge2.Sym = glUhalfEdge;
        glUhalfEdge2.Onext = glUhalfEdge2;
        glUhalfEdge2.Lnext = glUhalfEdge;
        glUhalfEdge2.Org = null;
        glUhalfEdge2.Lface = null;
        glUhalfEdge2.winding = 0;
        glUhalfEdge2.activeRegion = null;
        return glUhalfEdge;
    }
    
    static void Splice(final GLUhalfEdge lnext, final GLUhalfEdge lnext2) {
        final GLUhalfEdge onext = lnext.Onext;
        final GLUhalfEdge onext2 = lnext2.Onext;
        onext.Sym.Lnext = lnext2;
        onext2.Sym.Lnext = lnext;
        lnext.Onext = onext2;
        lnext2.Onext = onext;
    }
    
    static void MakeVertex(final GLUvertex org, final GLUhalfEdge anEdge, final GLUvertex next) {
        assert org != null;
        final GLUvertex prev = next.prev;
        org.prev = prev;
        prev.next = org;
        org.next = next;
        next.prev = org;
        org.anEdge = anEdge;
        org.data = null;
        GLUhalfEdge onext = anEdge;
        do {
            onext.Org = org;
            onext = onext.Onext;
        } while (onext != anEdge);
    }
    
    static void MakeFace(final GLUface lface, final GLUhalfEdge anEdge, final GLUface next) {
        assert lface != null;
        final GLUface prev = next.prev;
        lface.prev = prev;
        prev.next = lface;
        lface.next = next;
        next.prev = lface;
        lface.anEdge = anEdge;
        lface.data = null;
        lface.trail = null;
        lface.marked = false;
        lface.inside = next.inside;
        GLUhalfEdge lnext = anEdge;
        do {
            lnext.Lface = lface;
            lnext = lnext.Lnext;
        } while (lnext != anEdge);
    }
    
    static void KillEdge(GLUhalfEdge sym) {
        if (!sym.first) {
            sym = sym.Sym;
        }
        final GLUhalfEdge next = sym.next;
        final GLUhalfEdge next2 = sym.Sym.next;
        next.Sym.next = next2;
        next2.Sym.next = next;
    }
    
    static void KillVertex(final GLUvertex glUvertex, final GLUvertex org) {
        GLUhalfEdge glUhalfEdge = null;
        do {
            glUhalfEdge.Org = org;
            glUhalfEdge = glUhalfEdge.Onext;
        } while (glUhalfEdge != (glUhalfEdge = glUvertex.anEdge));
        final GLUvertex prev = glUvertex.prev;
        final GLUvertex next = glUvertex.next;
        next.prev = prev;
        prev.next = next;
    }
    
    static void KillFace(final GLUface glUface, final GLUface lface) {
        GLUhalfEdge glUhalfEdge = null;
        do {
            glUhalfEdge.Lface = lface;
            glUhalfEdge = glUhalfEdge.Lnext;
        } while (glUhalfEdge != (glUhalfEdge = glUface.anEdge));
        final GLUface prev = glUface.prev;
        final GLUface next = glUface.next;
        next.prev = prev;
        prev.next = next;
    }
    
    public static GLUhalfEdge __gl_meshMakeEdge(final GLUmesh glUmesh) {
        final GLUvertex glUvertex = new GLUvertex();
        final GLUvertex glUvertex2 = new GLUvertex();
        final GLUface glUface = new GLUface();
        final GLUhalfEdge makeEdge = MakeEdge(glUmesh.eHead);
        if (makeEdge == null) {
            return null;
        }
        MakeVertex(glUvertex, makeEdge, glUmesh.vHead);
        MakeVertex(glUvertex2, makeEdge.Sym, glUmesh.vHead);
        MakeFace(glUface, makeEdge, glUmesh.fHead);
        return makeEdge;
    }
    
    public static boolean __gl_meshSplice(final GLUhalfEdge glUhalfEdge, final GLUhalfEdge glUhalfEdge2) {
        if (glUhalfEdge == glUhalfEdge2) {
            return true;
        }
        if (glUhalfEdge2.Org != glUhalfEdge.Org) {
            KillVertex(glUhalfEdge2.Org, glUhalfEdge.Org);
        }
        if (glUhalfEdge2.Lface != glUhalfEdge.Lface) {
            KillFace(glUhalfEdge2.Lface, glUhalfEdge.Lface);
        }
        Splice(glUhalfEdge2, glUhalfEdge);
        return true;
    }
    
    static boolean __gl_meshDelete(final GLUhalfEdge glUhalfEdge) {
        final GLUhalfEdge sym = glUhalfEdge.Sym;
        if (glUhalfEdge.Lface != glUhalfEdge.Sym.Lface) {
            KillFace(glUhalfEdge.Lface, glUhalfEdge.Sym.Lface);
        }
        if (glUhalfEdge.Onext == glUhalfEdge) {
            KillVertex(glUhalfEdge.Org, null);
        }
        else {
            glUhalfEdge.Sym.Lface.anEdge = glUhalfEdge.Sym.Lnext;
            glUhalfEdge.Org.anEdge = glUhalfEdge.Onext;
            Splice(glUhalfEdge, glUhalfEdge.Sym.Lnext);
        }
        if (sym.Onext == sym) {
            KillVertex(sym.Org, null);
            KillFace(sym.Lface, null);
        }
        else {
            glUhalfEdge.Lface.anEdge = sym.Sym.Lnext;
            sym.Org.anEdge = sym.Onext;
            Splice(sym, sym.Sym.Lnext);
        }
        KillEdge(glUhalfEdge);
        return true;
    }
    
    static GLUhalfEdge __gl_meshAddEdgeVertex(final GLUhalfEdge glUhalfEdge) {
        final GLUhalfEdge makeEdge = MakeEdge(glUhalfEdge);
        final GLUhalfEdge sym = makeEdge.Sym;
        Splice(makeEdge, glUhalfEdge.Lnext);
        makeEdge.Org = glUhalfEdge.Sym.Org;
        MakeVertex(new GLUvertex(), sym, makeEdge.Org);
        final GLUhalfEdge glUhalfEdge2 = makeEdge;
        final GLUhalfEdge glUhalfEdge3 = sym;
        final GLUface lface = glUhalfEdge.Lface;
        glUhalfEdge3.Lface = lface;
        glUhalfEdge2.Lface = lface;
        return makeEdge;
    }
    
    public static GLUhalfEdge __gl_meshSplitEdge(final GLUhalfEdge glUhalfEdge) {
        final GLUhalfEdge sym = __gl_meshAddEdgeVertex(glUhalfEdge).Sym;
        Splice(glUhalfEdge.Sym, glUhalfEdge.Sym.Sym.Lnext);
        Splice(glUhalfEdge.Sym, sym);
        glUhalfEdge.Sym.Org = sym.Org;
        sym.Sym.Org.anEdge = sym.Sym;
        sym.Sym.Lface = glUhalfEdge.Sym.Lface;
        sym.winding = glUhalfEdge.winding;
        sym.Sym.winding = glUhalfEdge.Sym.winding;
        return sym;
    }
    
    static GLUhalfEdge __gl_meshConnect(final GLUhalfEdge glUhalfEdge, final GLUhalfEdge glUhalfEdge2) {
        final GLUhalfEdge makeEdge = MakeEdge(glUhalfEdge);
        final GLUhalfEdge sym = makeEdge.Sym;
        if (glUhalfEdge2.Lface != glUhalfEdge.Lface) {
            KillFace(glUhalfEdge2.Lface, glUhalfEdge.Lface);
        }
        Splice(makeEdge, glUhalfEdge.Lnext);
        Splice(sym, glUhalfEdge2);
        makeEdge.Org = glUhalfEdge.Sym.Org;
        sym.Org = glUhalfEdge2.Org;
        final GLUhalfEdge glUhalfEdge3 = makeEdge;
        final GLUhalfEdge glUhalfEdge4 = sym;
        final GLUface lface = glUhalfEdge.Lface;
        glUhalfEdge4.Lface = lface;
        glUhalfEdge3.Lface = lface;
        glUhalfEdge.Lface.anEdge = sym;
        return makeEdge;
    }
    
    static void __gl_meshZapFace(final GLUface glUface) {
        final GLUhalfEdge anEdge = glUface.anEdge;
        GLUhalfEdge glUhalfEdge = anEdge.Lnext;
        GLUhalfEdge glUhalfEdge2;
        do {
            glUhalfEdge2 = glUhalfEdge;
            glUhalfEdge = glUhalfEdge2.Lnext;
            glUhalfEdge2.Lface = null;
            if (glUhalfEdge2.Sym.Lface == null) {
                if (glUhalfEdge2.Onext == glUhalfEdge2) {
                    KillVertex(glUhalfEdge2.Org, null);
                }
                else {
                    glUhalfEdge2.Org.anEdge = glUhalfEdge2.Onext;
                    Splice(glUhalfEdge2, glUhalfEdge2.Sym.Lnext);
                }
                final GLUhalfEdge sym = glUhalfEdge2.Sym;
                if (sym.Onext == sym) {
                    KillVertex(sym.Org, null);
                }
                else {
                    sym.Org.anEdge = sym.Onext;
                    Splice(sym, sym.Sym.Lnext);
                }
                KillEdge(glUhalfEdge2);
            }
        } while (glUhalfEdge2 != anEdge);
        final GLUface prev = glUface.prev;
        final GLUface next = glUface.next;
        next.prev = prev;
        prev.next = next;
    }
    
    public static GLUmesh __gl_meshNewMesh() {
        final GLUmesh glUmesh = new GLUmesh();
        final GLUvertex vHead = glUmesh.vHead;
        final GLUface fHead = glUmesh.fHead;
        final GLUhalfEdge eHead = glUmesh.eHead;
        final GLUhalfEdge eHeadSym = glUmesh.eHeadSym;
        final GLUvertex glUvertex = vHead;
        final GLUvertex glUvertex2 = vHead;
        final GLUvertex glUvertex3 = vHead;
        glUvertex2.prev = glUvertex3;
        glUvertex.next = glUvertex3;
        vHead.anEdge = null;
        vHead.data = null;
        final GLUface glUface = fHead;
        final GLUface glUface2 = fHead;
        final GLUface glUface3 = fHead;
        glUface2.prev = glUface3;
        glUface.next = glUface3;
        fHead.anEdge = null;
        fHead.data = null;
        fHead.trail = null;
        fHead.marked = false;
        fHead.inside = false;
        eHead.next = eHead;
        eHead.Sym = eHeadSym;
        eHead.Onext = null;
        eHead.Lnext = null;
        eHead.Org = null;
        eHead.Lface = null;
        eHead.winding = 0;
        eHead.activeRegion = null;
        eHeadSym.next = eHeadSym;
        eHeadSym.Sym = eHead;
        eHeadSym.Onext = null;
        eHeadSym.Lnext = null;
        eHeadSym.Org = null;
        eHeadSym.Lface = null;
        eHeadSym.winding = 0;
        eHeadSym.activeRegion = null;
        return glUmesh;
    }
    
    static GLUmesh __gl_meshUnion(final GLUmesh glUmesh, final GLUmesh glUmesh2) {
        final GLUface fHead = glUmesh.fHead;
        final GLUvertex vHead = glUmesh.vHead;
        final GLUhalfEdge eHead = glUmesh.eHead;
        final GLUface fHead2 = glUmesh2.fHead;
        final GLUvertex vHead2 = glUmesh2.vHead;
        final GLUhalfEdge eHead2 = glUmesh2.eHead;
        if (fHead2.next != fHead2) {
            fHead.prev.next = fHead2.next;
            fHead2.next.prev = fHead.prev;
            fHead2.prev.next = fHead;
            fHead.prev = fHead2.prev;
        }
        if (vHead2.next != vHead2) {
            vHead.prev.next = vHead2.next;
            vHead2.next.prev = vHead.prev;
            vHead2.prev.next = vHead;
            vHead.prev = vHead2.prev;
        }
        if (eHead2.next != eHead2) {
            eHead.Sym.next.Sym.next = eHead2.next;
            eHead2.next.Sym.next = eHead.Sym.next;
            eHead2.Sym.next.Sym.next = eHead;
            eHead.Sym.next = eHead2.Sym.next;
        }
        return glUmesh;
    }
    
    static void __gl_meshDeleteMeshZap(final GLUmesh glUmesh) {
        final GLUface fHead = glUmesh.fHead;
        while (fHead.next != fHead) {
            __gl_meshZapFace(fHead.next);
        }
        assert glUmesh.vHead.next == glUmesh.vHead;
    }
    
    public static void __gl_meshDeleteMesh(final GLUmesh glUmesh) {
        for (GLUface glUface = glUmesh.fHead.next; glUface != glUmesh.fHead; glUface = glUface.next) {}
        for (GLUvertex glUvertex = glUmesh.vHead.next; glUvertex != glUmesh.vHead; glUvertex = glUvertex.next) {}
        for (GLUhalfEdge glUhalfEdge = glUmesh.eHead.next; glUhalfEdge != glUmesh.eHead; glUhalfEdge = glUhalfEdge.next) {}
    }
    
    public static void __gl_meshCheckMesh(final GLUmesh glUmesh) {
        final GLUface fHead = glUmesh.fHead;
        final GLUvertex vHead = glUmesh.vHead;
        final GLUhalfEdge eHead = glUmesh.eHead;
        GLUface glUface = fHead;
        GLUface next = null;
    Label_0021:
        while ((next = glUface.next) != fHead) {
            assert next.prev == glUface;
            GLUhalfEdge glUhalfEdge = next.anEdge;
            while (Mesh.$assertionsDisabled || glUhalfEdge.Sym != glUhalfEdge) {
                assert glUhalfEdge.Sym.Sym == glUhalfEdge;
                assert glUhalfEdge.Lnext.Onext.Sym == glUhalfEdge;
                assert glUhalfEdge.Onext.Sym.Lnext == glUhalfEdge;
                assert glUhalfEdge.Lface == next;
                glUhalfEdge = glUhalfEdge.Lnext;
                if (glUhalfEdge == next.anEdge) {
                    glUface = next;
                    continue Label_0021;
                }
            }
            throw new AssertionError();
        }
        assert next.prev == glUface && next.anEdge == null && next.data == null;
        GLUvertex glUvertex = vHead;
        GLUvertex next2;
        Label_0269:
        while ((next2 = glUvertex.next) != vHead) {
            assert next2.prev == glUvertex;
            GLUhalfEdge glUhalfEdge2 = next2.anEdge;
            while (Mesh.$assertionsDisabled || glUhalfEdge2.Sym != glUhalfEdge2) {
                assert glUhalfEdge2.Sym.Sym == glUhalfEdge2;
                assert glUhalfEdge2.Lnext.Onext.Sym == glUhalfEdge2;
                assert glUhalfEdge2.Onext.Sym.Lnext == glUhalfEdge2;
                assert glUhalfEdge2.Org == next2;
                glUhalfEdge2 = glUhalfEdge2.Onext;
                if (glUhalfEdge2 == next2.anEdge) {
                    glUvertex = next2;
                    continue Label_0269;
                }
            }
            throw new AssertionError();
        }
        assert next2.prev == glUvertex && next2.anEdge == null && next2.data == null;
        GLUhalfEdge glUhalfEdge3;
        GLUhalfEdge next3;
        for (glUhalfEdge3 = eHead; (next3 = glUhalfEdge3.next) != eHead; glUhalfEdge3 = next3) {
            assert next3.Sym.next == glUhalfEdge3.Sym;
            assert next3.Sym != next3;
            assert next3.Sym.Sym == next3;
            assert next3.Org != null;
            assert next3.Sym.Org != null;
            assert next3.Lnext.Onext.Sym == next3;
            assert next3.Onext.Sym.Lnext == next3;
        }
        assert next3.Sym.next == glUhalfEdge3.Sym && next3.Sym == glUmesh.eHeadSym && next3.Sym.Sym == next3 && next3.Org == null && next3.Sym.Org == null && next3.Lface == null && next3.Sym.Lface == null;
    }
    
    static {
        $assertionsDisabled = !Mesh.class.desiredAssertionStatus();
    }
}
