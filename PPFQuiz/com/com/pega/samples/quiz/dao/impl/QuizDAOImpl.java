/**
 * 
 */
package com.pega.samples.quiz.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pega.samples.bean.Essays;
import com.pega.samples.bean.OngoingStatus;
import com.pega.samples.bean.PreviousState;	
import com.pega.samples.bean.Question;
import com.pega.samples.bean.Result;
import com.pega.samples.bean.ResultStatus;
import com.pega.samples.quiz.dao.QuizDAO;
import com.pega.samples.quiz.model.Credentials;
import static com.pega.samples.constants.SQLQueryConstants.*;

/**
 * @author bajam
 *
 */
public class QuizDAOImpl implements QuizDAO {
	private final static Logger LOGGER = Logger.getLogger(QuizDAOImpl.class.getName());
	private Connection conn;

	QuizDAOImpl(Connection conn) {
		this.conn = conn;
	}

	QuizDAOImpl() {

	}

	public Map<Integer, Question> getQuestionMap() throws SQLException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Retrieving questions from Database");
		Map<Integer, Question> QMap = new HashMap<Integer, Question>();
		ResultSet rs;
		Statement st = conn.createStatement();
		rs = st.executeQuery(SELECTQUESTIONS);
		int i = 1;
		while (rs.next()) {
			Question ques = new Question();
			ques.setQuestionID(rs.getInt("SrNo"));
			ques.setCorrectAnswer(rs.getString("CorrectAnswer"));
			ques.setQuestion(rs.getString("Question"));
			ques.setDifficulty(rs.getInt("DifficultyLevel"));
			ques.getOptions().add(rs.getString("Option1"));
			ques.getOptions().add(rs.getString("Option2"));
			ques.getOptions().add(rs.getString("Option3"));
			QMap.put(i, ques);
			i++;
		}

		return QMap;

	}

	public OngoingStatus isOngoing(String user) throws SQLException {
		LOGGER.info("Checking for ongoing session of User:"+user);
		OngoingStatus obj = new OngoingStatus();
		obj.setOnEssay(false);
		obj.setStatus(false);
		PreparedStatement ps = conn.prepareStatement(CHECKONGOINGUSER);
		ps.setString(1, user);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			obj.setStatus(true);
			LOGGER.info("Retrieving essay status and CaseID");
			obj.setOnEssay(rs.getBoolean("OnEssay"));
			obj.setCaseID(rs.getInt("CaseID"));
			return obj;
		} else {
			return obj;
		}

	}

	public ResultStatus isCompleted(String user) throws SQLException {
		LOGGER.info("Checking for Previous Results of User:"+user);
		ResultStatus.setStatus(false);
		ResultStatus obj = new ResultStatus();
		PreparedStatement ps = conn
				.prepareStatement(RETRIEVESCORE);
		ps.setString(1, user);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Result QuizResult = new Result();
			ResultStatus.setStatus(true);
			QuizResult.setCaseID(rs.getInt("CaseID"));
			QuizResult.setEssayScore(rs.getInt("EssayScore"));
			QuizResult.setScore(rs.getInt("Score"));
			QuizResult.setUser(rs.getString("userName"));
			obj.getResults().add(QuizResult);

		}
		return obj;

	}

	public Map<Integer, Essays> RetrieveEssays() throws SQLException {
		LOGGER.info("Retrieving Essays from Database");
		Map<Integer, Essays> EssayList = new HashMap<Integer, Essays>();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(RETRIEVE_ESSAYS);
		while (rs.next()) {
			Essays obj = new Essays();
			obj.setCaseID(rs.getInt("CaseID"));
			obj.setEssays(rs.getString("Essay"));
			EssayList.put(obj.getCaseID(), obj);
		}
		return EssayList;
	}

	public Map<Integer, Essays> UpdateEssay(Map<Integer, Essays> map, int CaseID, int EssayScore) throws SQLException {
		LOGGER.info("Updating EssayScore");
		PreparedStatement ps1 = conn.prepareStatement(UPDATE_ESSAY);
		ps1.setInt(1, CaseID);
		ps1.setString(2, map.get(CaseID).getEssays());
		ps1.executeUpdate();

		PreparedStatement ps4 = conn.prepareStatement(UPDATE_ESSAYSCORE);
		ps4.setInt(1, EssayScore);
		ps4.setInt(2, CaseID);
		ps4.executeUpdate();

		map.remove(CaseID);
		return map;
	}

	
	public int getCaseIDfromOngoing() throws SQLException {
		ResultSet rs1;
		Statement st1 = conn.createStatement();
		LOGGER.info("Retrieving highest CaseID from Ongoging Cases List");
		rs1 = st1.executeQuery(SELECTFROMONGOING);
		if (rs1.next()) {

			return rs1.getInt("CaseID");
		} else
			return 0;
	}

	
	public int getCaseIDfromCompleted() throws SQLException {
		ResultSet rs1;
		Statement st1 = conn.createStatement();
		LOGGER.info("Retrieving highest CaseID from Completed Cases List");
		rs1 = st1.executeQuery(SELECTFROMCOMPLETED);
		if (rs1.next()) {

			return rs1.getInt("CaseID");
		} else
			return 0;
	}

	
	public String getPassword(Credentials cred) throws SQLException {
		PreparedStatement ps;
		ResultSet rs;
		String status = null;
		LOGGER.info("Retrieving password");
		ps = conn.prepareStatement(GETPASSWORD);
		ps.setString(1, cred.getUsername());
		ps.setString(2, cred.getRole());
		rs = ps.executeQuery();
		if (rs.next())
			status = rs.getString("Password");
		return status;
	}

	
	public void insertQuestion(Question q, int CaseID, int Score, String user) throws SQLException {
		PreparedStatement ps;
		LOGGER.info("Insert Question in the Database");
		ps = conn.prepareStatement(INSERTQUESTION);
		ps.setInt(1, CaseID);
		ps.setInt(2, q.getQuestionID());
		ps.setString(3, q.getCorrectAnswer());
		ps.setString(4, null);
		ps.setInt(5, Score);
		ps.setString(6, user);
		ps.executeUpdate();
	}

	
	public void updateScore(String option, int caseID, int Score) throws SQLException {
		LOGGER.info("Updating Score..");
		PreparedStatement ps1 = conn.prepareStatement(
				UPDATESCORE);
		ps1.setString(1, option);
		ps1.setInt(2, Score);
		ps1.setInt(3, caseID);
		ps1.execute();
	}

	
	public void moveRecords(int caseID) throws SQLException {
		LOGGER.info("Copying Questions from Ongoing Quiz to Completed Table");
		PreparedStatement ps1 = conn.prepareStatement(COPYRECORDS);
		ps1.setInt(1, caseID);
		ps1.execute();
		LOGGER.info("Deleting Questions form Ongoing Table");
		PreparedStatement ps2 = conn.prepareStatement(DELETEQUESTIONS);
		ps2.setInt(1, caseID);
		ps2.executeUpdate();
		LOGGER.info("Deleting Case from Ongoging Essays List");
		PreparedStatement ps3 = conn.prepareStatement(DELETE_ESSAYFROMONGOING);
		ps3.setInt(1, caseID);
		ps3.executeUpdate(); // delete from ongoing essays
		 // delete from ongoing userlist, closing the case

	}

	@Override
	public PreviousState getPreviousState(String user, Map<Integer, Question> map, int CaseID) throws SQLException {
		LOGGER.info("Retrieving Previous State of User");
		PreparedStatement ps1 = conn.prepareStatement(SELECTONGOINGUSERS,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ps1.setString(1, user);
		ps1.setInt(2, CaseID);
		ResultSet rs = ps1.executeQuery();
		PreviousState PS = new PreviousState();
		ArrayList<Integer> QList = new ArrayList<Integer>();
		while (rs.next()) {
			QList.add(rs.getInt("QuestionID"));
		}
		PS.setQList(QList);
		rs.last();
		PS.setCaseID(rs.getInt("CaseID"));
		PS.setQues(map.get(rs.getInt("QuestionID")));
		PS.setScore(rs.getInt("Score"));
		return PS;
	}

	@Override
	public void createOngoingEntry(int CaseID, String user) throws SQLException {
		LOGGER.info("Creating record of user:"+user+" with CaseID:"+CaseID);
		PreparedStatement ps = conn.prepareStatement(CREATEUSERINONGOING);
		ps.setInt(1, CaseID);
		ps.setFloat(2, 0);
		ps.setString(3, user);
		ps.executeUpdate();
		

	}

	public void updateOngoingEssayEntry(int CaseID) throws SQLException {
		LOGGER.info("Updating Essay of CaseID:"+CaseID);
		PreparedStatement ps = conn.prepareStatement(UPDATE_ESSAYENTRY);
		ps.setInt(1, CaseID);
		ps.executeUpdate();
		

	}

	
	public void createEssayRecord(String essay, int CaseID) throws SQLException {
		LOGGER.info("Creating essay record of  CaseID:"+CaseID);
		PreparedStatement ps = conn.prepareStatement(CREATE_ONGOING_ESSAYRECORD);
		ps.setInt(1, CaseID);
		ps.setString(2, essay);
		ps.executeUpdate();
		LOGGER.info("Deleting Case from Ongoing Users List");
		PreparedStatement ps2 = conn.prepareStatement(DELETEFROMONGOING);
		ps2.setInt(1, CaseID);
		ps2.executeUpdate();

	}

	
	public void updateScoreList(int CaseID, int Score, String user) throws SQLException {
		LOGGER.info("Updating Score of  user:"+user+" with Score:"+Score);
		PreparedStatement ps = conn.prepareStatement(INSERTSCORE);
		ps.setInt(1, CaseID);
		ps.setInt(2, Score);
		ps.setNull(3, Types.INTEGER);
		ps.setString(4, user);
		ps.executeUpdate();

	}
}
