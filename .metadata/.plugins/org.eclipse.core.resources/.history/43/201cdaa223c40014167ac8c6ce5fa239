package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSpinner;

import java.awt.Font;
import java.io.ObjectOutputStream;

import javax.swing.JScrollPane;

public class Window_dispatch extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191866542683478317L;
	private JFrame frmDispatchWindow;
	private JTextField txtFieldSupervisor;
	private JTextField txtClub;
	private JTextField txtAction;
	private JTextField txtTimeIn;
	private JSpinner spinner_field_supervisor;
	private JSpinner spinner_club;
	private JSpinner spinner_action;
	private JSpinner spinner_time_in;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window_dispatch window = new Window_dispatch();
					window.frmDispatchWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window_dispatch() {
		initialize();
	}

	public Window_dispatch(String clientName, ObjectOutputStream output_stream) {
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDispatchWindow = new JFrame();
		frmDispatchWindow.setTitle("Dispatch Window");
		frmDispatchWindow.setBounds(100, 100, 741, 565);
		frmDispatchWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDispatchWindow.getContentPane().setLayout(null);
		
		txtFieldSupervisor = new JTextField();
		txtFieldSupervisor.setText("Field Supervisor :");
		txtFieldSupervisor.setBounds(34, 81, 108, 20);
		frmDispatchWindow.getContentPane().add(txtFieldSupervisor);
		txtFieldSupervisor.setColumns(10);
		txtFieldSupervisor.setEditable(false);
		
		txtClub = new JTextField();
		txtClub.setText("Club                   :");
		txtClub.setBounds(34, 121, 108, 20);
		frmDispatchWindow.getContentPane().add(txtClub);
		txtClub.setColumns(10);
		txtClub.setEditable(false);
		
		txtAction = new JTextField();
		txtAction.setText("Action                :");
		txtAction.setBounds(34, 165, 108, 20);
		frmDispatchWindow.getContentPane().add(txtAction);
		txtAction.setColumns(10);
		txtAction.setEditable(false);
		
		txtTimeIn = new JTextField();
		txtTimeIn.setText("Time In              :");
		txtTimeIn.setBounds(34, 209, 108, 20);
		frmDispatchWindow.getContentPane().add(txtTimeIn);
		txtTimeIn.setColumns(10);
		txtTimeIn.setEditable(false);
		
		JButton btn_dispatch = new JButton("DISPATCH");
		btn_dispatch.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_dispatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btn_dispatch.setBounds(34, 255, 162, 106);
		frmDispatchWindow.getContentPane().add(btn_dispatch);
		
		spinner_field_supervisor = new JSpinner();
		spinner_field_supervisor.setBounds(152, 81, 93, 20);
		frmDispatchWindow.getContentPane().add(spinner_field_supervisor);
		
		spinner_club = new JSpinner();
		spinner_club.setBounds(152, 121, 93, 20);
		frmDispatchWindow.getContentPane().add(spinner_club);
		
		spinner_action = new JSpinner();
		spinner_action.setBounds(152, 165, 93, 20);
		frmDispatchWindow.getContentPane().add(spinner_action);
		
		spinner_time_in = new JSpinner();
		spinner_time_in.setBounds(152, 209, 93, 20);
		frmDispatchWindow.getContentPane().add(spinner_time_in);
		

	}
}
