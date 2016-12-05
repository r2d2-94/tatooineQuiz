package com.pega.samples.quiz.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pega.samples.bean.Question;
import com.pega.samples.bean.QuizParam;
import com.pega.samples.bean.Quizstep;
import com.pega.samples.quiz.service.QuizService;
import com.pega.samples.quiz.service.impl.QuizServiceFactory;
import static com.pega.samples.constants.ServletConstants.*;

/**
 * Servlet implementation class QuizHandler
 */
@WebServlet("/QuizHandler")
public class QuizHandler extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger(QuizHandler.class.getName());
	private static final long serialVersionUID = -185448669273769006L;
	private static QuizService service;

	public QuizHandler() {
		service = QuizServiceFactory.getInstance();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String option = request.getParameter(OPTION);
			int CaseID = (int) request.getSession().getAttribute(CASEID);
			Quizstep step = (Quizstep) request.getSession().getAttribute(STEP);
			QuizParam QP = (QuizParam) request.getSession().getAttribute(QUIZPARAM);
			String user = (String) request.getSession().getAttribute(USER);
			LOGGER.setLevel(Level.INFO);
			LOGGER.info("Recieved option :" + option + " from user :" + user);
			String json; // to be sent over to user-view page as a JSON object
			@SuppressWarnings("unchecked")
			Map<Integer, Question> map = (Map<Integer, Question>) request.getServletContext()
					.getAttribute(QUESTIONLIST);
			Gson gson = new Gson();
			LOGGER.info("Updating Score");
			QP = service.updateScore(option, CaseID, map, step.getQlist(), QP);
			if (step.getQlist().size() >= MAXQUESTIONS) {
				LOGGER.info("Ending the Quiz section");
				service.updateOngoingEntry(CaseID, QP.getScore(), user);
				json = gson.toJson(NULL);
			} else {
				LOGGER.info("Retrieving Question to be displayed to the User");
				Quizstep newStep = service.getQuestion(QP, step.getQlist(), map, CaseID, user);
				request.getSession().setAttribute(QUIZPARAM, QP);
				request.getSession().setAttribute(STEP, newStep);
				json = gson.toJson(newStep.getQuestion());
			}
			response.setContentType("application/json");
			response.getWriter().write(json);
			System.out.println(json);
		} catch (SQLException dbe) {
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.severe("Error in Updating Essay");
			response.sendRedirect("DBerror.jsp");
		} catch (Exception e) {
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.info("Error Ocuured in Quiz Process");
			response.sendRedirect("error.jsp");
		}
	}

}
