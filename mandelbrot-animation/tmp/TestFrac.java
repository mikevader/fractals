import java.awt.*;
import java.applet.*;


public class TestFrac extends Applet {

	MBFrac f;

	public void init () {
		f = new MBFrac ( 480, 361 );
	}

	public void paint ( Graphics g ) {
		f.paint ( g, -0.5, 0.0, 3.5, 40 );
	}
}