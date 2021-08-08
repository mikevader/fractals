import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.text.*;
import javax.imageio.*;


public class MBFracAnim {
	private int nCols, nRows;				//numbers of cols and rows
	private int iterMin, iterMax, iterCur; 	//minimum, maximum and the current number of iterations
	private int nSec;						//numbers of seconds: together with the fps this will get the num of pics
	private int nPics;						//numbers of the Pics

	private ViewPoint start, stop;			//The start and stop point of the movie


	final int fps = 7;						//Frames/Pictures per second


	/*************************************************************************
	* MBFracAnim --
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
	public MBFracAnim ( int cols, int rows, int sec ) {
		nCols = cols;
		nRows = rows;
		nSec = sec;

		nPics = sec * fps;
	}


	/*************************************************************************
	* setStartPoint --
	*
	* Description:
	*	Set the start point for the animation pictures
	*
	* Arguments:
	*	x		x coord
	*	y		y coord
	*	width	width of the view: say something about the zoom
	*	iter	number of iterations for this zoom (the fractale should be good with this number)
	*
	* Result:
	*	void
	*
	*************************************************************************/
	public void setStartPoint ( double x, double y, double width, int iter ) {
		if ( start == null )
			start = new ViewPoint();

		start.x = x;
		start.y = y;
		start.width = width;
		start.iter = iter;
	}

	/*************************************************************************
	* setStopPoint --
	*
	* Description:
	*	Set the stop point for the animation pictures
	*
	* Arguments:
	*	x		x coord
	*	y		y coord
	*	width	width of the view: say something about the zoom
	*	iter	number of iterations for this zoom (the fractale should be good with this number)
	*
	* Result:
	*	void
	*
	*************************************************************************/
	public void setStopPoint ( double x, double y, double width, int iter ) {
		if ( stop == null )
			stop = new ViewPoint();

		stop.x = x;
		stop.y = y;
		stop.width = width;
		stop.iter = iter;
	}

	/*************************************************************************
	* genaratePics --
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
	public void generatePics ( String dest ) {
		double dw, dx, dy, di;
		double cw = 1, cx = 1, cy = 1, ci = 1;
		double fw, fx, fy, fi;

		BufferedImage image;
		Graphics graphics;
		MBFrac frac;
		DecimalFormat df, of;

		//dw = ( stop.width - start.width ) / (nPics -1);	//diff value for width
		//di = (double)( stop.iter - start.iter ) / (nPics -1);	//diff value for iterations
		//dx = ( stop.x - start.x ) / (nPics -1);			//diff value for x position
		//dy = ( stop.y - start.y ) / (nPics -1);			//diff value for y position

		fw = Math.pow ( stop.width/start.width, 1.0f/(nPics -1) );
		fx = Math.pow ( stop.x-start.x, 1.0f/(nPics -1) );
		fy = Math.pow ( stop.y-start.y, 1.0f/(nPics -1) );
		fi = Math.pow ( (double)stop.iter-start.iter, 1.0f/(nPics -1) );


		image = new BufferedImage ( nCols, nRows, BufferedImage.TYPE_INT_RGB );
		graphics = image.getGraphics();
		frac = new MBFrac ( nCols, nRows );
		df = new DecimalFormat ( "00000" );
		of = new DecimalFormat ( "#####" );

		for ( int i = 0; i < nPics; i++ ) {
			//cx = start.x + i*dx;
			//cy = start.y + i*dy;
			//ci = start.iter + i*di;
			//cw = start.width + i*dw;

			System.out.println("cw: " + cw + "   fw: " + fw + "   end: " + (start.width * cw));
			System.out.println("cx: " + cx + "   fx: " + fx + "   end: " + (start.x + cx));
			System.out.println("cy: " + cy + "   fy: " + fy + "   end: " + (start.y + cy));
			System.out.println("ci: " + ci + "   fi: " + fi + "   end: " + (start.iter + ci));

			//frac.paint ( graphics, cx, cy, cw, (int)ci );
			frac.paint ( graphics, -start.x+cx, start.y+cy, start.width*cw, (int)(start.iter+ci) );

			try {
				ImageIO.write ( image, "PNG", new File ( dest, "image_" + df.format ( i ) + ".png" ) );
			}
			catch ( Exception e ) {
				System.out.println ( e );
			}

			System.out.println ( of.format ( i+1 ) + "/" + nPics + "     Iterationen: " + (int)(start.iter+ci) );

			cw *= fw;
			cx *= fx;
			cy *= fy;
			ci *= fi;
		}
	}
}


class ViewPoint {
	double x, y;
	double width;
	int iter;
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
