package starwars;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;

public class Door {
	private SWWorld outerWorld;
	private SWMobileWorld innerWorld;
	private SWLocation outerLoc;
	private SWLocation innerLoc;
	/**
	 * the constructor for Door class, it will be used to link between two world (one inside and one outside)
	 * And it can be used to access another world, it will initialize one reference to each of them in order 
	 * to keep track on them
	 * @param outerWorld the reference to outside world
	 * @param innerWorld the reference to inside world
	 */
	public Door(SWWorld outerWorld, SWMobileWorld innerWorld) {
		this.outerWorld = outerWorld;
		this.innerWorld = innerWorld;
		
	}
	
	/**
	 * set the outside location where the object will be transmitted to 
	 * @param outerLoc The location where the entity will be landed on outside world
	 */
	public void setOuterLoc(SWLocation outerLoc) {
		this.outerLoc = outerLoc;
		
	}
	
	/**
	 * set the inner location where the object will be transmitted to when entering 
	 * @param innerLoc the inner location where the object will be transmitted to when entering
	 */
	public void setInnerLoc(SWLocation innerLoc) {
		this.innerLoc = innerLoc;
	}
	/**
	 * get the outer location where the object will be transmitted to when exiting 
	 * @param outerLoc the outside location where the object will be transmitted to when exiting
	 */
	public SWLocation getOuterLoc() {
		return outerLoc;
	}
	
	/**
	 * get the inner location where the object will be transmitted to when entering 
	 * @param innerLoc the internal location where the object will be transmitted to when entering
	 */
	public SWLocation getInnerLoc() {
		return innerLoc;
	}
	
	/**
	 * Get the inner location by the coordinate
	 * @param x the column number 
	 * @param y the row number
	 * @return
	 */
	public SWLocation getInnerLocByCoor(int x, int y) {
		return this.innerWorld.getGrid().getLocationByCoordinates(x, y);
	}
	
	/**
	 * get the inside world of the door
	 * @return SWMobileWorld innerWorld
	 */
	public SWMobileWorld getInnerWorld() {
		return this.innerWorld;
	}
	
	/**
	 * The method that delete the entity from inside world and put it in outside location
	 * @param e the entity that is leaving the inside world
	 */
	public void leave(SWEntityInterface e) {

		this.outerWorld.getEntityManager().setLocation(e, this.getOuterLoc());
		this.innerWorld.getEntityManager().remove(e);
		if (e instanceof SWActor) {
			((SWActor) e).setWorld(this.outerWorld);
		}
	}
	
	/**
	 * The method that delete the entity from outside world and put it in inside location
	 * @param e the entity that is entering the inside world
	 */
	public void enter(SWEntityInterface e) {
		
		this.innerWorld.getEntityManager().setLocation(e, this.getInnerLoc());
		this.outerWorld.getEntityManager().remove(e);
		if (e instanceof SWActor) {
			((SWActor) e).setWorld(this.innerWorld);
		}
		
	}
	
}
