package FractalAnimator;
/**
 * Shows a Path of the Views
 *
 * @author Michael Muehlebach
 *
 * @see MandalbrotFractal
 * @see FractalAnimator
 */

import javax.swing.*;
import java.awt.*;

public class PathView extends JPanel{
	List list;

	public PathView(List path){
		list = path;
	}

	public void paint(Graphics g){
		Coordination coord;
		Iterator it;

		setBackground(Color.BLACK);

		while (it.hasMore()){
			coord = (Coordination)it.next();

		}

	}
}
