import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.*;

public class DisplayDoily extends JPanel {
	private int sectorLineLength;
	private int centerX;
	private int centerY;
	private int noOfSectors;
	private double degreeRotation;
	private int currentPenSize;
	private Color currentPenColour;
	private boolean viewSectorLines;
	private boolean reflectDrawnPoints;
	private ArrayList<Stroke> drawnStrokes;
	
	public DisplayDoily(){
		super();
		init();
	}
	
	public void init() {
		this.setBackground(Color.BLACK);
		noOfSectors = 12;
		currentPenSize = 7;
		currentPenColour = Color.WHITE;
		viewSectorLines = true;
		reflectDrawnPoints = true;
		drawnStrokes = new ArrayList<Stroke>();
		repaint();
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(600, 600);
	}
              
   public void setSectorNo(int n){
	   noOfSectors = n;
	   repaint();
   }
   
   public void setCurrentPenSize(int size){
	   this.currentPenSize = size;
   }
   
   public void setCurrentPenColour(Color colour){
	   this.currentPenColour = colour;
   }
   
   public void setViewSectorLines(boolean b){
	   this.viewSectorLines = b;
	   repaint();
   }
   
   public void setReflectDrawnPoints(boolean b){
	   this.reflectDrawnPoints = b;
	   repaint();
   }
   
   public void addStroke(Stroke s){
	   s.setSize(currentPenSize);
	   s.setColour(currentPenColour);
	   drawnStrokes.add(s);
	   
   }
   
   public void clear(){
	   drawnStrokes.clear();
	   repaint(); 
   }
   
   public void undo(){
	   int lastElementIndex = drawnStrokes.size() - 1;
	   if (lastElementIndex >= 0){
		   drawnStrokes.remove(lastElementIndex);
		   repaint();
	   } 
   }
   
   public void updateStroke(Point p){
	   int lastElementIndex = drawnStrokes.size() - 1;
	   Stroke currentStroke = drawnStrokes.get(lastElementIndex);
	   currentStroke.addPoint(p);
	   this.repaint();
   }
   
   public void paintComponent(Graphics g) {
	 
	   super.paintComponent(g);
	   Graphics2D g2d = (Graphics2D) g;
	   g2d.setColor(Color.blue);
	  
		sectorLineLength = this.getHeight()/2;
		centerX = this.getWidth()/2;
		centerY = this.getHeight()/2;
		degreeRotation = 360.00/noOfSectors;
	
		if (viewSectorLines == true && noOfSectors>1){
			for (int i = 1; i<=noOfSectors;i++){
				g2d.drawLine(centerX, centerY, centerX, centerY+sectorLineLength);
				g2d.rotate(Math.toRadians(degreeRotation), centerX, centerY);
			}
		}
    
		for (Stroke s : drawnStrokes){
			Iterator<Point> pointItr = s.getPointIterator();
			int penSize = s.getSize();
			Color penColour = s.getColour();
			g2d.setColor(penColour);
			while (pointItr.hasNext()){
				Point currentPoint = pointItr.next();
				
				int x = (int) currentPoint.getX();
				int y = (int) currentPoint.getY();
				
				for (int i = 1; i<=noOfSectors;i++){
					g2d.fillOval(x, y, penSize, penSize);
					g2d.rotate(Math.toRadians(degreeRotation), centerX, centerY);
					if (reflectDrawnPoints == true && noOfSectors>1){
						g2d.fillOval(this.getWidth() - x, y, penSize, penSize);
					}
				}
			}
		}
   }
   
   // return the graphics which make up the doily as an Image object
   public Image returnSaveImage(){
	   BufferedImage img = new BufferedImage(600,600,BufferedImage.TYPE_INT_RGB);
	   Graphics2D imgGraphicsContext = img.createGraphics();
	   
	   imgGraphicsContext.setColor(Color.BLUE);
	   
	   if (viewSectorLines == true && noOfSectors>1){
			for (int i = 1; i<=noOfSectors;i++){
				imgGraphicsContext.drawLine(centerX, centerY, centerX, centerY+sectorLineLength);
				imgGraphicsContext.rotate(Math.toRadians(degreeRotation), centerX, centerY);
			}
		}
	   
		for (Stroke s : drawnStrokes){
			Iterator<Point> pointItr = s.getPointIterator();
			int penSize = s.getSize();
			Color penColour = s.getColour();
			imgGraphicsContext.setColor(penColour);
			while (pointItr.hasNext()){
				Point currentPoint = pointItr.next();
				
				int x = (int) currentPoint.getX();
				int y = (int) currentPoint.getY();
				
				for (int i = 1; i<=noOfSectors;i++){
					imgGraphicsContext.fillOval(x, y, penSize, penSize);
					imgGraphicsContext.rotate(Math.toRadians(degreeRotation), centerX, centerY);
					if (reflectDrawnPoints == true && noOfSectors>1){
						imgGraphicsContext.fillOval(this.getWidth() - x, y, penSize, penSize);
					}
				}
			}
		}
		
		Image resizedImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		return resizedImg;
   }
}