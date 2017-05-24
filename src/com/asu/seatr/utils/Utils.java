/**
 * 
 */
package com.asu.seatr.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Lakshmisagar Kusnoor created on May 15, 2017
 *
 */
public class Utils {

	private static int[] mKC = new int[GlobalConstants.total_KCs];
	private static BigDecimal[] mInitialMastery = new BigDecimal[GlobalConstants.total_KCs];
	private static BigDecimal[] mLearn = new BigDecimal[GlobalConstants.total_KCs];
	private static BigDecimal[] mSlip = new BigDecimal[GlobalConstants.total_Questions];
	private static BigDecimal[] mGuess = new BigDecimal[GlobalConstants.total_Questions];
	private static int[] studentsList = new int[GlobalConstants.total_Students];

	// Datastructure to implement Question
	static HashMap<Integer, HashMap<Integer, Integer>> question_SA_Map = new HashMap<Integer, HashMap<Integer, Integer>>();

	// Datastructure to implement Answer
	static HashMap<Integer, HashMap<Integer, Integer>> answer_SA_Map = new HashMap<Integer, HashMap<Integer, Integer>>();
	
	//TODO change the logic from 3 maps to 1 map here in utils like Questionand Answer
	// Datastructure to implement BEST
	static HashMap<Integer, BigDecimal> best_innerBestMap = new HashMap<Integer, BigDecimal>();
	static HashMap<Integer, HashMap<Integer, BigDecimal>> best_innerKcBestMap = new HashMap<Integer, HashMap<Integer, BigDecimal>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, BigDecimal>>> best_outerStudentKcMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, BigDecimal>>>();

	// Datastructure to implement FORWARD
	static HashMap<Integer, BigDecimal> forward_innerForwardMap = new HashMap<Integer, BigDecimal>();
	static HashMap<Integer, HashMap<Integer, BigDecimal>> forward_innerKcBestMap = new HashMap<Integer, HashMap<Integer, BigDecimal>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, BigDecimal>>> forward_outerStudentKcMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, BigDecimal>>>();

	// Datastructure to implement BACKWARD
	static HashMap<Integer, BigDecimal> backward_innerBackwardMap = new HashMap<Integer, BigDecimal>();
	static HashMap<Integer, HashMap<Integer, BigDecimal>> backward_innerKcBestMap = new HashMap<Integer, HashMap<Integer, BigDecimal>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, BigDecimal>>> backward_outerStudentKcMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, BigDecimal>>>();
	
	
	/*
	 * Students List
	 */
	
	public static int[] getStudentsList() {
		return studentsList;
	}
	public static void setStudentsList(int[] studentsList) {
		Utils.studentsList = studentsList;
	}
	/*
	 * Student
	 */
	
	public static int getStudent(int index) {
		return studentsList[index];
	}
	public static void setStudent(int index,int studentid) {
		Utils.studentsList[index] = studentid;
	}
	/*
	 * Last
	 */
	public static int getLast(int mStudentId) {
		// TODO find the nQuestionsAttempted
		int nQuestionsAttempted = GlobalConstants.indexOf_nth_QuestionAttempted;
		return nQuestionsAttempted;
	}
	/*
	 * QMatrix
	 */
	public static ArrayList<Integer> getQuestionMatrix(int mQuestion) {
		// TODO find the list values
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(GlobalConstants.kcsInQuestionMatrix);
		//System.out.println("list.size() :"+list.size());
		return list;
	}
	/*
	 * Forward
	 */
	public static void updateForward(int S, int K, int A, BigDecimal forwardfillingValue) {
		// TODO implement forward filling
		//System.out.println("set Forward - S:"+S+" K:"+K+" A:"+A+" ="+forwardfillingValue);
		forward_innerForwardMap.put(A, forwardfillingValue);
		forward_innerKcBestMap.put(K, forward_innerForwardMap);
		forward_outerStudentKcMap.put(S, forward_innerKcBestMap);
	}
	
	public static BigDecimal getForward(int S, int K, int A) {
		HashMap<Integer, HashMap<Integer, BigDecimal>> Kcmap = forward_outerStudentKcMap.get(S);
		HashMap<Integer, BigDecimal> forwardmap = Kcmap.get(K);
		return forwardmap.get(A);
	}
	/*
	 * Backward
	 */
	public static void updateBackward(int S, int K, int A, BigDecimal backwardfillingValue) {
		//System.out.println("updateBackward S:"+S+" K:"+K+" A:"+A+" - "+backwardfillingValue);
		// TODO implement backward filling
		backward_innerBackwardMap.put(A, backwardfillingValue);
		backward_innerKcBestMap.put(K, backward_innerBackwardMap);
		backward_outerStudentKcMap.put(S, backward_innerKcBestMap);
	}

	public static BigDecimal getBackward(int S, int K, int A) {
		HashMap<Integer, HashMap<Integer, BigDecimal>> Kcmap = backward_outerStudentKcMap.get(S);
		HashMap<Integer, BigDecimal> backwardmap = Kcmap.get(K);
		//System.out.println("getBackward S:"+S+" K:"+K+" A:"+A+" - "+backwardmap.get(A));
		return backwardmap.get(A);
	}
	/*
	 * Best
	 */
	public static void updateBest(int S, int K, int A, BigDecimal bestValue) {
		// TODO implement forward filling
		best_innerBestMap.put(A, bestValue);
		best_innerKcBestMap.put(K, best_innerBestMap);
		best_outerStudentKcMap.put(S, best_innerKcBestMap);
	}

	public static BigDecimal getBest(int S, int K, int A) {
		HashMap<Integer, HashMap<Integer, BigDecimal>> Kcmap = best_outerStudentKcMap.get(S);
		HashMap<Integer, BigDecimal> bestmap = Kcmap.get(K);
		return bestmap.get(A);
	}
	/*
	 * InitialMastery
	 */
	public static void setInitialMastery(int K, BigDecimal value) {
		mInitialMastery[K] = value;
	}

	public static BigDecimal getInitialMastery(int K) {
		return mInitialMastery[K];
	}
	/*
	 * Slip
	 */
	public static void setSlip(int question, BigDecimal value) {
		mSlip[question] = value;
	}

	public static BigDecimal getSlip(int question) {
		return mSlip[question];
	}

	/*
	 * Guess
	 */
	public static void setGuess(int question, BigDecimal value) {
		mGuess[question] = value;
	}

	public static BigDecimal getGuess(int question) {
		return mGuess[question];
	}
	/*
	 * Learn
	 */
	public static void setLearn(int K, BigDecimal value) {
		//System.out.println("setLearn K: "+K+" "+value);
		mLearn[K] = value;
	}

	public static BigDecimal getLearn(int K) {
		//System.out.println("getLearn K: "+K+" "+mLearn[K]);
		return mLearn[K];
	}
	
	
	/*
	 * KCs List
	 */
	
	public static int[] getKCsList() {
		return mKC;
	}
	public static void setKCsList(int[] kcs) {
		mKC = kcs;
	}
	/*
	 * Kc
	 */
	public static void setKc(int K, int value) {
		//System.out.println("setKc K: "+K+" "+value);
		mKC[K] = value;
	}
	
	public static int getKc(int k) {
		//System.out.println("getKc K: "+k+" "+ mKC[k]);
		return mKC[k];
	}

	/*
	 * Answer
	 */
	public static void setAnswer(int s, HashMap<Integer, Integer> answer_AC_Map) {
		answer_SA_Map.put(s, answer_AC_Map);
	}

	public static int getAnswer(int S, int A) {
		HashMap<Integer, Integer> innerAC_map = answer_SA_Map.get(S);
		return innerAC_map.get(A);
	}
	
	/*
	 * Question
	 */
	public static void setQuestion(int s, HashMap<Integer, Integer> question_AQ_Map) {
		question_SA_Map.put(s, question_AQ_Map);
	}

	public static int getQuestion(int S, int A) {
		HashMap<Integer, Integer> innerAQ_map = question_SA_Map.get(S);
		return innerAQ_map.get(A);
	}

}
