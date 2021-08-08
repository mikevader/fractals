public class TestFractalAnim {

	public static void main ( String[] args ) {
		MandelFractalAnim fa = new MandelFractalAnim ( 320, 241, 10 );

		fa.setStartView ( 1.0, 0.0, 3.5, 256 );
		fa.setStopView  ( -0.7395903240555, 0.1574135095493, 0.00000000004366, 1000 );

		fa.generatePics ( "C:\\Ia02mueh\\Projekte\\FractalAnimator\\tmp\\pics" );
	}
}