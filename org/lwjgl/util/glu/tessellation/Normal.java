package org.lwjgl.util.glu.tessellation;

class Normal
{
    static boolean SLANTED_SWEEP;
    private static final boolean TRUE_PROJECT = false;
    static final boolean $assertionsDisabled;
    
    private Normal() {
    }
    
    private static double Dot(final double[] array, final double[] array2) {
        return array[0] * array2[0] + array[1] * array2[1] + array[2] * array2[2];
    }
    
    static void Normalize(final double[] array) {
        final double n = array[0] * array[0] + array[1] * array[1] + array[2] * array[2];
        assert n > 0.0;
        final double sqrt = Math.sqrt(n);
        final int n2 = 0;
        array[n2] /= sqrt;
        final int n3 = 1;
        array[n3] /= sqrt;
        final int n4 = 2;
        array[n4] /= sqrt;
    }
    
    static int LongAxis(final double[] array) {
        if (Math.abs(array[1]) > Math.abs(array[0])) {}
        if (Math.abs(array[2]) > Math.abs(array[2])) {}
        return 2;
    }
    
    static void ComputeNormal(final GLUtessellatorImpl glUtessellatorImpl, final double[] array) {
        final GLUvertex vHead = glUtessellatorImpl.mesh.vHead;
        final double[] array2 = new double[3];
        final double[] array3 = new double[3];
        final GLUvertex[] array4 = new GLUvertex[3];
        final GLUvertex[] array5 = new GLUvertex[3];
        final double[] array6 = new double[3];
        final double[] array7 = new double[3];
        final double[] array8 = new double[3];
        final double[] array9 = array2;
        final int n = 0;
        final double[] array10 = array2;
        final int n2 = 1;
        final double[] array11 = array2;
        final int n3 = 2;
        final double n4 = -2.0E150;
        array11[n3] = n4;
        array9[n] = (array10[n2] = n4);
        final double[] array12 = array3;
        final int n5 = 0;
        final double[] array13 = array3;
        final int n6 = 1;
        final double[] array14 = array3;
        final int n7 = 2;
        final double n8 = 2.0E150;
        array14[n7] = n8;
        array12[n5] = (array13[n6] = n8);
        for (GLUvertex glUvertex = vHead.next; glUvertex != vHead; glUvertex = glUvertex.next) {
            while (2 < 3) {
                final double n9 = glUvertex.coords[2];
                if (n9 < array3[2]) {
                    array3[2] = n9;
                    array4[2] = glUvertex;
                }
                if (n9 > array2[2]) {
                    array2[2] = n9;
                    array5[2] = glUvertex;
                }
                int n10 = 0;
                ++n10;
            }
        }
        if (array2[1] - array3[1] > array2[0] - array3[0]) {}
        if (array2[2] - array3[2] > array2[2] - array3[2]) {}
        if (array3[2] >= array2[2]) {
            array[1] = (array[0] = 0.0);
            array[2] = 1.0;
            return;
        }
        double n11 = 0.0;
        final GLUvertex glUvertex2 = array4[2];
        final GLUvertex glUvertex3 = array5[2];
        array6[0] = glUvertex2.coords[0] - glUvertex3.coords[0];
        array6[1] = glUvertex2.coords[1] - glUvertex3.coords[1];
        array6[2] = glUvertex2.coords[2] - glUvertex3.coords[2];
        for (GLUvertex glUvertex4 = vHead.next; glUvertex4 != vHead; glUvertex4 = glUvertex4.next) {
            array7[0] = glUvertex4.coords[0] - glUvertex3.coords[0];
            array7[1] = glUvertex4.coords[1] - glUvertex3.coords[1];
            array7[2] = glUvertex4.coords[2] - glUvertex3.coords[2];
            array8[0] = array6[1] * array7[2] - array6[2] * array7[1];
            array8[1] = array6[2] * array7[0] - array6[0] * array7[2];
            array8[2] = array6[0] * array7[1] - array6[1] * array7[0];
            final double n12 = array8[0] * array8[0] + array8[1] * array8[1] + array8[2] * array8[2];
            if (n12 > n11) {
                n11 = n12;
                array[0] = array8[0];
                array[1] = array8[1];
                array[2] = array8[2];
            }
        }
        if (n11 <= 0.0) {
            final int n13 = 0;
            final int n14 = 1;
            final int n15 = 2;
            final double n16 = 0.0;
            array[n15] = n16;
            array[n13] = (array[n14] = n16);
            array[LongAxis(array6)] = 1.0;
        }
    }
    
    static void CheckOrientation(final GLUtessellatorImpl glUtessellatorImpl) {
        final GLUface fHead = glUtessellatorImpl.mesh.fHead;
        final GLUvertex vHead = glUtessellatorImpl.mesh.vHead;
        double n = 0.0;
        for (GLUface glUface = fHead.next; glUface != fHead; glUface = glUface.next) {
            GLUhalfEdge glUhalfEdge = glUface.anEdge;
            if (glUhalfEdge.winding > 0) {
                do {
                    n += (glUhalfEdge.Org.s - glUhalfEdge.Sym.Org.s) * (glUhalfEdge.Org.t + glUhalfEdge.Sym.Org.t);
                    glUhalfEdge = glUhalfEdge.Lnext;
                } while (glUhalfEdge != glUface.anEdge);
            }
        }
        if (n < 0.0) {
            for (GLUvertex glUvertex = vHead.next; glUvertex != vHead; glUvertex = glUvertex.next) {
                glUvertex.t = -glUvertex.t;
            }
            glUtessellatorImpl.tUnit[0] = -glUtessellatorImpl.tUnit[0];
            glUtessellatorImpl.tUnit[1] = -glUtessellatorImpl.tUnit[1];
            glUtessellatorImpl.tUnit[2] = -glUtessellatorImpl.tUnit[2];
        }
    }
    
    public static void __gl_projectPolygon(final GLUtessellatorImpl glUtessellatorImpl) {
        final GLUvertex vHead = glUtessellatorImpl.mesh.vHead;
        final double[] array = { glUtessellatorImpl.normal[0], glUtessellatorImpl.normal[1], glUtessellatorImpl.normal[2] };
        if (array[0] == 0.0 && array[1] == 0.0 && array[2] == 0.0) {
            ComputeNormal(glUtessellatorImpl, array);
        }
        final double[] sUnit = glUtessellatorImpl.sUnit;
        final double[] tUnit = glUtessellatorImpl.tUnit;
        final int longAxis = LongAxis(array);
        sUnit[longAxis] = 0.0;
        sUnit[(longAxis + 1) % 3] = 1.0;
        tUnit[longAxis] = (sUnit[(longAxis + 2) % 3] = 0.0);
        tUnit[(longAxis + 1) % 3] = ((array[longAxis] > 0.0) ? (-0.0) : 0.0);
        tUnit[(longAxis + 2) % 3] = ((array[longAxis] > 0.0) ? 1.0 : (-1.0));
        for (GLUvertex glUvertex = vHead.next; glUvertex != vHead; glUvertex = glUvertex.next) {
            glUvertex.s = Dot(glUvertex.coords, sUnit);
            glUvertex.t = Dot(glUvertex.coords, tUnit);
        }
        if (true) {
            CheckOrientation(glUtessellatorImpl);
        }
    }
    
    static {
        $assertionsDisabled = !Normal.class.desiredAssertionStatus();
        if (Normal.SLANTED_SWEEP) {}
    }
}
