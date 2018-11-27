package a8;


public class GrayPixel implements Pixel {

	private double intensity;

	private static final char[] PIXEL_CHAR_MAP = {'#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' '};


	public GrayPixel(double intensity) {
		if (intensity < 0.0 || intensity > 1.0) {
			throw new IllegalArgumentException("Intensity of gray pixel is out of bounds.");
		}
		this.intensity = intensity;
	}

	@Override
	public double getRed() {
		return getIntensity();
	}

	@Override
	public double getBlue() {
		return getIntensity();
	}

	@Override
	public double getGreen() {
		return getIntensity();
	}

	@Override
	public double getIntensity() {
		return intensity;
	}

	@Override
	public char getChar() {
		return PIXEL_CHAR_MAP[(int) (getIntensity()*10.0)];
	}

	@Override
	public Pixel lighten(double factor) {
		// TODO Auto-generated method stub
		if(factor<0||factor>1)
			throw new RuntimeException("Invalid parameter");
		double new_Intensity=getIntensity()+factor*(1.0-getIntensity());
		Pixel pixel=new GrayPixel(new_Intensity);
		return pixel;
	}
	@Override
	public Pixel darken(double factor) {
		// TODO Auto-generated method stub
		if(factor<0||factor>1)
			throw new RuntimeException("Invalid parameter");
		double new_Intensity=getIntensity()-factor*getIntensity();
		Pixel pixel=new GrayPixel(new_Intensity);
		return pixel;
	}
	
}
