package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.Droid;

public class Own extends SWAffordance implements SWActionInterface {

	public Own(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return !((Droid) this.target).isOwned();
	}

	@Override
	public void act(SWActor a) {
		((Droid) this.target).setOwner(a);
		this.target.removeAffordance(this);
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Own " + this.target.getShortDescription();
	}

}
