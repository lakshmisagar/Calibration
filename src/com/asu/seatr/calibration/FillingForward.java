/**
 * 
 */
package com.asu.seatr.calibration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;

/**
 * @author Lakshmisagar Kusnoor created on May 15, 2017
 *
 */
public class FillingForward {

	static Double initial_OK = new Double(1.0);

	public static void fillingForward() throws FileNotFoundException {
		System.out.println("FillingForward ...here.............................");
		
		int Ns = GlobalConstants.total_Students;
		int Nk = GlobalConstants.total_KCs;
		System.out.println("Ns :" + Ns + "   Nk  :" + Nk);
		for (int St = 0; St < Ns; St++) {
			int S = Utils.getStudent(St);
			 System.out.println("Student ...."+S);
			for (int K = 0; K < Nk; K++) {
				Double value = Utils.getInitialMasteryMap(Utils.getKc(K));
				//System.out.println("updateForward  1 (" + S + ", " + Utils.getKc(K) + "," + 1 + ") :" + value);
				Utils.updateForward(S, Utils.getKc(K), 1, value);
			}
			 System.out.println("Attempts......"+ Utils.getLast(S));
			for (int A = 1; A < Utils.getLast(S); A++) {
				 System.out.println("Attempt....."+A);
				int question = Utils.getQuestion(S, A);
				ArrayList<Integer> KCs = Utils.getQuestionMatrix(question);
				for (int K = 0; K < Nk; K++) {
					Utils.updateForward(S, Utils.getKc(K), A + 1, Utils.getForward(S, Utils.getKc(K), A));
				}
				Double OK = initial_OK;
				for (int list_K = 0; list_K < KCs.size(); list_K++) {
					System.out.println("KCs involved....."+KCs.get(list_K));
					OK = Operations.multiplyDouble(OK, Utils.getForward(S, KCs.get(list_K), A));
				}
				// System.out.println();
				Double slip = Utils.getSlipMap(question);
				Double guess = Utils.getGuessMap(question);
				Double slipPlusGuess = Operations.addDouble(slip, guess);
				Double oneMinusSlipPlusGuess = Operations.substractDouble((double) 1, slipPlusGuess);
				Double x = Operations.multiplyDouble(OK, oneMinusSlipPlusGuess);
				Double y = guess;
				
				if (Utils.getAnswer(S, A) == 0) {
					y = Operations.substractDouble((double) 1, y);
					x = -x;
				}
				// System.out.println("Kcs at Attempt "+A+" :"+
				// Utils.getLast(S));
				for (int list_K = 0; list_K < KCs.size(); list_K++) {
						Double forwardNumeratorValue = Operations.addDouble(
								Operations.multiplyDouble(y, Utils.getForward(S, Utils.getKc(list_K), A)), x);
						Double forwardfillingValue = Operations.divideDouble(forwardNumeratorValue,
								Operations.addDouble(y, x));
						System.out.println("x   "+x+"  y  "+y);
						System.out.println("forwardNumeratorValue  "+forwardNumeratorValue+"  x+y  "+Operations.addDouble(y, x));
						System.out.println("updateForward 2  (" + S + ", " + Utils.getKc(list_K) + "," + (A + 1) + ") :"
								+ forwardfillingValue);
						Utils.updateForward(S, Utils.getKc(list_K), A + 1, forwardfillingValue);
					} 
				Double SE = initial_OK;
				for (int list_K = 0; list_K < KCs.size(); list_K++) {
					Double forward = Utils.getForward(S, KCs.get(list_K), A+1);
					Double var1 = Operations.substractDouble((double) 1, forward);
					Double var2 = Operations.multiplyDouble(var1, Utils.getLearnMap(KCs.get(list_K)));
					Double var3 = Operations.addDouble(forward, var2);
					SE = Operations.multiplyDouble(SE, var3);
				}
				for (int list_K = 0; list_K < KCs.size(); list_K++) {
					Double forward = Utils.getForward(S, KCs.get(list_K), A+1);
					Double Z = Operations.substractDouble((double) 1,
							Operations.multiplyDouble(forward, Utils.getLearnMap(KCs.get(list_K))));
					Double nume = Operations.multiplyDouble(SE, Z);
					Double denom = Operations.addDouble(forward, Z);
					Double var2 = Operations.divideDouble(nume, denom);
					Double forwardfillingValue = Operations.addDouble(forward, var2);
					// System.out.println("updateForward("+S+", "+list_K+","+
					// (A+1)+") :"+ forwardfillingValue);
					Utils.updateForward(S, KCs.get(list_K), A + 1, forwardfillingValue);
				}
			}
		}
	}
}
