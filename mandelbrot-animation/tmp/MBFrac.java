import java.awt.*;


public class MBFrac {
	private int nCols, nRows;				//numbers of cols and rows
	private int nIter; 						//number of iterations

	private double height, width;			//height and width of the current view
	private double xm, ym;					//coords of the current position (middle of the view)
	private double dx, dy;
	private double x_min, x_max, y_min, y_max;	//range of the view

	private Color[] colorTable;				//Array for the Colors

	final int colors = 256;					//number of colors for the colortable


	/*************************************************************************
	* MBFrac --
	*
	* Description:
	*	Constructor, set the important values and generates the colortable
	*
	* Arguments:
	*	cols	num of points in the width
	*	rows	same as cols for the height
	*
	* Result:
	*	void
	*
	*************************************************************************/
	public MBFrac ( int cols, int rows ) {
		nCols = cols;
		nRows = rows;

		//generation of the color array:
		colorTable = new Color[colors];

		for ( int i = 0; i < colors; i++ )
			colorTable[i] = new Color ( i, i, i );
	}


	/*************************************************************************
	* Methode paint --
	*
	* Description:
	*	Generate a Fractal and draws it with the Graphics Object
	*
	* Arguments:
	*	g		Graphics Object for draw the fractal
	*	xm		x coord of the middle
	*	ym		y coord of the middle
	*	width	width of the image (the real width... say something about the zoom)
	*
	* Result:
	*	void
	*
	*************************************************************************/
	public void paint ( Graphics g, double xm, double ym, double width, int iter ) {

		nIter = iter;
		this.xm = xm;
		this.ym = ym;
		this.width = width;

		//Width and Height must have the same realation as Cols and Rows
		height = width * (double)nRows / nCols;

		calcRanges();


		for ( int y = 1; y <= nRows; y++ )
			for ( int x = 1; x <= nCols; x++ ) {
				g.setColor ( getColor ( escapeNumber ( x_min + x * dx, y_max - y * dy ) ) );
				g.drawRect ( x, y, 1, 1 );
			}
	}


	/*************************************************************************
	* Methode calcRanges --
	*
	* Description:
	*	Calculate all the values which are depended from xm, ym and width
	*
	* Arguments:
	*
	* Result:
	*	void
	*
	*************************************************************************/
	private void calcRanges () {
		height = width * (double)nRows / nCols;
		x_min = xm - 0.5 * width;
		x_max = xm + 0.5 * width;
		y_min = ym - 0.5 * height;
		y_max = ym + 0.5 * height;

		dx = ( x_max - x_min ) / ( nCols - 1 );
		dy = ( y_max - y_min ) / ( nRows - 1 );
	}


	/*************************************************************************
	* Methode escapeNumber --
	*
	* Description:
	*	Calculate the iterations and return the number of succeeded iterations
	*
	* Arguments:
	*	cx		the x coord of the start point
	*	cy		the y coord of the start point
	*
	* Result:
	*	escape number
	*
	*************************************************************************/
	private int escapeNumber ( double cx, double cy ) {
		int i;
		double x = cx, y = cy;
		double xd = x*x, yd = y*y;

		for ( i = 1; i <= nIter && ( xd + yd ) <= 4; i++ ) {
			y = 2 * x * y + cy;
			x = xd - yd + cx;

			xd = x*x;
			yd = y*y;
		}
		return i;
	}


	/*************************************************************************
	* Methode getColor --
	*
	* Description:
	*	Get the Color for the depended escape number
	*
	* Arguments:
	*	escNr	The escape number
	*
	* Result:
	*	a color Object
	*
	*************************************************************************/
	private Color getColor ( int escNr ) {
		return colorTable [ (int)((double)escNr / (nIter+1) * (colors-1)) ];
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
