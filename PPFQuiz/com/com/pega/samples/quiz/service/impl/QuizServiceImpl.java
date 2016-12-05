package com.pega.samples.quiz.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pega.samples.bean.Essays;
import com.pega.samples.bean.OngoingStatus;
import com.pega.samples.bean.PreviousState;
import com.pega.samples.bean.Question;
import com.pega.samples.bean.QuizParam;
import com.pega.samples.bean.Quizstep;
import com.pega.samples.bean.ResultStatus;
import com.pega.samples.quiz.dao.QuizDAO;
import com.pega.samples.quiz.dao.impl.QuizDAOFactory;
import com.pega.samples.quiz.model.Credentials;
import com.pega.samples.quiz.service.QuizService;

/**
 * @author bajam Implementation of the QuizService class
 *
 */
public class QuizServiceImpl implements QuizService {
	private final static Logger LOGGER = Logger.getLogger(QuizServiceImpl.class.getName());
	private QuizDAO dao;

	QuizServiceImpl() {
		dao = QuizDAOFactory.getInstance();
	}

	public boolean validateUser(Credentials cred) throws SQLException {

		String pwd = dao.getPassword(cred);
		if (Objects.equals(cred.getPassword(), pwd)) { // Compare passwords
			return true;
		} else
			return false;
	}

	public OngoingStatus isOngoing(String user) throws SQLException {

		return dao.isOngoing(user);
	}

	public ResultStatus isCompleted(String user) throws SQLException {

		return dao.isCompleted(user);
	}

	public Map<Integer, Essays> RetrieveEssays() throws SQLException {
		return dao.RetrieveEssays();
	}

	public Map<Integer, Essays> UpdateEssay(Map<Integer, Essays> map, int CaseID, int EssayScore) throws SQLException {
		return dao.UpdateEssay(map, CaseID, EssayScore);
	}

	public int getCaseID() throws SQLException {

		return Math.max(dao.getCaseIDfromOngoing(), dao.getCaseIDfromCompleted())
				+ 1; /*
						 * Adding +1 to maximum of both for the current case
						 * Allotment
						 */
	}

	public Quizstep getQuestion(QuizParam QP, ArrayList<Integer> QList, Map<Integer, Question> map, int CaseID,
			String user) throws SQLException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("");
		int rnd = (int) Math
				.floor(Math.random() * map.size() + 1); /*
													 * Generate random question
													 * number between the limits
													 */
		LOGGER.info("Random number generated:"+rnd);
		while (QList.contains(rnd)) {
			if (map.get(rnd).getDifficulty() == QP.getDiff()) { // break if
																// difficulty
																// matches
				break;
			} else {
				LOGGER.info("Random number generated:"+rnd);
				rnd = (int) Math.floor(Math.random() * map.size()); // Generate
																	// again
			}

		}
		QList.add(rnd); // add to List of Questions asked
		dao.insertQuestion(map.get(rnd), CaseID, QP.getScore(),
				user); /*
						 * insert this question in db
						 */
		Quizstep qstep = new Quizstep();
		qstep.setQlist(QList); // send List back
		qstep.setQuestion(map.get(rnd)); // send Question back
		return qstep;
	}

	public QuizParam updateScore(String option, int CaseID, Map<Integer, Question> map, ArrayList<Integer> QList,
			QuizParam QP) throws SQLException {
		LOGGER.info("Validating user selected option:"+option);
		if (Objects.equals(option, map.get(QList.get(QList.size() - 1)).getCorrectAnswer())) { // Check
			LOGGER.info("Selected right answer");																				// answer
			if (QP.getDiff() < 2)
			{	LOGGER.info("Previous difficulty"+QP.getDiff());
				QP.setDiff(QP.getDiff() + 1); // Update difficulty level for
				LOGGER.info("Updated difficulty:"+QP.getDiff());								// correct answer
			}
			QP.setScore(QP.getScore() + 1);
			LOGGER.info("Updated Score"+QP.getScore());
			

		} else {
			if (QP.getDiff() > 0)
			{		LOGGER.info("Previous difficulty"+QP.getDiff());	// Update difficulty for wrong answer
				QP.setDiff(QP.getDiff() - 1);
				LOGGER.info("Updated difficulty:"+QP.getDiff());
			}
		}
		dao.updateScore(option, CaseID, QP.getScore()); // Update score in db
		return QP;

	}

	public void updateCase(int CaseID) throws SQLException {
		dao.moveRecords(CaseID);

	}

	public PreviousState getPreviousState(String user, Map<Integer, Question> map, int CaseID) throws SQLException {
		return dao.getPreviousState(user, map, CaseID);
	}

	public void createOngoingEntry(int CaseID, String user) throws SQLException {
		dao.createOngoingEntry(CaseID, user);

	}

	public void updateOngoingEntry(int CaseID, int Score, String user) throws SQLException {
		dao.updateOngoingEssayEntry(CaseID);
		dao.updateScoreList(CaseID, Score, user);

	}

	public void createEssayRecord(String essay, int CaseID) throws SQLException {

		dao.createEssayRecord(essay, CaseID);

	}

	public Map<?, ?> getQuestionMap() throws SQLException {

		return dao.getQuestionMap();
	}

}
