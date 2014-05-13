package ghzclicker;

import java.io.Serializable;

/**
 * Class used to hold names and scores for the highscore
 * 
 * @author Viktor Saltarski
 * 
 */
public class Score implements Serializable {
    private static final long serialVersionUID = 1L;
    private int score;
    private String name;

    public Score(int score, String name) {
        this.score = score;
        this.name = name;
    }

    /**
     * Getting the score
     * 
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Setting the score
     * 
     * @param score Inserted score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Getting the player name
     * 
     * @return name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Setting the name of the player
     * 
     * @param name Inserted name.
     */
    public void setName(String name) {
        this.name = name;
    }

}
