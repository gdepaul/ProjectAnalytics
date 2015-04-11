package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import server.DispatchServer;
import server.Serializer;
import model.Booth;
import model.Club;
import model.dispatch.Dispatch;
import model.dispatch.DispatchAll;
import model.dispatch.ExportCommand;
import model.dispatch.InitialCashDrop;
import model.dispatch.SaveCommand;

public class Panel_ADMIN extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8158953088490839171L;
//--Backend Variables
	private ObjectOutputStream output;
	private List<Club> clubs;
	private String clientName = "ADMIN";
	
//--GUI Variables
	JComboBox<String> comboBox;
	JList<Dispatch<DispatchServer>> clubHistory;
	DefaultListModel<Dispatch<DispatchServer>> listModel;
	JScrollPane scroll;
	JButton btnUpdateCommand;
	JButton save;
	JButton export;
	public Panel_ADMIN(String userName, ObjectOutputStream out, List<Club> activeClubs, List<String> availableFS, List<String> dispatchedFS) {
		this.clubs = activeClubs;
		this.output = out;
		
		this.setSize(800, 800);
		setLayout(null);
		
		this.comboBox = new JComboBox<String>();
		//this.comboBox = new JComboBox<String>();
		comboBox.setBounds(575, 11, 172, 23);
		comboBox.addActionListener(new ClubListener());
		add(comboBox);
		if(clubs != null) {
			for (Club club : clubs) {
				comboBox.addItem(club.getClubName());
			}
		}
		
		this.listModel = new DefaultListModel<Dispatch<DispatchServer>>();		
		clubHistory = new JList<Dispatch<DispatchServer>>(listModel);
		scroll = new JScrollPane(clubHistory);
		scroll.setBounds(10,45,745,615);
		add(scroll);	

		btnUpdateCommand = new JButton("Update Command");
		btnUpdateCommand.setBounds(605, 685, 150, 23);
		btnUpdateCommand.addActionListener(new AlterListener());
		add(btnUpdateCommand);
		
		save = new JButton("Save State");
		save.setBounds(10, 11, 150, 23);
		save.addActionListener(new SaveListener());
		add(save);
		
		export = new JButton("Export State");
		export.setBounds(170, 11, 150, 23);
		export.addActionListener(new ExportListener());
		add(export);
	}
	private void updateComboBox() {
		remove(comboBox);
		this.comboBox = new JComboBox<String>();
		//this.comboBox = new JComboBox<String>();
		comboBox.setBounds(575, 11, 172, 23);
		comboBox.addActionListener(new ClubListener());
		add(comboBox);
		for(Club club : clubs) {
			comboBox.addItem(club.getClubName());
		}
		add(comboBox);
		repaint();
	}
	//TODO
	public void updateHistoryBox() {
		this.removeAll();
		String clubName = (String)this.comboBox.getSelectedItem();
		//System.err.println((String)this.comboBox.getSelectedItem());
		
		List<Dispatch<DispatchServer>> transactions = new ArrayList<Dispatch<DispatchServer>>();
		
		if(clubName != null) {
			for(Club c : clubs) {
				if(c.getClubName().equals(clubName))
					transactions = c.getTransactions();
			}
		}	
		listModel = new DefaultListModel<Dispatch<DispatchServer>>();
		for (Dispatch<DispatchServer> d : transactions) {
			listModel.addElement(d);
			System.err.println(d);
		}

		JList<Dispatch<DispatchServer>> clubH = new JList<Dispatch<DispatchServer>>(listModel);
		scroll = new JScrollPane(clubH);
		scroll.setBounds(10,45,745,615);
		add(scroll); 	
		add(this.comboBox);
		add(btnUpdateCommand);
		add(export);
		add(save);
		this.repaint();
	}
	public void UpdateLists(List<Club> clubs) {
		this.clubs = clubs;
		updateComboBox();
		updateHistoryBox();
		this.repaint();
	}
	private class AlterListener implements ActionListener {
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0) {
			if(((JList<Dispatch<DispatchServer>>)scroll.getViewport().getView()).getSelectedValue() == null)
				return;
			int answer = JOptionPane.showConfirmDialog(null,"Do you want to undo this command?","WARNING", JOptionPane.YES_NO_OPTION);
			if(answer == JOptionPane.YES_OPTION) {
				//TODO: Send undo command through Output
				System.err.println("Admin wants to alter");
				Dispatch<DispatchServer> selectedTrans = ((Dispatch<DispatchServer>)((JList)scroll.getViewport().getView()).getSelectedValue());
				System.out.println("SELECTED: " + selectedTrans);
				getNewValues(selectedTrans);
			}
			else if(answer == JOptionPane.NO_OPTION)
				System.err.println("Admin does not want to alter");
			else
				System.err.println("Some other value " + answer);
		}
		private void undoDispatch(DispatchAll dispatch) {
			String clubSelected = dispatch.getClub();

			int addCashDrops = 0;
			int addChangeDrops = 0;
			int addFullSheets = 0;
			int addHalfSheets = 0;
			int addSingleTickets = 0;
			int addWristbands = 0;
			
			JTextField tf_cashDrops = new JTextField();
			tf_cashDrops.setText("0");
			JTextField tf_changeDrops = new JTextField();
			tf_changeDrops.setText("0");
			JTextField tf_addFullSheets = new JTextField();
			tf_addFullSheets.setText("0");
			JTextField tf_addHalfSheets = new JTextField();
			tf_addHalfSheets.setText("0");
			JTextField tf_addSingletickets = new JTextField();
			tf_addSingletickets.setText("0");
			JTextField tf_addWristbands = new JTextField();
			tf_addWristbands.setText("0");
			Object[] getDispatch = { "Cash Drops:", tf_cashDrops,
					"Change Drops:", tf_changeDrops, "Full Sheets:",
					tf_addFullSheets, "Half Sheets:", tf_addHalfSheets,
					"Single Tickets:", tf_addSingletickets, "Wristbands:",
					tf_addWristbands};

			int option = JOptionPane.showConfirmDialog(getParent(),
					getDispatch, "Enter all dispatch values",
					JOptionPane.OK_CANCEL_OPTION);

			if (option == JOptionPane.OK_OPTION) {
				
				try {
					addCashDrops = Integer.parseInt(tf_cashDrops.getText());
					addChangeDrops = Integer.parseInt(tf_changeDrops.getText());
					addFullSheets = Integer
							.parseInt(tf_addFullSheets.getText());
					addHalfSheets = Integer
							.parseInt(tf_addHalfSheets.getText());
					addSingleTickets = Integer.parseInt(tf_addSingletickets
							.getText());
					addWristbands = Integer
							.parseInt(tf_addWristbands.getText());
					if (addCashDrops >= 0 && addChangeDrops >= 0
							&& addFullSheets >= 0 && addHalfSheets >= 0
							&& addSingleTickets >= 0 && addWristbands >= 0) {
						JOptionPane.showMessageDialog(getParent(),
								"Updating "
										+ clubSelected + "!\n" + "Cash Drops: "
										+ addCashDrops + "\n"
										+ "Change Drops: " + addChangeDrops
										+ "\n" + "Full Sheets: "
										+ addFullSheets + "\n"
										+ "Half Sheets: " + addHalfSheets
										+ "\n" + "Single Tickets: "
										+ addSingleTickets + "\n"
										+ "Wristbands: " + addWristbands);

						// Execute
						
							((DispatchAll)dispatch).undoDispatch();
						output.writeObject(new DispatchAll(clientName,clubSelected, addCashDrops, addChangeDrops,addFullSheets, addHalfSheets, addSingleTickets,addWristbands));
						System.out.println(dispatch);
						output.writeObject(dispatch);
					} else {
						JOptionPane.showMessageDialog(getParent(),
								"Negative values not permitted.)");
					}

				} catch (NumberFormatException e) {
					JOptionPane
							.showMessageDialog(getParent(),
									"Enter valid number values please!\n(Number Format Exception)");
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(getParent(),
							"IO Exception");
					e.printStackTrace();
				}
			}
		}
		private void undoDispatch(InitialCashDrop initial) {
			String clubSelected = initial.getClub();
			System.err.println("INITIAL CASH DROP: NOT IMPLEMENTED");
			float initialCash = 0.0f;
			int initialTickets = 0;
			int initialWristbands = 0;
			String locale = "";
			JTextField cash = new JTextField();
			cash.setText("0");
			JTextField tix = new JTextField();
			tix.setText("0");
			JTextField wrist = new JTextField();
			wrist.setText("0");
			
			JComboBox<String> location = new JComboBox<String>();
			location.addItem("Castle 1");
			location.addItem("Castle 2");
			location.addItem("Castle 3");
			location.addItem("Castle 4");
			location.addItem("Cottage 1");
			location.addItem("Cottage 2");
			location.addItem("Cottage 3");
			location.addItem("Cottage 4");
			location.addItem("Wonderland");
			location.addItem("Wishing Well");
			location.addItem("Pride Rock");
			location.addItem("Palace");
			location.addItem("Enchanted Forest");
			location.addItem("Swamp");
			location.addItem("Woods");
			location.addItem("Beanstalk");
			
			Object[] getDispatch = { "Initial Cash:", cash, "Intial Tickets:", tix, "Initial Wristbands", wrist, "Location:",location };

			int option = JOptionPane.showConfirmDialog(getParent(), getDispatch, "Enter new Initial Cash Drop values", JOptionPane.OK_CANCEL_OPTION);

			if (option == JOptionPane.OK_OPTION) {
				
				try {
					initialCash = Float.parseFloat(cash.getText()) - 0.000f;
					initialTickets = Integer.parseInt(tix.getText());
					initialWristbands = Integer.parseInt(wrist.getText());
					locale = (String)location.getSelectedItem();
										
					if (initialCash >= 0 && initialTickets >= 0 && initialWristbands >= 0) {
						initial.undoDispatch();
						output.writeObject(new InitialCashDrop(clientName,clubSelected,initialCash,initialTickets,initialWristbands));
						output.writeObject(initial);
					} else {
						JOptionPane.showMessageDialog(getParent(),
								"Negative values not permitted.)");
					}

				} catch (NumberFormatException e) {
					JOptionPane
							.showMessageDialog(getParent(),
									"Enter valid number values please!\n(Number Format Exception)");
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(getParent(),
							"IO Exception");
					e.printStackTrace();
				}
			}

		}
		private void getNewValues(Dispatch<DispatchServer> selectedTrans) {
			if(selectedTrans instanceof DispatchAll) {
				undoDispatch((DispatchAll)selectedTrans);
			}
			else if(selectedTrans instanceof InitialCashDrop) {
				undoDispatch((InitialCashDrop)selectedTrans);
			}
		}
	}
	public class ClubListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("CLUB BOX ACTIVATED");
			updateHistoryBox();
			repaint();
		}		
	}
	public class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				output.writeObject(new SaveCommand(clientName));
				repaint();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public class ExportListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				output.writeObject(new ExportCommand(clientName));
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
