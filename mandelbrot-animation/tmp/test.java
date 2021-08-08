import java.awt.*;
import java.awt.image.*;
import java.io.*;
import com.sun.image.codec.jpeg.*;
import javax.imageio.*;

public class test {
	double c1_min, c1_max, c2_min, c2_max;
	double xm, ym, height, width;
	double dx, dy;
	double m, w0, zoomfactor;
	int cols, rows, n_max;

	public test () {
		double x1, x2, w1, w2;
		/********************************
		*	This datas you can change	*
		********************************/
		cols = 800;
		rows = 600;
		n_max = 1000;

		//4'194'304 fache Vergrösserung
		w1 = 0.0000008344650268554688f; //  8.344650268554688E-7;
		x1 = -1.7864400827884677f;
		w2 = 3.5f;
		x2 = -0.7f;

		zoomfactor = 0.9872585449f;


		/********************************
		*	Below here do not change	*
		********************************/
		m =  ( x2 - x1 ) / ( w2 - w1 );
		w0 = x1 - m * w1;

		xm = x2;
		ym = 0.0f;
		width = w2;
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
		test t = new test ();

		BufferedImage output = new BufferedImage (t.cols, t.rows, BufferedImage.TYPE_INT_RGB);
		Graphics memGr;

		memGr = output.getGraphics ();


		for ( int c = 0; c < 1200; c++ ) {
			t.width *= t.zoomfactor;
			t.xm = t.f ( t.width );

			t.calcRanges();

			memGr.clearRect (0, 0, t.cols, t.rows);
			double c1, c2;
			for ( int i = 1; i <= t.rows; i++ )
				for ( int j = 1; j <= t.cols; j++ ) {
					c1 = t.c1_min + j*t.dx;
					c2 = t.c2_max - i*t.dy;

					memGr.setColor ( t.getColor ( t.esc ( c1, c2, t.n_max ) ) );

					memGr.drawRect ( j, i, 1, 1 );
				}

			try {
				ImageIO.write(output, "JPG", new File( "c:\\tmp\\pics\\image_" + c + ".jpg" ));
			}
			catch ( Exception m ) {
				System.out.println ( m );
			}
			System.out.println ( c );
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
		if ( escNr >= 1 && escNr <= 125 )
			return new Color (128, 128, 128);
		if ( escNr <= 150 )
			return new Color (192, 192, 192);
		if ( escNr <= 175 )
			return new Color (255, 0, 255);
		if ( escNr <= 200 )
			return new Color (128, 0, 128);
		if ( escNr <= 225 )
			return new Color (128, 0, 0);
		if ( escNr <= 250 )
			return new Color (255, 0, 0);
		if ( escNr <= 300 )
			return new Color (255, 255, 0);
		if ( escNr <= 325 )
			return new Color (0, 255, 0);
		if ( escNr <= 350 )
			return new Color (0, 128, 0);
		if ( escNr <= 375 )
			return new Color (0, 255, 255);
		if ( escNr <= 475 )
			return new Color (0, 128, 128);
		if ( escNr <= 575 )
			return new Color (0, 0, 255);
		if ( escNr <= 725 )
			return new Color (0, 0, 128);
		if ( escNr <= 1000 )
			return new Color (128, 128, 0);

		return new Color (0, 0, 0);
	}
}
