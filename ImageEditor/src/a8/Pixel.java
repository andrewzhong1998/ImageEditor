package a8;

public interface Pixel {

	public double getRed();
	public double getBlue();
	public double getGreen();
	public double getIntensity();
	public char getChar();	
	public Pixel lighten(double factor);
	public Pixel darken(double factor);
}
