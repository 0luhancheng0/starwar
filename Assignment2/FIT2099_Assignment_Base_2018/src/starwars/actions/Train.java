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
	
	/**
	 * Constructor for the <code>Train</code> class. Initializes the <code>messageRenderer</code> and
	 * give <code>Train</code> a priority of 1.
	 * 
	 * @param m message renderer to display messages
	 */
	public Train(MessageRenderer m) {
		super(m);
		priority = 1;
	}

	/**
	 * Determines whether or not <code>Player</code> can be trained by <code>BenKenobi</code>. In order for training
	 * to happen, the <code>Player</code> must be in the same location as <code>BenKenobi</code>, and must not be
	 * at their maximum force value. Only <code>Player</code> can be trained, as of now.
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
	 * Method to train <code>Player</code> which increases their force value by a currently hardcoded value of 25.
	 * Training cannot increase <code>Player</code>'s force value above their pre-set maximum value. If, after the end 
	 * of a training session, the <code>Player</code> has a force value that is enough to wield a <code>LightSaber</code>, 
	 * and is currently in possession of one, the <code>Player</code> will be able to use it as a weapon.
	 */
	@Override
	public void act(SWActor a) {

		
		if (a.getForce() + 25 < 100) {
			a.gainForce(25);
			if (a.getForce() >= 75)
			{
				a.say("Congratulation! Now you are able to wield lightsaber!");
			}
			else {
				a.say("I have gained some force, but I have much to learn.");
			}
			
		}
		else {
			a.gainForce(100 - a.getForce());
			a.say("I have gained some force. I have learnt all that I can from Ben.");
		}
		a.say("Current force of " +a.getShortDescription() + " is " + a.getForce());

	}

	/**
	 * Returns time taken to perform this <code>Train</code> action.
	 * 
	 * @return The duration of the Train action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		return 1;
	}

	/**
	 * A String describing what this <code>Train</code> action will do.
	 * 
	 * @return String comprising "train under " and the short description of <code>BenKenobi</code>
	 */
	@Override
	public String getDescription() {
		return "train under " + BenKenobi.getBenKenobi().getShortDescription();
	}

}
