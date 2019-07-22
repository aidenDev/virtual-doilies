import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MyFrame extends JFrame {
	// panels
	JPanel displayPanel;
	JPanel controlPanel;
	JPanel galleryPanel;
	JPanel penSliderPanel;
	JPanel sectorSliderPanel;
	JPanel checkBoxPanel;
	
	// components
	JButton clearDisplayBtn;
	JButton changeColourBtn;
	JSlider penSizeSlider;
	JSlider sectorSlider;
	JComboBox<String> coloursComboBox;
	JCheckBox sectorLinesCheckBox;
	JCheckBox reflectCheckBox;
	JButton undoBtn;
	JButton saveBtn;
	
	// constants
	private final static String[] colourLabels = { "WHITE", "BLUE", "GRAY", "GREEN", "MAGENTA", "ORANGE", "PINK", "RED", "YELLOW"};
	
	// doily instance
	DisplayDoily myDoily;
	
    public MyFrame(){
    	super();
    	init();
    }
	
	public void init(){
		// window initialisation
		JFrame window = new JFrame("Digital Doilies");
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		
		// Panel initialisation
        displayPanel = new JPanel();
		controlPanel = new JPanel();
		galleryPanel = new Gallery();
			
		controlPanel.setLayout(new GridLayout(1,4));
		displayPanel.setBackground(Color.BLACK);
		controlPanel.setBackground(Color.LIGHT_GRAY);
		
		// clear display button
		clearDisplayBtn = new JButton("Clear display");
		clearDisplayBtn.addActionListener(new clearDisplayBtnHandler());
		controlPanel.add(clearDisplayBtn);
		
		// colour selection combobox
		coloursComboBox = new JComboBox<String>(colourLabels);
		coloursComboBox.addActionListener(new coloursComboBoxHandler());
		controlPanel.add(coloursComboBox);
		
		// I could have create a subclass of JSlider, however I decided not to (as I was only utilising the component twice)
		
		// pen size slider and label (in a JPanel)
		penSliderPanel = new JPanel();
		penSliderPanel.setBackground(Color.LIGHT_GRAY);
		penSliderPanel.setLayout(new BorderLayout());
		
		penSizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 7);
		penSizeSlider.setMajorTickSpacing(10);
		penSizeSlider.setPaintTicks(true);
		penSizeSlider.setPaintLabels(true);
		penSizeSlider.addChangeListener(new penSizeSliderHandler());
		penSliderPanel.add(penSizeSlider, BorderLayout.CENTER);
		penSliderPanel.add(new JLabel("Pen size:", JLabel.CENTER), BorderLayout.NORTH);
		
		controlPanel.add(penSliderPanel);
	
		// sector number slider and label (in a JPanel)
		sectorSliderPanel = new JPanel();
		sectorSliderPanel.setBackground(Color.LIGHT_GRAY);
		sectorSliderPanel.setLayout(new BorderLayout());
		
		sectorSlider = new JSlider(JSlider.HORIZONTAL, 1, 101, 12);
		sectorSlider.setMajorTickSpacing(20);
		sectorSlider.setPaintTicks(true);
		sectorSlider.setPaintLabels(true);
		sectorSlider.addChangeListener(new sectorSliderHandler());
		sectorSliderPanel.add(sectorSlider, BorderLayout.CENTER);
		sectorSliderPanel.add(new JLabel("No of sectors:", JLabel.CENTER), BorderLayout.NORTH);
		
		controlPanel.add(sectorSliderPanel);
		
		// reflect and show sector lines check boxes (in a JPanel)
		checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new GridLayout(2,1));
		checkBoxPanel.setBackground(Color.LIGHT_GRAY);
		
		sectorLinesCheckBox = new JCheckBox("Show sector lines");
		sectorLinesCheckBox.setSelected(true);
		sectorLinesCheckBox.addActionListener(new sectorLinesCBHandler());
		checkBoxPanel.add(sectorLinesCheckBox);
		
		reflectCheckBox = new JCheckBox("Reflect drawn points");
		reflectCheckBox.setSelected(true);
		reflectCheckBox.addActionListener(new reflectCBHandler());
		checkBoxPanel.add(reflectCheckBox);
		
		controlPanel.add(checkBoxPanel);
		
		// undo JButton
		undoBtn = new JButton("Undo drawn point");
		undoBtn.addActionListener(new undoBtnHandler());
		controlPanel.add(undoBtn);
		
		// save JButton
		saveBtn = new JButton("Save doily to gallery!");
		saveBtn.addActionListener(new saveBtnHandler());
		controlPanel.add(saveBtn);
	
		// display panel
		myDoily = new DisplayDoily();
		displayPanel.add(myDoily);
		myDoily.addMouseListener(new mouseHandler()); 
		myDoily.addMouseMotionListener(new mouseHandler());
		
		// content pane construction
		Container contentPane = window.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(displayPanel, BorderLayout.CENTER);
		contentPane.add(controlPanel, BorderLayout.SOUTH);
		contentPane.add(galleryPanel, BorderLayout.EAST);
		
		// set JFrame to full screen and visible
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
       	window.setVisible(true);
	}    	
    
	// Event handlers
	class sectorSliderHandler implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int value = sectorSlider.getValue();
			myDoily.setSectorNo(value);
		}
	}
	
	class penSizeSliderHandler implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int value = penSizeSlider.getValue();
			myDoily.setCurrentPenSize(value);
		}
	}
	
	class mouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			Stroke currentStroke = new Stroke();
			myDoily.addStroke(currentStroke);
	    }
		
	    public void mouseDragged(MouseEvent e) {
	        int x = e.getX();
	        int y = e.getY(); 
	        myDoily.updateStroke(new Point(x, y));
	    }
	}
	
	class clearDisplayBtnHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			myDoily.clear();
			
		}
	}
	
	class undoBtnHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			myDoily.undo();
		}
	}

	class coloursComboBoxHandler implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	    	Color[] colours = { Color.WHITE, Color.BLUE, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW };
	        JComboBox<String> coloursCB = (JComboBox<String>) e.getSource();
	        int selectedIndex = coloursCB.getSelectedIndex();
	        myDoily.setCurrentPenColour(colours[selectedIndex]);
	    }
	}

	class sectorLinesCBHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
	        JCheckBox abstractButton = (JCheckBox) e.getSource();
	        boolean selected = abstractButton.getModel().isSelected();
	        myDoily.setViewSectorLines(selected);
		}
	}
	
	class reflectCBHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
	        AbstractButton abstractButton = (AbstractButton) e.getSource();
	        boolean selected = abstractButton.getModel().isSelected();
	        myDoily.setReflectDrawnPoints(selected);
		}
	}
	
	class saveBtnHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Image img = myDoily.returnSaveImage();
			((Gallery) galleryPanel).addDoily(img);
		}
	}
	
	class galleryBtnHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		}
	}
}
