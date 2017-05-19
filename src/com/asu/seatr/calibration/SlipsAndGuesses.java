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

public class SlipsAndGuesses {
	// looping for each question in the course
	public static void updateSlipnGuesses() {
		System.out.println("SlipsAndGuesses ................................");
		for (int Q = 0; Q < GlobalConstants.total_Questions ; Q++) {
			BigDecimal SlipNumerator = BigDecimal.ZERO;
			BigDecimal SlipDenominator = BigDecimal.ZERO;
			BigDecimal GuessNumerator = BigDecimal.ZERO;
			BigDecimal GuessDenominator = BigDecimal.ZERO;
			
			// looping the number of students in the course
			for (int S = 0; S < GlobalConstants.total_Students ; S++) {
				// looping over each attempt of the student till the latest attempt
				for (int A = 0; A < Utils.getLast(S); A++) {
					//System.out.println("S "+S+" Utils.getQuestion("+S+", "+A+" ):"+Utils.getQuestion(S, A));
					if (Q == Utils.getQuestion(S, A)) {
						BigDecimal OK = new BigDecimal(1.0);
						//System.out.println("Matched "+Q);
						ArrayList<Integer> KCs = Utils.getQuestionMatrix(Q);
						for (int list_K = 0; list_K < KCs.size(); list_K++) {
							OK = OK.multiply(Utils.getBest(S, KCs.get(list_K), A)).setScale(20,RoundingMode.CEILING);
						}
						//System.out.println("OK "+OK);
						SlipDenominator = SlipDenominator.add(OK);
						//System.out.println("SlipDenominator "+SlipDenominator);
						GuessDenominator = GuessDenominator.add((BigDecimal.ONE.subtract(OK)));
						//System.out.println("GuessDenominatorK "+GuessDenominator);
						//System.out.println("Utils.getAnswer("+S+","+ A+")"+Utils.getAnswer(S, A));
						if (Utils.getAnswer(S, A) == 1){
							GuessNumerator = GuessNumerator.add((BigDecimal.ONE.subtract(OK)));
							//System.out.println("GuessNumerator "+GuessNumerator);
						}else{
							SlipNumerator = SlipNumerator.add(OK);
							//System.out.println("SlipNumerator "+SlipNumerator);
						}
						
						Utils.setSlip(Q, SlipNumerator.divide(SlipDenominator,20,RoundingMode.HALF_UP));
						//System.out.println("Utils.mSlip[Q]"+Utils.getSlip(Q));
						Utils.setGuess(Q, GuessNumerator.divide(GuessDenominator,20,RoundingMode.HALF_UP));
						//System.out.println("Utils.mGuess[Q]"+Utils.getGuess(Q));
					}
				}
			}
			
		}
	}
}
