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
    
    private final int    nColors = 256;
    private final double darkFactor = 0.9;

    private int       width, height;
    private double    xMin, xMax, yMin, yMax, zMin, zMax;
    private double    pixX, pixY, pixZ;
    private double    phiX, phiY;
    private double    R00, R01, R02;
    private double    R10, R11, R12;
    private double    R20, R21, R22;
    private CompareZ  cmpZ;
    private Vector    colorMaps;


    public Graph3d (int width, int height) {
        this.width  = width;
        this.height = height;
        R00 = R11 = R22 = 1.0;
        R01 = R02 = 0.0;
        R10 = R12 = 0.0;
        R20 = R21 = 0.0;
        cmpZ        = new CompareZ ();
        colorMaps   = new Vector ();
    }
    
    public void setWindowSize (int width, int height) {
        this.width  = width;
        this.height = height;
        updateValues ();
    }

    public void setDisplayRange (double xMin, double xMax,
            double yMin, double yMax) {
        this.xMin = xMin;   this.xMax = xMax;
        this.yMin = yMin;   this.yMax = yMax;
        updateValues ();
    }

    public void moveDisplayRange (int dCol, int dLine) {
        double dx = (double) dCol / pixX;
        double dy = (double) dLine / pixY;
        setDisplayRange (xMin + dx, xMax + dx, yMin + dy, yMax + dy);
    }

    public void initColorMap (int mapIndex, Color c) {
        Color[] colorMap = new Color[nColors];
        double redValue   = c.getRed ();
        double greenValue = c.getGreen ();
        double blueValue  = c.getBlue ();
        for (int i=0; i<nColors; i++) {
            double f = (double) i / (double) (nColors - 1);
            int red   = (int) colorFunc_01 (i, redValue);
            int green = (int) colorFunc_01 (i, greenValue);
            int blue  = (int) colorFunc_01 (i, blueValue);
            colorMap[i] = new Color (red, green, blue);
        }
        if (colorMaps.size () <= mapIndex) {
            colorMaps.addElement (colorMap);
        } else {
            colorMaps.setElementAt (colorMap, mapIndex);
        }
    }
    
    private double colorFunc_01 (int i, double maxValue) {
        double f = (double) i / (double) (nColors - 1);
        return maxValue * (1.0 - darkFactor * (1.0 - f));
    }
    
    private double colorFunc_02 (int i, double maxValue) {
        double f = (double) i / (double) (nColors - 1);
        return maxValue * (1.0 - darkFactor * (1.0 - Math.pow (f, 1.2)));
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
        double r10 =  cosPhi * R10 + sinPhi * R20;
        double r11 =  cosPhi * R11 + sinPhi * R21;
        double r12 =  cosPhi * R12 + sinPhi * R22;
        double r20 = -sinPhi * R10 + cosPhi * R20;
        double r21 = -sinPhi * R11 + cosPhi * R21;
        double r22 = -sinPhi * R12 + cosPhi * R22;
        R10 = r10; R11 = r11; R12 = r12;
        R20 = r20; R21 = r21; R22 = r22;
    }

    public void rotateX (int nPix) {
        rotateX ((double) nPix * phiY);
    }

    public void rotateY (double phi) {
        double sinPhi = Math.sin (phi);
        double cosPhi = Math.cos (phi);
        double r00 =  cosPhi * R00 - sinPhi * R20;
        double r01 =  cosPhi * R01 - sinPhi * R21;
        double r02 =  cosPhi * R02 - sinPhi * R22;
        double r20 =  sinPhi * R00 + cosPhi * R20;
        double r21 =  sinPhi * R01 + cosPhi * R21;
        double r22 =  sinPhi * R02 + cosPhi * R22;
        R00 = r00; R01 = r01; R02 = r02;
        R20 = r20; R21 = r21; R22 = r22;
    }

    public void rotateY (int nPix) {
        rotateY ((double) nPix * phiX);
    }

    public void rotateZ (double phi) {
        double sinPhi = Math.sin (phi);
        double cosPhi = Math.cos (phi);
        double r00 =  cosPhi * R00 + sinPhi * R10;
        double r01 =  cosPhi * R01 + sinPhi * R11;
        double r02 =  cosPhi * R02 + sinPhi * R12;
        double r10 = -sinPhi * R00 + cosPhi * R10;
        double r11 = -sinPhi * R01 + cosPhi * R11;
        double r12 = -sinPhi * R02 + cosPhi * R12;
        R00 = r00; R01 = r01; R02 = r02;
        R10 = r10; R11 = r11; R12 = r12;
    }
    
    public void rotateZ (int nPix) {
        rotateZ ((double) nPix * phiY);
    }

    public void setPoint (Graphics g, Point3d p) {
        Point3d q = transform (p);
        g.fillRect (toCol (q.x), toLine (q.y), 1, 1);
    }
    
    public void setPath (Graphics g, Vector[] path) {
        int nPoints    = 0;
        int pointIndex = 0;
        Vector[]  p = new Vector[path.length];
        Point3d[] q;
        
        for (int i=0; i<path.length; i++) {
            p[i] = (Vector) path[i].clone ();
            nPoints += p[i].size ();
        }
        q = new Point3d[nPoints];
        for (int i=0; i<p.length; i++) {
            Vector temp = p[i];
            for (int j=0; j<temp.size (); j++) {
                q[pointIndex++] = transform ((Point3d) temp.elementAt (j));
            }
        }
        Arrays.sort (q, cmpZ);
        zMin = q[0].z;
        zMax = q[q.length-1].z;
        pixZ = (double) (nColors - 1) / (zMax - zMin);
        for (int i=0; i<nPoints; i++) {
            Color[] colorMap = (Color[]) colorMaps.elementAt (q[i].mapIndex);
            g.setColor (colorMap[toColorIndex (q[i].z)]);
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
        q.x = R00 * p.x + R01 * p.y + R02 * p.z;
        q.y = R10 * p.x + R11 * p.y + R12 * p.z;
        q.z = R20 * p.x + R21 * p.y + R22 * p.z;
        q.mapIndex = p.mapIndex;
        return q;
    }

    private int toCol (double x) {
        return (int) (pixX * (x - xMin));
    }

    private int toLine (double y) {
        return (int) (pixY * (yMax - y));
    }
    
    private int toColorIndex (double z) {
        return (int) (pixZ * (z - zMin));
    }
}

