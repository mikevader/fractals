package FractalAnimator;

/**
 * A Fractal
 *
 * @author Michael Muehlebach
 *
 * @see MandalbrotFractal
 * @see FractalAnimator
 */

interface Fractal {

	/**
	 * Set the iterations ( normaly only used by the constructor
	 *
	 * @param iterations the number of iterations of this fractal
	 */
	void setIterations ( int iterations );

	/**
	 * Set the View Windows
	 *
	 * @param iterations the number of iterations of this fractal
	 * @param x_min the left border of the frame
	 * @param x_max the right border
	 * @param y_min the bottom border
	 * @param y_may the top border
	 */
	void setView ( double x_min, double x_max, double y_min, double y_max );

	/**
	 * Calculates the fractal by the preset frame and iterations and for the given matrix size
	 *
	 * @param w The width of the point matrix
	 * @param h the height of the point matrix
	 *
	 * @return a 2 dimesion Array with the escape numbers
	 */
	FractalMatrix toMatrix(int w, int h);
	FractalMatrix toMatrix();
}