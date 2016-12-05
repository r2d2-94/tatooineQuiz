package com.pega.samples.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bajam
 * 
 *         This class is used for storing Questions and their related properties
 */
public class Question {
	private int QuestionID;
	private String Question;
	private List<String> Options = new ArrayList<>();
	private String CorrectAnswer;
	private int Difficulty;

	public int getQuestionID() {
		return QuestionID;
	}

	public void setQuestionID(int questionID) {
		this.QuestionID = questionID;
	}

	public String getQuestion() {
		return Question;
	}

	public void setQuestion(String question) {
		this.Question = question;
	}

	public List<String> getOptions() {
		return Options;
	}

	public void setOptions(List<String> options) {
		this.Options = options;
	}

	public String getCorrectAnswer() {
		return CorrectAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.CorrectAnswer = correctAnswer;
	}

	public int getDifficulty() {
		return Difficulty;
	}

	public void setDifficulty(int difficulty) {
		Difficulty = difficulty;
	}
}
