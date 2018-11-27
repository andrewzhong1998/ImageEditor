package a8;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.*;
public class FramePuzzle extends JPanel implements MouseListener,KeyListener{
	private PictureView[][] pictureViews=new PictureView[5][5];
	private PictureView pictureView;
	private Picture pic;
	private Picture pictureOnScreen;
	private int solidX;
	private int solidY;
	private int dw;
	private int dh;
	public FramePuzzle(Picture p) {
		this.pic=p;
		dw=pic.getWidth()/5;
		dh=pic.getHeight()/5;
		pictureOnScreen=new PictureImpl(5*dw,5*dh);
		for(int j=0;j<5;j++) {
			for(int i=0;i<5;i++) {
				//System.out.println("hi");
				pictureViews[j][i]=new PictureView(pic.extract(i*dw, j*dh, dw, dh).createObservable());
			}
		}
		Picture solid=new PictureImpl(dw,dh);
		for(int i=0;i<dw;i++) {
			for(int j=0;j<dh;j++) {
				solid.setPixel(new Coordinate(i, j),new GrayPixel(1));
			}
		}
		pictureViews[4][4]=new PictureView(solid.createObservable());
		solidX=4;
		solidY=4;
		shuffle();
		updatePicture();
		add(pictureView);
		addKeyListener(this);
		setFocusable(true);
		//pictureView.addMouseListener(this);
		//setLayout(new GridLayout(5,5));
		//for(int i=0;i<5;i++) {
		//	for(int j=0;j<5;j++) {
		//		add(pictureViews[i][j]);
		//	}
		//}
		 
		//for(int j=0;j<5;j++) {
		//	for(int i=0;i<5;i++) {
		//		//System.out.println("hi");
		//		pictureViews[j][i].addMouseListener(this);
		//	}
		//}
		//System.out.println(solidX+"		"+solidY);
	}
	private void updatePicture() {
		// TODO Auto-generated method stub
		for(int i=0;i<pictureOnScreen.getWidth();i++) {
			for(int j=0;j<pictureOnScreen.getHeight();j++) {
				int curX=i/dw;
				int curY=j/dh;
				pictureOnScreen.setPixel(i, j, pictureViews[curY][curX].getPicture().getPixel(i%dw, j%dh));
			}
		}
		pictureView=new PictureView(pictureOnScreen.createObservable());
		pictureView.addMouseListener(this);
		//pictureView.addKeyListener(this);
		this.removeAll();
		this.add(pictureView);
		this.revalidate();
	}
	private void shuffle() {
		// TODO Auto-generated method stub
		for(int i=0;i<50;i++) {
			int x1=(int)(5*Math.random());
			int y1=(int)(5*Math.random());
			int x2=(int)(5*Math.random());
			int y2=(int)(5*Math.random());
			if(x1==solidX&&y1==solidY) {
				//System.out.println(solidX+"	"+solidY);
				solidX=x2;
				solidY=y2;
				//System.out.println(solidX+"	"+solidY);
			}
			else if(x2==solidX&&y2==solidY) {
				//System.out.println(solidX+"	"+solidY);
				solidX=x1;
				solidY=y1;
				//System.out.println(solidX+"	"+solidY);
			}
			PictureView t=pictureViews[y1][x1];
			pictureViews[y1][x1]=pictureViews[y2][x2];
			pictureViews[y2][x2]=t;
		}
	}
	private void exchange(int x1,int y1,int x2,int y2) {
		PictureView t=pictureViews[y1][x1];
		pictureViews[y1][x1]=pictureViews[y2][x2];
		pictureViews[y2][x2]=t;
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Picture p = A8Helper.readFromURL("https://pmchollywoodlife.files.wordpress.com/2017/08/kendall-jenner-bio.jpg?w=620");
		FramePuzzle frame_puzzle = new FramePuzzle(p);
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Assignment 8 Frame Puzzle");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(frame_puzzle, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);

		main_frame.pack();
		main_frame.setVisible(true);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x=e.getX()/dw;
		int y=e.getY()/dh;
		//System.out.println(x+" "+y);
		//System.out.println(dw+"	"+dh);
		//System.out.println(e.getX()+"	"+e.getY());
		if(x==solidX) {
			
			if(y<solidY) {
				int diff=solidY-y;
				for(int i=0;i<diff;i++) {
					exchange(solidX,solidY,solidX,--solidY);
					
				}
			}
			else if(y>solidY) {
				int diff=y-solidY;
				for(int i=0;i<diff;i++) {
					exchange(solidX,solidY,solidX,++solidY);
					
				}
			}
		}
		else if(y==solidY) {
			if(x<solidX) {
				int diff=solidX-x;
				for(int i=0;i<diff;i++) {
					exchange(solidX,solidY,--solidX,solidY);
					
				}
			}
			else if(x>solidX) {
				int diff=x-solidX;
				for(int i=0;i<diff;i++) {
					exchange(solidX,solidY,++solidX,solidY);
					
				}
			}
		}
		//System.out.println(x+" "+y);
		updatePicture();
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
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
            //Right arrow key code
			if(solidX==4)
				return;
			exchange(solidX,solidY,++solidX,solidY);
			updatePicture();
			
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
            //Left arrow key code
			if(solidX==0)
				return;
			exchange(solidX,solidY,--solidX,solidY);
			updatePicture();
		} 
		else if (e.getKeyCode() == KeyEvent.VK_UP ) {
            //Up arrow key code
			if(solidY==0)
				return;
			exchange(solidX,solidY,solidX,--solidY);
			updatePicture();
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {
            //Down arrow key code
			if(solidY==4)
				return;
			exchange(solidX,solidY,solidX,++solidY);
			updatePicture();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
