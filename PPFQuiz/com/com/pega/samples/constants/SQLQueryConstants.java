package com.pega.samples.constants;

public class SQLQueryConstants {
	public final static String SELECTQUESTIONS="Select * from Data_Quiz";
	public final static String CHECKONGOINGUSER="Select * from Ongoing_UserList where userName=?";	
	public final static String RETRIEVESCORE="Select * from Completed_ScoreList where userName=? And EssayScore IS NOT NULL";
	public final static String RETRIEVE_ESSAYS="Select * From Ongoing_Essays";
	public final static String UPDATE_ESSAY="Insert into Completed_EssayList values(?,?)";
	public final static String UPDATE_ESSAYSCORE="Update Completed_ScoreList Set EssayScore=? Where CaseID=?";
	public final static String SELECTFROMONGOING="SELECT TOP 1 * FROM Ongoing_UserList ORDER BY CaseID DESC";
	public final static String SELECTFROMCOMPLETED="SELECT TOP 1 * FROM Completed_ScoreList ORDER BY CaseID DESC";
	public final static String GETPASSWORD="Select Password From Users where userName=? and Role=?";
	public final static String INSERTQUESTION="Insert into Ongoing_Quiz values(?,?,?,?,?,?)";
	public final static String UPDATESCORE="Update Ongoing_Quiz Set ChosenAnswer=?,Score=? Where CaseID=? And ChosenAnswer IS NULL ";
	public final static String COPYRECORDS="Insert Into Completed_QuestionList(CaseID,QuestionID,CorrectAnswer,ChosenAnswer,userName) Select CaseID,QuestionID,CorrectAnswer,ChosenAnswer,userName From Ongoing_Quiz Where CaseID=?";
	public final static String DELETEQUESTIONS="Delete From Ongoing_Quiz Where CaseID=?";
	
	public final static String DELETE_ESSAYFROMONGOING="Delete From Ongoing_Essays Where CaseID=?";
	public final static String DELETEFROMONGOING="Delete From Ongoing_UserList Where CaseID=?";
	public final static String SELECTONGOINGUSERS="Select * From Ongoing_Quiz Where userName=? And CaseID=?";
	public final static String CREATEUSERINONGOING="Insert into Ongoing_UserList values(?,?,?)";
	
	public final static String UPDATE_ESSAYENTRY="Update Ongoing_UserList Set OnEssay=1 Where CaseID=?";
	public final static String CREATE_ONGOING_ESSAYRECORD="Insert into Ongoing_Essays values(?,?)";
	
	public final static String INSERTSCORE="Insert into Completed_ScoreList values(?,?,?,?)";
	
	
	

}
