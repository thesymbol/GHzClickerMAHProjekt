package ghzclicker;

import java.util.*;

/**
 * Class that handles the logic behind the HighScore
 * @author Viktor Saltarski
 *
 */
public class HighScoreManager {
	private ArrayList<Score> scores;
	/**
	 * Creates a new HighScoreManager
	 */
	public HighScoreManager(){
		scores = new ArrayList<Score>();
	}
	/**
	 * Adds a new Score to the highscore
	 * @param name the name of scoreholder
	 * @param score the score
	 */
	public void addScore(String name, double score){
		scores.add(new Score(name,score));
		
	}
	/**
	 * Sorts the highscore
	 */
	public void sort(){
		ScoreComparator comp = new ScoreComparator();
		Collections.sort(scores, comp);
	}
	/**
	 * Returns the highscores sorted
	 * @return the highscores
	 */
	public ArrayList<Score> getScores(){
		sort();
		return scores;
	}
	/**
	 * Returns a string holding the highscores
	 * @return the highscore string
	 */
	public String getHighScoreString(){
		String highScoreString = "";
		int max = 50;
		ArrayList<Score> res = getScores();
		int size = scores.size();
		if(size>max){
			size = max;
		}
		
		for (int i = 0; i < size; i++) {
			highScoreString += (i + 1) + ".   " + scores.get(i).getName() + "\t" + scores.get(i).getScore() + "\n";
		}
		
		return highScoreString;
	}
}
