package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Detonate;
import starwars.actions.Take;

public class Grenade extends SWEntity {
	/**
	 * Constructor will initialize the <code>Grenade</code>
	 * @param m the MessageRenderer to display messages
	 */
	public Grenade(MessageRenderer m) {
		super(m);
		// TODO Auto-generated constructor stub
		this.shortDescription = "a grenade";
		this.longDescription = "an explodable, nice grenade";
		this.capabilities.add(Capability.EXPLODABLE);
		this.hitpoints = 40;
		this.addAffordance(new Take(this, m));
		this.addAffordance(new Detonate(this, m));

	}
	
	/**
	 * 
	 * Returns the short description of this <code>Grenade</code>
	 */
	public String getShortDescription() {
		return this.shortDescription;
	}
	
	/**
	 * Returns the long description of this <code>Grenade</code>
	 */
	public String getLongDescription() {
		return this.longDescription;
	}
	
	/**
	 * Method that removes the exploded <code>Grenade</code> from the game
	 * 
	 * @author 	Luhan Cheng
	 */
	@Override
	public void takeDamage(int damage) {
		this.hitpoints -= damage;
	}
	

}
