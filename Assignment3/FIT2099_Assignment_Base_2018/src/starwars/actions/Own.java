package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.Droid;

public class Own extends SWAffordance{
	/**
	 * Constructor for the <code>Own</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that will be owned
	 * @param m the message renderer to display messages
	 */
	public Own(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
		// TODO Auto-generated constructor stub
	}
	/**
	 * Returns if or not this <code>Own</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>target</code> is not owned by someone else
	 *  
	 * @author 	Luhan Cheng
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>target</code> is not owned, false otherwise
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return !((Droid) this.target).isOwned();
	}
	/**
	 * Perform the <code>Own</code> action by setting the <code>owner</code> as <code>SWActor</code> a for <code>target</code>
	 * 
	 * <p>
	 * This method should only be called if the <code>SWActor</code> is alive.
	 * 
	 * @author 	Luhan Cheng
	 * @param 	a the <code>SWActor</code> that is will <code>Own</code> the target
	 */
	@Override
	public void act(SWActor a) {
		((Droid) this.target).setOwner(a);
		this.target.removeAffordance(this);
		// TODO Auto-generated method stub

	}
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author Luhan Cheng
	 * @return String comprising "take ownership of this " and the short description of the target of <code>Own</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "take ownership of this " + this.target.getShortDescription();
	}

}
