package com.pega.samples.quiz.listener;

import static com.pega.samples.quiz.listener.DBcredentials.dbURL;
import static com.pega.samples.quiz.listener.DBcredentials.password;
import static com.pega.samples.quiz.listener.DBcredentials.user;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.pega.samples.quiz.dao.QuizDAO;
import com.pega.samples.quiz.dao.impl.QuizDAOFactory;
import com.pega.samples.quiz.service.QuizService;
import com.pega.samples.quiz.service.impl.QuizServiceFactory;

/**
 * Application Lifecycle Listener implementation class initialize
 *
 */
@WebListener
public class ApplicationInitializer implements ServletContextListener {
	private final static Logger LOGGER = Logger.getLogger(ApplicationInitializer.class.getName());

	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			LOGGER.setLevel(Level.INFO);
			LOGGER.info("Closing Database Connection");
			ConnectionUtil.closeConnection(); // Close JDBC connection
		} catch (SQLException e) {
			LOGGER.setLevel(Level.WARNING);
			LOGGER.warning("Unable to close JDBC Connection");
		}

	}

	public void contextInitialized(ServletContextEvent ServEvent) {

		try {
			LOGGER.setLevel(Level.INFO);
			LOGGER.info("Retrieving DAO Instance");
			QuizDAO dao = QuizDAOFactory.getInstance(ConnectionUtil.getConnection(dbURL, user,
					password)); /* Create DAO Object */
			LOGGER.info("Retrieving Service Instance");
			QuizService service = QuizServiceFactory
					.getInstance(); /* Create Service Object */
			LOGGER.info("Loading Questions From DataBase");
			ServEvent.getServletContext().setAttribute("QuestionList",
					dao.getQuestionMap()); /* Retrieve Questions */
			LOGGER.info("Loading CaseID From Database");
			ServEvent.getServletContext().setAttribute("CaseIDFromDB",
					service.getCaseID()); /* Retrieve CaseID */
			boolean error = false;
			ServEvent.getServletContext().setAttribute("error", error);
		} catch (Exception e) {
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.severe("Error Initializing");
			boolean error = true;
			ServEvent.getServletContext().setAttribute("error", error);

		}

	}

}
