package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;
/**
 * <code>SWAction</code> that lets a <code>SWActor</code> pick up an object.
 * 
 * @author Luhan Cheng
 */
/*
 * Changelog
 * 2018/05/03	- class added
 */
public class Leave extends SWAffordance{

	/**
	 * Constructor for the <code>Leave</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is going to be taken
	 * @param m the message renderer to display messages
	 */
	
	public Leave(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Returns if or not this <code>Leave</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying any item already.
	 *  
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is can leave this item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return a.getItemCarried()!=null;
	}
	
	/**
	 * Perform the <code>Leave</code> action by setting the item carried by the <code>SWActor</code> to the target (
	 * the <code>SWActor a</code>'s item carried would be the target of this <code>Leave</code>).
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @param 	a the <code>SWActor</code> that is leaving the target
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.SWActor#isDead()}
	 */
	@Override
	public void act(SWActor a) {
		target = a.getItemCarried();
		if (target instanceof SWEntityInterface) {
			a.setItemCarried(null);
			SWWorld world = new SWWorld();
			SWAction.getEntitymanager().setLocation((SWEntityInterface)target, world.getEntityManager().whereIs(a));
			
			//remove the leave affordance
			target.removeAffordance(this);
			
			//add the take affordance
			target.addAffordance(new Take((SWEntityInterface) target, messageRenderer));
		}
		// TODO Auto-generated method stub

	}
	
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @return String comprising "leave " and the short description of the target of this <code>Leave</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "leave " + target.getShortDescription();
	}

}
