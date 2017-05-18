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

	static BigDecimal initial_OK = new BigDecimal(1.0);

	public static void fillingBackward() {
		int Ns = GlobalConstants.total_Students;
		int Nk = GlobalConstants.total_KCs;
		for (int S = 0; S < Ns; S++) {
			for (int K = 0; K < Nk; K++) {
				Utils.updateBackward(S, Utils.getKc(K), Utils.getLast(S), BigDecimal.ONE);
				for (int A = Utils.getLast(S) - 1; A >= 0; A--) {
					int question = Utils.getQuestion(S, A);
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
						BigDecimal backwardfillingValue = newVar1.divide(newVar2);
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

					for (int innerK = 0; innerK < Nk; innerK++) {
						int innerKc = Utils.getKc(innerK);
						if (KCs.contains(innerKc)) {
							BigDecimal backwardNumeratorValue = y.multiply(Utils.getBackward(S, innerKc, A)).add(x);
							BigDecimal forwardfillingValue = backwardNumeratorValue.divide(y.add(x));
							Utils.updateBackward(S, innerKc, A, forwardfillingValue);
						} else {
							Utils.updateBackward(S, innerKc, A, Utils.getBackward(S, innerKc, A + 1));
						}
					}

				}
			}
		}
	}

}
