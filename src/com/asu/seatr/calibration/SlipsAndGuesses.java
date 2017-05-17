package com.asu.seatr.calibration;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;

public class SlipsAndGuesses {
	// looping for each question in the course
	public static void updateSlipnGuesses() {

		for (int Q = 0; Q < GlobalConstants.total_Questions ; Q++) {
			BigDecimal SlipNumerator = new BigDecimal(0);
			BigDecimal SlipDenominator = new BigDecimal(0);
			BigDecimal GuessNumerator = new BigDecimal(0);
			BigDecimal GuessDenominator = new BigDecimal(0);
			BigDecimal OK = new BigDecimal(1.0);
			// looping the number of students in the course
			for (int S = 0; S < GlobalConstants.total_Students ; S++) {
				// looping over each attempt of the student till the latest
				// attempt
				for (int A = 0; A < Utils.getLast(S); A++) {
					Integer qObj = new Integer(Q);
					if (qObj.equals(Utils.getQuestion(S, A))) {

						ArrayList<Integer> KCofQ = new ArrayList<Integer>();
						KCofQ.addAll(Utils.getQuestionMatrix(Q));
						for (int m = 0; m < KCofQ.size(); m++) {
							OK = OK.multiply(new BigDecimal(KCofQ.get(m)));
						}
						SlipDenominator = SlipDenominator.add(OK);
						GuessDenominator = GuessDenominator.add((BigDecimal.ONE.subtract(OK)));
						if (Utils.getAnswer(S, A) == 1)
							GuessNumerator = GuessNumerator.add((BigDecimal.ONE.subtract(OK)));
						else
							SlipNumerator = SlipNumerator.add(OK);
					}
				}
			}
			Utils.mSlip[Q] = SlipNumerator.divide(SlipDenominator);
			Utils.mGuess[Q] = GuessNumerator.divide(GuessDenominator);
		}
	}
}
