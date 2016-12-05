package com.pega.samples.quiz.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pega.samples.bean.Question;
import com.pega.samples.bean.QuizParam;
import com.pega.samples.bean.Quizstep;
import com.pega.samples.quiz.service.QuizService;
import com.pega.samples.quiz.service.impl.QuizServiceFactory;
import static com.pega.samples.constants.ServletConstants.*;

@WebServlet("/FirstQuestion")
public class StartQuizServlet extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger(StartQuizServlet.class.getName());
	private static final long serialVersionUID = 993882327331539101L;
	private static QuizService service;

	public StartQuizServlet() {
		// get Service Object
		service = QuizServiceFactory.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// get User Name
			String username = (String) request.getSession().getAttribute(USER);
			// get CaseID
			int CaseID = (int) request.getServletContext().getAttribute(CASEIDFROMDB);
			LOGGER.setLevel(Level.INFO);
			LOGGER.info("Starting the Quiz for " + username + " with CaseID:" + CaseID);
			// Retrieving the Questions loaded in Listener
			@SuppressWarnings("unchecked")
			Map<Integer, Question> map = (Map<Integer, Question>) request.getServletContext()
					.getAttribute(QUESTIONLIST);
			// Initialize Quiz Parameters
			QuizParam QP = new QuizParam();
			// Initial Difficulty to be middle of 0 and 2
			QP.setDiff(1);
			// Initial Score to be 0
			QP.setScore(0);
			ArrayList<Integer> QList = new ArrayList<Integer>();
			LOGGER.info("Creating Entry for User:" + username);
			// Create Database Entry for this quiz
			service.createOngoingEntry(CaseID, username);
			Quizstep step = new Quizstep();
			LOGGER.info("Retrieving Question for User");
			// Retrieve Questions and List of Questions asked
			step = service.getQuestion(QP, QList, map, CaseID, username);
			// set Session Attributes
			request.getSession().setAttribute(QUIZPARAM, QP);
			request.getSession().setAttribute(STEP, step);
			request.getSession().setAttribute(CASEID, CaseID);
			// Increase CaseID for next Case
			request.getServletContext().setAttribute(CASEIDFROMDB, ++CaseID);
			// Forward to view
			RequestDispatcher rd = request.getRequestDispatcher("QuizHome.jsp");
			rd.forward(request, response);
		} catch (SQLException dbe) {
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.severe("Error in Updating Essay");
			response.sendRedirect("DBerror.jsp");
		} catch (Exception e) {
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.severe("Error in Strating the Quiz");
			response.sendRedirect("error.jsp");
		}
	}

}
