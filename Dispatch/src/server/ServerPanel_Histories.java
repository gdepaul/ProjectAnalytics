package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListModel;

import model.Club;
import model.dispatch.Dispatch;

import javax.swing.JButton;

public class ServerPanel_Histories extends JPanel {
	private HashMap<String, Club> clubs;
	private ObjectOutputStream output;
//--GUI Variables
	JComboBox<String> comboBox;
	JList<Dispatch<DispatchServer>> clubHistory;
	DefaultListModel<Dispatch<DispatchServer>> listModel;
	

	
	public ServerPanel_Histories(HashMap<String, Club> clubs, Map<String, Deque<Dispatch<DispatchServer>>> histories, ObjectOutputStream out) {
		this.clubs = clubs;
		this.output = output;
		
		this.setSize(800, 800);
		setLayout(null);
		
		this.comboBox = new JComboBox<String>();
		//this.comboBox = new JComboBox<String>();
		comboBox.setBounds(599, 11, 172, 23);
		comboBox.addActionListener(new ClubListener());
		add(comboBox);
		for(String clubName : clubs.keySet()) {
			comboBox.addItem(clubName);
		}
		this.listModel = new DefaultListModel<Dispatch<DispatchServer>>();

		clubHistory = new JList<Dispatch<DispatchServer>>();
		clubHistory.setBounds(10, 45, 761, 650);
		add(clubHistory);
		
		JButton btnUpdateCommand = new JButton("Update Command");
		btnUpdateCommand.setBounds(647, 702, 124, 23);
		btnUpdateCommand.addActionListener(new AlterListener());
		add(btnUpdateCommand);
	}
	private void updateComboBox() {
		remove(comboBox);
		this.comboBox = new JComboBox<String>();
		//this.comboBox = new JComboBox<String>();
		comboBox.setBounds(599, 11, 172, 23);
		comboBox.addActionListener(new ClubListener());
		add(comboBox);
		for(String clubName : clubs.keySet()) {
			comboBox.addItem(clubName);
		}
		add(comboBox);
	}
	public void updateHistoryBox() {
		String clubName = (String)this.comboBox.getSelectedItem();
		List<Dispatch<DispatchServer>> transactions = new ArrayList<Dispatch<DispatchServer>>();
		if(clubName != null) {
			transactions = clubs.get(clubName).getTransactions();
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
		clubHistory.setBounds(10, 45, 761, 650);
		add(clubHistory);
	}
	public void updatePanel(HashMap<String, Club> clubs, Map<String, Deque<Dispatch<DispatchServer>>> histories) {
		this.clubs = clubs;
		updateComboBox();
		updateHistoryBox();
		this.repaint();
	}
	public class ClubListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			// TODO Update Based on Club Selected
		}		
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
			}
			else if(answer == JOptionPane.NO_OPTION)
				System.err.println("Admin does not want to alter");
			else
				System.err.println("Some other value " + answer);
		}		
	}
}