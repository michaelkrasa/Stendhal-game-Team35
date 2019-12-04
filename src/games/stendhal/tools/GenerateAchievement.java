package games.stendhal.tools;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import games.stendhal.server.core.config.AchievementGroupFactoryLoader;
import games.stendhal.server.core.rp.achievement.*;

public class GenerateAchievement{
	public static void generateAchievement() throws Exception{
		/*
		 *  Wait the uri of xmls to test since we haven't merge the whole thing
		 */
		final AchievementGroupFactoryLoader loader =
				 new AchievementGroupFactoryLoader("the url of xml files written by teammates");
		final List<Achievement> achievements = loader.load();
		
		/*
		 *  When we load the achievements, we use sort() function to sort them
		 *  based on the difficulties of them (which is base score of completing them)
		 *  and can be changed to other functions like getTitle() or getType() etc
		 *  if we want another way of ordering
		 */
		Collections.sort(achievements, new Comparator<Achievement>() {

			@Override
			public int compare(final Achievement o1, final Achievement o2) {
				return o1.getBaseScore() - o2.getBaseScore();
			}
		});
		
		System.out.println();
		
		System.exit(0);
		
	}
	
	/*
	 *  The main function that calls the group loader
	 */
	public static void main(final String[] args) throws Exception {
		generateAchievement();
	}
}