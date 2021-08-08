import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.text.*;
import com.sun.image.codec.jpeg.*;
import javax.imageio.*;



public class Test2 {
	double c1_min, c1_max, c2_min, c2_max;
	double xm, ym, height, width;
	double dx, dy, di;
	double m, w0, zoomfactor;
	int cols, rows, anzPics, iter_min, iter_max, n_max;

	Color[] colortable;

	public Test2 () {
		double x1, x2, w1, w2;
		/********************************
		*	This datas you can change	*
		********************************/
		cols = 480;
		rows = 361;

		anzPics = 900;

		//4'194'304 fache Vergrösserung
		w1 = 3.5f;
		x1 = -0.7f;
		iter_min = 50;
		w2 = 0.0000008344650268554688f; //  8.344650268554688E-7;
		x2 = -1.7864400827884677f;
		iter_max = 1500;

		zoomfactor = 0.974679434; //0.9872585449f;


		/********************************
		*	Below here do not change	*
		********************************/
		colortable = new Color[256];

		for ( int i = 0; i < 256; i++ )
			colortable[i] = new Color ( i, i, i );

		m =  ( x2 - x1 ) / ( w2 - w1 );
		w0 = x1 - m * w1;

		di =  ( iter_max - iter_min ) / anzPics;

		xm = x1;
		ym = 0.0f;
		width = w1;
	}

	private double f ( double w ) {
		return w * m + w0;
	}

	private void calcRanges () {
		height = width * (double)rows / cols;
		c1_min = xm - 0.5 * width;
		c1_max = xm + 0.5 * width;
		c2_min = ym - 0.5 * height;
		c2_max = ym + 0.5 * height;

		dx = ( c1_max - c1_min ) / ( cols - 1 );
		dy = ( c2_max - c2_min ) / ( rows - 1 );
	}

	public static void main ( String[] args ) {
		Test2 t = new Test2 ();

		BufferedImage output = new BufferedImage (t.cols, t.rows, BufferedImage.TYPE_INT_RGB);
		Graphics memGr;
		DecimalFormat df = new DecimalFormat("00000");
		DecimalFormat of = new DecimalFormat("#####");

		memGr = output.getGraphics ();


		for ( int c = 0; c < t.anzPics; c++ ) {
			t.width *= t.zoomfactor;
			t.xm = t.f ( t.width );

			t.calcRanges();

			memGr.clearRect (0, 0, t.cols, t.rows);
			double c1, c2;
			t.n_max = t.iter_min + (int)(c * t.di);
			for ( int i = 1; i <= t.rows; i++ )
				for ( int j = 1; j <= t.cols; j++ ) {
					c1 = t.c1_min + j*t.dx;
					c2 = t.c2_max - i*t.dy;

					memGr.setColor ( t.getColor ( t.esc ( c1, c2, t.n_max ) ) );

					memGr.drawRect ( j, i, 1, 1 );
				}

			try {
				ImageIO.write(output, "PNG", new File( "c:\\tmp\\pics2\\image_" + df.format(c) + ".png" ));
			}
			catch ( Exception m ) {
				System.out.println ( m );
			}

			System.out.println ( of.format(c+1) + "/" + t.anzPics + "   Iterationen: " + t.n_max );
		}
	}

	private int esc ( double c1, double c2, int n_max ) {
		int i;
		double x = c1, y = c2;
		double xd, yd;

		for ( i = 1; i <= n_max && (x*x+y*y) <= 4; i++ ) {
			xd = x;
			yd = y;

			x = xd*xd - yd*yd + c1;
			y = 2 * xd * yd + c2;
		}

		return i;
	}

	Color getColor ( int escNr ) {
		return colortable [ (int)((double)escNr / (n_max+1) * 255) ];
	}
}


// Example for a methode description

	/*************************************************************************
	* Methode name --
	*
	* Description:
	*	Desc.
	*
	* Arguments:
	*	Arg1		Desc.
	*
	* Result:
	*	Desc.
	*
	*************************************************************************/
