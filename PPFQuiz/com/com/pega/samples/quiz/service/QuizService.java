package com.pega.samples.quiz.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.pega.samples.bean.Essays;
import com.pega.samples.bean.OngoingStatus;
import com.pega.samples.bean.PreviousState;
import com.pega.samples.bean.Question;
import com.pega.samples.bean.QuizParam;
import com.pega.samples.bean.Quizstep;
import com.pega.samples.bean.ResultStatus;
import com.pega.samples.quiz.model.Credentials;

public interface QuizService {
	/**
	 * @return CaseID from Database
	 * @throws SQLException
	 */
	public int getCaseID() throws SQLException;

	/**
	 * Validate User
	 * 
	 * @param cred
	 *            Credentials to be validated
	 * @return true if User exists with the role, false if doesn't.
	 * @throws SQLException
	 */
	public boolean validateUser(Credentials cred) throws SQLException;

	/**
	 * Check Whether Ongoing
	 */
	/**
	 * @param user
	 *            User Name
	 * @return Ongoing Status of the Type OngoingStatus
	 * @throws SQLException
	 */
	public OngoingStatus isOngoing(String user) throws SQLException;

	/**
	 * Check Whether is Completed and Retrieve Results if any
	 */
	/**
	 * @param user
	 *            User Name
	 * @return Result Status of that user including the results of type Result
	 * @throws SQLException
	 */
	public ResultStatus isCompleted(String user) throws SQLException;

	/**
	 * Retrieve Essays to be Assessed
	 */
	/**
	 * @return Map of type Essays with Case ID as key
	 * @throws SQLException
	 */
	public Map<Integer, Essays> RetrieveEssays() throws SQLException;

	/**
	 * Update The Score of Each Assessed Essay. Removes the Assessed Essay from
	 * the Map and returns it.
	 */
	/**
	 * @param map
	 *            Map of type Essays with Case ID as key
	 * @param CaseID
	 *            CaseID of the Quiz
	 * @param EssayScore
	 *            Essay Score of the Essays
	 * @return Map of type Essays with Case ID as key
	 * @throws SQLException
	 */
	public Map<Integer, Essays> UpdateEssay(Map<Integer, Essays> map, int CaseID, int EssayScore) throws SQLException;

	/**
	 * Get Next Question to be shown to the User
	 */
	/**
	 * @param QP
	 *            Quiz Parameter of the Quiz of type QuizParam
	 * @param QList
	 *            List of Integers containing the Question ID's of Questions
	 *            asked
	 * @param map
	 *            Map of type Question with Case ID as key
	 * @param CaseID
	 *            CaseID of the Quiz
	 * @param user
	 *            User Name of the Quiz
	 * @return Step value of the Quiz of type Quizstep
	 * @throws SQLException
	 */
	public Quizstep getQuestion(QuizParam QP, ArrayList<Integer> QList, Map<Integer, Question> map, int CaseID,
			String user) throws SQLException;

	/**
	 * Update the Score of the User
	 */
	/**
	 * @param option
	 *            String option selected by user
	 * @param CaseID
	 *            CaseID of the Quiz
	 * @param map
	 *            Map of type Question with Case ID as key
	 * @param QList
	 *            List of Integers containing the Question ID's of Question
	 *            asked
	 * @param QP
	 *            QP Quiz Parameter of the Quiz of type QuizParam
	 * @return QP Updated Quiz Parameter of the Quiz of type QuizParam
	 * @throws SQLException
	 */
	public QuizParam updateScore(String option, int CaseID, Map<Integer, Question> map, ArrayList<Integer> QList,
			QuizParam QP) throws SQLException;

	/**
	 * Update Case to Completed When Manager has Reviewed
	 */
	/**
	 * @param CaseID
	 *            CaseID of the Quiz
	 * @throws SQLException
	 */
	public void updateCase(int CaseID) throws SQLException;

	/**
	 * Get the Previous State of an Ongoing Quiz
	 */
	/**
	 * @param user
	 *            User Name
	 * @param map
	 *            Map of type Questions with Case ID as key
	 * @param CaseID
	 *            CaseID
	 * @return Previous Sate of the Case, of type PreviousState
	 * @throws SQLException
	 */
	public PreviousState getPreviousState(String user, Map<Integer, Question> map, int CaseID) throws SQLException;

	/**
	 * Creates an entry for a user for an Ongoing Quiz
	 */
	/**
	 * @param CaseID
	 *            CaseID of the User
	 * @param user
	 *            User Name
	 * @throws SQLException
	 */
	public void createOngoingEntry(int CaseID, String user) throws SQLException;

	/**
	 * Update Score and Difficulty
	 */
	/**
	 * @param CaseID
	 *            CaseID of the User
	 * @param Score
	 *            Updated Score of the Quiz
	 * @param user
	 *            User Name
	 * @throws SQLException
	 */
	public void updateOngoingEntry(int CaseID, int Score, String user) throws SQLException;

	/**
	 * Create An Essay Record for a Case
	 */
	/**
	 * @param essay
	 *            Essay of the User
	 * @param CaseID
	 *            CaseID of the Quiz
	 * @throws SQLException
	 */
	public void createEssayRecord(String essay, int CaseID) throws SQLException;

	/**
	 * Retrieve the Entire Questions from the Database in a Map
	 */
	/**
	 * @return Map of type Question with QuestionID as key
	 * @throws SQLException
	 */
	public Map<?, ?> getQuestionMap() throws SQLException;
}
