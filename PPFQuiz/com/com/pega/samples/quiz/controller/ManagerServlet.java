package com.pega.samples.quiz.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pega.samples.bean.Essays;
import com.pega.samples.quiz.service.QuizService;
import com.pega.samples.quiz.service.impl.QuizServiceFactory;
import static com.pega.samples.constants.ServletConstants.ESSAYMAP;
import static com.pega.samples.constants.ServletConstants.ESSAYSCORE;
import static com.pega.samples.constants.ServletConstants.CASEID;

/**
 * Servlet implementation class EssayHandler
 */
@WebServlet("/EssayHandler")
public class ManagerServlet extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger(ManagerServlet.class.getName());
	private static final long serialVersionUID = -3014649726388444431L;
	private static QuizService service;

	public ManagerServlet() {
		service = QuizServiceFactory.getInstance();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// get Essay
			int EssayScore = Integer.parseInt(request.getParameter(ESSAYSCORE));
			LOGGER.setLevel(Level.INFO);
			// Retrieve Map of all Essay
			@SuppressWarnings("unchecked")
			Map<Integer, Essays> map = (Map<Integer, Essays>) request.getSession().getAttribute(ESSAYMAP);
			int CaseID = Integer.parseInt(request.getParameter(CASEID));
			LOGGER.info("Recived Essay Score:" + EssayScore + " of CaseID:" + CaseID);
			LOGGER.info("Updating EssayScore");
			// Update Map of all Essay
			Map<Integer, Essays> NewMap = service.UpdateEssay(map, CaseID, EssayScore);
			LOGGER.info("Updating CaseID:" + CaseID);
			service.updateCase(CaseID);
			request.getSession().setAttribute(ESSAYMAP, NewMap);
			// Display Essay
			RequestDispatcher rd = request.getRequestDispatcher("EssayReview.jsp");
			rd.forward(request, response);

		} catch (SQLException dbe) {
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.severe("Error in Updating Essay");
			response.sendRedirect("DBerror.jsp");
		} catch (Exception e) {
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.severe("Error in updating Essay Score");
			response.sendRedirect("error.jsp");

		}
	}

}
