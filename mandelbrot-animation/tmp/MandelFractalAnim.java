/*************************************************************************
* MandelFractalAnim --
*
* Description:
*	Generates a serie of Fractal pictures and stores it on the hard disc
*	You can set a start and end point serie
*
*************************************************************************/


import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.text.*;
import javax.imageio.*;


public class MandelFractalAnim {
	private int nCols, nRows;				//numbers of cols and rows
	private int iterMin, iterMax, iterCur; 	//minimum, maximum and the current number of iterations
	private int nPics;						//numbers of the Pics

	private ViewPoint start, stop;			//The start and stop point of the movie

	final int fps = 15;						//Frames/Pictures per second


	/*************************************************************************
	* MandelFractalFracAnim --
	*
	* Description:
	*	Constructor, set the important values
	*
	* Arguments:
	*	cols	num of points in the width
	*	rows	same as cols for the height
	*	sec		num of seconds of the animation. with fps get the num of pics ( sec * fps = pics )
	*
	* Result:
	*	void
	*
	*************************************************************************/

	public MandelFractalAnim ( int cols, int rows, int sec ) {
		nCols = cols;
		nRows = rows;
		nPics = sec * fps;
	}

	/*************************************************************************
	* Methode setStartView --
	*
	* Description:
	*	Sets the start view for the animation pictures.
	*
	* Arguments:
	*	mx		x coord of the middle point
	*	my		y coord of the middle point
	*	width	width of the view
	*	iter	number of iterations for this zoom (the fractale should be
	*           good with this number)
	*
	*************************************************************************/

	public void setStartView (
			double mx, double my,
			double width, int iter ) {

		start = new ViewPoint();

		start.mx     = mx;
		start.my     = my;
		start.width  = width;
		start.iter   = iter;
	}

	/*************************************************************************
	* Methode setStopView --
	*
	* Description:
	*	Set the stop view for the animation pictures
	*
	* Arguments:
	*	x		x coord of the middle point
	*	y		y coord of the middle point
	*	width	width of the view
	*	iter	number of iterations for this zoom (the fractale should be
	*           good with this number)
	*
	*************************************************************************/

	public void setStopView (
			double mx, double my,
			double width, int iter ) {

		stop = new ViewPoint();

		stop.mx     = mx;
		stop.my     = my;
		stop.width  = width;
		stop.iter   = iter;
	}

	/*************************************************************************
	* Methode genaratePics --
	*
	* Description:
	*	Generate the Pics for the animation
	*
	* Arguments:
	*	dest	Destination Directory (in this version: the directory must exist)
	*
	* Result:
	*	void
	*
	*************************************************************************/

	public double geom (double f, double n, double i) {
		return (1.0 - Math.pow (f, i)) / (1.0 - Math.pow (f, n));
	}

	public void generatePics (String dest) {

		double f, g;
		double dx, dy, di;
		double cx, cy, cw, ci;

		BufferedImage image;
		Graphics graphics;
		MandelFractal frac;
		DecimalFormat df, of;

		f  = Math.pow (stop.width/start.width, 1.0/(double) (nPics -1));

		dx = stop.mx - start.mx;
		dy = stop.my - start.my;
		di = (double) (stop.iter - start.iter) / (double) (nPics -1);

		image = new BufferedImage (nCols, nRows, BufferedImage.TYPE_INT_RGB);
		graphics = image.getGraphics();
		frac = new MandelFractal (nCols, nRows);
		df = new DecimalFormat ("00000");
		of = new DecimalFormat ("#####");


		for ( int i = 0; i < nPics; i++ ) {

			g  = geom (f, nPics, i);
			cx = start.mx + dx * g;
			cy = start.my + dy * g;
			cw = start.width * Math.pow (f, i);
			ci = start.iter + i * di;

			frac.paint ( graphics, cx, cy, cw, (int) ci );

			System.out.println ("Bild " + of.format (i) + "/" + nPics + ". Anz. Iterationen: " + (int) ci);

			try {
				ImageIO.write ( image, "PNG", new File ( dest, "image_" + df.format ( i +1 ) + ".png" ) );
			} catch ( Exception e ) {
				System.out.println ( e );
			}
		}
	}
}


class ViewPoint {
	double mx, my;
	double width;
	int    iter;
}
