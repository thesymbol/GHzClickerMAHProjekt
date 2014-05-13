package ghzclicker;

import java.text.DecimalFormat;
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
			highScoreString += (i + 1) + ".   " + scores.get(i).getName() + "\t    " + stringify(scores.get(i).getScore()) + "\n";
		}
		
		return highScoreString;
	}
	/**
     * Changes the visual of costs and hertz
     * 
     * @param value The value that is going to be used to create a prefix'ed string.
     * @return prefixed string with M B T or something else at the end.
     */
    public String stringify(double value) {
        String[] format = { "", " K", " M", " G", " T", " Qa", " Qi", " Sx", " Sp", "Oc", "No", "Dc" };
        double temp = value;
        int order = 0;
        while (temp > 1000.0) {
            temp /= 1000.0;
            order += 1;
        }
        while (temp < 1.0 && temp > 0) {
            temp *= 1000.0;
            order -= 1;
        }
        DecimalFormat formatter = new DecimalFormat("#.###");
        return formatter.format(temp) + format[order] + "Hz";
    }
}
