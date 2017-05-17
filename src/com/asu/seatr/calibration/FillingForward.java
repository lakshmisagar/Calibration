/**
 * 
 */
package com.asu.seatr.calibration;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;

/**
 * @author Lakshmisagar Kusnoor created on May 15, 2017
 *
 */
public class FillingForward {

	static BigDecimal initial_OK = new BigDecimal(1.0);

	public static void fillingForward() {
		int Ns = GlobalConstants.total_Students;
		int Nk = GlobalConstants.total_KCs;
		for (int S = 0; S < Ns; S++) {
			for (int K = 0; K < Nk; K++) {
				BigDecimal value = Utils.getInitialMastery(Utils.getKc(K));
				Utils.updateForward(S, Utils.getKc(K), 1, value);
				for (int A = 0; A < Utils.getLast(S)-1; A++) {
					int question = Utils.getQuestionAtThisAttempt(S, A);
					ArrayList<Integer> KCs = Utils.getQuestionMatrix(question);
					BigDecimal OK = initial_OK;
					for (int list_K = 0; list_K < KCs.size(); list_K++) {
						OK = OK.multiply(Utils.getForward(S, KCs.get(list_K), A));
					}
					BigDecimal slip = Utils.getSlip(question);
					BigDecimal guess = Utils.getGuess(question);
					BigDecimal slipPlusGuess = slip.add(guess);
					BigDecimal oneMinusSlipPlusGuess = BigDecimal.ONE.subtract(slipPlusGuess);
					BigDecimal x = OK.multiply(oneMinusSlipPlusGuess);
					BigDecimal y = guess;

					if (Utils.getAnswer(S, A) == 0) {
						y = BigDecimal.ONE.subtract(y);
						x = x.negate();
					}

					for (int innerK = 0; innerK < Nk; innerK++) {
						if (KCs.contains(innerK)) {
							BigDecimal forwardNumeratorValue = y.multiply(Utils.getForward(S, innerK, A)).add(x);
							BigDecimal forwardfillingValue = forwardNumeratorValue.divide(y.add(x));
							Utils.updateForward(S, innerK, A + 1, forwardfillingValue);
						} else {
							Utils.updateForward(S, innerK, A + 1, Utils.getForward(S, innerK, A));
						}
					}
					BigDecimal SE = initial_OK;
					for (int list_K = 0; list_K < KCs.size(); list_K++) {
						BigDecimal forward = Utils.getForward(S, KCs.get(list_K), A);
						BigDecimal var1 = BigDecimal.ONE.subtract(forward);
						BigDecimal var2 = var1.multiply(Utils.getLearn(KCs.get(list_K)));
						BigDecimal var3 = forward.add(var2);
						SE = SE.multiply(var3);
					}
					for (int list_K = 0; list_K < KCs.size(); list_K++) {
						BigDecimal forward = Utils.getForward(S, KCs.get(list_K), A);
						BigDecimal Z = BigDecimal.ONE.subtract(forward.multiply(Utils.getLearn(KCs.get(list_K))));
						BigDecimal nume = SE.multiply(Z);
						BigDecimal denom = forward.add(Z);
						BigDecimal var2 = nume.divide(denom);
						BigDecimal forwardfillingValue = forward.add(var2);
						Utils.updateForward(S, KCs.get(list_K), A + 1, forwardfillingValue);
					}
				}
			}
		}
	}

}
