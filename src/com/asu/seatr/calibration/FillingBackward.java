/**
 * 
 */
package com.asu.seatr.calibration;

import java.util.ArrayList;
import java.util.Random;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;

/**
 * @author Lakshmisagar Kusnoor created on May 15, 2017
 *
 */
public class FillingBackward {

	static Random r = new Random();

	static Double initial_OK = new Double(1.0);

	public static void fillingBackward() {
		//System.out.println("SAGAR - START");
		//System.out.println("FillingBackward ................................");
		int Ns = GlobalConstants.total_Students;
		int Nk = GlobalConstants.total_KCs;
		for (int St = 0; St < Ns; St++) {
			int S = Utils.getStudent(St);
			 //System.out.println("Student ------------"+S);
			for (int K = 0; K < Nk; K++) {
				Utils.updateBackward(S, Utils.getKc(K), Utils.getLast(S)+1,
						Utils.getForward(S, Utils.getKc(K), Utils.getLast(S)));
				//System.out.println(" lastUpdatebackward : " + Utils.getBackward(S, Utils.getKc(K), Utils.getLast(S)+1) );
			} // is this the end of K loop? is it Utils.getLast(S)+1 or
				// Utils.getLast(S) ?
				// System.out.println("Attempts total-"+Utils.getLast(S));
			for (int A = Utils.getLast(S); A >= 1; A--) {
				// System.out.println("Attempt ---"+A);
				int question = Utils.getQuestion(S, A);
				ArrayList<Integer> KCs = Utils.getQuestionMatrix(question);
				Double SE = (double) 1;
				for (int list_K = 0; list_K < KCs.size(); list_K++) {
					SE = Operations.multiplyDouble(SE, Utils.getBackward(S, KCs.get(list_K), A + 1));
					//System.out.println("SE :"+SE+" * Utils.getBackward("+S+", "+KCs.get(list_K)+","+( A + 1)+") :"+Utils.getBackward(S, KCs.get(list_K), A + 1));
				}
				Double x;
				for (int list_K = 0; list_K < KCs.size(); list_K++) {
					int localK = KCs.get(list_K);
					 //System.out.println("localK :"+localK);

					 //System.out.println("SE :"+SE);
					 //System.out.println("Utils.getLearn(localK) :"+Utils.getLearnMap(localK));
					Double var1 = Operations.multiplyDouble(Utils.getLearnMap(localK), SE);
					 //System.out.println("var1: "+var1);

					Double var2 = Utils.getBackward(S, localK, A + 1);
					// System.out.println("Utils.getBackward("+S+","+ localK+","+ (A + 1)+"): "+var2);

					x = Operations.divideDouble(var1, var2);
					 //System.out.println(" X :"+x);

					Double newVar1 = Operations.substractDouble(var2, x);
					 //System.out.println(" numerator var2-x :"+newVar1);

					Double newVar2 = Operations.substractDouble((double) 1, x);
					 //System.out.println("denominator 1-x :"+newVar2);

					Double backwardfillingValue = Operations.divideDouble(newVar1, newVar2);
					//System.out.println("1  S:"+S+" K:"+localK+" A:"+A+" " + backwardfillingValue);
					if(backwardfillingValue<0)backwardfillingValue=0.01;
					else if(backwardfillingValue>1)backwardfillingValue = (double)1;
					Utils.updateBackward(S, localK, A, backwardfillingValue);
				}

				Double OK = initial_OK;
				for (int list_K = 0; list_K < KCs.size(); list_K++) {
					OK = Operations.multiplyDouble(OK, Utils.getBackward(S, KCs.get(list_K), A));
				}
				//System.out.println(" OK :"+OK);
				Double slip = Utils.getSlipMap(question);
				//System.out.println(" Slip :"+slip);
				Double guess = Utils.getGuessMap(question);
				//System.out.println(" GUess :"+guess);
				Double slipPlusGuess = Operations.addDouble(slip, guess);
				//System.out.println("slipPlusGuess :"+slipPlusGuess);
				Double oneMinusSlipPlusGuess = Operations.substractDouble((double) 1, slipPlusGuess);
				//System.out.println(" 1-(S+G) :"+oneMinusSlipPlusGuess);
				x = Operations.multiplyDouble(OK, oneMinusSlipPlusGuess);
				Double y = guess;

				/*
				 * if (Utils.getAnswer(S, A) == 0) { y =
				 * Operations.substractDouble((double)1,y); x = -x;
				 */
				// SIMULATION
				//System.out.println(" IS CORRECT : "+Utils.simulategetSetAnswer(S, question));
				if (Utils.simulategetSetAnswer(S, question) == 0) {
					y = Operations.substractDouble((double) 1, y);
					x = -x;
				}

				for (int innerK = 0; innerK < Nk; innerK++) {
					int innerKc = Utils.getKc(innerK);
					if (KCs.contains(innerKc)) {
						//System.out.println(" X :"+x);
						//System.out.println(" Y :"+y);
						Double backwardNumeratorValue = Operations
								.addDouble(Operations.multiplyDouble(y, Utils.getBackward(S, innerKc, A)), x);
						Double forwardfillingValue = Operations.divideDouble(backwardNumeratorValue,
								Operations.addDouble(y, x));
						/*System.out.println("backwardNumeratorValue :"+backwardNumeratorValue);
						System.out.println("backwardDenomenatorValue    :"+Operations.addDouble(y, x));
						System.out.println("2  S:"+S+" K:"+innerKc+" A:"+A+" "+ forwardfillingValue);
						*/Utils.updateBackward(S, innerKc, A, forwardfillingValue);
					} else {
						//System.out.println("3  S:"+S+" K:"+innerKc+" A:"+A+" " + Utils.getBackward(S, innerKc, A + 1));
						Utils.updateBackward(S, innerKc, A, Utils.getBackward(S, innerKc, A + 1));
					}
					// double randomUB = 0.05 + r.nextDouble() * (0.95 - 0.05);
					// System.out.println("1 GET Forward - S:"+S+" K:"+innerKc+"
					// A:"+A+" ="+Utils.getForward(S, innerKc, A));
					// Utils.updateBackward(S, innerKc, A,randomUB);
					// System.out.println("2 GET Forward - S:"+S+" K:"+innerKc+"
					// A:"+A+" ="+Utils.getForward(S, innerKc, A));
				}
				//System.out.println();
			}
			//System.out.println();
		}
		//System.out.println("SAGAR - END");
	}

}
