package com.asu.seatr.calibration;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.asu.seatr.utils.Utils;

public class Learn {
	
	public static void updateLearn(){
		
	//looing over each of the KC from 1 to total_KCs
		for(int K = 0; K < Calibration.total_KCs -  1; K++){
			BigDecimal LearnNumerator = new BigDecimal(0);
			BigDecimal LearnDenominator = new BigDecimal(0);
			BigDecimal SE= new BigDecimal(1);
			
			// looping over the set of all students from 1 to total_students
			for(int S = 0; S < Calibration.total_students - 1; S++){
				// looping over all the attempts of student s from 1 to Last[s]
				for(int A = 0;A < Utils.getLast(S) - 1; A++){
					ArrayList<Integer> KCSetforQ = new ArrayList<Integer>();
					KCSetforQ.addAll(Utils.getQuestionMatrix(Utils.getQuestionAtThisAttempt(S, A)));
					for(int j = 0; j < (KCSetforQ.size() - 1); j++){
						
						Integer kcK = new Integer(K);
						if(KCSetforQ.get(j).equals(kcK))
							SE = (SE.multiply((Utils.getBest(S,j,A)).add((((BigDecimal.ONE).subtract(Utils.getBest(S,j,A)).multiply(Calibration.Learn[j]))))));
					}
					LearnNumerator = LearnNumerator.add((Utils.getBest(S,K,A+1).subtract(Utils.getBest(S,K,A))));
					LearnDenominator = LearnDenominator.add(((BigDecimal.ONE.subtract(Utils.getBest(S,K,A))).multiply(SE)));
				}
			}
			Calibration.Learn[K] = (BigDecimal.ONE.max((BigDecimal.ZERO).min(LearnNumerator.divide(LearnDenominator))));
		}
}
}