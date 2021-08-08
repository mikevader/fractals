import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;

import com.sun.image.codec.jpeg.*;

import javax.imageio.*;

public class Mandelbrot extends Applet implements MouseListener, KeyListener {

	double c1_min, c1_max, c2_min, c2_max;
	double xm, ym, height, width;
	double dx, dy;
	double m, w0;
	int cols, rows, n_max;
	Color colortable;

	//int zoom1x, zoom1y, zoom2x, zoom2y;
	Dimension dim;
	BufferedImage output;



	public void init () {
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


		/********************************
		*	Below here do not change	*
		********************************/
		m =  ( x2 - x1 ) / ( w2 - w1 );
		w0 = x1 - m * w1;


		//xm = -0.7f;
		//ym = 0f;
		//width = 3.5f;

		xm = x2;
		ym = 0.0f;
		width = w2;

		calcRanges ();

		dim = getSize ();
		output = new BufferedImage (cols, rows, BufferedImage.TYPE_INT_RGB);

		addMouseListener ( this );
		addKeyListener ( this );
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


	public void pp () {
		Graphics memGr;

		memGr = output.getGraphics ();

		memGr.clearRect (0, 0, dim.width, dim.height);

		double c1, c2;
		for ( int i = 1; i <= rows; i++ )
			for ( int j = 1; j <= cols; j++ ) {
				c1 = c1_min + j*dx;
				c2 = c2_max - i*dy;

				memGr.setColor ( getColor ( esc ( c1, c2, n_max ) ) );

				memGr.drawRect ( j, i, 1, 1 );
			}


		File outputFile = new File( "image.png" );

		try {
    		ImageIO.write(output, "PNG", outputFile);
		}
		catch ( Exception m ) {
			System.out.println ( m );
		}
	}





	public void paint ( Graphics g ) {
		pp();
		g.drawImage ( output, 0, 0, null);
		requestFocus();
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

	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {

		// Koord Info
		if ( e.getButton() == MouseEvent.BUTTON2 ) {
			System.out.println ( "xm: " + xm );
			System.out.println ( "ym: " + ym );
			System.out.println ( "width: " + width );
			return;
		}


		// Zoom In
		if ( e.getButton() == MouseEvent.BUTTON1 ) {
			xm = c1_min + e.getX() / (double)cols * width;
			ym = c2_max - e.getY() / (double)rows * height;
			width = width / 2;
		}
		// Zoom Out
		else if ( e.getButton() == MouseEvent.BUTTON3 ) {
			width = 2 * width;
		}

		calcRanges ();
		//System.out.println ( "XM: " + xm + " YM: " + ym );
		//System.out.println ( "width: " + width + " height: " + height );

		repaint ();
	}

	public void keyPressed ( KeyEvent e ) {}

	public void keyReleased ( KeyEvent e ) {}

	public void keyTyped ( KeyEvent e ) {
		char inputChar = e.getKeyChar ();

		switch ( inputChar ) {
			case 'i':
				width /= 2;
				xm = f ( width );
				break;

			case 'o':
				width *= 2;
				xm = f ( width );
				break;

			default:
		}
		calcRanges ();
		repaint ();
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

		/*
		if ( escNr <= n_max )
			return new Color ( 128, 128, 128 );
		else
			return new Color ( 0, 0, 0 );
		*/
	}
}
