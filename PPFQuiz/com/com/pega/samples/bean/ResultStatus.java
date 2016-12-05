/**
 * 
 */
package com.pega.samples.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bajam This class is used for checking if the user has a Completed
 *         Reviewed Quiz Status will be true if the user has, and all the
 *         results of the user will be stored in a List of Result type
 *
 */
public class ResultStatus {
	private static boolean status;
	private List<Result> results = new ArrayList<>();

	public static boolean isStatus() {
		return status;
	}

	public static void setStatus(boolean status) {
		ResultStatus.status = status;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	};
}
