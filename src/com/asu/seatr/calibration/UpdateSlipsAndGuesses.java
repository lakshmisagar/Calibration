package com.asu.seatr.calibration;
import java.util.*;

public class UpdateSlipsAndGuesses {
	// looping for each question in the course
		int one = 1;
		Integer oneObj = new Integer(one);
		
		for(int q = 0; q < Nq-1; q++){
			float SlipNumerator = 0;
			float SlipDenominator = 0;
			float GuessNumerator = 0;
			float GuessDenominator = 0;
			// looping the number of students in the course
			for(int s = 0; s < Ns-1; s++){
				//looping over each attempt of the student till the latest attempt
				for(int a = 0; a < (Last[s] - 1); a++){
					Integer qObj = new Integer(q);
					if(qObj.equals(Question[S,A])){
						float OK = 1;
						ArrayList<Integer> KCofQ = new ArrayList<Integer>();
						KCofQ.addAll(Qmatrix[Q]);
						for(int m = 0; m < (KCofQ.size() - 1); m++ ){
							OK = OK * KCofQ.get(m);
						}
						SlipDenominator = SlipDenominator + OK ;
						GuessDenominator = GuessDenominator + (1 - OK) ;
						if(Answer[S,A].equals(oneObj))
							GuessNumerator = GuessNumerator + (1 - OK);
						else
							SlipNumerator = SlipNumerator + OK ;
					}
				}
			}
			Slip[Q] = SlipNumerator / SlipDenominator ;
			Guess[Q] = GuessNumerator / GuessDenominator ;
		}
}
