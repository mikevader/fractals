public class TestFracAnim {
	public static void main ( String[] args ) {
		MBFracAnim fa = new MBFracAnim ( 480, 361, 5 );

		fa.setStartPoint ( 0.7, 0.0, 3.5, 256 );
		fa.setStopPoint ( 0.7395903240555, 0.1574135095493, 0.00000000004366, 1500 );

		fa.generatePics ( "c:\\tmp\\pics3\\" );
	}
}