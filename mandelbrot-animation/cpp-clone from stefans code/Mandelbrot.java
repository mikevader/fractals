




public class Mandelbrot{




}





class View{
	double w, h, x, y;
	int it;

	public View(double w, double h, double x, double y, int it){
		this.w = w;
		this.h = h;
		this.x = x;
		this.y = y;
		this.it = it;
	}
}

class Path{
	int nViews;
	View v, cv;

	public Path(int nViews){
		this.nViews = nViews;
	}
}

class Field{
	long cols, rows;
	char f;

	public Field(long cols, long rows, char init){
		this.cols = cols;
		this.rows = rows;
	}
}