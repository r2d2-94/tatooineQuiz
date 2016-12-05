package com.pega.samples.bean;

import java.util.ArrayList;

/**
 * @author bajam
 * 
 *         This class is used for storing the previous state of the user which
 *         has an ongoing quiz Stores the next question to be displayed to the
 *         user(Ques) QList stores the Question IDs asked before so that they
 *         are not repeated.
 *
 */
public class PreviousState {
	private Question Ques;
	private int CaseID;
	private ArrayList<Integer> QList;
	private int Score;

	public Question getQues() {
		return Ques;
	}

	public void setQues(Question ques) {
		Ques = ques;
	}

	public int getCaseID() {
		return CaseID;
	}

	public void setCaseID(int caseID) {
		CaseID = caseID;
	}

	public ArrayList<Integer> getQList() {
		return QList;
	}

	public void setQList(ArrayList<Integer> qList) {
		QList = qList;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	};
}