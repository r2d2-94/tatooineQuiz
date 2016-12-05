package com.pega.samples.quiz.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pega.samples.quiz.service.QuizService;
import com.pega.samples.quiz.service.impl.QuizServiceFactory;
import static com.pega.samples.constants.ServletConstants.*;

/**
 * Servlet implementation class EssayServlet
 */
@WebServlet(description = "Servlet which updates the essay against the CaseID", urlPatterns = { "/EssayServlet" })
public class EssayServlet extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger(EssayServlet.class.getName());
	private static final long serialVersionUID = 2983514257823057611L;
	private static QuizService service;

	public EssayServlet() {
		service = QuizServiceFactory.getInstance(); // Get Service Object

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get Essay
		String essay = request.getParameter(ESSAY);
		// get CaseID
		LOGGER.setLevel(Level.INFO);
		try {
			int CaseID = (int) request.getSession().getAttribute(CASEID);
			LOGGER.info("Received Essay for Case:" + CaseID);

			// Create Essay Record
			service.createEssayRecord(essay, CaseID);
			response.sendRedirect("Thankyou.html");
			;
		} catch (SQLException dbe) {
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.severe("Error in Updating Essay");
			response.sendRedirect("DBerror.jsp");
		} catch (Exception e) {
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.severe("Error in Updating Essay");
			response.sendRedirect("error.jsp");

		}

	}

}
