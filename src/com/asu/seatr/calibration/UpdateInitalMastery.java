package com.asu.seatr.calibration;

public class UpdateInitalMastery {

		for(int k = 0; k < Nk -1; k++){
			float Sum = 0;
			int Count = 0;
			for(int s = 0; s < Ns -1; s++){
				Sum = Sum + Best[s,k,1];
				Count = Count + 1;
			}
			Inital_Mastery[k] = Sum / Count ;
		}
	}
}
