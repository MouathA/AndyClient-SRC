package org.lwjgl.util.glu.tessellation;

class Render
{
    private static final boolean USE_OPTIMIZED_CODE_PATH = false;
    private static final RenderFan renderFan;
    private static final RenderStrip renderStrip;
    private static final RenderTriangle renderTriangle;
    private static final int SIGN_INCONSISTENT = 2;
    static final boolean $assertionsDisabled;
    
    private Render() {
    }
    
    public static void __gl_renderMesh(final GLUtessellatorImpl glUtessellatorImpl, final GLUmesh glUmesh) {
        glUtessellatorImpl.lonelyTriList = null;
        for (GLUface glUface = glUmesh.fHead.next; glUface != glUmesh.fHead; glUface = glUface.next) {
            glUface.marked = false;
        }
        for (GLUface glUface2 = glUmesh.fHead.next; glUface2 != glUmesh.fHead; glUface2 = glUface2.next) {
            if (glUface2.inside && !glUface2.marked) {
                RenderMaximumFaceGroup(glUtessellatorImpl, glUface2);
                assert glUface2.marked;
            }
        }
        if (glUtessellatorImpl.lonelyTriList != null) {
            RenderLonelyTriangles(glUtessellatorImpl, glUtessellatorImpl.lonelyTriList);
            glUtessellatorImpl.lonelyTriList = null;
        }
    }
    
    static void RenderMaximumFaceGroup(final GLUtessellatorImpl glUtessellatorImpl, final GLUface glUface) {
        final GLUhalfEdge anEdge = glUface.anEdge;
        FaceCount faceCount = new FaceCount(null);
        faceCount.size = 1L;
        faceCount.eStart = anEdge;
        faceCount.render = Render.renderTriangle;
        if (!glUtessellatorImpl.flagBoundary) {
            final FaceCount maximumFan = MaximumFan(anEdge);
            if (maximumFan.size > faceCount.size) {
                faceCount = maximumFan;
            }
            final FaceCount maximumFan2 = MaximumFan(anEdge.Lnext);
            if (maximumFan2.size > faceCount.size) {
                faceCount = maximumFan2;
            }
            final FaceCount maximumFan3 = MaximumFan(anEdge.Onext.Sym);
            if (maximumFan3.size > faceCount.size) {
                faceCount = maximumFan3;
            }
            final FaceCount maximumStrip = MaximumStrip(anEdge);
            if (maximumStrip.size > faceCount.size) {
                faceCount = maximumStrip;
            }
            final FaceCount maximumStrip2 = MaximumStrip(anEdge.Lnext);
            if (maximumStrip2.size > faceCount.size) {
                faceCount = maximumStrip2;
            }
            final FaceCount maximumStrip3 = MaximumStrip(anEdge.Onext.Sym);
            if (maximumStrip3.size > faceCount.size) {
                faceCount = maximumStrip3;
            }
        }
        faceCount.render.render(glUtessellatorImpl, faceCount.eStart, faceCount.size);
    }
    
    private static boolean Marked(final GLUface glUface) {
        return !glUface.inside || glUface.marked;
    }
    
    private static GLUface AddToTrail(final GLUface glUface, final GLUface trail) {
        glUface.trail = trail;
        glUface.marked = true;
        return glUface;
    }
    
    private static void FreeTrail(GLUface trail) {
        while (trail != null) {
            trail.marked = false;
            trail = trail.trail;
        }
    }
    
    static FaceCount MaximumFan(final GLUhalfEdge glUhalfEdge) {
        final FaceCount faceCount = new FaceCount(0L, null, Render.renderFan, null);
        GLUface glUface = null;
        for (GLUhalfEdge onext = glUhalfEdge; !Marked(onext.Lface); onext = onext.Onext) {
            glUface = AddToTrail(onext.Lface, glUface);
            final FaceCount faceCount2 = faceCount;
            ++faceCount2.size;
        }
        GLUhalfEdge lnext;
        for (lnext = glUhalfEdge; !Marked(lnext.Sym.Lface); lnext = lnext.Sym.Lnext) {
            glUface = AddToTrail(lnext.Sym.Lface, glUface);
            final FaceCount faceCount3 = faceCount;
            ++faceCount3.size;
        }
        faceCount.eStart = lnext;
        FreeTrail(glUface);
        return faceCount;
    }
    
    private static boolean IsEven(final long n) {
        return (n & 0x1L) == 0x0L;
    }
    
    static FaceCount MaximumStrip(final GLUhalfEdge glUhalfEdge) {
        final FaceCount faceCount = new FaceCount(0L, null, Render.renderStrip, null);
        long n = 0L;
        long n2 = 0L;
        GLUface glUface = null;
        GLUhalfEdge glUhalfEdge2;
        for (glUhalfEdge2 = glUhalfEdge; !Marked(glUhalfEdge2.Lface); glUhalfEdge2 = glUhalfEdge2.Onext) {
            glUface = AddToTrail(glUhalfEdge2.Lface, glUface);
            ++n2;
            glUhalfEdge2 = glUhalfEdge2.Lnext.Sym;
            if (Marked(glUhalfEdge2.Lface)) {
                break;
            }
            glUface = AddToTrail(glUhalfEdge2.Lface, glUface);
            ++n2;
        }
        final GLUhalfEdge glUhalfEdge3 = glUhalfEdge2;
        GLUhalfEdge glUhalfEdge4;
        for (glUhalfEdge4 = glUhalfEdge; !Marked(glUhalfEdge4.Sym.Lface); glUhalfEdge4 = glUhalfEdge4.Sym.Onext.Sym) {
            glUface = AddToTrail(glUhalfEdge4.Sym.Lface, glUface);
            ++n;
            glUhalfEdge4 = glUhalfEdge4.Sym.Lnext;
            if (Marked(glUhalfEdge4.Sym.Lface)) {
                break;
            }
            glUface = AddToTrail(glUhalfEdge4.Sym.Lface, glUface);
            ++n;
        }
        final GLUhalfEdge eStart = glUhalfEdge4;
        faceCount.size = n2 + n;
        if (IsEven(n2)) {
            faceCount.eStart = glUhalfEdge3.Sym;
        }
        else if (IsEven(n)) {
            faceCount.eStart = eStart;
        }
        else {
            final FaceCount faceCount2 = faceCount;
            --faceCount2.size;
            faceCount.eStart = eStart.Onext;
        }
        FreeTrail(glUface);
        return faceCount;
    }
    
    static void RenderLonelyTriangles(final GLUtessellatorImpl glUtessellatorImpl, GLUface trail) {
        glUtessellatorImpl.callBeginOrBeginData(4);
        while (trail != null) {
            GLUhalfEdge glUhalfEdge = trail.anEdge;
            do {
                if (glUtessellatorImpl.flagBoundary && -1 != (glUhalfEdge.Sym.Lface.inside ? 0 : 1)) {
                    glUtessellatorImpl.callEdgeFlagOrEdgeFlagData(-1 != 0);
                }
                glUtessellatorImpl.callVertexOrVertexData(glUhalfEdge.Org.data);
                glUhalfEdge = glUhalfEdge.Lnext;
            } while (glUhalfEdge != trail.anEdge);
            trail = trail.trail;
        }
        glUtessellatorImpl.callEndOrEndData();
    }
    
    public static void __gl_renderBoundary(final GLUtessellatorImpl glUtessellatorImpl, final GLUmesh glUmesh) {
        for (GLUface glUface = glUmesh.fHead.next; glUface != glUmesh.fHead; glUface = glUface.next) {
            if (glUface.inside) {
                glUtessellatorImpl.callBeginOrBeginData(2);
                GLUhalfEdge glUhalfEdge = glUface.anEdge;
                do {
                    glUtessellatorImpl.callVertexOrVertexData(glUhalfEdge.Org.data);
                    glUhalfEdge = glUhalfEdge.Lnext;
                } while (glUhalfEdge != glUface.anEdge);
                glUtessellatorImpl.callEndOrEndData();
            }
        }
    }
    
    static int ComputeNormal(final GLUtessellatorImpl glUtessellatorImpl, final double[] array, final boolean b) {
        final CachedVertex[] cache = glUtessellatorImpl.cache;
        final int cacheCount = glUtessellatorImpl.cacheCount;
        final double[] array2 = new double[3];
        if (!b) {
            final int n = 0;
            final int n2 = 1;
            final int n3 = 2;
            final double n4 = 0.0;
            array[n3] = n4;
            array[n] = (array[n2] = n4);
        }
        double n5 = cache[1].coords[0] - cache[0].coords[0];
        double n6 = cache[1].coords[1] - cache[0].coords[1];
        double n7 = cache[1].coords[2] - cache[0].coords[2];
        while (true) {
            int n8 = 0;
            ++n8;
            if (1 >= cacheCount) {
                return -1;
            }
            final double n9 = n5;
            final double n10 = n6;
            final double n11 = n7;
            n5 = cache[1].coords[0] - cache[0].coords[0];
            n6 = cache[1].coords[1] - cache[0].coords[1];
            n7 = cache[1].coords[2] - cache[0].coords[2];
            array2[0] = n10 * n7 - n11 * n6;
            array2[1] = n11 * n5 - n9 * n7;
            array2[2] = n9 * n6 - n10 * n5;
            final double n12 = array2[0] * array[0] + array2[1] * array[1] + array2[2] * array[2];
            if (!b) {
                if (n12 >= 0.0) {
                    final int n13 = 0;
                    array[n13] += array2[0];
                    final int n14 = 1;
                    array[n14] += array2[1];
                    final int n15 = 2;
                    array[n15] += array2[2];
                }
                else {
                    final int n16 = 0;
                    array[n16] -= array2[0];
                    final int n17 = 1;
                    array[n17] -= array2[1];
                    final int n18 = 2;
                    array[n18] -= array2[2];
                }
            }
            else {
                if (n12 == 0.0) {
                    continue;
                }
                if (n12 > 0.0) {
                    if (-1 < 0) {
                        return 2;
                    }
                    continue;
                }
                else {
                    if (-1 > 0) {
                        return 2;
                    }
                    continue;
                }
            }
        }
    }
    
    public static boolean __gl_renderCache(final GLUtessellatorImpl glUtessellatorImpl) {
        final CachedVertex[] cache = glUtessellatorImpl.cache;
        final int cacheCount = glUtessellatorImpl.cacheCount;
        final double[] array = new double[3];
        if (glUtessellatorImpl.cacheCount < 3) {
            return true;
        }
        array[0] = glUtessellatorImpl.normal[0];
        array[1] = glUtessellatorImpl.normal[1];
        array[2] = glUtessellatorImpl.normal[2];
        if (array[0] == 0.0 && array[1] == 0.0 && array[2] == 0.0) {
            ComputeNormal(glUtessellatorImpl, array, false);
        }
        final int computeNormal = ComputeNormal(glUtessellatorImpl, array, true);
        return computeNormal != 2 && computeNormal == 0;
    }
    
    static GLUface access$500(final GLUface glUface, final GLUface glUface2) {
        return AddToTrail(glUface, glUface2);
    }
    
    static boolean access$600(final GLUface glUface) {
        return Marked(glUface);
    }
    
    static {
        $assertionsDisabled = !Render.class.desiredAssertionStatus();
        renderFan = new RenderFan(null);
        renderStrip = new RenderStrip(null);
        renderTriangle = new RenderTriangle(null);
    }
    
    private static class RenderStrip implements renderCallBack
    {
        static final boolean $assertionsDisabled;
        
        private RenderStrip() {
        }
        
        public void render(final GLUtessellatorImpl glUtessellatorImpl, GLUhalfEdge glUhalfEdge, long n) {
            glUtessellatorImpl.callBeginOrBeginData(5);
            glUtessellatorImpl.callVertexOrVertexData(glUhalfEdge.Org.data);
            glUtessellatorImpl.callVertexOrVertexData(glUhalfEdge.Sym.Org.data);
            while (!Render.access$600(glUhalfEdge.Lface)) {
                glUhalfEdge.Lface.marked = true;
                --n;
                glUhalfEdge = glUhalfEdge.Lnext.Sym;
                glUtessellatorImpl.callVertexOrVertexData(glUhalfEdge.Org.data);
                if (Render.access$600(glUhalfEdge.Lface)) {
                    break;
                }
                glUhalfEdge.Lface.marked = true;
                --n;
                glUhalfEdge = glUhalfEdge.Onext;
                glUtessellatorImpl.callVertexOrVertexData(glUhalfEdge.Sym.Org.data);
            }
            assert n == 0L;
            glUtessellatorImpl.callEndOrEndData();
        }
        
        RenderStrip(final Render$1 object) {
            this();
        }
        
        static {
            $assertionsDisabled = !Render.class.desiredAssertionStatus();
        }
    }
    
    private interface renderCallBack
    {
        void render(final GLUtessellatorImpl p0, final GLUhalfEdge p1, final long p2);
    }
    
    private static class RenderFan implements renderCallBack
    {
        static final boolean $assertionsDisabled;
        
        private RenderFan() {
        }
        
        public void render(final GLUtessellatorImpl glUtessellatorImpl, GLUhalfEdge onext, long n) {
            glUtessellatorImpl.callBeginOrBeginData(6);
            glUtessellatorImpl.callVertexOrVertexData(onext.Org.data);
            glUtessellatorImpl.callVertexOrVertexData(onext.Sym.Org.data);
            while (!Render.access$600(onext.Lface)) {
                onext.Lface.marked = true;
                --n;
                onext = onext.Onext;
                glUtessellatorImpl.callVertexOrVertexData(onext.Sym.Org.data);
            }
            assert n == 0L;
            glUtessellatorImpl.callEndOrEndData();
        }
        
        RenderFan(final Render$1 object) {
            this();
        }
        
        static {
            $assertionsDisabled = !Render.class.desiredAssertionStatus();
        }
    }
    
    private static class RenderTriangle implements renderCallBack
    {
        static final boolean $assertionsDisabled;
        
        private RenderTriangle() {
        }
        
        public void render(final GLUtessellatorImpl glUtessellatorImpl, final GLUhalfEdge glUhalfEdge, final long n) {
            assert n == 1L;
            glUtessellatorImpl.lonelyTriList = Render.access$500(glUhalfEdge.Lface, glUtessellatorImpl.lonelyTriList);
        }
        
        RenderTriangle(final Render$1 object) {
            this();
        }
        
        static {
            $assertionsDisabled = !Render.class.desiredAssertionStatus();
        }
    }
    
    private static class FaceCount
    {
        long size;
        GLUhalfEdge eStart;
        renderCallBack render;
        
        private FaceCount() {
        }
        
        private FaceCount(final long size, final GLUhalfEdge eStart, final renderCallBack render) {
            this.size = size;
            this.eStart = eStart;
            this.render = render;
        }
        
        FaceCount(final Render$1 object) {
            this();
        }
        
        FaceCount(final long n, final GLUhalfEdge glUhalfEdge, final renderCallBack renderCallBack, final Render$1 object) {
            this(n, glUhalfEdge, renderCallBack);
        }
    }
}
