package com.pega.samples.bean;

/**
 * @author bajam This class stores the Parameters of the quiz which change at
 *         every step of the Quiz. The Difficulty level here can be used to
 *         retrieve a question of particular difficulty Score used to update the
 *         score at each step
 * 
 */
public class QuizParam {
	private int diff; // Current Difficulty of the question, the user is at
	private int score; // Current Score he is at

	public int getDiff() {
		return diff;
	}

	public void setDiff(int diff) {
		this.diff = diff;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
