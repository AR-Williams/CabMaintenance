package edu.tridenttech.cpt237.williams;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Alexis Williams 
 * The console for the driver of the Acme Taxi Company. 
 *
 */
@SuppressWarnings("serial")
public class DriverUI extends JFrame implements ActionListener, ItemListener, MaintenanceListener
{
	private ExtendedCab cabPlus;
	private JTextField gasInput;
	private JTextField inputField;
	private JLabel gasPriceLabel;
	private JLabel serviceNeeded;
	private JButton okBttn;	
	private JComboBox<String> cabServices;
	private String mileage = "Enter mileage";
	private String moreGas = "Add Gas";
	private String haveMiles = "Report Miles Available";
	private String haveGas = "Report Gas Available";
	private String haveDriven = "Report Miles Driven";
	private String haveService = "Service Cab";
	private String[] serviceOptions = { mileage, moreGas, haveMiles, haveGas, haveDriven, haveService};
		
	
	public DriverUI(ExtendedCab c)
	{
		cabPlus = c;
		createUI();
		cabPlus.addMaintenanceListener(this);
	}
	
	public void createUI()
	{		
		JFrame frame = new JFrame("Acme Taxi Co.");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(500, 300, 250, 170);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JLabel options = new JLabel("Select the desired service:");
		panel.add(options);
		cabServices = new JComboBox<String>(serviceOptions);
		cabServices.setMaximumSize(new Dimension(Integer.MAX_VALUE, cabServices.getMinimumSize().height));
		cabServices.addItemListener(this);
		panel.add(cabServices);
		
		inputField = new JTextField();
		inputField.setToolTipText("Enter gas or mileage here");
		inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getMinimumSize().height));
		panel.add(inputField);
		
		gasPriceLabel = new JLabel("Enter price ger gallon here:");
		gasPriceLabel.setVisible(false);
		gasInput = new JTextField();
		gasInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, gasInput.getMinimumSize().height));
		gasInput.setToolTipText("Enter gas price here");
		gasInput.setEnabled(false);
		panel.add(gasPriceLabel);
		panel.add(gasInput);

		okBttn = new JButton("OK");
		okBttn.addActionListener(this);	
		panel.add(okBttn);
		
		serviceNeeded = new JLabel("Need maintenance");
		serviceNeeded.setVisible(false);
		panel.add(serviceNeeded);
		
		frame.add(panel);		
	}
		
	/**
	 *  actionPerformed: method that activates when the ok button is pushed.
	 *  it then stores the selected item from the combo box into a string.
	 * 	the string gets passed to the itemChosen method.
	 *  @param e: the ActionEvent activated
	 */
	
	public void actionPerformed(ActionEvent e) 
	{		
		String selection = (String) cabServices.getSelectedItem();
		
		itemChosen(selection);		
	}
		
	
	/**
	 *	itemStateChanged: method that checks what the user has currently selected in the combo box. 
	 *	It's primary use is to see whether or not the second input field needs to be enabled based on this selection 
	 *	(it only enables when "Enter Gas Amount" is chosen) 
	 */
		
	public void itemStateChanged(ItemEvent e) 
	{
		
		String item = (String) e.getItem();
		if (e.getStateChange() == ItemEvent.SELECTED && item == moreGas)
		 {
			gasInput.setEnabled(true);	
			gasPriceLabel.setVisible(true);
			inputField.setText("");
		 }
		else
		{
			gasInput.setEnabled(false);
			gasPriceLabel.setVisible(false);
		}
	}
	
	public void itemChosen(String ee)
	{				
		if(ee == mileage)
		{
			if(inputField.getText() != null)
			{
				int miles = 0;								
				miles = Integer.parseInt(inputField.getText());

				cabPlus.recordTrip(miles);														
			}//end if 			
		}//end if
		
		else if(ee == moreGas)
		{
			if(inputField.getText() != null && gasInput.getText() != null)
			{
				double gas = 0;
				double cost = 0;
				String gasString = inputField.getText();
				String costString = gasInput.getText();
				
				gas = Double.parseDouble(gasString);
				cost = Double.parseDouble(costString);
								
				cabPlus.addGas(gas, cost);
			}//end if			
		}//end else if
				
		else if(ee == haveGas)
		{
			double gas = cabPlus.getGasAvailable();
			String gasString = String.valueOf(gas);
			
			inputField.setText(gasString);			
		}
		
		else if(ee == haveMiles)
		{
			int miles = (int) cabPlus.milesAvailable();
			String milesString = String.valueOf(miles);
			
			inputField.setText(milesString);			
		}
		
		else if(ee == haveDriven)
		{
			int miles = cabPlus.getMilesSinceReset();
			String milesString = String.valueOf(miles);
			
			inputField.setText(milesString);		
		}

		else if(ee == haveService)
		{			
			cabPlus.serviceCab();		
		}			
}
	/**
	 * serviceStatus: checks to see if the needs service or not
	 * if true, then no message appears.
	 * if false, then a message telling the cab needs maintenance appears.
	 * @param t: boolean indicating cab maintenance.
	 */
	public void serviceStatus(boolean t) 
	{
		if(t == true) //if cab has been serviced
		{
			serviceNeeded.setVisible(false);
		}
		
		else if(t == false) //if cab needs service
		{
			serviceNeeded.setVisible(true);
		}		
	}
	
}	