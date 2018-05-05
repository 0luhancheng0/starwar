package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Own;


public class Droid extends SWActor {
	public Droid(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
		super(team, hitpoints, m, world);
		// TODO Auto-generated constructor stub
	}

	private SWActor owner = null;

	

	
	public void setupOwner(SWActor a) {
		owner = a; // Unfortunately there is a unavoidable privacy leak exist, but i don't know how to fix this
	}
	
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
		// TODO Auto-generated method stub
		
	}


}
