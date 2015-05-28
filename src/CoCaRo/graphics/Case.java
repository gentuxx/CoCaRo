package CoCaRo.graphics;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import CoCaRo.Element;

public class Case extends JTextField {

	Element e;

	public Case(Element e) {
		this.e = e;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public void setColor() {
		
		Font f  = getFont();
		setFont(f.deriveFont(Font.BOLD));
		
		if (e == null)
			this.setBackground(Color.white);
		
		if (e == Element.AGENT) {
			this.setBackground(Color.black);
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
