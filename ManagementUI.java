package edu.tridenttech.cpt237.williams;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
/**
 * 
 * @author Alexis Williams
 * The console for the management side of the Acme Taxi Company.
 *
 */
public class ManagementUI extends JFrame implements ActionListener, MaintenanceListener
{
	ExtendedCab cabPlus;
	private JTextField results;
	private JButton okBttn;	
	private JButton allowFare;
	private JLabel serviceNeeded;
	private JComboBox<String> management;
	private String giveNet = "Report Net Earnings";
	private String giveGross = "Report Gross Earnings";
	private String giveDriven = "Total Miles Driven";
	private String giveReset = "Reset";
	private String[] manageOptions = {giveNet, giveGross, giveDriven, giveReset};
		
	public ManagementUI(ExtendedCab c)
	{
		cabPlus = c;
		createUI();
		cabPlus.addMaintenanceListener(this);
	}

	private void createUI() 
	{
		JFrame frame = new JFrame("Acme Taxi Co.");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(500, 100, 300, 170);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JLabel options = new JLabel("What report would you like to see?");
		panel.add(options);
		management = new JComboBox<String>(manageOptions);
		management.setMaximumSize(new Dimension(Integer.MAX_VALUE, management.getMinimumSize().height));
		panel.add(management);
		
		results = new JTextField();
		results.setMaximumSize(new Dimension(Integer.MAX_VALUE, results.getMinimumSize().height));
		panel.add(results);
		
		okBttn = new JButton("OK");
		okBttn.addActionListener(this);	
		panel.add(okBttn);
		
		allowFare = new JButton("Fare Override");
		allowFare.addActionListener(this);
		allowFare.setVisible(false);
		panel.add(allowFare);
		
		serviceNeeded = new JLabel("Need maintenance");
		serviceNeeded.setVisible(false);
		panel.add(serviceNeeded);
		
		frame.add(panel);		
	}
	
	/**
	 *  actionPerformed: method that activates when the ok button is pushed.
	 *  it then stores the selected item from the combo box into a string.
	 * 	the string gets passed to the itemChosen method.
	 * 	It also adds an extra fare when the conditions are met for the Fare Override button to appear. 
	 *  @param e: the ActionEvent activated
	 */
	
	public void actionPerformed(ActionEvent e) 
	{
		JButton button = (JButton)e.getSource();			
			if(button == allowFare)
			{
				cabPlus.fareOverride(false);
			}				
			else
			{
				String selection = (String) management.getSelectedItem();	
				itemChosen(selection);
			}					
	}
	
	/**
	 *	itemStateChanged: method that checks what the user has currently selected in the combo box. 
	 *	It then performs what the selection entails.
	 */
	private void itemChosen(String c) 
	{
		if(c == giveNet)
		{
			double netEarnings = cabPlus.getNetEarnings();
			
			results.setText(String.format("$%.2f", netEarnings));			
		}
		
		else if(c == giveGross)
		{
			double grossEarnings = cabPlus.getGrossEarnings();
			
			results.setText(String.format("$%.2f", grossEarnings));			
		}
		
		else if(c == giveDriven)
		{
			int miles = cabPlus.getMilesSinceReset();
			String milesString = String.valueOf(miles);
									
			results.setText(milesString);		
		}
		
		else if(c == giveReset)
		{						
			cabPlus.reset();
			
			results.setText("");
		}				
	}
	
	/**
	 * serviceStatus: checks the status of the cab via a boolean flag
	 * if false, then the fare override button appears and a fare can be made to the cab.
	 * if true, then the fare override button stays hidden. 
	 */
	public void serviceStatus(boolean t) 
	{					
		if(t == true) //if cab has been serviced
		{
			serviceNeeded.setVisible(false);
			allowFare.setVisible(false);
		}
		else if(t == false) //if cab needs service
		{
			serviceNeeded.setVisible(true);
			allowFare.setVisible(true);
		}
	}
	
}
