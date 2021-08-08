package FractalAnimator;
/**
 * A Fractal in a 2d Matrix
 *
 * @author Michael Muehlebach
 *
 * @see MandalbrotFractal
 * @see FractalAnimator
 */
public class FractalMatrix{
	int[][] matrix;
	int rows, cols;

	public FractalMatrix(int cols, int rows){
		matrix = new int[cols][rows];
		this.rows = rows;
		this.cols = cols;
	}

	public void set(int col, int row, int value){
		matrix[col][row] = value;
	}

	public int get(int col, int row){
		return matrix[col][row];
	}
}
