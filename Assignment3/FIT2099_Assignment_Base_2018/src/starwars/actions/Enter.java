package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.Sandcrawler;

public class Enter extends SWAffordance {

	public Enter(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
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
		assert (target instanceof Sandcrawler) : "the target must be sandscrwler";
		((Sandcrawler) target).enterInnerWorld(a);
		
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Enter " + this.getTarget().getShortDescription();
	}

}
