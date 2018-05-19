package starwars;

import edu.monash.fit2099.simulator.matter.EntityInterface;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Own;
import starwars.entities.actors.Droid;

public class SWMobileWorld extends SWWorld {
	
	private static final EntityManager<SWEntityInterface, SWLocation> entityManager = new EntityManager();
	public SWMobileWorld(int width, int height) {
		super();
		super.resizeGrid(width, height);

		
	}
	
	public void initializeWorld(MessageRenderer iface) {
		SWLocation loc = null;
		super.setDefaultLocString(loc);
		
		Droid droid = new Droid(100, iface, this);
		droid.setSymbol("d");
		loc = this.getGrid().getLocationByCoordinates(0, 0);
		this.getEntityManager().setLocation(droid, loc);
		
	}
	
	
	public static EntityManager<SWEntityInterface, SWLocation> getEntitymanager() {
		return entityManager;
	}

}
