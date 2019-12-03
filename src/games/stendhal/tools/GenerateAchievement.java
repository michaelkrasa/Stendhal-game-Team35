package games.stendhal.tools;

//import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import games.stendhal.server.core.config.AchievementGroupFactoryLoader;
//import games.stendhal.server.core.rule.defaultruleset.DefaultCreature;
//import games.stendhal.server.core.rule.defaultruleset.DefaultItem;
import games.stendhal.server.core.rp.achievement.*;
//import games.stendhal.server.entity.creature.impl.DropItem;

public class GenerateAchievement{
	public static void generateAchievement() throws Exception{
		final AchievementGroupFactoryLoader loader =
				 new AchievementGroupFactoryLoader("the url of xml files written by teammates");
		final List<Achievement> achievements = loader.load();
		
		Collections.sort(achievements, new Comparator<Achievement>() {

			@Override
			public int compare(final Achievement o1, final Achievement o2) {
				return o1.getBaseScore() - o2.getBaseScore();
			}
		});
		
		System.out.println();
		
		System.exit(0);
		
	}
	
	public static void main(final String[] args) throws Exception {
		generateAchievement();
	}
}