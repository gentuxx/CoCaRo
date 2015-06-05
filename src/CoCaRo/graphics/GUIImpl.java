package CoCaRo.graphics;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.environment.interfaces.EnvChangeListener;
import speadl.environment.Environment.RobotGrid;
import speadl.graphics.GUI;

public class GUIImpl extends GUI implements ActionListener {

	private JFrame jFrame;
	private EnvChangeListener observer;
	final int GRID_SIZE = 20;
	private int lastSpeed;
	GridLayout layout;
	Element[][] elements;
	JPanel principal;
	JPanel panel;
	JPanel westPanel;
	JPanel centerPanel;
	JButton startButton;
	JButton pauseButton;
	JButton repriseButton;
	JLabel nbRobotsLabel;
	JLabel nbBoxesLabel;
	JLabel speedExecLabel;
	JTextField nbRobotsTextField;
	JTextField nbBoxesTextField;
	JTextField speedExecTextField;

	public GUIImpl() {
		init();
		//update();
		
		observer = new EnvChangeListener() {

			@Override
			public void changeEventReceived(EnvChangeEvent evt) {
				update();
			}
			
		};
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
		
		jFrame = new JFrame("Cocaro");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		elements = new Element[GRID_SIZE][GRID_SIZE];
		
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				elements[i][j] = null;
			}
		}

		startButton = new JButton("Start");
		startButton.addActionListener(this);
		
		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(this);
		
		repriseButton = new JButton("Reprise");
		repriseButton.addActionListener(this);
		
		nbRobotsLabel = new JLabel("Nombre de robots :");
		nbBoxesLabel = new JLabel("Nombre de boites :");
		speedExecLabel = new JLabel("speed d'execution :");
		
		nbRobotsTextField = new JTextField();
		nbBoxesTextField = new JTextField();
		speedExecTextField = new JTextField();
		
		layout = new GridLayout(GRID_SIZE,GRID_SIZE);

		// A mettre dans l'update la boucle de traitement
		
		GridLayout gl = new GridLayout();
		gl.setColumns(2);
		gl.setRows(3);
						
		principal = new JPanel();
		westPanel = new JPanel();
		centerPanel = new JPanel();
		
		principal.setLayout(new BoxLayout(principal, BoxLayout.LINE_AXIS));
		
		westPanel.setLayout(gl);
		westPanel.setPreferredSize(new Dimension(0,0));
		westPanel.add(nbRobotsLabel);
		westPanel.add(nbRobotsTextField);
		westPanel.add(nbBoxesLabel);
		westPanel.add(nbBoxesTextField);
		westPanel.add(speedExecLabel);
		westPanel.add(speedExecTextField);
		
		principal.add(westPanel);
				
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		centerPanel.add(startButton);
		centerPanel.add(pauseButton);
		centerPanel.add(repriseButton);
		
		principal.add(centerPanel);
				
		panel = new JPanel(layout);
		principal.add(panel);
		panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Case c = new Case(elements[i][j], i, j);
				panel.add(c);
				c.setColor(elements[i][j]);
				c.addMouseListener(new java.awt.event.MouseAdapter() {
		            public void mouseClicked(java.awt.event.MouseEvent evt) {
		            	Case c = (Case) evt.getSource();
		            	caseMouseClicked(evt, c);
		            }
		        });
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
	
	private void caseMouseClicked(java.awt.event.MouseEvent evt, Case c) {                                         
        System.out.println("Pos X: " + c.getCoordX() + " Pos Y : " + c.getCoordY());
        System.err.println(elements[c.getCoordY()][c.getCoordX()]);
        Map<Position,RobotGrid.Component> robotsMap = requires().envGet().getRobotsMap();
        
        if(elements[c.getCoordY()][c.getCoordX()] == Element.BLUE_AGENT
        		|| elements[c.getCoordY()][c.getCoordX()] == Element.GREEN_AGENT
        		|| elements[c.getCoordY()][c.getCoordX()] == Element.RED_AGENT){
        	RobotGrid.Component robot = robotsMap.get(new Position(c.getCoordY(), c.getCoordX()));
        	for(Position pos : robotsMap.keySet()){
        		System.err.println(pos.toString());
        	}
            if(robot == null){
            	System.err.println("NO ROBOT");
            }else{
            	System.out.println("ENERGY " + robot.robotCore().getEnergy() + " COLOR : " + robot.robotCore().getColor());
            }
        }
        
    }                                        

	
	private int nthComponent(int x, int y) {
		// 20 * 20
		return y * GRID_SIZE + x;
	}
	
	public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == startButton) {
        	
        	String nbRobots = nbRobotsTextField.getText();
			String nbBoxes = nbBoxesTextField.getText();
			String speedExec = speedExecTextField.getText();
			try{
				requires().init().init(Integer.parseInt(nbRobots), 
	        			Integer.parseInt(nbBoxes), Integer.parseInt(speedExec));
	        	requires().envGet().addGUI(observer);
			}catch(NumberFormatException e){
				System.out.println("Mauvaise saisie");
			}
        	        	
        } else if(ev.getSource() == pauseButton) {
        	requires().exec().pause();
        } else if(ev.getSource() == repriseButton){
        	requires().exec().restart();
        }
        
        
	}
	/*
	public static void main(String[] args) {
		new GUIImpl();
	}*/
}