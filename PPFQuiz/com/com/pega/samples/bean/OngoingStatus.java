package com.pega.samples.bean;

/**
 * @author bajam
 * 
 *         This class is used for storing User Status , whether it has an
 *         ongoing Quiz(status). Retrieves the CaseID if it exists. Also stores
 *         whether the user is on Essay part of the Quiz, if ongoing(onEssay).
 *
 */

public class OngoingStatus {
	private boolean status;
	private boolean onEssay;
	private int CaseID;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isOnEssay() {
		return onEssay;
	}

	public void setOnEssay(boolean onEssay) {
		this.onEssay = onEssay;
	}

	public int getCaseID() {
		return CaseID;
	}

	public void setCaseID(int caseID) {
		CaseID = caseID;
	}
}
