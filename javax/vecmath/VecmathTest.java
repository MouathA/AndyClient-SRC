package javax.vecmath;

public class VecmathTest
{
    public static String NL;
    
    static {
        VecmathTest.NL = System.getProperty("line.separator");
    }
    
    public static boolean equals(final double n, final double n2) {
        return Math.abs(n - n2) < 0.0f;
    }
    
    public static boolean equals(final Matrix3d matrix3d, final Matrix3d matrix3d2) {
        return matrix3d.epsilonEquals(matrix3d2, 0.0f);
    }
    
    public static boolean equals(final Matrix4d matrix4d, final Matrix4d matrix4d2) {
        return matrix4d.epsilonEquals(matrix4d2, (double)0.0f);
    }
    
    public static boolean equals(final Tuple4d tuple4d, final Tuple4d tuple4d2) {
        return tuple4d.epsilonEquals(tuple4d2, 0.0f);
    }
    
    public static boolean equals(final Tuple3d tuple3d, final Tuple3d tuple3d2) {
        return tuple3d.epsilonEquals(tuple3d2, 0.0f);
    }
    
    public static boolean equals(final Matrix3f matrix3f, final Matrix3f matrix3f2) {
        return matrix3f.epsilonEquals(matrix3f2, 0.0f);
    }
    
    public static boolean equals(final Matrix4f matrix4f, final Matrix4f matrix4f2) {
        return matrix4f.epsilonEquals(matrix4f2, 0.0f);
    }
    
    public static boolean equals(final GMatrix gMatrix, final GMatrix gMatrix2) {
        return gMatrix.epsilonEquals(gMatrix2, (double)0.0f);
    }
    
    public static boolean equals(final GVector gVector, final GVector gVector2) {
        return gVector.epsilonEquals(gVector2, 0.0f);
    }
    
    public static boolean equals(final Tuple4f tuple4f, final Tuple4f tuple4f2) {
        return tuple4f.epsilonEquals(tuple4f2, 0.0f);
    }
    
    public static boolean equals(final Tuple3f tuple3f, final Tuple3f tuple3f2) {
        return tuple3f.epsilonEquals(tuple3f2, 0.0f);
    }
    
    public static boolean equals(final AxisAngle4d axisAngle4d, final AxisAngle4d axisAngle4d2) {
        if (0.0 < axisAngle4d.x * axisAngle4d2.x + axisAngle4d.y * axisAngle4d2.y + axisAngle4d.z * axisAngle4d2.z) {
            return equals(axisAngle4d.y * axisAngle4d2.z - axisAngle4d.z * axisAngle4d2.y, 0.0) && equals(axisAngle4d.z * axisAngle4d2.x - axisAngle4d.x * axisAngle4d2.z, 0.0) && equals(axisAngle4d.x * axisAngle4d2.y - axisAngle4d.y * axisAngle4d2.x, 0.0) && equals(axisAngle4d.angle, axisAngle4d2.angle);
        }
        return equals(axisAngle4d.y * axisAngle4d2.z - axisAngle4d.z * axisAngle4d2.y, 0.0) && equals(axisAngle4d.z * axisAngle4d2.x - axisAngle4d.x * axisAngle4d2.z, 0.0) && equals(axisAngle4d.x * axisAngle4d2.y - axisAngle4d.y * axisAngle4d2.x, 0.0) && (equals(axisAngle4d.angle, -axisAngle4d2.angle) || equals(axisAngle4d.angle + axisAngle4d2.angle, 6.283185307179586) || equals(axisAngle4d.angle + axisAngle4d2.angle, -6.283185307179586));
    }
    
    public static boolean equals(final AxisAngle4f axisAngle4f, final AxisAngle4f axisAngle4f2) {
        if (0.0f < axisAngle4f.x * axisAngle4f2.x + axisAngle4f.y * axisAngle4f2.y + axisAngle4f.z * axisAngle4f2.z) {
            return equals(axisAngle4f.y * axisAngle4f2.z - axisAngle4f.z * axisAngle4f2.y, 0.0) && equals(axisAngle4f.z * axisAngle4f2.x - axisAngle4f.x * axisAngle4f2.z, 0.0) && equals(axisAngle4f.x * axisAngle4f2.y - axisAngle4f.y * axisAngle4f2.x, 0.0) && equals(axisAngle4f.angle, axisAngle4f2.angle);
        }
        return equals(axisAngle4f.y * axisAngle4f2.z - axisAngle4f.z * axisAngle4f2.y, 0.0) && equals(axisAngle4f.z * axisAngle4f2.x - axisAngle4f.x * axisAngle4f2.z, 0.0) && equals(axisAngle4f.x * axisAngle4f2.y - axisAngle4f.y * axisAngle4f2.x, 0.0) && (equals(axisAngle4f.angle, -axisAngle4f2.angle) || equals(axisAngle4f.angle + axisAngle4f2.angle, 6.283185307179586) || equals(axisAngle4f.angle + axisAngle4f2.angle, -6.283185307179586));
    }
    
    public static void ASSERT(final boolean b) {
        if (!b) {
            throw new InternalError("Vecmath Test Failed!");
        }
    }
    
    public static void ASSERT(final boolean b, final String s) {
        if (!b) {
            throw new InternalError("Vecmath Test Failed!: " + s);
        }
    }
    
    public static void exit() {
        System.out.println("java.vecmath all test passed successfully.");
        System.out.print("Quit ?");
        System.in.read();
    }
    
    public static void main(final String[] array) {
        System.out.print("Vector3d ...");
        System.out.println("ok.");
        System.out.print("Vector3f ...");
        System.out.println("ok.");
        System.out.print("Matrix3d with Quat4d, AxisAngle4d, Point/Vector3d interaction ...");
        System.out.println("ok.");
        System.out.print("Matrix3f with Quat4f, AxisAngle4f, Point/Vector3f interaction ...");
        System.out.println("ok.");
        System.out.print("Matrix4d with Quat4d, AxisAngle4d, Point/Vector3d interaction ...");
        System.out.println("ok.");
        System.out.print("Matrix4f with Quat4f, AxisAngle4f, Point/Vector3f interaction ...");
        System.out.println("ok.");
        System.out.print("GMatrix with GVector interaction ...");
        System.out.println("ok.");
        System.out.print("SVD test ...");
        System.out.println("ok.");
    }
    
    public static void Vector3dTest() {
        final Vector3d vector3d = new Vector3d();
        final Vector3d vector3d2 = new Vector3d(2.0, 3.0, 4.0);
        final Vector3d vector3d3 = new Vector3d(2.0, 5.0, -8.0);
        final Vector3d vector3d4 = new Vector3d();
        vector3d4.cross(vector3d2, vector3d3);
        ASSERT(equals(vector3d4.dot(vector3d2), 0.0));
        ASSERT(equals(vector3d4.dot(vector3d3), 0.0));
        vector3d2.cross(vector3d2, vector3d3);
        ASSERT(equals(vector3d2, new Vector3d(-44.0, 24.0, 4.0)));
        ASSERT(equals(vector3d3.lengthSquared(), 93.0));
        ASSERT(equals(vector3d3.length(), Math.sqrt(93.0)));
        vector3d2.set(vector3d3);
        vector3d3.normalize();
        ASSERT(equals(vector3d3.length(), 1.0));
        vector3d2.cross(vector3d3, vector3d2);
        ASSERT(equals(vector3d2, vector3d));
        vector3d2.set(1.0, 2.0, 3.0);
        vector3d3.set(-1.0, -6.0, -3.0);
        ASSERT(equals(vector3d2.length() * vector3d3.length() * Math.cos(vector3d2.angle(vector3d3)), vector3d2.dot(vector3d3)));
        vector3d2.set(vector3d3);
        final double angle = vector3d2.angle(vector3d3);
        ASSERT(equals(angle, 0.0));
        ASSERT(equals(vector3d2.length() * vector3d3.length() * Math.cos(angle), vector3d2.dot(vector3d3)));
        vector3d2.set(1.0, 2.0, 3.0);
        vector3d3.set(1.0, 2.0, 3.00001);
        ASSERT(equals(vector3d2.length() * vector3d3.length() * Math.cos(vector3d2.angle(vector3d3)), vector3d2.dot(vector3d3)));
        vector3d2.set(1.0, 2.0, 3.0);
        vector3d3.set(-1.0, -2.0, -3.00001);
        ASSERT(equals(vector3d2.length() * vector3d3.length() * Math.cos(vector3d2.angle(vector3d3)), vector3d2.dot(vector3d3)));
    }
    
    public static void Vector3fTest() {
        final Vector3f vector3f = new Vector3f();
        final Vector3f vector3f2 = new Vector3f(2.0f, 3.0f, 4.0f);
        final Vector3f vector3f3 = new Vector3f(2.0f, 5.0f, -8.0f);
        final Vector3f vector3f4 = new Vector3f();
        vector3f4.cross(vector3f2, vector3f3);
        ASSERT(equals(vector3f4.dot(vector3f2), 0.0));
        ASSERT(equals(vector3f4.dot(vector3f3), 0.0));
        vector3f2.cross(vector3f2, vector3f3);
        ASSERT(equals(vector3f2, new Vector3f(-44.0f, 24.0f, 4.0f)));
        ASSERT(equals(vector3f3.lengthSquared(), 93.0));
        ASSERT(equals(vector3f3.length(), Math.sqrt(93.0)));
        vector3f2.set(vector3f3);
        vector3f3.normalize();
        ASSERT(equals(vector3f3.length(), 1.0));
        vector3f2.cross(vector3f3, vector3f2);
        ASSERT(equals(vector3f2, vector3f));
        vector3f2.set(1.0f, 2.0f, 3.0f);
        vector3f3.set(-1.0f, -6.0f, -3.0f);
        ASSERT(equals(vector3f2.length() * vector3f3.length() * Math.cos(vector3f2.angle(vector3f3)), vector3f2.dot(vector3f3)));
        vector3f2.set(vector3f3);
        final double n = vector3f2.angle(vector3f3);
        ASSERT(equals(n, 0.0));
        ASSERT(equals(vector3f2.length() * vector3f3.length() * Math.cos(n), vector3f2.dot(vector3f3)));
    }
    
    public static void Matrix3dTest() {
        final Matrix3d matrix3d = new Matrix3d();
        final Matrix3d matrix3d2 = new Matrix3d();
        matrix3d2.setIdentity();
        final Matrix3d matrix3d3 = new Matrix3d();
        final Matrix3d matrix3d4 = new Matrix3d();
        final double[] array = { 2.0, 1.0, 4.0, 1.0, -2.0, 3.0, -3.0, -1.0, 1.0 };
        int n = 0;
        int n2 = 0;
        while (0 < 3) {
            while (0 < 3) {
                matrix3d3.setElement(0, 0, 3);
                ++n;
            }
            ++n2;
        }
        while (0 < 3) {
            while (0 < 3) {
                ASSERT(equals(matrix3d3.getElement(0, 0), 3));
                ++n;
            }
            ++n2;
        }
        matrix3d3.set(array);
        final Matrix3d matrix3d5 = new Matrix3d(matrix3d3);
        matrix3d5.mul(matrix3d);
        ASSERT(equals(matrix3d5, matrix3d));
        matrix3d5.mul(matrix3d3, matrix3d2);
        ASSERT(equals(matrix3d5, matrix3d3));
        ASSERT(equals(matrix3d3.determinant(), -36.0));
        matrix3d5.negate(matrix3d3);
        matrix3d5.add(matrix3d3);
        ASSERT(equals(matrix3d5, matrix3d));
        matrix3d5.negate(matrix3d3);
        final Matrix3d matrix3d6 = new Matrix3d(matrix3d3);
        matrix3d6.sub(matrix3d5);
        matrix3d6.mul(0.5);
        ASSERT(equals(matrix3d3, matrix3d6));
        matrix3d6.invert(matrix3d5);
        matrix3d6.mul(matrix3d5);
        ASSERT(equals(matrix3d6, matrix3d2));
        final Point3d point3d = new Point3d(1.0, 2.0, 3.0);
        final Vector3d vector3d = new Vector3d(2.0, -1.0, -4.0);
        point3d.set(1.0, 0.0, 0.0);
        matrix3d3.rotZ(0.5235987755982988);
        matrix3d3.transform(point3d);
        ASSERT(equals(point3d, new Point3d(Math.cos(0.5235987755982988), Math.sin(0.5235987755982988), 0.0)));
        point3d.set(1.0, 0.0, 0.0);
        matrix3d3.rotY(1.0471975511965976);
        matrix3d3.transform(point3d);
        ASSERT(equals(point3d, new Point3d(Math.cos(1.0471975511965976), 0.0, -Math.sin(1.0471975511965976))));
        final AxisAngle4d axisAngle4d = new AxisAngle4d(0.0, 1.0, 0.0, 1.0471975511965976);
        point3d.set(1.0, 0.0, 0.0);
        matrix3d3.set(axisAngle4d);
        matrix3d3.transform(point3d, point3d);
        ASSERT(equals(point3d, new Point3d(Math.cos(1.0471975511965976), 0.0, -Math.sin(1.0471975511965976))));
        final Quat4d quat4d = new Quat4d();
        point3d.set(1.0, 0.0, 0.0);
        quat4d.set(axisAngle4d);
        matrix3d5.set(quat4d);
        ASSERT(equals(matrix3d3, matrix3d5));
        matrix3d5.transform(point3d, point3d);
        ASSERT(equals(point3d, new Point3d(Math.cos(1.0471975511965976), 0.0, -Math.sin(1.0471975511965976))));
        axisAngle4d.set(1.0, 2.0, -3.0, 1.0471975511965976);
        Mat3dQuatAxisAngle(axisAngle4d);
        axisAngle4d.set(1.0, 2.0, 3.0, 3.141592653589793);
        Mat3dQuatAxisAngle(axisAngle4d);
        axisAngle4d.set(1.0, 0.1, 0.1, 3.141592653589793);
        Mat3dQuatAxisAngle(axisAngle4d);
        axisAngle4d.set(0.1, 1.0, 0.1, 3.141592653589793);
        Mat3dQuatAxisAngle(axisAngle4d);
        axisAngle4d.set(0.1, 0.1, 1.0, 3.141592653589793);
        Mat3dQuatAxisAngle(axisAngle4d);
        axisAngle4d.set(1.0, 1.0, 1.0, 2.0943951023931953);
        matrix3d3.set(axisAngle4d);
        point3d.set(1.0, 0.0, 0.0);
        matrix3d3.transform(point3d);
        ASSERT(equals(point3d, new Point3d(0.0, 1.0, 0.0)));
        matrix3d3.transform(point3d);
        ASSERT(equals(point3d, new Point3d(0.0, 0.0, 1.0)));
        matrix3d3.transform(point3d);
        ASSERT(equals(point3d, new Point3d(1.0, 0.0, 0.0)));
        matrix3d3.set(axisAngle4d);
        ASSERT(equals(matrix3d3.determinant(), 1.0));
        ASSERT(equals(matrix3d3.getScale(), 1.0));
        matrix3d5.set(axisAngle4d);
        matrix3d5.normalize();
        ASSERT(equals(matrix3d3, matrix3d5));
        matrix3d5.set(axisAngle4d);
        matrix3d5.normalizeCP();
        ASSERT(equals(matrix3d3, matrix3d5));
        final double n3 = 3.0;
        matrix3d5.rotZ(-0.7853981633974483);
        matrix3d5.mul(n3);
        ASSERT(equals(matrix3d5.determinant(), n3 * n3 * n3));
        ASSERT(equals(matrix3d5.getScale(), n3));
        matrix3d5.normalize();
        ASSERT(equals(matrix3d5.determinant(), 1.0));
        ASSERT(equals(matrix3d5.getScale(), 1.0));
        matrix3d5.rotX(1.0471975511965976);
        matrix3d5.mul(n3);
        ASSERT(equals(matrix3d5.determinant(), n3 * n3 * n3));
        ASSERT(equals(matrix3d5.getScale(), n3));
        matrix3d5.normalizeCP();
        ASSERT(equals(matrix3d5.determinant(), 1.0));
        ASSERT(equals(matrix3d5.getScale(), 1.0));
        matrix3d3.set(axisAngle4d);
        matrix3d5.invert(matrix3d3);
        matrix3d3.transpose();
        ASSERT(equals(matrix3d3, matrix3d5));
    }
    
    static void Mat3dQuatAxisAngle(final AxisAngle4d axisAngle4d) {
        final Matrix3d matrix3d = new Matrix3d();
        final Matrix3d matrix3d2 = new Matrix3d();
        final AxisAngle4d axisAngle4d2 = new AxisAngle4d();
        final Quat4d quat4d = new Quat4d();
        final Quat4d quat4d2 = new Quat4d();
        quat4d.set(axisAngle4d);
        axisAngle4d2.set(quat4d);
        ASSERT(equals(axisAngle4d, axisAngle4d2));
        final Quat4d quat4d3 = new Quat4d();
        quat4d3.set(axisAngle4d2);
        ASSERT(equals(quat4d, quat4d3));
        quat4d.set(axisAngle4d);
        matrix3d.set(quat4d);
        quat4d3.set(matrix3d);
        ASSERT(equals(quat4d, quat4d3));
        matrix3d2.set(quat4d3);
        ASSERT(equals(matrix3d, matrix3d2));
        matrix3d.set(axisAngle4d);
        axisAngle4d2.set(matrix3d);
        ASSERT(equals(axisAngle4d, axisAngle4d2));
        matrix3d2.set(axisAngle4d);
        ASSERT(equals(matrix3d, matrix3d2));
        axisAngle4d.x *= 2.0;
        axisAngle4d.y *= 2.0;
        axisAngle4d.z *= 2.0;
        matrix3d2.set(axisAngle4d);
        axisAngle4d.x = -axisAngle4d.x;
        axisAngle4d.y = -axisAngle4d.y;
        axisAngle4d.z = -axisAngle4d.z;
        axisAngle4d.angle = -axisAngle4d.angle;
        matrix3d2.set(axisAngle4d);
        ASSERT(equals(matrix3d, matrix3d2));
    }
    
    public static void Matrix3fTest() {
    }
    
    static void Mat4dQuatAxisAngle(final AxisAngle4d axisAngle4d) {
        final Matrix4d matrix4d = new Matrix4d();
        final Matrix4d matrix4d2 = new Matrix4d();
        final AxisAngle4d axisAngle4d2 = new AxisAngle4d();
        final Quat4d quat4d = new Quat4d();
        final Quat4d quat4d2 = new Quat4d();
        quat4d.set(axisAngle4d);
        axisAngle4d2.set(quat4d);
        ASSERT(equals(axisAngle4d, axisAngle4d2));
        final Quat4d quat4d3 = new Quat4d();
        quat4d3.set(axisAngle4d2);
        ASSERT(equals(quat4d, quat4d3));
        quat4d.set(axisAngle4d);
        matrix4d.set(quat4d);
        quat4d3.set(matrix4d);
        ASSERT(equals(quat4d, quat4d3));
        matrix4d2.set(quat4d3);
        ASSERT(equals(matrix4d, matrix4d2));
        matrix4d.set(axisAngle4d);
        axisAngle4d2.set(matrix4d);
        ASSERT(equals(axisAngle4d, axisAngle4d2));
        matrix4d2.set(axisAngle4d);
        ASSERT(equals(matrix4d, matrix4d2));
        axisAngle4d.x *= 2.0;
        axisAngle4d.y *= 2.0;
        axisAngle4d.z *= 2.0;
        matrix4d2.set(axisAngle4d);
        axisAngle4d.x = -axisAngle4d.x;
        axisAngle4d.y = -axisAngle4d.y;
        axisAngle4d.z = -axisAngle4d.z;
        axisAngle4d.angle = -axisAngle4d.angle;
        matrix4d2.set(axisAngle4d);
        ASSERT(equals(matrix4d, matrix4d2));
    }
    
    public static void Matrix4dTest() {
        final Matrix4d matrix4d = new Matrix4d();
        final Matrix4d matrix4d2 = new Matrix4d();
        matrix4d2.setIdentity();
        final Matrix4d matrix4d3 = new Matrix4d();
        final Matrix4d matrix4d4 = new Matrix4d();
        int n = 0;
        int n2 = 0;
        while (0 < 4) {
            while (0 < 4) {
                matrix4d3.setElement(0, 0, 3);
                ++n;
            }
            ++n2;
        }
        while (0 < 4) {
            while (0 < 4) {
                ASSERT(equals(matrix4d3.getElement(0, 0), 3));
                ++n;
            }
            ++n2;
        }
        final Matrix4d matrix4d5 = new Matrix4d(2.0, 1.0, 4.0, 1.0, -2.0, 3.0, -3.0, 1.0, -1.0, 1.0, 2.0, 2.0, 0.0, 8.0, 1.0, -10.0);
        final Matrix4d matrix4d6 = new Matrix4d(matrix4d5);
        matrix4d6.mul(matrix4d);
        ASSERT(equals(matrix4d6, matrix4d), "O = m2 x O");
        matrix4d6.mul(matrix4d5, matrix4d2);
        ASSERT(equals(matrix4d6, matrix4d5), "m2 = m1 x I");
        matrix4d6.negate(matrix4d5);
        matrix4d6.add(matrix4d5);
        ASSERT(equals(matrix4d6, matrix4d));
        matrix4d6.set(new double[] { 5.0, 1.0, 4.0, 0.0, 2.0, 3.0, -4.0, -1.0, 2.0, 3.0, -4.0, -1.0, 1.0, 1.0, 1.0, 1.0 });
        matrix4d6.negate(matrix4d5);
        final Matrix4d matrix4d7 = new Matrix4d(matrix4d5);
        matrix4d7.sub(matrix4d6);
        matrix4d7.mul(0.5);
        ASSERT(equals(matrix4d5, matrix4d7));
        final Matrix4d matrix4d8 = new Matrix4d(0.5, 1.0, 4.0, 1.0, -2.0, 3.0, -4.0, -1.0, 1.0, 9.0, 100.0, 2.0, -20.0, 2.0, 1.0, 9.0);
        matrix4d7.invert(matrix4d8);
        matrix4d7.mul(matrix4d8);
        ASSERT(equals(matrix4d7, matrix4d2));
        final Matrix4d matrix4d9 = new Matrix4d(-1.0, 2.0, 0.0, 3.0, -1.0, 1.0, -3.0, -1.0, 1.0, 2.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0);
        final Point3d point3d = new Point3d(1.0, 2.0, 3.0);
        final Vector3d vector3d = new Vector3d();
        final Vector3d vector3d2 = new Vector3d(1.0, 2.0, 3.0);
        final Vector4d vector4d = new Vector4d(2.0, -1.0, -4.0, 1.0);
        ASSERT(matrix4d9.toString().equals("[" + VecmathTest.NL + "  [-1.0\t2.0\t0.0\t3.0]" + VecmathTest.NL + "  [-1.0\t1.0\t-3.0\t-1.0]" + VecmathTest.NL + "  [1.0\t2.0\t1.0\t1.0]" + VecmathTest.NL + "  [0.0\t0.0\t0.0\t1.0] ]"));
        matrix4d9.transform(point3d);
        ASSERT(equals(point3d, new Point3d(6.0, -9.0, 9.0)));
        matrix4d9.transform(vector4d, vector4d);
        ASSERT(equals(vector4d, new Vector4d(-1.0, 8.0, -3.0, 1.0)));
        point3d.set(1.0, 0.0, 0.0);
        matrix4d9.rotZ(0.5235987755982988);
        matrix4d9.transform(point3d);
        ASSERT(equals(point3d, new Point3d(Math.cos(0.5235987755982988), Math.sin(0.5235987755982988), 0.0)));
        point3d.set(1.0, 0.0, 0.0);
        matrix4d9.rotY(1.0471975511965976);
        matrix4d9.transform(point3d);
        ASSERT(equals(point3d, new Point3d(Math.cos(1.0471975511965976), 0.0, -Math.sin(1.0471975511965976))));
        final AxisAngle4d axisAngle4d = new AxisAngle4d(0.0, 1.0, 0.0, 1.0471975511965976);
        point3d.set(1.0, 0.0, 0.0);
        matrix4d9.set(axisAngle4d);
        matrix4d9.transform(point3d, point3d);
        ASSERT(equals(point3d, new Point3d(Math.cos(1.0471975511965976), 0.0, -Math.sin(1.0471975511965976))));
        final Quat4d quat4d = new Quat4d();
        point3d.set(1.0, 0.0, 0.0);
        quat4d.set(axisAngle4d);
        matrix4d8.set(quat4d);
        ASSERT(equals(matrix4d9, matrix4d8));
        matrix4d8.transform(point3d, point3d);
        ASSERT(equals(point3d, new Point3d(Math.cos(1.0471975511965976), 0.0, -Math.sin(1.0471975511965976))));
        axisAngle4d.set(1.0, 2.0, -3.0, 1.0471975511965976);
        Mat4dQuatAxisAngle(axisAngle4d);
        axisAngle4d.set(1.0, 2.0, 3.0, 3.141592653589793);
        Mat4dQuatAxisAngle(axisAngle4d);
        axisAngle4d.set(1.0, 0.1, 0.1, 3.141592653589793);
        Mat4dQuatAxisAngle(axisAngle4d);
        axisAngle4d.set(0.1, 1.0, 0.1, 3.141592653589793);
        Mat4dQuatAxisAngle(axisAngle4d);
        axisAngle4d.set(0.1, 0.1, 1.0, 3.141592653589793);
        Mat4dQuatAxisAngle(axisAngle4d);
        axisAngle4d.set(1.0, 1.0, 1.0, 2.0943951023931953);
        matrix4d9.set(axisAngle4d);
        point3d.set(1.0, 0.0, 0.0);
        matrix4d9.transform(point3d);
        ASSERT(equals(point3d, new Point3d(0.0, 1.0, 0.0)));
        matrix4d9.transform(point3d);
        ASSERT(equals(point3d, new Point3d(0.0, 0.0, 1.0)));
        matrix4d9.transform(point3d);
        ASSERT(equals(point3d, new Point3d(1.0, 0.0, 0.0)));
        matrix4d9.set(axisAngle4d);
        ASSERT(equals(matrix4d9.determinant(), 1.0));
        ASSERT(equals(matrix4d9.getScale(), 1.0));
        matrix4d8.set(axisAngle4d);
        matrix4d9.set(axisAngle4d);
        matrix4d8.invert(matrix4d9);
        matrix4d9.transpose();
        ASSERT(equals(matrix4d9, matrix4d8));
        final Matrix3d matrix3d = new Matrix3d();
        matrix3d.set(axisAngle4d);
        final Matrix3d matrix3d2 = new Matrix3d();
        vector3d2.set(2.0, -1.0, -1.0);
        matrix4d9.set(matrix3d, vector3d2, 0.4);
        matrix4d8.set(matrix3d, vector3d2, 0.4);
        final Vector3d vector3d3 = new Vector3d();
        final double value = matrix4d9.get(matrix3d2, vector3d3);
        ASSERT(equals(matrix3d, matrix3d2));
        ASSERT(equals(value, 0.4));
        ASSERT(equals(vector3d2, vector3d3));
        ASSERT(equals(matrix4d9, matrix4d8));
    }
    
    public static void Matrix4fTest() {
    }
    
    public static void GMatrixTest() {
        final GMatrix gMatrix = new GMatrix(4, 4);
        final GMatrix gMatrix2 = new GMatrix(4, 4);
        gMatrix2.setZero();
        final GMatrix gMatrix3 = new GMatrix(3, 4);
        gMatrix3.setZero();
        final GMatrix gMatrix4 = new GMatrix(3, 4);
        final GMatrix gMatrix5 = new GMatrix(3, 4);
        final Matrix3d matrix3d = new Matrix3d();
        final Matrix3d matrix3d2 = new Matrix3d();
        int n = 0;
        int n2 = 0;
        while (0 < 3) {
            while (0 < 4) {
                gMatrix4.setElement(0, 0, 2);
                if (0 < 3) {
                    matrix3d.setElement(0, 0, 2);
                }
                ++n;
            }
            ++n2;
        }
        while (0 < 3) {
            while (0 < 4) {
                ASSERT(equals(gMatrix4.getElement(0, 0), 2));
                ++n;
            }
            ++n2;
        }
        gMatrix4.get(matrix3d2);
        ASSERT(equals(matrix3d, matrix3d2));
        gMatrix5.mul(gMatrix4, gMatrix);
        ASSERT(equals(gMatrix4, gMatrix5));
        gMatrix5.mul(gMatrix4, gMatrix2);
        ASSERT(equals(gMatrix3, gMatrix5));
        final Matrix4d matrix4d = new Matrix4d(1.0, 2.0, 3.0, 4.0, -2.0, 3.0, -1.0, 3.0, -1.0, -2.0, -4.0, 1.0, 1.0, 1.0, -1.0, -2.0);
        final Matrix4d matrix4d2 = new Matrix4d();
        final Matrix4d matrix4d3 = new Matrix4d();
        matrix4d3.set(matrix4d);
        gMatrix4.setSize(4, 4);
        gMatrix5.setSize(4, 4);
        gMatrix4.set(matrix4d);
        ASSERT(gMatrix4.toString().equals("[" + VecmathTest.NL + "  [1.0\t2.0\t3.0\t4.0]" + VecmathTest.NL + "  [-2.0\t3.0\t-1.0\t3.0]" + VecmathTest.NL + "  [-1.0\t-2.0\t-4.0\t1.0]" + VecmathTest.NL + "  [1.0\t1.0\t-1.0\t-2.0] ]"));
        gMatrix5.set(gMatrix4);
        gMatrix4.invert();
        matrix4d.invert();
        matrix4d3.mul(matrix4d);
        ASSERT(equals(matrix4d3, new Matrix4d(1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0)));
        gMatrix4.get(matrix4d2);
        ASSERT(equals(matrix4d, matrix4d2));
        gMatrix4.mul(gMatrix5);
        ASSERT(equals(gMatrix4, gMatrix));
        final Matrix4d matrix4d4 = new Matrix4d(1.0, 2.0, 3.0, 4.0, -2.0, 3.0, -1.0, 3.0, -1.0, -2.0, -4.0, 1.0, 1.0, 1.0, -1.0, -2.0);
        final Vector4d vector4d = new Vector4d(1.0, -1.0, -1.0, 2.0);
        final Vector4d vector4d2 = new Vector4d();
        final Vector4d vector4d3 = new Vector4d(4.0, 2.0, 7.0, -3.0);
        matrix4d4.transform(vector4d, vector4d2);
        ASSERT(equals(vector4d2, vector4d3));
        gMatrix4.set(matrix4d4);
        final GVector gVector = new GVector(4);
        final GVector gVector2 = new GVector(4);
        final GVector gVector3 = new GVector(4);
        gVector.set(vector4d);
        gVector3.set(vector4d3);
        final GVector gVector4 = new GVector(4);
        gVector4.mul(gMatrix4, gVector);
        ASSERT(equals(gVector4, gVector3));
        final GVector gVector5 = new GVector(4);
        gMatrix4.LUD(gMatrix5, gVector5);
        ASSERT(checkLUD(gMatrix4, gMatrix5, gVector5));
        final GVector gVector6 = new GVector(4);
        gVector6.LUDBackSolve(gMatrix5, gVector3, gVector5);
        ASSERT(equals(gVector6, gVector));
        final GMatrix gMatrix6 = new GMatrix(gMatrix4.getNumRow(), gMatrix4.getNumRow());
        final GMatrix gMatrix7 = new GMatrix(gMatrix4.getNumRow(), gMatrix4.getNumCol());
        final GMatrix gMatrix8 = new GMatrix(gMatrix4.getNumCol(), gMatrix4.getNumCol());
        ASSERT(gMatrix4.SVD(gMatrix6, gMatrix7, gMatrix8) == 4);
        ASSERT(checkSVD(gMatrix4, gMatrix6, gMatrix7, gMatrix8));
        gVector6.SVDBackSolve(gMatrix6, gMatrix7, gMatrix8, gVector3);
        ASSERT(equals(gVector6, gVector));
    }
    
    static boolean checkLUD(final GMatrix gMatrix, final GMatrix gMatrix2, final GVector gVector) {
        final int numCol = gMatrix.getNumCol();
        while (0 < numCol) {
            while (0 < numCol) {
                double n = 0.0;
                while (0 <= ((0 < 0) ? 0 : 0)) {
                    if (false) {
                        n += gMatrix2.getElement(0, 0) * gMatrix2.getElement(0, 0);
                    }
                    else {
                        n += gMatrix2.getElement(0, 0);
                    }
                    int n2 = 0;
                    ++n2;
                }
                if (Math.abs(n - gMatrix.getElement((int)gVector.getElement(0), 0)) > 0.0f) {
                    System.out.println("a[" + 0 + "," + 0 + "] = " + n + "(LU)ij ! = " + gMatrix.getElement((int)gVector.getElement(0), 0));
                }
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        return false;
    }
    
    static boolean checkSVD(final GMatrix gMatrix, final GMatrix gMatrix2, final GMatrix gMatrix3, final GMatrix gMatrix4) {
        final int n = (gMatrix3.getNumRow() < gMatrix3.getNumRow()) ? gMatrix3.getNumRow() : gMatrix3.getNumCol();
        while (0 < gMatrix.getNumRow()) {
            while (0 < gMatrix.getNumCol()) {
                double n2 = 0.0;
                while (0 < gMatrix.getNumCol()) {
                    n2 += gMatrix2.getElement(0, 0) * gMatrix3.getElement(0, 0) * gMatrix4.getElement(0, 0);
                    int n3 = 0;
                    ++n3;
                }
                if (0.0f < Math.abs(gMatrix.getElement(0, 0) - n2)) {
                    System.out.println("(SVD)ij = " + n2 + " != a[" + 0 + "," + 0 + "] = " + gMatrix.getElement(0, 0));
                }
                int n4 = 0;
                ++n4;
            }
            int n5 = 0;
            ++n5;
        }
        if (!false) {
            System.out.print("[W] = ");
            System.out.println(gMatrix3);
            System.out.print("[U] = ");
            System.out.println(gMatrix2);
            System.out.print("[V] = ");
            System.out.println(gMatrix4);
        }
        return false;
    }
    
    public static void SVDTest() {
        final GMatrix gMatrix = new GMatrix(5, 4, new double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 0.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0, 1.0 });
        final GMatrix gMatrix2 = new GMatrix(5, 5);
        final GMatrix gMatrix3 = new GMatrix(5, 4);
        final GMatrix gMatrix4 = new GMatrix(4, 4);
        gMatrix.SVD(gMatrix2, gMatrix3, gMatrix4);
        final GMatrix gMatrix5 = new GMatrix(5, 4);
        gMatrix5.mul(gMatrix2, gMatrix3);
        gMatrix4.transpose();
        gMatrix5.mul(gMatrix4);
        if (!equals(gMatrix5, gMatrix)) {
            System.out.println("matU=" + gMatrix2);
            System.out.println("matW=" + gMatrix3);
            System.out.println("matV=" + gMatrix4);
            System.out.println("matA=" + gMatrix);
            System.out.println("UWV=" + gMatrix5);
        }
        ASSERT(equals(gMatrix5, gMatrix));
    }
}
