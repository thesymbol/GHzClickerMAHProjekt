package ghzclicker;

import java.util.Comparator;

/**
 * Class used to compare scores for the highscore
 * 
 * @author Viktor Saltarski
 * 
 */
public class ScoreComparator implements Comparator<Score> {
    /**
     * Compare the two scores.
     * 
     * @param score1 The first score to compare
     * @param score2 The second score to compare
     */
    public int compare(Score score1, Score score2) {
        double sc1 = score1.getScore();
        double sc2 = score2.getScore();

        if (sc1 > sc2) {
            return -1;
        } else if (sc1 < sc2) {
            return +1;
        } else {
            return 0;
        }
    }
}
