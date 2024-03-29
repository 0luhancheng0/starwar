package starwars;

import java.util.ArrayList;
import java.util.HashMap;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.space.LocationMaker;

/**
 * Class that models locations in Star Wars.
 * <p>
 * <code>SWLocation</code>s are not restricted to 8-way movements. <I added this.Is this right? Asel>
 * 
 * @author ram
 */

public class SWLocation extends Location {
	
	/**A character that represents the <code>SWLocation</code>, suitable for display*/
	private char symbol;
	
	/**A character that represents an empty space in this <code>SWLocation</code>, suitable for display.
	 * By default empty spaces in all <code>SWLocation</code>s are represented by a '.'
	 */
	private char emptySymbol = '.';
	
	/**A longer string that describes this <code>SWLocation</code>*/
	private String longDescription;
	
	/**A shorter string that describes this <code>SWLocation</code>*/
	private String shortDescription;
	
	
	/**
	 * Factory class used by <code>Grids</code> to instantiate <code>SWLocation</code>s
	 * 
	 * @author ram
	 *
	 */
	public static class SWLocationMaker implements LocationMaker<SWLocation> {

		
		/**
		 * Factory method.
		 * 
		 * @author ram
		 * @return a new <code>SWLocation</code>
		 */
		@Override
		public SWLocation make() {
			return new SWLocation();
		}
		
	}

	/**
	 * Constructor for <code>SWLocation</code>. Will initialize the list of neighboring references
	 */
	public SWLocation() {
		neighbours = new HashMap<Direction, Location>();
	}
	
	/** 
	 * A factory of a factory.
	 * Makes the <code>SWLocationMaker</code> which has a <code>make()</code> method that makes the <code>SWLocation</code>
	 * 
	 * @author ram
	 * @return an object with a <code>make()</code> method that can create <code>SWLocation</code>s
	 */
	public static SWLocationMaker getMaker() {
		return new SWLocationMaker();
	}
	
	
	/**
	 * Returns a character that represents this <code>SWLocation</code>. 
	 * <p>
	 * The Views use this method to obtain the symbols that are used to query for resources(images of texture) and for display.
	 * 
	 * @author 	ram
	 * @return 	a char representing this <code>SWLocation</code>.
	 * @see 	#symbol
	 */
	public char getSymbol() {
		return symbol;
	}
	
	/**
	 * Sets the character symbol of this <code>SWLocation</code> to a new character <code>c</code>.
	 * <p>
	 * The Views use this symbols to query for resources(images of texture) and for display, hence although not a must
	 * symbols of different types of <code>SWLocations</code> are preferably unique.
	 * 
	 * @author 	ram
	 * @param 	c the new character symbol of this <code>SWLocation</code>
	 * @see 	#symbol
	 */
	public void setSymbol(char c) {
		symbol = c;
	}

	/**
	 * Returns a character that represents an empty space in this <code>SWLocation</code>.
	 * <p>
	 * <code>emptySymbols</code> are particularly used by text based Views (user interfaces)
	 * 
	 * @author 	dsquire
	 * @return 	a char representing empty space at this <code>SWLocation</code>
	 * @see 	#emptySymbol
	 */
	public char getEmptySymbol() {
		return emptySymbol;
	}
	
	/**
	 * Sets the <code>emptySymbol</code> of this <code>SWLocation</code> to a new
	 * character <code>c</code>
	 * <p>
	 * <code>emptySymbols</code> are particularly used by text based Views (user interfaces)
	 * 
	 * @author 	dsquire
	 * @param 	c the character to set the empty symbol to
	 * @see 	#emptySymbol
	 */
	public void setEmptySymbol(char c) {
		emptySymbol = c;
	}
	

	/**
	 * Sets the long description of this <code>SWLocation</code> to a new string <code>s</code>
	 * <p>
	 * Long description <code>s</code> should describe this <code>SWLocation</code> in general and 
	 * should not contain any information regarding what this location contains.
	 * 
	 * @param 	s the new long description string of this <code>SWLocation</code>
	 * @see 	#longDescription
	 */
	public void setLongDescription(String s) {
		longDescription = s;
	}
	
	/**
	 * Sets the short description of this <code>SWLocation</code> to a new string <code>s</code>
	 * <p>
	 * Short description <code>s</code> should describe this <code>SWLocation</code> in general and 
	 * should not contain any information regarding what this location contains.
	 * 
	 * @param 	s the new short description string of this <code>SWLocation</code>
	 * @see 	#longDescription
	 */
	public void setShortDescription(String s) {
		shortDescription = s;
	}
	
	/**
	 * Returns the long description of this <code>SWLocation</code>.
	 * 
	 * @return a string that describes this <code>SWLocation</code>
	 * @see #longDescription
	 */
	public String getLongDescription() {
		return longDescription;
	}
	
	/**
	 * Returns the short description of this <code>SWLocation</code>.
	 *  
	 * @return a string that describes this <code>SWLocation</code>
	 * @see #shortDescription
	 */
	public String getShortDescription() {
		return shortDescription;
	}
	
	/**
	 * the function <code>getNeighboursLoc</code> take a input steps, and return all the locations 
	 * within the steps from the <code>SWLocation</code> instance
	 * @param steps the distance from the <code>SWLocation</code>
	 * @return a list of <code>SWLocation</code> that has different distance from the instance
	 */
	public ArrayList<ArrayList<SWLocation>> getNeighboursLoc(int steps) {
		
		//initialize the return variable
		ArrayList<ArrayList<SWLocation>> neighboursLoc = new ArrayList<ArrayList<SWLocation>>();
		
		//initialze the central starting point, which is the SWLocation instance itself
		//and add it the the start of the result
		ArrayList<SWLocation> initialPoint = new ArrayList<SWLocation>();
		initialPoint.add(this);
		neighboursLoc.add(initialPoint);
		
		for (int i=0; i<steps;i++) {
			//create list to record the locations from a particular step
			ArrayList<SWLocation> temp = new ArrayList<SWLocation>();
			
			//iterate through all the neighbours' locations from internal location
			for (SWLocation central : neighboursLoc.get(neighboursLoc.size()-1)) {
				for (SWGrid.CompassBearing d : SWGrid.CompassBearing.values()) {
					SWLocation possibleNeighbourLoc = (SWLocation) central.neighbours.get(d);
					
					//set the flag indicate if the current location has been added in previous iteration
					boolean flag = true;
					for (ArrayList<SWLocation> neighbours : neighboursLoc) {
						//set flag to false if the neighbour location is null or it has been add by previous iteration
						if (possibleNeighbourLoc == null || neighbours.contains(possibleNeighbourLoc) || temp.contains(possibleNeighbourLoc)) {
							flag = false;
							
						}
					}
					
					//add the location if the flag is true
					if (flag) {
						temp.add(possibleNeighbourLoc);
					}
				
				}
			}		
			neighboursLoc.add(temp);
		}
		return neighboursLoc;
	}
	

	


}
