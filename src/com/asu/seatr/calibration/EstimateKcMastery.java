package com.asu.seatr.calibration;

import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;
/**
 * @author Lakshmisagar Kusnoor created on May 12, 2017
 *
 */
public class EstimateKcMastery {

	public static void Estimate_KC_mastery_Best(int studentCount, int kCount) {
		//System.out.println("EstimateKcMastery.....................");
		for (int St = 0; St < studentCount; St++) {
			//System.out.println(" St------------------------------:"+ St);
			int S = Utils.getStudent(St);
			for (int K = 0; K < kCount; K++) {
				int lastCount = Utils.getLast(S);
				for (int A = 1; A <= lastCount; A++) {
					//System.out.println("getForward  "+Utils.getForward(S, Utils.getKc(K), A)+"     getBackward   "+Utils.getBackward(S, Utils.getKc(K), A));
					//System.out.println("EstimateKcMastery::::");
					int PrevQuestion = Utils.getQuestion(S, A);
					System.out.println("IS CORRECT :"+Utils.simulategetSetAnswer(S, PrevQuestion));
					System.out.println("getForward S:"+S+" K:"+Utils.getKc(K)+" A:"+A+" - "+ Utils.getForward(S, Utils.getKc(K), A));
					System.out.println("getBackward S:"+S+" K:"+Utils.getKc(K)+" A:"+A+" - "+ Utils.getBackward(S, Utils.getKc(K), A));
					Double bestValue = Operations.multiplyDouble(Utils.getForward(S, Utils.getKc(K), A),Utils.getBackward(S, Utils.getKc(K), A));
					Utils.updateBest(S, Utils.getKc(K), A, bestValue);
					//System.out.println();
				}
			}
			//System.out.println();
		}
	}
}
