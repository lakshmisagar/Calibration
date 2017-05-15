/**
 * 
 */
package com.asu.seatr.calibration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Lakshmisagar Kusnoor created on May 15, 2017
 *
 */
public class Utils {

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
		int nQuestionsAttempted = 0;
		return nQuestionsAttempted;
	}

	public static int getQuestionAtThisAttempt(int mStudentId, int mAttempt) {
		// TODO find the attemptedQuestion
		int attemptedQuestion = 1;
		return attemptedQuestion;
	}

	public static ArrayList<Integer> getQuestionMatrix(int mQuestion) {
		// TODO find the list values
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		return list;
	}

	public static void updateForward(int S, int K, int A, BigDecimal forwardfillingValue) {
		// TODO implement forward filling

		forward_innerForwardMap.put(A, forwardfillingValue);
		forward_innerKcBestMap.put(K, forward_innerForwardMap);
		forward_outerStudentKcMap.put(S, forward_innerKcBestMap);
	}

	public void updateBackward(int S, int K, int A, BigDecimal backwardfillingValue) {
		// TODO implement backward filling
		backward_innerBackwardMap.put(A, backwardfillingValue);
		backward_innerKcBestMap.put(K, backward_innerBackwardMap);
		backward_outerStudentKcMap.put(S, backward_innerKcBestMap);
	}
	

	public BigDecimal getBackward(int S, int K, int A) {
		HashMap<Integer, HashMap<Integer, BigDecimal>> Kcmap = backward_outerStudentKcMap.get(S);
		HashMap<Integer, BigDecimal> backwardmap = Kcmap.get(K);
		return backwardmap.get(A);
	}

	public BigDecimal getForward(int S, int K, int A) {
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
	
	public BigDecimal getBest(int S, int K, int A) {
		HashMap<Integer, HashMap<Integer, BigDecimal>> Kcmap = best_outerStudentKcMap.get(S);
		HashMap<Integer, BigDecimal> bestmap = Kcmap.get(K);
		return bestmap.get(A);
	}
}
