package org.lwjgl.util.glu.tessellation;

class Geom
{
    static final boolean $assertionsDisabled;
    
    private Geom() {
    }
    
    static double EdgeEval(final GLUvertex glUvertex, final GLUvertex glUvertex2, final GLUvertex glUvertex3) {
        assert VertLeq(glUvertex, glUvertex2) && VertLeq(glUvertex2, glUvertex3);
        final double n = glUvertex2.s - glUvertex.s;
        final double n2 = glUvertex3.s - glUvertex2.s;
        if (n + n2 <= 0.0) {
            return 0.0;
        }
        if (n < n2) {
            return glUvertex2.t - glUvertex.t + (glUvertex.t - glUvertex3.t) * (n / (n + n2));
        }
        return glUvertex2.t - glUvertex3.t + (glUvertex3.t - glUvertex.t) * (n2 / (n + n2));
    }
    
    static double EdgeSign(final GLUvertex glUvertex, final GLUvertex glUvertex2, final GLUvertex glUvertex3) {
        assert VertLeq(glUvertex, glUvertex2) && VertLeq(glUvertex2, glUvertex3);
        final double n = glUvertex2.s - glUvertex.s;
        final double n2 = glUvertex3.s - glUvertex2.s;
        if (n + n2 > 0.0) {
            return (glUvertex2.t - glUvertex3.t) * n + (glUvertex2.t - glUvertex.t) * n2;
        }
        return 0.0;
    }
    
    static double TransEval(final GLUvertex glUvertex, final GLUvertex glUvertex2, final GLUvertex glUvertex3) {
        assert TransLeq(glUvertex, glUvertex2) && TransLeq(glUvertex2, glUvertex3);
        final double n = glUvertex2.t - glUvertex.t;
        final double n2 = glUvertex3.t - glUvertex2.t;
        if (n + n2 <= 0.0) {
            return 0.0;
        }
        if (n < n2) {
            return glUvertex2.s - glUvertex.s + (glUvertex.s - glUvertex3.s) * (n / (n + n2));
        }
        return glUvertex2.s - glUvertex3.s + (glUvertex3.s - glUvertex.s) * (n2 / (n + n2));
    }
    
    static double TransSign(final GLUvertex glUvertex, final GLUvertex glUvertex2, final GLUvertex glUvertex3) {
        assert TransLeq(glUvertex, glUvertex2) && TransLeq(glUvertex2, glUvertex3);
        final double n = glUvertex2.t - glUvertex.t;
        final double n2 = glUvertex3.t - glUvertex2.t;
        if (n + n2 > 0.0) {
            return (glUvertex2.s - glUvertex3.s) * n + (glUvertex2.s - glUvertex.s) * n2;
        }
        return 0.0;
    }
    
    static boolean VertCCW(final GLUvertex glUvertex, final GLUvertex glUvertex2, final GLUvertex glUvertex3) {
        return glUvertex.s * (glUvertex2.t - glUvertex3.t) + glUvertex2.s * (glUvertex3.t - glUvertex.t) + glUvertex3.s * (glUvertex.t - glUvertex2.t) >= 0.0;
    }
    
    static double Interpolate(double n, final double n2, double n3, final double n4) {
        n = ((n < 0.0) ? 0.0 : n);
        n3 = ((n3 < 0.0) ? 0.0 : n3);
        if (n > n3) {
            return n4 + (n2 - n4) * (n3 / (n + n3));
        }
        if (n3 == 0.0) {
            return (n2 + n4) / 2.0;
        }
        return n2 + (n4 - n2) * (n / (n + n3));
    }
    
    static void EdgeIntersect(GLUvertex glUvertex, GLUvertex glUvertex2, GLUvertex glUvertex3, GLUvertex glUvertex4, final GLUvertex glUvertex5) {
        if (!VertLeq(glUvertex, glUvertex2)) {
            final GLUvertex glUvertex6 = glUvertex;
            glUvertex = glUvertex2;
            glUvertex2 = glUvertex6;
        }
        if (!VertLeq(glUvertex3, glUvertex4)) {
            final GLUvertex glUvertex7 = glUvertex3;
            glUvertex3 = glUvertex4;
            glUvertex4 = glUvertex7;
        }
        if (!VertLeq(glUvertex, glUvertex3)) {
            final GLUvertex glUvertex8 = glUvertex;
            glUvertex = glUvertex3;
            glUvertex3 = glUvertex8;
            final GLUvertex glUvertex9 = glUvertex2;
            glUvertex2 = glUvertex4;
            glUvertex4 = glUvertex9;
        }
        if (!VertLeq(glUvertex3, glUvertex2)) {
            glUvertex5.s = (glUvertex3.s + glUvertex2.s) / 2.0;
        }
        else if (VertLeq(glUvertex2, glUvertex4)) {
            double edgeEval = EdgeEval(glUvertex, glUvertex3, glUvertex2);
            double edgeEval2 = EdgeEval(glUvertex3, glUvertex2, glUvertex4);
            if (edgeEval + edgeEval2 < 0.0) {
                edgeEval = -edgeEval;
                edgeEval2 = -edgeEval2;
            }
            glUvertex5.s = Interpolate(edgeEval, glUvertex3.s, edgeEval2, glUvertex2.s);
        }
        else {
            double edgeSign = EdgeSign(glUvertex, glUvertex3, glUvertex2);
            double n = -EdgeSign(glUvertex, glUvertex4, glUvertex2);
            if (edgeSign + n < 0.0) {
                edgeSign = -edgeSign;
                n = -n;
            }
            glUvertex5.s = Interpolate(edgeSign, glUvertex3.s, n, glUvertex4.s);
        }
        if (!TransLeq(glUvertex, glUvertex2)) {
            final GLUvertex glUvertex10 = glUvertex;
            glUvertex = glUvertex2;
            glUvertex2 = glUvertex10;
        }
        if (!TransLeq(glUvertex3, glUvertex4)) {
            final GLUvertex glUvertex11 = glUvertex3;
            glUvertex3 = glUvertex4;
            glUvertex4 = glUvertex11;
        }
        if (!TransLeq(glUvertex, glUvertex3)) {
            final GLUvertex glUvertex12 = glUvertex3;
            glUvertex3 = glUvertex;
            glUvertex = glUvertex12;
            final GLUvertex glUvertex13 = glUvertex4;
            glUvertex4 = glUvertex2;
            glUvertex2 = glUvertex13;
        }
        if (!TransLeq(glUvertex3, glUvertex2)) {
            glUvertex5.t = (glUvertex3.t + glUvertex2.t) / 2.0;
        }
        else if (TransLeq(glUvertex2, glUvertex4)) {
            double transEval = TransEval(glUvertex, glUvertex3, glUvertex2);
            double transEval2 = TransEval(glUvertex3, glUvertex2, glUvertex4);
            if (transEval + transEval2 < 0.0) {
                transEval = -transEval;
                transEval2 = -transEval2;
            }
            glUvertex5.t = Interpolate(transEval, glUvertex3.t, transEval2, glUvertex2.t);
        }
        else {
            double transSign = TransSign(glUvertex, glUvertex3, glUvertex2);
            double n2 = -TransSign(glUvertex, glUvertex4, glUvertex2);
            if (transSign + n2 < 0.0) {
                transSign = -transSign;
                n2 = -n2;
            }
            glUvertex5.t = Interpolate(transSign, glUvertex3.t, n2, glUvertex4.t);
        }
    }
    
    static boolean VertEq(final GLUvertex glUvertex, final GLUvertex glUvertex2) {
        return glUvertex.s == glUvertex2.s && glUvertex.t == glUvertex2.t;
    }
    
    static boolean VertLeq(final GLUvertex glUvertex, final GLUvertex glUvertex2) {
        return glUvertex.s < glUvertex2.s || (glUvertex.s == glUvertex2.s && glUvertex.t <= glUvertex2.t);
    }
    
    static boolean TransLeq(final GLUvertex glUvertex, final GLUvertex glUvertex2) {
        return glUvertex.t < glUvertex2.t || (glUvertex.t == glUvertex2.t && glUvertex.s <= glUvertex2.s);
    }
    
    static boolean EdgeGoesLeft(final GLUhalfEdge glUhalfEdge) {
        return VertLeq(glUhalfEdge.Sym.Org, glUhalfEdge.Org);
    }
    
    static boolean EdgeGoesRight(final GLUhalfEdge glUhalfEdge) {
        return VertLeq(glUhalfEdge.Org, glUhalfEdge.Sym.Org);
    }
    
    static double VertL1dist(final GLUvertex glUvertex, final GLUvertex glUvertex2) {
        return Math.abs(glUvertex.s - glUvertex2.s) + Math.abs(glUvertex.t - glUvertex2.t);
    }
    
    static {
        $assertionsDisabled = !Geom.class.desiredAssertionStatus();
    }
}
