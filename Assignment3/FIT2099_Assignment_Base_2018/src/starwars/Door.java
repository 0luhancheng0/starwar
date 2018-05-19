package starwars;

public class Door {
	private SWWorld outerWorld;
	private SWMobileWorld innerWorld;
	private SWLocation outerLoc;
	private SWLocation innerLoc;
	
	public Door(SWWorld outerWorld, SWMobileWorld innerWorld) {
		this.outerWorld = outerWorld;
		this.innerWorld = innerWorld;
		
	}

	public void setOuterLoc(SWLocation outerLoc) {
		this.outerLoc = outerLoc;
		
	}
	
	public void setInnerLoc(SWLocation innerLoc) {
		this.innerLoc = innerLoc;
	}
	
	public SWLocation getOuterLoc() {
		return outerLoc;
	}

	public SWLocation getInnerLoc() {
		return innerLoc;
	}
	
	public SWLocation getInnerLocByCoor(int x, int y) {
		return this.innerWorld.getGrid().getLocationByCoordinates(x, y);
	}
	
	public SWMobileWorld getInnerWorld() {
		return this.innerWorld;
	}
	
	public void leave(SWEntityInterface e) {

		this.outerWorld.getEntityManager().setLocation(e, this.getOuterLoc());
		this.innerWorld.getEntityManager().remove(e);
	}
	
	public void enter(SWEntityInterface e) {

		this.innerWorld.getEntityManager().setLocation(e, this.getInnerLoc());
		this.outerWorld.getEntityManager().remove(e);
	}
}
