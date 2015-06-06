package CoCaRo.graphics;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.environment.interfaces.EnvChangeListener;
import speadl.environment.Environment.RobotGrid;
import speadl.graphics.GUI;

public class GUIImpl extends GUI implements ActionListener {

	private final int GRID_SIZE = 20;
	
	private JFrame mainFrame;
	private EnvChangeListener observer;
	
	
	GridLayout gridLayout;
	Element[][] elements;
	
	JPanel mainPanel;
	JPanel gridPanel;
	JPanel configPanel;
	JPanel centerPanel;

	JButton startButton;
	JButton pauseButton;
	JButton repriseButton;
	JButton lowSpeedButton;
	JButton highSpeedButton;
	JButton resetButton;

	JLabel nbRobotsLabel;
	JLabel nbBoxesLabel;
	JLabel speedConfigLabel;
	JLabel speedValueLabel;
	
	JTextField nbRobotsField;
	JTextField nbBoxesField;
	JTextField speedConfigField;

	public GUIImpl() {
		mainFrame = new JFrame("Cocaro");
		init();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		// R�cup�rer le envGet par le requires
		// puis faire le traitement de juste en dessous, et enlever �a de l'init
		
		elements = requires().envGet().getGrid();
		
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Case c = (Case) gridPanel.getComponent(nthComponent(i,j));
				c.setColor(elements[i][j]);
			}
		}
		
		// How to update a case manually
		// Changing one case at [2][0]
		//Case c = (Case) panel.getComponent(nthComponent(2,0));
		//c.setColor();
	}
	
	private void initButtons() {
		
		//Initialisation des diff�rents boutons
		
		startButton = new JButton("",new ImageIcon(getClass().getResource("/resources/start.png")));
		startButton.addActionListener(this);
		startButton.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		
		pauseButton = new JButton("",new ImageIcon(getClass().getResource("/resources/pause.png")));
		pauseButton.addActionListener(this);
		pauseButton.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		
		repriseButton = new JButton("Reprise");
		repriseButton.addActionListener(this);
		repriseButton.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		resetButton.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		
		lowSpeedButton = new JButton(">>");
		lowSpeedButton.addActionListener(this);
		
		highSpeedButton = new JButton("<<");
		highSpeedButton.addActionListener(this);
		
		speedValueLabel = new JLabel("0");	
		
		//Cr�ation d'un panel pour la gestion de la vitesse
		JPanel speedPanel = new JPanel();
		speedPanel.setLayout(new BoxLayout(speedPanel,BoxLayout.LINE_AXIS));
		speedPanel.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		
		//Ajout des boutons de contr�le de la vitesse et du label
		speedPanel.add(highSpeedButton);
		speedPanel.add(Box.createRigidArea(new Dimension(10,0)));
		speedPanel.add(speedValueLabel);
		speedPanel.add(Box.createRigidArea(new Dimension(10,0)));
		speedPanel.add(lowSpeedButton);
		
		//Cr�ation du panel central
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
		
		//Ajout des diff�rents boutons et du speedPanel
		centerPanel.add(startButton);
		centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
		centerPanel.add(pauseButton);
		centerPanel.add(repriseButton);
		centerPanel.add(speedPanel);
		centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
		centerPanel.add(resetButton);
		
		//D�sactivation des boutons
		pauseButton.setEnabled(false);
		highSpeedButton.setEnabled(false);
		lowSpeedButton.setEnabled(false);
		resetButton.setEnabled(false);
		repriseButton.setEnabled(false);
	}
	
	private void initConfigPanel() {
		//Initialisation des labels
		nbRobotsLabel = new JLabel("Nombre de Robots :");
		nbRobotsLabel.setHorizontalAlignment(JLabel.CENTER);
		
		nbBoxesLabel = new JLabel("Nombre de Bo�tes :");
		nbBoxesLabel.setHorizontalAlignment(JLabel.CENTER);
		
		speedConfigLabel = new JLabel("Vitesse Initiale :");
		speedConfigLabel.setHorizontalAlignment(JLabel.CENTER);
		
		//Initialisation des champs
		nbRobotsField = new JTextField();
		nbRobotsField.setPreferredSize(new Dimension(50,32));
		
		nbBoxesField = new JTextField();
		nbBoxesField.setPreferredSize(new Dimension(50,32));
		
		speedConfigField = new JTextField();
		speedConfigField.setPreferredSize(new Dimension(50,32));
		
		//Initialisation du panel de configuration
		configPanel = new JPanel();
		configPanel.setLayout(new GridBagLayout());
		
		//Ajout des diff�rents �l�ments
		GridBagConstraints constraints=new GridBagConstraints();
		
		constraints.insets = new Insets(0,5,0,5);
		
		configPanel.add(nbRobotsLabel);
		configPanel.add(nbRobotsField);
		
		constraints.gridy = 1;  
		
		configPanel.add(nbBoxesLabel,constraints);
		configPanel.add(nbBoxesField,constraints);
		
		constraints.gridy = 2;  
		
		configPanel.add(speedConfigLabel,constraints);
		configPanel.add(speedConfigField,constraints);
		
		//Update the size of the panel
		configPanel.setMaximumSize(new Dimension(300,200));
		configPanel.setPreferredSize(new Dimension(300,200));
	}
	
	private void initGrid() {
		//Initialisation du layout
		gridLayout = new GridLayout(GRID_SIZE,GRID_SIZE);
		
		//Initialisation du conteneur de la grille
		gridPanel = new JPanel(gridLayout);
		gridPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				//Cr�ation d'une case
				Case c = new Case(elements[i][j], i, j);
				c.setColor(elements[i][j]);
				c.addMouseListener(new MouseAdapter() {
		            public void mouseClicked(MouseEvent evt) {
		            	Case c = (Case) evt.getSource();
		            	caseMouseClicked(evt, c);
		            }
		        });
				
				//Ajout � la grille
				gridPanel.add(c);
			}
		}
	}
	
	/**
	 * Changer -> il faut juste l'initialisation des variables
	 */
	public void init() {
		
		elements = new Element[GRID_SIZE][GRID_SIZE];

		initConfigPanel();
		
		initButtons();
		
		initGrid();
								
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
		
		JSeparator leftSeparator = new JSeparator(JSeparator.VERTICAL);
		leftSeparator.setPreferredSize(new Dimension(5,1000));
		leftSeparator.setMaximumSize(new Dimension(5,1000));
		
		JSeparator rightSeparator = new JSeparator(JSeparator.VERTICAL);
		rightSeparator.setPreferredSize(new Dimension(5,1000));
		rightSeparator.setMaximumSize(new Dimension(5,1000));
		
		
		mainPanel.add(configPanel);
		mainPanel.add(leftSeparator);
		mainPanel.add(Box.createRigidArea(new Dimension(5,0)));
		mainPanel.add(centerPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(5,0)));
		mainPanel.add(rightSeparator);
		mainPanel.add(gridPanel);

		mainFrame.add(mainPanel);

		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth()/2);
		int ySize = ((int) tk.getScreenSize().getHeight()/2);
		
		mainFrame.setSize(xSize, ySize);
		mainFrame.setLocationRelativeTo(null);
		
		mainFrame.setVisible(true);
	}
	
	private void caseMouseClicked(java.awt.event.MouseEvent evt, Case c) {                                         

        Map<Position,RobotGrid.Component> robotsMap = requires().envGet().getRobotsMap();
        
        if(elements[c.getCoordY()][c.getCoordX()] == Element.BLUE_AGENT
        		|| elements[c.getCoordY()][c.getCoordX()] == Element.GREEN_AGENT
        		|| elements[c.getCoordY()][c.getCoordX()] == Element.RED_AGENT){
        	RobotGrid.Component robot = null ;
        	
        	for(Position pos : robotsMap.keySet()){
        		
        		if(pos.equals(new Position(c.getCoordY(), c.getCoordX()))){
        			robot = robotsMap.get(pos);
        		}	
        	}
            if(robot != null){
            	String message = "ENERGY : " + robot.robotCore().getEnergy() + " ROBOT COLOR : " + robot.robotCore().getColor();
            	if(robot.robotCore().hasBox()){
            		message += " HAS BOX : " + robot.robotCore().getBoxColor();
            	}
            	
            	JOptionPane.showMessageDialog(mainFrame, message);
            }
        }
        
    }                                        

	
	private int nthComponent(int x, int y) {
		// 20 * 20
		return y * GRID_SIZE + x;
	}
	
	public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == startButton) {
        	
        	String nbRobots = nbRobotsField.getText();
			String nbBoxes = nbBoxesField.getText();
			String speedExec = speedConfigField.getText();
			try{
				requires().init().init(Integer.parseInt(nbRobots), 
	        			Integer.parseInt(nbBoxes), Integer.parseInt(speedExec));
	        	requires().envGet().addGUI(observer);
	        	
	        	repriseButton.setEnabled(false);
	        	startButton.setEnabled(false);
	        	pauseButton.setEnabled(true);
	        	lowSpeedButton.setEnabled(true);
	        	resetButton.setEnabled(true);
	        	
	        	if (Integer.parseInt(speedExec) > 1) {
	        		highSpeedButton.setEnabled(true);
	        	} else {
	        		highSpeedButton.setEnabled(false);
	        	}

			}catch(NumberFormatException e){
				System.out.println("Mauvaise saisie");
			}
        	        	
        } else if(ev.getSource() == pauseButton) {
        	requires().exec().pause();
        } else if(ev.getSource() == repriseButton){
        	
        	requires().exec().restart();
        	
        	repriseButton.setEnabled(false);
        	startButton.setEnabled(false);
        	pauseButton.setEnabled(true);
        } else if (ev.getSource() == highSpeedButton) {
        	requires().exec().decreaseSpeed();
        	if (requires().exec().getSpeed() <= 1) {
        		highSpeedButton.setEnabled(false);
        	}
        } else if (ev.getSource() == lowSpeedButton) {
        	requires().exec().increaseSpeed();
        	highSpeedButton.setEnabled(true);
        } else if (ev.getSource() == resetButton) {
        	/*
        	repriseButton.setEnabled(false);
        	startButton.setEnabled(true);
        	pauseButton.setEnabled(false);
        	vitessePlusButton.setEnabled(false);
        	vitesseMoinsButton.setEnabled(false);
        	resetButton.setEnabled(false);
        	
        	requires().exec().stop();
        	
    		for (int i = 0; i < GRID_SIZE; i++) {
    			for (int j = 0; j < GRID_SIZE; j++) {
    				elements[i][j] = null;
    			}

    		}*/
        	
        	String nbRobots = nbRobotsField.getText();
			String nbBoxes = nbBoxesField.getText();
			String speedExec = speedConfigField.getText();
			try{
				requires().init().init(Integer.parseInt(nbRobots), 
	        			Integer.parseInt(nbBoxes), Integer.parseInt(speedExec));
	        	requires().envGet().addGUI(observer);
	        	
	        	repriseButton.setEnabled(false);
	        	startButton.setEnabled(false);
	        	pauseButton.setEnabled(true);
	        	lowSpeedButton.setEnabled(true);
	        	resetButton.setEnabled(true);
	        	
	        	if (Integer.parseInt(speedExec) > 1) {
	        		highSpeedButton.setEnabled(true);
	        	} else {
	        		highSpeedButton.setEnabled(false);
	        	}

			}catch(NumberFormatException e){
				System.out.println("Mauvaise saisie");
			}

        }
        
        
	}
}
