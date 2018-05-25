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
	
	public Sandcrawler(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.NEUTRAL, 100, m, world);
		path = new Patrol(moves);
		this.setShortDescription("Jawa's Sandcrawler");
		this.setLongDescription("Jawa's Sandcrawler, a huge treaded fortresses");
		this.addAffordance(new Enter(this, m));
		
		SWMobileWorld worldCarried = new SWMobileWorld(WIDTH, HEIGHT);
		innerScheduler = new Scheduler(1, worldCarried);
		

		
		
		
		
		this.doorCarried = new Door(this.world, worldCarried);

	}
	
	

	@Override
	public void act() {
		
		SWLocation loc = (SWLocation) this.world.find(this);
		this.doorCarried.setInnerLoc(this.doorCarried.getInnerLocByCoor(0, 0));
		this.doorCarried.setOuterLoc(loc);
		for (SWEntityInterface e : this.world.getEntityManager().contents(loc)) {
			if (e != this && e.hasCapability(Capability.COLLECTABLE)) {

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
	
	private void setInnerUI(GridRenderer innerUI) {
		((SWGridTextInterface) innerUI).disableBanner();
		this.innerUI = innerUI;
	}
	private void setOuterUI(GridRenderer outerUI) {
		((SWGridTextInterface) outerUI).disableBanner();
		this.outerUI = outerUI;
	}
	public void enterInnerWorld(SWEntityInterface e) {
		this.doorCarried.enter(e);
		SWMobileWorld worldCarried = this.doorCarried.getInnerWorld();
		
		GridRenderer outerUI = SWGridController.getUI();
		this.setOuterUI(outerUI);
		
		GridController innerUIController = new SWGridController(worldCarried);
		
		GridRenderer innerUI = SWGridController.getUI();
		this.setInnerUI(innerUI);
		SWGridController.setUI(outerUI);

		if (e instanceof SWActor) {

			if (((SWActor) e).checkForce()) {
				((SWActor) e).addAction(new Exit(innerUIController));
			}
			((SWActor) e).setWhichSandcIn(this);
			((SWActor) e).setMessageRenderer(innerUIController);
			((SWActor) e).resetMoveCommands(worldCarried.getEntityManager().whereIs(e));

			if (e instanceof Player) {
				this.playInside = true;
				SWGridController.setUI(innerUI);
				
				outerScheduler = SWActor.getScheduler();
				
				SWActor.setScheduler(innerScheduler);
				
				if (!this.innerWorldInitialized) {
					worldCarried.initializeWorld(innerUIController);
				}
				else {
					this.innerWorldInitialized = true;
				}

				
				while (this.playInside) {
					innerUIController.render();
					innerScheduler.tick();
				}	
			}	
		}
	}
	
	public void exitInnerWorld(SWActor e) {
		this.doorCarried.leave(e);
		e.setWhichSandcIn(null);
		SWGridController outerUIController = new SWGridController(this.world);
		SWGridController.setUI(innerUI);
		e.setMessageRenderer(outerUIController);
		e.resetMoveCommands(this.world.getEntityManager().whereIs(e));
		if (e instanceof Player) {
			this.playInside = false;
			SWGridController.setUI(outerUI);

			SWActor.setScheduler(outerScheduler);
			
			
		}
			
		
	}
	

}
