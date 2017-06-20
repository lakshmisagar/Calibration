package com.asu.seatr.calibration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.asu.seatr.database.DatabaseResponse;
import com.asu.seatr.database.SimulateDataBase;
import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;

/**
 * @author Lakshmisagar Kusnoor created on May 11, 2017
 *
 */
public class Calibration {

	static int climb = 0;
	static HashMap<Integer, ArrayList<Double>> climbMap = new HashMap<Integer, ArrayList<Double>>();
	static Double old_initalMastery[];
	static Double old_Learn[];
	static Double old_slip[];
	static Double old_guess[];

	static int total_students;
	static int total_KCs;
	static int total_Q;

	static Double average_IM;
	static Double average_L;
	static Double average_S;
	static Double average_G;

	private static Double climbOnce() {
		// System.out.println("CLIMBONCE****************************** ");
		saveParameters();
		try {
			FillingForward.fillingForward();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FillingBackward.fillingBackward();
		EstimateKcMastery.Estimate_KC_mastery_Best(total_students, total_KCs);
		calculateNewParameters();// update initalMaster,Learn,slip,guess
		Double change = changeInParameter();
		System.out.println("CHANGE******************************   " + change);
		return change;
	}

	private static void calculateNewParameters() {
		SlipsAndGuesses.updateSlipnGuesses();
		Learn.updateLearn();
		InitalMastery.updateIntialMastery();
	}

	private static Double changeInParameter() {
		// System.out.println("changeInParameter()");
		Double sum_initalMaster = (double) 0;
		Double sum_Learn = (double) 0;
		Double sum_slip = (double) 0;
		Double sum_guess = (double) 0;

		Double maxChange, IMChange, LChange, SChange, GChange;

		for (int K = 0; K < total_KCs; K++) {
			// System.out.println(K);
			// System.out.println("sum_Learn :"+sum_Learn);
			int Kc = Utils.getKc(K);
			Double diff_IM = Operations.substractDouble(old_initalMastery[K], Utils.getInitialMasteryMap(Kc));
			// System.out.println("diff_IM :"+diff_IM+" =
			// "+old_initalMastery[K]+" - "+Utils.getInitialMasteryMap(Kc));
			Double change_IM = Operations.divideDouble(diff_IM, old_initalMastery[K]);
			// System.out.println("change_IM :"+change_IM);
			sum_initalMaster = Operations.addDouble(sum_initalMaster, change_IM);
			// System.out.println("sum_initalMaster :"+sum_initalMaster);

			Double diff_L = Operations.substractDouble(old_Learn[K], Utils.getLearnMap(Kc));
			// System.out.println("diff_L :"+diff_L+" = "+old_Learn[K]+" -
			// "+Utils.getLearnMap(Kc));
			Double change_L = Operations.divideDouble(diff_L, old_Learn[K]);
			// System.out.println("change_L :"+change_L);
			sum_Learn = Operations.addDouble(sum_Learn, change_L);
			// System.out.println("sum_Learn = sum_Learn+change_L "+sum_Learn);

		}
		for (int Q = 0; Q < total_Q; Q++) {
			int question = Utils.getQuestion(Q);
			Double diff_S = Operations.substractDouble(old_slip[Q], Utils.getSlipMap(question));
			Double change_S = Operations.divideDouble(diff_S, old_slip[Q]);
			sum_slip = Operations.addDouble(sum_slip, change_S);

			Double diff_G = Operations.substractDouble(old_guess[Q], Utils.getGuessMap(question));
			Double change_G = Operations.divideDouble(diff_G, old_guess[Q]);
			sum_guess = Operations.addDouble(sum_guess, change_G);
			// System.out.println("sum_guess "+sum_guess+" = "+sum_guess+" +
			// "+change_G);
		}
		// System.out.println("LChange = "+sum_Learn+ " /
		// "+Double.valueOf(total_KCs));
		// System.out.println("GChange = "+sum_guess+ " /
		// "+Double.valueOf(total_Q));
		IMChange = Operations.divideDouble(sum_initalMaster, Double.valueOf(total_KCs));
		LChange = Operations.divideDouble(sum_Learn, Double.valueOf(total_KCs));
		SChange = Operations.divideDouble(sum_slip, Double.valueOf(total_Q));
		GChange = Operations.divideDouble(sum_guess, Double.valueOf(total_Q));
		System.out.println("IMChange   " + IMChange + "   LChange:  " + LChange + "  SChange: " + SChange
				+ "    GChange:  " + GChange);
		maxChange = Math.max(IMChange, LChange);
		maxChange = Math.max(maxChange, SChange);
		maxChange = Math.max(maxChange, GChange);
		return maxChange;
	}

	private static void saveParameters() {
		for (int KcIndex = 0; KcIndex < total_KCs; KcIndex++) {
			int kc = Utils.getKc(KcIndex);
			old_initalMastery[KcIndex] = Utils.getInitialMasteryMap(kc);
			old_Learn[KcIndex] = Utils.getLearnMap(kc);
		}
		for (int Q = 0; Q < total_Q; Q++) {
			int question = Utils.getQuestion(Q);
			old_slip[Q] = Utils.getSlipMap(question);
			old_guess[Q] = Utils.getGuessMap(question);
		}
	}

	private static void fillRandomParameters() {
		Random r = new Random();
		for (int KcIndex = 0; KcIndex < total_KCs; KcIndex++) {
			/*
			 * double r_initalMaster = 0.05 + r.nextDouble() * (0.95 - 0.05);
			 * double r_Learn = 0.05 + r.nextDouble() * (0.5 - 0.05);
			 */

			// SIMULATION
			// double randomValue = rangeMin + (rangeMax - rangeMin) *
			// r.nextDouble();
			double r_initalMaster = 0.1 + r.nextDouble() * (0.7 - 0.1);
			double r_Learn = 0.1 + r.nextDouble() * (0.7 - 0.1);
			SimulateDataBase.setInitialCompetence();

			int Kc = Utils.getKc(KcIndex);
			Utils.setInitialMasteryMap(Kc, Double.valueOf(r_initalMaster));
			Utils.setLearnMap(Kc, Double.valueOf(r_Learn));
			// System.out.println("Kc :"+Utils.getKc(KcIndex)+" IM:
			// "+Utils.getInitialMasteryMap(Kc)+" L: "+Utils.getLearnMap(Kc));
		}
		// System.out.println("START");
		for (int Q = 0; Q < total_Q; Q++) {
			/*
			 * double r_slip = 0.05 + r.nextDouble() * (0.45 - 0.05); double
			 * r_guess = 0.01 + r.nextDouble() * (0.5 - 0.01);
			 */

			// SIMULATION
			double r_slip = 0.001 + r.nextDouble() * (0.1 - 0.001);
			double r_guess = 0.001 + r.nextDouble() * (0.1 - 0.001);

			int question = Utils.getQuestion(Q);
			Utils.setSlipMap(question, Double.valueOf(r_slip));
			Utils.setGuessMap(question, Double.valueOf(r_guess));
		}
		// System.out.println("STOP");
		// printRandomParameters();
	}

	

	/*
	 * private static void printRandomParameters() { for (int K = 0; K <
	 * total_KCs; K++) { int Kc = Utils.getKc(K);
	 * //System.out.println("mInitialMastery["+Kc+"] "+Utils.getInitialMastery(
	 * Kc)); } System.out.println(); for (int K = 0; K < total_KCs; K++) { int
	 * Kc = Utils.getKc(K);
	 * //System.out.println("mLearn["+Kc+"] "+Utils.getLearn(Kc)); }
	 * System.out.println(); for (int Q = 0; Q < total_Q; Q++) {
	 * //System.out.println("mSlip["+Q+"] "+Utils.getSlip(Q)); }
	 * System.out.println(); for (int Q = 0; Q < total_Q; Q++) {
	 * //System.out.println("mGuess["+Q+"] "+Utils.getGuess(Q)); }
	 * System.out.println(); }
	 */

	private static void setDatabase() {
		System.out.println("setDatabase()");
		DatabaseResponse.setAllStudentsData();

	}

	private static void setSimulatedData() {
		SimulateDataBase.setAllStudentsData();
	}

	/*
	 * private static void setKcs() { for(int i=0;i<total_KCs;i++){
	 * Utils.setKc(i, i); } }
	 * 
	 * private static void setAnswerValues() { for( int
	 * S=0;S<total_students;S++){ HashMap<Integer,Integer> answer_AC_Map= new
	 * HashMap<Integer,Integer>(); for(int A=0;A<Utils.getLast(S);A++){ int
	 * value =(Math.random()<0.5)?0:1; answer_AC_Map.put(A, value); }
	 * Utils.setAnswer(S, answer_AC_Map); } }
	 */
	/*
	 * private static void setQuestionValues() { for( int
	 * St=0;St<total_students;St++){ int S = Utils.getStudent(St);
	 * HashMap<Integer,Integer> question_AQ_Map= new HashMap<Integer,Integer>();
	 * int i = 0; int Q = Utils.getQuestion(i);
	 * System.out.println(Utils.getLast(S)); for(int
	 * A=0;A<Utils.getLast(S);A++){ int value =(Math.random()<0.5)?0:1;
	 * //System.out.println("SQA :"+S+" "+Q+" "+A); question_AQ_Map.put(A, Q);
	 * i++; Q=Utils.getQuestion(i); } Utils.setQuestion(S, question_AQ_Map); } }
	 */

	/**
	 * get final parameters
	 */
	private static void updateNewPrameters() {
		Double sum_IM = (double) 0;
		Double sum_L = (double) 0;
		Double sum_S = (double) 0;
		Double sum_G = (double) 0;
		for (int K = 0; K < total_KCs; K++) {
			int Kc = Utils.getKc(K);
			sum_IM = Operations.addDouble(sum_IM, Utils.getInitialMasteryMap(Kc));
			// System.out.println("sum_IM :"+sum_IM);
			sum_L = Operations.addDouble(sum_L, Utils.getLearnMap(Kc));
			// System.out.println("sum_L :"+sum_L);
		}
		average_IM = Operations.divideDouble(sum_IM, Double.valueOf(total_KCs));
		average_L = Operations.divideDouble(sum_L, Double.valueOf(total_KCs));
		// System.out.println("average_IM :"+average_IM);
		// System.out.println("average_L :"+average_L);
		for (int Q = 0; Q < total_Q; Q++) {
			int question = Utils.getQuestion(Q);
			sum_S = Operations.addDouble(sum_S, Utils.getSlipMap(question));
			sum_G = Operations.addDouble(sum_G, Utils.getGuessMap(question));
			// System.out.println("sum_S :"+sum_S);
			// System.out.println("sum_G :"+sum_G);
		}
		average_S = Operations.divideDouble(sum_S, Double.valueOf(total_Q));
		average_G = Operations.divideDouble(sum_G, Double.valueOf(total_Q));
		// System.out.println("average_S :"+average_S);
		// System.out.println("average_G :"+average_G);
	}

	/**
	 * FINAL RESULT
	 */
	private static void PrintResult() {
		for (Map.Entry<Integer, ArrayList<Double>> entry : climbMap.entrySet()) {
			System.out.println(" CLIMB " + entry.getKey());
			ArrayList<Double> list = entry.getValue();
			System.out.println(" InitialMastery :" + list.get(0));
			System.out.println(" Learn :" + list.get(1));
			System.out.println(" Slip :" + list.get(2));
			System.out.println(" Guess :" + list.get(3));
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		// MySQLConnection.SetConnection();
		System.out.println("CALIBRATION.....................");
		PrintStream o = new PrintStream(new File("F:/RA/CALIB.txt"));
		System.setOut(o);
		// SetDB
		// setDatabase();
		setSimulatedData();

		// TODO get data from DB
		total_students = GlobalConstants.total_Students;
		total_KCs = GlobalConstants.total_KCs;
		total_Q = GlobalConstants.total_Questions;

		// instantiation of old values
		old_initalMastery = new Double[GlobalConstants.total_KCs + 1];
		old_Learn = new Double[GlobalConstants.total_KCs + 1];
		old_slip = new Double[GlobalConstants.total_Questions + 1];
		old_guess = new Double[GlobalConstants.total_Questions + 1];
		// DatabaseResponse.setKcs();
		// setAnswerValues();
		// setQuestionValues();

		while (climb < 10) {

			fillRandomParameters();
			if (climbOnce().compareTo(Double.valueOf(0.1)) == -1) {
				updateNewPrameters();
				ArrayList<Double> list = new ArrayList<Double>();
				list.add(average_IM);
				list.add(average_L);
				list.add(average_S);
				list.add(average_G);
				climb++;
				climbMap.put(climb, list);
				System.out.println("CLIMB -----------------------------------------> " + climb);
			}
		}

		PrintResult();
	}

}
