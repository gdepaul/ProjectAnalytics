package View;

import javax.swing.JPanel;

public class Panel_CICO extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3778947721922456207L;

	public Panel_CICO() {
	}
	
	/**
	 * This method is called by a UpdateClientCommand executed on
	 * a Client
	 * 
	 * @param objects	the PaintObjects in the world
	 */
	public void update(){
		repaint();
	}

}
