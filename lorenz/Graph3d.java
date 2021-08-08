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
    
    private final int    nColors    = 256;
    private final double darkFactor = 0.7;

    private int       width, height;
    private double    xMin, xMax, yMin, yMax, zMin, zMax;
    private double    pixX, pixY, pixZ;
    private double    phiX, phiY;
    private double    R00, R01, R02;
    private double    R10, R11, R12;
    private double    R20, R21, R22;
    private Color[][] colorMaps;

    public Graph3d (int width, int height) {
        this.width  = width;
        this.height = height;
        R00 = R11 = R22 = 1.0;
        R01 = R02 = 0.0;
        R10 = R12 = 0.0;
        R20 = R21 = 0.0;
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
        Color[][] tmp = colorMaps;
        colorMaps = new Color[mapIndex + 1][];
        if (tmp != null) {
            for (int i=0; i<tmp.length; i++) {
                colorMaps[i] = tmp[i];
            }
        }
        colorMaps[mapIndex] = new Color[nColors];
        double redValue   = c.getRed ();
        double greenValue = c.getGreen ();
        double blueValue  = c.getBlue ();
        for (int i=0; i<nColors; i++) {
            double f = (double) i / (double) (nColors - 1);
            int red   = (int) colorFunc_02 (i, redValue);
            int green = (int) colorFunc_02 (i, greenValue);
            int blue  = (int) colorFunc_02 (i, blueValue);
            colorMaps[mapIndex][i] = new Color (red, green, blue);
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

    public void setPoints (Graphics g, Vector points) {
        Vector    p = (Vector) points.clone ();
        Point3d[] q = new Point3d[p.size ()];
        
        for (int i=0; i<p.size (); i++) {
            q[i] = transform ((Point3d) p.elementAt (i));
        }
        Arrays.sort (q);
        zMin = q[0].z;
        zMax = q[q.length-1].z;
        pixZ = (double) (nColors - 1) / (zMax - zMin);
        for (int i=0; i<q.length; i++) {
            int colorMap = q[i].mapIndex;
            g.setColor (colorMaps[colorMap][toColorIndex (q[i].z)]);
            g.fillRect (toCol (q[i].x), toLine (q[i].y), 1, 1);
        }
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

