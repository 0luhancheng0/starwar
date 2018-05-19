package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWMobileWorld;
import starwars.SWWorld;
import starwars.actions.Move;
import starwars.actions.Own;


public class Droid extends SWActor {
	
	/**The <code>owner</code> of the <code>Droid</code>. It is initialized as null by default*/
	private SWActor owner = null;
	
	/**
	 * The <code>heading</code> represents the direction that the Droid is heading to .
	 */
	Direction heading = null;
	
	/**
	 * Creates a <code>Droid</code>. It will not move if it doesn't have an <code>owner</code>.
	 * If it is in the same location as its owner, it will stay there. Otherwise, if the 
	 * <code>Droid<code>'s owner is in a neighbouring location, it will move to that location.
	 * If a <code>Droid</code> has no owner, it will not move.
	 * @param hitpoints the hit points of this <code>Droid</code>. If this decreases to below zero, it will be immobile.
	 * @param m the <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>Droid</code> belongs to
	 */
	public Droid(int hitpoints, MessageRenderer m, SWWorld world) {
		super(null, hitpoints, m, world);
		owner = null;
		this.shortDescription = "A droid";
		this.longDescription = "An intelligent droid.";
		this.addAffordance(new Own(this, m));
		this.capabilities.add(Capability.COLLECTABLE);
	}

	
	/**
	 * Sets up the <code>owner</code> of the <code>Droid</code>
	 * @param a the <code>SWActor</code> which this <code>Droid</code> belongs to
	 */
	public void setOwner(SWActor a) {
		owner = a; 
	}
	
	/**
	 * Checks if the <code>Droid</code> is owned by another <code>SWActor</code>
	 * @return true if it has an owner, false otherwise
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

	/**
	 * Determines how the <code>Droid</code> will act.
	 */
	@Override
	public void act() {
		if (this.isDead()) {
			return;
		}
		
		// if the Droid is owned by a SWActor
		Location droidLocation = this.world.find(this);
		if (this.isOwned()) {
			Move myMove = null; 
			ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
			Location ownerLocation = this.world.find(this.owner);
			// if owner is not in the same location of this Droid
			if (ownerLocation != droidLocation) {
				
				// build a list of available directions
				for (CompassBearing d : CompassBearing.values()) {
					if (world.getEntitymanager().seesExit(this, d)) {
						possibleDirections.add(d);
						
					}
					
					// move to owner's direction if its owner is in the neighbour locations of the Droid
					if (droidLocation.getNeighbour(d) == ownerLocation) {
		
						myMove = new Move(d, messageRenderer, world);
						
					}
				}
				// if Droid cannot find its owner
				if (myMove == null) {
					// randomly get a direction and move towards it
					if (heading == null || !(world.getEntitymanager().seesExit(this, heading)))
					{
						heading = possibleDirections.get((int) (Math.floor(Math.random() * possibleDirections.size())));
					}
					
					myMove = new Move(heading, messageRenderer, world);
					
					
					}
				
				// schedule the move event
				scheduler.schedule(myMove, this, 1);
			}
			

		}
	
		// check if the Droid is in BadLands before the move
		if (!(this.world instanceof SWMobileWorld)) {
			boolean inBadLand = false;
			for (int row = 5; row < 8; row++) {
				for (int col = 4; col < 7; col++) {
					if (droidLocation == world.getGrid().getLocationByCoordinates(col, row)) {
						inBadLand = true;
					}
					
					
				}
			}
			
			// decrease the hitpoint of the Droid if it is in BadLands
			if (inBadLand) {
				this.takeDamage(5);
				say("the droid lost 5 health in badland, current hitpoint: " + this.getHitpoints());
			}
		}
		
		
		
	}




}
