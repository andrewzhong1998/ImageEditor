package a8;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class ImageAdjuster extends JPanel implements ChangeListener{
	private PictureView picture_view;
	private Picture pic;
	private JPanel picturePanel=new JPanel();
	private JSlider blur;
	private JSlider saturation;
	private JSlider brightness;
	private Picture newPic;
	private int blurDegree=0;
	private int saturationDegree=0;
	private int brightnessDegree=0;
	public ImageAdjuster(Picture pic) {
		this.pic=pic;
		this.newPic=new PictureImpl(pic.getWidth(),pic.getHeight());
		picture_view=new PictureView(pic.createObservable());
		setLayout(new BorderLayout());
		picturePanel.add(picture_view);
		add(picturePanel,BorderLayout.CENTER);
		this.blur=new JSlider(0,5,0);
		blur.setMajorTickSpacing(1);
		blur.setSnapToTicks(true);
		blur.setPreferredSize(new Dimension(550,50));
		this.saturation=new JSlider(-100,100,0);
		saturation.setMajorTickSpacing(25);
		saturation.setPreferredSize(new Dimension(520,50));
		this.brightness=new JSlider(-100,100,0);
		brightness.setMajorTickSpacing(25);
		brightness.setPreferredSize(new Dimension(520,50));
		JSlider[] sliders={blur,saturation,brightness};
		JLabel blurLabel=new JLabel("Blur:	");
		JLabel saturationLabel=new JLabel("Saturation:	");
		JLabel brightnessLabel=new JLabel("Brightness:	");
		JLabel[] lables= {blurLabel,saturationLabel,brightnessLabel};
		JPanel controllPanel=new JPanel();
		controllPanel.setLayout(new GridLayout(3,1));
		JPanel[] elements=new JPanel[3];
		JSlider s;
		JPanel e;
		for(int i=0;i<3;i++) {
			s=sliders[i];
			e=elements[i];
			s.setPaintTicks(true);
			s.setPaintLabels(true);
			e=new JPanel();
			e.add(lables[i]);
			e.add(s);
			controllPanel.add(e);
		}
		add(controllPanel,BorderLayout.SOUTH);
		blur.addChangeListener(this);
		saturation.addChangeListener(this);
		brightness.addChangeListener(this);
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Picture p = A8Helper.readFromURL("https://pmchollywoodlife.files.wordpress.com/2017/08/kendall-jenner-bio.jpg?w=620");
		ImageAdjuster image_adjuster = new ImageAdjuster(p);
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Assignment 8 Image Adjuster");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(image_adjuster, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);

		main_frame.pack();
		main_frame.setVisible(true);
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		//Picture newPic=new PictureImpl(pic.getWidth(),pic.getHeight());
		for(int i=0;i<pic.getWidth();i++) {
			for(int j=0;j<pic.getHeight();j++) {
				newPic.setPixel(i, j, pic.getPixel(i, j));
			}
		}
		saturatePicture(saturation.getValue(), newPic);
		adjustBrightness(brightness.getValue(),newPic);
		blurPicture(blur.getValue(),newPic);
		picturePanel.removeAll();
		picture_view=new PictureView(newPic.createObservable());
		picturePanel.add(picture_view);
		picturePanel.revalidate();
	}
	private void saturatePicture(int value, Picture newPic) {
		// TODO Auto-generated method stub
		//if(saturation.getValue()==saturationDegree)
		//	return;
		//saturationDegree=saturation.getValue();
		for(int i=0;i<pic.getWidth();i++) {
			for(int j=0;j<pic.getHeight();j++) {
				Pixel p=newPic.getPixel(i, j);
				if(value<0) {
					newPic.setPixel(i,j,new ColorPixel(
							p.getRed() * (1.0 + (value / 100.0) ) - (p.getIntensity() * value/ 100.0),
							p.getGreen() * (1.0 + (value / 100.0) ) - (p.getIntensity() * value/ 100.0),
							p.getBlue() * (1.0 + (value / 100.0) ) - (p.getIntensity() * value/ 100.0)
							));
				}
				else if(value>0) {
					if(p.getIntensity()!=0) {
						double a;
						if(p.getRed()>p.getGreen())
							a=p.getRed();
						else a=p.getGreen();
						if(a<p.getBlue())
							a=p.getBlue();
						newPic.setPixel(i,j,new ColorPixel(
								p.getRed() * ((a + ((1.0 - a) * (value / 100.0))) / a),
								p.getGreen() * ((a + ((1.0 - a) * (value / 100.0))) / a),
								p.getBlue() * ((a + ((1.0 - a) * (value / 100.0))) / a)
								));
					}	
				}
				else {
					newPic.setPixel(i, j,newPic.getPixel(i,j));
				}
			}
		}
	}
	private void adjustBrightness(int value, Picture newPic) {
		// TODO Auto-generated method stub
		//if(brightness.getValue()==brightnessDegree)
		//	return;
		//System.out.println(brightnessDegree=brightness.getValue());
		for(int i=0;i<pic.getWidth();i++) {
			for(int j=0;j<pic.getHeight();j++) {
				if(value<0) {
					newPic.setPixel(i, j,newPic.getPixel(i,j).darken(-value/100.0));
				}
				else if(value>0) {
					newPic.setPixel(i, j,newPic.getPixel(i,j).lighten(value/100.0));
				}
				else {
					newPic.setPixel(i, j,newPic.getPixel(i,j));
				}
			}
		}
	}
	private void blurPicture(int value, Picture newPic) {
		// TODO Auto-generated method stub
		//if(blur.getValue()==blurDegree)
		//	return;
		//blurDegree=blur.getValue();
		for(int i=0;i<pic.getWidth();i++) {
			for(int j=0;j<pic.getHeight();j++) {
				ArrayList<Pixel> pixels=new ArrayList<Pixel>();
				for(int x=i-value;x<=i+value;x++) {
					for(int y=j-value;y<=j+value;y++) {
						if(x<0||
							x>=pic.getWidth()||
							y<0||
							y>=pic.getHeight())
							pixels.add(new ColorPixel(0,0,0));
						else pixels.add(newPic.getPixel(x, y));
					}
				}
				double newRed=0;
				double newGreen=0;
				double newBlue=0;
				for(int k=0;k<pixels.size();k++) {
					newRed+=pixels.get(k).getRed();
					newGreen+=pixels.get(k).getGreen();
					newBlue+=pixels.get(k).getBlue();
				}
				newRed/=pixels.size();
				newGreen/=pixels.size();
				newBlue/=pixels.size();
				//System.out.println("old: "+pic.getPixel(i,j).getRed()+" "+pic.getPixel(i,j).getGreen()+" "+pic.getPixel(i,j).getBlue());
				//System.out.println("new: "+newRed+" "+newGreen+" "+newBlue);
				newPic.setPixel(i, j, new ColorPixel(newRed,newGreen,newBlue));
			}
		}
	}
	

}
