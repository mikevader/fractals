/*
 * Lorenz-Attraktor (interaktiv, animiert)
 *
 *    Implementiert ein Applet, das den Verlauf dreier Punkte auf einem
 *    Lorenz-Attraktor darstellt. Die Darstellung kann mit der Maus im
 *    3D-Raum gedreht werden.
 */

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

public class Lorenz
        extends Applet
        implements KeyListener, MouseListener, MouseMotionListener,
            ComponentListener, ActionListener {

    /*
     * LorenzAttraktor --
     *
     *    Dies ist die Implementation des Diff.gl-Systems fuer den Lorenz-
     *    Attraktor. Die Diff.gl sind:
     *
     *        x' = s(y - x)
     *        y' = Rx - y - xz
     *        z' = -Bz + xy
     *
     *    wobei  s = 10, B = 8/3 und R = 28
     */
    private class LorenzAttraktor extends DiffSolver {

        final double s = 10.0;
        final double B = 8.0 / 3.0;
        final double R = 28.0;

        public Point3d df (Point3d x) {
            Point3d q = new Point3d ();
            q.x = s*(x.y - x.x);
            q.y = R*x.x - x.y - x.x*x.z;
            q.z = -B*x.z + x.x*x.y;
            return q;
        }
    }

    /* LorenzThread --
     *
     *    Damit das Berechnen der Punkte und das Drehen durch den Benutzer
     *    gleichzeitig erfolgen koennen, wird ein zusaetzlicher Thread
     *    eingefuehrt, der die Berechnung uebernimmt.
     */
    public class LorenzThread extends Thread {

        private int delay = 10;
        private boolean suspended = false;

        public void run () {
            while (true) {
                nextPoint ();
                try {
                    sleep (delay);
                    if (suspended) {
                        synchronized (this) {
                            while (suspended)
                                wait ();
                        }
                    }
                }  catch (InterruptedException e) { }
                repaint ();
            }
        }
        
        public synchronized void suspendAnim () {
            if (!suspended) {
                suspended = true;
            }
        }
        
        public synchronized void resumeAnim () {
            if (suspended) {
                suspended = false;
                notify ();
            }
        }
    }

    /*
     * Konstanten
     */
    final double xMin    = -40.0;
    final double xMax    =  40.0;
    final double yMin    = -20.0;
    final double yMax    =  40.0;
    final int    nPath   =     3;
    final int    nPoints =  5000;
    final double dx      = 0.003;
    final double phi     = Math.PI / 48.0;

    LorenzAttraktor lAttr;
    LorenzThread    lThread;

    BufferedImage offImage;
    Graphics2D offGraph;
    Graph3d   g3d;
    Vector    points;
    Point3d[] currentPoints;

    int      lastX, lastY;

    public void nextPoint () {
        for (int i=0; i<nPath; i++) {
            if (points.size () == nPath * nPoints) {
                points.removeElementAt (0);
            }
            points.addElement (currentPoints[i]);
            currentPoints[i] = lAttr.next (currentPoints[i], dx);
        }
    }

    /*
     * Ueberlagerte Methoden der Applet-Klasse.
     */
    public void init () {
        setBackground (Color.black);
        setForeground (Color.white);
        
        // Button button = new Button ("Anhalten");
        // add (button);
        // button.addActionListener (this);
        
        Dimension dim = getSize ();
        offImage = (BufferedImage) createImage (dim.width, dim.height);
        offGraph = offImage.createGraphics ();

        addKeyListener (this);
        addMouseListener (this);
        addMouseMotionListener (this);
        addComponentListener (this);

        g3d  = new Graph3d (getWidth (), getHeight ());
        g3d.setDisplayRange (xMin, xMax, yMin, yMax);
        g3d.initColorMap (0, Color.red);
        g3d.initColorMap (1, Color.green);
        g3d.initColorMap (2, Color.blue);
        
        lAttr  = new LorenzAttraktor ();
        currentPoints = new Point3d[nPath];
        
        currentPoints[0] = new Point3d ( 0.0,  5.0,  5.0, 0);
        currentPoints[1] = new Point3d ( 0.0,  4.0,  5.0, 1);
        currentPoints[2] = new Point3d ( 0.0,  3.0,  5.0, 2);
        
        points = new Vector (nPath * nPoints);
    }

    public void start () {
        if (lThread == null) {
            lThread = new LorenzThread ();
            lThread.start ();
        } else {
            lThread.resumeAnim ();
        }
    }

    public void stop () {
        lThread.suspendAnim ();
    }

    public void paint (Graphics g) {
        offGraph.clearRect (0, 0, getWidth (), getHeight ());
        g3d.setPoints (offGraph, points);
        g.drawImage (offImage, 0, 0, null);
    }

    public void update (Graphics g) {
        paint (g);
    }

    /*
     * Methoden des 'KeyListener'-Interfaces.
     */
    public void keyTyped (KeyEvent e) {
        char ch = e.getKeyChar ();
        switch (ch) {
            case 's':
                lThread.suspendAnim ();
                break;
            case 'c':
                lThread.resumeAnim ();
                break;
        }
        e.consume ();
    }
    public void keyPressed (KeyEvent e) { }
    public void keyReleased (KeyEvent e) { }
    
    /*
     * Methoden des 'MouseListener'-Interfaces.
     */
    public void mousePressed (MouseEvent e) {
        lastX = e.getX ();
        lastY = e.getY ();
        e.consume ();
    }
    public void mouseClicked (MouseEvent e) { }
    public void mouseReleased (MouseEvent e) { }
    public void mouseEntered (MouseEvent e) { }
    public void mouseExited (MouseEvent e) { }

    /*
     * Methoden des 'MouseMotionListener'-Interfaces.
     */
    public void mouseDragged (MouseEvent e) {
        int x = e.getX ();
        int y = e.getY ();
 
        if (e.isShiftDown ()) {
            g3d.rotateZ (y - lastY);
        } else if (e.isControlDown ()) {
            g3d.moveDisplayRange (lastX - x, y - lastY);
        } else {
            g3d.rotateX (lastY - y);
            g3d.rotateY (lastX - x);
        }
        lastX = x;
        lastY = y;
        e.consume ();
        repaint ();
    }
    public void mouseMoved (MouseEvent e) { }
    
    /*
     * Methoden des 'ComponentListener'-Interfaces.
     */
    public void componentHidden (ComponentEvent e) { }
    public void componentShown (ComponentEvent e) { }
    public void componentMoved (ComponentEvent e) { }
    /*
     * Bevor diese Methode 'offImage' und 'offGraph' ersetzt, sollte
     * der LorenzThread angehalten werden! Allerdings reicht ein einfacher
     * 'suspend' nicht, denn diese Methode darf erst dann weiterfahren, wenn
     * der Thread sicher mit 'wait()' blockiert wurde.
     */
    public void componentResized (ComponentEvent e) {
        Component c = e.getComponent ();
        Dimension dim = c.getSize ();
        offImage = (BufferedImage) createImage (dim.width, dim.height);
        offGraph = offImage.createGraphics ();
        g3d.setWindowSize (dim.width, dim.height);
        repaint ();
    }
    
    /*
     * Methoden des 'ActionListeners'-Interfaces.
     */
    public void actionPerformed (ActionEvent e) { }
}
