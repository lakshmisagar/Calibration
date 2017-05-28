package com.asu.seatr.calibration;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on May 12, 2017
 *
 */

public class InitalMastery {

	public static void updateIntialMastery() {
		System.out.println("updateIntialMastery ................................");
		for (int K = 0; K < GlobalConstants.total_KCs; K++) {
			BigDecimal Sum = new BigDecimal(0);
			int Count = 0;
			for (int S = 0; S < GlobalConstants.total_Students; S++) {
				Sum = Sum.add(Utils.getBest(S, Utils.getKc(K), 1));
				Count = Count + 1;
			}
			BigDecimal bigDecimalCount = new BigDecimal(Count);
			Utils.setInitialMasteryMap(Utils.getKc(K), Sum.divide(bigDecimalCount,20,RoundingMode.HALF_UP));
		}
	}
}
