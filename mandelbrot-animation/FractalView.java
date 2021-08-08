package FractalAnimator;
/**
 * Shows a Fractal
 *
 * @author Michael Muehlebach
 *
 * @see MandalbrotFractal
 * @see FractalAnimator
 */

import javax.swing.*;
import java.awt.*;

public class FractalView extends JPanel{
	private FractalMatrix fm;
	private Fractal fr;

	public FractalView(Fractal frac){
		setBackground(Color.BLACK);
		fr = frac;
	}

	public void showFractal(Fractal frac){
		fr = frac;
		repaint();
	}

	public void paint(Graphics g){
		fm = fr.toMatrix(getWidth(), getHeight());

		System.out.println(getWidth() + " " + getHeight());
		for (int x = 0; x < fm.cols; x++){
			for (int y = 0; y < fm.rows; y++){
				int c = fm.get(x, y);
				g.setColor( new Color(c, c, c));
				g.drawRect(x, y, 1, 1);
			}
		}
	}
}
