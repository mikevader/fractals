// --------------  Graphik mit mathematischen Koordinaten  -------------  
import java.awt.*;

public class Graph2d
{
    public Graph2d(int cols, int lines)              // Konstruktor mit Angabe
    {  width = cols;                                 // der Window-Groesse
       height = lines;
    }


    public void setDisplayRange                      // Koordinaten-Bereich festlegen
                    (double xMin, double xMax,
                     double yMin, double yMax)
    {  this.xMin = xMin;
       this.xMax = xMax;
       this.yMin = yMin;
       this.yMax = yMax;
       cx = (double) width / (xMax - xMin);       
       cy = (double) height / (yMax - yMin);
    }


    public void setPoint(Graphics g,                 // Punkt zeichnen 
                         double x, double y)
    {  int pCol = pixCol(x);
       int pLine = pixLine(y);
       g.fillRect(pCol, pLine, 1, 1);
    }


    public void setLine(Graphics g,                  // Strecke zeichnen
                        double x1, double y1,
                        double x2, double y2)
    {  g.drawLine(pixCol(x1), pixLine(y1),
                  pixCol(x2), pixLine(y2));
    }


    public int pixCol(double x)                       // Pixel-Kolonne eines Punktes
    {  return (int) Math.round(cx * (x-xMin));
    }


    public int pixLine(double y)                      // Pixel-Zeile eines Punktes
    {  return (int) Math.round(cy * (yMax-y));
    }

  
    // ------------  Daten-Komponenten  ------------------

    private int width, height;                        // Window-Groesse
    private double xMin, xMax;                        // Koordinaten-Bereich
    private double yMin, yMax;
    private double cx, cy;                            // Umrechnungs-Faktoren

}
