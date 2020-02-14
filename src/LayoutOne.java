import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;


public class LayoutOne {

	/**
	 * @param args
	 */
	private int screen_width, screen_height;
	private Card l1cards[] [] = new Card[8] [2];
	private DisplayCard displayCard;
	private int cardw;
	private int cardh;
	private boolean cardHeld = false;
	private int immediatex, immediatey, heldCardX, heldCardY;
	private Deck deck;
	public LayoutOne (int screen_width, int screen_height, Graphics2D g2d){
		this.screen_height = screen_height;
		this.screen_width = screen_width;
		this.cardw = (int)(Math.round(screen_width/9.0666666667));
		this.cardh = (int)(Math.round(screen_height/3.7463414634146341463414634146341));
		this.deck = new Deck(g2d);
		for (int i=0;i<8;i++){
			this.l1cards[i][0] = this.deck.pullCard();
		}
		this.l1cards[0][0].setX((int)Math.round(this.screen_width/3.5061439588688945)); this.l1cards[0][0].setY((int)Math.round(this.screen_height/15.673469387755102));
		this.l1cards[1][0].setX((int)Math.round(this.screen_width/1.7754569190600522)); this.l1cards[1][0].setY((int)Math.round(this.screen_height/15.673469387755102));
		this.l1cards[2][0].setX((int)Math.round(this.screen_width/6.938775510204081)); this.l1cards[2][0].setY((int)Math.round(this.screen_height/2.657439446366782));
		this.l1cards[3][0].setX((int)Math.round(this.screen_width/3.5051546391752577)); this.l1cards[3][0].setY((int)Math.round(this.screen_height/2.657439446366782));
		this.l1cards[4][0].setX((int)Math.round(this.screen_width/2.3429411764705883)); this.l1cards[4][0].setY((int)Math.round(this.screen_height/2.6666666666666665));
		this.l1cards[5][0].setX((int)Math.round(this.screen_width/1.7754569190600522)); this.l1cards[5][0].setY((int)Math.round(this.screen_height/2.657439446366782));
		this.l1cards[6][0].setX((int)Math.round(this.screen_width/3.5042118863049094)); this.l1cards[6][0].setY((int)Math.round(this.screen_height/1.471264367816092));
		this.l1cards[7][0].setX((int)Math.round(this.screen_width/1.7754569190600522)); this.l1cards[7][0].setY((int)Math.round(this.screen_height/1.471264367816092));
		displayCard = new DisplayCard("cardback1.png", 2, "cardback1.png", g2d);
		this.heldCardX = this.deck.getTopDeckX();
		this.heldCardY = this.deck.getTopDeckY();
	}
	
	public void drawLayoutOne(Graphics2D g2d){
		for (int i=0; i<8; i++){
			for (int j=0; j<2;j++){
				try{
					this.l1cards[i][j].drawCard(g2d);
				}catch(NullPointerException nullPointer){}
			}
		}
		displayCard.drawCard(g2d);
		displayCard.drawScrollBar(g2d);
		deck.drawDeck(g2d);
		String startpath = System.getProperty("user.dir") + "\\src\\Images\\Tarot\\";
		g2d.drawImage(new ImageIcon(startpath + "cardback1.png").getImage(), this.heldCardX, this.heldCardY, this.cardw, this.cardh, null);
	}
	
	public void checkPull(MouseEvent e, int mx, int my){
		if (this.immediatex >= (this.deck.getTopDeckX()) && this.immediatey <= this.deck.getTopDeckY() + this.cardh && this.immediatex <= this.deck.getTopDeckX() + this.cardw && this.immediatey >= this.deck.getTopDeckY()){
			this.cardHeld = true;
		}
		if (this.cardHeld){
			this.heldCardX = mx - this.cardw/2;
			this.heldCardY = my - this.cardh/2; 
		}

	}
	
	public void setHeld(Boolean bool){
		this.cardHeld = bool;
	}
	
	public Boolean getHeld(){
		return this.cardHeld;
	}
	
	public void checkRelease(MouseEvent e, int mx, int my) {

		for (int i=0; i<8; i++){
			if (e.getButton () == MouseEvent.BUTTON1 && l1cards[i][0].checkBoundaries(e)){
				//l1cards[i][0].setFaceDown(1);
				//displayCard.setCardToDisplay(l1cards[i][0]);
				if (this.cardHeld && !this.l1cards[i][0].getDoubled()){
					this.l1cards[i][1] = this.doubleUp(this.l1cards[i][0]);
				}
			}
		}
		
		this.cardHeld = false; 
		this.heldCardX = this.deck.getTopDeckX(); this.heldCardY = this.deck.getTopDeckY();
	}
	
	public Card doubleUp(Card original){
		Card temp = this.deck.pullCard();
		temp.setX(original.getX() - toInt(this.screen_width/68));
		temp.setY(original.getY() - toInt(this.screen_height/38.4));
		original.setDoubled(true);
		return temp;
	}
	
	public void checkClicks(MouseEvent e){
		int mx = e.getX ();
		int my = e.getY ();
		this.immediatex = mx;
		this.immediatey = my;

		for (int i=0; i<8; i++){
			for (int j = 0; j < 2; j++){
				try{
					if (e.getButton () == MouseEvent.BUTTON1 && this.l1cards[i][j].checkBoundaries(e)){
						this.l1cards[i][j].setFaceDown(1);
						displayCard.setCardToDisplay(this.l1cards[i][j]);
					}
				}catch(NullPointerException n){}
			}
		}
		
		try{
		displayCard.checkScroll(e, mx, my);
		}catch(NullPointerException ne){}
	}
	
	public DisplayCard getDisplayCard(){
		return this.displayCard;
	}
	
	public static int toInt(double d){
		return (int)Math.round(d);
	}


}
