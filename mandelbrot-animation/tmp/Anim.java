//  -----------  Standard-Applet fuer Animationen  -------

import java.applet.*;
import java.awt.*;

public class Anim extends Applet
                  implements Runnable
{
  // -------------------  Daten  -------------------------

  private int sleepTime = 10; 
  private Thread runner;
   
  
  // ------------------  Methoden  -----------------------

  protected void setPaintPeriod          // Periode zwischen Paint-Aufrufen
                    (int period)         // MilliSek
  { sleepTime = period;
  }


  public void start()
  {  if ( runner == null )
     {  runner = new Thread(this);
        runner.start();
     }
  }


  public void stop()
  {  runner = null;
  }

 
  public void update(Graphics g)
  {  paint(g);
  }
 

  public void run()
  {    
     while (Thread.currentThread() == runner)
     {  
         try
         {  runner.sleep(sleepTime);
         }
         catch (InterruptedException e)
         {  }
         repaint();
     }
  }

}
