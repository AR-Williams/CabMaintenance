package edu.tridenttech.cpt237.williams;
/**
 * @author Alexis Williams
 * 
 * Contains the methods to do the calculations passed in from the CabUI file 
 * and returns the respective values requested
 */

public class Cab 
{
	protected final double MILES_PER_GALLON = 17.8;
	protected final double TANK_SIZE = 22.9;
	protected final double BASE_FARE = 2.00;
	protected final double MILES_FARE = .5850;
	
	protected double gasAvailable;
	protected int milesSinceReset;
	protected double earnings;
	
	/**
	 * reset: sets the milesSinceReset and earnings value to zero.
	 */
	public void reset()
	{
		milesSinceReset = 0;
		earnings = 0;
	}	
	
	/**
	 * getMilesSinceReset: gives the current value of the milesSinceReset variable.
	 * @return: milesSinceReset
	 */
	public int getMilesSinceReset()
	{					
		return milesSinceReset;
	}
	
	/**
	 * getGasAvailable: gives the current value of the gasAvailable variable.
	 * @return: gasAvailable
	 */
	public double getGasAvailable()
	{								
		return gasAvailable;
	}
	
	/**
	 * getGrossEarnings: gives the total amount of earnings made by the cab company.
	 * @return: earnings
	 */
	public double getGrossEarnings()
	{		
		return earnings;		
	}
	
	/**
	 * recordTrip: stores the miles traveled as inputed by the user and gives the amount of cab fare to be paid.
	 * also updates the milesSinceReset and calculates how much gas has been used based on the miles traveled. 
	 * @param m: miles traveled
	 * @return: cab fare 
	 */
	public double recordTrip(int m)
	{
		double fare = getFare(m);
		milesSinceReset += m;
		
		double goneGallons = m/MILES_PER_GALLON;
		gasAvailable -= goneGallons;
		
		return fare;		
	}
	
	/**
	 * getFare: calculates cab fare and uses the miles passed in foe the equation needed to do so.
	 * also updates the earnings variable, for the total fares earned.
	 * @param m: miles traveled
	 * @return: cab fare;
	 */
	public double getFare(int m)
	{
		double fare;
		int miles = m;
		
		fare = (BASE_FARE + miles)*MILES_FARE;
		earnings += fare;
		
		return fare;
	}
	
	/**
	 * addGas: adds gas passed to the method to gasAvailable, which is how much the cab has in the tank.
	 * @param gallons: gas to be put in the tank
	 */
	public double addGas(double gallons)
	{
		double gasTank = 0;
			//a check for if the user put in too much or too little gas for the tank 
			if(gallons <= 0)
			{
				gasTank = 0;	
			}
			else if(gasAvailable == TANK_SIZE || (gasAvailable + gallons) > TANK_SIZE)
			{
				gasTank = 0;
			}
			else
			{
				gasTank += gallons;
				gasAvailable += gasTank;
			}
	
		return gasTank;	
	}
	
	/**
	 * milesAvailable: calculates the number of miles the cab can go based on current gallons of gas in the tank
	 * @return: milesLeft
	 */	
	public double milesAvailable()
	{
		double milesLeft = 0;
		
			if(gasAvailable <= 0)
			{
				milesLeft = 0;
			}
			else
			{	
				milesLeft = MILES_PER_GALLON * gasAvailable;
			}
			
		return milesLeft;		
	}	
		
}
