package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Detonate;
import starwars.actions.Take;

public class Grenade extends SWEntity {
	/**
	 * this constructor will initialize the grenade
	 * @param m
	 */
	public Grenade(MessageRenderer m) {
		super(m);
		// TODO Auto-generated constructor stub
		this.shortDescription = "a grenade";
		this.longDescription = "a explodable nice grenade";
		this.capabilities.add(Capability.EXPLODABLE);
		this.hitpoints = 40;
		this.addAffordance(new Take(this, m));
		this.addAffordance(new Detonate(this, m));

	}
	
	/**
	 * 
	 * return the short description of this grenade
	 */
	public String getShortDescription() {
		return this.shortDescription;
	}
	
	/**
	 * return the long description of this grenade
	 */
	public String getLongDescription() {
		return this.longDescription;
	}
	
	/**
	 * Method that remove the exploded grenade from the game
	 * 
	 * 
	 * @author 	Luhan Cheng

	 */
	@Override
	public void takeDamage(int damage) {
		this.hitpoints -= damage;
		
	}
	

}
