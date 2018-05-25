package starwars;


import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.entities.actors.Droid;

public class SWMobileWorld extends SWWorld {
	/**
	 * <code>EntityManager</code> of the world
	 */
	private static EntityManager<SWEntityInterface, SWLocation> entityManager = new EntityManager<SWEntityInterface, SWLocation>();
	
	/**
	 * constructor for <code>SWMobileWorld</code>, this will initialized a grid
	 * @param width the width of the world's grid
	 * @param height the height of the world's grid
	 */
	public SWMobileWorld(int width, int height) {
		super();
		super.resizeGrid(width, height);
		
	}
	/**
	 * Set up the world, placing items and actors on the grid.
	 * 
	 * @param 	iface a MessageRenderer to be passed onto newly-created entities
	 */
	public void initializeWorld(MessageRenderer iface) {
		
		// the default location 
		for (int col=0; col<this.getGrid().getWidth(); col++) {
			for (int row=0; row<this.getGrid().getHeight(); row++) {
				SWLocation loc = this.getGrid().getLocationByCoordinates(col, row);
				loc.setShortDescription("inner world of sandcrawler");
				loc.setLongDescription("inner world of sandcrawler at (" + col + ", " + row + ")");
			}
		}
		
		// setup a droid
		SWLocation loc = null;
		Droid droid = new Droid(100, iface, this);
		droid.setSymbol("d");
		loc = this.getGrid().getLocationByCoordinates(1, 0);
		this.getEntityManager().setLocation(droid, loc);
		
	}
	
	/**
	 * return the static entity manager of the instance world
	 */
	public EntityManager<SWEntityInterface, SWLocation> getEntityManager() {
		return this.entityManager;
	}

}
