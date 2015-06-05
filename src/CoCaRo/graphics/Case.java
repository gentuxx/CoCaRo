package CoCaRo.graphics;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import CoCaRo.Element;

public class Case extends JTextField {

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
		
		Font f  = getFont();
		setFont(f.deriveFont(Font.BOLD));
		
		if (e == null) {
			this.setBackground(Color.white);
			setText("");
		}
		
		if (e == Element.AGENT) {
			this.setBackground(Color.black);
			setText("A");
		}
		
		if (e == Element.BLUE_AGENT) {
			this.setBackground(Color.blue);
			setText("A");
		}
		
		if (e == Element.RED_AGENT) {
			this.setBackground(Color.red);
			setText("A");
		}
		
		if (e == Element.GREEN_AGENT) {
			this.setBackground(Color.green);
			setText("A");
		}
		
		if (e == Element.BLUE_BOX) {
			this.setBackground(Color.blue);
			setText("B");
		}
		
		if (e == Element.BLUE_NEST) {
			this.setBackground(Color.blue);
			setText("N");
		}
		
		if (e == Element.RED_BOX) {
			this.setBackground(Color.red);
			setText("B");
		}
		
		if (e == Element.RED_NEST) {
			this.setBackground(Color.red);
			setText("N");
		}
		
		if (e == Element.GREEN_BOX) {
			this.setBackground(Color.green);
			setText("B");
		}
		
		if (e == Element.GREEN_NEST) {
			this.setBackground(Color.green);
			setText("N");
		}
		
	    setHorizontalAlignment(JTextField.CENTER);
	    this.setEnabled(false);
		
	}
}
