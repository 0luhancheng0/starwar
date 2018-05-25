package starwars.entities.actors;

import edu.monash.fit2099.gridworld.GridController;
import edu.monash.fit2099.gridworld.GridRenderer;
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
import starwars.actions.Exit;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.Patrol;
import starwars.swinterfaces.SWGridController;
import starwars.swinterfaces.SWGridTextInterface;

public class Sandcrawler extends SWActor {
	private Patrol path;
	private Door doorCarried;
	private boolean moved = false;
	private static final int WIDTH = 2;
	private static final int HEIGHT = 2;
	private GridRenderer innerUI;
	private GridRenderer outerUI;
	private Scheduler innerScheduler;
	private Scheduler outerScheduler;
	private boolean playInside = false;
	private boolean innerWorldInitialized = false;
	
	/**
	 * The constructor of Sandcrawler class, it will initialize the sandcrawler instance
	 * @param m the message render that will used to pass message to user
	 * @param world the <code>SWWorld</code> world to which this <code>Player</code> belongs to
	 * @param moves the moving pattern that sandcrawler should follow
	 */
	public Sandcrawler(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.NEUTRAL, 100, m, world);
		path = new Patrol(moves);
		
		// set the description of the sandcrawler
		this.setShortDescription("Jawa's Sandcrawler");
		this.setLongDescription("Jawa's Sandcrawler, a huge treaded fortresses");
		
		// the sandcrawler should be able to be entered
		this.addAffordance(new Enter(this, m));
		
		// initialize the inner world
		SWMobileWorld worldCarried = new SWMobileWorld(WIDTH, HEIGHT);
		// set up the scheduler for the inside world and record it in the instance
		innerScheduler = new Scheduler(1, worldCarried);
		// link the inside world with the door
		this.doorCarried = new Door(this.world, worldCarried);

	}
	
	

	@Override
	public void act() {
		// find the location of sandcrawler and set it to the exit point of the door
		SWLocation loc = (SWLocation) this.world.find(this);
		this.doorCarried.setInnerLoc(this.doorCarried.getInnerLocByCoor(0, 0));
		this.doorCarried.setOuterLoc(loc);
		
		// check if the entity in the same location with sandcrawler can be collected
		for (SWEntityInterface e : this.world.getEntityManager().contents(loc)) {
			if (e != this && e.hasCapability(Capability.COLLECTABLE)) {

				// collect the entity
				this.enterInnerWorld(e);
				this.say(this.getShortDescription() + "has collected a " + e.getShortDescription());
			}
		}
		
		// set up the moved varible to make it only move in second turn
		if (moved == true) {
			moved = false;
		}
		else {
			// get the next direction of patrol
			Direction newDirection = this.path.getNext();
			say(getShortDescription() + " moves " + newDirection);
			Move myMove = new Move(newDirection, messageRenderer, world);
			moved = true;
			// schedule the move
			scheduler.schedule(myMove, this, 1);
			
		}
		

	}
	

	/**
	 * set the inner user interface
	 * @param innerUI user interface of internal world
	 */
	private void setInnerUI(GridRenderer innerUI) {
		((SWGridTextInterface) innerUI).disableBanner();
		this.innerUI = innerUI;
	}

	/**
	 * set the outer user interface
	 * @param outerUI user interface of outside world
	 */
	private void setOuterUI(GridRenderer outerUI) {
		((SWGridTextInterface) outerUI).disableBanner();
		this.outerUI = outerUI;
	}
	
	/**
	 * reset the move command in the destination world
	 * @param a the actor that tranfered to another world
	 * @param destinationWorld
	 */
	private void resetMovaCommandToWorld(SWActor a, SWWorld destinationWorld) {
		a.resetMoveCommands(destinationWorld.getEntityManager().whereIs(a));
	}
	
	/**
	 * get <code>SWInterface</code> e into the internal world of sandcrawler
	 * @param e the entity which is going to enter the sandcrawler
	 */
	public void enterInnerWorld(SWEntityInterface e) {
		
		// enter the door
		this.doorCarried.enter(e);
		SWMobileWorld worldCarried = this.doorCarried.getInnerWorld();
		
		// record the outside world user interface
		GridRenderer outerUI = SWGridController.getUI();
		this.setOuterUI(outerUI);
		
		GridController innerUIController = new SWGridController(worldCarried);
		
		// record the inner world user interface
		GridRenderer innerUI = SWGridController.getUI();
		this.setInnerUI(innerUI);
		
		// the outside world user interface should be set back since the ui should 
		// not change if the entity other than player entered the inner world
		SWGridController.setUI(outerUI);

		if (e instanceof SWActor) {
			this.resetMovaCommandToWorld(((SWActor) e), worldCarried);
			// only the SWActor have force will have ability to leave the sandcrawler
			// since the the SWActor (like droid)  may be collected by sandcrawler, this checking is necessary 
			// to make sure they won't be able to exit by themselves
			if (((SWActor) e).checkForce()) {
				((SWActor) e).addAction(new Exit(innerUIController));
			}
			
			// keep a reference to the outside sandcrawler, so it know where to exit from 
			((SWActor) e).setWhichSandcIn(this);
			
			// pass the inner world message renderer to the actor
			((SWActor) e).setMessageRenderer(innerUIController);
			
			

			
			
			if (e instanceof Player) {
				this.playInside = true;
				
				// switch the user interface to the inner world
				SWGridController.setUI(innerUI);
				
				// keep a reference to the outside world scheduler
				outerScheduler = SWActor.getScheduler();
				
				SWActor.setScheduler(innerScheduler);
				
				// only need to initialize the world once
				if (!this.innerWorldInitialized) {
					worldCarried.initializeWorld(innerUIController);
					this.innerWorldInitialized = true;
				}
				

				// run the inside controller only when the player is inside the internal world
				while (this.playInside) {
					innerUIController.render();
					innerScheduler.tick();
				}	
			}	
		}
	}
	
	/**
	 * get <code>SWInterface</code> e into the outside world of sandcrawler
	 * @param e the entity which is going to exit the sandcrawler
	 */
	public void exitInnerWorld(SWActor a) {
		this.doorCarried.leave(a);
		a.setWhichSandcIn(null);
		// create the UIcontroller for outside world
		SWGridController outerUIController = new SWGridController(this.world);
		SWGridController.setUI(innerUI);
		// pass the outside controller to a
		a.setMessageRenderer(outerUIController);
		this.resetMovaCommandToWorld(a, this.world);
		if (a instanceof Player) {
			// set false the flag variable
			this.playInside = false;
			((SWGridTextInterface) outerUI).disableBanner();
			SWGridController.setUI(outerUI);
			// switch back the scheduler
			SWActor.setScheduler(outerScheduler);
			
			
		}
			
		
	}
	

}
