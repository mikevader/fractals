/*
 * DiffSolver --
 *
 *     Fuer die Integration des Diff.gl-System des Lorenz-Attraktors wird
 *     zunaechst eine abstrakte Klasse eingefuehrt. Sie enthaelt eine
 *     abstrakte Methode 'df', deren konkrete Implementation das Diff.gl-
 *     System implementieren muss. Die Methode 'next' fuehrt einen Schritt
 *     der Integration durch. Fuer die Integration wird das Runge-Kutta-
 *     Verfahren 4. Ordnung verwendet. 
 */
 
public abstract class DiffSolver {

    public abstract Point3d df (Point3d x);

    public Point3d next (Point3d x, double dx) {
        Point3d k1, k2, k3, k4;

        k1 = Point3d.multiply (dx, df(x));
        k2 = Point3d.multiply (dx, df(Point3d.add (x, Point3d.multiply (0.5, k1))));
        k3 = Point3d.multiply (dx, df(Point3d.add (x, Point3d.multiply (0.5, k2))));
        k4 = Point3d.multiply (dx, df(Point3d.add (x, k3)));
        return Point3d.add (x, Point3d.multiply (1.0/6.0, Point3d.add (k1,
                Point3d.add (k2, Point3d.add (k3, k4)))));
    }
}

