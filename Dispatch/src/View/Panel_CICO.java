package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Club;
import model.dispatch.CheckOutDispatch;
import model.dispatch.DispatchAll;
import model.dispatch.InitialCashDrop;
import model.dispatch.RemoveClub;

public class Panel_CICO extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3778947721922456207L;
	private String clientName;
	private ObjectOutputStream output;
	private List<Club> activeClubs;
	private List<String> availableFS;
	private	List<String> dispatchedFS;
	
	static MyOwnFocusTraversalPolicy newPolicy;
	
	//WIDGETS
	private JTextField textField_penniesIn;
	private JTextField textField_nickelsIn;
	private JTextField textField_dimesIn;
	private JTextField textField_quartersIn;
	private JTextField textField_dollarsIn;
	private JTextField textField_twosIn;
	private JTextField textField_fivesIn;
	private JTextField textField_tensIn;
	private JTextField textField_twentiesIn;
	private JTextField textField_fiftiesIn;
	private JTextField textField_hundredsIn;
	private JTextField textField_penniesOut;
	private JTextField textField_nickelsOut;
	private JTextField textField_dimesOut;
	private JTextField textField_quartersOut;
	private JTextField textField_dollarsOut;
	private JTextField textField_twosOut;
	private JTextField textField_fivesOut;
	private JTextField textField_tensOut;
	private JTextField textField_twentiesOut;
	private JTextField textField_fiftiesOut;
	private JTextField textField_hundredsOut;
	
	private JTextArea textArea_cashDrops;
	private JTextArea textArea_startTotal;		//left hand start total on GUI
	private JTextArea textArea_startTotal2;		//right hand start total on GUI
	private JTextArea textArea_endTotal;
	private JTextArea textArea_endTotalCalc;
	private JTextArea textArea_cashDropsBy800;
	private JTextArea textArea_finalTotal;
	private JTextArea textArea_penniesIn;
	private JTextArea textArea_nickelsIn;
	private JTextArea textArea_dimesIn;
	private JTextArea textArea_quartersIn;
	private JTextArea textArea_dollarsIn;
	private JTextArea textArea_twosIn;
	private JTextArea textArea_fivesIn;
	private JTextArea textArea_tensIn;
	private JTextArea textArea_twentiesIn;
	private JTextArea textArea_fiftiesIn;
	private JTextArea textArea_hundredsIn;
	private JTextArea textArea_penniesOut;
	private JTextArea textArea_nickelsOut;
	private JTextArea textArea_dimesOut;
	private JTextArea textArea_quartersOut;
	private JTextArea textArea_dollarsOut;
	private JTextArea textArea_twosOut;
	private JTextArea textArea_fivesOut;
	private JTextArea textArea_tensOut;
	private JTextArea textArea_twentiesOut;
	private JTextArea textArea_fiftiesOut;
	private JTextArea textArea_hundredsOut;
	private JTextArea textArea_issuedTickets;
	private JTextArea textArea_issuedWristbands;
	private JTextArea textArea_expectedRevenue;
	private JTextArea textArea_soldWristbands;
	private JTextArea textArea_soldTickets;
	
	// Ticket Widgets
	private JTextField textField_fullSheetsUnsold;
	private JTextField textField_halfSheetsUnsold;
	private JTextField textField_singleTicketsUnsold;
	private JTextField textField_wristbandsUnsold;
	private JTextField textField_fullSheetsIn;
	private JTextField textField_halfSheetsIn;
	private JTextField textField_singleTicketsIn;
	private JTextField textField_wristbandsIn;
	private JTextArea textArea_initialtickets;
	private JTextArea textArea_unsoldTickets;
	private JTextArea textArea_ticketSales;
	private JTextArea textArea_initialWristbands;
	private JTextArea textArea_unsoldWristbands;
	private JTextArea textArea_wristbandSales;
	private JTextArea textArea_fullSheetsOut;
	private JTextArea textArea_halfSheetsOut;
	private JTextArea textArea_singleTicketsOut;
	private JTextArea textArea_wristbandsOut;
	
	//private JSpinner spinner_clubSelection;
	private JSpinner spinner_clubSelection_1;
	
	// Wristband multiplier spinner
	private JSpinner spinner_wbValue;
	private int wristbandMultiplier;
	
	// Location spinner
	private JSpinner spinner_locations;
	private String location;
	
	private JTextArea textArea_expectedEndingCash;
	private JTextField textField_credits;
	private JTextArea textArea_differences;
	
	private JButton btn_initialCashDrop;
	
	private JButton btn_drops;
	
	//Listeners
	private StartValsChangedListener startValsChangedListener;
	private EndValsChangedListener endValsChangedListener;
	private ConfirmInitialDropListener confirmInitialDropListener;
	private WristbandMultiplierListener wristbandMultiplierListener;
	private LocationSpinnerListener locationSpinnerListener;
	private ClubSpinnerListener clubSpinnerListener;
	private ConfirmCheckOutCashierListener confirmCheckOutCashierListener;
	
	//Initial values
	private String clubSelected;
	private Club actualClub;
	
	/**
	 *	DispatchButton Listener
	 *
	 */
	private class DispatchListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
				
				// Process actionSelected on Club
				if (clubSelected.compareTo("")!=0){

						int addCashDrops=0;
						int addChangeDrops=0;
						int addFullSheets=0;
						int addHalfSheets=0;
						int addSingleTickets=0;
						int addWristbands=0;
						
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
						
						Object[] getDispatch = {
								"Cash Drops:", tf_cashDrops,
								"Change Drops:", tf_changeDrops,
								"Full Sheets:", tf_addFullSheets,
								"Half Sheets:", tf_addHalfSheets,
								"Single Tickets:", tf_addSingletickets,
								"Wristbands:", tf_addWristbands
								};
						
						int option = JOptionPane.showConfirmDialog(getParent(), getDispatch, "Enter all dispatch values", JOptionPane.OK_CANCEL_OPTION);
						
						if (option==JOptionPane.OK_OPTION){
							try{
							addCashDrops = Integer.parseInt(tf_cashDrops.getText());
							addChangeDrops = Integer.parseInt(tf_changeDrops.getText());
							addFullSheets = Integer.parseInt(tf_addFullSheets.getText());
							addHalfSheets = Integer.parseInt(tf_addHalfSheets.getText());
							addSingleTickets = Integer.parseInt(tf_addSingletickets.getText());
							addWristbands = Integer.parseInt(tf_addWristbands.getText());
							if ( addCashDrops>=0 && 
									addChangeDrops>=0 &&
									addFullSheets>=0 &&
									addHalfSheets>=0 &&
									addSingleTickets>=0 &&
									addWristbands>=0){
								JOptionPane.showMessageDialog(getParent(), "Adding Cash Drop Ticket/ Field Support Form values" +
																			"to " + clubSelected + "!\n" + 
									"Cash Drops: " + addCashDrops + "\n" +
									"Change Drops: " + addChangeDrops + "\n" +
									"Full Sheets: " + addFullSheets + "\n" +
									"Half Sheets: " + addHalfSheets + "\n" +
									"Single Tickets: " + addSingleTickets + "\n" +
									"Wristbands: " + addWristbands);
								
								//Execute
								output.writeObject(new DispatchAll(clientName, clubSelected, addCashDrops, addChangeDrops, addFullSheets, addHalfSheets, addSingleTickets, addWristbands));
								//Dispatch field supe
							//	output.writeObject(new DispatchFieldSupe(clientName, DFSSelected));
								
							}else{
								JOptionPane.showMessageDialog(getParent(), "Negative values not permitted.");
							}
							
							} catch (NumberFormatException e){
								JOptionPane.showMessageDialog(getParent(), "Enter valid number values please!\n(Number Format Exception)");
								e.printStackTrace();
							} catch (Exception e) {
								JOptionPane.showMessageDialog(getParent(), "IO Exception Line 265");
								e.printStackTrace();
							}
						}
					
				}
			
		}
	}
	/**
	 *  Listener for the cashier checkout button
	 */
	public class ConfirmCheckOutCashierListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// First, everything that's in end vals listener. I'll clean this up later, put it all in a method. For now,
			// tons of redundant code.
			
			float endTotal = 0;
			
			//Let's start adding this up...
			 	if(textField_penniesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_penniesOut.setText("$0.00");
			 	}else{
					try { 
				        float penniesValue = (float) (Float.parseFloat(textField_penniesOut.getText())*.01); 
				        endTotal += penniesValue;
				        textArea_penniesOut.setText("$" +formatDecimal(penniesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of pennies!");
				    }
			 	}
			 	
			 	if(textField_nickelsOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_nickelsOut.setText("$0.00");
			 	}else{
					try { 
				        float nickelsValue = (float) (Float.parseFloat(textField_nickelsOut.getText())*.05); 
				        endTotal += nickelsValue;
				        textArea_nickelsOut.setText("$" +formatDecimal(nickelsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of nickels!");
				    }
			 	}
			 	
			 	if(textField_dimesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_dimesOut.setText("$0.00");
			 	}else{
					try { 
				        float dimesValue = (float) (Float.parseFloat(textField_dimesOut.getText())*.10); 
				        endTotal += dimesValue;
				        textArea_dimesOut.setText("$" +formatDecimal(dimesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of dimes!");
				    }
			 	}
			 	
			 	if(textField_quartersOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_quartersOut.setText("$0.00");
			 	}else{
					try { 
				        float quartersValue = (float) (Float.parseFloat(textField_quartersOut.getText())*.25); 
				        endTotal += quartersValue;
				        textArea_quartersOut.setText("$" +formatDecimal(quartersValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of quarters!");
				    }
			 	}
			 	
			 	if(textField_dollarsOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_dollarsOut.setText("$0.00");
			 	}else{
					try { 
				        float dollarsValue = (float) (Float.parseFloat(textField_dollarsOut.getText())*1.00); 
				        endTotal += dollarsValue;
				        textArea_dollarsOut.setText("$" +formatDecimal(dollarsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of single dollars!");
				    }
			 	}
			 	
			 	if(textField_twosOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_twosOut.setText("$0.00");
			 	}else{
					try { 
				        float twosValue = (float) (Float.parseFloat(textField_twosOut.getText())*2.00); 
				        endTotal += twosValue;
				        textArea_twosOut.setText("$" +formatDecimal(twosValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of twos!");
				    }
			 	}
			 	
			 	if(textField_fivesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_fivesOut.setText("$0.00");
			 	}else{
					try { 
				        float fivesValue = (float) (Float.parseFloat(textField_fivesOut.getText())*5.00); 
				        endTotal += fivesValue;
				        textArea_fivesOut.setText("$" +formatDecimal(fivesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of fives!");
				    }
			 	}
			 	
			 	if(textField_tensOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_tensOut.setText("$0.00");
			 	}else{
					try { 
				        float tensValue = (float) (Float.parseFloat(textField_tensOut.getText())*10.00); 
				        endTotal += tensValue;
				        textArea_tensOut.setText("$" +formatDecimal(tensValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of tens!");
				    }
			 	}
			 	
			 	if(textField_twentiesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_twentiesOut.setText("$0.00");
			 	}else{
					try { 
				        float twentiesValue = (float) (Float.parseFloat(textField_twentiesOut.getText())*20.00); 
				        endTotal += twentiesValue;
				        textArea_twentiesOut.setText("$" +formatDecimal(twentiesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of twenties!");
				    }
			 	}
			 	
			 	if(textField_fiftiesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_fiftiesOut.setText("$0.00");
			 	}else{
					try { 
				        float fiftiesValue = (float) (Float.parseFloat(textField_fiftiesOut.getText())*50.00); 
				        endTotal += fiftiesValue;
				        textArea_fiftiesOut.setText("$" +formatDecimal(fiftiesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of fifties!");
				    }
			 	}
			 	
			 	if(textField_hundredsOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_hundredsOut.setText("$0.00");
			 	}else{
					try { 
				        float hundredsValue = (float) (Float.parseFloat(textField_hundredsOut.getText())*100.00); 
				        endTotal += hundredsValue;
				        textArea_hundredsOut.setText("$" +formatDecimal(hundredsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of hundreds!");
				    }
			 	}
			 //^^^endTotal calculated, add ups all the cash textAreas on cash out side
			 
			 //Now, we manage the data we got from the server
			
			//Makes sure that our actualClub is updated to match with the server's
			actualClub = findActualClub(clubSelected);
			
			//Update textAreas
			
			//Update initial ticket values
			textArea_initialtickets.setText("" + (actualClub.getInitialTickets()));
			//Update tickets values
			textArea_issuedTickets.setText("" + (actualClub.getTickets()));
			
			//Calculate unsold tickets
			int unsoldTickets = 0;
			
			if(textField_fullSheetsUnsold.getText().compareTo("")==0){
		 		textField_fullSheetsUnsold.setText("0");
		 	}else{
				try { 
			        int fullSheets = (int) (Integer.parseInt(textField_fullSheetsUnsold.getText())*40); 
			        unsoldTickets += fullSheets;
			        textArea_fullSheetsOut.setText("" + fullSheets);
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of unsold full sheets!");
			    }
		 	}
			if(textField_halfSheetsUnsold.getText().compareTo("")==0){
		 		textField_halfSheetsUnsold.setText("0");
		 	}else{
				try { 
			        int halfSheets = (int) (Integer.parseInt(textField_halfSheetsUnsold.getText())*20); 
			        unsoldTickets += halfSheets;
			        textArea_halfSheetsOut.setText("" + halfSheets);
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of unsold half sheets!");
			    }
		 	}
			if(textField_singleTicketsUnsold.getText().compareTo("")==0){
		 		textField_singleTicketsUnsold.setText("0");
		 	}else{
				try { 
			        int singleTickets = (int) (Integer.parseInt(textField_singleTicketsUnsold.getText())); 
			        unsoldTickets += singleTickets;
			        textArea_singleTicketsOut.setText("" + singleTickets);
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of unsold single tickets!");
			    }
		 	}
			
			textArea_unsoldTickets.setText("" + unsoldTickets);
			
			textArea_soldTickets.setText("" + (actualClub.getInitialTickets()
												+actualClub.getTickets()
												-unsoldTickets));	// = initial+issued-(unsold tickets)
			

			textArea_ticketSales.setText("$" + formatDecimal((actualClub.getInitialTickets()+actualClub.getTickets()-unsoldTickets)*0.50f));
			
			//Update wristband values
			textArea_initialWristbands.setText("" + actualClub.getInitialWristbands());
			textArea_issuedWristbands.setText("" + (actualClub.getWristbands()));
			
			//DON'T FORGET TO DO WRISTBANDS TOO!!!
			int unsoldWristbands = 0;
			if(textField_wristbandsUnsold.getText().compareTo("")==0){
		 		textField_wristbandsUnsold.setText("0");
		 	}else{
				try { 
			        int wristbands = (int) (Integer.parseInt(textField_wristbandsUnsold.getText())); 
			        unsoldWristbands += wristbands ;
			        textArea_unsoldWristbands.setText("" + wristbands);
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of unsold wristbands!");
			    }
		 	}
			
			textArea_unsoldWristbands.setText(""+unsoldWristbands);
			textArea_soldWristbands.setText("" + (actualClub.getInitialWristbands()
													+actualClub.getWristbands()
													-unsoldWristbands));
			
			//makes sure wrisbandMultiplier is correct.
			wristbandMultiplier = Integer.parseInt(spinner_wbValue.getValue().toString().substring(1));
			
			//Get wristband multiplier, calculate final wristband sales
			textArea_wristbandSales.setText("$" + ((actualClub.getInitialWristbands()
													+actualClub.getWristbands()
													-unsoldWristbands) * wristbandMultiplier));
			
			//textArea_ticketSales.setText("$" + formatDecimal((actualClub.getInitialTickets()+actualClub.getTickets()-unsoldTickets)*0.50f));
			
			//Expected Revenue
			textArea_expectedRevenue.setText("$" + formatDecimal(((actualClub.getInitialTickets()+actualClub.getTickets()-unsoldTickets)*0.50f) 
													+ ((actualClub.getInitialWristbands()
															+actualClub.getWristbands()
															-unsoldWristbands) * wristbandMultiplier)  ));
		
			//Update Cash Calculations
			textArea_cashDrops.setText(actualClub.getCashdrops() + "");
			textArea_cashDropsBy800.setText("$" + formatDecimal(actualClub.getCashdrops()*800));
			
			//Collected Cash Calculations
			textArea_endTotal.setText("$" + formatDecimal(endTotal));
			textArea_endTotalCalc.setText("$" + formatDecimal(endTotal));
			textArea_finalTotal.setText(formatDecimal(
										actualClub.getCashdrops()*800
										-actualClub.getInitialCashDrop()
										+endTotal
										));
			textArea_startTotal2.setText("$" + formatDecimal(actualClub.getInitialCashDrop()));
			
			
			if (textArea_endTotalCalc.getText().compareTo("")!=0){
				textArea_finalTotal.setText("$"+
							(formatDecimal(actualClub.getCashdrops()*800
									+endTotal - actualClub.getInitialCashDrop())	
							));
				
	
			}else{
				textArea_finalTotal.setText(""+
						(formatDecimal(actualClub.getCashdrops()*800)));
			}
			

			//get credits
			float credits = 0;
			if(textField_credits.getText().compareTo("")==0){
		 		textField_credits.setText("0");
		 	}else{
				try { 
					credits = (float) Float.parseFloat(textField_credits.getText());
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid value for credits!");
			    }
		 	}
			float expectedEnding =( 	((actualClub.getInitialTickets()+actualClub.getTickets()-unsoldTickets)*0.50f) 
										+ ((actualClub.getInitialWristbands()
												+actualClub.getWristbands()
												-unsoldWristbands) * wristbandMultiplier) )
												- 
												credits;
			textArea_expectedEndingCash.setText("$" +formatDecimal(expectedEnding));	// expected revenue - credits
			
			float balance = (actualClub.getCashdrops()*800
					+endTotal - actualClub.getInitialCashDrop()) - expectedEnding;
			textArea_differences.setText("$" +formatDecimal(balance));			// collected - expected revenue - credits
			
			
			
			/*
			public CheckOutDispatch(String source, String club,
					float collected_revenue, int tickets_sold, int wristbands_sold,
					float misc_credits_promos, boolean on_credit_terminal) {
				super(source);
				this.club = club;
				this.collected_revenue = collected_revenue;
				this.tickets_sold = tickets_sold;
				this.wristbands_sold = wristbands_sold;
				this.misc_credits_promos = misc_credits_promos;
				this.on_credit_terminal = on_credit_terminal;
			}
			*/
			
			try {
				float collected_revenue = actualClub.getCashdrops()*800 + endTotal - actualClub.getInitialCashDrop();
				int tickets_sold = actualClub.getInitialTickets()
										+actualClub.getTickets()
										-unsoldTickets;
				int wristbands_sold = actualClub.getInitialWristbands()
										+actualClub.getWristbands()
										-unsoldWristbands;
				float misc_credits = credits;
				boolean on_terminal = false;		//RIGHT NOW IT DEFAULTS TO FALSE, NOT SURE IF CRED TERMINAL DETERMINED AT DROP OR AT CHECKOUT
				
				int response = JOptionPane.showConfirmDialog(null, "Confirm cashier turn-in for " + actualClub.getClubName()+ " for \n" + 
																		"Collected Revenue: "+  collected_revenue + "\n"
																		+"Tickets Sold: " + tickets_sold + "\n"
																		+"Wristbands Sold: " + wristbands_sold + "\n"
																		+"Misc. Credits: " + misc_credits + "\n"
																		+"On credit terminal: " + on_terminal, 
						"Confirm", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.NO_OPTION) {
				JOptionPane.showMessageDialog(getParent(), "Action Canceled,  values not logged");
				} else if (response == JOptionPane.YES_OPTION) {
				
					try {
						output.writeObject(new CheckOutDispatch(clientName, actualClub.getClubName(), collected_revenue, tickets_sold, 
																								wristbands_sold, misc_credits, on_terminal));
						output.writeObject(new RemoveClub(clientName, actualClub.getClubName()));
					} catch (IOException e) {
						JOptionPane.showMessageDialog(getParent(), "Could not send cashier turn-in to server! (Dispatch.View.Panel_CICO: 177)");
						e.printStackTrace();
					}
		
					JOptionPane.showMessageDialog(getParent(), "Initial cashier turn-in sent to server");
				} else if (response == JOptionPane.CLOSED_OPTION) {
				System.out.println("JOptionPane closed");
				}
			
			}catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(getParent(), "Number format exception from ConfirmCheckoutCashierListener");
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	/**
	 * Listener for the initial cash drop button
	 */
	public class ConfirmInitialDropListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			
			float startTotal = 0;
			
			//Let's start adding this up...
			 	if(textField_penniesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_penniesIn.setText("$0.00");
			 	}else{
					try { 
				        float penniesValue = (float) (Float.parseFloat(textField_penniesIn.getText())*.01); 
						startTotal += penniesValue;
				        textArea_penniesIn.setText("$" +formatDecimal(penniesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of pennies!");
				    }
			 	}
			 	
			 	if(textField_nickelsIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_nickelsIn.setText("$0.00");
			 	}else{
					try { 
				        float nickelsValue = (float) (Float.parseFloat(textField_nickelsIn.getText())*.05); 
						startTotal += nickelsValue;
				        textArea_nickelsIn.setText("$" +formatDecimal(nickelsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of nickels!");
				    }
			 	}
			 	
			 	if(textField_dimesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_dimesIn.setText("$0.00");
			 	}else{
					try { 
				        float dimesValue = (float) (Float.parseFloat(textField_dimesIn.getText())*.10); 
						startTotal += dimesValue;
				        textArea_dimesIn.setText("$" +formatDecimal(dimesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of dimes!");
				    }
			 	}
			 	
			 	if(textField_quartersIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_quartersIn.setText("$0.00");
			 	}else{
					try { 
				        float quartersValue = (float) (Float.parseFloat(textField_quartersIn.getText())*.25); 
						startTotal += quartersValue;
				        textArea_quartersIn.setText("$" +formatDecimal(quartersValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of quarters!");
				    }
			 	}
			 	
			 	if(textField_dollarsIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_dollarsIn.setText("$0.00");
			 	}else{
					try { 
				        float dollarsValue = (float) (Float.parseFloat(textField_dollarsIn.getText())*1.00); 
						startTotal += dollarsValue;
				        textArea_dollarsIn.setText("$" +formatDecimal(dollarsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of single dollars!");
				    }
			 	}
			 	
			 	if(textField_twosIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_twosIn.setText("$0.00");
			 	}else{
					try { 
				        float twosValue = (float) (Float.parseFloat(textField_twosIn.getText())*2.00); 
						startTotal += twosValue;
				        textArea_twosIn.setText("$" +formatDecimal(twosValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of twos!");
				    }
			 	}
			 	
			 	if(textField_fivesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_fivesIn.setText("$0.00");
			 	}else{
					try { 
				        float fivesValue = (float) (Float.parseFloat(textField_fivesIn.getText())*5.00); 
						startTotal += fivesValue;
				        textArea_fivesIn.setText("$" +formatDecimal(fivesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of fives!");
				    }
			 	}
			 	
			 	if(textField_tensIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_tensIn.setText("$0.00");
			 	}else{
					try { 
				        float tensValue = (float) (Float.parseFloat(textField_tensIn.getText())*10.00); 
						startTotal += tensValue;
				        textArea_tensIn.setText("$" +formatDecimal(tensValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of tens!");
				    }
			 	}
			 	
			 	if(textField_twentiesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_twentiesIn.setText("$0.00");
			 	}else{
					try { 
				        float twentiesValue = (float) (Float.parseFloat(textField_twentiesIn.getText())*20.00); 
						startTotal += twentiesValue;
				        textArea_twentiesIn.setText("$" +formatDecimal(twentiesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of twenties!");
				    }
			 	}
			 	
			 	if(textField_fiftiesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_fiftiesIn.setText("$0.00");
			 	}else{
					try { 
				        float fiftiesValue = (float) (Float.parseFloat(textField_fiftiesIn.getText())*50.00); 
						startTotal += fiftiesValue;
				        textArea_fiftiesIn.setText("$" +formatDecimal(fiftiesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of fifties!");
				    }
			 	}
			 	
			 	if(textField_hundredsIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_hundredsIn.setText("$0.00");
			 	}else{
					try { 
				        float hundredsValue = (float) (Float.parseFloat(textField_hundredsIn.getText())*100.00); 
						startTotal += hundredsValue;
				        textArea_hundredsIn.setText("$" +formatDecimal(hundredsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of hundreds!");
				    }
			 	}
			 	
			textArea_startTotal.setText("$" + formatDecimal(startTotal));
			
			try {
				float startTotalf = (float) Float.parseFloat(textArea_startTotal.getText().substring(1));
				int initialTickets = (Integer.parseInt(textField_fullSheetsIn.getText().toString()) * 40)+
									(Integer.parseInt(textField_halfSheetsIn.getText().toString()) * 20)+
										(Integer.parseInt(textField_singleTicketsIn.getText().toString()));
				int initialWristbands = Integer.parseInt(textField_wristbandsIn.getText());
				location = spinner_locations.getValue().toString();
				
				int response = JOptionPane.showConfirmDialog(null, "Confirm Initial Drop for " + actualClub.getClubName()+ " for \n" + 
																		"Cash: "+  startTotalf + "\n"
																		+"Tickets: " + initialTickets + "\n"
																		+"wristbands: " + initialWristbands + "\n"
																		+"location: " + location, 
						"Confirm", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.NO_OPTION) {
				JOptionPane.showMessageDialog(getParent(), "Initial Drop Canceled");
				} else if (response == JOptionPane.YES_OPTION) {
				
					try {
						output.writeObject(new InitialCashDrop(clientName, actualClub.getClubName(), startTotalf, initialTickets, initialWristbands, location));
					} catch (IOException e) {
						JOptionPane.showMessageDialog(getParent(), "Could not send initial drop to server! (Dispatch.View.Panel_CICO: 177)");
						e.printStackTrace();
					}
		
					JOptionPane.showMessageDialog(getParent(), "Initial drop values sent to server");
				} else if (response == JOptionPane.CLOSED_OPTION) {
				System.out.println("JOptionPane closed");
				}
				
				
//				output.writeObject(new InitialCashDrop(clientName, actualClub.getClubName(), startTotal, initialTickets, initialWristbands, location));
//				
//				JOptionPane.showMessageDialog(getParent(), "Initial Drop logged for " + actualClub.getClubName()+ " for " + 
//															"Cash: "+  actualClub.getInitialCashDrop() + "\n"
//															+"Tickets: " + actualClub.getInitialTickets() + "\n"
//															+"wristbands: " + actualClub.getInitialWristbands() + "\n"
//															+"location: " + actualClub.getLocation());
				
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(getParent(), "Number format exception from ConfirmInitialDropListener");
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * Start Values Changed Listener, automatically updates totals when values entered
	 */
	private class StartValsChangedListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			float startTotal = 0;
			
			//Let's start adding this up...
			 	if(textField_penniesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_penniesIn.setText("$0.00");
			 	}else{
					try { 
				        float penniesValue = (float) (Float.parseFloat(textField_penniesIn.getText())*.01); 
						startTotal += penniesValue;
				        textArea_penniesIn.setText("$" +formatDecimal(penniesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of pennies!");
				    }
			 	}
			 	
			 	if(textField_nickelsIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_nickelsIn.setText("$0.00");
			 	}else{
					try { 
				        float nickelsValue = (float) (Float.parseFloat(textField_nickelsIn.getText())*.05); 
						startTotal += nickelsValue;
				        textArea_nickelsIn.setText("$" +formatDecimal(nickelsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of nickels!");
				    }
			 	}
			 	
			 	if(textField_dimesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_dimesIn.setText("$0.00");
			 	}else{
					try { 
				        float dimesValue = (float) (Float.parseFloat(textField_dimesIn.getText())*.10); 
						startTotal += dimesValue;
				        textArea_dimesIn.setText("$" +formatDecimal(dimesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of dimes!");
				    }
			 	}
			 	
			 	if(textField_quartersIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_quartersIn.setText("$0.00");
			 	}else{
					try { 
				        float quartersValue = (float) (Float.parseFloat(textField_quartersIn.getText())*.25); 
						startTotal += quartersValue;
				        textArea_quartersIn.setText("$" +formatDecimal(quartersValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of quarters!");
				    }
			 	}
			 	
			 	if(textField_dollarsIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_dollarsIn.setText("$0.00");
			 	}else{
					try { 
				        float dollarsValue = (float) (Float.parseFloat(textField_dollarsIn.getText())*1.00); 
						startTotal += dollarsValue;
				        textArea_dollarsIn.setText("$" +formatDecimal(dollarsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of single dollars!");
				    }
			 	}
			 	
			 	if(textField_twosIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_twosIn.setText("$0.00");
			 	}else{
					try { 
				        float twosValue = (float) (Float.parseFloat(textField_twosIn.getText())*2.00); 
						startTotal += twosValue;
				        textArea_twosIn.setText("$" +formatDecimal(twosValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of twos!");
				    }
			 	}
			 	
			 	if(textField_fivesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_fivesIn.setText("$0.00");
			 	}else{
					try { 
				        float fivesValue = (float) (Float.parseFloat(textField_fivesIn.getText())*5.00); 
						startTotal += fivesValue;
				        textArea_fivesIn.setText("$" +formatDecimal(fivesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of fives!");
				    }
			 	}
			 	
			 	if(textField_tensIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_tensIn.setText("$0.00");
			 	}else{
					try { 
				        float tensValue = (float) (Float.parseFloat(textField_tensIn.getText())*10.00); 
						startTotal += tensValue;
				        textArea_tensIn.setText("$" +formatDecimal(tensValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of tens!");
				    }
			 	}
			 	
			 	if(textField_twentiesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_twentiesIn.setText("$0.00");
			 	}else{
					try { 
				        float twentiesValue = (float) (Float.parseFloat(textField_twentiesIn.getText())*20.00); 
						startTotal += twentiesValue;
				        textArea_twentiesIn.setText("$" +formatDecimal(twentiesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of twenties!");
				    }
			 	}
			 	
			 	if(textField_fiftiesIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_fiftiesIn.setText("$0.00");
			 	}else{
					try { 
				        float fiftiesValue = (float) (Float.parseFloat(textField_fiftiesIn.getText())*50.00); 
						startTotal += fiftiesValue;
				        textArea_fiftiesIn.setText("$" +formatDecimal(fiftiesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of fifties!");
				    }
			 	}
			 	
			 	if(textField_hundredsIn.getText().compareTo("")==0){
			 		startTotal += 0;
			 		textArea_hundredsIn.setText("$0.00");
			 	}else{
					try { 
				        float hundredsValue = (float) (Float.parseFloat(textField_hundredsIn.getText())*100.00); 
						startTotal += hundredsValue;
				        textArea_hundredsIn.setText("$" +formatDecimal(hundredsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of hundreds!");
				    }
			 	}
			 	
			textArea_startTotal.setText("$" + formatDecimal(startTotal));
		}
		
	}
	public String formatDecimal(float number) {
		  float epsilon = 0.004f; // 4 tenths of a cent
		  if (Math.abs(Math.round(number) - number) < epsilon) {
		     return String.format("%10.0f", number);
		  } else {
		     return String.format("%10.2f", number);
		  }
	}
	
	/**
	 * End Values Changed Listener, automatically updates totals when values entered
	 */
	private class EndValsChangedListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			float endTotal = 0;
			
			//Let's start adding this up...
			 	if(textField_penniesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_penniesOut.setText("$0.00");
			 	}else{
					try { 
				        float penniesValue = (float) (Float.parseFloat(textField_penniesOut.getText())*.01); 
				        endTotal += penniesValue;
				        textArea_penniesOut.setText("$" +formatDecimal(penniesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of pennies!");
				    }
			 	}
			 	
			 	if(textField_nickelsOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_nickelsOut.setText("$0.00");
			 	}else{
					try { 
				        float nickelsValue = (float) (Float.parseFloat(textField_nickelsOut.getText())*.05); 
				        endTotal += nickelsValue;
				        textArea_nickelsOut.setText("$" +formatDecimal(nickelsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of nickels!");
				    }
			 	}
			 	
			 	if(textField_dimesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_dimesOut.setText("$0.00");
			 	}else{
					try { 
				        float dimesValue = (float) (Float.parseFloat(textField_dimesOut.getText())*.10); 
				        endTotal += dimesValue;
				        textArea_dimesOut.setText("$" +formatDecimal(dimesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of dimes!");
				    }
			 	}
			 	
			 	if(textField_quartersOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_quartersOut.setText("$0.00");
			 	}else{
					try { 
				        float quartersValue = (float) (Float.parseFloat(textField_quartersOut.getText())*.25); 
				        endTotal += quartersValue;
				        textArea_quartersOut.setText("$" +formatDecimal(quartersValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of quarters!");
				    }
			 	}
			 	
			 	if(textField_dollarsOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_dollarsOut.setText("$0.00");
			 	}else{
					try { 
				        float dollarsValue = (float) (Float.parseFloat(textField_dollarsOut.getText())*1.00); 
				        endTotal += dollarsValue;
				        textArea_dollarsOut.setText("$" +formatDecimal(dollarsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of single dollars!");
				    }
			 	}
			 	
			 	if(textField_twosOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_twosOut.setText("$0.00");
			 	}else{
					try { 
				        float twosValue = (float) (Float.parseFloat(textField_twosOut.getText())*2.00); 
				        endTotal += twosValue;
				        textArea_twosOut.setText("$" +formatDecimal(twosValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of twos!");
				    }
			 	}
			 	
			 	if(textField_fivesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_fivesOut.setText("$0.00");
			 	}else{
					try { 
				        float fivesValue = (float) (Float.parseFloat(textField_fivesOut.getText())*5.00); 
				        endTotal += fivesValue;
				        textArea_fivesOut.setText("$" +formatDecimal(fivesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of fives!");
				    }
			 	}
			 	
			 	if(textField_tensOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_tensOut.setText("$0.00");
			 	}else{
					try { 
				        float tensValue = (float) (Float.parseFloat(textField_tensOut.getText())*10.00); 
				        endTotal += tensValue;
				        textArea_tensOut.setText("$" +formatDecimal(tensValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of tens!");
				    }
			 	}
			 	
			 	if(textField_twentiesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_twentiesOut.setText("$0.00");
			 	}else{
					try { 
				        float twentiesValue = (float) (Float.parseFloat(textField_twentiesOut.getText())*20.00); 
				        endTotal += twentiesValue;
				        textArea_twentiesOut.setText("$" +formatDecimal(twentiesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of twenties!");
				    }
			 	}
			 	
			 	if(textField_fiftiesOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_fiftiesOut.setText("$0.00");
			 	}else{
					try { 
				        float fiftiesValue = (float) (Float.parseFloat(textField_fiftiesOut.getText())*50.00); 
				        endTotal += fiftiesValue;
				        textArea_fiftiesOut.setText("$" +formatDecimal(fiftiesValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of fifties!");
				    }
			 	}
			 	
			 	if(textField_hundredsOut.getText().compareTo("")==0){
			 		endTotal += 0;
			 		textArea_hundredsOut.setText("$0.00");
			 	}else{
					try { 
				        float hundredsValue = (float) (Float.parseFloat(textField_hundredsOut.getText())*100.00); 
				        endTotal += hundredsValue;
				        textArea_hundredsOut.setText("$" +formatDecimal(hundredsValue));
				    } catch(NumberFormatException e) { 
				    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of hundreds!");
				    }
			 	}
			 //^^^endTotal calculated, add ups all the cash textAreas on cash out side
			 
			 //Now, we manage the data we got from the server
			
			//Makes sure that our actualClub is updated to match with the server's
			actualClub = findActualClub(clubSelected);
			
			//Update textAreas
			
			//Update initial ticket values
			textArea_initialtickets.setText("" + (actualClub.getInitialTickets()));
			//Update tickets values
			textArea_issuedTickets.setText("" + (actualClub.getTickets()));
			
			//Calculate unsold tickets
			int unsoldTickets = 0;
			
			if(textField_fullSheetsUnsold.getText().compareTo("")==0){
		 		textField_fullSheetsUnsold.setText("0");
		 	}else{
				try { 
			        int fullSheets = (int) (Integer.parseInt(textField_fullSheetsUnsold.getText())*40); 
			        unsoldTickets += fullSheets;
			        textArea_fullSheetsOut.setText("" + fullSheets);
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of unsold full sheets!");
			    }
		 	}
			if(textField_halfSheetsUnsold.getText().compareTo("")==0){
		 		textField_halfSheetsUnsold.setText("0");
		 	}else{
				try { 
			        int halfSheets = (int) (Integer.parseInt(textField_halfSheetsUnsold.getText())*20); 
			        unsoldTickets += halfSheets;
			        textArea_halfSheetsOut.setText("" + halfSheets);
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of unsold half sheets!");
			    }
		 	}
			if(textField_singleTicketsUnsold.getText().compareTo("")==0){
		 		textField_singleTicketsUnsold.setText("0");
		 	}else{
				try { 
			        int singleTickets = (int) (Integer.parseInt(textField_singleTicketsUnsold.getText())); 
			        unsoldTickets += singleTickets;
			        textArea_singleTicketsOut.setText("" + singleTickets);
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of unsold single tickets!");
			    }
		 	}
			
			textArea_unsoldTickets.setText("" + unsoldTickets);
			
			textArea_soldTickets.setText("" + (actualClub.getInitialTickets()
												+actualClub.getTickets()
												-unsoldTickets));	// = initial+issued-(unsold tickets)
			

			textArea_ticketSales.setText("$" + formatDecimal((actualClub.getInitialTickets()+actualClub.getTickets()-unsoldTickets)*0.50f));
			
			//Update wristband values
			textArea_initialWristbands.setText("" + actualClub.getInitialWristbands());
			textArea_issuedWristbands.setText("" + (actualClub.getWristbands()));
			
			//DON'T FORGET TO DO WRISTBANDS TOO!!!
			int unsoldWristbands = 0;
			if(textField_wristbandsUnsold.getText().compareTo("")==0){
		 		textField_wristbandsUnsold.setText("0");
		 	}else{
				try { 
			        int wristbands = (int) (Integer.parseInt(textField_wristbandsUnsold.getText())); 
			        unsoldWristbands += wristbands ;
			        textArea_unsoldWristbands.setText("" + wristbands);
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of unsold wristbands!");
			    }
		 	}
			
			textArea_unsoldWristbands.setText(""+unsoldWristbands);
			textArea_soldWristbands.setText("" + (actualClub.getInitialWristbands()
													+actualClub.getWristbands()
													-unsoldWristbands));
			
			//makes sure wrisbandMultiplier is correct.
			wristbandMultiplier = Integer.parseInt(spinner_wbValue.getValue().toString().substring(1));
			
			//Get wristband multiplier, calculate final wristband sales
			textArea_wristbandSales.setText("$" + ((actualClub.getInitialWristbands()
													+actualClub.getWristbands()
													-unsoldWristbands) * wristbandMultiplier));
			
			//textArea_ticketSales.setText("$" + formatDecimal((actualClub.getInitialTickets()+actualClub.getTickets()-unsoldTickets)*0.50f));
			
			//Expected Revenue
			textArea_expectedRevenue.setText("$" + formatDecimal(((actualClub.getInitialTickets()+actualClub.getTickets()-unsoldTickets)*0.50f) 
													+ ((actualClub.getInitialWristbands()
															+actualClub.getWristbands()
															-unsoldWristbands) * wristbandMultiplier)  ));
		
			//Update Cash Calculations
			textArea_cashDrops.setText(actualClub.getCashdrops() + "");
			textArea_cashDropsBy800.setText("$" + formatDecimal(actualClub.getCashdrops()*800));
			
			//Collected Cash Calculations
			textArea_endTotal.setText("$" + formatDecimal(endTotal));
			textArea_endTotalCalc.setText("$" + formatDecimal(endTotal));
			textArea_finalTotal.setText(formatDecimal(
										actualClub.getCashdrops()*800
										-actualClub.getInitialCashDrop()
										+endTotal
										));
			textArea_startTotal2.setText("$" + formatDecimal(actualClub.getInitialCashDrop()));
			
			
			if (textArea_endTotalCalc.getText().compareTo("")!=0){
				textArea_finalTotal.setText("$"+
							(formatDecimal(actualClub.getCashdrops()*800
									+endTotal - actualClub.getInitialCashDrop())	
							));
				
	
			}else{
				textArea_finalTotal.setText(""+
						(formatDecimal(actualClub.getCashdrops()*800)));
			}
			

			//get credits
			float credits = 0;
			if(textField_credits.getText().compareTo("")==0){
		 		textField_credits.setText("0");
		 	}else{
				try { 
					credits = (float) Float.parseFloat(textField_credits.getText());
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid value for credits!");
			    }
		 	}
			float expectedEnding =( 	((actualClub.getInitialTickets()+actualClub.getTickets()-unsoldTickets)*0.50f) 
										+ ((actualClub.getInitialWristbands()
												+actualClub.getWristbands()
												-unsoldWristbands) * wristbandMultiplier) )
												- 
												credits;
			textArea_expectedEndingCash.setText("$" +formatDecimal(expectedEnding));	// expected revenue - credits
			
			float balance = (actualClub.getCashdrops()*800
					+endTotal - actualClub.getInitialCashDrop()) - expectedEnding;
			textArea_differences.setText("$" +formatDecimal(balance));			// collected - expected revenue - credits
			
			
			//redline();
		}
	}
	
	
	private Club findActualClub(String clubSelected2) {
		
		if (activeClubs!=null){
			for(Club club : activeClubs){
				if (clubSelected.compareTo(club.getClubName())==0){
					return club;
				}
			}
		}
		return new Club("Dummy club");
	}
	/**
	 *  Creates an ArrayList<String> from the activeClubs list
	 * 
	 */
	private ArrayList<String> getClubs() {
		ArrayList<String> clubs = new ArrayList<>();
		if (activeClubs!=null){
			for (Club club : activeClubs){
				clubs.add(club.getClubName());
			}
		}
		if (clubs.size()==0){
			clubs.add("(No cashiers!)");
		} else{
			clubs.remove("(No cashiers!)");
		}
		return clubs;
	}
	
	/**
	 * UpdateLists method
	 */
	public void UpdateLists(List<Club> clubs, List<String> available, List<String> dispatched){
		this.activeClubs = clubs;
		this.availableFS = available;
		this.dispatchedFS = dispatched;
		
		//Remove, update, replace spinner_clubs
		remove(spinner_clubSelection_1);
		ArrayList<String> clubsArray = getClubs();
		SpinnerListModel clubsModel = new SpinnerListModel(clubsArray);
		spinner_clubSelection_1 = new JSpinner(clubsModel);
		spinner_clubSelection_1.setBounds(147, 23, 252, 40);
		spinner_clubSelection_1.addChangeListener(clubSpinnerListener);
		add(spinner_clubSelection_1);
		
		//this.repaint();
		
		if (clubsArray.contains(clubSelected)){
			
			spinner_clubSelection_1.setValue(clubSelected);		//resets value to club client was working on
			actualClub = findActualClub(clubSelected);			//changes actual club to reflect changed values from server
			
			// ...if it's there, show that value as startTotal
			textArea_startTotal.setText("$" + formatDecimal(actualClub.getInitialCashDrop()));
			
			//Update location spinner value and location if present
			if (actualClub.getLocation().compareTo("(Unassigned)")!=0){
				spinner_locations.setValue(actualClub.getLocation());
				spinner_locations.setEnabled(false);
				location = spinner_locations.getValue().toString();
			} else{
				spinner_locations.setEnabled(true);
			}
			
			//Update textAreas
			
			//Update initial ticket values
			textArea_initialtickets.setText("" + (actualClub.getInitialTickets()));
			//Update tickets values
			textArea_issuedTickets.setText("" + (actualClub.getTickets()));
			textArea_unsoldTickets.setText("0" );
			textArea_soldTickets.setText("" + (actualClub.getInitialTickets()+actualClub.getTickets()));	// = initial+issued-(unsold tickets, but unsold tickets cleared)
			textArea_ticketSales.setText("$" + formatDecimal((actualClub.getInitialTickets()+actualClub.getTickets())*0.50f));
			
			//Update wristband values
			textArea_initialWristbands.setText("" + actualClub.getInitialWristbands());
			textArea_issuedWristbands.setText("" + (actualClub.getWristbands()));
			textArea_unsoldWristbands.setText("0");
			textArea_soldWristbands.setText("" + (actualClub.getInitialWristbands()+actualClub.getWristbands()));
			
			//Get wristband multiplier, calculate final wristband sales
			textArea_wristbandSales.setText("" + (actualClub.getInitialWristbands()+actualClub.getWristbands()*wristbandMultiplier));
			
			//Expected Revenue
			textArea_expectedRevenue.setText("$" + formatDecimal(((actualClub.getInitialTickets()+actualClub.getTickets())*0.50f) + (actualClub.getInitialWristbands()+actualClub.getWristbands()*wristbandMultiplier)  ));
			
			//Update Cash Calculations
			textArea_cashDrops.setText(actualClub.getCashdrops() + "");
			textArea_cashDropsBy800.setText("$" + formatDecimal(actualClub.getCashdrops()*800));
			
			//Collected
			textArea_endTotalCalc.setText("");
			textArea_finalTotal.setText(formatDecimal(actualClub.getCashdrops()*800-actualClub.getInitialCashDrop()));
			textArea_startTotal2.setText("$" + formatDecimal(actualClub.getInitialCashDrop()));

			
		}else{
			clubSelected = spinner_clubSelection_1.getValue().toString();	//if clubSelected doesn't exist anymore, set to first value in new spinner
			actualClub = findActualClub(clubSelected);						//updates actual club to reflect changes made on it from server
			
			//Update textAreas
			
			//Update initial ticket values
			textArea_initialtickets.setText("" + (actualClub.getInitialTickets()));
			//Update tickets values
			textArea_issuedTickets.setText("" + (actualClub.getTickets()));
			textArea_unsoldTickets.setText("0" );
			textArea_soldTickets.setText("" + (actualClub.getInitialTickets()+actualClub.getTickets()));	// = initial+issued-(unsold tickets, but unsold tickets cleared)
			textArea_ticketSales.setText("$" + formatDecimal((actualClub.getInitialTickets()+actualClub.getTickets())*0.50f));
			
			//Update wristband values
			textArea_initialWristbands.setText("" + actualClub.getInitialWristbands());
			textArea_issuedWristbands.setText("" + (actualClub.getWristbands()));
			textArea_unsoldWristbands.setText("0");
			textArea_soldWristbands.setText("" + (actualClub.getInitialWristbands()+actualClub.getWristbands()));
			
			//Get wristband multiplier, calculate final wristband sales
			textArea_wristbandSales.setText("" + (actualClub.getInitialWristbands()+actualClub.getWristbands()*wristbandMultiplier));
			
			//Expected Revenue
			textArea_expectedRevenue.setText("$" + formatDecimal(((actualClub.getInitialTickets()+actualClub.getTickets())*0.50f) + (actualClub.getInitialWristbands()+actualClub.getWristbands()*wristbandMultiplier)  ));
			
			//Update Cash Calculations
			textArea_cashDrops.setText(actualClub.getCashdrops() + "");
			textArea_cashDropsBy800.setText("$" + formatDecimal(actualClub.getCashdrops()*800));
			
			//Collected
			textArea_endTotalCalc.setText("");
			textArea_finalTotal.setText(formatDecimal(actualClub.getCashdrops()*800-actualClub.getInitialCashDrop()));
			textArea_startTotal2.setText("$" + formatDecimal(actualClub.getInitialCashDrop()));
			
			
//			clearFields();	//haha, don't clear fields or you'll piss off tellers!
		}
		

		//if the club already has an initial cashdrop, turn off button and text fields...
		
		if (actualClub.getInitialCashDrop() !=0.0){
			btn_drops.setEnabled(true);
			btn_initialCashDrop.setEnabled(false);
			btn_initialCashDrop.setText("INITIAL CASH DROP DELIVERED!");
			textField_penniesIn.setEnabled(false);
			textField_nickelsIn.setEnabled(false);
			textField_dimesIn.setEnabled(false);
			textField_quartersIn.setEnabled(false);
			textField_dollarsIn.setEnabled(false);
			textField_twosIn.setEnabled(false);
			textField_fivesIn.setEnabled(false);
			textField_tensIn.setEnabled(false);
			textField_twentiesIn.setEnabled(false);
			textField_fiftiesIn.setEnabled(false);
			textField_hundredsIn.setEnabled(false);
			
			//ticket calculations
			textField_fullSheetsIn.setEnabled(false);
			textField_halfSheetsIn.setEnabled(false);
			textField_singleTicketsIn.setEnabled(false);
			textField_wristbandsIn.setEnabled(false);
			
			spinner_locations.setEnabled(false);
			
		} else{ // else, make sure, they're on...
			btn_initialCashDrop.setEnabled(true);
			btn_initialCashDrop.setText("CONFIRM INITIAL CASH DROP");
			textField_penniesIn.setEnabled(true);
			textField_nickelsIn.setEnabled(true);
			textField_dimesIn.setEnabled(true);
			textField_quartersIn.setEnabled(true);
			textField_dollarsIn.setEnabled(true);
			textField_twosIn.setEnabled(true);
			textField_fivesIn.setEnabled(true);
			textField_tensIn.setEnabled(true);
			textField_twentiesIn.setEnabled(true);
			textField_fiftiesIn.setEnabled(true);
			textField_hundredsIn.setEnabled(true);
			
			//ticket calculations
			textField_fullSheetsIn.setEnabled(true);
			textField_halfSheetsIn.setEnabled(true);
			textField_singleTicketsIn.setEnabled(true);
			textField_wristbandsIn.setEnabled(true);
			
			spinner_locations.setEnabled(true);
		}
	}
	
	/**
	 * ClubSelected Spinner Listener
	 */
	private class ClubSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			
			JScrollPane scrollpane = (JScrollPane) getParent().getParent();
			 final JScrollBar vertical = scrollpane.getVerticalScrollBar();
			 final JScrollBar horizontal = scrollpane.getHorizontalScrollBar();
		        final int vertValue = vertical.getValue();
		        final int horizValue = horizontal.getValue();

			clearFields();
			clubSelected = spinner_clubSelection_1.getValue().toString();
			actualClub = findActualClub(clubSelected);
			//if the club already has an initial cashdrop, turn off button and text fields...
			
			if (actualClub.getInitialCashDrop()!=0.0){
				btn_drops.setEnabled(true);
				btn_initialCashDrop.setEnabled(false);
				btn_initialCashDrop.setText("INITIAL CASH DROP DELIVERED!");
				textField_penniesIn.setEnabled(false);
				textField_nickelsIn.setEnabled(false);
				textField_dimesIn.setEnabled(false);
				textField_quartersIn.setEnabled(false);
				textField_dollarsIn.setEnabled(false);
				textField_twosIn.setEnabled(false);
				textField_fivesIn.setEnabled(false);
				textField_tensIn.setEnabled(false);
				textField_twentiesIn.setEnabled(false);
				textField_fiftiesIn.setEnabled(false);
				textField_hundredsIn.setEnabled(false);
				
				//ticket calculations
				textField_fullSheetsIn.setEnabled(false);
				textField_halfSheetsIn.setEnabled(false);
				textField_singleTicketsIn.setEnabled(false);
				textField_wristbandsIn.setEnabled(false);
				
				// ...if it's there, show that value as startTotal
				textArea_startTotal.setText("$" + formatDecimal(actualClub.getInitialCashDrop()));
				
			} else{ // else, make sure, they're on...
				//btn_drops.setEnabled(false);			//Greg said that there may be occasions where a drop will need to be logged before the initial drop -kc
				btn_initialCashDrop.setEnabled(true);
				btn_initialCashDrop.setText("CONFIRM INITIAL CASH DROP");
				textField_penniesIn.setEnabled(true);
				textField_nickelsIn.setEnabled(true);
				textField_dimesIn.setEnabled(true);
				textField_quartersIn.setEnabled(true);
				textField_dollarsIn.setEnabled(true);
				textField_twosIn.setEnabled(true);
				textField_fivesIn.setEnabled(true);
				textField_tensIn.setEnabled(true);
				textField_twentiesIn.setEnabled(true);
				textField_fiftiesIn.setEnabled(true);
				textField_hundredsIn.setEnabled(true);
				
				//setDefault $150 initial cashdrop
				textField_hundredsIn.setText("1");
				textField_fiftiesIn.setText("1");
				
				
				//ticket calculations
				textField_fullSheetsIn.setEnabled(true);
				textField_halfSheetsIn.setEnabled(true);
				textField_singleTicketsIn.setEnabled(true);
				textField_wristbandsIn.setEnabled(true);
				
				// ...if it's there, show that value as startTotal
				textArea_startTotal.setText("$150.00");
				
			}
			
			
			
			//Update location spinner value and location if present
			if (actualClub.getLocation().compareTo("(Unassigned)")!=0){
				spinner_locations.setValue(actualClub.getLocation());
				spinner_locations.setEnabled(false);
				location = spinner_locations.getValue().toString();
			} else{
				spinner_locations.setEnabled(true);
			}
			//Update textAreas
			
			
			
			
			//Update initial ticket values
			textArea_initialtickets.setText("" + (actualClub.getInitialTickets()));
			//Update tickets values
			textArea_issuedTickets.setText("" + (actualClub.getTickets()));
			textArea_unsoldTickets.setText("0" );
			textArea_soldTickets.setText("" + (actualClub.getInitialTickets()+actualClub.getTickets()));	// = initial+issued-(unsold tickets, but unsold tickets cleared)
			textArea_ticketSales.setText("$" + formatDecimal((actualClub.getInitialTickets()+actualClub.getTickets())*0.50f));
			
			//Update wristband values
			textArea_initialWristbands.setText("" + actualClub.getInitialWristbands());
			textArea_issuedWristbands.setText("" + (actualClub.getWristbands()));
			textArea_unsoldWristbands.setText("0");
			textArea_soldWristbands.setText("" + (actualClub.getInitialWristbands()+actualClub.getWristbands()));
			
			//Get wristband multiplier, calculate final wristband sales
			textArea_wristbandSales.setText("" + (actualClub.getInitialWristbands()+actualClub.getWristbands()*wristbandMultiplier));
			
			//Expected Revenue
			textArea_expectedRevenue.setText("$" + formatDecimal(((actualClub.getInitialTickets()+actualClub.getTickets())*0.50f) + (actualClub.getInitialWristbands()+actualClub.getWristbands()*wristbandMultiplier)  ));
			
			//Update Cash Calculations
			textArea_cashDrops.setText(actualClub.getCashdrops() + "");
			textArea_cashDropsBy800.setText("$" + formatDecimal(actualClub.getCashdrops()*800));
			
			//Collected
			textArea_endTotalCalc.setText("");
			textArea_finalTotal.setText(formatDecimal(actualClub.getCashdrops()*800-actualClub.getInitialCashDrop()));
			textArea_startTotal2.setText("$" + formatDecimal(actualClub.getInitialCashDrop()));
			
			SwingUtilities.invokeLater(new Runnable()
	        {
	            public void run()
	            {
	                vertical.setValue( vertValue );
	                horizontal.setValue(horizValue);
	            }
	        });
			
		}
	}
	
	/**
	 * 	Wristband Multiplier Spinner Listener
	 */
	private class WristbandMultiplierListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			wristbandMultiplier = Integer.parseInt(spinner_wbValue.getValue().toString().substring(1));
			
			//Update wristband values
			textArea_initialWristbands.setText("" + actualClub.getInitialWristbands());
			textArea_issuedWristbands.setText("" + (actualClub.getWristbands()));
			
			//DON'T FORGET TO DO WRISTBANDS TOO!!!
			int unsoldWristbands = 0;
			if(textField_wristbandsUnsold.getText().compareTo("")==0){
		 		textField_wristbandsUnsold.setText("0");
		 	}else{
				try { 
			        int wristbands = (int) (Integer.parseInt(textField_wristbandsUnsold.getText())); 
			        unsoldWristbands += wristbands ;
			        textArea_unsoldWristbands.setText("" + wristbands);
			    } catch(NumberFormatException e) { 
			    	JOptionPane.showMessageDialog(getParent(), "Please enter a valid number of unsold wristbands!");
			    }
		 	}
			
			textArea_unsoldWristbands.setText(""+unsoldWristbands);
			textArea_soldWristbands.setText("" + (actualClub.getInitialWristbands()
													+actualClub.getWristbands()
													-unsoldWristbands));
			
			//makes sure wrisbandMultiplier is correct.
			wristbandMultiplier = Integer.parseInt(spinner_wbValue.getValue().toString().substring(1));
			
			//Get wristband multiplier, calculate final wristband sales
			textArea_wristbandSales.setText("$" + ((actualClub.getInitialWristbands()
													+actualClub.getWristbands()
													-unsoldWristbands) * wristbandMultiplier));
			
		}

	}
	
	/**
	 *  Location Spinner Listener
	 */
	private class LocationSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			location = spinner_locations.getValue().toString();
		}
		
	}
	
	/**
	 * This clears all the fields. Should run when the cashier changes only
	 */
	private void clearFields() {
		textField_penniesIn.setText("");
		textField_penniesOut.setText("");
		textField_nickelsIn.setText("");
		textField_nickelsOut.setText("");
		textField_dimesIn.setText("");
		textField_dimesOut.setText("");
		textField_quartersIn.setText("");
		textField_quartersOut.setText("");
		textField_dollarsIn.setText("");
		textField_dollarsOut.setText("");
		textField_twosIn.setText("");
		textField_twosOut.setText("");
		textField_fivesIn.setText("");
		textField_fivesOut.setText("");
		textField_tensIn.setText("");
		textField_tensOut.setText("");
		textField_twentiesIn.setText("");
		textField_twentiesOut.setText("");
		textField_fiftiesIn.setText("");
		textField_fiftiesOut.setText("");
		textField_hundredsIn.setText("");
		textField_hundredsOut.setText("");
		
		textArea_penniesIn.setText("$0.00");
		textArea_penniesOut.setText("$0.00");
		textArea_nickelsIn.setText("$0.00");
		textArea_nickelsOut.setText("$0.00");
		textArea_dimesIn.setText("$0.00");
		textArea_dimesOut.setText("$0.00");
		textArea_quartersIn.setText("$0.00");
		textArea_quartersOut.setText("$0.00");
		textArea_dollarsIn.setText("$0.00");
		textArea_dollarsOut.setText("$0.00");
		textArea_twosIn.setText("$0.00");
		textArea_twosOut.setText("$0.00");
		textArea_fivesIn.setText("$0.00");
		textArea_fivesOut.setText("$0.00");
		textArea_tensIn.setText("$0.00");
		textArea_tensOut.setText("$0.00");
		textArea_twentiesIn.setText("$0.00");
		textArea_twentiesOut.setText("$0.00");
		textArea_fiftiesIn.setText("$0.00");
		textArea_fiftiesOut.setText("$0.00");
		textArea_hundredsIn.setText("$0.00");
		textArea_hundredsOut.setText("$0.00");
		
		textField_fullSheetsUnsold.setText("0");
		textField_halfSheetsUnsold.setText("0");
		textField_singleTicketsUnsold.setText("0");
		textField_wristbandsUnsold.setText("0");
		textField_fullSheetsIn.setText("0");
		textField_halfSheetsIn.setText("0");
		textField_singleTicketsIn.setText("0");
		textField_wristbandsIn.setText("0");
		
		
		//Calculation Fields
		textArea_finalTotal.setText("");
		textArea_endTotal.setText("");
		textArea_endTotalCalc.setText("");
	}

	public Panel_CICO(String userName, ObjectOutputStream out, List<Club> activeClubs2, List<String> availableFS2, List<String> dispatchedFS2) {
		
		clientName = userName;
		output = out;
		activeClubs = activeClubs2;
		availableFS = availableFS2;
		dispatchedFS = dispatchedFS2;
		
		startValsChangedListener = new StartValsChangedListener();
		endValsChangedListener = new EndValsChangedListener();
		confirmInitialDropListener = new ConfirmInitialDropListener();
		confirmCheckOutCashierListener = new ConfirmCheckOutCashierListener();
		wristbandMultiplierListener = new WristbandMultiplierListener();
		clubSpinnerListener = new ClubSpinnerListener();
		locationSpinnerListener = new LocationSpinnerListener();
		setLayout(null);
		
		JTextArea txtrX = new JTextArea();
		txtrX.setBounds(10, 139, 67, 22);
		txtrX.setBackground(SystemColor.control);
		txtrX.setEditable(false);
		txtrX.setText("$0.01 X");
		add(txtrX);
		
		JTextArea txtrX_1 = new JTextArea();
		txtrX_1.setBounds(10, 172, 67, 22);
		txtrX_1.setBackground(SystemColor.control);
		txtrX_1.setEditable(false);
		txtrX_1.setText("$0.05 X");
		add(txtrX_1);
		
		JTextArea txtrX_2 = new JTextArea();
		txtrX_2.setBounds(10, 205, 67, 22);
		txtrX_2.setBackground(SystemColor.control);
		txtrX_2.setEditable(false);
		txtrX_2.setText("$0.10 X");
		add(txtrX_2);
		
		JTextArea txtrX_3 = new JTextArea();
		txtrX_3.setBounds(10, 238, 67, 22);
		txtrX_3.setBackground(SystemColor.control);
		txtrX_3.setEditable(false);
		txtrX_3.setText("$0.25 X");
		add(txtrX_3);
		
		JTextArea txtrX_4 = new JTextArea();
		txtrX_4.setBounds(10, 271, 67, 22);
		txtrX_4.setBackground(SystemColor.control);
		txtrX_4.setEditable(false);
		txtrX_4.setText("$1    X");
		add(txtrX_4);
		
		JTextArea txtrX_5 = new JTextArea();
		txtrX_5.setBounds(10, 304, 67, 22);
		txtrX_5.setBackground(SystemColor.control);
		txtrX_5.setEditable(false);
		txtrX_5.setText("$2    X");
		add(txtrX_5);
		
		JTextArea txtrX_6 = new JTextArea();
		txtrX_6.setBounds(10, 337, 67, 22);
		txtrX_6.setBackground(SystemColor.control);
		txtrX_6.setEditable(false);
		txtrX_6.setText("$5    X");
		add(txtrX_6);
		
		JTextArea txtrX_7 = new JTextArea();
		txtrX_7.setBounds(10, 370, 67, 22);
		txtrX_7.setBackground(SystemColor.control);
		txtrX_7.setEditable(false);
		txtrX_7.setText("$10   X");
		add(txtrX_7);
		
		JTextArea txtrX_8 = new JTextArea();
		txtrX_8.setBounds(10, 403, 67, 22);
		txtrX_8.setBackground(SystemColor.control);
		txtrX_8.setEditable(false);
		txtrX_8.setText("$20   X");
		add(txtrX_8);
		
		JTextArea txtrX_9 = new JTextArea();
		txtrX_9.setBounds(10, 436, 67, 22);
		txtrX_9.setBackground(SystemColor.control);
		txtrX_9.setEditable(false);
		txtrX_9.setText("$50   X");
		add(txtrX_9);
		
		JTextArea txtrX_10 = new JTextArea();
		txtrX_10.setBounds(10, 469, 67, 22);
		txtrX_10.setBackground(SystemColor.control);
		txtrX_10.setEditable(false);
		txtrX_10.setText("$100  X");
		add(txtrX_10);
		
		textField_penniesIn = new JTextField();
		textField_penniesIn.setBounds(87, 140, 41, 22);
		textField_penniesIn.addActionListener(startValsChangedListener);
		add(textField_penniesIn);
		textField_penniesIn.setColumns(10);
		
		textField_nickelsIn = new JTextField();
		textField_nickelsIn.setBounds(87, 173, 41, 22);
		textField_nickelsIn.setColumns(10);
		textField_nickelsIn.addActionListener(startValsChangedListener);
		add(textField_nickelsIn);
		
		textField_dimesIn = new JTextField();
		textField_dimesIn.setBounds(87, 206, 41, 22);
		textField_dimesIn.setColumns(10);
		textField_dimesIn.addActionListener(startValsChangedListener);
		add(textField_dimesIn);
		
		textField_quartersIn = new JTextField();
		textField_quartersIn.setBounds(87, 239, 41, 22);
		textField_quartersIn.setColumns(10);
		textField_quartersIn.addActionListener(startValsChangedListener);
		add(textField_quartersIn);
		
		textField_dollarsIn = new JTextField();
		textField_dollarsIn.setBounds(87, 272, 41, 22);
		textField_dollarsIn.setColumns(10);
		textField_dollarsIn.addActionListener(startValsChangedListener);
		add(textField_dollarsIn);
		
		textField_twosIn = new JTextField();
		textField_twosIn.setBounds(87, 305, 41, 22);
		textField_twosIn.setColumns(10);
		textField_twosIn.addActionListener(startValsChangedListener);
		add(textField_twosIn);
		
		textField_fivesIn = new JTextField();
		textField_fivesIn.setBounds(87, 338, 41, 22);
		textField_fivesIn.setColumns(10);
		textField_fivesIn.addActionListener(startValsChangedListener);
		add(textField_fivesIn);
		
		textField_tensIn = new JTextField();
		textField_tensIn.setBounds(87, 371, 41, 22);
		textField_tensIn.setColumns(10);
		textField_tensIn.addActionListener(startValsChangedListener);
		add(textField_tensIn);
		
		textField_twentiesIn = new JTextField();
		textField_twentiesIn.setBounds(87, 404, 41, 22);
		textField_twentiesIn.setColumns(10);
		textField_twentiesIn.addActionListener(startValsChangedListener);
		add(textField_twentiesIn);
		
		textField_fiftiesIn = new JTextField();
		textField_fiftiesIn.setBounds(87, 437, 41, 22);
		textField_fiftiesIn.setColumns(10);
		textField_fiftiesIn.addActionListener(startValsChangedListener);
		add(textField_fiftiesIn);
		
		textField_hundredsIn = new JTextField();
		textField_hundredsIn.setBounds(87, 470, 41, 22);
		textField_hundredsIn.setColumns(10);
		textField_hundredsIn.addActionListener(startValsChangedListener);
		add(textField_hundredsIn);
		
		textArea_startTotal = new JTextArea();
		textArea_startTotal.setEditable(false);
		textArea_startTotal.setBounds(107, 635, 91, 27);
		textArea_startTotal.setBackground(Color.WHITE);
		add(textArea_startTotal);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(263, 139, 67, 22);
		textArea_1.setText("$0.01 X");
		textArea_1.setEditable(false);
		textArea_1.setBackground(SystemColor.menu);
		add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(263, 172, 67, 22);
		textArea_2.setText("$0.05 X");
		textArea_2.setEditable(false);
		textArea_2.setBackground(SystemColor.menu);
		add(textArea_2);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setBounds(263, 205, 67, 22);
		textArea_3.setText("$0.10 X");
		textArea_3.setEditable(false);
		textArea_3.setBackground(SystemColor.menu);
		add(textArea_3);
		
		JTextArea textArea_4 = new JTextArea();
		textArea_4.setBounds(263, 238, 67, 22);
		textArea_4.setText("$0.25 X");
		textArea_4.setEditable(false);
		textArea_4.setBackground(SystemColor.menu);
		add(textArea_4);
		
		JTextArea textArea_5 = new JTextArea();
		textArea_5.setBounds(263, 271, 67, 22);
		textArea_5.setText("$1    X");
		textArea_5.setEditable(false);
		textArea_5.setBackground(SystemColor.menu);
		add(textArea_5);
		
		JTextArea textArea_6 = new JTextArea();
		textArea_6.setBounds(263, 304, 67, 22);
		textArea_6.setText("$2    X");
		textArea_6.setEditable(false);
		textArea_6.setBackground(SystemColor.menu);
		add(textArea_6);
		
		JTextArea textArea_7 = new JTextArea();
		textArea_7.setBounds(263, 337, 67, 22);
		textArea_7.setText("$5    X");
		textArea_7.setEditable(false);
		textArea_7.setBackground(SystemColor.menu);
		add(textArea_7);
		
		JTextArea textArea_8 = new JTextArea();
		textArea_8.setBounds(263, 370, 67, 22);
		textArea_8.setText("$10   X");
		textArea_8.setEditable(false);
		textArea_8.setBackground(SystemColor.menu);
		add(textArea_8);
		
		JTextArea textArea_9 = new JTextArea();
		textArea_9.setBounds(263, 403, 67, 22);
		textArea_9.setText("$20   X");
		textArea_9.setEditable(false);
		textArea_9.setBackground(SystemColor.menu);
		add(textArea_9);
		
		JTextArea textArea_10 = new JTextArea();
		textArea_10.setBounds(263, 436, 67, 22);
		textArea_10.setText("$50   X");
		textArea_10.setEditable(false);
		textArea_10.setBackground(SystemColor.menu);
		add(textArea_10);
		
		JTextArea textArea_11 = new JTextArea();
		textArea_11.setBounds(263, 469, 67, 22);
		textArea_11.setText("$100  X");
		textArea_11.setEditable(false);
		textArea_11.setBackground(SystemColor.menu);
		add(textArea_11);
		
		btn_drops = new JButton("Add Drop to Cashier");
		btn_drops.setEnabled(true);
		btn_drops.setBounds(410,23,150,40);
		btn_drops.addActionListener(new DispatchListener());
		add(btn_drops);
		
		btn_initialCashDrop = new JButton("CONFIRM INITIAL CASH/TICKET DROP");
		btn_initialCashDrop.setBounds(10, 685, 252, 40);
		btn_initialCashDrop.addActionListener(confirmInitialDropListener);
		add(btn_initialCashDrop);
		
		textArea_penniesIn = new JTextArea();
		textArea_penniesIn.setBounds(160, 139, 67, 22);
		textArea_penniesIn.setText("$0.00");
		textArea_penniesIn.setEditable(false);
		textArea_penniesIn.setBackground(SystemColor.menu);
		add(textArea_penniesIn);
		
		textArea_nickelsIn = new JTextArea();
		textArea_nickelsIn.setBounds(160, 172, 67, 22);
		textArea_nickelsIn.setText("$0.00");
		textArea_nickelsIn.setEditable(false);
		textArea_nickelsIn.setBackground(SystemColor.menu);
		add(textArea_nickelsIn);
		
		textArea_dimesIn = new JTextArea();
		textArea_dimesIn.setBounds(160, 205, 67, 22);
		textArea_dimesIn.setText("$0.00");
		textArea_dimesIn.setEditable(false);
		textArea_dimesIn.setBackground(SystemColor.menu);
		add(textArea_dimesIn);
		
		textArea_quartersIn = new JTextArea();
		textArea_quartersIn.setBounds(160, 238, 67, 22);
		textArea_quartersIn.setText("$0.00");
		textArea_quartersIn.setEditable(false);
		textArea_quartersIn.setBackground(SystemColor.menu);
		add(textArea_quartersIn);
		
		textArea_dollarsIn = new JTextArea();
		textArea_dollarsIn.setBounds(160, 271, 67, 22);
		textArea_dollarsIn.setText("$0.00");
		textArea_dollarsIn.setEditable(false);
		textArea_dollarsIn.setBackground(SystemColor.menu);
		add(textArea_dollarsIn);
		
		textArea_twosIn = new JTextArea();
		textArea_twosIn.setBounds(160, 304, 67, 22);
		textArea_twosIn.setText("$0.00");
		textArea_twosIn.setEditable(false);
		textArea_twosIn.setBackground(SystemColor.menu);
		add(textArea_twosIn);
		
		textArea_fivesIn = new JTextArea();
		textArea_fivesIn.setBounds(160, 337, 67, 22);
		textArea_fivesIn.setText("$0.00");
		textArea_fivesIn.setEditable(false);
		textArea_fivesIn.setBackground(SystemColor.menu);
		add(textArea_fivesIn);
		
		textArea_tensIn = new JTextArea();
		textArea_tensIn.setBounds(160, 370, 67, 22);
		textArea_tensIn.setText("$0.00");
		textArea_tensIn.setEditable(false);
		textArea_tensIn.setBackground(SystemColor.menu);
		add(textArea_tensIn);
		
		textArea_twentiesIn = new JTextArea();
		textArea_twentiesIn.setBounds(160, 403, 67, 22);
		textArea_twentiesIn.setText("$0.00");
		textArea_twentiesIn.setEditable(false);
		textArea_twentiesIn.setBackground(SystemColor.menu);
		add(textArea_twentiesIn);
		
		textArea_fiftiesIn = new JTextArea();
		textArea_fiftiesIn.setBounds(160, 436, 67, 22);
		textArea_fiftiesIn.setText("$0.00");
		textArea_fiftiesIn.setEditable(false);
		textArea_fiftiesIn.setBackground(SystemColor.menu);
		add(textArea_fiftiesIn);
		
		textArea_hundredsIn = new JTextArea();
		textArea_hundredsIn.setBounds(160, 469, 67, 22);
		textArea_hundredsIn.setText("$0.00");
		textArea_hundredsIn.setEditable(false);
		textArea_hundredsIn.setBackground(SystemColor.menu);
		add(textArea_hundredsIn);
		
		textField_penniesOut = new JTextField();
		textField_penniesOut.setBounds(336, 139, 41, 22);
		textField_penniesOut.setColumns(10);
		textField_penniesOut.addActionListener(endValsChangedListener);
		add(textField_penniesOut);
		
		textField_nickelsOut = new JTextField();
		textField_nickelsOut.setBounds(336, 172, 41, 22);
		textField_nickelsOut.setColumns(10);
		textField_nickelsOut.addActionListener(endValsChangedListener);
		add(textField_nickelsOut);
		
		textField_dimesOut = new JTextField();
		textField_dimesOut.setBounds(336, 205, 41, 22);
		textField_dimesOut.setColumns(10);
		textField_dimesOut.addActionListener(endValsChangedListener);
		add(textField_dimesOut);
		
		textField_quartersOut = new JTextField();
		textField_quartersOut.setBounds(336, 238, 41, 22);
		textField_quartersOut.setColumns(10);
		textField_quartersOut.addActionListener(endValsChangedListener);
		add(textField_quartersOut);
		
		textField_dollarsOut = new JTextField();
		textField_dollarsOut.setBounds(336, 271, 41, 22);
		textField_dollarsOut.setColumns(10);
		textField_dollarsOut.addActionListener(endValsChangedListener);
		add(textField_dollarsOut);
		
		textField_twosOut = new JTextField();
		textField_twosOut.setBounds(336, 304, 41, 22);
		textField_twosOut.setColumns(10);
		textField_twosOut.addActionListener(endValsChangedListener);
		add(textField_twosOut);
		
		textField_fivesOut = new JTextField();
		textField_fivesOut.setBounds(336, 337, 41, 22);
		textField_fivesOut.setColumns(10);
		textField_fivesOut.addActionListener(endValsChangedListener);
		add(textField_fivesOut);
		
		textField_tensOut = new JTextField();
		textField_tensOut.setBounds(336, 370, 41, 22);
		textField_tensOut.setColumns(10);
		textField_tensOut.addActionListener(endValsChangedListener);
		add(textField_tensOut);
		
		textField_twentiesOut = new JTextField();
		textField_twentiesOut.setBounds(336, 403, 41, 22);
		textField_twentiesOut.setColumns(10);
		textField_twentiesOut.addActionListener(endValsChangedListener);
		add(textField_twentiesOut);
		
		textField_fiftiesOut = new JTextField();
		textField_fiftiesOut.setBounds(336, 436, 41, 22);
		textField_fiftiesOut.setColumns(10);
		textField_fiftiesOut.addActionListener(endValsChangedListener);
		add(textField_fiftiesOut);
		
		textField_hundredsOut = new JTextField();
		textField_hundredsOut.setBounds(336, 469, 41, 22);
		textField_hundredsOut.setColumns(10);
		textField_hundredsOut.addActionListener(endValsChangedListener);
		add(textField_hundredsOut);
		
		textArea_penniesOut = new JTextArea();
		textArea_penniesOut.setBounds(409, 139, 67, 22);
		textArea_penniesOut.setText("$0.00");
		textArea_penniesOut.setEditable(false);
		textArea_penniesOut.setBackground(SystemColor.menu);
		add(textArea_penniesOut);
		
		textArea_nickelsOut = new JTextArea();
		textArea_nickelsOut.setBounds(409, 173, 67, 22);
		textArea_nickelsOut.setText("$0.00");
		textArea_nickelsOut.setEditable(false);
		textArea_nickelsOut.setBackground(SystemColor.menu);
		add(textArea_nickelsOut);
		
		textArea_dimesOut = new JTextArea();
		textArea_dimesOut.setBounds(409, 205, 67, 22);
		textArea_dimesOut.setText("$0.00");
		textArea_dimesOut.setEditable(false);
		textArea_dimesOut.setBackground(SystemColor.menu);
		add(textArea_dimesOut);
		
		textArea_quartersOut = new JTextArea();
		textArea_quartersOut.setBounds(409, 237, 67, 22);
		textArea_quartersOut.setText("$0.00");
		textArea_quartersOut.setEditable(false);
		textArea_quartersOut.setBackground(SystemColor.menu);
		add(textArea_quartersOut);
		
		textArea_dollarsOut = new JTextArea();
		textArea_dollarsOut.setBounds(409, 271, 54, 22);
		textArea_dollarsOut.setText("$0.00");
		textArea_dollarsOut.setEditable(false);
		textArea_dollarsOut.setBackground(SystemColor.menu);
		add(textArea_dollarsOut);
		
		textArea_twosOut = new JTextArea();
		textArea_twosOut.setBounds(409, 304, 67, 22);
		textArea_twosOut.setText("$0.00");
		textArea_twosOut.setEditable(false);
		textArea_twosOut.setBackground(SystemColor.menu);
		add(textArea_twosOut);
		
		textArea_fivesOut = new JTextArea();
		textArea_fivesOut.setBounds(409, 337, 67, 22);
		textArea_fivesOut.setText("$0.00");
		textArea_fivesOut.setEditable(false);
		textArea_fivesOut.setBackground(SystemColor.menu);
		add(textArea_fivesOut);
		
		textArea_tensOut = new JTextArea();
		textArea_tensOut.setBounds(409, 370, 67, 22);
		textArea_tensOut.setText("$0.00");
		textArea_tensOut.setEditable(false);
		textArea_tensOut.setBackground(SystemColor.menu);
		add(textArea_tensOut);
		
		textArea_twentiesOut = new JTextArea();
		textArea_twentiesOut.setBounds(409, 403, 67, 22);
		textArea_twentiesOut.setText("$0.00");
		textArea_twentiesOut.setEditable(false);
		textArea_twentiesOut.setBackground(SystemColor.menu);
		add(textArea_twentiesOut);
		
		textArea_fiftiesOut = new JTextArea();
		textArea_fiftiesOut.setBounds(409, 436, 67, 22);
		textArea_fiftiesOut.setText("$0.00");
		textArea_fiftiesOut.setEditable(false);
		textArea_fiftiesOut.setBackground(SystemColor.menu);
		add(textArea_fiftiesOut);
		
		textArea_hundredsOut = new JTextArea();
		textArea_hundredsOut.setBounds(409, 469, 67, 22);
		textArea_hundredsOut.setText("$0.00");
		textArea_hundredsOut.setEditable(false);
		textArea_hundredsOut.setBackground(SystemColor.menu);
		add(textArea_hundredsOut);
		
		textArea_endTotal = new JTextArea();
		textArea_endTotal.setEditable(false);
		textArea_endTotal.setBounds(336, 635, 91, 27);
		textArea_endTotal.setBackground(Color.WHITE);
		add(textArea_endTotal);
		
		//spinner_clubSelection = new JSpinner();
		
		ArrayList<String> clubsArray = getClubs();
		SpinnerListModel clubsModel = new SpinnerListModel(clubsArray);
		spinner_clubSelection_1 = new JSpinner(clubsModel);
		spinner_clubSelection_1.setBounds(147, 23, 252, 40);
		spinner_clubSelection_1.addChangeListener(new ClubSpinnerListener());
		add(spinner_clubSelection_1);
		clubSelected = spinner_clubSelection_1.getValue().toString();
		actualClub = findActualClub(clubSelected);
		
		JTextArea txtrBooth = new JTextArea();
		txtrBooth.setBounds(10, 31, 121, 27);
		txtrBooth.setBackground(SystemColor.control);
		txtrBooth.setEditable(false);
		txtrBooth.setText("      CASHIER:");
		add(txtrBooth);
		
		textArea_endTotalCalc = new JTextArea();
		textArea_endTotalCalc.setBounds(699, 172, 75, 27);
		textArea_endTotalCalc.setEditable(false);
		textArea_endTotalCalc.setBackground(SystemColor.menu);
		add(textArea_endTotalCalc);
		
		textArea_finalTotal = new JTextArea();
		textArea_finalTotal.setBounds(683, 238, 91, 27);
		textArea_finalTotal.setEditable(false);
		textArea_finalTotal.setBackground(SystemColor.menu);
		add(textArea_finalTotal);
		
		textArea_cashDrops = new JTextArea();
		textArea_cashDrops.setBounds(577, 139, 41, 27);
		textArea_cashDrops.setBackground(SystemColor.control);
		textArea_cashDrops.setEditable(false);
		add(textArea_cashDrops);
		
		textArea_cashDropsBy800 = new JTextArea();
		textArea_cashDropsBy800.setBounds(699, 139, 61, 27);
		textArea_cashDropsBy800.setBackground(SystemColor.control);
		textArea_cashDropsBy800.setEditable(false);
		add(textArea_cashDropsBy800);
		
		textField_fullSheetsUnsold = new JTextField();
		textField_fullSheetsUnsold.setBounds(346, 502, 31, 22);
		textField_fullSheetsUnsold.setColumns(10);
		textField_fullSheetsUnsold.addActionListener(endValsChangedListener);
		add(textField_fullSheetsUnsold);
		
		textField_halfSheetsUnsold = new JTextField();
		textField_halfSheetsUnsold.setBounds(346, 535, 31, 22);
		textField_halfSheetsUnsold.setColumns(10);
		textField_halfSheetsUnsold.addActionListener(endValsChangedListener);
		add(textField_halfSheetsUnsold);
		
		textField_singleTicketsUnsold = new JTextField();
		textField_singleTicketsUnsold.setBounds(346, 568, 31, 22);
		textField_singleTicketsUnsold.setColumns(10);
		textField_singleTicketsUnsold.addActionListener(endValsChangedListener);
		add(textField_singleTicketsUnsold);
		
		textArea_soldTickets = new JTextArea();
		textArea_soldTickets.setBounds(699, 403, 61, 22);
		textArea_soldTickets.setEditable(false);
		add(textArea_soldTickets);
		
		textArea_expectedRevenue = new JTextArea();
		textArea_expectedRevenue.setBounds(657, 693, 102, 22);
		textArea_expectedRevenue.setBackground(Color.WHITE);
		textArea_expectedRevenue.setEditable(false);
		add(textArea_expectedRevenue);
		
		textArea_issuedTickets = new JTextArea();
		textArea_issuedTickets.setBounds(699, 337, 61, 22);
		textArea_issuedTickets.setBackground(Color.WHITE);
		textArea_issuedTickets.setEditable(false);
		add(textArea_issuedTickets);
		
		JLabel lblNewLabel = new JLabel("Location:");
		lblNewLabel.setBounds(23, 78, 54, 14);
		add(lblNewLabel);
		
		textArea_soldWristbands = new JTextArea();
		textArea_soldWristbands.setBounds(699, 600, 61, 22);
		add(textArea_soldWristbands);
		
		ArrayList<String> locationsArray = getLocations();
		SpinnerListModel locationsModel = new SpinnerListModel(locationsArray);
		spinner_locations = new JSpinner(locationsModel);
		spinner_locations.setBounds(87, 69, 162, 33);
		spinner_locations.addChangeListener(locationSpinnerListener);
		location = spinner_locations.getValue().toString();
		add(spinner_locations);
		
		textArea_issuedWristbands = new JTextArea();
		textArea_issuedWristbands.setBounds(699, 534, 61, 22);
		textArea_issuedWristbands.setBackground(Color.WHITE);
		textArea_issuedWristbands.setEditable(false);
		add(textArea_issuedWristbands);
		
		textField_wristbandsUnsold = new JTextField();
		textField_wristbandsUnsold.setBounds(346, 601, 31, 22);
		textField_wristbandsUnsold.setColumns(10);
		textField_wristbandsUnsold.addActionListener(endValsChangedListener);
		add(textField_wristbandsUnsold);
		
		textField_fullSheetsIn = new JTextField();
		textField_fullSheetsIn.setColumns(10);
		textField_fullSheetsIn.setBounds(97, 503, 31, 22);
		textField_fullSheetsIn.setText("0");
		textField_fullSheetsIn.addActionListener(startValsChangedListener);
		add(textField_fullSheetsIn);
		
		textField_halfSheetsIn = new JTextField();
		textField_halfSheetsIn.setColumns(10);
		textField_halfSheetsIn.setBounds(97, 536, 31, 22);
		textField_halfSheetsIn.setText("0");
		textField_halfSheetsIn.addActionListener(startValsChangedListener);
		add(textField_halfSheetsIn);
		
		textField_singleTicketsIn = new JTextField();
		textField_singleTicketsIn.setColumns(10);
		textField_singleTicketsIn.setBounds(97, 569, 31, 22);
		textField_singleTicketsIn.setText("0");
		textField_singleTicketsIn.addActionListener(startValsChangedListener);
		add(textField_singleTicketsIn);
		
		textField_wristbandsIn = new JTextField();
		textField_wristbandsIn.setColumns(10);
		textField_wristbandsIn.setBounds(97, 602, 31, 22);
		textField_wristbandsIn.setText("0");
		textField_wristbandsIn.addActionListener(startValsChangedListener);
		add(textField_wristbandsIn);
		
		JTextArea textArea_fullSheetsIn = new JTextArea();
		textArea_fullSheetsIn.setText("0");
		textArea_fullSheetsIn.setEditable(false);
		textArea_fullSheetsIn.setBackground(SystemColor.menu);
		textArea_fullSheetsIn.setBounds(160, 502, 41, 22);
		add(textArea_fullSheetsIn);
		
		JTextArea textArea_halfSheetsIn = new JTextArea();
		textArea_halfSheetsIn.setText("0");
		textArea_halfSheetsIn.setEditable(false);
		textArea_halfSheetsIn.setBackground(SystemColor.menu);
		textArea_halfSheetsIn.setBounds(160, 535, 31, 22);
		add(textArea_halfSheetsIn);
		
		JTextArea textArea_singleTicketsIn = new JTextArea();
		textArea_singleTicketsIn.setText("0");
		textArea_singleTicketsIn.setEditable(false);
		textArea_singleTicketsIn.setBackground(SystemColor.menu);
		textArea_singleTicketsIn.setBounds(160, 568, 31, 22);
		add(textArea_singleTicketsIn);
		
		JTextArea textArea_wristbandsin = new JTextArea();
		textArea_wristbandsin.setText("0");
		textArea_wristbandsin.setEditable(false);
		textArea_wristbandsin.setBackground(SystemColor.menu);
		textArea_wristbandsin.setBounds(160, 601, 31, 22);
		add(textArea_wristbandsin);
		
		textArea_fullSheetsOut = new JTextArea();
		textArea_fullSheetsOut.setText("0");
		textArea_fullSheetsOut.setEditable(false);
		textArea_fullSheetsOut.setBackground(SystemColor.menu);
		textArea_fullSheetsOut.setBounds(409, 502, 67, 22);
		add(textArea_fullSheetsOut);
		
		textArea_halfSheetsOut = new JTextArea();
		textArea_halfSheetsOut.setText("0");
		textArea_halfSheetsOut.setEditable(false);
		textArea_halfSheetsOut.setBackground(SystemColor.menu);
		textArea_halfSheetsOut.setBounds(409, 535, 67, 22);
		add(textArea_halfSheetsOut);
		
		textArea_singleTicketsOut = new JTextArea();
		textArea_singleTicketsOut.setText("0");
		textArea_singleTicketsOut.setEditable(false);
		textArea_singleTicketsOut.setBackground(SystemColor.menu);
		textArea_singleTicketsOut.setBounds(409, 568, 67, 22);
		add(textArea_singleTicketsOut);
		
		textArea_wristbandsOut = new JTextArea();
		textArea_wristbandsOut.setText("0");
		textArea_wristbandsOut.setEditable(false);
		textArea_wristbandsOut.setBackground(SystemColor.menu);
		textArea_wristbandsOut.setBounds(409, 601, 67, 22);
		add(textArea_wristbandsOut);
		
		JLabel lblFullSheetsX = new JLabel("Full Sheets");
		lblFullSheetsX.setBounds(10, 502, 77, 22);
		add(lblFullSheetsX);
		
		JLabel lblNewLabel_4 = new JLabel("Half Sheets");
		lblNewLabel_4.setBounds(10, 535, 77, 22);
		add(lblNewLabel_4);
		
		JLabel lblSinglesX = new JLabel("Single Tickets");
		lblSinglesX.setBounds(10, 569, 81, 22);
		add(lblSinglesX);
		
		JLabel lblWristbandsX = new JLabel("Wristbands ");
		lblWristbandsX.setBounds(10, 602, 81, 22);
		add(lblWristbandsX);
		
	
		
		textArea_startTotal2 = new JTextArea();
		textArea_startTotal2.setEditable(false);
		textArea_startTotal2.setBackground(SystemColor.menu);
		textArea_startTotal2.setBounds(699, 205, 75, 27);
		add(textArea_startTotal2);
		
		JLabel lblUnsoldFullSheets = new JLabel("Unsold Full Sheets");
		lblUnsoldFullSheets.setBounds(201, 502, 110, 22);
		add(lblUnsoldFullSheets);
		
		JLabel lblUnsoldHalfSheets = new JLabel(" Unsold Half Sheets");
		lblUnsoldHalfSheets.setBounds(207, 535, 114, 22);
		add(lblUnsoldHalfSheets);
		
		JLabel lblUnsoldSingleTickets = new JLabel("  Unsold Single Tickets");
		lblUnsoldSingleTickets.setBounds(201, 568, 129, 22);
		add(lblUnsoldSingleTickets);
		
		JLabel lblUnsoldWristbandsX = new JLabel(" Unsold Wristbands");
		lblUnsoldWristbandsX.setBounds(207, 601, 123, 22);
		add(lblUnsoldWristbandsX);
		
		JLabel lblInitialTickets = new JLabel("Initial Tickets:");
		lblInitialTickets.setBounds(577, 303, 112, 27);
		add(lblInitialTickets);
		
		JLabel lblIssuedTickets = new JLabel("+ Issued Tickets:");
		lblIssuedTickets.setBounds(577, 336, 112, 27);
		add(lblIssuedTickets);
		
		JLabel lblUnsoldTickets = new JLabel("- Unsold Tickets:");
		lblUnsoldTickets.setBounds(577, 369, 112, 27);
		add(lblUnsoldTickets);
		
		JLabel lblInitialWristbands = new JLabel("     Initial Wristbands:");
		lblInitialWristbands.setBounds(548, 500, 141, 27);
		add(lblInitialWristbands);
		
		JLabel lblIssuedWristbands = new JLabel("+ Issued Wristbands:");
		lblIssuedWristbands.setBounds(560, 533, 129, 27);
		add(lblIssuedWristbands);
		
		JLabel lblUnsoldWristbands = new JLabel("- Unsold Wristbands:");
		lblUnsoldWristbands.setBounds(560, 566, 129, 27);
		add(lblUnsoldWristbands);
		
		JLabel lblexpectedRevenue = new JLabel("Ticket Sales + Wristband Sales = EXPECTED REVENUE:");
		lblexpectedRevenue.setBounds(296, 692, 351, 27);
		add(lblexpectedRevenue);
		
		JLabel lblTicketsSold = new JLabel("  = Tickets Sold:");
		lblTicketsSold.setBounds(577, 402, 112, 27);
		add(lblTicketsSold);
		
		JLabel lblX_1 = new JLabel("X $0.50 = Ticket Sales :");
		lblX_1.setBounds(548, 435, 141, 27);
		add(lblX_1);
		
		JLabel lblWristbandsSold = new JLabel("= Wristbands Sold:");
		lblWristbandsSold.setBounds(560, 599, 129, 27);
		add(lblWristbandsSold);
		
		JLabel lblNewLabel_5 = new JLabel("INITIAL CASH =");
		lblNewLabel_5.setBounds(10, 634, 100, 27);
		add(lblNewLabel_5);
		
		JLabel lblCollectedCash = new JLabel("COLLECTED CASH =");
		lblCollectedCash.setBounds(208, 636, 128, 22);
		add(lblCollectedCash);
		
		JLabel lblX_2 = new JLabel("X");
		lblX_2.setBounds(474, 634, 12, 27);
		add(lblX_2);
		
		ArrayList<String> wbValueArray = new ArrayList<String>();
		wbValueArray.add("$30");
		wbValueArray.add("$20");
		SpinnerListModel wbValueModel = new SpinnerListModel(wbValueArray);
		spinner_wbValue = new JSpinner(wbValueModel);
		spinner_wbValue.setBounds(496, 636, 64, 23);
		spinner_wbValue.addChangeListener(wristbandMultiplierListener);
		wristbandMultiplier = Integer.parseInt(spinner_wbValue.getValue().toString().substring(1));
		add(spinner_wbValue);
		
		JLabel lblWristbandSales = new JLabel("= Wristband Sales:");
		lblWristbandSales.setBounds(566, 634, 123, 27);
		add(lblWristbandSales);
		
		textArea_initialtickets = new JTextArea();
		textArea_initialtickets.setEditable(false);
		textArea_initialtickets.setBounds(699, 304, 61, 22);
		add(textArea_initialtickets);
		
		textArea_unsoldTickets = new JTextArea();
		textArea_unsoldTickets.setEditable(false);
		textArea_unsoldTickets.setBounds(699, 370, 61, 22);
		add(textArea_unsoldTickets);
		
		textArea_ticketSales = new JTextArea();
		textArea_ticketSales.setEditable(false);
		textArea_ticketSales.setBounds(699, 436, 61, 22);
		add(textArea_ticketSales);
		
		textArea_initialWristbands = new JTextArea();
		textArea_initialWristbands.setEditable(false);
		textArea_initialWristbands.setBounds(699, 501, 61, 22);
		add(textArea_initialWristbands);
		
		textArea_unsoldWristbands = new JTextArea();
		textArea_unsoldWristbands.setEditable(false);
		textArea_unsoldWristbands.setBackground(Color.WHITE);
		textArea_unsoldWristbands.setBounds(699, 567, 61, 22);
		add(textArea_unsoldWristbands);
		
		textArea_wristbandSales = new JTextArea();
		textArea_wristbandSales.setEditable(false);
		textArea_wristbandSales.setBackground(Color.WHITE);
		textArea_wristbandSales.setBounds(699, 635, 61, 22);
		add(textArea_wristbandSales);
		
		JLabel lblFridayWristbandsAre = new JLabel("*Friday, wristbands are $30, Sunday, they're $20");
		lblFridayWristbandsAre.setBounds(460, 668, 300, 14);
		add(lblFridayWristbandsAre);
		
		JLabel lblMiscellaneousCredits = new JLabel("- Miscellaneous Credits and Promotions:");
		lblMiscellaneousCredits.setBounds(409, 731, 238, 14);
		add(lblMiscellaneousCredits);
		
		textArea_expectedEndingCash = new JTextArea();
		textArea_expectedEndingCash.setEditable(false);
		textArea_expectedEndingCash.setBackground(Color.WHITE);
		textArea_expectedEndingCash.setBounds(658, 765, 102, 22);
		add(textArea_expectedEndingCash);
		
		JLabel lblExpectedEnding = new JLabel("= EXPECTED ENDING CASH");
		lblExpectedEnding.setBounds(474, 770, 173, 14);
		add(lblExpectedEnding);
		
		JLabel lblCollectedRevenue = new JLabel("COLLECTED REVENUE - EXPECTED ENDING CASH = DIFFERENCES: ");
		lblCollectedRevenue.setBounds(237, 810, 410, 14);
		add(lblCollectedRevenue);
		
		textArea_differences = new JTextArea();
		textArea_differences.setEditable(false);
		textArea_differences.setBackground(Color.WHITE);
		textArea_differences.setBounds(657, 805, 102, 22);
		add(textArea_differences);
		
		JLabel lblCashDrops = new JLabel("Cash Drops");
		lblCashDrops.setBounds(500, 144, 67, 14);
		add(lblCashDrops);
		
		JButton btnCheckOutCashier = new JButton("CONFIRM CHECK OUT VALUES AND REMOVE CASHIER FROM ACTIVE LIST");
		btnCheckOutCashier.setBounds(237, 835, 523, 40);
		btnCheckOutCashier.addActionListener(confirmCheckOutCashierListener);
		add(btnCheckOutCashier);
		
		textField_credits = new JTextField();
		textField_credits.setColumns(10);
		textField_credits.setBounds(657, 726, 103, 22);
		textField_credits.addActionListener(endValsChangedListener);
		add(textField_credits);
		
		JCheckBox chckbx_creditTerminal = new JCheckBox("Check if cashier is on a credit terminal");
		chckbx_creditTerminal.setBounds(285, 74, 267, 23);
		add(chckbx_creditTerminal);
		chckbx_creditTerminal.setVisible(false);
		chckbx_creditTerminal.setEnabled(false);
		
		
	    Vector<Component> order = new Vector<Component>(22);
	    order.add(textField_penniesIn);
	    order.add(textField_nickelsIn);
	    order.add(textField_dimesIn);
	    order.add(textField_quartersIn);
	    order.add(textField_dollarsIn);
	    order.add(textField_twosIn);
	    order.add(textField_fivesIn);
	    order.add(textField_tensIn);
	    order.add(textField_twentiesIn);
	    order.add(textField_fiftiesIn);
	    order.add(textField_hundredsIn);
	    
	    order.add(textField_fullSheetsIn);
	    order.add(textField_halfSheetsIn);
	    order.add(textField_singleTicketsIn);
	    order.add(textField_wristbandsIn);
	    
	    order.add(textField_penniesOut);
	    order.add(textField_nickelsOut);
	    order.add(textField_dimesOut);
	    order.add(textField_quartersOut);
	    order.add(textField_dollarsOut);
	    order.add(textField_twosOut);
	    order.add(textField_fivesOut);
	    order.add(textField_tensOut);
	    order.add(textField_twentiesOut);
	    order.add(textField_fiftiesOut);
	    order.add(textField_hundredsOut);
	    
	    order.add(textField_fullSheetsUnsold);
	    order.add(textField_halfSheetsUnsold);
	    order.add(textField_singleTicketsUnsold);
	    order.add(textField_wristbandsUnsold);
	    
	    order.add(textField_credits);
	    
	    JLabel lblCollectedRevenue_1 = new JLabel("= Collected Revenue:");
	    lblCollectedRevenue_1.setBounds(514, 243, 159, 14);
	    add(lblCollectedRevenue_1);
	    
	    JLabel lblCashIn = new JLabel("CASH IN");
	    lblCashIn.setBounds(23, 114, 77, 14);
	    add(lblCashIn);
	    
	    JLabel lblCashOut = new JLabel("CASH OUT");
	    lblCashOut.setBounds(279, 114, 73, 14);
	    add(lblCashOut);
	    
	    JLabel label = new JLabel("=");
	    label.setBounds(138, 144, 12, 14);
	    add(label);
	    
	    JLabel label_1 = new JLabel("=");
	    label_1.setBounds(138, 177, 12, 14);
	    add(label_1);
	    
	    JLabel label_2 = new JLabel("=");
	    label_2.setBounds(138, 210, 12, 14);
	    add(label_2);
	    
	    JLabel label_3 = new JLabel("=");
	    label_3.setBounds(138, 243, 12, 14);
	    add(label_3);
	    
	    JLabel label_4 = new JLabel("=");
	    label_4.setBounds(138, 276, 12, 14);
	    add(label_4);
	    
	    JLabel label_5 = new JLabel("=");
	    label_5.setBounds(138, 309, 12, 14);
	    add(label_5);
	    
	    JLabel label_6 = new JLabel("=");
	    label_6.setBounds(138, 342, 12, 14);
	    add(label_6);
	    
	    JLabel label_7 = new JLabel("=");
	    label_7.setBounds(138, 375, 12, 14);
	    add(label_7);
	    
	    JLabel label_8 = new JLabel("=");
	    label_8.setBounds(138, 408, 12, 14);
	    add(label_8);
	    
	    JLabel label_9 = new JLabel("=");
	    label_9.setBounds(138, 441, 12, 14);
	    add(label_9);
	    
	    JLabel label_10 = new JLabel("=");
	    label_10.setBounds(138, 474, 12, 14);
	    add(label_10);
	    
	    JLabel label_11 = new JLabel("=");
	    label_11.setBounds(138, 506, 12, 14);
	    add(label_11);
	    
	    JLabel label_12 = new JLabel("=");
	    label_12.setBounds(138, 539, 12, 14);
	    add(label_12);
	    
	    JLabel label_13 = new JLabel("=");
	    label_13.setBounds(138, 572, 12, 14);
	    add(label_13);
	    
	    JLabel label_14 = new JLabel("=");
	    label_14.setBounds(138, 605, 12, 14);
	    add(label_14);
	    
	    JLabel label_15 = new JLabel("=");
	    label_15.setBounds(387, 144, 12, 14);
	    add(label_15);
	    
	    JLabel label_16 = new JLabel("=");
	    label_16.setBounds(387, 177, 12, 14);
	    add(label_16);
	    
	    JLabel label_17 = new JLabel("=");
	    label_17.setBounds(387, 210, 12, 14);
	    add(label_17);
	    
	    JLabel label_18 = new JLabel("=");
	    label_18.setBounds(387, 243, 12, 14);
	    add(label_18);
	    
	    JLabel label_19 = new JLabel("=");
	    label_19.setBounds(387, 276, 12, 14);
	    add(label_19);
	    
	    JLabel label_20 = new JLabel("=");
	    label_20.setBounds(387, 309, 12, 14);
	    add(label_20);
	    
	    JLabel label_21 = new JLabel("=");
	    label_21.setBounds(387, 342, 12, 14);
	    add(label_21);
	    
	    JLabel label_22 = new JLabel("=");
	    label_22.setBounds(387, 375, 12, 14);
	    add(label_22);
	    
	    JLabel label_23 = new JLabel("=");
	    label_23.setBounds(387, 408, 12, 14);
	    add(label_23);
	    
	    JLabel label_24 = new JLabel("=");
	    label_24.setBounds(387, 441, 12, 14);
	    add(label_24);
	    
	    JLabel label_25 = new JLabel("=");
	    label_25.setBounds(387, 474, 12, 14);
	    add(label_25);
	    
	    JLabel label_26 = new JLabel("=");
	    label_26.setBounds(387, 506, 12, 14);
	    add(label_26);
	    
	    JLabel label_27 = new JLabel("=");
	    label_27.setBounds(387, 539, 12, 14);
	    add(label_27);
	    
	    JLabel label_28 = new JLabel("=");
	    label_28.setBounds(387, 572, 12, 14);
	    add(label_28);
	    
	    JLabel label_29 = new JLabel("=");
	    label_29.setBounds(387, 605, 12, 14);
	    add(label_29);
	        
	    JLabel lblX = new JLabel("x $800 =");
	    lblX.setBounds(628, 144, 67, 14);
	    add(lblX);
	    newPolicy = new MyOwnFocusTraversalPolicy(order);
	    this.setFocusTraversalPolicyProvider(true);
	    
	    JLabel lblCollectedCash_1 = new JLabel("Collected Cash:");
	    lblCollectedCash_1.setBounds(531, 177, 91, 14);
	    add(lblCollectedCash_1);
	    
	    JLabel lblNewLabel_1 = new JLabel("- Initial Cash: ");
	    lblNewLabel_1.setBounds(541, 210, 77, 14);
	    add(lblNewLabel_1);
	    this.setFocusTraversalPolicy(newPolicy);
	}

		
	
//    order.add(spinner_clubSelection_1);
//    order.add(spinner_locations);
//    order.add(textField_penniesIn);
//    order.add(textField_nickelsIn);
//    order.add(textField_dimesIn);
//    order.add(textField_quartersIn);
//    order.add(textField_dollarsIn);
//	
	
	public class MyOwnFocusTraversalPolicy extends FocusTraversalPolicy{
		 Vector<Component> order;
	
		public MyOwnFocusTraversalPolicy(Vector<Component> order) {
			this.order = new Vector<Component>(order.size());
			this.order.addAll(order);
		}
		public Component getComponentAfter(Container focusCycleRoot, Component aComponent){
			int idx = (order.indexOf(aComponent) + 1) % order.size();
			return order.get(idx);
		}
		
		public Component getComponentBefore(Container focusCycleRoot, Component aComponent){
			int idx = order.indexOf(aComponent) - 1;
			if (idx < 0) {
			   idx = order.size() - 1;
			}
			return order.get(idx);
		}
		
		public Component getDefaultComponent(Container focusCycleRoot) {
			return order.get(0);
		}
		
		public Component getLastComponent(Container focusCycleRoot) {
			return order.lastElement();
		}
		
		public Component getFirstComponent(Container focusCycleRoot) {
			return order.get(0);
		}
	}
	
	private ArrayList<String> getLocations() {
		ArrayList<String> locations = new ArrayList<String>();
		locations.add("Castle 1");
		locations.add("Castle 2");
		locations.add("Castle 3");
		locations.add("Castle 4");
		locations.add("Cottage 1");
		locations.add("Cottage 2");
		locations.add("Cottage 3");
		locations.add("Wonderland");
		locations.add("Wishing Well");
		locations.add("Pride Rock");
		locations.add("Palace");
		locations.add("Enchanted Forest");
		locations.add("Swamp");
		locations.add("Woods");
		locations.add("Beanstalk");
		return locations;
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
