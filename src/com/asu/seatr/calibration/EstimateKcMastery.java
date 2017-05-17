package com.asu.seatr.calibration;

import java.math.BigDecimal;

import com.asu.seatr.utils.Utils;
/**
 * @author Lakshmisagar Kusnoor created on May 12, 2017
 *
 */
public class EstimateKcMastery {

	public static void Estimate_KC_mastery_Best(int studentCount, int kCount) {
		for (int S = 0; S < studentCount; S++) {
			for (int K = 0; K < kCount; K++) {
				int lastCount = Utils.getLast(S);
				for (int A = 0; A < lastCount; A++) {
					System.out.println("S: "+S+" K: "+K+" A:"+A);
					BigDecimal bestValue = Utils.getForward(S, K, A).multiply(Utils.getBackward(S, K, A));
					Utils.updateBest(S, K, A, bestValue);
				}
			}
		}
	}

	
}
