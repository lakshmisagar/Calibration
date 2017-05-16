package com.asu.seatr.calibration;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on May 14, 2017
 *
 */

public class Learn {

	public static void updateLearn() {

		// looping over each of the KC from 1 to total_KCs
		for (int K = 0; K < GlobalConstants.total_KCs - 1; K++) {
			BigDecimal LearnNumerator = new BigDecimal(0);
			BigDecimal LearnDenominator = new BigDecimal(0);
			BigDecimal SE = new BigDecimal(1.0);

			// looping over the set of all students from 1 to total_students
			for (int S = 0; S < GlobalConstants.total_Students - 1; S++) {
				// looping over all the attempts of student s from 1 to Last[s]
				for (int A = 0; A < Utils.getLast(S) - 1; A++) {
					ArrayList<Integer> KCSetforQ = new ArrayList<Integer>();
					KCSetforQ.addAll(Utils.getQuestionMatrix(Utils.getQuestionAtThisAttempt(S, A)));
					for (int j = 0; j < (KCSetforQ.size() - 1); j++) {

						Integer kcK = new Integer(K);
						if (KCSetforQ.get(j).equals(kcK))
							SE = (SE.multiply((Utils.getBest(S, j, A)).add(
									(((BigDecimal.ONE).subtract(Utils.getBest(S, j, A)).multiply(Utils.mLearn[j]))))));
					}
					LearnNumerator = LearnNumerator.add((Utils.getBest(S, K, A + 1).subtract(Utils.getBest(S, K, A))));
					LearnDenominator = LearnDenominator
							.add(((BigDecimal.ONE.subtract(Utils.getBest(S, K, A))).multiply(SE)));
				}
			}
			Utils.mLearn[K] = (BigDecimal.ONE.max((BigDecimal.ZERO).min(LearnNumerator.divide(LearnDenominator))));
		}
	}
}
