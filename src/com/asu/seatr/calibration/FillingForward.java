/**
 * 
 */
package com.asu.seatr.calibration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.asu.seatr.utils.GlobalConstants;

/**
 * @author Lakshmisagar Kusnoor created on May 15, 2017
 *
 */
public class FillingForward {

	public void fillingForward(){
		int Ns  = GlobalConstants.total_Students;
		int Nk = GlobalConstants.total_KCs;
		for(int S=1; S<=Ns; S++){
			for(int K=1;K<=Nk; K++){
				 BigDecimal value= getInitalMastery(K); 
				 Utils.updateForward(S,K,1,value);
				for(int A=1; A<=Utils.getLast(S);A++){
					int question = Utils.getQuestionAtThisAttempt(S, A);
					ArrayList<Integer> KCs = Utils.getQuestionMatrix(question);
				}
			}
		}
	}
}
