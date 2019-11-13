package games.stendhal.server.entity.status;

import games.stendhal.common.NotificationType;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.RPEntity;

public class SleepStatusHandler implements StatusHandler<SleepStatus>{

	@Override
	public void inflict(SleepStatus status, StatusList statusList, Entity attacker) {
		
		RPEntity entity = statusList.getEntity();
		if (entity == null) {
			return;
		}
		int count = statusList.countStatusByType(status.getStatusType());
		
		entity.sendPrivateText(NotificationType.SCENE_SETTING, "You're sleeping.");
		
		if(count == 0)
		{		
			statusList.addInternal(status);
			TurnListener listener = new SleepStatusTurnListener(statusList);
			TurnNotifier.get().notifyInTurns(0, listener);
		}
	}

	@Override
	public void remove(SleepStatus status, StatusList statusList) {
		statusList.removeInternal(status);
/*
		RPEntity entity = statusList.getEntity();
		if (entity == null) {
			return;
		}

		entity.sendPrivateText(NotificationType.SCENE_SETTING, "You are no longer sleeping.");
		entity.remove("status_" + status.getName()); */
		
	}

}
