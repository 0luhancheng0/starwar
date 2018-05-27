package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;

public class Exit extends SWAction {
	/**
	 * Constructor for the <code>Exit</code> Class. Will initialize the message renderer
	 * set the priority of this <code>SWAction</code> to 1 (lowest priority is 0).
	 * 
	 * @param m the message renderer to display messages
	 */
	public Exit(MessageRenderer m) {
		super(m);
		this.priority = 1;
	}

	@Override
	public boolean canDo(SWActor a) {
		return (a.insideMobileWorld() && a.canExitDoor());
		
	}

	@Override
	public void act(SWActor a) {
		a.exitMobileWorld();
		a.removeAction(this);

	}

	@Override
	public int getDuration() {
		return 1;
	}

	@Override
	public String getDescription() {
		return "Exit the current world";
	}

}
