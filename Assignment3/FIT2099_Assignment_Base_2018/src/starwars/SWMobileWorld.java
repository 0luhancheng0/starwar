package starwars;


import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.entities.actors.Droid;

public class SWMobileWorld extends SWWorld {
	
	private static EntityManager<SWEntityInterface, SWLocation> entityManager = new EntityManager<SWEntityInterface, SWLocation>();

	public SWMobileWorld(int width, int height) {
		super();
		super.resizeGrid(width, height);
		

		
	}
	
	public void initializeWorld(MessageRenderer iface) {
		SWLocation loc = null;
		super.setDefaultLocString(loc);
		assert iface != null;
		Droid droid = new Droid(100, iface, this);
		droid.setSymbol("d");
		loc = this.getGrid().getLocationByCoordinates(0, 0);
		this.getEntityManager().setLocation(droid, loc);
		
	}
	
	public EntityManager<SWEntityInterface, SWLocation> getEntityManager() {
		return this.entityManager;
	}

}
