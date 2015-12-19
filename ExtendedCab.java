package edu.tridenttech.cpt237.williams;

import java.util.ArrayList;

/**
 * 
 * @author Alexis Williams 
 * 
 * Added modifications for the cab service that utilizes several methods of the Cab parent class.
 */

public class ExtendedCab extends Cab
{
	private final double SERVICE_COST = 25.00;
	private double totalCosts = 0;
	private int milesSinceService = 0;
	private Double gas;
	private boolean hasOverride = false;
	private ArrayList<MaintenanceListener> queue = new ArrayList<MaintenanceListener>();
	
	/**
	 * ExtendedCab: the constructor for the ExtendedCab class. 
	 * It receives the initial gas amount as prompted in MainClass, parsing and adding it the cab's gas tank.
	 * @param g: String parameter to receive gas amount.
	 */	
	ExtendedCab(String g)
	{
		if(g != null)
		{
			Double gasValue;
			gasValue = Double.parseDouble(g);
		
			gas = gasValue;		
			super.addGas(gas);
		}	
	}
	
	/**
	 * getTotalCosts: returns the total maintenance costs done for the cab.
	 * @return: totalCosts 
	 */	
	public double getTotalCosts()
	{
		return totalCosts;
	}
	
	/**
	 * getNetEarnings: returns the net earnings that the cab has made.
	 * @return: netEarnings
	 */
	public double getNetEarnings()
	{		
		double netEarnings;
		
		netEarnings = super.getGrossEarnings() - totalCosts;
		
		return netEarnings;				
	}
	
	/**
	 * getMilesSinceService: returns the number of miles added since the last time the cab was serviced
	 * @return: milesSinceService
	 */
	
	public int getMilesSinceService()
	{
		return milesSinceService;
	}

	/**
	 * serviceCab: sets the milesSinceService value to zero and adds a service fee to the overall maintenance cost and earnings made by the cab
	 */	
	public void serviceCab()
	{
		milesSinceService = 0;
		totalCosts += SERVICE_COST;
		super.earnings += totalCosts;	
		
		noticeListeners(true);
		
	}
	
	/**
	 * addGas: the addGas method for the ExtendedCab class. 
	 * adds the cost for gas to the maintenance cost and passes the gas value to the addGas of the Cab class to be added to the cab's gas tank  
	 * also adds the maintenance cost to the earnings made by the cab
	 * @param g: parameter to accepts the gas value as inputed by the user.
	 * @param c: parameter for the price per gallon of gas, the first parameter
	 */
	public double addGas(double g, double c)
	{										
		double addTank = super.addGas(g);		
		double gasTotal = 0;
		
			if(addTank != 0)
			{
				gasTotal = g * c; 
			
				totalCosts += gasTotal;
				super.earnings += totalCosts;
			}		
			else
			{
				gasTotal = 0;
			}
		
		return gasTotal;
	}
	
	public double recordTrip(int m)
	{		
		double fare;
		double trip = m/MILES_PER_GALLON;
		
			if(gasAvailable < trip || gasAvailable <= 0)
			{
				fare = 0;
			}
			else if((m > 500 || milesSinceService > 500) && !hasOverride)
			{
				noticeListeners(false);
				fare = 0;
				hasOverride = false;
			}
			else
			{	
				fare = super.recordTrip(m);				
				milesSinceService += m;	
				hasOverride = false;
			}
			
		
		return fare;
	}
	
	/**
	 * reset: sets the maintenance costs to zero when called.
	 */
	public void reset()
	{		
		super.reset();
		totalCosts = 0;
	}
	
	public void fareOverride(boolean t)
	{
		if(t == false)
		{
			hasOverride = true;			
		}
				
	}
	
	public void addMaintenanceListener(MaintenanceListener ml)
	{
		queue.add(ml);							
	}

	public void noticeListeners(boolean t) 
	{
		for(MaintenanceListener ml: queue)
		{			
			ml.serviceStatus(t);							
		}		
	}

	
	
}
