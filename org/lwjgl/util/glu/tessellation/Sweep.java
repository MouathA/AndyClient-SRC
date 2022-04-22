package org.lwjgl.util.glu.tessellation;

class Sweep
{
    private static final boolean TOLERANCE_NONZERO = false;
    private static final double SENTINEL_COORD = 4.0E150;
    static final boolean $assertionsDisabled;
    
    private Sweep() {
    }
    
    private static void DebugEvent(final GLUtessellatorImpl glUtessellatorImpl) {
    }
    
    private static void AddWinding(final GLUhalfEdge glUhalfEdge, final GLUhalfEdge glUhalfEdge2) {
        glUhalfEdge.winding += glUhalfEdge2.winding;
        final GLUhalfEdge sym = glUhalfEdge.Sym;
        sym.winding += glUhalfEdge2.Sym.winding;
    }
    
    private static ActiveRegion RegionBelow(final ActiveRegion activeRegion) {
        return (ActiveRegion)Dict.dictKey(Dict.dictPred(activeRegion.nodeUp));
    }
    
    private static ActiveRegion RegionAbove(final ActiveRegion activeRegion) {
        return (ActiveRegion)Dict.dictKey(Dict.dictSucc(activeRegion.nodeUp));
    }
    
    static boolean EdgeLeq(final GLUtessellatorImpl glUtessellatorImpl, final ActiveRegion activeRegion, final ActiveRegion activeRegion2) {
        final GLUvertex event = glUtessellatorImpl.event;
        final GLUhalfEdge eUp = activeRegion.eUp;
        final GLUhalfEdge eUp2 = activeRegion2.eUp;
        if (eUp.Sym.Org == event) {
            if (eUp2.Sym.Org != event) {
                return Geom.EdgeSign(eUp2.Sym.Org, event, eUp2.Org) <= 0.0;
            }
            if (Geom.VertLeq(eUp.Org, eUp2.Org)) {
                return Geom.EdgeSign(eUp2.Sym.Org, eUp.Org, eUp2.Org) <= 0.0;
            }
            return Geom.EdgeSign(eUp.Sym.Org, eUp2.Org, eUp.Org) >= 0.0;
        }
        else {
            if (eUp2.Sym.Org == event) {
                return Geom.EdgeSign(eUp.Sym.Org, event, eUp.Org) >= 0.0;
            }
            return Geom.EdgeEval(eUp.Sym.Org, event, eUp.Org) >= Geom.EdgeEval(eUp2.Sym.Org, event, eUp2.Org);
        }
    }
    
    static void DeleteRegion(final GLUtessellatorImpl glUtessellatorImpl, final ActiveRegion activeRegion) {
        if (activeRegion.fixUpperEdge && !Sweep.$assertionsDisabled && activeRegion.eUp.winding != 0) {
            throw new AssertionError();
        }
        activeRegion.eUp.activeRegion = null;
        Dict.dictDelete(glUtessellatorImpl.dict, activeRegion.nodeUp);
    }
    
    static boolean FixUpperEdge(final ActiveRegion activeRegion, final GLUhalfEdge eUp) {
        assert activeRegion.fixUpperEdge;
        if (!Mesh.__gl_meshDelete(activeRegion.eUp)) {
            return false;
        }
        activeRegion.fixUpperEdge = false;
        activeRegion.eUp = eUp;
        eUp.activeRegion = activeRegion;
        return true;
    }
    
    static ActiveRegion TopLeftRegion(ActiveRegion activeRegion) {
        do {
            activeRegion = RegionAbove(activeRegion);
        } while (activeRegion.eUp.Org == activeRegion.eUp.Org);
        if (activeRegion.fixUpperEdge) {
            final GLUhalfEdge _gl_meshConnect = Mesh.__gl_meshConnect(RegionBelow(activeRegion).eUp.Sym, activeRegion.eUp.Lnext);
            if (_gl_meshConnect == null) {
                return null;
            }
            if (!FixUpperEdge(activeRegion, _gl_meshConnect)) {
                return null;
            }
            activeRegion = RegionAbove(activeRegion);
        }
        return activeRegion;
    }
    
    static ActiveRegion TopRightRegion(ActiveRegion regionAbove) {
        do {
            regionAbove = RegionAbove(regionAbove);
        } while (regionAbove.eUp.Sym.Org == regionAbove.eUp.Sym.Org);
        return regionAbove;
    }
    
    static ActiveRegion AddRegionBelow(final GLUtessellatorImpl glUtessellatorImpl, final ActiveRegion activeRegion, final GLUhalfEdge eUp) {
        final ActiveRegion activeRegion2 = new ActiveRegion();
        if (activeRegion2 == null) {
            throw new RuntimeException();
        }
        activeRegion2.eUp = eUp;
        activeRegion2.nodeUp = Dict.dictInsertBefore(glUtessellatorImpl.dict, activeRegion.nodeUp, activeRegion2);
        if (activeRegion2.nodeUp == null) {
            throw new RuntimeException();
        }
        activeRegion2.fixUpperEdge = false;
        activeRegion2.sentinel = false;
        activeRegion2.dirty = false;
        return eUp.activeRegion = activeRegion2;
    }
    
    static boolean IsWindingInside(final GLUtessellatorImpl glUtessellatorImpl, final int n) {
        switch (glUtessellatorImpl.windingRule) {
            case 100130: {
                return (n & 0x1) != 0x0;
            }
            case 100131: {
                return n != 0;
            }
            case 100132: {
                return n > 0;
            }
            case 100133: {
                return n < 0;
            }
            case 100134: {
                return n >= 2 || n <= -2;
            }
            default: {
                throw new InternalError();
            }
        }
    }
    
    static void ComputeWinding(final GLUtessellatorImpl glUtessellatorImpl, final ActiveRegion activeRegion) {
        activeRegion.windingNumber = RegionAbove(activeRegion).windingNumber + activeRegion.eUp.winding;
        activeRegion.inside = IsWindingInside(glUtessellatorImpl, activeRegion.windingNumber);
    }
    
    static void FinishRegion(final GLUtessellatorImpl glUtessellatorImpl, final ActiveRegion activeRegion) {
        final GLUhalfEdge eUp = activeRegion.eUp;
        final GLUface lface = eUp.Lface;
        lface.inside = activeRegion.inside;
        lface.anEdge = eUp;
        DeleteRegion(glUtessellatorImpl, activeRegion);
    }
    
    static GLUhalfEdge FinishLeftRegions(final GLUtessellatorImpl glUtessellatorImpl, final ActiveRegion activeRegion, final ActiveRegion activeRegion2) {
        ActiveRegion activeRegion3 = activeRegion;
        GLUhalfEdge glUhalfEdge = activeRegion.eUp;
        while (activeRegion3 != activeRegion2) {
            activeRegion3.fixUpperEdge = false;
            final ActiveRegion regionBelow = RegionBelow(activeRegion3);
            GLUhalfEdge glUhalfEdge2 = regionBelow.eUp;
            if (glUhalfEdge2.Org != glUhalfEdge.Org) {
                if (!regionBelow.fixUpperEdge) {
                    FinishRegion(glUtessellatorImpl, activeRegion3);
                    break;
                }
                glUhalfEdge2 = Mesh.__gl_meshConnect(glUhalfEdge.Onext.Sym, glUhalfEdge2.Sym);
                if (glUhalfEdge2 == null) {
                    throw new RuntimeException();
                }
                if (!FixUpperEdge(regionBelow, glUhalfEdge2)) {
                    throw new RuntimeException();
                }
            }
            if (glUhalfEdge.Onext != glUhalfEdge2) {
                if (!Mesh.__gl_meshSplice(glUhalfEdge2.Sym.Lnext, glUhalfEdge2)) {
                    throw new RuntimeException();
                }
                if (!Mesh.__gl_meshSplice(glUhalfEdge, glUhalfEdge2)) {
                    throw new RuntimeException();
                }
            }
            FinishRegion(glUtessellatorImpl, activeRegion3);
            glUhalfEdge = regionBelow.eUp;
            activeRegion3 = regionBelow;
        }
        return glUhalfEdge;
    }
    
    static void AddRightEdges(final GLUtessellatorImpl glUtessellatorImpl, final ActiveRegion activeRegion, final GLUhalfEdge glUhalfEdge, final GLUhalfEdge glUhalfEdge2, GLUhalfEdge onext, final boolean b) {
        GLUhalfEdge onext2 = glUhalfEdge;
        while (Sweep.$assertionsDisabled || Geom.VertLeq(onext2.Org, onext2.Sym.Org)) {
            AddRegionBelow(glUtessellatorImpl, activeRegion, onext2.Sym);
            onext2 = onext2.Onext;
            if (onext2 == glUhalfEdge2) {
                if (onext == null) {
                    onext = RegionBelow(activeRegion).eUp.Sym.Onext;
                }
                ActiveRegion activeRegion2 = activeRegion;
                GLUhalfEdge glUhalfEdge3 = onext;
                while (true) {
                    final ActiveRegion regionBelow = RegionBelow(activeRegion2);
                    final GLUhalfEdge sym = regionBelow.eUp.Sym;
                    if (sym.Org != glUhalfEdge3.Org) {
                        activeRegion2.dirty = true;
                        assert activeRegion2.windingNumber - sym.winding == regionBelow.windingNumber;
                        if (b) {
                            WalkDirtyRegions(glUtessellatorImpl, activeRegion2);
                        }
                        return;
                    }
                    else {
                        if (sym.Onext != glUhalfEdge3) {
                            if (!Mesh.__gl_meshSplice(sym.Sym.Lnext, sym)) {
                                throw new RuntimeException();
                            }
                            if (!Mesh.__gl_meshSplice(glUhalfEdge3.Sym.Lnext, sym)) {
                                throw new RuntimeException();
                            }
                        }
                        regionBelow.windingNumber = activeRegion2.windingNumber - sym.winding;
                        regionBelow.inside = IsWindingInside(glUtessellatorImpl, regionBelow.windingNumber);
                        activeRegion2.dirty = true;
                        if (!false && CheckForRightSplice(glUtessellatorImpl, activeRegion2)) {
                            AddWinding(sym, glUhalfEdge3);
                            DeleteRegion(glUtessellatorImpl, activeRegion2);
                            if (!Mesh.__gl_meshDelete(glUhalfEdge3)) {
                                throw new RuntimeException();
                            }
                        }
                        activeRegion2 = regionBelow;
                        glUhalfEdge3 = sym;
                    }
                }
            }
        }
        throw new AssertionError();
    }
    
    static void CallCombine(final GLUtessellatorImpl glUtessellatorImpl, final GLUvertex glUvertex, final Object[] array, final float[] array2, final boolean b) {
        final double[] array3 = { glUvertex.coords[0], glUvertex.coords[1], glUvertex.coords[2] };
        final Object[] array4 = { null };
        glUtessellatorImpl.callCombineOrCombineData(array3, array, array2, array4);
        glUvertex.data = array4[0];
        if (glUvertex.data == null) {
            if (!b) {
                glUvertex.data = array[0];
            }
            else if (!glUtessellatorImpl.fatalError) {
                glUtessellatorImpl.callErrorOrErrorData(100156);
                glUtessellatorImpl.fatalError = true;
            }
        }
    }
    
    static void SpliceMergeVertices(final GLUtessellatorImpl glUtessellatorImpl, final GLUhalfEdge glUhalfEdge, final GLUhalfEdge glUhalfEdge2) {
        final Object[] array = new Object[4];
        final float[] array2 = { 0.5f, 0.5f, 0.0f, 0.0f };
        array[0] = glUhalfEdge.Org.data;
        array[1] = glUhalfEdge2.Org.data;
        CallCombine(glUtessellatorImpl, glUhalfEdge.Org, array, array2, false);
        if (!Mesh.__gl_meshSplice(glUhalfEdge, glUhalfEdge2)) {
            throw new RuntimeException();
        }
    }
    
    static void VertexWeights(final GLUvertex glUvertex, final GLUvertex glUvertex2, final GLUvertex glUvertex3, final float[] array) {
        final double vertL1dist = Geom.VertL1dist(glUvertex2, glUvertex);
        final double vertL1dist2 = Geom.VertL1dist(glUvertex3, glUvertex);
        array[0] = (float)(0.5 * vertL1dist2 / (vertL1dist + vertL1dist2));
        array[1] = (float)(0.5 * vertL1dist / (vertL1dist + vertL1dist2));
        final double[] coords = glUvertex.coords;
        final int n = 0;
        coords[n] += array[0] * glUvertex2.coords[0] + array[1] * glUvertex3.coords[0];
        final double[] coords2 = glUvertex.coords;
        final int n2 = 1;
        coords2[n2] += array[0] * glUvertex2.coords[1] + array[1] * glUvertex3.coords[1];
        final double[] coords3 = glUvertex.coords;
        final int n3 = 2;
        coords3[n3] += array[0] * glUvertex2.coords[2] + array[1] * glUvertex3.coords[2];
    }
    
    static void GetIntersectData(final GLUtessellatorImpl glUtessellatorImpl, final GLUvertex glUvertex, final GLUvertex glUvertex2, final GLUvertex glUvertex3, final GLUvertex glUvertex4, final GLUvertex glUvertex5) {
        final Object[] array = new Object[4];
        final float[] array2 = new float[4];
        final float[] array3 = new float[2];
        final float[] array4 = new float[2];
        array[0] = glUvertex2.data;
        array[1] = glUvertex3.data;
        array[2] = glUvertex4.data;
        array[3] = glUvertex5.data;
        final double[] coords = glUvertex.coords;
        final int n = 0;
        final double[] coords2 = glUvertex.coords;
        final int n2 = 1;
        final double[] coords3 = glUvertex.coords;
        final int n3 = 2;
        final double n4 = 0.0;
        coords3[n3] = n4;
        coords[n] = (coords2[n2] = n4);
        VertexWeights(glUvertex, glUvertex2, glUvertex3, array3);
        VertexWeights(glUvertex, glUvertex4, glUvertex5, array4);
        System.arraycopy(array3, 0, array2, 0, 2);
        System.arraycopy(array4, 0, array2, 2, 2);
        CallCombine(glUtessellatorImpl, glUvertex, array, array2, true);
    }
    
    static boolean CheckForRightSplice(final GLUtessellatorImpl glUtessellatorImpl, final ActiveRegion activeRegion) {
        final ActiveRegion regionBelow = RegionBelow(activeRegion);
        final GLUhalfEdge eUp = activeRegion.eUp;
        final GLUhalfEdge eUp2 = regionBelow.eUp;
        if (Geom.VertLeq(eUp.Org, eUp2.Org)) {
            if (Geom.EdgeSign(eUp2.Sym.Org, eUp.Org, eUp2.Org) > 0.0) {
                return false;
            }
            if (!Geom.VertEq(eUp.Org, eUp2.Org)) {
                if (Mesh.__gl_meshSplitEdge(eUp2.Sym) == null) {
                    throw new RuntimeException();
                }
                if (!Mesh.__gl_meshSplice(eUp, eUp2.Sym.Lnext)) {
                    throw new RuntimeException();
                }
                final ActiveRegion activeRegion2 = regionBelow;
                final boolean b = true;
                activeRegion2.dirty = b;
                activeRegion.dirty = b;
            }
            else if (eUp.Org != eUp2.Org) {
                glUtessellatorImpl.pq.pqDelete(eUp.Org.pqHandle);
                SpliceMergeVertices(glUtessellatorImpl, eUp2.Sym.Lnext, eUp);
            }
        }
        else {
            if (Geom.EdgeSign(eUp.Sym.Org, eUp2.Org, eUp.Org) < 0.0) {
                return false;
            }
            final ActiveRegion regionAbove = RegionAbove(activeRegion);
            final boolean b2 = true;
            activeRegion.dirty = b2;
            regionAbove.dirty = b2;
            if (Mesh.__gl_meshSplitEdge(eUp.Sym) == null) {
                throw new RuntimeException();
            }
            if (!Mesh.__gl_meshSplice(eUp2.Sym.Lnext, eUp)) {
                throw new RuntimeException();
            }
        }
        return true;
    }
    
    static boolean CheckForLeftSplice(final GLUtessellatorImpl glUtessellatorImpl, final ActiveRegion activeRegion) {
        final ActiveRegion regionBelow = RegionBelow(activeRegion);
        final GLUhalfEdge eUp = activeRegion.eUp;
        final GLUhalfEdge eUp2 = regionBelow.eUp;
        assert !Geom.VertEq(eUp.Sym.Org, eUp2.Sym.Org);
        if (Geom.VertLeq(eUp.Sym.Org, eUp2.Sym.Org)) {
            if (Geom.EdgeSign(eUp.Sym.Org, eUp2.Sym.Org, eUp.Org) < 0.0) {
                return false;
            }
            final ActiveRegion regionAbove = RegionAbove(activeRegion);
            final boolean b = true;
            activeRegion.dirty = b;
            regionAbove.dirty = b;
            final GLUhalfEdge _gl_meshSplitEdge = Mesh.__gl_meshSplitEdge(eUp);
            if (_gl_meshSplitEdge == null) {
                throw new RuntimeException();
            }
            if (!Mesh.__gl_meshSplice(eUp2.Sym, _gl_meshSplitEdge)) {
                throw new RuntimeException();
            }
            _gl_meshSplitEdge.Lface.inside = activeRegion.inside;
        }
        else {
            if (Geom.EdgeSign(eUp2.Sym.Org, eUp.Sym.Org, eUp2.Org) > 0.0) {
                return false;
            }
            final ActiveRegion activeRegion2 = regionBelow;
            final boolean b2 = true;
            activeRegion2.dirty = b2;
            activeRegion.dirty = b2;
            final GLUhalfEdge _gl_meshSplitEdge2 = Mesh.__gl_meshSplitEdge(eUp2);
            if (_gl_meshSplitEdge2 == null) {
                throw new RuntimeException();
            }
            if (!Mesh.__gl_meshSplice(eUp.Lnext, eUp2.Sym)) {
                throw new RuntimeException();
            }
            _gl_meshSplitEdge2.Sym.Lface.inside = activeRegion.inside;
        }
        return true;
    }
    
    static boolean CheckForIntersect(final GLUtessellatorImpl glUtessellatorImpl, ActiveRegion activeRegion) {
        final ActiveRegion regionBelow = RegionBelow(activeRegion);
        final GLUhalfEdge eUp = activeRegion.eUp;
        final GLUhalfEdge eUp2 = regionBelow.eUp;
        final GLUvertex org = eUp.Org;
        final GLUvertex org2 = eUp2.Org;
        final GLUvertex org3 = eUp.Sym.Org;
        final GLUvertex org4 = eUp2.Sym.Org;
        final GLUvertex glUvertex = new GLUvertex();
        assert !Geom.VertEq(org4, org3);
        assert Geom.EdgeSign(org3, glUtessellatorImpl.event, org) <= 0.0;
        assert Geom.EdgeSign(org4, glUtessellatorImpl.event, org2) >= 0.0;
        assert org != glUtessellatorImpl.event && org2 != glUtessellatorImpl.event;
        assert !activeRegion.fixUpperEdge && !regionBelow.fixUpperEdge;
        if (org == org2) {
            return false;
        }
        if (Math.min(org.t, org3.t) > Math.max(org2.t, org4.t)) {
            return false;
        }
        if (Geom.VertLeq(org, org2)) {
            if (Geom.EdgeSign(org4, org, org2) > 0.0) {
                return false;
            }
        }
        else if (Geom.EdgeSign(org3, org2, org) < 0.0) {
            return false;
        }
        DebugEvent(glUtessellatorImpl);
        Geom.EdgeIntersect(org3, org, org4, org2, glUvertex);
        assert Math.min(org.t, org3.t) <= glUvertex.t;
        assert glUvertex.t <= Math.max(org2.t, org4.t);
        assert Math.min(org4.s, org3.s) <= glUvertex.s;
        assert glUvertex.s <= Math.max(org2.s, org.s);
        if (Geom.VertLeq(glUvertex, glUtessellatorImpl.event)) {
            glUvertex.s = glUtessellatorImpl.event.s;
            glUvertex.t = glUtessellatorImpl.event.t;
        }
        final GLUvertex glUvertex2 = Geom.VertLeq(org, org2) ? org : org2;
        if (Geom.VertLeq(glUvertex2, glUvertex)) {
            glUvertex.s = glUvertex2.s;
            glUvertex.t = glUvertex2.t;
        }
        if (Geom.VertEq(glUvertex, org) || Geom.VertEq(glUvertex, org2)) {
            CheckForRightSplice(glUtessellatorImpl, activeRegion);
            return false;
        }
        if ((!Geom.VertEq(org3, glUtessellatorImpl.event) && Geom.EdgeSign(org3, glUtessellatorImpl.event, glUvertex) >= 0.0) || (!Geom.VertEq(org4, glUtessellatorImpl.event) && Geom.EdgeSign(org4, glUtessellatorImpl.event, glUvertex) <= 0.0)) {
            if (org4 == glUtessellatorImpl.event) {
                if (Mesh.__gl_meshSplitEdge(eUp.Sym) == null) {
                    throw new RuntimeException();
                }
                if (!Mesh.__gl_meshSplice(eUp2.Sym, eUp)) {
                    throw new RuntimeException();
                }
                activeRegion = TopLeftRegion(activeRegion);
                if (activeRegion == null) {
                    throw new RuntimeException();
                }
                final GLUhalfEdge eUp3 = RegionBelow(activeRegion).eUp;
                FinishLeftRegions(glUtessellatorImpl, RegionBelow(activeRegion), regionBelow);
                AddRightEdges(glUtessellatorImpl, activeRegion, eUp3.Sym.Lnext, eUp3, eUp3, true);
                return true;
            }
            else {
                if (org3 != glUtessellatorImpl.event) {
                    if (Geom.EdgeSign(org3, glUtessellatorImpl.event, glUvertex) >= 0.0) {
                        final ActiveRegion regionAbove = RegionAbove(activeRegion);
                        final ActiveRegion activeRegion2 = activeRegion;
                        final boolean b = true;
                        activeRegion2.dirty = b;
                        regionAbove.dirty = b;
                        if (Mesh.__gl_meshSplitEdge(eUp.Sym) == null) {
                            throw new RuntimeException();
                        }
                        eUp.Org.s = glUtessellatorImpl.event.s;
                        eUp.Org.t = glUtessellatorImpl.event.t;
                    }
                    if (Geom.EdgeSign(org4, glUtessellatorImpl.event, glUvertex) <= 0.0) {
                        final ActiveRegion activeRegion3 = activeRegion;
                        final ActiveRegion activeRegion4 = regionBelow;
                        final boolean b2 = true;
                        activeRegion4.dirty = b2;
                        activeRegion3.dirty = b2;
                        if (Mesh.__gl_meshSplitEdge(eUp2.Sym) == null) {
                            throw new RuntimeException();
                        }
                        eUp2.Org.s = glUtessellatorImpl.event.s;
                        eUp2.Org.t = glUtessellatorImpl.event.t;
                    }
                    return false;
                }
                if (Mesh.__gl_meshSplitEdge(eUp2.Sym) == null) {
                    throw new RuntimeException();
                }
                if (!Mesh.__gl_meshSplice(eUp.Lnext, eUp2.Sym.Lnext)) {
                    throw new RuntimeException();
                }
                final ActiveRegion activeRegion5 = activeRegion;
                activeRegion = TopRightRegion(activeRegion);
                final GLUhalfEdge onext = RegionBelow(activeRegion).eUp.Sym.Onext;
                activeRegion5.eUp = eUp2.Sym.Lnext;
                AddRightEdges(glUtessellatorImpl, activeRegion, FinishLeftRegions(glUtessellatorImpl, activeRegion5, null).Onext, eUp.Sym.Onext, onext, true);
                return true;
            }
        }
        else {
            if (Mesh.__gl_meshSplitEdge(eUp.Sym) == null) {
                throw new RuntimeException();
            }
            if (Mesh.__gl_meshSplitEdge(eUp2.Sym) == null) {
                throw new RuntimeException();
            }
            if (!Mesh.__gl_meshSplice(eUp2.Sym.Lnext, eUp)) {
                throw new RuntimeException();
            }
            eUp.Org.s = glUvertex.s;
            eUp.Org.t = glUvertex.t;
            eUp.Org.pqHandle = glUtessellatorImpl.pq.pqInsert(eUp.Org);
            if (eUp.Org.pqHandle == Long.MAX_VALUE) {
                glUtessellatorImpl.pq.pqDeletePriorityQ();
                glUtessellatorImpl.pq = null;
                throw new RuntimeException();
            }
            GetIntersectData(glUtessellatorImpl, eUp.Org, org, org3, org2, org4);
            final ActiveRegion regionAbove2 = RegionAbove(activeRegion);
            final ActiveRegion activeRegion6 = activeRegion;
            final ActiveRegion activeRegion7 = regionBelow;
            final boolean dirty = true;
            activeRegion7.dirty = dirty;
            activeRegion6.dirty = dirty;
            regionAbove2.dirty = dirty;
            return false;
        }
    }
    
    static void WalkDirtyRegions(final GLUtessellatorImpl glUtessellatorImpl, ActiveRegion activeRegion) {
        ActiveRegion activeRegion2 = RegionBelow(activeRegion);
        while (true) {
            if (activeRegion2.dirty) {
                activeRegion = activeRegion2;
                activeRegion2 = RegionBelow(activeRegion2);
            }
            else {
                if (!activeRegion.dirty) {
                    activeRegion2 = activeRegion;
                    activeRegion = RegionAbove(activeRegion);
                    if (activeRegion == null || !activeRegion.dirty) {
                        return;
                    }
                }
                activeRegion.dirty = false;
                GLUhalfEdge glUhalfEdge = activeRegion.eUp;
                GLUhalfEdge glUhalfEdge2 = activeRegion2.eUp;
                if (glUhalfEdge.Sym.Org != glUhalfEdge2.Sym.Org && CheckForLeftSplice(glUtessellatorImpl, activeRegion)) {
                    if (activeRegion2.fixUpperEdge) {
                        DeleteRegion(glUtessellatorImpl, activeRegion2);
                        if (!Mesh.__gl_meshDelete(glUhalfEdge2)) {
                            throw new RuntimeException();
                        }
                        activeRegion2 = RegionBelow(activeRegion);
                        glUhalfEdge2 = activeRegion2.eUp;
                    }
                    else if (activeRegion.fixUpperEdge) {
                        DeleteRegion(glUtessellatorImpl, activeRegion);
                        if (!Mesh.__gl_meshDelete(glUhalfEdge)) {
                            throw new RuntimeException();
                        }
                        activeRegion = RegionAbove(activeRegion2);
                        glUhalfEdge = activeRegion.eUp;
                    }
                }
                if (glUhalfEdge.Org != glUhalfEdge2.Org) {
                    if (glUhalfEdge.Sym.Org != glUhalfEdge2.Sym.Org && !activeRegion.fixUpperEdge && !activeRegion2.fixUpperEdge && (glUhalfEdge.Sym.Org == glUtessellatorImpl.event || glUhalfEdge2.Sym.Org == glUtessellatorImpl.event)) {
                        if (CheckForIntersect(glUtessellatorImpl, activeRegion)) {
                            return;
                        }
                    }
                    else {
                        CheckForRightSplice(glUtessellatorImpl, activeRegion);
                    }
                }
                if (glUhalfEdge.Org != glUhalfEdge2.Org || glUhalfEdge.Sym.Org != glUhalfEdge2.Sym.Org) {
                    continue;
                }
                AddWinding(glUhalfEdge2, glUhalfEdge);
                DeleteRegion(glUtessellatorImpl, activeRegion);
                if (!Mesh.__gl_meshDelete(glUhalfEdge)) {
                    throw new RuntimeException();
                }
                activeRegion = RegionAbove(activeRegion2);
            }
        }
    }
    
    static void ConnectRightVertex(final GLUtessellatorImpl glUtessellatorImpl, ActiveRegion topLeftRegion, GLUhalfEdge finishLeftRegions) {
        GLUhalfEdge glUhalfEdge = finishLeftRegions.Onext;
        final ActiveRegion regionBelow = RegionBelow(topLeftRegion);
        final GLUhalfEdge eUp = topLeftRegion.eUp;
        final GLUhalfEdge eUp2 = regionBelow.eUp;
        if (eUp.Sym.Org != eUp2.Sym.Org) {
            CheckForIntersect(glUtessellatorImpl, topLeftRegion);
        }
        if (Geom.VertEq(eUp.Org, glUtessellatorImpl.event)) {
            if (!Mesh.__gl_meshSplice(glUhalfEdge.Sym.Lnext, eUp)) {
                throw new RuntimeException();
            }
            topLeftRegion = TopLeftRegion(topLeftRegion);
            if (topLeftRegion == null) {
                throw new RuntimeException();
            }
            glUhalfEdge = RegionBelow(topLeftRegion).eUp;
            FinishLeftRegions(glUtessellatorImpl, RegionBelow(topLeftRegion), regionBelow);
        }
        if (Geom.VertEq(eUp2.Org, glUtessellatorImpl.event)) {
            if (!Mesh.__gl_meshSplice(finishLeftRegions, eUp2.Sym.Lnext)) {
                throw new RuntimeException();
            }
            finishLeftRegions = FinishLeftRegions(glUtessellatorImpl, regionBelow, null);
        }
        if (true) {
            AddRightEdges(glUtessellatorImpl, topLeftRegion, finishLeftRegions.Onext, glUhalfEdge, glUhalfEdge, true);
            return;
        }
        GLUhalfEdge lnext;
        if (Geom.VertLeq(eUp2.Org, eUp.Org)) {
            lnext = eUp2.Sym.Lnext;
        }
        else {
            lnext = eUp;
        }
        final GLUhalfEdge _gl_meshConnect = Mesh.__gl_meshConnect(finishLeftRegions.Onext.Sym, lnext);
        if (_gl_meshConnect == null) {
            throw new RuntimeException();
        }
        AddRightEdges(glUtessellatorImpl, topLeftRegion, _gl_meshConnect, _gl_meshConnect.Onext, _gl_meshConnect.Onext, false);
        _gl_meshConnect.Sym.activeRegion.fixUpperEdge = true;
        WalkDirtyRegions(glUtessellatorImpl, topLeftRegion);
    }
    
    static void ConnectLeftDegenerate(final GLUtessellatorImpl glUtessellatorImpl, ActiveRegion topRightRegion, final GLUvertex glUvertex) {
        final GLUhalfEdge eUp = topRightRegion.eUp;
        if (Geom.VertEq(eUp.Org, glUvertex)) {
            assert false;
            SpliceMergeVertices(glUtessellatorImpl, eUp, glUvertex.anEdge);
        }
        else if (!Geom.VertEq(eUp.Sym.Org, glUvertex)) {
            if (Mesh.__gl_meshSplitEdge(eUp.Sym) == null) {
                throw new RuntimeException();
            }
            if (topRightRegion.fixUpperEdge) {
                if (!Mesh.__gl_meshDelete(eUp.Onext)) {
                    throw new RuntimeException();
                }
                topRightRegion.fixUpperEdge = false;
            }
            if (!Mesh.__gl_meshSplice(glUvertex.anEdge, eUp)) {
                throw new RuntimeException();
            }
            SweepEvent(glUtessellatorImpl, glUvertex);
        }
        else {
            assert false;
            topRightRegion = TopRightRegion(topRightRegion);
            final ActiveRegion regionBelow = RegionBelow(topRightRegion);
            GLUhalfEdge glUhalfEdge = regionBelow.eUp.Sym;
            GLUhalfEdge onext;
            final GLUhalfEdge glUhalfEdge2 = onext = glUhalfEdge.Onext;
            if (regionBelow.fixUpperEdge) {
                assert onext != glUhalfEdge;
                DeleteRegion(glUtessellatorImpl, regionBelow);
                if (!Mesh.__gl_meshDelete(glUhalfEdge)) {
                    throw new RuntimeException();
                }
                glUhalfEdge = onext.Sym.Lnext;
            }
            if (!Mesh.__gl_meshSplice(glUvertex.anEdge, glUhalfEdge)) {
                throw new RuntimeException();
            }
            if (!Geom.EdgeGoesLeft(onext)) {
                onext = null;
            }
            AddRightEdges(glUtessellatorImpl, topRightRegion, glUhalfEdge.Onext, glUhalfEdge2, onext, true);
        }
    }
    
    static void ConnectLeftVertex(final GLUtessellatorImpl glUtessellatorImpl, final GLUvertex glUvertex) {
        final ActiveRegion activeRegion = new ActiveRegion();
        activeRegion.eUp = glUvertex.anEdge.Sym;
        final ActiveRegion activeRegion2 = (ActiveRegion)Dict.dictKey(Dict.dictSearch(glUtessellatorImpl.dict, activeRegion));
        final ActiveRegion regionBelow = RegionBelow(activeRegion2);
        final GLUhalfEdge eUp = activeRegion2.eUp;
        final GLUhalfEdge eUp2 = regionBelow.eUp;
        if (Geom.EdgeSign(eUp.Sym.Org, glUvertex, eUp.Org) == 0.0) {
            ConnectLeftDegenerate(glUtessellatorImpl, activeRegion2, glUvertex);
            return;
        }
        final ActiveRegion activeRegion3 = Geom.VertLeq(eUp2.Sym.Org, eUp.Sym.Org) ? activeRegion2 : regionBelow;
        if (activeRegion2.inside || activeRegion3.fixUpperEdge) {
            GLUhalfEdge glUhalfEdge;
            if (activeRegion3 == activeRegion2) {
                glUhalfEdge = Mesh.__gl_meshConnect(glUvertex.anEdge.Sym, eUp.Lnext);
                if (glUhalfEdge == null) {
                    throw new RuntimeException();
                }
            }
            else {
                final GLUhalfEdge _gl_meshConnect = Mesh.__gl_meshConnect(eUp2.Sym.Onext.Sym, glUvertex.anEdge);
                if (_gl_meshConnect == null) {
                    throw new RuntimeException();
                }
                glUhalfEdge = _gl_meshConnect.Sym;
            }
            if (activeRegion3.fixUpperEdge) {
                if (!FixUpperEdge(activeRegion3, glUhalfEdge)) {
                    throw new RuntimeException();
                }
            }
            else {
                ComputeWinding(glUtessellatorImpl, AddRegionBelow(glUtessellatorImpl, activeRegion2, glUhalfEdge));
            }
            SweepEvent(glUtessellatorImpl, glUvertex);
        }
        else {
            AddRightEdges(glUtessellatorImpl, activeRegion2, glUvertex.anEdge, glUvertex.anEdge, null, true);
        }
    }
    
    static void SweepEvent(final GLUtessellatorImpl glUtessellatorImpl, final GLUvertex event) {
        glUtessellatorImpl.event = event;
        DebugEvent(glUtessellatorImpl);
        GLUhalfEdge glUhalfEdge = event.anEdge;
        while (glUhalfEdge.activeRegion == null) {
            glUhalfEdge = glUhalfEdge.Onext;
            if (glUhalfEdge == event.anEdge) {
                ConnectLeftVertex(glUtessellatorImpl, event);
                return;
            }
        }
        final ActiveRegion topLeftRegion = TopLeftRegion(glUhalfEdge.activeRegion);
        if (topLeftRegion == null) {
            throw new RuntimeException();
        }
        final ActiveRegion regionBelow = RegionBelow(topLeftRegion);
        final GLUhalfEdge eUp = regionBelow.eUp;
        final GLUhalfEdge finishLeftRegions = FinishLeftRegions(glUtessellatorImpl, regionBelow, null);
        if (finishLeftRegions.Onext == eUp) {
            ConnectRightVertex(glUtessellatorImpl, topLeftRegion, finishLeftRegions);
        }
        else {
            AddRightEdges(glUtessellatorImpl, topLeftRegion, finishLeftRegions.Onext, eUp, eUp, true);
        }
    }
    
    static void AddSentinel(final GLUtessellatorImpl glUtessellatorImpl, final double n) {
        final ActiveRegion activeRegion = new ActiveRegion();
        if (activeRegion == null) {
            throw new RuntimeException();
        }
        final GLUhalfEdge _gl_meshMakeEdge = Mesh.__gl_meshMakeEdge(glUtessellatorImpl.mesh);
        if (_gl_meshMakeEdge == null) {
            throw new RuntimeException();
        }
        _gl_meshMakeEdge.Org.s = 4.0E150;
        _gl_meshMakeEdge.Org.t = n;
        _gl_meshMakeEdge.Sym.Org.s = -4.0E150;
        _gl_meshMakeEdge.Sym.Org.t = n;
        glUtessellatorImpl.event = _gl_meshMakeEdge.Sym.Org;
        activeRegion.eUp = _gl_meshMakeEdge;
        activeRegion.windingNumber = 0;
        activeRegion.inside = false;
        activeRegion.fixUpperEdge = false;
        activeRegion.sentinel = true;
        activeRegion.dirty = false;
        activeRegion.nodeUp = Dict.dictInsert(glUtessellatorImpl.dict, activeRegion);
        if (activeRegion.nodeUp == null) {
            throw new RuntimeException();
        }
    }
    
    static void InitEdgeDict(final GLUtessellatorImpl glUtessellatorImpl) {
        glUtessellatorImpl.dict = Dict.dictNewDict(glUtessellatorImpl, new Dict.DictLeq(glUtessellatorImpl) {
            final GLUtessellatorImpl val$tess;
            
            public boolean leq(final Object o, final Object o2, final Object o3) {
                return Sweep.EdgeLeq(this.val$tess, (ActiveRegion)o2, (ActiveRegion)o3);
            }
        });
        if (glUtessellatorImpl.dict == null) {
            throw new RuntimeException();
        }
        AddSentinel(glUtessellatorImpl, -4.0E150);
        AddSentinel(glUtessellatorImpl, 4.0E150);
    }
    
    static void DoneEdgeDict(final GLUtessellatorImpl glUtessellatorImpl) {
        ActiveRegion activeRegion;
        while ((activeRegion = (ActiveRegion)Dict.dictKey(Dict.dictMin(glUtessellatorImpl.dict))) != null) {
            if (!activeRegion.sentinel) {
                assert activeRegion.fixUpperEdge;
                if (!Sweep.$assertionsDisabled) {
                    int n = 0;
                    ++n;
                    if (false != true) {
                        throw new AssertionError();
                    }
                }
            }
            assert activeRegion.windingNumber == 0;
            DeleteRegion(glUtessellatorImpl, activeRegion);
        }
        Dict.dictDeleteDict(glUtessellatorImpl.dict);
    }
    
    static void RemoveDegenerateEdges(final GLUtessellatorImpl glUtessellatorImpl) {
        GLUhalfEdge glUhalfEdge;
        for (GLUhalfEdge eHead = glUtessellatorImpl.mesh.eHead, next = eHead.next; next != eHead; next = glUhalfEdge) {
            glUhalfEdge = next.next;
            GLUhalfEdge glUhalfEdge2 = next.Lnext;
            if (Geom.VertEq(next.Org, next.Sym.Org) && next.Lnext.Lnext != next) {
                SpliceMergeVertices(glUtessellatorImpl, glUhalfEdge2, next);
                if (!Mesh.__gl_meshDelete(next)) {
                    throw new RuntimeException();
                }
                next = glUhalfEdge2;
                glUhalfEdge2 = next.Lnext;
            }
            if (glUhalfEdge2.Lnext == next) {
                if (glUhalfEdge2 != next) {
                    if (glUhalfEdge2 == glUhalfEdge || glUhalfEdge2 == glUhalfEdge.Sym) {
                        glUhalfEdge = glUhalfEdge.next;
                    }
                    if (!Mesh.__gl_meshDelete(glUhalfEdge2)) {
                        throw new RuntimeException();
                    }
                }
                if (next == glUhalfEdge || next == glUhalfEdge.Sym) {
                    glUhalfEdge = glUhalfEdge.next;
                }
                if (!Mesh.__gl_meshDelete(next)) {
                    throw new RuntimeException();
                }
            }
        }
    }
    
    static boolean InitPriorityQ(final GLUtessellatorImpl glUtessellatorImpl) {
        final PriorityQ pqNewPriorityQ = PriorityQ.pqNewPriorityQ(new PriorityQ.Leq() {
            public boolean leq(final Object o, final Object o2) {
                return Geom.VertLeq((GLUvertex)o, (GLUvertex)o2);
            }
        });
        glUtessellatorImpl.pq = pqNewPriorityQ;
        final PriorityQ priorityQ = pqNewPriorityQ;
        if (priorityQ == null) {
            return false;
        }
        GLUvertex vHead;
        GLUvertex glUvertex;
        for (vHead = glUtessellatorImpl.mesh.vHead, glUvertex = vHead.next; glUvertex != vHead; glUvertex = glUvertex.next) {
            glUvertex.pqHandle = priorityQ.pqInsert(glUvertex);
            if (glUvertex.pqHandle == Long.MAX_VALUE) {
                break;
            }
        }
        if (glUvertex != vHead || !priorityQ.pqInit()) {
            glUtessellatorImpl.pq.pqDeletePriorityQ();
            glUtessellatorImpl.pq = null;
            return false;
        }
        return true;
    }
    
    static void DonePriorityQ(final GLUtessellatorImpl glUtessellatorImpl) {
        glUtessellatorImpl.pq.pqDeletePriorityQ();
    }
    
    static boolean RemoveDegenerateFaces(final GLUmesh glUmesh) {
        GLUface next2;
        for (GLUface next = glUmesh.fHead.next; next != glUmesh.fHead; next = next2) {
            next2 = next.next;
            final GLUhalfEdge anEdge = next.anEdge;
            assert anEdge.Lnext != anEdge;
            if (anEdge.Lnext.Lnext == anEdge) {
                AddWinding(anEdge.Onext, anEdge);
                if (!Mesh.__gl_meshDelete(anEdge)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean __gl_computeInterior(final GLUtessellatorImpl glUtessellatorImpl) {
        glUtessellatorImpl.fatalError = false;
        RemoveDegenerateEdges(glUtessellatorImpl);
        if (!InitPriorityQ(glUtessellatorImpl)) {
            return false;
        }
        InitEdgeDict(glUtessellatorImpl);
        GLUvertex glUvertex;
        while ((glUvertex = (GLUvertex)glUtessellatorImpl.pq.pqExtractMin()) != null) {
            while (true) {
                final GLUvertex glUvertex2 = (GLUvertex)glUtessellatorImpl.pq.pqMinimum();
                if (glUvertex2 == null || !Geom.VertEq(glUvertex2, glUvertex)) {
                    break;
                }
                SpliceMergeVertices(glUtessellatorImpl, glUvertex.anEdge, ((GLUvertex)glUtessellatorImpl.pq.pqExtractMin()).anEdge);
            }
            SweepEvent(glUtessellatorImpl, glUvertex);
        }
        glUtessellatorImpl.event = ((ActiveRegion)Dict.dictKey(Dict.dictMin(glUtessellatorImpl.dict))).eUp.Org;
        DebugEvent(glUtessellatorImpl);
        DoneEdgeDict(glUtessellatorImpl);
        DonePriorityQ(glUtessellatorImpl);
        if (!RemoveDegenerateFaces(glUtessellatorImpl.mesh)) {
            return false;
        }
        Mesh.__gl_meshCheckMesh(glUtessellatorImpl.mesh);
        return true;
    }
    
    static {
        $assertionsDisabled = !Sweep.class.desiredAssertionStatus();
    }
}
