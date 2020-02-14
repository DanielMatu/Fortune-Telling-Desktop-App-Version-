
// The "Card" class.
import java.awt.*;
import java.applet.*;
import javax.swing.ImageIcon;
import java.awt.event.MouseEvent;

//import hsa.Console;

public class Card extends Applet
{    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    public int x, y, facedown;
    public int width;
    public int height;
    public String name;
    public String path;
   // public String text;
    public boolean clicked;
    public int pile_index;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static double screen_width = screenSize.getWidth();
	static double screen_height = screenSize.getHeight();
	private int scrollfactor;
	private String text;
	private boolean doubled;


    public Card (int x, int y, String name, int facedown, int width, int height, String text, Graphics2D g2d)
    {
    	this.width = width;
    	this.height = height;
    	this.x = x;
    	this.y = y;
    	this.name = name;
    	this.facedown = facedown;
    	this.drawCard(g2d);
    	this.text = text;
    	this.doubled = false;
    }
    
    
    public void drawCard(Graphics2D g2d){
    	float opac = 0.8f;
    	Image local_image;
		String startpath = System.getProperty("user.dir") + "\\src\\Images\\Tarot\\";
    	local_image = new ImageIcon(startpath + this.name).getImage();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opac));
    	if (this.facedown == 1){
    		g2d.drawImage(local_image, this.x, this.y, this.width, this.height, null);
    	}
    	else if (this.facedown == 0){
    		g2d.drawImage(new ImageIcon(startpath + "cardback1.png").getImage(), this.x, this.y, this.width, this.height, null);
    	}
    	else{
    		//;
    	}
    	opac = 1;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opac));
		

    }
    
    public void setDoubled(boolean doubled){
    	this.doubled = doubled;
    }
    
    public boolean getDoubled(){
    	return this.doubled;
    }
    
    public boolean checkBoundaries(MouseEvent e){
    	int mx = e.getX();
    	int my = e.getY();
    	return (mx >= this.x && my >= this.y && mx <= this.x + this.width && my <= this.y + this.height);
    }
    
    public void setX(int newValue){
    	this.x = newValue;
    }
    
    public void setY(int newValue){
    	this.y = newValue;
    }
    
    public int getX(){
    	return this.x;
    }
    
    public int getY(){
    	return this.y;
    }
    
    public void setFaceDown(int i){
    	this.facedown = i;
    }

	public int getFaceDown() {
		return this.facedown;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public void setName(String input){
		this.name = input;
	}
	
	public void setText(String input){
		this.text = input;
	}
   
	public String getName(){
		return this.name;
	}
	
	public String getText(){
		return this.text;
	}
	
	public int getScrollFactor(){
		return this.scrollfactor;
	}
	
	public void setScrollFactor(int i){
		this.scrollfactor = i;
	}

    

    
    
    
} // Card class
