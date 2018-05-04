package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntity;
import starwars.actions.Own;


public class Droid extends SWEntity {
	private SWActor owner = null;
	protected Droid(MessageRenderer m) {
		super(m);
		this.shortDescription = "a droid";
		this.longDescription = "A stupid droid";
		this.hitpoints = 100; // start with a fully functional droid
		
		this.addAffordance(new Own(this, m));//add the Own affordance so that the blaster can be picked up
		
		
		// TODO Auto-generated constructor stub
	}
	
	
	public void setupOwner(SWActor a) {
		owner = a; // Unfortunately there is a unavoidable privacy leak exist, but i don't know how to fix this
	}
	
	public boolean isOwned() {
		if (owner != null) {
			return true;
		}
		else {
			return false;
		}
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


}
