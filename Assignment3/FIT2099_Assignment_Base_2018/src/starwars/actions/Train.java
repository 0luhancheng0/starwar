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
	 * the force should increase by 25 each training
	 */
	private static final int FORCE_INCREASE_PER_TRAIN = 25;
	
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
	 * @param a the <code>SWActor</code> that is being queried
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.SWActor#isDead()}
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
	 * Training cannot increase <code>Player</code>'s force value above their pre-set maximum value. 
	 * @param a the <code>SWActor</code> that is going to be trained
	 */
	@Override
	public void act(SWActor a) {
		
		
		// check if the force is less than the maximum for a person can reach
		if (a.getForce() + FORCE_INCREASE_PER_TRAIN < SWActor.MAX_FORCE) {
			a.say("This training will increase " + FORCE_INCREASE_PER_TRAIN + " points force for you");
			a.gainForce(FORCE_INCREASE_PER_TRAIN);
			
			// check if the actor a can wield the lightsaber
			if (a.getForce() >= LightSaber.FORCE_LIMIT)
			{
				a.say("Congratulations! You are able to wield a LightSaber now.");
			}
			else {
				a.say("You still need to gain " + (LightSaber.FORCE_LIMIT - a.getForce()) + 
						" to use a LightSaber as weapon");
			}
			
		}
		else {
			a.gainForce(SWActor.MAX_FORCE - a.getForce());
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
