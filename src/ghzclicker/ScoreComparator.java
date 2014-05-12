package ghzclicker;

import java.util.Comparator;
/**
 * Class used to compare scores for the highscore
 * @author Viktor Saltarski
 *
 */
public class ScoreComparator implements Comparator<Score> {
    /**
     * 
     * @param score1
     * @param score2
     */
	public int compare(Score score1, Score score2){
		int sc1 = score1.getScore();
		int sc2 = score2.getScore();
		
		if(sc1 > sc2){
			return -1;
		}else if(sc1 < sc2){
			return +1;
		}else{
			return 0;
		}
	}
}
