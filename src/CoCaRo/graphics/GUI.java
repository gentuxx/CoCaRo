package CoCaRo.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import CoCaRo.Element;

public class GUI extends JFrame {

	final int GRID_SIZE = 20;
	GridLayout layout;
	Element[][] elements;

	public GUI() {
		init();
	}

	public void init() {

		elements = new Element[GRID_SIZE][GRID_SIZE];
		
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				
				// TODO : Recuperer les vraies valeurs
				Element[] e = new Element[8];

				e[0] = Element.AGENT;
				e[1] = Element.BLUE_BOX;
				e[2] = Element.BLUE_NEST;
				e[3] = Element.RED_BOX;
				e[4] = Element.RED_NEST;
				e[5] = Element.GREEN_BOX;
				e[6] = Element.GREEN_NEST;
				e[7] = null;

				Random r = new Random();
				int num = r.nextInt(8);

				elements[i][j] = e[num];
			}
		}

		layout = new GridLayout(GRID_SIZE,GRID_SIZE);

		setTitle("GUI CoCaRo");

		JPanel panel = new JPanel(layout);
		panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Case c = new Case(elements[i][j]);
				panel.add(c);
				c.setColor();
			}
		}

		add(panel);
		setSize(700, 700);

		// Centers the window
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		// Changing one case at [2][0]
		Case c = (Case) panel.getComponent(nthComponent(2,0));
		c.setColor();
		
		setVisible(true);
	} 
	
	private int nthComponent(int x, int y) {
		// 20 * 20
		return y * GRID_SIZE + x;
	}
	
	public static void main(String[] args) {
		new GUI();
	}
}