package com.asu.seatr.calibration;
import java.util.*;

public class UpdateLearn {
	//looing over each of the KC from 1 to Nk
		for(int k = 0; k < Nk -  1; k++){
			float LearnNumerator = 0;
			float LearnDenominator = 0;
			// looping over the set of all students from 1 to Ns
			for(int s = 0; s < Ns - 1; s++){
				// looping over all the attempts of student s from 1 to Last[s]
				for(int a = 0;a < Last[s] - 1; a++){
					ArrayList<Integer> KCSetforQ = new ArrayList<Integer>();
					KCSetforQ.addAll(Qmatrix[Question[S,A]]);
					for(int j = 0; j < (KCSetforQ.size() - 1); j++){
						float SE = 1;
						Integer kcK = new Integer(k);
						if(KCSetforQ.get(j).equals(kcK))
							SE = SE * (Best[s,j,a] + (1 - Best[s,j,a]) * Learn[j]);
					}
					LearnNumerator = LearnNumerator + (Best[s,k,a+1] - Best[s,k,a]);
					LearnDenominator = LearnDenominator + ((1 - Best[s,k,a]) * SE);
				}
			}
			Learn[k] = Math.max(1, Math.min(0, LearnNumerator / LearnDenominator));
		}
}
