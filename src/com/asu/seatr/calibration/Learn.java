package com.asu.seatr.calibration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on May 14, 2017
 *
 */

public class Learn {

	public static void updateLearn() {
		for (int K = 0; K < GlobalConstants.total_KCs ; K++) {
			int Kc = Utils.getKc(K);
			BigDecimal LearnNumerator = new BigDecimal(0);
			BigDecimal LearnDenominator = new BigDecimal(0);
			BigDecimal SE = new BigDecimal(1.0);
			for (int S = 0; S < GlobalConstants.total_Students ; S++) {
				for (int A = 0; A < Utils.getLast(S)-1; A++) {
					ArrayList<Integer> KCs = Utils.getQuestionMatrix(Utils.getQuestion(S, A));
					for (int list_K = 0; list_K < KCs.size(); list_K++) {
						if(KCs.get(list_K) == Kc){
							int j = KCs.get(list_K);
							BigDecimal var1 = BigDecimal.ONE.subtract(Utils.getBest(S, j, A));
							BigDecimal var2 = var1.multiply(Utils.mLearn[j]);
							BigDecimal var3 = Utils.getBest(S, j, A).add(var2);
							SE = SE.multiply(var3);
						}
					LearnNumerator = LearnNumerator.add((Utils.getBest(S, Kc, A + 1).subtract(Utils.getBest(S, Kc, A))));
					LearnDenominator = LearnDenominator.add(((BigDecimal.ONE.subtract(Utils.getBest(S, Kc, A))).multiply(SE)));
					}
				}
			}
			BigDecimal LnByLd =  LearnNumerator.divide(LearnDenominator ,20, RoundingMode.HALF_UP);
			BigDecimal min = BigDecimal.ZERO.min(LnByLd);
			Utils.mLearn[Kc] = BigDecimal.ONE.max(min);
		}
	}
}
