package com.pega.samples.bean;

import java.util.ArrayList;

/**
 * @author bajam This class stores the question to be displayed.
 *
 */
public class Quizstep {

	private Question question; // Question
	private ArrayList<Integer> Qlist; // List of Questions already asked to the
										// user

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public ArrayList<Integer> getQlist() {
		return Qlist;
	}

	public void setQlist(ArrayList<Integer> qlist) {
		Qlist = qlist;
	}

}
