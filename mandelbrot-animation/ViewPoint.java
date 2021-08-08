package FractalAnimator;
/**
 * A Fractal View
 *
 * @author Michael Muehlebach
 *
 * @see MandalbrotFractal
 * @see FractalAnimator
 */
public class ViewPoint {
	double xm, ym;
	double width, height;
	int    iter;

	public ViewPoint(double x, double y, double w, double h, int i){
		xm = x;
		ym = y;
		width = w;
		height = h;
		iter = i;
	}

	public String toString(){
		return "Mittelpunkt: (" + xm + ", " + ym + "); Frame: (" + width + ", " + height + "); Iterationen: " + iter;
	}
}
