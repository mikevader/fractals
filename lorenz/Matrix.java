
public class Matrix {

    public double[][] m;

    public Matrix () {
        m = new double[3][3];
    }

    public static Matrix add (Matrix a, Matrix b) {
        Matrix c = new Matrix ();
        c.m[0][0] = a.m[0][0] + b.m[0][0];
        c.m[0][1] = a.m[0][1] + b.m[0][1];
        c.m[0][2] = a.m[0][2] + b.m[0][2];

        c.m[1][0] = a.m[1][0] + b.m[1][0];
        c.m[1][1] = a.m[1][1] + b.m[1][1];
        c.m[1][2] = a.m[1][2] + b.m[1][2];

        c.m[2][0] = a.m[2][0] + b.m[2][0];
        c.m[2][1] = a.m[2][1] + b.m[2][1];
        c.m[2][2] = a.m[2][2] + b.m[2][2];
        return c;
    }

    public static Matrix multiply (Matrix a, Matrix b) {
        Matrix c = new Matrix ();
        c.m[0][0] = a.m[0][0] * b.m[0][0] + a.m[0][1] * b.m[1][0] + a.m[0][2] * b.m[2][0];
        c.m[0][1] = a.m[0][0] * b.m[0][1] + a.m[0][1] * b.m[1][1] + a.m[0][2] * b.m[2][1];
        c.m[0][2] = a.m[0][0] * b.m[0][2] + a.m[0][1] * b.m[1][2] + a.m[0][2] * b.m[2][2];

        c.m[1][0] = a.m[1][0] * b.m[0][0] + a.m[1][1] * b.m[1][0] + a.m[1][2] * b.m[2][0];
        c.m[1][1] = a.m[1][0] * b.m[0][1] + a.m[1][1] * b.m[1][1] + a.m[1][2] * b.m[2][1];
        c.m[1][2] = a.m[1][0] * b.m[0][2] + a.m[1][1] * b.m[1][2] + a.m[1][2] * b.m[2][2];

        c.m[2][0] = a.m[2][0] * b.m[0][0] + a.m[2][1] * b.m[1][0] + a.m[2][2] * b.m[2][0];
        c.m[2][1] = a.m[2][0] * b.m[0][1] + a.m[2][1] * b.m[1][1] + a.m[2][2] * b.m[2][1];
        c.m[2][2] = a.m[2][0] * b.m[0][2] + a.m[2][1] * b.m[1][2] + a.m[2][2] * b.m[2][2];
        return c;
    }

    /*
     * Beschleunigte Version fuer M = A * M
     */

    public void multiply (Matrix a) {
        double m00 = a.m[0][0] * m[0][0] + a.m[0][1] * m[1][0] + a.m[0][2] * m[2][0];
        double m01 = a.m[0][0] * m[0][1] + a.m[0][1] * m[1][1] + a.m[0][2] * m[2][1];
        double m02 = a.m[0][0] * m[0][2] + a.m[0][1] * m[1][2] + a.m[0][2] * m[2][2];

        double m10 = a.m[1][0] * m[0][0] + a.m[1][1] * m[1][0] + a.m[1][2] * m[2][0];
        double m11 = a.m[1][0] * m[0][1] + a.m[1][1] * m[1][1] + a.m[1][2] * m[2][1];
        double m12 = a.m[1][0] * m[0][2] + a.m[1][1] * m[1][2] + a.m[1][2] * m[2][2];

        double m20 = a.m[2][0] * m[0][0] + a.m[2][1] * m[1][0] + a.m[2][2] * m[2][0];
        double m21 = a.m[2][0] * m[0][1] + a.m[2][1] * m[1][1] + a.m[2][2] * m[2][1];
        double m22 = a.m[2][0] * m[0][2] + a.m[2][1] * m[1][2] + a.m[2][2] * m[2][2];

        m[0][0] = m00; m[0][1] = m01; m[0][2] = m02;
        m[1][0] = m10; m[1][1] = m11; m[1][2] = m12;
        m[2][0] = m20; m[2][1] = m21; m[2][2] = m22;
    }

    public static Vektor multiply (Matrix a, Vektor b) {
        Vektor c = new Vektor ();
        c.v[0] = a.m[0][0] * b.v[0] + a.m[0][1] * b.v[1] + a.m[0][2] * b.v[2];
        c.v[1] = a.m[1][0] * b.v[0] + a.m[1][1] * b.v[1] + a.m[1][2] * b.v[2];
        c.v[2] = a.m[2][0] * b.v[0] + a.m[2][1] * b.v[1] + a.m[2][2] * b.v[2];
        return c;
    }

    public static void multiply (Matrix a, Vektor b, Vektor c) {
        c.v[0] = a.m[0][0] * b.v[0] + a.m[0][1] * b.v[1] + a.m[0][2] * b.v[2];
        c.v[1] = a.m[1][0] * b.v[0] + a.m[1][1] * b.v[1] + a.m[1][2] * b.v[2];
        c.v[2] = a.m[2][0] * b.v[0] + a.m[2][1] * b.v[1] + a.m[2][2] * b.v[2];
    }
}
