package com.pega.samples.quiz.dao.impl;

import java.sql.Connection;

import com.pega.samples.quiz.dao.QuizDAO;

public class QuizDAOFactory {

	private static QuizDAO INSTANACE;

	/**
	 *constructor to initialize a DAO instance
	 * with a specified connection
	 */
	public static QuizDAO getInstance(Connection con) {
		if (INSTANACE == null) { 
			INSTANACE = new QuizDAOImpl(con);
		}
		return INSTANACE;
	}

	/**
	 * constructor to retrieve DAO instance
	 */
	public static QuizDAO getInstance() {
		if (INSTANACE == null) { 
			INSTANACE = new QuizDAOImpl();
		}
		return INSTANACE;
	}

}
