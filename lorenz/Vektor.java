/*
 * Vektor --
 *
 *    Diese Klasse repraesentiert einen Punkt im Raum. Die Koordinaten werden
 *    mit Array von 'double'-Werten dargestellt.
 *
 *    NB: Obwohl mit der Klasse grundsaetzlich Vektoren mit beliebig vielen
 *    Komponenten dargestellt werden koennen, ist diese Implementation auf
 *    drei Komponenten beschraenkt. 
 */

public class Vektor {

    public double[] v;

    public Vektor () {
        v = new double[3];
    }

    public Vektor (double x, double y, double z) {
        v = new double[] { x, y, z };
    }

    public static Vektor add (Vektor a, Vektor b) {
        return new Vektor (a.v[0] + b.v[0], a.v[1] + b.v[1],
                a.v[2] + b.v[2]);
    }

    public static Vektor multiply (double a, Vektor b) {
        return new Vektor (a * b.v[0], a * b.v[1], a * b.v[2]);
    }
}
