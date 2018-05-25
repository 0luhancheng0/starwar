package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.Sandcrawler;

public class Enter extends SWAffordance {
	/**
	 * Constructor for the <code>Enter</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Affordance</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is going to be enter
	 * @param m the message renderer to display messages
	 */
	public Enter(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		this.priority = 1;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor a) {
		if (a.isDead()) {
			return false;
		}
		
		if (!a.checkForce()) {
			return false;
		}
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void act(SWActor a) {
		target = this.getTarget();
		assert (target instanceof Sandcrawler) : "The target must be a Sandcrawler";
		((Sandcrawler) target).enterInnerWorld(a);
		
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "enter " + this.getTarget().getShortDescription();
	}

}
