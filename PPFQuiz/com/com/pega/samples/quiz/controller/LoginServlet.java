package com.pega.samples.quiz.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pega.samples.bean.Essays;
import com.pega.samples.bean.OngoingStatus;
import com.pega.samples.bean.PreviousState;
import com.pega.samples.bean.Question;
import com.pega.samples.bean.QuizParam;
import com.pega.samples.bean.Quizstep;
import com.pega.samples.bean.ResultStatus;
import com.pega.samples.quiz.model.Credentials;
import com.pega.samples.quiz.service.QuizService;
import com.pega.samples.quiz.service.impl.QuizServiceFactory;
import static com.pega.samples.constants.ServletConstants.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "Controller for handling Event when a User Logs into Web Application", urlPatterns = {
		"/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
	private static final long serialVersionUID = 3143815372050508394L;
	private static QuizService service;

	public LoginServlet() {
		// get Service Object
		service = QuizServiceFactory.getInstance();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String role = request.getParameter(ROLE);
			String username = request.getParameter(NAME);
			String password = request.getParameter(PASSWORD);
			LOGGER.setLevel(Level.INFO);
			LOGGER.info("Logged in as " + username + "for role:" + role);
			// Create Credential Object
			Credentials cred = new Credentials(username, password, role);
			LOGGER.info("Retrieving Questions");
			@SuppressWarnings("unchecked")
			Map<Integer, Question> map = (Map<Integer, Question>) request.getServletContext()
					.getAttribute(QUESTIONLIST);
			LOGGER.info("Validating Credentials");
			if (service.validateUser(cred)) {
				// set User Session
				request.getSession().setAttribute(USER, username);
				LOGGER.info("Logged in ");
				LOGGER.info("Checking for Ongoing Quiz");
				// Check Ongoing Quiz
				OngoingStatus obj = service.isOngoing(username);
				if (Objects.equals(cred.getRole(), USER)) {
					if (obj.isStatus() == false) {
						LOGGER.info("Loading previous Results");
						ResultStatus QuizResult = service.isCompleted(username);
						request.getSession().setAttribute(RESULTS, QuizResult);
						// display Results
						RequestDispatcher rd = request.getRequestDispatcher("ResultReview.jsp");
						rd.forward(request, response);

					} else if (obj.isStatus() == true) {
						LOGGER.info("A previous ongoing quiz exists");
						if (obj.isOnEssay() == true) {
							LOGGER.info("User on Essay Question...");
							request.getSession().setAttribute(CASEID, obj.getCaseID());
							// load to essay page
							RequestDispatcher rd = request.getRequestDispatcher("Essay.jsp");
							rd.forward(request, response);

						} else {
							LOGGER.info("User in middle... Loading previous quiz");
							// Get Previous State
							PreviousState PS = service.getPreviousState(username, map, obj.getCaseID());
							QuizParam QP = new QuizParam();
							QP.setDiff(PS.getQues().getDifficulty());
							QP.setScore(PS.getScore());
							Quizstep step = new Quizstep();
							step.setQlist(PS.getQList());
							step.setQuestion(PS.getQues());
							request.getSession().setAttribute(CASEID, PS.getCaseID());
							request.getSession().setAttribute(QUIZPARAM, QP);
							request.getSession().setAttribute(STEP, step);
							// load to middle of the quiz
							RequestDispatcher rd = request.getRequestDispatcher("QuizHome.jsp");
							rd.forward(request, response);

						}
					}
				} else if (Objects.equals(cred.getRole(), MANAGER)) {
					LOGGER.info("Retrieving Essay");
					// Retrieve Essays
					Map<Integer, Essays> EssayMap = service.RetrieveEssays();
					request.getSession().setAttribute(ESSAYMAP, EssayMap);
					// Display Essays
					RequestDispatcher rd = request.getRequestDispatcher("EssayReview.jsp");
					rd.forward(request, response);
				}

			} else {
				if (Objects.equals(cred.getRole(), USER)) {
					LOGGER.info("User not available");
					// Send back to Student Login page with error
					response.sendRedirect("StudentHome.jsp?error=true");
				} else if (Objects.equals(cred.getRole(), MANAGER)) {
					LOGGER.info("Manager not available");
					// Send back to Manager Login page with error
					response.sendRedirect("ManagerHome.jsp?error=true");
				}

			}

		} catch (SQLException dbe) {
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.severe("Error in Updating Essay");
			response.sendRedirect("DBerror.jsp");
		} catch (Exception e) {
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.severe("Error Ocurred while Logging");
			response.sendRedirect("error.jsp"); // Server Error page
		}

	}

}
