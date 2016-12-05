package com.pega.samples.quiz.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import static com.pega.samples.constants.ServletConstants.ERROR;

/**
 * Servlet Filter implementation class LoginServletFilter
 */
@WebFilter(value = { "/LoginServlet" })
public class LoginServletFilter implements Filter {

	private final static Logger LOGGER = Logger.getLogger(LoginServletFilter.class.getName());

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		boolean error = (boolean) request.getServletContext().getAttribute(ERROR);
		if (error) {
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.severe("Recieved error while initializing application");

			RequestDispatcher rd = request.getRequestDispatcher("DBerror.jsp");
			rd.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

}
