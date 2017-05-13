package com.asu.seatr.calibration;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.asu.seatr.utils.GlobalConstants;

public class Calibration {

	static int climb = 0;
	static HashMap<Integer, ArrayList<String>> climbMap = new HashMap<Integer, ArrayList<String>>();
	static BigDecimal old_initalMaster[];
	static BigDecimal old_Learn[];
	static BigDecimal old_slip[];
	static BigDecimal old_guess[];

	static BigDecimal initalMaster[];
	static BigDecimal Learn[];
	static BigDecimal slip[];
	static BigDecimal guess[];

	static int total_students;
	static int total_KCs;
	static int total_Q;

	private static BigDecimal climbOnce(BigDecimal[] iM, BigDecimal[] l, BigDecimal[] s, BigDecimal[] g) {
		saveParameters(iM, l, s, g);
		EstimateKcMastery.Estimate_KC_mastery_Best(total_students, total_KCs);
		calculateNewParameters();// update initalMaster,Learn,slip,guess
		BigDecimal change = changeInParameter();
		return change;
	}

	private static BigDecimal changeInParameter() {
		BigDecimal sum_initalMaster = new BigDecimal(0);
		BigDecimal sum_Learn = new BigDecimal(0);
		BigDecimal sum_slip = new BigDecimal(0);
		BigDecimal sum_guess = new BigDecimal(0);

		BigDecimal maxChange, IMChange, LChange, SChange, GChange;
		MathContext mc = new MathContext(6);

		for (int K = 0; K < total_KCs; K++) {
			BigDecimal diff_IM = old_initalMaster[K].subtract(initalMaster[K], mc);
			BigDecimal change_IM = diff_IM.abs().divide(old_initalMaster[K], mc);
			sum_initalMaster.add(change_IM);

			BigDecimal diff_L = old_Learn[K].subtract(Learn[K], mc);
			BigDecimal change_L = diff_L.abs().divide(old_Learn[K], mc);
			sum_Learn.add(change_L);
		}
		for (int Q = 0; Q < total_Q; Q++) {
			BigDecimal diff_S = old_slip[Q].subtract(slip[Q], mc);
			BigDecimal change_S = diff_S.abs().divide(old_slip[Q], mc);
			sum_slip.add(change_S);

			BigDecimal diff_G = old_guess[Q].subtract(guess[Q], mc);
			BigDecimal change_G = diff_G.abs().divide(old_guess[Q], mc);
			sum_guess.add(change_G);
		}
		IMChange = sum_initalMaster.divide(BigDecimal.valueOf(total_KCs));
		LChange = sum_Learn.divide(BigDecimal.valueOf(total_KCs));
		SChange = sum_slip.divide(BigDecimal.valueOf(total_Q));
		GChange = sum_guess.divide(BigDecimal.valueOf(total_Q));

		maxChange = IMChange.max(LChange);
		maxChange = maxChange.max(SChange);
		maxChange = maxChange.max(GChange);
		return maxChange;
	}

	private static void saveParameters(BigDecimal[] iM, BigDecimal[] l, BigDecimal[] s, BigDecimal[] g) {
		old_initalMaster = iM;
		old_Learn = l;
		old_slip = s;
		old_guess = g;
	}

	private static void fillRandomParameters() {
		Random r = new Random();
		for (int K = 0; K < total_KCs; K++) {
			double r_initalMaster = 0.05 + r.nextDouble() * (0.95 - 0.05);
			double r_Learn = 0.05 + r.nextDouble() * (0.5 - 0.05);
			initalMaster[K] = BigDecimal.valueOf(r_initalMaster);
			Learn[K] = BigDecimal.valueOf(r_Learn);
		}
		for (int Q = 0; Q < total_Q; Q++) {
			double r_slip = 0.05 + r.nextDouble() * (0.45 - 0.05);
			double r_guess = 0.01 + r.nextDouble() * (0.5 - 0.01);
			slip[Q] = BigDecimal.valueOf(r_slip);
			guess[Q] = BigDecimal.valueOf(r_guess);
		}
	}

	public static void main(String[] args) {
		// TODO get data from DB
		total_students = GlobalConstants.total_Students;
		total_KCs = GlobalConstants.total_KCs;
		total_Q = GlobalConstants.total_Questions;

		while (climb < 10) {
			fillRandomParameters();
			while (climbOnce(initalMaster, Learn, slip, guess).compareTo(BigDecimal.valueOf(0.1)) == -1) {
				ArrayList<String> list = new ArrayList<String>();
				list.add(String.valueOf(initalMaster));
				list.add(String.valueOf(Learn));
				list.add(String.valueOf(slip));
				list.add(String.valueOf(guess));
				climbMap.put(climb++, list);
			}
		}
	}
}
