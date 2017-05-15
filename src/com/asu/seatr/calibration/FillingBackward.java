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
public class FillingBackward {

	BigDecimal initial_OK = new BigDecimal(1.0);

	public void fillingBackward() {
		int Ns = GlobalConstants.total_Students;
		int Nk = GlobalConstants.total_KCs;
		for (int S = 1; S <= Ns; S++) {
			for (int K = 1; K <= Nk; K++) {
				Utils.updateBackward(S, K, 1, BigDecimal.ONE);
				for (int A = Utils.getLast(S) - 1; A >= 1; A--) {
					int question = Utils.getQuestionAtThisAttempt(S, A);
					ArrayList<Integer> KCs = Utils.getQuestionMatrix(question);
					BigDecimal SE = BigDecimal.ONE;
					for (int list_K = 0; list_K < KCs.size(); list_K++) {
						SE = SE.multiply(Utils.getBackward(S, KCs.get(list_K), A + 1));
					}
					BigDecimal x;
					for (int list_K = 0; list_K < KCs.size(); list_K++) {
						int localK = KCs.get(list_K);
						BigDecimal var1 = Utils.getLearn(localK).multiply(SE);
						BigDecimal var2 = Utils.getBackward(S, localK, A + 1);
						x = var1.divide(var2);

						BigDecimal newVar1 = var2.subtract(x);
						BigDecimal newVar2 = BigDecimal.ONE.subtract(x);
						BigDecimal backwardfillingValue = newVar1.divideToIntegralValue(newVar2);
						Utils.updateBackward(S, localK, A, backwardfillingValue);
					}

					BigDecimal OK = initial_OK;
					for (int list_K = 0; list_K < KCs.size(); list_K++) {
						OK = OK.multiply(Utils.getBackward(S, KCs.get(list_K), A));
					}
					BigDecimal slip = Utils.getSlip(question);
					BigDecimal guess = Utils.getGuess(question);
					BigDecimal slipPlusGuess = slip.add(guess);
					BigDecimal oneMinusSlipPlusGuess = BigDecimal.ONE.subtract(slipPlusGuess);
					x = OK.multiply(oneMinusSlipPlusGuess);
					BigDecimal y = guess;

					if (Utils.getAnswer(S, A) == 0) {
						y = BigDecimal.ONE.subtract(y);
						x = x.negate();
					}

					for (int innerK = 1; innerK <= Nk; innerK++) {
						if (KCs.contains(innerK)) {
							BigDecimal backwardNumeratorValue = y.multiply(Utils.getBackward(S, innerK, A)).add(x);
							BigDecimal forwardfillingValue = backwardNumeratorValue.divideToIntegralValue(y.add(x));
							Utils.updateBackward(S, innerK, A + 1, forwardfillingValue);
						} else {
							Utils.updateBackward(S, innerK, A + 1, Utils.getForward(S, innerK, A + 1));
						}
					}

				}
			}
		}
	}

}
