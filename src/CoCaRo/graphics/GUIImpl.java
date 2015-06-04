package CoCaRo.graphics;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import speadl.graphics.GUI;
import CoCaRo.Element;
import CoCaRo.environment.interfaces.EnvChangeListener;

public class GUIImpl extends GUI implements ActionListener {

	private JFrame jFrame;
	private EnvChangeListener observer;
	final int GRID_SIZE = 20;
	GridLayout layout;
	Element[][] elements;
	JPanel principal;
	JPanel panel;
	JButton startButton;

	public GUIImpl() {
		jFrame = new JFrame("Cocaro");
		init();
		//update();
		
		observer = new EnvChangeListener() {

			@Override
			public void changeEventReceived(EnvChangeEvent evt) {
				update();
			}
			
		};
		
		//requires().envGet().addGUI(observer);
	}

	public void update() {
		//TODO Mettre � jour l'interface graphique � partir des donn�es fournies par le "requires"
		// Récupérer le envGet par le requires
		// puis faire le traitement de juste en dessous, et enlever ça de l'init
		
		elements = requires().envGet().getGrid();
		
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Case c = (Case) panel.getComponent(nthComponent(i,j));
				c.setColor(elements[i][j]);
			}
		}
		
		// How to update a case manually
		// Changing one case at [2][0]
		//Case c = (Case) panel.getComponent(nthComponent(2,0));
		//c.setColor();
	}
	
	/**
	 * Changer -> il faut juste l'initialisation des variables
	 */
	public void init() {

		elements = new Element[GRID_SIZE][GRID_SIZE];
		
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				elements[i][j] = null;
			}
		}

		startButton = new JButton("Start");
		startButton.addActionListener(this);
		
		layout = new GridLayout(GRID_SIZE,GRID_SIZE);

		// A mettre dans l'update la boucle de traitement
		principal = new JPanel();
		principal.setLayout(new BoxLayout(principal, BoxLayout.LINE_AXIS));
		principal.add(startButton);
		
		panel = new JPanel(layout);
		principal.add(panel);
		panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		//panel.add(startButton);

		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Case c = new Case(elements[i][j]);
				panel.add(c);
				c.setColor(elements[i][j]);
			}
		}

		jFrame.add(principal);
		jFrame.setSize(700, 700);

		// Centers the window
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		jFrame.setLocation(dim.width/2-jFrame.getSize().width/2, dim.height/2-jFrame.getSize().height/2);

		// Changing one case at [2][0]
		//Case c = (Case) panel.getComponent(nthComponent(2,0));
		//c.setColor();
		
		jFrame.setVisible(true);
	}
	
	private int nthComponent(int x, int y) {
		// 20 * 20
		return y * GRID_SIZE + x;
	}
	
	public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == startButton) {
        	update();
        }
	}
	/*
	public static void main(String[] args) {
		new GUIImpl();
	}*/
}