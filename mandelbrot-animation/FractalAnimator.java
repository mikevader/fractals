package FractalAnimator;
/**
 * Generates a serie of Fractal pictures and stores it on the hard disc
 * You can set a start and end point serie *
 *
 * @author Michael Muehlebach
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import java.text.*;
import javax.imageio.*;
import java.util.*;


public class FractalAnimator implements ActionListener{
	private static int nCols, nRows;
	private ViewPoint start, stop;
	private java.util.List path;
	private static JFrame mainWindow;
	private int nPics;


	public static void main(String[] args){
		mainWindow = new Animator(200, 150);
		mainWindow.show();
	}

	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();
	}

	/**
	 * Sets the start view for the animation pictures.
	 *
	 * @param mx x coord of the middle point
	 * @param my y coord of the middle point
	 * @param width width of the view
	 * @param iter number of iterations for this zoom (the fractale should be good with this number)
	 */
	public void setStartView ( double xm, double ym, double width, int iter ) {
	}


	/**
	 * Set the stop view for the animation pictures
	 *
	 * @param x		x coord of the middle point
	 * @param y		y coord of the middle point
	 * @param width	width of the view
	 * @param iter	number of iterations for this zoom (the fractale should be good with this number)
	 */
	public void setStopView ( double xm, double ym, double width, int iter ) {
	}


	/**
	 * Generate the Pics for the animation
	 *
	 * @param frac Fractal
	 * @param dest Destination Directory (in this version: the directory must exist)
	 */
	public void generatePics ( Fractal frac, String dest ) {
	}

	//public nextPic
}