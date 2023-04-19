import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class arrowButton extends JButton{
	
	ImageIcon icon;
	
	arrowButton(){
		icon = new ImageIcon("resources\\images\\arrow2.png");
		super.setIcon(icon);
	}
	
	public arrowButton(ImageIcon icon){
		this.icon=new ImageIcon("resources\\images\\arrow2.png");
		
	}

	void buttonlistener()
	    {
	        addMouseListener(new MouseAdapter() {

	            @Override
	            public void mouseEntered(MouseEvent me) {
	                setIcon(new ImageIcon("resources\\images\\arrow.png"));
	            }
	            public void mouseExited(MouseEvent me){
	                setIcon(new ImageIcon("resources\\images\\arrow2.png"));
	            }
	        });
	    }
}
