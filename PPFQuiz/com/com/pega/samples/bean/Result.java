package com.pega.samples.bean;

/**
 * @author bajam This class is used for storing the Result of a Completed Quiz
 *
 */
public class Result {
	private int caseID;
	private int score;
	private int essayScore;
	private String user;

	public int getCaseID() {
		return caseID;
	}

	public void setCaseID(int caseID) {
		this.caseID = caseID;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getEssayScore() {
		return essayScore;
	}

	public void setEssayScore(int essayScore) {
		this.essayScore = essayScore;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
