package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class DispatchPanel extends JPanel{
	private static final long serialVersionUID = 8568633961499977471L;

	public  JPanel DispatchWindow;
	private JTextField txtFieldSupervisor;
	private JTextField txtClub;
	private JTextField txtAction;
	private JTextField txtTimeIn;
	private JSpinner spinner_field_supervisor;
	private JSpinner spinner_club;
	private JSpinner spinner_action;
	private JSpinner spinner_time_in;
	private String clientName;
	private ObjectOutputStream output;
	
	/**
	 *	ButtonListener
	 *
	 *	Used to choose the type of object to be drawn
	 */
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 *	UndoListener
	 * 
	 *	Writes an UndoLastCommand to the server when the undo button is clicked
	 */
	private class UndoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	/**
	 * DispatchPanel constructor
	 * 
	 * @param name		the client's name
	 * @param output	the output stream to the server
	 */
	public DispatchPanel(String name, ObjectOutputStream output) {
		clientName = name;
		this.output = output;
		
		
		this.setLayout(new BorderLayout());
		
		// create button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		// make a listener for the buttons
		ButtonListener buttonListener = new ButtonListener();
		
		// make a bunch of buttons
		JButton button_dispatch = new JButton("DISPATCH");
		
		// make button group, set default selection
		ButtonGroup group = new ButtonGroup();
		group.add(button_dispatch);
		
		// make the undo button, add listener
		JButton undo = new JButton("Undo");
		undo.addActionListener(new UndoListener());
		
		this.setVisible(true);
	}
	
	/**
	 * This method is called by a UpdateClientCommand executed on
	 * a NetpaintClient
	 * 
	 * @param objects	the PaintObjects in the world
	 */
//	public void update(List<PaintObject> objects){
//		this.objects = objects;
//		repaint();
//	}
}

