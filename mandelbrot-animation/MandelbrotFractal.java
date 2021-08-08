package FractalAnimator;
/**
 * The Mandelbrot fractal
 *
 * @author Michael Muehlebach
 */

public class MandelbrotFractal implements Fractal {
	private int iterations;
	private double x_min, x_max, y_min, y_max;

	/**
	 *
	 */
	public MandelbrotFractal ( int iterations, double x_min, double x_max, double y_min, double y_max ) {
		setIterations ( iterations );
		setView ( x_min, x_max, y_min, y_max );
	}

	/**
	 * Set the iterations ( normaly only used by the constructor
	 *
	 * @param iterations the number of iterations of this fractal
	 */
	public void setIterations ( int iterations ) {
		this.iterations = iterations;
	}

	/**
	 * Set the View Windows
	 *
	 * @param iterations the number of iterations of this fractal
	 * @param x_min the left border of the frame
	 * @param x_max the right border
	 * @param y_min the bottom border
	 * @param y_may the top border
	 */
	public void setView ( double x_min, double x_max, double y_min, double y_max ) {
		this.x_min = x_min;
		this.x_max = x_max;
		this.y_min = y_min;
		this.y_max = y_max;
	}

	/**
	 * Calculates the fractal by the preset frame and iterations and for the given matrix size
	 *
	 * @param w The width of the point matrix
	 * @param h the height of the point matrix
	 *
	 * @return a 2 dimesion Array with the escape numbers
	 */
	public FractalMatrix toMatrix(int w, int h){
		double dx, dy;

		FractalMatrix ret = new FractalMatrix(w, h);

		dx = ( x_max - x_min ) / ( w - 1 );
		dy = ( y_max - y_min ) / ( h - 1 );

		for ( int x = 1; x <= w; x++ )
			for ( int y = 1; y <= h; y++ )
				ret.set(x-1, y-1, escapeNumber(x_min + x*dx, y_max - y*dy));

		return ret;
	}

	public FractalMatrix toMatrix(){
		return null;
	}

	/**
	 * Calculate the iterations and return the number of succeeded iterations
	 *
	 * @param cx the x coord of the start point
	 * @param cy the y coord of the start point
	 *
	 * @return escape number
	 */
	private int escapeNumber ( double cx, double cy ) {
		double x = cx, y = cy;
		double xd = x*x, yd = y*y;
		int i;

		for ( i = 1; i <= iterations && ( xd + yd ) <= 4; i++ ) {
			y = 2 * x * y + cy;
			x = xd - yd + cx;

			xd = x*x;
			yd = y*y;
		}
		return i;
	}
}