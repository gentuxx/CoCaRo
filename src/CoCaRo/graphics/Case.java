package CoCaRo.graphics;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

import CoCaRo.Element;

public class Case extends JLabel {

	/**
	 * Generated Serial ID 
	 */
	private static final long serialVersionUID = 8166664197376509785L;
	
	
	Element e;
	int x;
	int y;

	public Case(Element e, int x, int y) {
		this.e = e;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.x = x;
		this.y = y;
	}

	public int getCoordX() {
		return x;
	}

	public int getCoordY() {
		return y;
	}

	public synchronized void setColor(Element e) {
		
		this.setOpaque(true);
		
		if (e == null) {
			this.setIcon(null);
			this.setBackground(Color.white);
		}
		
		if (e == Element.AGENT) {
			this.setIcon(new ImageIcon(getClass().getResource("/resources/robot.png")));
			this.setBackground(Color.white);
		}
		
		if (e == Element.BLUE_AGENT) {
			this.setIcon(new ImageIcon(getClass().getResource("/resources/robot.png")));
			this.setBackground(Color.blue);
		}
		
		if (e == Element.RED_AGENT) {
			this.setIcon(new ImageIcon(getClass().getResource("/resources/robot.png")));
			this.setBackground(Color.red);
		}
		
		if (e == Element.GREEN_AGENT) {
			this.setIcon(new ImageIcon(getClass().getResource("/resources/robot.png")));
			this.setBackground(Color.green);
		}
		
		if (e == Element.BLUE_BOX) {
			this.setIcon(new ImageIcon(getClass().getResource("/resources/blue_box.jpg")));
			this.setBackground(Color.white);
		}
		
		if (e == Element.BLUE_NEST) {
			this.setIcon(new ImageIcon(getClass().getResource("/resources/nest.png")));
			this.setBackground(Color.blue);
		}
		
		if (e == Element.RED_BOX) {
			this.setIcon(new ImageIcon(getClass().getResource("/resources/red_box.jpg")));
			this.setBackground(Color.white);
		}
		
		if (e == Element.RED_NEST) {
			this.setIcon(new ImageIcon(getClass().getResource("/resources/nest.png")));
			this.setBackground(Color.red);
		}
		
		if (e == Element.GREEN_BOX) {
			this.setIcon(new ImageIcon(getClass().getResource("/resources/green_box.jpg")));
			this.setBackground(Color.white);
		}
		
		if (e == Element.GREEN_NEST) {
			this.setIcon(new ImageIcon(getClass().getResource("/resources/nest.png")));
			this.setBackground(Color.green);
		}
		
	    setHorizontalAlignment(JTextField.CENTER);	
	}
}
