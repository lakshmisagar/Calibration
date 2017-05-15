package com.asu.seatr.calibration;

import java.util.HashMap;

import com.asu.seatr.utils.GlobalConstants;
/**
 * @author Lakshmisagar Kusnoor created on May 12, 2017
 *
 */
public class EstimateKcMastery {

	// Datastructure to implement BEST
	static HashMap<Integer, Float> best_innerBestMap = new HashMap<Integer, Float>();
	static HashMap<Integer, HashMap<Integer, Float>> best_innerKcBestMap = new HashMap<Integer, HashMap<Integer, Float>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, Float>>> best_outerStudentKcMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Float>>>();

	// Datastructure to implement FORWARD
	HashMap<Integer, Float> forward_innerForwardMap = new HashMap<Integer, Float>();
	HashMap<Integer, HashMap<Integer, Float>> forward_innerKcBestMap = new HashMap<Integer, HashMap<Integer, Float>>();
	HashMap<Integer, HashMap<Integer, HashMap<Integer, Float>>> forward_outerStudentKcMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Float>>>();

	// Datastructure to implement BACKWARD
	HashMap<Integer, Float> backward_innerBackwardMap = new HashMap<Integer, Float>();
	HashMap<Integer, HashMap<Integer, Float>> backward_innerKcBestMap = new HashMap<Integer, HashMap<Integer, Float>>();
	HashMap<Integer, HashMap<Integer, HashMap<Integer, Float>>> backward_outerStudentKcMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Float>>>();

	public static void Estimate_KC_mastery_Best(int studentCount, int kCount) {
		for (int S = 1; S <= studentCount; S++) {
			for (int K = 1; K < kCount; K++) {
				int lastCount = Utils.getLast(S);
				for (int A = 1; A <= lastCount; A++) {

					float bestValue = forward(S, K, A) * backward(S, K, A);

					best_innerBestMap.put(A, bestValue);
					best_innerKcBestMap.put(K, best_innerBestMap);
					best_outerStudentKcMap.put(S, best_innerKcBestMap);
				}
			}
		}
	}

	private void updateForward(int S, int K, int A) {
		// TODO implement forward filling
		float forwardfillingValue = 0;

		forward_innerForwardMap.put(A, forwardfillingValue);
		forward_innerKcBestMap.put(K, forward_innerForwardMap);
		forward_outerStudentKcMap.put(S, forward_innerKcBestMap);
	}

	private void updateBackward(int S, int K, int A) {
		// TODO implement backward filling
		float backwardfillingValue = 0;

		backward_innerBackwardMap.put(A, backwardfillingValue);
		backward_innerKcBestMap.put(K, backward_innerBackwardMap);
		backward_outerStudentKcMap.put(S, backward_innerKcBestMap);
	}

	private float backward(int S, int K, int A) {
		HashMap<Integer, HashMap<Integer, Float>> KcBestmap = backward_outerStudentKcMap.get(S);
		HashMap<Integer, Float> bestmap = KcBestmap.get(K);
		return bestmap.get(A);
	}

	private float forward(int S, int K, int A) {
		HashMap<Integer, HashMap<Integer, Float>> KcBestmap = backward_outerStudentKcMap.get(S);
		HashMap<Integer, Float> bestmap = KcBestmap.get(K);
		return bestmap.get(A);
	}

}
