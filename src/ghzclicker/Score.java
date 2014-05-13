package ghzclicker;

import java.io.Serializable;

/**
 * Class used to hold names and scores for the highscore
 * @author Viktor Saltarski
 *
 */
public class Score implements Serializable {
	private double score;
	private String name;
	
	public Score(String name, double score){
		this.score = score;
		this.name = name;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
