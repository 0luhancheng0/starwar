package starwars.actions;

import edu.monash.fit2099.simulator.matter.EntityInterface;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;

public class Leave extends SWAffordance{
	protected EntityInterface target;
	
	
	public Leave(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return a.getItemCarried()!=null;
	}

	@Override
	public void act(SWActor a) {
		target = a.getItemCarried();
		if (target instanceof SWEntityInterface) {
			a.setItemCarried(null);
			SWWorld world = new SWWorld();
			SWAction.getEntitymanager().setLocation((SWEntityInterface)target, world.getEntityManager().whereIs(a));;
			target.removeAffordance(this);
		}
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "leave " + target.getShortDescription();
	}

}
