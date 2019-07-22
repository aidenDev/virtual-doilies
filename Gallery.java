import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gallery extends JPanel{
	int maxItems;
	int nextFreeIndex;
	int jumpBackTo;
	GalleryJButton[] buttons;
	
	public Gallery(){
		super();
		init();
	}
	
	private void init(){
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new GridLayout(6,2));
		
		maxItems = 12;
		nextFreeIndex = -1;
		buttons = new GalleryJButton[maxItems];
		populateGallery();
	}
	
	// add the buttons to the array
	private void populateGallery(){
		for (int i = 0; i < maxItems; i++){
			GalleryJButton button = new GalleryJButton();
			button.addActionListener(new galleryBtnHandler());
			buttons[i] = button;
			this.add(buttons[i]);
		}
	}
	
	// getter and setter methods
	public void addDoily(Image img){
		Integer potentialAvailableIndex = getIndexOfGap();
		if (potentialAvailableIndex != null){
			jumpBackTo = nextFreeIndex;
			nextFreeIndex = potentialAvailableIndex;
			addItemToArray(img);
			nextFreeIndex = jumpBackTo;
			if (nextFreeIndex > maxItems){
				nextFreeIndex = 0;
			}
			
		} else{
			nextFreeIndex++;
			if (nextFreeIndex > maxItems-1){
				nextFreeIndex = 0;
			}
		}
	}
	
	public void removeDoily(int index){
		buttons[index] = null;
	}
	
	private void addItemToArray(Image img){
		ImageIcon icon = new ImageIcon(img);
		buttons[nextFreeIndex].setIcon(icon);
	}
	
	private void clearIcon(GalleryJButton component){
		component.setIcon(null);
	}
	
	private Integer getIndexOfGap(){
		for (int i=0; i<maxItems;i++){
			if (buttons[i].getIcon() == null){
				return i;
			}
		}
		return null;
	}
	
	// event handling for deleting gallery items
	class galleryBtnHandler implements ActionListener {
		@Override
		// when a button is clicked delete the image it's representing, from the gallery.
		public void actionPerformed(ActionEvent e) {
			GalleryJButton source = (GalleryJButton) e.getSource();
			clearIcon(source);
		}
	}
}
