package ghzclicker;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that handles the logic behind the HighScore
 * 
 * @author Viktor Saltarski
 */
public class HighScoreManager {
    private ArrayList<Score> scores;
    private Controller controller;

    /**
     * Creates a new HighScoreManager
     */
    public HighScoreManager(Controller controller) {
        scores = new ArrayList<Score>();
        this.controller = controller;
    }

    /**
     * Empty the highscore list for the client.
     */
    public void clear() {
        scores.clear();
    }

    /**
     * Updated the scoreboard with the name and score
     * 
     * @param name The name of the user
     * @param score The score of the user
     */
    private boolean updateScore(String name, double score) {
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i).getName().equals(name)) {
                scores.get(i).setScore(score);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new Score to the highscore
     * 
     * @param name The name of scoreholder
     * @param score The score
     */
    public void addScore(String name, double score) {
        if (!updateScore(name, score)) {
            scores.add(new Score(name, score));
        }
    }

    /**
     * Returns the highscores sorted
     * 
     * @return The highscores in an ArrayList
     */
    public ArrayList<Score> getScores() {
        ScoreComparator comp = new ScoreComparator();
        Collections.sort(scores, comp);
        return scores;
    }

    /**
     * Returns the highscores to be able to save or display them.
     * 
     * @param saveformat If true it will be a save format if false it will be a display format that is returned.
     * @return The highscore in a save format or a display format.
     */
    public String getHighScores(boolean saveformat) {
        String highScoreString = "";
        int max = 50;
        ArrayList<Score> res = getScores();
        int size = res.size();
        if (size > max) {
            size = max;
        }
        
        for (int i = 0; i < size; i++) {
            if (saveformat) {
                highScoreString += res.get(i).getName() + ":" + res.get(i).getScore() + ";";
            } else {
                highScoreString += (i + 1) + ".   " + res.get(i).getName() + "\t    " + controller.stringify(res.get(i).getScore()) + "\n";
            }
        }
        return highScoreString;
    }
}