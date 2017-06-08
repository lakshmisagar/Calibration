package com.asu.seatr.calibration;

import java.util.ArrayList;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on May 14, 2017
 *
 */

public class Learn {

	public static void updateLearn() {
		System.out.println("Learn ................................");
		for (int K = 0; K < GlobalConstants.total_KCs; K++) {
			int Kc = Utils.getKc(K);
			Double LearnNumerator = new Double(0);
			Double LearnDenominator = new Double(0);

			for (int St = 0; St < GlobalConstants.total_Students; St++) {
				int S = Utils.getStudent(St);
				for (int A = 1; A < Utils.getLast(S); A++) {
					ArrayList<Integer> KCs = Utils.getQuestionMatrix(Utils.getQuestion(S, A));
					// for (int list_K = 0; list_K < KCs.size(); list_K++) {
					// if (KCs.get(list_K) == Kc) {
					if (KCs.contains(Kc)) {
						Double SE = new Double(1.0);
						for (int list_K = 0; list_K < KCs.size(); list_K++) {
							int j = KCs.get(list_K);
							if (j != Kc) {
								Double var1 = Operations.substractDouble((double) 1, Utils.getBest(S, j, A));
								Double var2 = Operations.multiplyDouble(var1, Utils.getLearnMap(j));
								Double var3 = Operations.addDouble(Utils.getBest(S, j, A), var2);
								SE = Operations.multiplyDouble(SE, var3);
							}
						}
						// System.out.println((A+1)+" "+Utils.getBest(S, Kc,
						// A +
						// 1)+" , "+A+" "+Utils.getBest(S, Kc, A));
						LearnNumerator = Operations.addDouble(LearnNumerator,Operations.substractDouble(Utils.getBest(S, Kc, A + 1), Utils.getBest(S, Kc, A)));
						LearnDenominator = Operations.addDouble(LearnDenominator, Operations.multiplyDouble(Operations.substractDouble((double) 1, Utils.getBest(S, Kc, A)), SE));
						// System.out.println(St+" "+A+" "+list_K);
						//System.out.println("LearnNumerator	 :" + LearnNumerator + " = " + LearnNumerator + "+"
							//	+ Operations.substractDouble(Utils.getBest(S, Kc, A + 1), Utils.getBest(S, Kc, A)));
						//System.out.println("LearnDenominator :" + LearnDenominator + " = " + LearnDenominator + "+"
							//	+ Operations.multiplyDouble(
								//		Operations.substractDouble((double) 1, Utils.getBest(S, Kc, A)), SE));
					}
				}
			}
			Double LnByLd = Operations.divideDouble(LearnNumerator, LearnDenominator);
			// System.out.println("LnByLd :" + LnByLd);
			Double max = Math.max(Double.valueOf(0.05), LnByLd);
			// System.out.println(
			// "setLearnMap :" + Math.min(Double.valueOf(0.5), max) + " " +
			// Double.valueOf(0.5) + " " + max);
			Utils.setLearnMap(Kc, Math.min(Double.valueOf(0.5), max));
		}

	}
}
