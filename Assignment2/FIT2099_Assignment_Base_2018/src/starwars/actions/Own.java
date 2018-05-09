package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.Droid;

public class Own extends SWAffordance{
	/**
	 * Constructor for the <code>Own</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being Owned
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
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	Luhan Cheng
	 * @param 	a the <code>SWActor</code> that is owning the target
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
	 * @return String comprising "own " and the short description of the target of this <code>Own</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Own " + this.target.getShortDescription();
	}

}
