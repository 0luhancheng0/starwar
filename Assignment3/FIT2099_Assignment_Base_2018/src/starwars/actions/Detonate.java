package starwars.actions;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;

public class Detonate extends SWAffordance {

	private static final int[] DAMAGE_LIST = {20, 10, 5};

	
	

	public Detonate(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		SWEntityInterface item = a.getItemCarried();
		if (a.isDead() || item == null || !item.hasCapability(Capability.EXPLODABLE)) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void act(SWActor a) {
		//precondition: the item must be explodable to be detonated
		assert a.getItemCarried().hasCapability(Capability.EXPLODABLE) : "The item carried must be explodable";

		//get all the neighbour entities within a particular step
		ArrayList<ArrayList<ArrayList<SWEntityInterface>>> allNeighbours = SWWorld.getNeighboursContents(SWWorld.getEntitymanager().whereIs(a), 2);
		
		//take damage for the entities has different distance from the 
		for (int i=0; i<allNeighbours.size(); i++) {
			
			//get all the entities those stay in a particular contents
			for (ArrayList<SWEntityInterface> contentsPerLoc : allNeighbours.get(i)) {

				for (SWEntityInterface e : contentsPerLoc) {
					
					//check if the entity is the actor detonate the exploder
					//and he is not damaged in the explosion
					if (e != a) {
						int damage = DAMAGE_LIST[i];
						e.takeDamage(damage);
						e.say(e.getShortDescription() + " lost " + damage + " hitpoints in explosion");
					}
				}
				
			}
		}
		
		//the exploder should be cleared after the detonation
		a.setItemCarried(null);
		
		
		
		

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "detonate " + target.getShortDescription();
	}

}
