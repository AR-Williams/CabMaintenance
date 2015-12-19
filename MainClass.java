package edu.tridenttech.cpt237.williams;
import javax.swing.JOptionPane;
//Program: MainClass.java
//Programmer: Alexis Williams
//Purpose: invokes both the Cab and CabUI classes from the main method. 
import javax.swing.SwingUtilities;

public class MainClass 
{

	public static void main(String[] args) 
	{
		String gasPrompt = JOptionPane.showInputDialog("How much gas do you want?");
		ExtendedCab cab = new ExtendedCab(gasPrompt);
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new DriverUI(cab);
				new ManagementUI(cab);
			}						
		});
		
	}//end main

}//end class
