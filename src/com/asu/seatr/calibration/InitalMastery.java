package com.asu.seatr.calibration;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on May 12, 2017
 *
 */

public class InitalMastery {

	public static void updateIntialMastery() {
		System.out.println("updateIntialMastery ................................");
		for (int K = 0; K < GlobalConstants.total_KCs; K++) {
			Double Sum = (double)0;
			int Count = 0;
			for (int St = 0; St < GlobalConstants.total_Students; St++) {
				int S = Utils.getStudent(St);
				//System.out.println("Sum :"+Operations.addDouble(Sum,Utils.getBest(S, Utils.getKc(K), 1))+" =  "+Sum+" + "+Utils.getBest(S, Utils.getKc(K), 1));
				Sum = Operations.addDouble(Sum,Utils.getBest(S, Utils.getKc(K), 1));
				Count = Count + 1;
			}
			/*System.out.println("Sum :"+Sum+"  Count :"+Count);
			System.out.println(" old value :"+Utils.getKc(K)+"  "+Utils.getInitialMasteryMap(Utils.getKc(K)));
			System.out.println("IntialMastery  "+Utils.getKc(K)+" "+Operations.divideDouble(Sum,(double)Count));
			System.out.println();
			System.out.println();*/
			Utils.setInitialMasteryMap(Utils.getKc(K), Operations.divideDouble(Sum,(double)Count));
		}
	}
}
