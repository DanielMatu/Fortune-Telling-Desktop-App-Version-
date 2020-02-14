import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseEvent;



public class DisplayCard extends Card //in hindsight extending card was a design flaw. will revise.
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Card card_to_display;
	private int pages, adjustedBarHeight;
	private float fadeFactor = 0;
	private static int scrollheight = toInt(screen_height/2.844444);
    
    public DisplayCard(String name, int facedown, String text, Graphics2D g2d){
    	super((int)Math.round(screen_width/1.2914354066985645), (int)Math.round(screen_height/22.58823529411765), name, facedown, (int)Math.round(screen_width/4.8637583892617449664429530201342), (int)Math.round(screen_height/1.8731707317073170731707317073171), text, g2d);
    	//this.getCardToDisplay().setScrollFactor(0);
    }

    public void setCardToDisplay(Card c){
    	this.card_to_display = c;
    	this.name = c.getName();
    	this.facedown = 1;
    	//this.scrollfactor = 0;
    }
    
    public Card getCardToDisplay(){
    	return this.card_to_display;
    }
    
	public void drawScrollBar(Graphics2D g2d){
		//g2d.drawString(l1cards[i].getText(), x, y)
		g2d.setFont(new Font("Monospaced", Font.PLAIN, 14));  // Monospaced
		List<String> strings;
		try{
		strings = splitString(this.getCardToDisplay().getText(), 26); //22
		strings = trimStrings(strings);
		g2d.setColor(Color.WHITE);
		this.drawCurrentPage(g2d, strings, 0, strings.size());
		} catch(NullPointerException nullPointerException){}
		//g2d.setColor(Color.WHITE);
	}

	public List<String> splitString(String s, int length){
		// end the string with an empty space (e.g. "this is a string ")
		// not designed to handle strings longer than line length (e.g. 25 character long strings)
		char c;
		List<String> strings = new ArrayList<String>();
		int safe_index = 0;
		int j = -1;
		for (int i = 0; i < s.length(); i++){
		    j++;
		    c = s.charAt(i);
		    if (c == ' '){
		    	if (j < length){
		    		safe_index = i; //3
		    	}
		    	else{
		    		try{
		    			strings.add(s.substring(i - j, safe_index));
		    		} catch(StringIndexOutOfBoundsException sIOBE){}
		    		i = safe_index;
		    		j = 0;
		    	}
			}
		}
		if (s.length() - (safe_index - j) > length){
			strings.add(s.substring(s.length() - j, safe_index));
			strings.add(s.substring(safe_index, s.length()));
		}
		else{
			strings.add(s.substring(safe_index, s.length()));   // strings.add(s.substring(s.length() - j, s.length())); 
		}
		return strings;
	}
	
	public void drawCurrentPage(Graphics2D g2d, List<String> strings, int start, int end){
		int localy;
		this.pages = (int)Math.ceil(strings.size()/13.0);  // number of lines / number of lines per page
		int UPPERBOUND = toInt(screen_height/1.422), LOWERBOUND = toInt(screen_height/1.052), LINEHEIGHT = toInt(screen_height/38.4);
		//550                                        730

		for (int i = start; i < end; i++){
			localy = toInt(UPPERBOUND + i*toInt(screen_height/38.4) - (this.getCardToDisplay().getScrollFactor()*(strings.size()+3)/13.0));
			if (localy >= (UPPERBOUND - LINEHEIGHT) && localy <= LOWERBOUND){ 
				//soft boundaries (fade effect)
				

				
				if (localy >= (UPPERBOUND - LINEHEIGHT) && localy <= UPPERBOUND){ //|| (localy <= 730 && localy >= 710){
					fadeFactor = localy - (UPPERBOUND - LINEHEIGHT);
					this.fade(fadeFactor, localy, strings, i, toInt(screen_width/1.289), g2d);
				}
				else if (localy <= LOWERBOUND && localy >= (LOWERBOUND - LINEHEIGHT)){
					fadeFactor = LOWERBOUND - localy;
					this.fade(fadeFactor, localy, strings, i, toInt(screen_width/1.289), g2d);
				}
				else{
					g2d.drawString(strings.get(i), toInt(screen_width/1.289), localy); //1068
				}

			}
		}
		this.drawScroll(g2d, this.pages, this.getCardToDisplay().getScrollFactor());
	}
	
	public void drawScroll(Graphics2D g2d, int pages, int scrollfactor){
		g2d.fillRoundRect(toInt(screen_width/1.042), toInt(screen_height/1.6) + scrollfactor, toInt(screen_height/21.943), (int)Math.round(scrollheight/this.pages), toInt(screen_height/21.943), toInt(screen_height/51.2));
	}
	
	public void fade(float fadeFactor, int localy, List<String> strings, int i, int x, Graphics2D g2d){
		float opac = 1;
		opac = opac * (fadeFactor/20);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opac));
		g2d.drawString(strings.get(i), x, localy); //1055
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
	
	public void scrollDown(Graphics2D g2d){
	}
	
	public void checkScroll(MouseEvent e, int mx, int my){
		if (this.pages != 0){
			this.adjustedBarHeight = scrollheight/(this.pages);
		}
		if (mx >= screen_width/1.042 && mx <= screen_width/1.015 && my >= screen_height/1.6 && my <= screen_height/1.6 + (int)Math.round(scrollheight)){
			this.getCardToDisplay().setScrollFactor( (my - toInt(screen_height/1.6) - ((int)Math.round(this.adjustedBarHeight/2)    )));
			//scroll boundaries
			if (this.getCardToDisplay().getScrollFactor() > (toInt(screen_height/2.844444) - this.adjustedBarHeight)){ 
				this.getCardToDisplay().setScrollFactor(toInt(screen_height/2.844444) - this.adjustedBarHeight);
			}
			if (this.getCardToDisplay().getScrollFactor() < 0){
				this.getCardToDisplay().setScrollFactor(0);
			}
		}
		
		
		
	}

	public static int toInt(double d){
		return (int)Math.round(d);
	}
	
	public static List<String> trimStrings(List<String> input){
		List<String> returnList = new ArrayList<String>();
		for (String s: input){
			returnList.add(" " + s.trim());
		}
		return returnList;
	}
	    
	public static void delay(int k){
	    try {
			Thread.sleep(k);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
