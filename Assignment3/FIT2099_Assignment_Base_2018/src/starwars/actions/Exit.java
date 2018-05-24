package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;

public class Exit extends SWAction {

	public Exit(MessageRenderer m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor a) {
		return a.insideMobileWorld();
	}

	@Override
	public void act(SWActor a) {
		a.exitMobileWorld();
		// TODO Auto-generated method stub

	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Exit the current world";
	}

}
