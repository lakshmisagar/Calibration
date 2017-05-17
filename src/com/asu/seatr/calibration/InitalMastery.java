package com.asu.seatr.calibration;

import java.math.BigDecimal;
import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on May 12, 2017
 *
 */

public class InitalMastery {

	public static void updateIntialMastery() {

		for (int K = 0; K < GlobalConstants.total_KCs ; K++) {
			BigDecimal Sum = new BigDecimal(0);
			int Count = 0;
			for (int S = 0; S < GlobalConstants.total_Students ; S++) {

				Sum = Sum.add(Utils.getBest(S, K, 1));
				Count = Count + 1;
			}
			BigDecimal bigDecimalCount = new BigDecimal(Count);
			Utils.mInitialMastery[K] = Sum.divide(bigDecimalCount);
		}
	}
}
