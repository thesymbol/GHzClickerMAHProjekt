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
	
	public void clear() {
	    scores.clear();
	}
	
	private boolean updateScore(String name, double score) {
	    for(int i = 0; i < scores.size(); i++) {
            if(scores.get(i).getName().equals(name)) {
                scores.get(i).setScore(score);
                return true;
            }
        }
	    return false;
	}
	
	/**
	 * Adds a new Score to the highscore
	 * @param name the name of scoreholder
	 * @param score the score
	 */
	public void addScore(String name, double score) {
	    if(!updateScore(name, score)) {
	        scores.add(new Score(name, score));
	    }
	}
	/**
	 * Returns the highscores sorted
	 * @return the highscores
	 */
	public ArrayList<Score> getScores(){
	    ScoreComparator comp = new ScoreComparator();
        Collections.sort(scores, comp);
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
		int size = res.size();
		if(size>max){
			size = max;
		}
		
		for (int i = 0; i < size; i++) {
			highScoreString += (i + 1) + ".   " + res.get(i).getName() + "\t    " + stringify(res.get(i).getScore()) + "\n";
		}
		
		return highScoreString;
	}
	public String getHighScoresToSave(){
		String highscoresToSave = "";
		int max = 50;
		ArrayList<Score> res = getScores();
		int size = res.size();
		if(size>max){
			size = max;
		}
		
		for (int i = 0; i < size; i++) {
			highscoresToSave += res.get(i).getName() + ":" + res.get(i).getScore() + ";";
		}
		
		return highscoresToSave;
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
