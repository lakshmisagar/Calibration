package com.asu.seatr.utils;

public class GlobalConstants {
	//TODO get data from DB and update below values
	/*public static int total_Students = 100;
	public static int total_Questions = 50;
	public static int total_KCs = 1;
	
	public static int numberOfQAteempetedByEachStudent = 50;
	public static int questionAtThisAttempt = 2;
	public static int kcsInQuestionMatrix = 1;*/
	
	//TESTING 
	public static String OPE_Class_25 = "/hibernateOpeClass25.cfg.xml";
	public static String OPE_global = "/hibernateOpeGlobal.cfg.xml";
	public static int total_Students /*3*/;
	public static int total_Questions = 3;
	public static int total_KCs = 1;// i.e Kc = 0;
	
	public static int indexOf_nth_QuestionAttempted = 2; // last question number.
	public static int kcsInQuestionMatrix = 0; // only 0th Kc present in all QMatrix[Q] = {0}
}
