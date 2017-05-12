package com.asu.seatr.calibration;

import java.util.Random;

public class calibration {

	static int climb = 0;
	static double initalMaster[];
	static double Learn[];
	static double slip[];
	static double guess[];
	
	public static void main(String[] args) {
		int mKnowledgeComponent = 1;
		int mQuestion = 1;
		while(climb<10){
			fillRandomParameters(mKnowledgeComponent, mQuestion);
			while(climbOnce()>0.1){
				
			}
		}
	}
	private static double climbOnce() {
		EstimateKcMastery
		return 0;
	}
	private static void fillRandomParameters(int K, int Q) {
		Random r = new Random();
		double r_initalMaster = 0.05 + r.nextDouble() * (0.95 - 0.05);
		double r_Learn = 0.05 + r.nextDouble() * (0.5 - 0.05);
		double r_slip = 0.05 + r.nextDouble() * (0.45 - 0.05);
		double r_guess = 0.01 + r.nextDouble() * (0.5 - 0.01);
		
		initalMaster[K] = r_initalMaster;
		Learn[K] = r_Learn;
		slip[Q] = r_slip;
		guess[Q] = r_guess;
		
	}
}
