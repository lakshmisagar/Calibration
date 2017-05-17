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

	public static BigDecimal[] mInitialMastery = new BigDecimal[GlobalConstants.total_KCs];
	public static BigDecimal[] mLearn = new BigDecimal[GlobalConstants.total_KCs];
	public static BigDecimal[] mSlip = new BigDecimal[GlobalConstants.total_Questions];
	public static BigDecimal[] mGuess = new BigDecimal[GlobalConstants.total_Questions];
	
	
	// Datastructure to implement Question
	static HashMap<Integer, Integer> question_AQ_Map = new HashMap<Integer, Integer>();
	static HashMap<Integer, HashMap<Integer, Integer>> question_SA_Map = new HashMap<Integer, HashMap<Integer, Integer>>();

	// Datastructure to implement Answer
	static HashMap<Integer, Integer> answer_AC_Map = new HashMap<Integer, Integer>();
	static HashMap<Integer, HashMap<Integer, Integer>> answer_SA_Map = new HashMap<Integer, HashMap<Integer, Integer>>();
	
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

	public static int getLast(int mStudentId) {
		// TODO find the nQuestionsAttempted
		int nQuestionsAttempted = GlobalConstants.numberOfQAteempetedByEachStudent;
		return nQuestionsAttempted;
	}

	public static int getQuestionAtThisAttempt(int mStudentId, int mAttempt) {
		// TODO find the attemptedQuestion
		int attemptedQuestion = GlobalConstants.questionAtThisAttempt;
		return attemptedQuestion;
	}

	public static ArrayList<Integer> getQuestionMatrix(int mQuestion) {
		// TODO find the list values
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(GlobalConstants.kcsInQuestionMatrix);
		return list;
	}

	public static void updateForward(int S, int K, int A, BigDecimal forwardfillingValue) {
		// TODO implement forward filling

		forward_innerForwardMap.put(A, forwardfillingValue);
		forward_innerKcBestMap.put(K, forward_innerForwardMap);
		forward_outerStudentKcMap.put(S, forward_innerKcBestMap);
	}

	public static void updateBackward(int S, int K, int A, BigDecimal backwardfillingValue) {
		// TODO implement backward filling
		backward_innerBackwardMap.put(A, backwardfillingValue);
		backward_innerKcBestMap.put(K, backward_innerBackwardMap);
		backward_outerStudentKcMap.put(S, backward_innerKcBestMap);
	}

	public static BigDecimal getBackward(int S, int K, int A) {
		HashMap<Integer, HashMap<Integer, BigDecimal>> Kcmap = backward_outerStudentKcMap.get(S);
		HashMap<Integer, BigDecimal> backwardmap = Kcmap.get(K);
		return backwardmap.get(A);
	}

	public static BigDecimal getForward(int S, int K, int A) {
		HashMap<Integer, HashMap<Integer, BigDecimal>> Kcmap = backward_outerStudentKcMap.get(S);
		HashMap<Integer, BigDecimal> forwardmap = Kcmap.get(K);
		return forwardmap.get(A);
	}

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

	public void setInitialMastery(int K, BigDecimal value) {
		mInitialMastery[K] = value;
	}

	public static BigDecimal getInitialMastery(int K) {
		return mInitialMastery[K];
	}

	public static void setSlip(int question, BigDecimal value) {
		mSlip[question] = value;
	}

	public static BigDecimal getSlip(int question) {
		return mSlip[question];
	}

	public void setGuess(int question, BigDecimal value) {
		mInitialMastery[question] = value;
	}

	public static BigDecimal getGuess(int question) {
		return mInitialMastery[question];
	}

	public static void setAnswer(int S, int A, int value) {
		answer_AC_Map.put(A, value);
		answer_SA_Map.put(S, answer_AC_Map);
	}

	public static int getAnswer(int S, int A) {
		HashMap<Integer, Integer> innerAC_map = answer_SA_Map.get(S);
		return innerAC_map.get(A);
	}
	
	public static void setQuestion(int S, int A, int value) {
		question_AQ_Map.put(A, value);
		question_SA_Map.put(S, question_AQ_Map);
	}

	public static int getQuestion(int S, int A) {
		HashMap<Integer, Integer> innerAQ_map = question_SA_Map.get(S);
		return innerAQ_map.get(A);
	}


	public void setLearn(int K, BigDecimal value) {
		mLearn[K] = value;
	}

	public static BigDecimal getLearn(int K) {
		return mLearn[K];
	}

}
