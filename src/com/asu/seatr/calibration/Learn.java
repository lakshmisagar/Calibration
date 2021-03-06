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
		//System.out.println("Learn ................................");
		for (int K = 0; K < GlobalConstants.total_KCs; K++) {
			int Kc = Utils.getKc(K);
			Double LearnNumerator = new Double(0);
			Double LearnDenominator = new Double(0);

			for (int St = 0; St < GlobalConstants.total_Students; St++) {
				int S = Utils.getStudent(St);
				for (int A = 1; A < Utils.getLast(S) - 1; A++) {
					ArrayList<Integer> KCs = Utils.getQuestionMatrix(Utils.getQuestion(S, A));
					// for (int list_K = 0; list_K < KCs.size(); list_K++) {
					// if (KCs.get(list_K) == Kc) {
					//System.out.println(" Kc:"+Kc+"  and Qmatrix:"+KCs);
					if (KCs.contains(Kc)) {
						
						//NEW CHNAGES
						System.out.println(" LearnNumerator: = "+LearnNumerator+" + ("+Utils.getBest(S, Kc, A + 1)+" * ( 1-"+Utils.getBest(S, Kc, A)+"))");
						LearnNumerator = Operations.addDouble(LearnNumerator,Operations.multiplyDouble(Utils.getBest(S, Kc, A + 1), Operations.substractDouble(1.0,Utils.getBest(S, Kc, A))));
                        LearnDenominator = Operations.addDouble(LearnDenominator, Operations.substractDouble((double) 1, Utils.getBest(S, Kc, A)));
						/*Double SE = new Double(1.0);
						for (int list_K = 0; list_K < KCs.size(); list_K++) {
							int j = KCs.get(list_K);
							if (j != Kc) {
								Double var1 = Operations.substractDouble((double) 1, Utils.getBest(S, j, A));
								Double var2 = Operations.multiplyDouble(var1, Utils.getLearnMap(j));
								Double var3 = Operations.addDouble(Utils.getBest(S, j, A), var2);
								SE = Operations.multiplyDouble(SE, var3);
							}
						}*/
						// System.out.println((A+1)+" "+Utils.getBest(S, Kc, A + 1)+" , "+A+" "+Utils.getBest(S, Kc, A));
				//		LearnNumerator = Operations.addDouble(LearnNumerator,Operations.substractDouble(Utils.getBest(S, Kc, A + 1), Utils.getBest(S, Kc, A)));
				//		LearnDenominator = Operations.addDouble(LearnDenominator, Operations.multiplyDouble(Operations.substractDouble((double) 1, Utils.getBest(S, Kc, A)), SE));
						// System.out.println(St+" "+A+" "+list_K);
						//System.out.println("LearnNumerator	 :" + LearnNumerator + " = " + LearnNumerator + "+"	+ Operations.substractDouble(Utils.getBest(S, Kc, A + 1), Utils.getBest(S, Kc, A)));
					   // System.out.println("LearnDenominator :" + LearnDenominator + " = " + LearnDenominator + "+"	+ Operations.multiplyDouble(Operations.substractDouble((double) 1, Utils.getBest(S, Kc, A)), SE));
					}
				}
				//System.out.println("LearnNumerator	 :" + LearnNumerator );
			   // System.out.println("LearnDenominator :" + LearnDenominator );
			}
			Double LnByLd;
			if(LearnDenominator==0){
				LnByLd = (double)0;
			}else{
				LnByLd = Operations.divideDouble(LearnNumerator, LearnDenominator);
				
			}
			for (int St = 0; St < GlobalConstants.total_Students; St++) {
				int S = Utils.getStudent(St);
				for (int A = Utils.getLast(S); A >= 1; A--) {
					int question = Utils.getQuestion(S, A);
					ArrayList<Integer> KCs = Utils.getQuestionMatrix(question);
					for (int list_K = 0; list_K < KCs.size(); list_K++) {
						System.out.println("LnByLd:"+LnByLd+" Utils.getBackward(" + S + ", " + KCs.get(list_K) + "," + (A + 1) + ") :"
								+ Utils.getBackward(S, KCs.get(list_K), A + 1));
						if(LnByLd==Utils.getBackward(S, KCs.get(list_K), A + 1)){
							System.out.println("FOUND");
						}
					}
				}
			}
			
			//System.out.println("LnByLd :" + LnByLd);
			Double max = Math.max(Double.valueOf(0.05), LnByLd);
			// System.out.println("setLearnMap :" + Math.min(Double.valueOf(0.5), max) + " " + Double.valueOf(0.5) + " " + max);
			Utils.setLearnMap(Kc, /*LnByLd*/ Math.min(Double.valueOf(0.5), max));
			
			//SIMULATION
			//Double max = Math.max(Double.valueOf(0.1), LnByLd);
			// System.out.println("setLearnMap :" + Math.min(Double.valueOf(0.5), max) + " " + Double.valueOf(0.5) + " " + max);
			//Utils.setLearnMap(Kc, Math.min(Double.valueOf(0.7), max));
		}
	}
}
