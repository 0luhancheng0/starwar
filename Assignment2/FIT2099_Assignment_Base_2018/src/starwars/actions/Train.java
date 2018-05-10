package starwars.actions;

import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.entities.LightSaber;
import starwars.entities.actors.BenKenobi;
import starwars.entities.actors.Player;

public class Train extends SWAction implements SWActionInterface {

	public Train(MessageRenderer m) {
		super(m);
		priority = 1;
	}

	/**
	 * Do this method if Luke is in the same location as Ben.
	 */
	@Override
	public boolean canDo(SWActor a) {
		if (!(a instanceof Player)) {
			return false;
		}
		if (a.checkForceMax() == true) {
			return false;
		}
		Location bensLocation = SWAction.getEntitymanager().whereIs(BenKenobi.getBenKenobi());
		
		if (bensLocation == SWAction.getEntitymanager().whereIs(a))
			return true;
		return false;
	}

	/**
	 * Method to train Luke. Increases Luke's force ability.
	 */
	@Override
	public void act(SWActor a) {

		
		if (a.getForce() + 90 < 100) {
			a.gainForce(90);
//			a.say("I have gained some force, but I have much to learn.");
			a.say("Force value = " + a.getForce());
		}
		else {
			a.gainForce(100 - a.getForce());
			a.say("I have gained some force. I have learnt all that I can from Ben.");
		}
		if (a.getItemCarried() instanceof LightSaber && a.getForce() > 75)
			((LightSaber)a.getItemCarried()).canUseAsWeapon();
			

	}

	/**
	 * Duration of the action. Hardcoded to return 1.
	 */
	@Override
	public int getDuration() {
		return 1;
	}

	/**
	 * Description of the training.
	 */
	@Override
	public String getDescription() {
		return "train under Ben Kenobi";
	}

}
