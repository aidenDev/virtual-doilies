import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JButton;

public class GalleryJButton extends JButton {
	
	// constructor
	public GalleryJButton(){
		super();
		init();
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(100, 100);
	}
	
	public GalleryJButton(Icon icon){
		super();
		this.setBackground(Color.WHITE);
		this.setIcon(icon);
	}

	private void init(){
	}

}