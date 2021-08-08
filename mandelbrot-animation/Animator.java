package FractalAnimator;
/**
 * Generates a serie of Fractal pictures and stores it on the hard disc
 * You can set a start and end point serie *
 *
 * @author Michael Muehlebach
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Animator extends JFrame{
	private JTextField x, y, w, h, i;
	public Animator(int cols, int rows){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(cols*2, rows*2);
		setTitle("Fractal-Animator");
		Container cp = getContentPane();

		JPanel set = new JPanel(new GridLayout(5, 2));
		FractalView view = new FractalView(new MandelbrotFractal(254, -2f, 2f, -2f, 2f));

		x = new JTextField();
		y = new JTextField();
		w = new JTextField();
		h = new JTextField();
		i = new JTextField();


		set.add(new JLabel("xm:"));
		set.add(x);
		set.add(new JLabel("ym:"));
		set.add(y);
		set.add(new JLabel("w:"));
		set.add(w);
		set.add(new JLabel("h"));
		set.add(h);
		set.add(new JLabel("i"));
		set.add(i);

		view.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				x.setText(new Integer(e.getX()).toString());
				y.setText(new Integer(e.getY()).toString());
			}
		});
		cp.add(view, BorderLayout.CENTER);
		cp.add(set, BorderLayout.NORTH);
	}
}