package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.actions.Move;
import starwars.actions.Own;


public class Droid extends SWActor {
	
	/**the <code>owner</code> of the droid, it is initialized to null by default*/
	private SWActor owner = null;
	
	/**
	 * the <code>heading</code> represent the dirction of the droid heading to 
	 */
	Direction heading = null;
	
	/**
	 * Create a Droid. Droid will not move if it doesn't have owner
	 * if it is in the same location as its owner, it will stay there. If the droid’s owner is in a neighbouring 
	 * location, it will move to that location.
	 * If a droid has no owner, it will not move.
	 * @param hitpoints
	 *            the number of hit points of this droid. If this
	 *            decreases to below zero, the droid will be immobile.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>droid</code> belongs to
	 * 
	 */
	public Droid(int hitpoints, MessageRenderer m, SWWorld world) {
		super(null, hitpoints, m, world);
		owner = null;
		this.shortDescription = "a droid";
		this.longDescription = "a smart droid";
		this.addAffordance(new Own(this, m));
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * set up the owner of the droid
	 * @param a the SWActor which droid belong to
	 */
	public void setOwner(SWActor a) {
		owner = a; 
	}
	
	/**
	 * check if the droid has been owned by another SWActor
	 * @return true if it has owner, false otherwise
	 */
	public boolean isOwned() {
		return owner != null;
	}
	
	
	public String getSymbol() {
		return "d";
	}
	
	public String getShortDescription() {
		return this.shortDescription;
	}
	
	public String getLongDescription() {
		return this.longDescription;
	}

	@Override
	public void act() {
		// if the droid is ownered by other SWActor
		Location droidLocation = this.world.find(this);
		if (this.isOwned()) {
			Move myMove = null; 
			ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
			Location ownerLocation = this.world.find(this.owner);
			// if owner is not in the same location with droid
			if (ownerLocation != droidLocation) {
				
				// build a list of available directions
				for (CompassBearing d : CompassBearing.values()) {
					if (SWWorld.getEntitymanager().seesExit(this, d)) {
						possibleDirections.add(d);
						
					}
					
					// move to owner's direction if owner is in the neighbour of the droid
					if (droidLocation.getNeighbour(d) == ownerLocation) {
		
						myMove = new Move(d, messageRenderer, world);
						
					}
				}
				// it droid cannot find its owner
				if (myMove == null) {
					// randomly get a direction and move towards it
					if (heading == null || !(SWWorld.getEntitymanager().seesExit(this, heading)))
					{
						heading = possibleDirections.get((int) (Math.floor(Math.random() * possibleDirections.size())));
					}
					
					myMove = new Move(heading, messageRenderer, world);
					
					
					}
				
				// schedule the move event
				scheduler.schedule(myMove, this, 1);
			}
			

		}
		
		// check if the droid is in the bad land before the move
		boolean inBadLand = false;
		for (int row = 5; row < 8; row++) {
			for (int col = 4; col < 7; col++) {
				if (droidLocation == world.getGrid().getLocationByCoordinates(col, row)) {
					inBadLand = true;
				}
				
				
			}
		}
		
		// decrease the hitpoint of droid if it is in the bad land
		if (inBadLand) {
			this.takeDamage(5);
			say("the droid lost 5 health in badland, current hitpoint: " + this.getHitpoints());
		}
		
		
	}



}
