package com.pega.samples.quiz.dao;

import java.sql.SQLException;
import java.util.Map;

import com.pega.samples.bean.Essays;
import com.pega.samples.bean.OngoingStatus;
import com.pega.samples.bean.PreviousState;
import com.pega.samples.bean.Question;
import com.pega.samples.bean.ResultStatus;
import com.pega.samples.quiz.model.Credentials;

public interface QuizDAO {
	/**
	 * Get Password for the user and role
	 * 
	 * @param cred
	 *            Credentials of the user(name,password, role)
	 * @return password of the user from DataBase
	 * @throws SQLException
	 */
	public String getPassword(Credentials cred) throws SQLException;

	/**
	 * Retrieve the Question Map containing all Question
	 * 
	 * @return Map of type Question with Question IDs as key
	 * @throws SQLException
	 */
	public Map<?, ?> getQuestionMap() throws SQLException;

	/**
	 * get the Latest CaseID from Ongoing Quizzes
	 *
	 * @return Latest Case ID
	 * @throws SQLException
	 */
	public int getCaseIDfromOngoing() throws SQLException;

	/**
	 * get the Latest CaseID from Completed Quizzes
	 *
	 * @return Latest Case ID
	 * @throws SQLException
	 */
	public int getCaseIDfromCompleted() throws SQLException;

	/**
	 * Check whether the User has an Ongoing quiz and return the quiz status and
	 * essay status
	 *
	 * @param user
	 *            User Name
	 * @return Ongoing Status of that user of type OngoingStatus
	 * @throws SQLException
	 */
	public OngoingStatus isOngoing(String user) throws SQLException;

	/**
	 * Check whether the user has completed quizzes, and return the results of
	 * those quiz
	 * 
	 * @param user
	 * @return Result Status of that user including the results of type Result
	 * @throws SQLException
	 */
	public ResultStatus isCompleted(String user) throws SQLException;

	/**
	 * Retrieve Essays of all the unassessed Users in a Map
	 * 
	 * @return Map of type Essays with CaseID as key
	 * @throws SQLException
	 */
	public Map<Integer, Essays> RetrieveEssays() throws SQLException;

	/**
	 * Update the Essay Map.Removes the assessed essay form the map and stores
	 * the score against the CaseID in DataBase
	 * 
	 * @param map
	 *            Map of all Essays
	 * @param CaseID
	 *            CaseID of the assessed Case
	 * @param EssayScore
	 *            Essay Score to be updated against the CaseID
	 * @return Map of type Essays with CaseID as key
	 * @throws SQLException
	 */
	public Map<Integer, Essays> UpdateEssay(Map<Integer, Essays> map, int CaseID, int EssayScore) throws SQLException;

	/**
	 * Insert Question into Database
	 * 
	 * @param Q
	 *            Question to be inserted
	 * @param caseID
	 *            CaseId of the Quiz
	 * @param score
	 *            Current Score of the CaseID
	 * @param user
	 *            User Name
	 * @throws SQLException
	 */
	public void insertQuestion(Question Q, int caseID, int score, String user) throws SQLException;

	/**
	 * Update the Score of the User along with the Option chosen.To be called at
	 * each step of the Quiz
	 * 
	 * @param option
	 *            Option selected by the user
	 * @param caseID
	 *            CaseId of the Quiz
	 * @param Score
	 *            Updated Score of the user
	 * @throws SQLException
	 */
	public void updateScore(String option, int caseID, int Score) throws SQLException;

	/**
	 * Copies questions asked to user from an ongoing quiz to a completed quiz
	 * database. Also delete the records of the CaseID from all the Ongoing
	 * Database(Ongoing User, Ongoing Questions, Ongoing Essays)
	 * 
	 * @param caseID
	 *            CaseID of the Quiz
	 * @throws SQLException
	 */
	public void moveRecords(int caseID) throws SQLException;

	/**
	 * Gets the previous state of User and Retrieves necessary data in Previous
	 * State
	 * 
	 * @param user
	 *            User Name
	 * @param Map
	 *            of type Question with Question IDs as key
	 * @param caseID
	 *            CaseID of the Quiz
	 * @return Previous State of the Case of Type PreviousState
	 * @throws SQLException
	 */
	public PreviousState getPreviousState(String user, Map<Integer, Question> map, int caseID) throws SQLException;

	/**
	 * Creates an Entry of the User in Ongoing User Database
	 * 
	 * @param CaseID
	 *            CaseID of the Quiz
	 * @param user
	 *            User Name of the Case
	 * @throws SQLException
	 */
	public void createOngoingEntry(int CaseID, String user) throws SQLException;

	/**
	 * Updates the Essay Status of the User in an ongoing quiz
	 * 
	 * @param CaseID
	 *            Case ID of the Quiz
	 * @throws SQLException
	 */
	public void updateOngoingEssayEntry(int CaseID) throws SQLException;

	/**
	 * Creates an Essay Record in Database againt a user and databse
	 * 
	 * @param essay
	 *            Essay to be updated
	 * @param CaseID
	 *            Case ID of the Quiz
	 * @throws SQLException
	 */
	public void createEssayRecord(String essay, int CaseID) throws SQLException;

	/**
	 * Updates the score of the user
	 * 
	 * @param CaseID
	 *            CaseID of the Quiz
	 * @param Score
	 *            Final Score of the Quiz
	 * @param user
	 *            User Name
	 * @throws SQLException
	 */
	public void updateScoreList(int CaseID, int Score, String user) throws SQLException;

}
