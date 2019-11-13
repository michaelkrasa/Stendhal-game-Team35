package games.stendhal.server.entity.status;

import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.entity.RPEntity;

public class SleepStatusTurnListener implements TurnListener{
	StatusList statusList;
	
	public SleepStatusTurnListener(StatusList statusList) {
		this.statusList = statusList;
	}	
	
	@Override
	public void onTurnReached(int turn) {
		RPEntity entity = statusList.getEntity();
		
		// check that the entity exists
		if (entity == null) {
			return;
		}

				
	}
}