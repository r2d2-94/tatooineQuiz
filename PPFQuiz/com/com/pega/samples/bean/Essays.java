package com.pega.samples.bean;

import java.io.Serializable;

/**
 * @author bajam
 * 
 *         This class is user for storing Essay along with the CaseID for
 *         retrieval during Essay Assessment Serializable because it needs to be
 *         processed by JSP
 *
 */
public class Essays implements Serializable {

	private static final long serialVersionUID = -2002384702811202551L;
	private int CaseID;
	private String Essays;

	public int getCaseID() {
		return CaseID;
	}

	public void setCaseID(int caseID) {
		CaseID = caseID;
	}

	public String getEssays() {
		return Essays;
	}

	public void setEssays(String essays) {
		Essays = essays;
	}
}
