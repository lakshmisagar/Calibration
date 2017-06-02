/**
 * 
 */
package com.asu.seatr.calibration;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
		System.out.println("FillingForward ................................");
		int Ns = GlobalConstants.total_Students;
		int Nk = GlobalConstants.total_KCs;
		System.out.println("Ns :"+Ns+"   Nk  :"+Nk);
		for (int St = 0; St < Ns; St++) {
			int S = Utils.getStudent(St);
			System.out.println("Student ...."+S);
			for (int K = 0; K < Nk; K++) {
				BigDecimal value = Utils.getInitialMasteryMap(Utils.getKc(K));
				//System.out.println("updateForward("+S+", "+Utils.getKc(K)+","+ 0+") :"+ value);
				Utils.updateForward(S, Utils.getKc(K), 1, value);
			}
			System.out.println("Attempts......"+ Utils.getLast(S));
			for (int A = 1; A < Utils.getLast(S) - 1; A++) {
				System.out.println("Attempt....."+A);
				int question = Utils.getQuestion(S, A);
				ArrayList<Integer> KCs = Utils.getQuestionMatrix(question);
				BigDecimal OK = initial_OK;
				for (int list_K = 0; list_K < KCs.size(); list_K++) {
					OK = OK.multiply(Utils.getForward(S, KCs.get(list_K), A));
				}
				//System.out.println();
				BigDecimal slip = Utils.getSlipMap(question);
				BigDecimal guess = Utils.getGuessMap(question);
				BigDecimal slipPlusGuess = slip.add(guess);
				BigDecimal oneMinusSlipPlusGuess = BigDecimal.ONE.subtract(slipPlusGuess);
				BigDecimal x = OK.multiply(oneMinusSlipPlusGuess);
				BigDecimal y = guess;

				if (Utils.getAnswer(S, A) == 0) {
					y = BigDecimal.ONE.subtract(y);
					x = x.negate();
				}
				//System.out.println("Kcs at Attempt "+A+"  :"+ Utils.getLast(S));
				for (int innerK = 0; innerK < Nk; innerK++) {
					System.out.println("innerK..."+innerK);
					if (KCs.contains(Utils.getKc(innerK))) {
						BigDecimal forwardNumeratorValue = y.multiply(Utils.getForward(S, Utils.getKc(innerK), A))
								.add(x);
						BigDecimal forwardfillingValue = forwardNumeratorValue.divide(y.add(x), 20,RoundingMode.HALF_UP);
						//System.out.println("updateForward("+S+", "+Utils.getKc(innerK)+","+ (A+1)+") :"+ forwardfillingValue);
						Utils.updateForward(S, Utils.getKc(innerK), A + 1, forwardfillingValue);
					} else {
						//System.out.println("updateForward("+S+", "+Utils.getKc(innerK)+","+ (A+1)+") :  Utils.getForward(S, Utils.getKc(innerK), A)"+ Utils.getForward(S, Utils.getKc(innerK), A));
						Utils.updateForward(S, Utils.getKc(innerK), A + 1, Utils.getForward(S, Utils.getKc(innerK), A));
					}
				}
				BigDecimal SE = initial_OK;
				for (int list_K = 0; list_K < KCs.size(); list_K++) {
					BigDecimal forward = Utils.getForward(S, KCs.get(list_K), A);
					BigDecimal var1 = BigDecimal.ONE.subtract(forward);
					BigDecimal var2 = var1.multiply(Utils.getLearnMap(KCs.get(list_K)));
					BigDecimal var3 = forward.add(var2);
					SE = SE.multiply(var3);
				}
				for (int list_K = 0; list_K < KCs.size(); list_K++) {
					BigDecimal forward = Utils.getForward(S, KCs.get(list_K), A);
					BigDecimal Z = BigDecimal.ONE.subtract(forward.multiply(Utils.getLearnMap(KCs.get(list_K))));
					BigDecimal nume = SE.multiply(Z);
					BigDecimal denom = forward.add(Z);
					BigDecimal var2 = nume.divide(denom, 20, RoundingMode.HALF_UP);
					BigDecimal forwardfillingValue = forward.add(var2);
					//System.out.println("updateForward("+S+", "+list_K+","+ (A+1)+") :"+ forwardfillingValue);
					Utils.updateForward(S, KCs.get(list_K), A + 1, forwardfillingValue);
				}
			}
		}
	}
}
