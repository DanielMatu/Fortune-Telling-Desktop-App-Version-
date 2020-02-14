import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Start extends JFrame implements MouseListener, MouseMotionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image bg, circ, dbImage, TOF, Begin, l1, layout1, imageExit;
	private Graphics2D g2d, dbg;
	private int circw,bgx = 0;
	private double i=0.01;
	private float opac = 0.1f;
	private boolean layoutOneExists = false;
	private double firstclick[] ={0,0}, secondclick[] = {0,0};
	private LayoutOne layoutOne;
	int state = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
		double width = screenSize.getWidth ();
		double height = screenSize.getHeight ();
		DisplayMode dm = new DisplayMode ((int) height, (int) width, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
		Start start = new Start ();
		start.run (dm);
	}
	
    public void run (DisplayMode dm)
    {
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	Screen s = new Screen ();
    	s.setFullScreen (dm, this);
    	loadpics();
    }
	
	public void paint(Graphics g){
		dbImage = createImage(getWidth(), getHeight());
		dbg = (Graphics2D) dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
		repaint();
	}

	public void paintComponent(Graphics g){
		g2d = (Graphics2D) g;
		if (state == 0){
			drawLoadingScreen(g2d);
		}
		else if (state == 1){
			drawChoiceScreen(g2d);
		}
		else if (state == 2){
			drawLayoutOne(g2d);
		}
		this.drawExit(g2d);

	}
	
	public void drawLayoutOne(Graphics2D g2d){
		drawBackground(g2d,bg,true);
		drawBackground(g2d, layout1, false);
		if (!layoutOneExists){
			layoutOne = new LayoutOne(getWidth(), getHeight(), g2d);
			layoutOneExists = true;
		}
		layoutOne.drawLayoutOne(g2d);
	}

	public void drawChoiceScreen(Graphics2D g2d){
		drawBackground(g2d,bg, true);
		drawBackground(g2d, l1, false);
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {		
	}

	public void mouseExited(MouseEvent e) {		
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX ();
		int my = e.getY ();
		try{

		//printCoords(mx,my,e);
		if (state == 0 && e.getButton () == MouseEvent.BUTTON1 && mx >= getWidth()/2.4285714285714284 && my >= getHeight()/1.824228028503563 && mx <= getWidth()/1.7435897435897436 && my <= getHeight()/1.4970760233918128){
			state = 1;
		}

		if (state == 1 && e.getButton () == MouseEvent.BUTTON1 && mx >= getWidth()/14.31578947368421 && my <= getWidth()/2.7254509018036073 && mx <= getHeight()/1.8113207547169812 && my >= getHeight()/3.213389121338912){
			state = 2;
		}
		if (state == 2){
			layoutOne.checkClicks(e);
		}
		if (mx >= getWidth()/1.0225563909774436090225563909774 && my <= getHeight()/25.6){
			this.dispose();
		}
		}catch(NullPointerException n){}
	}

	public void mouseReleased(MouseEvent e) {
		int mx = e.getX ();
		int my = e.getY ();
		if (state == 2){
			try{
			layoutOne.getDisplayCard().checkScroll(e, mx, my);
			layoutOne.checkRelease(e, mx, my);
			}catch(NullPointerException nullPointerException){}
		}
	}
	
	public static void delay(int k){
	    try {
			Thread.sleep(k);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void printCoords(int mx, int my, MouseEvent e){
		if (firstclick[0]== 0 && firstclick[1] == 0){
			firstclick[0] = mx;
			firstclick[1] = my;
		}
		else if (secondclick[0]== 0 && secondclick[1] == 0){
			secondclick[0] = mx;
			secondclick[1] = my;
		}
		
		String[] df1 = {"",""};
		String[] df2 = {"",""};
		
		df1[0] = "getWidth()/" + Double.toString(1360/firstclick[0]);
		df1[1] = "getHeight()/" + Double.toString(768/firstclick[1]);
		df2[0] = "getWidth()/" + Double.toString(1360/secondclick[0]);
		df2[1] = "getHeight()/" + Double.toString(768/secondclick[1]);
		
		// first click bottom left; second click top right
		
		if (firstclick[0] != 0 && firstclick[1] != 0 && secondclick[0] != 0 && secondclick[1] != 0){
			System.out.println("if (mx >= " + df1[0] + " && my >= " + df1[1] + " && mx <= " + df2[0] +
					           " && my <= " + df2[1] + "){" + "\n" + "\t" + "//stuff" + "\n" + "}");
			System.out.println("if (mx >= " + firstclick [0] + " && my >= " + firstclick [1] + " && mx <= " + secondclick[0] +
					           " && my <= " + secondclick[1] + "){" + "\n" + "\t" + "//stuff" + "\n" + "}");
			firstclick [0] = 0; 
			firstclick [1] = 0;
			secondclick[0] = 0; 
			secondclick[1] = 0;
		}
			
	}


	public void loadpics(){
		String startpath = System.getProperty("user.dir") + "\\src\\Images\\";
		TOF = new ImageIcon(startpath + "TOF.png").getImage();
		Begin = new ImageIcon(startpath + "Begin.png").getImage();
		//sirius = new ImageIcon(startpath + "sirius.png").getImage();
		bg = new ImageIcon(startpath + "galaxy.jpg").getImage();
		l1 = new ImageIcon(startpath + "galaxytemplate.png").getImage();
		layout1 = new ImageIcon(startpath + "layout1.png").getImage();
		ImageIcon w = new ImageIcon(startpath + "circle3.png");
		circ = w.getImage();
		circw = w.getIconWidth();
		imageExit = new ImageIcon(startpath + "exit.png").getImage();
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File (System.getProperty("user.dir") + "\\src\\Audio\\Frost.wav")));     //(new URL(startpath + "\\src\\Audio\\Frost.wav")));
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e){e.printStackTrace();}
		repaint();
	}

	public void drawLoadingScreen(Graphics2D g2d){
		drawBackground(g2d, bg, true);
		drawLoadCircle(g2d);
		drawLoadText(g2d);
	}

	public void drawBackground(Graphics2D g2d, Image bg, Boolean moving){
		if (moving){
			g2d.drawImage(bg, bgx, 0, getWidth(), getHeight(), null);
			g2d.drawImage(bg, -getWidth() + bgx, 0, getWidth(), getHeight(), null);
			bgx ++;
		}
		else{
			g2d.drawImage(bg, 0,0,getWidth(),getHeight(),null);
		}
		if (bgx == getWidth()){
			bgx = 0;
		}
		i=i+0.003;
		if (i == 2){
			i = 0.01;
		}
	}

	public void drawLoadCircle(Graphics2D g2d){
		g2d.translate(getWidth()/2 , getHeight()/2);
		g2d.rotate(Math.PI*i);
		g2d.translate(-getWidth()/2, -getHeight()/2);
		g2d.drawImage(circ, getWidth()/2 - circw/2,getHeight()/2 -circw/2, null);
		g2d.translate(getWidth()/2 , getHeight()/2);
		g2d.rotate(-Math.PI*i);
		g2d.translate(-getWidth()/2, -getHeight()/2);
	}

	public void drawLoadText(Graphics2D g2d){
		Font font = new Font("Precious", Font.PLAIN, 70);
		g2d.setFont(font);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opac));
		g2d.drawImage(TOF, getWidth()/2 - getWidth()/4, getHeight()/2 - getHeight()/4, getWidth()/2, getHeight()/6, null);
		g2d.drawImage(Begin, getWidth()/2 - getWidth()/(23/2), getHeight()/2 + getHeight()/25, getWidth()/6, getHeight()/7, null);
		if (opac < 0.99){
			opac+=0.003;
		}
	}
	
	public void drawExit(Graphics2D g2d){
		g2d.drawImage(imageExit, toInt(getWidth()/1.0225563909774436090225563909774), 0, null);
	}
	
	
	public void mouseDragged(MouseEvent e){
		int mx = e.getX ();
		int my = e.getY ();
		if (state == 2){
			try{
			layoutOne.getDisplayCard().checkScroll(e, mx, my);
			layoutOne.checkPull(e, mx, my);
			}catch(NullPointerException nullPointerException){}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
	
	public static int toInt(double d){
		return (int)Math.round(d);
	}
	
}



















