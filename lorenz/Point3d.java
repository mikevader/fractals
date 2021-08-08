/*
 * Point3d --
 *
 *    Diese Klasse repraesentiert einen Punkt im Raum. Die Koordinaten werden
 *    mit Array von 'double'-Werten dargestellt.
 *
 */

public class Point3d {

    public double x, y, z;
    public int    mapIndex;

    public Point3d () {
        x = y = z = 0.0;
        mapIndex = 0;
    }

    public Point3d (double x, double y, double z, int mapIndex) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.mapIndex = mapIndex;
    }

    public static Point3d add (Point3d a, Point3d b) {
        return new Point3d (a.x + b.x, a.y + b.y, a.z + b.z, a.mapIndex);
    }

    public static Point3d multiply (double a, Point3d b) {
        return new Point3d (a * b.x, a * b.y, a * b.z, b.mapIndex);
    }
}
