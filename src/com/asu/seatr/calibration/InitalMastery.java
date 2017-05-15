package com.asu.seatr.calibration;

import java.math.BigDecimal;

import com.asu.seatr.utils.Utils;

public class InitalMastery {
	
	public static void updateIntialMastery(){
		
	for(int K = 0; K < Calibration.total_KCs - 1; K++){
		BigDecimal Sum = new BigDecimal(0);
		int Count = 0;
		for(int S = 0; S < Calibration.total_students - 1; S++){
				
			Sum = Sum.add(Utils.getBest(S, K, 1));
			Count = Count + 1;
		}
		BigDecimal bigDecimalCount = new BigDecimal(Count);
		Calibration.initalMaster[K] = Sum.divide(bigDecimalCount);
		}
}
}
