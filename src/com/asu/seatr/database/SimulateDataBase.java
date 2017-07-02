package com.asu.seatr.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;

public class SimulateDataBase {
	public static void setAllStudentsData() {

		GlobalConstants.total_KCs = 1;
		GlobalConstants.total_Questions = 10;
		GlobalConstants.total_Students = 1;
		Random r = new Random();

		// KC
		for (int i = 0; i < GlobalConstants.total_KCs; i++) {
			int kcValue = i;
			Utils.setKc(i, kcValue);
			Utils.setKcMap(kcValue);
		}

		// S
		int[] ids = new int[GlobalConstants.total_Students];
		for (int i = 0; i < GlobalConstants.total_Students; i++) {
			ids[i] = i;
		}
		Utils.setStudentsList(ids);

		// Q
		for (int q = 0; q < GlobalConstants.total_Questions; q++) {
			Utils.setQuestion(q, q);
			Utils.setQuestionMap(q);
			Utils.setClassIdQuestion(q, q);
			// Q KC
			int n_KCs = r.nextInt((GlobalConstants.total_KCs - 1) + 1) + 1;
			//System.out.println("setQuestionMatrix  n_KCs: "+n_KCs);
			for (int j = 0; j < n_KCs; j++) {
				int kc = r.nextInt(((GlobalConstants.total_KCs - 1) - 0) + 1) + 0;
				Utils.setQuestionMatrix(q, kc);
			}
		}

		// LAST
		// set of Q answered by student
		HashMap<Integer, Integer> question_AQ_Map = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> answer_AC_Map = new HashMap<Integer, Integer>();
		
		for (int st = 0; st < GlobalConstants.total_Students; st++) {
			int id = st;
			HashMap<Integer, Integer> inner_setAnswer = new HashMap<Integer, Integer>();
			int numberOfQuestionsAttempted =/* r.nextInt((*/GlobalConstants.total_Questions /*- 1) + 1) + 1*/;
			Set<Integer> generated = new LinkedHashSet<Integer>();
			while(generated.size()<numberOfQuestionsAttempted){
				int Q = r.nextInt(((GlobalConstants.total_Questions - 1) - 0) + 1) + 0;
				generated.add(Q);
			}
			for(Integer Q : generated){
				inner_setAnswer.put(Q, 0);
				int correct = /*(Math.random() < 0.5) ? 0 : 1*/0;
				int count = Utils.getLast(id);
				count++;
				Utils.setLast(id, count);
				int A = count;
				answer_AC_Map.put(A, correct);
				Utils.setAnswer(st, answer_AC_Map);
				question_AQ_Map.put(A, Q);
				Utils.setQuestion(st, question_AQ_Map);
			}
			Utils.simulateInitalizeSetAnswer(id,inner_setAnswer);
		}
		
		
		// ************** MAP - S K A ***************************
		for (int St = 0; St < GlobalConstants.total_Students; St++) {
			int S = Utils.getStudent(St);
			HashMap<Integer, HashMap<Integer, Double>> inner_KcA_Forward_Map = new HashMap<Integer, HashMap<Integer, Double>>();
			HashMap<Integer, HashMap<Integer, Double>> inner_KcA_Backward_Map = new HashMap<Integer, HashMap<Integer, Double>>();
			HashMap<Integer, HashMap<Integer, Double>> inner_KcA_Best_Map = new HashMap<Integer, HashMap<Integer, Double>>();
			for (int K = 0; K < GlobalConstants.total_KCs; K++) {
				int Kc = Utils.getKc(K);
				HashMap<Integer, Double> inner_AV_Forward_Map = new HashMap<Integer, Double>();
				HashMap<Integer, Double> inner_AV_Backward_Map = new HashMap<Integer, Double>();
				HashMap<Integer, Double> inner_AV_Best_Map = new HashMap<Integer, Double>();
				inner_KcA_Forward_Map.put(Kc, inner_AV_Forward_Map);
				inner_KcA_Backward_Map.put(Kc, inner_AV_Backward_Map);
				inner_KcA_Best_Map.put(Kc, inner_AV_Best_Map);
			}
			Utils.initalizeForwardBackwardBestMap(S, inner_KcA_Forward_Map, inner_KcA_Backward_Map, inner_KcA_Best_Map);
		}

		// ************** MAP - S K COMPETENCE ***************************
		for (int St = 0; St < GlobalConstants.total_Students; St++) {
			int S = Utils.getStudent(St);
			HashMap<Integer, Double> inner_KcV_Map = new HashMap<Integer, Double>();
			for (int K = 0; K < GlobalConstants.total_KCs; K++) {
				int Kc = Utils.getKc(K);
				inner_KcV_Map.put(Kc, (double) 0);
			}
			Utils.initalizeCompetence(S, inner_KcV_Map);
		}

		// ************** MAP - S Q COMPETENCE ***************************
	}

	// SIMULATION
	public static void setInitialCompetence() {
		for (int St = 0; St < GlobalConstants.total_Students; St++) {
			int S = Utils.getStudent(St);
			for (int K = 0; K < GlobalConstants.total_KCs; K++) {
				int Kc = Utils.getKc(K);
				Utils.setCompetence(S, Kc, Utils.getInitialMasteryMap(Kc));
			}
		}
		OKCompetence();
	}

	public static void OKCompetence() {
		for (int St = 0; St < GlobalConstants.total_Students; St++) {
			int S = Utils.getStudent(St);
			for(int q=0;q<GlobalConstants.total_Questions;q++){
				int Q = Utils.getQuestion(q);
				Double OK = (double)1;
				ArrayList<Integer> KCs = Utils.getQuestionMatrix(Q);
				for(int kc =0;kc<KCs.size();kc++){
					int KC = Utils.getKc(kc);
					OK = OK*Utils.getCompetence(S,KC);
				}
				Double A = OK*(1-Utils.getSlipMap(Q))+(1-OK)*Utils.getGuessMap(Q);
				int randomC = (Math.random() < 0.5) ? 0 : 1;
				int ans = 0;
				if(A>=randomC){
					ans=1;
				}
				//System.out.println("simulateSetAnswer : "+A+" >= "+randomC+" = "+ans);
				Utils.simulateSetAnswer(S, Q, ans);
				for(int kc =0;kc<KCs.size();kc++){
					int KC = Utils.getKc(kc);
					Double value = Utils.getCompetence(S,KC)+Utils.getLearnMap(KC)*(1-Utils.getCompetence(S,KC));
					Utils.setCompetence(S, KC, value);
				}
			}
			
		}
	}
	
	
	
	
	
	
}
