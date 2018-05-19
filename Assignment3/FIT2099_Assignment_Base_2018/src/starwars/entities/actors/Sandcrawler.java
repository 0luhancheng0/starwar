package starwars.entities.actors;

import edu.monash.fit2099.gridworld.GridController;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.Door;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWMobileWorld;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Enter;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.Patrol;
import starwars.swinterfaces.SWGridController;

public class Sandcrawler extends SWActor {
	private Patrol path;
	private Door doorCarried;
	private boolean moved = false;
	private static final int WIDTH = 2;
	private static final int HEIGHT = 2;
	private GridController innerUIController;
	private Scheduler theScheduler;
	
	public Sandcrawler(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.NEUTRAL, 100, m, world);
		path = new Patrol(moves);
		this.setShortDescription("Jawa's Sandcrawler");
		this.setLongDescription("Jawa's Sandcrawler, a huge treaded fortresses");
		this.addAffordance(new Enter(this, m));
		
		SWMobileWorld worldCarried = new SWMobileWorld(WIDTH, HEIGHT);
		

		
		
		
		
		this.doorCarried = new Door(this.world, worldCarried);

	}
	
	

	@Override
	public void act() {
		
		SWLocation loc = (SWLocation) this.world.find(this);
		this.doorCarried.setInnerLoc(this.doorCarried.getInnerLocByCoor(0, 0));
		this.doorCarried.setOuterLoc(loc);
		for (SWEntityInterface e : this.world.getEntityManager().contents(loc)) {
			if (e != this && e.hasCapability(Capability.COLLECTABLE)) {
				e.takeDamage(e.getHitpoints());
				this.enterInnerWorld(e);

				
				this.say(this.getShortDescription() + "has collected a " + e.getShortDescription());
			}
		}
		
		if (moved == true) {
			moved = false;
		}
		else {
			Direction newDirection = this.path.getNext();
			say(getShortDescription() + " moves " + newDirection);
			Move myMove = new Move(newDirection, messageRenderer, world);
			moved = true;
			scheduler.schedule(myMove, this, 1);
			
		}
		

	}
	
	public void enterInnerWorld(SWEntityInterface e) {
		this.doorCarried.enter(e);

		if (e instanceof Player) {
			SWMobileWorld worldCarried = this.doorCarried.getInnerWorld();
			theScheduler = new Scheduler(1, worldCarried);
			SWActor.setScheduler(theScheduler);
			innerUIController = new SWGridController(worldCarried);
			worldCarried.initializeWorld(innerUIController);
			
			


			return;
		}
		
		
	}
	

}
