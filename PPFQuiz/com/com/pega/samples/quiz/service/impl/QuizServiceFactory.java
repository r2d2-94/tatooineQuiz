/**
 * 
 */
package com.pega.samples.quiz.service.impl;

import com.pega.samples.quiz.service.QuizService;

/**
 * @author bajam Gives the instance of a Service Object
 *
 */
public class QuizServiceFactory {

	private static QuizService service;

	public static QuizService getInstance() {

		if (service == null) { /*
								 * Singleton model, will create a service object
								 * only if a previous doesn;t exist
								 */
			service = new QuizServiceImpl();
		}

		return service;
	}
}
