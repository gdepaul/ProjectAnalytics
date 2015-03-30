package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import server.DispatchServer;
import server.ServerPanel_Histories.ClubListener;
import model.Club;
import model.dispatch.Dispatch;
import model.dispatch.DispatchAll;
import model.dispatch.DispatchFieldSupe;
import model.dispatch.InitialCashDrop;

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

		clubHistory = new JList<Dispatch<DispatchServer>>();
		clubHistory.setBounds(10, 45, 745, 615);
		add(clubHistory);
		
		JButton btnUpdateCommand = new JButton("Update Command");
		btnUpdateCommand.setBounds(605, 685, 150, 23);
		btnUpdateCommand.addActionListener(new AlterListener());
		add(btnUpdateCommand);
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
	}
	public void updateHistoryBox() {
		String clubName = (String)this.comboBox.getSelectedItem();
		System.err.println((String)this.comboBox.getSelectedItem());
		
		List<Dispatch<DispatchServer>> transactions = new ArrayList<Dispatch<DispatchServer>>();
		if(clubName != null) {
			for(Club c : clubs) {
				if(c.getClubName().equals(clubName))
					transactions = c.getTransactions();
			}
		}
		for (Dispatch<DispatchServer> d : transactions) {
			System.out.println(d);
		}
		System.out.println("------------------");
		
		remove(clubHistory);
		this.listModel = new DefaultListModel<Dispatch<DispatchServer>>();
		for (Dispatch<DispatchServer> d : transactions) {
			listModel.addElement(d);
		}
		clubHistory = new JList<Dispatch<DispatchServer>>(listModel);
		clubHistory.setBounds(10, 45, 745, 625);
		add(clubHistory);
	}
	public void UpdateLists(List<Club> clubs) {
		this.clubs = clubs;
		updateComboBox();
		updateHistoryBox();
		this.repaint();
	}
	private class AlterListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(clubHistory.getSelectedValue() == null)
				return;
			System.out.println("Selected Valued: " + clubHistory.getSelectedValue());
			System.out.println("Club: " + clubHistory.getSelectedValue().getClub());
			System.out.println("-----------------");
			int answer = JOptionPane.showConfirmDialog(null,"Do you want to undo this command?","WARNING", JOptionPane.YES_NO_OPTION);
			if(answer == JOptionPane.YES_OPTION) {
				//TODO: Send undo command through Output
				System.err.println("Admin wants to alter");
				Dispatch<DispatchServer> selectedTrans = clubHistory.getSelectedValue();
				System.out.println("SELECTED: " + selectedTrans);
				getNewValues(selectedTrans);
			}
			else if(answer == JOptionPane.NO_OPTION)
				System.err.println("Admin does not want to alter");
			else
				System.err.println("Some other value " + answer);
		}

		private void getNewValues(Dispatch<DispatchServer> selectedTrans) {
			String clubSelected = selectedTrans.getClub();
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
					tf_addWristbands };

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
						output.writeObject(new DispatchAll(clientName,
								clubSelected, addCashDrops, addChangeDrops,
								addFullSheets, addHalfSheets, addSingleTickets,
								addWristbands));
						if(selectedTrans instanceof DispatchAll)
							((DispatchAll)selectedTrans).undoDispatch();
						else if(selectedTrans instanceof InitialCashDrop)
							((InitialCashDrop)selectedTrans).undoDispatch();
						output.writeObject(selectedTrans);
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
	}
	public class ClubListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			updateHistoryBox();
			repaint();
		}		
	}
}
