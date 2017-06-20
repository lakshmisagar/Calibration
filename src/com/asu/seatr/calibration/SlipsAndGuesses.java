package com.asu.seatr.calibration;

import java.util.ArrayList;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on May 14, 2017
 *
 */

public class SlipsAndGuesses {
	// looping for each question in the course
	public static void updateSlipnGuesses() {
		System.out.println("SlipsAndGuesses ................................");
		for (int Qi = 0; Qi < GlobalConstants.total_Questions ; Qi++) {
			int Q = Utils.getQuestion(Qi);
			Double SlipNumerator = (double)0;
			Double SlipDenominator = (double)0;
			Double GuessNumerator = (double)0;
			Double GuessDenominator = (double)0;
			
			// looping the number of students in the course
			for (int St = 0; St < GlobalConstants.total_Students ; St++) {
				int S = Utils.getStudent(St);
				// looping over each attempt of the student till the latest attempt
				for (int A = 1; A <= Utils.getLast(S); A++) {
					//System.out.println("S "+S+" Utils.getQuestion("+S+", "+A+" ):"+Utils.getQuestion(S, A));
					if (Q == Utils.getQuestion(S, A)) {
						Double OK = new Double(1.0);
						//System.out.println("O   K   "+OK);
						//System.out.println("Matched "+Q);
						ArrayList<Integer> KCs = Utils.getQuestionMatrix(Q);
						for (int list_K = 0; list_K < KCs.size(); list_K++) {
							//System.out.println("getBest  "+Utils.getBest(S, KCs.get(list_K), A));
							OK = Operations.multiplyDouble(OK,Utils.getBest(S, KCs.get(list_K), A));
							//System.out.println("OK "+OK);
						}
						
						//System.out.println("final OK "+OK);
						SlipDenominator = Operations.addDouble(SlipDenominator,OK);
						GuessDenominator = Operations.addDouble(GuessDenominator,Operations.substractDouble((double)1,OK));
						
						//System.out.println("Utils.getAnswer("+S+","+ A+")"+Utils.getAnswer(S, A));
						
						/*if (Utils.getAnswer(S, A) == 1){*/
						//SIMULATE
						//System.out.println("Utils.simulategetSetAnswer("+S+","+ Utils.getQuestion(S, A)+")"+Utils.simulategetSetAnswer(S, Utils.getQuestion(S, A)));
						if (Utils.simulategetSetAnswer(S, Utils.getQuestion(S, A)) == 1){
							GuessNumerator =  Operations.addDouble(GuessNumerator,Operations.substractDouble((double)1,OK));
						}else{
							SlipNumerator =  Operations.addDouble(SlipNumerator,OK);
						}
						
						//System.out.println("SlipNumerator "+SlipNumerator);
						//System.out.println("SlipDenominator "+SlipDenominator);
						Utils.setSlipMap(Q,  Operations.divideDouble(SlipNumerator,SlipDenominator));
						//System.out.println("Utils.mSlip[Q]"+Utils.getSlipMap(Q));
						//System.out.println("GuessNumerator "+GuessNumerator);
						//System.out.println("GuessDenominator "+GuessDenominator);
						Utils.setGuessMap(Q, Operations.divideDouble(GuessNumerator,GuessDenominator));
						//System.out.println("Utils.mGuess[Q]"+Utils.getGuessMap(Q));
						//System.out.println();
					}
				}
			}
		}
	}
}
