package com.asu.seatr.calibration;

import java.math.BigDecimal;
import java.util.HashMap;

import com.asu.seatr.utils.GlobalConstants;
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
					BigDecimal bestValue = forward(S, K, A) * backward(S, K, A);

				}
			}
		}
	}

	

	


}
