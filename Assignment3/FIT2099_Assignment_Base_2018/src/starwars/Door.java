package starwars;

public class Door {
	private SWWorld outerWorld;
	private SWMobileWorld innerWorld;
	private SWLocation outerLoc;
	private SWLocation innerLoc;
	/**
	 * The constructor for <code>Door</code> class. It will be used to link between two worlds, 
	 * (one inside and one outside) and it can be used to access another world. It will initialize 
	 * one reference to each of them in order to keep track of the worlds.
	 * @param outerWorld the reference to outside world
	 * @param innerWorld the reference to inside world
	 */
	public Door(SWWorld outerWorld, SWMobileWorld innerWorld) {
		this.outerWorld = outerWorld;
		this.innerWorld = innerWorld;
		
	}
	
	/**
	 * Set the outside <code>Location</code> where the object will be transported to. 
	 * @param outerLoc The <code>Location</code> where the <code>SWEntity</code> will land, on the outside world
	 */
	public void setOuterLoc(SWLocation outerLoc) {
		this.outerLoc = outerLoc;
		
	}
	
	/**
	 * Set the inner <code>Location</code> where the object will be transported to, when entering.
	 * @param innerLoc the inner <code>Location</code>
	 */
	public void setInnerLoc(SWLocation innerLoc) {
		this.innerLoc = innerLoc;
	}
	/**
	 * Gets the outer <code>Location</code> where the object will be transported to, when exiting 
	 * @param outerLoc the outside <code>Location</code>
	 */
	public SWLocation getOuterLoc() {
		return outerLoc;
	}
	
	/**
	 * get the inner <code>Location</code> where the object will be transported to, when entering 
	 * @param innerLoc the inner <code>location</code>
	 */
	public SWLocation getInnerLoc() {
		return innerLoc;
	}
	
	/**
	 * Get the coordinate of the inner <code>Location</code>
	 * @param x the column number 
	 * @param y the row number
	 * @return
	 */
	public SWLocation getInnerLocByCoor(int x, int y) {
		return this.innerWorld.getGrid().getLocationByCoordinates(x, y);
	}
	
	/**
	 * get the <code>World</code> inside the <code>Door</code>
	 * @return SWMobileWorld innerWorld the inner <code>World</code>
	 */
	public SWMobileWorld getInnerWorld() {
		return this.innerWorld;
	}
	
	/**
	 * Method that deletes the entity from inside world and puts it outside
	 * @param e the <code>SWEntity</code> that is leaving
	 */
	public void leave(SWEntityInterface e) {

		this.outerWorld.getEntityManager().setLocation(e, this.getOuterLoc());
		this.innerWorld.getEntityManager().remove(e);
		if (e instanceof SWActor) {
			((SWActor) e).setWorld(this.outerWorld);
		}
	}
	
	/**
	 * Method that deletes the entity from outside world and puts it inside
	 * @param e the <code>SWEntity</code> that is entering
	 */
	public void enter(SWEntityInterface e) {
		
		this.innerWorld.getEntityManager().setLocation(e, this.getInnerLoc());
		this.outerWorld.getEntityManager().remove(e);
		if (e instanceof SWActor) {
			((SWActor) e).setWorld(this.innerWorld);
		}
		
	}
	
}
