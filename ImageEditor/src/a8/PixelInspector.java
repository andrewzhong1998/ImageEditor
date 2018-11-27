package a8;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.*;
public class PixelInspector extends JPanel implements MouseListener{
	private PictureView picture_view;
	private Picture pic;
	private JLabel xCoordinate;
	private JLabel yCoordinate;
	private JLabel redValue;
	private JLabel greenValue;
	private JLabel blueValue;
	private JLabel brightnessValue;
	public PixelInspector(Picture pic) {
		setLayout(new BorderLayout());
		this.pic=pic;
		picture_view=new PictureView(pic.createObservable());
		picture_view.addMouseListener(this);
		add(picture_view, BorderLayout.CENTER);
		xCoordinate=new JLabel("	x: 0	");
		yCoordinate=new JLabel("	y: 0	");
		redValue=new JLabel("	Red: 0.00	");
		greenValue=new JLabel("	Green: 0.00	");
		blueValue=new JLabel("	Blue: 0.00	");
		brightnessValue=new JLabel("	Brightness: 0.00	");
		JPanel statistics=new JPanel();
		statistics.setLayout(new GridLayout(6,1));
		statistics.add(xCoordinate);
		statistics.add(yCoordinate);
		statistics.add(redValue);
		statistics.add(greenValue);
		statistics.add(blueValue);
		statistics.add(brightnessValue);
		add(statistics,BorderLayout.WEST);
		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x=e.getX();
		int y=e.getY();
		Pixel p=pic.getPixel(x, y);
		xCoordinate.setText("	x: "+x+"	");
		yCoordinate.setText("	y: "+y+"	");
		redValue.setText("	Red: "+((int)(p.getRed()*100+0.5))/100.00+"	");
		greenValue.setText("	Green: "+((int)(p.getGreen()*100+0.5))/100.00+"	");
		blueValue.setText("	Blue: "+((int)(p.getBlue()*100+0.5))/100.00+"	");
		brightnessValue.setText("	Brightness: "+((int)(p.getIntensity()*100+0.5))/100.00+"	");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Picture p = A8Helper.readFromURL("https://pmchollywoodlife.files.wordpress.com/2017/08/kendall-jenner-bio.jpg?w=620");
		PixelInspector pixel_inspector = new PixelInspector(p);
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Assignment 8 Pixel Inspector");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(pixel_inspector, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);

		main_frame.pack();
		main_frame.setVisible(true);
	}
}
