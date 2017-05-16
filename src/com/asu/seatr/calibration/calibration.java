package com.asu.seatr.calibration;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;
/**
 * @author Lakshmisagar Kusnoor created on May 11, 2017
 *
 */
public class calibration {

	static int climb = 0;
	static HashMap<Integer, ArrayList<String>> climbMap = new HashMap<Integer, ArrayList<String>>();
	static BigDecimal old_initalMastery[];
	static BigDecimal old_Learn[];
	static BigDecimal old_slip[];
	static BigDecimal old_guess[];

	static int total_students;
	static int total_KCs;
	static int total_Q;

	private static BigDecimal climbOnce() {
		saveParameters();
		EstimateKcMastery.Estimate_KC_mastery_Best(total_students, total_KCs);
		calculateNewParameters();// update initalMaster,Learn,slip,guess
		BigDecimal change = changeInParameter();
		return change;
	}

	/**
	 * 
	 */
	private static void calculateNewParameters() {
		SlipsAndGuesses.updateSlipnGuesses();
		Learn.updateLearn();
		InitalMastery.updateIntialMastery();
	}

	private static BigDecimal changeInParameter() {
		BigDecimal sum_initalMaster = BigDecimal.ZERO;
		BigDecimal sum_Learn = BigDecimal.ZERO;
		BigDecimal sum_slip = BigDecimal.ZERO;
		BigDecimal sum_guess = BigDecimal.ZERO;

		BigDecimal maxChange, IMChange, LChange, SChange, GChange;
		MathContext mc = new MathContext(6);

		for (int K = 0; K < total_KCs; K++) {
			BigDecimal diff_IM = old_initalMastery[K].subtract(Utils.mInitialMastery[K], mc);
			BigDecimal change_IM = diff_IM.abs().divide(old_initalMastery[K], mc);
			sum_initalMaster.add(change_IM);

			BigDecimal diff_L = old_Learn[K].subtract(Utils.mLearn[K], mc);
			BigDecimal change_L = diff_L.abs().divide(old_Learn[K], mc);
			sum_Learn.add(change_L);
		}
		for (int Q = 0; Q < total_Q; Q++) {
			BigDecimal diff_S = old_slip[Q].subtract(Utils.mSlip[Q], mc);
			BigDecimal change_S = diff_S.abs().divide(old_slip[Q], mc);
			sum_slip.add(change_S);

			BigDecimal diff_G = old_guess[Q].subtract(Utils.mGuess[Q], mc);
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

	private static void saveParameters() {
		for (int K = 0; K < total_KCs; K++) {
			old_initalMastery[K] = Utils.mInitialMastery[K];
			old_Learn[K] = Utils.mLearn[K];
		}
		for (int Q = 0; Q < total_Q; Q++) {
			old_slip[Q] = Utils.mSlip[Q];
			old_guess[Q] = Utils.mGuess[Q];
		}
	}

	private static void fillRandomParameters() {
		Random r = new Random();
		for (int K = 0; K < total_KCs; K++) {
			double r_initalMaster = 0.05 + r.nextDouble() * (0.95 - 0.05);
			double r_Learn = 0.05 + r.nextDouble() * (0.5 - 0.05);
			Utils.mInitialMastery[K] = BigDecimal.valueOf(r_initalMaster);
			Utils.mLearn[K] = BigDecimal.valueOf(r_Learn);
		}
		for (int Q = 0; Q < total_Q; Q++) {
			double r_slip = 0.05 + r.nextDouble() * (0.45 - 0.05);
			double r_guess = 0.01 + r.nextDouble() * (0.5 - 0.01);
			Utils.mSlip[Q] = BigDecimal.valueOf(r_slip);
			Utils.mGuess[Q] = BigDecimal.valueOf(r_guess);
		}
	}

	public static void main(String[] args) {
		// TODO get data from DB
		total_students = GlobalConstants.total_Students;
		total_KCs = GlobalConstants.total_KCs;
		total_Q = GlobalConstants.total_Questions;
		
		//instantiation of old values
		old_initalMastery = new BigDecimal[GlobalConstants.total_KCs];
		old_Learn = new BigDecimal[GlobalConstants.total_KCs];
		old_slip = new BigDecimal[GlobalConstants.total_Questions];
		old_guess = new BigDecimal[GlobalConstants.total_Questions];
		
		while (climb < 10) {
			fillRandomParameters();
			while (climbOnce().compareTo(BigDecimal.valueOf(0.1)) == -1) {
				ArrayList<String> list = new ArrayList<String>();
				list.add(String.valueOf(Utils.mInitialMastery));
				list.add(String.valueOf(Utils.mLearn));
				list.add(String.valueOf(Utils.mSlip));
				list.add(String.valueOf(Utils.mGuess));
				climbMap.put(climb++, list);
			}
		}
	}
}
