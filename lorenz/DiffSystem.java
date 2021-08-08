
public abstract class DiffSystem {

    protected abstract Vektor df (Vektor x);

    public Vektor integrate (Vektor x, double dx) {
        Vektor k1, k2, k3, k4;

        k1 = Vektor.multiply (dx, df(x));
        k2 = Vektor.multiply (dx, df(Vektor.add (x, Vektor.multiply (0.5, k1))));
        k3 = Vektor.multiply (dx, df(Vektor.add (x, Vektor.multiply (0.5, k2))));
        k4 = Vektor.multiply (dx, df(Vektor.add (x, k3)));
        return Vektor.add (x, Vektor.multiply (1.0/6.0, Vektor.add (k1,
                Vektor.add (k2, Vektor.add (k3, k4)))));
    }
}

