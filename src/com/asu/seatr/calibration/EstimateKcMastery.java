package com.asu.seatr.calibration;

import java.math.BigDecimal;

import com.asu.seatr.utils.Utils;
/**
 * @author Lakshmisagar Kusnoor created on May 12, 2017
 *
 */
public class EstimateKcMastery {

	public static void Estimate_KC_mastery_Best(int studentCount, int kCount) {
		for (int S = 1; S <= studentCount; S++) {
			for (int K = 1; K < kCount; K++) {
				int lastCount = Utils.getLast(S);
				for (int A = 1; A <= lastCount; A++) {
					BigDecimal bestValue = Utils.getForward(S, K, A).multiply(Utils.getBackward(S, K, A));
					Utils.updateBest(S, K, A, bestValue);
				}
			}
		}
	}

	
}
