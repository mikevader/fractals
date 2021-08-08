/*
 * Graph3d --
 *
 *    Diese Klasse rechnet die Punkte aus dem mathematischen 3D-Raum in
 *    Pixel-Koordinaten um und stellt Methoden zur Verfuegung um Punkte
 *    und Linien zu zeichnen.
 *
 *    NB: Fuer die Umrechnung werden Matrizen verwendet, die man
 *    grundsaetzlich als eigenen Klasse (wie die Vektoren) realisieren
 *    koennte. Ausser der Klasse 'Graph3d' verwendet aber niemand Matrizen
 *    und ausserdem koennen einige Operationen (wie das Drehen oder das
 *    Umrechnen in 2D-Koordinaten) effizienter realisert werden, wenn man
 *    nicht allgemeine Matrix-Multiplikationen verwendet.
 */

import java.awt.*;
import java.util.*;

public class Graph3d {
    
    public static class CompareZ implements Comparator {
        public int compare (Object o1, Object o2) {
            if (((Point3d) o1).z < ((Point3d) o2).z) {
                return -1;
            }
            if (((Point3d) o1).z > ((Point3d) o2).z) {
                return +1;
            }
            return 0;
        }
    }
    
    private int        width, height;
    private double     xMin, xMax, yMin, yMax;
    private double     pixX, pixY;
    private double     phiX, phiY;
    private double[][] R;
    private int        colIndex;
    private Color[][]  colArray;
    private CompareZ   cmpZ;

    public Graph3d (int width, int height) {
        this.width  = width;
        this.height = height;
        R           = new double[3][3];
        R[0][0] = R[1][1] = R[2][2] = 1.0;
        colArray    = new Color[10][];
        cmpZ        = new CompareZ ();
    }
    
    public void setWindowSize (int width, int height) {
        this.width  = width;
        this.height = height;
        updateValues ();
    }

    public void setDisplayRange (double xMin, double xMax,
            double yMin, double yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        updateValues ();
    }

    public void moveDisplayRange (int dCol, int dLine) {
        double dx = (double) dCol / pixX;
        double dy = (double) dLine / pixY;
        setDisplayRange (xMin + dx, xMax + dx, yMin + dy, yMax + dy);
    }

    private void updateValues () {
        pixX = (double) width / (xMax - xMin);
        pixY = (double) height / (yMax - yMin);
        phiX = 2.0 * Math.PI / (double) width;
        phiY = 2.0 * Math.PI / (double) height;
    }

    /*
     * rotateX/Y/Z --
     *
     *    Die Methoden zum Rotieren des Raumes koennen entweder mit einem
     *    'double'-Wert (= Drehwinkel in 'rad') oder mit einen 'int'-Wert
     *    (nur bei 'rotateX/Y') aufgerufen werden. 
     */
    public void rotateX (double phi) {
        double sinPhi = Math.sin (phi);
        double cosPhi = Math.cos (phi);
        double R10 =  cosPhi * R[1][0] + sinPhi * R[2][0];
        double R11 =  cosPhi * R[1][1] + sinPhi * R[2][1];
        double R12 =  cosPhi * R[1][2] + sinPhi * R[2][2];
        double R20 = -sinPhi * R[1][0] + cosPhi * R[2][0];
        double R21 = -sinPhi * R[1][1] + cosPhi * R[2][1];
        double R22 = -sinPhi * R[1][2] + cosPhi * R[2][2];
        R[1][0] = R10; R[1][1] = R11; R[1][2] = R12;
        R[2][0] = R20; R[2][1] = R21; R[2][2] = R22;
    }

    public void rotateX (int nPix) {
        rotateX ((double) nPix * phiY);
    }

    public void rotateY (double phi) {
        double sinPhi = Math.sin (phi);
        double cosPhi = Math.cos (phi);
        double R00 =  cosPhi * R[0][0] - sinPhi * R[2][0];
        double R01 =  cosPhi * R[0][1] - sinPhi * R[2][1];
        double R02 =  cosPhi * R[0][2] - sinPhi * R[2][2];
        double R20 =  sinPhi * R[0][0] + cosPhi * R[2][0];
        double R21 =  sinPhi * R[0][1] + cosPhi * R[2][1];
        double R22 =  sinPhi * R[0][2] + cosPhi * R[2][2];
        R[0][0] = R00; R[0][1] = R01; R[0][2] = R02;
        R[2][0] = R20; R[2][1] = R21; R[2][2] = R22;
    }

    public void rotateY (int nPix) {
        rotateY ((double) nPix * phiX);
    }

    public void rotateZ (double phi) {
        double sinPhi = Math.sin (phi);
        double cosPhi = Math.cos (phi);
        double R00 =  cosPhi * R[0][0] + sinPhi * R[1][0];
        double R01 =  cosPhi * R[0][1] + sinPhi * R[1][1];
        double R02 =  cosPhi * R[0][2] + sinPhi * R[1][2];
        double R10 = -sinPhi * R[0][0] + cosPhi * R[1][0];
        double R11 = -sinPhi * R[0][1] + cosPhi * R[1][1];
        double R12 = -sinPhi * R[0][2] + cosPhi * R[1][2];
        R[0][0] = R00; R[0][1] = R01; R[0][2] = R02;
        R[1][0] = R10; R[1][1] = R11; R[1][2] = R12;
    }
    
    public void rotateZ (int nPix) {
        rotateZ ((double) nPix * phiY);
    }

    public void initColor (int i, Color c) {
        colArray[i] = new Color[256];
        int ir = c.getRed (), ig = c.getGreen (), ib = c.getBlue ();
        for (int j=0; j<256; j++) {
            int r = (int) colFunc2 (ir, 0.8, 255, j);
            int g = (int) colFunc2 (ig, 0.8, 255, j);
            int b = (int) colFunc2 (ib, 0.8, 255, j);
            colArray[i][j] = new Color (r, g, b);
        }
    }
    
    private double colFunc1 (double initVal, double darkFactor,
            int nPoints, int i) {
        double x = (double) i / (double) nPoints;
        return initVal * ((1.0 - darkFactor) + darkFactor * Math.pow (x, 1.0/2.0));
    }
    
    private double colFunc2 (double initVal, double darkFactor,
            int nPoints, int i) {
        double x = (double) i / (double) nPoints;
        return initVal * ((1.0 - darkFactor) + darkFactor * x);
    }
    
    public void setColor (int i) {
        colIndex = i;
    }
    
    public void setPoint (Graphics g, Point3d p) {
        Point3d q = transform (p);
        g.fillRect (toCol (q.x), toLine (q.y), 1, 1);
    }
    
    public void setPath (Graphics g, Vector path) {
        Vector p = (Vector) path.clone ();
        Point3d[] q = new Point3d[p.size ()];
        for (int i=0; i<p.size (); i++) {
            q[i] = transform ((Point3d) p.elementAt (i));
        }
        Arrays.sort (q, cmpZ);
        double zMin = q[0].z;
        double zMax = q[q.length-1].z;
        double zDiff = 255.0 / (zMax - zMin);
        for (int i=0; i<q.length; i++) {
            int index = (int) (zDiff * (q[i].z - zMin));
            g.setColor (colArray[colIndex][index]);
            g.fillRect (toCol (q[i].x), toLine (q[i].y), 1, 1);
        }
    }

    public void setLine (Graphics g, Point3d p1, Point3d p2) {
        Point3d q1 = transform (p1);
        Point3d q2 = transform (p2);
        g.drawLine (toCol (q1.x), toLine (q1.y),
                toCol (q2.x), toLine (q2.y));
    }
    
    private Point3d transform (Point3d p) {
        Point3d q = new Point3d ();
        q.x = R[0][0] * p.x + R[0][1] * p.y + R[0][2] * p.z;
        q.y = R[1][0] * p.x + R[1][1] * p.y + R[1][2] * p.z;
        q.z = R[2][0] * p.x + R[2][1] * p.y + R[2][2] * p.z;
        return q;
    }

    private int toCol (double x) {
        return (int) (pixX * (x - xMin));
    }

    private int toLine (double y) {
        return (int) (pixY * (yMax - y));
    }
}

